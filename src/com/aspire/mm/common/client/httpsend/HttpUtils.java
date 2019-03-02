package com.aspire.mm.common.client.httpsend;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpProtocolParams;

import com.aspire.dotcard.appmonitor.bo.AppMonitorDBOpration;

/**
 * ͨ��apache httpclient��ָ��url����http����Ĺ�����
 * @author baojun
 * @since jdk1.6 apache httpcore 4.2.2
 * @version 1.0
 */
public class HttpUtils 
{
	/**
	 * ����post����
	 * @param url ����URL
	 * @param params �������
	 * @param charset ��Ӧ���ַ�������
	 * @return Զ�̷���������Ӧ
	 * @throws Exception
	 */
	public static HttpEntity post(String url,Map<String, String[]>params)throws Exception
	{
		HttpPost post = new HttpPost(url);
		return request(post, params);
	}
	/**
	 * ����json��ʽ��post�������
	 * @param url ����URL
	 * @param jsonString �����json�ַ���
	 * @return Զ�̷���������Ӧ
	 * @throws Exception
	 */
	public static HttpEntity postJson(String url,String jsonString,String charset)throws Exception
	{
		return post(url,jsonString.getBytes(charset));
	}
	/**
	 * ����post����
	 * @param url ����URL
	 * @param bytes post���������
	 * @param mimeType mime����
	 * @return Զ�̷���������Ӧ
	 * @throws Exception
	 */
	public static HttpEntity post(String url,
								byte[]bytes)throws Exception 
	{
		return post(url, bytes, null);
	}
	/**
	 * ����post����
	 * @param url ����URL
	 * @param bytes post���������
	 * @param params �������
	 * @param charset �ַ�����
	 * @return Զ�̷���������Ӧ
	 * @throws Exception
	 */
	public static HttpEntity post(String url,
								byte[]bytes,
								Map<String, String[]>params)throws Exception 
	{
		HttpPost post = new HttpPost(url);
		ByteArrayEntity entity = new ByteArrayEntity(bytes);
		post.setEntity(entity);
		if(bytes==null)
		{
			bytes = new byte[]{};
		}
		if(!hasObject(params))
		{
			params = new HashMap<String, String[]>();
		}
		return request(post, params);
	}
	/**
	 * ����get����
	 * @param url ����URL
	 * @param params �������
	 * @param charset ��Ӧ���ַ�����
	 * @return Զ�̷���������Ӧ
	 * @throws Exception
	 */
	public static HttpEntity get(String url,Map<String, String[]>params)throws Exception 
	{
		HttpGet get = new HttpGet(url);
		return request(get, params);
	}
	/**
	 * ��������
	 * @param request �������
	 * @param params �������
	 * @return Զ�̷���������Ӧ
	 * @throws Exception
	 */
	public static HttpEntity request(HttpUriRequest request,Map<String, String[]>params)throws Exception
	{
		if(hasObject(params))
		{
			for(String key:params.keySet())
			{
				request.getParams().setParameter(key, params.get(key));
			}
		}
		HttpClient client = getClient();
		HttpResponse response = client.execute(request);
		HttpEntity entity = response.getEntity();
		return entity;
	}
	/**
	 * �õ�һ��HttpClient����
	 * @return HttpClient����
	 * @throws Exception
	 */
	private static HttpClient getClient()throws Exception
	{
		HttpClient client = new DefaultHttpClient();
		String agent = "Mozilla/5.0 (Windows; U; Windows NT 5.1;" +
							" zh-CN; rv:1.9.1.9) Gecko/20100315 Firefox/3.5.9";
		HttpProtocolParams.setUserAgent(client.getParams(),agent);
		Scheme scheme = getSSLScheme();
		// �������ӳ�ʱʱ��(��λ����) 
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,30000);
		//���ö����ݳ�ʱʱ��(��λ����)
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,60000);
		client.getConnectionManager().getSchemeRegistry().register(scheme);
		return client;
	}
	/**
	 * �õ�һ������SSL��Scheme
	 * @return SSL��Scheme
	 * @throws Exception
	 */
	private static Scheme getSSLScheme()throws Exception
	{
		TrustManager trustManager = getTrustManager();
		SSLContext context = SSLContext.getInstance(SSLSocketFactory.TLS);
		context.init(null, new TrustManager[]{trustManager}, null);
		SSLSocketFactory factory = new SSLSocketFactory(context,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		Scheme scheme = new Scheme("https", 443, factory);
		return scheme;
	}
	/**
	 * �õ�TrustManager����
	 * @return TrustManager����
	 */
	private static TrustManager getTrustManager()
	{
		TrustManager manager = new X509TrustManager() {
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
			
			public void checkServerTrusted(X509Certificate[] chain, String authType)
					throws CertificateException {
				
			}
			
			public void checkClientTrusted(X509Certificate[] chain, String authType)
					throws CertificateException {
					
			}
		};
		return manager;
	}
	/**
	 * ���Map�Ƿ����Ԫ��
	 * @param map
	 * @return
	 */
	public static boolean hasObject(Map<?, ?>map)
	{
		return map!=null&&map.size()>0;
	}
	/**
     * �滻http�����url
     * @param url �滻���url
     * @return
     */
    public static String parseUrl(String url)
    {
    	url = url.replaceAll("^(http:(/)*)+","#{http}");
    	url = url.replace('\\', '/');
    	url = url.replaceAll("/+", "/");
    	url = url.replaceFirst("#\\{http\\}", "http://");
        return url;
    }
    /**
     * �ж�һ��url�Ƿ���http�����url���Ƿ����http�����schema��
     * @param url http�����url
     * @return
     */
    public static boolean isHttpUrl(String url)
    {
    	url = parseUrl(url);
    	return url.matches("^((http)|(https))://.*$");
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
	public static void main(String[] args)throws Exception
	{
		//System.out.println(isHttpUrl("http:https://10.12.3.195:8161"));
		//URI uri = new URI("http://10.12.3.195:8161/admin/a?aaa=ccc");
//		System.out.println(uri.getHost());
//		System.out.println(uri.getPort());
//		System.out.println(uri.getScheme());
		//System.out.println(uri.getPath());
//		System.out.println(uri.getQuery());
//		System.out.println(uri.getRawPath());
//		System.out.println(uri.getRawQuery());
//		System.out.println(uri.getRawSchemeSpecificPart());
		//System.out.println(uri.getAuthority());
		//System.out.println(uri.getFragment());
//		System.out.println(uri.toURL().toString());
//		URL url = new URL("http", "10.12.3.195", 8161, "/admin");
//		URL u2 = new URL(new URL("http://10.12.3.195:8161/admin"), "/b");
//		System.out.println(u2.toURI());
		String dcurl ="http://10.1.5.50:18080/pkginfo.do";
		//String jsonString =inputStream2String(postJson(dcurl,"{\"Portal\":\"M\",\"Contentid\": [300005006472,320000226126],\"Deviceid\":\"512\"}","utf-8").getContent(),"utf-8");
		String url ="http://10.12.3.119:12201/mmsearch/query?f=O&t=&field=contentid&value=300002555782";
		//System.out.println(jsonString);
		String jsonString = inputStream2String(get(url,null).getContent(),"utf-8");
		String js = "{\"result\":[{\"id\":\"dsf\"}]}";
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		JSONArray obj = jsonObj.getJSONArray("result");
		System.out.println(obj.isEmpty());
		System.out.println(obj);
		String contentid = "300001509575,300005006478,300005006477";
	}
}
