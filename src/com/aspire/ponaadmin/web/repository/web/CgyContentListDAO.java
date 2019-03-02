package com.aspire.ponaadmin.web.repository.web;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.gcontent.GAppContent;
import com.aspire.dotcard.gcontent.GContent;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.page.PageVOInterface;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.ReferenceNode;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.repository.SearchParam;
import com.aspire.ponaadmin.web.repository.Searchor;
import com.aspire.ponaadmin.web.repository.Taxis;
import com.aspire.ponaadmin.web.repository.TaxisParam;
import com.aspire.ponaadmin.web.util.PublicUtil;
/**
 * 
 * @author zhangwei
 *
 */
public class CgyContentListDAO
{
	private static CgyContentListDAO DAO=new CgyContentListDAO();
	
	private static final JLogger LOG = LoggerFactory.getLogger(CgyContentListDAO.class) ;
	
	public static CgyContentListDAO getInstance()
	{
		return DAO;
	}
	
	
	/**
	 * 获取当前货架/非当前货架下分页的商品的内容。
	 * @param page 分页对象
	 * @param category 待查询的货架
	 *  @param parasMap 查询条件
	 * @param isExistedType boolean 是否搜索条件含有type类型，如果有的话，特殊处理。
	 * @param isIncluded boolean 是否包含本地货架的商品。true表示之查询该货架的商品内容，false表示查询非该货架的商品内容
	 * @param isExistedDevice 是否包含机型查询
	 * @param aprovalStatus
	 *           货架商品状态 0：编辑 1：已发布 2：审批不通过 3：待审批
	 */
	public void getCgyContentList( PageResult page,Category category, Searchor searchor,Taxis taxis,boolean isExistedType,boolean isExistedDevice,String aprovalStatus)throws DAOException
	{
		 String sql;
		// 有内容类型条件需要关联t_r_base表。
		if (isExistedType)
		{
			if (LOG.isDebugEnabled())
			{
				LOG.debug("开始查询货架为：" + category.getName() + ",下的商品，查询条件含有类型字段");
			}
			if (isExistedDevice)
			{//包含机型  关联v_cm_device_resource 提升查询速度
				sql = DB
				.getInstance()
				.getSQLByCode(
						//"ponaadmin.web.repository.web.CgyContentListDAO.existedTypeExistedDevice.SELECT");
						  "ponaadmin.web.repository.web.CgyContentListDAO.existedTypeExistedDevice.SELECT_Export");
			}
			else
			{
				sql = DB
						.getInstance()
						.getSQLByCode(
								//"ponaadmin.web.repository.web.CgyContentListDAO.existedType.SELECT");
								  "ponaadmin.web.repository.web.CgyContentListDAO.existedType.SELECT_Export");
			}
		}
		else
		{
			if (LOG.isDebugEnabled())
			{
				LOG.debug("开始查询货架为：" + category.getName() + ",下的商品，查询条件不含有类型字段");
			}
			if (isExistedDevice)
			{//包含机型 关联v_cm_device_resource 提升查询速度
				sql = DB
				.getInstance()
				.getSQLByCode(
						//"ponaadmin.web.repository.web.CgyContentListDAO.notExistedTypeExistedDevice.SELECT");
						  "ponaadmin.web.repository.web.CgyContentListDAO.notExistedTypeExistedDevice.SELECT_Export");
			}
			else
			{
				sql = DB
				.getInstance()
				.getSQLByCode(
						//"ponaadmin.web.repository.web.CgyContentListDAO.notExistedType.SELECT");
						  "ponaadmin.web.repository.web.CgyContentListDAO.notExistedType.SELECT_Export");
			}
			
		}
	    StringBuffer bf=new StringBuffer(sql);
	    List paraList=new ArrayList();
	    paraList.add(category.getCategoryID());//第一个参数。
	    //设置查询参数。
	    setSearchSQL(bf, paraList, searchor,aprovalStatus);
	    //设置排序参数。
	    setTaxisSQL(bf,taxis);
	    //查询本分类下的内容 page包含的节点类型为：nt:reference
	    page.excute(bf.toString(), paraList.toArray(), new AppContentPageTempVO());
	    
		
	}

