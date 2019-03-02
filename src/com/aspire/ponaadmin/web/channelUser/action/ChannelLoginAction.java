package com.aspire.ponaadmin.web.channelUser.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.util.GenerateVerifyImg;
import com.aspire.ponaadmin.common.usermanager.UserAccessInfoVO;
import com.aspire.ponaadmin.common.usermanager.UserManagerBO;
import com.aspire.ponaadmin.common.usermanager.UserManagerConstant;
import com.aspire.ponaadmin.common.usermanager.UserSessionVO;
import com.aspire.ponaadmin.common.usermanager.UserVO;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.actionlog.ActionLogBO;
import com.aspire.ponaadmin.web.channelUser.bo.ChannelBO;
import com.aspire.ponaadmin.web.channelUser.util.Common;
import com.aspire.ponaadmin.web.channelUser.vo.ChannelVO;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.constant.ResourceConstants;
import com.aspire.ponaadmin.webpps.common.constant.Constant;

/**
 * �����̵�½
 * 
 * @author shiyangwang
 * 
 */
public class ChannelLoginAction extends BaseAction {

	/**
	 * ��־����
	 */
	private static final JLogger LOG = LoggerFactory
			.getLogger(ChannelLoginAction.class);
	/**
	 * forward relogin
	 */
	private static final String FORWARD_RELOGIN = "relogin";

	/**
	 * forward main
	 */
	private static final String FORWARD_MAIN = "main";

	@Override
	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		LOG.debug("ChannelLoginAction()");
		String encode_channelsNO = this.getParameter(request, "channelsNO").trim();
		String encode_channelsPwd = this.getParameter(request, "channelsPwd").trim();
		//RSA����
		String channelsNO = decrypt(encode_channelsNO,request);
		String channelsPwd =decrypt(encode_channelsPwd,request);
		
		
		String imgCode = this.getParameter(request, "imgCode").trim();
		//������
        if (channelsPwd.equals("") || (imgCode == null || imgCode.equals("")))
        {
            throw new BOException("invalid parameter!", UserManagerConstant.INVALID_PARA);
        }
        
        String userReg = "[\\w,\\u4e00-\\u9fa5]+";
        
