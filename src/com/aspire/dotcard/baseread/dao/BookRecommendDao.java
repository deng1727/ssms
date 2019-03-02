package com.aspire.dotcard.baseread.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseread.vo.RecommendVO;

public class BookRecommendDao
{
	
	protected static JLogger log = LoggerFactory
			.getLogger(BookRecommendDao.class);
	private static BookRecommendDao instance = new BookRecommendDao();
	
	private BookRecommendDao()
	{

	}
	
	public static BookRecommendDao getInstance()
	{
		return instance;
	}
	
	/**
	 * ����
	 * 
	 * @param recommend
	 * @throws Exception
	 */
	public void add(RecommendVO recommend) throws Exception
	{
		if (log.isDebugEnabled())
		{
			log.debug("add RecommendVO(" + recommend + ")");
		}
		String sqlCode = "com.aspire.dotcard.baseread.dao.BookRecommendDao.add";
		// ������sql�����Ҫ�滻�Ĳ���,
		
		Object[] paras = { recommend.getRecommendId(), recommend.getTypeId(),
				recommend.getBookId() };
		DB.getInstance().executeBySQLCode(sqlCode, paras);
	}
	
	/**
	 * ����
	 * 
	 * @param recommend
	 * @throws Exception
	 */
	public void update(RecommendVO recommend) throws Exception
	{
		if (log.isDebugEnabled())
		{
			log.debug("update RecommendVO(" + recommend + ")");
		}
		String sqlCode = "com.aspire.dotcard.baseread.dao.BookRecommendDao.update";
		
		// ������sql�����Ҫ�滻�Ĳ���,
		Object[] paras = { recommend.getRecommendId(), recommend.getTypeId(),
				recommend.getBookId() };
		
		if (null == recommend.getTypeId() || "".equals(recommend.getTypeId()))
		{
			sqlCode = "com.aspire.dotcard.baseread.dao.BookRecommendDao.updateTypeNull";
			paras = new Object[] { recommend.getRecommendId(),
					recommend.getBookId() };
		}
		DB.getInstance().executeBySQLCode(sqlCode, paras);
	}
	
	/**
	 * ɾ��
	 * 
	 * @param recommend
	 * @throws Exception
	 */
	public void delete(RecommendVO recommend) throws Exception
	{
		if (log.isDebugEnabled())
		{
			log.debug("delete RecommendVO(" + recommend + ")");
		}
		// delete from T_RB_RECOMMEND_NEW  where Typeid\=? and Bookid\=? 
		String sqlCode = "com.aspire.dotcard.baseread.dao.BookRecommendDao.delete";
		Object[] paras;
		
		if("".equals(recommend.getTypeId()))
		{
			// delete from T_RB_RECOMMEND_NEW  where Typeid is null and Bookid\=? 
			sqlCode = "com.aspire.dotcard.baseread.dao.BookRecommendDao.typeisnull.delete";
			paras =  new Object[]{ recommend.getBookId() };
		}
		else 
		{
			paras = new Object[]{ recommend.getTypeId(), recommend.getBookId() };
		}
		// ������sql�����Ҫ�滻�Ĳ���,
		DB.getInstance().executeBySQLCode(sqlCode, paras);
	}
	
	/**
	 * �Ƿ����
	 * 
	 * @return false������ true ����
	 * @throws Exception
	 */
	public boolean isExist(RecommendVO recommend) throws Exception
	{
		int count = 0;
		String sqlCode = "com.aspire.dotcard.baseread.dao.BookRecommendDao.isExist";
		Object[] paras = { recommend.getTypeId(), recommend.getBookId() };
		ResultSet rs = null;
		try
		{
			if (null == recommend.getTypeId()
					|| "".equals(recommend.getTypeId()))
			{
				sqlCode = "com.aspire.dotcard.baseread.dao.BookRecommendDao.isExistTypeNull";
				paras = new Object[] { recommend.getBookId() };
			}
			rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
			
			while (rs.next())
			{
				count = rs.getInt("count");
			}
		}
		catch (SQLException e)
		{
			log.error("���ݿ����ʧ��");
			throw new DAOException("���ݿ����ʧ�ܣ�" + e);
			
		}
		finally
		{
			DB.close(rs);
		}
		return count == 0 ? false : true;
	}
	
	/**
	 * ��ѯ���е�ͼ���Ƽ���Ϣ
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map queryAllRecommend() throws Exception
    {
        String sqlCode = "com.aspire.dotcard.baseread.dao.BookRecommendDao.queryAllRecommend";
        Map m = new HashMap();
        ResultSet rs = null;
        try
        {

            rs = DB.getInstance().queryBySQLCode(sqlCode, null);
            while (rs.next())
            {
            	String typeId = rs.getString("typeid");
            	if ("".equals(typeId) || null == typeId)
				{	
            		m.put(rs.getString("recommendId") + "||"
							+ rs.getString("bookid"), "-1");
				}
				else
				{
					m.put(rs.getString("recommendId") + "|"
							+ rs.getString("typeid") + "|"
							+ rs.getString("bookid"), "-1");
				}
            }
        }
        catch (SQLException e)
        {
            log.error("���ݿ����ʧ��");
            throw new DAOException("���ݿ����ʧ�ܣ�" + e);

        }
        finally
        {
            DB.close(rs);
        }
        return m;
    }
}
