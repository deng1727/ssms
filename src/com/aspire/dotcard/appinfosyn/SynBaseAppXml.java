package com.aspire.dotcard.appinfosyn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.Servlet;

import org.dom4j.Element;


import com.aspire.common.config.ServerInfo;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.threadtask.ReflectedTask;
import com.aspire.common.threadtask.TaskRunner;
import com.aspire.dotcard.appinfosyn.config.AppInfoConfig;
import com.aspire.dotcard.appinfosyn.dao.AppInfoSynDAO;
import com.aspire.dotcard.basemusic.config.BaseMusicConfig;
import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.util.PublicUtil;


public class SynBaseAppXml {

private JLogger logger = LoggerFactory.getLogger(SynBaseAppXml.class);
protected int getDateNum = 0;
/**
 * 手动设置调整导出日期
 */
protected Calendar setTime = null;
/**
 * 取文件的时间，0：为当小时，1：为前小时，2：为前两小时
 */
protected int getTimeNum = 0;

protected boolean isByHour=false;

	private String xmlFileName = "APP_~DyyyyMMdd~_[0-9]{4}.xml";
	private String tarFileName = "APP_~DyyyyMMdd~_[0-9]{4}.tar.gz";
	  /**
     * singleton模式的实例
     */
    private TaskRunner updateTaskRunner;
    private TaskRunner insertRefTaskRunner;
	/**
	 * 调用执行任务类
	 */
	private BaseExportXmlFile baseXml;
	/**
	 * 文件存放的本地路径
	 */
	protected String localDir = "";

    StringBuffer msgInfo = new StringBuffer();

    Date startDate;


	public SynBaseAppXml(
		 BaseExportXmlFile base)
			throws InstantiationException, IllegalAccessException
	{
		this.localDir = AppInfoConfig.LOCALDIR;
		this.baseXml = base;
		this.getDateNum= AppInfoConfig.GET_DATE_NUM;
	}
	
