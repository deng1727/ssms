/**
 * 
 */
package com.aspire.dotcard.baseread.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseread.vo.ReadLikeAuthorVO;
import com.aspire.dotcard.baseread.vo.ReadLikeHisReadVO;
import com.aspire.dotcard.baseread.vo.ReadLikePercentageVO;

/**
 * @author wangminlong
 * 
 */
public class ReadLikeDAO
{
	protected static JLogger log = LoggerFactory.getLogger(ReadLikeDAO.class);
	private static ReadLikeDAO dao = new ReadLikeDAO();
	
	private ReadLikeDAO()
	{}
	
	public static ReadLikeDAO getInstance()
	{
		return dao;
	}
	
	/**
	 * ��ѯ����ϲ����ʷ�Ķ��Ƽ��ӿ��������б�
	 * 
	 * @return
	 * @throws DAOException
	 */
	private Map<String, String> getKeyMapByHis() throws DAOException
	{
		// select t.msisdn from t_rb_like_his_read t
		String sqlCode = "baseread.dao.ReadLikeDAO.getKeyMapByHis";
		Map<String, String> m = null;
		ResultSet rs = null;
		try
		{
			
			rs = DB.getInstance().queryBySQLCode(sqlCode, null);
			m = new HashMap<String, String>();
			while (rs.next())
			{
				m.put(rs.getString("MSISDN"), rs.getString("MSISDN"));
			}
		}
		catch (SQLException e)
		{
			log.error("��ѯ����ϲ����ʷ�Ķ��Ƽ��ӿ��������б����ݿ����ʧ��");
			throw new DAOException("��ѯ����ϲ����ʷ�Ķ��Ƽ��ӿ��������б����ݿ����ʧ�ܣ�" + e);
		}
		catch (DAOException e)
		{
			log.error("��ѯ����ϲ����ʷ�Ķ��Ƽ��ӿ��������б����ݿ����ʧ��");
			throw new DAOException("��ѯ����ϲ����ʷ�Ķ��Ƽ��ӿ��������б����ݿ����ʧ�ܣ�" + e);
		}
		finally
		{
			DB.close(rs);
		}
		return m;
	}
	
	/**
	 * ��ѯ����ϲ�������Ƽ��ӿ��������б�
	 * 
	 * @return
	 * @throws DAOException
	 */
	private Map<String, String> getKeyMapByAuthor() throws DAOException
	{
		// select t.msisdn from t_rb_like_author t
		String sqlCode = "baseread.dao.ReadLikeDAO.getKeyMapByAuthor";
		Map<String, String> m = null;
		ResultSet rs = null;
		try
		{
			
			rs = DB.getInstance().queryBySQLCode(sqlCode, null);
			m = new HashMap<String, String>();
			while (rs.next())
			{
				m.put(rs.getString("MSISDN"), rs.getString("MSISDN"));
			}
		}
		catch (SQLException e)
		{
			log.error("��ѯ����ϲ�������Ƽ��ӿ��������б����ݿ����ʧ��");
			throw new DAOException("��ѯ����ϲ�������Ƽ��ӿ��������б����ݿ����ʧ�ܣ�" + e);
		}
		catch (DAOException e)
		{
			log.error("��ѯ����ϲ�������Ƽ��ӿ��������б����ݿ����ʧ��");
			throw new DAOException("��ѯ����ϲ�������Ƽ��ӿ��������б����ݿ����ʧ�ܣ�" + e);
		}
		finally
		{
			DB.close(rs);
		}
		return m;
	}
	
	/**
	 * ��ѯ����ϲ��ͼ�鼶�Ķ������Ƽ��ӿ��������б�
	 * 
	 * @return
	 * @throws DAOException
	 */
	private Map<String, String> getKeyMapByReadPerc() throws DAOException
	{
		// select t.sourcebookid from t_rb_like_read_perc t
		String sqlCode = "baseread.dao.ReadLikeDAO.getKeyMapByReadPerc";
		Map<String, String> m = null;
		ResultSet rs = null;
		try
		{
			
			rs = DB.getInstance().queryBySQLCode(sqlCode, null);
			m = new HashMap<String, String>();
			while (rs.next())
			{
				m.put(rs.getString("SOURCEBOOKID"), rs
						.getString("SOURCEBOOKID"));
			}
		}
		catch (SQLException e)
		{
			log.error("��ѯ����ϲ��ͼ�鼶�Ķ������Ƽ��ӿ��������б����ݿ����ʧ��");
			throw new DAOException("��ѯ����ϲ��ͼ�鼶�Ķ������Ƽ��ӿ��������б����ݿ����ʧ�ܣ�" + e);
		}
		catch (DAOException e)
		{
			log.error("��ѯ����ϲ��ͼ�鼶�Ķ������Ƽ��ӿ��������б����ݿ����ʧ��");
			throw new DAOException("��ѯ����ϲ��ͼ�鼶�Ķ������Ƽ��ӿ��������б����ݿ����ʧ�ܣ�" + e);
		}
		finally
		{
			DB.close(rs);
		}
		return m;
	}
	
