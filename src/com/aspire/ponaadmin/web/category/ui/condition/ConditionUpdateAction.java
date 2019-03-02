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
     * 日志引用
     */
    protected static final JLogger logger = LoggerFactory.getLogger(ConditionUpdateAction.class);

    public ActionForward doPerform(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("doPerform()");
        }

        // 从请求中获取操作类型
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
            this.saveMessagesValue(request, "对不起，您访问的路径不存在");
            return mapping.findForward(forward);
        }
    }

    private ActionForward addView(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    {
        String forward = "addView";
        // 从请求中获取参数
        String id = this.getParameter(request, "id").trim();
        List baseCondList = new ArrayList();
        
        if (logger.isDebugEnabled())
        {
            logger.debug("从请求中获取参数：id=" + id);

        }
        // 查询基本条件类型列表
        try
        {
            // 查询基本条件类型列表
            baseCondList = ConditionUpdateBO.getInstance().queryBaseCondList();
        }
        catch (BOException e)
        {
            logger.error(e);
            forward = Constants.FORWARD_COMMON_FAILURE;
            this.saveMessagesValue(request, "查询基本条件类型列表信息出错");
            return mapping.findForward(forward);
        }

        request.setAttribute("id", id);
        request.setAttribute("baseCondList", baseCondList);
        
        return mapping.findForward(forward);
    }

    private ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    {
        String forward = Constants.FORWARD_COMMON_FAILURE;

        // 从请求中获取参数
        String ruleId = this.getParameter(request, "ruleId").trim();
        String cid = this.getParameter(request, "id");
        String condType = this.getParameter(request, "condType").trim();
        String wSql = this.getParameter(request, "wSql").trim();
        String oSql = this.getParameter(request, "oSql").trim();
        String count = this.getParameter(request, "count").trim();
        String sortId = this.getParameter(request, "sortId").trim();

        // 组装信息
        ConditionVO vo = new ConditionVO();
        vo.setRuleId(Integer.parseInt(ruleId));
        vo.setCid(cid);
        vo.setCondType(Integer.parseInt(condType));
        vo.setWSql(wSql);
        vo.setOSql(oSql);
        
		String actionType = "新增规则条件信息";
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
            logger.debug("从请求中获取参数：vo=" + vo.toString());

        }
        // 新增规则条件信息
        try
        {
            // 新增规则信息
            ConditionUpdateBO.getInstance().addConditeionVO(vo);
        }
        catch (BOException e)
        {
			// 写操作日志
			actionResult = false;
			actionDesc = "新增规则条件信息出错!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            logger.error(e);
            this.saveMessagesValue(request, "新增规则条件信息出错");
            return mapping.findForward(forward);
        }
		// 写操作日志
		actionDesc = "新增规则条件信息成功!";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "新增规则条件信息成功！");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL, "ruleView.do?action=editView&ruleId="+ruleId);
        return mapping.findForward(forward);
    }

    private ActionForward editView(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    {
        String forward = "editView";
        
        // 从请求中获取参数
        String ruleId = this.getParameter(request, "ruleId").trim();
        String id = this.getParameter(request, "id");
        ConditionVO vo = null;
        List baseCondList = new ArrayList();
        
        if (logger.isDebugEnabled())
        {
            logger.debug("从请求中获取参数：id=" + id);

        }
        // 删除规则条件信息
        try
        {
            // 删除规则信息
            vo = ConditionUpdateBO.getInstance().getConditionVOById(id);
        }
        catch (BOException e)
        {
            logger.error(e);
            forward = Constants.FORWARD_COMMON_FAILURE;
            this.saveMessagesValue(request, "根据编码查询规则条件信息出错");
            return mapping.findForward(forward);
        }
        
        // 查询基本条件类型列表
        try
        {
            // 查询基本条件类型列表
            baseCondList = ConditionUpdateBO.getInstance().queryBaseCondList();
        }
        catch (BOException e)
        {
            logger.error(e);
            forward = Constants.FORWARD_COMMON_FAILURE;
            this.saveMessagesValue(request, "查询基本条件类型列表信息出错");
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

        // 从请求中获取参数
        String ruleId = this.getParameter(request, "ruleId").trim();
        String cid = this.getParameter(request, "id");
        String condType = this.getParameter(request, "condType").trim();
        String wSql = this.getParameter(request, "wSql").trim();
        String oSql = this.getParameter(request, "oSql").trim();
        String count = this.getParameter(request, "count").trim();
        String sortId = this.getParameter(request, "sortId").trim();
        String condId = this.getParameter(request, "condId");
        
        
        // 组装信息
        ConditionVO vo = new ConditionVO();
        vo.setRuleId(Integer.parseInt(ruleId));
        vo.setCid(cid);
        vo.setCondType(Integer.parseInt(condType));
        vo.setWSql(wSql);
        vo.setOSql(oSql);
        vo.setSortId(Integer.parseInt(sortId));
        vo.setId(Integer.parseInt(condId));
        
		String actionType = "修改规则条件信息";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = cid;
        
        if(!"".equals(count))
        {
            vo.setCount(Integer.parseInt(count));
        }
        

        if (logger.isDebugEnabled())
        {
            logger.debug("从请求中获取参数：vo=" + vo.toString());

        }
        // 修改规则条件信息
        try
        {
            // 修改规则信息
            ConditionUpdateBO.getInstance().editConditeionVO(vo);
        }
        catch (BOException e)
        {
			// 写操作日志
			actionResult = false;
			actionDesc = "修改规则条件信息出错!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            logger.error(e);
            this.saveMessagesValue(request, "修改规则条件信息出错");
            return mapping.findForward(forward);
        }
		// 写操作日志
		actionDesc = "修改规则条件信息成功！";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "修改规则条件信息成功！");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL, "ruleView.do?action=editView&ruleId="+ruleId);
        return mapping.findForward(forward);
    }
    
    private ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    {
        String forward = Constants.FORWARD_COMMON_FAILURE;

        // 从请求中获取参数
        String ruleId = this.getParameter(request, "ruleId").trim();
        String id = this.getParameter(request, "id");
        
		String actionType = "修改规则条件信息";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = id;
		
        if (logger.isDebugEnabled())
        {
            logger.debug("从请求中获取参数：id=" + id);

        }
        // 删除规则条件信息
        try
        {
            // 删除规则信息
            ConditionUpdateBO.getInstance().deleteConditeionVO(id);
        }
        catch (BOException e)
        {
			// 写操作日志
			actionResult = false;
			actionDesc = "删除规则条件信息出错!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            logger.error(e);
            this.saveMessagesValue(request, "删除规则条件信息出错");
            this.actionLog(request, "删除规则条件", id, false, "");
            return mapping.findForward(forward);
        }
		// 写操作日志
		actionDesc = "删除规则条件信息成功！";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);

        this.saveMessagesValue(request, "删除规则条件信息成功！");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL, "ruleView.do?action=editView&ruleId="+ruleId);
        this.actionLog(request, "删除规则条件", id, true, "");
        return mapping.findForward(forward);
    }


}
