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
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(UserCenterManagerBO.class);

	/**
	 * singletonģʽ��ʵ��
	 */
    private static UserCenterManagerBO instance = new UserCenterManagerBO();

    /**
     * ���췽������singletonģʽ����
     */
    protected UserCenterManagerBO ()
    {
    }

	/**
	 * ��ȡʵ��
	 * @return ʵ��
	 */
    public static UserCenterManagerBO getInstance()
    {
        return instance;
    }
    
    
    /**
     * �û���¼����
     * @param userID String,�û�ID���ʺţ�
     * @param pwd String,����
     * @return int��0���ɹ���1002���û������ڣ�1003���������1��ϵͳԭ��ʧ��
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
                //�Ҳ����û�
                result = UserManagerConstant.USER_NOT_EXISTED;
            }
            else
            {//���û�������¼�ɹ�
            	
                //�����ǲ��ü�����ʽ��ŵġ�
//                if(!user.getPassword().toUpperCase().equals(pwd.toUpperCase()))
//                {
//                    //���벻��
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
     *@desc У���¼
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
			throw new BOException("��¼У��ʧ��",e);
		}
    	return hm;
    }
    
    /**
     * ���ͷ���
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
            // ��������output
            httpurlconnection.setDoOutput(true);
            // ����Ϊpost��ʽ
            httpurlconnection.setRequestMethod("POST");
            // ģ���ie
            httpurlconnection.setRequestProperty("user-agent", "mozilla/4.7 [en] (win98; i)");
            // post��Ϣ
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
            // �Ȼ�ȡXML���ݰ�
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
     *@desc ��ȡ�������ݰ�
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
        strContent.append("<token>" + token + "</token>");   //����
        strContent.append("</body>");
        strContent.append("</req>"); 
       return  strContent.toString();
    }
    
    /**
     * 
     *@desc ��ȡ���յ����ݰ�
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
