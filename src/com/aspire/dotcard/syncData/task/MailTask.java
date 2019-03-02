package com.aspire.dotcard.syncData.task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimerTask;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.gcontent.DeviceVO;
import com.aspire.dotcard.gcontent.GContent;
import com.aspire.dotcard.syncData.bo.AddGoodsBo;
import com.aspire.dotcard.syncData.bo.DataSyncBO;
import com.aspire.dotcard.syncData.dao.AddGoodsDAO;
import com.aspire.dotcard.syncData.dao.DataSyncDAO;
import com.aspire.dotcard.syncData.tactic.TacticDAO;
import com.aspire.dotcard.syncData.util.SyncDataConstant;
import com.aspire.ponaadmin.web.category.SynCategoryMgr;
import com.aspire.ponaadmin.web.category.SynOpenOperationData;
import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.mail.MailConfig;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.CategoryBO;
import com.aspire.ponaadmin.web.repository.CategoryDeviceDAO;
import com.aspire.ponaadmin.web.repository.CategoryTools;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * 
 * @author dengshaobo
 * 
 */
public class MailTask extends TimerTask {
	/**
	 * ��־����
	 */
	protected static JLogger logger = LoggerFactory.getLogger(MailTask.class);

	/**
	 * ִ������ҵ��ͬ��������ͬ����
	 */
	public void run() {

		Date startDate = new Date();
		StringBuffer mailContent = new StringBuffer();
//		mailContent.append("��ʼʱ�䣺");
		String startTime = PublicUtil.getDateString(startDate,
				"yyyy-MM-dd HH:mm:ss");
//		mailContent.append(PublicUtil.getDateString(startDate,
//				"yyyy-MM-dd HH:mm:ss"));
		logger.debug("�Զ��ϼ�ͬ����startTime:"
				+ PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
		try {
			AddGoodsBo.getInstance().goodsServices(mailContent);
		} catch (Exception e) {
			logger.error(e);
			mailContent.append("ҵ�����,����ϵ����Ա...");
		}
		Date endDate = new Date();
//		mailContent.append("����ʱ�䣺");
		String endTime = PublicUtil.getDateString(endDate,
				"yyyy-MM-dd HH:mm:ss");
//		mailContent.append(PublicUtil.getDateString(endDate,
//				"yyyy-MM-dd HH:mm:ss"));
		StringBuffer mailContents = new StringBuffer();
		mailContents.append("��ʼʱ�䣺" + startTime + " , " + "����ʱ�䣺" + endTime + "<br>");
		mailContents.append("����������: <br>");
		mailContents.append(mailContent);
		
		this.sendMail(mailContents.toString(), SyncDataConstant.CONTENT_TYPE);
	}
	/**
	 * �����ʼ�
	 * 
	 * @param mailContent
	 *            ,�ʼ�����
	 */
	private void sendMail(String mailContent, String type) {
		if (logger.isDebugEnabled()) {
			logger.debug("sendMail(" + mailContent + "," + type + ")");
		}
		// �õ��ʼ�����������
		String[] mailTo = MailConfig.getInstance().getMailToArray();
		String subject = null;
		if (SyncDataConstant.CONTENT_TYPE.equals(type)) {
			subject = MailConfig.getInstance().getAutoUpdateSubject();
		}
		if (logger.isDebugEnabled()) {
			logger.debug("mailTo Array is:" + Arrays.asList(mailTo));
			logger.debug("mail subject is:" + subject);
			logger.debug("mailContent is:" + mailContent);
		}
		Mail.sendMail(subject, mailContent, mailTo);
	}
}
