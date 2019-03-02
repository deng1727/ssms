package com.aspire.dotcard.baseVideo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseVideo.conf.BaseVideoConfig;
import com.aspire.dotcard.baseVideo.dao.BaseVideoFileDAO;
import com.aspire.dotcard.syncData.dao.DataSyncDAO;
import com.aspire.ponaadmin.web.util.HttpUtil;

/**
 * 节目下架通知(产品基地调用) 接口描述 请求使用HTTP GET方法，返回XML格式的响应数据。 视频基地调用MM基地节目下架通知。
 * http://localhost:7001/ponaadmin/video/deleteProgram?programid=1111
 */
public class BaseVideoProgramServlet extends HttpServlet
{

    private static final long serialVersionUID = -5226172620353411362L;

    private static final JLogger LOG = LoggerFactory.getLogger(BaseVideoProgramServlet.class);

    private String[] urlIPConfig;

    private String[] urlPortalConfig;

    private String[] phoneList;

    
    private String wapProcedureName;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

        String requestIP = request.getRemoteAddr();
        String programId = request.getParameter("programid");
        String message = "";
        
        boolean isAssignationIP = false;
        boolean delProgram = false;

        // 判断接入IP是否为指定队列中的IP
        for (int i = 0; i < urlIPConfig.length; i++)
        {
            String tempIP = urlIPConfig[i];
            if (requestIP.indexOf(tempIP) != -1)
            {
                isAssignationIP = true;
                break;
            }
        }

        // 如果不是指定IP,接口不处理,返回失败
        if (!isAssignationIP)
        {
            message = "接入的IP：" + requestIP + " 非指定队列IP. ";
            if(LOG.isDebugEnabled()){
                LOG.debug(message);
            }
            // 返回响应xml
            sendMessage(message, response);
            return;
        }

        // 如果沒有节目programId,接口不处理,返回失败
        if (StringUtils.isBlank(programId))
        {
            message = "当前请求没有带参数。接入IP为：" + requestIP;
            if(LOG.isDebugEnabled()){
                LOG.debug(message);
            }
            // 返回响应xml
            sendMessage(message, response);
            return;
        }

        // 删除处理这个progranmID的数据
        delProgram = delProgramById(programId);
        
        //如果删除成功
        if (delProgram)
        {
            // 通知几个门户
            for (int i = 0; i < urlPortalConfig.length; i++)
            {
                String tempPortalURL = urlPortalConfig[i];
                if (StringUtils.isNotBlank(tempPortalURL))
                {
                    tempPortalURL = tempPortalURL + "?programid=" + programId;
                   
                    sendPortalIP(tempPortalURL);
                }
            }

            
            if (StringUtils.isNotBlank(wapProcedureName))
            {
                callFunction(wapProcedureName, programId);
            }
            
            message = "";
            
        }
        else
        {// 如果删除失败，短信通知相关人员
            message = "IP:" + requestIP + " 发送请求删除节目:" + programId + "执行失败!";
            if (LOG.isDebugEnabled())
            {
                LOG.debug(message);
            }
            sendMsg(message);
        }
        
        // 返回响应xml
        sendMessage(message, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        this.doGet(request, response);
    }

    /**
     * 返回用户请求响应
     * 
     * @param delProgram
     *            删除是否成功
     */
    private void sendMessage(String message, HttpServletResponse response) throws IOException
    {
        response.setContentType("text/xml; charset=UTF-8");
        // response.setHeader("Cache-Control", "no-cache");
        // response.setHeader("Pragma", "no-cache");
        // response.setDateHeader("Expires", 0);

        PrintWriter out = response.getWriter();

        StringBuilder xml = new StringBuilder(200);
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append("<return>");

        // 加上你要发送的xml
        xml.append("<messagecode>");
        if (StringUtils.isBlank(message))
        {
            xml.append("000000");
        }
        else
        {
            xml.append("999999");
        }
        xml.append("</messagecode>");
        xml.append("<messageinfo>");
        if (StringUtils.isBlank(message))
        {
            xml.append("操作成功");
        }
        else
        {
            xml.append(message);
        }
        xml.append("</messageinfo>");
        xml.append("</return>");
        
//        if (LOG.isDebugEnabled())
//        {
//            LOG.debug("返回用户请求响应：" + xml.toString());
//        }
        
        out.print(xml.toString());

        
        xml.delete(0, xml.length()-1);
        xml = null;
        out.close();
        
    }

