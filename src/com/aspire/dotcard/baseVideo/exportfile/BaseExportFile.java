
package com.aspire.dotcard.baseVideo.exportfile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.ftpfile.SFTPServer;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.threadtask.ReflectedTask;
import com.aspire.common.threadtask.TaskRunner;
import com.aspire.dotcard.baseVideo.bo.BaseVideoFileBO;
import com.aspire.dotcard.baseVideo.conf.BaseVideoConfig;
import com.aspire.dotcard.baseVideo.vo.VerfDataVO;
import com.aspire.ponaadmin.web.util.PublicUtil;
import com.aspire.ponaadmin.web.util.StringTool;
import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPTransferType;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;

/**
 * ʵ�ֵ�����������Ϊ����
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2012
 * </p>
 * 
 * @author wangminlong
 * @version
 */
public abstract class BaseExportFile implements BaseFileAbility
{
    /**
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(BaseExportFile.class);

    /**
     * �������У������
     */
    protected List verfDataList = new ArrayList();
    
    /**
     * ����MAP
     */
    protected static Map keyMap = Collections.synchronizedMap(new HashMap());

    /**
     * �ʼ�������Ϣ
     */
    protected String mailTitle = "";

    /**
     * FTP����
     */
    protected FTPClient ftp = null;

    /**
     * ָ��ftp�ϵ��ļ����·��
     */
    protected String ftpDir = "";

    /**
     * �ļ���ŵı���·��
     */
    protected String localDir = "";

    /**
     * ȡ�ļ������ڣ�0��Ϊ���죬1��Ϊǰһ�죬2��Ϊǰ����
     */
    protected int getDateNum = 0;
    
    /**
     * ȡ�ļ���ʱ�䣬0��Ϊ��Сʱ��1��ΪǰСʱ��2��Ϊǰ��Сʱ
     */
    protected int getTimeNum = 0;
    
    protected boolean isByHour=false;

    /**
     * ������ļ���
     */
    protected String fileName = "";

    /**
     * �Ƿ���У���ļ�
     */
    protected boolean hasVerf = true;

    /**
     * �Ƿ�Ҫȫ�����
     */
    protected boolean isDelTable = true;

    /**
     * �ļ���������
     */
    protected String fileEncoding = "GBK";

    /**
     * У���ļ���
     */
    protected String verfFileName = "";

    /**
     * У����ֵ�����õ�������ʽ
     */
    private static String INTEGER = "-?[0-9]{0,d}";

    /**
     * �����ļ���ʽ�ж���ļ����
     */
    protected String dataSpacers = "";

    /**
     * �����ļ���ʽ�ж���ļ����
     */
    protected String verDataSpacers = "";

    /**
     * ͬ����ʼʱ��
     */
    protected Date startDate = null;

    /**
     * У���ļ������ʼ���Ϣ
     */
    protected StringBuffer verfMailText = new StringBuffer();

    /**
     * ͬ���ļ�����Ϣ
     */
    protected StringBuffer syncMailText = new StringBuffer();

    /**
     * �ʼ���Ϣ
     */
    protected StringBuffer mailText = new StringBuffer();

    /**
     * �ܸ���
     */
    protected int countNum = 0;

    /**
     * �ɹ�����ĸ���
     */
    protected int successAdd = 0;

    /**
     * ʧ�ܴ���ĸ���
     */
    protected int failureProcess = 0;

    /**
     * У��ʧ�ܵĸ���
     */
    protected int failureCheck = 0;

    /**
     * д��ʧ���ʼ�����
     */
    protected int failMailTextNum = 0;

    /**
     * �Ƿ�д��ʧ���ʼ�
     */
    protected boolean isMailText = true;

    /**
     * ִ����
     */
    private TaskRunner dataSynTaskRunner;
    
    /**
     * �Ƿ�ִ�����������ݵ���
     */
    protected boolean isImputDate = false;

