package com.aspire.ponaadmin.web.dataexport.appexp;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.ponaadmin.web.BaseAction;

public class AppsExpAction extends BaseAction {

	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		String opertion = request.getParameter("opertion");
		response.setContentType("text/plain;charset=GBK");
		ExportResult r = AppsExpBo.getInstance().calculateTime(opertion,
				new Date());
		try {
			response.getOutputStream().write(r.getMsg().getBytes());
			response.getOutputStream().flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				response.getOutputStream().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
