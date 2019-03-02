
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
 * �����������������ĵ�¼������
 */
public class BBSSsoAuthorizeFilter implements Filter
{
    private static final Logger logger = Logger.getLogger(BBSSsoAuthorizeFilter.class);
    

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {

        HttpServletRequest httpRequest = ( HttpServletRequest ) request;
        HttpServletResponse httpResponse = (HttpServletResponse)response; 
        // �õ����ʵ�URL�Ͳ���MAP
        String contextPath = httpRequest.getContextPath();
        String requestUri = httpRequest.getRequestURI();
        String addressUri = StringUtils.substringAfter(requestUri, contextPath);
        //������id
        String channelsId = null;
        //���ص�¼������У��token
        if(addressUri.contains("/web/channelUser/index.jsp")
        		||addressUri.contains("/web/channelUser/channelLogin.do")
        		||addressUri.contains("/web/channelUser/RSAPublicKey.do")
        		||addressUri.contains("/web/common/error.jsp")){
        	chain.doFilter(request, response);
        	return;
        }
        // �Ӳ����еõ����ƺ��û���ʶ
        String contentType = request.getContentType(); 
        if ((contentType !=null) && contentType.startsWith("multipart/form-data")){
        	chain.doFilter(request, response);
            return; 
        }

        String token = httpRequest.getParameter("UT");
        String uid = httpRequest.getParameter("PUID");
        logger.debug("#######������������token������UT=" + token);
        logger.debug("#######������������BUID������PUID=" + uid);
        
        if (token != null)
        {
        	String buid = null;
            // ���Я����������Ϣ����������֤
            try
            {
                buid = MmSsoHelper.validateToken(BbsSSOHelper.getAuthorizeUrl(),BbsSSOHelper.getBPID(),token, uid);
                logger.debug("buid===" + buid);
            }
            catch (Exception e)
            {
                logger.error("��֤���� ʧ�ܡ�", e);
                channelsId = null;
            }
            
            //��������ID��������Ļ�ȡ������ID
            try
            {
            	// ��������ID��������Ļ�ȡ������ID
                String queryChannelsAPUrl = BbsSSOHelper.getQueryChannelsAPUrl();
                logger.debug("queryChannelsAPUrl:"+queryChannelsAPUrl);
                String result =  sendGet(queryChannelsAPUrl, "UID="+buid);
                String ss[] = result.split("=");
                channelsId = ss[1].trim();
                logger.debug("��������ID��������Ļ�ȡchannelsId===" + channelsId);
                setBBsLogon(httpRequest, channelsId);
            }
            catch (Exception e)
            {
                logger.error("�������Ļ�ȡ������ID ʧ�ܡ�", e);
                channelsId = null;
            }
        }
        else
        {
            logger.debug("���û��Я�����ƣ������SESSION���Ƿ��е�¼��־!");
            channelsId = getBBsLogonChannelsId(httpRequest);
        }

        if (!StringUtils.isEmpty(channelsId))
        {
            logger.debug("��Ȩ�ɹ�,������һ��Filter����!");
            chain.doFilter(request, response);
            return;
        }
        else
        {
            logger.debug("�û�δ��½!~");
            saveMessagesValue(httpRequest, "�û�δ��½�����½�����ԣ�");
            // ��ת����¼ҳ
    		httpResponse.sendRedirect(BbsSSOHelper.getLoginUrl());     
    		return;
            /*request.getRequestDispatcher("/web/common/error.jsp").forward(request, response);
            return;*/
        }

    }

    /**
     * ����BBS��¼
     * 
     * @param request
     * @param developerid
     * @throws BOException 
     */
    public void setBBsLogon(HttpServletRequest request, String apId) throws BOException
    {

        HttpSession session = request.getSession(true);
      //���û����й���Ϣ�����浽UserSessionVO�����У������浽session��
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
     * ����BBS��¼
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
     * ��ָ��URL����GET����������
     */
    public static String sendGet(String url, String param)
    {

        String result = "";
        BufferedReader in = null;
        try
        {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // �򿪺�URL֮�������
            URLConnection connection = realUrl.openConnection();
            // ����ͨ�õ���������
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // ����ʵ�ʵ�����
            connection.connect();

            // ���� BufferedReader����������ȡURL����Ӧ
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null)
            {
                result += line;
            }
        }
        catch (Exception e)
        {
        	logger.error("����GET��������쳣��",e);
        }
        // ʹ��finally�����ر�������
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
     * @param request http����
     * @param msgValue ��Ϣ����
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
