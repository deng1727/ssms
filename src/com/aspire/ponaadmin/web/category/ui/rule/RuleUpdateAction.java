/*
 * 
 */

package com.aspire.ponaadmin.web.category.ui.rule;

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
import com.aspire.ponaadmin.web.actionlog.ActionLogBO;
import com.aspire.ponaadmin.web.category.rule.RuleVO;
import com.aspire.ponaadmin.web.category.ui.CategoryUpdateBO;
import com.aspire.ponaadmin.web.category.ui.condition.ConditionUpdateBO;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.system.PageSizeConstants;

/**
 * @author x_wangml
 * 
 */
public class RuleUpdateAction extends BaseAction
{

    /**
     * ��־����
     */
    protected static final JLogger logger = LoggerFactory.getLogger(RuleUpdateAction.class);

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

        if ("edit".equals(action))
        {
            return edit(mapping, form, request, response);
        }
        if ("editView".equals(action))
        {
            return editView(mapping, form, request, response);
        }
        else if ("del".equals(action))
        {
            return del(mapping, form, request, response);
        }
        else if ("add".equals(action))
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

    private ActionForward add(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response)
    {

        String forward = Constants.FORWARD_COMMON_FAILURE;

        // �������л�ȡ����
        String ruleName = this.getParameter(request, "ruleName").trim();
        String ruleType = this.getParameter(request, "ruleType");
        String intervalType = this.getParameter(request, "intervalType").trim();
        String excuteInterval = this.getParameter(request, "excuteInterval")
                                    .trim();
        String excuteTime = this.getParameter(request, "excuteTime");
        String randomFactor = this.getParameter(request, "randomFactor");
        int ruleId = 0;

        // ��װ��Ϣ
        RuleVO vo = new RuleVO();
        vo.setRuleName(ruleName);
        vo.setRuleType(Integer.parseInt(ruleType));
        vo.setIntervalType(Integer.parseInt(intervalType));
        vo.setExcuteInterval(Integer.parseInt(excuteInterval));
        vo.setRandomFactor(Integer.parseInt(randomFactor));
        
		String actionType = "����������Ϣ";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = ruleName;

        if ("0".equals(intervalType))
        {
            vo.setExcuteTime(0);
        }
        else
        {
            vo.setExcuteTime(Integer.parseInt(excuteTime));
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("�������л�ȡ������vo=" + vo.toString());
        }
        // �������ܹ�����Ϣ
        try
        {
            // �鿴�����Ƿ����
            if (RuleBO.getInstance().hasRuleName(vo.getRuleName()))
            {
                logger.error("������Ĺ��������Ѵ��ڣ��������");
                this.saveMessagesValue(request, "������Ĺ��������Ѵ��ڣ��������");
                return mapping.findForward(forward);
            }

            // �õ�����id
            ruleId = RuleBO.getInstance().getRuleId();

            vo.setRuleId(ruleId);

            // ����������Ϣ
            RuleBO.getInstance().addRuleVO(vo);
        }
        catch (BOException e)
        {
			// д������־
			actionResult = false;
			actionDesc = "����������Ϣ����!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            logger.error(e);
            this.saveMessagesValue(request, "����������Ϣ����");
            return mapping.findForward(forward);
        }
		// д������־
		actionDesc = "����������Ϣ�ɹ�!";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "����������Ϣ�ɹ���");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "ruleView.do?action=editView&ruleId=" + ruleId);
        return mapping.findForward(forward);
    }

    private ActionForward del(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response)
    {

        String forward = Constants.FORWARD_COMMON_FAILURE;

        // �������л�ȡ����id
        String ruleId = this.getParameter(request, "id").trim();

		String actionType = "ͨ������idɾ����Ӧ�Ĺ���";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = ruleId;
		
        if (logger.isDebugEnabled())
        {
            logger.debug("�������л�ȡ����id��" + ruleId);

        }
        // ͨ������idɾ����Ӧ�Ĺ���
        try
        {
            // �鿴�˹����Ƿ�󶨻���
            if (RuleBO.getInstance().isRuleBind(ruleId))
            {
                logger.error("��Ҫɾ���Ĺ�������ܹ�����������ɾ����");
                this.saveMessagesValue(request, "��Ҫɾ���Ĺ�������ܹ�����������ɾ����");
                return mapping.findForward(forward);
            }

            RuleBO.getInstance().dellRuleVOByID(ruleId);
        }
        catch (BOException e)
        {
			// д������־
			actionResult = false;
			actionDesc = "ͨ������idɾ����Ӧ�Ĺ������";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            logger.error(e);
            this.saveMessagesValue(request, "ͨ������idɾ����Ӧ�Ĺ������");
            this.actionLog(request, "ɾ������", ruleId, false, "");
            return mapping.findForward(forward);
        }
		// д������־
		actionDesc = "ͨ������idɾ����Ӧ�Ĺ���ɹ�";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "ͨ������idɾ����Ӧ�Ĺ���ɹ���");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL, "ruleList.do");
        this.actionLog(request, "ɾ������", ruleId, true, "");
        return mapping.findForward(forward);
    }

    /**
     * �����޸Ĺ�����Ϣ
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

        // �������л�ȡ����
        String id = this.getParameter(request, "id").trim();
        String ruleName = this.getParameter(request, "ruleName").trim();
        String ruleType = this.getParameter(request, "ruleType");
        String intervalType = this.getParameter(request, "intervalType").trim();
        String excuteInterval = this.getParameter(request, "excuteInterval")
                                    .trim();
        String excuteTime = this.getParameter(request, "excuteTime");
        String randomFactor = this.getParameter(request, "randomFactor");
        String isEditName = this.getParameter(request, "isEditName");
        String actionUrl = this.getParameter(request, "actionUrl");
        
		String actionType = "�޸Ĺ�����Ϣ";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = id;
		
       //������Ϊ������ˢ�»�������Ʒ�� �µ������Ʒ���� add by aiyan 2011-12-21
        // �������ֻ��ΪruleTypeΪ������ˢ�»�������Ʒ����ruleType==7�������ֵģ����������ľͲ��ô�����һ��
        //����ֵ��0���������ݿ�Ͱ��ˡ�
        
        
        int maxGoodsNum = -1;
        if("7".equals(ruleType)){
        	String strMaxGoodsNum = this.getParameter(request, "maxGoodsNum");
        	try{
        		maxGoodsNum = Integer.parseInt(strMaxGoodsNum);
        	}catch(Exception e){
        		logger.info("�û������������Ʒ��������"+strMaxGoodsNum);
        	}
        	
        }
        	
        	

        // ��װ��Ϣ
        RuleVO vo = new RuleVO();
        vo.setRuleId(Integer.parseInt(id));
        vo.setRuleName(ruleName);
        vo.setRuleType(Integer.parseInt(ruleType));
        vo.setIntervalType(Integer.parseInt(intervalType));
        vo.setExcuteInterval(Integer.parseInt(excuteInterval));
        vo.setRandomFactor(Integer.parseInt(randomFactor));
        vo.setMaxGoodsNum(maxGoodsNum);
        if ("0".equals(intervalType))
        {
            vo.setExcuteTime(0);
        }
        else
        {
            vo.setExcuteTime(Integer.parseInt(excuteTime));
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("�������л�ȡ������vo=" + vo.toString());

        }
        // �޸Ļ��ܹ�����Ϣ
        try
        {
            // ���Ʊ��޸��Ҳ�Ϊ��
            if ("1".equals(isEditName) && !"".equals(vo.getRuleName()))
            {
                // �鿴Ҫ�޸ĵ������Ƿ����
                if (RuleBO.getInstance().hasRuleName(vo.getRuleName()))
                {
                    logger.error("������Ĺ��������Ѵ��ڣ��������");
                    this.saveMessagesValue(request, "������Ĺ��������Ѵ��ڣ��������");
                    return mapping.findForward(forward);
                }
            }

            // �޸Ĺ�����Ϣ
            RuleBO.getInstance().editRuleVO(vo);
        }
        catch (BOException e)
        {
			// д������־
			actionResult = false;
			actionDesc = "�޸Ĺ�����Ϣ����";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            logger.error(e);
            this.saveMessagesValue(request, "�޸Ĺ�����Ϣ����");
            return mapping.findForward(forward);
        }
		// д������־
		actionDesc = "�޸Ĺ�����Ϣ�ɹ�";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "�޸Ĺ�����Ϣ�ɹ���");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL, actionUrl);
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
    private ActionForward editView(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {

        String forward = "editView";

        // �������л�ȡ����id
        String ruleId = this.getParameter(request, "ruleId").trim();
        String backId=this.getParameter(request, "backId").trim();
        String backName=this.getParameter(request, "backName").trim();
        String backRuleType=this.getParameter(request, "backRuleType").trim();
        String backIntervalType=this.getParameter(request, "backIntervalType").trim();
        PageResult page = new PageResult(request);
        page.setPageSize(Integer.parseInt(PageSizeConstants.page_DEFAULT));

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

        // ���ݹ������õ����ڴ˹��������
        ConditionUpdateBO.getInstance().queryCondListByID(page, ruleId);

        // ��list���õ�page�����ڷ�ҳչʾ
        request.setAttribute("PageResult", page);
        request.setAttribute("vo", vo);
        request.setAttribute("backId", backId);
        request.setAttribute("backName", backName);
        request.setAttribute("backRuleType", backRuleType);
        request.setAttribute("backIntervalType", backIntervalType);

        return mapping.findForward(forward);
    }
}
