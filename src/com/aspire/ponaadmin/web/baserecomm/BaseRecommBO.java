
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
 * ������������
 * 
 * @author wml
 * 
 */
public class BaseRecommBO
{

    /**
     * ��־����
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
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static BaseRecommBO getInstance()
    {
        return bo;
    }

    /**
     * ���ڲ�ѯ������Ϸ�����Ƽ���Ϣ
     * 
     * @param page
     * @param gameName ��Ϸ��
     * @param gameDesc ��Ϸ���
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

            throw new BOException("��ѯ������Ϸ�����Ƽ���Ϣʱ�������ݿ��쳣��");
        }
    }

    /**
     * ���ڲ�ѯ�������ֱ����Ƽ���Ϣ
     * 
     * @param page
     * @param gameName ��Ϸ��
     * @param gameDesc ��Ϸ���
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

            throw new BOException("��ѯ�������ֱ����Ƽ���Ϣʱ�������ݿ��쳣��");
        }
    }

    /**
     * ���ڲ�ѯ����ͼ������Ƽ���Ϣ
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

            throw new BOException("��ѯ����ͼ������Ƽ���Ϣʱ�������ݿ��쳣��");
        }
    }

    /**
     * ���ڲ�ѯ������Ƶ�����Ƽ���Ϣ
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

            throw new BOException("��ѯ������Ƶ�����Ƽ���Ϣʱ�������ݿ��쳣��");
        }
    }

    /**
     * ����������Ƶ�����Ƽ���Ϣ
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

            throw new BOException("����������Ƶ�����Ƽ���Ϣʱ�������ݿ��쳣��");
        }
    }

    /**
     * ���ڲ�ѯ������ʱ����ָ�������Ƽ���Ϣ
     * 
     * @param page
     * @param dataType ������������
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

            throw new BOException("��ѯ������ʱ����ָ�������Ƽ���Ϣʱ�������ݿ��쳣��");
        }
    }

    /**
     * �ѻ�����Ϸ���ݵ������ļ�
     * 
     * @param dataType ������������
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

        // �õ������ļ���������
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

            throw new BOException("���������Ƽ��ļ�ʱ�������ݿ��쳣��");
        }

        // �����ļ�
        try
        {
            toFileByData(dataList, dataType);
        }
        catch (Exception e)
        {
            logger.error(e);

            throw new BOException("���������Ƽ��ļ�ʱ�����쳣��");
        }
    }

    /**
     * ���ݲ�ͬ���͵��������ɲ�ͬ�Ļ��������ļ�
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
            // ѭ������
            for (int i = 0; i < dataList.size(); i++)
            {
                // �����¼�����Ա�30000���������ļ�
                if (i % BaseRecommConfig.DATANUM == 0)
                {
                    // �������Ϊ�ա���
                    if (bw != null)
                    {
                        bw.close();
                        fileSize.add(String.valueOf(n));
                        n = 0;
                    }

                    // �õ��ļ�
                    file = getFileName(dataType, i);

                    // �����ļ�����Ϣ�������ϴ�FTP������
                    absoluteFileName.add(file.getAbsoluteFile());
                    fileName.add(file.getName());

                    // �����ļ��н���
                    bw = new BufferedWriter(new FileWriter(file, true), 2048);
                }

                bw.write(String.valueOf(dataList.get(i)));

                bw.write("\r\n");

                n++;
            }

            // �����һ���ļ�����
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
                    throw new BOException("�����Ƽ������ļ�ʱ�������쳣! fileName="
                                          + file.getName());
                }
            }

            throw new BOException("�����Ƽ������ļ�ʱ�������쳣! fileName=" + file.getName());

        }

        // ���ɶ����ļ�
        absoluteFileName.add(saveRecmdFile(fileName, fileSize, dataType));

        // �ϴ���FTP������
        upLoadFileToFTP(fileName, absoluteFileName);
    }

    /**
     * ����д������ı���
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
                    throw new BOException("�����Ƽ������ļ�ʱ�������쳣! fileName="
                                          + file.getName());
                }
            }
            
            throw new BOException("�����Ƽ������ļ�ʱ�������쳣! fileName=" + file.getName());
        }
        
        // �ļ��������ϴ��б���
        fileName.add(file.getName());
        
        return file.getAbsolutePath();
    }

    /**
     * �����ϴ��ļ���ָ����������
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

            logger.error("�ϴ����������ļ����ļ�������ʱ�������쳣! fileName=" + file);

            throw new BOException("�ϴ����������ļ����ļ�������ʱ�������쳣! fileName=" + file);
        }
    }

    /**
     * �������ĸ����õ������ļ����ļ���
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

        // �õ�Ҫд���ļ�
        File file = new File(getFileName(dataType, tempNum));

        // ������ڵ���������ļ���ɾ��
        if (file.exists())
        {
            file.delete();
        }

        return file;
    }

    /**
     * �������ɶ�Ӧ�������͵����� XXX_yyyymmdd.txt
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
     * ���ɶ����ļ�
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

        // �õ�Ҫд���ļ�
        File file = new File(fileName.toString());

        // ������ڵ���������ļ���ɾ��
        if (file.exists())
        {
            file.delete();
        }

        return file;
    }

    /**
     * �����������Ͷ�Ӧ������
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
     * �õ��ϴ�ͼƬӦ�õ�ftp����
     * 
     * @return
     * @throws IOException
     * @throws FTPException
     */
    private FTPClient getFTPClient() throws IOException, FTPException
    {
        FTPClient ftp = new FTPClient(BaseRecommConfig.FTPIP,
                                      BaseRecommConfig.FTPPORT);
        // ��ʼ��ftp����ģʽ��FTPConnectMode.PASV����FTPConnectMode.ACTIVE��
        ftp.setConnectMode(FTPConnectMode.PASV);

        // ʹ�ø������û����������½ftp
        if (logger.isDebugEnabled())
        {
            logger.debug("login to FTPServer...");
        }

        ftp.login(BaseRecommConfig.FTPUSER, BaseRecommConfig.FTPPASS);
        // �����ļ��������ͣ�FTPTransferType.ASCII����FTPTransferType.BINARY��
        ftp.setType(FTPTransferType.BINARY);

        if (!"".equals(BaseRecommConfig.FTPDIR))// ���뵱ǰ��Դ������Ŀ¼��
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
     * ���ڰ����ݴ�����ʱ����
     * 
     * @param baseIds ����id����
     * @param baseType ��������
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

        // �ж��Ƿ�������ݿ���
        for (int i = 0; i < baseIds.length; i++)
        {
            try
            {
                // ������ڣ��׳��쳣����
                if (BaseRecommDAO.getInstance().isHasBaseDate(baseIds[i],
                                                              baseType))
                {
                    throw new BOException("�������Ѵ��ڻ�����ʱ���У� baseId=" + baseIds[i]);
                }
            }
            catch (DAOException e)
            {
                logger.error(e);
                throw new BOException("���ݴ�����ʱ����ʱ�������ݿ��쳣��");
            }
        }

        // �������ݿ���
        try
        {
            BaseRecommDAO.getInstance().addBaseData(baseIds, baseType);
        }
        catch (DAOException e)
        {
            logger.error(e);

            throw new BOException("���ݴ�����ʱ����ʱ�������ݿ��쳣��");
        }
    }

    /**
     * ���ڰ����ݴ���ʱ����ɾ��
     * 
     * @param baseIds ����id����
     * @param baseType ��������
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

        // �����ݿ���ɾ��
        try
        {
            BaseRecommDAO.getInstance().delBaseData(baseIds, baseType);
        }
        catch (DAOException e)
        {
            logger.error(e);

            throw new BOException("���ݴ���ʱ����ɾ��ʱ�������ݿ��쳣��");
        }
    }
}