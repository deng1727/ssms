/**
 * SSMS
 * com.aspire.ponaadmin.web.dataexport.experience ExperienceBO.java
 * Jul 9, 2010
 * @author tungke
 * @version 1.0
 *
 */
package com.aspire.ponaadmin.web.dataexport.experience;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.dataexport.DataExportTools;
import com.aspire.ponaadmin.web.dataexport.experience.ext.AllAppExperience;
import com.aspire.ponaadmin.web.dataexport.experience.ext.AppExperience;
import com.aspire.ponaadmin.web.dataexport.experience.ext.AppUpdateExperience;
import com.aspire.ponaadmin.web.dataexport.experience.ext.CategoryAppExperience;
import com.aspire.ponaadmin.web.dataexport.experience.ext.DeviceExperience;
import com.aspire.ponaadmin.web.dataexport.experience.task.ExperienceTask;
import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * @author tungke
 *手动执行 体验营销 数据导出 功能类
 */
public class ExperienceBO
{
	private static final JLogger LOG = LoggerFactory.getLogger(ExperienceTask.class);

    private List sucessList = new ArrayList();

    private List failureList = new ArrayList();

    private Date startDate;
    
    
    
    
    /**
     * 
     * 
     * 手动 体验营销全量数据 导出
     * @param opType
     * 0, 全部导出
     * 1，应用分类信息数据导出
     * 2，增量终端适配信息导出
     * 3，全量终端适配信息导出
     * 4，榜单应用数据导出
     * 5，增量应用信息同步导出
     * 6，全量应用信息同步导出
     * 7，应用信息更新数据同步导出
     * 
     */
	public void fullExport(String opType){
		
//		 TODO Auto-generated method stub
		 if (LOG.isDebugEnabled())
	        {
	            LOG.debug("手动开始生成体验营销平台文件");
	        }
	        startDate = new Date();
	        sucessList.clear();
	        failureList.clear();
		 String message = "";
	        boolean result = false;
	        
	        
	       
	        
	        boolean cateType = false;//应用分类信息
	        boolean addDeviceType = false;//增量终端适配
	        boolean fullDeviceType = false;//全量终端适配
	        boolean appType = false;//榜单信息
	        boolean addAllAppType = false;//增量应用信息
	        boolean fullAllAppType = false;//全量应用信息
	        
	        boolean appUpdateType = false;//应用信息更新
	        
	        if(opType != null && opType.equals("0")){
	        	//0, 全部增量导出
	        	cateType = true;
	        	addDeviceType = true;
	        	appType = true;
	        	addAllAppType = true;
	        	appUpdateType = true;
	        }else if(opType != null && opType.equals("1")){
	         	//1，应用分类信息数据导出
	        	cateType = true;
	        	
	        }else if(opType != null && opType.equals("2")){
//	        	2，增量终端适配信息导出
	        	addDeviceType = true;
	        	
	        }else if(opType != null && opType.equals("3")){
//	        	3，全量终端适配信息导出
	        	fullDeviceType = true;
	        	
	        }else if(opType != null && opType.equals("4")){
//	        	4，榜单应用数据导出
	        	appType = true;
	        	
	        }else if(opType != null && opType.equals("5")){
//	        	5，增量应用信息同步导出
	        	addAllAppType = true;
	        	
	        }else if(opType != null && opType.equals("6")){
//	        	6，全量应用信息同步导出
	        	fullAllAppType = true;
	        }else if(opType != null && opType.equals("7")){
//	        	7，应用信息更新数据同步导出
	        	appUpdateType = true;
	        	
	        }
	        try
	        {
			CommonExperience appexport = null;
			CreateFileByExperience exporter = new CreateFileByExperience();
			if (cateType)
			{
				// 应用分类信息
				appexport = new CategoryAppExperience();
				result = exporter.export(appexport);
				if (result)
				{
					message = "应用分类信息数据同步  成功";
					sucessList.add(message);
				}
				else
				{
					message = "应用分类信息数据同步  失败";
					failureList.add(message);
				}
			}
			if (addDeviceType)
			{
				// 增量终端适配
				appexport = new DeviceExperience();
				result = exporter.export(appexport);

				if (result)
				{
					message = "增量终端适配应用信息同步  成功";
					sucessList.add(message);
				}
				else
				{
					message = "增量终端适配应用信息同步  失败";
					failureList.add(message);
				}
			}
			if (fullDeviceType)
			{
				// 全量终端适配
				appexport = new DeviceExperience();
				appexport.setConstraint("1");//1,全量；0，增量
				appexport.fileName = DataExportTools.parseFileName(ExperienceConfig.FullDeviceName);
				 if(!CommonExperience.FileWriteDIR.endsWith(File.separator)){
					 appexport.exportedFileName = CommonExperience.FileWriteDIR + File.separator +  appexport.fileName;
			        }else{
			        	appexport.exportedFileName = CommonExperience.FileWriteDIR +  appexport.fileName;
			        }
				result = exporter.export(appexport);

				if (result)
				{
					message = "全量终端适配应用信息同步  成功";
					sucessList.add(message);
				}
				else
				{
					message = "全量终端适配应用信息同步  失败";
					failureList.add(message);
				}
			}
			if (appType)
			{
				appexport = new AppExperience();
				result = exporter.export(appexport);

				if (result)
				{
					message = "榜单应用信息同步  成功";
					sucessList.add(message);
				}
				else
				{
					message = "榜单应用信息同步  失败";
					failureList.add(message);
				}
			}
			if (addAllAppType)
			{
				appexport = new AllAppExperience();
				result = exporter.export(appexport);

				if (result)
				{
					message = "增量应用信息同步  成功";
					sucessList.add(message);
				}
				else
				{
					message = "增量应用信息同步  失败";
					failureList.add(message);
				}
			}
			if (fullAllAppType)
			{
				appexport = new AllAppExperience();
				appexport.setConstraint("1");//1,全量；0，增量
				appexport.fileName = DataExportTools.parseFileName(ExperienceConfig.APPFullName);
				if(!CommonExperience.FileWriteDIR.endsWith(File.separator)){
					 appexport.exportedFileName = CommonExperience.FileWriteDIR + File.separator +  appexport.fileName;
			        }else{
			        	appexport.exportedFileName = CommonExperience.FileWriteDIR +  appexport.fileName;
			        }
				result = exporter.export(appexport);
				
				if (result)
				{
					message = "全量应用信息同步  成功";
					sucessList.add(message);
				}
				else
				{
					message = "全量应用信息同步  失败";
					failureList.add(message);
				}
			}
			if (appUpdateType)
			{
				appexport = new AppUpdateExperience();
				result = exporter.export(appexport);

				if (result)
				{
					message = "应用信息更新数据同步  成功";
					sucessList.add(message);
				}
				else
				{
					message = "应用信息更新数据同步  失败";
					failureList.add(message);
				}
			}
		}
	        catch (Exception e)
	        {

	        }
	        sendResultMail(true, null);  
	}
	  /**
		 * 发送结果邮件。
		 */
   private void sendResultMail(boolean result, String reason)
   {

       String mailTitle;
       // 发送邮件表示本次处理结束
       Date endDate = new Date();
       StringBuffer sb = new StringBuffer();
       int totalSuccessCount = sucessList.size();
       int totalFailureCount = failureList.size();
       mailTitle = "手动体验营销数据文件生成结果";
       if (result)
       {

           sb.append("开始时间：");
           sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
           sb.append(",结束时间：");
           sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
           sb.append("。<h4>处理结果：</h4>");
           sb.append("<p>其中成功导出<b>");
           sb.append(totalSuccessCount);
           sb.append("</b>个文件。");
           sb.append("失败导出<b>");
           sb.append(totalFailureCount);
           sb.append("</b>个文件。");
           if (totalSuccessCount != 0)
           {
               sb.append("<p>具体信息为：<p>");

               for (int i = 0; i < sucessList.size(); i++)
               {
                   sb.append(sucessList.get(i));
                   sb.append("<br>");
               }
               sb.append("<br>");
           }

           for (int i = 0; i < failureList.size(); i++)
           {
               sb.append(failureList.get(i));
               sb.append("<br>");
           }

       }
       else
       {
           sb.append("开始时间：");
           sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
           sb.append(",结束时间：");
           sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
           sb.append("。<p>失败原因：<br>");
           sb.append(reason);
       }

       LOG.info(sb.toString());
       Mail.sendMail(mailTitle, sb.toString(), ExperienceConfig.mailTo);
   }
	
	
}
