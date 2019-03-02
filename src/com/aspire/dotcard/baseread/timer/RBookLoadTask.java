
package com.aspire.dotcard.baseread.timer;

import java.io.File;
import java.util.TimerTask;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseVideo.conf.BaseVideoConfig;
import com.aspire.dotcard.baseread.BaseReadFtpProcess;
import com.aspire.dotcard.baseread.biz.RReadBO;
import com.aspire.dotcard.baseread.config.BaseReadConfig;
import com.aspire.ponaadmin.web.mail.Mail;

public class RBookLoadTask extends TimerTask
{
    protected static JLogger logger = LoggerFactory.getLogger(RBookLoadTask.class);

    private boolean isDelRank;
    
    /**
     * 是否打出错误详细信息
     */
    private boolean isPutAll = false;
    
    public void run()
    {
        logger.debug("import book base begin!");
        // Ftp服务
        BaseReadFtpProcess ftp = new BaseReadFtpProcess();

        StringBuffer msgInfo = new StringBuffer();
        String encoding = BaseReadConfig.get("fileEncoding");
        String sep = BaseReadConfig.get("BBookListSep");
        if (null == encoding)
        {
            encoding = "UTF-8";
        }
        if (null == sep)
        {
            sep = "|";
        }

        // 获取要处理的对象
        if (sep.startsWith("0x"))
        {
            // 0x开头的，表示是16进制的，需要转换
            String s = sep.substring(2, sep.length());
            int i1 = Integer.parseInt(s, 16);
            char c = ( char ) i1;
            sep = String.valueOf(c);
        }

        /**
         * 0,处理的总行数；1，成功新增；2，成功修改；3，成功删除；4，数据检查不合法；5，数据处理失败
         */

        // 图书分类
        int[] rs = new int[6];
        synBookType(ftp, msgInfo, encoding, sep, rs);
        msgInfo.append("<br>");
        // 作者信息同步
        rs = new int[6];
        synBookAuthor(ftp, msgInfo, encoding, sep, rs);
        msgInfo.append("<br>");
        // 图书信息
        rs = new int[6];
        synBookInfo(ftp, msgInfo, encoding, sep, rs);
        msgInfo.append("<br>");
        // 推荐图书信息
        rs = new int[6];
        synBookRecommend(ftp, msgInfo, encoding, sep, rs);
        msgInfo.append("<br>");
        
        // -------------0930版本需求新增开始
        // 猜你喜欢——历史阅读推荐接口
        rs = new int[6];
        synLikeHisRead(ftp, msgInfo, encoding, sep, rs);
        msgInfo.append("<br>");
        // 猜你喜欢——名家推荐接口
        rs = new int[6];
        synLikeAuthor(ftp, msgInfo, encoding, sep, rs);
        msgInfo.append("<br>");
        // 猜你喜欢——图书级阅读关联推荐接口
        rs = new int[6];
        synLikeReadPercentage(ftp, msgInfo, encoding, sep, rs);
        msgInfo.append("<br>");
        // 猜你喜欢——图书级订购关联推荐接口
        rs = new int[6];
        synLikeOrderPercentage(ftp, msgInfo, encoding, sep, rs);
        msgInfo.append("<br>");
        // --------------0930版本需求新增结束
        
        // 图书统计信息
        rs = new int[6];
        synBookCount(ftp, msgInfo, encoding, sep, rs);
        msgInfo.append("<br>");
        // 图书更新信息
        rs = new int[6];
        synBookUpdate(ftp, msgInfo, encoding, sep, rs);
        msgInfo.append("<br>");
        // 专区信息
        rs = new int[6];
        synBookArea(ftp, msgInfo, encoding, sep, rs);
        msgInfo.append("<br>");

        // 第一步：根据专区信息，删除现货架中多出的货架
        RReadBO.getInstance().cleanOldSimulationDataTree();
        
        // 第二步：根据专区信息，新增或更新货架中存在的货架
        RReadBO.getInstance().diySimulationDataTree();
        
        // 书包信息
        rs = new int[6];
        synBookMonth(ftp, msgInfo, encoding, sep, rs);
        msgInfo.append("<br>");
        
        // 书包地域信息
        rs = new int[4];
        synBookMonthCity(ftp, msgInfo, encoding, sep, rs);
        msgInfo.append("<br>");
        
        // 终端目录
        rs = new int[6];
        synMoDirectory(ftp, msgInfo, encoding, sep, rs);
        msgInfo.append("<br>");
        
        // 专区内容信息
        rs = new int[6];
        synBookAreaReference(ftp, msgInfo, encoding, sep, rs);
        msgInfo.append("<br>");

        isDelRank = false;
        
        // 排行榜数据
        rs = new int[6];
        synBookTotalRank(ftp, msgInfo, encoding, sep, rs);
        msgInfo.append("<br>");
        
		//月排行
		rs = new int[6];
		synBookMonthRank(ftp, msgInfo, encoding, sep, rs);	
		msgInfo.append("<br>");
		
		//周排行
		rs = new int[6];
		synBookWeekRank(ftp, msgInfo, encoding, sep, rs);
        
		
        // 第一步：删除商品信息，在根据排行榜信息，删除现货架中多出的货架
        RReadBO.getInstance().cleanOldSimulationDataRank();
        
        // 第二步：根据排行榜信息，新增或更新货架中存在的货架
        RReadBO.getInstance().diySimulationDataRank();
        
        // 第三步：根据排行榜信息，加入商品表中
        RReadBO.getInstance().addDataByRankToReference();

        // 更新各货架下商品总数
        RReadBO.getInstance().updateCateTotal();

        // 发送邮件
        logger.info("send mail begin!");
        String[] mailTo = BaseReadConfig.get("BaseBookSynMailto").split(",");
        String synBaseBookSubject = BaseReadConfig.get("synBaseBookSubject");
        Mail.sendMail(synBaseBookSubject, msgInfo.toString(), mailTo);
        logger.info("send mail end!");
        logger.debug("import book base end!");
    }

    /**
     * 图书总排行信息
     * 
     * @param ftp
     * @param msgInfo
     * @param encoding
     * @param sep
     * @param rs
     */
    public void synBookTotalRank(BaseReadFtpProcess ftp, StringBuffer msgInfo,
                                 String encoding, String sep, int[] rs)
    {
        logger.info("import base read totalrank info begin!");
        StringBuffer errorRowNumber = new StringBuffer();
        StringBuffer error = new StringBuffer();
        boolean hasVerf = true;
        boolean isDelTable = false;

        // 图书分类信息
        try
        {
            if (hasVerf)
            {
                msgInfo.append(synReadVerf("图书排行信息",
                                           "BookTotalRankVerfRegex",
                                           ftp,
                                           encoding,
                                           sep));
            }
            msgInfo.append("导入图书排行信息数据结果：");
            msgInfo.append("<br>");
            String[] fNameRegex = BaseReadConfig.get("BookTotalRankRegex")
                                                .split(",");

            // ftp 下载到本地
            String lfs[] = ftp.process(fNameRegex);

            if (lfs.length == 0)
            {
                msgInfo.append("没有找到图书排行信息数据文件");
                msgInfo.append("<br>");
            }
            else
            {
                // 判断文件长度是否为0
                if (isNullFile(lfs))
                {
                    // 加入邮件错误信息..............
                    msgInfo.append("此次同步的数据文件都为空，此次类型数据同步中止！！！");
                    msgInfo.append("<br>");
                }
                // 文件长度至少有一个不为空，且还要清表全量同步
                else if (isDelTable && !isDelRank)
                {
                    // 清空处理。但现在没想好怎么清
                    delTable("com.aspire.dotcard.baseread.dao.RankDao.delete");
                    isDelRank = true;
                }
                
                RReadBO.getInstance().dealBaseBookTotalRank(encoding,
                                                            sep,
                                                            rs,
                                                            lfs,
                                                            errorRowNumber,
                                                            error,"total");
                msgInfo.append("共处理行数：" + rs[0]);
                msgInfo.append(";<br>成功新增：" + rs[1]);
                msgInfo.append(";<br>成功修改：" + rs[2]);
                msgInfo.append(";<br>成功下线：" + rs[3]);
                msgInfo.append(";<br>数据检查不合法：" + rs[4]);
                if (rs[4] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>失败校验所在行具体为：");
                    msgInfo.append(errorRowNumber);
                }
                msgInfo.append(";<br>数据处理失败：" + rs[5]);
                if (rs[5] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>处理失败所在行具体为：第");
                    msgInfo.append(error.substring(0, error.length() - 2));
                    msgInfo.append("行。");
                }
                msgInfo.append("<br>");
            }
        }
        catch (Exception e)
        {
            logger.error("导入图书排行信息数据操作失败" + e);
            logger.error(e);
            msgInfo.append("导入图书排行信息数据操作失败" + e);
            msgInfo.append("<br>");
        }
        logger.info("import base read totalrank info begin!");
    }
    
