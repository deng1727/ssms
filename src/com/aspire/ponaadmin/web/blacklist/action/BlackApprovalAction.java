package com.aspire.ponaadmin.web.blacklist.action;

import java.util.ArrayList;
import java.util.List;

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
import com.aspire.ponaadmin.web.blacklist.biz.BlackListBo;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.repository.SearchParam;
import com.aspire.ponaadmin.web.repository.Searchor;
import com.aspire.ponaadmin.web.repository.Taxis;
import com.aspire.ponaadmin.web.repository.TaxisParam;
import com.aspire.ponaadmin.web.system.PageSizeConstants;
public class BlackApprovalAction extends BaseAction
{
    private static final JLogger LOG = LoggerFactory.getLogger(BlackImportAction.class);

    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("doPerform() is start");
        }
        String action = this.getParameter(request, "action");
	    if("submitApproval".equals(action)){
	    	return this.submitApproval(mapping, form, request, response);
	    }else if("showList".equals(action)){
	    	return showList(mapping, form, request, response);
	    }else if("approval".equals(action)){
	    	return approval(mapping, form, request, response);
	    }else{
	    	return refuse(mapping, form, request, response);
	    }
    }
    /**
     * 黑名单提交审批
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward submitApproval(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("submitApproval() is start");
		}
		String forward = Constants.FORWARD_COMMON_SUCCESS;
		String[] dealContent = request.getParameterValues("dealContent");
		BlackListBo.getInstance().submitAproval(request, dealContent);
		this.saveMessages(request, "RESOURCE_BLACKLIST_RESULT_001");
		request.setAttribute(Constants.PARA_GOURL, "black.do");
		return mapping.findForward(forward);
	}
    
    /**
     * 黑名单审批发布
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
	public ActionForward approval(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("approval() is start");
		}
		String forward = Constants.FORWARD_COMMON_SUCCESS;
		String[] dealContent = request.getParameterValues("dealContent");
		BlackListBo.getInstance().approval(request, dealContent);
		this.saveMessages(request, "RESOURCE_BLACKLIST_RESULT_002");
		request.setAttribute(Constants.PARA_GOURL, "approval.do?action=showList&aprovalStatus=2");
		return mapping.findForward(forward);
	}
	
	/**
	 * 黑名单审批不通过
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws BOException
	 */
	public ActionForward refuse(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("refuse() is start");
		}
		String forward = Constants.FORWARD_COMMON_SUCCESS;
		this.saveMessages(request, "RESOURCE_BLACKLIST_RESULT_003");
		String[] dealContent = request.getParameterValues("dealContent");
		BlackListBo.getInstance().refuse(request, dealContent);
		request.setAttribute(Constants.PARA_GOURL, "approval.do?action=showList&aprovalStatus=2");
		return mapping.findForward(forward);
	}
	
	/**
	 * 内容黑名单管理-审批发布查询
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws BOException
	 */
	public ActionForward showList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("showList() is start");
		}
		String forward = "showList";
		 // 是否查询内容的类型。
        String name = this.getParameter(request, "name");
        String spName = this.getParameter(request, "spName");
        String contentType = this.getParameter(request, "contentType");
        String contentID = this.getParameter(request, "contentID");
        String pageSize = this.getParameter(request, "pageSize");
        String aprovalStatus = this.getParameter(request, "aprovalStatus");

        if ("".equals(pageSize.trim()))
        {
            pageSize = PageSizeConstants.page_DEFAULT;
        }

        // 设置搜索条件：
        Searchor searchor = new Searchor();

        if (!name.equals(""))
        {
            searchor.getParams()
                    .add(new SearchParam("trg.name",
                                         RepositoryConstants.OP_LIKE,
                                         '%' + SQLUtil.escape(name) + '%'));
        }
        if (!spName.equals(""))
        {
            searchor.getParams()
                    .add(new SearchParam("trg.spName",
                                         RepositoryConstants.OP_LIKE,
                                         '%' + SQLUtil.escape(spName) + '%'));
        }
        if (!contentType.equals(""))
        {
            searchor.getParams()
                    .add(new SearchParam("contentType",
                                         RepositoryConstants.OP_LIKE,
                                         '%' + SQLUtil.escape(contentType) + '%'));
        }

        if (!contentID.equals(""))
        {
            searchor.getParams()
                    .add(new SearchParam("tcb.contentID",
                                         RepositoryConstants.OP_LIKE,
                                         '%' + SQLUtil.escape(contentID) + '%'));
        }

        // 获取分类下的内容
        PageResult page = new PageResult(request);
        // page.setPageSize(6+6);
        page.setPageSize(Integer.parseInt(pageSize));

        BlackListBo.getInstance().queryBlackListOperation(page, searchor, aprovalStatus);
        request.setAttribute("contentType", contentType);
        request.setAttribute("PageResult", page);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("aprovalStatus", aprovalStatus);
		return mapping.findForward(forward);
	}

}

