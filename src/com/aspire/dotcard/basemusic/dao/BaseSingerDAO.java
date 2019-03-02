package com.aspire.dotcard.basemusic.dao;

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
import com.aspire.dotcard.basemusic.vo.BaseSingerVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.page.PageVOInterface;
import com.aspire.ponaadmin.web.datafield.vo.ResourceVO;

public class BaseSingerDAO
{
	/**
	 * ��¼��־��ʵ������
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(BaseSingerDAO.class);

	/**
	 * singletonģʽ��ʵ��
	 */
	private static BaseSingerDAO instance = new BaseSingerDAO();

	/**
	 * ���췽������singletonģʽ����
	 */
	private BaseSingerDAO()
	{}

	/**
	 * ��ȡʵ��
	 * 
	 * @return ʵ��
	 */
	public static BaseSingerDAO getInstance()
	{
		return instance;
	}

	/**
	 * Ӧ�����ҳ��ȡVO��ʵ����
	 */
	private class BaseSingerPageVO implements PageVOInterface
	{
		public void CopyValFromResultSet(Object content, ResultSet rs)
				throws SQLException
		{
			BaseSingerVO vo = (BaseSingerVO) content;
			vo.setSingerId(rs.getString("sid"));
			vo.setSingerName(rs.getString("singername"));
			vo.setDescription(rs.getString("singerdesc"));
			vo.setNameLetter(rs.getString("singerupcase"));
			vo.setType(rs.getString("type"));
			vo.setImgUrl(rs.getString("imgurl"));
			vo.setUpdate(rs.getDate("upddate"));
		}

		public Object createObject()
		{
			return new BaseSingerVO();
		}
	}

	private BaseSingerVO baseSingerVoData(ResultSet rs) throws SQLException
	{
		BaseSingerVO vo = new BaseSingerVO();
		vo.setSingerId(rs.getString("sid"));
		vo.setSingerName(rs.getString("singername"));
		vo.setDescription(rs.getString("singerdesc"));
		vo.setNameLetter(rs.getString("singerupcase"));
		vo.setType(rs.getString("type"));
		vo.setImgUrl(rs.getString("imgurl"));
		vo.setUpdate(rs.getDate("upddate"));

		return vo;
	}

	/**
	 * ���ڲ�ѯ�������ָ����б�
	 * 
	 * @param page
	 * @param vo
	 * @throws DAOException
	 */
	public void queryBaseSingerList(PageResult page, BaseSingerVO vo)
			throws DAOException
	{

		if (logger.isDebugEnabled())
		{
			logger.debug("queryBaseSingerList(" + vo.getSingerId() + ", "
					+ vo.getSingerName() + ") is starting ...");
		}

		// select * from t_mb_singer_new t where t.delflag=0
		String sqlCode = "music.BaseSingerDAO.queryBaseSingerList().SELECT";
		String sql = null;

		try
		{
			sql = SQLCode.getInstance().getSQLStatement(sqlCode);

			StringBuffer sqlBuffer = new StringBuffer(sql);
			List paras = new ArrayList();

			// ����������sql�Ͳ���
			if (!"".equals(vo.getSingerId()))
			{
				sqlBuffer.append(" and t.sid =? ");
				paras.add(vo.getSingerId());
			}
			if (!"".equals(vo.getSingerName()))
			{
				sqlBuffer.append(" and t.singername like ? ");
				paras.add("%" + SQLUtil.escape(vo.getSingerName()) + "%");
			}

			page.excute(sqlBuffer.toString(), paras.toArray(),
					new BaseSingerPageVO());
		}
		catch (DataAccessException e)
		{
			throw new DAOException(
					"��sql.properties�л�ȡsql���ʱ�������󣬶�ӦsqlCode�����ڡ�", e);
		}
	}

