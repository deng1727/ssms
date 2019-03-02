/**
 * SSMS
 * com.aspire.ponaadmin.web.category ManUpdateCategoryAction.java
 * Apr 26, 2010
 * @author tungke
 * @version 1.0
 *
 */
package com.aspire.ponaadmin.web.category;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.BaseAction;

/**
 * @author tungke
 *
 */
public class ManUpdateCategoryAction extends BaseAction
{
	 /**
     * 日志引用
     */
    private static final JLogger LOG = LoggerFactory.getLogger(ManUpdateCategoryAction.class) ;

	/* (non-Javadoc)
	 * @see com.aspire.ponaadmin.web.BaseAction#doPerform(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws BOException
	{
		CategoryRuleBO categoryRuleBO = CategoryRuleBO.getInstance();
		List categoryRuleNames = categoryRuleBO.getAllCategoryRuleName();
		
		 LOG.debug("doPerform()");
		   request.setAttribute("categoryRuleNames",categoryRuleNames);
		   
	        return mapping.findForward("showCateResult");
	}

}