    /**
     * 图书月排行信息
     * 
     * @param ftp
     * @param msgInfo
     * @param encoding
     * @param sep
     * @param rs
     */
    public void synBookMonthRank(BaseReadFtpProcess ftp, StringBuffer msgInfo,
                                 String encoding, String sep, int[] rs)
    {
        logger.info("import base read monthrank info begin!");
        StringBuffer errorRowNumber = new StringBuffer();
        StringBuffer error = new StringBuffer();
        boolean hasVerf = true;
        boolean isDelTable = false;

        // 图书分类信息
        try
        {
            if (hasVerf)
            {
                msgInfo.append(synReadVerf("图书月排行信息",
                                           "BookMonthRankVerfRegex",
                                           ftp,
                                           encoding,
                                           sep));
            }
            msgInfo.append("导入图书月排行信息数据结果：");
            msgInfo.append("<br>");
            String[] fNameRegex = BaseReadConfig.get("BookMonthRankRegex")
                                                .split(",");

            // ftp 下载到本地
            String lfs[] = ftp.process(fNameRegex);

            if (lfs.length == 0)
            {
                msgInfo.append("没有找到图书月排行信息数据文件");
                msgInfo.append("<br>");
            }
            else
            {
                // 判断文件长度是否为0
                if (isNullFile(lfs))
                {
                    // 加入邮件错误信息..............
                    msgInfo.append("此次同步的数据文件都为空，此次类型数据同步中止！！！");
                    msgInfo.append("<br>");
                }
                // 文件长度至少有一个不为空，且还要清表全量同步
                else if (isDelTable && !isDelRank)
                {
                    // 清空处理。但现在没想好怎么清
                    delTable("com.aspire.dotcard.baseread.dao.RankDao.delete");
                    isDelRank = true;
                }
                
                RReadBO.getInstance().dealBaseBookTotalRank(encoding,
                                                            sep,
                                                            rs,
                                                            lfs,
                                                            errorRowNumber,
                                                            error,
                                                            "month");
                msgInfo.append("共处理行数：" + rs[0]);
                msgInfo.append(";<br>成功新增：" + rs[1]);
                msgInfo.append(";<br>成功修改：" + rs[2]);
                msgInfo.append(";<br>数据检查不合法：" + rs[4]);
                if (rs[4] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>失败校验所在行具体为：");
                    msgInfo.append(errorRowNumber);
                }
                msgInfo.append(";<br>数据处理失败：" + rs[5]);
                if (rs[5] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>处理失败所在行具体为：第");
                    msgInfo.append(error.substring(0, error.length() - 2));
                    msgInfo.append("行。");
                }
                msgInfo.append("<br>");
            }
        }
        catch (Exception e)
        {
            logger.error("导入图书月排行信息数据操作失败" + e);
            logger.error(e);
            msgInfo.append("导入图书月排行信息数据操作失败" + e);
            msgInfo.append("<br>");
        }
        logger.info("import base read monthrank info begin!");
    }
    
    /**
     * 图书周排行信息
     * 
     * @param ftp
     * @param msgInfo
     * @param encoding
     * @param sep
     * @param rs
     */
    public void synBookWeekRank(BaseReadFtpProcess ftp, StringBuffer msgInfo,
                                 String encoding, String sep, int[] rs)
    {
        logger.info("import base read weekrank info begin!");
        StringBuffer errorRowNumber = new StringBuffer();
        StringBuffer error = new StringBuffer();
        boolean hasVerf = true;
        boolean isDelTable = false;

        // 图书分类信息
        try
        {
            if (hasVerf)
            {
                msgInfo.append(synReadVerf("图书周排行信息",
                                           "BookWeekRankVerfRegex",
                                           ftp,
                                           encoding,
                                           sep));
            }
            msgInfo.append("导入图书周排行信息数据结果：");
            msgInfo.append("<br>");
            String[] fNameRegex = BaseReadConfig.get("BookWeekRankRegex")
                                                .split(",");

            // ftp 下载到本地
            String lfs[] = ftp.process(fNameRegex);

            if (lfs.length == 0)
            {
                msgInfo.append("没有找到图书周排行信息数据文件");
                msgInfo.append("<br>");
            }
            else
            {
                // 判断文件长度是否为0
                if (isNullFile(lfs))
                {
                    // 加入邮件错误信息..............
                    msgInfo.append("此次同步的数据文件都为空，此次类型数据同步中止！！！");
                    msgInfo.append("<br>");
                }
                // 文件长度至少有一个不为空，且还要清表全量同步
                else if (isDelTable && !isDelRank)
                {
                    // 清空处理。但现在没想好怎么清
                    delTable("com.aspire.dotcard.baseread.dao.RankDao.delete");
                    isDelRank = true;
                }
                
                RReadBO.getInstance().dealBaseBookTotalRank(encoding,
                                                            sep,
                                                            rs,
                                                            lfs,
                                                            errorRowNumber,
                                                            error,
                                                            "week");
                msgInfo.append("共处理行数：" + rs[0]);
                msgInfo.append(";<br>成功新增：" + rs[1]);
                msgInfo.append(";<br>成功修改：" + rs[2]);
                msgInfo.append(";<br>成功下线：" + rs[3]);
                msgInfo.append(";<br>数据检查不合法：" + rs[4]);
                if (rs[4] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>失败校验所在行具体为：");
                    msgInfo.append(errorRowNumber);
                }
                msgInfo.append(";<br>数据处理失败：" + rs[5]);
                if (rs[5] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>处理失败所在行具体为：第");
                    msgInfo.append(error.substring(0, error.length() - 2));
                    msgInfo.append("行。");
                }
                msgInfo.append("<br>");
            }
        }
        catch (Exception e)
        {
            logger.error("导入图书周排行信息数据操作失败" + e);
            logger.error(e);
            msgInfo.append("导入图书周排行信息数据操作失败" + e);
            msgInfo.append("<br>");
        }
        logger.info("import base read weekrank info begin!");
    }

