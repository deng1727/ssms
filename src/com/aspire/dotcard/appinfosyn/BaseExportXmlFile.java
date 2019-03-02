package com.aspire.dotcard.appinfosyn;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts.upload.FormFile;
import org.dom4j.Element;

import com.aspire.common.config.ServerInfo;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

import com.aspire.dotcard.appinfosyn.config.AppInfoConfig;
import com.aspire.dotcard.baseVideoNew.config.BaseVideoNewConfig;
import com.aspire.dotcard.baseVideoNew.exportfile.BaseFileNewTools;
import com.aspire.dotcard.resourceftp.FtpVO;
import com.aspire.dotcard.resourceftp.ResourceFtp;
import com.aspire.ponaadmin.web.repository.RepositoryBOCode;
import com.aspire.ponaadmin.web.system.SystemConfig;
import com.aspire.ponaadmin.web.util.IOUtil;
import com.aspire.ponaadmin.web.util.PublicUtil;
import com.enterprisedt.net.ftp.FTPClient;

public abstract class BaseExportXmlFile implements BaseFileAbility {

	/**
	 * ��־����
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(BaseExportXmlFile.class);

	/**
	 * �ʼ�������Ϣ
	 */
	protected String mailTitle = "";

	/**
	 * �ļ���ŵı��ظ�·��
	 */
	protected String localDir = AppInfoConfig.LOCALDIR;
	/**
	 * �ļ���ŵı��ظ�·��
	 */
	protected String tarFileName = "";
	/**
	 * ftp�ļ���Ÿ�Ŀ¼
	 */
	protected String ftpFileDirectory = "";
	
	/**
	 * �ļ����Ŀ¼
	 */
	protected String fileDir = "";

	
	/**
	 * �Ƿ�ִ�����������ݵ���
	 */
	protected boolean isImputDate = false;

	/**
	 * �ܸ���
	 */
	protected int countNum = 0;
	
	/**
	 * ��������
	 */
	protected int addNum = 0;
	
	/**
	 * �޸ĸ���
	 */
	protected int modifyNum = 0;
	
	/**
	 * ɾ������
	 */
	protected int deleteNum = 0;

	/**
	 * �����ļ���ʽ�ж���ļ����
	 */
	protected String dataSpacers = "";
	
	/**
	 * ͬ����ʼʱ��
	 */
	protected Date startDate = null;
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
	 * �ʼ���Ϣ
	 */
	protected StringBuffer mailText = new StringBuffer();

	/**
	 * ͬ���ļ�����Ϣ
	 */
	protected StringBuffer syncMailText = new StringBuffer();

	/**
	 * �����б�
	 */
	protected static List<String> dataList = Collections.synchronizedList(new ArrayList<String>());

	/**
	 * ����MAP
	 */
	protected static Map<String, String> appKeyMap = Collections
			.synchronizedMap(new HashMap<String, String>());
	
	/**
	 * �������׼����������
	 */
	public void init() {
		localDir = AppInfoConfig.LOCALDIR;
		dataSpacers = getDataSpacers();
		ftpFileDirectory=AppInfoConfig.ftpFileDirectory;
		fileDir="appinfo";
	}

