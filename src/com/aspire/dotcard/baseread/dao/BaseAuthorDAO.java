package com.aspire.dotcard.baseread.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.db.SQLUtil;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.DataAccessException;
import com.aspire.common.persistence.util.SQLCode;
import com.aspire.dotcard.baseread.vo.BookAuthorVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.page.PageVOInterface;
import com.aspire.ponaadmin.web.datafield.vo.ResourceVO;

public class BaseAuthorDAO
{
	/**
	 * ��¼��־��ʵ������
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(BaseAuthorDAO.class);
	
	/**
	 * singletonģʽ��ʵ��
	 */
	private static BaseAuthorDAO instance = new BaseAuthorDAO();
	
	/**
	 * ���췽������singletonģʽ����
	 */
	private BaseAuthorDAO()
	{}
	
	/**
	 * ��ȡʵ��
	 * 
	 * @return ʵ��
	 */
	public static BaseAuthorDAO getInstance()
	{
		return instance;
	}
	
	/**
	 * Ӧ�����ҳ��ȡVO��ʵ����
	 */
	private class BaseAuthorPageVO implements PageVOInterface
	{
		public void CopyValFromResultSet(Object content, ResultSet rs)
				throws SQLException
		{
			BookAuthorVO vo = (BookAuthorVO) content;
			vo.setAuthorId(rs.getString("authorid"));
			vo.setAuthorName(rs.getString("authorname"));
			vo.setDescription(rs.getString("authordesc"));
			vo.setNameLetter(rs.getString("nameletter"));
			vo.setAuthorPic(rs.getString("authorpic"));
			vo.setIsOriginal(rs.getString("isoriginal"));
			vo.setIsPublish(rs.getString("ispublish"));
			vo
					.setRecommendManual(String.valueOf(rs
							.getInt("recommend_manual")));
		}
		
		public Object createObject()
		{
			return new BookAuthorVO();
		}
	}
	
	private BookAuthorVO bookAuthorVoData(ResultSet rs) throws SQLException
	{
		BookAuthorVO vo = new BookAuthorVO();
		vo.setAuthorId(rs.getString("authorid"));
		vo.setAuthorName(rs.getString("authorname"));
		vo.setDescription(rs.getString("authordesc"));
		vo.setNameLetter(rs.getString("nameletter"));
		vo.setAuthorPic(rs.getString("authorpic"));
		vo.setIsOriginal(rs.getString("isoriginal"));
		vo.setIsPublish(rs.getString("ispublish"));
		vo.setRecommendManual(String.valueOf(rs.getInt("recommend_manual")));
		
		return vo;
	}
	
	/**
	 * ���ڲ�ѯ�����Ķ������б�
	 * 
	 * @param page
	 * @param vo
	 * @throws DAOException
	 */
	public void queryBaseAuthorList(PageResult page, BookAuthorVO vo)
			throws DAOException
	{
		
		if (logger.isDebugEnabled())
		{
			logger.debug("queryBaseAuthorList(" + vo.getAuthorId() + ", "
					+ vo.getAuthorName() + ") is starting ...");
		}
		
		// select
		// t.authorid,t.authorname,t.authordesc,t.nameletter,t.isoriginal,t.ispublish,t.authorpic,t.recommend_manual
		// from t_rb_author_new t where 1=1
		String sqlCode = "read.BaseAuthorDAO.queryBaseAuthorList().SELECT";
		String sql = null;
		
		try
		{
			sql = SQLCode.getInstance().getSQLStatement(sqlCode);
			
			StringBuffer sqlBuffer = new StringBuffer(sql);
			List paras = new ArrayList();
			
			// ����������sql�Ͳ���
			if (!"".equals(vo.getAuthorId()))
			{
				sqlBuffer.append(" and t.AuthorId =? ");
				paras.add(vo.getAuthorId());
			}
			if (!"".equals(vo.getAuthorName()))
			{
				sqlBuffer.append(" and t.AuthorName like ? ");
				paras.add("%" + SQLUtil.escape(vo.getAuthorName()) + "%");
			}
			
			sqlBuffer.append(" order by t.RECOMMEND_MANUAL desc");
			
			page.excute(sqlBuffer.toString(), paras.toArray(),
					new BaseAuthorPageVO());
		}
		catch (DataAccessException e)
		{
			throw new DAOException(
					"��sql.properties�л�ȡsql���ʱ�������󣬶�ӦsqlCode�����ڡ�", e);
		}
	}
	
