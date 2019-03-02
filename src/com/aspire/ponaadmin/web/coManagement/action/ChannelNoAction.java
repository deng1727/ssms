package com.aspire.ponaadmin.web.coManagement.action;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.coManagement.bo.ChannelNoBO;
import com.aspire.ponaadmin.web.coManagement.bo.CooperationBO;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.repository.web.DataImportForm;
import com.aspire.ponaadmin.web.system.PageSizeConstants;

public class ChannelNoAction extends BaseAction {
	/**
	 * 日志引用
	 */
	private static final JLogger logger = LoggerFactory
			.getLogger(CoManagementList.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.aspire.ponaadmin.web.BaseAction#doPerform(org.apache.struts.action
	 * .ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		if (logger.isDebugEnabled()) {
			logger.debug("doPerform()");
		}

		// 从请求中获取操作类型
		String method = this.getParameter(request, "method").trim();

		if ("query".equals(method)) {
			return query(mapping, form, request, response);
		} else if ("importData".equals(method)) {
			return importData(mapping, form, request, response);
		} else {
			String forward = Constants.FORWARD_COMMON_FAILURE;
			this.saveMessagesValue(request, "对不起，您访问的路径不存在");
			return mapping.findForward(forward);
		}

	}

	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		String forward = "list";
		PageResult page = new PageResult(request);
		page.setPageSize(Integer.parseInt(PageSizeConstants.page_DEFAULT));

		ChannelNoBO.getInstance().queryChannelsNoList(page);
		BigDecimal bigDecimal = new BigDecimal(ChannelNoBO.getInstance().queryChannelsNoTotal("2"));
		request.setAttribute("unused", bigDecimal.intValue());
		int used = ChannelNoBO.getInstance().queryChannelsNoTotal("1");
		request.setAttribute("used", used);
		bigDecimal = bigDecimal.add(new BigDecimal(used));
		request.setAttribute("total", bigDecimal.intValue());
		request.setAttribute("PageResult", page);
		return mapping.findForward(forward);
	}

	public ActionForward importData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		DataImportForm iForm = (DataImportForm) form;

		String actionType = "批量渠道号导入";
		boolean actionResult = true;
		String actionDesc = "批量渠道号导入";
		String actionTarget = "批量渠道号导入";
		String ret = "";

		FormFile dataFile = iForm.getDataFile();
		try {
			ret = ChannelNoBO.getInstance().importChannelsNo(request, dataFile);
		} catch (BOException e) {
			logger.error(e);

			// 写操作日志
			actionResult = false;
			actionDesc = "批量渠道号导入出错";
			this.actionLog(request, actionType, actionTarget, actionResult,
					actionDesc);

			this.saveMessagesValue(request, "批量渠道号导入出错");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}

		// 写操作日志
		this.actionLog(request, actionType, actionTarget, actionResult,
				actionDesc);

		this.saveMessagesValue(request, "批量渠道号导入成功，" + ret);
		String forward = Constants.FORWARD_COMMON_SUCCESS;
		request.setAttribute(Constants.PARA_GOURL,
                "channelsNoInfoList.do?method=query");

		return mapping.findForward(forward);
	}

}
