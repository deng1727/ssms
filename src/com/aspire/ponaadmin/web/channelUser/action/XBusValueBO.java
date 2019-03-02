/*
 * 
 */

package com.aspire.ponaadmin.web.channelUser.action;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * @author x_wangml
 * 
 */
public class XBusValueBO
{

    /**
     * 日志引用
     */
    private static final JLogger log = LoggerFactory.getLogger(XBusValueBO.class);

    /**
     * 单一实例
     */
    private static XBusValueBO bo = new XBusValueBO();

    /**
     * 常量换行符
     */
    private static final String nextLine = "\r\n";

    /**
     * 配置文件中的配置根节点名
     */
    private static final String SYSTEMNAME = "xbus";

    private XBusValueBO()
    {
    }

    public static XBusValueBO getInstance()
    {

        return bo;
    }

    /**
     * 用于发送变更文件
     * 
     * @param xBus
     * @throws IOException
     */
    public void sendXBusValue(XBusValueVo[] xBus) throws Exception
    {
        // 开始日志
        if (log.isDebugEnabled())
        {
            log.debug("通知xbus方法开始");
        }

        // 得到变更信息
        String sendXml = getSendXmlValue(xBus);
        
        // 得到通知urls
        String urls = getSendURL();
        
        // 响应码
        int ret = 0;

        // 配置项是否是通知
        if ("true".equals(isSend()))
        {
            String[] url = urls.split("#");

            // 循环通知
            for (int i = 0; i < url.length; i++)
            {
                // 通知xbus
                try
                {
                    ret = send(url[i], sendXml, "UTF-8");
                }
                catch (Exception e)
                {
                    log.error(e);
                    log.error("通知xbus失败，通知的url=" + url[i]);
                }

                // 如果响应码为200
                if (ret == 200)
                {
                    if (log.isDebugEnabled())
                    {
                        log.debug("通知xbus成功，通知的url=" + url[i]);
                        log.debug("通知xbus返回响应码为 code=" + ret);
                    }
                }
                // 否则
                else
                {
                    if (log.isDebugEnabled())
                    {
                        log.debug("通知xbus返回响应码为 code=" + ret);
                    }
                }
            }
        }

        // 结束日志
        if (log.isDebugEnabled())
        {
            log.debug("通知xbus方法结束");
        }
    }

    public int send(String urls, String sendXml, String encode) throws IOException
    {

        // 根据requestURL实例化一个URL url。
        URL url = new URL(urls);
        HttpURLConnection httpConn = ( HttpURLConnection ) url.openConnection();

        // 设置httpConn请求方式为post
        httpConn.setDoInput(true);
        httpConn.setDoOutput(true);
        httpConn.setRequestMethod("POST");
        
        // 写入url流中
        OutputStream os = null;
        
        try
        {
            os = httpConn.getOutputStream();
            os.write(sendXml.toString().getBytes("utf-8"));
            os.close();
        }
        finally
        {
            if (os != null)
            {
                try
                {
                    os.close() ;
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }


        // 连接httpConn
        httpConn.connect();

        // 返回响应码
        int code = httpConn.getResponseCode();

        httpConn.disconnect();

        return code;
    }

    /**
     * 得到生成文件信息
     * 
     * @param xBus 变更内容
     * @return
     */
    public String getSendXmlValue(XBusValueVo[] xBus)
    {

        StringBuffer str = new StringBuffer();

        str.append(getSendXmlValueHead("ssms_sortid"));
        str.append("<Body>").append(nextLine);

        for (int i = 0; i < xBus.length; i++)
        {
            str.append(xBus[i].toString());
        }

        str.append("</Body>").append(nextLine);
        str.append(getSendXmlValueEnd());

        return str.toString();
    }

    /**
     * 得到生成文件的尾部信息
     * 
     * @return
     */
    public String getSendXmlValueEnd()
    {

        return "</xBusReq>";
    }

    /**
     * 得到生成文件的头部信息
     * 
     * @param msgType 消息类型
     * @return
     */
    public String getSendXmlValueHead(String msgType)
    {
        // 生成时间
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy:MM:dd HH:mm:ss");
        
        StringBuffer str = new StringBuffer();

        str.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>")
           .append(nextLine);
        str.append("<xBusReq xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">")
           .append(nextLine);
        str.append("<Head>").append(nextLine);
        str.append("<MsgType>")
           .append(msgType)
           .append("</MsgType>")
           .append(nextLine);
        str.append("<Version>1.0</Version>").append(nextLine);
        str.append("<MsgSeqID/>").append(nextLine);
        str.append("<MsgCreateTime>").append(formatter.format(date)).append("</MsgCreateTime>").append(nextLine);
        str.append("<MsgTimeout>0</MsgTimeout>").append(nextLine);
        str.append("<AckURL />").append(nextLine);
        str.append("<Send_Address>").append(nextLine);
        str.append("<Address_InfoList>").append(nextLine);
        str.append("<DeviceID />").append(nextLine);
        str.append("</Address_InfoList>").append(nextLine);
        str.append("</Send_Address>").append(nextLine);
        str.append("<Dest_Address>").append(nextLine);
        str.append("<Address_InfoList>").append(nextLine);
        str.append("<DeviceID />").append(nextLine);
        str.append("</Address_InfoList>").append(nextLine);
        str.append("</Dest_Address>").append(nextLine);
        str.append("</Head>").append(nextLine);

        return str.toString();
    }

    /**
     * XBUS要通知哪几个url
     * 
     * @return
     */
    private String getSendURL()
    {
        return ConfigFactory.getSystemConfig()
                            .getModuleConfig(SYSTEMNAME)
                            .getItemValue("urls");
    }

    /**
     * 是否发送XBUS
     * 
     * @return
     */
    private String isSend()
    {
        return ConfigFactory.getSystemConfig()
                            .getModuleConfig(SYSTEMNAME)
                            .getItemValue("isSend");
    }
}
