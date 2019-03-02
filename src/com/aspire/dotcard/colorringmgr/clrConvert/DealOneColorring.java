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
 * 单个彩铃试听转换处理类
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
     * 日志引用
     */
    protected static JLogger logger = LoggerFactory.getLogger(DealOneColorring.class);
    
    /**
     * 彩铃试听转换日志引用
     */
    private static final JLogger convertLog = LoggerFactory.getLogger("colorring.convert") ;
    
    /**
     * 待处理的彩铃对象
     */
    private GColorring clrVO = null;
    
    /**
     * 统一内容适配平台访问URL
     */
    private String UCAPUrl = "";
    
    /**
     * 编码格式和编码率组成的数组，使用英文逗号分隔开。
     */
    private String[] code_abps = null;
        
    /**
     * 资源服务器上彩铃试听文件的存放目录
     */
    private String storePath = "";
    
    /**
     * 构造方法
     * @param clrVO 待处理的彩铃对象
     * @param UCAPUrl 内容统一适配平台URL
     * @param code_abps 彩铃试听转换的类型和编码率数组
     * @param colorring_tmp 彩铃试听文件本地临时存放路径
     * @param storePath 资源服务器存贮路径
     */
    public DealOneColorring(GColorring clrVO, String UCAPUrl, String[] code_abps, String storePath)
    {
        this.clrVO = clrVO;
        this.UCAPUrl = UCAPUrl;
        this.code_abps = code_abps;
        this.storePath = storePath;
    }
    
    /**
     * 彩铃转换的主方法
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
            convertLog.error("构造统一内容适配平台访问XML时发生了异常，彩铃转换失败，铃音编码为：" + this.clrVO.getContentID(), e1);
            return;
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("XML数据构造成功，准备启动彩铃转换。。。");
        }
        //构造资源服务器的ftp连接
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
            convertLog.error("从彩铃配置项中获取资源服务器ftp连接参数并请求ftp连接时发生异常，本彩铃试听文件转换停止，铃音编码为：" + this.clrVO.getContentID(), e);
            return;
        }
        int length = XML.length;
        //每种类型转换结果，是否成功
        boolean[] convertResult = new boolean[length];
        // 所有类型转换结果
        boolean totalResult = true;
        //循环启动试听转换
        for (int i = 0; i < length; i++)
        {
            //目标格式
            String target = this.code_abps[i].split(",")[0];
            if(logger.isDebugEnabled())
            {
                logger.debug("第"+ (i+1) + "个转换请求的xml数据为：");
                logger.debug(XML[i]);
            }
            byte[] data = null;
            try
            {
                data = XML[i].getBytes("UTF-8");
            }
            catch (UnsupportedEncodingException e)
            {
                convertLog.error("将XML字符串转换成UTF-8编码的数组时发生了异常，彩铃转换失败，铃音编码为：" + this.clrVO.getContentID()+"，本次转换终止!", e);
                break;
            }
            //构造POST提交数据
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
                //如果发生了异常，将method链接提前释放。
                method.releaseConnection();
                convertLog.error("转换为" + target + "时，与第三方内容适配平台交互失败！铃音编码为：" + this.clrVO.getContentID(), e);
                break;
            }
            //取服务器返回的HTTP响应码
            int respCode = method.getStatusCode();

            if (logger.isDebugEnabled())
            {
                logger.debug("服务器返回的HTTP响应码 respCode == " + respCode);
            }
            if (HttpServletResponse.SC_OK != respCode)
            {
                convertLog.error("转换为" + target + "时，与第三方内容适配平台交互失败！服务器返回码是" + respCode+ "，本次转换终止!铃音编码为：" + this.clrVO.getContentID());
                break;
            }
            // 返回字符串信息
            String respStr = null;
            try
            {
                // 返回信息
                respStr = new String(method.getResponseBody(),"UTF-8");
            }
            catch (Exception e)
            {
                convertLog.error("转换为" + target + "时，与第三方内容适配平台交互失败！");
                convertLog.error("返回的xml字节数组按UTF-8格式转化成String对象时出错，本次转换终止！铃音编码为：" + this.clrVO.getContentID(),e);
                break;
            }
            finally
            {
                //后面不再使用method，将其释放掉。
                method.releaseConnection();
            }
            //转换结果码
            String result = null;
            Element root = null;
            try
            {
                // 解析返回的内容
                SAXBuilder sax = new SAXBuilder();
                Document doc = sax.build(new StringReader(respStr));
                root = doc.getRootElement();
            }
            catch (Exception e)
            {
                convertLog.error("转换为" + target + "时，与第三方内容适配平台交互失败！");
                convertLog.error("用统一内容适配平台返回的xml结果对象构造document时发生异常，本次转换终止！铃音编码为：" + this.clrVO.getContentID());
                break;
            }
            if (null == root || null == root.getChild("content"))
            {
                convertLog.error("转换为" + target + "时，与第三方内容适配平台交互失败！");
                convertLog.error("根据返回的xml结果得知root节点或content节点不存在，本次转换终止！铃音编码为：" + this.clrVO.getContentID());
                break;
            }
            Element content = root.getChild("content");
            //获取适配结果码
            result = content.getChild("result").getText();
            if (logger.isDebugEnabled())
            {
                logger.debug("彩铃适配的返回码是 " + result);
            }
            //如果适配结果码不是100，表明适配失败，处理下一个转换
            if (!"100".equals(result))
            {
                convertLog.error("彩铃转换失败，UCAP返回的转换码是：" + result + "，本次转换终止！铃音编码为：" + this.clrVO.getContentID());
                break;
            }
            String sid = content.getChild("sid").getText().trim();
            String size = content.getChild("size").getText().trim();
            String type = content.getChild("type").getText().trim();
            String http = content.getChild("http").getText().trim();
            if (logger.isDebugEnabled())
            {
                logger.debug("彩铃适配结果的返回值 sid = " + sid);
                logger.debug("彩铃适配结果的返回值 size = " + size);
                logger.debug("彩铃适配结果的返回值 type = " + type);
                logger.debug("彩铃适配结果的返回值 http = " + http);
            }
            if ("".equals(http))
            {
                convertLog.error("彩铃转换失败，UCAP返回的http为空！对应的铃音编码为：" + this.clrVO.getContentID());
                break;
            }
            BufferedInputStream bin = null;
            HttpURLConnection httpConn = null;
            try
            {
                URL url = new URL(http);
                httpConn = (HttpURLConnection)url.openConnection();
                //连接httpConn
                httpConn.connect();
                //得到输入流
                bin = new BufferedInputStream(httpConn.getInputStream());
            }
            catch (Exception e)
            {
                convertLog.error("从适配后的url：" + http + "读取内容时失败！对应的铃音编码为：" + this.clrVO.getContentID());
                break;
            }
            if (logger.isDebugEnabled())
            {
                logger.debug("从内容适配平台返回的url获取输入流成功，启动写入资源服务器操作！");
            }
            if (!"audio".equalsIgnoreCase(type))
            {
                convertLog.error("彩铃转换失败，UCAP转换成功后返回的type == " + type + "，类型有误！对应的铃音编码为：" + this.clrVO.getContentID());
                break;
            }
            
            //如果是转换成aac格式的，需要记录彩铃的播放时长
            if (sid.equals(this.clrVO.getContentID() + ".aac"))
            {
                this.clrVO.setAverageMark(Integer.parseInt(size));
            }
            
            try
            {
                if (logger.isDebugEnabled())
                {
                    logger.debug("开始往资源服务器上放置转换后的试听文件 sid = " + sid);
                }  
                ftp.put(bin, sid, false);
                if (logger.isDebugEnabled())
                {
                    logger.debug("彩铃试听文件 " + sid + " 保存到资源服务器成功！");
                }
                //能走到这里说明转换成功了
                convertResult[i] = true;
            }
            catch (Exception e)
            {
                convertLog.error(new StringBuffer().append("ftp保存到资源服务器 ")
                                                   .append(this.storePath)
                                                   .append(File.separator)
                                                   .append(sid)
                                                   .append(" 失败！对应的铃音编码为：")
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
            convertLog.error("退出ftp连接时发生异常！");
        }
        try
        {            
            for (int i = 0; i < length; i++)
            {
                totalResult = totalResult && convertResult[i];
                //如果有一个是转换失败，结果就是失败
                if (!totalResult)
                {
                    break;
                }
            }
            convertLog.error("所有彩铃类型的转换结果是" + totalResult);
            //如果所有类型都转换成功了，更改彩铃的终端试听属性
            if (totalResult)
            {
                this.clrVO.save(); 
            }
        }
        catch (BOException e)
        {
            convertLog.error("更新彩铃属性时发生数据库异常！对应的铃音编码为：" + this.clrVO.getContentID(), e);
        }
    }
    
    /**
     * 构造UCAP适配访问xml
     * @return
     */
    private String[] generateXML() throws Exception
    {
        String encode = "UTF-8";
        int count = this.code_abps.length;
        //构造访问UCAP平台的xml数据数组
        String[] content = new String[count];
        for (int i = 0; i < count; i++)
        {
            // 字符串缓冲生成XML文件
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
                logger.debug("构造的xml数据为：");
                logger.debug(content[i]);
            }
        }
        return content;
    }
    
    /**
     * 获取资源服务器ftp连接
     * 
     * @return
     * @throws FTPException 
     * @throws IOException 
     * @throws BOException
     */
    private FTPClient getResourceServerFtp() throws IOException, FTPException
    {
        // 从配置项读取ftp连接的地址
        String sourceServerIP =ColorringConfig.sourceServerIP;// ColorringConfig.get("sourceServerIP");
        // 从配置项读取ftp连接传输的端口号
        int sourceServerPort = ColorringConfig.sourceServerPort;//Integer.parseInt(ColorringConfig.get("sourceServerPort"));
        // 从配置项读取FTP服务器的登录用户名
        String sourceServerUser = ColorringConfig.sourceServerUser;//ColorringConfig.get("sourceServerUser");
        // 从配置项读取FTP服务器的登录密码
        String sourceServerPassword = ColorringConfig.sourceServerPassword;//ColorringConfig.get("sourceServerPassword");

        FTPClient ftp = new FTPClient(sourceServerIP, sourceServerPort);
        // 初始化ftp连接模式（FTPConnectMode.PASV或者FTPConnectMode.ACTIVE）
        ftp.setConnectMode(FTPConnectMode.PASV);
        ftp.login(sourceServerUser, sourceServerPassword);
        // 设置文件传输类型（FTPTransferType.ASCII或者FTPTransferType.BINARY）
        ftp.setType(FTPTransferType.BINARY);
        return ftp;
    }
    
    /**
     * 在ftp服务器上创建文件夹
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
            logger.debug("ftp资源服务器上彩铃存放目录 remotePath == " + remotePath);
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
                    convertLog.error(paths[i] + " 目录不存在，需要创建！");
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
                        convertLog.error(paths[i] + " 目录已经由其他线程创建！");
                    }
                    ftp.chdir(paths[i]);
                }
            }
        }
    }
}
