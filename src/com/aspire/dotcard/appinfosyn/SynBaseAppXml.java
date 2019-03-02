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
 * �ֶ����õ�����������
 */
protected Calendar setTime = null;
/**
 * ȡ�ļ���ʱ�䣬0��Ϊ��Сʱ��1��ΪǰСʱ��2��Ϊǰ��Сʱ
 */
protected int getTimeNum = 0;

protected boolean isByHour=false;

	private String xmlFileName = "APP_~DyyyyMMdd~_[0-9]{4}.xml";
	private String tarFileName = "APP_~DyyyyMMdd~_[0-9]{4}.tar.gz";
	  /**
     * singletonģʽ��ʵ��
     */
    private TaskRunner updateTaskRunner;
    private TaskRunner insertRefTaskRunner;
	/**
	 * ����ִ��������
	 */
	private BaseExportXmlFile baseXml;
	/**
	 * �ļ���ŵı���·��
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
	 * ���ڱ����̵߳��õľ���ִ�з���
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
			// ת��ģ���ļ����е�����, �õ��ļ��б�
		
				try {
					fileList = baseXml.getDataFileList(BaseFileTools
							.fileNameDataChange(tarFileName, getDateNum, setTime,isByHour,getTimeNum));
				} catch (BOException e1) {
					logger.debug("��ȡ�ļ��б�ʧ��!");
				}
		
		//��ѹѹ���ļ�
		try {
			for(int i=0;i<fileList.size();i++){
				BaseFileNewTools.ungzip(fileList.get(i),localDir);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.debug("��ѹѹ��ʧ��!");
		}
	
			//��ȡ�ʹ����Ŀ����xml�ļ�����
	    	exportDBText = exportXmlDataToDBByApp();
			if("success".equals(exportDBText)){
				logger.debug("����ɹ�!");
			}else{
				logger.debug("����ʧ��!");
			}
	}
	
	/**
	 * ʵ����ʵ�ְ����ݴ������
	 * 
	 * @param data
	 *            ������Ϣ
	 * @return ������ȷ/���ش�����Ϣ
	 */
	private String exportXmlDataToDBByApp()
	{

	      updateTaskRunner = new TaskRunner(AppInfoConfig.AppInfoTaskNum,
	    		  AppInfoConfig.AppInfoTaskMaxReceivedNum);
		if (logger.isDebugEnabled())
		{
			logger.debug("��ʼ�洢����������.");
		}
		  int successInsertNum=0;
		  int successDeleteNum=0;
		  int SucccessUpdateNum=0;
		  int appTotalNum=0;
		  int failTotalNum=0;
		  String  failAppNames="";
		  String filePath = null;
		  List<String> fileList = new ArrayList<String>();
			// ת��ģ���ļ����е�����, �õ��ļ��б�
			 try {

					fileList = baseXml.getDataFileList(BaseFileTools
							.fileNameDataChange(tarFileName, getDateNum, setTime,isByHour,getTimeNum));
				
			} catch (BOException e) {
				logger.error("���Xml�����ļ���ʧ��",e);
			}	
	
		for(int i=0;i<fileList.size();i++){
			//ȡxml�ļ�Ŀ¼

			Element root = BaseFileTools.loadXML(fileList.get(i).substring(0, fileList.get(i).indexOf(".",53))+"/"+fileList.get(i).substring(fileList.get(i).indexOf(".",53)-17, fileList.get(i).indexOf(".",53))+".xml");
			if(root == null){
				return "Ӧ�����������ļ�����Ϊ�գ��ļ�����Ϊ��"+filePath;
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
        updateTaskRunner.waitToFinished();// �ȴ��������ݿ���ϡ�
        updateTaskRunner.end();// ����������
		//�ʼ�����
		 msgInfo.append("ͬ���ļ���ʼʱ�䣺").append(
					PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"))
					.append("������ʱ�䣺").append(
							PublicUtil.getDateString(new Date(),
									"yyyy-MM-dd HH:mm:ss"));
        msgInfo.append("<br>");
        msgInfo.append("������Ӧ������" + appTotalNum);
        msgInfo.append(";<br>�ɹ�����������"
                       + successInsertNum);
        msgInfo.append(";<br>�ɹ��޸ĸ�����"
                       + SucccessUpdateNum);
        msgInfo.append(";<br>�ɹ�ɾ��������"
                       + successDeleteNum);
        msgInfo.append(";<br>��֤ʧ�ܸ�����"
                + failTotalNum);
        msgInfo.append(";<br>��֤ʧ��APPID��"
                + failAppNames);
        msgInfo.append("<br>");
        sendMail(msgInfo.toString(),AppInfoConfig.mailTo,"WPӦ����Ϣ�����");
        
        baseXml.destroy();
        baseXml.clear();
		return AppInfoConfig.CHECK_DATA_SUCCESS;
	}
    /**
     * �����ʼ�
     * 
     * @param mailContent,�ʼ�����
     */
    private void sendMail(String mailContent, String[] mailTo, String subject)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("sendMail(" + mailContent + ")");
        }
        // �õ��ʼ�����������
        // String[] mailTo = MailConfig.getInstance().getMailToArray();
        // String subject = "�����������ݵ���";
        if (logger.isDebugEnabled())
        {
            logger.debug("mailTo Array is:" + Arrays.asList(mailTo));
            logger.debug("mail subject is:" + subject);
            logger.debug("mailContent is:" + mailContent);
        }
        Mail.sendMail(subject, mailContent, mailTo);
    }
	  /**
     * ����Ӧ����Ϣ,���ö��߳�����첽���¡�
     */
    private void updateAppInfo(AppInfoVO vo)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("updateAppInfo() id=" + vo.getAppID());
        }
        AppInfoDBOpration cm = new AppInfoDBOpration(vo);
        // �����첽����
        ReflectedTask task = new ReflectedTask(cm, "updateAppInfo", null, null);
        // ������ӵ���������
        updateTaskRunner.addTask(task);
    }

    /**
     * ����Ӧ����Ϣ,���ö��߳�����첽���¡�
     */
    private void insertDBAppInfo(AppInfoVO vo)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("insertDBAppInfo() id=" + vo.getAppID());
        }
        AppInfoDBOpration cm = new AppInfoDBOpration(vo);
        // �����첽����
        ReflectedTask task = new ReflectedTask(cm, "insertAppInfo", null, null);
        // ������ӵ���������
        updateTaskRunner.addTask(task);
    }

    /**
     * ɾ��Ӧ����Ϣ,���ö��߳�����첽���¡�
     */
    private void delAppInfo(AppInfoVO vo)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("delAppInfo() id=" + vo.getAppID());
        }
        AppInfoDBOpration cm = new AppInfoDBOpration(vo);
        // �����첽����
        ReflectedTask task = new ReflectedTask(cm, "deleteAppInfo", null, null);
        // ������ӵ���������
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