	/**
	 * 获取当前货架/非当前货架下分页的商品的内容。
	 * @param page 分页对象
	 * @param category 待查询的货架
	 *  @param parasMap 查询条件
	 * @param isExistedType boolean 是否搜索条件含有type类型，如果有的话，特殊处理。
	 * @param isIncluded boolean 是否包含本地货架的商品。true表示之查询该货架的商品内容，false表示查询非该货架的商品内容
	 */
	public void getCgyContentList( PageResult page,Category category, Searchor searchor,Taxis taxis,boolean isExistedType)throws DAOException
	{
		 String sql;
		// 有内容类型条件需要关联t_r_base表。
		if (isExistedType)
		{
			if (LOG.isDebugEnabled())
			{
				LOG.debug("开始查询货架为：" + category.getName() + ",下的商品，查询条件含有类型字段");
			}
			sql = DB.getInstance().getSQLByCode(
					"ponaadmin.web.repository.web.CgyContentListDAO.existedType.SELECT");
		}
		else
		{
			if (LOG.isDebugEnabled())
			{
				LOG.debug("开始查询货架为：" + category.getName() + ",下的商品，查询条件不含有类型字段");
			}
			sql = DB
					.getInstance()
					.getSQLByCode(
							"ponaadmin.web.repository.web.CgyContentListDAO.notExistedType.SELECT");
		}
	    StringBuffer bf=new StringBuffer(sql);
	    List paraList=new ArrayList();
	    paraList.add(category.getCategoryID());//第一个参数。
	    //设置查询参数。
	    setSearchSQL(bf, paraList, searchor,null);
	    //设置排序参数。
	    setTaxisSQL(bf,taxis);
	    //查询本分类下的内容 page包含的节点类型为：nt:reference
	    page.excute(bf.toString(), paraList.toArray(), new AppContentPageVO());
	    
		
	}
	/**
	 * 获取非当前货架下分页的商品的内容。如果categoryid是根货架id，则表示查询未分类资源。
	 * @param page 分页对象
	 * @param category 待查询的货架
	 *  @param parasMap 查询条件
	 * @param isExistedType boolean 是否搜索条件含有type类型，如果有的话，特殊处理。
	 * @param isIncluded boolean 是否包含本地货架的商品。true表示之查询该货架的商品内容，false表示查询非该货架的商品内容
	 */
	public void getCgyNotContentList( PageResult page,Category category, Searchor searchor,Taxis taxis,boolean isExistedType)throws DAOException
	{
		String sql;
		List paraList = new ArrayList();
		if(isExistedType)
		{
			if(LOG.isDebugEnabled())
			{
				LOG.debug("开始查询不属于货架为："+category.getName()+",下的内容，查询条件不含有类型字段");
			}
			sql =DB.getInstance().getSQLByCode("ponaadmin.web.repository.web.CgyContentListDAO.notCgy.existedType.SELECT");
		}else
		{
			if(LOG.isDebugEnabled())
			{
				LOG.debug("开始查询不属于货架为："+category.getName()+",下的内容，查询条件不含有类型字段");
			}
			sql =DB.getInstance().getSQLByCode("ponaadmin.web.repository.web.CgyContentListDAO.notCgy.notExistedType.SELECT");
		}
		if(RepositoryConstants.ROOT_CATEGORY_ID.equals(category.getId()))//查询未分类资源。需要sql中的 and r.categoryid=? 去掉。为了简单处理只能和sql耦合了
		{
			sql=PublicUtil.replace(sql, "and r.categoryid=?", "");
		}else
		{
			paraList.add(category.getCategoryID());// 第一个参数。
		}
		StringBuffer bf = new StringBuffer(sql);
		// 设置查询参数。
		setSearchSQL(bf, paraList, searchor,null);
		// 设置排序参数。
		setTaxisSQL(bf, taxis);
		page.excute(bf.toString(), paraList.toArray(), new NotCgyContentPageVO());
	}
	/**
	 * 应用类分页读取VO的实现类
	 * 
	 * @author zhangwei
	 * 
	 */
	private class AppContentPageVO implements PageVOInterface
	{
		public void CopyValFromResultSet(Object vo, ResultSet rs) throws SQLException
		{
			ReferenceNode refNode=(ReferenceNode)vo;
			setReferenceValues(refNode,rs);
			GAppContent app=new GAppContent();
			setGAppContentValues(app,rs);
			refNode.setRefNode(app);
		}

		public Object createObject()
		{
			// TODO Auto-generated method stub
			return new ReferenceNode();
		}
	}
	
	/**
	 * 应用类分页读取VO的实现类
	 * 
	 * @author zhangwei
	 * 
	 */
	private class AppContentPageTempVO implements PageVOInterface
	{
		public void CopyValFromResultSet(Object vo, ResultSet rs) throws SQLException
		{
			ReferenceNode refNode=(ReferenceNode)vo;
			setReferenceValues(refNode,rs);
			GAppContent app=new GAppContent();
			setGAppContentValues(app,rs);
			app.setAdvertPic(String.valueOf(rs.getInt("mobileprice")/10));
			refNode.setRefNode(app);
		}