	/**
	 * ���ڲ�ѯ�����Ķ���������
	 * 
	 * @param page
	 * @param vo
	 * @throws DAOException
	 */
	public BookAuthorVO queryBaseAuthorVO(String authorId) throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("queryBaseAuthorVO(" + authorId + ") is starting ...");
		}
		
		// select
		// t.authorid,t.authorname,t.authordesc,t.nameletter,t.isoriginal,t.ispublish,t.authorpic,t.recommend_manual
		// from t_rb_author_new t where t.authorid=?
		String sqlCode = "read.BaseAuthorDAO.queryBaseAuthorVO().SELECT";
		ResultSet rs = null;
		BookAuthorVO vo = null;
		
		try
		{
			rs = DB.getInstance().queryBySQLCode(sqlCode,
					new Object[] { authorId });
			
			if (rs.next())
			{
				vo = bookAuthorVoData(rs);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new DAOException("ִ�в�ѯ�����Ķ���������ʧ��", e);
		}
		finally
		{
			DB.close(rs);
		}
		return vo;
	}
	
	/**
	 * ���ڱ�������Ķ�������Ϣ
	 * 
	 * @param newMusicCategory
	 * @throws BOException
	 */
	public void updateBaseAuthorVO(BookAuthorVO authorVO) throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("updateBaseAuthorVO() is starting ...");
		}
		
		// update t_rb_author_new c set c.nameletter=?, c.isoriginal=?,
		// c.ispublish=?, c.recommend_manual=? where authorid=?
		String sqlCode = "read.BaseAuthorDAO.updateReadAuthor().update";
		
		// update t_rb_author_new c set c.nameletter=?, c.isoriginal=?,
		// c.ispublish=?, c.recommend_manual=?,c.AUTHORPIC=?, c. LUPDATE=sysdate
		// where authorid=?
		String sqlCode2 = "read.BaseAuthorDAO.updateReadAuthor().updatepic";
		try
		{
			if (authorVO.getAuthorPic() != null
					&& !authorVO.getAuthorPic().equals(""))
			{
				// ��ͼƬ
				DB.getInstance()
						.executeBySQLCode(
								sqlCode2,
								new Object[] { authorVO.getDescription(),
										authorVO.getNameLetter(),
										authorVO.getIsOriginal(),
										authorVO.getIsPublish(),
										authorVO.getRecommendManual(),
										authorVO.getAuthorPic(),
										authorVO.getAuthorId() });
			}
			else
			{
				DB.getInstance().executeBySQLCode(
						sqlCode,
						new Object[] { authorVO.getDescription(),
								authorVO.getNameLetter(),
								authorVO.getIsOriginal(),
								authorVO.getIsPublish(),
								authorVO.getRecommendManual(),
								authorVO.getAuthorId() });
			}
		}
		catch (DAOException e)
		{
			throw new DAOException("��������Ķ�������Ϣʱ�����쳣:", e);
		}
	}
	
	/**
	 * ���ڸ��µ�ǰ����������Ϣ
	 * 
	 * @param list
	 * @param temp
	 * @throws DAOException
	 */
	public void updateReadAuthor(List list, String[] temp)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("updateReadAuthor() is starting ...");
		}
		
		// update t_rb_author_new c set c.nameletter=?, c.isoriginal=?,
		// c.ispublish=?, c.recommend_manual=? where authorid=?
		String sqlCode = "read.BaseAuthorDAO.updateReadAuthor().update";
		int numUpdate = 0;
		StringBuffer errorId = new StringBuffer();
		
		for (Iterator iterator = list.iterator(); iterator.hasNext();)
		{
			BookAuthorVO vo = (BookAuthorVO) iterator.next();
			
			int exe = 0;
			
			try
			{
				exe = DB.getInstance().executeBySQLCode(
						sqlCode,
						new Object[] { vo.getDescription(), vo.getNameLetter().toUpperCase(),
								vo.getIsOriginal(), vo.getIsPublish(),
								vo.getRecommendManual(), vo.getAuthorId() });
			}
			catch (DAOException e)
			{
				logger.error("ִ�в�ѯ�����Ķ�ָ������������Ϣʧ��", e);
			}
			
			if (exe > 0)
			{
				numUpdate++;
			}
			else
			{
				errorId.append(vo.getAuthorId()).append("</br>");
			}
		}
		
		temp[0] = String.valueOf(numUpdate);
		temp[1] = errorId.toString();
		
		if (logger.isDebugEnabled())
		{
			logger.debug("queryListByExport() is end ...");
		}
	}
	
	/**
	 * ��ȡ��ǰ���������л����Ķ�����������Ϣ��
	 * 
	 * @return
	 * @throws DAOException
	 */
	public List queryListByExport(String authorId, String authorName)
			throws DAOException
	{
		
		if (logger.isDebugEnabled())
		{
			logger.debug("queryListByExport(" + authorId + ", " + authorName
					+ ") is starting ...");
		}
		
		// select
		// t.authorid,t.authorname,t.authordesc,t.nameletter,t.isoriginal,t.ispublish,t.authorpic,t.recommend_manual
		// from t_rb_author_new t where t.authorid=? and authorname like '%?%'
		String sqlCode = "read.BaseAuthorDAO.queryBaseAuthorList().SELECT";
		List list = new ArrayList();
		ResultSet rs = null;
		String sql;
		try
		{
			sql = SQLCode.getInstance().getSQLStatement(sqlCode);
			
			StringBuffer sqlBuffer = new StringBuffer(sql);
			List paras = new ArrayList();
			
			// ����������sql�Ͳ���
			if (!"".equals(authorId))
			{
				sqlBuffer.append(" and t.AuthorId =? ");
				paras.add(authorId);
			}
			if (!"".equals(authorName))
			{
				sqlBuffer.append(" and t.AuthorName like ? ");
				paras.add("%" + SQLUtil.escape(authorName) + "%");
			}
			
			sqlBuffer.append(" order by t.RECOMMEND_MANUAL desc");
			
			rs = DB.getInstance().query(sqlBuffer.toString(), paras.toArray());
			
			while (rs.next())
			{
				list.add(bookAuthorVoData(rs));
			}
		}
		catch (SQLException e)
		{
			logger.error("ִ�в�ѯ�����Ķ�ָ������������Ϣʧ��", e);
			e.printStackTrace();
		}
		catch (DataAccessException e)
		{
			logger.error("ִ�в�ѯ�����Ķ�ָ������������Ϣʧ��", e);
			e.printStackTrace();
		}
		finally
		{
			DB.close(rs);
		}
		
		return list;
	}
	
	/**
	 * @desc ��ȡ�����Ķ�ָ�����ߵ���չ�ֶ�
	 * @param authorId
	 * @return
	 * @throws DAOException
	 */
	public List queryAuthorKeyResource(String authorId) throws DAOException
	{
		List keyResourceList = null;
		if (logger.isDebugEnabled())
		{
			logger.debug("queryAuthorKeyResource( ) is starting ...");
		}
		
		// select * from t_key_base b, (select * from t_key_resource r where
		// r.tid = ?) y where b.keytable = 't_rb_author_new' and b.keyid =
		// y.keyid(+)
		String sqlCode = "read.BaseAuthorDAO.queryAuthorKeyResource().SELECT";
		DB db = DB.getInstance();
		ResultSet rs = null;
		Object paras[] = { authorId };
		try
		{
			keyResourceList = new ArrayList();
			rs = db.queryBySQLCode(sqlCode, paras);
			while (rs.next())
			{
				ResourceVO vo = new ResourceVO();
				vo.setKeyid(rs.getString("keyid"));
				vo.setKeyname(rs.getString("keyname"));
				vo.setKeytable(rs.getString("keytable"));
				vo.setKeydesc(rs.getString("keydesc"));
				vo.setKeyType(rs.getString("keytype"));
				vo.setTid(rs.getString("tid"));
				vo.setValue(rs.getString("value"));
				
				keyResourceList.add(vo);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new DAOException("ִ�в�ѯ�����Ķ�ָ��������չ�ֶ�����ʧ��", e);
		}
		finally
		{
			DB.close(rs);
		}
		return keyResourceList;
	}
	
	/**
	 * ���ڷ��ػ����Ķ�������չ�ֶ��б�
	 * 
	 * @return
	 * @throws BOException
	 */
	public List queryAuthorKeyBaseList(String tid) throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("queryAuthorKeyBaseList( ) is starting ...");
		}
		String sqlCode = null;
		ResultSet rs = null;
		List list = new ArrayList();
		boolean insert = true;
		try
		{
			if (tid != null && !tid.equals(""))
			{
				// select * from t_key_base b, (select * from t_key_resource r
				// where r.tid = ?) y where b.keytable = 't_rb_author_new' and
				// b.keyid = y.keyid(+)
				sqlCode = "read.BaseAuthorDAO.queryAuthorKeyBaseResList.SELECT";
				Object[] paras = { tid };
				rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
				insert = false;
			}
			else
			{
				// select * from t_key_base b where b.keytable =
				// 't_rb_author_new'
				sqlCode = "read.BaseAuthorDAO.queryAuthorKeyBaseList.SELECT";
				rs = DB.getInstance().queryBySQLCode(sqlCode, null);
				insert = true;
			}
			while (rs.next())
			{
				ResourceVO vo = new ResourceVO();
				fromReadCategoryKeyBaseVOByRs(vo, rs, insert);
				list.add(vo);
			}
		}
		catch (DAOException e)
		{
			throw new DAOException("��ȡ�����Ķ�������չ��Ϣ����Ϣ��ѯ�����쳣:", e);
		}
		catch (SQLException e)
		{
			throw new DAOException("��ȡ�����Ķ�������չ��Ϣ����Ϣ��ѯ�����쳣:", e);
		}
		finally
		{
			DB.close(rs);
		}
		
		return list;
	}
	
	/**
	 * Ϊ�������Ը�ֵ
	 * 
	 * @param vo
	 * @param rs
	 * @throws SQLException
	 */
	private void fromReadCategoryKeyBaseVOByRs(ResourceVO vo, ResultSet rs,
			boolean insert) throws SQLException
	{
		vo.setKeyid(rs.getString("keyid"));
		vo.setKeyname(rs.getString("keyname"));
		vo.setKeytable(rs.getString("keytable"));
		vo.setKeydesc(rs.getString("keydesc"));
		vo.setKeyType(rs.getString("keytype"));
		if (!insert)
		{
			vo.setTid(rs.getString("tid"));
			vo.setValue(rs.getString("value"));
		}
	}
}
