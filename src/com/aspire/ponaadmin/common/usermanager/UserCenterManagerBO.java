/**
 * com.aspire.ponaadmin.common.usermanager UserCenterManagerBO.java
 * Jul 27, 2011
 *<p>
 * Copyright (c) 2003-2011 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights
 * Reserved
 * </p>
 * @author dongke
 * @version 1.0
 *
 */
package com.aspire.ponaadmin.common.usermanager;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


import com.aspire.common.config.ConfigFactory;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * @author tungke
 *
 */
public class UserCenterManagerBO
{
	/**
     * 日志引用
     */
    protected static JLogger logger = LoggerFactory.getLogger(UserCenterManagerBO.class);

	/**
	 * singleton模式的实例
	 */
    private static UserCenterManagerBO instance = new UserCenterManagerBO();

    /**
     * 构造方法，由singleton模式调用
     */
    protected UserCenterManagerBO ()
    {
    }

	/**
	 * 获取实例
	 * @return 实例
	 */
    public static UserCenterManagerBO getInstance()
    {
        return instance;
    }
    
    
    /**
     * 用户登录方法
     * @param userID String,用户ID（帐号）
     * @param pwd String,密码
     * @return int，0：成功，1002：用户不存在，1003：密码错误，1：系统原因失败
     * @throws BOException
     */
    public int userCenterLogin(String userID) throws BOException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("login("+userID);
        }
        if((userID == null) || userID.trim().equals("") )
        {
            throw new BOException("Invalid parameter.", UserManagerConstant.INVALID_PARA);
        }
        int result = UserManagerConstant.SUCC;
        try
        {
            UserVO user = UserDAO.getUserByID(userID);
            if(user == null)
            {
                //找不到用户
                result = UserManagerConstant.USER_NOT_EXISTED;
            }
            else
            {//有用户名，登录成功
            	
                //密码是采用加密形式存放的。
//                if(!user.getPassword().toUpperCase().equals(pwd.toUpperCase()))
//                {
//                    //密码不对
//                    result = UserManagerConstant.INVALID_PWD;
//                }
            }
        }
        catch(Exception e)
        {
            logger.error("error during login.", e);
            result = UserManagerConstant.FAIL;
        }
        return result;
    }
    
    
    
    /**
     * 
     *@desc 校验登录
     *@author dongke
     *Jul 27, 2011
     * @param tokenId
     * @return throws Exception
     * @throws BOException 
     * @throws Exception
     */
    public HashMap checkLogin(String tokenId) throws BOException {
    	String authorizeUrl = ConfigFactory.getSystemConfig()
        .getModuleConfig("ssms")
        .getItemValue("authorizeUrl");;
    	String sendData = this.getSendData(tokenId);
    	HashMap hm;
		try
		{
			hm = this.sendRequest(authorizeUrl,sendData,"UTF-8");
		} catch (Exception e)
		{
			e.printStackTrace();
			throw new BOException("登录校验失败",e);
		}
    	return hm;
    }
    
    /**
     * 发送方法
     * 
     * @param basurl
     * @param req
     * @return
     * @throws Exception
     */
    private   HashMap sendRequest(String basurl, String msg, String charSet) throws Exception
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("send(" + basurl + "," + msg + ")");
        }
        //String msg = req.toData();
        logger.debug("send msg:" + msg);
        HttpURLConnection httpurlconnection = null;
        HashMap hm = null;
        try
        {
            URL url = new URL(basurl);
            httpurlconnection = ( HttpURLConnection ) url.openConnection();
            // 设置允许output
            httpurlconnection.setDoOutput(true);
            // 设置为post方式
            httpurlconnection.setRequestMethod("POST");
            // 模拟成ie
            httpurlconnection.setRequestProperty("user-agent", "mozilla/4.7 [en] (win98; i)");
            // post信息
            OutputStream os = httpurlconnection.getOutputStream();
            if (StringUtils.isEmpty(charSet))
            {
                charSet = "utf-8";
            }
            if (logger.isDebugEnabled())
            {
                logger.debug("bas data "+charSet+" encode write ");
            }
           
            os.write(msg.getBytes(charSet));
            os.close();
            if (logger.isDebugEnabled())
            {
                logger.debug("bss data send succeed ");
                logger.debug("ret code:" + httpurlconnection.getResponseCode());
            }
            // 先获取XML数据包
            SAXReader  sax = new SAXReader ();
            Document doc = sax.read(httpurlconnection.getInputStream());
//            File file = new File("C:\\resp.xml"); 
//            Document doc = sax.read(file);
            logger.debug("response xml : \n" + doc.asXML());
           //TODO
            hm = this.getReceiveData(doc);
            if (logger.isDebugEnabled())
            {
                logger.debug("receive data:" +"");
            }
        }
        catch (Exception e)
        {
            logger.error(e);
            throw new Exception("send message to http url error.", e);
        }
        finally
        {
            if (httpurlconnection != null)
            {
                httpurlconnection.disconnect();
            }
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("send() end.......");
        }
        return hm;
    }
    
    /**
     * 
     *@desc 获取发送数据包
     *@author dongke
     *Jul 27, 2011
     * @param token
     * @return
     */
    private String getSendData(String token){
    	
    	StringBuffer strContent = new StringBuffer();
        strContent.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        strContent.append("<req>");
        strContent.append("<head>");        
        strContent.append("<msgType>userQuery</msgType>");        
        strContent.append("</head>");        
        strContent.append("<body>");        
        strContent.append("<token>" + token + "</token>");   //令牌
        strContent.append("</body>");
        strContent.append("</req>"); 
       return  strContent.toString();
    }
    
    /**
     * 
     *@desc 获取接收的数据包
     *@author dongke
     *Jul 27, 2011
     * @param doc
     * @return
     */
    private HashMap getReceiveData(Document doc){
    	Element rootE = doc.getRootElement();
		String hRet = rootE.element("hRet").getText();
		HashMap hm = null;
		if("0".equals(hRet)){
			hm = new HashMap();
			hm.put("hRet",hRet);
			Element data = rootE.element("data").element("staff");	
			hm.put("userName",data.element("loginName").getText());
			
		}else{
			
		}
		
    	return hm;
    }
}