    /**
     * 同步专区内容信息
     * 
     * @param ftp
     * @param msgInfo
     * @param encoding
     * @param sep
     * @param rs
     */
    public void synBookAreaReference(BaseReadFtpProcess ftp,
                                     StringBuffer msgInfo, String encoding,
                                     String sep, int[] rs)
    {
        logger.info("import base book area reference info begin!");
        StringBuffer errorRowNumber = new StringBuffer();
        StringBuffer error = new StringBuffer();
        boolean hasVerf = true;

        // 专区内容信息
        try
        {
            if (hasVerf)
            {
                msgInfo.append(synReadVerf("专区内容信息",
                                           "BookAreaReferenceVerfRegex",
                                           ftp,
                                           encoding,
                                           sep));
            }
            msgInfo.append("导入专区内容信息数据结果：");
            msgInfo.append("<br>");
            String[] fNameRegex = BaseReadConfig.get("BookAreaReferenceRegex")
                                                .split(",");
            String lfs[] = ftp.process(fNameRegex);// ftp 下载到本地

            if (lfs.length == 0)
            {
                msgInfo.append("没有找到专区内容信息数据文件");
                msgInfo.append("<br>");
            }
            else
            {
                RReadBO.getInstance().dealBaseBookReference(encoding,
                                                            sep,
                                                            rs,
                                                            lfs,
                                                            errorRowNumber,
                                                            error);
                msgInfo.append("共处理行数：" + rs[0]);
                msgInfo.append(";<br>成功新增：" + rs[1]);
                msgInfo.append(";<br>成功修改：" + rs[2]);
                msgInfo.append(";<br>成功下线：" + rs[3]);
                msgInfo.append(";<br>数据检查不合法：" + rs[4]);
                if (rs[4] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>失败校验所在行具体为：");
                    msgInfo.append(errorRowNumber);
                }
                msgInfo.append(";<br>数据处理失败：" + rs[5]);
                if (rs[5] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>处理失败所在行具体为：第");
                    msgInfo.append(error.substring(0, error.length() - 2));
                    msgInfo.append("行。");
                }
                msgInfo.append("<br>");
            }
        }
        catch (Exception e)
        {
            logger.error("导入专区内容信息数据操作失败" + e);
            logger.error(e);
            msgInfo.append("导入专区内容信息数据操作失败" + e);
            msgInfo.append("<br>");
        }
        logger.info("import base book area reference info end!");
    }

    /**
     * 同步专区信息
     * 
     * @param ftp
     * @param msgInfo
     * @param encoding
     * @param sep
     * @param rs
     */
    public void synBookArea(BaseReadFtpProcess ftp, StringBuffer msgInfo,
                            String encoding, String sep, int[] rs)
    {
        logger.info("import base book area info begin!");
        StringBuffer errorRowNumber = new StringBuffer();
        StringBuffer error = new StringBuffer();
        boolean hasVerf = true;

        // 专区信息
        try
        {
            if (hasVerf)
            {
                msgInfo.append(synReadVerf("专区信息",
                                           "BookAreaVerfRegex",
                                           ftp,
                                           encoding,
                                           sep));
            }

            msgInfo.append("导入专区信息数据结果：");
            msgInfo.append("<br>");
            String[] fNameRegex = BaseReadConfig.get("BookAreaRegex")
                                                .split(",");
            String lfs[] = ftp.process(fNameRegex);// ftp 下载到本地

            if (lfs.length == 0)
            {
                msgInfo.append("没有找到专区信息数据文件");
                msgInfo.append("<br>");
            }
            else
            {
                RReadBO.getInstance().dealBaseBookArea(encoding,
                                                       sep,
                                                       rs,
                                                       lfs,
                                                       errorRowNumber,
                                                       error);
                msgInfo.append("共处理行数：" + rs[0]);
                msgInfo.append(";<br>成功新增：" + rs[1]);
                msgInfo.append(";<br>成功修改：" + rs[2]);
                msgInfo.append(";<br>成功下线：" + rs[3]);
                msgInfo.append(";<br>数据检查不合法：" + rs[4]);
                if (rs[4] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>失败校验所在行具体为：");
                    msgInfo.append(errorRowNumber);
                }
                msgInfo.append(";<br>数据处理失败：" + rs[5]);
                if (rs[5] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>处理失败所在行具体为：第");
                    msgInfo.append(error.substring(0, error.length() - 2));
                    msgInfo.append("行。");
                }
                msgInfo.append("<br>");
            }
        }
        catch (Exception e)
        {
            logger.error("导入专区信息数据操作失败" + e);
            logger.error(e);
            msgInfo.append("导入专区信息数据操作失败" + e);
            msgInfo.append("<br>");
        }
        logger.info("import base book aren info end!");
    }
    

    /**
     * 猜你喜欢——历史阅读推荐接口
     * 
     * @param ftp
     * @param msgInfo
     * @param encoding
     * @param sep
     * @param rs
     */
    public void synLikeHisRead(BaseReadFtpProcess ftp, StringBuffer msgInfo,
                                 String encoding, String sep, int[] rs)
    {
        logger.info("import base book LikeHisRead info begin!");
        StringBuffer errorRowNumber = new StringBuffer();
        StringBuffer error = new StringBuffer();
        boolean hasVerf = true;

        // 历史阅读推荐接口
        try
        {
            if (hasVerf)
            {
                msgInfo.append(synReadVerf("猜你喜欢——历史阅读推荐",
                                           "BookLikeHisReadVerfRegex",
                                           ftp,
                                           encoding,
                                           sep));
            }

            msgInfo.append("导入基地猜你喜欢——历史阅读推荐信息数据结果：");
            msgInfo.append("<br>");
            String[] fNameRegex = BaseReadConfig.get("BookLikeHisReadRegex")
                                                .split(",");
            String lfs[] = ftp.process(fNameRegex);// ftp 下载到本地

            if (lfs.length == 0)
            {
                msgInfo.append("没有找到基地猜你喜欢——历史阅读推荐信息数据文件");
                msgInfo.append("<br>");
            }
            else
            {
                RReadBO.getInstance().dealBaseLikeHisRead(encoding,
                                                            sep,
                                                            rs,
                                                            lfs,
                                                            errorRowNumber,
                                                            error);
                msgInfo.append("共处理行数：" + rs[0]);
                msgInfo.append(";<br>成功新增：" + rs[1]);
                msgInfo.append(";<br>成功修改：" + rs[2]);
                msgInfo.append(";<br>成功下线：" + rs[3]);
                msgInfo.append(";<br>数据检查不合法：" + rs[4]);
                if (rs[4] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>失败校验所在行具体为：");
                    msgInfo.append(errorRowNumber);
                }
                msgInfo.append(";<br>数据处理失败：" + rs[5]);
                if (rs[5] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>处理失败所在行具体为：第");
                    msgInfo.append(error.substring(0, error.length() - 2));
                    msgInfo.append("行。");
                }
                msgInfo.append("<br>");
            }
        }
        catch (Exception e)
        {
            logger.error("导入基地猜你喜欢——历史阅读推荐信息数据操作失败" + e);
            logger.error(e);
            msgInfo.append("导入基地猜你喜欢——历史阅读推荐信息数据操作失败" + e);
            msgInfo.append("<br>");
        }
        logger.info("import base book LikeHisRead info end!");
    }

