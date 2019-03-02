package com.aspire.ponaadmin.web.comic.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basecomic.bo.BlackBO;
import com.aspire.dotcard.basecomic.vo.BlackVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.repository.web.DataImportForm;

/**
 * ������������ɾ�����������
 */
public class BlackAction extends BaseAction
{

    private static final JLogger LOG = LoggerFactory.getLogger(BlackAction.class);

    /**
     * ACTION
     */
    public ActionForward doPerform(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BOException
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("����doPerform()����");
        }

        // �������л�ȡ��������
        String method = this.getParameter(request, "method");

        if ("query".equals(method))
        {
            return query(mapping, form, request, response);
        }
        else if ("query_cb_content".equals(method))
        {
            return query_cb_content(mapping, form, request, response);
        }
        else if ("remove".equals(method))
        {
            return remove(mapping, form, request, response);
        }
        else if ("add".equals(method))
        {
            return add(mapping, form, request, response);
        }
        else if ("importData".equals(method))
        {
            return importData(mapping, form, request, response);
        }
        else
        {
            this.saveMessagesValue(request, "�Բ��������ʵ�·��������");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
    }

    /**
     * ��ѯ�����������б���Ϣ
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward query(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BOException
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("���� query() ����");
        }
        String actionType = "��ѯ������������Ϣ";
        String actionDesc = "��ѯ������������Ϣ�ɹ�!";
        String actionTarget = "BlackAction.query()";
        boolean actionResult = true;

        String contentId = request.getParameter("contentId") == null ? "" : request.getParameter("contentId");
        String contentName = request.getParameter("contentName") == null ? "" : request.getParameter("contentName");

        PageResult page = new PageResult(request);
        BlackVO vo = new BlackVO();
        vo.setContentId(contentId);
        vo.setContentName(contentName);

        try
        {
            BlackBO.getInstance().queryBlackList(page, vo);
        }
        catch (BOException e)
        {
            actionResult = false;
            actionDesc = "��ѯ������������Ϣ����";
            LOG.error(actionDesc, e);
            // д������־
            this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
            this.saveMessagesValue(request, actionDesc);
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        if (LOG.isDebugEnabled())
        {
            LOG.debug(actionDesc);
        }

        request.setAttribute("PageResult", page);
        request.setAttribute("contentId", contentId);
        request.setAttribute("contentName", contentName);

        // д������־
        this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);

        if (LOG.isDebugEnabled())
        {
            LOG.debug("�뿪 query() ����");
        }
        return mapping.findForward(Constants.FORWARD_QUERY_TOKEN);
    }

    /**
     * ��ѯ���������б���Ϣ
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward query_cb_content(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BOException
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("���� query_cb_content() ����");
        }
        String forward = "query_cb_content";
        String actionType = "��ѯ���������б���Ϣ";
        String actionDesc = "��ѯ���������б���Ϣ�ɹ�!";
        String actionTarget = "BlackAction.query_cb_content()";
        boolean actionResult = true;

        // �������л�ȡ��������
        String isFirst = this.getParameter(request, "isFirst").trim();
        String contentName = this.getParameter(request, "contentName").trim();
        String contentId = this.getParameter(request, "contentId").trim();
        BlackVO vo = new BlackVO();
        PageResult page = new PageResult(request);

        // ����ǵ�һ�Ρ�����
        if ("1".equals(isFirst))
        {
            request.setAttribute("isFirst", isFirst);
            request.setAttribute("method", forward);
            request.setAttribute("PageResult", page);
            request.setAttribute("contentName", contentName);
            request.setAttribute("contentId", contentId);

            return mapping.findForward(forward);
        }

        vo.setContentId(contentId);
        vo.setContentName(contentName);

        try
        {
            BlackBO.getInstance().queryContentList(page, vo);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "��ѯ�����б����");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        request.setAttribute("method", forward);
        request.setAttribute("PageResult", page);
        request.setAttribute("contentName", contentName);
        request.setAttribute("contentId", contentId);

        // д������־
        this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);

        if (LOG.isDebugEnabled())
        {
            LOG.debug("�뿪 query_cb_content() ����");
        }

        return mapping.findForward(forward);
    }

    /**
     * ɾ������������
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BOException
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("���� remove() ����");
        }
        String actionType = "ɾ��������������Ϣ";
        String actionDesc = "ɾ��������������Ϣ�ɹ�!";
        String actionTarget = "BlackAction.remove()";
        String[] id = request.getParameterValues("dealRef");
        boolean actionResult = true;

        if (id == null || (id != null && id.length <= 0))
        {
            actionResult = false;
            actionDesc = "ɾ����������id����Ϊ��!";
            LOG.warn(actionDesc);
            // д������־
            this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
            this.saveMessagesValue(request, actionDesc);
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        try
        {
            BlackBO.getInstance().removeBlack(id);
        }
        catch (BOException e)
        {
            actionResult = false;
            actionDesc = "ɾ��������������Ϣ����";
            LOG.error(actionDesc, e);
            // д������־
            this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
            this.saveMessagesValue(request, actionDesc);
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        if (LOG.isDebugEnabled())
        {
            LOG.debug(actionDesc);
        }

        request.setAttribute(Constants.PARA_GOURL, "black.do?method=query");

        // д������־
        this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
        this.saveMessagesValue(request, actionDesc);

        if (LOG.isDebugEnabled())
        {
            LOG.debug("�뿪 remove() ����");
        }
        return mapping.findForward(Constants.FORWARD_COMMON_SUCCESS);
    }

    /**
     * ��������������
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    @SuppressWarnings("unchecked")
    public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BOException
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("���� add() ����");
        }
        String actionType = "��������������";
        String actionDesc = "���������������ɹ�!";
        String actionTarget = "BlackAction.add()";
        String contentId = this.getParameter(request, "contentId");
        boolean actionResult = true;

        if (StringUtils.isBlank(contentId))
        {
            actionResult = false;
            actionDesc = "���ʧ�ܣ�������������IDΪ��!";
            LOG.warn(actionDesc);
            // д������־
            this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
            this.saveMessagesValue(request, actionDesc);

            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        String[] contentIdArray = contentId.split(";");
        if (contentIdArray == null || (contentIdArray != null && contentIdArray.length <= 0))
        {
            actionResult = false;
            actionDesc = "���ʧ�ܣ�������������IDΪ��!";
            LOG.warn(actionDesc);
            // д������־
            this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
            this.saveMessagesValue(request, actionDesc);
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        try
        {

            // Map<String, Object> resultMap1 = BlackBO.getInstance().isExistContent(contentIdArray);
            // List<String> list1 = (List<String>)resultMap1.get("list");
            // String msg11 = (String)resultMap1.get("msg1");
            // String msg12 = (String)resultMap1.get("msg2");

            List<String> list1 = new ArrayList<String>(contentIdArray.length);
            for (String id : contentIdArray)
            {
                list1.add(id);
            }

            Map<String, Object> resultMap2 = BlackBO.getInstance().isExistBlack(list1);
            List<String> list2 = (List<String>)resultMap2.get("list");
            String msg21 = (String)resultMap2.get("msg1");
            String msg22 = (String)resultMap2.get("msg2");

            String arr[] = new String[list2.size()];
            list2.toArray(arr);

            int result = BlackBO.getInstance().addBlack(arr);

            actionDesc = "��������������" + result + "����¼!";

//            if (StringUtils.isNotBlank(msg11))
//            {
//                actionDesc = actionDesc + "<br/>" + msg11;
//            }
//            if (StringUtils.isNotBlank(msg12))
//            {
//                actionDesc = actionDesc + "<br/>" + msg12;
//            }
            if (StringUtils.isNotBlank(msg21))
            {
                actionDesc = actionDesc + "<br/>" + msg21;
            }
            if (StringUtils.isNotBlank(msg22))
            {
                actionDesc = actionDesc + "<br/>" + msg22;
            }

        }
        catch (BOException e)
        {
            // д������־
            actionResult = false;
            actionDesc = "������������������!";
            LOG.error(actionDesc, e);
            this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
            this.saveMessagesValue(request, "�������ָ���ĺ�����������ʱ����");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        if (LOG.isDebugEnabled())
        {
            LOG.debug(actionDesc);
        }

        request.setAttribute(Constants.PARA_GOURL, "black.do?method=query");

        // д������־
        this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
        this.saveMessagesValue(request, actionDesc);

        if (LOG.isDebugEnabled())
        {
            LOG.debug("�뿪 add() ����");
        }
        return mapping.findForward(Constants.FORWARD_COMMON_SUCCESS);
    }

    /**
     * ������������ϢEXCEL����
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    @SuppressWarnings("unchecked")
    public ActionForward importData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BOException
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("���� importData() ����");
        }
        int result = 0;
        String actionType = "���붯����������Ϣ";
        String actionDesc = "���붯����������Ϣ�ɹ�!";
        String actionTarget = "BlackAction.importData()";
        DataImportForm iForm = (DataImportForm)form;
        String addType = this.getParameter(request, "addType");
        boolean actionResult = true;

        if (StringUtils.isBlank(addType))
        {
            actionResult = false;
            actionDesc = "����ʧ�ܣ�������������IDΪ��!";
            LOG.warn(actionDesc);
            // д������־
            this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
            this.saveMessagesValue(request, actionDesc);
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        // У���ļ���׺��
        if (!iForm.checkFileNameExtension(new String[] { "xls","xlsx"}))
        {
            actionResult = false;
            actionDesc = "�ļ���׺������!";
            LOG.warn(actionDesc);
            // д������־
            this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
            this.saveMessagesValue(request, actionDesc);
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        FormFile dataFile = iForm.getDataFile();

        if (dataFile == null)
        {
            actionResult = false;
            actionDesc = "�������Ϊ��!";
            LOG.warn(actionDesc);
            // д������־
            this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
            this.saveMessagesValue(request, actionDesc);
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        if (LOG.isDebugEnabled())
        {
            LOG.debug("�����ļ����ƣ�" + dataFile.getFileName() + ";�ļ���С��" + dataFile.getFileSize());
        }
        try
        {
            String[] arrList = BlackBO.getInstance().getImportBalckParaseDataList(dataFile);
            Map<String, Object> resultMap1 = BlackBO.getInstance().isExistContent(arrList);
            List<String> list1 = (List<String>)resultMap1.get("list");
            String msg11 = (String)resultMap1.get("msg1");
            String msg12 = (String)resultMap1.get("msg2");

            if ("ADD".equals(addType))
            {
                Map<String, Object> resultMap2 = BlackBO.getInstance().isExistBlack(list1);
                List<String> list2 = (List<String>)resultMap2.get("list");
                String msg21 = (String)resultMap2.get("msg1");
                String msg22 = (String)resultMap2.get("msg2");
                String arr[] = new String[list2.size()];
                list2.toArray(arr);

                result = BlackBO.getInstance().importBlackADD(arr);

                actionDesc = "�������붯��������" + result + "����¼!";
                if (StringUtils.isNotBlank(msg11))
                {
                    actionDesc = actionDesc + "<br/>" + msg11;
                }
                if (StringUtils.isNotBlank(msg12))
                {
                    actionDesc = actionDesc + "<br/>" + msg12;
                }
                if (StringUtils.isNotBlank(msg21))
                {
                    actionDesc = actionDesc + "<br/>" + msg21;
                }
                if (StringUtils.isNotBlank(msg22))
                {
                    actionDesc = actionDesc + "<br/>" + msg22;
                }
            }
            else if ("ALL".equals(addType))
            {
                result = BlackBO.getInstance().importBlackALL(list1);
                actionDesc = "ȫ�����붯��������" + result + "����¼!";
                if (StringUtils.isNotBlank(msg11))
                {
                    actionDesc = actionDesc + "<br/>" + msg11;
                }
                if (StringUtils.isNotBlank(msg12))
                {
                    actionDesc = actionDesc + "<br/>" + msg12;
                }
            }

        }
        catch (BOException e)
        {
            actionResult = false;
            actionDesc = "�������������!";
            LOG.error(actionDesc, e);
            // д������־
            this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
            this.saveMessagesValue(request, actionDesc);

            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        if (LOG.isDebugEnabled())
        {
            LOG.debug(actionDesc);
        }
        request.setAttribute(Constants.PARA_GOURL, "black.do?method=query");

        // д������־
        this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
        this.saveMessagesValue(request, actionDesc);

        if (LOG.isDebugEnabled())
        {
            LOG.debug("�뿪 importData() ����");
        }
        return mapping.findForward(Constants.FORWARD_COMMON_SUCCESS);
    }

}