	/**
	 * ���ڲ�ѯ�������ָ�������
	 * 
	 * @param page
	 * @param vo
	 * @throws DAOException
	 */
	public BaseSingerVO queryBaseSingerVO(String singerId) throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("queryBaseSingerVO(" + singerId + ") is starting ...");
		}

		// select * from t_mb_singer_new t where t.delflag=0 and t.sid=?
		String sqlCode = "music.BaseSingerDAO.queryBaseSingerVO().SELECT";
		ResultSet rs = null;
		BaseSingerVO vo = null;

		try
		{
			rs = DB.getInstance().queryBySQLCode(sqlCode, new Object[]
			{ singerId });

			if (rs.next())
			{
				vo = baseSingerVoData(rs);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new DAOException("ִ�в�ѯ�������ָ�������ʧ��", e);
		}
		finally
		{
			DB.close(rs);
		}
		return vo;
	}

	/**
	 * ���ڱ���������ָ�����Ϣ
	 * 
	 * @param newMusicCategory
	 * @throws BOException
	 */
	public void updateBaseSingerVO(BaseSingerVO singerVO) throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("updateBaseSingerVO() is starting ...");
		}

		// update t_mb_singer_new c set c.type=?,c.UPDDATE=sysdate where t.delflag=0 and sid=?
		String sqlCode = "music.BaseSingerDAO.updateBaseSinger().update";

		try
		{
			DB.getInstance().executeBySQLCode(sqlCode, new Object[]
			{ singerVO.getType(), singerVO.getSingerId() });
		}
		catch (DAOException e)
		{
			throw new DAOException("����������ָ�����Ϣʱ�����쳣:", e);
		}
	}

	/**
	 * ���ڸ��µ�ǰ����������Ϣ
	 * 
	 * @param list
	 * @param temp
	 * @throws DAOException
	 */
	public void updateBaseSinger(List list, String[] temp) throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("updateBaseSinger() is starting ...");
		}

		// update t_mb_singer_new c set c.type=?,c.UPDDATE=sysdate where c.delflag=0 and sid=?
		String sqlCode = "music.BaseSingerDAO.updateBaseSinger().update";
		int numUpdate = 0;
		StringBuffer errorId = new StringBuffer();

		for (Iterator iterator = list.iterator(); iterator.hasNext();)
		{
			BaseSingerVO singerVO = (BaseSingerVO) iterator.next();

			int exe = DB.getInstance().executeBySQLCode(sqlCode, new Object[]
			{ singerVO.getType(), singerVO.getSingerId() });

			if (exe > 0)
			{
				numUpdate++;
			}
			else
			{
				errorId.append(singerVO.getSingerId()).append(".");
			}
		}

		temp[0] = String.valueOf(numUpdate);
		temp[1] = errorId.toString();

		if (logger.isDebugEnabled())
		{
			logger.debug("updateBaseSinger() is end ...");
		}
	}

	/**
	 * ��ȡ��ǰ���������л������ָ���������Ϣ��
	 * 
	 * @return
	 * @throws DAOException
	 */
	public List queryListByExport(String singerId, String singerName)
			throws DAOException
	{

		if (logger.isDebugEnabled())
		{
			logger.debug("queryListByExport(" + singerId + ", " + singerName
					+ ") is starting ...");
		}

		// select * from t_mb_singer_new t where t.delflag=0
		String sqlCode = "music.BaseSingerDAO.queryBaseSingerList().SELECT";
		List list = new ArrayList();
		ResultSet rs = null;
		String sql;
		try
		{
			sql = SQLCode.getInstance().getSQLStatement(sqlCode);

			StringBuffer sqlBuffer = new StringBuffer(sql);
			List paras = new ArrayList();

			// ����������sql�Ͳ���
			if (!"".equals(singerId))
			{
				sqlBuffer.append(" and t.sId =? ");
				paras.add(singerId);
			}
			if (!"".equals(singerName))
			{
				sqlBuffer.append(" and t.singerName like ? ");
				paras.add("%" + SQLUtil.escape(singerName) + "%");
			}

			rs = DB.getInstance().query(sqlBuffer.toString(), paras.toArray());

			while (rs.next())
			{
				list.add(baseSingerVoData(rs));
			}
		}
		catch (SQLException e)
		{
			logger.error("ִ�в�ѯ�������ָ���������Ϣʧ��", e);
			e.printStackTrace();
		}
		catch (DataAccessException e)
		{
			logger.error("ִ�в�ѯ�������ָ���������Ϣʧ��", e);
			e.printStackTrace();
		}
		finally
		{
			DB.close(rs);
		}

		return list;
	}

	/**
	 * @desc ��ȡ�������ָ��ֵ���չ�ֶ�
	 * @param singerId
	 * @return
	 * @throws DAOException
	 */
	public List querySingerKeyResource(String singerId) throws DAOException
	{
		List keyResourceList = null;
		if (logger.isDebugEnabled())
		{
			logger.debug("querySingerKeyResource( ) is starting ...");
		}

		// select * from t_key_base b, (select * from t_key_resource r where
		// r.tid = ?) y where b.keytable = 't_mb_singer_new' and b.keyid =
		// y.keyid(+)
		String sqlCode = "music.BaseSingerDAO.querySingerKeyResource().SELECT";
		DB db = DB.getInstance();
		ResultSet rs = null;
		Object paras[] =
		{ singerId };
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
			throw new DAOException("ִ�в�ѯ�������ָ�����չ�ֶ�����ʧ��", e);
		}
		finally
		{
			DB.close(rs);
		}
		return keyResourceList;
	}

	/**
	 * ���ڷ��ػ������ָ�����չ�ֶ��б�
	 * 
	 * @return
	 * @throws BOException
	 */
	public List querySingerKeyBaseList(String tid) throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("querySingerKeyBaseList( ) is starting ...");
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
				// where r.tid = ?) y where b.keytable = 't_mb_singer_new' and
				// b.keyid = y.keyid(+)
				sqlCode = "music.BaseSingerDAO.querySingerKeyBaseResList.SELECT";
				Object[] paras =
				{ tid };
				rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
				insert = false;
			}
			else
			{
				// select * from t_key_base b where b.keytable \=
				// 't_mb_singer_new'
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
			throw new DAOException("��ȡ�������ָ�����չ��Ϣ����Ϣ��ѯ�����쳣:", e);
		}
		catch (SQLException e)
		{
			throw new DAOException("��ȡ�������ָ�����չ��Ϣ����Ϣ��ѯ�����쳣:", e);
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
