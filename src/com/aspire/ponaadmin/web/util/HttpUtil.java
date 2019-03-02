package com.aspire.ponaadmin.web.util ;

import javax.servlet.http.HttpServletRequest ;
import javax.servlet.ServletInputStream ;
import java.io.IOException ;
import java.io.UnsupportedEncodingException ;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedInputStream;

/**
 *
 * <p>Title: http�Ĺ�����</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author shidr
 * @version 1.1.0.0
 */
public class HttpUtil
{
    /**
     * ��http�����ж�ȡ����post��put������
     * @param req HttpServletRequest
     * @param encode String
     * @return String
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    public static String getRequestContent (HttpServletRequest req,
                                            String encode)
        throws UnsupportedEncodingException, IOException
    {
        ServletInputStream in = null ;
        String content = null;
        try
        {
            //��req�л�ȡServletInputStream in
            in = req.getInputStream() ;

            //��in�ж�ȡ�����xml��װ��ע����뷽ʽΪutf-8
            content = IOUtil.getInputStreamText(in, encode) ;
        }
        finally
        {
            PublicUtil.CloseInputStream(in) ;
        }
        return content;
    }

    /**
     * ��һ���ı�����ָ���ı��뷽ʽд��http response��
     * @param rsp HttpServletResponse
     * @param content String
     * @param encode String
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    public static void writeToResponse (HttpServletResponse rsp, String content,
                                        String encode)
        throws UnsupportedEncodingException, IOException
    {
        OutputStream out = null ;
        try
        {
            byte[] contentBytes = content.getBytes(encode);
            rsp.setContentLength(contentBytes.length);
            //��xmlд��rsp�С�
            out = rsp.getOutputStream() ;
            out.write(contentBytes) ;
            out.flush();
        }
        finally
        {
            PublicUtil.CloseOutStream(out) ;
        }
    }

    /**
     * ͨ��HttpURLConnection����http����
     * @param httpConn HttpURLConnection
     * @param requestContent String
     * @param encode String
     * @throws IOException
     */
    public static void sendHttpRequest (HttpURLConnection httpConn,
                                    String requestContent, String encode)
        throws IOException
    {
        OutputStream out = null ;
        try
        {
            out = new BufferedOutputStream(httpConn.getOutputStream()) ;
            out.write(requestContent.getBytes(encode)) ;
            out.flush();
        }
        finally
        {
            PublicUtil.CloseOutStream(out) ;
        }
    }

    /**
     * ��HttpURLConnection�л�ȡhttp��Ӧ������
     * @param httpConn HttpURLConnection
     * @param encode String
     * @return String
     * @throws IOException
     */
    public static String getHttpResponse (HttpURLConnection httpConn,String encode)
        throws IOException
    {
        InputStream in = null;
        ByteArrayOutputStream bout = null;
        OutputStream out = null;
        String result = null;
        try
        {
            in = new BufferedInputStream(httpConn.getInputStream());
            bout = new ByteArrayOutputStream();
            out = new BufferedOutputStream(bout);
            //�����ж�����Ӧ
            byte[] buff = new byte[1024] ;
            int rc = 0 ;
            while ((rc = in.read(buff)) >= 0)
            {
                out.write(buff, 0, rc) ;
            }
            out.flush();
            result = new String(bout.toByteArray(),encode);
        }
        finally
        {
            PublicUtil.CloseInputStream(in);
            PublicUtil.CloseOutStream(out);
        }
        return result;
    }

    /**
     * ������ֵ������ת��Ϊ����
     * @param url ��String
     * @return byte[]
     * @throws Exception
     */
    public static byte[] getBytesFromURL(String url) throws Exception
    {
        //����urlʵ����һ��URL uri��
        URL uri = new URL(url);
        HttpURLConnection httpConn = (HttpURLConnection)uri.openConnection();
        //����httpConn
        httpConn.connect();
        //�õ�������
        InputStream in = httpConn.getInputStream();
        //��������ת��Ϊ�����
        ByteArrayOutputStream out = PublicUtil.InputStreamToOutStream(in);
        //�������תΪ����
        byte[] content = out.toByteArray();
        //�ر������
        PublicUtil.CloseOutStream(out);
        return content;
    }

    /**
     * ��һ��url��ַ�������󣬲���ȡ��Ӧ������
     * @param requestURL String
     * @param encode String
     * @return String
     * @throws Exception
     */
    public static String getResponseFromURL (String requestURL, String encode)
        throws Exception
    {
        //����requestURLʵ����һ��URL url��
        URL url = new URL(requestURL) ;
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection() ;

        //����httpConn����ʽΪpost
        httpConn.setDoInput(true) ;
        httpConn.setDoOutput(false) ;
        httpConn.setRequestMethod("POST") ;

        //����httpConn
        httpConn.connect() ;

        //��httpConn��ȡ��Ӧ
        String response = HttpUtil.getHttpResponse(httpConn, encode) ;
        httpConn.disconnect();
        return response ;
    }

    /**
     * ��һ��url��ַ�������󣬲���ȡ��Ӧ״̬��
     * @param requestURL String
     * @param encode String
     * @return String
     * @throws Exception
     */
    public static int getResponseCodeFromURL (String requestURL, String encode)
        throws Exception
    {
        //����requestURLʵ����һ��URL url��
        URL url = new URL(requestURL) ;
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection() ;

        //����httpConn����ʽΪpost
        httpConn.setDoInput(true) ;
        httpConn.setDoOutput(false) ;
        httpConn.setRequestMethod("POST") ;

        //����httpConn
        httpConn.connect() ;

        //��httpConn��ȡ��Ӧ
        HttpUtil.getHttpResponse(httpConn, encode) ;
        int resCode = httpConn.getResponseCode();
        httpConn.disconnect();
        return resCode ;
    }
    
    /**
     * ��һ��url��ַ�������󣬲���ȡ��Ӧ״̬��
     * @param requestURL String
     * @param encode String
     * @return String
     * @throws Exception
     */
    public static Object[] getResponseCodeAndRespFromURL (String requestURL, String encode)
        throws Exception
    {
    	Object[] object = new Object[2];
        //����requestURLʵ����һ��URL url��
        URL url = new URL(requestURL) ;
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection() ;

        //����httpConn����ʽΪpost
        httpConn.setDoInput(true) ;
        httpConn.setDoOutput(false) ;
        httpConn.setRequestMethod("POST") ;

        //����httpConn
        httpConn.connect() ;

        //��httpConn��ȡ��Ӧ
        object[1] = HttpUtil.getHttpResponse(httpConn, encode) ;
        object[0] = httpConn.getResponseCode();
        httpConn.disconnect();
        return object ;
    }
}
