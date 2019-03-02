package com.aspire.ponaadmin.web.auditManagement.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.auditManagement.bo.AuditDetailsBO;
import com.aspire.ponaadmin.web.auditManagement.bo.ChannelsInfoBO;
import com.aspire.ponaadmin.web.channeladmin.bo.OpenOperationChannelBo;
import com.aspire.ponaadmin.web.channeladmin.vo.OpenOperationChannelVo;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.system.PageSizeConstants;

public class AuditAction extends BaseAction {
	private static final JLogger LOG = LoggerFactory
			.getLogger(AuditAction.class);

	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		LOG.debug("doPerform()");
		String method = this.getParameter(request, "method");
		try {
			if ("queryList".equals(method)) {
				return list(mapping, form, request, response);
			} else if("details".equals(method)){
				return detail(mapping, form, request, response);
			}else if("audit".equals(method)){
				return auditItem(mapping, form, request, response);
			}else if("toAudit".equals(method)){
				return audit(mapping, form, request, response);
			}else {
				String forward = Constants.FORWARD_COMMON_FAILURE;
				this.saveMessagesValue(request, "对不起，您访问的路径不存在");
				return mapping.findForward(forward);
			}
		} catch (DAOException e) {
			LOG.debug("查询数据失败！", e);
			String forward = Constants.FORWARD_COMMON_FAILURE;
			this.saveMessagesValue(request, "查询数据失败！");
			return mapping.findForward(forward);
		}
	}

	private ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws DAOException {
		String forward = "queryList";
		PageResult page = new PageResult(request);
        page.setPageSize(Integer.parseInt(PageSizeConstants.page_DEFAULT));
        ChannelsInfoBO.getInstance().channelsInfoList(page);
        request.setAttribute("PageResult", page);
		return mapping.findForward(forward);
	}
	
	private ActionForward detail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws DAOException {
		String categoryId = this.getParameter(request, "categoryId");
		String forward = "detail";
		PageResult page = new PageResult(request);
        page.setPageSize(Integer.parseInt(PageSizeConstants.page_DEFAULT));
        AuditDetailsBO.getInstance().auditDetails(page,categoryId);
        request.setAttribute("PageResult", page);
        request.setAttribute("categoryId", categoryId);
		return mapping.findForward(forward);
	}
	
	private ActionForward auditItem(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws DAOException {
		String[] categoryId = request.getParameterValues("checkbox");
		String flag = this.getParameter(request, "flag");
        AuditDetailsBO.getInstance().toAudit(categoryId,flag);
		String forward = "detail";
		PageResult page = new PageResult(request);
        page.setPageSize(Integer.parseInt(PageSizeConstants.page_DEFAULT));
        AuditDetailsBO.getInstance().auditDetails(page,categoryId[0].split("/")[0]);
        request.setAttribute("PageResult", page);
        request.setAttribute("categoryId", categoryId[0].split("/")[0]);
		return mapping.findForward(forward);
	}
	
	private ActionForward audit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws DAOException {
		String[] categoryId = request.getParameterValues("checkbox");
		String flag = this.getParameter(request, "flag");
		ChannelsInfoBO.getInstance().toAudit(categoryId,flag);
		String forward = "queryList";
		PageResult page = new PageResult(request);
        page.setPageSize(Integer.parseInt(PageSizeConstants.page_DEFAULT));
        ChannelsInfoBO.getInstance().channelsInfoList(page);
        request.setAttribute("PageResult", page);
		return mapping.findForward(forward);
	}

}
