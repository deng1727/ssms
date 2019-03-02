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
	 * 查询猜你喜欢历史阅读推荐接口中主键列表
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
			log.error("查询猜你喜欢历史阅读推荐接口中主键列表，数据库操作失败");
			throw new DAOException("查询猜你喜欢历史阅读推荐接口中主键列表，数据库操作失败，" + e);
		}
		catch (DAOException e)
		{
			log.error("查询猜你喜欢历史阅读推荐接口中主键列表，数据库操作失败");
			throw new DAOException("查询猜你喜欢历史阅读推荐接口中主键列表，数据库操作失败，" + e);
		}
		finally
		{
			DB.close(rs);
		}
		return m;
	}
	
	/**
	 * 查询猜你喜欢名家推荐接口中主键列表
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
			log.error("查询猜你喜欢名家推荐接口中主键列表，数据库操作失败");
			throw new DAOException("查询猜你喜欢名家推荐接口中主键列表，数据库操作失败，" + e);
		}
		catch (DAOException e)
		{
			log.error("查询猜你喜欢名家推荐接口中主键列表，数据库操作失败");
			throw new DAOException("查询猜你喜欢名家推荐接口中主键列表，数据库操作失败，" + e);
		}
		finally
		{
			DB.close(rs);
		}
		return m;
	}
	
	/**
	 * 查询猜你喜欢图书级阅读关联推荐接口中主键列表
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
			log.error("查询猜你喜欢图书级阅读关联推荐接口中主键列表，数据库操作失败");
			throw new DAOException("查询猜你喜欢图书级阅读关联推荐接口中主键列表，数据库操作失败，" + e);
		}
		catch (DAOException e)
		{
			log.error("查询猜你喜欢图书级阅读关联推荐接口中主键列表，数据库操作失败");
			throw new DAOException("查询猜你喜欢图书级阅读关联推荐接口中主键列表，数据库操作失败，" + e);
		}
		finally
		{
			DB.close(rs);
		}
		return m;
	}
	
	/**
	 * 查询猜你喜欢图书级订购关联推荐接口中主键列表
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
			log.error("查询猜你喜欢图书级订购关联推荐接口中主键列表，数据库操作失败");
			throw new DAOException("查询猜你喜欢图书级订购关联推荐接口中主键列表，数据库操作失败，" + e);
		}
		catch (DAOException e)
		{
			log.error("查询猜你喜欢图书级订购关联推荐接口中主键列表，数据库操作失败");
			throw new DAOException("查询猜你喜欢图书级订购关联推荐接口中主键列表，数据库操作失败，" + e);
		}
		finally
		{
			DB.close(rs);
		}
		return m;
	}
	
	/**
	 * 得到指定类弄的主键信息
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
			// 历史阅读推荐接口
			case 1:
				map = getKeyMapByHis();
				break;
			// 名家推荐接口
			case 2:
				map = getKeyMapByAuthor();
				break;
			// 图书级阅读关联推荐接口
			case 3:
				map = getKeyMapByReadPerc();
				break;
			// 图书级订购关联推荐接口
			case 4:
				map = getKeyMapByOrderPerc();
				break;
		}
		return map;
	}
	
	/**
	 * 根据指定主键删除指定表中相关数据
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
			// 历史阅读推荐接口
			case 1:
				// delete from t_rb_like_his_read t where t.msisdn=?
				sqlCode = "baseread.dao.ReadLikeDAO.deleteDataByKey.his";
				break;
			// 名家推荐接口
			case 2:
				// delete from t_rb_like_author t where t.msisdn=?
				sqlCode = "baseread.dao.ReadLikeDAO.deleteDataByKey.author";
				break;
			// 图书级阅读关联推荐接口
			case 3:
				// delete from t_rb_like_read_perc t where t.sourcebookid=?
				sqlCode = "baseread.dao.ReadLikeDAO.deleteDataByKey.perc";
				break;
			// 图书级订购关联推荐接口
			case 4:
				// delete from t_rb_like_order_perc t where t.sourcebookid=?
				sqlCode = "baseread.dao.ReadLikeDAO.deleteDataByKey.orderPerc";
				break;
		}
		return DB.getInstance().executeBySQLCode(sqlCode, paras);
	}
	
	/**
	 * 新增历史阅读推荐接口数据
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
			throw new DAOException("新增历史阅读推荐接口数据时发生异常:", e);
		}
	}
	
	/**
	 * 新增名家推荐接口数据
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
			throw new DAOException("新增名家推荐接口数据时发生异常:", e);
		}
	}
	
	/**
	 * 新增图书级阅读关联推荐接口数据
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
			throw new DAOException("新增图书级阅读关联推荐接口数据时发生异常:", e);
		}
	}
	
	/**
	 * 新增图书级订购关联推荐接口数据
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
			throw new DAOException("新增图书级订购关联推荐接口数据时发生异常:", e);
		}
	}
}
