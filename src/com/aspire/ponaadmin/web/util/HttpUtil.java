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
 * <p>Title: http的工具类</p>
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
     * 从http请求中读取请求post、put的内容
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
            //从req中获取ServletInputStream in
            in = req.getInputStream() ;

            //从in中读取请求的xml封装，注意编码方式为utf-8
            content = IOUtil.getInputStreamText(in, encode) ;
        }
        finally
        {
            PublicUtil.CloseInputStream(in) ;
        }
        return content;
    }

    /**
     * 把一段文本按照指定的编码方式写到http response中
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
            //把xml写到rsp中。
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
     * 通过HttpURLConnection发送http请求
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
     * 从HttpURLConnection中获取http回应的内容
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
            //从流中读出回应
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
     * 将返回值的数据转换为数组
     * @param url ，String
     * @return byte[]
     * @throws Exception
     */
    public static byte[] getBytesFromURL(String url) throws Exception
    {
        //根据url实例化一个URL uri。
        URL uri = new URL(url);
        HttpURLConnection httpConn = (HttpURLConnection)uri.openConnection();
        //连接httpConn
        httpConn.connect();
        //得到输入流
        InputStream in = httpConn.getInputStream();
        //将输入流转换为输出流
        ByteArrayOutputStream out = PublicUtil.InputStreamToOutStream(in);
        //将输出流转为数组
        byte[] content = out.toByteArray();
        //关闭输出流
        PublicUtil.CloseOutStream(out);
        return content;
    }

    /**
     * 向一个url地址发送请求，并获取回应的内容
     * @param requestURL String
     * @param encode String
     * @return String
     * @throws Exception
     */
    public static String getResponseFromURL (String requestURL, String encode)
        throws Exception
    {
        //根据requestURL实例化一个URL url。
        URL url = new URL(requestURL) ;
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection() ;

        //设置httpConn请求方式为post
        httpConn.setDoInput(true) ;
        httpConn.setDoOutput(false) ;
        httpConn.setRequestMethod("POST") ;

        //连接httpConn
        httpConn.connect() ;

        //从httpConn获取回应
        String response = HttpUtil.getHttpResponse(httpConn, encode) ;
        httpConn.disconnect();
        return response ;
    }

    /**
     * 向一个url地址发送请求，并获取回应状态码
     * @param requestURL String
     * @param encode String
     * @return String
     * @throws Exception
     */
    public static int getResponseCodeFromURL (String requestURL, String encode)
        throws Exception
    {
        //根据requestURL实例化一个URL url。
        URL url = new URL(requestURL) ;
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection() ;

        //设置httpConn请求方式为post
        httpConn.setDoInput(true) ;
        httpConn.setDoOutput(false) ;
        httpConn.setRequestMethod("POST") ;

        //连接httpConn
        httpConn.connect() ;

        //从httpConn获取回应
        HttpUtil.getHttpResponse(httpConn, encode) ;
        int resCode = httpConn.getResponseCode();
        httpConn.disconnect();
        return resCode ;
    }
    
    /**
     * 向一个url地址发送请求，并获取回应状态码
     * @param requestURL String
     * @param encode String
     * @return String
     * @throws Exception
     */
    public static Object[] getResponseCodeAndRespFromURL (String requestURL, String encode)
        throws Exception
    {
    	Object[] object = new Object[2];
        //根据requestURL实例化一个URL url。
        URL url = new URL(requestURL) ;
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection() ;

        //设置httpConn请求方式为post
        httpConn.setDoInput(true) ;
        httpConn.setDoOutput(false) ;
        httpConn.setRequestMethod("POST") ;

        //连接httpConn
        httpConn.connect() ;

        //从httpConn获取回应
        object[1] = HttpUtil.getHttpResponse(httpConn, encode) ;
        object[0] = httpConn.getResponseCode();
        httpConn.disconnect();
        return object ;
    }
}
