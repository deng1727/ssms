package com.aspire.ponaadmin.web.dataexport.entitycard.timer;

import java.util.Date;
import java.util.TimerTask;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.dataexport.entitycard.EntityCardConfig;
import com.aspire.ponaadmin.web.dataexport.entitycard.biz.EntityCardBo;
import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.util.DateUtil;

public class EntityCardTask extends TimerTask {
	private static JLogger logger = LoggerFactory.getLogger(EntityCardTask.class);
	public void run() {
		logger.debug("ʵ�忨ƽ̨�����ݵ�����ʼ");
		String begin = DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
		StringBuffer sb = new StringBuffer();
		
		//����AP��Ϣ
		EntityCardBo.getInstance().exportIncrementAPData(sb);
		
		//����APҵ�����������Ϣ
		EntityCardBo.getInstance().exportIncrementAPOperData(sb);
		
		String end = DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
		logger.debug("ʵ�忨ƽ̨�����ݵ�����ʼʱ�䣺"+begin+"������ʱ�䣺"+end);
		StringBuffer mailContent = new StringBuffer();
		mailContent.append("ʵ�忨ƽ̨�����ݵ�����ʼʱ�䣺"+begin+"������ʱ�䣺"+end);
		mailContent.append("<br/>");
		mailContent.append(sb.toString());
		String mailTitle = "ʵ�忨ƽ̨�����ݵ������";
		Mail.sendMail(mailTitle, mailContent.toString(), EntityCardConfig.mailTo);
		
		logger.debug("ʵ�忨ƽ̨�����ݵ�������");
	}

}
