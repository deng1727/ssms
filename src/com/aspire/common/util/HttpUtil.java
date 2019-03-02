package com.aspire.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;


/**
 * Description: Copyright(C) httpClient工具类
 * @author huangshsh
 * @version 1.0   createTime: 2014-8-4
 */
public class HttpUtil {
	static JLogger logger = LoggerFactory.getLogger(HttpUtil.class);
	/**
	 * httpClient的post请求
	 * @param url 请求地址
	 * @param params 请求参数
	 * @param contentType 请求头信息，可设置为application/json, application/xml等
	 * @param charset 编码
	 * @param timeout 超时时间
	 * @return
	 * @throws IOException
	 */
	public static String post(String url, String params, String contentType, String charset, int timeout) throws Exception {
		PostMethod method = null;
		try {
			method = new PostMethod(url);
			method.setRequestEntity(new StringRequestEntity(params, contentType, charset));
			HttpClient client = new HttpClient();
			client.getHttpConnectionManager().getParams().setConnectionTimeout(timeout);
			client.getHttpConnectionManager().getParams().setSoTimeout(timeout); 
			client.getParams().setContentCharset(charset);
			client.executeMethod(method);

			InputStream stream = method.getResponseBodyAsStream();
			byte[] b = new byte[2048];
			int length;
			StringBuffer sb = new StringBuffer();
			while ((length = stream.read(b)) > 0) {
				sb.append(new String(b, 0, length, charset));
			}
			return sb.toString();
		} catch (Exception e) {
			logger.debug(e);
			throw e;
		} finally {
			if(method != null){
				method.releaseConnection();
			}
		}
	}
	
	/**
	 * httpClient的post请求
	 * @param url		请求地址
	 * @param params	请求参数
	 * @param timeOut	超时时间(ms)
	 * @return String
	 * @throws IOException 
	 */
	public static String post(String url, Map<String, String> params, int timeOut) throws IOException {
		//启动httpClient，post的请求方式
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(url);
		method.addRequestHeader("Connection", "close");
		//装载请求参数
		Iterator<Entry<String, String>> it = params.entrySet().iterator();
		while(it.hasNext()) {
			Entry<String, String> entry = it.next();
			method.addParameter(entry.getKey(),entry.getValue());
		}
		//设置超时时间为3秒
		client.getHttpConnectionManager().getParams().setConnectionTimeout(timeOut);
		client.getHttpConnectionManager().getParams().setSoTimeout(timeOut); 
		client.getParams().setContentCharset("utf-8");

		return execute(client, method);
	}
	
	public static String get(String url, int timeOut) throws IOException{
		String result = "";
		//启动httpClient，get的请求方式
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod(url);
		//设置超时时间为3秒
		client.getHttpConnectionManager().getParams().setConnectionTimeout(timeOut);
		client.getHttpConnectionManager().getParams().setSoTimeout(timeOut); 
		client.getParams().setContentCharset("utf-8");

		return execute(client, method);
	}
	
	public static Res requestGet(String url, Map<String, String> params, int timeOut) {
		// 启动httpClient，get的请求方式
		HttpClient client = new HttpClient();
		client.getHttpConnectionManager().getParams().setConnectionTimeout(timeOut);
		client.getHttpConnectionManager().getParams().setSoTimeout(timeOut);
		client.getParams().setContentCharset("utf-8");

		GetMethod method = null;
		try {
			method = getMethod(url, params);
			client.executeMethod(method);
			String result = method.getResponseBodyAsString();
			return new Res(method.getStatusCode(), result);
		} catch (Exception e) {
			return new Res(500, e.getMessage());
		} finally {
			if(method != null){
				method.releaseConnection();
			}
		}
	}
	
	public static GetMethod getMethod(String url, Map<String, String> params) throws UnsupportedEncodingException {
		StringBuffer sb = new StringBuffer(url).append("?");
		if (params != null) {
			for (Entry<String, String> entry : params.entrySet()) {
				String encodeStr = URLEncoder.encode((String) entry.getValue(), "UTF-8");
				sb.append((String) entry.getKey()).append("=").append(encodeStr).append("&");
			}
		}
		return new GetMethod(sb.toString());
	}

	private static String execute(HttpClient client, HttpMethod method) throws IOException {
		String result;

		try {
			client.executeMethod(method);
			result = method.getResponseBodyAsString();
		} catch (HttpException e) {
			throw new HttpException(e.getMessage(), e);
		} catch (IOException e) {
			throw e;
		} finally {
			method.releaseConnection();
		}

		return result;
	}

	public static class Res {
		public int statusCode;
		public String content;

		public Res(int statusCode, String content) {
			this.statusCode = statusCode;
			this.content = content;
		}

		@Override
        public String toString() {
			return "[" + this.statusCode + "] \n" + this.content;
		}

		public int getStatusCode() {
			return statusCode;
		}

		public void setStatusCode(int statusCode) {
			this.statusCode = statusCode;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}
	}
	
	public static String post(String url , String params ) throws Exception{
		String kk = "";
		try {
			kk = post(url, params, "application/xml", "UTF-8", 5000);
		} catch (Exception e) {
			logger.debug("post in .....params :" + params,e);
		}
			return kk;
	}
}
