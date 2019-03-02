package com.aspire.dotcard.basevideosync.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basevideosync.bo.BaseVideoBO;
import com.aspire.dotcard.basevideosync.exportfile.BaseFileFactory;
import com.aspire.dotcard.basevideosync.sync.BaseVideoTask;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;

public class BaseFileExportAction extends BaseAction{

	/**
	 * 日志引用
	 */
	private static final JLogger LOG = LoggerFactory
			.getLogger(BaseFileExportAction.class);
	
	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
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
	
	/**
     * 新基地视频数据文件同步
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
	private ActionForward synVideo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException
	{
		if (LOG.isDebugEnabled())
		{
			LOG.debug("新基地视频数据文件同步开始");
		}
		// 从请求中获取操作类型
		String synVideoType = this.getParameter(request, "synVideoType").trim();
		
		String actionType = "新基地视频数据文件同步";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = synVideoType;
		
		StringBuffer mailText = new StringBuffer();
		
		mailText.append(BaseFileFactory.getInstance().getBaseFile(synVideoType)
				.execution(true));
		
		actionDesc = "新基地视频数据文件同步完成";
		this.actionLog(request, actionType, actionTarget, actionResult,
				actionDesc);
		
		this.saveMessagesValue(request, "新基地视频数据文件同步完成");
		return mapping.findForward(Constants.FORWARD_COMMON_SUCCESS);
	}
	
	/**
     * 新基地视频数据全量同步类
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
	private ActionForward synVideoAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException
	{
		if (LOG.isDebugEnabled())
		{
			LOG.debug("全量新基地视频数据同步类（包括中间表切换动作）开始");
		}
		// 从请求中获取操作类型
		String synVideoType = this.getParameter(request, "synVideoAll").trim();
		
		String actionType = "全量新基地视频数据同步类（包括中间表切换动作）";
		boolean actionResult = true;
		String actionDesc = "全量新基地视频数据同步完成（包括中间表切换动作）";
		String actionTarget = synVideoType;
		
		new BaseVideoTask().run();
		
		this.actionLog(request, actionType, actionTarget, actionResult,
				actionDesc);
		
		this.saveMessagesValue(request, "actionDesc");
		return mapping.findForward(Constants.FORWARD_COMMON_SUCCESS);
	}
	
	/**
     * 新基地视频节目详情相关中间表切换
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
	private ActionForward renameDataSync(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException
	{
		if (LOG.isDebugEnabled())
		{
			LOG.debug("新基地视频节目详情相关中间表切换开始");
		}
		
		String actionType = "新基地视频节目详情相关中间表切换";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = "";
		
		// 调用存储过程 用以执行中间表与正式表中数据转移
		BaseVideoBO.getInstance().syncMidTableData();
		
		// 调用存储过程 用以执行节目商品上架操作
		BaseVideoBO.getInstance().updateCategoryReference();
		
		actionDesc = "新基地视频节目详情相关中间表切换完成";
		this.actionLog(request, actionType, actionTarget, actionResult,
				actionDesc);
		
		this.saveMessagesValue(request, actionDesc);
		return mapping.findForward(Constants.FORWARD_COMMON_SUCCESS);
	}

}
