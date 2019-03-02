/*
 * �ļ�����CategoryDeviceAction.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
 */
package com.aspire.ponaadmin.web.channelUser.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.gcontent.PlatFormVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.usermanager.UserManagerBO;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.channelUser.util.Common;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.repository.CategoryPlatFormBO;
import com.aspire.ponaadmin.web.system.PageSizeConstants;



/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2011</p>
 * @author wangminlong
 * @version 
 */
public class CategoryPlatFormAction extends BaseAction
{
    /**
     * ��־����
     */
    private static final JLogger logger = LoggerFactory.getLogger(CategoryPlatFormAction.class) ;
    
    /* (non-Javadoc)
     * @see com.aspire.ponaadmin.web.BaseAction#doPerform(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {

    	if(request.getSession() == null || UserManagerBO.getInstance()
    			.getUserSessionVO(request.getSession()) == null){
    		this.saveMessages(request, "�û�δ��½���ߵ�½�ѳ�ʱ��");
    		request.setAttribute(Constants.PARA_GOURL, "index.jsp");
    		 request.setAttribute("loginResult", new Integer(Common.LOGINRESULT));
    		return mapping.findForward(Common.LOGIN_HINT_CHANNELUSER);
    	}
        if (logger.isDebugEnabled())
        {
            logger.debug("doPerform()");
        }
        String forward = "platForm";
        
        // �������л�ȡ��������
        String platFormId = this.getParameter(request, "platFormId").trim();
        String platFormName = this.getParameter(request, "platFormName").trim();
        String isFirst = this.getParameter(request, "isFirst").trim();
        
        PageResult page = new PageResult(request);
        page.setPageSize(Integer.parseInt(PageSizeConstants.page_DEFAULT));

        // ���ݻ�ȡ�Ĳ�����ƽ̨����Ϣ
        if(isFirst.equals("1"))//��һ�β���Ҫ��ѯ
        {
            page.excute(new ArrayList(0));
            request.setAttribute("notice", "������������ѯ");
        }else
        {
            PlatFormVO platForm = new PlatFormVO();
            platForm.setPlatFormId(platFormId);
            platForm.setPlatFormName(platFormName);
            
            CategoryPlatFormBO.getInstance().queryPlatFormList(page, platForm);
        }
        
        
        // ��list���õ�page�����ڷ�ҳչʾ
        request.setAttribute("platFormId", platFormId);
        request.setAttribute("platFormName", platFormName);
        request.setAttribute("PageResult", page);

        return mapping.findForward(forward);
    }

}
