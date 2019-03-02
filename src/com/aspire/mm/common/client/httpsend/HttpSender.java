
package com.aspire.mm.common.client.httpsend;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import sun.misc.BASE64Encoder;

import com.aspire.common.util.EncryptUtil;

public class HttpSender
{

    private static final Logger logger = Logger.getLogger(HttpSender.class);
    private static int connectTimeout =  2000;
    private static int timeout =  2000;
    /**
     * 发送方法 
     * 
     * @param basurl
     * @param req
     * @return
     * @throws Exception
     */
    public final static Resp sendRequest(String basurl, Req req ) throws Exception
    {
       return  HttpSender.sendRequest(basurl, req, "UTF-8");
    }

    /**
     * 发送加密方法 
     * 
     * @param basurl
     * @param req
     * @return
     * @throws Exception
     */
    public final static Resp sendRequestEncrypt(String basurl, Req req ) throws Exception
    {
       return  HttpSender.sendRequestEncrypt(basurl, req, "UTF-8");
    }
    
    /**
     * 发送方法
     * 
     * @param basurl
     * @param req
     * @return
     * @throws Exception
     */
    public final static Resp sendRequest(String basurl, Req req, String charSet) throws Exception
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("send(" + basurl + "," + req + ")");
        }
        String msg = req.toData();
        logger.debug("send msg:" + msg);
        HttpURLConnection httpurlconnection = null;
        Resp resp = null;
        try
        {
            URL url = new URL(basurl);
            httpurlconnection = ( HttpURLConnection ) url.openConnection();
            // 设置允许output
            httpurlconnection.setDoOutput(true);
            httpurlconnection.setConnectTimeout(connectTimeout);
            httpurlconnection.setReadTimeout(timeout);
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
            resp = req.getResp();
            resp.praseResp(doc);

            if (logger.isDebugEnabled())
            {
                logger.debug("receive data:" + resp.toString());
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
        return resp;
    }

    /**
     * 发送加密方法
     * 
     * @param basurl
     * @param req
     * @return
     * @throws Exception
     */
    public final static Resp sendRequestEncrypt(String basurl, Req req, String charSet) throws Exception
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("send(" + basurl + "," + req + ")");
        }
        String msg = req.toData();
        logger.debug("send msg:" + msg);
        HttpURLConnection httpurlconnection = null;
        Resp resp = null;
        try
        {
            URL url = new URL(basurl);
            httpurlconnection = ( HttpURLConnection ) url.openConnection();
            // 设置允许output
            httpurlconnection.setDoOutput(true);
            httpurlconnection.setConnectTimeout(connectTimeout);
            httpurlconnection.setReadTimeout(timeout);
            // 设置为post方式
            httpurlconnection.setRequestMethod("POST");
            
            // 模拟成ie
            httpurlconnection.setRequestProperty("user-agent", "mozilla/4.7 [en] (win98; i)");
            httpurlconnection.setRequestProperty("Content-Type", "application/xml");
            //String channelType = "2";//渠道类型,Integer类型 [1:电子流, 2:货架, 3:渠道报备,4:汇聚]
            httpurlconnection.setRequestProperty("CHANNEL-TYPE", "2");
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
            //加密
            byte[] reqEncryptMsg = EncryptUtil.encrypt(msg.getBytes(charSet));
            if (logger.isDebugEnabled())
            {
                logger.debug(" EncryptUtil.encrypt reqEncryptMsg="+reqEncryptMsg.toString());
            }
            os.write(reqEncryptMsg);
            os.close();
            if (logger.isDebugEnabled())
            {
                logger.debug("bss data send succeed ");
                logger.debug("ret code:" + httpurlconnection.getResponseCode());
            }
            System.out.println("ret code:" + httpurlconnection.getResponseCode());
            byte[] respDecryptMsg = streamToBytes(httpurlconnection.getInputStream());
            if (logger.isDebugEnabled())
            {
                logger.debug("ret respDecryptMsg:" + respDecryptMsg.toString());
            }
            //解密
            byte[] respMsg = EncryptUtil.decrypt(respDecryptMsg);
            if (logger.isDebugEnabled())
            {
                logger.debug("ret respMsg:" + respMsg);
            }
            // 先获取XML数据包
            SAXReader  sax = new SAXReader ();
            
            Document doc = sax.read(new ByteArrayInputStream(respMsg));

            logger.debug("response xml : \n" + doc.asXML());
            resp = req.getResp();
            resp.praseResp(doc);

            if (logger.isDebugEnabled())
            {
                logger.debug("receive data:" + resp.toString());
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
        return resp;
    }
    
    /**
     * 发送请求
     * @return
     */
    public static Resp sendRequest(String basurl, NameValuePair[] nameValuePairs,Resp resp) throws Exception {
        // 构造HTTP请求
        HttpClient httpclient = new HttpClient();
        PostMethod postMethod = new PostMethod(basurl);
        if (nameValuePairs != null) {
            postMethod.addParameters(nameValuePairs);
        }

        // 发送请求
        String strResponse = null;
        try {
        	// 2011.04.27 增加了设置超时方法
        	//httpclient.getHttpConnectionManager().getParams().setConnectionTimeout(connectTimeout);   
        	//httpclient.getHttpConnectionManager().getParams().setSoTimeout(timeout);  
        	httpclient.setConnectionTimeout(connectTimeout);
        	httpclient.setTimeout(timeout);
            httpclient.executeMethod(postMethod);
            strResponse=IOUtils.toString(postMethod.getResponseBodyAsStream(),"UTF-8" );
        } catch (HttpException e) {
            logger.error("打开 URL失败，URL："  + basurl);
            throw new Exception("打开 URL失败，URL："  + basurl);
        } catch (IOException e) {
            logger.error("打开 URL失败，URL："  + basurl);
            throw new Exception("打开 URL失败，URL："  + basurl);
        } finally {
            postMethod.releaseConnection();            
        }
        
        // debug接收消息内容
        if (logger.isDebugEnabled()) {
            logger.debug("============ url: " + basurl +  " =============");
            logger.debug("============ resp start=============");
            logger.debug(strResponse);
            logger.debug("============ resp end=============");
        }
        
        if (StringUtils.isEmpty(strResponse)) {
            logger.error("SSO返回消息为空，URL："  + basurl);
            throw new Exception("SSO返回消息为空，URL："  + basurl);
        }

        // dom4j解析返回的xml文档
        ByteArrayInputStream inputStream = null;
        try {
            // 去掉<xml>格式前边的回车换行
            int xmlBeginIndex = strResponse.indexOf("<?xml");
            if (xmlBeginIndex < 0) {
                throw new Exception("XML格式错误");
            }
            
            String strXml = strResponse.substring(xmlBeginIndex);
            SAXReader reader = new SAXReader();

            try {
                inputStream = new ByteArrayInputStream(strXml.getBytes("utf-8"));
            } catch (UnsupportedEncodingException e) {
                throw new Exception("XML非UTF-8格式");
            }
            
            Document document = reader.read(inputStream);
            
            return resp.praseResp(document);
            
        } catch (DocumentException e) {
            logger.error("SSO返回格式错误strResponse="+strResponse,e);
            e.printStackTrace();
            throw new Exception("SSO返回格式错误");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error("关闭流异常, error=" + e.getMessage());
                }
            }
        }
    }
    
    private static byte[] streamToBytes(InputStream in) throws IOException {
		byte[] buffer = new byte[5120];
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int readSize;
		while ((readSize = in.read(buffer)) >= 0)
			out.write(buffer, 0, readSize);
		in.close();
		return out.toByteArray();
	}
    
    public static String inputStream2String(InputStream inputStream,String encode) {
		InputStreamReader reader = null;
		StringBuffer stringBuffer = new StringBuffer();
		try {
			reader = new InputStreamReader(inputStream, encode);
			char[] buff = new char[1024];
			int length = 0;
			while ((length = reader.read(buff)) != -1) {
				stringBuffer.append(new String(buff, 0, length));
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return stringBuffer.toString();
	}
}