    /**
     * 猜你喜欢——名家推荐接口
     * 
     * @param ftp
     * @param msgInfo
     * @param encoding
     * @param sep
     * @param rs
     */
    public void synLikeAuthor(BaseReadFtpProcess ftp, StringBuffer msgInfo,
                                 String encoding, String sep, int[] rs)
    {
        logger.info("import base book LikeAuthor info begin!");
        StringBuffer errorRowNumber = new StringBuffer();
        StringBuffer error = new StringBuffer();
        boolean hasVerf = true;

        // 历史阅读推荐接口
        try
        {
            if (hasVerf)
            {
                msgInfo.append(synReadVerf("猜你喜欢——名家推荐",
                                           "BookLikeAuthorVerfRegex",
                                           ftp,
                                           encoding,
                                           sep));
            }

            msgInfo.append("导入基地猜你喜欢——名家推荐信息数据结果：");
            msgInfo.append("<br>");
            String[] fNameRegex = BaseReadConfig.get("BookLikeAuthorRegex")
                                                .split(",");
            String lfs[] = ftp.process(fNameRegex);// ftp 下载到本地

            if (lfs.length == 0)
            {
                msgInfo.append("没有找到基地猜你喜欢——名家推荐信息数据文件");
                msgInfo.append("<br>");
            }
            else
            {
                RReadBO.getInstance().dealBaseLikeAuthor(encoding,
                                                            sep,
                                                            rs,
                                                            lfs,
                                                            errorRowNumber,
                                                            error);
                msgInfo.append("共处理行数：" + rs[0]);
                msgInfo.append(";<br>成功新增：" + rs[1]);
                msgInfo.append(";<br>成功修改：" + rs[2]);
                msgInfo.append(";<br>成功下线：" + rs[3]);
                msgInfo.append(";<br>数据检查不合法：" + rs[4]);
                if (rs[4] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>失败校验所在行具体为：");
                    msgInfo.append(errorRowNumber);
                }
                msgInfo.append(";<br>数据处理失败：" + rs[5]);
                if (rs[5] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>处理失败所在行具体为：第");
                    msgInfo.append(error.substring(0, error.length() - 2));
                    msgInfo.append("行。");
                }
                msgInfo.append("<br>");
            }
        }
        catch (Exception e)
        {
            logger.error("导入基地猜你喜欢——名家推荐信息数据操作失败" + e);
            logger.error(e);
            msgInfo.append("导入基地猜你喜欢——名家推荐信息数据操作失败" + e);
            msgInfo.append("<br>");
        }
        logger.info("import base book LikeAuthor info end!");
    }

    /**
     * 猜你喜欢——图书级阅读关联推荐接口
     * 
     * @param ftp
     * @param msgInfo
     * @param encoding
     * @param sep
     * @param rs
     */
    public void synLikeReadPercentage(BaseReadFtpProcess ftp, StringBuffer msgInfo,
                                 String encoding, String sep, int[] rs)
    {
        logger.info("import base book LikeReadPercentage info begin!");
        StringBuffer errorRowNumber = new StringBuffer();
        StringBuffer error = new StringBuffer();
        boolean hasVerf = true;

        // 历史阅读推荐接口
        try
        {
            if (hasVerf)
            {
                msgInfo.append(synReadVerf("猜你喜欢——图书级阅读关联推荐",
                                           "BookLikeReadPercentageVerfRegex",
                                           ftp,
                                           encoding,
                                           sep));
            }

            msgInfo.append("导入基地猜你喜欢——图书级阅读关联推荐信息数据结果：");
            msgInfo.append("<br>");
            String[] fNameRegex = BaseReadConfig.get("BookLikeReadPercentageRegex")
                                                .split(",");
            String lfs[] = ftp.process(fNameRegex);// ftp 下载到本地

            if (lfs.length == 0)
            {
                msgInfo.append("没有找到基地猜你喜欢——图书级阅读关联推荐信息数据文件");
                msgInfo.append("<br>");
            }
            else
            {
                RReadBO.getInstance().dealBaseLikeReadPercentage(encoding,
                                                            sep,
                                                            rs,
                                                            lfs,
                                                            errorRowNumber,
                                                            error);
                msgInfo.append("共处理行数：" + rs[0]);
                msgInfo.append(";<br>成功新增：" + rs[1]);
                msgInfo.append(";<br>成功修改：" + rs[2]);
                msgInfo.append(";<br>成功下线：" + rs[3]);
                msgInfo.append(";<br>数据检查不合法：" + rs[4]);
                if (rs[4] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>失败校验所在行具体为：");
                    msgInfo.append(errorRowNumber);
                }
                msgInfo.append(";<br>数据处理失败：" + rs[5]);
                if (rs[5] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>处理失败所在行具体为：第");
                    msgInfo.append(error.substring(0, error.length() - 2));
                    msgInfo.append("行。");
                }
                msgInfo.append("<br>");
            }
        }
        catch (Exception e)
        {
            logger.error("导入基地猜你喜欢——图书级阅读关联推荐信息数据操作失败" + e);
            logger.error(e);
            msgInfo.append("导入基地猜你喜欢——图书级阅读关联推荐信息数据操作失败" + e);
            msgInfo.append("<br>");
        }
        logger.info("import base book LikeReadPercentage info end!");
    }
    
    /**
     * 猜你喜欢——图书级订购关联推荐接口
     * 
     * @param ftp
     * @param msgInfo
     * @param encoding
     * @param sep
     * @param rs
     */
    public void synLikeOrderPercentage(BaseReadFtpProcess ftp, StringBuffer msgInfo,
                                 String encoding, String sep, int[] rs)
    {
        logger.info("import base book LikeOrderPercentage info begin!");
        StringBuffer errorRowNumber = new StringBuffer();
        StringBuffer error = new StringBuffer();
        boolean hasVerf = true;

        // 历史阅读推荐接口
        try
        {
            if (hasVerf)
            {
                msgInfo.append(synReadVerf("猜你喜欢——图书级订购关联推荐",
                                           "BookLikeOrderPercentageVerfRegex",
                                           ftp,
                                           encoding,
                                           sep));
            }

            msgInfo.append("导入基地猜你喜欢——图书级订购关联推荐信息数据结果：");
            msgInfo.append("<br>");
            String[] fNameRegex = BaseReadConfig.get("BookLikeOrderPercentageRegex")
                                                .split(",");
            String lfs[] = ftp.process(fNameRegex);// ftp 下载到本地

            if (lfs.length == 0)
            {
                msgInfo.append("没有找到基地猜你喜欢——图书级订购关联推荐信息数据文件");
                msgInfo.append("<br>");
            }
            else
            {
                RReadBO.getInstance().dealBaseLikeOrderPercentage(encoding,
                                                            sep,
                                                            rs,
                                                            lfs,
                                                            errorRowNumber,
                                                            error);
                msgInfo.append("共处理行数：" + rs[0]);
                msgInfo.append(";<br>成功新增：" + rs[1]);
                msgInfo.append(";<br>成功修改：" + rs[2]);
                msgInfo.append(";<br>成功下线：" + rs[3]);
                msgInfo.append(";<br>数据检查不合法：" + rs[4]);
                if (rs[4] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>失败校验所在行具体为：");
                    msgInfo.append(errorRowNumber);
                }
                msgInfo.append(";<br>数据处理失败：" + rs[5]);
                if (rs[5] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>处理失败所在行具体为：第");
                    msgInfo.append(error.substring(0, error.length() - 2));
                    msgInfo.append("行。");
                }
                msgInfo.append("<br>");
            }
        }
        catch (Exception e)
        {
            logger.error("导入基地猜你喜欢——图书级订购关联推荐信息数据操作失败" + e);
            logger.error(e);
            msgInfo.append("导入基地猜你喜欢——图书级订购关联推荐信息数据操作失败" + e);
            msgInfo.append("<br>");
        }
        logger.info("import base book LikeOrderPercentage info end!");
    }
    
