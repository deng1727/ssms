package com.aspire.mm.common.client.mportal.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.aspire.mm.common.client.mportal.MportalHelper;
import com.aspire.mm.common.client.mportal.vo.StaffVO;
import com.aspire.ponaadmin.common.usermanager.UserManagerBO;
import com.aspire.ponaadmin.common.usermanager.UserSessionVO;
import com.aspire.ponaadmin.common.usermanager.UserVO;

public class SubsystemAuthorizeFilter implements Filter {
    /*** logger.*/
    private static final Logger logger = Logger.getLogger(SubsystemAuthorizeFilter.class);

    /***令牌. */
    public static final String MPORTAL_TOKEN = "MT";
    
    
    /***登录用户VO. */
    public static final String LOGIN_STAFF = "LOGIN_STAFF";
    private static final String actionRegx = "^MT=.*?(?=$|&)";
    
    private static final String MTRegx = "MT=.*?(?=$|&)";
    
    
    public static final String SUCCESS = "0";
    
    public static final String EXPIRE_TOKEN = "101";
    public static final String AUTH_FAILED = "102";
    
    public static final String REFERER = "REFERER";
    
    
	public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
    	HttpServletRequest httpRequest = (HttpServletRequest)request;
    	HttpServletResponse httpResponse = (HttpServletResponse)response;    	
        HttpSession session = httpRequest.getSession(true);
        String loginName = null;
        
     // 得到访问的URL和参数MAP
        String contextPath = httpRequest.getContextPath();
        String requestUri = httpRequest.getRequestURI();
        String addressUri = StringUtils.substringAfter(requestUri, contextPath);
      //渠道商，无需鉴权步骤，直接跳出过滤器
        if(addressUri.contains("/web/channelUser/")||addressUri.contains("/web/common/")){
//        	System.out.println("正常：addressUri="+addressUri);
        	// 从 session 中得到 csrftoken 属性
        	 String sToken = (String)session.getAttribute("csrftoken"); 
        	 if(sToken == null){

        	    // 产生新的 token 放入 session 中
        		 SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        		 Random random = new Random();
        	    sToken = format.format(new Date())+random.nextInt(1000); 
        	    session.setAttribute("csrftoken",sToken); 
        	    chain.doFilter(request, response); 
        	    return;
        	 } else{
        		 if(addressUri.contains("/web/channelUser/channelLogin.do")||addressUri.contains("/web/channelUser/exit.jsp")||addressUri.contains("/web/channelUser/updateChannelPwd.do")){
        			// 从 HTTP 头中取得 csrftoken 
             	    String xhrToken = httpRequest.getHeader("csrftoken"); 
             	    // 从请求参数中取得 csrftoken 
             	    String pToken = httpRequest.getParameter("csrftoken"); 
//             	    System.out.println("addressUri="+addressUri+";sToken="+sToken+";xhrToken="+xhrToken+";pToken="+pToken);
             	    if(sToken != null && xhrToken != null && sToken.equals(xhrToken)){ 
             	        chain.doFilter(request, response); 
             	        return;
             	    }else if(sToken != null && pToken != null && sToken.equals(pToken)){ 
             	        chain.doFilter(request, response); 
             	        return;
             	    }else{
             	    	httpResponse.sendRedirect(contextPath+"/web/channelUser/index.jsp");
             	    	return;
             	    }
        		 }else{
        			chain.doFilter(request, response); 
         	        return;
        		 }
        	    
        	 }
        }
//        System.out.println("***************非正常：addressUri="+addressUri+"***************");
        //System.out.println(session.getId());
        // 得到上次用户访问的令牌和本次访问的令牌
        String latestToken = (String)session.getAttribute(MPORTAL_TOKEN);
        //String token = (String)httpRequest.getParameter(MPORTAL_TOKEN);  
		String referer = httpRequest.getHeader("Referer");
        String query = httpRequest.getQueryString();
        String token = null;
        
        String loginUrl = MportalHelper.getLoginUrl();
        /*** 缺省未授权提醒页面 */
        String authErrorUrl = MportalHelper.getAuthErrorUrl();
        String type = httpRequest.getHeader("X-Requested-With");
        
