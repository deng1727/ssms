package com.aspire.ponaadmin.web.dissertation;

import java.io.IOException;

import javax.servlet.ServletException;
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


public class DissAction extends BaseAction {
	 /**
     * 日志引用
     */
    private static final JLogger LOG = LoggerFactory.getLogger(DissAction.class) ;

	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		LOG.debug("DissAction()");
		DissQueryForm dissForm = (DissQueryForm) form;
		String params=this.getParameter(request, "param");
		PageResult page=new PageResult(request,20);		
		choose(dissForm,params,page);
		request.setAttribute("PageResult", page);
		 request.setAttribute("dissName", dissForm.getDissName());
		 request.setAttribute("status", dissForm.getStatus().toString());
		 request.setAttribute("tag", dissForm.getTag());
		 request.setAttribute("type", dissForm.getType());		 
		return mapping.getInputForward();
	}
	private void choose(DissQueryForm dissForm,String method,PageResult page) throws BOException{
		if(method.equals("all")){
			DisserBo.getInstance().queryAll(page);
		}
		else{
			DisserBo.getInstance().queryByCondition(dissForm, page);
		}
	}
}
