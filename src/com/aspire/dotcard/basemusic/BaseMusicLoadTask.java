
package com.aspire.dotcard.basemusic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.TimerTask;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basecomic.common.FileUtil;
import com.aspire.dotcard.basemusic.config.BaseMusicConfig;
import com.aspire.dotcard.basemusic.dao.ToneBoxDAO;
import com.aspire.dotcard.basemusic.dao.ToneBoxSongDAO;
import com.aspire.ponaadmin.web.mail.Mail;

public class BaseMusicLoadTask extends TimerTask
{

    /**
     * 日志引用
     */
    protected static JLogger logger = LoggerFactory.getLogger(BaseMusicLoadTask.class);

    /**
     * 覆盖run运行方法
     */
    public void run()
    {
        // 初始化统计信息。
        // this.successAdd = 0;
        // this.successDelete = 0;
        // this.successUpdate = 0;
        // this.failureCheck = 0;
        // this.failureProcess = 0;

        String isCloseNotBaseMusicData = BaseMusicConfig.get("isCloseNotBaseMusicData");
        String synBaseMusicSubject = BaseMusicConfig.get("synBaseMusicSubject");
        String[] BaseMusicSynMailto = BaseMusicConfig.get("BaseMusicSynMailto")
                                                     .split(",");
        StringBuffer msgInfo = new StringBuffer();
        /**
         * 0,处理的总行数；1，成功新增；2，成功修改；3，成功删除；4，数据检查不合法；5，数据处理失败
         */
        Integer musicmailInfo[] = new Integer[6];
        /**
         * 0,处理的总行数；1，成功新增；2，重复上架失败；3，元数据不存在上架失败；4，数据检查不合法；5，数据处理失败
         */
        Integer newRefmailInfo[] = new Integer[6];
        /**
         * 0,处理的总行数；1，成功新增；2，重复上架失败；3，元数据不存在上架失败；4，数据检查不合法；5，数据处理失败
         */
        Integer charRefmailInfo[] = new Integer[6];

        /**
         * 下架商品情况
         */
        Integer[] delRefmailInfo = new Integer[1];

        /**
         * 更新货架商品数量
         */
        Integer[] updatecrInfo = new Integer[1];

        /**
         * 用于保存校验失败的数据行信息
         */
        StringBuffer checkFailureRow = new StringBuffer();

        /**
         * 用于保存新音乐校验失败的数据行信息
         */
        StringBuffer checkFailureRowByNew = new StringBuffer();

        /**
         * 用于保存校验失败的数据行信息
         */
        StringBuffer checkFailureRowByLatest = new StringBuffer();

        /**
         * 用于保存新音乐校验失败的数据行信息
         */
        StringBuffer checkFailureRowByUp = new StringBuffer();
        
        /**
         * 用于保存校验失败的数据行信息
         */
        StringBuffer errorFailureRow = new StringBuffer();

        /**
         * 用于保存新音乐校验失败的数据行信息
         */
        StringBuffer errorFailureRowByNew = new StringBuffer();

        /**
         * 用于保存校验失败的数据行信息
         */
        StringBuffer errorFailureRowByLatest = new StringBuffer();

        /**
         * 用于保存新音乐校验失败的数据行信息
         */
        StringBuffer errorFailureRowByUp = new StringBuffer();

        //
      //  BaseMusicFtpProcessor bp = new BaseMusicFtpProcessor();

        // List nameList=new ArrayList(1);
        try
        {
            // 已存在的全量基地音乐id清单
            HashMap existsMusic = new HashMap();
            // 已存在的全量基地音乐商品清单
            HashMap existsRefMusic = new HashMap();
           /* try
            { // 获取所有的已经存在的音乐
                existsMusic = BMusicDAO.getInstance().getAllexistMusicID();

            }
            catch (DAOException e1)
            // catch (Exception e1)
            {
                logger.error("从数据库中获取所有基地音乐ID时发生数据库异常！", e1);
                throw new BOException("从数据库中获取所有基地音乐ID时发生数据库异常", e1);
            }

            // 1,获取音乐清单文件名称
            String[] fNameRegex = BaseMusicConfig.get("MusicNameRegex")
                                                 .split(",");
            msgInfo.append("导入基地音乐元数据结果：");
            msgInfo.append("<br>");
            try
            {
                String localFileNames[] = bp.process(fNameRegex);// ftp 下载到本地
                this.importBaseMusicData(existsMusic,
                                         musicmailInfo,
                                         localFileNames,
                                         checkFailureRow,
                                         errorFailureRow);// 执行音乐清单导入

                if (musicmailInfo[0] != null)
                {
                    msgInfo.append("共处理行数：" + musicmailInfo[0].intValue());
                    msgInfo.append(";<br>成功新增：" + musicmailInfo[1].intValue());
                    msgInfo.append(";<br>成功修改：" + musicmailInfo[2].intValue());
                    msgInfo.append(";<br>成功删除：" + musicmailInfo[3].intValue());
                    msgInfo.append(";<br>数据检查不合法："
                                   + musicmailInfo[4].intValue());
                    if (musicmailInfo[4].intValue() > 0)
                    {
                        msgInfo.append(";<br>数据检查出错具体行：")
                               .append(checkFailureRow);
                    }
                    msgInfo.append(";<br>数据处理失败：" + musicmailInfo[5].intValue());
                    if (musicmailInfo[5].intValue() > 0)
                    {
                        msgInfo.append(";<br>数据处理失败具体行：第")
                               .append(errorFailureRow.substring(0, errorFailureRow.length()-2))
                               .append("行<br>");
                    }
                    msgInfo.append("<br>");
                }

            }
            catch (BOException e)
            {
                // TODO Auto-generated catch block
                logger.error("导入基地音乐元数据操作失败" + e);
                e.printStackTrace();
                msgInfo.append("导入基地音乐元数据操作失败" + e);
                msgInfo.append("<br>");
            }*/
            /** ********************新增导入新音乐数据 歌手信息解析入库********************** */
            /**
             * 0,处理的总行数；1，成功新增；2，成功修改；3，成功删除；4，数据检查不合法；5，数据处理失败
             */
            // 已存在的全量基地音乐歌手id清单
            HashMap existsNewMusicSinger = new HashMap();
            Integer newmusicSingermailInfo[] = new Integer[6];
            
            String[] newFNamesRegex = BaseMusicConfig.get("NewMusicSingerNameRegex")
            .split(",");
            NewBaseMusicFtpProcessor newbps = new NewBaseMusicFtpProcessor();
           
            try
            { // 获取所有的已经存在的音乐歌手ID
            	existsNewMusicSinger = BMusicDAO.getInstance()
            					.getAllexistNewMusicSingerID();
            }
            catch (DAOException e1)
            {
            		logger.error("从数据库中获取所有基地 新 音乐歌手 时发生数据库异常！", e1);
            		throw new BOException("从数据库中获取所有基地 新 音乐歌手时发生数据库异常", e1);
            }
            		msgInfo.append("<br>");
            		msgInfo.append("导入基地 新 音乐歌手 元数据结果：");
            		msgInfo.append("<br>");
            		try
            		{
            				String newSingerLocalFileNames[] = newbps.process(newFNamesRegex);// ftp
            				 // 下载到本地
                            this.importNewBaseMusicSingerData(existsNewMusicSinger,
                            							newmusicSingermailInfo,
                                                        newSingerLocalFileNames,
                                                        checkFailureRowByNew,
                                                        errorFailureRowByNew);// 执行音乐歌手导入
                            if (newmusicSingermailInfo[0] != null)
                            {
                                msgInfo.append("共处理行数：" + newmusicSingermailInfo[0].intValue());
                                msgInfo.append(";<br>成功新增："
                                               + newmusicSingermailInfo[1].intValue());
                                msgInfo.append(";<br>成功修改："
                                               + newmusicSingermailInfo[2].intValue());
                                msgInfo.append(";<br>成功删除："
                                               + newmusicSingermailInfo[3].intValue());
                                msgInfo.append(";<br>数据检查不合法："
                                               + newmusicSingermailInfo[4].intValue());
                                if (newmusicSingermailInfo[4].intValue() > 0)
                                {
                                    msgInfo.append(";<br>数据检查出错具体行：")
                                           .append(checkFailureRowByNew );
                                }
                                msgInfo.append(";<br>数据处理失败："
                                               + newmusicSingermailInfo[5].intValue());
                                if (newmusicSingermailInfo[5].intValue() > 0)
                                {
                                    msgInfo.append(";<br>数据处理失败具体行：第")
                                           .append(errorFailureRowByNew.substring(0, errorFailureRowByNew.length()-2))
                                           .append("行<br>");
                                }
                                msgInfo.append("<br>");
                            }

                        }
                        catch (BOException e)
                        {
                            // TODO Auto-generated catch block
                            logger.error("导入基地 新 音乐歌手数据操作失败" + e);
                            e.printStackTrace();
                            msgInfo.append("导入基地 新 音乐歌手数据操作失败" + e);
                            msgInfo.append("<br>");
                        }
            
            
            
            /** *******************新增导入新音乐数据 歌手信息解析入库 结束******************* */
            
            
            
            /** ********************新增导入新音乐数据到WAP门户********************** */
            /**
             * 0,处理的总行数；1，成功新增；2，成功修改；3，成功删除；4，数据检查不合法；5，数据处理失败
             */
            Integer newmusicmailInfo[] = new Integer[6];
            // 1,获取新音乐清单文件名称
            /**
             * 下架新商品情况
             */
            Integer[] delNewRefmailInfo = new Integer[1];
            /**
             * 更新货架商品数量
             */
            Integer[] updateNewcrInfo = new Integer[1];

            String[] newFNameRegex = BaseMusicConfig.get("NewMusicNameRegex")
                                                    .split(",");
            NewBaseMusicFtpProcessor newbp = new NewBaseMusicFtpProcessor();
            // 已存在的全量基地音乐id清单
            HashMap existsNewMusic = new HashMap();
            try
            { // 获取所有的已经存在的音乐
                existsNewMusic = BMusicDAO.getInstance()
                                          .getAllexistNewMusicID();
            }
            catch (DAOException e1)
            {
                logger.error("从数据库中获取所有基地 新 音乐ID时发生数据库异常！", e1);
                throw new BOException("从数据库中获取所有基地 新 音乐ID时发生数据库异常", e1);
            }
            msgInfo.append("<br>");
            msgInfo.append("导入基地 新 音乐元数据结果：");
            msgInfo.append("<br>");
            try
            {
                String newLocalFileNames[] = newbp.process(newFNameRegex);// ftp
                // 下载到本地
                this.importNewBaseMusicData(existsNewMusic,existsNewMusicSinger,
                                            newmusicmailInfo,
                                            newLocalFileNames,
                                            checkFailureRowByNew,
                                            errorFailureRowByNew);// 执行音乐清单导入
                if (newmusicmailInfo[0] != null)
                {
                    msgInfo.append("共处理行数：" + newmusicmailInfo[0].intValue());
                    msgInfo.append(";<br>成功新增："
                                   + newmusicmailInfo[1].intValue());
                    msgInfo.append(";<br>成功修改："
                                   + newmusicmailInfo[2].intValue());
                    msgInfo.append(";<br>成功删除："
                                   + newmusicmailInfo[3].intValue());
                    msgInfo.append(";<br>数据检查不合法："
                                   + newmusicmailInfo[4].intValue());
                    if (newmusicmailInfo[4].intValue() > 0)
                    {
                        msgInfo.append(";<br>数据检查出错具体行：")
                               .append(checkFailureRowByNew );
                    }
                    msgInfo.append(";<br>数据处理失败："
                                   + newmusicmailInfo[5].intValue());
                    if (newmusicmailInfo[5].intValue() > 0)
                    {
                        msgInfo.append(";<br>数据处理失败具体行：第")
                               .append(errorFailureRowByNew.substring(0, errorFailureRowByNew.length()-2))
                               .append("行<br>");
                    }
                    msgInfo.append("<br>");
                }

            }
            catch (BOException e)
            {
                // TODO Auto-generated catch block
                logger.error("导入基地 新 音乐元数据操作失败" + e);
                e.printStackTrace();
                msgInfo.append("导入基地 新 音乐元数据操作失败" + e);
                msgInfo.append("<br>");
            }
            // 4,下架全量过期基地音乐商品
            // this.delInvalBMusicRef(delNewRefmailInfo);
            BaseMusicBO.getInstance().delInvalNewBMusicRef(delRefmailInfo);
            if (delNewRefmailInfo != null && delNewRefmailInfo[0] != null)
            {
                msgInfo.append("<br>");
                msgInfo.append("下架过期基地 新 音乐商品数量为："
                               + delNewRefmailInfo[0].intValue());
                msgInfo.append("<br>");
            }
            // 5,更新货架商品数量
            // this.updateCategoryRefSum(updateNewcrInfo);
            BaseMusicBO.getInstance().updateAllNewCategoryRefSum(updatecrInfo);
            if (updateNewcrInfo != null && updateNewcrInfo[0] != null)
            {
                msgInfo.append("<br>");
                msgInfo.append("更新 新 音乐货架数量为：" + updateNewcrInfo[0].intValue());
                msgInfo.append("<br>");
            }

            /** *******************新增导入新音乐数据到WAP门户 结束******************* */

            // 导入完成
            if ("true".equals(isCloseNotBaseMusicData))
            {
            	 /** *******************新增导入新音乐专辑及专辑歌曲 数据数据到货架 开始******************* */
//            	 2,同步专辑
            	/**
                 * 0,处理的总行数；1，成功新增；2，成功修改；3，成功删除；4，数据检查不合法；5，数据处理失败
                 */
                Integer newmusicAlbummailInfo[] = new Integer[6];
                
                List albumNameAndCate = BaseMusicConfig.getArrayValues("albumNameRegex");
                if (albumNameAndCate != null && albumNameAndCate.size() > 0)
                {
                    msgInfo.append("<br>");
                    msgInfo.append("导入基地新音乐专辑数据结果：");
                    msgInfo.append("<br>");
                    for (int i = 0; i < albumNameAndCate.size(); i++)
                    {
                        String hm[] = ( String[] ) albumNameAndCate.get(i);
                        if (hm.length == 2)
                        {

                            String newcategoryID = hm[0];
                            String newNameRegexs[] = hm[1].split(",");
                            
                            // 已存在的全量基地专辑清单
                            HashMap existsAlbumCate = new HashMap();
                            try
                            { // 获取已经存在的音乐专辑
                            	existsAlbumCate = BMusicDAO.getInstance()
                                                          .getAllexistAlbumMusicCateID(newcategoryID);
                            }
                            catch (DAOException e1)
                            {
                                logger.error("从数据库中获取所有基地 新 音乐ID时发生数据库异常！", e1);
                                throw new BOException("从数据库中获取所有基地 新 音乐ID时发生数据库异常", e1);
                            }
                            
                            try
                            {
                                String localNewNameRegexs[] = newbp.process(newNameRegexs);// ftp
                                // 下载到本地
                                this.importAlbumMusicCate(existsNewMusicSinger,
                                							existsAlbumCate,
                                							newmusicAlbummailInfo,
                                                            newcategoryID,
                                                            localNewNameRegexs,
                                                            checkFailureRowByLatest,
                                                            errorFailureRowByLatest);
                            }
                            catch (BOException e)
                            {
                                // TODO Auto-generated catch block
                                logger.error("同步新音乐专辑操作失败" + e);
                                e.printStackTrace();
                                msgInfo.append("同步新音乐专辑操作失败" + e);
                                msgInfo.append("<br>");
                            }
                        }
                    }
                    if (newmusicAlbummailInfo[0] != null)
                    {
                    	msgInfo.append("共处理行数：" + newmusicAlbummailInfo[0].intValue());
                        msgInfo.append(";<br>成功新增："
                                       + newmusicAlbummailInfo[1].intValue());
                        msgInfo.append(";<br>成功修改："
                                       + newmusicAlbummailInfo[2].intValue());
                        msgInfo.append(";<br>成功删除："
                                       + newmusicAlbummailInfo[3].intValue());
                        msgInfo.append(";<br>数据检查不合法："
                                       + newmusicAlbummailInfo[4].intValue());
                        if (newmusicAlbummailInfo[4].intValue() > 0)
                        {
                            msgInfo.append(";<br>数据检查出错具体行：")
                                   .append(checkFailureRowByNew );
                        }
                        msgInfo.append(";<br>数据处理失败："
                                       + newmusicAlbummailInfo[5].intValue());
                        if (newmusicAlbummailInfo[5].intValue() > 0)
                        {
                            msgInfo.append(";<br>数据处理失败具体行：第")
                                   .append(errorFailureRowByNew.substring(0, errorFailureRowByNew.length()-2))
                                   .append("行<br>");
                        }
                        msgInfo.append("<br>");
                    }


                }
            	
            	
                // 2,上架专辑歌曲音乐商品
                String[] albumRefNameAndCate = BaseMusicConfig.get("albumRefNameRegex").split(",");
                HashMap  existsAlbumCateId = null;
                try
                { // 获取所有的已经存在的专辑
                existsAlbumCateId = BMusicDAO.getInstance()
                .getAllexistAlbumMusicCateID();
                }catch (DAOException e1)
              {
                  logger.error("从数据库中获取所有基地 新 音乐专辑 时发生数据库异常！", e1);
                  throw new BOException("从数据库中获取所有基地 新 音乐专辑 时发生数据库异常", e1);
              }
                if (albumRefNameAndCate != null && albumRefNameAndCate.length > 0)
                {
                    msgInfo.append("<br>");
                    msgInfo.append("导入基地音乐专辑歌曲音乐商品数据结果：");
                    msgInfo.append("<br>");
               
                            try
                            {
                                String localNewNameRegexs[] = newbp.process(albumRefNameAndCate);// ftp
                                // 下载到本地
                                this.importNewMusicRefrence( existsAlbumCateId,
                                		 					//existsMusic,
                                							existsNewMusic,
                                                            existsRefMusic,
                                                            newRefmailInfo,
                                                           
                                                            localNewNameRegexs,
                                                            checkFailureRowByLatest,
                                                            errorFailureRowByLatest);
                            }
                            catch (BOException e)
                            {
                                // TODO Auto-generated catch block
                                logger.error("上架新音乐专辑歌曲操作失败" + e);
                                e.printStackTrace();
                                msgInfo.append("上架新音乐专辑歌曲操作失败" + e);
                                msgInfo.append("<br>");
                            }
           
                    if (newRefmailInfo[0] != null)
                    {
                        msgInfo.append("共处理行数：" + newRefmailInfo[0].intValue());
                        msgInfo.append(";<br>上架："
                                       + (newRefmailInfo[1].intValue() - newRefmailInfo[2].intValue()));
                        msgInfo.append(";<br>重复上架失败："
                                       + newRefmailInfo[2].intValue());
                        msgInfo.append(";<br>元数据不存在上架失败："
                                       + newRefmailInfo[3].intValue());
                        msgInfo.append(";<br>数据检查不合法："
                                       + newRefmailInfo[4].intValue());
                        if (newRefmailInfo[4].intValue() > 0)
                        {
                            msgInfo.append(";<br>数据检查出错具体行：")
                                   .append(checkFailureRowByLatest );
                        }
                        msgInfo.append(";<br>数据处理失败："
                                       + newRefmailInfo[5].intValue());
                        if (newRefmailInfo[5].intValue() > 0)
                        {
                            msgInfo.append(";<br>数据处理失败具体行：第")
                                   .append(errorFailureRowByLatest.substring(0, errorFailureRowByLatest.length()-2))
                                   .append("行<br>");
                        }
                        msgInfo.append("<br>");
                    }

                }
                /** *******************新增导入新音乐专辑及专辑歌曲 数据数据到货架 结束******************* */
            }
            // 3，上架榜单音乐商品
            List chartNameAndCate = BaseMusicConfig.getArrayValues("ChartNameRegex");
            if (chartNameAndCate != null && chartNameAndCate.size() > 0)
            {

                for (int i = 0; i < chartNameAndCate.size(); i++)
                {
                    String hm2[] = ( String[] ) chartNameAndCate.get(i);
                    if (hm2.length == 2)
                    {
                        String chartcategoryID = hm2[0];
                        String chartNameRegexs[] = hm2[1].split(",");

                        try
                        {
                            // String localChartNameRegexs[] =
                            // bp.process(chartNameRegexs);// ftp
                            String localChartNameRegexs[] = newbp.process(chartNameRegexs);// ftp
                            // 下载到本地
                            this.importChartsMusicRefrence(existsNewMusic,
                                                           existsRefMusic,
                                                           charRefmailInfo,
                                                           chartcategoryID,
                                                           localChartNameRegexs,
                                                           checkFailureRowByUp,
                                                           errorFailureRowByUp);
                        }
                        catch (BOException e)
                        {
                            // TODO Auto-generated catch block
                            // MUSIC_LIST_~DyyyyMMdd~_销量周榜.txt
                            // MUSIC_LIST_~DyyyyMMdd[-1]~_销量周榜.txt
                            String filename = hm2[1].substring(hm2[1].lastIndexOf("_") + 1,
                                                               hm2[1].lastIndexOf("."));
                            msgInfo.append("<br>");
                            msgInfo.append("导入基地音乐排行榜文件[" + filename + "]数据结果：");
                            msgInfo.append("<br>");
                            logger.error("上架榜单音乐排行榜商品操作失败" + e);
                            e.printStackTrace();
                            msgInfo.append("上架榜单音排行榜乐商品操作失败" + e);
                            msgInfo.append("<br>");
                        }
                        String filename = hm2[1].substring(hm2[1].lastIndexOf("_") + 1,
                                                           hm2[1].lastIndexOf("."));
                        msgInfo.append("<br>");
                        msgInfo.append("导入基地音乐排行榜文件[" + filename + "]数据结果：");
                        msgInfo.append("<br>");
                        if (charRefmailInfo[0] != null)
                        {
                            msgInfo.append("共处理行数："
                                           + charRefmailInfo[0].intValue());
                            msgInfo.append(";<br>上架:"
                                           + (charRefmailInfo[1].intValue() - charRefmailInfo[2].intValue()));
                            msgInfo.append(";<br>重复上架失败:"
                                           + charRefmailInfo[2].intValue());
                            msgInfo.append(";<br>元数据不存在上架失败："
                                           + charRefmailInfo[3].intValue());
                            msgInfo.append(";<br>数据检查不合法："
                                           + charRefmailInfo[4].intValue());
                            if (charRefmailInfo[4].intValue() > 0)
                            {
                                msgInfo.append(";<br>数据检查出错具体行：")
                                       .append(checkFailureRowByUp );
                            }
                            msgInfo.append(";<br>数据处理失败："
                                           + charRefmailInfo[5].intValue());
                            if (charRefmailInfo[5].intValue() > 0)
                            {
                                msgInfo.append(";<br>数据处理失败具体行：第")
                                       .append(errorFailureRowByUp.substring(0, errorFailureRowByUp.length()-2))
                                       .append("行<br>");
                            }
                            msgInfo.append("<br>");
                        }
                        else
                        {
                            // 没有找到数据
                            msgInfo.append("没有找到文件");
                            msgInfo.append("<br>");
                        }
                        // 重新初始化数据
                        charRefmailInfo = new Integer[6];
                    }
                }
            }
            // 4,下架全量过期基地音乐商品
            this.delInvalBMusicRef(delRefmailInfo);
            if (delRefmailInfo != null && delRefmailInfo[0] != null)
            {
                msgInfo.append("<br>");
                msgInfo.append("下架过期基地音乐商品数量为：" + delRefmailInfo[0].intValue());
                msgInfo.append("<br>");
            }
            // 5,更新货架商品数量
            this.updateCategoryRefSum(updatecrInfo);
            if (updatecrInfo != null && updatecrInfo[0] != null)
            {
                msgInfo.append("<br>");
                msgInfo.append("更新音乐货架数量为：" + updatecrInfo[0].intValue());
                msgInfo.append("<br>");
            }
            
            
            
         // 6，同步更新铃音盒
            /** ********************新增导入铃音盒数据********************** */
            /**
             * 0,处理的总行数；1，成功新增；2，成功修改；3，成功删除；4，数据检查不合法；5，数据处理失败
             */
            // 已存在的全量铃音盒id清单
            HashMap existsTonebox = new HashMap();
            Integer toneboxMailInfo[] = new Integer[6];
            checkFailureRowByNew=new StringBuffer();
            errorFailureRowByNew=new StringBuffer();
            String[] toneboxNameRegex = BaseMusicConfig.get("toneboxNameRegex").split(",");
           
            try
            { // 获取所有的已经存在的音乐歌手ID
            	existsTonebox = new ToneBoxDAO().getAllExistToneBoxID();
            					
            }
            catch (DAOException e1)
            {
            		logger.error("从数据库中获取所有铃音盒时发生数据库异常！", e1);
            		throw new BOException("从数据库中获取所有铃音盒时发生数据库异常", e1);
            }
    		msgInfo.append("<br>");
    		msgInfo.append("导入铃音盒元数据结果：");
    		msgInfo.append("<br>");
    		try
    		{
    				String toneboxLocalFileNames[] = newbps.process(toneboxNameRegex);// ftp
    				
    				String verfFile = FileUtil.getVerfFile(toneboxLocalFileNames);
    				String verfMsg = null;
    				if(verfFile!=null){
    					try{
    						verfMsg = FileUtil.getVerfMsg(verfFile);
    					}catch(Exception e){
    						
    					}
    					
    				}
    				
    				 // 下载到本地
                    this.importToneBox(existsTonebox,
                    							toneboxMailInfo,
                    							toneboxLocalFileNames,
                                                checkFailureRowByNew,
                                                errorFailureRowByNew);// 执行音乐歌手导入
                    if (toneboxMailInfo[0] != null)
                    {
                    	msgInfo.append("校验文件信息如下："+verfMsg);
                        msgInfo.append("共处理行数：" + toneboxMailInfo[0].intValue());
                        msgInfo.append(";<br>成功新增："
                                       + toneboxMailInfo[1].intValue());
                        msgInfo.append(";<br>成功修改："
                                       + toneboxMailInfo[2].intValue());
                        msgInfo.append(";<br>成功删除："
                                       + toneboxMailInfo[3].intValue());
                        msgInfo.append(";<br>数据检查不合法："
                                       + toneboxMailInfo[4].intValue());
                        if (toneboxMailInfo[4].intValue() > 0)
                        {
                            msgInfo.append(";<br>数据检查出错具体行：")
                                   .append(checkFailureRowByNew );
                        }
                        msgInfo.append(";<br>数据处理失败："
                                       + toneboxMailInfo[5].intValue());
                        if (toneboxMailInfo[5].intValue() > 0)
                        {
                            msgInfo.append(";<br>数据处理失败具体行：第")
                                   .append(errorFailureRowByNew.substring(0, errorFailureRowByNew.length()-2))
                                   .append("行<br>");
                        }
                        msgInfo.append("<br>");
                    }

                }
                catch (BOException e)
                {
                    // TODO Auto-generated catch block
                    logger.error("导入铃音盒数据操作失败" + e);
                    e.printStackTrace();
                    msgInfo.append("导入铃音盒数据操作失败" + e);
                    msgInfo.append("<br>");
                }
            
            /** *******************新增导入铃音盒信息解析入库 结束******************* */
            
             // 7，同步更新铃音盒歌曲
                /** ********************新增导入铃音盒歌曲数据********************** */
                /**
                 * 0,处理的总行数；1，成功新增；2，成功修改；3，成功删除；4，数据检查不合法；5，数据处理失败
                 */
                // 已存在的全量铃音盒id清单
                HashMap existsToneboxSong = new HashMap();
                Integer toneboxSongMailInfo[] = new Integer[6];
                checkFailureRowByNew=new StringBuffer();
                errorFailureRowByNew=new StringBuffer();
                String[] toneboxSongNameRegex = BaseMusicConfig.get("toneboxSongNameRegex").split(",");
               
                try
                { // 获取所有的已经存在的音乐歌手ID
                	existsToneboxSong = new ToneBoxSongDAO().getAllExistToneBoxSongID();
                					
                }
                catch (DAOException e1)
                {
                		logger.error("从数据库中获取所有铃音盒歌曲时发生数据库异常！", e1);
                		throw new BOException("从数据库中获取所有铃音盒歌曲时发生数据库异常", e1);
                }
        		msgInfo.append("<br>");
        		msgInfo.append("导入铃音盒歌曲元数据结果：");
        		msgInfo.append("<br>");
        		try
        		{
        				String toneboxSongLocalFileNames[] = newbps.process(toneboxSongNameRegex);// ftp
        				
        				String verfFile = FileUtil.getVerfFile(toneboxSongLocalFileNames);
        				String verfMsg = null;
        				if(verfFile!=null){
        					try{
        						verfMsg = FileUtil.getVerfMsg(verfFile);
        					}catch(Exception e){
        						
        					}
        					
        				}
        				
        				 // 下载到本地
                        this.importToneBoxSong(existsToneboxSong,
                        							toneboxSongMailInfo,
                        							toneboxSongLocalFileNames,
                                                    checkFailureRowByNew,
                                                    errorFailureRowByNew);// 执行音乐歌手导入
                        if (toneboxSongMailInfo[0] != null)
                        {
                        	msgInfo.append("校验文件信息如下："+verfMsg);
                        	
                            msgInfo.append("共处理行数：" + toneboxSongMailInfo[0].intValue());
                            msgInfo.append(";<br>成功新增："
                                           + toneboxSongMailInfo[1].intValue());
                            msgInfo.append(";<br>成功修改："
                                           + toneboxSongMailInfo[2].intValue());
                            msgInfo.append(";<br>成功删除："
                                           + toneboxSongMailInfo[3].intValue());
                            msgInfo.append(";<br>数据检查不合法："
                                           + toneboxSongMailInfo[4].intValue());
                            if (toneboxSongMailInfo[4].intValue() > 0)
                            {
                                msgInfo.append(";<br>数据检查出错具体行：")
                                       .append(checkFailureRowByNew );
                            }
                            msgInfo.append(";<br>数据处理失败："
                                           + toneboxSongMailInfo[5].intValue());
                            if (toneboxSongMailInfo[5].intValue() > 0)
                            {
                                msgInfo.append(";<br>数据处理失败具体行：第")
                                       .append(errorFailureRowByNew.substring(0, errorFailureRowByNew.length()-2))
                                       .append("行<br>");
                            }
                            msgInfo.append("<br>");
                        }

                    }
                    catch (BOException e)
                    {
                        // TODO Auto-generated catch block
                        logger.error("导入铃音盒歌曲数据操作失败" + e);
                        e.printStackTrace();
                        msgInfo.append("导入铃音盒歌曲数据操作失败" + e);
                        msgInfo.append("<br>");
                    }
                
                /** *******************新增导入铃音盒歌曲信息解析入库 结束******************* */   
            

        }
        catch (BOException e)
        {
            // TODO Auto-generated catch block
            logger.error("操作失败" + e);
            e.printStackTrace();
            msgInfo.append("操作失败" + e);
        }
        finally
        {
            logger.error("基地音乐数据导入：" + msgInfo);
            this.sendMail(msgInfo.toString(),
                          BaseMusicSynMailto,
                          synBaseMusicSubject);
        }
    }

