package com.aspire.dotcard.basevideosync.bo;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basevideosync.config.BaseVideoConfig;
import com.aspire.dotcard.basevideosync.dao.BaseVideoDAO;
import com.aspire.dotcard.basevideosync.exportfile.BaseFileFactory;
import com.aspire.ponaadmin.web.mail.Mail;

public class BaseVideoBO {

	/**
	 * ��־����
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(BaseVideoBO.class);

	private static BaseVideoBO bo = new BaseVideoBO();

	private BaseVideoBO() {
	}

	public static BaseVideoBO getInstance() {
		return bo;
	}

	/**
	 * ����ȫ��ͬ������txt�ļ�����
	 */
	public void fileDataSync() {
		StringBuffer mailText = new StringBuffer();

		// ��Ƶ��Ŀ�ļ�����ȫ������
		mailText.append(BaseFileFactory.getInstance()
				.getBaseFile(BaseVideoConfig.FILE_TYPE_PROGRAM)
				.execution(false));

		// ҵ���Ʒ�ļ����ݵ���
		mailText.append(BaseFileFactory.getInstance()
				.getBaseFile(BaseVideoConfig.FILE_TYPE_PRD_PKG)
				.execution(false));

		// ��Ʒ�������Ʒ������ļ����ݵ���
		mailText.append(BaseFileFactory.getInstance()
				.getBaseFile(BaseVideoConfig.FILE_TYPE_PKG_SALES)
				.execution(false));

		// �Ʒ������ļ����ݵ���
		mailText.append(BaseFileFactory.getInstance()
				.getBaseFile(BaseVideoConfig.FILE_TYPE_PRODUCT)
				.execution(false));

		// �ȵ������б��ļ����ݵ���
		mailText.append(BaseFileFactory.getInstance()
				.getBaseFile(BaseVideoConfig.FILE_TYPE_HOTCONTENT)
				.execution(false));

		// �񵥷����ļ����ݵ���
		mailText.append(BaseFileFactory.getInstance()
				.getBaseFile(BaseVideoConfig.FILE_TYPE_TOPLIST)
				.execution(false));

		sendResultMail("�»�����Ƶȫ��txt�ļ�����ͬ������ʼ�", mailText);
	}

	/**
	 * ����ȫ��ͬ������xml�ļ�����
	 */
	public void xmlFileDataSync() {

		StringBuffer mailText = new StringBuffer();
		// ��Ƶ��Ŀ����xml�ļ�����ȫ������
		mailText.append(BaseFileFactory.getInstance()
				.getBaseFile(BaseVideoConfig.FILE_TYPE_PROGRAM_XML)
				.execution(false));
		// ��Ƶֱ����Ŀ��xml�ļ�����ȫ������
		mailText.append(BaseFileFactory.getInstance()
				.getBaseFile(BaseVideoConfig.FILE_TYPE_LIVE_XML)
				.execution(false));
		// ��Ƶ�ȵ������б�xml�ļ�����ȫ������
		mailText.append(BaseFileFactory.getInstance()
				.getBaseFile(BaseVideoConfig.FILE_TYPE_HOTCONTENT_XML)
				.execution(false));

		sendResultMail("�»�����Ƶȫ��xml�ļ�����ͬ������ʼ�", mailText);
	}

	/**
	 * ��������ͬ����Ŀ�ļ�����
	 */
	public void propramDataByHourSync() {

		StringBuffer mailText = new StringBuffer();
		// ������Ƶ��Ŀ����txt����ͬ��
		mailText.append(BaseFileFactory.getInstance()
				.getBaseFile(BaseVideoConfig.FILE_TYPE_PROGRAM_ADD_HOUR)
				.execution(false));
		// ��Ƶ��Ŀ����xml�ļ����ݵ���
		mailText.append(BaseFileFactory.getInstance()
				.getBaseFile(BaseVideoConfig.FILE_TYPE_PROGRAM_XML)
				.execution(false));
		// ��Ƶֱ����Ŀ��xml�ļ����ݵ���
		mailText.append(BaseFileFactory.getInstance()
				.getBaseFile(BaseVideoConfig.FILE_TYPE_LIVE_XML)
				.execution(false));

		sendResultMail("�»�����Ƶ��ͨ��Ŀ�ļ�����ͬ������ʼ�", mailText);
	}

	/**
	 * ����ɾ��ָ��������
	 * 
	 * @param sql
	 * @param key
	 * @return
	 */
	public boolean delDataByKey(String sql, String[] key) {
		boolean isTrue = true;
		try {
			BaseVideoDAO.getInstance().delDataByKey(sql, key);
		} catch (BOException e) {
			isTrue = false;
			logger.error("ִ��ɾ��ָ��������ʱ�������󣡣���", e);
		}
		return isTrue;
	}

	/**
	 * ɾ����ǰͬ��������
	 * 
	 * @return
	 */
	public void delTable(String tableName) {
		if (logger.isDebugEnabled()) {
			logger.debug("ɾ����ǰͬ��������  strat ,tablename=" + tableName);
		}

		try {
			// ɾ����ǰͬ��������
			BaseVideoDAO.getInstance()
					.truncateTable(new String[] { tableName });
		} catch (Exception e) {
			logger.error("ɾ����ǰͬ��������ʧ�ܣ�tablename=" + tableName);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("ɾ����ǰͬ��������  end ,tablename=" + tableName);
		}
	}

