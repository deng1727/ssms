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
 * <p>Title:我的应用 </p>
 * <p>Description: 合作商的自有应用查询相关操作</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * @author baojun
 * @version 
 */
public class MyContentAction extends BaseAction{

	/**
     * 日志引用
     */
    private static final JLogger LOG = LoggerFactory.getLogger(MyContentAction.class);
    
	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		LOG.debug("doPerform()");

        if(request.getSession() == null || UserManagerBO.getInstance()
    			.getUserSessionVO(request.getSession()) == null){
    		this.saveMessages(request, "用户未登陆或者登陆已超时！");
    		request.setAttribute(Constants.PARA_GOURL, "index.jsp");
    		 request.setAttribute("loginResult", new Integer(Common.LOGINRESULT));
    		return mapping.findForward(Common.LOGIN_HINT_CHANNELUSER);
    	}
        
        // 从请求中获取操作类型
        String perType = this.getParameter(request, "perType").trim();

        if ("query".equals(perType))
        {
            return query(mapping, form, request, response);
        }
        else
        {
            String forward = Constants.FORWARD_COMMON_FAILURE;
            this.saveMessagesValue(request, "对不起，您访问的路径不存在");
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
		
		// 实现分页，列表对象是保存在分页对象PageResult中的。
		PageResult page = new PageResult(request, Integer
				.parseInt(pageSize));
		try {
			UserSessionVO userSession = UserManagerBO.getInstance()
	    			.getUserSessionVO(request.getSession());
	    	ChannelVO channel =  userSession.getChannel();
			//合作商的合作商id（channelsID）对应内容表（t_r_gcontent）的企业内码companyid
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
			this.saveMessages(request, "我的全量应用查询列表失败");
		}
		return mapping.findForward(forward);
	}
}