    /**
     * 用来删除指定节目详情。其中包括直播节目单，商品信息，节目详情。
     * 
     * @param programId
     */
    private boolean delProgramById(String programId)
    {
        try
        {
            BaseVideoFileDAO.getInstance().delProgramById(programId);
            return true;
        }
        catch (BOException e)
        {
            LOG.error("执行删除指定节目详情时发生数据库异常。。。" + e);
            return false;
        }
    }

    /**
     * 通知门户方法
     * 
     * @param url
     *            门户url
     * @return
     */
    private void sendPortalIP(String url)
    {
        int code = 0;

        if (LOG.isDebugEnabled())
        {
            LOG.debug("通知门户开始" );
        }
        
        try
        {
            code = HttpUtil.getResponseCodeFromURL(url, "utf-8");
          
        }
        catch (Exception e)
        {
            LOG.error("通知门户方法调用失败");
        }

        if (code == 200)
        {
            if (LOG.isDebugEnabled())
            {
                LOG.debug("通知门户：url=" + url + " 成功. ");
            }
        }
        else
        {
            if (LOG.isDebugEnabled())
            {
                LOG.debug("通知门户：url=" + url + " 失败. ");
            }
        }
    }

    /**
     * 当删除指定节目失败时，用来发短信知会相关人员
     * 
     * @param content
     */
    private void sendMsg(String content)
    {
        DataSyncDAO dao = DataSyncDAO.getInstance();
        for (int i = 0; i < phoneList.length; i++)
        {
            try
            {
                dao.sendMsg(phoneList[i], content);// 发送短信
            }
            catch (DAOException e)
            {
                LOG.error("删除指定节目中，给手机" + phoneList[i] + "发送短信失败！" + e);
            }
        }
    }

    
    /**
     * wap执行函数
     * 
     * @param functionName，programId
     */
    private int callFunction(String functionName, String programId)
    {
        Connection conn = null;
        CallableStatement cs = null;
        String errorMessage;
        int status = -1;
        // 执行存储过程。
        if (LOG.isDebugEnabled())
        {
            LOG.debug("开始执行函数：" + functionName);
        }

        try
        {
            conn = DB.getInstance().getConnection();
            if (StringUtils.isNotBlank(programId))
            {
                cs = conn.prepareCall("{?="+functionName+"}");
                cs.setString(2, programId);
                cs.registerOutParameter(1, Types.INTEGER);
                cs.execute();
                status = cs.getInt(1); //获取函数返回结果
                
                if (LOG.isDebugEnabled())
                {
                    LOG.debug("调用函数：" + functionName+" 成功，执行状态：" + status);
                }
            }
        }
        catch (Exception e)
        {
            status = -1;
            errorMessage = "调用函数" + functionName + "失败,执行状态：" + status + ",请立刻联系管理员！！！";
            LOG.error(errorMessage, e);
            
        }
        finally
        {
            if (cs != null)
            {
                try
                {
                    cs.close();
                }
                catch (Exception ex)
                {
                    LOG.error("关闭CallableStatement=失败", ex);
                }
            }
            if (conn != null)
            {
                try
                {
                    conn.close();
                }
                catch (Exception e)
                {
                    LOG.error("关闭conn=失败", e);
                }
            }
        }
        return status;
    }

    /**
     * 初始化,加载配置信息
     */
    public void init(ServletConfig config) throws ServletException
    {
        // 允许访问的Ip
        urlIPConfig = BaseVideoConfig.ipListConfig.split("\\|");
        // 通知门户地址
        urlPortalConfig = BaseVideoConfig.urlPortalConfig.split("\\|");
        // 短信通知号码
        phoneList = BaseVideoConfig.phoneList.split("\\|");

        //执行wap存储过程
        wapProcedureName = BaseVideoConfig.wapProcedureName;
        
    }

}