    /**
     * 同步图书推荐信息
     * 
     * @param ftp
     * @param msgInfo
     * @param encoding
     * @param sep
     * @param rs
     */
    public void synBookRecommend(BaseReadFtpProcess ftp, StringBuffer msgInfo,
                                 String encoding, String sep, int[] rs)
    {
        logger.info("import base book recommend info begin!");
        StringBuffer errorRowNumber = new StringBuffer();
        StringBuffer error = new StringBuffer();
        boolean hasVerf = true;

        // 图书推荐
        try
        {
            if (hasVerf)
            {
                msgInfo.append(synReadVerf("图书推荐",
                                           "BookRecommendVerfRegex",
                                           ftp,
                                           encoding,
                                           sep));
            }

            msgInfo.append("导入基地推荐图书信息数据结果：");
            msgInfo.append("<br>");
            String[] fNameRegex = BaseReadConfig.get("BookRecommendRegex")
                                                .split(",");
            String lfs[] = ftp.process(fNameRegex);// ftp 下载到本地

            if (lfs.length == 0)
            {
                msgInfo.append("没有找到基地推荐图书信息数据文件");
                msgInfo.append("<br>");
            }
            else
            {
                RReadBO.getInstance().dealBaseBookRecommend(encoding,
                                                            sep,
                                                            rs,
                                                            lfs,
                                                            errorRowNumber,
                                                            error);
                msgInfo.append("共处理行数：" + rs[0]);
                msgInfo.append(";<br>成功新增：" + rs[1]);
                msgInfo.append(";<br>成功修改：" + rs[2]);
                msgInfo.append(";<br>成功下线：" + rs[3]);
                msgInfo.append(";<br>数据检查不合法：" + rs[4]);
                if (rs[4] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>失败校验所在行具体为：");
                    msgInfo.append(errorRowNumber);
                }
                msgInfo.append(";<br>数据处理失败：" + rs[5]);
                if (rs[5] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>处理失败所在行具体为：第");
                    msgInfo.append(error.substring(0, error.length() - 2));
                    msgInfo.append("行。");
                }
                msgInfo.append("<br>");
            }
        }
        catch (Exception e)
        {
            logger.error("导入基地推荐图书信息数据操作失败" + e);
            logger.error(e);
            msgInfo.append("导入基地推荐图书信息数据操作失败" + e);
            msgInfo.append("<br>");
        }
        logger.info("import base book recommend info end!");
    }

    /**
     * 同步图书包月信息
     * 
     * @param ftp
     * @param msgInfo
     * @param encoding
     * @param sep
     * @param rs
     */
    public void synBookMonth(BaseReadFtpProcess ftp, StringBuffer msgInfo,
                             String encoding, String sep, int[] rs)
    {
        logger.info("import base book month info begin!");
        StringBuffer errorRowNumber = new StringBuffer();
        StringBuffer error = new StringBuffer();
        boolean hasVerf = true;

        // 图书包月信息
        try
        {
            if (hasVerf)
            {
                msgInfo.append(synReadVerf("图书包月",
                                           "BookMonthVerfRegex",
                                           ftp,
                                           encoding,
                                           sep));
            }

            msgInfo.append("导入基地图书包月数据结果：");
            msgInfo.append("<br>");
            String[] fNameRegex = BaseReadConfig.get("BookMonthRegex")
                                                .split(",");
            String lfs[] = ftp.process(fNameRegex);// ftp 下载到本地

            if (lfs.length == 0)
            {
                msgInfo.append("没有找到基地图书包月数据文件");
                msgInfo.append("<br>");
            }
            else
            {
                RReadBO.getInstance().dealBaseBookMonth(encoding,
                                                        sep,
                                                        rs,
                                                        lfs,
                                                        errorRowNumber,
                                                        error);
                msgInfo.append("共处理行数：" + rs[0]);
                msgInfo.append(";<br>成功新增：" + rs[1]);
                msgInfo.append(";<br>成功修改：" + rs[2]);
                msgInfo.append(";<br>成功下线：" + rs[3]);
                msgInfo.append(";<br>数据检查不合法：" + rs[4]);
                if (rs[4] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>失败校验所在行具体为：");
                    msgInfo.append(errorRowNumber);
                }
                msgInfo.append(";<br>数据处理失败：" + rs[5]);
                if (rs[5] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>处理失败所在行具体为：第");
                    msgInfo.append(error.substring(0, error.length() - 2));
                    msgInfo.append("行。");
                }
                msgInfo.append("<br>");
            }
        }
        catch (Exception e)
        {
            logger.error("导入基地图书包月数据操作失败" + e);
            logger.error(e);
            msgInfo.append("导入基地图书包月数据操作失败" + e);
            msgInfo.append("<br>");
        }
        logger.info("import base book month info end!");
    }