    /**
     * 插入音乐清单
     * 
     * @param msgInfo
     * @param RefDataTime
     * @param nameList
     * @return
     * @throws BOException
     */
    private int importBaseMusicData(HashMap existsMusic,
                                    Integer musicmailInfo[], String[] nameList,
                                    StringBuffer checkFailureRow, StringBuffer errorFailureRow)
                    throws BOException
    {
        BaseMusicBO.getInstance().importBaseMusic(existsMusic,
                                                  musicmailInfo,
                                                  nameList,
                                                  checkFailureRow, errorFailureRow);
        return 0;
    }

    /**
     * 插入新 音乐清单
     * 
     * @param msgInfo
     * @param RefDataTime
     * @param nameList
     * @return
     * @throws BOException
     */
    private void importNewBaseMusicData(HashMap existsMusic,
    									HashMap  existsMusicSingers,
                                        Integer musicmailInfo[],
                                        String[] nameList,
                                        StringBuffer checkFailureRowByNew, StringBuffer errorFailureRowByNew)
                    throws BOException
    {
        BaseMusicBO.getInstance().importNewBaseMusic(existsMusic,existsMusicSingers,
                                                     musicmailInfo,
                                                     nameList,
                                                     checkFailureRowByNew, errorFailureRowByNew);
    }