	/**
	 * �����ֶηָ���
	 * 
	 * @return
	 */
	public String getDataSpacers()
	{
		return "\\|";
	}
	/**
	 * ���ڵõ������ļ��б�
	 * 
	 * @param fileName
	 *            ģ���ļ���
	 * @return
	 */
	protected List<String> getDataFileList(String fileName) throws BOException
	{
		List<String> fileList = new ArrayList<String>();
		
		FTPClient ftp = getFTPClient();
		
		try
		{
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
				if (BaseFileNewTools.isMatchFileName(fileName, tempFileName))
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
			throw new BOException(e, BaseVideoNewConfig.EXCEPTION_FTP);
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
				{}
			}
		}
		return fileList;
	}

	/**
	 * ���ڵõ������ļ��б�
	 * 
	 * @param fileName
	 *            ģ���ļ���
	 * @return
	 */
	protected List<String> getDataFileList2(String fileName) throws BOException
	{
		List<String> fileList = new ArrayList<String>();
		
		FTPClient ftp = getFTPClient();
		
		try
		{
			if (logger.isDebugEnabled())
			{
				logger.debug("ƥ���ļ�����ʼ��" + fileName);
			}

			String remoteFilePath =localDir + File.separator
	           + fileName;
			//�ж�FTP�Ƿ�����ļ�
			remoteFilePath = remoteFilePath.replace('\\', '/');
			// �õ�����·��
			String absolutePath = localDir + File.separator+"local"+File.separator
			           + fileName;
			BaseFileTools.createFilePath(absolutePath);
			absolutePath = absolutePath.replace('\\', '/');
			ftp.get(absolutePath, remoteFilePath);
			fileList.add(absolutePath);
			
		}
		catch (Exception e)
		{
			throw new BOException(e, AppInfoConfig.EXCEPTION_FTP);
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
				{}
			}
		}
		return fileList;
	}
	
	/**
	 * 
	 *@desc �ϴ��ļ�����Դ������
	 *@author dongke
	 *Aug 6, 2011
	 * @param uploadFile
	 * @param resServerPath
	 * @param resourceId
	 * @throws BOException 
	 */
	public String upLoadfileToResServer(String filesName,String appname,
			String name) throws BOException
	{
//		String tempDir = ServerInfo.getAppRootPath() + File.separator + "temp"
//				+ File.separator;

		String resServerPath = AppInfoConfig.RESERVERPATH;
        String path="";
		String localfilePath = localDir + File.separator+filesName+File.separator + appname+File.separator+name;
		File file = new File(localfilePath);
		if(!file.exists()){
			return null;
		}
		FTPClient ftp = null;
		try
		{
//			ftp = PublicUtil.getFTPClient(SystemConfig.SOURCESERVERIP,
//					SystemConfig.SOURCESERVERPORT, SystemConfig.SOURCESERVERUSER,
//					SystemConfig.SOURCESERVERPASSWORD, resServerPath);
			FtpVO ftpVo =   ResourceFtp.getInstance().getResourceServerFtp();
			ftp = ftpVo.getFtp();
			ftp.chdir(resServerPath);
			
			if(filesName != null &&!"".equals(filesName)){
				PublicUtil.checkAndCreateDir(ftp, filesName);
				ftp.chdir(filesName);	
			}
			if(appname != null &&!"".equals(appname)){
				PublicUtil.checkAndCreateDir(ftp, appname);
				ftp.chdir(appname);	
			}
		
			 path =ftpVo.getWwwUrl()+ftpVo.getResroot()+File.separator+resServerPath+ File.separator +filesName+ File.separator +appname+File.separator+name;
			ftp.put(localfilePath, name);
		} catch (Exception e)
		{
			throw new BOException("�ϴ�����Դ�����������쳣��", e,
					RepositoryBOCode.CATEGORY_CATE_PIC_UPLOAD);
		} finally
		{
			if (ftp != null)
			{
				try
				{
					ftp.quit();
				} catch (Exception e)
				{
				}
			}
		}
		return path;
		
	
	}

	
	/**
	 * �õ���ǰFTP���Ӷ���
	 * 
	 * @return
	 */
	protected FTPClient getFTPClient()
	{
		FTPClient ftp = null;
		String ftpDir = AppInfoConfig.ftpFileDirectory;
		try
		{
			ftp = BaseFileTools.getFTPClient();
			// ��������·��
			if (!"".equals(ftpDir))
			{
				ftp.chdir(ftpDir);
			}
		}
		catch (Exception e)
		{
			mailText.append("FTP���ó���FTP���ӳ�������<br>");
			logger.error(e);
		}
		return ftp;
	}
	
	/**
	 * д�������Ϣ
	 * 
	 * @param fileName
	 * @param lineNum
	 * @param dataText
	 * @param reasonText
	 */
	public synchronized void writeErrorToMail(int lineNum,
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
			syncMailText.append("�������ݵ�").append(lineNum).append(
					"�����������д�ԭ��Ϊ��").append(reasonText).append("<br>");
			failMailTextNum++;
		}
	}
	

	
	

	public synchronized boolean hasAppKeyMap(String key)
	{
		if (!appKeyMap.containsKey(key))
		{
			appKeyMap.put(key, "0");
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public synchronized void delAppKeyMap(String key)
	{
		appKeyMap.put(key, "0");
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
	
	public synchronized void setAddNumAdd()
	{
		this.addNum++;
	}
	
	public synchronized void setModifyNumAdd()
	{
		this.modifyNum++;
	}
	
	public synchronized void setDeleteNumAdd()
	{
		this.deleteNum++;
	}
	
	public void setExportTime(Calendar setTime) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * ʵ����ʵ��У���ֶε���ȷ��
	 * 
	 * @param data
	 *            ������Ϣ
	 * @param flag
	 *            ��־λ
	 * @return ������ȷ/���ش�����Ϣ
	 */
	protected abstract List<AppInfoVO> checkData(Object object);
	
	/**
	 * ʵ����ʵ�ֽ���xml�ļ����ݵ�����
	 * 
	 * @param data
	 *            ������Ϣ
	 * @return ����
	 */
	protected abstract Object getObject(Element root);
	
	/**
	 * ʵ����ʵ�ֽ�����ת����sql������Object[][]
	 * 
	 * @param object
	 *            ������Ϣ
	 * @return ����
	 */
	protected abstract Object[][] getObjectParas(Object object);
	
	/**
	 * ʵ����ʵ�ַ���ִ��Ԥɾ����ǰ���ݵ�keyֵ
	 * 
	 * @param data
	 *            ������Ϣ
	 * @return ���ص�ǰ���ݵ�keyֵ
	 */
	protected String[] getDelKey(String key)
	{
		return key.split("\\|", -1);
	}
	
	/**
	 * ʵ����ʵ�ַ�����ӵ�ǰҵ���sql���
	 * 
	 * @return sql���
	 */
	protected abstract String getInsertSqlCode();
	
	/**
	 * ʵ����ʵ�ַ�����ӵ�ǰҵ���sql���,���ö���sql���
	 * 
	 * @return sql���
	 */
	protected abstract Object[] getInsertSqlCodes();
	
	/**
	 * ʵ����ʵ�ַ��ظ��µ�ǰҵ���sql���
	 * 
	 * @return sql���
	 */
	protected abstract String getUpdateSqlCode();
	
	/**
	 * ʵ����ʵ�ַ��ظ��µ�ǰҵ���sql���
	 * 
	 * @return sql���
	 */
	protected abstract String getDelSqlCode();
	protected abstract String getInsertSqlCodeByAppInfo();
	protected abstract String checkData2(AppInfoVO object);

	protected String getImageAllPath(String fileName, String image) {
		// TODO Auto-generated method stub
		return null;
	}

	protected Object uplodPicture(List<AppInfoVO> appInfoList,String fileName) {
		// TODO Auto-generated method stub
		return null;
	}
	protected abstract void destroy();
	
	protected abstract void clear();


	

}