    /**
     * 同步图书包月地域信息
     * 
     * @param ftp
     * @param msgInfo
     * @param encoding
     * @param sep
     * @param rs
     */
    public void synBookMonthCity(BaseReadFtpProcess ftp, StringBuffer msgInfo,
                             String encoding, String sep, int[] rs)
    {
        logger.info("import base book month city info begin!");
        StringBuffer errorRowNumber = new StringBuffer();
        StringBuffer error = new StringBuffer();
        boolean hasVerf = true;
        int count = 0;
        
        try
        {
        	count = Integer.parseInt(BaseReadConfig.get("BookMonthCityCount"));
        }
        catch(Exception e)
        {
        	count = 500;
        }
        

        // 图书包月信息
        try
        {
            if (hasVerf)
            {
                msgInfo.append(synReadVerf("图书包月地域",
                                           "BookMonthCityVerfRegex",
                                           ftp,
                                           encoding,
                                           sep));
            }

            msgInfo.append("导入基地图书包月地域数据结果：");
            msgInfo.append("<br>");
            String[] fNameRegex = BaseReadConfig.get("BookMonthCityRegex")
                                                .split(",");
            String lfs[] = ftp.process(fNameRegex);// ftp 下载到本地

            if (lfs.length == 0)
            {
                msgInfo.append("没有找到基地图书包月地域数据文件");
                msgInfo.append("<br>");
            }
            else
            {
            	// 先变更数据库中现存数据的数据状态为1：被更新中
            	if(!RReadBO.getInstance().updateBookBagArea("start"))
            	{
            		throw new Exception("同步阅读书包地域信息第一步时，先变更现有数据状态时发生错误，后续动作取消!");
            	}
            	
            	
                RReadBO.getInstance().dealBaseBookMonthCity(encoding,
                                                        sep,
                                                        rs, 
                                                        lfs,
                                                        errorRowNumber, error);
				
				// 如果当前新增数据过少，少于指定阀值。则回滚，删除新增数据，变更原数据状态为0：临时正常数据
				if (RReadBO.getInstance().queryCountBookBagArea() < count)
				{
					if (!RReadBO.getInstance().delBookBagAreaByStart("0"))
					{
						throw new Exception(
								"同步阅读书包地域信息完成后，确定新增数据无效后，删除新增数据时发生错误，请手动删除此失效数据!");
					}
					if (!RReadBO.getInstance().updateBookBagArea("end"))
					{
						throw new Exception(
								"同步阅读书包地域信息完成后，确定新增数据无效后，删除新增数据后，变原有数据状态被更新中为正常时发生错误，请手动变更此失效数据!");
					}
				}
				// 如果当前数据正常，则删除状态为1：被更新中的原数据
				else
				{
					if (!RReadBO.getInstance().delBookBagAreaByStart("1"))
					{
						throw new Exception(
								"同步阅读书包地域信息完成后，确定新增数据有效后，删除原有状态为被更新中的数据时发生错误，请手动删除此失效数据!");
					}
				}
                
                msgInfo.append("共处理行数：" + rs[0]);
                msgInfo.append(";<br>成功新增：" + rs[1]);
                msgInfo.append(";<br>数据检查不合法：" + rs[2]);
                if (rs[2] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>失败校验所在行具体为：");
                    msgInfo.append(errorRowNumber);
                }
                msgInfo.append(";<br>数据处理失败：" + rs[3]);
                if (rs[3] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>处理失败所在行具体为：第");
                    msgInfo.append(error.substring(0, error.length() - 2));
                    msgInfo.append("行。");
                }
                msgInfo.append("<br>");
            }
        }
        catch (Exception e)
        {
            logger.error("导入基地图书包月地域数据操作失败:" + e.getMessage());
            logger.error(e);
            msgInfo.append("导入基地图书包月地域数据操作失败:" + e.getMessage());
            msgInfo.append("<br>");
        }
        logger.info("import base book month city info end!");
    }
    
    
    /**
     * 同步图书包月信息
     * 
     * @param ftp
     * @param msgInfo
     * @param encoding
     * @param sep
     * @param rs
     
    public void synBookBagContent(BaseReadFtpProcess ftp, StringBuffer msgInfo,
                                  String encoding, String sep, int[] rs)
    {
        logger.info("import base book bag content info begin!");
        StringBuffer errorRowNumber = new StringBuffer();
        StringBuffer error = new StringBuffer();
        boolean hasVerf = true;

        // 书包内容信息
        try
        {
            if (hasVerf)
            {
                msgInfo.append(synReadVerf("书包内容",
                                           "BookBagContentVerfRegex",
                                           ftp,
                                           encoding,
                                           sep));
            }

            msgInfo.append("导入基地书包内容数据结果：");
            msgInfo.append("<br>");
            String[] fNameRegex = BaseReadConfig.get("BookBagContentRegex")
                                                .split(",");
            String lfs[] = ftp.process(fNameRegex);// ftp 下载到本地

            if (lfs.length == 0)
            {
                msgInfo.append("没有找到基地书包内容数据文件");
                msgInfo.append("<br>");
            }
            else
            {
                RReadBO.getInstance().dealBaseBookBagContent(encoding,
                                                             sep,
                                                             rs,
                                                             lfs,
                                                             errorRowNumber,
                                                             error);
                msgInfo.append("共处理行数：" + rs[0]);
                msgInfo.append(";<br>成功新增：" + rs[1]);
                msgInfo.append(";<br>成功修改：" + rs[2]);
                msgInfo.append(";<br>成功下线：" + rs[3]);
                msgInfo.append(";<br>数据检查不合法：" + rs[4]);
                if (rs[4] > 0)
                {
                    msgInfo.append(";<br>失败校验所在行具体为：");
                    msgInfo.append(errorRowNumber);
                }
                msgInfo.append(";<br>数据处理失败：" + rs[5]);
                if (rs[5] > 0)
                {
                    msgInfo.append(";<br>处理失败所在行具体为：第");
                    msgInfo.append(error.substring(0, error.length() - 2));
                    msgInfo.append("行。");
                }
                msgInfo.append("<br>");
            }
        }
        catch (Exception e)
        {
            logger.error("导入基地书包内容数据操作失败" + e);
            logger.error(e);
            msgInfo.append("导入基地书包内容数据操作失败" + e);
            msgInfo.append("<br>");
        }
        logger.info("import base book bag content info end!");
    }
	*/
    
    
    /**
     * 同步终端目录信息
     * 
     * @param ftp
     * @param msgInfo
     * @param encoding
     * @param sep
     * @param rs
     */
    public void synMoDirectory(BaseReadFtpProcess ftp, StringBuffer msgInfo,
                                  String encoding, String sep, int[] rs)
    {
        logger.info("import base catalogid info begin!");
        StringBuffer errorRowNumber = new StringBuffer();
        StringBuffer error = new StringBuffer();
        boolean hasVerf = true;
        boolean isDelTable = true;

        // 书包内容信息
        try
        {
            if (hasVerf)
            {
                msgInfo.append(synReadVerf("终端目录",
                                           "MoDirectoryVerfRegex",
                                           ftp,
                                           encoding,
                                           sep));
            }

            msgInfo.append("导入基地终端目录数据结果：");
            msgInfo.append("<br>");
            String[] fNameRegex = BaseReadConfig.get("MoDirectoryRegex")
                                                .split(",");
            String lfs[] = ftp.process(fNameRegex);// ftp 下载到本地

            if (lfs.length == 0)
            {
                msgInfo.append("没有找到基地终端目录数据文件");
                msgInfo.append("<br>");
            }
            else
            {
                // 判断文件长度是否为0
                if (isNullFile(lfs))
                {
                    // 加入邮件错误信息..............
                    msgInfo.append("此次同步的数据文件都为空，此次类型数据同步中止！！！");
                    msgInfo.append("<br>");
                }
                // 文件长度至少有一个不为空，且还要清表全量同步
                else if (isDelTable)
                {
                    // 清空处理。但现在没想好怎么清
                    delTable("com.aspire.dotcard.baseread.dao.BookBagContentDao.deleteMoDirectory");
                }
                
                RReadBO.getInstance().dealBaseMoDirectory(encoding,
                                                             sep,
                                                             rs,
                                                             lfs,
                                                             errorRowNumber,
                                                             error);
                msgInfo.append("共处理行数：" + rs[0]);
                msgInfo.append(";<br>成功新增：" + rs[1]);
                msgInfo.append(";<br>成功修改：" + rs[2]);
                msgInfo.append(";<br>成功下线：" + rs[3]);
                msgInfo.append(";<br>数据检查不合法：" + rs[4]);
                if (rs[4] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>失败校验所在行具体为：");
                    msgInfo.append(errorRowNumber);
                }
                msgInfo.append(";<br>数据处理失败：" + rs[5]);
                if (rs[5] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>处理失败所在行具体为：第");
                    msgInfo.append(error.substring(0, error.length() - 2));
                    msgInfo.append("行。");
                }
                msgInfo.append("<br>");
            }
        }
        catch (Exception e)
        {
            logger.error("导入基地终端目录数据操作失败" + e);
            logger.error(e);
            msgInfo.append("导入基地终端目录数据操作失败" + e);
            msgInfo.append("<br>");
        }
        logger.info("import base catalogid info end!");
    }
    
