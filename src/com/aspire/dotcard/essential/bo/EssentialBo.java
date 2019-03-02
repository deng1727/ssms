package com.aspire.dotcard.essential.bo;

import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.essential.dao.EssentialDAO;

public class EssentialBo {
	/**
	 * 日志引用
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(EssentialBo.class);

	private static EssentialBo instance = new EssentialBo();

	private EssentialBo() {
	}

	/**
	 * 得到单例模式
	 * 
	 */
	public static EssentialBo getInstance() {
		return instance;
	}

	public List<String> getContactIds() {
		List<String> list = null;
		try {
			list = EssentialDAO.getInstance().getContactIds();
		} catch (Exception e) {
			logger.debug("查询触点应用.");
		}
		return list;
	};

	public String getMMContentId(String clientid) {
		String list = "";
		try {
			list = EssentialDAO.getInstance().getMMContentId(clientid);
		} catch (Exception e) {
			logger.debug("查询触点应用出错");
		}
		return list;
	};

	public String getClientid(String contactId) {
		String clientid = "";

		try {
			clientid = EssentialDAO.getInstance().getClientId(contactId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return clientid;
	}

	public void insertData(String mmContentid, String contactid) {
		try {
			EssentialDAO.getInstance().insertData(mmContentid, contactid);
		} catch (DAOException e) {
			logger.debug("插入数据出错");
		}
	}

	public void clearData() {
		try {
			EssentialDAO.getInstance().clearData();
		} catch (Exception e) {
			logger.debug("清除T_MM_ESSENTIAL表中数据出错");
		}

	}
}