	/**
	 * 用于被多线程调用的具体执行方法
	 * 
	 * @param lineText
	 * @param tempFileName
	 */
	public void sysDataByXml()
	{
		 startDate= new Date();
        baseXml.init();
		String exportDBText;
		List<String> fileList = new ArrayList<String>();
			// 转义模糊文件名中的日期, 得到文件列表
		
				try {
					fileList = baseXml.getDataFileList(BaseFileTools
							.fileNameDataChange(tarFileName, getDateNum, setTime,isByHour,getTimeNum));
				} catch (BOException e1) {
					logger.debug("获取文件列表失败!");
				}
		
		//解压压缩文件
		try {
			for(int i=0;i<fileList.size();i++){
				BaseFileNewTools.ungzip(fileList.get(i),localDir);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.debug("解压压缩失败!");
		}
	
			//获取和处理节目详情xml文件内容
	    	exportDBText = exportXmlDataToDBByApp();
			if("success".equals(exportDBText)){
				logger.debug("处理成功!");
			}else{
				logger.debug("处理失败!");
			}
	}
	
	/**
	 * 实现类实现把数据存入库中
	 * 
	 * @param data
	 *            数据信息
	 * @return 返回正确/返回错误信息
	 */
	private String exportXmlDataToDBByApp()
	{

	      updateTaskRunner = new TaskRunner(AppInfoConfig.AppInfoTaskNum,
	    		  AppInfoConfig.AppInfoTaskMaxReceivedNum);
		if (logger.isDebugEnabled())
		{
			logger.debug("开始存储数据至库中.");
		}
		  int successInsertNum=0;
		  int successDeleteNum=0;
		  int SucccessUpdateNum=0;
		  int appTotalNum=0;
		  int failTotalNum=0;
		  String  failAppNames="";
		  String filePath = null;
		  List<String> fileList = new ArrayList<String>();
			// 转义模糊文件名中的日期, 得到文件列表
			 try {

					fileList = baseXml.getDataFileList(BaseFileTools
							.fileNameDataChange(tarFileName, getDateNum, setTime,isByHour,getTimeNum));
				
			} catch (BOException e) {
				logger.error("获得Xml类型文件名失败",e);
			}	
	
		for(int i=0;i<fileList.size();i++){
			//取xml文件目录

			Element root = BaseFileTools.loadXML(fileList.get(i).substring(0, fileList.get(i).indexOf(".",53))+"/"+fileList.get(i).substring(fileList.get(i).indexOf(".",53)-17, fileList.get(i).indexOf(".",53))+".xml");
			if(root == null){
				return "应用详情数据文件内容为空，文件名称为："+filePath;
			}
			
			Object object = baseXml.getObject(root);

			//List<AppInfoVO> applicationInfoList = baseXml.checkData(object);
			List<AppInfoVO> applicationInfoList =(List<AppInfoVO>) object;
			
			
			for(int k=0;k<applicationInfoList.size();k++){

				if(AppInfoConfig.CHECK_FAILED.equals(baseXml.checkData2(applicationInfoList.get(k)))){
					failAppNames=failAppNames+","+applicationInfoList.get(k).getAppID();
					failTotalNum++;
					appTotalNum++;
					applicationInfoList.remove(k);
				}
			}
			
			baseXml.uplodPicture(applicationInfoList, fileList.get(i).substring(fileList.get(i).indexOf(".")-17, fileList.get(i).indexOf(".")));
			
			
			
			 if(applicationInfoList!=null){
			  for(int j=0;j<applicationInfoList.size();j++){
				  //add
				  appTotalNum++;
				  //int number = AppInfoSynDAO.getInstance().queryAppId(applicationInfoList.get(j).getAppID());
				  
				  
				  if("1".equals(applicationInfoList.get(j).getStatus())){
					  if(!baseXml.hasAppKeyMap(applicationInfoList.get(j).getAppID())){
						  this.insertDBAppInfo(applicationInfoList.get(j));
						  successInsertNum++;
					  }else{
						  this.updateAppInfo(applicationInfoList.get(j));
						  SucccessUpdateNum++;
					  }
				  }
				  
				  //update
	             if("2".equals(applicationInfoList.get(j).getStatus())){
	            	 if(!baseXml.hasAppKeyMap(applicationInfoList.get(j).getAppID())){
	            		  this.insertDBAppInfo(applicationInfoList.get(j));
						  successInsertNum++;
						
	            	 }else{
	            		  this.updateAppInfo(applicationInfoList.get(j));
						  SucccessUpdateNum++;
	            	 }
	             }
	             //delete
	             if("3".equals(applicationInfoList.get(j).getStatus())){
	            	 baseXml.delAppKeyMap(applicationInfoList.get(j).getAppID());
	            	 this.delAppInfo(applicationInfoList.get(j));
	            	 successDeleteNum++;
				  }						  
			  }
    }
			 
		}
        updateTaskRunner.waitToFinished();// 等待更新数据库完毕。
        updateTaskRunner.end();// 结束运行器
		//邮件发送
		 msgInfo.append("同步文件开始时间：").append(
					PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"))
					.append("，结束时间：").append(
							PublicUtil.getDateString(new Date(),
									"yyyy-MM-dd HH:mm:ss"));
        msgInfo.append("<br>");
        msgInfo.append("共处理应用数：" + appTotalNum);
        msgInfo.append(";<br>成功新增个数："
                       + successInsertNum);
        msgInfo.append(";<br>成功修改个数："
                       + SucccessUpdateNum);
        msgInfo.append(";<br>成功删除个数："
                       + successDeleteNum);
        msgInfo.append(";<br>验证失败个数："
                + failTotalNum);
        msgInfo.append(";<br>验证失败APPID："
                + failAppNames);
        msgInfo.append("<br>");
        sendMail(msgInfo.toString(),AppInfoConfig.mailTo,"WP应用信息入库结果");
        
        baseXml.destroy();
        baseXml.clear();
		return AppInfoConfig.CHECK_DATA_SUCCESS;
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
	  /**
     * 更新应用信息,调用多线程完成异步更新。
     */
    private void updateAppInfo(AppInfoVO vo)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("updateAppInfo() id=" + vo.getAppID());
        }
        AppInfoDBOpration cm = new AppInfoDBOpration(vo);
        // 构造异步任务
        ReflectedTask task = new ReflectedTask(cm, "updateAppInfo", null, null);
        // 将任务加到运行器中
        updateTaskRunner.addTask(task);
    }

    /**
     * 插入应用信息,调用多线程完成异步更新。
     */
    private void insertDBAppInfo(AppInfoVO vo)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("insertDBAppInfo() id=" + vo.getAppID());
        }
        AppInfoDBOpration cm = new AppInfoDBOpration(vo);
        // 构造异步任务
        ReflectedTask task = new ReflectedTask(cm, "insertAppInfo", null, null);
        // 将任务加到运行器中
        updateTaskRunner.addTask(task);
    }

    /**
     * 删除应用信息,调用多线程完成异步更新。
     */
    private void delAppInfo(AppInfoVO vo)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("delAppInfo() id=" + vo.getAppID());
        }
        AppInfoDBOpration cm = new AppInfoDBOpration(vo);
        // 构造异步任务
        ReflectedTask task = new ReflectedTask(cm, "deleteAppInfo", null, null);
        // 将任务加到运行器中
        updateTaskRunner.addTask(task);
    }	
    
    
    public static void main(String[] args) {
    	List<String> fileList = new ArrayList<String>();
    	fileList.add("/opt/aspire/product/mm_ssms/apache-tomcat-7.0.75/bin/ssms/ftpdata/baseData/appinfo/APP_20170327_0001.tar.gz");
   //fileList.add("/opt/aspire/product/mm_ssms/ssms/user_projects/domains/base_domain/AdminServer/ssms/ftpdata/baseData/appinfo/APP_20170327_0001.tar.gz");
//    	System.out.println(ServerInfo.getAppRootPath());
    	System.out.println("/opt/aspire/product/mm_ssms/apache-tomcat-7.0.75/bin/".length());
    	for(int i=0;i<fileList.size();i++)
    	{
    		System.out.println(fileList.get(i).substring(0, fileList.get(i).indexOf(".",53))+"/"+fileList.get(i).substring(fileList.get(i).indexOf(".",53)-17, fileList.get(i).indexOf(".",53))+".xml");
    	}
    	
	}
	
}
