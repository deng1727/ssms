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
	 * ��־����
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(AppMonitorTask.class);
	
	/**
	 * �ص�Ӧ�ü������
	 */
	public void run()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("�ص�Ӧ�ü�ؿ�ʼ...");
		}
		int index = AppMonitorConfig.mailTitle.indexOf("-");
		String dataType = AppMonitorConfig.mailTitle.substring(index + 1);
		//�ʼ�����
		String title = AppMonitorConfig.mailTitle.substring(0,index+1)+PublicUtil.getCurDateTime(dataType);
		//��ؽ���ļ�
		String fileAllName = ServerInfo.getAppRootPath() + File.separator
                + "ftpdata" + File.separator + "monitor"+ File.separator
                + title + ".xlsx";
		StringBuffer mailContent = new StringBuffer();
		mailContent.append(AppMonitorConfig.mailContent);
		mailContent.append("<br>");
		mailContent.append("   ").append(PublicUtil.getCurDateTime("yyyy��MM��dd��")).append(getWeekOfDate(new Date()));
		AppMonitorDAO.getInstance().updateMonitorForInit();  //�Ƚ���������ݳ�ʼ��,�ѱ����appid�ֶ�����
		//��ȡ�ص�Ӧ�ü���б�
		List<MonitorContentVO> monitorContentList = AppMonitorDAO.getInstance().getMonitorContentIDList();;
		
		if(monitorContentList == null || monitorContentList.size() < 1){
			//û����Ҫ��ص�����
			mailContent.append("��ǰû���ص���Ӧ�ã���ȷ���ص���Ӧ���Ƿ���ӵ�����б�");
			Mail.sendMail(title,mailContent.toString(), AppMonitorConfig.MAILTO,AppMonitorConfig.MAILCC,null);
		}else{
			
			try {
				//��ʼ������
				AppMonitorBO.getInstance().init();
				//���ܼ�ش���
				AppMonitorBO.getInstance().monitorCategory(monitorContentList);
				//�������ļ�ش���
				AppMonitorBO.getInstance().monitorDataCenter(monitorContentList);
				//������ش���
				AppMonitorBO.getInstance().monitorMMSearch(monitorContentList);
				//��������
				AppMonitorBO.getInstance().clear();
				//����Ӧ�ü�ؽ�����еİ汾versionname��Ӧ�ø���ʱ���ֶ�
				AppMonitorDAO.getInstance().updateMonitorResult();
				//���ɼ�ؽ���ļ�
				//fileAllName = new String(fileAllName.getBytes(),"");
				AppMonitorBO.getInstance().createFile(fileAllName);
				
				mailContent.append("�ص�Ӧ�ü���ձ��ѳ�������ո�������л��");
				Mail.sendMail(title,mailContent.toString(), AppMonitorConfig.MAILTO,AppMonitorConfig.MAILCC,new File[]{new File(fileAllName)});
			
			} catch (Exception e) {
				logger.error("�ص�Ӧ�ü�������ǰ�...",e);
			}finally{
				monitorContentList.clear();
			}
		}
		
		
		if (logger.isDebugEnabled())
		{
			logger.debug("�ص�Ӧ�ü����������...");
		}
	}

	/** 
     * ��ȡ��ǰ�������ܼ� 
     * 
     * @param date 
     * @return ��ǰ�������ܼ� 
     */ 
    public static String getWeekOfDate(Date date) { 
        String[] weekDays = {"�����գ�", "����һ��", "���ܶ���", "��������", "�����ģ�", "�����壩", "��������"}; 
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
