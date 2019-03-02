/*
 * 
 */

package com.aspire.ponaadmin.web.category.intervenor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.category.intervenor.gcontent.IntervenorGcontentVO;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.repository.web.DataImportForm;
import com.aspire.ponaadmin.web.system.PageSizeConstants;

/**
 * �˹���Ԥ����������
 * 
 * @author x_wangml
 * 
 */
public class IntervenorQueryAction extends BaseAction
{

    /**
     * ��־����
     */
    protected static final JLogger logger = LoggerFactory.getLogger(IntervenorQueryAction.class);

    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("doPerform()");
        }

        // �������л�ȡ��������
        String action = this.getParameter(request, "actionType").trim();

        if ("list".equals(action))
        {
            return list(mapping, form, request, response);
        }
        else if ("editView".equals(action))
        {
            return editView(mapping, form, request, response);
        }
        else if ("view".equals(action))
        {
            return view(mapping, form, request, response);
        }
        else if ("delete".equals(action))
        {
            return delete(mapping, form, request, response);
        }
        else if ("deleteContent".equals(action))
        {
            return deleteContent(mapping, form, request, response);
        }
        else if ("edit".equals(action))
        {
            return edit(mapping, form, request, response);
        }
        else if ("setSort".equals(action))
        {
            return setSort(mapping, form, request, response);
        }
        else if ("add".equals(action))
        {
            return add(mapping, form, request, response);
        }
        else if ("addContent".equals(action))
        {
            return addContent(mapping, form, request, response);
        }
        else if ("queryContent".equals(action))
        {
            return queryContent(mapping, form, request, response);
        }
        else if ("importFile".equals(action))
        {
            return importFile(mapping, form, request, response);
        }
        else
        {
            String forward = Constants.FORWARD_COMMON_FAILURE;
            this.saveMessagesValue(request, "�Բ��������ʵ�·��������");
            return mapping.findForward(forward);
        }
    }

    /**
     * ��ѯ�����˹���Ԥ����
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    private ActionForward list(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response)
    {

        String forward = "list";
        PageResult page = new PageResult(request);
        String where = this.getParameter(request, "where").trim();

        List list = null;
        try
        {
            list = IntervenorBO.getInstance().queryIntervenorVOList(where);
        }
        catch (BOException e)
        {
            logger.error(e);
            this.saveMessagesValue(request, "��ѯ�����˹���Ԥ�����б����");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        page.setPageInfo(list);
        request.setAttribute("where", where);
        request.setAttribute("PageResult", page);
        return mapping.findForward(forward);
    }

    /**
     * ��ʾ�˹���Ԥ������Ϣ
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    private ActionForward editView(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
    {

        String forward = "editView";
        PageResult page = new PageResult(request);
        page.setPageSize(Integer.parseInt(PageSizeConstants.page_DEFAULT));

        // �������л�ȡ������Ϣ
        String id = this.getParameter(request, "id").trim();
        IntervenorVO vo = new IntervenorVO();

        try
        {
            vo = IntervenorBO.getInstance().queryInternorVOById(id);
            IntervenorBO.getInstance()
                        .queryGcontentListByIntervenorId(page, id);
        }
        catch (BOException e)
        {
            logger.error(e);
            this.saveMessagesValue(request, "��ʾ�˹���Ԥ������Ϣ����");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        request.setAttribute("actionType", forward);
        request.setAttribute("id", id);
        request.setAttribute("intervenorVO", vo);
        request.setAttribute("PageResult", page);
        return mapping.findForward(forward);
    }

    private ActionForward view(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response)
    {

        String forward = "view";
        PageResult page = new PageResult(request);
        page.setPageSize(Integer.parseInt(PageSizeConstants.page_DEFAULT));

        // �������л�ȡ������Ϣ
        String id = this.getParameter(request, "id").trim();
        String categoryId = this.getParameter(request, "categoryId").trim();
        IntervenorVO vo = new IntervenorVO();

        try
        {
            vo = IntervenorBO.getInstance().queryInternorVOById(id);
            IntervenorBO.getInstance()
                        .queryGcontentListByIntervenorId(page, id);
        }
        catch (BOException e)
        {
            logger.error(e);
            this.saveMessagesValue(request, "��ʾ�˹���Ԥ������Ϣ����");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        request.setAttribute("actionType", forward);
        request.setAttribute("id", id);
        request.setAttribute("categoryId", categoryId);
        request.setAttribute("intervenorVO", vo);
        request.setAttribute("PageResult", page);
        return mapping.findForward(forward);
    }

    /**
     * ����ɾ������
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    private ActionForward delete(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
    {

        String forward = Constants.FORWARD_COMMON_FAILURE;

        // �������л�ȡ��Ԥid
        String intervenorId = this.getParameter(request, "id").trim();

		String actionType = "ͨ����Ԥidɾ����Ӧ�ĸ�Ԥ����";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = intervenorId;
		
        if (logger.isDebugEnabled())
        {
            logger.debug("�������л�ȡ��Ԥid��" + intervenorId);

        }
        // ͨ����Ԥidɾ����Ӧ�ĸ�Ԥ����
        try
        {
            // ͨ����Ԥidɾ����Ӧ�ĸ�Ԥ����
            IntervenorBO.getInstance().deleteInternorVOById(intervenorId);
        }
        catch (BOException e)
        {
			// д������־
			actionResult = false;
			actionDesc = "ͨ����Ԥidɾ����Ӧ�ĸ�Ԥ��������!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            logger.error(e);
            this.saveMessagesValue(request, "ͨ����Ԥidɾ����Ӧ�ĸ�Ԥ��������");
            return mapping.findForward(forward);
        }
		// д������־
		actionDesc = "ͨ����Ԥidɾ����Ӧ�ĸ�Ԥ�����ɹ�!";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "ͨ����Ԥidɾ����Ӧ�ĸ�Ԥ�����ɹ���");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL, "intervenor_main_notes.jsp");
        return mapping.findForward(forward);
    }

    /**
     * ����ɾ��ָ�������е�ָ������
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    private ActionForward deleteContent(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request,
                                        HttpServletResponse response)
    {

        String forward = Constants.FORWARD_COMMON_FAILURE;

        // �������л�ȡ��Ԥid
        String intervenorId = this.getParameter(request, "id").trim();
        String contentId = this.getParameter(request, "contentId").trim();

		String actionType = "ɾ��ָ�������е�ָ������";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = intervenorId + "-" + contentId;
		
        if (logger.isDebugEnabled())
        {
            logger.debug("�������л�ȡ��Ԥid��" + intervenorId + ", contentId="
                         + contentId);

        }

        try
        {
            // ɾ��ָ�������е�ָ������
            IntervenorBO.getInstance().deleteGcontentById(intervenorId,
                                                          contentId);
        }
        catch (BOException e)
        {
			// д������־
			actionResult = false;
			actionDesc = "ɾ��ָ�������е�ָ�����ݳ���!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            logger.error(e);
            this.saveMessagesValue(request, "ɾ��ָ�������е�ָ�����ݳ���");
            return mapping.findForward(forward);
        }
		// д������־
		actionDesc = "ɾ��ָ�������е�ָ�����ݳɹ�!";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);

        ActionForward actionForward = new ActionForward();
        actionForward.setPath("/web/intervenor/intervenorView.do?actionType=editView&id="
                              + intervenorId);
        return actionForward;
    }

    /**
     * �޸�������Ϣ
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    private ActionForward edit(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response)
    {

        String forward = Constants.FORWARD_COMMON_FAILURE;

        // �������л�ȡ������Ϣ
        String intervenorId = this.getParameter(request, "id").trim();
        String name = this.getParameter(request, "name").trim();
        String oldName = this.getParameter(request, "oldName").trim();
        String startSortId = this.getParameter(request, "startSortId").trim();
        String endSortId = this.getParameter(request, "endSortId").trim();
        String startDate = this.getParameter(request, "startDate")
                               .trim()
                               .replaceAll("-", "");
        String endDate = this.getParameter(request, "endDate")
                             .trim()
                             .replaceAll("-", "");
        
		String actionType = "�޸��˹���Ԥ������Ϣ";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = intervenorId;

        IntervenorVO vo = new IntervenorVO();
        vo.setId(Integer.parseInt(intervenorId));
        vo.setName(name);
        vo.setStartSortId(Integer.parseInt(startSortId));
        vo.setEndSortId(Integer.parseInt(endSortId));
        vo.setStartDate(startDate);
        vo.setEndDate(endDate);

        if (logger.isDebugEnabled())
        {
            logger.debug("�������л�ȡ��Ϣ��" + vo.toString());

        }

        // ����޸�����������ʱ
        if (!name.equals(oldName))
        {
            try
            {
                // �鿴���������Ƿ����
                if (IntervenorBO.getInstance().hasInternorName(name))
                {
        			// д������־
        			actionResult = false;
        			actionDesc = "�޸��˹���Ԥ������Ϣ�����Ѵ��ڴ���������!";
        	        this.actionLog(request,
        	                       actionType,
        	                       actionTarget,
        	                       actionResult,
        	                       actionDesc);
        	        
                    this.saveMessagesValue(request,
                                           "�޸��˹���Ԥ������Ϣ�����Ѵ��ڴ��������ơ����������룡");
                    return mapping.findForward(forward);
                }
            }
            catch (BOException e)
            {
    			// д������־
    			actionResult = false;
    			actionDesc = "�޸�������Ϣ����!";
    	        this.actionLog(request,
    	                       actionType,
    	                       actionTarget,
    	                       actionResult,
    	                       actionDesc);
    	        
                logger.error(e);
                this.saveMessagesValue(request, "�޸�������Ϣ����");
                return mapping.findForward(forward);
            }
        }

        // �޸�������Ϣ
        try
        {
            // ͨ����Ԥidɾ����Ӧ�ĸ�Ԥ����
            IntervenorBO.getInstance().editInternorVO(vo);
        }
        catch (BOException e)
        {
			// д������־
			actionResult = false;
			actionDesc = "�޸�������Ϣ����!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            logger.error(e);
            this.saveMessagesValue(request, "�޸�������Ϣ����");
            return mapping.findForward(forward);
        }

		// д������־
		actionDesc = "�޸�������Ϣ�ɹ�!";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "�޸�������Ϣ�ɹ���");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "intervenorView.do?actionType=editView&isReload=yes&id="
                                             + intervenorId);
        return mapping.findForward(forward);
    }

    /**
     * �����������������ݵ�����
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    private ActionForward setSort(ActionMapping mapping, ActionForm form,
                                  HttpServletRequest request,
                                  HttpServletResponse response)
    {

        String forward = Constants.FORWARD_COMMON_FAILURE;

        // �������л�ȡ��Ԥid
        String intervenorId = this.getParameter(request, "id").trim();
        String contentId = this.getParameter(request, "contentId").trim();
        
		String actionType = "�������������ݵ�����";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = intervenorId + "-" + contentId;
		
        if (logger.isDebugEnabled())
        {
            logger.debug("�������л�ȡ��Ԥid��" + intervenorId + ", contentId="
                         + contentId);
        }

        String[] sortValue = contentId.split(";");

        try
        {
            // �������������ݵ�����
            IntervenorBO.getInstance().editContentSort(intervenorId, sortValue);
        }
        catch (BOException e)
        {
			// д������־
			actionResult = false;
			actionDesc = "�������������ݵ�����ʱ����!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
        	
            logger.error(e);
            this.saveMessagesValue(request, "�������������ݵ�����ʱ����");
            return mapping.findForward(forward);
        }
		// д������־
		actionDesc = "�������������ݵ�����ɹ�!";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);

        ActionForward actionForward = new ActionForward();
        actionForward.setPath("/web/intervenor/intervenorView.do?actionType=editView&id="
                              + intervenorId);
        return actionForward;
    }

    /**
     * �����˹���Ԥ����
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    private ActionForward add(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response)
    {

        String forward = Constants.FORWARD_COMMON_FAILURE;

        // �������л�ȡ������Ϣ
        String name = this.getParameter(request, "name").trim();
        String startSortId = this.getParameter(request, "startSortId").trim();
        String endSortId = this.getParameter(request, "endSortId").trim();
        String startDate = this.getParameter(request, "startDate")
                               .trim()
                               .replaceAll("-", "");
        String endDate = this.getParameter(request, "endDate")
                             .trim()
                             .replaceAll("-", "");
        
        String actionType = "�����˹���Ԥ����";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = name;
		
        int id = -1;

        IntervenorVO vo = new IntervenorVO();
        vo.setName(name);
        vo.setStartSortId(Integer.parseInt(startSortId));
        vo.setEndSortId(Integer.parseInt(endSortId));
        vo.setStartDate(startDate);
        vo.setEndDate(endDate);

        if (logger.isDebugEnabled())
        {
            logger.debug("�������л�ȡ��Ϣ��" + vo.toString());
        }

        // �鿴���������Ƿ����
        try
        {
            if (IntervenorBO.getInstance().hasInternorName(name))
            {
            	// д������־
    			actionResult = false;
    			actionDesc = "�����˹���Ԥ������Ϣ�����Ѵ��ڴ���������!";
    	        this.actionLog(request,
    	                       actionType,
    	                       actionTarget,
    	                       actionResult,
    	                       actionDesc);
    	        
                this.saveMessagesValue(request, "�����˹���Ԥ������Ϣ�����Ѵ��ڴ��������ơ����������룡");
                return mapping.findForward(forward);
            }
        }
        catch (BOException e)
        {
        	// д������־
			actionResult = false;
			actionDesc = "�����˹���Ԥ������Ϣ����!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            logger.error(e);
            this.saveMessagesValue(request, "�����˹���Ԥ������Ϣ����");
            return mapping.findForward(forward);
        }

        // �޸�������Ϣ
        try
        {
            // �õ�id
            id = IntervenorBO.getInstance().getInternorId();
            vo.setId(id);
            // ͨ����Ԥidɾ����Ӧ�ĸ�Ԥ����
            IntervenorBO.getInstance().addInternorVO(vo);
        }
        catch (BOException e)
        {
        	// д������־
			actionResult = false;
			actionDesc = "�����˹���Ԥ������Ϣ����!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            logger.error(e);
            this.saveMessagesValue(request, "�����˹���Ԥ������Ϣ����");
            return mapping.findForward(forward);
        }
        // д������־
		actionDesc = "�����˹���Ԥ������Ϣ�ɹ�";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "�����˹���Ԥ������Ϣ�ɹ�����������Ϊ" + id);
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "intervenorView.do?actionType=editView&isReload=yes&id="
                                             + id);
        return mapping.findForward(forward);
    }

    /**
     * �����������˹���Ԥ����
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    private ActionForward addContent(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response)
    {

        String forward = Constants.FORWARD_COMMON_FAILURE;

        // �������л�ȡ������Ϣ
        String id = this.getParameter(request, "id").trim();
        String contentId = this.getParameter(request, "contentId").trim();
        
        String actionType = "�����������˹���Ԥ����";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = id + "-" + contentId;

        if (logger.isDebugEnabled())
        {
            logger.debug("�������л�ȡ��Ϣ��id=" + id + ", contentId=" + contentId);

        }

        String[] contentIds = contentId.split(",");

        for (int i = 0; i < contentIds.length; i++)
        {
            String temp = contentIds[i];

            try
            {
                IntervenorGcontentVO vo = IntervenorBO.getInstance()
                                                      .getContentVO(id, temp);

                if (temp.equals(vo.getId()) && id.equals(vo.getIntervenorId()))
                {
        			// д������־
        			actionResult = false;
        			actionDesc = "�����������˹���Ԥ���������������Ѵ�������!";
        	        this.actionLog(request,
        	                       actionType,
        	                       actionTarget,
        	                       actionResult,
        	                       actionDesc);
        	        
                    this.saveMessagesValue(request, "�����������˹���Ԥ���������������Ѵ������� "
                                                    + temp);
                    return mapping.findForward(forward);
                }
            }
            catch (BOException e)
            {
    			// д������־
    			actionResult = false;
    			actionDesc = "��ѯ�����Ƿ�������˹���Ԥ����ʱ����!";
    	        this.actionLog(request,
    	                       actionType,
    	                       actionTarget,
    	                       actionResult,
    	                       actionDesc);
    	        
                logger.error(e);
                this.saveMessagesValue(request, "��ѯ�����Ƿ�������˹���Ԥ����ʱ����!");
                return mapping.findForward(forward);
            }
        }

        try
        {
            // �����������˹���Ԥ����
            IntervenorBO.getInstance()
                        .addGcontentToIntervenorId(id, contentIds);
        }
        catch (BOException e)
        {
			// д������־
			actionResult = false;
			actionDesc = "�����������˹���Ԥ��������!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            logger.error(e);
            this.saveMessagesValue(request, "�����������˹���Ԥ��������");
            return mapping.findForward(forward);
        }
        // д������־
		actionDesc = "�����������˹���Ԥ������Ϣ�ɹ�";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "�����������˹���Ԥ������Ϣ�ɹ���");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "intervenorView.do?actionType=editView&id=" + id);
        return mapping.findForward(forward);
    }

    /**
     * ���ڲ�ѯ������Ϣ
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    private ActionForward queryContent(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request,
                                       HttpServletResponse response)
    {

        String forward = "queryContent";

        // �������л�ȡ������Ϣ
        String id = this.getParameter(request, "id").trim();
        String name = this.getParameter(request, "name").trim();
        String spName = this.getParameter(request, "spName").trim();
        String keywordsDesc = this.getParameter(request, "keywordsDesc").trim();
        String contentId = this.getParameter(request, "contentId").trim();
        String contentTag = this.getParameter(request, "contentTag").trim();

        PageResult page = new PageResult(request);
        page.setPageSize(Integer.parseInt(PageSizeConstants.page_DEFAULT));

        if (logger.isDebugEnabled())
        {
            logger.debug("�������л�ȡ��Ϣ��id=" + id + ", name=" + name);

        }

        try
        {
            // ���ڲ�ѯ������Ϣ
            IntervenorBO.getInstance().queryGcontentList(page,
                                                         id,
                                                         name,
                                                         spName,
                                                         keywordsDesc,
                                                         contentId,
                                                         contentTag);
        }
        catch (BOException e)
        {
            logger.error(e);
            this.saveMessagesValue(request, "��ѯ������Ϣ����");
            return mapping.findForward(forward);
        }

        request.setAttribute("actionType", forward);
        request.setAttribute("id", id);
        request.setAttribute("name", name);
        request.setAttribute("spName", spName);
        request.setAttribute("keywordsDesc", keywordsDesc);
        request.setAttribute("contentId", contentId);
        request.setAttribute("contentTag", contentTag);
        request.setAttribute("PageResult", page);
        request.setAttribute("isQuery", "true");
        request.setAttribute("size", String.valueOf(page.getPageInfo().size()));
        return mapping.findForward(forward);
    }

    /**
     * �����������������ļ�
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    private ActionForward importFile(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response)
    {

        String forward = Constants.FORWARD_COMMON_FAILURE;

        // �������л�ȡ����id
        String id = this.getParameter(request, "id").trim();

        // �õ����������ļ���Ϣ
        DataImportForm iForm = ( DataImportForm ) form;
        
        FormFile dataFile = iForm.getDataFile();

		String actionType = "���������ļ�������ָ��������";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = dataFile.getFileName();
		
        // У���ļ���׺��
        if(!iForm.checkFileNameExtension(new String[]{"xls"}))
        {
            this.saveMessages(request, "�ļ���׺������");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        
        if (logger.isDebugEnabled())
        {
            logger.debug("�������л�ȡ����id��Ϣ��id=" + id);

        }

        try
        {
            // ���������ļ�����
            IntervenorBO.getInstance().importFileById(id, dataFile);
        }
        catch (BOException e)
        {
			// д������־
			actionResult = false;
			actionDesc = "���������ļ�������ָ��������ʱ����!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            logger.error(e);
            this.saveMessagesValue(request, "���������ļ�������ָ��������ʱ����");
            return mapping.findForward(forward);
        }
		// д������־
		actionDesc = "�����������������ļ���Ϣ�ɹ�";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "�����������������ļ���Ϣ�ɹ���");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "intervenorView.do?actionType=editView&id=" + id);
        return mapping.findForward(forward);
    }
}
