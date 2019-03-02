package com.aspire.ponaadmin.web.coManagement.action;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

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
import com.aspire.ponaadmin.web.coManagement.bo.ChannelListBO;
import com.aspire.ponaadmin.web.coManagement.bo.ChannelNoBO;
import com.aspire.ponaadmin.web.coManagement.vo.ChannelListVO;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.repository.web.DataImportForm;
import com.aspire.ponaadmin.web.system.PageSizeConstants;

public class ChannelListAction extends BaseAction {
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
		} else if ("detail".equals(method)) {
			return detail(mapping, form, request, response);
		} else {
			String forward = Constants.FORWARD_COMMON_FAILURE;
			this.saveMessagesValue(request, "对不起，您访问的路径不存在");
			return mapping.findForward(forward);
		}

	}

	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {

		Map<String, Object> map = new HashMap<String, Object>();
		String channelId = request.getParameter("channelId");
		map.put("channelId", channelId);
		
		String channelName = request.getParameter("channelName");
		map.put("channelName", channelName);
		
		String distributorId = request.getParameter("distributorId");
		map.put("distributorId", distributorId);
		
		String distributorName = request.getParameter("distributorName");
		map.put("distributorName", distributorName);
		
		String channelType = request.getParameter("channelType");
		map.put("channelType", channelType);
		
		String forward = "list";
		PageResult page = new PageResult(request);
		page.setPageSize(Integer.parseInt(PageSizeConstants.page_DEFAULT));
		ChannelListBO.getInstance().queryChannelList(page, map);
		
		request.setAttribute("channelId", channelId);
		request.setAttribute("channelName", channelName);
		request.setAttribute("distributorId", distributorId);
		request.setAttribute("distributorName", distributorName);
		request.setAttribute("channelType", channelType);
		request.setAttribute("PageResult", page);
		return mapping.findForward(forward);
	}

	public ActionForward detail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		String forward = "detail";
		String channelId = this.getParameter(request, "channelId")
				.trim();
		ChannelListVO vo = new ChannelListVO();
		vo = ChannelListBO.getInstance().getChannelDetail(channelId);
		request.setAttribute("channelContent", vo);
		
		return mapping.findForward(forward);
	}

}