package com.aspire.ponaadmin.web.dataexport.channel.task;

import java.io.File;
import java.io.FileFilter;
import java.util.Date;
import java.util.TimerTask;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.dataexport.channel.ChannelConfig;
import com.aspire.ponaadmin.web.dataexport.channel.ZipUtil;
import com.aspire.ponaadmin.web.dataexport.channel.bo.ThirdChannelMkBo;
import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.util.DateUtil;

public class ThirdChannelMkTask extends TimerTask {
	protected static JLogger logger = LoggerFactory.getLogger(ThirdChannelMkTask.class);
	/**
	 * ����ѹ������
	 */
	public void run() {
		
		//ȡ����
		logger.debug("��ʼ��������������Ӫ������");
		StringBuffer sb = new StringBuffer();
		String toDay = DateUtil.formatDate(new Date(), "yyyyMMdd");
		String localDir = ChannelConfig.LOCALDIR+File.separator+toDay;
		File f = new File(localDir);
		if(!f.exists()){
			f.mkdirs();
		}
		String begin = DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
		//����WWW���� Ӧ��������Ϣ
		ThirdChannelMkBo.getInstance().exportWwwZx(sb,localDir);
				
		//����WWW���� Ӧ��������Ϣ
		ThirdChannelMkBo.getInstance().exportWwwZr(sb,localDir);
		
		//����WWWС���Ƽ�  Ӧ��������Ϣ
		ThirdChannelMkBo.getInstance().exportWwwXbtj(sb,localDir);
		
		//����WAP���� Ӧ��������Ϣ
		ThirdChannelMkBo.getInstance().exportWapZx(sb,localDir);
		
		//����WAP���� Ӧ��������Ϣ
		ThirdChannelMkBo.getInstance().exportWapZr(sb,localDir);
		
		//����WAPС���Ƽ�  Ӧ��������Ϣ
		ThirdChannelMkBo.getInstance().exportWapXbtj(sb,localDir);
		
		//�г��ļ���ѹ��
		File[] filese = f.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				if(pathname.getName().toLowerCase().endsWith(".csv")){
					return true;
				}
				return false;
			}
		});
		String end = DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
		logger.debug("��������������Ӫ�����ݿ�ʼʱ�䣺"+begin+"������ʱ�䣺"+end);
		StringBuffer mailContent = new StringBuffer();
		mailContent.append("��������������Ӫ�����ݿ�ʼʱ�䣺"+begin+"������ʱ�䣺"+end);
		mailContent.append("<br/>");
		mailContent.append(sb.toString());
		String mailTitle = "����������Ӫ�����ݵ������";		
		File zipFile  = null;
		if(filese.length!=0){
			//ѹ��
			zipFile = new File(localDir+File.separator+toDay+".zip");
			ZipUtil.compress(zipFile, localDir,"*.csv");
		
			Mail.sendMail(mailTitle, mailContent.toString(), ChannelConfig.mailTo, new File[]{zipFile});
		}else{
			//�޸���
			Mail.sendMail(mailTitle, mailContent.toString(), ChannelConfig.mailTo);
		}
		logger.debug("������������������Ӫ������");
	}

}
