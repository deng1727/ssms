package com.aspire.dotcard.appmonitor.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.DataAccessException;
import com.aspire.dotcard.appinfosyn.AppInfoVO;
import com.aspire.dotcard.appmonitor.vo.ContentVO;
import com.aspire.dotcard.appmonitor.vo.MonitorContentVO;

public class AppMonitorDAO {

	/**
	 * 日志引用
	 */
	JLogger logger = LoggerFactory.getLogger(AppMonitorDAO.class);
	
	private static AppMonitorDAO dao = new AppMonitorDAO();
	
	private AppMonitorDAO()
	{}
	
	/**
	 * 单例模式
	 * 
	 * @return
	 */
	public static AppMonitorDAO getInstance()
	{
		return dao;
	}
	
	/**
	 * 用来得到重点应用监控内容列表
	 * 
	 * @return 内容ID列表
	 */
	public List<MonitorContentVO> getMonitorContentIDList()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("得到重点应用监控内容列表,开始");
		}
		List<MonitorContentVO> contentIdList = new ArrayList<MonitorContentVO>();
		ResultSet rs = null;
		try
		{
			// select m.type,m.appid,m.packagename,m.name from t_pivot_app_monitor m
			rs = DB.getInstance().queryBySQLCode(
					"com.aspire.dotcard.appmonitor.dao.AppMonitorDAO.getMonitorContentIDList", null);
			while (rs.next())
			{
				MonitorContentVO mc = new MonitorContentVO();
				mc.setType(rs.getString("type"));
				mc.setAppid(rs.getString("appid"));   //表结构里对应的contentid
				mc.setPackagename(rs.getString("packagename"));
				mc.setName(rs.getString("name"));
				mc.setId(rs.getString("id"));     //表结构里的appid
				contentIdList.add(mc);
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
			logger.debug("得到MM全量内容ID列表,结束");
		}
		return contentIdList;
	}
	
	/**
	 * 用来得到重点应用监控结果内容列表
	 * 
	 * @return 结果列表
	 */
	public List<MonitorContentVO> getMonitorResultList(String type)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("得到重点应用监控内容列表,开始");
		}
		List<MonitorContentVO> contentIdList = new ArrayList<MonitorContentVO>();
		ResultSet rs = null;
		
		try
		{
			// select m.type,m.appid,m.packagename from t_pivot_content_monitor m
			rs = DB.getInstance().queryBySQLCode(
					"com.aspire.dotcard.appmonitor.dao.AppMonitorDAO.getMonitorContentIDList", null);
			while (rs.next())
			{
				MonitorContentVO mc = new MonitorContentVO();
				mc.setType(rs.getString("type"));
				mc.setAppid(rs.getString("appid"));
				mc.setPackagename(rs.getString("packagename"));
				contentIdList.add(mc);
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
			logger.debug("得到MM全量内容ID列表,结束");
		}
		return contentIdList;
	}
	
	/**
	 * 根据packagename获取汇聚应用的contentid列表，一个packagename可能有多个contentid
	 * 
	 * @return 内容ID列表
	 */
	public List<String> getContentIDByPackagename(String packagename)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("获取汇聚应用对应的contentid,开始");
		}
		List<String> contentIdList = new ArrayList<String>();
		ResultSet rs = null;
		try
		{
			//select distinct contentid from v_om_third_party_relation r where r.joincontentid =?
			rs = DB.getInstance().queryBySQLCode(
					"com.aspire.dotcard.appmonitor.dao.AppMonitorDAO.getContentIDByPackagename", new Object[]{packagename});
			while (rs.next())
			{
				contentIdList.add(rs.getString("contentid"));
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
			logger.debug("得到MM全量内容ID列表,结束");
		}
		return contentIdList;
	}
	
	/**
	 * 用来得到MM全量内容ID列表
	 * 
	 * @return 内容ID列表
	 */
	public Map<String, String> getContentIDMapByMM()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("得到MM全量内容ID列表,开始");
		}
		Map<String, String> contentIdMap = new HashMap<String, String>();
		ResultSet rs = null;
		
		try
		{
			// select distinct g.contentid from t_r_gcontent g
			rs = DB.getInstance().queryBySQLCode(
					"com.aspire.dotcard.appmonitor.dao.AppMonitorDAO.getContentIDMapByMM", null);
			while (rs.next())
			{
				contentIdMap.put(rs.getString("contentid"), "");
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
			logger.debug("得到MM全量内容ID列表,结束");
		}
		return contentIdMap;
	}
	
	/**
	 * 用来得到监控结果内容ID列表
	 * 
	 * @return 内容ID列表
	 */
	public Map<String, String[]> getResultContentIDMap(String type)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("得到监控结果内容ID列表,开始");
		}
		Map<String, String[]> contentIdMap = new HashMap<String, String[]>();
		ResultSet rs = null;
		String sqlCode = null;
		try
		{
			if("1".equals(type)){
				//select distinct r.appid contentid,r.hj_state,r.dc_state,r.ss_state from t_pivot_app_monitor_result r where r.type = '1'
				sqlCode = "com.aspire.dotcard.appmonitor.dao.AppMonitorDAO.getResultContentIDMap_MM";
			}else if("2".equals(type)){
				//select distinct r.packagename contentid,r.hj_state,r.dc_state,r.ss_state from t_pivot_app_monitor_result r where r.type = '2'
				sqlCode = "com.aspire.dotcard.appmonitor.dao.AppMonitorDAO.getResultContentIDMap_HJ";
			}
			rs = DB.getInstance().queryBySQLCode(sqlCode, null);
			while (rs.next())
			{
				contentIdMap.put(rs.getString("contentid"), new String[]{rs.getString("hj_state"),rs.getString("dc_state"),rs.getString("ss_state"),rs.getString("id")});
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
			logger.debug("得到MM监控结果内容ID列表,结束");
		}
		return contentIdMap;
	}
	
	/**
	 * 用来得到汇聚全量内容ID列表
	 * 
	 * @return 内容ID列表
	 */
	public Map<String, String> getContentIDMapByHJ()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("得到汇聚全量内容ID列表,开始");
		}
		Map<String, String> contentIdMap = new HashMap<String, String>();
		ResultSet rs = null;
		
		try
		{
			// select distinct t.joincontentid contentid from v_om_third_party_relation t
			rs = DB.getInstance().queryBySQLCode(
					"com.aspire.dotcard.appmonitor.dao.AppMonitorDAO.getContentIDMapByHJ", null);
			while (rs.next())
			{
				contentIdMap.put(rs.getString("contentid"), "");
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
			logger.debug("得到汇聚全量内容ID列表,结束");
		}
		return contentIdMap;
	}
	
	public List<String[]> getAppMonitorResult(String type){
		// 执行sql语句得到数据集。
        ResultSet rs = null;

        String sql = null;
        List<String[]> list = new ArrayList<String[]>();
        try
        {
        	//select type,appid,packagename,name,versionname,updatedate,hj_state,hj_state_updatedate,
        	//dc_state,dc_state_updatedate,ss_state,ss_state_updatedate from t_pivot_app_monitor_result where type = ?
        	sql = "com.aspire.dotcard.appmonitor.dao.AppMonitorDAO.getAppMonitorResult";
            rs = DB.getInstance().queryBySQLCode(sql, new Object[]{type});

            while (rs.next())
            {
                // 放入数据集合
                String[] va = fromObject(rs);
                list.add(va);
            }
            
        }
        catch (DAOException e)
        {
        	logger.error("数据库操作异常，查询失败", e);
        }
        catch (SQLException e)
        {
        	logger.error("数据库SQL执行异常，查询失败", e);
        }
        return list;
	}
	
	/**
	 * 添加应用监控结果
	 * 
	 */
	public void addAppMonitorResult(MonitorContentVO vo,String monitorType,String isHas) {
		if (logger.isDebugEnabled())
		{
			logger.debug("添加应用监控结果,开始");
		}
		String sqlCode = null;
		//
		if("HJ".equals(monitorType)){
			//insert into t_pivot_app_monitor_result(type,appid,packagename,name,hj_state,hj_state_updatedate) values (?,?,?,?,?,sysdate)
			sqlCode = "com.aspire.dotcard.appmonitor.dao.AppMonitorDAO.addAppMonitorResult_HJ";
		}else if("DC".equals(monitorType)){
			//insert into t_pivot_app_monitor_result(type,appid,packagename,name,dc_state,dc_state_updatedate) values (?,?,?,?,?,sysdate)
			sqlCode = "com.aspire.dotcard.appmonitor.dao.AppMonitorDAO.addAppMonitorResult_DC";
		}else if("SS".equals(monitorType)){
			//insert into t_pivot_app_monitor_result(type,appid,packagename,name,ss_state,ss_state_updatedate) values (?,?,?,?,?,sysdate)
			sqlCode = "com.aspire.dotcard.appmonitor.dao.AppMonitorDAO.addAppMonitorResult_SS";
		}
		
		try
		{
			DB.getInstance().executeBySQLCode(sqlCode, new String[]{vo.getType(),vo.getAppid(),vo.getPackagename(),vo.getName(),isHas,vo.getId()});
		}
		catch (DAOException e)
		{
			logger.error("添加应用监控结果信息处理失败:", e);
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("添加应用监控结果信息已处理,结束");
		}
	}
	
	/**
	 * 更新MM应用监控结果
	 * 
	 */
	public void updateMMAppMonitorResult(MonitorContentVO vo,String monitorType,String isHas) {
		if (logger.isDebugEnabled())
		{
			logger.debug("更新MM应用监控结果,开始");
		}
		String sqlCode = null;
		//
		if("HJ".equals(monitorType)){
			//update t_pivot_app_monitor_result set hj_state=?,hj_state_updatedate = sysdate where type =? and appid = ?
			sqlCode = "com.aspire.dotcard.appmonitor.dao.AppMonitorDAO.updateMMAppMonitorResult_HJ";
		}else if("DC".equals(monitorType)){
			//update t_pivot_app_monitor_result set dc_state=?,dc_state_updatedate = sysdate where type =? and appid = ?
			sqlCode = "com.aspire.dotcard.appmonitor.dao.AppMonitorDAO.updateMMAppMonitorResult_DC";
		}else if("SS".equals(monitorType)){
			//update t_pivot_app_monitor_result set ss_state=?,ss_state_updatedate = sysdate where type =? and appid = ?
			sqlCode = "com.aspire.dotcard.appmonitor.dao.AppMonitorDAO.updateMMAppMonitorResult_SS";
		}
		
		try
		{
			DB.getInstance().executeBySQLCode(sqlCode, new String[]{isHas,vo.getType(),vo.getAppid()});
		}
		catch (DAOException e)
		{
			logger.error("更新MM应用监控结果信息处理失败:", e);
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("更新MM应用监控结果信息已处理,结束");
		}
	}
	
	/**
	 * 更新汇聚应用监控结果
	 * 
	 */
	public void updateHJAppMonitorResult(MonitorContentVO vo,String monitorType,String isHas) {
		if (logger.isDebugEnabled())
		{
			logger.debug("更新汇聚应用监控结果,开始");
		}
		String sqlCode = null;
		//货架
		if("HJ".equals(monitorType)){
			//update t_pivot_app_monitor_result set hj_state=?,hj_state_updatedate = sysdate where type =? and packagename = ?
			sqlCode = "com.aspire.dotcard.appmonitor.dao.AppMonitorDAO.updateHJAppMonitorResult_HJ";
		}//数据中心
		else if("DC".equals(monitorType)){
			//update t_pivot_app_monitor_result set dc_state=?,dc_state_updatedate = sysdate where type =? and packagename = ?
			sqlCode = "com.aspire.dotcard.appmonitor.dao.AppMonitorDAO.updateHJAppMonitorResult_DC";
		}//搜索
		else if("SS".equals(monitorType)){
			//update t_pivot_app_monitor_result set ss_state=?,ss_state_updatedate = sysdate where type =? and packagename = ?
			sqlCode = "com.aspire.dotcard.appmonitor.dao.AppMonitorDAO.updateHJAppMonitorResult_SS";
		}
		
		try
		{
			DB.getInstance().executeBySQLCode(sqlCode, new String[]{isHas,vo.getType(),vo.getPackagename(),});
		}
		catch (DAOException e)
		{
			logger.error("更新汇聚应用监控结果信息处理失败:", e);
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("更新汇聚应用监控结果信息已处理,结束");
		}
	}
	
	/**
	 * 更新重点应用监控结果相关字段
	 * 
	 */
	public void updateMonitorResult() {
		if (logger.isDebugEnabled())
		{
			logger.debug("更新重点应用监控结果相关字段,开始");
		}
		//UPDATE t_pivot_app_monitor_result a
		//  SET (a.versionname,a.updatedate) = (select g.version,g.lupddate from t_r_gcontent g
		//		   where a.appid = g.contentid and a.type = '1' and rownum = 1)
		//		   where exists
		//		   (select 1 from t_r_gcontent c where a.appid = c.contentid and a.type = '1');
		String sqlCode_MM = "com.aspire.dotcard.appmonitor.dao.AppMonitorDAO.updateMonitorResult_MM";
		//UPDATE t_pivot_app_monitor_result a
		//   SET (a.versionname,a.updatedate) = (select r.versionname,r.updatetime from v_om_third_party_relation r
		//		   where a.packagename = r.joincontentid and a.type = '2' and rownum = 1)
		//		   where exists
		//		   (select 1 from v_om_third_party_relation c where a.packagename = c.joincontentid and a.type = '2');
		String sqlCode_HJ = "com.aspire.dotcard.appmonitor.dao.AppMonitorDAO.updateMonitorResult_HJ";
		try
		{
			DB.getInstance().executeBySQLCode(sqlCode_MM, null);
			DB.getInstance().executeBySQLCode(sqlCode_HJ, null);
		}
		catch (DAOException e)
		{
			logger.error("更新重点应用监控结果相关字段信息处理失败:", e);
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("更新重点应用监控结果相关字段信息已处理,结束");
		}
	}
	
	private String[] fromObject(ResultSet rs) throws SQLException
    {	
		int count = rs.getMetaData().getColumnCount();
        String[] obj = new String[count];
        //select type,appid,packagename,name,versionname,updatedate,hj_state,hj_state_updatedate,
    	//dc_state,dc_state_updatedate,ss_state,ss_state_updatedate  t_pivot_app_monitor_result where type = ?
        obj[0] = "1".equals(rs.getString("type"))?"MM应用":"汇聚应用";
        obj[1] = rs.getString("appid");
        obj[2] = rs.getString("packagename");
        obj[3] = rs.getString("name");
        obj[4] = rs.getString("versionname");
        obj[5] = rs.getString("updatedate");
        obj[6] = "0".equals(rs.getString("hj_state"))?"是":"否";
        obj[7] = rs.getString("hj_state_updatedate");
        obj[8] = "0".equals(rs.getString("dc_state"))?"是":"否";
        obj[9] = rs.getString("dc_state_updatedate");
        obj[10] = "0".equals(rs.getString("ss_state"))?"是":"否";
        obj[11] = rs.getString("ss_state_updatedate");
        obj[12] = rs.getString("id");
        return obj;
    }
    	
	public void updateMonitorForInit() {
		String sqlCode = "com.aspire.dotcard.appmonitor.dao.AppMonitorDAO.updateMonitorForInit";
		try {
			DB.getInstance().executeBySQLCode(sqlCode, null);
		} catch (DAOException e) {
			logger.error("updateMonitorForInit处理失败:", e);
		}
	}
}
