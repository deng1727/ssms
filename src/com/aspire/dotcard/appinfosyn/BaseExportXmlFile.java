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
	 * 日志对象
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(BaseExportXmlFile.class);

	/**
	 * 邮件标题信息
	 */
	protected String mailTitle = "";

	/**
	 * 文件存放的本地根路径
	 */
	protected String localDir = AppInfoConfig.LOCALDIR;
	/**
	 * 文件存放的本地根路径
	 */
	protected String tarFileName = "";
	/**
	 * ftp文件存放根目录
	 */
	protected String ftpFileDirectory = "";
	
	/**
	 * 文件存放目录
	 */
	protected String fileDir = "";

	
	/**
	 * 是否执行了内容数据导入
	 */
	protected boolean isImputDate = false;

	/**
	 * 总个数
	 */
	protected int countNum = 0;
	
	/**
	 * 新增个数
	 */
	protected int addNum = 0;
	
	/**
	 * 修改个数
	 */
	protected int modifyNum = 0;
	
	/**
	 * 删除个数
	 */
	protected int deleteNum = 0;

	/**
	 * 数据文件格式中定义的间隔符
	 */
	protected String dataSpacers = "";
	
	/**
	 * 同步开始时间
	 */
	protected Date startDate = null;
	/**
	 * 成功处理的个数
	 */
	protected int successAdd = 0;
	/**
	 * 失败处理的个数
	 */
	protected int failureProcess = 0;
	
	/**
	 * 校验失败的个数
	 */
	protected int failureCheck = 0;
	/**
	 * 写入失败邮件条数
	 */
	protected int failMailTextNum = 0;
	
	/**
	 * 是否还写入失败邮件
	 */
	protected boolean isMailText = true;
	
	/**
	 * 邮件信息
	 */
	protected StringBuffer mailText = new StringBuffer();

	/**
	 * 同步文件总信息
	 */
	protected StringBuffer syncMailText = new StringBuffer();

	/**
	 * 数据列表
	 */
	protected static List<String> dataList = Collections.synchronizedList(new ArrayList<String>());

	/**
	 * 主键MAP
	 */
	protected static Map<String, String> appKeyMap = Collections
			.synchronizedMap(new HashMap<String, String>());
	
	/**
	 * 用于添加准备动作数据
	 */
	public void init() {
		localDir = AppInfoConfig.LOCALDIR;
		dataSpacers = getDataSpacers();
		ftpFileDirectory=AppInfoConfig.ftpFileDirectory;
		fileDir="appinfo";
	}

	/**
	 * 返回字段分隔符
	 * 
	 * @return
	 */
	public String getDataSpacers()
	{
		return "\\|";
	}
	/**
	 * 用于得到数据文件列表
	 * 
	 * @param fileName
	 *            模糊文件名
	 * @return
	 */
	protected List<String> getDataFileList(String fileName) throws BOException
	{
		List<String> fileList = new ArrayList<String>();
		
		FTPClient ftp = getFTPClient();
		
		try
		{
			// 得到目录下文件列表
			String[] ftpFileList = ftp.dir();
			
			if (logger.isDebugEnabled())
			{
				logger.debug("匹配文件名开始：" + fileName);
			}
			
			for (int j = 0; j < ftpFileList.length; j++)
			{
				String tempFileName = ftpFileList[j];
				
				// 匹配文件名是否相同
				if (BaseFileNewTools.isMatchFileName(fileName, tempFileName))
				{
					// 得到本地路径
					String absolutePath = localDir + File.separator
							+ tempFileName;
					absolutePath = absolutePath.replace('\\', '/');
					ftp.get(absolutePath, ftpFileList[j]);
					
					// 存入结果集
					fileList.add(absolutePath);
					
					if (logger.isDebugEnabled())
					{
						logger.debug("成功下载文件：" + absolutePath);
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
	 * 用于得到数据文件列表
	 * 
	 * @param fileName
	 *            模糊文件名
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
				logger.debug("匹配文件名开始：" + fileName);
			}

			String remoteFilePath =localDir + File.separator
	           + fileName;
			//判断FTP是否存在文件
			remoteFilePath = remoteFilePath.replace('\\', '/');
			// 得到本地路径
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
	 *@desc 上传文件到资源服务器
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
			throw new BOException("上传到资源服务器出现异常。", e,
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
	 * 得到当前FTP连接对象
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
			// 进入下载路径
			if (!"".equals(ftpDir))
			{
				ftp.chdir(ftpDir);
			}
		}
		catch (Exception e)
		{
			mailText.append("FTP配置出错，FTP链接出错！！！<br>");
			logger.error(e);
		}
		return ftp;
	}
	
	/**
	 * 写入错误信息
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
			syncMailText.append("由于数据过大，以下不提供详情...").append("<br>");
			isMailText = false;
		}
		else
		{
			syncMailText.append("数据内容第").append(lineNum).append(
					"条，此数据有错原因为：").append(reasonText).append("<br>");
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
	 * 实现类实现校验字段的正确性
	 * 
	 * @param data
	 *            数据信息
	 * @param flag
	 *            标志位
	 * @return 返回正确/返回错误信息
	 */
	protected abstract List<AppInfoVO> checkData(Object object);
	
	/**
	 * 实现类实现解析xml文件内容到对象
	 * 
	 * @param data
	 *            数据信息
	 * @return 返回
	 */
	protected abstract Object getObject(Element root);
	
	/**
	 * 实现类实现将对象转换成sql参数的Object[][]
	 * 
	 * @param object
	 *            数据信息
	 * @return 返回
	 */
	protected abstract Object[][] getObjectParas(Object object);
	
	/**
	 * 实现类实现返回执行预删除当前数据的key值
	 * 
	 * @param data
	 *            数据信息
	 * @return 返回当前数据的key值
	 */
	protected String[] getDelKey(String key)
	{
		return key.split("\\|", -1);
	}
	
	/**
	 * 实现类实现返回添加当前业务的sql语句
	 * 
	 * @return sql语句
	 */
	protected abstract String getInsertSqlCode();
	
	/**
	 * 实现类实现返回添加当前业务的sql语句,适用多组sql语句
	 * 
	 * @return sql语句
	 */
	protected abstract Object[] getInsertSqlCodes();
	
	/**
	 * 实现类实现返回更新当前业务的sql语句
	 * 
	 * @return sql语句
	 */
	protected abstract String getUpdateSqlCode();
	
	/**
	 * 实现类实现返回更新当前业务的sql语句
	 * 
	 * @return sql语句
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
