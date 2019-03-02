package com.aspire.ponaadmin.web.ztree;

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
import com.aspire.dotcard.basecomic.template.UpdateImportTemplate;

public class TreeNodeManager
{

	private static JLogger logger = LoggerFactory
			.getLogger(TreeNodeManager.class);

	private static Map path = new HashMap()
	{
		{
			put("701", "货架");
		}
	};

	public static List getchildrenNodes(String id) throws DAOException
	{
		List nodeList = new ArrayList();
		ResultSet rs = null;
		try
		{
			String sql = "com.aspire.ponaadmin.my_ztree.TreeNodeManager.getchildrenNodes().SELECT";
			String[] strs = id.split(",");
			if(strs.length > 1){
				StringBuffer sb = new StringBuffer(DB.getInstance().getSQLByCode("com.aspire.ponaadmin.my_ztree.TreeNodeManager.getchildrenNodes().SELECT1"));
				sb.append(" and b.parentid = '" + strs[0] + "'");
				sb.append(" and c.classify_status = '1' ");
				sb.append(" and delFlag != 1 order by sortId desc, ctype desc, name asc");
				rs = DB.getInstance().query(sb.toString(), null);
			}else{
				rs = DB.getInstance().queryBySQLCode(sql, new Object[]
						{ id });
			}
			

			while (rs.next())
			{
				nodeList.add(new TreeNode(rs.getString("id"), rs
						.getString("name"), rs.getString("parentid")));
				path.put(rs.getString("id"), path.get(rs.getString("parentid"))
						+ ">>" + rs.getString("name"));

			}

		}
		catch (SQLException e)
		{
			throw new DAOException("获取树的子节点出问题！", e);
		}
		return nodeList;
	}

	public static List getchildrenNodes(String sql, String id)
	{
		List nodeList = new ArrayList();
		ResultSet rs = null;
		try
		{
			if (id == null)
			{
				rs = DB.getInstance().query(sql, null);
			}
			else
			{
				rs = DB.getInstance().query(sql, new Object[]
				{ id });
			}

			while (rs.next())
			{
				nodeList.add(new TreeNode(rs.getString("id"), rs
						.getString("name"), rs.getString("parentid")));
			}

		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			logger.error("获取树的子节点出问题！", e);
		}
		return nodeList;

	}

	public static List getchildrenNodesByVideo(String sqlCode, String id)
	{
		List nodeList = new ArrayList();
		ResultSet rs = null;
		try
		{
			if (id == null)
			{
				rs = DB.getInstance().queryBySQLCode(sqlCode, null);
			}
			else
			{
				rs = DB.getInstance().queryBySQLCode(sqlCode, new Object[]
				{ id });
			}

			while (rs.next())
			{
				nodeList.add(new TreeNode(rs.getString("id"), rs
						.getString("name"), rs.getString("parentid"), rs
						.getString("baseid")));
			}

		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			logger.error("获取树的子节点出问题！", e);
		}
		return nodeList;

	}

	public static List getchildrenNodesComic(String id,String flag)
	{
		String sqlCode = null;
		if (id == null)
		{
			sqlCode = "com.aspire.ponaadmin.web.ztree.TreeNodeManager.getchildrenNodes.comic.SELECT";
		}
		else
		{
			sqlCode = "com.aspire.ponaadmin.web.ztree.TreeNodeManager.getchildrenNodes.comic.SELECT2";
		}
		StringBuffer sql = new StringBuffer();
		try {
			sql.append(DB.getInstance().getSQLByCode(sqlCode));
			if(flag != null && !"".equals(flag) && "1".equals(flag)){
				sql.append(" and c.anime_status = '1' ");
			}
			
			sql.append(" order by sortid desc nulls last");
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			logger.error("获取树的子节点出问题！", e);
		}

		return getChildrenNodes(sql.toString(), id);

	}
	
	
	public static List getchildrenNodesNewMusic(String id,String flag)
	{
		String sqlCode = null;
		if (id == null)
		{
			sqlCode = "com.aspire.ponaadmin.web.ztree.TreeNodeManager.getchildrenNodes.newmusic.SELECT";
		}
		else
		{
			sqlCode = "com.aspire.ponaadmin.web.ztree.TreeNodeManager.getchildrenNodes.newmusic.SELECT2";
		}

		StringBuffer sql = new StringBuffer();
		try {
			sql.append(DB.getInstance().getSQLByCode(sqlCode));
			if(flag != null && !"".equals(flag) && "1".equals(flag)){
				sql.append(" and c.MUSIC_STATUS = '1' ");
			}
			
			sql.append(" order by sortid desc nulls last");
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			logger.error("获取树的子节点出问题！", e);
		}

		return getChildrenNodes(sql.toString(), id);

	}
	public static List getchildrenNodesVideo(String id,String flag)
	{
		String sqlCode = null;
		if (id == null)
		{
			sqlCode = "com.aspire.ponaadmin.web.ztree.TreeNodeManager.getchildrenNodes.video.SELECT";
		}
		else
		{
			sqlCode = "com.aspire.ponaadmin.web.ztree.TreeNodeManager.getchildrenNodes.video.SELECT2";
		}

		StringBuffer sql = new StringBuffer();
		try {
			sql.append(DB.getInstance().getSQLByCode(sqlCode));
			if(flag != null && !"".equals(flag) && "1".equals(flag)){
				sql.append(" and t.video_status = '1' ");
			}
			
			sql.append(" order by sortid desc nulls last");
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			logger.error("获取树的子节点出问题！", e);
		}

		return getChildrenNodes(sql.toString(), id);

	}
	
