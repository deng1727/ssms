/*
 * 
 */
package com.aspire.ponaadmin.web.category.ui.condition;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.category.rule.condition.ConditionVO;
import com.aspire.ponaadmin.web.constant.Constants;


/**
 * @author x_wangml
 *
 */
public class ConditionUpdateAction extends BaseAction
{
    /**
     * ��־����
     */
    protected static final JLogger logger = LoggerFactory.getLogger(ConditionUpdateAction.class);

    public ActionForward doPerform(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("doPerform()");
        }

        // �������л�ȡ��������
        String action = this.getParameter(request, "action").trim();

        if ("addView".equals(action))
        {
            return addView(mapping, form, request, response);
        }
        if ("add".equals(action))
        {
            return add(mapping, form, request, response);
        }
        else if ("editView".equals(action))
        {
            return editView(mapping, form, request, response);
        }
        else if ("edit".equals(action))
        {
            return edit(mapping, form, request, response);
        }
        else if ("delete".equals(action))
        {
            return delete(mapping, form, request, response);
        }
        else
        {
            String forward = Constants.FORWARD_COMMON_FAILURE;
            this.saveMessagesValue(request, "�Բ��������ʵ�·��������");
            return mapping.findForward(forward);
        }
    }

    private ActionForward addView(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    {
        String forward = "addView";
        // �������л�ȡ����
        String id = this.getParameter(request, "id").trim();
        List baseCondList = new ArrayList();
        
        if (logger.isDebugEnabled())
        {
            logger.debug("�������л�ȡ������id=" + id);

        }
        // ��ѯ�������������б�
        try
        {
            // ��ѯ�������������б�
            baseCondList = ConditionUpdateBO.getInstance().queryBaseCondList();
        }
        catch (BOException e)
        {
            logger.error(e);
            forward = Constants.FORWARD_COMMON_FAILURE;
            this.saveMessagesValue(request, "��ѯ�������������б���Ϣ����");
            return mapping.findForward(forward);
        }

        request.setAttribute("id", id);
        request.setAttribute("baseCondList", baseCondList);
        
        return mapping.findForward(forward);
    }

    private ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    {
        String forward = Constants.FORWARD_COMMON_FAILURE;

        // �������л�ȡ����
        String ruleId = this.getParameter(request, "ruleId").trim();
        String cid = this.getParameter(request, "id");
        String condType = this.getParameter(request, "condType").trim();
        String wSql = this.getParameter(request, "wSql").trim();
        String oSql = this.getParameter(request, "oSql").trim();
        String count = this.getParameter(request, "count").trim();
        String sortId = this.getParameter(request, "sortId").trim();

        // ��װ��Ϣ
        ConditionVO vo = new ConditionVO();
        vo.setRuleId(Integer.parseInt(ruleId));
        vo.setCid(cid);
        vo.setCondType(Integer.parseInt(condType));
        vo.setWSql(wSql);
        vo.setOSql(oSql);
        
		String actionType = "��������������Ϣ";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = cid;
		
        if(!"".equals(count))
        {
            vo.setCount(Integer.parseInt(count));
        }
        if(!"".equals(sortId))
        {
            vo.setSortId(Integer.parseInt(sortId));
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("�������л�ȡ������vo=" + vo.toString());

        }
        // ��������������Ϣ
        try
        {
            // ����������Ϣ
            ConditionUpdateBO.getInstance().addConditeionVO(vo);
        }
        catch (BOException e)
        {
			// д������־
			actionResult = false;
			actionDesc = "��������������Ϣ����!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            logger.error(e);
            this.saveMessagesValue(request, "��������������Ϣ����");
            return mapping.findForward(forward);
        }
		// д������־
		actionDesc = "��������������Ϣ�ɹ�!";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "��������������Ϣ�ɹ���");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL, "ruleView.do?action=editView&ruleId="+ruleId);
        return mapping.findForward(forward);
    }

    private ActionForward editView(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    {
        String forward = "editView";
        
        // �������л�ȡ����
        String ruleId = this.getParameter(request, "ruleId").trim();
        String id = this.getParameter(request, "id");
        ConditionVO vo = null;
        List baseCondList = new ArrayList();
        
        if (logger.isDebugEnabled())
        {
            logger.debug("�������л�ȡ������id=" + id);

        }
        // ɾ������������Ϣ
        try
        {
            // ɾ��������Ϣ
            vo = ConditionUpdateBO.getInstance().getConditionVOById(id);
        }
        catch (BOException e)
        {
            logger.error(e);
            forward = Constants.FORWARD_COMMON_FAILURE;
            this.saveMessagesValue(request, "���ݱ����ѯ����������Ϣ����");
            return mapping.findForward(forward);
        }
        
        // ��ѯ�������������б�
        try
        {
            // ��ѯ�������������б�
            baseCondList = ConditionUpdateBO.getInstance().queryBaseCondList();
        }
        catch (BOException e)
        {
            logger.error(e);
            forward = Constants.FORWARD_COMMON_FAILURE;
            this.saveMessagesValue(request, "��ѯ�������������б���Ϣ����");
            return mapping.findForward(forward);
        }

        request.setAttribute("id", id);
        request.setAttribute("ruleId", ruleId);
        request.setAttribute("vo", vo);
        request.setAttribute("baseCondList", baseCondList);
        return mapping.findForward(forward);
    }

    private ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    {
        String forward = Constants.FORWARD_COMMON_FAILURE;

        // �������л�ȡ����
        String ruleId = this.getParameter(request, "ruleId").trim();
        String cid = this.getParameter(request, "id");
        String condType = this.getParameter(request, "condType").trim();
        String wSql = this.getParameter(request, "wSql").trim();
        String oSql = this.getParameter(request, "oSql").trim();
        String count = this.getParameter(request, "count").trim();
        String sortId = this.getParameter(request, "sortId").trim();
        String condId = this.getParameter(request, "condId");
        
        
        // ��װ��Ϣ
        ConditionVO vo = new ConditionVO();
        vo.setRuleId(Integer.parseInt(ruleId));
        vo.setCid(cid);
        vo.setCondType(Integer.parseInt(condType));
        vo.setWSql(wSql);
        vo.setOSql(oSql);
        vo.setSortId(Integer.parseInt(sortId));
        vo.setId(Integer.parseInt(condId));
        
		String actionType = "�޸Ĺ���������Ϣ";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = cid;
        
        if(!"".equals(count))
        {
            vo.setCount(Integer.parseInt(count));
        }
        

        if (logger.isDebugEnabled())
        {
            logger.debug("�������л�ȡ������vo=" + vo.toString());

        }
        // �޸Ĺ���������Ϣ
        try
        {
            // �޸Ĺ�����Ϣ
            ConditionUpdateBO.getInstance().editConditeionVO(vo);
        }
        catch (BOException e)
        {
			// д������־
			actionResult = false;
			actionDesc = "�޸Ĺ���������Ϣ����!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            logger.error(e);
            this.saveMessagesValue(request, "�޸Ĺ���������Ϣ����");
            return mapping.findForward(forward);
        }
		// д������־
		actionDesc = "�޸Ĺ���������Ϣ�ɹ���";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "�޸Ĺ���������Ϣ�ɹ���");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL, "ruleView.do?action=editView&ruleId="+ruleId);
        return mapping.findForward(forward);
    }
    
    private ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    {
        String forward = Constants.FORWARD_COMMON_FAILURE;

        // �������л�ȡ����
        String ruleId = this.getParameter(request, "ruleId").trim();
        String id = this.getParameter(request, "id");
        
		String actionType = "�޸Ĺ���������Ϣ";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = id;
		
        if (logger.isDebugEnabled())
        {
            logger.debug("�������л�ȡ������id=" + id);

        }
        // ɾ������������Ϣ
        try
        {
            // ɾ��������Ϣ
            ConditionUpdateBO.getInstance().deleteConditeionVO(id);
        }
        catch (BOException e)
        {
			// д������־
			actionResult = false;
			actionDesc = "ɾ������������Ϣ����!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            logger.error(e);
            this.saveMessagesValue(request, "ɾ������������Ϣ����");
            this.actionLog(request, "ɾ����������", id, false, "");
            return mapping.findForward(forward);
        }
		// д������־
		actionDesc = "ɾ������������Ϣ�ɹ���";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);

        this.saveMessagesValue(request, "ɾ������������Ϣ�ɹ���");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL, "ruleView.do?action=editView&ruleId="+ruleId);
        this.actionLog(request, "ɾ����������", id, true, "");
        return mapping.findForward(forward);
    }


}
