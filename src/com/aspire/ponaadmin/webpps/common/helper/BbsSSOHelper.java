package com.aspire.ponaadmin.webpps.common.helper;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.log4j.Logger;

public class BbsSSOHelper {

	private static Logger logger = Logger.getLogger(BbsSSOHelper.class);

    public static String authorizeUrl = "";
    
    public static String queryChannelsAPUrl = "";
    public static String loginUrl = "";
    public static String loginENUrl = "";
    
    public static String authErrorUrl = "";
    
    public static String BPID = "";
    
    protected static XMLConfiguration xmlConfiguration ;
    
    public static void init(String confFileName) throws Exception  {
        try {
            xmlConfiguration = new XMLConfiguration(confFileName);
            authorizeUrl = xmlConfiguration.getString("authorizeUrl");
            loginUrl = xmlConfiguration.getString("loginUrl");
            loginENUrl = xmlConfiguration.getString("loginENUrl");
            authErrorUrl = xmlConfiguration.getString("authErrorUrl");
            BPID = xmlConfiguration.getString("BPID");
            queryChannelsAPUrl = xmlConfiguration.getString("queryChannelsAPUrl");
        }
        catch (ConfigurationException e) {
            logger.error("配置文件[" + confFileName + "]不存在");
            throw new Exception("配置文件[" + confFileName + "]不存在");            
        }
    }
    
    public static String getAuthorizeUrl() {
    	return authorizeUrl;
    }
    

    public static String getLoginUrl() {
    	return  loginUrl;
    }
    
    public static String getLoginENUrl() {
    	return  loginENUrl;
    }
    
    public static String getAuthErrorUrl() {
    	return authErrorUrl;
    }
    
    public static String getBPID() {
    	return BPID;
    }
    
    public static String getQueryChannelsAPUrl(){
    	return queryChannelsAPUrl;
    }
}