    /**
     * �������׼����������
     */
    public void init()
    {
        ftpDir = BaseVideoConfig.FTPPAHT;
        localDir = BaseVideoConfig.LOCALDIR;
        getDateNum = BaseVideoConfig.GET_DATE_NUM;
        getTimeNum = BaseVideoConfig.GET_TIME_NUM;
        dataSpacers = getDataSpacers();
        verDataSpacers = BaseVideoConfig.verDataSpacers;
    }

    /**
     * ִ����
     */
    public String execution(boolean isSendMail)
    {
        // ׼������
        init();

        // ��¼��ʼʱ��
        startDate = new Date();

        // ����У�������ļ�
        if (this.hasVerf)
        {
            try
            {
                exportVerfFile();
            }
            catch (BOException e)
            {
                logger.error(e);
            }
        }

        // �������������ļ�
        try
        {
            exportDataFile();
        }
        catch (BOException e)
        {
            logger.error(e);
        }

        // ��װ��������ʼ�
        getMailText();

        // �����ͷŶ��м���
        destroy();

        if (isSendMail)
        {
            // ִ�з��ʼ��Ĺ���
            BaseVideoFileBO.getInstance().sendResultMail(mailTitle, mailText);
            return "";
        }
        else
        {
            return mailText.toString();
        }
    }

    /**
     * ������װ����
     * 
     */
    public void getMailText()
    {
        if (mailText.length() <= 0)
        {
            mailText.append("<b>ͬ��").append(mailTitle).append("���</b>�� <br>");
            mailText.append("ͬ���ļ���ʼʱ�䣺")
                    .append(PublicUtil.getDateString(startDate,
                                                     "yyyy-MM-dd HH:mm:ss"))
                    .append("������ʱ�䣺")
                    .append(PublicUtil.getDateString(new Date(),
                                                     "yyyy-MM-dd HH:mm:ss"));
            mailText.append("<br><br>");

            mailText.append("У���ļ����ݵ�������� <br>");
            mailText.append(verfMailText).append("<br>");
            for (int i = 0; i < verfDataList.size(); i++)
            {
                mailText.append((( VerfDataVO ) verfDataList.get(i)).toMailText());
            }
            mailText.append("<br><br>");

            mailText.append("У���ļ����ݵ�������� <br>");
            mailText.append("�ܵ���������")
                    .append(countNum)
                    .append("��")
                    .append("<br>");
            mailText.append("�ɹ�����������")
                    .append(successAdd)
                    .append("��")
                    .append("<br>");
            mailText.append("ʧ�ܴ���������")
                    .append(failureProcess)
                    .append("��")
                    .append("<br>");
            mailText.append("У��ʧ��������")
                    .append(failureCheck)
                    .append("��")
                    .append("<br>");
            mailText.append(syncMailText).append("<br>");
            mailText.append("<br><br>");
        }
    }