	/**
	 * ��ѯ����ϲ��ͼ�鼶���������Ƽ��ӿ��������б�
	 * 
	 * @return
	 * @throws DAOException
	 */
	private Map<String, String> getKeyMapByOrderPerc() throws DAOException
	{
		// select t.sourcebookid from t_rb_like_order_perc t
		String sqlCode = "baseread.dao.ReadLikeDAO.getKeyMapByOrderPerc";
		Map<String, String> m = null;
		ResultSet rs = null;
		try
		{
			
			rs = DB.getInstance().queryBySQLCode(sqlCode, null);
			m = new HashMap<String, String>();
			while (rs.next())
			{
				m.put(rs.getString("SOURCEBOOKID"), rs
						.getString("SOURCEBOOKID"));
			}
		}
		catch (SQLException e)
		{
			log.error("��ѯ����ϲ��ͼ�鼶���������Ƽ��ӿ��������б����ݿ����ʧ��");
			throw new DAOException("��ѯ����ϲ��ͼ�鼶���������Ƽ��ӿ��������б����ݿ����ʧ�ܣ�" + e);
		}
		catch (DAOException e)
		{
			log.error("��ѯ����ϲ��ͼ�鼶���������Ƽ��ӿ��������б����ݿ����ʧ��");
			throw new DAOException("��ѯ����ϲ��ͼ�鼶���������Ƽ��ӿ��������б����ݿ����ʧ�ܣ�" + e);
		}
		finally
		{
			DB.close(rs);
		}
		return m;
	}
	
	/**
	 * �õ�ָ����Ū��������Ϣ
	 * 
	 * @param type
	 * @return
	 * @throws DAOException
	 */
	public Map<String, String> getKeyMap(int type) throws DAOException
	{
		Map<String, String> map = null;
		
		switch (type)
		{
			// ��ʷ�Ķ��Ƽ��ӿ�
			case 1:
				map = getKeyMapByHis();
				break;
			// �����Ƽ��ӿ�
			case 2:
				map = getKeyMapByAuthor();
				break;
			// ͼ�鼶�Ķ������Ƽ��ӿ�
			case 3:
				map = getKeyMapByReadPerc();
				break;
			// ͼ�鼶���������Ƽ��ӿ�
			case 4:
				map = getKeyMapByOrderPerc();
				break;
		}
		return map;
	}
	
	/**
	 * ����ָ������ɾ��ָ�������������
	 * 
	 * @param type
	 * @return
	 * @throws DAOException
	 * @throws DAOException
	 */
	public int deleteDataByKey(int type, String key) throws DAOException
	{
		String sqlCode = null;
		Object[] paras = new Object[] { key };
		switch (type)
		{
			// ��ʷ�Ķ��Ƽ��ӿ�
			case 1:
				// delete from t_rb_like_his_read t where t.msisdn=?
				sqlCode = "baseread.dao.ReadLikeDAO.deleteDataByKey.his";
				break;
			// �����Ƽ��ӿ�
			case 2:
				// delete from t_rb_like_author t where t.msisdn=?
				sqlCode = "baseread.dao.ReadLikeDAO.deleteDataByKey.author";
				break;
			// ͼ�鼶�Ķ������Ƽ��ӿ�
			case 3:
				// delete from t_rb_like_read_perc t where t.sourcebookid=?
				sqlCode = "baseread.dao.ReadLikeDAO.deleteDataByKey.perc";
				break;
			// ͼ�鼶���������Ƽ��ӿ�
			case 4:
				// delete from t_rb_like_order_perc t where t.sourcebookid=?
				sqlCode = "baseread.dao.ReadLikeDAO.deleteDataByKey.orderPerc";
				break;
		}
		return DB.getInstance().executeBySQLCode(sqlCode, paras);
	}
	
