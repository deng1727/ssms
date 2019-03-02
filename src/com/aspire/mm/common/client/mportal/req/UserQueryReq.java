package com.aspire.mm.common.client.mportal.req;

import org.apache.log4j.Logger;

import com.aspire.mm.common.client.httpsend.Req;
import com.aspire.mm.common.client.httpsend.Resp;
import com.aspire.mm.common.client.mportal.resp.UserQueryResp;

public class UserQueryReq implements Req {
	
	/**
     * 日志引用
     */
    private static Logger logger = Logger.getLogger(UserQueryReq.class);
    
    /**
     * 用户ID
     */
    private String token;

	public Resp getResp() {
		return new UserQueryResp();
	}

	public String toData() {
		if(logger.isDebugEnabled()){
            logger.debug("UserQueryReq   start");
        }
        
        StringBuffer strContent = new StringBuffer();
        strContent.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        strContent.append("<req>");
        strContent.append("<head>");        
        strContent.append("<msgType>userQuery</msgType>");        
        strContent.append("</head>");        
        strContent.append("<body>");        
        strContent.append("<token>" + this.token + "</token>");   //令牌
        strContent.append("</body>");
        strContent.append("</req>");        
        
        if(logger.isDebugEnabled()){
            logger.debug(strContent.toString());
            logger.debug("UserQueryReq   end");
        }
        return strContent.toString();
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
