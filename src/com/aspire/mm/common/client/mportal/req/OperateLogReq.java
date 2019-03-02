package com.aspire.mm.common.client.mportal.req;

import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.aspire.mm.common.client.httpsend.Req;
import com.aspire.mm.common.client.httpsend.Resp;
import com.aspire.mm.common.client.mportal.resp.OperateLogResp;

public class OperateLogReq implements Req {
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Map getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map paramMap) {
		this.paramMap = paramMap;
	}

	/**
     * 日志引用
     */
    private static Logger logger = Logger.getLogger(OperateLogReq.class);
    
    /**
     * 令牌
     */
    private String token;
    
    /***操作日志类型.*/
    private String logType;
    
    /***实体ID.*/
    private String entityId;
    
    /***实体名称.*/
    private String entityName;
    
    /***客户端IP.*/
    private String clientIp;    
    
    /***操作结果.*/
    private String result;    
   
    /**
     * 地址参数
     */
    private Map paramMap;    
    

	public Resp getResp() {
		return new OperateLogResp();
	}

	public String toData() {
		if(logger.isDebugEnabled()){
            logger.debug("UserQueryReq   start");
        }
        
        StringBuffer strContent = new StringBuffer();
        strContent.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        strContent.append("<req>");
        strContent.append("<head>");        
        strContent.append("<msgType>operateLog</msgType>");        
        strContent.append("</head>");        
        strContent.append("<body>");                
        strContent.append("<logType>" + this.logType + "</logType>");   //日志类型
        strContent.append("<token>" + this.token + "</token>");   //令牌
        strContent.append("<entityId>" + this.entityId + "</entityId>");  //实体ID       
        strContent.append("<entityName>" + this.entityName + "</entityName>");  //实体名称        
        strContent.append("<clientIp>" + this.clientIp + "</clientIp>");  //IP
        strContent.append("<result>" + this.result + "</result>");  // 操作结果        
        strContent.append("<params>" + this.getParams() + "</params>");   //参数        
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
