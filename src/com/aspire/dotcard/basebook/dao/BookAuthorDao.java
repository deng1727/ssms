package com.aspire.dotcard.basebook.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basebook.vo.BookAuthorVO;

public class BookAuthorDao {
	protected static JLogger log = LoggerFactory.getLogger(BookAuthorDao.class);
	private static BookAuthorDao instance = new BookAuthorDao();
	
	public static synchronized BookAuthorDao getInstance(){

		return instance;
	}
	
	private BookAuthorDao(){
		
	}
	
	/**
	 * 新增
	 * @param author
	 * @throws Exception
	 */
	public void add(BookAuthorVO author)throws Exception{
		if (log.isDebugEnabled()) {
			log.debug("add BaseBookAuthor(" + author + ")");
		}
		String sqlCode = "com.aspire.dotcard.basebook.dao.BookAuthorDao.add";
		// 定义在sql语句中要替换的参数,

		Object[] paras = { author.getAuthorId(),author.getAuthorName(),author.getDescription() };
		DB.getInstance().executeBySQLCode(sqlCode, paras);				
	}
	
	/**
	 * 更新
	 * @param author
	 * @throws Exception
	 */
	public void update(BookAuthorVO author)throws Exception{
		String sqlCode = "com.aspire.dotcard.basebook.dao.BookAuthorDao.update";
		// 定义在sql语句中要替换的参数,

		Object[] paras = { author.getAuthorName(),author.getDescription() ,author.getAuthorId()};
		DB.getInstance().executeBySQLCode(sqlCode, paras);			
	}
	
	/**
	 * 删除
	 * @param author
	 * @throws Exception
	 */
	public void delete(BookAuthorVO author)throws Exception{
		String sqlCode = "com.aspire.dotcard.basebook.dao.BookAuthorDao.delete";
		// 定义在sql语句中要替换的参数,

		Object[] paras = {author.getAuthorId()};
		DB.getInstance().executeBySQLCode(sqlCode, paras);			
	}
	
	public boolean isExist(BookAuthorVO author)throws Exception{
		int count =0;
		String sqlCode = "com.aspire.dotcard.basebook.dao.BookAuthorDao.isExist";

		ResultSet rs = null;
		try {
			Object[] paras = { author.getAuthorId()};
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
	 * 根据作者id查询作者信息
	 * @param author
	 * @return
	 * @throws Exception
	 */
	public BookAuthorVO getAuthor(BookAuthorVO author)throws Exception{
		String sqlCode = "com.aspire.dotcard.basebook.dao.BookAuthorDao.getAuthor";
		ResultSet rs = null;
		BookAuthorVO au = null;
		try {
			Object[] paras = { author.getAuthorId()};
			rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
	
			while (rs.next()) {
				au = new BookAuthorVO();
				au.setAuthorName(rs.getString("authorname"));
				au.setAuthorId(rs.getString("authorid"));
				au.setDescription(rs.getString("authordesc"));
			}
		} catch (SQLException e) {
			log.error("数据库操作失败");
			throw new DAOException("数据库操作失败，" + e);

		} finally {
			DB.close(rs);
		}
		return au;
	}	
	
	public Map queryAllAuthor()throws Exception{
		String sqlCode = "com.aspire.dotcard.basebook.dao.BookAuthorDao.queryAllAuthor";
		Map m = null;
		ResultSet rs = null;
		try {

			rs = DB.getInstance().queryBySQLCode(sqlCode, null);
			m = new HashMap();
			while (rs.next()) {
				m.put(rs.getString("authorid"), rs.getString("authorid"));
			}
		} catch (SQLException e) {
			log.error("数据库操作失败");
			throw new DAOException("数据库操作失败，" + e);

		} finally {
			DB.close(rs);
		}
		return m;			
	}
}
