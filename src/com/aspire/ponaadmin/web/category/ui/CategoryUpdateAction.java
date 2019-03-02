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
     * 日志引用
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

        // 从请求中获取操作类型
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
            this.saveMessagesValue(request, "对不起，您访问的路径不存在");
            return mapping.findForward(forward);
        }

    }

    /**
     * 对显示详情请求进行处理
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

        // 从请求中获取货架内码
        String id = this.getParameter(request, "id").trim();
        String lastExcuteTime = this.getParameter(request, "lastExcuteTime")
                                    .trim();
        String effectiveTime = this.getParameter(request, "effectiveTime")
                                   .trim();

        if (logger.isDebugEnabled())
        {
            logger.debug("从请求中获取货架内码：" + id);

        }
        // 通过货架内码获取货架规则详情
        CategoryRuleVO vo = CategoryUpdateBO.getInstance()
                                            .getCategoryRuleVOByID(id);

        if (vo == null)
        {
            throw new BOException("根据货架内码ID找不到对应的规则信息！");
        }

        request.setAttribute("vo", vo);
        request.setAttribute("lastExcuteTime", lastExcuteTime);
        request.setAttribute("effectiveTime", effectiveTime);

        return mapping.findForward(forward);
    }

    /**
     * 对规则显示详情请求进行处理
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

        // 从请求中获取规则id
        String ruleId = this.getParameter(request, "ruleId").trim();

        if (logger.isDebugEnabled())
        {
            logger.debug("从请求中获取规则id：" + ruleId);

        }
        // 通过货架内码获取规则详情
        RuleVO vo = CategoryUpdateBO.getInstance().getCateRulesVOByID(ruleId);

        if (vo == null)
        {
            throw new BOException("根据规则id找不到对应的规则信息！");
        }

        if (vo == null)
        {
            throw new BOException("根据规则id找不到对应的规则信息！");
        }

        // 根据规则编码得到属于此规则的条件
        ConditionUpdateBO.getInstance().queryCondListByID(page, ruleId);

        // 将list放置到page中用于分页展示
        request.setAttribute("PageResult", page);
        request.setAttribute("vo", vo);

        return mapping.findForward(forward);
    }

    /**
     * 对规则显示详情请求进行处理
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

        // 从请求中获取货架内码
        String id = this.getParameter(request, "id").trim();

		String actionType = "通过货架内码删除相应的货架规则";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = id;
		
        if (logger.isDebugEnabled())
        {
            logger.debug("从请求中获取货架内码：" + id);

        }
        // 通过货架内码删除相应的货架规则
        try
        {
            CategoryUpdateBO.getInstance().dellCateRulesVOByID(id);
        }
        catch (BOException e)
        {
			// 写操作日志
			actionResult = false;
			actionDesc = "通过货架内码删除相应的货架规则出错!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            logger.error(e);
            this.saveMessagesValue(request, "通过货架内码删除相应的货架规则出错");
            return mapping.findForward(forward);
        }
        
		// 写操作日志
		actionDesc = "通过货架内码删除相应的货架规则成功";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);

        this.saveMessagesValue(request, "通过货架内码删除相应的货架规则成功！");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL, "categoryRuleList.do");
        return mapping.findForward(forward);
    }

    /**
     * 修改货架规则信息
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

        // 从请求中获取参数
        String id = this.getParameter(request, "id").trim();
        String ruleId = this.getParameter(request, "ruleId").trim();
        String effectiveTime = this.getParameter(request, "effectiveTime")
                                   .trim()
                                   .replaceAll("-", "")
                                   .replaceAll(" ", "")
                                   .replaceAll(":", "");
        
		String actionType = "修改货架规则信息";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = id;

        if (logger.isDebugEnabled())
        {
            logger.debug("从请求中获取参数：cid=" + id + " ruleId=" + ruleId
                         + " effectiveTime=" + effectiveTime);

        }
        // 修改货架规则信息
        try
        {
            RuleVO rule = CategoryUpdateBO.getInstance()
                                          .getCateRulesVOByID(ruleId);

            if (Integer.parseInt(ruleId) == 0
                || rule.getRuleId() != Integer.parseInt(ruleId))
            {
                logger.error("您输入的规则不存在，请输入已创建规则！");
                this.saveMessagesValue(request, "您输入的规则不存在，请输入已创建规则！");
                return mapping.findForward(forward);
            }

            CategoryUpdateBO.getInstance().editCateRulesVOByID(id,
                                                               ruleId,
                                                               effectiveTime);
        }
        catch (BOException e)
        {
			// 写操作日志
			actionResult = false;
			actionDesc = "修改货架规则信息出错!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            logger.error(e);
            this.saveMessagesValue(request, "修改货架规则信息出错");
            return mapping.findForward(forward);
        }
		// 写操作日志
		actionDesc = "修改货架规则信息成功";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);

        this.saveMessagesValue(request, "修改货架规则信息成功！");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL, "categoryRuleList.do");
        return mapping.findForward(forward);
    }

    /**
     * 新增货架规则信息
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

        // 从请求中获取参数
        String id = this.getParameter(request, "id").trim();
        String ruleId = this.getParameter(request, "ruleId").trim();
        String effectiveTime = this.getParameter(request, "effectiveTime")
                                   .trim()
                                   .replaceAll("-", "")
                                   .replaceAll(" ", "")
                                   .replaceAll(":", "");

		String actionType = "新增货架规则信息";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = id + "-" + ruleId;
		
        if (logger.isDebugEnabled())
        {
            logger.debug("从请求中获取参数：cid=" + id + " ruleId=" + ruleId
                         + " effectiveTime=" + effectiveTime);

        }
        // 修改货架规则信息
        try
        {
            // 通过货架内码获取是否已存在此货架
            CategoryRuleVO categoryRule = CategoryUpdateBO.getInstance()
                                                          .getCategoryRuleVOByID(id);
            if (id.equals(categoryRule.getCid()))
            {
                logger.error("您输入的货架内码相应规则已存在，请重新输入！");
                this.saveMessagesValue(request, "您输入的货架内码相应规则已存在，请重新输入！");
                return mapping.findForward(forward);
            }

            // 通过用户传入规则id查询是否存在此规则
            RuleVO rule = CategoryUpdateBO.getInstance()
                                          .getCateRulesVOByID(ruleId);

            if (Integer.parseInt(ruleId) == 0
                || rule.getRuleId() != Integer.parseInt(ruleId))
            {
                logger.error("您输入的规则不存在，请输入已创建规则！");
                this.saveMessagesValue(request, "您输入的规则不存在，请输入已创建规则！");
                return mapping.findForward(forward);
            }

            CategoryUpdateBO.getInstance().addCateRulesVOByID(id,
                                                              ruleId,
                                                              effectiveTime);
        }
        catch (BOException e)
        {
			// 写操作日志
			actionResult = false;
			actionDesc = "新增货架规则信息出错";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            logger.error(e);
            this.saveMessagesValue(request, "新增货架规则信息出错");
            return mapping.findForward(forward);
        }
		// 写操作日志
		actionDesc = "新增货架规则信息成功";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);

        this.saveMessagesValue(request, "新增货架规则信息成功！");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL, "categoryRuleList.do");
        return mapping.findForward(forward);
    }

    /**
     * 执行规则
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

        // 从请求中获取货架内码
        String id = this.getParameter(request, "id").trim();
        String[] ids = id.split(",");
        
		String actionType = "执行货架规则";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = id;
		

        if (logger.isDebugEnabled())
        {
            logger.debug("执行货架规则 cid：" + id);
        }
        try
        {
            for (int i = 0; i < ids.length; i++)
            {
            	  // 更新规则上次执行时间
                //CategoryRuleDAO.getInstance().updateRuleLastExecTime(ids[i],
                //                                                     null);
            	long starttime = System.currentTimeMillis();
                // 通过货架内码获取是否已存在此货架
                CategoryRuleVO categoryRule = CategoryUpdateBO.getInstance()
                                                              .getCategoryRuleVOByID(ids[i]);

                // 得到货架规则
                Map rulesMap = RuleBO.getInstance().getAllRules();
                categoryRule.setRuleVO(( RuleVO ) rulesMap.get(new Integer(categoryRule.getRuleId())));

                // 规则执行器
                CategoryRuleExcutor excutor = new CategoryRuleExcutor(categoryRule);

                // 执行
                excutor.excucte();
                CategoryRuleExcutor.clearCache();
                long endtime = System.currentTimeMillis();
                long exetime = endtime - starttime;
                // add by tungke 更新规则上次执行时间
                CategoryRuleDAO.getInstance().updateRuleLastExecTime(ids[i],exetime,
                                                                     new Date());
                
            }
        }
        catch (BOException e)
        {
			// 写操作日志
			actionResult = false;
			actionDesc = "执行货架规则出错";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            logger.error(e);
            this.saveMessagesValue(request, "执行规则出错");
            return mapping.findForward(forward);
        }
		// 写操作日志
		actionDesc = "执行货架规则成功";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);

        this.saveMessagesValue(request, "执行规则成功！");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL, "categoryRuleList.do");
        return mapping.findForward(forward);
    }

    /**
     * 执行精品库更新同步
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

        // 从请求中获取货架内码
        if (logger.isDebugEnabled())
        {
            logger.debug("执行精品库更新同步 ");
        }
        try
        {
            // 执行精品库更新同步
            SynCategoryTask task = new SynCategoryTask();
            task.run();
        }
        catch (Exception e)
        {
            logger.error(e);
            this.saveMessagesValue(request, "执行精品库更新同步出错");
            return mapping.findForward(forward);
        }
        
        this.saveMessagesValue(request, "执行精品库更新同步成功！");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        return mapping.findForward(forward);
    }
}
