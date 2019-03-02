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
	 * ���� ר����Ϣ
	 * 
	 * @param cate
	 * @throws Exception
	 */
	public void add(RCategoryVO cate) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("add BookCategory(" + cate + ")");
		}
		String sqlCode = "com.aspire.dotcard.basebook.dao.BookCategoryDao.add";
		// ������sql�����Ҫ�滻�Ĳ���,

		Object[] paras = {cate.getCategoryId(),cate.getCategoryName(),cate.getCatalogType()
				,cate.getDescription(),cate.getParentId(),cate.getPicUrl()};
		DB.getInstance().executeBySQLCode(sqlCode, paras);
	}

	/**
	 * ����ͼ�������Ϣ
	 * 
	 * @param ref
	 * @throws Exception
	 */
	public void addBookMonth(RCategoryVO cate) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("add BookCategory(" + cate + ")");
		}
		String sqlCode = "com.aspire.dotcard.basebook.dao.BookCategoryDao.addBookMonth";
		// ������sql�����Ҫ�滻�Ĳ���,

		Object[] paras = {cate.getCategoryId(),cate.getCategoryName()
				,cate.getDescription(),new Integer(cate.getFee()),cate.getUrl()};
		DB.getInstance().executeBySQLCode(sqlCode, paras);
	}

	/**
	 * �޸�
	 * 
	 * @param cate
	 * @throws Exception
	 */
	public void update(RCategoryVO cate) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("add BookCategory(" + cate + ")");
		}
		String sqlCode = "com.aspire.dotcard.basebook.dao.BookCategoryDao.update";
		// ������sql�����Ҫ�滻�Ĳ���,

		Object[] paras = {cate.getCategoryName(),
				cate.getDescription(),cate.getParentId(),cate.getPicUrl(),cate.getCatalogType(),cate.getCategoryId()};
		DB.getInstance().executeBySQLCode(sqlCode, paras);
	}

	/**
	 * ���»���ͼ�������Ϣ
	 * @param cate
	 * @throws Exception
	 */
	public void updateBookMonth(RCategoryVO cate) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("add BookCategory(" + cate + ")");
		}
		String sqlCode = "com.aspire.dotcard.basebook.dao.BookCategoryDao.updateBookMonth";
		// ������sql�����Ҫ�滻�Ĳ���,

		Object[] paras = {cate.getCategoryName(),cate.getDescription(),
				new Integer(cate.getFee()),cate.getUrl(),cate.getCategoryId()};
		DB.getInstance().executeBySQLCode(sqlCode, paras);
	}
	
	/**
	 * ɾ��
	 * 
	 * @param cate
	 * @throws Exception
	 */
	public void delete(RCategoryVO cate) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("add BookCategory(" + cate + ")");
		}
		//ɾ��ר����Ϣ
		String sqlCode = "com.aspire.dotcard.basebook.dao.BookCategoryDao.delete";
		Object[] paras = {cate.getCategoryId(),cate.getCatalogType()};
		
		//ɾ��ר��������Ϣ
		String delRefSqlCode = "com.aspire.dotcard.basebook.dao.BookReferenceDao.deleteRank";
		Object[] delRefParas = {new Integer(cate.getId()),cate.getCategoryId()};		

		
		DB.getInstance().executeMutiBySQLCode(new String[]{sqlCode,delRefSqlCode}, new Object[][]{paras,delRefParas});
	}
	
	/**
	 * ���»�������Ʒ����
	 * 
	 * @param cate
	 * @throws Exception
	 */
	public void updateCateTotal() throws Exception {
		log.debug("update category total books");
		//ɾ��ר����Ϣ
		String sqlCode = "com.aspire.dotcard.basebook.dao.BookCategoryDao.updateTotal";
		
		DB.getInstance().executeBySQLCode(sqlCode, null);
	}	

	/**
	 * ��ѯ���д��ڵĻ���
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
			log.error("���ݿ����ʧ��");
			throw new DAOException("���ݿ����ʧ�ܣ�" + e);

		} finally {
			DB.close(rs);

		}
		return m;
	}
	
	/**
	 * ��ѯ���л���ͼ�������Ϣ
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
			log.error("���ݿ����ʧ��");
			throw new DAOException("���ݿ����ʧ�ܣ�" + e);

		} finally {
			DB.close(rs);
		}		
		return count==0?false:true;
	}	
	
	/**
	 * ����Ƿ����
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
			log.error("���ݿ����ʧ��");
			throw new DAOException("���ݿ����ʧ�ܣ�" + e);

		} finally {
			DB.close(rs);
		}		
		return count==0?false:true;
	}
	
	/**
	 * ��ѯĳ������
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
			log.error("���ݿ����ʧ��");
			throw new DAOException("���ݿ����ʧ�ܣ�" + e);

		} finally {
			DB.close(rs);

		}
		return cate;
	}
	
	/**
	 * ��ѯ�������
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
			log.error("���ݿ����ʧ��");
			throw new DAOException("���ݿ����ʧ�ܣ�" + e);

		} finally {
			DB.close(rs);

		}
		return cate;
	}	
}
