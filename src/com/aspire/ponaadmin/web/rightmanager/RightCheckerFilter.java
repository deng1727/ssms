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
 * <p>权限控制的filter类</p>
 * <p>权限控制的filter类</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class RightCheckerFilter extends BaseAction
    implements Filter
{

    /**
     * 日志引用
     */
    private static final JLogger LOG = LoggerFactory.getLogger(RightCheckerFilter.class);

    /**
     * 未登录的页面入口
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
        //这边用于获取web应用的上下文，这种方法比较土，不过暂时没有更好的办法。
        if(SystemInfo.getInstance().getWebAppContext()==null)
        {
            SystemInfo.getInstance().setWebAppContext(context);
        }
        
        
        //首先获取sesson里面的用户信息
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
//                //没有登录，让用户去登录
//                LOG.debug("用户未登录！") ;
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
            //比较常见
            //select * from t_pageuri这个表中，例子如下
            // /web/resourcemgr/categoryDel.do
            // /web/resourcemgr/categoryEdit.do?action=update
            // /web/resourcemgr/categoryEdit.do?action=new
            Pattern pattern = Pattern.compile(actionRegx);
            Matcher matcher = pattern.matcher(query);
            if(matcher.find()) {
            	uri+='?'+matcher.group();
            }
            //from的情况很少啊！！
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
            //检查用户的操作权限
            {
                //根据uri获取权限对应的权限right
                RightVO right = rightManager.getRightOfPageURI(uri);
                //如果right不为空，说明访问这个url需要授权
                if(right != null)
                {
                    //需要鉴权的uri
                    if(LOG.isDebugEnabled())
                    {
                        LOG.debug(uri+"，本页面的访问需要授权!");
                    }
                    int result = rightManager.checkUserRight(userSession, right.getRightID());
                    if (result == RightManagerConstant.RIGHTCHECK_NO_USERINFO)
                    {
                        //没有登录，让用户去登录
                        LOG.debug("用户未登录！");
                        rsp.sendRedirect(context+LOGIN_PAGE);
                        return;
                    }
                    else if (result == RightManagerConstant.RIGHTCHECK_NO_RIGHT)
                    {
                        //用户没有这个权限
                        LOG.debug("用户无权访问：" + uri) ;
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
            LOG.debug("权限检查失败！");
            LOG.error(e);
            //有异常，去错误页面吧。
           this.saveMessages(req, ResourceConstants.WEB_ERR_SYSTEM) ;
            RequestDispatcher dispatcher = req.getRequestDispatcher(
                RightManagerConstant.ERROR_PAGE) ;
            dispatcher.forward(req, rsp) ;
            return ;
        }
        
        if(LOG.isDebugEnabled())
        {
            LOG.debug("权限检查通过。");
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
     * 覆盖baseActino的方法，这个方法不能被调用！
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
