package com.aspire.ponaadmin.web.repository.camonitor;

import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.db.SQLUtil;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.repository.SearchParam;
import com.aspire.ponaadmin.web.repository.Searchor;
import com.aspire.ponaadmin.web.repository.Taxis;
import com.aspire.ponaadmin.web.repository.TaxisParam;
import com.aspire.ponaadmin.web.repository.web.CgyContentListBO;
import com.aspire.ponaadmin.web.system.PageSizeConstants;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * <p>查询栏目下内容列表的Action</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class MoContentListAction extends BaseAction
{

	/**
	 * 日志引用
	 */
	private static final JLogger LOG = LoggerFactory
			.getLogger(MoContentListAction.class);

	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws BOException
	{
		LOG.debug("doPerform()");

		//是否查询内容的类型。
		boolean isExistedType = false;
		String categoryID = this.getParameter(request, "categoryID");
		String name = this.getParameter(request, "name");
		// String author = this.getParameter(request, "author");
		//String source = this.getParameter(request, "source");
		// String keywords = this.getParameter(request, "keywords");

		String spName = this.getParameter(request, "spName");
		String cateName = this.getParameter(request, "cateName");
		String type = this.getParameter(request, "type");
		String icpCode = this.getParameter(request, "icpCode");
		String icpServId = this.getParameter(request, "icpServId");
		String contentID = this.getParameter(request, "contentID");

		String contentTag = this.getParameter(request, "contentTag");
		String tagLogic = this.getParameter(request, "tagLogic");

		String devicen = (String)request.getSession().getAttribute("deviceName");
		String deviceName = "";
		if(devicen == null || devicen.equals("")){
			 deviceName = this.getParameter(request, "deviceName").toUpperCase();//忽略大小写。
		}else{
			 deviceName = devicen;
		}
		

		String pageSize = this.getParameter(request, "pageSize");
		String servAttr = this.getParameter(request, "servAttr");

		if ("".equals(pageSize.trim()))
		{
			pageSize = PageSizeConstants.page_DEFAULT;
		}

		//设置搜索条件：
		Searchor searchor = new Searchor();

		if (!name.equals(""))
		{
			searchor.getParams()
					.add(
							new SearchParam("name", RepositoryConstants.OP_LIKE,
									'%' + SQLUtil.escape(name) + '%'));
		}
		if (!spName.equals(""))
		{
			searchor.getParams().add(
					new SearchParam("spName", RepositoryConstants.OP_LIKE,
							'%' + SQLUtil.escape(spName) + '%'));
		}
		if (!cateName.equals(""))
		{
			searchor.getParams().add(
					new SearchParam("cateName", RepositoryConstants.OP_LIKE,
							'%' + SQLUtil.escape(cateName) + '%'));
		}
		if (!type.equals(""))
		{
			SearchParam searchParam = new SearchParam("type",
					RepositoryConstants.OP_LIKE, SQLUtil.escape(type) + '%');
			searchParam.setSearchRef(true);
			searchor.getParams().add(searchParam);

			isExistedType = true;
		}
		if (!icpCode.equals(""))
		{
			searchor.getParams().add(
					new SearchParam("icpCode", RepositoryConstants.OP_LIKE,
							'%' + SQLUtil.escape(icpCode) + '%'));
		}
		if (!icpServId.equals(""))
		{
			searchor.getParams().add(
					new SearchParam("icpServId", RepositoryConstants.OP_LIKE,
							'%' + SQLUtil.escape(icpServId) + '%'));
		}
		if (!contentTag.equals(""))
		{
			StringTokenizer st = new StringTokenizer(contentTag, " ");
			int count = st.countTokens();
			for (int i = 0; st.hasMoreTokens(); i++)
			{
				String keyword = st.nextToken();
				SearchParam param = new SearchParam("keywords",
						RepositoryConstants.OP_LIKE, "%" + SQLUtil.escape(keyword) + "%");
				// 如果不是第一个，就是or类型的
				if (i > 0)
				{
					if ("OR".equals(tagLogic))
					{
						param.setMode(RepositoryConstants.SEARCH_PARAM_MODE_OR);
					}
					else
					{
						param.setMode(RepositoryConstants.SEARCH_PARAM_MODE_AND);
					}

				}
				// 如果有多个条件，要组合括号
				if (count > 1)
				{
					// 第一个要加一个左括号
					if (i == 0)
					{
						param.setBracket(RepositoryConstants.SEARCH_PARAM_BRACKET_LEFT);
					}
					// 最后一个要加一个右括号
					if (i == count - 1)
					{
						param.setBracket(RepositoryConstants.SEARCH_PARAM_BRACKET_RIGHT);
					}
				}
				searchor.getParams().add(param);
			}
		}

		if (!contentID.equals(""))
		{
			searchor.getParams().add(
					new SearchParam("refNodeID", RepositoryConstants.OP_LIKE,
							'%' + SQLUtil.escape(contentID) + '%'));
		}
		//      add by tungke 
		if (!servAttr.equals(""))
		{
			searchor.getParams().add(
					new SearchParam("servAttr", RepositoryConstants.OP_EQUAL, servAttr));
		}
		boolean isexistDevice = false;
		if (!deviceName.equals(""))
		{
			String newCategoryID = MoContentListBO.getInstance().getDeviceCategory(categoryID,deviceName.toUpperCase());
			if(newCategoryID != null && !newCategoryID.equals("") )
			{//是机型货架
				categoryID = newCategoryID;
				
			}
			else
			{//不是机型货架
				String devicenamen = PublicUtil.filterMbrandEspecialChar(deviceName);
			String temp = '%' + devicenamen + '%';
			SearchParam para=new SearchParam("fulldevicename",RepositoryConstants.OP_LIKE_IgnoreCase, temp);
			//para.setBracket(RepositoryConstants.SEARCH_PARAM_BRACKET_LEFT);//第一个参数前需要添加左括号
			searchor.getParams().add(para);
			isexistDevice = true;//按适配机型查询
			}
//			for (int i = 2; i <= 20; i++)
//			{
//				String colName = i < 10 ? "deviceName0" + i : "deviceName" + i;
//				para=new SearchParam(colName, RepositoryConstants.OP_LIKE_IgnoreCase,temp);
//				para.setMode(RepositoryConstants.SEARCH_PARAM_MODE_OR);
//				searchor.getParams().add(para);
//			}
//			para.setBracket(RepositoryConstants.SEARCH_PARAM_BRACKET_RIGHT);//最后一个参数需要添加右括号。
		}
		// 都是浅度搜索
		searchor.setIsRecursive(false);

		// 获取分类信息
		Category category = (Category) Repository.getInstance().getNode(categoryID,
				RepositoryConstants.TYPE_CATEGORY);

		//获取分类下的内容
		PageResult page = new PageResult(request);
		// page.setPageSize(6+6);
		page.setPageSize(Integer.parseInt(pageSize));

		//排序方式:根据1.1.1.044需求（先按序号降序再按接入时间降序排序）　guanzf 20071108
		Taxis taxis = new Taxis();
		taxis.getParams().add(
				new TaxisParam("sortID", RepositoryConstants.ORDER_TYPE_DESC));
		taxis.getParams().add(
				new TaxisParam("marketDate", RepositoryConstants.ORDER_TYPE_DESC));

//		CgyContentListBO.getInstance().getCgyContentList(page, category, searchor, taxis,
//				isExistedType);
		CgyContentListBO.getInstance().getCgyContentList(page, category, searchor, taxis,
				isExistedType,isexistDevice,null);
		request.setAttribute("category", category);
		request.setAttribute("PageResult", page);
		request.setAttribute("pageSize", pageSize);

		String forward = "showList";

		return mapping.findForward(forward);
	}
}
