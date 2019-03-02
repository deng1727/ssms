/*
 * 文件名：DataSyncTaskForA8MusicDesc.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */

package com.aspire.ponaadmin.web.datasync.implement.music;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

import jxl.Sheet;
import jxl.Workbook;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataSyncConfig;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.DataSyncTask;
import com.aspire.ponaadmin.web.datasync.DataSyncTools;
import com.aspire.ponaadmin.web.datasync.FtpProcessor;
import com.aspire.ponaadmin.web.util.StringTool;
import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPTransferType;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2010
 * </p>
 * 
 * @author x_wangml
 * @version
 */
public class DataSyncTaskForA8MusicDesc extends DataSyncTask
{

    private FtpProcessor ftp;

    private List contentList = new ArrayList();

    private List mucisIdList = new ArrayList();

    private String toftpIP;

    private int toftpPort;

    private String toftpUser;

    private String toftpPassword;

    private String toftpDir;

    private String toftpUrl;
    
    private long fileSize = 50*1024;
    
    private String fileCss = "220*200";

    private static JLogger logger = LoggerFactory.getLogger(DataSyncTaskForA8MusicDesc.class);

    public void init(DataSyncConfig config) throws Exception
    {
        super.init(config);
        this.ftp = ( FtpProcessor ) Class.forName(config.get("task.ftp-class"))
                                         .newInstance();
        ftp.init(config);

        toftpIP = config.get("toftp.ip");
        toftpPort = Integer.parseInt(config.get("toftp.port"));
        toftpUser = config.get("toftp.user");
        toftpPassword = config.get("toftp.password");
        toftpDir = config.get("toftp.ftpDir");
        toftpUrl = config.get("toftp.url");
    }

    protected void doTask() throws BOException
    {
        String[] filenameList = null;
        List musicList = null;

        // ftp上取文件
        filenameList = ftp.process();

        if (filenameList.length == 0)
        {
            throw new BOException("没有找到本次任务的文件异常",
                                  DataSyncConstants.EXCEPTION_FILE_NOT_EXISTED);
        }

        try
        {
            // 首先解压缩文件到当前目录。
            String fileRootPath = DataSyncTools.unZip(filenameList[0]);

            // 得到音乐专辑列表文件
            String serviceinfoPath = getServiceInfoFileName(fileRootPath);

            // 解析xls文件判断数据合法性
            musicList = parseSheets(serviceinfoPath);

            // 加载基地音乐id列表与内容id列表
            initListData();

            // 加入数据库中
            addMusicList(musicList, fileRootPath);
        }
        catch (Exception e)
        {
            throw new BOException(e, DataSyncConstants.EXCEPTION_INNER_ERR);
        }
    }

    /**
     * 对xls文件中的数据进行入库操作
     * 
     * @param musicList
     * @param serviceinfoPath 本地根目录
     */
    private void addMusicList(List musicList, String serviceinfoPath)
    {
        // 遍历音乐专辑列表
        for (Iterator iter = musicList.iterator(); iter.hasNext();)
        {
            MusicDescVO temp = ( MusicDescVO ) iter.next();

            // 库中是否存在
            if (isExistMusic(temp))
            {
                // 上传图片文件
                try
                {
                    temp.setImageName(imageToFTP(temp, serviceinfoPath));
                }
                catch (Exception e1)
                {
                    logger.error("上传音乐专辑图片至文件服务器时，发生异常情况! musicid="
                                 + temp.getMusicId());

                    // 加入失败
                    addStatisticCount(DataSyncConstants.FAILURE);
                    
                    continue;
                }

                // 加入数据库中
                try
                {
                    MusicDescDAO.getInstance().insertMusicDesc(temp);
                    
                    // 加入成功
                    addStatisticCount(DataSyncConstants.SUCCESS_ADD);
                }
                catch (DAOException e)
                {
                    logger.error("存入音乐专辑到数据库中时，数据库发生异常! musicid="
                                 + temp.getMusicId());
                    
                    // 加入失败
                    addStatisticCount(DataSyncConstants.FAILURE);

                    continue;
                }
            }
            else
            {
                logger.error("存入音乐专辑数据时发现数据与库中数据不能对应! musicid="
                             + temp.getMusicId());
                
                addStatisticCount(DataSyncConstants.FAILURE);
            }
        }
    }

