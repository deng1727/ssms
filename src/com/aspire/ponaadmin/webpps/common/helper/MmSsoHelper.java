package com.aspire.ponaadmin.webpps.common.helper;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.log4j.Logger;

import com.aspire.mm.common.client.httpsend.HttpSender;
import com.aspire.ponaadmin.webpps.common.filter.BBSSsoAuthorizeFilter;

public class MmSsoHelper {

	/**
     * logger.
     */
    private static final Logger logger = Logger.getLogger(MmSsoHelper.class);
    
    /**
     * 验证令牌
     * @param token 令牌 1
     * @param buid  用户标识
     * @return 成功，失败
     * @throws SsoException
     * @throws ValidateTokenFailureException 
     */
    public static String validateToken(String validateTokenUrl,String BPID,String token, String buid)  throws Exception  {
        
        // 准备请求参数
        NameValuePair BPIDPair = new NameValuePair("BPID", BPID);
        
        NameValuePair utPair = new NameValuePair("UT", token);
        NameValuePair buidPair = new NameValuePair("BUID", buid);
        NameValuePair[] nameValuePairs = new NameValuePair[3];
        nameValuePairs[0] = utPair;
        nameValuePairs[1] = buidPair;
        nameValuePairs[2] = BPIDPair;
        logger.debug("mmsso鉴权url:="+validateTokenUrl);
        // 发送验证令牌请求
        MmSsoResp ssoResp =  ( MmSsoResp ) HttpSender.sendRequest(validateTokenUrl, nameValuePairs,new MmSsoResp());
        logger.debug("返回码："+ssoResp.getResultCode());
        if (ssoResp != null && "0".equals(ssoResp.getResultCode()))
        {
            return ssoResp.getBuid();
        }
        else
        {
            logger.error("验证令牌失败，token："  + token+";buid="+buid +";resultCode="+ssoResp.getResultCode());
            throw new Exception("验证令牌失败，token："  + token+";buid="+buid);
        }
    }
    
    /**
     * 创建令牌
     * @param buid 用户标识
     * @return 令牌
     * @throws SsoException
     */
    public  static String createToken(String createTokenUrl,String BPID,String buid) throws Exception {
        
        // 准备请求参数
        NameValuePair BPIDPair = new NameValuePair("BPID", BPID);
        NameValuePair buidPair = new NameValuePair("BUID", buid);
        
        NameValuePair[] nameValuePairs = new NameValuePair[2];
        nameValuePairs[0] = buidPair;
        nameValuePairs[1] = BPIDPair;
        
        // 发送验证令牌请求
        MmSsoResp resp = ( MmSsoResp ) HttpSender.sendRequest(createTokenUrl, nameValuePairs,new MmSsoResp());
        return resp.getUt();
    }
    
    /**
     * 刷新令牌
     * @param buid 用户标识
     * @return 令牌
     * @throws SsoException
     */
    public  static String refreshToken(String refreshTokenUrl,String BPID,String buid) throws Exception {
        
        // 准备请求参数    
        NameValuePair BPIDPair = new NameValuePair("BPID", BPID);
        NameValuePair buidPair = new NameValuePair("BUID", buid);
        NameValuePair[] nameValuePairs = new NameValuePair[2];
        nameValuePairs[0] = buidPair;
        nameValuePairs[1] = BPIDPair;
        
        // 发送验证令牌请求
        MmSsoResp userInfo = ( MmSsoResp ) HttpSender.sendRequest(refreshTokenUrl, nameValuePairs,new MmSsoResp() );
        return userInfo.getUt();
    }

    /**
     * 注销令牌
     * @param buid 用户标识
     * @return 令牌
     * @throws SsoException
     */
    public  static void logoffToken(String logoffTokenUrl,String BPID,String buid,String ut) throws Exception {
        
        // 准备请求参数
        NameValuePair BPIDPair = new NameValuePair("BPID", BPID);
        NameValuePair buidPair = new NameValuePair("BUID", buid);
        NameValuePair UTPair = new NameValuePair("UT", ut);
        
        NameValuePair[] nameValuePairs = new NameValuePair[3];
        nameValuePairs[0] = buidPair;
        nameValuePairs[1] = BPIDPair;
        nameValuePairs[2] = UTPair;
        
        // 注销令牌
        HttpSender.sendRequest(logoffTokenUrl, nameValuePairs,new MmSsoResp() );
        
    }
    
    public static void main(String[] args){
    	String url = "http://10.1.3.220:8015/sso/sso/validateToken.action";
    	String buid = "5003637";
    	String ut = "D5E750BCB651FBAEC1982D1E6BDFBA5802100B39C9BCB91BD82C934AE9E358AD";
    	String queryUrl = "http://10.1.3.222:8002/oms/ChannelAPInfo";
    	try {
			buid = validateToken(url,"",ut,buid);
			String result = BBSSsoAuthorizeFilter.sendGet(queryUrl, "UD="+buid);
			String ss[] = result.split("=");
            System.out.println(ss[1].trim());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
