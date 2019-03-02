package com.aspire.mm.common.client.mportal.resp;

import org.dom4j.Document;
import org.dom4j.Element;
import com.aspire.mm.common.client.httpsend.Resp;


import org.apache.log4j.Logger;



public class OperateLogResp implements Resp {
	
	/**
     * 日志引用
     */
    private static Logger logger = Logger.getLogger(OperateLogResp.class);
    
    private String hRet;
    
	public Resp praseResp(Document doc) {
		if(logger.isDebugEnabled()){
            logger.debug("OperateLogResp   start");
        }
		
		Element rootE = doc.getRootElement();
		this.setHRet(rootE.element("hRet").getText());

		if(logger.isDebugEnabled()){
            logger.debug("hRet = " + this.getHRet());
        }
		
		if(logger.isDebugEnabled()){
            logger.debug("OperateLogResp   end");
        }
		
		return this;
	}
	
	public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("OperateLogResp[");
        sb.append("hRet:").append(hRet).append("]");
        return sb.toString();
    }

	public String getHRet() {
		return hRet;
	}

	public void setHRet(String ret) {
		hRet = ret;
	}

}
