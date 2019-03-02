package com.aspire.ponaadmin.web.repository.web;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.audit.dao.CheckLogDAO;
import com.aspire.dotcard.syncAndroid.ssms.SSMSDAO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.CategoryOperationBO;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.repository.SearchParam;
import com.aspire.ponaadmin.web.repository.Searchor;
import com.aspire.ponaadmin.web.repository.Taxis;
import com.aspire.ponaadmin.web.repository.TaxisParam;
import com.aspire.ponaadmin.web.system.PageSizeConstants;
import com.aspire.ponaadmin.web.util.PublicUtil;

public class CategoryGoodsListAction extends BaseAction{
	
	 /**
     * 日志引用
     */
    private static final JLogger logger = LoggerFactory.getLogger(CategoryGoodsListAction.class) ;
    
    
    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException
	{
		if (log.isDebugEnabled())
		{
			log.debug("CgyContentListAction in.....");
		}
	    String subSystem = this.getParameter(request, "subSystem");
	    if("ssms".equals(subSystem)){
	    	return this.ssms(mapping, form, request, response);
	    }else if("approval".equals(subSystem)){
	    	return approval(mapping, form, request, response);
	    }else{
	    	return refuse(mapping, form, request, response);
	    }
		
	}
    
    public ActionForward approval(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws BOException
	{
    	log.debug("CategoryGoodsListAction.approval is start!");
        String forward = "back";
        String cateid = request.getParameter("cateid");
        String backURL = this.getParameter(request, "backURL");
        String[] dealContent = request.getParameterValues("dealContent");
        
        // 获取分类信息
        Category category = ( Category ) Repository.getInstance()
                                                   .getNode(cateid,
                                                            RepositoryConstants.TYPE_CATEGORY);
        
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("mailTitle", "数据已发布：客户端门户运营内容已发布");
        String value = "";
        //value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;以下内容已发布，请注意验证：<br><br>&nbsp;&nbsp;&nbsp;&nbsp;审批对象：货架;<br>" + "&nbsp;&nbsp;&nbsp;&nbsp;货架Id：" + category.getCategoryID() ;
		try {
			value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;以下内容已发布，请注意验证：<br><br>&nbsp;&nbsp;&nbsp;&nbsp;审批对象：货架;<br>" + "&nbsp;&nbsp;&nbsp;&nbsp;货架Id：" + category.getCategoryID() + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;路径：" + CheckLogDAO.getInstance().getCategoryPath(category.getCategoryID());
		} catch (DAOException e) {
			log.debug("CategoryGoodsListAction.approval.mailContent" + e);
		}
        map.put("mailContent", value);
        map.put("status", "1");
        map.put("categoryId",category.getCategoryID());
        map.put("category", category);
        map.put("operation", "10");
        map.put("operationtype","货架商品管理审批发布");
        map.put("operationobj", "货架");
        map.put("operationobjtype", "货架Id："+ category.getCategoryID());
        CategoryOperationBO.getInstance().operateItemContentInfo(request, dealContent, "1", "2", map);
        this.saveMessages(request, "RESOURCE_CATE_RESULT_005");
        
        log.debug("CategoryGoodsListAction.approval.parameters:" + cateid);
        
        if("".equals(backURL.trim()))
        {
        	backURL="/web/resourcemgr/categoryGoodsList.do?subSystem=ssms&categoryID=" + cateid;
        }
        
        request.setAttribute(Constants.PARA_GOURL,
                             backURL);
        log.debug("CategoryGoodsListAction.approval.gourl=="
                  + request.getContextPath()
                  + backURL);
        
        return mapping.findForward(forward);
	}
    
    public ActionForward refuse(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws BOException
	{
    	log.debug("CategoryGoodsListAction.refuse is start!");
        String forward = "back";
        String cateid = request.getParameter("cateid");
        String backURL=this.getParameter(request, "backURL");
        String[] dealContent = request.getParameterValues("dealContent");
        
        // 获取分类信息
        Category category = ( Category ) Repository.getInstance()
                                                   .getNode(cateid,
                                                            RepositoryConstants.TYPE_CATEGORY);
        
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("mailTitle", "审核驳回：客户端门户运营内容审批驳回");
        String value = "";
        //value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;以下内容已发布，请注意验证：<br><br>&nbsp;&nbsp;&nbsp;&nbsp;审批对象：货架;<br>" + "&nbsp;&nbsp;&nbsp;&nbsp;货架Id：" + category.getCategoryID() ;
		try {
			value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;以下内容被" +  CategoryOperationBO.getInstance().getUserInfo(request) + "驳回，请进入系统重新编辑：<br><br>&nbsp;&nbsp;&nbsp;&nbsp;审批对象：货架;<br>" + "&nbsp;&nbsp;&nbsp;&nbsp;货架Id：" + category.getCategoryID() + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;路径：" + CheckLogDAO.getInstance().getCategoryPath(category.getCategoryID());
		} catch (DAOException e) {
			log.debug("CategoryGoodsListAction.refuse.mailContent" + e);
		}
        map.put("mailContent", value);
        map.put("status", "3");
        map.put("categoryId",category.getCategoryID());
        map.put("category", category);
        map.put("operation", "10");
        map.put("operationtype","货架商品管理审批不通过");
        map.put("operationobj", "货架");
        map.put("operationobjtype", "货架Id："+ category.getCategoryID());
        CategoryOperationBO.getInstance().operateItemContentInfo(request, dealContent, "2", "2", map);
        this.saveMessages(request, "RESOURCE_CATE_RESULT_006");
        
        log.debug("CategoryGoodsListAction.refuse.parameters:" + cateid);
        
        if("".equals(backURL.trim()))
        {
        	backURL="/web/resourcemgr/categoryGoodsList.do?subSystem=ssms&categoryID=" + cateid;
        }
        
        request.setAttribute(Constants.PARA_GOURL,
                             backURL);
        log.debug("CategoryGoodsListAction.refuse.gourl=="
                  + request.getContextPath()
                  + backURL);

        return mapping.findForward(forward);
	}
    
    
    public ActionForward ssms(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws BOException
	{
    	log.debug("ssms()");
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
		//add by aiyan 2012-03-05
		String trueContentID = this.getParameter(request, "trueContentID");

		String contentTag = this.getParameter(request, "contentTag");
		String tagLogic = this.getParameter(request, "tagLogic");

		//String deviceName = this.getParameter(request, "deviceName").toUpperCase();//忽略大小写。
		String deviceName = this.getParameter(request, "deviceName").trim();
		String platform = this.getParameter(request, "platform").trim();

		String pageSize = this.getParameter(request, "pageSize");
		String servAttr = this.getParameter(request, "servAttr");
		String aprovalStatus = this.getParameter(request, "aprovalStatus");

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
									'%' + name + '%'));
		}
		if (!spName.equals(""))
		{
			searchor.getParams().add(
					new SearchParam("g.spName", RepositoryConstants.OP_LIKE,
							'%' + spName + '%'));
		}
		if (!cateName.equals(""))
		{
			searchor.getParams().add(
					new SearchParam("cateName", RepositoryConstants.OP_LIKE,
							'%' + cateName + '%'));
		}
		if (!type.equals(""))
		{
			if("nt:gcontent:appBaseGame".equals(type))
			{
				SearchParam searchParam = new SearchParam("type",
						RepositoryConstants.OP_LIKE, "nt:gcontent:appGame" + '%');
				searchParam.setSearchRef(true);
				searchor.getParams().add(searchParam);
				
				searchor.getParams().add(
						new SearchParam("provider", RepositoryConstants.OP_EQUAL,
								"B"));
			}
			else
			{
				SearchParam searchParam = new SearchParam("type",
						RepositoryConstants.OP_LIKE, type + '%');
				searchParam.setSearchRef(true);
				searchor.getParams().add(searchParam);
			}

			isExistedType = true;
		}
		if (!icpCode.equals(""))
		{
			searchor.getParams().add(
					new SearchParam("g.icpCode", RepositoryConstants.OP_LIKE,
							'%' + icpCode + '%'));
		}
		if (!icpServId.equals(""))
		{
			searchor.getParams().add(
					new SearchParam("g.icpServId", RepositoryConstants.OP_LIKE,
							'%' + icpServId + '%'));
		}
		if (!contentTag.equals(""))
		{
			StringTokenizer st = new StringTokenizer(contentTag, " ");
			int count = st.countTokens();
			for (int i = 0; st.hasMoreTokens(); i++)
			{
				String keyword = st.nextToken();
				SearchParam param = new SearchParam("keywords",
						RepositoryConstants.OP_LIKE, "%" + keyword + "%");
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
							'%' + contentID + '%'));
		}
		
