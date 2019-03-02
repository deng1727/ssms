package com.aspire.dotcard.colorringmgr.clrConvert;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.colorringmgr.clrLoad.ColorringConfig;
import com.aspire.dotcard.gcontent.GColorring;
import com.aspire.ponaadmin.web.util.PublicUtil;
import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPTransferType;

/**
 * <p>
 * ������������ת��������
 * </p>
 * <p>
 * Copyright (c) 2009 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved
 * </p>
 * 
 * @author bihui
 * @version 1.0.0.0
 */
public class DealOneColorring
{
    /**
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(DealOneColorring.class);
    
    /**
     * ��������ת����־����
     */
    private static final JLogger convertLog = LoggerFactory.getLogger("colorring.convert") ;
    
    /**
     * ������Ĳ������
     */
    private GColorring clrVO = null;
    
    /**
     * ͳһ��������ƽ̨����URL
     */
    private String UCAPUrl = "";
    
    /**
     * �����ʽ�ͱ�������ɵ����飬ʹ��Ӣ�Ķ��ŷָ�����
     */
    private String[] code_abps = null;
        
    /**
     * ��Դ�������ϲ��������ļ��Ĵ��Ŀ¼
     */
    private String storePath = "";
    
    /**
     * ���췽��
     * @param clrVO ������Ĳ������
     * @param UCAPUrl ����ͳһ����ƽ̨URL
     * @param code_abps ��������ת�������ͺͱ���������
     * @param colorring_tmp ���������ļ�������ʱ���·��
     * @param storePath ��Դ����������·��
     */
    public DealOneColorring(GColorring clrVO, String UCAPUrl, String[] code_abps, String storePath)
    {
        this.clrVO = clrVO;
        this.UCAPUrl = UCAPUrl;
        this.code_abps = code_abps;
        this.storePath = storePath;
    }
    
