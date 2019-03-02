/**
 * SSMS
 * com.aspire.ponaadmin.web.repository.camonitor CategoryMonitorDAO.java
 * Apr 20, 2010
 * @author tungke
 * @version 1.0
 *
 */
package com.aspire.ponaadmin.web.repository.camonitor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * @author tungke
 *
 */
public class CategoryMonitorDAO
{

	private static CategoryMonitorDAO instance = new CategoryMonitorDAO();

	private CategoryMonitorDAO()
	{

	}

	public static CategoryMonitorDAO getInstance()
	{
		return instance;
	}

	/**
	 * 日志引用
	 */
	private static final JLogger logger = LoggerFactory
			.getLogger(CategoryMonitorDAO.class);

	/**
	 * 通过分类id去查找对应的货架编码
	 * @return categoryID 
	 * @throws DAOException
	 */
	public List getCategoryMonitor() throws DAOException
	{
		List cmList = new ArrayList();
		if (logger.isDebugEnabled())
		{
			logger.debug("getCategoryMonitor is beginning ....");
		}
		Object[] paras = {};
		String sqlCode = "CategoryMonitorDAO.getCategoryMonitor().SELECT";

		ResultSet rs = null;
		try
		{
			rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
			cmList = this.getCategoryMonitorVOByRS(rs);
		} catch (Exception e)
		{
			throw new DAOException(e);
		} finally
		{
			DB.close(rs);
		}
		return cmList;
	}

	/**
	 * 通过分类id去查找对应的货架编码
	 * @return categoryID 
	 * @throws DAOException
	 */
	public HashMap getAllDeviceMonitor() throws DAOException
	{
		HashMap dbn = null;
		if (logger.isDebugEnabled())
		{
			logger.debug("getAllDeviceMonitor is beginning ....");
		}
		Object[] paras = {};
		String sqlCode = "CategoryMonitorDAO.getAllDeviceMonitor().SELECT";

		ResultSet rs = null;
		try
		{
			rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
			dbn = this.getAllDeviceMonitorVOByRS(rs);
		} catch (Exception e)
		{
			throw new DAOException(e);
		} finally
		{
			DB.close(rs);
		}
		return dbn;
	}
	
	/**
	 * 获取结果封装
	 * @param rs
	 * @return
	 * @throws SQLException
	 * @throws DAOException 
	 */
	public HashMap getAllDeviceMonitorVOByRS(ResultSet rs) throws DAOException
	{
		HashMap dbn = new HashMap();
		try
		{
			while (rs.next())
			{

				String deviceBland = rs.getString("brand_name");
				String deviceName = rs.getString("device_name");
				String deviceDesc = rs.getString("device_desc");
				List devicelist = (List) dbn.get(deviceBland);
				if (devicelist == null)
				{
					List devicel = new ArrayList();
					//HashMap hmdevice = new HashMap();	
					//hmdevice.put(deviceDesc,deviceDesc);
					String [] devices = {deviceName,deviceDesc};
					devicel.add(devices);
					dbn.put(deviceBland, devicel);
				}
				else
				{
					//HashMap hmdevice = new HashMap();
					//hmdevice.put(deviceDesc,deviceDesc);
					String [] devices = {deviceName,deviceDesc};
					devicelist.add(devices);
					dbn.put(deviceBland, devicelist);
				}
			}
		} catch (Exception e)
		{
			throw new DAOException(e);
		} finally
		{
			DB.close(rs);
		}
		return dbn;
	}
	/**
	 * 获取结果封装
	 * @param rs
	 * @return
	 * @throws SQLException
	 * @throws DAOException 
	 */
	public List getCategoryMonitorVOByRS(ResultSet rs) throws DAOException
	{
		List cmList = new ArrayList();
		try
		{
			while (rs.next())
			{
				CategoryMonitor cm = new CategoryMonitor();
				cm.setId(rs.getString("id"));
				cm.setCategoryid(rs.getString("categoryid"));
				cm.setParentcategoryid(rs.getString("parentcategoryid"));
				cm.setName(rs.getString("name"));
				cm.setCategorytype(rs.getString("categorytype"));
				cm.setFullName(rs.getString("fullname"));
				if (cm.getCategorytype() != null && cm.getCategorytype().equals("1"))
				{
					// 只查找一级子货架
					String sqlCode = "CategoryMonitorDAO.getCategoryMonitorVOByRS1().SELECT";
					Object[] paras = { cm.getFullName()+">",cm.getCategoryid() };
					List sub1 = this.getSubCategoryMonitor(sqlCode, paras);
					cmList.addAll(sub1);
					cmList.add(cm);
				}
				else if (cm.getCategorytype() != null && cm.getCategorytype().equals("9"))
				{
					if(cm.getFullName() != null && cm.getFullName().indexOf(">")>=0){
						String fullnametemp = cm.getFullName().substring(0,cm.getFullName().lastIndexOf(">"));
						// 查找所有子货架
						String sqlCode = "CategoryMonitorDAO.getCategoryMonitorVOByRS9().SELECT";
						Object[] paras = {  fullnametemp,cm.getCategoryid() };
						List sub2 = this.getSubCategoryMonitor(sqlCode, paras);
						cmList.addAll(sub2);
					}else{
						logger.error("获取货架全名称失败");
					}
					
				}
				else
				{
					// 只添加本身不处理
					cmList.add(cm);
				}

			}
		} catch (Exception e)
		{
			throw new DAOException(e);
		} finally
		{
			DB.close(rs);
		}
		return cmList;
	}

	/**
	 * 基础查询
	 * 
	 * @return categoryID
	 * @throws DAOException
	 */
	public List getSubCategoryMonitor(String sqlCode, Object[] paras) throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("getSubCategoryMonitor(" + sqlCode + ") is beginning ....");
		}
		List result = new ArrayList();
		//Object[] paras = {categoryid};
		// String sqlCode = "CategoryDAO.getCategoryIDByID.SELECT";
		ResultSet rs = null;
		try
		{
			rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
			result = this.getCategoryMonitorVOByRS(rs);
			//            if(rs.next())
			//            {
			//                result = rs.getString("categoryID");
			//            }
		} catch (Exception e)
		{
			throw new DAOException(e);
		} finally
		{
			DB.close(rs);
		}
		return result;
	}

}
