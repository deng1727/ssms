
package com.aspire.ponaadmin.web.baserecomm;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.util.StringUtils;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.baserecomm.basebook.BaseBookDAO;
import com.aspire.ponaadmin.web.baserecomm.basebook.BaseBookVO;
import com.aspire.ponaadmin.web.baserecomm.basegame.BaseGameDAO;
import com.aspire.ponaadmin.web.baserecomm.basemusic.BaseMusicDAO;
import com.aspire.ponaadmin.web.baserecomm.basevideo.BaseVideoDAO;
import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPTransferType;

/**
 * 基地数据类型
 * 
 * @author wml
 * 
 */
public class BaseRecommBO
{

    /**
     * 日志引用
     */
    protected static final JLogger logger = LoggerFactory.getLogger(BaseRecommBO.class);

    private static BaseRecommBO bo = new BaseRecommBO();

    private static String BASEGAME = "baseGame";

    private static String BASEMUSIC = "baseMusic";

    private static String BASEBOOK = "baseBook";

    private static String BASEVIDEO = "baseVideo";

    private BaseRecommBO()
    {
    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static BaseRecommBO getInstance()
    {
        return bo;
    }

    /**
     * 用于查询基地游戏表中推荐信息
     * 
     * @param page
     * @param gameName 游戏名
     * @param gameDesc 游戏简介
     * @throws BOException
     */
    public void queryBaseGameList(PageResult page, String gameName,
                                  String gameDesc) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("BaseRecommBO.queryBaseGameList(gameName=" + gameName
                         + ", gameDesc=" + gameDesc + ") is start.");
        }

