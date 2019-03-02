
package com.aspire.ponaadmin.webpps.common.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.aspire.common.exception.BOException;
import com.aspire.ponaadmin.common.usermanager.UserManagerBO;
import com.aspire.ponaadmin.common.usermanager.UserSessionVO;
import com.aspire.ponaadmin.common.usermanager.UserVO;
import com.aspire.ponaadmin.web.channelUser.bo.ChannelBO;
import com.aspire.ponaadmin.web.channelUser.vo.ChannelVO;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.webpps.common.constant.Constant;
import com.aspire.ponaadmin.webpps.common.helper.BbsSSOHelper;
import com.aspire.ponaadmin.webpps.common.helper.MmSsoHelper;

/**
 * 开发者社区管理中心登录过滤器
 */
public class BBSSsoAuthorizeFilter implements Filter
{
    private static final Logger logger = Logger.getLogger(BBSSsoAuthorizeFilter.class);
    

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {

        HttpServletRequest httpRequest = ( HttpServletRequest ) request;
        HttpServletResponse httpResponse = (HttpServletResponse)response; 
        // 得到访问的URL和参数MAP
        String contextPath = httpRequest.getContextPath();
        String requestUri = httpRequest.getRequestURI();
        String addressUri = StringUtils.substringAfter(requestUri, contextPath);
        //合作商id
        String channelsId = null;
        //本地登录，不用校验token
        if(addressUri.contains("/web/channelUser/index.jsp")
        		||addressUri.contains("/web/channelUser/channelLogin.do")
        		||addressUri.contains("/web/channelUser/RSAPublicKey.do")
        		||addressUri.contains("/web/common/error.jsp")){
        	chain.doFilter(request, response);
        	return;
        }
        // 从参数中得到令牌和用户标识
        String contentType = request.getContentType(); 
        if ((contentType !=null) && contentType.startsWith("multipart/form-data")){
        	chain.doFilter(request, response);
            return; 
        }

        String token = httpRequest.getParameter("UT");
        String uid = httpRequest.getParameter("PUID");
        logger.debug("#######社区传过来的token参数：UT=" + token);
        logger.debug("#######社区传过来的BUID参数：PUID=" + uid);
        
        if (token != null)
        {
        	String buid = null;
            // 如果携带有令牌信息，必须做验证
            try
            {
                buid = MmSsoHelper.validateToken(BbsSSOHelper.getAuthorizeUrl(),BbsSSOHelper.getBPID(),token, uid);
                logger.debug("buid===" + buid);
            }
            catch (Exception e)
            {
                logger.error("验证令牌 失败。", e);
                channelsId = null;
            }
            
            //根据社区ID向管理中心获取合作商ID
            try
            {
            	// 根据社区ID向管理中心获取合作商ID
                String queryChannelsAPUrl = BbsSSOHelper.getQueryChannelsAPUrl();
                logger.debug("queryChannelsAPUrl:"+queryChannelsAPUrl);
                String result =  sendGet(queryChannelsAPUrl, "UID="+buid);
                String ss[] = result.split("=");
                channelsId = ss[1].trim();
                logger.debug("根据社区ID向管理中心获取channelsId===" + channelsId);
                setBBsLogon(httpRequest, channelsId);
            }
            catch (Exception e)
            {
                logger.error("管理中心获取合作商ID 失败。", e);
                channelsId = null;
            }
        }
        else
        {
            logger.debug("如果没有携带令牌，检查在SESSION中是否有登录标志!");
            channelsId = getBBsLogonChannelsId(httpRequest);
        }

        if (!StringUtils.isEmpty(channelsId))
        {
            logger.debug("鉴权成功,继续下一个Filter处理!");
            chain.doFilter(request, response);
            return;
        }
        else
        {
            logger.debug("用户未登陆!~");
            saveMessagesValue(httpRequest, "用户未登陆，请登陆后再试！");
            // 跳转到登录页
    		httpResponse.sendRedirect(BbsSSOHelper.getLoginUrl());     
    		return;
            /*request.getRequestDispatcher("/web/common/error.jsp").forward(request, response);
            return;*/
        }

    }

    /**
     * 设置BBS登录
     * 
     * @param request
     * @param developerid
     * @throws BOException 
     */
    public void setBBsLogon(HttpServletRequest request, String apId) throws BOException
    {

        HttpSession session = request.getSession(true);
      //把用户的有关信息都保存到UserSessionVO对象中，并保存到session中
        UserSessionVO userSession = new UserSessionVO();
        ChannelVO channel = ChannelBO.getInstance().getChannelByChannelsId(apId);
        userSession.setChannel(channel);
        UserVO user = new UserVO();
        user.setUserID(channel.getChannelsId());
        user.setName(channel.getChannelsName());
        userSession.setUser(user);
        UserManagerBO.getInstance().setUserSessionVO(request.getSession(), userSession);
        session.setAttribute(Constant.TOUCHSPOT_APID, apId);
    }

    /**
     * 设置BBS登录
     * 
     * @param request
     * @param developerid
     */
    public String getBBsLogonChannelsId(HttpServletRequest request)
    {

        HttpSession session = request.getSession(true);
        return session.getAttribute(Constant.TOUCHSPOT_APID) == null?null:(String) session.getAttribute(Constant.TOUCHSPOT_APID) ;
    }
    
    /**
     * 向指定URL发送GET方法的请求
     */
    public static String sendGet(String url, String param)
    {

        String result = "";
        BufferedReader in = null;
        try
        {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();

            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null)
            {
                result += line;
            }
        }
        catch (Exception e)
        {
        	logger.error("发送GET请求出现异常！",e);
        }
        // 使用finally块来关闭输入流
        finally
        {
            try
            {
                if (in != null)
                {
                    in.close();
                }
            }
            catch (Exception e2)
            {
                e2.printStackTrace();
            }
        }
        return result;
    }

    public void init(FilterConfig config) throws ServletException
    {

    }

    public void destroy()
    {

    }

    /**
     * add a message value to the message vector
     * @param request http请求
     * @param msgValue 信息内容
     */
    protected void saveMessagesValue (HttpServletRequest request,
                                      String msgValue)

    {
        if ((msgValue == null) || msgValue.equals(""))
        { //no key ,just return
            return ;
        }
        Vector msgs = (Vector) request.getAttribute(Constants.
                                                    REQ_KEY_MESSAGE) ;
        if (msgs == null)
        {
            msgs = new Vector() ;
        }
        if (!msgs.contains(msgValue))
        { //donot add repeated
            msgs.add(msgValue) ;
        }
        request.setAttribute(Constants.REQ_KEY_MESSAGE, msgs) ;
    }
    
}