    /**
     * 插入新 音乐歌手
     * 
     * @param msgInfo
     * @param RefDataTime
     * @param nameList
     * @return
     * @throws BOException
     */
    private void importNewBaseMusicSingerData(HashMap existsNewMusicSinger,
                                        Integer newmusicSingermailInfo[],
                                        String[] nameList,
                                        StringBuffer checkFailureRowByNew, StringBuffer errorFailureRowByNew)
                    throws BOException
    {
        BaseMusicBO.getInstance().importNewBaseMusicSinger(existsNewMusicSinger,
        												newmusicSingermailInfo,
                                                     nameList,
                                                     checkFailureRowByNew, errorFailureRowByNew);
    }
    
    
    /**
     * 导入专辑数据
     * 
     * @return
     */
    private int importAlbumMusicCate(HashMap existsNewMusicSinger,
                                       HashMap existsAlbumCate,
                                       Integer newmusicAlbummailInfo[],
                                       String categoryID, String[] fileName,
                                       StringBuffer checkFailureRowByLatest, StringBuffer errorFailureRowByLatest)
                    throws BOException
    {
        BaseMusicBO.getInstance()
                   .importNewBaseMusicAlbumCate(existsNewMusicSinger,
                		   					existsAlbumCate,
                		   					newmusicAlbummailInfo,
                                           categoryID,
                                           fileName,
                                           checkFailureRowByLatest,
                                           errorFailureRowByLatest);
        return 0;
    }
    