        try
        {
            BaseGameDAO.getInstance().queryBaseGameList(page,
                                                        gameName,
                                                        gameDesc);
        }
        catch (DAOException e)
        {
            logger.error(e);

            throw new BOException("查询基地游戏表中推荐信息时发生数据库异常！");
        }
    }

    /**
     * 用于查询基地音乐表中推荐信息
     * 
     * @param page
     * @param gameName 游戏名
     * @param gameDesc 游戏简介
     * @throws BOException
     */
    public void queryBaseMusicList(PageResult page, String songName,
                                   String singer) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("BaseRecommBO.queryBaseMusicList(songName=" + songName
                         + ", singer=" + singer + ") is start.");
        }

        try
        {
            BaseMusicDAO.getInstance().queryBaseMusicList(page,
                                                          songName,
                                                          singer);
        }
        catch (DAOException e)
        {
            logger.error(e);

            throw new BOException("查询基地音乐表中推荐信息时发生数据库异常！");
        }
    }

    /**
     * 用于查询基地图书表中推荐信息
     * 
     * @param page
     * @throws BOException
     */
    public void queryBaseBookList(PageResult page, BaseBookVO vo)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("BaseRecommBO.queryBaseBookList( ) is start.");
        }

        try
        {
            BaseBookDAO.getInstance().queryBaseBookList(page, vo);
        }
        catch (DAOException e)
        {
            logger.error(e);

            throw new BOException("查询基地图书表中推荐信息时发生数据库异常！");
        }
    }

    /**
     * 用于查询基地视频表中推荐信息
     * 
     * @param page
     * @param videoName
     * @throws BOException
     */
    public void queryBaseVideoList(PageResult page, String videoName)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("BaseRecommBO.queryBaseVideoList( " + videoName
                         + " ) is start.");
        }

        try
        {
            BaseVideoDAO.getInstance().queryBaseVideoList(page, videoName);
        }
        catch (DAOException e)
        {
            logger.error(e);

            throw new BOException("查询基地视频表中推荐信息时发生数据库异常！");
        }
    }

    /**
     * 新增基地视频数据推荐信息
     * 
     * @param videoName
     * @param videoUrl
     * @throws BOException
     */
    public void addData(String videoName, String videoUrl) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("BaseRecommBO.addData( " + videoName + ", " + videoUrl
                         + " ) is start.");
        }

        try
        {
            BaseVideoDAO.getInstance().addBaseVideo(videoName, videoUrl);
        }
        catch (DAOException e)
        {
            logger.error(e);

            throw new BOException("新增基地视频数据推荐信息时发生数据库异常！");
        }
    }

    /**
     * 用于查询基地临时表中指定类型推荐信息
     * 
     * @param page
     * @param dataType 基地数据类型
     * @throws BOException
     */
    public void queryBaseDateTempList(PageResult page, String dataType)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("BaseRecommBO.queryBaseGameList(dataType=" + dataType
                         + ") is start.");
        }

        try
        {
            if (BASEGAME.equals(dataType))
            {
                BaseGameDAO.getInstance().queryBaseGameTempList(page);
            }
            else if (BASEMUSIC.equals(dataType))
            {
                BaseMusicDAO.getInstance().queryBaseMusicTempList(page);
            }
            else if (BASEBOOK.equals(dataType))
            {
                BaseBookDAO.getInstance().queryBaseBookTempList(page);
            }
            else if (BASEVIDEO.equals(dataType))
            {
                BaseVideoDAO.getInstance().queryBaseVideoTempList(page);
            }
        }
        catch (DAOException e)
        {
            logger.error(e);

            throw new BOException("查询基地临时表中指定类型推荐信息时发生数据库异常！");
        }
    }

    /**
     * 把基地游戏数据导出成文件
     * 
     * @param dataType 基地数据类型
     * @throws BOException
     */
    public void toFile(String dataType) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("BaseRecommBO.toFile(dataType=" + dataType
                         + ") is start.");
        }

        List dataList = new ArrayList();

        // 得到生成文件所需数据
        try
        {
            if (BASEGAME.equals(dataType))
            {
                dataList = BaseGameDAO.getInstance().queryToFileData();
            }
            else if (BASEMUSIC.equals(dataType))
            {
                dataList = BaseMusicDAO.getInstance().queryToFileData();
            }
            else if (BASEBOOK.equals(dataType))
            {
                dataList = BaseBookDAO.getInstance().queryToFileData();
            }
            else if (BASEVIDEO.equals(dataType))
            {
                dataList = BaseVideoDAO.getInstance().queryToFileData();
            }
        }
        catch (DAOException e)
        {
            logger.error(e);

            throw new BOException("导出基地推荐文件时发生数据库异常！");
        }

        // 生成文件
        try
        {
            toFileByData(dataList, dataType);
        }
        catch (Exception e)
        {
            logger.error(e);

            throw new BOException("导出基地推荐文件时发生异常！");
        }
    }

    /**
     * 根据不同类型的数据生成不同的基地数据文件
     * 
     * @param dataList
     * @param dataType
     * @throws BOException
     * @throws Exception
     */
    private void toFileByData(List dataList, String dataType)
                    throws BOException
    {
        List fileName = new ArrayList();
        List absoluteFileName = new ArrayList();
        List fileSize = new ArrayList();

        File file = null;
        BufferedWriter bw = null;

        int n = 0;

        try
        {
            // 循环遍历
            for (int i = 0; i < dataList.size(); i++)
            {
                // 如果记录数可以被30000整除。换文件
                if (i % BaseRecommConfig.DATANUM == 0)
                {
                    // 如果流不为空。关
                    if (bw != null)
                    {
                        bw.close();
                        fileSize.add(String.valueOf(n));
                        n = 0;
                    }

                    // 得到文件
                    file = getFileName(dataType, i);

                    // 保存文件名信息，用以上传FTP服务器
                    absoluteFileName.add(file.getAbsoluteFile());
                    fileName.add(file.getName());

                    // 在新文件中建流
                    bw = new BufferedWriter(new FileWriter(file, true), 2048);
                }

                bw.write(String.valueOf(dataList.get(i)));

                bw.write("\r\n");

                n++;
            }

            // 关最后一个文件的流
            if (bw != null)
            {
                bw.close();
                fileSize.add(String.valueOf(n));
            }
        }
        catch (IOException e)
        {
            logger.error(e);

            if (bw != null)
            {
                try
                {
                    bw.close();
                }
                catch (Exception e1)
                {
                    throw new BOException("生成推荐数据文件时，发生异常! fileName="
                                          + file.getName());
                }
            }

            throw new BOException("生成推荐数据文件时，发生异常! fileName=" + file.getName());

        }

        // 生成对账文件
        absoluteFileName.add(saveRecmdFile(fileName, fileSize, dataType));

        // 上传至FTP服务器
        upLoadFileToFTP(fileName, absoluteFileName);
    }

    /**
     * 用于写入对账文本中
     * 
     * @param fileName
     * @param absoluteFileName
     * @param fileSize
     * @param dataType
     * @throws BOException
     */
    private String saveRecmdFile(List fileName, List fileSize, String dataType)
                    throws BOException
    {
        File file = getRecmdFileName(dataType);
        StringBuffer sb = null;
        BufferedWriter bw = null;

        try
        {
            bw = new BufferedWriter(new FileWriter(file, true), 2048);

            for (int i = 0; i < fileName.size(); i++)
            {
                sb = new StringBuffer();

                sb.append(String.valueOf(fileName.get(i)))
                  .append("|")
                  .append(String.valueOf(fileSize.get(i)));

                bw.write(sb.toString());

                bw.write("\r\n");
            }
            
            bw.write("999999");

            bw.close();
        }
        catch (IOException e)
        {
            logger.error(e);

            if (bw != null)
            {
                try
                {
                    bw.close();
                }
                catch (Exception e1)
                {
                    throw new BOException("生成推荐数据文件时，发生异常! fileName="
                                          + file.getName());
                }
            }
            
            throw new BOException("生成推荐数据文件时，发生异常! fileName=" + file.getName());
        }
        
        // 文件名存入上传列表中
        fileName.add(file.getName());
        
        return file.getAbsolutePath();
    }

    /**
     * 用于上传文件至指定服务器上
     * 
     * @param fileName
     * @param absoluteFileName
     * @throws BOException
     */
    private void upLoadFileToFTP(List fileName, List absoluteFileName)
                    throws BOException
    {
        FTPClient ftp = null;
        String file = "";

        try
        {
            ftp = getFTPClient();

            for (int i = 0; i < fileName.size(); i++)
            {
                file = String.valueOf(fileName.get(i));

                ftp.put(String.valueOf(absoluteFileName.get(i)), file, false);
            }

            ftp.quit();
        }
        catch (Exception e)
        {
            if (ftp != null)
            {
                try
                {
                    ftp.quit();
                }
                catch (Exception e1)
                {
                    logger.error(e1);
                }
            }

            logger.error("上传基地数据文件至文件服务器时，发生异常! fileName=" + file);

            throw new BOException("上传基地数据文件至文件服务器时，发生异常! fileName=" + file);
        }
    }

    /**
     * 根据数的个数得到生成文件的文件名
     * 
     * @param dataType
     * @param dataNum
     * @return
     */
    private File getFileName(String dataType, int dataNum)
    {
        String tempNum = String.valueOf(dataNum / BaseRecommConfig.DATANUM + 1);

        if (tempNum.length() == 1)
        {
            tempNum = "00".concat(tempNum);
        }
        else if (tempNum.length() == 2)
        {
            tempNum = "0".concat(tempNum);
        }

        // 得到要写的文件
        File file = new File(getFileName(dataType, tempNum));

        // 如果存在当天的数据文件，删除
        if (file.exists())
        {
            file.delete();
        }

        return file;
    }

    /**
     * 用于生成对应基地类型的数据 XXX_yyyymmdd.txt
     * 
     * @param dataType
     * @return
     */
    private String getFileName(String dataType, String fileNum)
    {
        StringBuffer fileName = new StringBuffer();

        fileName.append(BaseRecommConfig.LOCALFILEPATH);

        fileName.append(getTypeName(dataType));

        fileName.append("_")
                .append(StringUtils.toString(new Date(), "yyyyMMdd"))
                .append("_")
                .append(fileNum)
                .append(".txt");

        return fileName.toString();
    }

    /**
     * 生成对账文件
     * 
     * @param dataType
     * @return
     */
    private File getRecmdFileName(String dataType)
    {
        StringBuffer fileName = new StringBuffer();

        fileName.append(BaseRecommConfig.LOCALFILEPATH);

        fileName.append("jd")
                .append("_")
                .append(getTypeName(dataType))
                .append("_recmd_")
                .append(StringUtils.toString(new Date(), "yyyyMMdd"))
                .append(".chk");

        // 得到要写的文件
        File file = new File(fileName.toString());

        // 如果存在当天的数据文件，删除
        if (file.exists())
        {
            file.delete();
        }

        return file;
    }

    /**
     * 返回数据类型对应的名称
     * 
     * @param dataType
     * @return
     */
    private String getTypeName(String dataType)
    {
        if (BASEGAME.equals(dataType))
        {
            return "game";
        }
        else if (BASEMUSIC.equals(dataType))
        {
            return "music";
        }
        else if (BASEBOOK.equals(dataType))
        {
            return "book";
        }
        else if (BASEVIDEO.equals(dataType))
        {
            return "video";
        }

        return "";
    }

    /**
     * 得到上传图片应用的ftp对象
     * 
     * @return
     * @throws IOException
     * @throws FTPException
     */
    private FTPClient getFTPClient() throws IOException, FTPException
    {
        FTPClient ftp = new FTPClient(BaseRecommConfig.FTPIP,
                                      BaseRecommConfig.FTPPORT);
        // 初始化ftp连接模式（FTPConnectMode.PASV或者FTPConnectMode.ACTIVE）
        ftp.setConnectMode(FTPConnectMode.PASV);

        // 使用给定的用户名、密码登陆ftp
        if (logger.isDebugEnabled())
        {
            logger.debug("login to FTPServer...");
        }

        ftp.login(BaseRecommConfig.FTPUSER, BaseRecommConfig.FTPPASS);
        // 设置文件传输类型（FTPTransferType.ASCII或者FTPTransferType.BINARY）
        ftp.setType(FTPTransferType.BINARY);

        if (!"".equals(BaseRecommConfig.FTPDIR))// 进入当前资源服务器目录。
        {
            ftp.chdir(BaseRecommConfig.FTPDIR);
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("login FTPServer successfully,transfer type is binary");
        }

        return ftp;
    }

    /**
     * 用于把数据存入临时表中
     * 
     * @param baseIds 数据id集合
     * @param baseType 数据类型
     * @throws BOException
     */
    public void addBaseData(String[] baseIds, String baseType)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("BaseRecommBO.addBaseData(baseType=" + baseType
                         + ") is start.");
        }

        // 判断是否存在数据库中
        for (int i = 0; i < baseIds.length; i++)
        {
            try
            {
                // 如果存在，抛出异常报错。
                if (BaseRecommDAO.getInstance().isHasBaseDate(baseIds[i],
                                                              baseType))
                {
                    throw new BOException("此数据已存在缓存临时表中！ baseId=" + baseIds[i]);
                }
            }
            catch (DAOException e)
            {
                logger.error(e);
                throw new BOException("数据存入临时表中时发生数据库异常！");
            }
        }

        // 加入数据库中
        try
        {
            BaseRecommDAO.getInstance().addBaseData(baseIds, baseType);
        }
        catch (DAOException e)
        {
            logger.error(e);

            throw new BOException("数据存入临时表中时发生数据库异常！");
        }
    }

    /**
     * 用于把数据从临时表中删除
     * 
     * @param baseIds 数据id集合
     * @param baseType 数据类型
     * @throws BOException
     */
    public void delBaseData(String[] baseIds, String baseType)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("BaseRecommBO.delBaseData(baseType=" + baseType
                         + ") is start.");
        }

        // 入数据库中删除
        try
        {
            BaseRecommDAO.getInstance().delBaseData(baseIds, baseType);
        }
        catch (DAOException e)
        {
            logger.error(e);

            throw new BOException("数据从临时表中删除时发生数据库异常！");
        }
    }
}