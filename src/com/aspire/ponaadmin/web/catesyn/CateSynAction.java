package com.aspire.ponaadmin.web.catesyn;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.music139.AbstractMusic139SynBo;

public class CateSynAction extends BaseAction {

	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		response.setContentType("text/xml;charset=gbk");

		Vector msgs = (Vector) request.getAttribute(Constants.REQ_KEY_MESSAGE);
		if (msgs == null) {
			msgs = new Vector();
		}
		request.setAttribute(Constants.REQ_KEY_MESSAGE, msgs);
		CateSynBo bo = new CateSynBo();
		try {
			//remove by aiyan 2012-03-30 
			//bo.doSynCate();
			//msgs.add(bo.sb.toString());
			return mapping.findForward(Constants.FORWARD_COMMON_SUCCESS);
		} catch (Exception e) {
			msgs.add(bo.sb.toString());
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}

	}

}
