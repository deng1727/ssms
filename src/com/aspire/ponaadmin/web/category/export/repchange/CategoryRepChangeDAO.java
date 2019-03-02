/**
 * SSMS
 * com.aspire.ponaadmin.web.category.export.repchange CategoryRepChangeDAO.java
 * Apr 14, 2010
 * @author tungke
 * @version 1.0
 *
 */
package com.aspire.ponaadmin.web.category.export.repchange;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.category.export.CategoryExportDAO;
import com.aspire.ponaadmin.web.category.export.ExportReferenceVO;

/**
 * @author tungke
 *
 */
public class CategoryRepChangeDAO {

	
	/**
	 * 记录日志的实例对象
	 */
	protected static JLogger LOG = LoggerFactory.getLogger(CategoryRepChangeDAO.class);
	/**
	 * singleton模式的实例
	 */
	private static CategoryRepChangeDAO instance = new CategoryRepChangeDAO();

	/**
	 * 构造方法，由singleton模式调用
	 */
	private CategoryRepChangeDAO()
	{
	}

	/**
	 * 获取实例
	 * 
	 * @return 实例
	 */
	public static CategoryRepChangeDAO getInstance() throws DAOException
	{
		return instance;
	}
	
	/**
	 * 初始化統計數據
	 *
	 */
	public void initRepChangeData() throws DAOException {

		//将19个常规机型今天的数据插入历史表 
		
		String del19type = "category.export.repchange.CategoryRepChangeBO.initRepChangeData().del19type";
		
		String insert19type = "category.export.repchange.CategoryRepChangeBO.initRepChangeData().insert19type";
//		将N5802type机型今天的数据插入历史表 	
		String del5802type =  "category.export.repchange.CategoryRepChangeBO.initRepChangeData().del5802type";
	//	category.export.repchange.CategoryRepChangeBO.initRepChangeData().del5802type=delete from r_charts01 t where  t.phdate = to_char(sysdate,'yyyymmdd')
	//	category.export.repchange.CategoryRepChangeBO.initRepChangeData().n5802type=insert into  r_charts01 select t.sortid,t.refnodeid,t.categoryid,to_char(sysdate,'yyyymmdd') phdate from t_r_reference t where t.categoryid in ('100000346','100000348','100000353','100000345','100000349','100000354','100000343','100000350','100000355','100000344','100000352','100000357','100000347','100000351','100000356')

		String n5802type = "category.export.repchange.CategoryRepChangeBO.initRepChangeData().n5802type";
		//建立19个常规机型的临时表1，存储今天比昨天变化的应用数量
		
		String dropsql1 = "category.export.repchange.CategoryRepChangeBO.initRepChangeData().dropsql1";
		//--表a用作当前表，表b用作前一天的表数据，2张表的phdate可以手工设置

		String creatasql1 = "category.export.repchange.CategoryRepChangeBO.initRepChangeData().creatasql1";
		
		//建立19个常规机型的临时表2，统计今天所有榜单的应用数 前30个应用

		String dropsql2 = "category.export.repchange.CategoryRepChangeBO.initRepChangeData().dropsql2";
		
		String createsql2="category.export.repchange.CategoryRepChangeBO.initRepChangeData().createsql2";
		

		String updatesql21 = "category.export.repchange.CategoryRepChangeBO.initRepChangeData().updatesql21";

		String updatesql22 = "category.export.repchange.CategoryRepChangeBO.initRepChangeData().updatesql22";

		String updatesql23 = "category.export.repchange.CategoryRepChangeBO.initRepChangeData().updatesql23";
		
		//-建立19个常规机型的临时表1，存储今天比昨天变化的应用数量

		String dropsql3 = "category.export.repchange.CategoryRepChangeBO.initRepChangeData().dropsql3";

		String createsql3 = "category.export.repchange.CategoryRepChangeBO.initRepChangeData().createsql3";
		
		//建立19个常规机型临时表2，统计今天所有榜单的应用数 前15个应用

		String dropsql4 = "category.export.repchange.CategoryRepChangeBO.initRepChangeData().dropsql4";

		String createsql4 = "category.export.repchange.CategoryRepChangeBO.initRepChangeData().createsql4";

		String updatesql41 = "category.export.repchange.CategoryRepChangeBO.initRepChangeData().updatesql41";

		String updatesql42 = "category.export.repchange.CategoryRepChangeBO.initRepChangeData().updatesql42";

		String updatesql43 = "category.export.repchange.CategoryRepChangeBO.initRepChangeData().updatesql43";
		
		//----将5802的榜单插入历史表

		String dropsql5 = "category.export.repchange.CategoryRepChangeBO.initRepChangeData().dropsql5";

		String createsql5 = "category.export.repchange.CategoryRepChangeBO.initRepChangeData().createsql5";
		//将历史表中的分类update

		String updatesql5 = "category.export.repchange.CategoryRepChangeBO.initRepChangeData().updatesql5";
		//--将5802的的榜单序号标注

		String dropsql6 = "category.export.repchange.CategoryRepChangeBO.initRepChangeData().dropsql6";
//		category.export.repchange.CategoryRepChangeBO.initRepChangeData().createsql6=create table r_charts03 as select  b.phdate,b.name,b.categoryid,b.catename,b.catname, row_number() over (partition by b.categoryid,b.phdate order by b.sortid desc) rowlist from  r_charts02 b
		String createsql6 = "category.export.repchange.CategoryRepChangeBO.initRepChangeData().createsql6";
		try {
		    DB.getInstance().executeBySQLCode(del19type, null);         
			DB.getInstance().executeBySQLCode(insert19type, null);
			DB.getInstance().executeBySQLCode(del5802type, null); 
			DB.getInstance().executeBySQLCode(n5802type, null);   
			DB.getInstance().executeBySQLCode(dropsql1, null);    
			DB.getInstance().executeBySQLCode(creatasql1, null);  
			DB.getInstance().executeBySQLCode(dropsql2, null);    
			DB.getInstance().executeBySQLCode(createsql2, null);  
			DB.getInstance().executeBySQLCode(updatesql21, null); 
			DB.getInstance().executeBySQLCode(updatesql22, null); 
			DB.getInstance().executeBySQLCode(updatesql23, null); 
			DB.getInstance().executeBySQLCode(dropsql3, null);    
			DB.getInstance().executeBySQLCode(createsql3, null);  
			DB.getInstance().executeBySQLCode(dropsql4, null);    
			DB.getInstance().executeBySQLCode(createsql4, null);  
			                                     
			DB.getInstance().executeBySQLCode(updatesql41, null); 
			DB.getInstance().executeBySQLCode(updatesql42, null); 
			DB.getInstance().executeBySQLCode(updatesql43, null); 
			DB.getInstance().executeBySQLCode(dropsql5, null);    
			DB.getInstance().executeBySQLCode(createsql5, null);  
			                                   
			DB.getInstance().executeBySQLCode(updatesql5, null);  
			DB.getInstance().executeBySQLCode(dropsql6, null);    
			DB.getInstance().executeBySQLCode(createsql6, null);  
			
		} catch (DAOException e) {
			LOG.error("轮换率重复率数据初始化失败，执行 SQL 语句出错："+e);
			throw new DAOException("执行 SQL 语句出错：",
					e);
		}
	}
	
	

	
	/**
	 * 分机型统计榜单分类重复率
	 * @param sqlgetRepeateRate1
	 * @param parm
	 * @return
	 * @throws DAOException
	 */
	public List getRepeateRate1(String sqlgetRepeateRate1,Object [] parm) throws DAOException 
	{
		if (LOG.isDebugEnabled()) {
			LOG.debug("分机型统计榜单分类重复率");
		}
		// String sqlgetRepeateRate1 = "";
		// Object [] parm = {};
		ResultSet rs = this.querySql(sqlgetRepeateRate1, parm);

		List exportRepeateRateList = new ArrayList();
		try {
			while (rs.next()) {
				ExportRepeateRate1 vo = new ExportRepeateRate1();
				vo.setDeviceName(rs.getString("devicename"));
				vo.setAppType(rs.getString("apptype"));
				vo.setCaAllCount(new Integer(rs.getInt("allcount")));
				vo
						.setCanoRepeateCount(new Integer(rs
								.getInt("norepeatecount")));
				vo.setCanorepeateRate(rs.getString("norepeaterate"));
				exportRepeateRateList.add(vo);
			}
		} catch (SQLException e) {
			LOG.error("分机型提取榜单变更率发生异常:"+e);
			throw new DAOException("分机型提取榜单变更率发生异常:", e);
		} finally {
			DB.close(rs);
		}
		return exportRepeateRateList;
	}
	
	
	/**
	 * 清理历史数据
	 *
	 */
	public void clearHisData(){
		
		String clearaSQLCode = "category.export.repchange.CategoryRepChangeBO.clearHisData().delete1";
		String clearbSQLCode = "category.export.repchange.CategoryRepChangeBO.clearHisData().delete2";
		 try
		{
			DB.getInstance().executeBySQLCode(clearaSQLCode, null);
			 DB.getInstance().executeBySQLCode(clearbSQLCode, null); 
			 LOG.debug("轮换率重复率清理历史数据完成");
		} catch (DAOException e)
		{
			// TODO Auto-generated catch block
			LOG.error("轮换率重复率清理历史数据发生异常:"+e);
			e.printStackTrace();
		}    
	 
		
	}
	
	
	/**
	 * 分机型提取榜单变更率
	 * @param sqlgetRepChangeRate1
	 * @param parm
	 * @return
	 * @throws DAOException
	 */
	public List getRepChangeRate1(String sqlgetRepChangeRate1,Object [] parm) throws DAOException 
	{
		if(LOG.isDebugEnabled()){
			LOG.debug("获取分机型提取榜单变更率");			
		}
		//String sqlgetRepChangeRate1 = "";
		//Object [] parm = {};
		ResultSet rs = this.querySql(sqlgetRepChangeRate1,parm);
		
		List exportRechangeRateList = new ArrayList();
		try
		{
			while(rs.next()){
				ExportRechangeRate1 vo = new ExportRechangeRate1();
				vo.setCaName(rs.getString("caname"));
				vo.setDeviceName(rs.getString("devicename"));
				vo.setType(rs.getString("type"));
				vo.setAllCount(new Integer(rs.getInt("allcount")));
				vo.setCount(new Integer(rs.getInt("count")));
				vo.setDifCount(new Integer(rs.getInt("difcount")));
				vo.setChangeRate(rs.getString("changerate"));
				exportRechangeRateList.add(vo);					
		} 
		} catch (SQLException e)
		{ LOG.error("分机型提取榜单变更率发生异常:"+e);
			throw new DAOException("分机型提取榜单变更率发生异常:", e);
		} finally
		{
			DB.close(rs);
		}			
		return exportRechangeRateList;
	}
	
	
	/**
	 * 获取榜单变更率
	 * @param sqlgetCaRepChangeRate1
	 * @param parm
	 * @return
	 * @throws DAOException
	 */
	public List getCaRepChangeRate1(String sqlgetCaRepChangeRate1,Object [] parm) throws DAOException 
	{
		if(LOG.isDebugEnabled()){
			LOG.debug("获取榜单变更率");			
		}
		//String sqlgetCaRepChangeRate1 = "";
		//Object [] parm = {};
		ResultSet rs = this.querySql(sqlgetCaRepChangeRate1,parm);
		
		List exportCaRepRateList = new ArrayList();
		try
		{
			//rs = DB.getInstance().queryBySQLCode("category.export.CategoryExportDAO.select().GETREFRENCEBYCATEGORYID",parm);
			while(rs.next()){
				ExportCaRepChangeRate1 vo = new ExportCaRepChangeRate1();
				vo.setCateName(rs.getString("catename"));
				vo.setChangeRate(rs.getString("changerate"));
				exportCaRepRateList.add(vo);					
		} 
		} catch (SQLException e)
		{
			throw new DAOException("提取榜单变更率发生异常:", e);
		} finally
		{
			DB.close(rs);
		}			
		return exportCaRepRateList;
	}
	
	
	/**
	 * 执行数据库查询
	 * @param sql
	 * @param parm
	 * @return
	 * @throws DAOException
	 */
	private ResultSet querySql(String sql,Object [] parm) throws DAOException{	
		ResultSet rs = null;
		rs = DB.getInstance().queryBySQLCode(sql,parm);
		return rs;
	}
}
