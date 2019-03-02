package com.aspire.ponaadmin.web.blacklist.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class BlackListEmailDAO {
	
	/**
	 * ��־����
	 */
	protected static final JLogger logger = LoggerFactory
			.getLogger(BlackListEmailDAO.class);

	private static BlackListEmailDAO dao = new BlackListEmailDAO();

	private BlackListEmailDAO() {
	}

	/**
	 * ��ȡʵ��
	 * 
	 * @return ʵ��
	 */
	public static BlackListEmailDAO getInstance() {
		return dao;
	}
	
	/**
	 * ��ȡ�����ַ
	 * 
	 * @param map status:����״̬ 0 �ύ���� 1 ��������  ;contentId:Ӧ������Id;operation:����ʽ
	 * @return
	 * @throws DAOException
	 */
	public String getMailAddress(Map<String,Object> map) throws DAOException{
		ResultSet rs = null;
		try {
			if(Integer.parseInt(map.get("status").toString()) == 0){
				rs = DB.getInstance().queryBySQLCode("com.aspire.ponaadmin.web.blacklist.dao.BlackListEmailDAO.getMailAddress2", new Object[] {map.get("operation") });
			}else{
				rs = DB.getInstance().queryBySQLCode("com.aspire.ponaadmin.web.blacklist.dao.BlackListEmailDAO.getMailAddress1", new Object[] {map.get("operation") });
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