    /**
     * 用于上传图片文件至文件服务器上
     * 
     * @param vo 音乐专辑对象
     * @param serviceinfoPath 根目录
     * @return
     * @throws BOException 
     */
    private String imageToFTP(MusicDescVO vo, String serviceinfoPath) throws BOException
    {
        FTPClient ftp = null;

        try
        {
            ftp = getFTPClient();

            String image = serviceinfoPath + File.separator + vo.getImageName();

            // 判断文件是符合标准
            fileCheck(image, vo);

            ftp.put(image, vo.getImageName(), false);

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
            
            logger.error("上传音乐专辑图片至文件服务器时，发生异常! musicid="
                         + vo.getMusicId());

            throw new BOException("上传音乐专辑图片至文件服务器时，发生异常! musicid="
                         + vo.getMusicId());
        }

        return toftpUrl + "/" + vo.getImageName();
    }

    /**
     * 用于校验图片文件属性
     * @param image
     * @param vo
     * @throws BOException
     */
    private void fileCheck(String image , MusicDescVO vo) throws BOException
    {
        File file = new File(image);

        // 文件是否存在
        if (!file.exists())
        {
            logger.error("上传音乐专辑图片至文件服务器时，图片文件不存在! musicid="
                         + vo.getMusicId());

            throw new BOException("上传音乐专辑图片至文件服务器时，图片文件不存在! musicid="
                         + vo.getMusicId());
        }
        
        // 文件大小判断
        if(file.length() > fileSize)
        {
            logger.error("上传音乐专辑图片至文件服务器时，图片文件大于约定大小! musicid="
                         + vo.getMusicId() + " 图片文件大小=" + file.length());

            throw new BOException("上传音乐专辑图片至文件服务器时，图片文件大于约定大小! musicid="
                         + vo.getMusicId());
        }
        
        BufferedImage sourceImage = null;
        
        try
        {
            sourceImage = ImageIO.read(file);
        }
        catch (IOException e)
        {
            logger.error("上传音乐专辑图片至文件服务器时，创建图片文件出错! musicid="
                         + vo.getMusicId());

            throw new BOException("上传音乐专辑图片至文件服务器时，创建图片文件出错! musicid="
                                  + vo.getMusicId());
        }
        
        // 得到文件尺寸
        String tempCss = sourceImage.getWidth() + "*" + sourceImage.getHeight();
        
        // 校验文件尺寸
        if(!fileCss.equals(tempCss))
        {
            logger.error("上传音乐专辑图片至文件服务器时，文件尺寸不合格! musicid="
                         + vo.getMusicId() + " ,当前文件尺寸为" + tempCss);

            throw new BOException("上传音乐专辑图片至文件服务器时，文件尺寸不合格! musicid="
                                  + vo.getMusicId() + " ,当前文件尺寸为" + tempCss);
        }
        
    }
    
    
    /**
     * 得到上传图片应用的ftp对象
     * @return
     * @throws IOException
     * @throws FTPException
     */
    private FTPClient getFTPClient() throws IOException, FTPException
    {
        FTPClient ftp = new FTPClient(toftpIP, toftpPort);
        // 初始化ftp连接模式（FTPConnectMode.PASV或者FTPConnectMode.ACTIVE）
        ftp.setConnectMode(FTPConnectMode.PASV);

        // 使用给定的用户名、密码登陆ftp
        if (logger.isDebugEnabled())
        {
            logger.debug("login to FTPServer...");
        }

        ftp.login(toftpUser, toftpPassword);
        // 设置文件传输类型（FTPTransferType.ASCII或者FTPTransferType.BINARY）
        ftp.setType(FTPTransferType.BINARY);

        if (!"".equals(toftpDir))// 进入当前资源服务器目录。
        {
            ftp.chdir(toftpDir);
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("login FTPServer successfully,transfer type is binary");
        }

        return ftp;
    }

