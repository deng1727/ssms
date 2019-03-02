package com.aspire.dotcard.reportdata.a8statistic;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
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
public class A8StatisticImportBO
{
	private static final JLogger LOG = LoggerFactory.getLogger(A8StatisticImportBO.class);
	
	/**
     * ����ר����־
     */
    protected static JLogger REPORT_LOG = LoggerFactory.getLogger("reportimp.client");
	
	private static A8StatisticImportBO DAO=new A8StatisticImportBO();
	public static A8StatisticImportBO getInstance()
	{
		return DAO;
	}
	/**
	 * ����a8����ͳ�������ļ�
	 * @return �ļ��б�
	 * @throws Exception
	 */
	public List downLoadReportFile()throws Exception
	{
		String ip=TopDataConfig.get("TopDataFTPServerIP");
		int port=Integer.parseInt(TopDataConfig.get("TopDataFTPServerPort"));
		String user=TopDataConfig.get("TopDataFTPServerUser");
		String password = TopDataConfig.get("TopDataFTPServerPassword");
		String ftpDir=PublicUtil.getEndWithSlash(TopDataConfig.get("A8_FtpDir"));
		String pasPath = PublicUtil.getEndWithSlash(TopDataConfig.get("TopDataSiteDir"));
		Calendar date=Calendar.getInstance();
		date.add(Calendar.DAY_OF_MONTH, -1);
		String dateString = PublicUtil.getDateString(date.getTime(), "yyyyMMdd");
		String fileNameRegex="gmcc_song_play_"+dateString+"_[0-9]{6}+\\.txt";//gmcc_song_play_YYYYMMDD_xxxxxx.txt
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
				LOG.debug("��ʼ����A8�����ļ���"+fileNameRegex);
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
	 */
	public int saveCategoryStatistic(List fileNameList)throws Exception
	{
		int successCount=0;
        for(int i=0;i<fileNameList.size();i++)
        {
        	successCount+=processFileData((String)fileNameList.get(i));
        }
        return successCount;
       
	}
	 /**
     * ���������ݲ��뵽���ݿ�
     *
     * @param fileName String
     * @throws Exception
     * @return int �ɹ����µĴ���
     */
    private int processFileData(String fileName)
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
            	long count=Long.parseLong(lineData[2]);
            	int result=A8StatisticImportDAO.getInstance().updateCategoryStatistic(lineData[1], count);
            	successCount+=result;
            }catch(NumberFormatException e)
            {
            	LOG.error("��"+(i+1)+"�е���ʧ��:"+arrColorContent[i]+" ͳ�Ʊ���������");
            	REPORT_LOG.error("��"+(i+1)+"�е���ʧ��:"+arrColorContent[i]+" ͳ�Ʊ���������");
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

		if (StringTool.lengthOfHZ(recordContent) != 28)
		{
			throw new BOException("�ļ����ݸ�ʽ����ȷ!���ܳ��Ȳ�����28���ַ�");
		}
		int[] l = { 8, 9, 11 };
		int startIndex = 0;//�ֶο�ʼλ��
		for (int i = 0; i < 3; i++)
		{
			r[i] = StringTool.formatByLen(recordContent.substring(startIndex), l[i], "").trim();
			startIndex+=l[i];
		}
		return r;
	}
        

}