        if(query!=null){
        	int n = query.indexOf("MT=");
        	if(n!=-1){
        		token = query.substring(n+3,n+35);
        		
        	}
//            //比较常见
//            //select * from t_pageuri这个表中，例子如下
//            // /web/resourcemgr/categoryDel.do
//            // /web/resourcemgr/categoryEdit.do?action=update
//            // /web/resourcemgr/categoryEdit.do?action=new
//            Pattern pattern = Pattern.compile(actionRegx);
//            Matcher matcher = pattern.matcher(query);
//            if(matcher.find()) {
//            	uri+='?'+matcher.group();
//            }
            //from的情况很少啊！！
//        	Pattern pattern = Pattern.compile(MTRegx);
//        	Matcher matcher = pattern.matcher(query);
//        	
//            if(matcher.find()) {
//            	token = matcher.group();
//            	if(token.indexOf("=")!=-1){
//            		token = token.substring(token.indexOf("=")+1);
//            	}
//            	
//            }
        }
        
       // System.out.println(latestToken);
        //logger.info("aiyan:token");
        //logger.info("token:"+token);
        boolean isLogin = false;        
        if (session !=null ){
            // 用户第一次推带token访问时，到统一门户查询用户信息
            if (token != null && !token.equals(latestToken)) {
                Map resultMap = MportalHelper.userQuery(token);
                if ("0".equals(resultMap.get("hRet"))) {
                    // 查询成功, 写入会话
                    StaffVO staff = (StaffVO)resultMap.get("staff");
                    session.setAttribute(LOGIN_STAFF, staff);
					session.setAttribute(REFERER, referer);
					
                    UserSessionVO userSession = new UserSessionVO();
                    UserVO user=new UserVO();
                    user.setUserID(staff.getLoginName());
                    user.setName(staff.getRealName());
                    userSession.setUser(user);
                    //userSession.setAccessInfo(accessInfo);
                   // userSession.setRights(rightList);
                    UserManagerBO.getInstance().setUserSessionVO(session, userSession);
                } else {  
                	session.removeAttribute(LOGIN_STAFF);
                }
            }
			
			
			
			//检测refer
			String origionRefer = (String) session.getAttribute(REFERER);
	        if (logger.isDebugEnabled()) {
	        	logger.debug("Get Referer from session, origionRefer="+origionRefer);
	        }
//	        if (StringUtils.isBlank(referer) && StringUtils.isBlank(origionRefer)) {
//			if (StringUtils.isBlank(referer)) {
//				if (logger.isDebugEnabled()) {
//					logger.debug("referer is not found, then subsystem redirect to loginUrl="+loginUrl);
//				}
//				if (ajaxRequestProcess(httpResponse, EXPIRE_TOKEN, type, loginUrl)) {
//					return;
//				}
//				// 跳转到登录页
//				if (isFullHttpUrl(loginUrl)) { // 此判断是为了兼容原有的固定地址跳转
//					httpResponse.sendRedirect(loginUrl);
//				} else {
//					request.getRequestDispatcher(loginUrl).forward(request,
//							response);
//				}
//				return;
//			
//			}

            
            // 如果会话中有用户信息， 用户为登录状态
            if (session.getAttribute(LOGIN_STAFF) != null) {
            	isLogin = true;
            }
            
            // 记录本次访问TOKEN
            if (token !=  null) {
            	session.setAttribute(MPORTAL_TOKEN, token);
            }
        }
        
        
        Map params = request.getParameterMap();
        Map destParms = new HashMap();
        
        // 如果有多个同名参数,取第一个
        Iterator it= params.keySet().iterator();
        while(it.hasNext()){ 
        	String key=(String)it.next();
            if(params.get(key)!=null){
            	String value =((String[])params.get(key))[0];
                destParms.put(key, StringEscapeUtils.escapeXml(value));
            }
        }
        
