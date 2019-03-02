/*
 * 文件名：BaseVideoNewFileDAO.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */

package com.aspire.dotcard.baseVideoNew.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseVideoNew.vo.NodeVO;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2012
 * </p>
 * 
 * @author wangminlong
 * @version
 */
public class BaseVideoNewFileDAO
{
	protected static JLogger logger = LoggerFactory
			.getLogger(BaseVideoNewFileDAO.class);
	
	private static BaseVideoNewFileDAO dao = new BaseVideoNewFileDAO();
	
	private BaseVideoNewFileDAO()
	{}
	
	public static BaseVideoNewFileDAO getInstance()
	{
		return dao;
	}
	
	/**
	 * 得到用于校验数据合法性的正式表与中间表合表数据
	 * 
	 * @param map
	 *            正式表數據集
	 * @param mapAdd
	 *            中間表新增數據集
	 * @param mapDel
	 *            中間表刪除數據集
	 * @return 合表數據集
	 */
	public Map<String, String> getCheckMidIDMap(Map<String, String> map,
			Map<String, String> mapAdd, Map<String, String> mapDel)
	{
		map.putAll(mapAdd);
		
		Iterator<String> it = mapDel.keySet().iterator();
		while (it.hasNext())
		{
			String key = it.next();
			if (map.containsKey(key))
			{
				map.remove(key);
			}
		}
		
		return map;
	}
	
