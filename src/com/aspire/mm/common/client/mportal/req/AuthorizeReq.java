package com.aspire.mm.common.client.mportal.req;

import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.aspire.mm.common.client.httpsend.Req;
import com.aspire.mm.common.client.httpsend.Resp;
import com.aspire.mm.common.client.mportal.resp.AuthorizeResp;

public class AuthorizeReq implements Req {
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map paramMap) {
		this.paramMap = paramMap;
	}

	/**
     * ��־����
     */
    private static Logger logger = Logger.getLogger(AuthorizeReq.class);
    
    /**
     * ����
     */
    private String token;
    
    /**
     * ��Ȩ��ַ
     */
    private String url;
    
    
    /**
     * ��ַ����
     */
    private Map paramMap;    
    

	public Resp getResp() {
		return new AuthorizeResp();
	}

	public String toData() {
		if(logger.isDebugEnabled()){
            logger.debug("UserQueryReq   start");
        }
        
        StringBuffer strContent = new StringBuffer();
        strContent.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        strContent.append("<req>");
        strContent.append("<head>");        
        strContent.append("<msgType>authorize</msgType>");        
        strContent.append("</head>");        
        strContent.append("<body>");                
        strContent.append("<token>" + this.token + "</token>");   //����
        strContent.append("<url>" + this.url + "</url>");   //��ַ       
        strContent.append("<params>" + this.getParams() + "</params>");   //����        
        strContent.append("</body>");
        strContent.append("</req>");        
        
        if(logger.isDebugEnabled()){
            logger.debug(strContent.toString());
            logger.debug("UserQueryReq   end");
        }
        return strContent.toString();
	}
	
	private String getParams() {
		StringBuffer stringBuffer = new StringBuffer();
		if (paramMap != null) {
			for (Iterator it = paramMap.entrySet().iterator();  it.hasNext();) {
				Map.Entry entry = (Map.Entry) it.next(); 
			    Object key = entry.getKey(); 
			    Object value = entry.getValue(); 
			    stringBuffer.append("<param name=\"" + (String)key + "\" value=\"" + (String)value + "\"/>"); 			    
			} 
		}
		return stringBuffer.toString();
	}
}
