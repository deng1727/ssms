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
	 * 查询基地图书所有大类
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
			log.error("数据库操作失败");
			throw new DAOException("数据库操作失败，" + e);

		} finally {
			DB.close(rs);
		}
		return type;
	}
	
	/**
	 * 新增图书分类
	 * @param type
	 * @throws Exception
	 */
	public void addType(RTypeVO type)throws Exception{
		if (log.isDebugEnabled()) {
			log.debug("add BaseBookType(" + type + ")");
		}
		String sqlCode = "com.aspire.dotcard.basebook.dao.TypeDao.addType";
		// 定义在sql语句中要替换的参数,

		Object[] paras = { type.getTypeId(),type.getTypeName(),type.getParentId() };
		DB.getInstance().executeBySQLCode(sqlCode, paras);		
	}
	
	/**
	 * 更新 修改
	 * @param type
	 * @throws Exception
	 */
	public void update(RTypeVO type)throws Exception{
		if (log.isDebugEnabled()) {
			log.debug("update BaseBookType(" + type + ")");
		}
		String sqlCode = "com.aspire.dotcard.basebook.dao.TypeDao.update";
		// 定义在sql语句中要替换的参数,

		Object[] paras = { type.getTypeName(),type.getParentId() ,type.getTypeId()};
		DB.getInstance().executeBySQLCode(sqlCode, paras);			
	}
	
	/**
	 * 删除类型
	 * @param type
	 * @throws Exception
	 */
	public void delete(RTypeVO type)throws Exception{
		if (log.isDebugEnabled()) {
			log.debug("update BaseBookType(" + type + ")");
		}
		String sqlCode = "com.aspire.dotcard.basebook.dao.TypeDao.delete";
		// 定义在sql语句中要替换的参数,

		Object[] paras = { type.getTypeId()};
		DB.getInstance().executeBySQLCode(sqlCode, paras);			
	}
	
	/**
	 * 检查是否存在
	 * @param type
	 * @return false不存在 true存在
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
			log.error("数据库操作失败");
			throw new DAOException("数据库操作失败，" + e);

		} finally {
			DB.close(rs);
		}
		return count==0?false:true;
	}
	
	/**
	 * 获取图书类型对象
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
			log.error("数据库操作失败");
			throw new DAOException("数据库操作失败，" + e);

		} finally {
			DB.close(rs);
		}
		return t;		
	}
}
