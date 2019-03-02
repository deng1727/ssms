/*
 * �ļ�����DataSyncTaskForA8MusicDesc.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
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

        // ftp��ȡ�ļ�
        filenameList = ftp.process();

        if (filenameList.length == 0)
        {
            throw new BOException("û���ҵ�����������ļ��쳣",
                                  DataSyncConstants.EXCEPTION_FILE_NOT_EXISTED);
        }

        try
        {
            // ���Ƚ�ѹ���ļ�����ǰĿ¼��
            String fileRootPath = DataSyncTools.unZip(filenameList[0]);

            // �õ�����ר���б��ļ�
            String serviceinfoPath = getServiceInfoFileName(fileRootPath);

            // ����xls�ļ��ж����ݺϷ���
            musicList = parseSheets(serviceinfoPath);

            // ���ػ�������id�б�������id�б�
            initListData();

            // �������ݿ���
            addMusicList(musicList, fileRootPath);
        }
        catch (Exception e)
        {
            throw new BOException(e, DataSyncConstants.EXCEPTION_INNER_ERR);
        }
    }

    /**
     * ��xls�ļ��е����ݽ���������
     * 
     * @param musicList
     * @param serviceinfoPath ���ظ�Ŀ¼
     */
    private void addMusicList(List musicList, String serviceinfoPath)
    {
        // ��������ר���б�
        for (Iterator iter = musicList.iterator(); iter.hasNext();)
        {
            MusicDescVO temp = ( MusicDescVO ) iter.next();

            // �����Ƿ����
            if (isExistMusic(temp))
            {
                // �ϴ�ͼƬ�ļ�
                try
                {
                    temp.setImageName(imageToFTP(temp, serviceinfoPath));
                }
                catch (Exception e1)
                {
                    logger.error("�ϴ�����ר��ͼƬ���ļ�������ʱ�������쳣���! musicid="
                                 + temp.getMusicId());

                    // ����ʧ��
                    addStatisticCount(DataSyncConstants.FAILURE);
                    
                    continue;
                }

                // �������ݿ���
                try
                {
                    MusicDescDAO.getInstance().insertMusicDesc(temp);
                    
                    // ����ɹ�
                    addStatisticCount(DataSyncConstants.SUCCESS_ADD);
                }
                catch (DAOException e)
                {
                    logger.error("��������ר�������ݿ���ʱ�����ݿⷢ���쳣! musicid="
                                 + temp.getMusicId());
                    
                    // ����ʧ��
                    addStatisticCount(DataSyncConstants.FAILURE);

                    continue;
                }
            }
            else
            {
                logger.error("��������ר������ʱ����������������ݲ��ܶ�Ӧ! musicid="
                             + temp.getMusicId());
                
                addStatisticCount(DataSyncConstants.FAILURE);
            }
        }
    }

    /**
     * �����ϴ�ͼƬ�ļ����ļ���������
     * 
     * @param vo ����ר������
     * @param serviceinfoPath ��Ŀ¼
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

            // �ж��ļ��Ƿ��ϱ�׼
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
            
            logger.error("�ϴ�����ר��ͼƬ���ļ�������ʱ�������쳣! musicid="
                         + vo.getMusicId());

            throw new BOException("�ϴ�����ר��ͼƬ���ļ�������ʱ�������쳣! musicid="
                         + vo.getMusicId());
        }

        return toftpUrl + "/" + vo.getImageName();
    }

    /**
     * ����У��ͼƬ�ļ�����
     * @param image
     * @param vo
     * @throws BOException
     */
    private void fileCheck(String image , MusicDescVO vo) throws BOException
    {
        File file = new File(image);

        // �ļ��Ƿ����
        if (!file.exists())
        {
            logger.error("�ϴ�����ר��ͼƬ���ļ�������ʱ��ͼƬ�ļ�������! musicid="
                         + vo.getMusicId());

            throw new BOException("�ϴ�����ר��ͼƬ���ļ�������ʱ��ͼƬ�ļ�������! musicid="
                         + vo.getMusicId());
        }
        
        // �ļ���С�ж�
        if(file.length() > fileSize)
        {
            logger.error("�ϴ�����ר��ͼƬ���ļ�������ʱ��ͼƬ�ļ�����Լ����С! musicid="
                         + vo.getMusicId() + " ͼƬ�ļ���С=" + file.length());

            throw new BOException("�ϴ�����ר��ͼƬ���ļ�������ʱ��ͼƬ�ļ�����Լ����С! musicid="
                         + vo.getMusicId());
        }
        
        BufferedImage sourceImage = null;
        
        try
        {
            sourceImage = ImageIO.read(file);
        }
        catch (IOException e)
        {
            logger.error("�ϴ�����ר��ͼƬ���ļ�������ʱ������ͼƬ�ļ�����! musicid="
                         + vo.getMusicId());

            throw new BOException("�ϴ�����ר��ͼƬ���ļ�������ʱ������ͼƬ�ļ�����! musicid="
                                  + vo.getMusicId());
        }
        
        // �õ��ļ��ߴ�
        String tempCss = sourceImage.getWidth() + "*" + sourceImage.getHeight();
        
        // У���ļ��ߴ�
        if(!fileCss.equals(tempCss))
        {
            logger.error("�ϴ�����ר��ͼƬ���ļ�������ʱ���ļ��ߴ粻�ϸ�! musicid="
                         + vo.getMusicId() + " ,��ǰ�ļ��ߴ�Ϊ" + tempCss);

            throw new BOException("�ϴ�����ר��ͼƬ���ļ�������ʱ���ļ��ߴ粻�ϸ�! musicid="
                                  + vo.getMusicId() + " ,��ǰ�ļ��ߴ�Ϊ" + tempCss);
        }
        
    }
    
    
    /**
     * �õ��ϴ�ͼƬӦ�õ�ftp����
     * @return
     * @throws IOException
     * @throws FTPException
     */
    private FTPClient getFTPClient() throws IOException, FTPException
    {
        FTPClient ftp = new FTPClient(toftpIP, toftpPort);
        // ��ʼ��ftp����ģʽ��FTPConnectMode.PASV����FTPConnectMode.ACTIVE��
        ftp.setConnectMode(FTPConnectMode.PASV);

        // ʹ�ø������û����������½ftp
        if (logger.isDebugEnabled())
        {
            logger.debug("login to FTPServer...");
        }

        ftp.login(toftpUser, toftpPassword);
        // �����ļ��������ͣ�FTPTransferType.ASCII����FTPTransferType.BINARY��
        ftp.setType(FTPTransferType.BINARY);

        if (!"".equals(toftpDir))// ���뵱ǰ��Դ������Ŀ¼��
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
     * �жϵ�ǰר����Ϣ�ڿ����Ƿ����
     * 
     * @param vo ר����Ϣ
     * @return
     */
    private boolean isExistMusic(MusicDescVO vo)
    {
        // ����id���ڡ���������id����
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
     * ���ػ�������id�б�������id�б�
     * 
     * @throws BOException
     * @throws DAOException
     */
    private void initListData() throws BOException
    {
        // ���ػ�������id�б�������id�б�
        try
        {
            contentList = MusicDescDAO.getInstance().getAllContentId();
            mucisIdList = MusicDescDAO.getInstance().getAllBaseMusicId();
        }
        catch (DAOException e)
        {
            logger.error("���ػ�������id�б�������id�б�ʱ�����ݿ��쳣!");
            throw new BOException(e, DataSyncConstants.EXCEPTION_INNER_ERR);
        }

    }

    /**
     * ����xls�ļ�����
     * 
     * @param fileName �ļ���
     * @return �Ϸ�������ת���б�
     * @throws BOException
     */
    private List parseSheets(String fileName) throws BOException
    {
        Workbook book = null;
        Sheet[] sheets = null;
        List musicList = new ArrayList();

        try
        {
            // ���ڽ���xls�ļ�
            book = Workbook.getWorkbook(new File(fileName));

            // ��ȡ��Ϸ�б��ļ���
            logger.info("��ʼ��������ר����Ϣ�ļ���" + fileName);

            // �õ�������
            sheets = book.getSheets();

            // Ĭ�����ݴ�ŵع�����
            Sheet work = sheets[0];

            // �õ�����
            int rows = work.getRows();

            // �õ�����
            int columns = work.getColumns();

            // �������8�У��쳣
            if (columns < 8)
            {
                throw new BOException("�ļ��������쳣",
                                      DataSyncConstants.EXCEPTION_INNER_ERR);
            }

            for (int i = 0; i < rows; i++)
            {
                if (logger.isDebugEnabled())
                {
                    logger.debug("��ʼ������" + (i + 1) + "�����ݡ�");
                }

                // ��װÿһ������Ϊ����
                MusicDescVO musicDescVO = formatToMusicDescVO(work, i);

                // ���������ԵĺϷ���
                int check = checkerDate(musicDescVO);

                // ������ݲ��ϵ�������
                if (check == DataSyncConstants.CHECK_FAILED)
                {
                    logger.error("��" + (i + 1) + "�����ݼ��ʧ�ܣ����Ը����ݡ�");
                    this.addStatisticCount(check);
                    continue;
                }

                // ��������ר���б�
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
     * ���ڸ���xls�ļ���������װ����ר����Ϣ
     * 
     * @param work ������
     * @param i �ڼ���
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
     * ��ȡ�����б��ļ���
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

        // �ڵ�ǰĿ¼���ҵ������б��ļ�
        for (int i = 0; i < fileNames.length; i++)
        {
            if (fileNames[i].matches("A8_jdyy_zj_\\d{8}\\.xls"))
            {
                return fileRootPath + File.separator + fileNames[i];
            }
        }
        throw new BOException("�Ҳ��������б��ļ��쳣��");
    }

    /**
     * �жϸ��ַ��Ƿ񳬳�maxLength�ĳ��ȡ�
     * 
     * @param field Ҫ��֤���ֶ�����
     * @param maxLength �������󳤶�
     * @param must �Ƿ��Ǳ����ֶΣ����Ϊtrue����Ҫ��֤���ֶ��Ƿ�Ϊ�գ�""��
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
     * ����Ч��������ԵĺϷ���
     * 
     * @param vo
     * @return
     */
    private int checkerDate(MusicDescVO vo)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("��ʼ��֤�����ֶθ�ʽ��musicId=" + vo.getMusicId());
        }

        // musicId
        if (!this.checkFieldLength(vo.getMusicId(), 30, true))
        {
            logger.error("musicId=" + vo.getMusicId()
                         + ",musicId��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����30���ַ�");
            return DataSyncConstants.CHECK_FAILED;
        }

        // songName
        if (!this.checkFieldLength(vo.getSongName(), 200, true))
        {
            logger.error("musicId=" + vo.getMusicId()
                         + ",songName��֤���󣬸��ֶ��Ǳ����ֶΣ��ҳ��Ȳ�����200���ַ�����songName="
                         + vo.getSongName());
            return DataSyncConstants.CHECK_FAILED;
        }

        // singer
        if (!this.checkFieldLength(vo.getSinger(), 100, true))
        {
            logger.error("musicId=" + vo.getMusicId()
                         + ",singer��֤���󣬸��ֶ��Ǳ����ֶΣ��ҳ��Ȳ�����100���ַ�����singer="
                         + vo.getSinger());
            return DataSyncConstants.CHECK_FAILED;
        }

        // specialName
        if (!this.checkFieldLength(vo.getSpecialName(), 100, true))
        {
            logger.error("musicId="
                         + vo.getMusicId()
                         + ",specialName��֤���󣬸��ֶ��Ǳ����ֶΣ��䳤�Ȳ�����100���ַ�����specialName="
                         + vo.getSpecialName());
            return DataSyncConstants.CHECK_FAILED;
        }

        // specialDesc
        if (!this.checkFieldLength(vo.getSpecialDesc(), 400, true))
        {
            logger.error("musicId="
                         + vo.getMusicId()
                         + ",specialDesc��֤���󣬸��ֶ��Ǳ����ֶΣ��䳤�Ȳ�����400���ַ�����specialDesc="
                         + vo.getSpecialDesc());
            return DataSyncConstants.CHECK_FAILED;
        }

        // imageName
        if (!this.checkFieldLength(vo.getImageName(), 200, true))
        {
            logger.error("musicId=" + vo.getMusicId()
                         + ",imageName��֤���󣬸��ֶ��Ǳ����ֶΣ��䳤�Ȳ�����200���ַ�����imageName="
                         + vo.getImageName());
            return DataSyncConstants.CHECK_FAILED;
        }

        // contentId
        if (!this.checkFieldLength(vo.getContentId(), 30, true))
        {
            logger.error("musicId=" + vo.getMusicId()
                         + ",contentId��֤���󣬸��ֶ��Ǳ����ֶΣ��䳤�Ȳ�����30���ַ�����contentId="
                         + vo.getContentId());
            return DataSyncConstants.CHECK_FAILED;
        }

        // contentName
        if (!this.checkFieldLength(vo.getContentName(), 300, true))
        {
            logger.error("musicId="
                         + vo.getMusicId()
                         + ",contentName��֤���󣬸��ֶ��Ǳ����ֶΣ��䳤�Ȳ�����300���ַ�����contentName="
                         + vo.getContentName());
            return DataSyncConstants.CHECK_FAILED;
        }
        return DataSyncConstants.CHECK_SUCCESSFUL;
    }

}
