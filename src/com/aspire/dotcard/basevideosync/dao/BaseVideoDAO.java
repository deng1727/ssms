package com.aspire.dotcard.basevideosync.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.util.PublicUtil;

public class BaseVideoDAO {

	protected static JLogger logger = LoggerFactory
			.getLogger(BaseVideoDAO.class);

	private static BaseVideoDAO dao = new BaseVideoDAO();

	private BaseVideoDAO() {
	}

	public static BaseVideoDAO getInstance() {
		return dao;
	}
	
	/**
	 * 得到视频节目概览表节目ID列表
	 * 
	 * @return
	 */
	public Map<String, String> getsProgramIDMap()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("得到视频节目概览表节目ID列表,开始！");
		}
		Map<String, String> programIDMap = new HashMap<String, String>();
		ResultSet rs = null;
		
		try
		{
			// select t.programid from t_v_sprogram t
			rs = DB.getInstance().queryBySQLCode(
					"com.aspire.dotcard.basevideosync.dao.BaseVideoDAO.getsProgramIDMap",
					null);
			while (rs.next())
			{
				String keyid = rs.getString("programid");
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
			logger.debug("得到视频节目概览表节目ID列表,结束");
		}
		return programIDMap;
	}
	
	/**
	 * 得到热点主题列表热点主题ID列表
	 * 
	 * @return
	 */
	public Map<String, String> getTitleIDMap()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("得到热点主题列表热点主题ID列表,开始！");
		}
		Map<String, String> titleIDMap = new HashMap<String, String>();
		ResultSet rs = null;
		
		try
		{
			// select h.titleid from t_v_hotcontent h
			rs = DB.getInstance().queryBySQLCode(
					"com.aspire.dotcard.basevideosync.dao.BaseVideoDAO.getTitleIDMap",
					null);
			while (rs.next())
			{
				String keyid = rs.getString("titleid");
				titleIDMap.put(keyid, "");
			}
		}
		catch (SQLException e)
		{
			logger.error("得到热点主题列表热点主题ID列表执行数据库SQL执行异常，查询失败", e);
		}
		catch (DAOException ex)
		{
			logger.error("得到热点主题列表热点主题ID列表执行数据库操作异常，查询失败", ex);
		}
		finally
		{
			DB.close(rs);
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("得到热点主题列表热点主题ID列表,结束");
		}
		return titleIDMap;
	}
	
	/**
	 * 得到视频业务产品ID列表
	 * 
	 * @return
	 */
	public Map<String, String> getServiceIDMap()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("得到视频业务产品ID列表,开始！");
		}
		Map<String, String> serviceIDMap = new HashMap<String, String>();
		ResultSet rs = null;
		
		try
		{
			// select t.Servid from t_v_propkg t
			rs = DB.getInstance().queryBySQLCode(
					"com.aspire.dotcard.basevideosync.dao.BaseVideoDAO.getServiceIDMap",
					null);
			while (rs.next())
			{
				String keyid = rs.getString("Servid");
				serviceIDMap.put(keyid, "");
			}
		}
		catch (SQLException e)
		{
			logger.error("得到视频业务产品ID列表执行数据库SQL执行异常，查询失败", e);
		}
		catch (DAOException ex)
		{
			logger.error("得到视频业务产品ID列表执行数据库操作异常，查询失败", ex);
		}
		finally
		{
			DB.close(rs);
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("得到视频业务产品ID列表,结束");
		}
		return serviceIDMap;
	}
	
	/**
	 * 得到视频计费数据计费编码列表
	 * 
	 * @return
	 */
	public Map<String,String> getFEEcodeMap(){
		if (logger.isDebugEnabled())
		{
			logger.debug("得到视频计费数据计费编码列表,开始！");
		}
		Map<String, String> FEEcodeMap = new HashMap<String, String>();
		ResultSet rs = null;
		
		try
		{
			// select p.feecode from t_v_product p
			rs = DB.getInstance().queryBySQLCode(
					"com.aspire.dotcard.basevideosync.dao.BaseVideoDAO.getFEEcodeMap",
					null);
			while (rs.next())
			{
				String keyid = rs.getString("feecode");
				FEEcodeMap.put(keyid, "");
			}
		}
		catch (SQLException e)
		{
			logger.error("得到视频计费数据计费编码列表执行数据库SQL执行异常，查询失败", e);
		}
		catch (DAOException ex)
		{
			logger.error("得到视频计费数据计费编码列表执行数据库操作异常，查询失败", ex);
		}
		finally
		{
			DB.close(rs);
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("得到视频计费数据计费编码列表,结束");
		}
		return FEEcodeMap;
	}
	
	/**
	 * 得到视频产品包促销计费ID列表
	 * 
	 * @return
	 */
	public Map<String,String> getPkgSalesIDMap(){
		if (logger.isDebugEnabled())
		{
			logger.debug("得到视频产品包促销计费ID列表,开始！");
		}
		Map<String, String> PkgSalesIDMap = new HashMap<String, String>();
		ResultSet rs = null;
		
		try
		{
			// select p.prdpack_id,p.salesproductid from t_v_pkgsales p
			rs = DB.getInstance().queryBySQLCode(
					"com.aspire.dotcard.basevideosync.dao.BaseVideoDAO.getPkgSalesIDMap",
					null);
			while (rs.next())
			{
				String keyid = rs.getString("prdpack_id")+"|"+rs.getString("salesproductid");
				PkgSalesIDMap.put(keyid, "");
			}
		}
		catch (SQLException e)
		{
			logger.error("得到视频产品包促销计费ID列表执行数据库SQL执行异常，查询失败", e);
		}
		catch (DAOException ex)
		{
			logger.error("得到视频产品包促销计费ID列表执行数据库操作异常，查询失败", ex);
		}
		finally
		{
			DB.close(rs);
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("得到视频产品包促销计费ID列表,结束");
		}
		return PkgSalesIDMap;
	}
	
	/**
	 * 用于删除指定的数据
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
			logger.error("删除表数据失败:", e);
			throw new BOException("删除表数据:" + PublicUtil.GetCallStack(e)
					+ "<br>");
		}
		
		return ret;
	}
	
	/**
	 * 删除当前同步表数据
	 * 
	 * @param tableName
	 * @throws BOException
	 */
	public void truncateTable(String[] tableName)
			throws BOException
	{
		
		if (logger.isDebugEnabled())
		{
			logger.debug("truncateTable 删除当前同步表数据,开始");
		}
		
		if(null == tableName)
			return;
		
		try
		{
			String[] truncateSqls = new String[tableName.length];
			String truncateSql = " truncate table ";
			for(int i=0; i < tableName.length ; i++){
				truncateSqls[i] = truncateSql + tableName[i];
			}
			DB.getInstance().executeMutiFaild(truncateSqls, null);
		}
		catch (DAOException e)
		{
			logger.error("truncateTable,删除当前同步表数据失败:", e);
			throw new BOException("删除当前同步表数据:" + PublicUtil.GetCallStack(e)
					+ "<br>");
		}
	}
	
	/**
	 * 用于保存增量执行时间
	 * 
	 * @param date
	 * @return
	 * @throws BOException
	 */
	public void saveLastTime(String date) throws BOException
	{
		try
		{
			//insert into t_v_lasttime(id,lupdate) values(SEQ_T_V_LASTTIME_ID.nextval,to_date(?,'yyyy-MM-dd hh24:mi:ss'))
			DB.getInstance().executeBySQLCode(
					"com.aspire.dotcard.basevideosync.dao.BaseVideoDAO.saveLastTime.insert", new Object[]{date});
		}
		catch (DAOException e)
		{
			logger.error("用于保存增量执行时间失败:", e);
			throw new BOException("用于保存增量执行时间:" + PublicUtil.GetCallStack(e)
					+ "<br>");
		}
	}
	
	/**
	 * 用于获取增量最后一次执行时间
	 * 
	 * return 
	 */
	public String getLastUpdateTime(){
		
		if (logger.isDebugEnabled())
		{
			logger.debug("用于获取增量最后一次执行时间,开始！");
		}
		String lastUpTime = null;
		ResultSet rs = null;
		
		try
		{
			// select to_char(max(l.lupdate-1/24),'yyyy-MM-dd HH24:mi:ss') lupdate from t_v_lasttime l
			rs = DB.getInstance().queryBySQLCode(
					"com.aspire.dotcard.basevideosync.dao.BaseVideoDAO.getLastUpdateTime",
					null);
			if (rs.next())
			{
				lastUpTime =rs.getString("lupdate");
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
			logger.debug("用于获取增量最后一次执行时间,结束");
		}
		return lastUpTime;
	}
	
	/**
	 * 得到视频节目概览表中已发布且未处理的节目列表
	 * 
	 * @return
	 */
	public List<String> getProgramIDList()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("得到视频节目概览表中已发布且未处理的节目列表,开始！");
		}
		List<String> programIDList = new ArrayList<String>();
		ResultSet rs = null;
		
		try
		{
			// select p.programid,p.cmsid from t_v_sprogram p where p.status ='12' and p.exestatus = '0'
			rs = DB.getInstance().queryBySQLCode(
					"com.aspire.dotcard.basevideosync.dao.BaseVideoDAO.getProgramIDList",
					null);
			while (rs.next())
			{
				String keyid = rs.getString("programid") + "|" + rs.getString("cmsid");
				programIDList.add(keyid);
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
			logger.debug("得到视频节目概览表中已发布且未处理的节目列表,结束");
		}
		return programIDList;
	}
	
	/**
	 * 得到视频节目详情表节目ID列表
	 * 
	 * @return
	 */
	public Map<String, String> getdProgramIDMap()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("得到视频节目详情表节目ID列表,开始！");
		}
		Map<String, String> programIDMap = new HashMap<String, String>();
		ResultSet rs = null;
		
		try
		{
			// select p.programid from t_v_dprogram p 
			rs = DB.getInstance().queryBySQLCode(
					"com.aspire.dotcard.basevideosync.dao.BaseVideoDAO.getdProgramIDMap",
					null);
			while (rs.next())
			{
				String keyid = rs.getString("programid");
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
			logger.debug("得到视频节目详情表节目ID列表,结束");
		}
		return programIDMap;
	}
	
	/**
	 * 标签组ID与标签组名称key-value列表
	 * 
	 * @return
	 */
	public Map<String, String> getTagGroupIDMap()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("标签组ID与标签组名称key-value列表,开始！");
		}
		Map<String, String> tagGroupIDMap = new HashMap<String, String>();
		ResultSet rs = null;
		
		try
		{
			// select t.taggruid,t.taggroup from t_v_type t 
			rs = DB.getInstance().queryBySQLCode(
					"com.aspire.dotcard.basevideosync.dao.BaseVideoDAO.getTagGroupIDMap",
					null);
			while (rs.next())
			{
				tagGroupIDMap.put(rs.getString("taggruid"), rs.getString("taggroup"));
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
			logger.debug("标签组ID与标签组名称key-value列表,结束");
		}
		return tagGroupIDMap;
	}
	
	/**
	 * 热点内容的位置Id列表
	 * 
	 * @return
	 */
	public Map<String, String> getHotcontentIDMap()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("热点内容的位置Id列表,开始！");
		}
		Map<String, String> titleCategoryIDMap = new HashMap<String, String>();
		ResultSet rs = null;
		
		try
		{
			// select locationId from t_v_hotcontent_location
			rs = DB.getInstance().queryBySQLCode(
					"com.aspire.dotcard.basevideosync.dao.BaseVideoDAO.getHotcontentIDMap",
					null);
			while (rs.next())
			{
				titleCategoryIDMap.put(rs.getString("locationId"), "");
			}
		}
		catch (SQLException e)
		{
			logger.error("热点内容的位置Id列表执行数据库SQL执行异常，查询失败", e);
		}
		catch (DAOException ex)
		{
			logger.error("热点内容的位置Id列表执行数据库操作异常，查询失败", ex);
		}
		finally
		{
			DB.close(rs);
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("热点内容的位置Id列表,结束");
		}
		return titleCategoryIDMap;
	}
	
	/**
	 * 调用存储过程 用以执行中间表与正式表中数据转移
	 */
	public int callSyncMidTableData()
	{
		CallableStatement cs = null;
		
		try
		{
			Connection conn = DB.getInstance().getConnection();
			
			cs = conn.prepareCall("{call p_v_pdetail_delete_insert()}");
			cs.execute();
			return 1;
		}
		catch (Exception e)
		{
			logger.error("调用存储过程 用以执行中间表与正式表中数据转移时，出错！！！", e);
			return 0;
		}
	}
	
	/**
	 * 调用存储过程  用以执行节目商品上架更新操作
	 */
	public int callUpdateCategoryReference()
	{
		CallableStatement cs = null;
		
		try
		{
			Connection conn = DB.getInstance().getConnection();
			
			cs = conn.prepareCall("{call p_v_program_update()}");
			cs.execute();
			return 1;
		}
		catch (Exception e)
		{
			logger.error("调用存储过程 用以执行节目商品上架更新操作时，出错！！！", e);
			return 0;
		}
	}
	
	/**
	 * 调用存储过程  用以执行热点主题货架更新操作
	 */
	public int callUpdateHotcontentCategoryMap()
	{
		CallableStatement cs = null;
		
		try
		{
			Connection conn = DB.getInstance().getConnection();
			
			cs = conn.prepareCall("{call p_v_hotcatemap_update()}");
			cs.execute();
			return 1;
		}
		catch (Exception e)
		{
			logger.error("调用存储过程 用以执行热点主题货架更新操作时，出错！！！", e);
			return 0;
		}
	}
	
	/**
	 * 获取节目ID对应内容ID和节目名称的key-value列表
	 * 
	 * @return
	 */
	public Map<String, String[]> getProgramIDAndNameMap()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("获取节目ID对应内容ID和节目名称的key-value列表,开始！");
		}
		Map<String, String[]> programIDAndNameMap = new HashMap<String, String[]>();
		ResultSet rs = null;
		
		try
		{
			// select p.programid,p.cmsid,p.name from t_v_dprogram p 
			rs = DB.getInstance().queryBySQLCode(
					"com.aspire.dotcard.basevideosync.dao.BaseVideoDAO.getProgramIDAndNameMap",
					null);
			while (rs.next())
			{
				programIDAndNameMap.put(rs.getString("programid"), new String[]{rs.getString("cmsid"),rs.getString("name")});
			}
		}
		catch (SQLException e)
		{
			logger.error("获取节目ID对应内容ID和节目名称的key-value列表执行数据库SQL执行异常，查询失败", e);
		}
		catch (DAOException ex)
		{
			logger.error("获取节目ID对应内容ID和节目名称的key-value列表执行数据库操作异常，查询失败", ex);
		}
		finally
		{
			DB.close(rs);
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("获取节目ID对应内容ID和节目名称的key-value列表,结束");
		}
		return programIDAndNameMap;
	}
	
	/**
	 * 根据节目ID更新节目概览表的处理状态为已处理
	 * 
	 * @return
	 */
	public void updateSProgramExestatus(String programId,String cmsID,String stauts) {
		if (logger.isDebugEnabled())
		{
			logger.debug("根据节目ID更新节目概览表的处理状态为已处理,开始");
		}
		//update t_v_sprogram p set p.exestatus = ?  where p.programid = ? and p.cmsid = ?
		String sqlCode = "com.aspire.dotcard.basevideosync.dao.BaseVideoDAO.updateSProgramExestatus.update";
		try
		{
			DB.getInstance().executeBySQLCode(sqlCode, new String[]{stauts,programId,cmsID});
		}
		catch (DAOException e)
		{
			logger.error("根据节目ID更新节目概览表的处理状态为已处理失败:", e);
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("根据节目ID更新节目概览表的处理状态为已处理,结束");
		}
	}
	
	/**
	 * 得到视频直播节目ID列表
	 * 
	 * @return
	 */
	public List<String> getLiveIDList()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("得到视频直播节目ID列表,开始！");
		}
		List<String> liveIDList = new ArrayList<String>();
		ResultSet rs = null;
		
		try
		{
			// select p.programid,p.cmsid from t_v_dprogram p where p.LIVESTATUS='0' and p.TYPE in (12,9,8)
			rs = DB.getInstance().queryBySQLCode(
					"com.aspire.dotcard.basevideosync.dao.BaseVideoDAO.getLiveIDList",
					null);
			while (rs.next())
			{
				String keyid = rs.getString("programid")+"|"+rs.getString("cmsid");
				liveIDList.add(keyid);
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
			logger.debug("得到视频直播节目ID列表,结束");
		}
		return liveIDList;
	}
	
	/**
	 * 得到热点主题ID列表
	 * 
	 * @return
	 */
	public List<String> getHotcontentIDList()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("得到热点主题ID列表,开始！");
		}
		List<String> hotcontentList = new ArrayList<String>();
		ResultSet rs = null;
		
		try
		{
			// select locationId from t_v_hotcontent_location
			rs = DB.getInstance().queryBySQLCode(
					"com.aspire.dotcard.basevideosync.dao.BaseVideoDAO.getHotcontentIDList",
					null);
			while (rs.next())
			{
				String keyid = rs.getString("locationId");
				hotcontentList.add(keyid);
			}
		}
		catch (SQLException e)
		{
			logger.error("得到热点主题ID列表执行数据库SQL执行异常，查询失败", e);
		}
		catch (DAOException ex)
		{
			logger.error("得到热点主题ID列表执行数据库操作异常，查询失败", ex);
		}
		finally
		{
			DB.close(rs);
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("得到热点主题ID列表,结束");
		}
		return hotcontentList;
	}
	
	
	/**
	 * 得到视频榜单表榜单名称和节目id列表
	 * 
	 * @return
	 */
	public Map<String, String> getBankNameAndProgramIDMap()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("得到视频榜单表榜单名称和节目id列表,开始！");
		}
		Map<String, String> bankNameAndProgramIDMap = new HashMap<String, String>();
		ResultSet rs = null;
		
		try
		{
			// select p.platform,p.programid from T_V_LIST_PUBLISH p
			rs = DB.getInstance().queryBySQLCode(
					"com.aspire.dotcard.basevideosync.dao.BaseVideoDAO.getBankNameAndProgramIDMap",
					null);
			while (rs.next())
			{
				bankNameAndProgramIDMap.put(rs.getString("programid")+"|"+rs.getString("platform"), "");
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
			logger.debug("得到视频榜单表榜单名称和节目id列表,结束");
		}
		return bankNameAndProgramIDMap;
	}
	
	
	/**
	 * 得到api请求参数
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getApiRequestParamter(String str)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("得到api请求参数,开始！");
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		ResultSet rs = null;
		
		try
		{
			// select content,contentType from t_v_apiRequestParamter where content=?
			rs = DB.getInstance().queryBySQLCode(
					"com.aspire.dotcard.basevideosync.dao.BaseVideoDAO.getApiRequestParamter",
					new Object[]{str});
			while (rs.next())
			{
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("content", rs.getString("content"));
				map.put("contentType", rs.getString("contentType"));
				list.add(map);
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
			logger.debug("得到api请求参数,结束");
		}
		return list;
	}
}