    /**
     * 同步图书信息
     * 
     * @param ftp
     * @param msgInfo
     * @param encoding
     * @param sep
     * @param rs
     */
    public void synBookInfo(BaseReadFtpProcess ftp, StringBuffer msgInfo,
                            String encoding, String sep, int[] rs)
    {
        logger.info("import base book info begin!");
        StringBuffer errorRowNumber = new StringBuffer();
        StringBuffer error = new StringBuffer();
        boolean hasVerf = true;

        // 图书信息
        try
        {
            if (hasVerf)
            {
                msgInfo.append(synReadVerf("图书信息",
                                           "BookVerfRegex",
                                           ftp,
                                           encoding,
                                           sep));
            }

            msgInfo.append("导入基地图书信息元数据结果：");
            msgInfo.append("<br>");
            String[] fNameRegex = BaseReadConfig.get("BookRegex").split(",");
            String lfs[] = ftp.process(fNameRegex);// ftp 下载到本地

            if (lfs.length == 0)
            {
                msgInfo.append("没有找到基地图书数据文件");
                msgInfo.append("<br>");
            }
            else
            {
                RReadBO.getInstance().dealBaseBook(encoding,
                                                   sep,
                                                   rs,
                                                   lfs,
                                                   errorRowNumber,
                                                   error);
                msgInfo.append("共处理行数：" + rs[0]);
                msgInfo.append(";<br>成功新增：" + rs[1]);
                msgInfo.append(";<br>成功修改：" + rs[2]);
                msgInfo.append(";<br>成功下线：" + rs[3]);
                msgInfo.append(";<br>数据检查不合法：" + rs[4]);
                if (rs[4] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>失败校验所在行具体为：");
                    msgInfo.append(errorRowNumber);
                }
                msgInfo.append(";<br>数据处理失败：" + rs[5]);
                if (rs[5] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>处理失败所在行具体为：第");
                    msgInfo.append(error.substring(0, error.length() - 2));
                    msgInfo.append("行。");
                }
                msgInfo.append("<br>");
            }
        }
        catch (Exception e)
        {
            logger.error("导入基地图书信息数据操作失败" + e);
            logger.error(e);
            msgInfo.append("导入基地图书信息数据操作失败" + e);
            msgInfo.append("<br>");
        }
        logger.info("import base book info end!");
    }

    /**
     * 同步作者信息
     * 
     * @param ftp
     * @param msgInfo
     * @param encoding
     * @param sep
     * @param rs
     */
    public void synBookAuthor(BaseReadFtpProcess ftp, StringBuffer msgInfo,
                              String encoding, String sep, int[] rs)
    {
        logger.info("import base book author info begin!");
        StringBuffer errorRowNumber = new StringBuffer();
        StringBuffer error = new StringBuffer();
        boolean hasVerf = true;

        // 图书分类信息
        try
        {
            if (hasVerf)
            {
                msgInfo.append(synReadVerf("作者信息",
                                           "BookAuthorVerfRegex",
                                           ftp,
                                           encoding,
                                           sep));
            }

            msgInfo.append("导入基地图书作者元数据结果：");
            msgInfo.append("<br>");
            String[] fNameRegex = BaseReadConfig.get("BookAuthorRegex")
                                                .split(",");
            String lfs[] = ftp.process(fNameRegex);// ftp 下载到本地

            if (lfs.length == 0)
            {
                msgInfo.append("没有找到基地图书作者数据文件");
                msgInfo.append("<br>");
            }
            else
            {

                RReadBO.getInstance().dealBaseBookAuthor(encoding,
                                                         sep,
                                                         rs,
                                                         lfs,
                                                         errorRowNumber,
                                                         error);
                msgInfo.append("共处理行数：" + rs[0]);
                msgInfo.append(";<br>成功新增：" + rs[1]);
                msgInfo.append(";<br>成功修改：" + rs[2]);
                msgInfo.append(";<br>成功下线：" + rs[3]);
                msgInfo.append(";<br>数据检查不合法：" + rs[4]);
                if (rs[4] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>失败校验所在行具体为：");
                    msgInfo.append(errorRowNumber);
                }
                msgInfo.append(";<br>数据处理失败：" + rs[5]);
                if (rs[5] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>处理失败所在行具体为：第");
                    msgInfo.append(error.substring(0, error.length() - 2));
                    msgInfo.append("行。");
                }
                msgInfo.append("<br>");
            }
        }
        catch (Exception e)
        {
            logger.error("导入基地图书作者数据操作失败" + e);
            logger.error(e);
            msgInfo.append("导入基地图书作者数据操作失败" + e);
            msgInfo.append("<br>");
        }
        logger.info("import base book author info end!");
    }

    /**
     * 同步图书分类信息
     * 
     * @param ftp
     * @param msgInfo
     * @param encoding
     * @param sep
     * @param rs
     */
    public void synBookType(BaseReadFtpProcess ftp, StringBuffer msgInfo,
                            String encoding, String sep, int[] rs)
    {
        logger.info("import base book type begin!");
        StringBuffer errorRowNumber = new StringBuffer();
        StringBuffer error = new StringBuffer();
        boolean hasVerf = true;
        boolean isDelTable = true;

        // 图书分类信息
        try
        {
            if (hasVerf)
            {
                msgInfo.append(synReadVerf("图书分类",
                                           "BookTypeVerfRegex",
                                           ftp,
                                           encoding,
                                           sep));
            }

            msgInfo.append("导入基地图书分类元数据结果：");
            msgInfo.append("<br>");
            String[] fNameRegex = BaseReadConfig.get("BookTypeRegex")
                                                .split(",");
            String lfs[] = ftp.process(fNameRegex);// ftp 下载到本地

            if (lfs.length == 0)
            {
                msgInfo.append("没有找到基地图书分类数据文件");
                msgInfo.append("<br>");
            }
            else
            {
                // 判断文件长度是否为0
                if (isNullFile(lfs))
                {
                    // 加入邮件错误信息..............
                    msgInfo.append("此次同步的数据文件都为空，此次类型数据同步中止！！！");
                    msgInfo.append("<br>");
                }
                // 文件长度至少有一个不为空，且还要清表全量同步
                else if (isDelTable)
                {
                    delTable("com.aspire.dotcard.baseread.dao.TypeDao.delete");
                }

                RReadBO.getInstance().dealBaseBookType(encoding,
                                                       sep,
                                                       rs,
                                                       lfs,
                                                       errorRowNumber,
                                                       error);
                msgInfo.append("共处理行数：" + rs[0]);
                msgInfo.append(";<br>成功新增：" + rs[1]);
                msgInfo.append(";<br>成功修改：" + rs[2]);
                msgInfo.append(";<br>成功下线：" + rs[3]);
                msgInfo.append(";<br>数据检查不合法：" + rs[4]);
                if (rs[4] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>失败校验所在行具体为：");
                    msgInfo.append(errorRowNumber);
                }
                msgInfo.append(";<br>数据处理失败：" + rs[5]);
                if (rs[5] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>处理失败所在行具体为：第");
                    msgInfo.append(error.substring(0, error.length() - 2));
                    msgInfo.append("行。");
                }
                msgInfo.append("<br>");
            }
        }
        catch (Exception e)
        {
            logger.error("导入基地图书分类数据操作失败" + e);
            logger.error(e);
            msgInfo.append("导入基地图书分类数据操作失败" + e);
            msgInfo.append("<br>");
        }

        logger.info("import base book type end!");
    }

    /**
     * 用于解析基地阅读的校验文件
     * 
     * @param BaseTypeName 基地数据类型名，用于显示在邮件中
     * @param fileNameRegex 基地数据类型校验文件配置名
     * @param ftp FTP
     * @param encoding 编码
     * @param sep 分隔符
     * @return 返回校验文件结果
     */
    public String synReadVerf(String BaseTypeName, String fileNameRegex,
                              BaseReadFtpProcess ftp, String encoding,
                              String sep)
    {
        String verfSep = BaseReadConfig.get("BBookVerfListSep");

        if (null == verfSep)
        {
        	verfSep = "|";
        }

        // 获取要处理的对象
        if (verfSep.startsWith("0x"))
        {
            // 0x开头的，表示是16进制的，需要转换
            String s = verfSep.substring(2, verfSep.length());
            int i1 = Integer.parseInt(s, 16);
            char c = ( char ) i1;
            verfSep = String.valueOf(c);
        }
        
        StringBuffer mailText = new StringBuffer();

        mailText.append("导入基地").append(BaseTypeName).append("校验数据结果：");
        String[] fNameVerfRegex = BaseReadConfig.get(fileNameRegex).split(",");
        String lvfs[] = null;

        try
        {
            lvfs = ftp.process(fNameVerfRegex);
        }
        catch (BOException e)
        {
            logger.error(e);
            mailText.append("查找基地").append(BaseTypeName).append("校验数据文件时发生错误");
        }

        if (lvfs.length == 0)
        {
            mailText.append("没有找到基地").append(BaseTypeName).append("校验数据文件");
            mailText.append("<br>");
        }
        else
        {
			mailText.append(RReadBO.getInstance().dealBaseReadVerf(encoding,
					verfSep, lvfs));
		}
        mailText.append("<br>");

        return mailText.toString();
    }

