/*
 * 文件名：DataExportAction.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */
package com.aspire.ponaadmin.web.dataexport.sqlexport.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.dataexport.sqlexport.VO.DataExportVO;
import com.aspire.ponaadmin.web.dataexport.sqlexport.bo.DataExportBO;
import com.aspire.ponaadmin.web.dataexport.sqlexport.dao.DataExportGroupDAO;
import com.aspire.ponaadmin.web.dataexport.sqlexport.exe.ExportSqlFactory;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * 
 * @author wangminlong
 * @version
 */
public class DataExportAction extends BaseAction
{
	
	/**
	 * 日志引用
	 */
	private static final JLogger LOG = LoggerFactory
			.getLogger(DataExportAction.class);
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.ponaadmin.web.BaseAction#doPerform(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException
	{
		LOG.debug("doPerform()");
		
		// 从请求中获取操作类型
		String perType = this.getParameter(request, "perType").trim();
		
		if ("query".equals(perType))
		{
			return query(mapping, form, request, response);
		}
		else if ("del".equals(perType))
		{
			return del(mapping, form, request, response);
		}
		else if ("testSql".equals(perType))
		{
			return testSql(mapping, form, request, response);
		}
		else if ("exec".equals(perType))
		{
			return exec(mapping, form, request, response);
		}
		else
		{
			String forward = Constants.FORWARD_COMMON_FAILURE;
			this.saveMessagesValue(request, "对不起，您访问的路径不存在");
			return mapping.findForward(forward);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.ponaadmin.web.BaseAction#doPerform(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException
	{
		LOG.debug("doPerform()");
		String forward = "query";
		PageResult page = new PageResult(request);
		String exportByUser = this.getParameter(request, "exportByUser").trim();
		
		String actionType = "查询导出任务列表信息";
		boolean actionResult = true;
		String actionDesc = "查询导出任务列表成功";
		String actionTarget = exportByUser;
		
		try
		{
			DataExportBO.getInstance().queryDataExportList(page, exportByUser);
		}
		catch (BOException e)
		{
			LOG.error(e);
			
			// 写操作日志
			actionResult = false;
			actionDesc = "查询导出任务列表出错";
			this.actionLog(request, actionType, actionTarget, actionResult,
					actionDesc);
			
			this.saveMessagesValue(request, "查询导出任务列表出错");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		
		// 写操作日志
		this.actionLog(request, actionType, actionTarget, actionResult,
				actionDesc);
		
		request.setAttribute("exportByUser", exportByUser);
		request.setAttribute("PageResult", page);
		
		return mapping.findForward(forward);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.ponaadmin.web.BaseAction#doPerform(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward del(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException
	{
		LOG.debug("doPerform()");
		String forward = Constants.FORWARD_COMMON_SUCCESS;
		String id = this.getParameter(request, "id").trim();
		
		String actionType = "删除导出任务列表信息";
		boolean actionResult = true;
		String actionDesc = "删除导出任务列表成功";
		String actionTarget = id;
		
		try
		{
			// 用于删除指定货架
			DataExportBO.getInstance().delDataExport(id);
		}
		catch (BOException e)
		{
			LOG.error(e);
			
			// 写操作日志
			actionResult = false;
			actionDesc = "删除导出任务出错";
			this.actionLog(request, actionType, actionTarget, actionResult,
					actionDesc);
			
			this.saveMessagesValue(request, "删除导出任务出错");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		
		// 写操作日志
		this.actionLog(request, actionType, actionTarget, actionResult,
				actionDesc);
		this.saveMessagesValue(request, "删除导出任务列表成功");
		request.setAttribute(Constants.PARA_GOURL,
				"../exportData/queryExport.do?perType=query&isFirst=1");
		// request.setAttribute(Constants.PARA_TARGETFRAME, "_parent");
		return mapping.findForward(forward);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.ponaadmin.web.BaseAction#doPerform(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward testSql(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException
	{
		LOG.debug("doPerform()");
		String forward = "testSql";
		String exportSql = this.getParameter(request, "exportSql").trim();
		String exportLine = this.getParameter(request, "exportLine").trim();
		boolean isTrue = false;
		
		String actionType = "验证导出sql语句的正确性";
		boolean actionResult = true;
		String actionDesc = "验证导出sql语句的正确性成功";
		String actionTarget = exportSql;
		
		try
		{
			// 得到当前货架信息
			isTrue = DataExportBO.getInstance().hasSqlTrue(exportSql,
					exportLine);
		}
		catch (BOException e)
		{
			LOG.error(e);
			
			// 写操作日志
			actionResult = false;
			actionDesc = "验证导出sql语句的正确性出错";
			this.actionLog(request, actionType, actionTarget, actionResult,
					actionDesc);
			
			this.saveMessagesValue(request, "验证导出sql语句的正确性出错");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		
		// 写操作日志
		this.actionLog(request, actionType, actionTarget, actionResult,
				actionDesc);
		
		request.setAttribute("isTrue", isTrue);
		request.setAttribute("exportLine", exportLine);
		request.setAttribute("exportSql", exportSql);
		return mapping.findForward(forward);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.ponaadmin.web.BaseAction#doPerform(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward exec(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException
	{
		LOG.debug("doPerform()");
		String forward = "exec";
		String exportByUser = this.getParameter(request, "exportByUser").trim();
		String id = this.getParameter(request, "id").trim();
		
		String actionType = "执行导出任务信息";
		boolean actionResult = true;
		String actionDesc = "执行导出任务成功";
		String actionTarget = id;
		
		DataExportVO dataExportVO = new DataExportVO();
		String returnStr;
		
		try
		{
			// 得到当前导出任务信息
			dataExportVO = DataExportBO.getInstance().queryDataExportVO(id);
			returnStr = ExportSqlFactory.getExportSql(dataExportVO).createFile(
					DataExportGroupDAO.getInstance().queryDataExportGroup(
							dataExportVO.getGroupId()));
		}
		catch (BOException e)
		{
			LOG.error(e);
			
			// 写操作日志
			actionResult = false;
			actionDesc = "执行导出任务出错";
			this.actionLog(request, actionType, actionTarget, actionResult,
					actionDesc);
			
			this.saveMessagesValue(request, "执行导出任务出错");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		catch (DAOException e)
		{
			LOG.error(e);
			
			// 写操作日志
			actionResult = false;
			actionDesc = "获取分组任务信息时出错";
			this.actionLog(request, actionType, actionTarget, actionResult,
					actionDesc);
			
			this.saveMessagesValue(request, "获取分组任务信息时出错");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		
		// 写操作日志
		this.actionLog(request, actionType, actionTarget, actionResult,
				actionDesc);
		
		this.saveMessagesValue(request, "执行导出任务结果：" + returnStr);
		request.setAttribute(Constants.PARA_GOURL,
				"../exportData/queryExport.do?perType=query&exportByUser="
						+ exportByUser);
		forward = Constants.FORWARD_COMMON_SUCCESS;
		return mapping.findForward(forward);
	}
}
