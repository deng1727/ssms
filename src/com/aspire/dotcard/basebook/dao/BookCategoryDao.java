package com.aspire.dotcard.basebook.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basebook.vo.RCategoryVO;

public class BookCategoryDao {
	protected static JLogger log = LoggerFactory
			.getLogger(BookCategoryDao.class);
	private static BookCategoryDao instance = new BookCategoryDao();

	private BookCategoryDao() {
	}

	public static BookCategoryDao getInstance() {
		return instance;
	}

	/**
	 * 新增 专区信息
	 * 
	 * @param cate
	 * @throws Exception
	 */
	public void add(RCategoryVO cate) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("add BookCategory(" + cate + ")");
		}
		String sqlCode = "com.aspire.dotcard.basebook.dao.BookCategoryDao.add";
		// 定义在sql语句中要替换的参数,

		Object[] paras = {cate.getCategoryId(),cate.getCategoryName(),cate.getCatalogType()
				,cate.getDescription(),cate.getParentId(),cate.getPicUrl()};
		DB.getInstance().executeBySQLCode(sqlCode, paras);
	}

	/**
	 * 新增图书包月信息
	 * 
	 * @param ref
	 * @throws Exception
	 */
	public void addBookMonth(RCategoryVO cate) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("add BookCategory(" + cate + ")");
		}
		String sqlCode = "com.aspire.dotcard.basebook.dao.BookCategoryDao.addBookMonth";
		// 定义在sql语句中要替换的参数,

		Object[] paras = {cate.getCategoryId(),cate.getCategoryName()
				,cate.getDescription(),new Integer(cate.getFee()),cate.getUrl()};
		DB.getInstance().executeBySQLCode(sqlCode, paras);
	}

	/**
	 * 修改
	 * 
	 * @param cate
	 * @throws Exception
	 */
	public void update(RCategoryVO cate) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("add BookCategory(" + cate + ")");
		}
		String sqlCode = "com.aspire.dotcard.basebook.dao.BookCategoryDao.update";
		// 定义在sql语句中要替换的参数,

		Object[] paras = {cate.getCategoryName(),
				cate.getDescription(),cate.getParentId(),cate.getPicUrl(),cate.getCatalogType(),cate.getCategoryId()};
		DB.getInstance().executeBySQLCode(sqlCode, paras);
	}

	/**
	 * 更新基地图书包月信息
	 * @param cate
	 * @throws Exception
	 */
	public void updateBookMonth(RCategoryVO cate) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("add BookCategory(" + cate + ")");
		}
		String sqlCode = "com.aspire.dotcard.basebook.dao.BookCategoryDao.updateBookMonth";
		// 定义在sql语句中要替换的参数,

		Object[] paras = {cate.getCategoryName(),cate.getDescription(),
				new Integer(cate.getFee()),cate.getUrl(),cate.getCategoryId()};
		DB.getInstance().executeBySQLCode(sqlCode, paras);
	}
	
	/**
	 * 删除
	 * 
	 * @param cate
	 * @throws Exception
	 */
	public void delete(RCategoryVO cate) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("add BookCategory(" + cate + ")");
		}
		//删除专区信息
		String sqlCode = "com.aspire.dotcard.basebook.dao.BookCategoryDao.delete";
		Object[] paras = {cate.getCategoryId(),cate.getCatalogType()};
		
		//删除专区内容信息
		String delRefSqlCode = "com.aspire.dotcard.basebook.dao.BookReferenceDao.deleteRank";
		Object[] delRefParas = {new Integer(cate.getId()),cate.getCategoryId()};		

		
		DB.getInstance().executeMutiBySQLCode(new String[]{sqlCode,delRefSqlCode}, new Object[][]{paras,delRefParas});
	}
	
	/**
	 * 更新货架下商品总数
	 * 
	 * @param cate
	 * @throws Exception
	 */
	public void updateCateTotal() throws Exception {
		log.debug("update category total books");
		//删除专区信息
		String sqlCode = "com.aspire.dotcard.basebook.dao.BookCategoryDao.updateTotal";
		
		DB.getInstance().executeBySQLCode(sqlCode, null);
	}	

	/**
	 * 查询所有存在的货架
	 * @return
	 * @throws Exception
	 */
	public Map queryAllAreaCate() throws Exception {
		String sqlCode = "com.aspire.dotcard.basebook.dao.BookCategoryDao.queryAllAreaCate";
		Map m = null;
		ResultSet rs = null;
		try {

			rs = DB.getInstance().queryBySQLCode(sqlCode, null);
			m = new HashMap();
			while (rs.next()) {
				RCategoryVO cate = new RCategoryVO();
				cate.setId(rs.getInt("id"));
				cate.setCategoryId(rs.getString("categoryid"));
				cate.setCategoryName(rs.getString("categoryname"));
				cate.setCatalogType(rs.getString("catalogtype"));
				cate.setParentId(rs.getString("parentid"));
				//list.add(cate);
				m.put(cate.getCategoryId(), cate);

			}
		} catch (SQLException e) {
			log.error("数据库操作失败");
			throw new DAOException("数据库操作失败，" + e);

		} finally {
			DB.close(rs);

		}
		return m;
	}
	
	/**
	 * 查询所有基地图书包月信息
	 * @return
	 * @throws Exception
	 */
	public boolean isMonthExist(RCategoryVO cate) throws Exception {
		int count = 0;
		String sqlCode = "com.aspire.dotcard.basebook.dao.BookCategoryDao.isMonthExist";

		ResultSet rs = null;
		try {
			Object[] paras = { cate.getCategoryId()};
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
	 * 检查是否存在
	 * @param cate
	 * @return
	 * @throws Exception
	 */
	public boolean isExist(RCategoryVO cate) throws Exception {
		int count = 0;
		String sqlCode = "com.aspire.dotcard.basebook.dao.BookCategoryDao.isExist";

		ResultSet rs = null;
		try {
			Object[] paras = { cate.getCategoryId()};
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
	 * 查询某个货架
	 * @param categoryId
	 * @param typeId
	 * @return
	 * @throws Exception
	 */
	public RCategoryVO getCategoryByType(String categoryId,String typeId)throws Exception{
		String sqlCode = "com.aspire.dotcard.basebook.dao.BookCategoryDao.selectCate";
		RCategoryVO cate = null;
		ResultSet rs = null;
		try {
			Object[] paras = new Object[]{categoryId,typeId};
			rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
			while (rs.next()) {
				cate = new RCategoryVO();
				cate.setId(rs.getInt("id"));
				cate.setCategoryId(rs.getString("categoryid"));
				cate.setCategoryName(rs.getString("categoryname"));
				cate.setCatalogType(rs.getString("catalogtype"));
				cate.setParentId(rs.getString("parentid"));
				
			}
		} catch (SQLException e) {
			log.error("数据库操作失败");
			throw new DAOException("数据库操作失败，" + e);

		} finally {
			DB.close(rs);

		}
		return cate;
	}
	
	/**
	 * 查询顶层货架
	 * @param categoryId
	 * @param typeId
	 * @return
	 * @throws Exception
	 */
	public RCategoryVO getTopCateByType(String typeId)throws Exception{
		String sqlCode = "com.aspire.dotcard.basebook.dao.BookCategoryDao.getTopCateByType";
		RCategoryVO cate = null;
		ResultSet rs = null;
		try {
			Object[] paras = new Object[]{typeId};
			rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
			while (rs.next()) {
				cate = new RCategoryVO();
				cate.setId(rs.getInt("id"));
				cate.setCategoryId(rs.getString("categoryid"));
				cate.setCategoryName(rs.getString("categoryname"));
				cate.setCatalogType(rs.getString("catalogtype"));
				cate.setParentId(rs.getString("parentid"));
				
			}
		} catch (SQLException e) {
			log.error("数据库操作失败");
			throw new DAOException("数据库操作失败，" + e);

		} finally {
			DB.close(rs);

		}
		return cate;
	}	
}
