package com.aspire.dotcard.syncAndroid.autosync.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.RowSet;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.syncAndroid.autosync.vo.AutoVO;

public class AutoSyncDAO
{
	
	/**
	 * 日志引用
	 */
	JLogger logger = LoggerFactory.getLogger(AutoSyncDAO.class);
	
	private static AutoSyncDAO dao = new AutoSyncDAO();
	
	private AutoSyncDAO()
	{}
	
	/**
	 * 单例模式
	 * 
	 * @return
	 */
	public static AutoSyncDAO getInstance()
	{
		return dao;
	}
	
	/**
	 * 查询需要向数据中心发送同步消息的货架
	 * 
	 * @return
	 * @throws DAOException
	 */
	public List<AutoVO> queryAutoList() throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("AutoSyncDAO.queryAutoList( ) is start...");
		}
		
		// select t.* from t_a_auto_category t, t_r_category c where t.categoryid = c.categoryid
		String sqlCode = "syncAndroid.autosync.queryAutoList().SELECT";
		List<AutoVO> list = new ArrayList<AutoVO>();
		ResultSet rs = null;
		
		try
		{
			rs = DB.getInstance().queryBySQLCode(sqlCode, null);
			
			while (rs.next())
			{
				AutoVO vo = new AutoVO();
				
				vo.setId(String.valueOf(rs.getInt("id")));
				vo.setCategoryId(rs.getString("categoryId"));
				vo.setIsNullByAuto(rs.getString("isnulltosync"));
				
				list.add(vo);
			}
		}
		catch (SQLException e)
		{
			logger.error(e);
			throw new DAOException("查询导出任务列表时发生数据库异常！", e);
		}
		finally
		{
			DB.close(rs);
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("AutoSyncDAO.queryAutoList( ) is end...");
		}
		
		return list;
	}
	
	/**
	 * 查询指定货架下是否存在商品
	 * 
	 * @param categoryId
	 * @return
	 */
	public boolean hasRef(String categoryId) 
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("AutoSyncDAO.hasRef(" + categoryId + ") is start...");
		}
		
		// select 1 from t_r_reference r where r.categoryid=?
		String sqlCode = "syncAndroid.autosync.hasRef().SELECT";
		ResultSet rs = null;
		
		try
		{
			rs = DB.getInstance().queryBySQLCode(sqlCode, new Object[]{categoryId});
			
			if (rs.next())
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch (DAOException e)
		{
			logger.error(e);
			return false;
		}
		catch (SQLException e)
		{
			logger.error(e);
			return false;
		}
		finally
		{
			DB.close(rs);
		}
	}
	
	public void addAutoRef(String cid)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("AutoSyncDAO.addAutoRef( ) is start...");
		}
		
		String[] mutiSQLCode = new String[2];
		// delete from t_r_reference_auto a where a.categoryid = (select c.categoryid from t_r_category c where c.id=?)
		mutiSQLCode[0] = "syncAndroid.autosync.addAutoRef().DEL";
		
		// insert into t_r_reference_auto value select * from t_r_reference r where r.categoryid = (select c.categoryid from t_r_category c where c.id=?)
		mutiSQLCode[1] = "syncAndroid.autosync.addAutoRef().ADD";
		
		String[][] mutiParas = new String[2][1];
		mutiParas[0][0]=cid;
		mutiParas[1][0]=cid;
		
		try
		{
			DB.getInstance().executeMutiBySQLCode(mutiSQLCode, mutiParas);
		}
		catch (DAOException e)
		{
			logger.error(e);
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("AutoSyncDAO.addAutoRef( ) is end...");
		}
	}
	/**
	 * 根据categoryId 获取id
	 * @param categoryId
	 * @return
	 */
	   public  String getCategoryCId(String categoryId){
	    	String sqlCode="syncAndroid.AutoSyncDAO.getCategoryCId.SELECT";
	    	// select id from t_r_category t where t.categoryid=?
	    	String cid = null;
	        try
	        {
	            Object[] paras = {categoryId};
	            RowSet rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
	            if(rs.next()){
	            	cid = rs.getString("id");
	            }
	            
	        }
	        catch (Exception e)
	        {
	        	logger.error(e);
	            e.printStackTrace();
	        }
	        return cid;
	    	
	    }
	
}