    /**
     * �������������ļ�
     */
    public void exportDataFile() throws BOException
    {
        // ת��ģ���ļ����е�����
        fileName = fileNameDataChange(fileName);

        // �õ��ļ��б�
        List fileList = getDataFileListBySftp(fileName);

        if (fileList.size() == 0)
        {
            // �����ʼ�������Ϣ..............
            syncMailText.append("���Ҳ�����ǰ��Ҫ�������ļ�������");
            throw new BOException("���Ҳ�����ǰ��Ҫ�������ļ�������",
                                  BaseVideoConfig.EXCEPTION_FILE_NOT_EXISTED);
        }

        // �ж��ļ������Ƿ�Ϊ0
        if (isNullFile(fileList))
        {
            // �����ʼ�������Ϣ..............
            syncMailText.append("�˴�ͬ���������ļ���Ϊ�գ��˴���������ͬ����ֹ������");
            throw new BOException("�˴�ͬ���������ļ���Ϊ�գ��˴���������ͬ����ֹ������",
                                  BaseVideoConfig.EXCEPTION_FILE_NOT_EXISTED);
        }
        // �ļ�����������һ����Ϊ�գ��һ�Ҫ���ȫ��ͬ��
        else if (isDelTable)
        {
            delTable();
        }

        BufferedReader reader = null;
        String lineText = null;
        int lineNumeber = 0;
        dataSynTaskRunner = new TaskRunner(BaseVideoConfig.taskRunnerNum, BaseVideoConfig.taskMaxReceivedNum);

        try
        {
            // ������ڣ�����
            for (int i = 0; i < fileList.size(); i++)
            {
                String tempFileName = String.valueOf(fileList.get(i));

                if (logger.isDebugEnabled())
                {
                    logger.debug("��ʼ���������ļ���" + tempFileName);
                }

                File file = new File(tempFileName);

                // ����ļ�Ϊ��
                if (file.length() == 0)
                {
                    // ��������ʼ���Ϣ..............
                    syncMailText.append("��ǰ�������ļ�����Ϊ�գ�fileName="
                                        + "tempFileName");
                    continue;
                }
                
                // ִ�����������ݵ���
                isImputDate = true;

                // ���ļ�
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(tempFileName),
                                                                  this.fileEncoding));

                while ((lineText = reader.readLine()) != null)
                {
                    lineNumeber++;
                    countNum++;

                    SynTask task = new SynTask(lineText,
                                               tempFileName,
                                               lineNumeber,
                                               dataSpacers,
                                               this);

                    ReflectedTask refTask = new ReflectedTask(task,
                                                              "sysDataByFile",
                                                              null,null);
                    dataSynTaskRunner.addTask(refTask);
                }
            }
            dataSynTaskRunner.waitToFinished();
            dataSynTaskRunner.stop();
        }
        catch (Exception e)
        {
            throw new BOException(e, BaseVideoConfig.EXCEPTION_INNER_ERR);
        }
        finally
        {
            try
            {
                if (reader != null)
                {
                    reader.close();
                }
            }
            catch (IOException e)
            {
                logger.error(e);
            }
        }
    }

    /**
     * ���ڱ����̵߳��õľ���ִ�з���
     * 
     * @param lineText
     * @param tempFileName
     * 
     * public void sysDataByFile(String lineText, String tempFileName) { //
     * ��¼�ļ��������� lineNumeber++; countNum++;
     * 
     * if (logger.isDebugEnabled()) { logger.debug("��ʼ����У���ļ���" + lineNumeber +
     * "�����ݡ�"); }
     * 
     * if (lineNumeber == 1) { // ɾ����һ��bom�ַ� lineText =
     * PublicUtil.delStringWithBOM(lineText); }
     *  // ��ʱ���� tempData = lineText.split(dataSpacers, -1);
     *  // У��������ȷ�ԣ����У��ʧ�ܼ����ʼ���Ϣ if
     * (!BaseVideoConfig.CHECK_DATA_SUCCESS.equals(checkData(tempData))) { //
     * ���������Ϣ���ʼ���................... writeErrorToMail(tempFileName, lineNumeber,
     * lineText, "У��ʧ��"); failureCheck++; return; }
     * 
     * String exportDBText = exportDataToDB(tempData);
     *  // �������ݿ⣬�������ʧ�ܼ����ʼ���Ϣ if
     * (!BaseVideoConfig.EXPORT_DATA_SUCCESS.equals(exportDBText)) { //
     * ���������Ϣ���ʼ���................... writeErrorToMail(tempFileName, lineNumeber,
     * lineText, exportDBText); failureProcess++; return; } else { successAdd++; } }
     */

    /**
     * �����ֶηָ���
     * 
     * @return
     */
    public String getDataSpacers()
    {
        int a = 0x1f;
        char r = ( char ) a;
        return String.valueOf(r);
    }

    /**
     * У���ļ��Ƿ�ȫΪ���Ծ����Ƿ�ɾ��
     * 
     * @param fileList
     * @return
     */
    public boolean isNullFile(List fileList)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("У���ļ��Ƿ�ȫΪ���Ծ����Ƿ�ɾ����ʼ");
        }

        boolean isNullFile = true;

        for (int i = 0; i < fileList.size(); i++)
        {
            String tempFileName = String.valueOf(fileList.get(i));

            File file = new File(tempFileName);

            // ����ļ�Ϊ��
            if (file.length() > 0)
            {
                isNullFile = false;
            }
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("У���ļ��Ƿ�ȫΪ���Ծ����Ƿ�ɾ��" + isNullFile);
        }

        return isNullFile;
    }

    /**
     * ����У�������ļ�
     */
    public void exportVerfFile() throws BOException
    {
        // ת��ģ���ļ����е�����
        verfFileName = fileNameDataChange(verfFileName);

        // �õ��ļ��б�
        List fileList = getDataFileListBySftp(verfFileName);

        if (fileList.size() == 0)
        {
            // �����ʼ�������Ϣ..............
            verfMailText.append("���Ҳ�����ǰ��Ҫ��У���ļ�������<br>");
            throw new BOException("У���ļ�Ϊ��",
                                  BaseVideoConfig.EXCEPTION_FILE_NOT_EXISTED);
        }
        BufferedReader reader = null;
        String lineText = null;
        int lineNumeber = 0;

        try
        {
            // ������ڣ�����
            for (int i = 0; i < fileList.size(); i++)
            {
                String tempFileName = String.valueOf(fileList.get(i));

                if (logger.isDebugEnabled())
                {
                    logger.debug("��ʼ����У���ļ���" + tempFileName);
                }

                // ���ļ�
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(tempFileName),
                                                                  this.fileEncoding));

                while ((lineText = reader.readLine()) != null)
                {
                    // ��¼�ļ���������
                    lineNumeber++;

                    if (logger.isDebugEnabled())
                    {
                        logger.debug("��ʼ����У���ļ���" + lineNumeber + "�����ݡ�");
                    }

                    if (lineNumeber == 1)
                    {
                        // ɾ����һ��bom�ַ�
                        lineText = PublicUtil.delStringWithBOM(lineText);
                    }

                    // У���ļ������һ��
                    if (BaseVideoConfig.FILE_END.equals(lineText.trim()))
                    {
                        break;
                    }

                    // ���ļ�����
                    VerfDataVO vo = readVerfData(lineText, lineNumeber);

                    // д�뼯���б���
                    verfDataList.add(vo);
                }
            }
        }
        catch (Exception e)
        {
            verfMailText.append("����У���ļ�ʱ�������� ������<br>");
            throw new BOException(e, BaseVideoConfig.EXCEPTION_INNER_ERR);
        }
        finally
        {
            try
            {
                if (reader != null)
                {
                    reader.close();
                }
            }
            catch (IOException e)
            {
                logger.error(e);
            }
        }
    }

    /**
     * ʵ����ʵ��У���ֶε���ȷ��
     * 
     * @param data ������Ϣ
     * @return ������ȷ/���ش�����Ϣ
     */
    protected abstract String checkData(String[] data);

    /**
     * ʵ����ʵ�ַ���ִ�е�ǰҵ���sql����Ӧ��ֵ
     * 
     * @param data ������Ϣ
     * @return ����sql��Ӧֵ
     */
    protected abstract Object[] getObject(String[] data);
    
    /**
     * ʵ����ʵ�ַ���ִ�е�ǰ���ݵ�keyֵ
     * 
     * @param data ������Ϣ
     * @return ���ص�ǰ���ݵ�keyֵ
     */
    protected abstract String getKey(String[] data);

    /**
     * ʵ����ʵ�ַ���ִ�в�ѯ��ǰ�����Ƿ���ڵ�sql����Ӧ��ֵ
     * 
     * @param data ������Ϣ
     * @return ����sql��Ӧֵ
     */
    protected abstract Object[] getHasObject(String[] data);

    /**
     * ʵ����ʵ�ַ�����ӵ�ǰҵ���sql���
     * 
     * @return sql���
     */
    protected abstract String getInsertSqlCode();

    /**
     * ʵ����ʵ�ַ��ظ��µ�ǰҵ���sql���
     * 
     * @return sql���
     */
    protected abstract String getUpdateSqlCode();

    /**
     * ʵ����ʵ�ַ��ز�ѯ�Ƿ���ڵ�ǰ���ݵ�sql���
     * 
     * @return sql���
     */
    protected abstract String getHasSqlCode();

    /**
     * �õ����ԭ����Ϣ��sql���
     * 
     * @return
     */
    protected abstract String getDelSqlCode();

    /**
     * �������ԭ����Ϣ���Ա�ȫ��ͬ��
     * 
     * @return
     */
    public String delTable()
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("���ԭ����Ϣ���Ա�ȫ��ͬ��.��ʼ");
        }

        // �õ����ԭ����Ϣ��sql���
        String delSqlCode = getDelSqlCode();

        try
        {
            DB.getInstance().executeBySQLCode(delSqlCode, null);
        }
        catch (DAOException e)
        {
            logger.error("ִ�����ԭ����Ϣ���Ա�ȫ��ͬ��ʱʧ�� sql=" + delSqlCode + " ������ϢΪ��" + e);
            return "ִ�����ԭ����Ϣʱ�������ݿ��쳣";
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("���ԭ����Ϣ���Ա�ȫ��ͬ��.����");
        }

        return BaseVideoConfig.CHECK_DATA_SUCCESS;
    }

    /**
     * ʵ����ʵ�ְ����ݴ������
     * 
     * @param data ������Ϣ
     * @return ������ȷ/���ش�����Ϣ
     * 
     * public String exportDataToDB(String[] data) { if
     * (logger.isDebugEnabled()) { logger.debug("��ʼ�洢����������."); }
     * 
     * String insertSqlCode = getInsertSqlCode(); String updateSqlCode =
     * getUpdateSqlCode(); Object[] paras = getObject(data); try {
     * 
     * if (!hasDateByDB(data)) { // ���½����Ϊ0,�����
     * DB.getInstance().executeBySQLCode(insertSqlCode, paras); } else {
     * DB.getInstance().executeBySQLCode(updateSqlCode, paras); } } catch
     * (DAOException e) { logger.error("ִ�л�����Ƶ���ݲ���ʧ��" + e); e.printStackTrace();
     * return "ִ��ʱ�������ݿ��쳣"; }
     * 
     * return BaseVideoConfig.CHECK_DATA_SUCCESS; }
     */

    /**
     * �ж��Ƿ���ڵ�ǰ����
     * 
     * @return
     * 
     * public boolean hasDateByDB(String[] data) throws DAOException { String
     * hasSqlCode = getHasSqlCode(); Object[] paras = getHasObject(data);
     * ResultSet rs = null;
     * 
     * try { rs = DB.getInstance().queryBySQLCode(hasSqlCode, paras);
     * 
     * if (rs.next()) { return true; }
     * 
     * return false; } catch (Exception e) { throw new DAOException(e); }
     * finally { DB.close(rs); } }
     */

    /**
     * ����У���ļ���Ϣ��ͳһ����ʽ����ΪУ���ļ���ʽһ����
     * 
     * @param lineText ��ǰ����Ϣ
     * @param lineNumeber �ڼ��� ���ڼ�¼������Ϣ
     * @return У���ļ����ݶ���
     */
    public VerfDataVO readVerfData(String lineText, int lineNumeber)
    {
        VerfDataVO vo = new VerfDataVO();
        // String[] tempData = lineText.split(dataSpacers);
        String[] tempData = lineText.split(verDataSpacers);

        if (tempData.length == 5)
        {
            vo.setFileName(tempData[0]);
            vo.setFileSiz(tempData[1]);
            vo.setFileDataNum(tempData[2]);
            vo.setLineNum(lineNumeber);
        }
        else
        {
            // �����ʼ�������Ϣ...............
            writeErrorToVerfMail(lineNumeber, lineText, "У���ļ��ṹ���ԣ����ڻ�С��5������");
        }

        return vo;
    }

    /**
     * �õ�ftp��Ϣ
     * 
     * @return
     * @throws IOException
     * @throws FTPException
     */
    public FTPClient getFTPClient() throws IOException, FTPException
    {
        String ip = BaseVideoConfig.FTPIP;
        int port = BaseVideoConfig.FTPPORT;
        String user = BaseVideoConfig.FTPNAME;
        String password = BaseVideoConfig.FTPPAS;

        ftp = new FTPClient(ip, port);
        // ��ʼ��ftp����ģʽ��FTPConnectMode.PASV����FTPConnectMode.ACTIVE��
        ftp.setConnectMode(FTPConnectMode.PASV);
        // ʹ�ø������û����������½ftp
        ftp.login(user, password);
        // �����ļ��������ͣ�FTPTransferType.ASCII����FTPTransferType.BINARY��
        ftp.setType(FTPTransferType.BINARY);

        return ftp;
    }
    /**
     * �õ�ftp��Ϣ
     * 
     * @return
     * @throws JSchException 
     * @throws IOException
     * @throws FTPException
     */
    public SFTPServer getSFTPClient() throws JSchException 
    {
    	
    	
        String ip = BaseVideoConfig.FTPIP;
        int port = BaseVideoConfig.FTPPORT;
        String user = BaseVideoConfig.FTPNAME;
        String password = BaseVideoConfig.FTPPAS;

        SFTPServer server = new SFTPServer(ip, user, password,port);
		if (logger.isDebugEnabled())
		{
			logger.debug("login SFTPServer successfully,transfer type is binary");
		}
		return server;
       /* ftp = new FTPClient(ip, port);
        // ��ʼ��ftp����ģʽ��FTPConnectMode.PASV����FTPConnectMode.ACTIVE��
        ftp.setConnectMode(FTPConnectMode.PASV);
        // ʹ�ø������û����������½ftp
        ftp.login(user, password);
        // �����ļ��������ͣ�FTPTransferType.ASCII����FTPTransferType.BINARY��
        ftp.setType(FTPTransferType.BINARY);

        return ftp;*/
    }
    /**
     * ���ڵõ������ļ��б�
     * 
     * @param fileName ģ���ļ���
     * @return
     */
    public List getDataFileList(String fileName) throws BOException
    {
        List fileList = new ArrayList();
        
//       String ftpType =  ConfigFactory.getSystemConfig()
//        .getModuleConfig("ssms").getItemValue("FTP_TYPE").trim();
//        
//        if( ftpType!= null  && ftpType.equals("2")){
//        	//��ȫftpЭ������
//        	   fileList = this.getDataFileListBySftp( fileName);
//        }else {
        	   fileList = this.getDateFileListByFtp( fileName);
  //      }
     
       
        return fileList;
    }

    public List getDateFileListByFtp(String fileName) throws BOException{
    	List fileList = new ArrayList();
    	 try
         {
             ftp = getFTPClient();
         }
         catch (Exception e)
         {
             mailText.append("FTP���ó���FTP���ӳ�������<br>");
             e.printStackTrace();
             logger.error(e);
         }

         try
         {
             // ��������·��
             if (!"".equals(ftpDir))
             {
                 ftp.chdir(ftpDir);
             }

             // �õ�Ŀ¼���ļ��б�
             String[] ftpFileList = ftp.dir();

             if (logger.isDebugEnabled())
             {
                 logger.debug("ƥ���ļ�����ʼ��" + fileName);
             }

             for (int j = 0; j < ftpFileList.length; j++)
             {
                 String tempFileName = ftpFileList[j];

                 // ƥ���ļ����Ƿ���ͬ
                 if (isMatchFileName(fileName, tempFileName))
                 {
                     // �õ�����·��
                     String absolutePath = localDir + File.separator
                                           + tempFileName;
                     absolutePath = absolutePath.replace('\\', '/');
                     ftp.get(absolutePath, ftpFileList[j]);

                     // ��������
                     fileList.add(absolutePath);

                     if (logger.isDebugEnabled())
                     {
                         logger.debug("�ɹ������ļ���" + absolutePath);
                     }
                 }
             }
         }
         catch (Exception e)
         {
        	 e.printStackTrace();
             throw new BOException(e, BaseVideoConfig.EXCEPTION_FTP);
         }
         finally
         {
             if (ftp != null)
             {
                 try
                 {
                     ftp.quit();
                 }
                 catch (Exception e)
                 {
                 }
             }
         }
         return fileList;
    }
    
    /**
     * ���ڵõ������ļ��б�
     * 
     * @param fileName ģ���ļ���
     * @return
     */
    public List getDataFileListBySftp(String fileName) throws BOException
    {
        List fileList = new ArrayList();
        SFTPServer server = null;
        ChannelSftp sftp = null;
        try
        {
        	server =  this.getSFTPClient();
        	sftp = server.login();
        }
        catch (Exception e)
        {
            mailText.append("FTP���ó���FTP���ӳ�������<br>");
            logger.error(e);
        }

        try
        {
            // ��������·��
            if (!"".equals(ftpDir))
            {
            	sftp.cd(ftpDir);
            }

            // �õ�Ŀ¼���ļ��б�
            String[] ftpFileList = server.getDirFilenames(sftp);

            if (logger.isDebugEnabled())
            {
                logger.debug("ƥ���ļ�����ʼ��" + fileName);
            }

            for (int j = 0; j < ftpFileList.length; j++)
            {
                String tempFileName = ftpFileList[j];

                // ƥ���ļ����Ƿ���ͬ
                if (isMatchFileName(fileName, tempFileName))
                {
                    // �õ�����·��
                    String absolutePath = localDir + File.separator
                                          + tempFileName;
                    absolutePath = absolutePath.replace('\\', '/');
                    //ftp.get(absolutePath, ftpFileList[j]);
                    //sftp.get(absolutePath, ftpFileList[j]);
                    SFTPServer.downloadSingleFile(absolutePath, ftpFileList[j],sftp);

                    // ��������
                    fileList.add(absolutePath);

                    if (logger.isDebugEnabled())
                    {
                        logger.debug("�ɹ������ļ���" + absolutePath);
                    }
                }
            }
        }
        catch (Exception e)
        {
        	e.printStackTrace();
            throw new BOException(e, BaseVideoConfig.EXCEPTION_FTP);
        }
        finally
        {
            if (sftp != null)
            {
                try
                {
                	sftp.disconnect();
                }
                catch (Exception e)
                {
                	e.printStackTrace();
                }
            }
            if (server != null)
            {
                try
                {
                	server.disconnect();
                }
                catch (Exception e2)
                {
                	e2.printStackTrace();
                }
            }
        }
        return fileList;
    }
    /**
     * �ж��Ƿ��Ǳ���������Ҫ���ص��ļ���
     * 
     * @param fName ��ǰftp�������ϵ��ļ�
     * @return true �ǣ�false ��
     */
    public boolean isMatchFileName(String fileName, String FtpFName)
    {
//        if (FtpFName.matches(fileName))
//        {
//            return true;
//        }
//        return false;
        
        return FtpFName.matches(fileName);
    }

    /**
     * ����ת��ģ���ļ����е�������ֵ
     * 
     * @param fileName
     * @return
     */
    public String fileNameDataChange(String fileName)
    {
        // ����ļ��������������ڶ���
        if (fileName.indexOf("~D") != -1)
        {
            StringBuffer tempString = new StringBuffer();

            Calendar nowTime = Calendar.getInstance();

            int temp = fileName.indexOf("~D");

            tempString.append(fileName.substring(0, temp));

            String dataType = fileName.substring(temp + 2,
                                                 fileName.lastIndexOf("~"));

            //���ǰ�Сʱ����
            if(!isByHour)
            {
            	// ��������
            	nowTime.add(Calendar.DAY_OF_MONTH, 0 - getDateNum);
            }
            else
            {
            	nowTime.add(Calendar.HOUR_OF_DAY, 0 - getTimeNum);
            }
            

            tempString.append(PublicUtil.getDateString(nowTime.getTime(),
                                                       dataType));

            tempString.append(fileName.substring(fileName.lastIndexOf("~") + 1));

            return tempString.toString();
        }

        return fileName;
    }

    /**
     * д�������Ϣ
     * 
     * @param fileName
     * @param lineNum
     * @param dataText
     * @param reasonText
     */
    public synchronized void writeErrorToMail(String fileName, int lineNum,
                                              String dataText, String reasonText)
    {
        if (!isMailText)
        {
            return;
        }

        if (failMailTextNum >= 10000)
        {
            syncMailText.append("�������ݹ������²��ṩ����...").append("<br>");
            isMailText = false;
        }
        else
        {
            /*syncMailText.append("���������ļ�")
                        .append(fileName)
                        .append("�е�")
                        .append(lineNum)
                        .append("�У�")
                        .append(dataText)
                        .append("�� �������д�ԭ��Ϊ��")
                        .append(reasonText)
                        .append("<br>");
                        */
        	syncMailText.append("���������ļ��е�").append(
					lineNum).append("�У��������д�ԭ��Ϊ��").append(reasonText).append("<br>");
        }

        failMailTextNum++;
    }

    /**
	 * д��У�鴦�����Ĵ�����Ϣ���ʼ�
	 * 
	 * @param lineNum
	 *            �ڼ���
	 * @param dataText
	 *            ʲô����
	 * @param reasonText
	 *            ����ԭ��
	 */
    public void writeErrorToVerfMail(int lineNum, String dataText,
                                     String reasonText)
    {
        verfMailText.append("У���ļ���")
                    .append(" ��")
                    .append(lineNum)
                    .append("��: ")
                    .append(dataText)
                    .append("�� �������д�ԭ��Ϊ��")
                    .append(reasonText)
                    .append("<br>");
    }

    /**
     * ���ڻ�������
     */
    public void destroy()
    {
    	verfDataList.clear();
        keyMap.clear();
    }

    protected boolean checkFieldLength(String field, int maxLength, boolean must)
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
     * ����֤���衣��֤ʧ�ܴ�ӡ��־��
     * 
     * @param fieldName ��������ơ�������־��¼
     * @param field ���ֵ
     * @param maxLength ��������ȡ�
     * @param must �Ƿ�����
     * @return ��֤ʧ�ܷ���false�����򷵻�true
     */
    protected boolean checkFieldLength(String fieldName, String field,
                                       int maxLength, boolean must)
    {
        boolean result = true;
        if (field == null)
        {
            result = false;
        }
        if (StringTool.lengthOfHZ(field) > maxLength)
        {
            result = false;
            logger.error(fieldName + "=" + field + ",���ֶ���֤ʧ�ܡ�ԭ�򣺳��ȳ�����"
                         + maxLength + "���ַ���");
        }
        if (must)
        {
            if (field.equals(""))
            {
                result = false;
                logger.error(fieldName + "=" + field
                             + ",���ֶ���֤ʧ�ܡ�ԭ�򣺸��ֶ��Ǳ����ֶΣ�������Ϊ��");
            }
        }
        return result;

    }

    /**
     * �����ֶε���������
     * 
     * @param field �������ֶ�
     * @param maxLength ���ֵ���󳤶�
     * @param must �Ƿ������ֶ�
     * @return
     */
    protected boolean checkIntegerField(String fieldName, String field,
                                        int maxLength, boolean must)
    {
        if (field == null)
        {
            return false;
        }
        if (!field.matches(INTEGER.replaceFirst("d", String.valueOf(maxLength))))
        {
            logger.error(fieldName + "=" + field + ",���ֶ���֤ʧ�ܡ�ԭ�򣺳��ȳ�����"
                         + maxLength + "�����֣�");
            return false;
        }
        if (must)
        {
            if (field.equals(""))
            {
                logger.error(fieldName + "=" + field
                             + ",���ֶ���֤ʧ�ܡ�ԭ�򣺸��ֶ��Ǳ����ֶΣ�������Ϊ��");
                return false;
            }
        }
        return true;

    }

    public synchronized void setFailureCheckAdd()
    {
        this.failureCheck++;
    }

    public synchronized void setFailureProcessAdd()
    {
        this.failureProcess++;
    }

    public synchronized void setSuccessAddAdd()
    {
        this.successAdd++;
    }

    public synchronized void setCountNumAdd()
    {
        this.countNum++;
    }

    public synchronized boolean hasKey(String key)
    {
        if(!keyMap.containsKey(key))
        {
            keyMap.put(key, "");
            return false;
        }
        else
        {
            return true;
        }
        
    }
}
