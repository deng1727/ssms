package com.aspire.ponaadmin.webpps.mychannel.action;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.usermanager.UserManagerBO;
import com.aspire.ponaadmin.common.usermanager.UserSessionVO;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.channelUser.util.Common;
import com.aspire.ponaadmin.web.channelUser.vo.ChannelVO;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.webpps.mychannel.bo.MyChannelBO;
import com.aspire.ponaadmin.webpps.mychannel.vo.MyChannelVO;

/**
 * <p>Title:�ҵ����� </p>
 * <p>Description: �ҵ�������ѯ��������ز���</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * @author baojun
 * @version 
 */
public class MyChannelAction extends BaseAction{

	/**
     * ��־����
     */
    private static final JLogger LOG = LoggerFactory.getLogger(MyChannelAction.class);
    
    /* (non-Javadoc)
     * @see com.aspire.ponaadmin.web.BaseAction#doPerform(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {
        LOG.debug("doPerform()");

        if(request.getSession() == null || UserManagerBO.getInstance()
    			.getUserSessionVO(request.getSession()) == null){
    		this.saveMessages(request, "�û�δ��½���ߵ�½�ѳ�ʱ��");
    		request.setAttribute(Constants.PARA_GOURL, "index.jsp");
    		 request.setAttribute("loginResult", new Integer(Common.LOGINRESULT));
    		return mapping.findForward(Common.LOGIN_HINT_CHANNELUSER);
    	}
        
        // �������л�ȡ��������
        String perType = this.getParameter(request, "perType").trim();

        if ("query".equals(perType))
        {
            return query(mapping, form, request, response);
        }
        else if("add".equals(perType))
        {
            return add(mapping, form, request, response);
        }
        else if("save".equals(perType))
        {
            return save(mapping, form, request, response);
        }else if("detail".equals(perType))
        {
            return detail(mapping, form, request, response);
        }else if("edit".equals(perType))
        {
            return edit(mapping, form, request, response);
        }
        else if("update".equals(perType))
        {
            return update(mapping, form, request, response);
        }
        else
        {
            String forward = Constants.FORWARD_COMMON_FAILURE;
            this.saveMessagesValue(request, "�Բ��������ʵ�·��������");
            return mapping.findForward(forward);
        }
    }
    
    /**
     * ��ѯ�ҵ������б�
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward query(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws BOException
    {
        LOG.debug("doPerform()");
        String forward = "list";
        PageResult page = new PageResult(request);
        //����һ������������ܷ���20������
        page.setPageSize(25);
        Map<String,Object> map;
        
        try
        {
        	UserSessionVO userSession = UserManagerBO.getInstance()
        			.getUserSessionVO(request.getSession());
        	ChannelVO channel =  userSession.getChannel();

        	MyChannelBO.getInstance().queryChannelInfoList(page, channel.getChannelsNO());
        	map = MyChannelBO.getInstance().queryChannelsNoTotal(channel.getChannelsNO());
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "��ѯ�ҵ������б����");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        
        if(map.isEmpty() || map.get("total") == null){
        	request.setAttribute("unused", "0");
        	request.setAttribute("used", "0");
        	request.setAttribute("total", "0");
        }else{
        	request.setAttribute("total", map.get("total"));
        	request.setAttribute("used", map.get("used"));
        	BigDecimal bigDecimal = new BigDecimal(map.get("total").toString());
        	bigDecimal = bigDecimal.subtract(new BigDecimal(map.get("used").toString()));
        	request.setAttribute("unused", bigDecimal.toString());
        }
		request.setAttribute("PageResult", page);
        return mapping.findForward(forward);
    }
    
    /**
     * ��������
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward add(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws BOException
    {
        String forward = "add";
        return mapping.findForward(forward);
    }
    
    /**
     * ������������
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward save(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response)
                    throws BOException
    {
        String forward = Constants.FORWARD_COMMON_SUCCESS;
        
        UserSessionVO userSession = UserManagerBO.getInstance()
    			.getUserSessionVO(request.getSession());
    	ChannelVO channel =  userSession.getChannel();
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("channelsId", channel.getChannelsId());
    	map.put("channelType",request.getParameter("channelType"));
    	map.put("channelName",request.getParameter("channelName"));
    	map.put("channelDesc",request.getParameter("channelDesc"));
    	map.put("channelsNo", channel.getChannelsNO());

    	String actionType = "��������";
		boolean actionResult = true;
		String actionDesc = "��������";
		String actionTarget = channel.getChannelsId();

		try {
			String channelNo = MyChannelBO.getInstance().queryChannelsNo();
			if(channelNo == null || "".equals(channelNo)){
				this.saveMessages(request, "RESOURCE_CHANNELINFO_RESULT_003");
				request.setAttribute(Constants.PARA_GOURL,
						"channelInfo.do?perType=query");
		        return mapping.findForward(forward);
			}
			MyChannelBO.getInstance().insertChannelContent(map);
		} catch (BOException e) {
			LOG.error("�������������쳣��", e);

			// д������־
			actionResult = false;
			actionDesc = "������������";
			this.actionLog(request, actionType, actionTarget, actionResult,
					actionDesc);

			this.saveMessagesValue(request, "������������");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		
		this.saveMessages(request, "RESOURCE_CHANNELINFO_RESULT_001");
		request.setAttribute(Constants.PARA_GOURL,
				"channelInfo.do?perType=query");
        return mapping.findForward(forward);
        
    }
    
    /**
     * ��������
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward detail(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws BOException
    {
        String forward = "detail";
        String channelId = this.getParameter(request, "channelId")
				.trim();
        MyChannelVO vo = new MyChannelVO();
		vo = MyChannelBO.getInstance().getChannelDetail(channelId);
		request.setAttribute("channelContent", vo);
        return mapping.findForward(forward);
    }
    
    /**
     * �༭����
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward edit(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws BOException
    {
        String forward = "edit";
        String channelId = this.getParameter(request, "radio")
				.trim();
        MyChannelVO vo = new MyChannelVO();
		vo = MyChannelBO.getInstance().getChannelDetail(channelId);
		request.setAttribute("channelContent", vo);
        return mapping.findForward(forward);
    }
    
    /**
     * ������������
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward update(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response)
                    throws BOException
    {
        String forward = Constants.FORWARD_COMMON_SUCCESS;
        
        UserSessionVO userSession = UserManagerBO.getInstance()
    			.getUserSessionVO(request.getSession());
    	ChannelVO channel =  userSession.getChannel();
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("channelId", request.getParameter("channelId"));
    	map.put("channelName",request.getParameter("channelName"));
    	map.put("channelDesc",request.getParameter("channelDesc"));

    	String actionType = "�༭����";
		boolean actionResult = true;
		String actionDesc = "�༭����";
		String actionTarget = channel.getChannelsId();

		try {
			MyChannelBO.getInstance().updateChannelContent(map);
		} catch (BOException e) {
			LOG.error("�༭���������쳣��", e);

			// д������־
			actionResult = false;
			actionDesc = "�༭��������";
			this.actionLog(request, actionType, actionTarget, actionResult,
					actionDesc);

			this.saveMessagesValue(request, "�༭��������");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		
		this.saveMessages(request, "RESOURCE_CHANNELINFO_RESULT_002");
		request.setAttribute(Constants.PARA_GOURL,
				"channelInfo.do?perType=query");
        return mapping.findForward(forward);
        
    }
}