        if(!channelsNO.matches(userReg) || channelsNO.length() > 20)
        {
            request.setAttribute("userID", channelsNO);
            this.saveMessages(request, "��¼�û�У�����");
            request.setAttribute("loginResult", new Integer(1));
            //д������־
            {
                String logActionType = "�������û���¼";
                boolean actionResult = false;
                String logDesc = "";
                ActionLogBO.getInstance().log(channelsNO, "", "", logActionType,
                                              actionResult, "",
                                              request.getRemoteAddr(), logDesc) ;
            }
            return mapping.findForward(FORWARD_RELOGIN);
        }
        //��鸽����
        if(!GenerateVerifyImg.verify(imgCode, request.getSession()))
        {
            //�����벻��ȷ��ʱ����Ҫ��ʾ�û���ǰ������ʺ���
            request.setAttribute("channelsNO", channelsNO);
            this.saveMessages(request, ResourceConstants.WEB_ERR_IMGCODE);
            request.setAttribute("loginResult", new Integer(1));
            //д������־
            {
                String logActionType = "�������û���¼";
                boolean actionResult = false;
                String logDesc = "";
                ActionLogBO.getInstance().log(channelsNO, "", "", logActionType,
                                              actionResult, "",
                                              request.getRemoteAddr(), logDesc) ;
            }
            return mapping.findForward(FORWARD_RELOGIN);
        }
      //����ҵ���߼�������
        int result = UserManagerConstant.INVALID_PWD;
        LOG.debug("login result:"+result+".");
        String forward = "commonSuccess";
        boolean actionResult = false;
        String logDesc = "";
        String logUserName = "";
        String logUserRole = "";
        try
        {
        	result = ChannelBO.getInstance().channelLogin(channelsNO, channelsPwd);
            if(result == UserManagerConstant.SUCC)
            {
                //��¼�ɹ�
                actionResult = true;
                //�����û�������Ϣ�ͽ�ɫ��Ϣ��
                ChannelVO channel = ChannelBO.getInstance().getChannelByNO(channelsNO);
                         
                //��¼�û�������Ϣ
                UserAccessInfoVO accessInfo = new UserAccessInfoVO();
                accessInfo.setUserID(channelsNO);
                accessInfo.setLoginTime(System.currentTimeMillis());
                accessInfo.setIP(request.getRemoteAddr());

                //�����û���״̬�ж�һ�²���Ҫ�Ĵ���
                if(UserManagerConstant.CHANNEL_STATUS_NORMAL.equals(channel.getStatus()))
                {
                    //�����û�
                    forward = FORWARD_MAIN;
                  //���û����й���Ϣ�����浽UserSessionVO�����У������浽session��
                    UserSessionVO userSession = new UserSessionVO();
                    userSession.setChannel(channel);
                    userSession.setAccessInfo(accessInfo);
                    UserManagerBO.getInstance().setUserSessionVO(request.getSession(), userSession);
                    request.getSession().setAttribute(Constant.TOUCHSPOT_APID, channel.getChannelsId());
                    if(channel != null)
                    {
                        logUserName = channel.getChannelsName();
                        logUserRole = channel.getChannelsDesc() ;
                        
                        UserVO user = new UserVO();
                        user.setUserID(channel.getChannelsNO());
                        user.setName(logUserName);
                        userSession.setUser(user);
                    }
                    /*//���������ͣ�0-��ʾ�ն˹�˾��1-���ն˹�˾
                    if("0".equals(channel.getMoType())){
                    	//�ն˹�˾����tac����������͹���˵���Ȩ��
                    	//�û�Ȩ��
                        List rightList = new ArrayList();
                        
                        RightVO rightVO = new RightVO();
                        rightVO.setRightID("100_1105_TACCODE_MANAGE");
                        rightVO.setName("TAC������");
                        rightList.add(rightVO);
                        rightVO = new RightVO();
                        rightVO.setRightID("200_1105_PUSHADV_MANAGE");
                        rightVO.setName("���͹���");
                        rightList.add(rightVO);
                        userSession.setRights(rightList);
                    }*/
                }
                else if(UserManagerConstant.CHANNEL_STATUS_DOWN.equals(channel.getStatus()))
                {
                	 forward = Common.LOGIN_HINT_CHANNELUSER;
                     request.setAttribute(Constants.PARA_GOURL, "index.jsp");
                     this.saveMessages(request, ResourceConstants.WEB_ERR_USER_NOT_EXISTED, channelsNO);
                     logDesc = "�����ߵ��û�";
                }
                else
                {
                    throw new RuntimeException("unknowned user state!!!");
                }
                

            }
            else if(result == UserManagerConstant.USER_NOT_EXISTED)
            {
                forward = Common.LOGIN_HINT_CHANNELUSER;
                request.setAttribute(Constants.PARA_GOURL, "index.jsp");
                this.saveMessages(request, ResourceConstants.WEB_ERR_USER_NOT_EXISTED, channelsNO);
                logDesc = "δע����û�";
            }
            else if(result == UserManagerConstant.INVALID_PWD)
            {
                forward = Common.LOGIN_HINT_CHANNELUSER;
                request.setAttribute(Constants.PARA_GOURL, "index.jsp");
                this.saveMessages(request, ResourceConstants.WEB_INF_INVALID_PWD);
                logDesc = "�������";
            }
            else
            {
                //��¼ʧ��
                request.setAttribute(Constants.PARA_GOURL, "index.jsp");
                forward = Common.LOGIN_HINT_CHANNELUSER;
            }
        }
        catch(Exception e)
        {
            LOG.error(e) ;
            actionResult = false ;
            result = UserManagerConstant.FAIL ;
            forward = Common.LOGIN_HINT_CHANNELUSER;
            request.setAttribute(Constants.PARA_GOURL, "index.jsp");
        }
        request.setAttribute("loginResult", new Integer(result));
        //д������־
        {
            String logActionType = "�û���¼";
            ActionLogBO.getInstance().log(channelsNO, logUserName, logUserRole, logActionType,
                                          actionResult, "",
                                          request.getRemoteAddr(), logDesc) ;
        }
		// TODO Auto-generated method stub
		return mapping.findForward(forward);
	}

}
