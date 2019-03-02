/*
 * 文件名：BaseAction.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  这是所有Action的基类。
 * 修改人： 吴付维
 * 修改时间：2005/06/20
 * 修改内容：初稿
 × 修改人：高宝兵
 × 修改时间：2005/06/20
 × 修改内容：提供公共的doPerform()方法。以及保存提示信息保存功能。
 */
package com.aspire.ponaadmin.web ;

import java.io.IOException;
import java.security.KeyPair;
import java.text.MessageFormat;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.aspire.common.HttpServletRequestParameter;
import com.aspire.common.Validator;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.usermanager.UserManagerBO;
import com.aspire.ponaadmin.common.usermanager.UserSessionVO;
import com.aspire.ponaadmin.common.usermanager.UserVO;
import com.aspire.ponaadmin.web.actionlog.ActionLogBO;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.constant.ResourceConstants;
import com.aspire.ponaadmin.web.constant.ResourceUtil;
import com.aspire.ponaadmin.web.datafield.UploadFileKeyResUtil;
import com.aspire.ponaadmin.web.datafield.vo.ResourceVO;
import com.aspire.ponaadmin.web.newmusicsys.action.FileForm;
import com.aspire.common.util.RSAUtil;

/**
 * <p>Title: Action基类</p>
 * <p>Description: 所有webportal的Action必须基于本类。并提供公共的保存后台提示信息
 * 前台只需要调用tag<bt:messagelist>即可。以及以后需要公共处理的逻辑也可以放在此类。
 * 各子Action类需要继承并实现doPerform方法即可。</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: ASPire TECHNOLOGIES (SHENZHEN) LIMITED</p>
 * @author gaobaobing
 * @version 1.0.0.0
 */

