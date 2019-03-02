package com.aspire.dotcard.basebook.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basebook.vo.RCategoryVO;
import com.aspire.dotcard.basebook.vo.RecommendVO;
import com.aspire.dotcard.basebook.vo.ReferenceVO;

public class BookReferenceDao {
	
	protected static JLogger log = LoggerFactory.getLogger(BookReferenceDao.class);
	
	private static BookReferenceDao instance = new BookReferenceDao();
	
	private BookReferenceDao(){
	}
	
	public static BookReferenceDao getInstance(){
		return instance;
	}
	
	/**
	 * ����
	 * @param ref
	 * @throws Exception
	 */
	public void add(ReferenceVO ref)throws Exception{
		if (log.isDebugEnabled()) {
			log.debug("add BookReferenceVO(" + ref + ")");
		}
		String sqlCode = "com.aspire.dotcard.basebook.dao.BookReferenceDao.addArea";
		// ������sql�����Ҫ�滻�Ĳ���,

		Object[] paras = {ref.getCategoryId(),ref.getCategoryId(),ref.getBookId()};
		try {
			DB.getInstance().executeBySQLCode(sqlCode, paras);
		} catch (Exception e) {
			log.error("���ݿ����ʧ��",e);
			throw e;
		}			
	}
	

	
	/**
	 * �޸�
	 * @param ref
	 * @throws Exception
	 */
	public void update(ReferenceVO ref)throws Exception{
		if (log.isDebugEnabled()) {
			log.debug("update BookReferenceVO(" + ref + ")");
		}
		String sqlCode = "com.aspire.dotcard.basebook.dao.BookReferenceDao.addArea";
		// ������sql�����Ҫ�滻�Ĳ���,

		Object[] paras = {ref.getCategoryId(),ref.getCategoryId(),ref.getBookId()};
		try {
			DB.getInstance().executeBySQLCode(sqlCode, paras);
		} catch (Exception e) {
			log.error("���ݿ����ʧ��",e);
			throw e;
		}			
	}
	
	/**
	 * ɾ��
	 * @param ref
	 * @throws Exception
	 */
	public void delete(ReferenceVO ref)throws Exception{
		if (log.isDebugEnabled()) {
			log.debug("delete BookReferenceVO(" + ref + ")");
		}
		String sqlCode = "com.aspire.dotcard.basebook.dao.BookReferenceDao.deleteArea";
		// ������sql�����Ҫ�滻�Ĳ���,

		Object[] paras = {ref.getCategoryId(),ref.getCategoryId(),ref.getBookId()};
		try {
			DB.getInstance().executeBySQLCode(sqlCode, paras);
		} catch (Exception e) {
			log.error("���ݿ����ʧ��",e);
			throw e;
		}			
	}
	
	/**
	 * �Ƿ����
	 * @return false������ true ����
	 * @throws Exception
	 */
	public boolean isExist(ReferenceVO ref)throws Exception{
		int count = 0;
		String sqlCode = "com.aspire.dotcard.basebook.dao.BookReferenceDao.isExist";

		ResultSet rs = null;
		try {
			Object[] paras = { ref.getCategoryId(),ref.getCategoryId(),ref.getBookId()};
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
	 * ����������Ϣ
	 * ��ɾ�������
	 * @throws Exception
	 */
	public void updateRank(List list,RCategoryVO cate)throws Exception{
		log.debug("BookReferenceDao update book rank begin!");
		String[] sql = new String[list.size()+1];
		Object[][] paras = new Object[list.size()+1][];
		sql[0] = "com.aspire.dotcard.basebook.dao.BookReferenceDao.deleteRank";
		paras[0] = new Object[]{new Integer(cate.getId()),cate.getCategoryId()};
		
		for(int i=0;i<list.size();i++){
			sql[i+1] = "com.aspire.dotcard.basebook.dao.BookReferenceDao.addRank";
			ReferenceVO ref = (ReferenceVO)list.get(i);
			paras[i+1] = new Object[]{ref.getCategoryId(),ref.getCategoryId(),ref.getBookId()
					,new Integer(ref.getSortNumber()),new Integer(ref.getRankValue())};
		}
		
		//DB.getInstance().executeMutiBySQLCode(sql, paras);
		DB.getInstance().executeMutiBySQLCodeFaild(sql, paras);//�ݴ����м�һ��ִ��ʧ�ܲ�Ӱ�����ִ��
		log.debug("BookReferenceDao update book rank end!");
	}
}
