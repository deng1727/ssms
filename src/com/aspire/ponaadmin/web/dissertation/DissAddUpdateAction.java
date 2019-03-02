package com.aspire.ponaadmin.web.dissertation;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.aspire.common.exception.BOException;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;

public class DissAddUpdateAction extends BaseAction {
	
	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		// TODO 自动生成方法存根
		String para=this.getParameter(request, "param");
		DissAddForm addForm=(DissAddForm)form;
		String message=null;
		if(para.equals("add")){
		   DisserBo.getInstance().add(addForm);
		   request.setAttribute("goURL", "../dissertation/queryDiss.do?param=all");
			message="增加专题记录成功!";
		}
		else if(para.equals("del")){
			String id=this.getParameter(request, "dissId");
		    DisserBo.getInstance().del(id);
		    request.setAttribute("goURL", "../dissertation/queryDiss.do?param=all");
			message="删除专题记录成功!";
		}else if(para.equals("detail")){
			String id=this.getParameter(request, "dissId");
			DisserBo.getInstance().getDiss(id,addForm);
			request.setAttribute("src", addForm.getUploadFile(0).getPicUrl());
			request.setAttribute("msg", addForm.getMsg());
			return mapping.findForward("detail");
		}else if(para.equals("update")){
			DisserBo.getInstance().update(addForm);
			 request.setAttribute("goURL", "../dissertation/queryDiss.do?param=all");
			message="更新专题记录成功!";
		}
		
		Vector msgs = (Vector) request.getAttribute(Constants.REQ_KEY_MESSAGE);
		if (msgs == null) {
			msgs = new Vector();
		}
		msgs.add(message);
		request.setAttribute(Constants.REQ_KEY_MESSAGE, msgs);
		return mapping.findForward("commonSuccess");
	}


}
