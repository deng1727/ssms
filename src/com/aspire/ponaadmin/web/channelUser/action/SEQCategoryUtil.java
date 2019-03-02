package com.aspire.ponaadmin.web.channelUser.action;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class SEQCategoryUtil
{
	/**
	 * 记录日志的实例对象
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(SEQCategoryUtil.class);
	
	/**
	 * singleton模式的实例
	 */
	private static SEQCategoryUtil instance = new SEQCategoryUtil();
	
	/**
	 * 构造方法，由singleton模式调用
	 */
	private SEQCategoryUtil()
	{}
	
	/**
	 * 获取实例
	 * 
	 * @return 实例
	 */
	public static SEQCategoryUtil getInstance()
	{
		return instance;
	}
	
	/**
	 * 根据不同的货架类型，返回不同的sqe
	 * 
	 * @param categoryType
	 * @return
	 * @throws DAOException
	 */
	public int getSEQByCategoryType(int categoryType)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("getSEQByCategoryType(" + categoryType + ") start");
		}
		
		int seqId = 0;
		
		try
		{
			switch (categoryType)
			{
				// MM货架
				case 1:
					seqId = DB.getSeqValue("SEQ_category_sort_id");
					break;
				// 基地阅读货架
				case 2:
					seqId = DB.getSeqValue("SEQ_category_r_sort_id");
					break;
				// 基地视频货架
				case 3:
					seqId = DB.getSeqValue("SEQ_category_v_sort_id");
					break;
				// 基地音乐货架
				case 4:
					seqId = DB.getSeqValue("SEQ_category_m_sort_id");
					break;
				// 基地动漫货架
				case 5:
					seqId = DB.getSeqValue("SEQ_category_c_sort_id");
					break;
				// 基地游戏货架
				case 6:
					seqId = DB.getSeqValue("SEQ_category_g_sort_id");
					break;
			}
		}
		catch (DAOException e)
		{
			logger.error("得到货架的排序序列值出错！", e);
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("getSEQByCategoryType(" + categoryType + ") end");
		}
		return seqId;
	}
	
	public String getPathByCategoryId(String categoryId, int type)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("getPathByCategoryId(" + categoryId + ", " + type
					+ ") start");
		}
		
		String pathBase = "货架根目录";
		String path = "";
		
		try
		{
			switch (type)
			{
				// 基地阅读货架
				case 1:
					// select to_char(wmsys.wm_concat('>>' || categoryname)) as path from
					// (select c.categoryname from t_rb_category_new c start
					// with c.id = ? connect by prior c.parentid = c.id order by
					// rownum desc)
					path = getPathBySql(
							"SEQCategoryUtil.getPathByCategoryId.category_r",
							categoryId);
					break;
				// 基地视频货架
				case 2:
					// select to_char(wmsys.wm_concat('>>' ||basename)) as path from
					// (select c.basename from t_vo_category c start with c.id =
					// ? connect by prior c.parentid = id order by rownum desc)
					path = getPathBySql(
							"SEQCategoryUtil.getPathByCategoryId.category_v",
							categoryId);
					break;
				// 基地音乐货架
				case 3:
					// select to_char(wmsys.wm_concat('>>' || categoryname)) as path from
					// (select c.categoryname from t_mb_category_new c start
					// with c.categoryid = ? connect by prior c.parentcategoryid
					// = c.categoryid order by rownum desc)
					path = getPathBySql(
							"SEQCategoryUtil.getPathByCategoryId.category_m",
							categoryId);
					break;
				// 基地动漫货架
				case 4:
					// select to_char(wmsys.wm_concat('>>' || categoryname)) as path from
					// (select c.categoryname from t_cb_category c start with
					// c.categoryid = ? connect by prior c.parentcategoryid =
					// c.categoryid order by rownum desc)
					path = getPathBySql(
							"SEQCategoryUtil.getPathByCategoryId.category_c",
							categoryId);
					break;
					// 新基地视频货架
				case 5:
					// select to_char(wmsys.wm_concat('>>' ||cname)) as path from
					// (select c.cname from t_v_category c start with c.categoryid =
					// ? connect by prior c.parentcid = c.categoryid order by rownum desc)
					path = getPathBySql(
							"SEQCategoryUtil.getPathByCategoryId.category_v_new",
							categoryId);
					break;
				case 6:
					// select to_char(wmsys.wm_concat('>>' ||cname)) as path from
					// (select c.cname from t_v_category c start with c.categoryid =
					// ? connect by prior c.parentcid = c.categoryid order by rownum desc)
					path = getPathBySql(
							"SEQCategoryUtil.getPathByCategoryId.category_wp",
							categoryId);
					break;
			}
		}
		catch (DAOException e)
		{
			logger.error("得到货架的路径时数据库出现错误！", e);
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("getPathByCategoryId(" + categoryId + ", " + type
					+ ")  end");
		}
		
		return pathBase + path;
	}
	
	public String getPathBySql(String sql, String id) throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("getPathBySql() is starting ...");
		}
		
		ResultSet rs = null;
		String path = "";
		
		try
		{
			rs = DB.getInstance().queryBySQLCode(sql.toString(),
					new Object[] { id });
			if (rs.next())
			{
				path = rs.getString(1);
			}
		}
		catch (SQLException e)
		{
			throw new DAOException("查看指定货架中是否存在指定音乐时发生异常:", e);
		}
		finally
		{
			DB.close(rs);
		}
		
		return path.replaceAll(",", "");
	}
}
