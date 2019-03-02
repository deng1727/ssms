package com.aspire.dotcard.basevideosync.client;

import java.io.IOException;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

public class HttpSender {

	private static final Logger logger = Logger.getLogger(HttpSender.class);
	private static int connectTimeout = 300000;
	private static int timeout = 300000;

	/**
	 * ��������
	 * 
	 * @return
	 */
	public static JSONObject sendRequest(String basurl,NameValuePair[] nameValuePairs) throws Exception {
		 // ����HTTP����
        HttpClient httpclient = new HttpClient();
        PostMethod postMethod = new PostMethod(basurl);
        JSONObject jsonResult = null;
        if (nameValuePairs != null) {
            postMethod.addParameters(nameValuePairs);
            if(logger.isDebugEnabled()){
            	StringBuffer sb = new StringBuffer("");
            	for(int i = 0;i < nameValuePairs.length;i++){
            		if(i != 0){
            			sb.append("&");
            		}
            		sb.append(nameValuePairs[i].getName()+"="+nameValuePairs[i].getValue());
            	}
            	logger.debug("api�����url�� " + basurl + "?" + sb.toString());
            	
            }
        }

        // ��������
        String strResponse = null;
        try {
        	httpclient.setConnectionTimeout(connectTimeout);
        	httpclient.setTimeout(timeout);
            httpclient.executeMethod(postMethod);
            strResponse = IOUtils.toString(postMethod.getResponseBodyAsStream(),"UTF-8" );
            jsonResult = JSONObject.fromObject(strResponse);
            return jsonResult;
        } catch (HttpException e) {
            logger.error("�� URLʧ�ܣ�URL��"  + basurl);
            throw new Exception("�� URLʧ�ܣ�URL��"  + basurl);
        } catch (IOException e) {
            logger.error("�� URLʧ�ܣ�URL��"  + basurl);
            throw new Exception("�� URLʧ�ܣ�URL��"  + basurl);
        } finally {
            postMethod.releaseConnection();            
        }
	}

}