    /**
     * 导入专辑歌曲音乐商品
     * 
     * @return
     */
    private int importNewMusicRefrence(HashMap existsAlbum,
    		                              HashMap existsMusic,
                                       HashMap existsRefMusic,
                                       Integer[] newRefmailInfo,
                                        String[] fileName,
                                       StringBuffer checkFailureRowByLatest, StringBuffer errorFailureRowByLatest)
                    throws BOException
    {
        BaseMusicBO.getInstance()
                   .importNewMusicRefrence(existsAlbum,
                		                     existsMusic,
                                           existsRefMusic,
                                           newRefmailInfo,
                                          
                                           fileName,
                                           checkFailureRowByLatest,
                                           errorFailureRowByLatest);
        return 0;
    }

    /**
     * 上架榜单音乐商品
     * 
     * @return
     */
    private int importChartsMusicRefrence(HashMap existsMusic,
                                          HashMap existsRefMusic,
                                          Integer[] charRefmailInfo,
                                          String categoryID, String[] fileName,
                                          StringBuffer checkFailureRowByUp,
                                          StringBuffer errorFailureRowByUp)
                    throws BOException
    {
        BaseMusicBO.getInstance()
                   .importCharNewMusicRefrence(existsMusic,
                                               existsRefMusic,
                                               charRefmailInfo,
                                               categoryID,
                                               fileName,
                                               checkFailureRowByUp,
                                               errorFailureRowByUp);

        return 0;
    }
    
