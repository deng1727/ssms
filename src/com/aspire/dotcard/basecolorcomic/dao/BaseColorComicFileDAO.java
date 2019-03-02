/*
 * 文件名：BaseVideoFileDAO.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */

package com.aspire.dotcard.basecolorcomic.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basecolorcomic.conf.BaseColorComicConfig;

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
public class BaseColorComicFileDAO
{
	protected static JLogger logger = LoggerFactory
			.getLogger(BaseColorComicFileDAO.class);
	
	private static BaseColorComicFileDAO dao = new BaseColorComicFileDAO();
	
	private BaseColorComicFileDAO()
	{}
	
	public static BaseColorComicFileDAO getInstance()
	{
		return dao;
	}
	
	public Map<String, String> getKeyIDMap(String type)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("得到指定数据ID列表,开始！当前类型为:" + type);
		}
		Map<String, String> idMap = new HashMap<String, String>();
		ResultSet rs = null;
		String sql = null;
		
        // 类型导入对象
        if (type.equals(BaseColorComicConfig.FILE_TYPE_CATEGORY))
        {
        	// select t.categoryid from t_cm_category t
        	sql = "baseColorComic.exportfile.BaseColorComicFileDAO.getTypeMap";
        }
        // 内容导入对象
        if (type.equals(BaseColorComicConfig.FILE_TYPE_CONTENT))
        {
        	// select t.id from t_cm_content t
        	sql = "baseColorComic.exportfile.BaseColorComicFileDAO.getContentMap";
        }
        // 商品导入对象
        else if (type.equals(BaseColorComicConfig.FILE_TYPE_REFERENCE))
        {
        	// select t.id from t_cm_reference t
        	sql = "baseColorComic.exportfile.BaseColorComicFileDAO.getRefMap";
        }
        // 推荐导入对象
        else if (type.equals(BaseColorComicConfig.FILE_TYPE_RECOMMEND))
        {
        	// select t.id from t_cm_recommend t
        	sql = "baseColorComic.exportfile.BaseColorComicFileDAO.getRecommendMap";
        }
        // 推荐关联关系导入对象
        else if (type.equals(BaseColorComicConfig.FILE_TYPE_RECOMMEND_LINK))
        {
        	// select t.id from t_cm_recommend_link t
        	sql = "baseColorComic.exportfile.BaseColorComicFileDAO.getLinkMap";
        }
		
		try
		{
			rs = DB.getInstance().queryBySQLCode(sql, null);
			
			while (rs.next())
			{
				idMap.put(rs.getString(1), "");
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
			logger.debug("得到数据ID列表,结束");
		}
		return idMap;
	}
	
    public int callUpdateCategoryNum()
	{
		CallableStatement cs = null;
		
		try
		{
			Connection conn = DB.getInstance().getConnection();
			
			cs = conn.prepareCall("{?=call f_update_cm_cate_rnum}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.execute();
			int intValue = cs.getInt(1); //获取函数返回结果
			return intValue;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return 0;
		}
	}
}
