/*
 * �ļ�����BaseAction.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  ��������Action�Ļ��ࡣ
 * �޸��ˣ� �⸶ά
 * �޸�ʱ�䣺2005/06/20
 * �޸����ݣ�����
 �� �޸��ˣ��߱���
 �� �޸�ʱ�䣺2005/06/20
 �� �޸����ݣ��ṩ������doPerform()�������Լ�������ʾ��Ϣ���湦�ܡ�
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
 * <p>Title: Action����</p>
 * <p>Description: ����webportal��Action������ڱ��ࡣ���ṩ�����ı����̨��ʾ��Ϣ
 * ǰֻ̨��Ҫ����tag<bt:messagelist>���ɡ��Լ��Ժ���Ҫ����������߼�Ҳ���Է��ڴ��ࡣ
 * ����Action����Ҫ�̳в�ʵ��doPerform�������ɡ�</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: ASPire TECHNOLOGIES (SHENZHEN) LIMITED</p>
 * @author gaobaobing
 * @version 1.0.0.0
 */

public abstract class BaseAction
    extends DispatchAction
{

    /**
     * ��־����
     */
    private static final JLogger LOG = LoggerFactory.getLogger(BaseAction.class) ;

    /**
     * ��ڷ��������ֹ�����Ҫ������߼�����������ִ�С�Ϊ�Ժ���չ�á�
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
        String forward = Constants.FORWARD_COMMON_FAILURE ; //ת�������ҳ
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
     * ����������Ҫʵ�ֵķ�����
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
     * �ṩ���ౣ����Ҫ��ʾ��ǰ̨����Ϣ��
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
     * ����ʵ�ַ�ʽ��
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
     * ���ݴ������Դkey�Ͳ�����Ϣ���飬������ʾ��Ϣ�����浽��һ��forwardҳ�档
     * ��java.text.MessageFormat����ʽ����Ҫ����Դ��Ϣ�����Ϊ{0},{1},...{n}��ʽ��
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

    /**
     * �������л�ȡ���������Ϊnull�ͷ��ؿ��ַ���""
     * @param request http����
     * @param key �����Ĺؼ���
     * @return ����ֵ
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
     * �ض���ĳ��ҳ�棬ͬʱ��request�����attributeȡ��������get�����ķ�ʽ����
     * ���������Ҫ�Ƕ���sendredirect�����޷�����attribute�Ĳ��䡣
     * @param request HttpServletRequest http����
     * @param response HttpServletResponse http��Ӧ
     * @param url String Ŀ��ҳ��
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
     * д������־�ķ�װ������
     * ��ע�⣡����
     * ֻ���ǵ�¼���action���ò��ܵ��������������Ȼ���¼��־ʧ�ܣ�����
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
        //д������־
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
			{// ͼƬ���ļ�����
				String fileUrlValue = UploadFileKeyResUtil.getInstance().getFileUrl(
						fileForm, resServerPath, cid, vo.getKeyname(),servicePath);
					vo.setValue(fileUrlValue);
					vo.setTid(cid);			
			}
			else if (vo.getKeyType().equals("1")||vo.getKeyType().equals("3"))
			{
				// ��ͨ�ı���	����ı���	
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
     * RSA�������
     * @param ciphertext
     * @param request
     * @return
     */
    protected String decrypt(String ciphertext,HttpServletRequest request) {

		if (ciphertext == null) {
			return "";
		}
		LOG.debug("��ʼ����: " + ciphertext);
		long start = System.currentTimeMillis();
		HttpSession session = request.getSession();
		KeyPair keyPair =(KeyPair) session.getAttribute("keyPair");
		if (keyPair == null) {
			LOG.error("��ȡ���ܼ�ֵ��ʧ��");
			return "";
		}
		byte[] en_result = toBytes(ciphertext);
		try {
			LOG.debug(keyPair.getPrivate().getFormat());
			byte[] de_result = RSAUtil.decrypt(keyPair.getPrivate(), en_result);
			StringBuffer sb = new StringBuffer();
			sb.append(new String(de_result));
			LOG.debug("���ܳɹ�,����ʱ�䣺" + (System.currentTimeMillis() - start));
			return sb.reverse().toString();
		} catch (Exception e) {
			LOG.error("����ʧ��:" + e);
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
