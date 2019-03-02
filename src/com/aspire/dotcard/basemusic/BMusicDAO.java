/**
 * SSMS
 * com.aspire.ponaadmin.web.datasync.implement.music BMusicDAO.java
 * May 7, 2010
 * @author tungke
 * @version 1.0
 *
 */
package com.aspire.dotcard.basemusic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.db.TransactionDB;

/**
 * @author tungke
 *
 */
public class BMusicDAO
{
	/**
	 * 记录日志的实例对象
	 */
	protected static JLogger LOG = LoggerFactory.getLogger(BMusicDAO.class);
	/**
	 * singleton模式的实例
	 */
	private static BMusicDAO instance = new BMusicDAO();

	/**
	 * 构造方法，由singleton模式调用
	 */
	private BMusicDAO()
	{
	}

	/**
	 * 获取实例
	 * 
	 * @return 实例
	 */
	public static BMusicDAO getInstance() throws DAOException
	{
		return instance;
	}

	/**
	 * 支持事务的数据库操作器，如果为空表示是非事务类型的操作
	 */
	private TransactionDB transactionDB;

	/**
	 * 获取事务类型TransactionDB的实例 如果已经指定了，用已经指定的。如果没有指定，自己创建一个，注意自己创建的直接用不支持事务类型的即可
	 * 
	 * @return TransactionDB
	 */
	private TransactionDB getTransactionDB()
	{

		if (this.transactionDB != null)
		{
			return this.transactionDB;
		}
		return TransactionDB.getInstance();
	}

	/**
	 * 下架货架上的基地音乐商品
	 * @param categoryID
	 */

	public void delBMusicRefByCateID(String categoryID)
	{
		String delSqlCode = "com.aspire.dotcard.basemusic.BMusicDAO.delBMusicRefByCateID().DELETE";
		Object paras[] = { categoryID };
		try
		{
			int result = this.execueSqlCode(delSqlCode, paras);
			LOG.debug("下架货架：" + categoryID + " 上的全部音乐商品成功！");
		} catch (DAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error("下架基地货架：" + categoryID + " 上的全部商品失败！", e);
		}finally
		{
			
		
			
		}
	}

	/**
	 * 下架过期商品
	 * @return
	 */
	public int delInvalBMusicRef(){
		
		String delSqlCode = "com.aspire.dotcard.basemusic.BMusicDAO.delInvalBMusicRef().DELETE";
		int result = 0;
		try
		{
			 result = this.execueSqlCode(delSqlCode, null);
			LOG.debug("下架过期的基地音乐商品成功！");
		} catch (DAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error("下架过期的基地音乐商品 上的全部商品失败！", e);
		}
			return result;
		
	}
	/**
	 * 下架过期商品
	 * @return
	 */
	public int delInvalNewBMusicRef(){
		
		String delSqlCode = "com.aspire.dotcard.basemusic.BMusicDAO.delInvalNewBMusicRef().DELETE";
		int result = 0;
		try
		{
			 result = this.execueSqlCode(delSqlCode, null);
			LOG.debug("下架过期的基地音乐商品成功！");
		} catch (DAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error("下架过期的基地音乐商品 上的全部商品失败！", e);
		}
			return result;
		
	}
	/**
	 *  根据货架名称和货架父id获取货架ID,没有则添加
	 * @param pcategoryID
	 * @param Cname
	 * @return
	 * @throws DAOException 
	 */
	public String getCategoryIDByNameAndParCid(String pcategoryID, String Cname,String listId,int sum) throws DAOException
	{

		String selectSqlCode = "com.aspire.dotcard.basemusic.BMusicDAO.getCategoryIDByNameAndParCid().SELECT";
		Object paras[] = { Cname, pcategoryID };
		
		if(listId.equals("null")){
			listId = "";
		}
		
		String categoryid = null;
		ResultSet rs = null;
		ResultSet rstemp = null;
		try
		{
			 rs = DB.getInstance().queryBySQLCode(selectSqlCode, paras);
			while (rs.next())
			{
				categoryid = rs.getString("id");
			}
			if (categoryid == null)
			{//没有对应货架则插入货架
				// insert into t_mb_category_new
				// (CATEGORYID,PARENTCATEGORYID,CATEGORYNAME,TYPE,DELFLAG,SORTID,SUM,CREATETIME,ALBUM_ID)
				// values(SEQ_BM_CATEGORY_ID.nextval,?,?,?,?,?,?,to_char(sysdate,'yyyy-mm-dd
				// hh24:mi:ss'))
				String  insertCateSqlCode = "com.aspire.dotcard.basemusic.BMusicDAO.getCategoryIDByNameAndParCid().INSERT";
				Object para[] = {pcategoryID,Cname,new String("1"),new String("0"),new Integer(0),new Integer(sum),listId};
				int result = BMusicDAO.getInstance().execueSqlCode(insertCateSqlCode, para);
				//DB.getInstance().queryBySQLCode(sqlCode, null);
				 rstemp = DB.getInstance().queryBySQLCode(selectSqlCode, paras);
				while (rstemp.next())
				{
					categoryid = rstemp.getString("id");
				}
			}else{
				//更新货架上架商品数量
				this.updateCategorySum(sum,categoryid);
				
			}
			LOG.debug("获取货架ID：" + categoryid + " 成功！");
		} catch (DAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error("根据货架父ID：" + pcategoryID + "和名称"+Cname+" 获取货架ID失败！", e);
			throw new  DAOException("数据库操作失败，" + e);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error("根据货架父ID：" + pcategoryID + "和名称"+Cname+" 获取货架ID失败！数据库操作失败", e);
			throw new  DAOException("数据库操作失败，" + e);
		}finally
		{
			 DB.close(rs);
			 DB.close(rstemp);
			
		}
		return categoryid;

	}
	
