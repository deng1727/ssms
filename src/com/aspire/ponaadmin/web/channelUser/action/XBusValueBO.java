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
     * ��־����
     */
    private static final JLogger log = LoggerFactory.getLogger(XBusValueBO.class);

    /**
     * ��һʵ��
     */
    private static XBusValueBO bo = new XBusValueBO();

    /**
     * �������з�
     */
    private static final String nextLine = "\r\n";

    /**
     * �����ļ��е����ø��ڵ���
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
     * ���ڷ��ͱ���ļ�
     * 
     * @param xBus
     * @throws IOException
     */
    public void sendXBusValue(XBusValueVo[] xBus) throws Exception
    {
        // ��ʼ��־
        if (log.isDebugEnabled())
        {
            log.debug("֪ͨxbus������ʼ");
        }

        // �õ������Ϣ
        String sendXml = getSendXmlValue(xBus);
        
        // �õ�֪ͨurls
        String urls = getSendURL();
        
        // ��Ӧ��
        int ret = 0;

        // �������Ƿ���֪ͨ
        if ("true".equals(isSend()))
        {
            String[] url = urls.split("#");

            // ѭ��֪ͨ
            for (int i = 0; i < url.length; i++)
            {
                // ֪ͨxbus
                try
                {
                    ret = send(url[i], sendXml, "UTF-8");
                }
                catch (Exception e)
                {
                    log.error(e);
                    log.error("֪ͨxbusʧ�ܣ�֪ͨ��url=" + url[i]);
                }

                // �����Ӧ��Ϊ200
                if (ret == 200)
                {
                    if (log.isDebugEnabled())
                    {
                        log.debug("֪ͨxbus�ɹ���֪ͨ��url=" + url[i]);
                        log.debug("֪ͨxbus������Ӧ��Ϊ code=" + ret);
                    }
                }
                // ����
                else
                {
                    if (log.isDebugEnabled())
                    {
                        log.debug("֪ͨxbus������Ӧ��Ϊ code=" + ret);
                    }
                }
            }
        }

        // ������־
        if (log.isDebugEnabled())
        {
            log.debug("֪ͨxbus��������");
        }
    }

    public int send(String urls, String sendXml, String encode) throws IOException
    {

        // ����requestURLʵ����һ��URL url��
        URL url = new URL(urls);
        HttpURLConnection httpConn = ( HttpURLConnection ) url.openConnection();

        // ����httpConn����ʽΪpost
        httpConn.setDoInput(true);
        httpConn.setDoOutput(true);
        httpConn.setRequestMethod("POST");
        
        // д��url����
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


        // ����httpConn
        httpConn.connect();

        // ������Ӧ��
        int code = httpConn.getResponseCode();

        httpConn.disconnect();

        return code;
    }

    /**
     * �õ������ļ���Ϣ
     * 
     * @param xBus �������
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
     * �õ������ļ���β����Ϣ
     * 
     * @return
     */
    public String getSendXmlValueEnd()
    {

        return "</xBusReq>";
    }

    /**
     * �õ������ļ���ͷ����Ϣ
     * 
     * @param msgType ��Ϣ����
     * @return
     */
    public String getSendXmlValueHead(String msgType)
    {
        // ����ʱ��
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
     * XBUSҪ֪ͨ�ļ���url
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
     * �Ƿ���XBUS
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