	/**
	 * ɾ����ǰͬ���м������
	 * 
	 * @return
	 */
	public void delMidTable() {
		if (logger.isDebugEnabled()) {
			logger.debug("ɾ����ǰͬ���м������  strat ");
		}

		try {
			// ɾ����ǰͬ���м�������
			BaseVideoDAO.getInstance().truncateTable(
					new String[] { "t_v_lables_mid", "t_v_videomedia_mid",
							"t_v_videopic_mid", "t_v_videospropertys_mid","T_V_PROGRAM_SALES_MID" });

		} catch (Exception e) {
			logger.error("ɾ����ǰͬ���м������ʧ�ܣ�");
		}
		if (logger.isDebugEnabled()) {
			logger.debug("ɾ����ǰͬ���м������  end ");
		}
	}

	/**
	 * ���ڱ�������ִ��ʱ��
	 * 
	 * @param date
	 * @return
	 */
	public boolean saveLastTime(String date) {
		boolean isTrue = true;
		try {
			BaseVideoDAO.getInstance().saveLastTime(date);
		} catch (BOException e) {
			isTrue = false;
			logger.error("���ڱ�������ִ��ʱ��ʱ�������󣡣���", e);
		}
		return isTrue;
	}

	/**
	 * ���ô洢���� ����ִ���м������ʽ��������ת��
	 */
	public boolean syncMidTableData() {
		if (logger.isDebugEnabled()) {
			logger.debug("���ô洢���� ����ִ���м������ʽ��������ת��, ��ʼ");
		}

		StringBuffer mailText = new StringBuffer();
		int status = 0;

		status = BaseVideoDAO.getInstance().callSyncMidTableData();

		if (logger.isDebugEnabled()) {
			logger.debug("ִ���м������ʽ��������ת��, ����,ִ�н��status=" + status);
		}
		if (status != 0) {
			mailText.append("������Ƶִ���м������ʽ��������ת�ƽ���ɹ�success��");
		} else {
			mailText.append("������Ƶִ���м������ʽ��������ת�ƽ��ʧ�ܣ�������������鿴�洢������־");
		}

		sendResultMail("�»�����Ƶִ���м������ʽ��������ת�ƽ���ʼ�", mailText);

		return status != 0 ? true : false;
	}

	/**
	 * ���ô洢���� ����ִ�н�Ŀ��Ʒ�ϼܸ��²���
	 */
	public void updateCategoryReference() {
		if (logger.isDebugEnabled()) {
			logger.debug("���ô洢����  ����ִ�н�Ŀ��Ʒ�ϼܸ��²���, ��ʼ");
		}

		StringBuffer mailText = new StringBuffer();
		int status = 0;

		status = BaseVideoDAO.getInstance().callUpdateCategoryReference();

		if (logger.isDebugEnabled()) {
			logger.debug("���ô洢����  ����ִ�н�Ŀ��Ʒ�ϼܸ��²���, ����,ִ�н��status=" + status);
		}
		if (status != 0) {
			mailText.append("������Ƶִ�н�Ŀ��Ʒ�ϼܸ��²�������ɹ�success��");
		} else {
			mailText.append("������Ƶִ�н�Ŀ��Ʒ�ϼܸ��²������ʧ�ܣ�������������鿴�洢������־");
		}

		sendResultMail("�»�����Ƶִ�н�Ŀ��Ʒ�ϼܸ��²�������ʼ�", mailText);

	}

	/**
	 * ���ô洢���� ����ִ���ȵ�������ܸ��²���
	 */
	public String updateHotcontentCategoryMap() {
		if (logger.isDebugEnabled()) {
			logger.debug("���ô洢����  ����ִ���ȵ�������ܸ��²���, ��ʼ");
		}

		int status = 0;

		status = BaseVideoDAO.getInstance().callUpdateHotcontentCategoryMap();

		if (logger.isDebugEnabled()) {
			logger.debug("���ô洢����  ����ִ���ȵ�������ܸ��²���, ����,ִ�н��status=" + status);
		}
		if (status != 0) {
			return "������Ƶִ���ȵ�������ܸ��²�������ɹ�success��";
		} else {
			return "������Ƶִ���ȵ�������ܸ��²������ʧ�ܣ�������������鿴�洢������־";
		}

	}

	/**
	 * ���ͽ���ʼ���
	 * 
	 * @param mailTitle
	 *            ����
	 * @param mailText
	 *            ����
	 */
	public void sendResultMail(String mailTitle, StringBuffer mailText) {
		logger.info(mailText.toString());
		Mail.sendMail(mailTitle, mailText.toString(), BaseVideoConfig.mailTo);
	}

	/**
	 * ����api�ӿڵ��� ����ͬ��
	 */
	public void DataApiRequestSync() {
		StringBuffer mailText = new StringBuffer();
		// ��ͨ��Ŀapi�������� ���ݵ���
		mailText.append(BaseFileFactory.getInstance()
				.getBaseFile(BaseVideoConfig.FILE_TYPE_PROGRAM_API)
				.execution(false));
		// ��Ʒ�������Ʒ�api�������� ���ݵ���
		mailText.append(BaseFileFactory.getInstance()
				.getBaseFile(BaseVideoConfig.FILE_TYPE_PRD_API)
				.execution(false));
		// ҵ���Ʒapi���� ���ݵ���
		mailText.append(BaseFileFactory.getInstance()
				.getBaseFile(BaseVideoConfig.FILE_TYPE_PKG_SALES_API)
				.execution(false));
		// �Ʒ���Ϣ����Api���� ����ͬ��
		mailText.append(BaseFileFactory.getInstance()
				.getBaseFile(BaseVideoConfig.FILE_TYPE_PRODUCT_API)
				.execution(false));
		// �ȵ������б�api�������� ���ݵ���
		mailText.append(BaseFileFactory.getInstance()
				.getBaseFile(BaseVideoConfig.FILE_TYPE_HOTCONTENT_API)
				.execution(false));

		sendResultMail("����api�ӿڵ��� ����ͬ������ʼ�", mailText);
	}
}
