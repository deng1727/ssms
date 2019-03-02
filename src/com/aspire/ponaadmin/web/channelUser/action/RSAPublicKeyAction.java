package com.aspire.ponaadmin.web.channelUser.action;

import java.io.PrintWriter;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.aspire.common.exception.BOException;
import com.aspire.common.util.RSAConfiguration;





public class RSAPublicKeyAction  extends DispatchAction {

	private static final long serialVersionUID = 6645705977899186931L;
	private static final Logger logger = Logger
			.getLogger(RSAPublicKeyAction.class);

	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setHeader("Content-Type", "text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		HttpSession session = request.getSession();
		try {
			logger.debug("开始获取公钥");
			KeyPair keyPair = (KeyPair) session.getAttribute("keyPair");
			if (keyPair == null) {
				keyPair = RSAConfiguration.getKeyPair();
				session.setAttribute("keyPair", keyPair);
			}
			RSAPublicKey rsap = (RSAPublicKey) keyPair.getPublic();
			writer.print("{\"ret\":0,\"key\":\""
					+ rsap.getModulus().toString(16) + "\"}");
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			logger.debug("获取公钥结束");
			return null;
		} catch (Exception e) {
			logger.error(e);
			writer.print("{\"ret\":0}");
			return null;
		}
	}


}
