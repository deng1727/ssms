package com.aspire.ponaadmin.web.rightmanager.action ;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.rightmanager.RightManagerBO;
import com.aspire.ponaadmin.common.rightmanager.RightManagerConstant;
import com.aspire.ponaadmin.common.rightmanager.RoleVO;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.constant.ResourceConstants;

/**
 * <p>�����ɫ��Ϣ��action�������������������޸�</p>
 * <p>action�����ж�ִ�����ֲ�����add��update��ʾ�������޸�</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author zhangmin
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class RoleAction extends BaseAction
{

	/**
	 * ���debug��־
     */
    protected static JLogger LOGGER = LoggerFactory.getLogger (RoleAction.class) ;

     /**
     *
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws BOException
     * @todo Implement this com.aspire.ponaadmin.web.BaseAction method
     */
	 public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			 HttpServletRequest request,
			 HttpServletResponse response) throws BOException
	 {
         LOGGER.debug("RoleAction()") ;

         //��ȡ����
         String action = request.getParameter("action");
         LOGGER.debug("action======"+action);
         String roleID = request.getParameter("roleID") ;
         //�����޸ĳ�������Ա
         if(roleID.equals("1"))
         {
             throw new BOException("A hijack access found!Can't modify role ��������Ա!");
         }
         String name = request.getParameter("name") ;
         String descs = request.getParameter("descs") ;
         RoleVO vo = new RoleVO() ;
         vo.setRoleID(roleID) ;
         vo.setName(name) ;
         vo.setDesc(descs) ;

         RightManagerBO rightManager = RightManagerBO.getInstance();
         String forward = null;
         boolean actionResult = false;
         String logActionType = "";
         if(action.equals(Constants.FORWARD_ADD_TOKEN))
         {
             //��ӽ�ɫ����
             logActionType = "��ӽ�ɫ";
             try
             {
                 rightManager.addRole(vo) ;
                 forward = Constants.FORWARD_COMMON_SUCCESS;
                 request.setAttribute(Constants.PARA_GOURL, "add_role.jsp");
                 this.saveMessages(request, ResourceConstants.WEB_INF_SAVEROLE_OK);
                 actionResult = true;
             }
             catch (BOException e)
             {
                 LOGGER.error(e);
                 if(e.getErrorCode() == RightManagerConstant.ROLE_NAME_EXIST)
                 {
                     forward = Constants.FORWARD_COMMON_FAILURE ;
                     this.saveMessages(request, ResourceConstants.WEB_ERR_ROLE_EXIST);
                 }
                 else
                 {
                     forward = Constants.FORWARD_COMMON_FAILURE ;
                     this.saveMessages(request,
                                       ResourceConstants.WEB_INF_SAVEROLE_ERR) ;
                 }
             }
         }
         else if (action.equals(Constants.FORWARD_UPDATE_TOKEN))
         {
             //���½�ɫ��Ϣ����
             logActionType = "�޸Ľ�ɫ��Ϣ";
             try
             {
                 rightManager.modRoleInfo(vo) ;
                 forward = Constants.FORWARD_COMMON_SUCCESS;
                 request.setAttribute(Constants.PARA_GOURL, "roleList.do");
                 this.saveMessages(request, ResourceConstants.WEB_INF_SAVEROLE_OK);
                 actionResult = true;
             }
             catch (BOException e)
             {
                 if(e.getErrorCode() == RightManagerConstant.ROLE_NAME_EXIST)
                 {
                     forward = Constants.FORWARD_COMMON_FAILURE ;
                     this.saveMessages(request, ResourceConstants.WEB_ERR_ROLE_EXIST);
                 }
                 else
                 {
                     LOGGER.error(e) ;
                     forward = Constants.FORWARD_COMMON_FAILURE ;
                     this.saveMessages(request,
                                       ResourceConstants.WEB_INF_SAVEROLE_ERR) ;
                 }
             }
         }
         else
         {
             throw new RuntimeException("unknown action:" + action);
         }

         //д������־
         {
             String logActionTarget = name;
             String logDesc = "";
             this.actionLog(request, logActionType, logActionTarget,
                            actionResult, logDesc) ;
         }

         return mapping.findForward(forward) ;
     }

}
