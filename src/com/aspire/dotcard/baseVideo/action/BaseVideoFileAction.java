/*
 * 文件名：BaseVideoFileAction.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */

package com.aspire.dotcard.baseVideo.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseVideo.bo.BaseVideoFileBO;
import com.aspire.dotcard.baseVideoNew.bo.BaseVideoNewFileBO;
import com.aspire.dotcard.baseVideoNew.exportfile.BaseFileFactory;
import com.aspire.dotcard.baseVideoNew.sync.BaseVideoNewTask;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;

/**
 * <p>
 * Title:基地视频数据同步类
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2012
 * </p>
 * 
 * @author wangminlong
 * @version
 */
public class BaseVideoFileAction extends BaseAction
{
	/**
	 * 日志引用
	 */
	private static final JLogger LOG = LoggerFactory
			.getLogger(BaseVideoFileAction.class);
	
	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException
	{
		LOG.debug("doPerform()");
		
		// 从请求中获取操作类型
		String perType = this.getParameter(request, "perType").trim();
		
		if ("synVideo".equals(perType))
		{
			return synVideo(mapping, form, request, response);
		}
		else if ("renameDataSync".equals(perType))
		{
			return renameDataSync(mapping, form, request, response);
		}
		else if ("synVideoAll".equals(perType))
		{
			return synVideoAll(mapping, form, request, response);
		}
		else
		{
			String forward = Constants.FORWARD_COMMON_FAILURE;
			this.saveMessagesValue(request, "对不起，您访问的路径不存在");
			return mapping.findForward(forward);
		}
	}
	
	ActionForward synVideo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException
	{
		if (LOG.isDebugEnabled())
		{
			LOG.debug("基地视频数据同步类开始");
		}
		// 从请求中获取操作类型
		String synVideoType = this.getParameter(request, "synVideoType").trim();
		
		String actionType = "基地视频数据同步类";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = synVideoType;
		
		StringBuffer mailText = new StringBuffer();
		
		mailText.append(BaseFileFactory.getInstance().getBaseFile(synVideoType)
				.execution(true));
		
		actionDesc = "基地视频数据同步完成";
		this.actionLog(request, actionType, actionTarget, actionResult,
				actionDesc);
		
		this.saveMessagesValue(request, "基地视频数据同步完成");
		return mapping.findForward(Constants.FORWARD_COMMON_SUCCESS);
	}
	
	ActionForward synVideoAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException
	{
		if (LOG.isDebugEnabled())
		{
			LOG.debug("全量基地视频数据同步类（包括临时表切换动作）开始");
		}
		// 从请求中获取操作类型
		String synVideoType = this.getParameter(request, "synVideoAll").trim();
		
		String actionType = "全量基地视频数据同步类（包括临时表切换动作）";
		boolean actionResult = true;
		String actionDesc = "全量基地视频数据同步完成（包括临时表切换动作）";
		String actionTarget = synVideoType;
		
		new BaseVideoNewTask().run();
		
		this.actionLog(request, actionType, actionTarget, actionResult,
				actionDesc);
		
		this.saveMessagesValue(request, "全量基地视频数据同步完成（包括临时表切换动作）");
		return mapping.findForward(Constants.FORWARD_COMMON_SUCCESS);
	}
	
	ActionForward renameDataSync(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException
	{
		if (LOG.isDebugEnabled())
		{
			LOG.debug("修改同步临时表为正式表开始");
		}
		
		StringBuffer sb = new StringBuffer();
		String actionType = "修改视频同步临时表为正式表";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = "";
		
		// 调用存储过程 用以执行中间表与正式表中数据转移
		boolean syncTrue = BaseVideoNewFileBO.getInstance().syncVideoData();
		
		// 如果执行正确
		if (syncTrue)
		{
			sb.append("调用存储过程 用以执行中间表与正式表中数据转移完成！");
			
			// 用于清空旧的模拟表数据，以用于全量更新
			BaseVideoNewFileBO.getInstance().cleanOldSimulationData(sb);
			
			// 根据现有表情况，组装模拟表
			BaseVideoNewFileBO.getInstance().diyDataTable(sb);
			
			// 用于统计子栏目数与栏目下节目数
			BaseVideoNewFileBO.getInstance().updateCategoryNodeNum();
		}
		else
		{
			sb.append("调用存储过程 用以执行中间表与正式表中数据转移时发生错误，后续动作取消！");
		}
		
        BaseVideoFileBO.getInstance().sendResultMail("基地视频组装模拟货架结果邮件", sb);
		
		actionDesc = "修改视频同步临时表为正式表完成";
		this.actionLog(request, actionType, actionTarget, actionResult,
				actionDesc);
		
		this.saveMessagesValue(request, "修改视频同步临时表为正式表完成");
		return mapping.findForward(Constants.FORWARD_COMMON_SUCCESS);
	}
}