	public void updateCategorySum(int sum,String  categoryID) throws DAOException{
//		更新货架上架商品数量
		String updateSqlCode = "com.aspire.dotcard.basemusic.BMusicDAO.getCategoryIDByNameAndParCid().UPDATE";
		Object paras1[] = { new Integer(sum), categoryID };
		int result = BMusicDAO.getInstance().execueSqlCode(updateSqlCode, paras1);
		
	}
	
	public int updateAllCategoryRefSum() throws DAOException{
//		更新货架上架商品数量
		String updateAllSqlCode = "com.aspire.dotcard.basemusic.BMusicDAO.updateAllCategoryRefSum().UPDATE";
		//Object paras1[] = { new Integer(sum), categoryID };
		int result = BMusicDAO.getInstance().execueSqlCode(updateAllSqlCode, null);
		return result;
		
	}
	
	public int updateAllNewCategoryRefSum() throws DAOException{
//		更新货架上架商品数量
		String updateAllSqlCode = "com.aspire.dotcard.basemusic.BMusicDAO.updateAllNewCategoryRefSum().UPDATE";
		//Object paras1[] = { new Integer(sum), categoryID };
		int result = BMusicDAO.getInstance().execueSqlCode(updateAllSqlCode, null);
		return result;
		
	}
	/**
	 * 
	 * @return
	 * @throws DAOException 
	 * @throws SQLException 
	 */
	public HashMap getAllexistMusicID() throws DAOException
	{
		String sqlCode = "com.aspire.dotcard.basemusic.BMusicDAO.getAllexistMusicID().SELECT";
		HashMap allExistMusicMap = null;
		ResultSet rs = null;
		try
		{
			
			rs = DB.getInstance().queryBySQLCode(sqlCode, null);
			//ResultSet rs = this.querySqlCode(sqlCode, );
			allExistMusicMap = new HashMap();
			while (rs.next())
			{
				allExistMusicMap.put(rs.getString("id"), new Integer(rs.getInt("delflag")));

			}
		} catch (SQLException e)
		{
			LOG.error("数据库操作失败");
			throw new DAOException("数据库操作失败，" + e);

		}finally
		{
			 DB.close(rs);
			
		}
		return allExistMusicMap;
	}

	/**
	 * 新音乐基地ID
	 * @return
	 * @throws DAOException 
	 * @throws SQLException 
	 */
	public HashMap getAllexistNewMusicID() throws DAOException
	{
		String sqlCode = "com.aspire.dotcard.basemusic.BMusicDAO.getAllexistNewMusicID().SELECT";
		HashMap allExistMusicMap = null;
		ResultSet rs = null;
		try
		{
			
			rs = DB.getInstance().queryBySQLCode(sqlCode, null);
			//ResultSet rs = this.querySqlCode(sqlCode, );
			allExistMusicMap = new HashMap();
			while (rs.next())
			{
				allExistMusicMap.put(rs.getString("id"), new Integer(rs.getInt("delflag")));

			}
		} catch (SQLException e)
		{
			LOG.error("数据库操作失败");
			throw new DAOException("数据库操作失败，" + e);

		}finally
		{
			 DB.close(rs);
			
		}
		return allExistMusicMap;
	}
	
