package com.aspire.ponaadmin.webpps.mycontent.action;

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
import com.aspire.ponaadmin.web.queryapp.bo.QueryAppBO;
import com.aspire.ponaadmin.web.system.PageSizeConstants;
import com.aspire.ponaadmin.webpps.mychannel.action.MyChannelAction;
import com.aspire.ponaadmin.webpps.mycontent.bo.MyContentBO;

/**
 * <p>Title:�ҵ�Ӧ�� </p>
 * <p>Description: �����̵�����Ӧ�ò�ѯ��ز���</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * @author baojun
 * @version 
 */
public class MyContentAction extends BaseAction{

	/**
     * ��־����
     */
    private static final JLogger LOG = LoggerFactory.getLogger(MyContentAction.class);
    
	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
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
        else
        {
            String forward = Constants.FORWARD_COMMON_FAILURE;
            this.saveMessagesValue(request, "�Բ��������ʵ�·��������");
            return mapping.findForward(forward);
        }
	}

	private ActionForward query(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		if (log.isDebugEnabled()) {
			log.debug("doQueryContentList in.............");
		}
		String forward = Constants.FORWARD_COMMON_FAILURE;

		String contentid = this.getParameter(request, "contentid").trim();
		String name = this.getParameter(request, "name").trim();
		String beginDate = this.getParameter(request, "beginDate").trim();
		String endDate = this.getParameter(request, "endDate").trim();
		String catename = this.getParameter(request, "catename").trim();
		String keywords = this.getParameter(request, "keywords").trim();
		String pageSize = this.getParameter(request, "pageSize").trim();
		if(pageSize == null || pageSize.equals("")){
			pageSize = PageSizeConstants.page_DEFAULT.toString();
		}
		
		// ʵ�ַ�ҳ���б�����Ǳ����ڷ�ҳ����PageResult�еġ�
		PageResult page = new PageResult(request, Integer
				.parseInt(pageSize));
		try {
			UserSessionVO userSession = UserManagerBO.getInstance()
	    			.getUserSessionVO(request.getSession());
	    	ChannelVO channel =  userSession.getChannel();
			//�����̵ĺ�����id��channelsID����Ӧ���ݱ�t_r_gcontent������ҵ����companyid
			MyContentBO.getInstance().queryContentList(page,channel.getChannelsId(), contentid, name,
					beginDate, endDate, catename,keywords);
			request.setAttribute("PageResult", page);
			request.setAttribute("contentid", contentid);
			request.setAttribute("name", name);
			request.setAttribute("beginDate", beginDate);
			request.setAttribute("endDate", endDate);
			request.setAttribute("catename", catename);
			request.setAttribute("keywords", keywords);
			request.setAttribute("pageSize", pageSize);

			forward = "query";
		} catch (Exception e) {
			this.saveMessages(request, "�ҵ�ȫ��Ӧ�ò�ѯ�б�ʧ��");
		}
		return mapping.findForward(forward);
	}
}
