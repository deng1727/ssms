package com.aspire.dotcard.appmonitor.timer;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import com.aspire.common.config.ServerInfo;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.appmonitor.bo.AppMonitorBO;
import com.aspire.dotcard.appmonitor.config.AppMonitorConfig;
import com.aspire.dotcard.appmonitor.dao.AppMonitorDAO;
import com.aspire.dotcard.appmonitor.vo.MonitorContentVO;
import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.util.PublicUtil;

public class AppMonitorTask extends TimerTask{

	/**
	 * 日志引用
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(AppMonitorTask.class);
	
	/**
	 * 重点应用监控启动
	 */
	public void run()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("重点应用监控开始...");
		}
		int index = AppMonitorConfig.mailTitle.indexOf("-");
		String dataType = AppMonitorConfig.mailTitle.substring(index + 1);
		//邮件标题
		String title = AppMonitorConfig.mailTitle.substring(0,index+1)+PublicUtil.getCurDateTime(dataType);
		//监控结果文件
		String fileAllName = ServerInfo.getAppRootPath() + File.separator
                + "ftpdata" + File.separator + "monitor"+ File.separator
                + title + ".xlsx";
		StringBuffer mailContent = new StringBuffer();
		mailContent.append(AppMonitorConfig.mailContent);
		mailContent.append("<br>");
		mailContent.append("   ").append(PublicUtil.getCurDateTime("yyyy年MM月dd日")).append(getWeekOfDate(new Date()));
		AppMonitorDAO.getInstance().updateMonitorForInit();  //先将导入的数据初始化,把表里的appid字段填充好
		//获取重点应用监控列表
		List<MonitorContentVO> monitorContentList = AppMonitorDAO.getInstance().getMonitorContentIDList();;
		
		if(monitorContentList == null || monitorContentList.size() < 1){
			//没有需要监控的内容
			mailContent.append("当前没有重点监控应用，请确认重点监控应用是否添加到监控列表。");
			Mail.sendMail(title,mailContent.toString(), AppMonitorConfig.MAILTO,AppMonitorConfig.MAILCC,null);
		}else{
			
			try {
				//初始化数据
				AppMonitorBO.getInstance().init();
				//货架监控处理
				AppMonitorBO.getInstance().monitorCategory(monitorContentList);
				//数据中心监控处理
				AppMonitorBO.getInstance().monitorDataCenter(monitorContentList);
				//搜索监控处理
				AppMonitorBO.getInstance().monitorMMSearch(monitorContentList);
				//清理数据
				AppMonitorBO.getInstance().clear();
				//更新应用监控结果表中的版本versionname、应用更新时间字段
				AppMonitorDAO.getInstance().updateMonitorResult();
				//生成监控结果文件
				//fileAllName = new String(fileAllName.getBytes(),"");
				AppMonitorBO.getInstance().createFile(fileAllName);
				
				mailContent.append("重点应用监控日报已出，请查收附件，感谢。");
				Mail.sendMail(title,mailContent.toString(), AppMonitorConfig.MAILTO,AppMonitorConfig.MAILCC,new File[]{new File(fileAllName)});
			
			} catch (Exception e) {
				logger.error("重点应用监控任务是吧...",e);
			}finally{
				monitorContentList.clear();
			}
		}
		
		
		if (logger.isDebugEnabled())
		{
			logger.debug("重点应用监控启动结束...");
		}
	}

	/** 
     * 获取当前日期是周几 
     * 
     * @param date 
     * @return 当前日期是周几 
     */ 
    public static String getWeekOfDate(Date date) { 
        String[] weekDays = {"（周日）", "（周一）", "（周二）", "（周三）", "（周四）", "（周五）", "（周六）"}; 
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) 
            w = 0; 
        return weekDays[w];
    }
	
	public static void main(String[] asgr){
		System.out.println(getWeekOfDate(new Date()));
	} 
}