	/**
	 * 新音乐基地专辑ID
	 * @return
	 * @throws DAOException 
	 * @throws SQLException 
	 */
	public HashMap getAllexistAlbumMusicCateID(String parentId) throws DAOException
	{
		
		//select t.album_id,t.delflag from T_MB_CATEGORY_NEW  t where t.parentcategoryid=?
		String sqlCode = "com.aspire.dotcard.basemusic.BMusicDAO.getAllexistAlbumMusicCateID.SELECT";
		HashMap allExistAlbumMusicCateMap = null;
		ResultSet rs = null;
		try
		{
			Object paras[] = {parentId};
			rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
			//ResultSet rs = this.querySqlCode(sqlCode, );
			allExistAlbumMusicCateMap = new HashMap();
			while (rs.next())
			{
				allExistAlbumMusicCateMap.put(rs.getString("album_id"), new Integer(rs.getInt("delflag")));

			}
		} catch (SQLException e)
		{
			LOG.error("数据库操作失败");
			throw new DAOException("数据库操作失败，" + e);

		}finally
		{
			 DB.close(rs);
			
		}
		return allExistAlbumMusicCateMap;
	}
	/**
	 * 新音乐基地货架ID
	 * @return
	 * @throws DAOException 
	 * @throws SQLException 
	 */
	public HashMap getAllexistAlbumMusicCateID() throws DAOException
	{
		
		//select t.album_id,t.CATEGORYID from T_MB_CATEGORY_NEW  t where t.delflag=0 and t.album_id is not null
		String sqlCode = "com.aspire.dotcard.basemusic.BMusicDAO.getAllexistAlbumMusicCateIDAll.SELECT";
		HashMap allExistAlbumMusicCateMap = null;
		ResultSet rs = null;
		try
		{
			
			rs = DB.getInstance().queryBySQLCode(sqlCode, null);
			//ResultSet rs = this.querySqlCode(sqlCode, );
			allExistAlbumMusicCateMap = new HashMap();
			while (rs.next())
			{
				allExistAlbumMusicCateMap.put(rs.getString("album_id"), rs.getString("CATEGORYID"));

			}
		} catch (SQLException e)
		{
			LOG.error("数据库操作失败");
			throw new DAOException("数据库操作失败，" + e);

		}finally
		{
			 DB.close(rs);
			
		}
		return allExistAlbumMusicCateMap;
	}

	/**
	 * 新音乐基地音乐歌手ID
	 * @return
	 * @throws DAOException 
	 * @throws SQLException 
	 */
	public HashMap getAllexistNewMusicSingerID() throws DAOException
	{
		
		//select t.sid,t.singername from T_MB_SINGER_NEW t 
		String sqlCode = "com.aspire.dotcard.basemusic.BMusicDAO.getAllexistNewMusicSingerID().SELECT";
		HashMap allExistMusicSingerMap = null;
		ResultSet rs = null;
		try
		{
			
			rs = DB.getInstance().queryBySQLCode(sqlCode, null);
			//ResultSet rs = this.querySqlCode(sqlCode, );
			allExistMusicSingerMap = new HashMap();
			while (rs.next())
			{
				allExistMusicSingerMap.put(rs.getString("SID"), rs.getString("singername"));

			}
		} catch (SQLException e)
		{
			LOG.error("数据库操作失败");
			throw new DAOException("数据库操作失败，" + e);

		}finally
		{
			 DB.close(rs);
			
		}
		return allExistMusicSingerMap;
	}

	
	public boolean checkCategoryById(String categoryId) throws DAOException
	{
		String sqlCode = "com.aspire.dotcard.basemusic.BMusicDAO.checkCategoryById().SELECT";
		String paras[] = { categoryId };
		ResultSet rs = null;
		try
		{
			rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
			//ResultSet rs = this.querySqlCode(sqlCode, paras);
			while (rs.next())
			{
				//有该货架
				return true;
			}
		} catch (SQLException e)
		{
			LOG.error("数据库操作失败");
			throw new DAOException("数据库操作失败，" + e);

		}finally
		{
			 DB.close(rs);
			
		}

		return false;
	}

	/**
	 * 获取事务类型的DAO实例
	 * 
	 * @return AwardDAO
	 */

	public static BMusicDAO getTransactionInstance(TransactionDB transactionDB)
	{

		BMusicDAO dao = new BMusicDAO();
		dao.transactionDB = transactionDB;
		return dao;
	}

	/**
	 * 执行数据库查询
	 * @param sql
	 * @param parm
	 * @return
	 * @throws DAOException
	 */
//	private ResultSet querySqlCode(String sql, Object[] parm) throws DAOException
//	{
//		ResultSet rs = null;
//		rs = DB.getInstance().queryBySQLCode(sql, parm);
//		return rs;
//	}

	/**
	 * 执行数据库查询
	 * @param sql
	 * @param parm
	 * @return
	 * @throws DAOException
	 */
	public int insertIntoMBSqlCode(String sqlCode, Object[] parm) throws DAOException
	{
		int rs = 0;
		TransactionDB tdb = this.getTransactionDB();
		rs = tdb.executeBySQLCode(sqlCode, parm);

		//rs = DB.getInstance().executeBySQLCode(sql,parm);
		return rs;
	}

	/**
	 * 执行数据库查询
	 * @param sql
	 * @param parm
	 * @return
	 * @throws DAOException
	 */
	private ResultSet querySql(String sql, Object[] parm) throws DAOException
	{
		ResultSet rs = null;
		rs = DB.getInstance().query(sql, parm);
		return rs;
	}

	/**
	 * 执行数据库查询
	 * @param sql
	 * @param parm
	 * @return
	 * @throws DAOException
	 */
	private int execueSql(String sql, Object[] parm) throws DAOException
	{
		int rs = 0;
		rs = DB.getInstance().execute(sql, parm);
		return rs;
	}

	/**
	 * 执行数据库查询
	 * @param sql
	 * @param parm
	 * @return
	 * @throws DAOException
	 */
	public int execueSqlCode(String sql, Object[] parm) throws DAOException
	{
		int rs = 0;
		rs = DB.getInstance().executeBySQLCode(sql, parm);

		return rs;
	}
}
