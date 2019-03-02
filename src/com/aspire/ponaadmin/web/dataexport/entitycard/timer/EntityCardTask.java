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
		logger.debug("实体卡平台局数据导出开始");
		String begin = DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
		StringBuffer sb = new StringBuffer();
		
		//导出AP信息
		EntityCardBo.getInstance().exportIncrementAPData(sb);
		
		//导出AP业务代码数据信息
		EntityCardBo.getInstance().exportIncrementAPOperData(sb);
		
		String end = DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
		logger.debug("实体卡平台局数据导出开始时间："+begin+"，结束时间："+end);
		StringBuffer mailContent = new StringBuffer();
		mailContent.append("实体卡平台局数据导出开始时间："+begin+"，结束时间："+end);
		mailContent.append("<br/>");
		mailContent.append(sb.toString());
		String mailTitle = "实体卡平台局数据导出结果";
		Mail.sendMail(mailTitle, mailContent.toString(), EntityCardConfig.mailTo);
		
		logger.debug("实体卡平台局数据导出结束");
	}

}
