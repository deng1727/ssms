package com.aspire.ponaadmin.web.datasync.implement.game;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.DataAccessException;
import com.aspire.common.persistence.util.SQLCode;
import com.aspire.dotcard.syncData.dao.DataSyncDAO;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;

public class GameSyncDAO
{
	/**
	 * 日志引用
	 */
	private static JLogger logger = LoggerFactory.getLogger(GameSyncDAO.class);

	private static GameSyncDAO dao = new GameSyncDAO();

	public static GameSyncDAO getInstance()
	{
		return dao;
	}
	public boolean isExistedGameService(GameServiceVO vo)throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("isExistedGameService(),icpcode=" + vo.getIcpCode()
					+ ",icpservId=" + vo.getIcpServid());
		}
		Object paras[] = {vo.getIcpServid()};
		String sqlCode = "datasync.implement.game.GameSyncDAO.isExistedGameService().SELECT";
		ResultSet rs= DB.getInstance().queryBySQLCode(sqlCode, paras);
		try
		{
			boolean result=rs.next();
			return result;
		} catch (SQLException e)
		{
			throw new DAOException(e);
		}finally
		{
			DB.close(rs);
		}
	}
	
	public boolean isExistedGameService2(GameServiceVO vo)throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("isExistedGameService(),icpcode=" + vo.getIcpCode()
					+ ",icpservId=" + vo.getIcpServid());
		}
		Object paras[] = {vo.getIcpServid()};
		String sqlCode = "datasync.implement.game.GameSyncDAO.isExistedGameService2";
		ResultSet rs= DB.getInstance().queryBySQLCode(sqlCode, paras);
		try
		{
			return rs.next();
		} catch (SQLException e)
		{
			throw new DAOException(e);
		}finally
		{
			DB.close(rs);
		}
	}

	/**
	 * 新增游戏业务信息
	 * 
	 * @param vo
	 *            游戏业务的vo类
	 * @return 返回新增结果
	 */
	public int insertGameService(GameServiceVO vo)throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("insertGameServiceVO(),icpcode=" + vo.getIcpCode()
					+ ",icpservId=" + vo.getIcpServid());
		}
		Object paras[] = {vo.getContentid(),vo.getIcpCode(),vo.getSpName(),vo.getIcpServid(),vo.getServName(),vo.getServDesc(),vo.getChargeType()
				,vo.getChargDesc(),new Integer(vo.getMobilePrice()),new Integer(vo.getServType()),new Integer(vo.getServFlag()),new Integer(vo.getPtypeId())};
		String sqlCode = "datasync.implement.game.GameSyncDAO.insertGameServiceVO().INSERT";
		DB.getInstance().executeBySQLCode(sqlCode, paras);
		return DataSyncConstants.SUCCESS_ADD;
	}
	
	//add by aiyan 2012-09-12
	public int insertGameService2(GameServiceVO vo)throws DAOException
	{
		
		
		if (logger.isDebugEnabled())
		{
			logger.debug("insertGameServiceVO(),icpcode=" + vo.getIcpCode()
					+ ",icpservId=" + vo.getIcpServid());
		}
		Object paras[] = null;
		String sqlCode = null;
		try{
			paras = new Object[]{vo.getContentid(),vo.getIcpCode(),vo.getSpName(),vo.getIcpServid(),vo.getServName(),vo.getServDesc(),vo.getChargeType()
					,vo.getChargDesc(),new Integer(vo.getMobilePrice()),new Integer(vo.getServType()),new Integer(vo.getServFlag()),new Integer(vo.getPtypeId()),
					vo.getOldprice(),vo.getFirsttype(),vo.getContenttag()};
			sqlCode = "datasync.implement.game.GameSyncDAO.insertGameServiceVO2";
			DB.getInstance().executeBySQLCode(sqlCode, paras);

		}catch(DAOException e){
			logger.error("数据库操作失败" + e);
			try {
				logger.error("出错的执行语句："+getDebugSql(SQLCode.getInstance().getSQLStatement(sqlCode),paras));
			} catch (DataAccessException e1) {
				// TODO Auto-generated catch block
				logger.error(e1);
			}
			throw new DAOException("游戏业务插入出错！",e);
		}
		return DataSyncConstants.SUCCESS_ADD;
	}
	
	private  String getDebugSql(String sql,Object[] para){
		if(para!=null){
			for(int i=0;i<para.length;i++){
				sql = sql.replaceFirst("\\?", "'"+para[i]+"'");
			}
		}
		return sql;
	}

	/**
	 * 删除游戏业务信息
	 * 
	 * @return
	 */
	public void delGameService(String icpCode, String icpServId) throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("deleteAuthor");
		}
		String paras[] = { icpServId };
		String sqlCode = "datasync.implement.game.GameSyncDAO.delGameService().DELETE";
		DB.getInstance().executeBySQLCode(sqlCode, paras);

	}

	/**
	 * 更新游戏业务信息
	 * 
	 * @return
	 */
	public void updateGameService(GameServiceVO vo) throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("updateGameService,icpcode="+vo.getIcpCode()+",icpservid="+vo.getIcpServid());
		}
		Object paras[] = { vo.getContentid(),vo.getSpName(), vo.getServName(),vo.getServDesc(),vo.getChargeType(),vo.getChargDesc(),new Integer(vo.getMobilePrice()),
				new Integer(vo.getServType()),new Integer(vo.getServFlag()),new Integer(vo.getPtypeId()),vo.getIcpCode(),vo.getIcpServid()};
		String sqlCode = "datasync.implement.game.GameSyncDAO.updateGameServices().UPDATE";
		DB.getInstance().executeBySQLCode(sqlCode, paras);

	}
	
	//add by aiyan 2012-09-12
	public void updateGameService2(GameServiceVO vo) throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("updateGameService,icpcode="+vo.getIcpCode()+",icpservid="+vo.getIcpServid());
		}
		Object paras[] = { vo.getContentid(),vo.getSpName(), vo.getServName(),vo.getServDesc(),vo.getChargeType(),vo.getChargDesc(),new Integer(vo.getMobilePrice()),
				new Integer(vo.getServType()),new Integer(vo.getServFlag()),new Integer(vo.getPtypeId()),vo.getIcpCode(),vo.getOldprice(),
				vo.getFirsttype(),vo.getContenttag(),vo.getIcpServid()};
		String sqlCode = "datasync.implement.game.GameSyncDAO.updateGameServices2";
		DB.getInstance().executeBySQLCode(sqlCode, paras);

	}
	/**
	 * 获取当前系统所有基地游戏的列表。
	 * @return
	 */
	public List getAllBaseGames()throws DAOException
	{
		List gameList=new LinkedList();
		String sqlCode="datasync.implement.game.GameSyncDAO.getAllBaseGames().SELECT";
		ResultSet rs= DB.getInstance().queryBySQLCode(sqlCode, null);
		BaseGameKeyVO game=null;
		try
		{
			while(rs.next())
			{
				game=new BaseGameKeyVO();
				game.setId(rs.getString("id"));
				game.setIcpCode(rs.getString("icpCode"));
				game.setIcpServid(rs.getString("icpservid"));
				game.setExpPrice(rs.getInt("expprice"));
				game.setAppcateName(rs.getString("appcatename"));
				game.setChargeTime(rs.getString("chargetime"));
				game.setOnlineType(rs.getInt("onlinetype"));
				game.setStatus(0);
				gameList.add(game);
			}
		} catch (SQLException e)
		{
			throw new DAOException("读取游戏UA映射关系错误");
		}finally
		{
			DB.close(rs);
		}
		return gameList;
	}
	/**
	 * 获取当前基地游戏 单机精品二级分类同步策略
	 * @return
	 */
	public HashMap getAllBaseGameTactic()throws DAOException
	{
		HashMap hm = new HashMap();
		String sqlCode="datasync.implement.game.GameSyncDAO.getAllBaseGameTactic().SELECT";
		ResultSet rs= DB.getInstance().queryBySQLCode(sqlCode, null);
		
		try
		{
			while(rs.next())
			{
				hm.put(rs.getString("aliasname"),rs.getString("cid"));

			}
		} catch (SQLException e)
		{
			throw new DAOException("读取基地游戏单机精品 二级分类同步策略关系错误");
		}finally
		{
			DB.close(rs);
		}
		return hm;
	}
	/**
	 * 删除BaseUA信息
	 * @param list 所有待删除的UA信息的集合
	 * @return
	 * @throws DAOException
	 */
	public int delGameInfo(List list)throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			StringBuffer bf=new StringBuffer();
			for(int i=0;i<list.size();i++)
			{
				if(i!=0)
				{
					bf.append(',');
				}
				bf.append(list.get(i));
			}
			logger.debug("待删除的baseUA为"+bf.toString());
		}
	    Object mutiParas[][] = new Object[list.size()][1];
	    for(int i=0;i<list.size();i++)
		{
	    	mutiParas[i][0]=list.get(i);
		}
		String sqlCode = "datasync.implement.game.GameSyncDAO.delGameInfo().DELETE";
		DB.getInstance().executeBatchBySQLCode(sqlCode, mutiParas);
		return list.size();
	}
	/**
	 * 新增BaseUA信息
	 * @param baseUA
	 * @return int 新增是否成功
	 * @throws DAOException
	 */
	public int addGameInfo(String baseUA)throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("待新增的baseUA为"+baseUA);
		}
	    Object paras[] = {baseUA};
		String sqlCode = "datasync.implement.game.GameSyncDAO.addGameInfo().INSERT";
		DB.getInstance().executeBySQLCode(sqlCode, paras);
		return DataSyncConstants.SUCCESS_ADD;
	}
	/**
     * 刷新v_service 物化视图。
     * 
     */
    public void refreshVService() throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("refreshVService()");
        }
        // 将需求中罗列的字段查询出来；
        // 写一个sql语句将一期的业务表和cms中查出来的信息合成一个物化视图即可。
        
        try{
        DataSyncDAO.getInstance().syncVService();
        }catch(Exception e){
        	
        	logger.error("重建v_service表失败"+e);
        	throw new DAOException("重建v_service表失败",e);
        }
//        CallableStatement cs = null;
//        Connection conn = DB.getInstance().getConnection();
//        try
//        {
//            cs = conn.prepareCall("{call dbms_mview.refresh(list => 'v_service')}");
//            cs.execute();
//        }
//        catch (Exception ex)
//        {
//            throw new DAOException("同步基地游戏时，刷新v_service 物化视图出错",ex);
//        }
//        finally
//        {
//            try
//            {
//                cs.close();
//                DB.close(conn);
//            }
//            catch (Exception ex)
//            {
//                logger.error("关闭CallableStatement=失败");
//            }
//        }
    }

}
