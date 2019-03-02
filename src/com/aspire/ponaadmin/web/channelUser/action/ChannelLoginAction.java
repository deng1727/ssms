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
 * 渠道商登陆
 * 
 * @author shiyangwang
 * 
 */
public class ChannelLoginAction extends BaseAction {

	/**
	 * 日志引用
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
		//RSA解密
		String channelsNO = decrypt(encode_channelsNO,request);
		String channelsPwd =decrypt(encode_channelsPwd,request);
		
		
		String imgCode = this.getParameter(request, "imgCode").trim();
		//检查参数
        if (channelsPwd.equals("") || (imgCode == null || imgCode.equals("")))
        {
            throw new BOException("invalid parameter!", UserManagerConstant.INVALID_PARA);
        }
        
        String userReg = "[\\w,\\u4e00-\\u9fa5]+";
        
        if(!channelsNO.matches(userReg) || channelsNO.length() > 20)
        {
            request.setAttribute("userID", channelsNO);
            this.saveMessages(request, "登录用户校验出错！");
            request.setAttribute("loginResult", new Integer(1));
            //写操作日志
            {
                String logActionType = "渠道商用户登录";
                boolean actionResult = false;
                String logDesc = "";
                ActionLogBO.getInstance().log(channelsNO, "", "", logActionType,
                                              actionResult, "",
                                              request.getRemoteAddr(), logDesc) ;
            }
            return mapping.findForward(FORWARD_RELOGIN);
        }
        //检查附加码
        if(!GenerateVerifyImg.verify(imgCode, request.getSession()))
        {
            //附加码不正确的时候，需要显示用户以前输入的帐号名
            request.setAttribute("channelsNO", channelsNO);
            this.saveMessages(request, ResourceConstants.WEB_ERR_IMGCODE);
            request.setAttribute("loginResult", new Integer(1));
            //写操作日志
            {
                String logActionType = "渠道商用户登录";
                boolean actionResult = false;
                String logDesc = "";
                ActionLogBO.getInstance().log(channelsNO, "", "", logActionType,
                                              actionResult, "",
                                              request.getRemoteAddr(), logDesc) ;
            }
            return mapping.findForward(FORWARD_RELOGIN);
        }
      //调用业务逻辑处理方法
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
                //登录成功
                actionResult = true;
                //载入用户个人信息和角色信息来
                ChannelVO channel = ChannelBO.getInstance().getChannelByNO(channelsNO);
                         
                //记录用户访问信息
                UserAccessInfoVO accessInfo = new UserAccessInfoVO();
                accessInfo.setUserID(channelsNO);
                accessInfo.setLoginTime(System.currentTimeMillis());
                accessInfo.setIP(request.getRemoteAddr());

                //根据用户的状态判断一下步需要的处理
                if(UserManagerConstant.CHANNEL_STATUS_NORMAL.equals(channel.getStatus()))
                {
                    //正常用户
                    forward = FORWARD_MAIN;
                  //把用户的有关信息都保存到UserSessionVO对象中，并保存到session中
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
                    /*//渠道商类型，0-表示终端公司，1-非终端公司
                    if("0".equals(channel.getMoType())){
                    	//终端公司，有tac码库管理和推送管理菜单的权限
                    	//用户权限
                        List rightList = new ArrayList();
                        
                        RightVO rightVO = new RightVO();
                        rightVO.setRightID("100_1105_TACCODE_MANAGE");
                        rightVO.setName("TAC码库管理");
                        rightList.add(rightVO);
                        rightVO = new RightVO();
                        rightVO.setRightID("200_1105_PUSHADV_MANAGE");
                        rightVO.setName("推送管理");
                        rightList.add(rightVO);
                        userSession.setRights(rightList);
                    }*/
                }
                else if(UserManagerConstant.CHANNEL_STATUS_DOWN.equals(channel.getStatus()))
                {
                	 forward = Common.LOGIN_HINT_CHANNELUSER;
                     request.setAttribute(Constants.PARA_GOURL, "index.jsp");
                     this.saveMessages(request, ResourceConstants.WEB_ERR_USER_NOT_EXISTED, channelsNO);
                     logDesc = "已下线的用户";
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
                logDesc = "未注册的用户";
            }
            else if(result == UserManagerConstant.INVALID_PWD)
            {
                forward = Common.LOGIN_HINT_CHANNELUSER;
                request.setAttribute(Constants.PARA_GOURL, "index.jsp");
                this.saveMessages(request, ResourceConstants.WEB_INF_INVALID_PWD);
                logDesc = "密码错误";
            }
            else
            {
                //登录失败
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
        //写操作日志
        {
            String logActionType = "用户登录";
            ActionLogBO.getInstance().log(channelsNO, logUserName, logUserRole, logActionType,
                                          actionResult, "",
                                          request.getRemoteAddr(), logDesc) ;
        }
		// TODO Auto-generated method stub
		return mapping.findForward(forward);
	}

}
