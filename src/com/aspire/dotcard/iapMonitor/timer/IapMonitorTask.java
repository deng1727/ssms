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
	 * ��־����
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(IapMonitorTask.class);
	
	/**
	 * ִ������ҵ��ͬ��������ͬ����
	 */
	public void run()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("��ʼ...");
		}
		
		/*String mailTitle = "����ص�ϵ��ָ��ʱ�䣨һ�ܣ��ں�����IAP������";
		String[] mailTo = IapMonitorConfig.MAILTO;
		String categoryId1 = IapMonitorConfig.categoryId1;
		String deviceId1 = IapMonitorConfig.deviceId1;
		String categoryId2 = IapMonitorConfig.categoryId2;
		String deviceId2 = IapMonitorConfig.deviceId2;
		int countNum1 = 0;
		int countNum2 = 0;
		// �õ���ǰ��Ҫͬ��������
		try
		{
			countNum1 = IapMonitorDAO.getInstance().queryCountNum(categoryId1,deviceId1);
			countNum2 = IapMonitorDAO.getInstance().queryCountNum(categoryId2,deviceId2);
			
		}
		catch (DAOException e)
		{
			logger.error("�ص�ϵ��ָ��ʱ�䣨һ�ܣ��ں�����IAP������ʱ�����˴��� ��", e);
			return;
		}*/
		
		// ���;��������Ϣ
		
		//  �����ʼ�
		/*if (countNum1 < IapMonitorConfig.countNum)
		{
			Mail.sendMail(IapMonitorConfig.mailTitle1, IapMonitorConfig.mailContent1.replace("{1}", countNum1+"").replace("{2}", IapMonitorConfig.countNum+"")+"<br>��Ӧ���ܱ���ID:"+categoryId1+",�ص�ϵ�л���ID:"+deviceId1, mailTo);
		}
		if(countNum2 < IapMonitorConfig.countNum)
		{
			Mail.sendMail(IapMonitorConfig.mailTitle2, IapMonitorConfig.mailContent2.replace("{1}", countNum2+"").replace("{2}", IapMonitorConfig.countNum+"")+"<br>��Ӧ���ܱ���ID:"+categoryId2+",�ص�ϵ�л���ID:"+deviceId2, mailTo);
		}*/
		//����ص�ϵ��ָ��ʱ�䣨һ�ܣ��ں�����IAP������-9���Ż���
		IapMonitorBO.getInstance().execution("1");
		//����ص�ϵ��ָ��ʱ�䣨һ�ܣ��ں�����IAP������-MM��������
		IapMonitorBO.getInstance().execution("2");
		
		if (logger.isDebugEnabled())
		{
			logger.debug("ÿ�����ص�ϵ��ָ��ʱ�䣨һ�ܣ��ں�����IAP����������...");
		}
	}

	public static void main(String[] asgr){
		String ss = "MM�������������ص�ϵ�к�����IAP������:{1}���ٹ�ָ��������{2}����";
		System.out.println(ss.replace("{1}", "0").replace("{2}", "10"));
		StringBuffer sb = new StringBuffer();
		sb.append("MM�������������ص�ϵ�к�����IAP����");
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
				// ������¼Ϊû��ʹ�ù��ģ���������ɾ������
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
