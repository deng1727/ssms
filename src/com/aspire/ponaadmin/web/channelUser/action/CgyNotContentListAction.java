package com.aspire.ponaadmin.web.channelUser.action ;

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
import com.aspire.ponaadmin.common.usermanager.UserManagerBO;
import com.aspire.ponaadmin.common.usermanager.UserSessionVO;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.channelUser.util.Common;
import com.aspire.ponaadmin.web.channelUser.vo.ChannelVO;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.constant.ResourceConstants;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.repository.SearchParam;
import com.aspire.ponaadmin.web.repository.Searchor;
import com.aspire.ponaadmin.web.repository.Taxis;
import com.aspire.ponaadmin.web.repository.TaxisParam;
import com.aspire.ponaadmin.web.system.PageSizeConstants;

/**
 * <p>查询不在本栏目下的内容列表的Action</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class CgyNotContentListAction extends BaseAction
{

    /**
     * 日志引用
     */
    private static final JLogger LOG = LoggerFactory.getLogger(CgyNotContentListAction.class) ;

    /**
     *
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws BOException
     * @todo Implement this com.aspire.ponaadmin.web.BaseAction method
     */
    public ActionForward doPerform (ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request,
                                    HttpServletResponse response)
        throws BOException
    {
    	if(request.getSession() == null || UserManagerBO.getInstance()
    			.getUserSessionVO(request.getSession()) == null){
    		this.saveMessages(request, "用户未登陆或者登陆已超时！");
    		request.setAttribute(Constants.PARA_GOURL, "index.jsp");
    		 request.setAttribute("loginResult", new Integer(Common.LOGINRESULT));
    		return mapping.findForward(Common.LOGIN_HINT_CHANNELUSER);
    	}
        LOG.debug("doPerform()");
        
        //是否查询内容的类型。
		boolean isExistedType = false;
        String categoryID = this.getParameter(request, "categoryID");
        String name = this.getParameter(request, "name");
        //String author = this.getParameter(request, "author");
        //String source = this.getParameter(request, "source");
        //String keywords = this.getParameter(request, "keywords");

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
		String deviceName = this.getParameter(request, "deviceName").toUpperCase();//忽略大小写。

        String servAttr = this.getParameter(request, "servAttr").trim();
        String exeType = this.getParameter(request, "exeType");
        String pageSize=this.getParameter(request, "pageSize");
        if("".equals(pageSize.trim()))
        {
        	pageSize=PageSizeConstants.page_DEFAULT;
        }
                
        //设置搜索条件：
        Searchor searchor = new Searchor();
        if(!name.equals(""))
        {
            searchor.getParams().add(new SearchParam("name",
                                                     RepositoryConstants.
                                                     OP_LIKE, '%' + SQLUtil.escape(name) + '%')) ;
        }
        if(!spName.equals(""))
        {
            searchor.getParams().add(new SearchParam("g.spName",
                                                     RepositoryConstants.
                                                     OP_LIKE, '%' + SQLUtil.escape(spName) + '%')) ;
        }
        if(!cateName.equals(""))
        {
            searchor.getParams().add(new SearchParam("appCateName",
                                                     RepositoryConstants.
                                                     OP_LIKE, '%' + SQLUtil.escape(cateName) + '%')) ;
        }   
        if(!type.equals(""))
        {
			if("nt:gcontent:appBaseGame".equals(type))
			{
				SearchParam searchParam = new SearchParam("type",
						RepositoryConstants.OP_LIKE, "nt:gcontent:appGame" + '%');
				searchParam.setSearchRef(true);
				searchor.getParams().add(searchParam);
				
				/*searchor.getParams().add(
						new SearchParam("provider", RepositoryConstants.OP_EQUAL,
								"B"));*/
				searchor.getParams().add(
						new SearchParam("subtype", RepositoryConstants.OP_EQUAL,
								"22"));
			}
			else
			{
				SearchParam searchParam = new SearchParam("type",
						RepositoryConstants.OP_LIKE, type + '%');
				searchParam.setSearchRef(true);
				searchor.getParams().add(searchParam);
			}
			
			isExistedType=true;
        }
        if(!icpCode.equals(""))
        {
            searchor.getParams().add(new SearchParam("g.icpCode",
                                                     RepositoryConstants.
                                                     OP_LIKE, '%' + SQLUtil.escape(icpCode) + '%')) ;
        }    
        if(!icpServId.equals(""))
        {
            searchor.getParams().add(new SearchParam("g.icpServId",
                                                     RepositoryConstants.
                                                     OP_LIKE, '%' + SQLUtil.escape(icpServId) + '%')) ;
        }
        if (!contentTag.equals(""))
        {
            StringTokenizer st = new StringTokenizer(contentTag, " ");
            int count = st.countTokens();
            for (int i = 0; st.hasMoreTokens(); i++)
            {
                String keyword = st.nextToken();
                SearchParam param = new SearchParam("keywords",
                                                    RepositoryConstants.OP_LIKE,
                                                    "%{" + SQLUtil.escape(keyword) + "}%");
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
        
        if(!contentID.equals(""))
        {
            searchor.getParams().add(new SearchParam("g.id",
                                                     RepositoryConstants.
                                                     OP_LIKE, '%' + SQLUtil.escape(contentID) + '%')) ;
        }
        
		//add by aiyan 2012-03-06
		if (!"".equals(trueContentID)){
			searchor.getParams().add(new SearchParam("g.ContentID", RepositoryConstants.OP_LIKE,'%' +SQLUtil.escape(trueContentID)+ '%'));
		}
		
        //add by tungke 
        if(!servAttr.equals(""))
        {
            searchor.getParams().add(new SearchParam("g.servAttr",
                                                     RepositoryConstants.
                                                     OP_EQUAL, servAttr)) ;
        }
        if (!deviceName.equals(""))
		{
        	String temp = '%' + deviceName + '%';
			SearchParam para=new SearchParam("deviceName",RepositoryConstants.OP_LIKE_IgnoreCase, temp);
			para.setBracket(RepositoryConstants.SEARCH_PARAM_BRACKET_LEFT);//第一个参数前需要添加左括号
			searchor.getParams().add(para);
			for (int i = 2; i <= 20; i++)
			{
				String colName = i < 10 ? "deviceName0" + i : "deviceName" + i;
				para=new SearchParam(colName, RepositoryConstants.OP_LIKE_IgnoreCase,temp);
				para.setMode(RepositoryConstants.SEARCH_PARAM_MODE_OR);
				searchor.getParams().add(para);
			}
			para.setBracket(RepositoryConstants.SEARCH_PARAM_BRACKET_RIGHT);//最后一个参数需要添加右括号。
		}
        //设置搜索模式：
        if(categoryID.equals(RepositoryConstants.ROOT_CATEGORY_ID))
        {
            //当指定未分类时是深度搜索，要找出所有未在根分类下的。
            searchor.setIsRecursive(true);
        }
        else
        {
            //其它的是浅度搜索，只要找出未在本分类下的
            searchor.setIsRecursive(false);
        }
        //未在本分类下的
        searchor.setIsNotIn(true);


        //设置排序条件：按名称排序
        Taxis taxis = new Taxis();
        //taxis.getParams().add(new TaxisParam("name", RepositoryConstants.ORDER_TYPE_DEFAULT));
        
        //cq: IMALL_3147
        taxis.getParams().add(new TaxisParam("marketDate", RepositoryConstants.ORDER_TYPE_DESC));

        String forward = "showList";
        try
        {
            // 获取分类信息
            Category category = ( Category ) Repository.getInstance()
                                                       .getNode(categoryID,
                                                                RepositoryConstants.TYPE_CATEGORY);
            if (categoryID.equals(RepositoryConstants.ROOT_CATEGORY_ID))
            {
                category.setName("未分类资源");
            }
            // 查询资源内容，获取分类下的内容
            PageResult page = new PageResult(request);
            page.setPageSize(Integer.parseInt(pageSize));

            // add by tungke 20090928
            if (exeType.equals("0"))//第一次访问该页面不执行。
            {
                // do nothing
            }
            else
            {
            	UserSessionVO userSession = UserManagerBO.getInstance()
            			.getUserSessionVO(request.getSession());
            	ChannelVO channel =  userSession.getChannel();
            	CgyContentListBO.getInstance().getCgyNotContentList(page, category, searchor, taxis,
        				isExistedType,channel.getChannelsId());  
            }

            request.setAttribute("exeType", exeType);

            request.setAttribute("category", category);
            request.setAttribute("PageResult", page);
            request.setAttribute("pageSize", pageSize);
        }
        catch (Exception ex)
        {
            LOG.error(ex);
            forward = Constants.FORWARD_COMMON_FAILURE;
            this.saveMessages(request, ResourceConstants.WEB_ERR_SYSTEM) ;
            //如果不是未分类的，是要添加内容到分类下，都是模式窗口，需要关闭按钮的！
            if(!categoryID.equals(RepositoryConstants.ROOT_CATEGORY_ID))
            {
                request.setAttribute(Constants.PARA_ISCLOSE,"true");
            }
        }


        return mapping.findForward(forward);
    }
}
