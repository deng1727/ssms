package com.aspire.dotcard.reportdata.appstatistic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.config.ServerInfo;
import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.reportdata.TopDataConfig;
import com.aspire.dotcard.reportdata.gcontent.ImportReportDataBO;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.util.IOUtil;
import com.aspire.ponaadmin.web.util.PublicUtil;
import com.aspire.ponaadmin.web.util.StringTool;
import com.enterprisedt.net.ftp.FTPClient;
/**
 * �ն����ֱ���ͳ�Ƶ����BO��
 * @author zhangwei
 *
 */
public class AppStatisticImportBO
{
	private static final JLogger LOG = LoggerFactory.getLogger(AppStatisticImportBO.class);
	
	/**
     * ����ר����־
     */
    protected static JLogger REPORT_LOG = LoggerFactory.getLogger("reportimp.client");
	
	private static AppStatisticImportBO DAO=new AppStatisticImportBO();
	public static AppStatisticImportBO getInstance()
	{
		return DAO;
	}
	/**
	 * ����a8����ͳ�������ļ�
	 * @return �ļ��б�
	 * @throws Exception
	 */
	public List downLoadReportFile(String ftpDir,String fileNameRegex)throws Exception
	{
		String ip=TopDataConfig.get("TopDataFTPServerIP");
		int port=Integer.parseInt(TopDataConfig.get("TopDataFTPServerPort"));
		String user=TopDataConfig.get("TopDataFTPServerUser");
		String password = TopDataConfig.get("TopDataFTPServerPassword");
		//String ftpDir=PublicUtil.getEndWithSlash(TopDataConfig.get("A8_FtpDir"));
		String pasPath = PublicUtil.getEndWithSlash(TopDataConfig.get("TopDataSiteDir"));
		//Calendar date=Calendar.getInstance();
		//date.add(Calendar.DAY_OF_MONTH, -1);
		//String dateString = PublicUtil.getDateString(date.getTime(), "yyyyMMdd");
		//String fileNameRegex="gmcc_song_play_"+dateString+"_[0-9]{6}+\\.txt";//gmcc_song_play_YYYYMMDD_xxxxxx.txt
		String localDir=ServerInfo.getAppRootPath()+File.separator+pasPath+ftpDir;
		
		FTPClient ftp = null;
		try
		{
			ftp= PublicUtil.getFTPClient(ip, port, user, password, ftpDir);
			//�������debug��־�����汾������ͬ�����ļ���
			//��ȷ������Ŀ¼�Ѿ������ˡ�
			IOUtil.checkAndCreateDir(localDir);			
			//��Ż�ȡ�����ļ��б��list
			ArrayList fileList = new ArrayList();
			String[] Remotefiles = ftp.dir();
			//����������ʽ����ȡƥ����ļ��������档
			if (LOG.isDebugEnabled())
			{
				LOG.debug("��ʼ����Ӧ�ñ����ļ���"+fileNameRegex);
			}
			for (int j = 0; j < Remotefiles.length; j++)
			{
				String remotefileName = Remotefiles[j]; 
				if (remotefileName.matches(fileNameRegex))
				{
					String absolutePath = localDir  + remotefileName;
					absolutePath = absolutePath.replace('\\', '/');
					ftp.get(absolutePath, Remotefiles[j]);
					fileList.add(absolutePath);
					if (LOG.isDebugEnabled())
					{
						LOG.debug("�ɹ������ļ���" + absolutePath);
					}
				}
			}
			
			return fileList;
		} catch(Exception e)
		{
			throw new BOException(e,DataSyncConstants.EXCEPTION_FTP);
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
	}
	
	/**
	 * ����ͳ����Ϣ��
	 * @param fileNameList
	 * @return
	 * @throws Exception
	 * �Ƽ�Ӧ����ͳ������
	 */
	public int saveAppLastStatistic(List fileNameList)throws Exception
	{
		int successCount=0;
        for(int i=0;i<fileNameList.size();i++)
        {
        	successCount+=processFileDataAppLast((String)fileNameList.get(i));
        }
        return successCount;
       
	}
	
	/**
	 * ����ͳ����Ϣ��
	 * @param fileNameList
	 * @return
	 * @throws Exception
	 * �Ƽ�Ӧ����ͳ������
	 */
	public int saveAppRecommondStatistic(List fileNameList)throws Exception
	{
		int successCount=0;
        for(int i=0;i<fileNameList.size();i++)
        {
        	successCount+=processFileDataAppRecommond((String)fileNameList.get(i));
        }
        return successCount;
       
	}
	
	
	/**
	 * ����ͳ����Ϣ��
	 * @param fileNameList
	 * @return
	 * @throws Exception
	 * ����Ӧ����ͳ������
	 */
	public int saveAppBaseStatistic(String type,List fileNameList)throws Exception
	{
		String updateSql = "reportdata.applaststatistic.updateAppLastStatistic.UPDATE";
		String insertSql  = "reportdata.applaststatistic.updateAppLastStatistic.INSERT";
		if(type != null && type.equals("latest")){
			 updateSql = "reportdata.applaststatistic.updateAppLastStatistic.UPDATE";
			insertSql  = "reportdata.applaststatistic.updateAppLastStatistic.INSERT";			
		}else if(type != null && type.equals("recommend")){
			 updateSql = "reportdata.apprecommondstatistic.updateAppRecommondStatistic.UPDATE";
				insertSql  = "reportdata.apprecommondstatistic.updateAppRecommondStatistic.INSERT";
		}else if(type != null && type.equals("competitive")){
			 updateSql = "reportdata.appcompetitivestatistic.updateAppCompetitiveStatistic.UPDATE";
				insertSql  = "reportdata.appcompetitivestatistic.updateAppCompetitiveStatistic.INSERT";
		}
		
		
		int successCount=0;
        for(int i=0;i<fileNameList.size();i++)
        {
        	successCount+=processFileDataAppBase(updateSql,insertSql,(String)fileNameList.get(i));
        }
        return successCount;
       
	}
	 /**
     * �ӱ���ͬ�����������µ����ݿ�
     *
     * @param fileName String
     * @throws Exception
     * @return int �ɹ����µĴ���
     */
	
	public int saveContentBlackList(List fileNameList) {
		String updateSql = "reportdata.BlackListImportBO.saveContentBlackList().UPDATE";
		String insertSql = "reportdata.BlackListImportBO.saveContentBlackList().INSERT";
		int successCount = 0;
		LOG.debug("fileNameList=" + fileNameList);
		for (int i = 0; i < fileNameList.size(); i++) {
			String fileName = (String) fileNameList.get(i);
			List oneFileDatas = getDatasByFileName(fileName);
			for (int j = 0; j < oneFileDatas.size(); j++) {
				String oneLineData = (String) oneFileDatas.get(j);
				Object splitOneLineData[];
				try {
					int l[] = { 8,8, 12,1 };
					splitOneLineData = parseLineText(oneLineData.trim(), l);
					if (splitOneLineData != null
							&& splitOneLineData.length == 4) {
						//��contentid������󣬱�֤update ��insert����˳������
						Object contentid = 	splitOneLineData[2];
						splitOneLineData[2] = splitOneLineData[3];
						splitOneLineData[3] = contentid;
						successCount += AppStatisticImportDAO.getInstance()
								.updateAppBaseStatistic(updateSql, insertSql,
										splitOneLineData, splitOneLineData);
					} else {
						LOG.error("��" + (j + 1) + "�е���ʧ��:" + oneLineData);
						REPORT_LOG
								.error("��" + (j + 1) + "�е���ʧ��:" + oneLineData);
					}
				} catch (DAOException e) {
					e.printStackTrace();
					LOG.error(
							"��" + (j + 1) + "�е���ʧ��:" + oneLineData + " ���ݿ����",
							e);
					REPORT_LOG.error("��" + (j + 1) + "�е���ʧ��:" + oneLineData
							+ " ���ݿ����", e);
				} catch (BOException e) {
					e.printStackTrace();
					LOG.error("��" + (j + 1) + "�е���ʧ��:" + oneLineData, e);
					REPORT_LOG.error("��" + (j + 1) + "�е���ʧ��:" + oneLineData, e);
				}
			}
		}
		return successCount;
	}
	
	

    /**
     * ��ȡһ���ļ�������
     * @param fileName
     * @return
     */
    public List getDatasByFileName(String fileName){
    	//List allLineDate = null;
    	List arrColorContent;
		try {
			arrColorContent = this.readAllFileLine(fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
         
    	return arrColorContent;
    }
	
	 /**
     * ����Ӧ�ø��µ����ݿ�
     *
     * @param fileName String
     * @throws Exception
     * @return int �ɹ����µĴ���
     */
    private int processFileDataAppBase(String updateSql,String insertSql,String fileName)
                    throws Exception
    {

        if (LOG.isDebugEnabled())
        {
            LOG.debug("The file name of processing is��" + fileName);
        }
        // ��ȡ�ļ�����
        Object[] arrColorContent = ImportReportDataBO.readAllFileLine(fileName);
        int iDataNum = arrColorContent.length;
        if (iDataNum == 0)
        {
            LOG.debug("û��Ҫ���������");
            return 0;
        }
        else
        {
            LOG.debug("һ��Ҫ����" + iDataNum + "������");
        }
        int successCount=0;
        // ���д���
        for (int i = 0; i < iDataNum; i++)
        {     
            String lineData[];
            
            try
            {
            	lineData = parseLineText(( String ) arrColorContent[i]);
            	double count=Double.parseDouble(lineData[2]);
            	int result=AppStatisticImportDAO.getInstance().updateAppBaseStatistic(updateSql,insertSql,lineData[1],lineData[0], count);
            	successCount+=result;
            }catch(NumberFormatException e)
            {
            	LOG.error("��"+(i+1)+"�е���ʧ��:"+arrColorContent[i]+" ͳ�Ʊ�����double������");
            	REPORT_LOG.error("��"+(i+1)+"�е���ʧ��:"+arrColorContent[i]+" ͳ�Ʊ�����double������");
            }catch(DAOException e)
            {
            	LOG.error("��"+(i+1)+"�е���ʧ��:"+arrColorContent[i]+" ���ݿ����",e);
            	REPORT_LOG.error("��"+(i+1)+"�е���ʧ��:"+arrColorContent[i]+" ���ݿ����",e);
            } catch(BOException e)
            {
            	LOG.error("��"+(i+1)+"�е���ʧ��:"+arrColorContent[i],e);
            	REPORT_LOG.error("��"+(i+1)+"�е���ʧ��:"+arrColorContent[i],e);
            }
        }
        return successCount;
    }
	 /**
     * ����Ӧ�ø��µ����ݿ�
     *
     * @param fileName String
     * @throws Exception
     * @return int �ɹ����µĴ���
     */
    private int processFileDataAppLast(String fileName)
                    throws Exception
    {

        if (LOG.isDebugEnabled())
        {
            LOG.debug("The file name of processing is��" + fileName);
        }
        // ��ȡ�ļ�����
        Object[] arrColorContent = ImportReportDataBO.readAllFileLine(fileName);
        int iDataNum = arrColorContent.length;
        if (iDataNum == 0)
        {
            LOG.debug("û��Ҫ���������");
            return 0;
        }
        else
        {
            LOG.debug("һ��Ҫ����" + iDataNum + "������");
        }
        int successCount=0;
        // ���д���
        for (int i = 0; i < iDataNum; i++)
        {     
            String lineData[];
            
            try
            {
            	lineData = parseLineText(( String ) arrColorContent[i]);
            	double count=Double.parseDouble(lineData[2]);
            	int result=AppStatisticImportDAO.getInstance().updateAppLastStatistic(lineData[1],lineData[0], count);
            	successCount+=result;
            }catch(NumberFormatException e)
            {
            	LOG.error("��"+(i+1)+"�е���ʧ��:"+arrColorContent[i]+" ͳ�Ʊ�����double������");
            	REPORT_LOG.error("��"+(i+1)+"�е���ʧ��:"+arrColorContent[i]+" ͳ�Ʊ�����double������");
            }catch(DAOException e)
            {
            	LOG.error("��"+(i+1)+"�е���ʧ��:"+arrColorContent[i]+" ���ݿ����",e);
            	REPORT_LOG.error("��"+(i+1)+"�е���ʧ��:"+arrColorContent[i]+" ���ݿ����",e);
            } catch(BOException e)
            {
            	LOG.error("��"+(i+1)+"�е���ʧ��:"+arrColorContent[i],e);
            	REPORT_LOG.error("��"+(i+1)+"�е���ʧ��:"+arrColorContent[i],e);
            }
        }
        return successCount;
    }
    
    
    /**
     * �Ƽ�Ӧ�ø��µ����ݿ�
     *
     * @param fileName String
     * @throws Exception
     * @return int �ɹ����µĴ���
     */
    private int processFileDataAppRecommond(String fileName)
                    throws Exception
    {

        if (LOG.isDebugEnabled())
        {
            LOG.debug("The file name of processing is��" + fileName);
        }
        // ��ȡ�ļ�����
        Object[] arrColorContent = ImportReportDataBO.readAllFileLine(fileName);
        int iDataNum = arrColorContent.length;
        if (iDataNum == 0)
        {
            LOG.debug("û��Ҫ���������");
            return 0;
        }
        else
        {
            LOG.debug("һ��Ҫ����" + iDataNum + "������");
        }
        int successCount=0;
        // ���д���
        for (int i = 0; i < iDataNum; i++)
        {     
            String lineData[];
            
            try
            {
            	lineData = parseLineText(( String ) arrColorContent[i]);
            	double count=Double.parseDouble(lineData[2]);
            	int result=AppStatisticImportDAO.getInstance().updateAppRecommondStatistic(lineData[1],lineData[0], count);
            	successCount+=result;
            }catch(NumberFormatException e)
            {
            	LOG.error("��"+(i+1)+"�е���ʧ��:"+arrColorContent[i]+" ͳ�Ʊ�����double������");
            	REPORT_LOG.error("��"+(i+1)+"�е���ʧ��:"+arrColorContent[i]+" ͳ�Ʊ�����double������");
            }catch(DAOException e)
            {
            	LOG.error("��"+(i+1)+"�е���ʧ��:"+arrColorContent[i]+" ���ݿ����",e);
            	REPORT_LOG.error("��"+(i+1)+"�е���ʧ��:"+arrColorContent[i]+" ���ݿ����",e);
            } catch(BOException e)
            {
            	LOG.error("��"+(i+1)+"�е���ʧ��:"+arrColorContent[i],e);
            	REPORT_LOG.error("��"+(i+1)+"�е���ʧ��:"+arrColorContent[i],e);
            }
        }
        return successCount;
    }
    /**
     * ����ÿһ�����ݵ�������
     */
    private String[]  parseLineText(String recordContent)throws BOException
    {

    	String[] r = new String[3];

		if (StringTool.lengthOfHZ(recordContent) != 31)
		{
			throw new BOException("�ļ����ݸ�ʽ����ȷ!���ܳ��Ȳ�����31���ַ�");
		}
		int[] l = { 8, 12, 11 };
		int startIndex = 0;//�ֶο�ʼλ��
		for (int i = 0; i < 3; i++)
		{
			r[i] = StringTool.formatByLen(recordContent.substring(startIndex), l[i], "").trim();
			startIndex+=l[i];
		}
		return r;
	}
    
    /**
     * ����ÿһ�����ݵ�������
     */
    private String[]  parseLineText(String recordContent ,int[]l)throws BOException
    {

    	int len = l.length;
    	String[] r = new String[len];
        int totallen = 0;
        for(int i =0;i < len; i ++){
        	totallen += l[i];
        }
		if (StringTool.lengthOfHZ(recordContent) != totallen)
		{
			throw new BOException("�ļ����ݸ�ʽ����ȷ!���ܳ��Ȳ�����"+totallen+"���ַ�"+recordContent);
		}
		//int[] l = { 8, 12, 11 };
		
		int startIndex = 0;//�ֶο�ʼλ��
		for (int i = 0; i < len; i++)
		{
			r[i] = StringTool.formatByLen(recordContent.substring(startIndex), l[i], "").trim();
			startIndex+=l[i];
		}
		return r;
	}
    /**
     * ����ÿһ�����ݵ�������
     * @param recordContent �����һ������
     * @param rexg ����ַ�
     * @param size һ�е��ֶ���
     * @return
     * @throws BOException
     */
    private String[]  parseLineTextByRexg(String recordContent,String rexg,int size)throws BOException
    {
		String[] r = null;
		if (recordContent == null || recordContent.length() <= 0) {
			LOG.error("�ļ����ݸ�ʽ����ȷ!����Ϊ��" + recordContent);
			throw new BOException("�ļ����ݸ�ʽ����ȷ!����Ϊ��" + recordContent);
		}
		r = recordContent.split(rexg);
		if (r.length != size) {
			LOG.error("�ļ�������" + recordContent + " ��ʽ����ȷ!�����ݷָ�����" + rexg
					+ "�ֶ�����Ϊ" + size);
			throw new BOException("�ļ�������" + recordContent + " ��ʽ����ȷ!�����ݷָ�����"
					+ rexg + "�ֶ�����Ϊ" + size);
		}
		return r;
	}  
    /**
     * ��ȡ�ļ��е�ȫ�����ݣ����һ��String
     *
     * @param fileName ,�ļ���ȫ·������
     * @return String
     * @throws IOException
     */
    public static List readAllFileLine(String fileName) throws IOException
    {
        List list = new ArrayList();
        FileReader fr = null;
        BufferedReader br = null;
        try{
            fr = new FileReader(fileName);
            br = new BufferedReader(fr);
            String line = br.readLine();
            while (line != null && line.length() > 0)
            {
                list.add(line);
                line = br.readLine();
            }
        }
        catch(IOException ie)
        {
        	LOG.error(ie);
            throw ie;
        }
        finally{
            br.close();
            fr.close();
        }

        return list;
    }
    
    /**
     * ��dblink��ʽ����С���Ƽ�����
     * @return �������� 
     */
    public int updateRecommendDate()
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("xiaobian Recommend update date");
        }

        // ���¼�¼����
        int result = 0;
        
        try
        {
            result = AppStatisticImportDAO.getInstance().updateRecommendDate();
        }
        catch (DAOException e)
        {
            LOG.error("��dblink��ʽ����С���Ƽ�����ʱ���ݿ����", e);
            REPORT_LOG.error("��dblink��ʽ����С���Ƽ�����ʱ���ݿ����", e);
        }
        return result;

    }
}
