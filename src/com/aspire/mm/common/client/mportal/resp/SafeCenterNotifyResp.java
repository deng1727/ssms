package com.aspire.mm.common.client.mportal.resp;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.aspire.mm.common.client.httpsend.Resp;

public class SafeCenterNotifyResp implements Resp{

	/**
     * ��־����
     */
    private static Logger logger = Logger.getLogger(SafeCenterNotifyResp.class);
    
    /**
     * ��������(����Դ����):1-������,2-����,3-��������,4-���
     */
	private String pushtype;
	/**
	 * ͬ�����
	 */
	private String update;
	
	private String asXml;
    
	public String getAsXml() {
		return asXml;
	}

	public void setAsXml(String asXml) {
		this.asXml = asXml;
	}

	public String getPushtype() {
		return pushtype;
	}

	public void setPushtype(String pushtype) {
		this.pushtype = pushtype;
	}

	public String getUpdate() {
		return update;
	}

	public void setUpdate(String update) {
		this.update = update;
	}

	public String toString()
    {
        return this.asXml;
    }
	
	public Resp praseResp(Document doc) {
		if(logger.isDebugEnabled()){
            logger.debug("SafeCenterNotifyResp   start");
        }
		this.setAsXml(doc.asXML());
		Element root = doc.getRootElement();
		Element msgheader = root.element("msgheader");
		this.setPushtype(msgheader.element("pushtype").getText());
		Element msgbody = root.element("msgbody");
		this.setUpdate(msgbody.element("update").getText());

		if(logger.isDebugEnabled()){
            logger.debug("pushtype = " + this.getPushtype());
            logger.debug("update = " + this.getUpdate());
        }
		
		if(logger.isDebugEnabled()){
            logger.debug("SafeCenterNotifyResp   end");
        }
		return this;
	}

}
