package com.aspire.dotcard.wpinfo.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.wpinfo.bo.AppInfoBO;
import com.aspire.dotcard.wpinfo.vo.AppInfoReferenceVO;
import com.aspire.dotcard.wpinfo.vo.AppInfoVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;

public class AppInfoReferenceAction extends BaseAction{

	/**
	 * ��־����
	 */
	private static final JLogger LOG = LoggerFactory
			.getLogger(AppInfoReferenceAction.class);
	
	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		LOG.debug("doPerform()");

        // �������л�ȡ��������
        String perType = this.getParameter(request, "perType").trim();
        if ("query".equals(perType))
        {
            return query(mapping, form, request, response);
        }
        else if ("remove".equals(perType))
        {
            return remove(mapping, form, request, response);
        }
        else if ("setSort".equals(perType))
        {
            return setSort(mapping, form, request, response);
        }
        else if ("queryProgram".equals(perType))
        {
            return queryProgram(mapping, form, request, response);
        }
        else if ("add".equals(perType))
        {
            return add(mapping, form, request, response);
        }

        else
        {
            String forward = Constants.FORWARD_COMMON_FAILURE;
            this.saveMessagesValue(request, "�Բ��������ʵ�·��������");
            return mapping.findForward(forward);
        }
	}
	
	/**
     * ��ѯwp��ۻ����µ���Ʒ�б�
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
        String forward = "query";
        AppInfoReferenceVO vo = new AppInfoReferenceVO();
        PageResult page = new PageResult(request);

        // �������л�ȡ��������
        String refId = this.getParameter(request, "refId").trim();
        String categoryId = this.getParameter(request, "categoryId").trim();
        String appId = this.getParameter(request, "appId").trim();
        String appName = this.getParameter(request, "appName").trim();

        vo.setRefId(refId);
        vo.setCategoryId(categoryId);
        vo.setAppId(appId);
        vo.setAppName(appName);

        
        String actionType = "��ѯwp��ۻ�������Ʒ";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = categoryId;

        if (LOG.isDebugEnabled())
        {
            LOG.debug("�������л�ȡ�������룺" + categoryId);
        }

        try
        {
            AppInfoBO.getInstance().queryAppInfoReferenceList(page, vo);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "��ѯwp���ָ�������µ���Ʒ�б����");
            
			// д������־
			actionResult = false;
			actionDesc = "��ѯwp��ۻ�������Ʒ����";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        request.setAttribute("perType", forward);
        request.setAttribute("PageResult", page);
        request.setAttribute("categoryId", categoryId);
        request.setAttribute("appId", appId);
        request.setAttribute("appName", appName);
        
		// д������־
		actionDesc = "��ѯwp��ۻ�������Ʒ�ɹ�";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        return mapping.findForward(forward);
    }

    /**
     * ɾ��wp��ۼ��µ���Ʒ
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward remove(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response)
                    throws BOException
    {
        String forward = "remove";

        // �������л�ȡ��������
        String categoryId = this.getParameter(request, "categoryId").trim();
        String[] refId = request.getParameterValues("dealRef");
        
        String actionType = "ɾ��wp��ۻ�������Ʒ";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = categoryId;

        if (LOG.isDebugEnabled())
        {
            LOG.debug("�������л�ȡ�������룺" + categoryId);
        }

        try
        {
            AppInfoBO.getInstance().delAppInfoReferences(categoryId, refId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "�����Ƴ�ָ��������ָ������Ʒ����");
            
			// д������־
			actionResult = false;
			actionDesc = "ɾ��wp��ۻ�������Ʒ����";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        this.saveMessagesValue(request, "ɾ��wp�����Ʒ�ɹ�");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "appInfoReference.do?perType=query&categoryId="
                                             + categoryId);
        
		// д������־
		actionDesc = "ɾ��wp��ۻ�������Ʒ�ɹ�";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        return mapping.findForward(forward);
    }

    /**
     * ����wp��ۻ�����wp�����Ʒ����ֵ
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward setSort(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
                    throws BOException
    {
        String forward = "setSort";

        // �������л�ȡ��������
        String categoryId = this.getParameter(request, "categoryId").trim();
        String setSortId = this.getParameter(request, "setSortId").trim();
        
        String actionType = "�޸�wp��ۻ�������Ʒ����";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = categoryId;
		

        if (LOG.isDebugEnabled())
        {
            LOG.debug("�������л�ȡ�������룺" + categoryId);
        }

        try
        {
            // ����wp��ۻ�����wp�����Ʒ����ֵ
            AppInfoBO.getInstance().setAppInfoReferenceSort(categoryId, setSortId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "��������wp��ۻ���������wp�������ֵʱ����");
            
			// д������־
			actionResult = false;
			actionDesc = "�޸�wp��ۻ�������Ʒ�������";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
	        
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        this.saveMessagesValue(request, "����wp�����Ʒ����ֵ�ɹ�");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "appInfoReference.do?perType=query&categoryId="
                                             + categoryId);
        
		// д������־
		actionDesc = "�޸�wp��ۻ�������Ʒ����ɹ�";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        return mapping.findForward(forward);
    }

    /**
     * ��ѯwp��۽�Ŀ�б�
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward queryProgram(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {
        String forward = "queryProgram";
        
        
        String actionType = "��������wp��ۻ�������Ʒ��wp��۽�Ŀ��ѯ";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = "";

        // �������л�ȡ��������
        String isFirst = this.getParameter(request, "isFirst").trim();
        String appName = this.getParameter(request, "appName").trim();
        String appId = this.getParameter(request, "appId").trim();
        AppInfoVO vo = new AppInfoVO();
        PageResult page = new PageResult(request);

        // ����ǵ�һ�Ρ�����
        if ("1".equals(isFirst))
        {
            request.setAttribute("isFirst", isFirst);
            request.setAttribute("PageResult", page);
            request.setAttribute("appName", appName);
            request.setAttribute("appId", appId);

            return mapping.findForward(forward);
        }

        vo.setAppId(appId);
        vo.setAppName(appName);

        if (LOG.isDebugEnabled())
        {
            LOG.debug("�������л�ȡwp���������appName=" + appName + ", programId=" + appId);
        }

        try
        {
            // ��ѯwp��۽�Ŀ�б������ϼ����»�����
            AppInfoBO.getInstance().queryAppInfoList(page, vo);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "��ѯwp����б����");
            
			// д������־
			actionResult = false;
			actionDesc = "��������wp��ۻ�������Ʒ��wp��۲�ѯ����";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        request.setAttribute("perType", forward);
        request.setAttribute("PageResult", page);
        request.setAttribute("appName", appName);
        request.setAttribute("appId", appId);

		// д������־
		actionDesc = "��������wp��ۻ�������Ʒ��wp��۲�ѯ�ɹ�";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        return mapping.findForward(forward);
    }

    /**
     * ���wp��۽�Ŀ��������
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

        // �������л�ȡ��������
        String categoryId = this.getParameter(request, "categoryId").trim();
        String appInfoId = this.getParameter(request, "addAppInfoId").trim();
        
        String actionType = "����wp��ۻ�������Ʒ";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = categoryId;
		

        if (LOG.isDebugEnabled())
        {
            LOG.debug("�������л�ȡ�������룺" + categoryId);
        }

        try
        {
            // �鿴ԭ�����Ƿ�����û���Ҫ��������Ʒ��Ϣ
            String ret = AppInfoBO.getInstance().isHasReferences(categoryId, appInfoId);
            if (!"".equals(ret))
            {
                this.saveMessagesValue(request, "ԭ���ܴ����û���ѡ����wp��۽�Ŀ��" + ret);
                
    			// д������־
    			actionResult = false;
    			actionDesc = "����wp��ۻ�������Ʒ����!��Ʒ�Ѵ���";
    	        this.actionLog(request,
    	                       actionType,
    	                       actionTarget,
    	                       actionResult,
    	                       actionDesc);
    	        
                return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
            }
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "�鿴ԭ�����Ƿ�����û���Ҫ����ʱ����");
            
            // д������־
			actionResult = false;
			actionDesc = "����wp��ۻ�������Ʒ����!�鿴ԭ�����Ƿ�����û���Ҫ����ʱ����!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        try
        {
            // ����ָ��wp��۽�Ŀ��Ʒ��ָ��������
            AppInfoBO.getInstance().addAppInfoReferences(categoryId, appInfoId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "�������ָ����wp��۽�Ŀ������ʱ����");
            
            // д������־
			actionResult = false;
			actionDesc = "����wp��ۻ�������Ʒ����!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        this.saveMessagesValue(request, "���wp��ۻ�������Ʒ�ɹ�");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "appInfoReference.do?perType=query&categoryId="
                                             + categoryId);
        
		// д������־
		actionDesc = "����wp��ۻ�������Ʒ�ɹ�";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        return mapping.findForward(forward);
    }

}
