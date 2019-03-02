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
     * ��־����
     */
    private static Logger logger = Logger.getLogger(OperateLogReq.class);
    
    /**
     * ����
     */
    private String token;
    
    /***������־����.*/
    private String logType;
    
    /***ʵ��ID.*/
    private String entityId;
    
    /***ʵ������.*/
    private String entityName;
    
    /***�ͻ���IP.*/
    private String clientIp;    
    
    /***�������.*/
    private String result;    
   
    /**
     * ��ַ����
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
        strContent.append("<logType>" + this.logType + "</logType>");   //��־����
        strContent.append("<token>" + this.token + "</token>");   //����
        strContent.append("<entityId>" + this.entityId + "</entityId>");  //ʵ��ID       
        strContent.append("<entityName>" + this.entityName + "</entityName>");  //ʵ������        
        strContent.append("<clientIp>" + this.clientIp + "</clientIp>");  //IP
        strContent.append("<result>" + this.result + "</result>");  // �������        
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
