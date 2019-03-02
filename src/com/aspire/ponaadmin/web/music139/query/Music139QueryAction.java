package com.aspire.ponaadmin.web.music139.query;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.music139.Music139Bo;

public class Music139QueryAction extends BaseAction {

	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		response.setContentType("text/xml;charset=GBK");
		try {
			request.setCharacterEncoding("utf-8");
			String musicname = request.getParameter("musicname");
			String singer = request.getParameter("singer");

			String temp = "";
			temp = Music139Bo.queryMusicData(musicname, singer, null);
			response.getWriter().write(temp);
			response.getWriter().flush();
		} catch (UnsupportedEncodingException e) {
		} catch (IOException e) {
			throw new BOException("Õ¯¬Á“Ï≥£");
		} finally {
			try {
				response.getWriter().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