    /**
     * ����ת����������
     *
     */
    public void colorringConvert()
    {
        String[] XML= null;
        try
        {
            XML = this.generateXML();
        }
        catch (Exception e1)
        {
            convertLog.error("����ͳһ��������ƽ̨����XMLʱ�������쳣������ת��ʧ�ܣ���������Ϊ��" + this.clrVO.getContentID(), e1);
            return;
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("XML���ݹ���ɹ���׼����������ת��������");
        }
        //������Դ��������ftp����
        FTPClient ftp = null;
        try
        {
            ftp = this.getResourceServerFtp();
            this.mkdirs(ftp,this.storePath);
        }
        catch (Exception e)
        {
            try
            {
                ftp.quit();
            }
            catch (Exception e1)
            {
            }
            convertLog.error("�Ӳ����������л�ȡ��Դ������ftp���Ӳ���������ftp����ʱ�����쳣�������������ļ�ת��ֹͣ����������Ϊ��" + this.clrVO.getContentID(), e);
            return;
        }
        int length = XML.length;
        //ÿ������ת��������Ƿ�ɹ�
        boolean[] convertResult = new boolean[length];
        // ��������ת�����
        boolean totalResult = true;
        //ѭ����������ת��
        for (int i = 0; i < length; i++)
        {
            //Ŀ���ʽ
            String target = this.code_abps[i].split(",")[0];
            if(logger.isDebugEnabled())
            {
                logger.debug("��"+ (i+1) + "��ת�������xml����Ϊ��");
                logger.debug(XML[i]);
            }
            byte[] data = null;
            try
            {
                data = XML[i].getBytes("UTF-8");
            }
            catch (UnsupportedEncodingException e)
            {
                convertLog.error("��XML�ַ���ת����UTF-8���������ʱ�������쳣������ת��ʧ�ܣ���������Ϊ��" + this.clrVO.getContentID()+"������ת����ֹ!", e);
                break;
            }
            //����POST�ύ����
            PostMethod method = null;
            try
            { 
                method = new PostMethod(this.UCAPUrl) ;
                method.setRequestBody(new ByteArrayInputStream(data));
                method.setRequestHeader("Content-type", "text/XML; charset=UTF-8");
                method.addRequestHeader("Connection", "close");
                HttpClient client = new HttpClient();
                client.executeMethod(method);
            }
            catch (Exception e)
            {
                //����������쳣����method������ǰ�ͷš�
                method.releaseConnection();
                convertLog.error("ת��Ϊ" + target + "ʱ�����������������ƽ̨����ʧ�ܣ���������Ϊ��" + this.clrVO.getContentID(), e);
                break;
            }
            //ȡ���������ص�HTTP��Ӧ��
            int respCode = method.getStatusCode();

            if (logger.isDebugEnabled())
            {
                logger.debug("���������ص�HTTP��Ӧ�� respCode == " + respCode);
            }
            if (HttpServletResponse.SC_OK != respCode)
            {
                convertLog.error("ת��Ϊ" + target + "ʱ�����������������ƽ̨����ʧ�ܣ���������������" + respCode+ "������ת����ֹ!��������Ϊ��" + this.clrVO.getContentID());
                break;
            }
            // �����ַ�����Ϣ
            String respStr = null;
            try
            {
                // ������Ϣ
                respStr = new String(method.getResponseBody(),"UTF-8");
            }
            catch (Exception e)
            {
                convertLog.error("ת��Ϊ" + target + "ʱ�����������������ƽ̨����ʧ�ܣ�");
                convertLog.error("���ص�xml�ֽ����鰴UTF-8��ʽת����String����ʱ��������ת����ֹ����������Ϊ��" + this.clrVO.getContentID(),e);
                break;
            }
            finally
            {
                //���治��ʹ��method�������ͷŵ���
                method.releaseConnection();
            }
            //ת�������
            String result = null;
            Element root = null;
            try
            {
                // �������ص�����
                SAXBuilder sax = new SAXBuilder();
                Document doc = sax.build(new StringReader(respStr));
                root = doc.getRootElement();
            }
            catch (Exception e)
            {
                convertLog.error("ת��Ϊ" + target + "ʱ�����������������ƽ̨����ʧ�ܣ�");
                convertLog.error("��ͳһ��������ƽ̨���ص�xml���������documentʱ�����쳣������ת����ֹ����������Ϊ��" + this.clrVO.getContentID());
                break;
            }
            if (null == root || null == root.getChild("content"))
            {
                convertLog.error("ת��Ϊ" + target + "ʱ�����������������ƽ̨����ʧ�ܣ�");
                convertLog.error("���ݷ��ص�xml�����֪root�ڵ��content�ڵ㲻���ڣ�����ת����ֹ����������Ϊ��" + this.clrVO.getContentID());
                break;
            }
            Element content = root.getChild("content");
            //��ȡ��������
            result = content.getChild("result").getText();
            if (logger.isDebugEnabled())
            {
                logger.debug("��������ķ������� " + result);
            }
            //����������벻��100����������ʧ�ܣ�������һ��ת��
            if (!"100".equals(result))
            {
                convertLog.error("����ת��ʧ�ܣ�UCAP���ص�ת�����ǣ�" + result + "������ת����ֹ����������Ϊ��" + this.clrVO.getContentID());
                break;
            }
            String sid = content.getChild("sid").getText().trim();
            String size = content.getChild("size").getText().trim();
            String type = content.getChild("type").getText().trim();
            String http = content.getChild("http").getText().trim();
            if (logger.isDebugEnabled())
            {
                logger.debug("�����������ķ���ֵ sid = " + sid);
                logger.debug("�����������ķ���ֵ size = " + size);
                logger.debug("�����������ķ���ֵ type = " + type);
                logger.debug("�����������ķ���ֵ http = " + http);
            }
            if ("".equals(http))
            {
                convertLog.error("����ת��ʧ�ܣ�UCAP���ص�httpΪ�գ���Ӧ����������Ϊ��" + this.clrVO.getContentID());
                break;
            }
            BufferedInputStream bin = null;
            HttpURLConnection httpConn = null;
            try
            {
                URL url = new URL(http);
                httpConn = (HttpURLConnection)url.openConnection();
                //����httpConn
                httpConn.connect();
                //�õ�������
                bin = new BufferedInputStream(httpConn.getInputStream());
            }
            catch (Exception e)
            {
                convertLog.error("��������url��" + http + "��ȡ����ʱʧ�ܣ���Ӧ����������Ϊ��" + this.clrVO.getContentID());
                break;
            }
            if (logger.isDebugEnabled())
            {
                logger.debug("����������ƽ̨���ص�url��ȡ�������ɹ�������д����Դ������������");
            }
            if (!"audio".equalsIgnoreCase(type))
            {
                convertLog.error("����ת��ʧ�ܣ�UCAPת���ɹ��󷵻ص�type == " + type + "���������󣡶�Ӧ����������Ϊ��" + this.clrVO.getContentID());
                break;
            }
            
            //�����ת����aac��ʽ�ģ���Ҫ��¼����Ĳ���ʱ��
            if (sid.equals(this.clrVO.getContentID() + ".aac"))
            {
                this.clrVO.setAverageMark(Integer.parseInt(size));
            }
            
            try
            {
                if (logger.isDebugEnabled())
                {
                    logger.debug("��ʼ����Դ�������Ϸ���ת����������ļ� sid = " + sid);
                }  
                ftp.put(bin, sid, false);
                if (logger.isDebugEnabled())
                {
                    logger.debug("���������ļ� " + sid + " ���浽��Դ�������ɹ���");
                }
                //���ߵ�����˵��ת���ɹ���
                convertResult[i] = true;
            }
            catch (Exception e)
            {
                convertLog.error(new StringBuffer().append("ftp���浽��Դ������ ")
                                                   .append(this.storePath)
                                                   .append(File.separator)
                                                   .append(sid)
                                                   .append(" ʧ�ܣ���Ӧ����������Ϊ��")
                                                   .append(this.clrVO.getContentID())
                                                   .toString(),
                                 e);
                break;
            }
            finally
            {
                httpConn.disconnect();
                PublicUtil.CloseInputStream(bin);
            }
        }
        try
        {
            ftp.quit();
        }
        catch (Exception e)
        {
            convertLog.error("�˳�ftp����ʱ�����쳣��");
        }
        try
        {            
            for (int i = 0; i < length; i++)
            {
                totalResult = totalResult && convertResult[i];
                //�����һ����ת��ʧ�ܣ��������ʧ��
                if (!totalResult)
                {
                    break;
                }
            }
            convertLog.error("���в������͵�ת�������" + totalResult);
            //����������Ͷ�ת���ɹ��ˣ����Ĳ�����ն���������
            if (totalResult)
            {
                this.clrVO.save(); 
            }
        }
        catch (BOException e)
        {
            convertLog.error("���²�������ʱ�������ݿ��쳣����Ӧ����������Ϊ��" + this.clrVO.getContentID(), e);
        }
    }
    
