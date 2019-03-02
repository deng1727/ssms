package com.aspire.ponaadmin.web.lockLocation.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.constant.ResourceConstants;
import com.aspire.ponaadmin.web.lockLocation.bo.LockLocationBO;

public class ContentListAction extends BaseAction {
	private static JLogger LOG = LoggerFactory
			.getLogger(ContentListAction.class);

	@Override
	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		LOG.debug("ContentListAction()");
		String forward = null;
		try {
			String categoryId = this.getParameter(request, "categoryId");
			String name = this.getParameter(request, "name");
			String contentId = this.getParameter(request, "contentId");
			String lockNums = this.getParameter(request, "lockNums");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("categoryId",categoryId);
			map.put("contentId",contentId);
			map.put("name", name);
			if(lockNums==null){
				lockNums = LockLocationBO.getInstance().getLockNums(map);
			}
			PageResult page = new PageResult(request);
			LockLocationBO.getInstance().queryContentList(page, map);
			request.setAttribute("lockNums", lockNums);
			request.setAttribute("categoryId", categoryId);
			request.setAttribute("PageResult", page);
			forward = "contentList";
		} catch (Exception ex) {
			LOG.error(ex);
			forward = Constants.FORWARD_COMMON_FAILURE;
			this.saveMessages(request, ResourceConstants.WEB_ERR_SYSTEM);
		}

		return mapping.findForward(forward);

	}

}