	public static List getchildrenNodesVideoNew(String id,String flag)
	{
		String sqlCode = null;
		if (id == null)
		{
			sqlCode = "com.aspire.ponaadmin.web.ztree.TreeNodeManager.getchildrenNodes.videoNew.SELECT";
		}
		else
		{
			sqlCode = "com.aspire.ponaadmin.web.ztree.TreeNodeManager.getchildrenNodes.videoNew.SELECT2";
		}
		StringBuffer sql = new StringBuffer();
		try {
			sql.append(DB.getInstance().getSQLByCode(sqlCode));
			if(flag != null && !"".equals(flag) && "1".equals(flag)){
				sql.append(" and c.video_status = '1' ");
			}
			
			sql.append(" order by sortid desc nulls last");
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			logger.error("获取树的子节点出问题！", e);
		}

		return getchildrenNodes(sql.toString(), id);
//		return getchildrenNodes(sqlCode, id);

	}
	public static List getchildrenNodesWpInfo(String id)
	{
		String sqlCode = null;
		if (id == null)
		{
			sqlCode = "com.aspire.ponaadmin.web.ztree.TreeNodeManager.getchildrenNodes.wpInfo.SELECT";
		}
		else
		{
			sqlCode = "com.aspire.ponaadmin.web.ztree.TreeNodeManager.getchildrenNodes.wpInfo.SELECT2";
		}
		StringBuffer sql = new StringBuffer();
		try {
			sql.append(DB.getInstance().getSQLByCode(sqlCode));
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("getchildrenNodesWpInfo:"+sqlCode, e);
		}
		return getchildrenNodes(sql.toString(), id);

	}
	public static List getchildrenNodesRead(String id,String flag)
	{
		String sqlCode = null;
		if (id == null)
		{
			sqlCode = "com.aspire.ponaadmin.web.ztree.TreeNodeManager.getchildrenNodes.read.SELECT";
		}
		else
		{
			sqlCode = "com.aspire.ponaadmin.web.ztree.TreeNodeManager.getchildrenNodes.read.SELECT2";
		}

		StringBuffer sql = new StringBuffer();
		try {
			sql.append(DB.getInstance().getSQLByCode(sqlCode));
			if(flag != null && !"".equals(flag) && "1".equals(flag)){
				sql.append(" and t.read_status = '1' ");
			}
			
			sql.append(" order by sortid desc nulls last");
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			logger.error("获取树的子节点出问题！", e);
		}

		return getChildrenNodes(sql.toString(), id);

	}

	public static String getPath(String id)
	{
//		return"";
		return (String) path.get(id);

	}
	public static String getPath(String id,String pathName)
	{
//		return "";
		if(pathName == null || "".equals(pathName.trim())){
			return (String) path.get(id);
		}else{
			String pathAdr = (String) path.get(id);
			if(pathAdr == null){
				pathAdr = pathName;
				path.put(id, pathName);
			}
			if(pathAdr != null && pathAdr.contains("null")){
				pathAdr = pathAdr.replace("null", pathName);
			}
			return pathAdr;
		}
	}
	
	public static List getChildrenNodes(String sql, String id)
	{
		List nodeList = new ArrayList();
		ResultSet rs = null;
		try
		{
			if (id == null)
			{
				rs = DB.getInstance().query(sql, null);
			}
			else
			{
				rs = DB.getInstance().query(sql, new Object[]
				{ id });
			}

			while (rs.next())
			{
				nodeList.add(new TreeNode(rs.getString("id"), rs
						.getString("name"), rs.getString("parentid")));
			}

		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			logger.error("获取树的子节点出问题！", e);
		}
		return nodeList;

	}
	
	public static List getSingleCategoryNodes(String userId,String type) throws DAOException
	{
		List nodeList = new ArrayList();
		ResultSet rs = null;
		try
		{
			String sql = "com.aspire.ponaadmin.my_ztree.TreeNodeManager.getSingleCategoryNodes().SELECT";

			rs = DB.getInstance().queryBySQLCode(sql,
					new Object[] { userId, type });

			while (rs.next()) {
				nodeList.add(new TreeNode(rs.getString("id"), rs
						.getString("name"), rs.getString("parentid")));
				path.put(rs.getString("id"), path.get(rs.getString("parentid"))
						+ ">>" + rs.getString("name"));

			}

		}
		catch (SQLException e)
		{
			throw new DAOException("获取树的子节点出问题！", e);
		}
		return nodeList;
	}
}
