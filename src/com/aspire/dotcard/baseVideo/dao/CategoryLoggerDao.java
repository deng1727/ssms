package com.aspire.dotcard.baseVideo.dao;

import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class CategoryLoggerDao {
	
	/**
	 * ��־����
	 */
	protected static final JLogger logger = LoggerFactory
			.getLogger(CategoryLoggerDao.class);

	private static CategoryLoggerDao bo = new CategoryLoggerDao();

	private CategoryLoggerDao() {
	}

	/**
	 * ��ȡʵ��
	 * 
	 * @return ʵ��
	 */
	public static CategoryLoggerDao getInstance() {
		return bo;
	}

	/**
	 * �����־
	 * 
	 * @param map
	 * @throws DAOException
	 */
	public void addLogger(Map<String, Object> map) throws DAOException {
		try {
			DB.getInstance()
					.executeBySQLCode(
							"com.aspire.dotcard.baseVideo.dao.CategoryLoggerDao.addLogger",
							new Object[] { map.get("operationtype"),// ��������
									map.get("operator"), // ������
									map.get("operationobj"), // ��������
									map.get("operationobjtype") }); // ������������
		} catch (DAOException e) {
			logger.error("�����Ӫ������־�����쳣:", e);
			throw new DAOException("�����Ӫ������־�����쳣:", e);
		}

	}

}