        // 如果没有令牌, 以上次访问令牌鉴权
//        
//        destParms.put("subSystem", "ssms");//add by aiyan 2012-12-28
//        
//        // add by wml 2013-1-4
//        if ("/web/singlecategory/singleCategoryTreeAction.do"
//				.equals(addressUri)
//				|| "/web/singlecategory/singleCgyContentTreeAction.do"
//						.equals(addressUri))
//        {
//        	String userId = "";
//        	
//        	if (query != null) {
//				int num = query.indexOf("userId=");
//				String temp = query.substring(num + 7);
//				if (num != -1) {
//					if(temp.indexOf("&") != -1)
//					{
//						userId = temp.substring(0, temp.indexOf("&"));
//					}
//					else
//					{
//						userId = temp;
//					}
//				}
//			}
//        	
//			destParms.put("userId", userId);
//		}
        
        
        if (token == null) {
        	token = latestToken;
        }  
        
        //  地址鉴权   
        Map resultMap = MportalHelper.authorize(token, addressUri, destParms);
        
        
        if ("0".equals(resultMap.get("hRet"))) {
            // 鉴权成功
            chain.doFilter(request, response);
            return;        	
        } else {
        	if (isLogin) {
        		// 跳转到鉴权出错页
        		httpResponse.sendRedirect(authErrorUrl);      
        		 return;      
        	} else {
        		// 跳转到登录页
        		httpResponse.sendRedirect(loginUrl);     
        		 return;      
        	}
        }
    }
    
    
    private boolean ajaxRequestProcess(HttpServletResponse httpResponse, 
    		String retCode, String type, String loginUrl) throws IOException {
        if ("XMLHttpRequest".equalsIgnoreCase(type)) 
        {
        	// AJAX REQUEST PROCESS
        	httpResponse.setHeader("sessionstatus", retCode);
        	PrintWriter printWriter = httpResponse.getWriter();
        	JSONObject result = new JSONObject();
        	result.put("ret", retCode);
        	result.put("url", loginUrl);
            printWriter.print(result);  
            printWriter.flush();  
            printWriter.close();
            return true;
        }  else {
			return false;
		}
    }
    
    private boolean isFullHttpUrl(String url) {
    	if (StringUtils.isEmpty(url)) {
    		logger.error("please check mportal config, some url is empty!");
    		return false;
    	}
    	if (url.indexOf("http://") >= 0) {//此判断是为了兼容原有的固定地址跳转
    		return true;
    	} else {
    		return false;
		}
    }

    public void init(FilterConfig filterConfig) throws ServletException {

    }
    
    public static void main(String[] argv){
    	String a= "/web/resourcemgr/categoryEdit.do?MT=update";
    	
    	String b="/ssms/web/queryapp/QueryAppAction.do?opType=doQueryAddList&MT=E587B5BE7FF0FFE739B117965C3BF003&jid=1030344978";
    	String c="/ssms/web/singlecategory/singleCategoryTreeAction.do?userId=all_category_class&MT=E587B5BE7FF0FFE739B117965C3BF003&jid=1030344978";
//        if(query!=null){
//          //比较常见
//          //select * from t_pageuri这个表中，例子如下
//          // /web/resourcemgr/categoryDel.do
//          // /web/resourcemgr/categoryEdit.do?action=update
//          // /web/resourcemgr/categoryEdit.do?action=new
//          Pattern pattern = Pattern.compile(actionRegx);
//          Matcher matcher = pattern.matcher(query);
//          if(matcher.find()) {
//          	uri+='?'+matcher.group();
//          }
          //from的情况很少啊！！
//      	Pattern pattern = Pattern.compile(MTRegx);
//      	Matcher matcher = pattern.matcher(c);
//      	String token = "---";
//          if(matcher.find()) {
//          	token = matcher.group();
//          	if(token.indexOf("=")!=-1){
//          		token = token.substring(token.indexOf("=")+1);
//          	}
//          	
//          }
//      //}
    	String token = null;
        if(c!=null){
        	int n = c.indexOf("MT=");
        	if(n!=-1){
        		token = c.substring(n+3,n+35);
        		
        	}
          
          System.out.println(token);
    	
    }
    }
    }