    /**
     * 导入铃音盒
     * 
     * @return
     */
    private int importToneBox(HashMap existsTonebox,
                                          Integer[] toneboxMailInfo,
                                          String[] toneboxLocalFileNames,
                                          StringBuffer checkFailureRowByNew,
                                          StringBuffer errorFailureRowByNew)
                    throws BOException
    {
        BaseMusicBO.getInstance()
                   .importToneBox(existsTonebox,
							toneboxMailInfo,
							toneboxLocalFileNames,
                            checkFailureRowByNew,
                            errorFailureRowByNew);

        return 0;
    }
    
    /**
     * 导入铃音盒歌曲
     * 
     * @return
     */
    private int importToneBoxSong(HashMap existsToneboxSong,
                                          Integer[] toneboxSongMailInfo,
                                          String[] toneboxSongLocalFileNames,
                                          StringBuffer checkFailureRowByNew,
                                          StringBuffer errorFailureRowByNew)
                    throws BOException
    {
        BaseMusicBO.getInstance()
                   .importToneBoxSong(existsToneboxSong,
							toneboxSongMailInfo,
							toneboxSongLocalFileNames,
                            checkFailureRowByNew,
                            errorFailureRowByNew);

        return 0;
    }


    /**
     * 下架全量过期音乐商品
     * 
     * @return
     */
    private int delInvalBMusicRef(Integer[] delRefmailInfo) throws BOException
    {
        // BaseMusicBO.getInstance().importCharMusicRefrence(existsMusic,existsRefMusic,charRefmailInfo,categoryID,fileName);
        BaseMusicBO.getInstance().delInvalBMusicRef(delRefmailInfo);
        return 0;
    }

    private int updateCategoryRefSum(Integer[] updatecrInfo) throws BOException
    {
        BaseMusicBO.getInstance().updateAllCategoryRefSum(updatecrInfo);
        return 0;
    }

    /**
     * 发送邮件
     * 
     * @param mailContent,邮件内容
     */
    private void sendMail(String mailContent, String[] mailTo, String subject)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("sendMail(" + mailContent + ")");
        }
        // 得到邮件接收者数组
        // String[] mailTo = MailConfig.getInstance().getMailToArray();
        // String subject = "基地音乐数据导入";
        if (logger.isDebugEnabled())
        {
            logger.debug("mailTo Array is:" + Arrays.asList(mailTo));
            logger.debug("mail subject is:" + subject);
            logger.debug("mailContent is:" + mailContent);
        }
        Mail.sendMail(subject, mailContent, mailTo);
    }

}
