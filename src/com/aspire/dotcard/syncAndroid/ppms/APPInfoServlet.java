package com.aspire.dotcard.syncAndroid.ppms;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.syncAndroid.common.Constant;

/**
 * @author aiyan
 * 
 */
public class APPInfoServlet extends HttpServlet {

	/**
	 * 日志引用
	 */
	private static final JLogger LOG = LoggerFactory
			.getLogger(APPInfoServlet.class);
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		process(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		process(request, response);
	}

	public void init() throws ServletException {
		LOG.info("APPInfoSevlet started!");
	}

	public void process(HttpServletRequest request, HttpServletResponse response) {
		APPInfoVO vo = null;
		try {
			vo = XMLParser.getAPPInfoVO(request.getInputStream());
			PPMSBO.getInstance().addAPPInfo(vo);
			new PPMSReceiveBO(vo).execute();//这里是接收ppms的数据，存储到t_a_ppms_receive_change表中。
			handlePost(response,vo,Constant.MESSAGE_HANDLE_STATUS_SUCC);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error("response.getWriter()出现错误！", e);
			handlePost(response,vo,Constant.MESSAGE_HANDLE_STATUS_FAIL);
			
		}
	}



	private void handlePost(HttpServletResponse response,APPInfoVO vo,int status){
		// TODO Auto-generated method stub
		response.setContentType("text/xml");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String responseStr = getResponseStr(vo.getTransactionID(), vo
					.getVersion(), status);
			out.print(responseStr);
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error("response出错",e);
		}

	}


	private String getResponseStr(String transactionID, String version,
			int rspCode) {
		java.text.SimpleDateFormat sd = new java.text.SimpleDateFormat(
				"yyyyMMddHHmmss");
		sd.format(new Date());
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<APPInfoNotifyResponse>");
		sb.append("<Head>");
		sb.append("<ActionCode>1</ActionCode>");
		sb.append("<TransactionID>").append(transactionID).append(
				"</TransactionID>");
		sb.append("<Version>").append(version).append("</Version>");
		sb.append("<ProcessTime>").append(sd.format(new Date())).append(
				"</ProcessTime>");
		sb.append("</Head>");
		sb.append("<Body>");
		sb.append("<RspCode>").append(rspCode).append("</RspCode>");
		sb.append("<RspDesc><![CDATA[").append(rspCode == 0 ? "接受成功" : "接受失败")
				.append("]]></RspDesc>");
		sb.append("</Body>");
		sb.append("</APPInfoNotifyResponse>");
		return sb.toString();
	}

}