		public Object createObject()
		{
			// TODO Auto-generated method stub
			return new ReferenceNode();
		}
	}
	
	/**
	 * 应用类分页读取VO的实现类
	 * @author zhangwei
	 *
	 */
	private class NotCgyContentPageVO implements PageVOInterface
	{
		public void CopyValFromResultSet(Object vo, ResultSet rs) throws SQLException
		{
			GContent content=(GContent)vo;
			setGContentValues(content, rs);
			content.setAdvertPic(String.valueOf(rs.getInt("mobileprice")/10));
		}

		public Object createObject()
		{
			return new GContent();
		}
		
	}
	/**
	 * 设置商品表的信息。
	 * @param content
	 * @param rs
	 * @throws SQLException 
	 */
	private void setReferenceValues(ReferenceNode refNode,ResultSet rs) throws SQLException
	{
		//refNode.setId("")；为了性能，再次只读取t_r_reference 表中商品的信息。
		//t_r_base 表中的商品信息暂时不会用到，所以在此不用去取了。
		refNode.setId(rs.getString("referenceid"));//商品内码id。为了区别内容的id，取了个别名。
		refNode.setGoodsID(rs.getString("goodsid"));
		refNode.setCategoryID(rs.getString("categoryid"));
		refNode.setSortID(rs.getInt("sortid"));
		refNode.setLoadDate("LoadDate");
		refNode.setRefNodeID("id");
		refNode.setVerifyStatus(rs.getString("verifyStatus"));
		refNode.setDelFlag(rs.getString("delFlag"));
		refNode.setAppId(rs.getString("appId"));
	}
	/**
	 * 设置基本内容表的信息。目前只需要填写列表中需要的即可
	 * @param content
	 * @param rs
	 */
	private void setGContentValues(GContent content,ResultSet rs)throws SQLException
	{
		content.setType(null);//去除类型。
		content.setId(rs.getString("id"));
		content.setName(rs.getString("name"));
        content.setCateName(rs.getString("cateName"));
        content.setSpName(rs.getString("spName"));
        content.setIcpCode(rs.getString("icpCode"));
        content.setIcpServId(rs.getString("icpServId"));
        content.setContentID(rs.getString("contentID"));
        content.setMarketDate(rs.getString("marketdate"));
        content.setKeywords(rs.getString("keywords"));
        content.setLupdDate(rs.getString("lupddate"));
        content.setServAttr(rs.getString("servAttr"));
        content.setSubType(rs.getString("subType"));
        content.setAppId(rs.getString("appId"));
	}
	/**
	 * 设置应用的类型的信息。
	 * @param content
	 * @param rs
	 */
	private void setGAppContentValues(GAppContent content,ResultSet rs)throws SQLException
	{
		setGContentValues(content,rs);
		//以后需要添加。
	}
	private void setSearchSQL(StringBuffer sql, List paraList,Searchor searchor,String aprovalStatus)
	{
		 // 遍历表达式的各个参数，进行处理
        for (int i = 0; i < searchor.getParams().size(); i++)
        {
            SearchParam param = ( SearchParam ) searchor.getParams().get(i);
            sql.append(" ");
            sql.append(param.getMode());
            sql.append(" ");
            // 如果是左括号开始，要添加一个(
            if (param.getBracket()
                     .equals(RepositoryConstants.SEARCH_PARAM_BRACKET_LEFT))
            {
                sql.append(RepositoryConstants.SEARCH_PARAM_BRACKET_LEFT);
            }
            // 从类字段对应到数据库表字段
            if(param.getOperator().equals(RepositoryConstants.OP_LIKE_IgnoreCase))
            {
            	sql.append("upper(");
            	sql.append(param.getProperty());
                sql.append(") like ");
                sql.append("?");
            	
            }else
            {
            	sql.append(param.getProperty());
                sql.append(' ');
                sql.append(param.getOperator());
                sql.append(' ');
                sql.append('?');
            }
            
            // 如果是右括号结束，要添加一个)
            if (param.getBracket()
                     .equals(RepositoryConstants.SEARCH_PARAM_BRACKET_RIGHT))
            {
                sql.append(RepositoryConstants.SEARCH_PARAM_BRACKET_RIGHT);
            }
            
            paraList.add(param.getValue());
        }
        if(aprovalStatus != null && !"".equals(aprovalStatus) && !"4".equals(aprovalStatus)){
        	String[] strs = aprovalStatus.split(",");
        	if(strs.length > 1){
        		sql.append(" and r.verify_status in ('" + strs[0] + "','"+strs[1]+"')");
        	}else{
        		sql.append(" and r.verify_status = '" +strs[0]+"'");
        	}
	    	
	    }
	}
	 /**
     * 设置查询时的排序语句
     *
     * @param sql StringBuffer，sql保存的StringBuffer
     * @param taxis Taxis，排序表达式置
     */
    private void setTaxisSQL(StringBuffer sql, Taxis taxis)
    {

        // 如果没有排序参数，直接返回
        if ((taxis == null) || (taxis.getParams().size() == 0))
        {
            return;
        }

        sql.append(" order by ");

        for (int i = 0; i < taxis.getParams().size(); i++)
        {
            TaxisParam param = ( TaxisParam ) taxis.getParams().get(i);
            String row = param.getProperty();
            if (i > 0)
            {
                sql.append(',');
            }
            sql.append(row);
            sql.append(' ');
            sql.append(param.getOrder());
        }
    }

    
    /**
	 * 获取当前货架/非当前货架下分页的商品的内容。
	 * 
	 * @param page
	 *            分页对象
	 * @param category
	 *            待查询的货架
	 * @param parasMap
	 *            查询条件
	 * @param isExistedType
	 *            boolean 是否搜索条件含有type类型，如果有的话，特殊处理。
	 * @param isIncluded
	 *            boolean 是否包含本地货架的商品。true表示之查询该货架的商品内容，false表示查询非该货架的商品内容
	 * @param isExistedDevice
	 *            是否包含机型查询
	 */
	public List getCgyContentListByExport(PageResult page, Category category,
			Searchor searchor, Taxis taxis, boolean isExistedType,
			boolean isExistedDevice) throws DAOException
	{
		List list = new ArrayList();
		ResultSet rs = null;
		String sql;
		// 有内容类型条件需要关联t_r_base表。
		if (isExistedType)
		{
			if (LOG.isDebugEnabled())
			{
				LOG.debug("开始查询货架为：" + category.getName() + ",下的商品，查询条件含有类型字段");
			}
			if (isExistedDevice)
			{
				// 包含机型 关联v_cm_device_resource 提升查询速度
				sql = DB
						.getInstance()
						.getSQLByCode(
								"ponaadmin.web.repository.web.CgyContentListDAO.existedTypeExistedDevice.SELECT_Export");
			}
			else
			{
				sql = DB
						.getInstance()
						.getSQLByCode(
								"ponaadmin.web.repository.web.CgyContentListDAO.existedType.SELECT_Export");
			}
		}
		else
		{
			if (LOG.isDebugEnabled())
			{
				LOG
						.debug("开始查询货架为：" + category.getName()
								+ ",下的商品，查询条件不含有类型字段");
			}
			if (isExistedDevice)
			{
				// 包含机型 关联v_cm_device_resource 提升查询速度
				sql = DB
						.getInstance()
						.getSQLByCode(
								"ponaadmin.web.repository.web.CgyContentListDAO.notExistedTypeExistedDevice.SELECT_Export");
			}
			else
			{
				sql = DB
						.getInstance()
						.getSQLByCode(
								"ponaadmin.web.repository.web.CgyContentListDAO.notExistedType.SELECT_Export");
			}

		}
		StringBuffer bf = new StringBuffer(sql);
		List paraList = new ArrayList();
		paraList.add(category.getCategoryID());// 第一个参数。
		// 设置查询参数。
		setSearchSQL(bf, paraList, searchor,null);
		// 设置排序参数。
		setTaxisSQL(bf, taxis);

		try
		{
			rs = DB.getInstance().query(bf.toString(), paraList.toArray());

			while (rs.next())
			{
				GContent content = new GContent();

				content.setType(null);//去除类型。
				content.setId(String.valueOf(rs.getInt("mobileprice")));
				content.setName(rs.getString("name"));
				content.setCateName(rs.getString("appcatename"));
				content.setSpName(rs.getString("spName"));
				content.setIcpCode(rs.getString("icpCode"));
				content.setIcpServId(rs.getString("icpServId"));
				content.setContentID(rs.getString("contentID"));
				content.setMarketDate(rs.getString("marketdate"));
				content.setKeywords(rs.getString("keywords"));
				content.setLupdDate(rs.getString("lupddate"));
				content.setServAttr(rs.getString("servAttr"));
				content.setSubType(rs.getString("subType"));
				content.setAppId(rs.getString("appId"));
				list.add(content);
			}
		}
		catch (Exception e)
		{
			throw new DAOException("获取当前货架/非当前货架下分页的商品的内容时发生异常:", e);
		}

		return list;
	}
}
