package com.aspire.ponaadmin.web.repository;

import java.sql.SQLException;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class CategoryLoggerDAO {

	/**
	 * ��־����
	 */
	protected static final JLogger logger = LoggerFactory
			.getLogger(CategoryLoggerDAO.class);

	private static CategoryLoggerDAO bo = new CategoryLoggerDAO();

	private CategoryLoggerDAO() {
	}

	/**
	 * ��ȡʵ��
	 * 
	 * @return ʵ��
	 */
	public static CategoryLoggerDAO getInstance() {
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
							"com.aspire.ponaadmin.web.repository.CategoryLoggerDAO.addLogger",
							new Object[] { map.get("operationtype"),//��������
									map.get("operator"), //������
									map.get("operationobj"), //��������
									map.get("operationobjtype") }); //������������
		} catch (DAOException e) {
			logger.error("�����Ӫ������־�����쳣:", e);
			throw new DAOException("�����Ӫ������־�����쳣:", e);
		}

	}

}