		//add by aiyan 2012-03-06
		if (!"".equals(trueContentID)){
			searchor.getParams().add(new SearchParam("g.ContentID", RepositoryConstants.OP_LIKE,'%' +trueContentID+ '%'));
		}
		
		//      add by tungke 
		if (!servAttr.equals(""))
		{
			searchor.getParams().add(
					new SearchParam("g.servAttr", RepositoryConstants.OP_EQUAL, servAttr));
		}
		
		// add by wml 2012-10-18
		if(!"".equals(platform))
		{
			searchor.getParams().add(
					new SearchParam("platform", RepositoryConstants.OP_LIKE, '%' +platform+ '%'));
		}
		boolean isexistDevice = false;
		if (!deviceName.equals(""))
		{
			String devicenamen = PublicUtil.filterMbrandEspecialChar(deviceName);
			String temp = '%' + devicenamen + '%';
			SearchParam para=new SearchParam("fulldevicename",RepositoryConstants.OP_LIKE, temp);
			searchor.getParams().add(para);
			isexistDevice = true;//按适配机型查询
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

		CgyContentListBO.getInstance().getCgyContentList(page, category, searchor, taxis,
				isExistedType,isexistDevice,aprovalStatus);
		request.setAttribute("category", category);
		request.setAttribute("PageResult", page);
		request.setAttribute("pageSize", pageSize);

		String forward = "showList";

		return mapping.findForward(forward);
	}

}
