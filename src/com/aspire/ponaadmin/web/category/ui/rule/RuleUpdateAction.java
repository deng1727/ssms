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
     * 日志引用
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

        // 从请求中获取操作类型
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
            this.saveMessagesValue(request, "对不起，您访问的路径不存在");
            return mapping.findForward(forward);
        }
    }

    private ActionForward add(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response)
    {

        String forward = Constants.FORWARD_COMMON_FAILURE;

        // 从请求中获取参数
        String ruleName = this.getParameter(request, "ruleName").trim();
        String ruleType = this.getParameter(request, "ruleType");
        String intervalType = this.getParameter(request, "intervalType").trim();
        String excuteInterval = this.getParameter(request, "excuteInterval")
                                    .trim();
        String excuteTime = this.getParameter(request, "excuteTime");
        String randomFactor = this.getParameter(request, "randomFactor");
        int ruleId = 0;

        // 组装信息
        RuleVO vo = new RuleVO();
        vo.setRuleName(ruleName);
        vo.setRuleType(Integer.parseInt(ruleType));
        vo.setIntervalType(Integer.parseInt(intervalType));
        vo.setExcuteInterval(Integer.parseInt(excuteInterval));
        vo.setRandomFactor(Integer.parseInt(randomFactor));
        
		String actionType = "新增规则信息";
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
            logger.debug("从请求中获取参数：vo=" + vo.toString());
        }
        // 新增货架规则信息
        try
        {
            // 查看名称是否存在
            if (RuleBO.getInstance().hasRuleName(vo.getRuleName()))
            {
                logger.error("您输入的规则名称已存在，请更换！");
                this.saveMessagesValue(request, "您输入的规则名称已存在，请更换！");
                return mapping.findForward(forward);
            }

            // 得到规则id
            ruleId = RuleBO.getInstance().getRuleId();

            vo.setRuleId(ruleId);

            // 新增规则信息
            RuleBO.getInstance().addRuleVO(vo);
        }
        catch (BOException e)
        {
			// 写操作日志
			actionResult = false;
			actionDesc = "新增规则信息出错!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            logger.error(e);
            this.saveMessagesValue(request, "新增规则信息出错");
            return mapping.findForward(forward);
        }
		// 写操作日志
		actionDesc = "新增规则信息成功!";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "新增规则信息成功！");
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

        // 从请求中获取规则id
        String ruleId = this.getParameter(request, "id").trim();

		String actionType = "通过规则id删除相应的规则";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = ruleId;
		
        if (logger.isDebugEnabled())
        {
            logger.debug("从请求中获取规则id：" + ruleId);

        }
        // 通过规则id删除相应的规则
        try
        {
            // 查看此规则是否绑定货架
            if (RuleBO.getInstance().isRuleBind(ruleId))
            {
                logger.error("您要删除的规则还与货架关联，不可以删除！");
                this.saveMessagesValue(request, "您要删除的规则还与货架关联，不可以删除！");
                return mapping.findForward(forward);
            }

            RuleBO.getInstance().dellRuleVOByID(ruleId);
        }
        catch (BOException e)
        {
			// 写操作日志
			actionResult = false;
			actionDesc = "通过规则id删除相应的规则出错";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            logger.error(e);
            this.saveMessagesValue(request, "通过规则id删除相应的规则出错");
            this.actionLog(request, "删除规则", ruleId, false, "");
            return mapping.findForward(forward);
        }
		// 写操作日志
		actionDesc = "通过规则id删除相应的规则成功";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "通过规则id删除相应的规则成功！");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL, "ruleList.do");
        this.actionLog(request, "删除规则", ruleId, true, "");
        return mapping.findForward(forward);
    }

    /**
     * 用于修改规则信息
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

        // 从请求中获取参数
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
        
		String actionType = "修改规则信息";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = id;
		
       //当规则为“增量刷新货架下商品” 下的最大商品数量 add by aiyan 2011-12-21
        // 这个属性只是为ruleType为“增量刷新货架下商品”（ruleType==7）而出现的，所以其他的就不用处理，用一个
        //随便的值（0）放在数据库就罢了。
        
        
        int maxGoodsNum = -1;
        if("7".equals(ruleType)){
        	String strMaxGoodsNum = this.getParameter(request, "maxGoodsNum");
        	try{
        		maxGoodsNum = Integer.parseInt(strMaxGoodsNum);
        	}catch(Exception e){
        		logger.info("用户给出的最大商品数量错误"+strMaxGoodsNum);
        	}
        	
        }
        	
        	

        // 组装信息
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
            logger.debug("从请求中获取参数：vo=" + vo.toString());

        }
        // 修改货架规则信息
        try
        {
            // 名称被修改且不为空
            if ("1".equals(isEditName) && !"".equals(vo.getRuleName()))
            {
                // 查看要修改的名称是否存在
                if (RuleBO.getInstance().hasRuleName(vo.getRuleName()))
                {
                    logger.error("您输入的规则名称已存在，请更换！");
                    this.saveMessagesValue(request, "您输入的规则名称已存在，请更换！");
                    return mapping.findForward(forward);
                }
            }

            // 修改规则信息
            RuleBO.getInstance().editRuleVO(vo);
        }
        catch (BOException e)
        {
			// 写操作日志
			actionResult = false;
			actionDesc = "修改规则信息出错";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            logger.error(e);
            this.saveMessagesValue(request, "修改规则信息出错");
            return mapping.findForward(forward);
        }
		// 写操作日志
		actionDesc = "修改规则信息成功";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "修改规则信息成功！");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL, actionUrl);
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
    private ActionForward editView(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {

        String forward = "editView";

        // 从请求中获取规则id
        String ruleId = this.getParameter(request, "ruleId").trim();
        String backId=this.getParameter(request, "backId").trim();
        String backName=this.getParameter(request, "backName").trim();
        String backRuleType=this.getParameter(request, "backRuleType").trim();
        String backIntervalType=this.getParameter(request, "backIntervalType").trim();
        PageResult page = new PageResult(request);
        page.setPageSize(Integer.parseInt(PageSizeConstants.page_DEFAULT));

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

        // 根据规则编码得到属于此规则的条件
        ConditionUpdateBO.getInstance().queryCondListByID(page, ruleId);

        // 将list放置到page中用于分页展示
        request.setAttribute("PageResult", page);
        request.setAttribute("vo", vo);
        request.setAttribute("backId", backId);
        request.setAttribute("backName", backName);
        request.setAttribute("backRuleType", backRuleType);
        request.setAttribute("backIntervalType", backIntervalType);

        return mapping.findForward(forward);
    }
}
