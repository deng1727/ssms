package com.aspire.dotcard.basebook.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basebook.vo.RecommendVO;

public class BookRecommendDao {
	
	protected static JLogger log = LoggerFactory.getLogger(BookRecommendDao.class);
	private static BookRecommendDao instance = new BookRecommendDao();
	
	private BookRecommendDao(){
		
	}
	
	public static BookRecommendDao getInstance(){
		return instance;
	}
	
	/**
	 * ����
	 * @param recommend
	 * @throws Exception
	 */
	public void add(RecommendVO recommend)throws Exception{
		if (log.isDebugEnabled()) {
			log.debug("add RecommendVO(" + recommend + ")");
		}
		String sqlCode = "com.aspire.dotcard.basebook.dao.BookRecommendDao.add";
		// ������sql�����Ҫ�滻�Ĳ���,

		Object[] paras = {recommend.getRecommendId(),recommend.getTypeId(),recommend.getBookId() };
		DB.getInstance().executeBySQLCode(sqlCode, paras);			
	}
	
	/**
	 * ����
	 * @param recommend
	 * @throws Exception
	 */
	public void update(RecommendVO recommend)throws Exception{
		if (log.isDebugEnabled()) {
			log.debug("update RecommendVO(" + recommend + ")");
		}
		String sqlCode = "com.aspire.dotcard.basebook.dao.BookRecommendDao.update";
		// ������sql�����Ҫ�滻�Ĳ���,

		Object[] paras = {recommend.getRecommendId(),recommend.getTypeId(),recommend.getBookId() };
		
		if(null==recommend.getTypeId()||"".equals(recommend.getTypeId())){
			sqlCode="com.aspire.dotcard.basebook.dao.BookRecommendDao.updateTypeNull";
			paras = new Object[]{ recommend.getRecommendId(),recommend.getBookId()};
		}		
		DB.getInstance().executeBySQLCode(sqlCode, paras);		
	}
	
	/**
	 * ɾ��
	 * @param recommend
	 * @throws Exception
	 */
	public void delete(RecommendVO recommend)throws Exception{
		if (log.isDebugEnabled()) {
			log.debug("delete RecommendVO(" + recommend + ")");
		}
		String sqlCode = "com.aspire.dotcard.basebook.dao.BookRecommendDao.delete";
		// ������sql�����Ҫ�滻�Ĳ���,

		Object[] paras = {recommend.getTypeId(),recommend.getBookId() };
		DB.getInstance().executeBySQLCode(sqlCode, paras);				
	}
	
	/**
	 * �Ƿ����
	 * @return false������ true ����
	 * @throws Exception
	 */
	public boolean isExist(RecommendVO recommend)throws Exception{
		int count = 0;
		String sqlCode = "com.aspire.dotcard.basebook.dao.BookRecommendDao.isExist";
		Object[] paras = { recommend.getTypeId(),recommend.getBookId()};
		ResultSet rs = null;
		try {
			if(null==recommend.getTypeId()||"".equals(recommend.getTypeId())){
				sqlCode="com.aspire.dotcard.basebook.dao.BookRecommendDao.isExistTypeNull";
				paras = new Object[]{ recommend.getBookId()};
			}
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
	 * ��ѯ���е�ͼ���Ƽ���Ϣ
	 * @return
	 * @throws Exception
	 */
	public Map queryAllRecommend()throws Exception{
		String sqlCode = "com.aspire.dotcard.basebook.dao.BookRecommendDao.queryAllRecommend";
		Map m = null;
		ResultSet rs = null;
		try {

			rs = DB.getInstance().queryBySQLCode(sqlCode, null);
			m = new HashMap();
			while (rs.next()) {
				RecommendVO recommend = new RecommendVO();
				recommend.setRecommendId(rs.getString("recommendId"));
				recommend.setTypeId(rs.getString("typeid"));
				recommend.setBookId(rs.getString("bookid"));
				//list.add(cate);
				m.put(recommend.getRecommendId(), recommend);
			}
		} catch (SQLException e) {
			log.error("���ݿ����ʧ��");
			throw new DAOException("���ݿ����ʧ�ܣ�" + e);

		} finally {
			DB.close(rs);
		}
		return m;		
	}
}