    /**
     * 判断当前专辑信息在库中是否存在
     * 
     * @param vo 专辑信息
     * @return
     */
    private boolean isExistMusic(MusicDescVO vo)
    {
        // 内容id存在。基地音乐id存在
        if (contentList.contains(vo.getContentId())
            && mucisIdList.contains(vo.getMusicId()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * 加载基地音乐id列表与内容id列表
     * 
     * @throws BOException
     * @throws DAOException
     */
    private void initListData() throws BOException
    {
        // 加载基地音乐id列表与内容id列表
        try
        {
            contentList = MusicDescDAO.getInstance().getAllContentId();
            mucisIdList = MusicDescDAO.getInstance().getAllBaseMusicId();
        }
        catch (DAOException e)
        {
            logger.error("加载基地音乐id列表与内容id列表时，数据库异常!");
            throw new BOException(e, DataSyncConstants.EXCEPTION_INNER_ERR);
        }

    }

    /**
     * 解析xls文件过程
     * 
     * @param fileName 文件名
     * @return 合法的音乐转辑列表
     * @throws BOException
     */
    private List parseSheets(String fileName) throws BOException
    {
        Workbook book = null;
        Sheet[] sheets = null;
        List musicList = new ArrayList();

        try
        {
            // 用于解析xls文件
            book = Workbook.getWorkbook(new File(fileName));

            // 读取游戏列表文件。
            logger.info("开始处理音乐专辑信息文件：" + fileName);

            // 得到工作表
            sheets = book.getSheets();

            // 默认数据存放地工作表
            Sheet work = sheets[0];

            // 得到行数
            int rows = work.getRows();

            // 得到列数
            int columns = work.getColumns();

            // 如果少于8列，异常
            if (columns < 8)
            {
                throw new BOException("文件中列数异常",
                                      DataSyncConstants.EXCEPTION_INNER_ERR);
            }

            for (int i = 0; i < rows; i++)
            {
                if (logger.isDebugEnabled())
                {
                    logger.debug("开始解析第" + (i + 1) + "行数据。");
                }

                // 组装每一行数据为对象
                MusicDescVO musicDescVO = formatToMusicDescVO(work, i);

                // 检查对象属性的合法性
                int check = checkerDate(musicDescVO);

                // 如果数据不合当。忽略
                if (check == DataSyncConstants.CHECK_FAILED)
                {
                    logger.error("第" + (i + 1) + "行数据检查失败，忽略该数据。");
                    this.addStatisticCount(check);
                    continue;
                }

                // 存入音乐专辑列表
                musicList.add(musicDescVO);
            }
        }
        catch (Exception e)
        {
            throw new BOException(e, DataSyncConstants.EXCEPTION_INNER_ERR);
        }
        finally
        {
            book.close();
        }

        return musicList;
    }

    /**
     * 用于根据xls文件中数据组装音乐专辑信息
     * 
     * @param work 工作区
     * @param i 第几行
     * @return
     */
    private MusicDescVO formatToMusicDescVO(Sheet work, int i)
    {
        MusicDescVO temp = new MusicDescVO();

        temp.setMusicId(work.getCell(0, i).getContents().trim());
        temp.setSongName(work.getCell(1, i).getContents().trim());
        temp.setSinger(work.getCell(2, i).getContents().trim());
        temp.setSpecialName(work.getCell(3, i).getContents().trim());
        temp.setSpecialDesc(work.getCell(4, i).getContents().trim());
        temp.setImageName(work.getCell(5, i).getContents().trim());
        temp.setContentId(work.getCell(6, i).getContents().trim());
        temp.setContentName(work.getCell(7, i).getContents().trim());

        return temp;
    }

    /**
     * 获取音乐列表文件。
     * 
     * @param fileRootPath
     * @return
     * @throws BOException
     */
    private String getServiceInfoFileName(String fileRootPath)
                    throws BOException
    {
        File dir = new File(fileRootPath);
        String fileNames[] = dir.list();

        // 在当前目录下找到音乐列表文件
        for (int i = 0; i < fileNames.length; i++)
        {
            if (fileNames[i].matches("A8_jdyy_zj_\\d{8}\\.xls"))
            {
                return fileRootPath + File.separator + fileNames[i];
            }
        }
        throw new BOException("找不到音乐列表文件异常！");
    }

    /**
     * 判断该字符是否超出maxLength的长度。
     * 
     * @param field 要验证的字段内容
     * @param maxLength 允许的最大长度
     * @param must 是否是必填字段，如果为true，需要验证该字段是否为空（""）
     * @return
     */
    private boolean checkFieldLength(String field, int maxLength, boolean must)
    {
        if (field == null)
        {
            return false;
        }
        if (StringTool.lengthOfHZ(field) > maxLength)
        {
            return false;
        }
        if (must)
        {
            if (field.equals(""))
            {
                return false;
            }
        }
        return true;

    }

    /**
     * 用于效验对象属性的合法性
     * 
     * @param vo
     * @return
     */
    private int checkerDate(MusicDescVO vo)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("开始验证音乐字段格式，musicId=" + vo.getMusicId());
        }

        // musicId
        if (!this.checkFieldLength(vo.getMusicId(), 30, true))
        {
            logger.error("musicId=" + vo.getMusicId()
                         + ",musicId验证错误，该字段是必填字段，且不超过30个字符");
            return DataSyncConstants.CHECK_FAILED;
        }

        // songName
        if (!this.checkFieldLength(vo.getSongName(), 200, true))
        {
            logger.error("musicId=" + vo.getMusicId()
                         + ",songName验证错误，该字段是必填字段，且长度不超过200个字符错误！songName="
                         + vo.getSongName());
            return DataSyncConstants.CHECK_FAILED;
        }

        // singer
        if (!this.checkFieldLength(vo.getSinger(), 100, true))
        {
            logger.error("musicId=" + vo.getMusicId()
                         + ",singer验证错误，该字段是必填字段，且长度不超过100个字符错误！singer="
                         + vo.getSinger());
            return DataSyncConstants.CHECK_FAILED;
        }

        // specialName
        if (!this.checkFieldLength(vo.getSpecialName(), 100, true))
        {
            logger.error("musicId="
                         + vo.getMusicId()
                         + ",specialName验证错误，该字段是必填字段，其长度不超过100个字符错误！specialName="
                         + vo.getSpecialName());
            return DataSyncConstants.CHECK_FAILED;
        }

        // specialDesc
        if (!this.checkFieldLength(vo.getSpecialDesc(), 400, true))
        {
            logger.error("musicId="
                         + vo.getMusicId()
                         + ",specialDesc验证错误，该字段是必填字段，其长度不超过400个字符错误！specialDesc="
                         + vo.getSpecialDesc());
            return DataSyncConstants.CHECK_FAILED;
        }

        // imageName
        if (!this.checkFieldLength(vo.getImageName(), 200, true))
        {
            logger.error("musicId=" + vo.getMusicId()
                         + ",imageName验证错误，该字段是必填字段，其长度不超过200个字符错误！imageName="
                         + vo.getImageName());
            return DataSyncConstants.CHECK_FAILED;
        }

        // contentId
        if (!this.checkFieldLength(vo.getContentId(), 30, true))
        {
            logger.error("musicId=" + vo.getMusicId()
                         + ",contentId验证错误，该字段是必填字段，其长度不超过30个字符错误！contentId="
                         + vo.getContentId());
            return DataSyncConstants.CHECK_FAILED;
        }

        // contentName
        if (!this.checkFieldLength(vo.getContentName(), 300, true))
        {
            logger.error("musicId="
                         + vo.getMusicId()
                         + ",contentName验证错误，该字段是必填字段，其长度不超过300个字符错误！contentName="
                         + vo.getContentName());
            return DataSyncConstants.CHECK_FAILED;
        }
        return DataSyncConstants.CHECK_SUCCESSFUL;
    }

}