	/**
	 * 用来得到视频ID列表
	 * 
	 * @return
	 */
	public Map<String, String> getVideoIDMap()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("得到视频ID列表,开始");
		}
		Map<String, String> videoIDMap = new HashMap<String, String>();
		ResultSet rs = null;
		
		try
		{
			// select v.videoid from t_vo_video v
			rs = DB.getInstance().queryBySQLCode(
					"baseVideoNew.dao.BaseVideoNewFileDAO.getVideoIDMap", null);
			while (rs.next())
			{
				videoIDMap.put(rs.getString("videoid"), "");
			}
		}
		catch (SQLException e)
		{
			logger.error("数据库SQL执行异常，查询失败", e);
		}
		catch (DAOException ex)
		{
			logger.error("数据库操作异常，查询失败", ex);
		}
		finally
		{
			DB.close(rs);
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("得到视频ID列表,结束");
		}
		return videoIDMap;
	}
	
	/**
	 * 根据操作类型得到视频中间表ID列表
	 * 
	 * @return
	 */
	public Map<String, String> getVideoMidIDMap(String status)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("根据操作类型得到视频中间表ID列表,开始");
		}
		Map<String, String> videoIDMap = new HashMap<String, String>();
		ResultSet rs = null;
		
		try
		{
			// select v.videoid from t_vo_video_mid v where v.status = ?
			rs = DB.getInstance().queryBySQLCode(
					"baseVideoNew.dao.BaseVideoNewFileDAO.getVideoMidIDMap",
					new Object[] { status });
			while (rs.next())
			{
				videoIDMap.put(rs.getString("videoid"), "");
			}
		}
		catch (SQLException e)
		{
			logger.error("数据库SQL执行异常，查询失败", e);
		}
		catch (DAOException ex)
		{
			logger.error("数据库操作异常，查询失败", ex);
		}
		finally
		{
			DB.close(rs);
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("根据操作类型得到视频中间表ID列表,结束");
		}
		return videoIDMap;
	}
	
	/**
	 * 用来得到栏目ID列表
	 * 
	 * @return 栏目ID列表
	 */
	public Map<String, String> getNodeIDMap()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("得到栏目信息表ID列表,开始");
		}
		Map<String, String> nodeIdMap = new HashMap<String, String>();
		ResultSet rs = null;
		
		try
		{
			// select n.nodeid from t_vo_node n
			rs = DB.getInstance().queryBySQLCode(
					"baseVideoNew.dao.BaseVideoNewFileDAO.getNodeIDMap", null);
			while (rs.next())
			{
				nodeIdMap.put(rs.getString("nodeid"), "");
			}
		}
		catch (SQLException e)
		{
			logger.error("数据库SQL执行异常，查询失败", e);
		}
		catch (DAOException ex)
		{
			logger.error("数据库操作异常，查询失败", ex);
		}
		finally
		{
			DB.close(rs);
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("得到栏目信息表ID列表,结束");
		}
		return nodeIdMap;
	}
	
	/**
	 * 根据操作类型得到栏目中间表ID列表
	 * 
	 * @return
	 */
	public Map<String, String> getNodeMidIDMap(String status)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("根据操作类型得到栏目中间表ID列表,开始");
		}
		Map<String, String> nodeIdMap = new HashMap<String, String>();
		ResultSet rs = null;
		try
		{
			// select n.nodeid from t_vo_node_mid n where n.status = ?
			rs = DB.getInstance().queryBySQLCode(
					"baseVideoNew.dao.BaseVideoNewFileDAO.getNodeMidIDMap",
					new Object[] { status });
			while (rs.next())
			{
				nodeIdMap.put(rs.getString("nodeid"), "");
			}
		}
		catch (SQLException e)
		{
			logger.error("数据库SQL执行异常，查询失败", e);
		}
		catch (DAOException ex)
		{
			logger.error("数据库操作异常，查询失败", ex);
		}
		finally
		{
			DB.close(rs);
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("根据操作类型得到栏目中间表ID列表,结束");
		}
		return nodeIdMap;
	}
	
	/**
	 * 根据栏目ID获取栏目信息
	 * 
	 * @return 栏目信息
	 */
	public NodeVO getNodeDataByNodeId(String nodeId)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("getNodeDataByNodeId(" + nodeId + ") is starting ...");
		}
		
		// select
		// n.nodeid,n.nodename,n.description,n.logopath,n.productid,n.collectflag
		// from t_vo_node n where n.nodeid=?
		String sqlCode = "baseVideoNew.dao.BaseVideoNewFileDAO.getNodeDataByNodeId.SELECT";
		ResultSet rs = null;
		NodeVO vo = null;
		
		try
		{
			rs = DB.getInstance().queryBySQLCode(sqlCode,
					new Object[] { nodeId });
			
			if (rs.next())
			{
				vo = new NodeVO();
				vo.setNodeId(rs.getString("nodeid"));
				vo.setNodename(rs.getString("nodename"));
				vo.setDescription(rs.getString("description"));
				vo.setLogopath(rs.getString("logopath"));
				vo.setProductid(rs.getString("productid"));
				vo.setCollectflag(rs.getString("collectflag") == null ? 1 : Integer.parseInt(rs.getString("collectflag")));
			}
		}
		catch (SQLException e)
		{
			logger.error("数据库SQL执行异常，查询失败", e);
			vo = null;
		}
		catch (DAOException ex)
		{
			logger.error("数据库操作异常，查询失败", ex);
			vo = null;
		}
		finally
		{
			DB.close(rs);
		}
		return vo;
	}
	
	/**
	 * 用来得到内容集节点ID列表
	 * 
	 * @return 栏目ID列表
	 */
	public Map<String, String> getCollectNodeIDMap()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("得到内容集节点表ID列表,开始");
		}
		Map<String, String> collectNodeIdMap = new HashMap<String, String>();
		ResultSet rs = null;
		
		try
		{
			// select n.nodeid,n.parentnodeid from t_vo_collect_node n
			rs = DB.getInstance().queryBySQLCode(
					"baseVideoNew.dao.BaseVideoNewFileDAO.getCollectNodeIDMap", null);
			while (rs.next())
			{
				collectNodeIdMap.put(rs.getString("nodeid")+"|"+rs.getString("parentnodeid"), "");
			}
		}
		catch (SQLException e)
		{
			logger.error("数据库SQL执行异常，查询失败", e);
		}
		catch (DAOException ex)
		{
			logger.error("数据库操作异常，查询失败", ex);
		}
		finally
		{
			DB.close(rs);
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("得到内容集节点表ID列表,结束");
		}
		return collectNodeIdMap;
	}
	
	/**
	 * 用来得到内容集ID列表
	 * 
	 * @return 内容集ID列表
	 */
	public Map<String, String> getCollectIDMap()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("得到内容集表ID列表,开始");
		}
		Map<String, String> collectIdMap = new HashMap<String, String>();
		ResultSet rs = null;
		
		try
		{
			// select c.collectid from t_vo_collect 
			rs = DB.getInstance().queryBySQLCode(
					"baseVideoNew.dao.BaseVideoNewFileDAO.getCollectIDMap", null);
			while (rs.next())
			{
				collectIdMap.put(rs.getString("collectid"), "");
			}
		}
		catch (SQLException e)
		{
			logger.error("数据库SQL执行异常，查询失败", e);
		}
		catch (DAOException ex)
		{
			logger.error("数据库操作异常，查询失败", ex);
		}
		finally
		{
			DB.close(rs);
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("得到内容集表ID列表,结束");
		}
		return collectIdMap;
	}
	/**
	 * 用来得到产品ID列表
	 * 
	 * @return 产品集ID列表
	 */
	public Map<String, String> getCostProductIDMap()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("得到产品ID列表,开始");
		}
		Map<String, String> collectIdMap = new HashMap<String, String>();
		ResultSet rs = null;
		
		try
		{
			rs = DB.getInstance().queryBySQLCode(
					"baseVideoNew.dao.BaseVideoNewFileDAO.getCostProductIDMap", null);
			while (rs.next())
			{
				collectIdMap.put(rs.getString("productid"), "");
			}
		}
		catch (SQLException e)
		{
			logger.error("数据库SQL执行异常，查询失败", e);
		}
		catch (DAOException ex)
		{
			logger.error("数据库操作异常，查询失败", ex);
		}
		finally
		{
			DB.close(rs);
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("得到产品ID列表,结束");
		}
		return collectIdMap;
	}
	
	/**
	 * 得到用于校驗直播節目單的视频详情ID列表
	 * 
	 * @return
	 */
	public Map<String, String> getProgramNodeIDMap()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("得到用于校驗直播節目單的视频详情ID列表,开始！");
		}
		Map<String, String> programIDMap = new HashMap<String, String>();
		ResultSet rs = null;
		
		try
		{
			// select t.nodeid,t.programid from t_vo_program t
			rs = DB.getInstance().queryBySQLCode(
					"baseVideoNew.dao.BaseVideoNewFileDAO.getProgramIDMap",
					null);
			while (rs.next())
			{
				programIDMap.put(rs.getString("nodeid") + "|"
						+ rs.getString("programid"), "");
			}
		}
		catch (SQLException e)
		{
			logger.error("数据库SQL执行异常，查询失败", e);
		}
		catch (DAOException ex)
		{
			logger.error("数据库操作异常，查询失败", ex);
		}
		finally
		{
			DB.close(rs);
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("得到用于校驗直播節目單的视频详情ID列表,结束");
		}
		return programIDMap;
	}
	
	/**
	 * 根据操作类型得到用于校驗直播節目單的视频详情中间表ID列表
	 * 
	 * @return
	 */
	public Map<String, String> getProgramNodeMidIDMap(String status)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("根据操作类型得到用于校驗直播節目單的视频详情中间表ID列表,开始！");
		}
		Map<String, String> programIDMap = new HashMap<String, String>();
		ResultSet rs = null;
		
		try
		{
			// select t.nodeid,t.programid from t_vo_program_mid t where
			// t.status = ?
			rs = DB
					.getInstance()
					.queryBySQLCode(
							"baseVideoNew.dao.BaseVideoNewFileDAO.getProgramNodeMidIDMap",
							new Object[] { status });
			while (rs.next())
			{
				programIDMap.put(rs.getString("nodeid") + "|"
						+ rs.getString("programid"), "");
			}
		}
		catch (SQLException e)
		{
			logger.error("数据库SQL执行异常，查询失败", e);
		}
		catch (DAOException ex)
		{
			logger.error("数据库操作异常，查询失败", ex);
		}
		finally
		{
			DB.close(rs);
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("根据操作类型得到用于校驗直播節目單的视频详情中间表ID列表,结束");
		}
		return programIDMap;
	}
	
	/**
	 * 用来得到视频产品ID列表
	 * 
	 * @return
	 */
	public Map<String, String> getVideoProductMap()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("来得到视频产品ID列表,开始");
		}
		Map<String, String> productIDMap = new HashMap<String, String>();
		ResultSet rs = null;
		
		try
		{
			// select v.PRODUCTID from T_VO_PRODUCT v
			rs = DB.getInstance().queryBySQLCode(
					"baseVideoNew.dao.BaseVideoNewFileDAO.getVideoProductMap",
					null);
			while (rs.next())
			{
				productIDMap.put(rs.getString("PRODUCTID"), "");
			}
		}
		catch (SQLException e)
		{
			logger.error("数据库SQL执行异常，查询失败", e);
		}
		catch (DAOException ex)
		{
			logger.error("数据库操作异常，查询失败", ex);
		}
		finally
		{
			DB.close(rs);
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("来得到视频产品ID列表,结束");
		}
		return productIDMap;
	}
	
	/**
	 * 得到视频详情ID列表
	 * 
	 * @return
	 */
	public Map<String, String> getProgramIDMap()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("得到视频详情ID列表,开始！");
		}
		Map<String, String> programIDMap = new HashMap<String, String>();
		ResultSet rs = null;
		
		try
		{
			// select t.programid from t_vo_program t
			       //baseVideoNew.dao.BaseVideoNewFileDAO.getProgramIDMap=select t.nodeid,t.programid from t_vo_program t
			rs = DB.getInstance().queryBySQLCode(
					"baseVideoNew.dao.BaseVideoNewFileDAO.getProgramIDMap",
					null);
			while (rs.next())
			{
				String keyid = rs.getString("programid")+"|"+rs.getString("nodeid");
				programIDMap.put(keyid, "");
			}
		}
		catch (SQLException e)
		{
			logger.error("数据库SQL执行异常，查询失败", e);
		}
		catch (DAOException ex)
		{
			logger.error("数据库操作异常，查询失败", ex);
		}
		finally
		{
			DB.close(rs);
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("得到视频详情ID列表,结束");
		}
		return programIDMap;
	}
	
	/**
	 * 得到视频直播节目单Key列表
	 * 
	 * @return
	 */
	public Map<String, String> getLiveIDMap()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("得到视频直播节目单Key列表,开始！");
		}
		Map<String, String> programIDMap = new HashMap<String, String>();
		ResultSet rs = null;
		
		try
		{
			// select l.videoid, l.starttime from t_vo_live l
			rs = DB.getInstance().queryBySQLCode(
					"baseVideoNew.dao.BaseVideoNewFileDAO.getLiveIDMap", null);
			while (rs.next())
			{
				programIDMap.put(rs.getString("videoid") + "|"
						+ rs.getString("starttime"), "");
			}
		}
		catch (SQLException e)
		{
			logger.error("数据库SQL执行异常，查询失败", e);
		}
		catch (DAOException ex)
		{
			logger.error("数据库操作异常，查询失败", ex);
		}
		finally
		{
			DB.close(rs);
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("得到视频直播节目单Key列表,结束");
		}
		return programIDMap;
	}
	
	/**
	 * 得到视频节目统计Key列表
	 * 
	 * @return
	 */
	public Map<String, String> getVideoDetailIDMap()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("得到节目统计列表,开始！");
		}
		Map<String, String> programIDMap = new HashMap<String, String>();
		ResultSet rs = null;
		
		try
		{
			// select v.programid from t_Vo_Videodetail v
			rs = DB.getInstance().queryBySQLCode(
					"baseVideoNew.dao.BaseVideoNewFileDAO.getVideoDetailIDMap",
					null);
			while (rs.next())
			{
				programIDMap.put(rs.getString("programid"), "0");
			}
		}
		catch (SQLException e)
		{
			logger.error("数据库SQL执行异常，查询失败", e);
		}
		catch (DAOException ex)
		{
			logger.error("数据库操作异常，查询失败", ex);
		}
		finally
		{
			DB.close(rs);
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("得到节目统计列表,结束");
		}
		return programIDMap;
	}
	
	/**
	 * 删除同步中间表当前数据
	 * 
	 * @param renameTable
	 * @param defSuffix
	 * @throws BOException
	 */
	public void truncateTempSync(String renameTable, String defSuffix)
			throws BOException
	{
		
		if (logger.isDebugEnabled())
		{
			logger.debug("truncateTempSync 删除所有同步临时表数据,开始 renameTables: "
					+ renameTable + ",defSuffix:" + defSuffix);
		}
		
		String truncateSql = " truncate table " + renameTable + defSuffix;
		
		try
		{
			DB.getInstance().execute(truncateSql, null);
		}
		catch (DAOException e)
		{
			logger.error("truncateTempSync,删除所有同步临时表数据失败:", e);
			throw new BOException("删除所有同步临时表数据:" + PublicUtil.GetCallStack(e)
					+ "<br>");
		}
	}
	
	/**
	 * 用于插入预删除数据至指定中间表
	 * 
	 * @param sql
	 * @param key
	 * @return
	 * @throws BOException
	 */
	public int delDataByKey(String sql, String[] key) throws BOException
	{
		int ret = 0;
		
		try
		{
			ret = DB.getInstance().executeBySQLCode(sql, key);
		}
		catch (DAOException e)
		{
			logger.error("truncateTempSync,删除所有同步临时表数据失败:", e);
			throw new BOException("删除所有同步临时表数据:" + PublicUtil.GetCallStack(e)
					+ "<br>");
		}
		
		return ret;
	}
	
	/**
	 * 用于清空旧的模拟表数据，进行每天全量同步
	 */
	public String cleanOldSimulationData() throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("用于清空旧的模拟表数据,开始");
		}
		
		StringBuffer sf = new StringBuffer();
		
		// 删除模拟商品表
		// delete from t_vo_reference r where r.baseid in (select c.baseid from
		// t_vo_category c start with c.parentid = '101' connect by prior c.id =
		// c.parentid)
		String sqlCode = "baseVideoNew.dao.BaseVideoNewFileDAO.cleanOldSimulationData.reference";
		
		// 删除模拟货架表
		// delete from t_vo_category v where v.id in (select c.id from
		// t_vo_category c start with c.parentid = '101' connect by prior c.id =
		// c.parentid union select c.id from t_vo_category c where c.parentid is
		// null and c.id not in('101','202','303','404'))
		String delRefSqlCode = "baseVideoNew.dao.BaseVideoNewFileDAO.cleanOldSimulationData.category";
		
		try
		{
			int ret1 = DB.getInstance().executeBySQLCode(sqlCode, null);
			sf.append("删除基地视频模拟商品表中数据" + ret1 + "条。<br>");
			int ret2 = DB.getInstance().executeBySQLCode(delRefSqlCode, null);
			sf.append("删除基地视频模拟货架表中数据" + ret2 + "条。<br>");
		}
		catch (DAOException e)
		{
			logger.error("执行清空旧的模拟表数据SQL发生异常，删除旧表失败", e);
			throw new BOException("执行清空旧的模拟表数据SQL发生异常", e);
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("用于清空旧的模拟表数据,结束。");
		}
		
		logger.info(sf.toString());
		return sf.toString();
	}
	
	/**
	 * 自定义组装模拟树结构表
	 * 
	 */
	public String insertDataToTree() throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("用于自定义组装模拟树结构表,开始");
		}
		StringBuffer sf = new StringBuffer();
		
		// 加入栏目信息
		// insert into t_vo_category (id, parentid, baseid, baseparentid,
		// basetype, basename) select SEQ_VO_CATEGORY_ID.NEXTVAL,
		// decode(n.parentnodeid,'-1','101',null), n.nodeid, n.parentnodeid, '1'
		// as type, n.nodename from t_vo_node n start with n.parentnodeid = '-1'
		// connect by prior n.nodeid = n.parentnodeid
		String sqlCode = "baseVideoNew.dao.BaseVideoNewFileDAO.insertDataToTree.node";
		
		// 更新栏目父id
		// update t_vo_category c set c.parentid = (select v.id from
		// t_vo_category v where v.baseid = c.baseparentid and v.basetype = '1')
		// where c.parentid is null and c.basetype = '1'
		String sqlCode1 = "baseVideoNew.dao.BaseVideoNewFileDAO.insertDataToTree.updateNode";
		
		try
		{
			int ret1 = DB.getInstance().executeBySQLCode(sqlCode, null);
			sf.append("自定义组装模拟树结构加入数据" + ret1 + "条。<br>");
			int ret2 = DB.getInstance().executeBySQLCode(sqlCode1, null);
			sf.append("更新模拟树结构更新数据" + ret2 + "条。<br>");
		}
		catch (DAOException e)
		{
			logger.error("执行自定义组装模拟树结构表时发生异常，创建表失败", e);
			throw new BOException("执行自定义组装模拟树结构表时发生异常", e);
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("用于自定义组装模拟树结构表,结束");
		}
		
		logger.info(sf.toString());
		return sf.toString();
	}
	
	/**
	 * 自定义组装模拟商品结构表
	 * 
	 */
	public String insertDataToReference() throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("用于自定义组装模拟商品结构表,开始");
		}
		StringBuffer sf = new StringBuffer();
		
		// 加入栏目商品信息
		// insert into t_vo_reference (id, categoryid, baseid, basetype,
		// programid, programname,sortid) select SEQ_VO_REFERENCE_ID.NEXTVAL,
		// c.id, baseid, basetype, p.programid, p.programname,rownum from
		// t_vo_node n, t_vo_category c, t_vo_program p where n.nodeid =
		// c.baseid and p.nodeid = n.nodeid and c.basetype = '1'
		String sqlCode = "baseVideoNew.dao.BaseVideoNewFileDAO.insertDataToReference.node";
		
		try
		{
			int ret = DB.getInstance().executeBySQLCode(sqlCode, null);
			sf.append("执行自定义组装模拟商品结构表加入数据" + ret + "条。<br>");
		}
		catch (DAOException e)
		{
			logger.error("执行自定义组装模拟商品结构表时发生异常，创建表失败", e);
			throw new BOException("执行自定义组装模拟商品结构表时发生异常", e);
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("用于自定义组装模拟树商品构表,结束");
		}
		
		logger.info(sf.toString());
		return sf.toString();
	}
	
	/**
	 * 得到不同中间表中数据的分类信息。用以放入结果邮件
	 * 
	 * @return
	 */
	public String getMidTableGroupBy(String tableName)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("得到不同中间表中数据的分类信息,开始！");
		}
		StringBuffer sb = new StringBuffer();
		ResultSet rs = null;
		
		try
		{
			// select decode(status,'D','delete','A','insert','U','update',status) status, count(1) num from XXX group by status
			String sql = DB.getInstance().getSQLByCode(
					"baseVideoNew.dao.BaseVideoNewFileDAO.getMidTableGroupBy");
			sql = sql.replaceAll("XXX", tableName);
			rs = DB.getInstance().query(sql, null);
			while (rs.next())
			{
				sb.append(rs.getString("status"));
				sb.append(":");
				sb.append(rs.getInt("num")).append("条").append("<br>");
			}
		}
		catch (SQLException e)
		{
			logger.error("数据库SQL执行异常，查询失败", e);
		}
		catch (DAOException ex)
		{
			logger.error("数据库操作异常，查询失败", ex);
		}
		finally
		{
			DB.close(rs);
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("得到不同中间表中数据的分类信息,结束");
		}
		return sb.toString();
	}
	
	/**
	 * 调用存储过程 用以执行中间表与正式表中数据转移
	 */
	public int callSyncVideoData()
	{
		CallableStatement cs = null;
		Connection conn = null;
		try
		{
			conn = DB.getInstance().getConnection();
			
			cs = conn.prepareCall("{call p_delete_insert()}");
			cs.execute();
			return 1;
		}
		catch (Exception e)
		{
			logger.error("调用存储过程 用以执行中间表与正式表中数据转移时，出错！！！", e);
			return 0;
		}finally {
			if (cs != null) {
				try {
					cs.close();
				} catch (Exception e) {
					logger.error("关闭CallableStatement=失败",e);
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					logger.error("关闭Connection=失败",e);
				}
			}
		}
	}
	
	/**
	 * 调用存储过程 用以执行视频全量临时表与中间表中数据转移
	 */
	public int callSyncVideoFullData()
	{
		CallableStatement cs = null;
		Connection conn = null;
		try
		{
			conn = DB.getInstance().getConnection();
			
			cs = conn.prepareCall("{call p_videofull_delete_insert()}");
			cs.execute();
			return 1;
		}
		catch (Exception e)
		{
			logger.error("调用存储过程 用以执行视频全量临时表与中间表中数据转移时，出错！！！", e);
			return 0;
		}finally {
			if (cs != null) {
				try {
					cs.close();
				} catch (Exception e) {
					logger.error("关闭CallableStatement=失败",e);
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					logger.error("关闭Connection=失败",e);
				}
			}
		}
	}
	
	/**
	 * 调用存储过程 用以执行统计子栏目数与栏目下节目数
	 */
	public int callUpdateCategoryNum()
	{
		CallableStatement cs = null;
		Connection conn = null;
		try
		{
			conn = DB.getInstance().getConnection();
			
			cs = conn.prepareCall("{?=call f_update_video_rnum}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.execute();
			int intValue = cs.getInt(1); // 获取函数返回结果
			return intValue;
		}
		catch (Exception e)
		{
			logger.error("调用存储过程 用以执行统计子栏目数与栏目下节目数时，出错！！！", e);
			return 0;
		}finally {
			if (cs != null) {
				try {
					cs.close();
				} catch (Exception e) {
					logger.error("关闭CallableStatement=失败",e);
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					logger.error("关闭Connection=失败",e);
				}
			}
		}
	}
	
	public void updateCollectflag() {
		if (logger.isDebugEnabled())
		{
			logger.debug("更新栏目表和内容集节点表的内容集标识字段设定为1,开始");
		}
		//update t_vo_node n set n.collectflag = 1 
		//where ( n.collectflag is null or n.collectflag <> 1 ) and 
		//exists (select 1 from t_vo_collect c where c.collectid = n.nodeid)
		String sqlNodeCode = "baseVideoNew.dao.BaseVideoNewFileDAO.updateNodeCollectflag.update";
		String sqlCollectCode = "baseVideoNew.dao.BaseVideoNewFileDAO.updateCollectNodeCollectflag.update";
		try
		{
			DB.getInstance().executeMutiBySQLCodeFaild(new String[]{sqlNodeCode,sqlCollectCode}, null);
		}
		catch (DAOException e)
		{
			logger.error("更新栏目表和内容集节点表的内容集标识字段失败:", e);
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("更新栏目表和内容集节点表的内容集标识字段设定为1,结束");
		}
	}
	
	public int updateNodeCollectflag() {
		if (logger.isDebugEnabled())
		{
			logger.debug("更新栏目表的内容集标识字段设定为1,开始");
		}
		//update t_vo_node n set n.collectflag = 1 
		//where ( n.collectflag is null or n.collectflag <> 1 ) and 
		//exists (select 1 from t_vo_collect c where c.collectid = n.nodeid)
		String sqlCode = "baseVideoNew.dao.BaseVideoNewFileDAO.updateNodeCollectflag.update";
		int number = 0 ;
		try
		{
			number = DB.getInstance().executeBySQLCode(sqlCode, null);
		}
		catch (DAOException e)
		{
			logger.error("更新栏目表的内容集标识字段失败:", e);
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("更新栏目表的内容集标识字段设定为1,结束");
		}
		return number;
	}
	
	public int updateCollectNodeCollectflag(){
		
		if (logger.isDebugEnabled())
		{
			logger.debug("更新内容集节点表的内容集标识字段设定为1,开始");
		}
		//update t_vo_collect_node n set n.collectflag = 1 
		//where ( n.collectflag is null or n.collectflag <> 1 ) and  
		//exists (select 1 from t_vo_collect c where c.collectid = n.nodeid)
		String sqlCode = "baseVideoNew.dao.BaseVideoNewFileDAO.updateCollectNodeCollectflag.update";
		int number = 0 ;
		try
		{
			number = DB.getInstance().executeBySQLCode(sqlCode, null);
		}
		catch (DAOException e)
		{
			logger.error("更新内容集节点表的内容集标识字段失败:", e);
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("更新内容集节点表的内容集标识字段设定为1,结束");
		}
		return number;
	}
	
	/**
	 * 用于清空视频文件正式表融创格式视频数据
	 */
	public String deleteVideoData() throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("用于清空视频文件正式表融创格式视频数据,开始");
		}
		
		StringBuffer sf = new StringBuffer();
		
		// 删除视频正式表融创格式视频数据
		//  delete  t_vo_video t where exists (select 1 from v_vo_video_delete v where v.videoid = t.videoid );
		String sqlCode = "baseVideoNew.dao.BaseVideoNewFileDAO.deleteVideoData.delete";
		
		try
		{
			int ret1 = DB.getInstance().executeBySQLCode(sqlCode, null);
			sf.append("删除视频文件正式表融创格式视频数据" + ret1 + "条。<br>");
		}
		catch (DAOException e)
		{
			logger.error("执行清空视频文件正式表融创格式视频数据SQL发生异常，删除旧表失败", e);
			throw new BOException("执行清空视频文件正式表融创格式视频数据SQL发生异常", e);
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("用于清空视频文件正式表融创格式视频数据,结束。");
		}
		
		logger.info(sf.toString());
		return sf.toString();
	}
	
	/**
	 * 用于清空视频节目正式表融创格式视频数据
	 */
	public String deleteProgramData() throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("用于清空视频节目正式表融创格式视频数据,开始");
		}
		
		StringBuffer sf = new StringBuffer();
		
		// 删除视频节目正式表融创格式视频数据
		//  delete T_VO_PROGRAM g
		//  where not exists (select 1 from t_vo_video v where g.videoid=v.videoid);
		String sqlCode = "baseVideoNew.dao.BaseVideoNewFileDAO.deleteProgramData.delete";
		
		try
		{
			int ret1 = DB.getInstance().executeBySQLCode(sqlCode, null);
			sf.append("删除视频节目正式表融创格式视频数据" + ret1 + "条。<br>");
		}
		catch (DAOException e)
		{
			logger.error("执行清空视频节目正式表融创格式视频数据SQL发生异常，删除旧表失败", e);
			throw new BOException("执行清空视频节目正式表融创格式视频数据SQL发生异常", e);
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("用于清空视频节目正式表融创格式视频数据,结束。");
		}
		
		logger.info(sf.toString());
		return sf.toString();
	}
	
	/**
	 * 用于清空视频文件中间表融创格式视频数据
	 */
	public String deleteVideoMidData() throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("用于清空视频文件中间表融创格式视频数据,开始");
		}
		
		StringBuffer sf = new StringBuffer();
		
		// 删除视频文件中间表融创格式视频数据
		//   delete  t_vo_video_mid t where exists (select 1 from v_vo_video_delete v where v.videoid = t.videoid );
		String sqlCode = "baseVideoNew.dao.BaseVideoNewFileDAO.deleteVideoMidData.delete";
		
		try
		{
			int ret1 = DB.getInstance().executeBySQLCode(sqlCode, null);
			sf.append("删除视频文件中间表融创格式视频数据" + ret1 + "条。<br>");
		}
		catch (DAOException e)
		{
			logger.error("执行清空视频文件中间表融创格式视频数据SQL发生异常，删除旧表失败", e);
			throw new BOException("执行清空视频文件中间表融创格式视频数据SQL发生异常", e);
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("用于清空视频文件中间表融创格式视频数据,结束。");
		}
		
		logger.info(sf.toString());
		return sf.toString();
	}
	
	/**
	 * 调用存储过程 用以执行删除内容集节目为空的内容集和内容集节点
	 */
	public int deleteCollectData()
	{
		CallableStatement cs = null;
		Connection conn = null;
		try
		{
			conn = DB.getInstance().getConnection();
			
			cs = conn.prepareCall("{call p_vo_collect_delete()}");
			cs.execute();
			return 1;
		}
		catch (Exception e)
		{
			logger.error("调用存储过程 用以执行删除内容集节目为空的内容集和内容集节点时，出错！！！", e);
			return 0;
		}finally {
			if (cs != null) {
				try {
					cs.close();
				} catch (Exception e) {
					logger.error("关闭CallableStatement=失败",e);
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					logger.error("关闭Connection=失败",e);
				}
			}
		}
	}
	
}