public abstract class BaseAction
    extends DispatchAction
{

    /**
     * 日志引用
     */
    private static final JLogger LOG = LoggerFactory.getLogger(BaseAction.class) ;

    /**
     * 入口方法。各种公共需要处理的逻辑可以在里面执行。为以后扩展用。
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws IOException
     * @throws ServletException
     */
    public ActionForward execute (ActionMapping mapping, ActionForm form,
                                  HttpServletRequest request,
                                  HttpServletResponse response)
        throws
        IOException, ServletException
    {
        String forward = Constants.FORWARD_COMMON_FAILURE ; //转向错误处理页
        try
        {
        	
        	HttpServletRequestParameter requestParameter = new HttpServletRequestParameter(request); 
            //return doPerform(mapping, form, request, response) ;
        	return doPerform(mapping, form, requestParameter, response) ;
        }
        catch (BOException e)
        {
            this.saveMessages(request, ResourceConstants.WEB_ERR_SYSTEM) ;
            LOG.error(e) ;
        }
        catch (Exception e)
        {
            this.saveMessages(request, ResourceConstants.WEB_ERR_SYSTEM) ;
            LOG.error(e) ;
        }
        return mapping.findForward(forward) ;
    }

    /**
     * 各个子类需要实现的方法。
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws BOException
     */
    public abstract ActionForward doPerform (ActionMapping mapping,
                                             ActionForm form,
                                             HttpServletRequest request,
                                             HttpServletResponse response)
        throws BOException ;

    /**
     * 提供子类保存需要显示到前台的信息。
     * @param request HttpServletRequest
     * @param key String
     */
    protected void saveMessages (HttpServletRequest request, String key)
    {
        if ((key == null) || key.equals(""))
        { //no key ,just return
            return ;
        }
        saveMessages(request, key, "") ;
    }

    /**
     * 具体实现方式。
     * @param request HttpServletRequest
     * @param key String
     * @param addInfo String
     */
    protected void saveMessages (HttpServletRequest request, String key,
                                 String addInfo)
    {
        String[] addInfos =
            {addInfo} ;
        this.saveMessages(request, key, addInfos) ;
    }

    /**
     * 根据传入的资源key和补充信息数组，构造提示信息并保存到下一个forward页面。
     * 用java.text.MessageFormat来格式化，要求资源信息里参数为{0},{1},...{n}格式。
     * @param request HttpServletRequest
     * @param key String
     * @param addInfos String[]
     */
    protected void saveMessages (HttpServletRequest request, String key,
                                 String[] addInfos)
    {
        if ((key == null) || key.equals(""))
        { //no key ,just return
            return ;
        }
        String msg = "" ;

        msg = ResourceUtil.getResource(key) ;
        if ((msg != null) && !msg.equals(""))
        {
            msg = MessageFormat.format(msg, addInfos) ;
            this.saveMessagesValue(request, msg) ;
        }
        else
        {
            LOG.error("Cannot get the key's value.The key is " + key) ;
        }
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

    /**
     * 从请求中获取参数，如果为null就返回空字符串""
     * @param request http请求
     * @param key 参数的关键字
     * @return 参数值
     */
    protected String getParameter (HttpServletRequest request,
                                   String key)
    {
        String value = request.getParameter(key) ;
        if (value == null)
        {
            value = "" ;
        }
//        return Validator.filter(value.trim()) ;
        return value.trim() ;
    }

    /**
     * 重定向到某个页面，同时把request里面的attribute取出来并用get参数的方式传递
     * 这个方法主要是对于sendredirect方法无法传递attribute的补充。
     * @param request HttpServletRequest http请求
     * @param response HttpServletResponse http相应
     * @param url String 目标页面
     */
    protected void gotoPage (HttpServletRequest request,
                             HttpServletResponse response, String url)
    {
        LOG.debug("goto URL:[" + url + "]") ;
        try
        {
            response.sendRedirect(url) ;
        }
        catch (IOException e)
        {
            throw new RuntimeException("sendredirect fail!", e) ;
        }
    }

    /**
     * 写操作日志的封装方法，
     * 请注意！！！
     * 只能是登录后的action调用才能调用这个方法，不然会记录日志失败！！！
     * @param request HttpServletRequest
     * @param actionType String
     * @param actionTarget String
     * @param actionResult boolean
     * @param desc String
     */
    protected void actionLog (HttpServletRequest request, String actionType,
                              String actionTarget, boolean actionResult,
                              String desc)
    {
        //写操作日志
        try
        {
            UserVO logUser = null ;
            String IP = null ;
            UserSessionVO userSessionVO = UserManagerBO.getInstance().
                getUserSessionVO(request.getSession()) ;

            if (userSessionVO != null)
            {
                logUser = userSessionVO.getUser() ;
                //IP = userSessionVO.getAccessInfo().getIP() ;
                IP = request.getRemoteAddr() ;
            }
            else
            {
                logUser = new UserVO() ;
                IP = request.getRemoteAddr() ;
                logUser.setUserID("unknow") ;
                logUser.setName("unknow") ;

            }

            ActionLogBO.getInstance().log(logUser.getUserID(), logUser.getName(),
                                          logUser.getUserRolesInfo() == null ?
                                          "" : logUser.getUserRolesInfo(),
                                          actionType, actionResult,
                                          actionTarget, IP, desc) ;
        }
        catch (BOException e)
        {
            LOG.error(e) ;
        }
    }
    
    /**
	 * 
	 *@desc 
	 *@author dongke
	 *Aug 9, 2011
	 * @param keyBaseList
	 * @param delResourceList
	 * @param fileForm
	 * @param cid
	 * @param request
	 * @param resServerPath
	 * @throws BOException
	 */
    protected void saveKeyResource(List keyBaseList,List delResourceList, FileForm fileForm, String cid,
			HttpServletRequest request, String resServerPath,String servicePath) throws BOException
	{
		for (int i = 0; i < keyBaseList.size(); i++)
		{
			ResourceVO vo = (ResourceVO) keyBaseList.get(i);
			if (vo.getKeyType().equals("2"))
			{// 图片（文件）域
				String fileUrlValue = UploadFileKeyResUtil.getInstance().getFileUrl(
						fileForm, resServerPath, cid, vo.getKeyname(),servicePath);
					vo.setValue(fileUrlValue);
					vo.setTid(cid);			
			}
			else if (vo.getKeyType().equals("1")||vo.getKeyType().equals("3"))
			{
				// 普通文本域	或大文本域	
					vo.setValue(this.getParameter(request, vo.getKeyname()).trim());
					vo.setTid(cid);						
			}
			else
			{
				LOG.debug("keyType is not reg:vo.getKeyType() = " + vo.getKeyType());
			}
			String delValue = this.getParameter(request, "clear_"+vo.getKeyname()).trim();
			if(delValue != null && delValue.equals("1")){
				delResourceList.add(vo);
			}
		}
	}
    
    /**
     * RSA密码解密
     * @param ciphertext
     * @param request
     * @return
     */
    protected String decrypt(String ciphertext,HttpServletRequest request) {

		if (ciphertext == null) {
			return "";
		}
		LOG.debug("开始解密: " + ciphertext);
		long start = System.currentTimeMillis();
		HttpSession session = request.getSession();
		KeyPair keyPair =(KeyPair) session.getAttribute("keyPair");
		if (keyPair == null) {
			LOG.error("获取加密键值对失败");
			return "";
		}
		byte[] en_result = toBytes(ciphertext);
		try {
			LOG.debug(keyPair.getPrivate().getFormat());
			byte[] de_result = RSAUtil.decrypt(keyPair.getPrivate(), en_result);
			StringBuffer sb = new StringBuffer();
			sb.append(new String(de_result));
			LOG.debug("解密成功,消耗时间：" + (System.currentTimeMillis() - start));
			return sb.reverse().toString();
		} catch (Exception e) {
			LOG.error("解密失败:" + e);
			return "";
		}
	}
	public static final byte[] toBytes(String s) {

		byte[] bytes;
		bytes = new byte[s.length() / 2];
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2)
					.trim(), 16);
		}
		return bytes;
	}
}
