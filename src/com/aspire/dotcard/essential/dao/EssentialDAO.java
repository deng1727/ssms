package com.aspire.dotcard.essential.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.constants.LogErrorCode;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.synczcom.vo.ZcomContentTmp;
import com.aspire.dotcard.synczcoms.dao.ZcomsDataSyncDAO;

public class EssentialDAO {

	/**
	 * ��־����
	 */
	JLogger logger = LoggerFactory.getLogger(EssentialDAO.class);

	private static EssentialDAO dao = new EssentialDAO();

	private EssentialDAO() {

	}

	/**
	 * ����ģʽ
	 * 
	 * @return
	 */
	public static EssentialDAO getInstance() {

		return dao;
	}

	/**
	 * ��ѯMMӦ���ٷ���
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<String> getContactIds() throws Exception {

		if (logger.isDebugEnabled()) {
			logger.debug("getMMContentid()");
		}

		// sql=select t.contentid from t_r_gcontent t where channeldisptype =
		// '0'
		// ��MMӦ�ò�ѯ����
		String sqlCode = "com.aspire.dotcard.essential.dao.EssentialDAO.getContactIds().SELECT";
		List<String> list = new ArrayList<String>();
		ResultSet rs = null;
		try {

			rs = DB.getInstance().queryBySQLCode(sqlCode, null);

			while (rs.next()) {
				String contentid = rs.getString("contentid");
				list.add(contentid);
			}

		} catch (Exception e) {
			throw new Exception("��ѯ����Ӧ��ʧ��");
		} finally {
			if (null != rs) {
				DB.close(rs);
			}

		}
		// ������ContentTmp����list�з���
		return list;

	}

	

	/**
	 * ����contentid��ѯ����Ӧ��clientid
	 * 
	 * @param contentid
	 * @return
	 * @throws Exception
	 */
	public String getClientId(String contactId) throws Exception {

		if (logger.isDebugEnabled()) {
			logger.debug("getClientId() in.........");
		}

		// select clientid from t_a_cm_device_resource where contentid = ? and
		// prosubmitdate in ( select MAX(prosubmitdate) from
		// t_a_cm_device_resource where contentid = ? )
		// �Ѵ���Ӧ�ò�ѯ����
		String sqlCode = "com.aspire.dotcard.essential.dao.EssentialDAO.getClientId().SELECT";

		String clientid = "";
		Object[] paras = new Object[] { contactId };
		ResultSet rs = null;
		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode, paras);

			while (rs.next()) {
				clientid = rs.getString("clientid");
			}

		} catch (Exception e) {
			throw new Exception("��ȡɾ��������", e);
		} finally {
			if (null != rs) {
				DB.close(rs);
			}

		}
		return clientid;

	}

	public void insertData(String mmContentid, String contactid)
			throws DAOException {

		if (logger.isDebugEnabled()) {
			logger.debug("insertData() in.........");
		}

		// insert into T_MM_ESSENTIAL t (t.mmcontentid,t.contactid,t.updatetime)
		// values (?,?,sysdate)
		// �Ѵ���Ӧ�ò�ѯ����
		String sqlCode = "com.aspire.dotcard.essential.dao.EssentialDAO.insertData().INSERT";
		Object[] paras = new Object[] { mmContentid, contactid };
		DB.getInstance().executeBySQLCode(sqlCode, paras);

	}

	public String getMMContentId(String clientid) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("getMMContentId() in.........");
		}
		String sqlCode = "com.aspire.dotcard.essential.dao.EssentialDAO.getMMContentId().SELECT";

		Object[] paras = new Object[] { clientid ,clientid };
		ResultSet rs = null;
		String contact = "";
		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode, paras);

			while (rs.next()) {
				contact = rs.getString("contentid");
			}

		} catch (Exception e) {
			throw new Exception("����clientid��ѯ������ΨһMMcontentid����");
		} finally {
			if (null != rs) {
				DB.close(rs);
			}

		}
		return contact;
	}
	public void clearData(){
		

		if (logger.isDebugEnabled()) {
			logger.debug("clearData() in.........");
		}
		String sqlCode = "com.aspire.dotcard.essential.dao.EssentialDAO.clearData().delete";
		
			try {
				DB.getInstance().executeBySQLCode(sqlCode, null);
			} catch (DAOException e) {
				logger.error("���T_MM_ESSENTIAL�������ݳ���" ,e);
			}
	
	}
}