    /**
     * ����UCAP�������xml
     * @return
     */
    private String[] generateXML() throws Exception
    {
        String encode = "UTF-8";
        int count = this.code_abps.length;
        //�������UCAPƽ̨��xml��������
        String[] content = new String[count];
        for (int i = 0; i < count; i++)
        {
            // �ַ�����������XML�ļ�
            StringBuffer sb = new StringBuffer();
            sb.append("<?xml version=\"1.0\" encoding=\"" + encode + "\"?>");
            sb.append("<req>");
            String[] temp = this.code_abps[i].split(",");
            sb.append("<ua>12530").append(temp[0]).append("</ua>");
            sb.append("<content>");
            sb.append("<source>").append(this.clrVO.getAuditionUrl()).append("</source>");
            sb.append("<sid>")
              .append(this.clrVO.getContentID()).append(".").append(temp[0])
              .append("</sid>");
            sb.append("<type>audio</type>");
            sb.append("<target>1</target>");
            sb.append("<params>");
            sb.append("<param name=\"audioCodec\">").append(temp[0]).append("</param>");
            sb.append("<param name=\"abps\">").append(temp[1]).append("</param>");
            sb.append("</params>");
            sb.append("</content>");
            sb.append("</req>");
            content[i] = sb.toString();
            if (logger.isDebugEnabled())
            {
                logger.debug("�����xml����Ϊ��");
                logger.debug(content[i]);
            }
        }
        return content;
    }
    
    /**
     * ��ȡ��Դ������ftp����
     * 
     * @return
     * @throws FTPException 
     * @throws IOException 
     * @throws BOException
     */
    private FTPClient getResourceServerFtp() throws IOException, FTPException
    {
        // ���������ȡftp���ӵĵ�ַ
        String sourceServerIP =ColorringConfig.sourceServerIP;// ColorringConfig.get("sourceServerIP");
        // ���������ȡftp���Ӵ���Ķ˿ں�
        int sourceServerPort = ColorringConfig.sourceServerPort;//Integer.parseInt(ColorringConfig.get("sourceServerPort"));
        // ���������ȡFTP�������ĵ�¼�û���
        String sourceServerUser = ColorringConfig.sourceServerUser;//ColorringConfig.get("sourceServerUser");
        // ���������ȡFTP�������ĵ�¼����
        String sourceServerPassword = ColorringConfig.sourceServerPassword;//ColorringConfig.get("sourceServerPassword");

        FTPClient ftp = new FTPClient(sourceServerIP, sourceServerPort);
        // ��ʼ��ftp����ģʽ��FTPConnectMode.PASV����FTPConnectMode.ACTIVE��
        ftp.setConnectMode(FTPConnectMode.PASV);
        ftp.login(sourceServerUser, sourceServerPassword);
        // �����ļ��������ͣ�FTPTransferType.ASCII����FTPTransferType.BINARY��
        ftp.setType(FTPTransferType.BINARY);
        return ftp;
    }
    
    /**
     * ��ftp�������ϴ����ļ���
     * 
     * @param ftp
     * @param remotePath
     * @throws IOException
     * @throws FTPException
     */
    private void mkdirs(FTPClient ftp, String remotePath) throws Exception
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("ftp��Դ�������ϲ�����Ŀ¼ remotePath == " + remotePath);
        }
        String[] paths = remotePath.split("/");
        if (paths != null)
        {    
            for (int i = 0; i < paths.length; i++)
            {
                if ("".equals(paths[i]))
                {
                    continue;
                } 
                boolean isExist = true;
                
                try
                {
                  ftp.chdir(paths[i]);
                }
                catch(Exception e )
                {
                    convertLog.error(paths[i] + " Ŀ¼�����ڣ���Ҫ������");
                    isExist = false;
                }
                if (!isExist)
                {
                    try
                    {
                        ftp.mkdir(paths[i]);
                    }
                    catch (Exception e)
                    {
                        convertLog.error(paths[i] + " Ŀ¼�Ѿ��������̴߳�����");
                    }
                    ftp.chdir(paths[i]);
                }
            }
        }
    }
}
