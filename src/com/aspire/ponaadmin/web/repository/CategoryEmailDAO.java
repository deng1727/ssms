package com.aspire.ponaadmin.web.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	 * @return
	 * @throws DAOException
	 */
	public String getMailAddress(Map<String,Object> map) throws DAOException{
		ResultSet rs = null;
		try {
			if(Integer.parseInt(map.get("status").toString()) == 0){
				rs = DB.getInstance().queryBySQLCode("com.aspire.ponaadmin.web.repository.CategoryEmailDAO.getMailAddress2", new Object[] {map.get("categoryId"),map.get("operation") });
			}else{
				rs = DB.getInstance().queryBySQLCode("com.aspire.ponaadmin.web.repository.CategoryEmailDAO.getMailAddress1", new Object[] {map.get("categoryId"),map.get("operation") });
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
	/**
	 * ��ȡ�����ַ
	 * 
	 * @param map
	 * @return
	 * @throws DAOException
	 */
	public Set<String> getMailAddress2(Map<String,Object> map) throws DAOException{
		ResultSet rs = null;
		try {
			String sql = "";
			if(Integer.parseInt(map.get("status").toString()) == 0){
				
				sql = "select operatoremail from T_EMAIL_ADDRESS where categoryid in ("+map.get("categoryId")+")  and status = ?";
				
				rs = DB.getInstance().query(sql, new Object[] {map.get("operation") });
			}else{
				sql = "select approvalemail||','||Operatoremail from T_EMAIL_ADDRESS where categoryid in ("+map.get("categoryId")+") and status = ?";
				
				rs = DB.getInstance().query(sql, new Object[] {map.get("operation") });
			}
			Set<String> set = new HashSet<String>();
			while (rs.next()) {
				
				set.add(rs.getString(1));
			}
			return set;
		} catch (DAOException e) {
			logger.error("��ѯ�ʼ���ַ�����쳣:", e);
			throw new DAOException("��ѯ�ʼ���ַ�����쳣:", e);
		} catch (SQLException e) {
			logger.error("��ѯ�ʼ���ַ�����쳣:", e);
			throw new DAOException("��ѯ�ʼ���ַ�����쳣:", e);
		}
	}

}
