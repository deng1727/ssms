package com.aspire.ponaadmin.web.comic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class CategoryEmailDAO {

	/**
	 * ��־����
	 */
	protected static final JLogger logger = LoggerFactory
			.getLogger(CategoryEmailDAO.class);

	private static CategoryEmailDAO dao = new CategoryEmailDAO();

	private CategoryEmailDAO() {
	}

	/**
	 * ��ȡʵ��
	 * 
	 * @return ʵ��
	 */
	public static CategoryEmailDAO getInstance() {
		return dao;
	}

	/**
	 * ��ȡ�����ַ
	 * 
	 * @param map
	 *            status:����״̬ 0 �ύ���� 1 �������� ;categoryId:����Id;operation:����ʽ
	 * @return
	 * @throws DAOException
	 */
	public String getMailAddress(Map<String, Object> map) throws DAOException {
		ResultSet rs = null;
		try {
			if (Integer.parseInt(map.get("status").toString()) == 0) {
				rs = DB.getInstance()
						.queryBySQLCode(
								"com.aspire.ponaadmin.web.comic.dao.CategoryEmailDAO.getMailAddress2",
								new Object[] { map.get("operation") });
			} else {
				rs = DB.getInstance()
						.queryBySQLCode(
								"com.aspire.ponaadmin.web.comic.dao.CategoryEmailDAO.getMailAddress1",
								new Object[] { map.get("operation")  });
			}

			while (rs.next()) {
				return rs.getString(1);
			}
		} catch (DAOException e) {
			logger.error("��ѯ�ʼ���ַ�����쳣:", e);
			throw new DAOException("��ѯ�ʼ���ַ�����쳣:", e);
		} catch (SQLException e) {
			logger.error("��ѯ�ʼ���ַ�����쳣:", e);
			throw new DAOException("��ѯ�ʼ���ַ�����쳣:", e);
		}
		return null;
	}

}
