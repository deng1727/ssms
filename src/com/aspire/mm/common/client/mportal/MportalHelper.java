
package com.aspire.mm.common.client.mportal;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.log4j.Logger;

import com.aspire.mm.common.client.httpsend.HttpSender;
import com.aspire.mm.common.client.mportal.req.AuthorizeReq;
import com.aspire.mm.common.client.mportal.req.OperateLogReq;
import com.aspire.mm.common.client.mportal.req.UserQueryReq;
import com.aspire.mm.common.client.mportal.resp.AuthorizeResp;
import com.aspire.mm.common.client.mportal.resp.OperateLogResp;
import com.aspire.mm.common.client.mportal.resp.UserQueryResp;

public class MportalHelper
{

    private static Logger logger = Logger.getLogger(MportalHelper.class);

    public static String authorizeUrl = "";    
    
    public static String loginUrl = "";
    
    public static String authErrorUrl = "";    
    
    protected static XMLConfiguration xmlConfiguration ;
    
    public static void init(String confFileName) throws Exception  {
        try {
            xmlConfiguration = new XMLConfiguration(confFileName);
            authorizeUrl = xmlConfiguration.getString("authorizeUrl");
            loginUrl = xmlConfiguration.getString("loginUrl");
            authErrorUrl = xmlConfiguration.getString("authErrorUrl");            
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
    
    public static String getAuthErrorUrl() {
    	return authErrorUrl;
    }    

    /**
     * 访问鉴权.
     * @param token
     * @param url
     * @param paramMap
     * @return
     */
    public static Map authorize(String token, String url, Map paramMap)
    {
        Map result = new HashMap();
        if (logger.isDebugEnabled()) {
            logger.debug("authorize");
            logger.debug("token:" + token);
            logger.debug("url:" + url);            
        }

        AuthorizeReq req = new AuthorizeReq();
        req.setToken(token);
        req.setUrl(url);
        req.setParamMap(paramMap);

        try {
            AuthorizeResp resp = ( AuthorizeResp ) HttpSender.sendRequest(getAuthorizeUrl(), req);
            result.put("hRet", resp.getHRet());
        }
        catch (Exception e) {
            logger.error("访问鉴权异常：" + e);
            result.put("hRet", "-1");
        }
        return result;
    }

    /**
     * 操作日志.
     * @param token
     * @param url
     * @param paramMap
     * @return
     */
    public static Map info(String token, String logType, String entityId, String entityName, String result, Map otherInfoMap)
    {
        Map resultMap = new HashMap();
        if (logger.isDebugEnabled()) {
            logger.debug("authorize");
            logger.debug("token:" + token);
            logger.debug("logType:" + logType);
            logger.debug("entityId:" + entityId);            
            logger.debug("entityName:" + entityName);            
            logger.debug("result:" + result);            
        }

        OperateLogReq req = new OperateLogReq();
        req.setToken(token);
        req.setLogType(logType);
        req.setEntityId(entityId);
        req.setEntityName(entityName);
        req.setResult(result);
        req.setParamMap(otherInfoMap);

        try {
            OperateLogResp resp = ( OperateLogResp ) HttpSender.sendRequest(getAuthorizeUrl(), req);
            resultMap.put("hRet", resp.getHRet());
        }
        catch (Exception e) {
            logger.error("访问鉴权异常：" + e);
            resultMap.put("hRet", "-1");
        }
        return resultMap;
    }
    
    

    /**
     * 查询用户信息.
     * @param token
     * @return
     */
    public static Map userQuery(String token)
    {

        Map result = new HashMap();
        String url = getAuthorizeUrl();

        if (logger.isDebugEnabled())
        {
            logger.debug("userQuery");
            logger.debug("token:" + token);
        }

        UserQueryReq req = new UserQueryReq();
        req.setToken(token);

        try
        {
            UserQueryResp resp = ( UserQueryResp ) HttpSender.sendRequest(url, req);
            result.put("hRet", resp.getHRet());
            result.put("staff", resp.getStaff());
        }
        catch (Exception e)
        {
            logger.error("查询个人资料异常：" + e);
        }

        return result;
    }

}
