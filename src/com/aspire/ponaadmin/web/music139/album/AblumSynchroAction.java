package com.aspire.ponaadmin.web.music139.album;

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
import com.aspire.ponaadmin.web.music139.KeywordSynchroBo;
import com.aspire.ponaadmin.web.music139.billboard.BillboardSynchroBo;
import com.aspire.ponaadmin.web.music139.pic.PicSynchroBoImpl;
import com.aspire.ponaadmin.web.music139.tag.MusicTagSynchroBo;

public class AblumSynchroAction extends BaseAction {

	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		String op = request.getParameter("operation");
		AbstractMusic139SynBo bo = getBo(op);
		bo.synchroMusic139Data();
		response.setContentType("text/xml;charset=gbk");
		// try {
		// response.getWriter().write(bo.getR().getMsg());
		// } catch (IOException e) {
		// e.printStackTrace();
		// } finally {
		// try {
		// response.getWriter().flush();
		// response.getWriter().close();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }
		// request.
		Vector msgs = (Vector) request.getAttribute(Constants.REQ_KEY_MESSAGE);
		if (msgs == null) {
			msgs = new Vector();
		}
		msgs.add(bo.getR().getMsg());
		request.setAttribute(Constants.REQ_KEY_MESSAGE, msgs);
		if (bo.getR().isSuccess()) {
			return mapping.findForward(Constants.FORWARD_COMMON_SUCCESS);
		} else {
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
	}

	public AbstractMusic139SynBo getBo(String op) {
		if ("ablum".equals(op)) {
			return new AlbumSynchroBo();
		}
		if ("keyword".equals(op)) {
			return new KeywordSynchroBo();
		}
		if ("billbor".equals(op)) {
			return new BillboardSynchroBo();
		}
		if("musicTag".equals(op)){
			return new MusicTagSynchroBo();
		}
		if("picSysnchro".equals(op)){
			return new PicSynchroBoImpl();
		}
		return null;
	}

}