	/**
	 * ������ʷ�Ķ��Ƽ��ӿ�����
	 * 
	 * @param vo
	 * @throws DAOException
	 */
	public void insertDataByHis(ReadLikeHisReadVO vo) throws DAOException
	{
		// insert into t_rb_like_his_read (record_day, msisdn, bookid, sortid,
		// RESON) values (?,?,?,?,?)
		String sql = "baseread.dao.ReadLikeDAO.insertDataByHis";
		String sqlCode[] = new String[vo.getList().size()];
		Object[][] object = new Object[vo.getList().size()][5];
		
		for (int i = 0; i < vo.getList().size(); i++)
		{
			ReadLikeHisReadVO temp = vo.getList().get(i);
			sqlCode[i] = sql;
			object[i][0] = temp.getRecordDay();
			object[i][1] = temp.getMsisdn();
			object[i][2] = temp.getBookId();
			object[i][3] = temp.getSortId();
			object[i][4] = temp.getReson();
		}
		
		try
		{
			DB.getInstance().executeMutiBySQLCode(sqlCode, object);
		}
		catch (DAOException e)
		{
			throw new DAOException("������ʷ�Ķ��Ƽ��ӿ�����ʱ�����쳣:", e);
		}
	}
	
	/**
	 * ���������Ƽ��ӿ�����
	 * 
	 * @param vo
	 * @throws DAOException
	 */
	public void insertDataByAuthor(ReadLikeAuthorVO vo) throws DAOException
	{
		// insert into t_rb_like_author (record_day, msisdn, authorid, bookid,
		// sortid, RECTYPE) values (?,?,?,?,?,?)
		String sql = "baseread.dao.ReadLikeDAO.insertDataByAuthor";
		String sqlCode[] = new String[vo.getList().size()];
		Object[][] object = new Object[vo.getList().size()][6];
		
		for (int i = 0; i < vo.getList().size(); i++)
		{
			ReadLikeAuthorVO temp = vo.getList().get(i);
			sqlCode[i] = sql;
			object[i][0] = temp.getRecordDay();
			object[i][1] = temp.getMsisdn();
			object[i][2] = temp.getAuthorId();
			object[i][3] = temp.getBookId();
			object[i][4] = temp.getSortId();
			object[i][5] = temp.getRecType();
		}
		
		try
		{
			DB.getInstance().executeMutiBySQLCode(sqlCode, object);
		}
		catch (DAOException e)
		{
			throw new DAOException("���������Ƽ��ӿ�����ʱ�����쳣:", e);
		}
	}
	
	/**
	 * ����ͼ�鼶�Ķ������Ƽ��ӿ�����
	 * 
	 * @param vo
	 * @throws DAOException
	 */
	public void insertDataByPercentage(ReadLikePercentageVO vo)
			throws DAOException
	{
		// insert into t_rb_like_read_perc (record_day, sourcebookid, bookid,
		// rate, sortid) values (?,?,?,?,?)
		String sql = "baseread.dao.ReadLikeDAO.insertDataByPercentage";
		String sqlCode[] = new String[vo.getList().size()];
		Object[][] object = new Object[vo.getList().size()][5];
		
		for (int i = 0; i < vo.getList().size(); i++)
		{
			ReadLikePercentageVO temp = vo.getList().get(i);
			sqlCode[i] = sql;
			object[i][0] = temp.getRecordDay();
			object[i][1] = temp.getSourceBookId();
			object[i][2] = temp.getBookId();
			object[i][3] = temp.getRate();
			object[i][4] = temp.getSortId();
		}
		
		try
		{
			DB.getInstance().executeMutiBySQLCode(sqlCode, object);
		}
		catch (DAOException e)
		{
			throw new DAOException("����ͼ�鼶�Ķ������Ƽ��ӿ�����ʱ�����쳣:", e);
		}
	}
	
	/**
	 * ����ͼ�鼶���������Ƽ��ӿ�����
	 * 
	 * @param vo
	 * @throws DAOException
	 */
	public void insertDataByOrderPercentage(ReadLikePercentageVO vo)
			throws DAOException
	{
		// insert into t_rb_like_order_perc (record_day, sourcebookid, bookid,
		// rate, sortid) values (?,?,?,?,?)
		String sql = "baseread.dao.ReadLikeDAO.insertDataByOrderPercentage";
		String sqlCode[] = new String[vo.getList().size()];
		Object[][] object = new Object[vo.getList().size()][5];
		
		for (int i = 0; i < vo.getList().size(); i++)
		{
			ReadLikePercentageVO temp = vo.getList().get(i);
			sqlCode[i] = sql;
			object[i][0] = temp.getRecordDay();
			object[i][1] = temp.getSourceBookId();
			object[i][2] = temp.getBookId();
			object[i][3] = temp.getRate();
			object[i][4] = temp.getSortId();
		}
		
		try
		{
			DB.getInstance().executeMutiBySQLCode(sqlCode, object);
		}
		catch (DAOException e)
		{
			throw new DAOException("����ͼ�鼶���������Ƽ��ӿ�����ʱ�����쳣:", e);
		}
	}
}