    /**
	 * 校验文件是否全为空以决定是否删表
	 * 
	 * @param fileList
	 * @return
	 */
    public boolean isNullFile(String[] files)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("校验文件是否全为空以决定是否删表：开始");
        }

        boolean isNullFile = true;

        for (int i = 0; i < files.length; i++)
        {
            String tempFileName = String.valueOf(files[i]);

            File file = new File(tempFileName);

            // 如果文件为空
            if (file.length() > 0)
            {
                isNullFile = false;
            }
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("校验文件是否全为空以决定是否删表：" + isNullFile);
        }

        return isNullFile;
    }

    /**
     * 用来清空原表信息，以备全量同步
     * 
     * @return
     */
    public String delTable(String delSqlCode)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("清空原表信息，以备全量同步.开始");
        }

        // 得到清空原表信息的sql语句
        try
        {
            DB.getInstance().executeBySQLCode(delSqlCode, null);
        }
        catch (DAOException e)
        {
            logger.error("执行清空原表信息，以备全量同步时失败 sql=" + delSqlCode + " 出错信息为：" + e);
            return "执行清空原表信息时发生数据库异常";
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("清空原表信息，以备全量同步.结束");
        }

        return BaseVideoConfig.CHECK_DATA_SUCCESS;
    }

    /**
     * 同步图书统计信息
     * 
     * @param ftp
     * @param msgInfo
     * @param encoding
     * @param sep
     * @param rs
     */
    public void synBookCount(BaseReadFtpProcess ftp, StringBuffer msgInfo,
                             String encoding, String sep, int[] rs)
    {
        logger.info("import base book count begin!");
        StringBuffer errorRowNumber = new StringBuffer();
        StringBuffer error = new StringBuffer();
        boolean hasVerf = true;
        boolean isDelTable = true;

        // 图书统计信息
        try
        {
            if (hasVerf)
            {
                msgInfo.append(synReadVerf("图书统计",
                                           "BookCountVerfRegex",
                                           ftp,
                                           encoding,
                                           sep));
            }

            msgInfo.append("导入基地图书统计元数据结果：");
            msgInfo.append("<br>");
            String[] fNameRegex = BaseReadConfig.get("BookCountRegex")
                                                .split(",");
            String lfs[] = ftp.process(fNameRegex);// ftp 下载到本地

            if (lfs.length == 0)
            {
                msgInfo.append("没有找到基地图书统计数据文件");
                msgInfo.append("<br>");
            }
            else
            {
                // 判断文件长度是否为0
                if (isNullFile(lfs))
                {
                    // 加入邮件错误信息..............
                    msgInfo.append("此次同步的数据文件都为空，此次类型数据同步中止！！！");
                    msgInfo.append("<br>");
                }
                // 文件长度至少有一个不为空，且还要清表全量同步
                else if (isDelTable)
                {
                    delTable("com.aspire.dotcard.baseread.dao.RCountDao.delete");
                }

                RReadBO.getInstance().dealBaseBookCount(encoding,
                                                        sep,
                                                        rs,
                                                        lfs,
                                                        errorRowNumber,
                                                        error);
                msgInfo.append("共处理行数：" + rs[0]);
                msgInfo.append(";<br>成功新增：" + rs[1]);
                msgInfo.append(";<br>成功修改：" + rs[2]);
                msgInfo.append(";<br>成功下线：" + rs[3]);
                msgInfo.append(";<br>数据检查不合法：" + rs[4]);
                if (rs[4] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>失败校验所在行具体为：");
                    msgInfo.append(errorRowNumber);
                }
                msgInfo.append(";<br>数据处理失败：" + rs[5]);
                if (rs[5] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>处理失败所在行具体为：第");
                    msgInfo.append(error.substring(0, error.length() - 2));
                    msgInfo.append("行。");
                }
                msgInfo.append("<br>");
            }
        }
        catch (Exception e)
        {
            logger.error("导入基地图书统计数据操作失败" + e);
            logger.error(e);
            msgInfo.append("导入基地图书统计数据操作失败" + e);
            msgInfo.append("<br>");
        }

        logger.info("import base book count end!");
    }

    /**
     * 同步图书更新信息
     * 
     * @param ftp
     * @param msgInfo
     * @param encoding
     * @param sep
     * @param rs
     */
    public void synBookUpdate(BaseReadFtpProcess ftp, StringBuffer msgInfo,
                              String encoding, String sep, int[] rs)
    {
        logger.info("import base book update begin!");
        StringBuffer errorRowNumber = new StringBuffer();
        StringBuffer error = new StringBuffer();
        boolean hasVerf = true;

        // 图书更新信息
        try
        {
            if (hasVerf)
            {
                msgInfo.append(synReadVerf("图书更新",
                                           "BookUpdateVerfRegex",
                                           ftp,
                                           encoding,
                                           sep));
            }

            msgInfo.append("导入基地图书更新元数据结果：");
            msgInfo.append("<br>");
            String[] fNameRegex = BaseReadConfig.get("BookUpdateRegex")
                                                .split(",");
            String lfs[] = ftp.process(fNameRegex);// ftp 下载到本地

            if (lfs.length == 0)
            {
                msgInfo.append("没有找到基地图书更新数据文件");
                msgInfo.append("<br>");
            }
            else
            {

                RReadBO.getInstance().dealBaseBookUpdate(encoding,
                                                         sep,
                                                         rs,
                                                         lfs,
                                                         errorRowNumber,
                                                         error);
                msgInfo.append("共处理行数：" + rs[0]);
                msgInfo.append(";<br>成功新增：" + rs[1]);
                msgInfo.append(";<br>成功修改：" + rs[2]);
                msgInfo.append(";<br>成功下线：" + rs[3]);
                msgInfo.append(";<br>数据检查不合法：" + rs[4]);
                if (rs[4] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>失败校验所在行具体为：");
                    msgInfo.append(errorRowNumber);
                }
                msgInfo.append(";<br>数据处理失败：" + rs[5]);
                if (rs[5] > 0 && isPutAll)
                {
                    msgInfo.append(";<br>处理失败所在行具体为：第");
                    msgInfo.append(error.substring(0, error.length() - 2));
                    msgInfo.append("行。");
                }
                msgInfo.append("<br>");
            }
        }
        catch (Exception e)
        {
            logger.error("导入基地图书更新数据操作失败" + e);
            logger.error(e);
            msgInfo.append("导入基地图书更新数据操作失败" + e);
            msgInfo.append("<br>");
        }

        logger.info("import base book update end!");
    }

	public void setDelRank(boolean isDelRank)
	{
		this.isDelRank = isDelRank;
	}

}
