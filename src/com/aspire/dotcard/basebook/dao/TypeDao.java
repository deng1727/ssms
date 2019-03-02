package com.aspire.dotcard.basebook.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basebook.vo.RTypeVO;

public class TypeDao {

	protected static JLogger log = LoggerFactory.getLogger(TypeDao.class);

	private static TypeDao instance = new TypeDao();

	public synchronized static TypeDao getInstance() {

		return instance;
	}

	private TypeDao() {

	}

	/**
	 * ��ѯ����ͼ�����д���
	 * 
	 * @return
	 */
	public Map getAllParentType() throws Exception {

		String sqlCode = "com.aspire.dotcard.basebook.dao.TypeDao.getAllParentType";
		Map type = null;
		ResultSet rs = null;
		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode, null);
			type = new HashMap();
			while (rs.next()) {
				type.put(rs.getString("typename"), rs.getString("typeid"));
			}
		} catch (SQLException e) {
			log.error("���ݿ����ʧ��");
			throw new DAOException("���ݿ����ʧ�ܣ�" + e);

		} finally {
			DB.close(rs);
		}
		return type;
	}
	
	/**
	 * ����ͼ�����
	 * @param type
	 * @throws Exception
	 */
	public void addType(RTypeVO type)throws Exception{
		if (log.isDebugEnabled()) {
			log.debug("add BaseBookType(" + type + ")");
		}
		String sqlCode = "com.aspire.dotcard.basebook.dao.TypeDao.addType";
		// ������sql�����Ҫ�滻�Ĳ���,

		Object[] paras = { type.getTypeId(),type.getTypeName(),type.getParentId() };
		DB.getInstance().executeBySQLCode(sqlCode, paras);		
	}
	
	/**
	 * ���� �޸�
	 * @param type
	 * @throws Exception
	 */
	public void update(RTypeVO type)throws Exception{
		if (log.isDebugEnabled()) {
			log.debug("update BaseBookType(" + type + ")");
		}
		String sqlCode = "com.aspire.dotcard.basebook.dao.TypeDao.update";
		// ������sql�����Ҫ�滻�Ĳ���,

		Object[] paras = { type.getTypeName(),type.getParentId() ,type.getTypeId()};
		DB.getInstance().executeBySQLCode(sqlCode, paras);			
	}
	
	/**
	 * ɾ������
	 * @param type
	 * @throws Exception
	 */
	public void delete(RTypeVO type)throws Exception{
		if (log.isDebugEnabled()) {
			log.debug("update BaseBookType(" + type + ")");
		}
		String sqlCode = "com.aspire.dotcard.basebook.dao.TypeDao.delete";
		// ������sql�����Ҫ�滻�Ĳ���,

		Object[] paras = { type.getTypeId()};
		DB.getInstance().executeBySQLCode(sqlCode, paras);			
	}
	
	/**
	 * ����Ƿ����
	 * @param type
	 * @return false������ true����
	 * @throws Exception
	 */
	public boolean isExist(RTypeVO type)throws Exception{
		int count = 0;
		String sqlCode = "com.aspire.dotcard.basebook.dao.TypeDao.isExist";

		ResultSet rs = null;
		try {
			Object[] paras = { type.getTypeId()};
			rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
	
			while (rs.next()) {
				count = rs.getInt("count");
			}
		} catch (SQLException e) {
			log.error("���ݿ����ʧ��");
			throw new DAOException("���ݿ����ʧ�ܣ�" + e);

		} finally {
			DB.close(rs);
		}
		return count==0?false:true;
	}
	
	/**
	 * ��ȡͼ�����Ͷ���
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public RTypeVO getRTypeVO(RTypeVO type)throws Exception{
		String sqlCode = "com.aspire.dotcard.basebook.dao.TypeDao.getTypeById";
		ResultSet rs = null;
		RTypeVO t = null;
		try {
			Object[] para = new Object[]{type.getTypeId()};
			rs = DB.getInstance().queryBySQLCode(sqlCode, para);
			while (rs.next()) {
				t = new RTypeVO();
				t.setParentId(rs.getString("parentid"));
				t.setTypeName(rs.getString("typename"));
				t.setTypeId(rs.getString("typeid"));
			}
		} catch (SQLException e) {
			log.error("���ݿ����ʧ��");
			throw new DAOException("���ݿ����ʧ�ܣ�" + e);

		} finally {
			DB.close(rs);
		}
		return t;		
	}
}
