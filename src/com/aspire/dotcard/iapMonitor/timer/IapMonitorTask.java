package com.aspire.dotcard.iapMonitor.timer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TimerTask;

import com.aspire.common.db.DAOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseVideoNew.bo.BaseVideoNewFileBO;
import com.aspire.dotcard.iapMonitor.bo.IapMonitorBO;
import com.aspire.dotcard.iapMonitor.config.IapMonitorConfig;
import com.aspire.dotcard.iapMonitor.dao.IapMonitorDAO;
import com.aspire.ponaadmin.web.mail.Mail;

public class IapMonitorTask extends TimerTask{

	/**
	 * 日志引用
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(IapMonitorTask.class);
	
	/**
	 * 执行任务（业务同步和内容同步）
	 */
	public void run()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("开始...");
		}
		
		/*String mailTitle = "监控重点系列指定时间（一周）内和娱乐IAP榜单数量";
		String[] mailTo = IapMonitorConfig.MAILTO;
		String categoryId1 = IapMonitorConfig.categoryId1;
		String deviceId1 = IapMonitorConfig.deviceId1;
		String categoryId2 = IapMonitorConfig.categoryId2;
		String deviceId2 = IapMonitorConfig.deviceId2;
		int countNum1 = 0;
		int countNum2 = 0;
		// 得到当前将要同步的任务
		try
		{
			countNum1 = IapMonitorDAO.getInstance().queryCountNum(categoryId1,deviceId1);
			countNum2 = IapMonitorDAO.getInstance().queryCountNum(categoryId2,deviceId2);
			
		}
		catch (DAOException e)
		{
			logger.error("重点系列指定时间（一周）内和娱乐IAP榜单数量时发生了错误 ！", e);
			return;
		}*/
		
		// 发送具体操作消息
		
		//  发送邮件
		/*if (countNum1 < IapMonitorConfig.countNum)
		{
			Mail.sendMail(IapMonitorConfig.mailTitle1, IapMonitorConfig.mailContent1.replace("{1}", countNum1+"").replace("{2}", IapMonitorConfig.countNum+"")+"<br>对应货架编码ID:"+categoryId1+",重点系列机型ID:"+deviceId1, mailTo);
		}
		if(countNum2 < IapMonitorConfig.countNum)
		{
			Mail.sendMail(IapMonitorConfig.mailTitle2, IapMonitorConfig.mailContent2.replace("{1}", countNum2+"").replace("{2}", IapMonitorConfig.countNum+"")+"<br>对应货架编码ID:"+categoryId2+",重点系列机型ID:"+deviceId2, mailTo);
		}*/
		//监控重点系列指定时间（一周）内和娱乐IAP榜单数量-9折优惠区
		IapMonitorBO.getInstance().execution("1");
		//监控重点系列指定时间（一周）内和娱乐IAP榜单数量-MM币消费区
		IapMonitorBO.getInstance().execution("2");
		
		if (logger.isDebugEnabled())
		{
			logger.debug("每天监控重点系列指定时间（一周）内和娱乐IAP榜单数量结束...");
		}
	}

	public static void main(String[] asgr){
		String ss = "MM币消费区货架重点系列和娱乐IAP榜单数量:{1}，少过指定数量（{2}个）";
		System.out.println(ss.replace("{1}", "0").replace("{2}", "10"));
		StringBuffer sb = new StringBuffer();
		sb.append("MM币消费区货架重点系列和娱乐IAP榜单数");
		sb.append("sfdadfaskj123421");
		System.out.println(sb.toString());
		sb.delete(0, sb.length());
		sb.append("123456789");
		System.out.println(sb.toString());
		Map<String, String> collectNodeIdMap = new HashMap<String, String>();
		collectNodeIdMap.put("123", "");
		collectNodeIdMap.put("124", "");
		System.out.println(collectNodeIdMap.get("123"));
		collectNodeIdMap.put("123", "0");
		Iterator<String> ite = collectNodeIdMap.keySet().iterator();
		while (ite.hasNext())
		{
			String key = ite.next();
			if (!"0".equals(collectNodeIdMap.get(key)))
			{
				// 此条记录为没被使用过的，可以列入删除行列
				System.out.println(key + ":"+collectNodeIdMap.get(key));
			}
		}
		System.out.println(collectNodeIdMap.get("123"));
		String str = "2313?sfdf??123";
		int a = 63;
		char r = (char) a;
		System.out.println("-----:"+str.split("?").length);
	} 
}
