package com.aspire.dotcard.syncAndroid.unifiedPackage.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.syncAndroid.common.Constant;
import com.aspire.dotcard.syncAndroid.ppms.APPInfoVO;
import com.aspire.dotcard.syncAndroid.ppms.PPMSBO;
import com.aspire.dotcard.syncAndroid.ppms.PPMSReceiveBO;
import com.aspire.dotcard.syncAndroid.ppms.XMLParser;
import com.aspire.dotcard.syncAndroid.unifiedPackage.bo.UnifiedBo;
import com.aspire.dotcard.syncAndroid.unifiedPackage.vo.GoodsCenterMessagesVo;
import com.aspire.ponaadmin.web.BaseServlet;

public class UnifiedPackageMessagesServlet extends BaseServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8206280103244685737L;
	/**
	 * 日志引用
	 */
	private static final JLogger LOG = LoggerFactory
			.getLogger(UnifiedPackageMessagesServlet.class);

	
	private static final String SUCCESS = "{\"Result\":\"000\",\"Msg\":\"写入消息表成功\"}";
	private static final String FAIL = "{\"Result\":\"999\",\"Msg\":\"写入消息表失败\"}";
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		process(req,resp);
	}

	public void process(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<GoodsCenterMessagesVo> vos =  getRequestMessages(request, response);
			UnifiedBo.getInstance().HandleGoodsCenterMessagesVos(vos);
			flushJsonStr(SUCCESS, response);
		} catch (Exception e) {
			flushJsonStr(FAIL, response);
		}
		return ;
	}

	private List<GoodsCenterMessagesVo> getRequestMessages(
			HttpServletRequest request, HttpServletResponse response)
			throws JSONException, IOException {
		JSONObject paras = getJsonFromRequest(request, response);
		JSONArray messages = paras.getJSONArray("message");
		List<GoodsCenterMessagesVo> vos = new ArrayList<GoodsCenterMessagesVo>();
		GoodsCenterMessagesVo vo = null;

		for (int i = 0; i < messages.size(); i++) {
			JSONObject message = messages.getJSONObject(i);
			vo = new GoodsCenterMessagesVo();
			vo.setAction(message.getInt("action"));
			vo.setAppId(message.getString("appid"));
			vo.setContentId(message.getString("contentid"));
			vo.setChangeTime(message.getString("changeTime"));
			vo.setSendTime(message.getString("sendTime"));
			vos.add(vo);
		}
		return vos;
	}
}
