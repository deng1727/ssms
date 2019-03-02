package com.aspire.ponaadmin.web.rightmanager ;

import java.io.IOException ;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter ;
import javax.servlet.FilterChain ;
import javax.servlet.FilterConfig ;
import javax.servlet.ServletException ;
import javax.servlet.ServletRequest ;
import javax.servlet.ServletResponse ;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.usermanager.UserSessionVO;
import javax.servlet.http.HttpServletRequest;
import com.aspire.ponaadmin.web.constant.ResourceConstants;
import com.aspire.ponaadmin.web.BaseAction;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import javax.servlet.RequestDispatcher;
import com.aspire.ponaadmin.common.usermanager.UserManagerBO;
import com.aspire.ponaadmin.common.rightmanager.RightManagerConstant;
import com.aspire.ponaadmin.common.rightmanager.RightManagerBO;
import com.aspire.ponaadmin.common.rightmanager.RightVO;
import com.aspire.ponaadmin.web.system.SystemInfo;

/**
 * <p>Ȩ�޿��Ƶ�filter��</p>
 * <p>Ȩ�޿��Ƶ�filter��</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class RightCheckerFilter extends BaseAction
    implements Filter
{

    /**
     * ��־����
     */
    private static final JLogger LOG = LoggerFactory.getLogger(RightCheckerFilter.class);

    /**
     * δ��¼��ҳ�����
     */
    private static final String LOGIN_PAGE = "/web/notLogin.do";
    
    //add by aiyan 2012-11-28
    private static final String actionRegx = "^action=.*?(?=$|&)";
    private static final String fromRegx = "(?<=^&?)from=.*?(?=$|&)";

    /**
     * destroy
     *
     * @todo Implement this javax.servlet.Filter method
     */
    public void destroy ()
    {
    }

    /**
     * doFilter
     *
     * @param servletRequest ServletRequest
     * @param servletResponse ServletResponse
     * @param filterChain FilterChain
     * @throws IOException
     * @throws ServletException
     * @todo Implement this javax.servlet.Filter method
     */
    public void doFilter (ServletRequest servletRequest,
                          ServletResponse servletResponse,
                          FilterChain filterChain)
        throws IOException, ServletException
    {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse rsp = (HttpServletResponse) servletResponse;
        String context = req.getContextPath();
        String uri = req.getRequestURI();
//        String codetemplateuri = req.getRequestURI();
//        String tag = req.getParameter("tag");
//        if(tag == null || "".equals(tag))
//        {
//            tag = (String)req.getAttribute("tag");
//        }
//        if(tag != null && !tag.trim().equals(""))
//        {
//            codetemplateuri += "?tag="+tag;
//        }
        //������ڻ�ȡwebӦ�õ������ģ����ַ����Ƚ�����������ʱû�и��õİ취��
        if(SystemInfo.getInstance().getWebAppContext()==null)
        {
            SystemInfo.getInstance().setWebAppContext(context);
        }
        
        
        //���Ȼ�ȡsesson������û���Ϣ
        UserSessionVO userSession = UserManagerBO.getInstance().getUserSessionVO(
            req.getSession()) ;
      //2013-01-07
//        if (userSession == null)
//        {
//            if ((uri.indexOf("/web/notLogin.do") == -1) &&
//            		(uri.indexOf("/web/login.do") == -1) &&
//            		(uri.indexOf("web/userCenterLogin.do") == -1) &&
//                (uri.indexOf("/web/usermanager/register.do") == -1) &&
//                (uri.indexOf("/web/usermanager/registerInfo.do") == -1))
//            {
//                //û�е�¼�����û�ȥ��¼
//                LOG.debug("�û�δ��¼��") ;
//                rsp.sendRedirect(context + LOGIN_PAGE) ;
//                return ;
//            }
//        }
        //2013-01-07
//        String actionPara = req.getParameter(RightManagerConstant.URL_PARA_ACTION);
//        if(actionPara != null && !actionPara.trim().equals(""))
//        {
//            uri += '?'+RightManagerConstant.URL_PARA_ACTION+'='+actionPara;
//        }
//        String fromPara = req.getParameter(RightManagerConstant.URL_PARA_FROM);
//        if(fromPara != null && !fromPara.trim().equals(""))
//        {
//            if(uri.indexOf("?") != -1)
//            {
//                uri += '&'+RightManagerConstant.URL_PARA_FROM+'='+fromPara;
//            }
//            else
//            {
//                uri += '?'+RightManagerConstant.URL_PARA_FROM+'='+fromPara;
//            }
//        }
        String query = req.getQueryString();
        if(query!=null){
            //�Ƚϳ���
            //select * from t_pageuri������У���������
            // /web/resourcemgr/categoryDel.do
            // /web/resourcemgr/categoryEdit.do?action=update
            // /web/resourcemgr/categoryEdit.do?action=new
            Pattern pattern = Pattern.compile(actionRegx);
            Matcher matcher = pattern.matcher(query);
            if(matcher.find()) {
            	uri+='?'+matcher.group();
            }
            //from��������ٰ�����
            pattern = Pattern.compile(fromRegx);
            matcher = pattern.matcher(query);
            if(matcher.find()) {
            	if(uri.indexOf("?") != -1){
                      uri += '&'+matcher.group();
                }else{
                      uri += '?'+matcher.group();
                }
            }
        }
        
        LOG.debug(uri);
        uri = uri.substring(context.length(), uri.length());
        RightManagerBO rightManager = RightManagerBO.getInstance() ;

        try
        {
            //����û��Ĳ���Ȩ��
            {
                //����uri��ȡȨ�޶�Ӧ��Ȩ��right
                RightVO right = rightManager.getRightOfPageURI(uri);
                //���right��Ϊ�գ�˵���������url��Ҫ��Ȩ
                if(right != null)
                {
                    //��Ҫ��Ȩ��uri
                    if(LOG.isDebugEnabled())
                    {
                        LOG.debug(uri+"����ҳ��ķ�����Ҫ��Ȩ!");
                    }
                    int result = rightManager.checkUserRight(userSession, right.getRightID());
                    if (result == RightManagerConstant.RIGHTCHECK_NO_USERINFO)
                    {
                        //û�е�¼�����û�ȥ��¼
                        LOG.debug("�û�δ��¼��");
                        rsp.sendRedirect(context+LOGIN_PAGE);
                        return;
                    }
                    else if (result == RightManagerConstant.RIGHTCHECK_NO_RIGHT)
                    {
                        //�û�û�����Ȩ��
                        LOG.debug("�û���Ȩ���ʣ�" + uri) ;
                        this.saveMessages(req, ResourceConstants.WEB_INF_NO_RIGHT) ;
                        RequestDispatcher dispatcher = req.getRequestDispatcher(
                            RightManagerConstant.ERROR_PAGE) ;
                        dispatcher.forward(req, rsp) ;
                        return ;
                    }
                }
            }
        }
        catch(Exception e)
       {
            LOG.debug("Ȩ�޼��ʧ�ܣ�");
            LOG.error(e);
            //���쳣��ȥ����ҳ��ɡ�
           this.saveMessages(req, ResourceConstants.WEB_ERR_SYSTEM) ;
            RequestDispatcher dispatcher = req.getRequestDispatcher(
                RightManagerConstant.ERROR_PAGE) ;
            dispatcher.forward(req, rsp) ;
            return ;
        }
        
        if(LOG.isDebugEnabled())
        {
            LOG.debug("Ȩ�޼��ͨ����");
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * init
     *
     * @param filterConfig FilterConfig
     * @throws ServletException
     * @todo Implement this javax.servlet.Filter method
     */
    public void init (FilterConfig filterConfig)
        throws ServletException
    {
    }

    /**
     * ����baseActino�ķ���������������ܱ����ã�
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     */
    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
    {
        throw new RuntimeException("never invoke this method!!!");
    }

}
