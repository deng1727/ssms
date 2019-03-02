/*
 * 
 */

package com.aspire.ponaadmin.web.category.ui;

import java.util.Date;
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
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.category.CategoryRuleDAO;
import com.aspire.ponaadmin.web.category.CategoryRuleExcutor;
import com.aspire.ponaadmin.web.category.CategoryRuleVO;
import com.aspire.ponaadmin.web.category.SynCategoryTask;
import com.aspire.ponaadmin.web.category.rule.RuleBO;
import com.aspire.ponaadmin.web.category.rule.RuleVO;
import com.aspire.ponaadmin.web.category.ui.condition.ConditionUpdateBO;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.system.PageSizeConstants;

/**
 * @author x_wangml
 * 
 */
public class CategoryUpdateAction extends BaseAction
{

    /**
     * ��־����
     */
    protected static final JLogger logger = LoggerFactory.getLogger(CategoryUpdateAction.class);

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
        String action = this.getParameter(request, "action").trim();

        if ("view".equals(action))
        {
            return view(mapping, form, request, response);
        }
        else if ("editView".equals(action))
        {
            return editView(mapping, form, request, response);
        }
        else if ("edit".equals(action))
        {
            return edit(mapping, form, request, response);
        }
        else if ("dell".equals(action))
        {
            return del(mapping, form, request, response);
        }
        else if ("add".equals(action))
        {
            return add(mapping, form, request, response);
        }
        else if ("exe".equals(action))
        {
            return exe(mapping, form, request, response);
        }
        else if ("exeSyn".equals(action))
        {
            return exeSyn(mapping, form, request, response);
        }
        else
        {
            String forward = Constants.FORWARD_COMMON_FAILURE;
            this.saveMessagesValue(request, "�Բ��������ʵ�·��������");
            return mapping.findForward(forward);
        }

    }

    /**
     * ����ʾ����������д���
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward editView(ActionMapping mapping, ActionForm form,
                                  HttpServletRequest request,
                                  HttpServletResponse response)
                    throws BOException
    {

        String forward = "editView";

        // �������л�ȡ��������
        String id = this.getParameter(request, "id").trim();
        String lastExcuteTime = this.getParameter(request, "lastExcuteTime")
                                    .trim();
        String effectiveTime = this.getParameter(request, "effectiveTime")
                                   .trim();

        if (logger.isDebugEnabled())
        {
            logger.debug("�������л�ȡ�������룺" + id);

        }
        // ͨ�����������ȡ���ܹ�������
        CategoryRuleVO vo = CategoryUpdateBO.getInstance()
                                            .getCategoryRuleVOByID(id);

        if (vo == null)
        {
            throw new BOException("���ݻ�������ID�Ҳ�����Ӧ�Ĺ�����Ϣ��");
        }

        request.setAttribute("vo", vo);
        request.setAttribute("lastExcuteTime", lastExcuteTime);
        request.setAttribute("effectiveTime", effectiveTime);

        return mapping.findForward(forward);
    }

    /**
     * �Թ�����ʾ����������д���
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward view(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response) throws BOException
    {

        String forward = "view";
        PageResult page = new PageResult(request);
        page.setPageSize(Integer.parseInt(PageSizeConstants.page_DEFAULT));

        // �������л�ȡ����id
        String ruleId = this.getParameter(request, "ruleId").trim();

        if (logger.isDebugEnabled())
        {
            logger.debug("�������л�ȡ����id��" + ruleId);

        }
        // ͨ�����������ȡ��������
        RuleVO vo = CategoryUpdateBO.getInstance().getCateRulesVOByID(ruleId);

        if (vo == null)
        {
            throw new BOException("���ݹ���id�Ҳ�����Ӧ�Ĺ�����Ϣ��");
        }

        if (vo == null)
        {
            throw new BOException("���ݹ���id�Ҳ�����Ӧ�Ĺ�����Ϣ��");
        }

        // ���ݹ������õ����ڴ˹��������
        ConditionUpdateBO.getInstance().queryCondListByID(page, ruleId);

        // ��list���õ�page�����ڷ�ҳչʾ
        request.setAttribute("PageResult", page);
        request.setAttribute("vo", vo);

        return mapping.findForward(forward);
    }

    /**
     * �Թ�����ʾ����������д���
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward del(ActionMapping mapping, ActionForm form,
                             HttpServletRequest request,
                             HttpServletResponse response) throws BOException
    {

        String forward = Constants.FORWARD_COMMON_FAILURE;

        // �������л�ȡ��������
        String id = this.getParameter(request, "id").trim();

		String actionType = "ͨ����������ɾ����Ӧ�Ļ��ܹ���";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = id;
		
        if (logger.isDebugEnabled())
        {
            logger.debug("�������л�ȡ�������룺" + id);

        }
        // ͨ����������ɾ����Ӧ�Ļ��ܹ���
        try
        {
            CategoryUpdateBO.getInstance().dellCateRulesVOByID(id);
        }
        catch (BOException e)
        {
			// д������־
			actionResult = false;
			actionDesc = "ͨ����������ɾ����Ӧ�Ļ��ܹ������!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            logger.error(e);
            this.saveMessagesValue(request, "ͨ����������ɾ����Ӧ�Ļ��ܹ������");
            return mapping.findForward(forward);
        }
        
		// д������־
		actionDesc = "ͨ����������ɾ����Ӧ�Ļ��ܹ���ɹ�";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);

        this.saveMessagesValue(request, "ͨ����������ɾ����Ӧ�Ļ��ܹ���ɹ���");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL, "categoryRuleList.do");
        return mapping.findForward(forward);
    }

    /**
     * �޸Ļ��ܹ�����Ϣ
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

        String forward = Constants.FORWARD_COMMON_FAILURE;

        // �������л�ȡ����
        String id = this.getParameter(request, "id").trim();
        String ruleId = this.getParameter(request, "ruleId").trim();
        String effectiveTime = this.getParameter(request, "effectiveTime")
                                   .trim()
                                   .replaceAll("-", "")
                                   .replaceAll(" ", "")
                                   .replaceAll(":", "");
        
		String actionType = "�޸Ļ��ܹ�����Ϣ";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = id;

        if (logger.isDebugEnabled())
        {
            logger.debug("�������л�ȡ������cid=" + id + " ruleId=" + ruleId
                         + " effectiveTime=" + effectiveTime);

        }
        // �޸Ļ��ܹ�����Ϣ
        try
        {
            RuleVO rule = CategoryUpdateBO.getInstance()
                                          .getCateRulesVOByID(ruleId);

            if (Integer.parseInt(ruleId) == 0
                || rule.getRuleId() != Integer.parseInt(ruleId))
            {
                logger.error("������Ĺ��򲻴��ڣ��������Ѵ�������");
                this.saveMessagesValue(request, "������Ĺ��򲻴��ڣ��������Ѵ�������");
                return mapping.findForward(forward);
            }

            CategoryUpdateBO.getInstance().editCateRulesVOByID(id,
                                                               ruleId,
                                                               effectiveTime);
        }
        catch (BOException e)
        {
			// д������־
			actionResult = false;
			actionDesc = "�޸Ļ��ܹ�����Ϣ����!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            logger.error(e);
            this.saveMessagesValue(request, "�޸Ļ��ܹ�����Ϣ����");
            return mapping.findForward(forward);
        }
		// д������־
		actionDesc = "�޸Ļ��ܹ�����Ϣ�ɹ�";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);

        this.saveMessagesValue(request, "�޸Ļ��ܹ�����Ϣ�ɹ���");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL, "categoryRuleList.do");
        return mapping.findForward(forward);
    }

    /**
     * �������ܹ�����Ϣ
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

        String forward = Constants.FORWARD_COMMON_FAILURE;

        // �������л�ȡ����
        String id = this.getParameter(request, "id").trim();
        String ruleId = this.getParameter(request, "ruleId").trim();
        String effectiveTime = this.getParameter(request, "effectiveTime")
                                   .trim()
                                   .replaceAll("-", "")
                                   .replaceAll(" ", "")
                                   .replaceAll(":", "");

		String actionType = "�������ܹ�����Ϣ";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = id + "-" + ruleId;
		
        if (logger.isDebugEnabled())
        {
            logger.debug("�������л�ȡ������cid=" + id + " ruleId=" + ruleId
                         + " effectiveTime=" + effectiveTime);

        }
        // �޸Ļ��ܹ�����Ϣ
        try
        {
            // ͨ�����������ȡ�Ƿ��Ѵ��ڴ˻���
            CategoryRuleVO categoryRule = CategoryUpdateBO.getInstance()
                                                          .getCategoryRuleVOByID(id);
            if (id.equals(categoryRule.getCid()))
            {
                logger.error("������Ļ���������Ӧ�����Ѵ��ڣ����������룡");
                this.saveMessagesValue(request, "������Ļ���������Ӧ�����Ѵ��ڣ����������룡");
                return mapping.findForward(forward);
            }

            // ͨ���û��������id��ѯ�Ƿ���ڴ˹���
            RuleVO rule = CategoryUpdateBO.getInstance()
                                          .getCateRulesVOByID(ruleId);

            if (Integer.parseInt(ruleId) == 0
                || rule.getRuleId() != Integer.parseInt(ruleId))
            {
                logger.error("������Ĺ��򲻴��ڣ��������Ѵ�������");
                this.saveMessagesValue(request, "������Ĺ��򲻴��ڣ��������Ѵ�������");
                return mapping.findForward(forward);
            }

            CategoryUpdateBO.getInstance().addCateRulesVOByID(id,
                                                              ruleId,
                                                              effectiveTime);
        }
        catch (BOException e)
        {
			// д������־
			actionResult = false;
			actionDesc = "�������ܹ�����Ϣ����";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            logger.error(e);
            this.saveMessagesValue(request, "�������ܹ�����Ϣ����");
            return mapping.findForward(forward);
        }
		// д������־
		actionDesc = "�������ܹ�����Ϣ�ɹ�";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);

        this.saveMessagesValue(request, "�������ܹ�����Ϣ�ɹ���");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL, "categoryRuleList.do");
        return mapping.findForward(forward);
    }

    /**
     * ִ�й���
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward exe(ActionMapping mapping, ActionForm form,
                             HttpServletRequest request,
                             HttpServletResponse response) throws BOException
    {

        String forward = Constants.FORWARD_COMMON_FAILURE;

        // �������л�ȡ��������
        String id = this.getParameter(request, "id").trim();
        String[] ids = id.split(",");
        
		String actionType = "ִ�л��ܹ���";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = id;
		

        if (logger.isDebugEnabled())
        {
            logger.debug("ִ�л��ܹ��� cid��" + id);
        }
        try
        {
            for (int i = 0; i < ids.length; i++)
            {
            	  // ���¹����ϴ�ִ��ʱ��
                //CategoryRuleDAO.getInstance().updateRuleLastExecTime(ids[i],
                //                                                     null);
            	long starttime = System.currentTimeMillis();
                // ͨ�����������ȡ�Ƿ��Ѵ��ڴ˻���
                CategoryRuleVO categoryRule = CategoryUpdateBO.getInstance()
                                                              .getCategoryRuleVOByID(ids[i]);

                // �õ����ܹ���
                Map rulesMap = RuleBO.getInstance().getAllRules();
                categoryRule.setRuleVO(( RuleVO ) rulesMap.get(new Integer(categoryRule.getRuleId())));

                // ����ִ����
                CategoryRuleExcutor excutor = new CategoryRuleExcutor(categoryRule);

                // ִ��
                excutor.excucte();
                CategoryRuleExcutor.clearCache();
                long endtime = System.currentTimeMillis();
                long exetime = endtime - starttime;
                // add by tungke ���¹����ϴ�ִ��ʱ��
                CategoryRuleDAO.getInstance().updateRuleLastExecTime(ids[i],exetime,
                                                                     new Date());
                
            }
        }
        catch (BOException e)
        {
			// д������־
			actionResult = false;
			actionDesc = "ִ�л��ܹ������";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            logger.error(e);
            this.saveMessagesValue(request, "ִ�й������");
            return mapping.findForward(forward);
        }
		// д������־
		actionDesc = "ִ�л��ܹ���ɹ�";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);

        this.saveMessagesValue(request, "ִ�й���ɹ���");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL, "categoryRuleList.do");
        return mapping.findForward(forward);
    }

    /**
     * ִ�о�Ʒ�����ͬ��
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward exeSyn(ActionMapping mapping, ActionForm form,
                             HttpServletRequest request,
                             HttpServletResponse response) throws BOException
    {

        String forward = Constants.FORWARD_COMMON_FAILURE;

        // �������л�ȡ��������
        if (logger.isDebugEnabled())
        {
            logger.debug("ִ�о�Ʒ�����ͬ�� ");
        }
        try
        {
            // ִ�о�Ʒ�����ͬ��
            SynCategoryTask task = new SynCategoryTask();
            task.run();
        }
        catch (Exception e)
        {
            logger.error(e);
            this.saveMessagesValue(request, "ִ�о�Ʒ�����ͬ������");
            return mapping.findForward(forward);
        }
        
        this.saveMessagesValue(request, "ִ�о�Ʒ�����ͬ���ɹ���");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        return mapping.findForward(forward);
    }
}
