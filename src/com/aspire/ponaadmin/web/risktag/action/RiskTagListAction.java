package com.aspire.ponaadmin.web.risktag.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.WritableWorkbook;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.util.StringUtils;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.category.blacklist.action.AndroidBlackListAction;
import com.aspire.ponaadmin.web.category.blacklist.bo.AndroidBlackListBO;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.risktag.bo.RiskBo;
import com.aspire.ponaadmin.web.risktag.bo.RiskTagBo;
import com.jcraft.jsch.Logger;

public class RiskTagListAction extends BaseAction {

	/**
	 * 日志引用
	 */
	private static final JLogger LOG = LoggerFactory
			.getLogger(RiskTagListAction.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.aspire.ponaadmin.web.BaseAction#doPerform(org.apache.struts.action
	 * .ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		LOG.debug("doPerform()");

		// 从请求中获取操作类型
		String perType = this.getParameter(request, "perType").trim();

		if ("query".equals(perType)) {
			return query(mapping, form, request, response);
		}
		if ("edit".equals(perType)) {
			return edit(mapping, form, request, response);
		}
		if ("cancel".equals(perType)) {
			return cancel(mapping, form, request, response);
		}
		if ("output".equals(perType)) {
			return output(mapping, form, request, response);
		}
		if ("queryDetail".equals(perType)) {
			return queryDetail(mapping, form, request, response);
		}else {
			String forward = Constants.FORWARD_COMMON_FAILURE;
			this.saveMessagesValue(request, "对不起，您访问的路径不存在");
			return mapping.findForward(forward);
		}
	}

	// 首页查询
	private ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		LOG.debug("doPerform()");
		String forward = "risktag";
		PageResult page = new PageResult(request);

		String id = this.getParameter(request, "id").trim();
		String stats = this.getParameter(request, "stats").trim();

		String actionType = "查询风险标签数据列表";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = stats;

		try {
			RiskBo.getInstance().query(page, id, stats);
		} catch (BOException e) {
			LOG.error(e);
			this.saveMessagesValue(request, "查询风险标签数据列表出错");
			// 写操作日志
			actionResult = false;
			actionDesc = "查询风险标签数据列表出错";
			this.actionLog(request, actionType, actionTarget, actionResult,
					actionDesc);
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}

		// 写操作日志
		actionDesc = "查询榜单黑名单元数据列表成功";
		this.actionLog(request, actionType, actionTarget, actionResult,
				actionDesc);

		request.setAttribute("PageResult", page);
		request.setAttribute("id", id);
		request.setAttribute("stats", stats);

		return mapping.findForward(forward);
	}

	/**
	 * 标签明细数据
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws BOException
	 */
	private ActionForward queryDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		LOG.debug("doPerform()");
		String forward = "query";
		PageResult page = new PageResult(request);

		String riskid = this.getParameter(request, "riskid").trim();
		String stats = this.getParameter(request, "stats").trim();
		String contentid = this.getParameter(request, "contentid").trim();
		String content = this.getParameter(request, "content").trim();
		String actionType = "查询黑名单元数据列表";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = riskid;

		try {
			RiskTagBo.getInstance().queryList(page,riskid,stats,content,contentid);
		} catch (BOException e) {
			LOG.error(e);
			this.saveMessagesValue(request, "查询黑名单元数据列表出错");
			// 写操作日志
			actionResult = false;
			actionDesc = "查询榜单黑名单元数据列表出错";
			this.actionLog(request, actionType, actionTarget, actionResult,
					actionDesc);
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}

		// 写操作日志
		actionDesc = "查询榜单黑名单元数据列表成功";
		this.actionLog(request, actionType, actionTarget, actionResult,
				actionDesc);

		request.setAttribute("PageResult", page);
		request.setAttribute("riskid", riskid);
		request.setAttribute("contentid", contentid);
		request.setAttribute("content", content);
		return mapping.findForward(forward);
	}

	private ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOG.debug("doPerform()");
		String contentids[] = request.getParameterValues("dealRef");
		String forward = "edit";
		String isblack = this.getParameter(request, "isblack"); // isblack为1的时候下线
																// 为0的时候上线
		String actionType = "查询黑名单元数据列表";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = contentids.toString();

		try {
			RiskTagBo.getInstance().doEdit(contentids, isblack);
		} catch (BOException e) {
			LOG.error(e);
			this.saveMessagesValue(request, "装填变更失败");
			// 写操作日志
			actionResult = false;
			actionDesc = "插入黑名单时出错";
			this.actionLog(request, actionType, actionTarget, actionResult,
					actionDesc);
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		// 写操作日志
		actionDesc = "状态变更成功!";
		this.actionLog(request, actionType, actionTarget, actionResult,
				actionDesc);
		try {
			RiskTagBo.getInstance().sendmessage(contentids, isblack);
			actionDesc += "发送消息成功!";
		} catch (DAOException e) {
			LOG.error(e);
		}
		this.saveMessagesValue(request, actionDesc);

		forward = Constants.FORWARD_COMMON_SUCCESS;
		request.setAttribute(Constants.PARA_GOURL,
				"riskTagList.do?perType=query");
		return mapping.findForward(forward);
	}

	private ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOG.debug("doPerform()");
		String forward = "cancel";
		String actionType = "查询黑名单元数据列表";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = "";
		String riskid = this.getParameter(request, "riskid");
		String stats = this.getParameter(request, "stats");
		try {
			RiskTagBo.getInstance().sendmessages(riskid,stats);
		} catch (Exception e) {
			LOG.error(e);
			this.saveMessagesValue(request, e.getMessage());
			// 写操作日志
			actionResult = false;
			actionDesc = "操作失败";
			this.actionLog(request, actionType, actionTarget, actionResult,
					actionDesc);
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		// 写操作日志
		actionDesc = "消息处理成功!";
		this.actionLog(request, actionType, actionTarget, actionResult,
				actionDesc);

		this.saveMessagesValue(request, actionDesc);

		forward = Constants.FORWARD_COMMON_SUCCESS;
		request.setAttribute(Constants.PARA_GOURL,
				"riskTagList.do?perType=query");
		return mapping.findForward(forward);
	}
	private ActionForward output(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
    	if (log.isDebugEnabled())
		{
			log.debug("excel导出开始.......");
		}
    	String type = this.getParameter(request, "type");
    	String riskid = this.getParameter(request, "riskid");
    	String contentid = this.getParameter(request, "contentid");
		String actionType = "导出数据";
		boolean actionResult = true;
		String actionDesc = "导出元数据成功";
		String actionTarget = "";
		String des="forbidden";
		if ("0".equals(type) || "3".equals(type)) {
			des="show";
		}
		if (StringUtils.isEmpty(type)) {
			des="export";
		}
		OutputStream os = null;
		WritableWorkbook wwb = null;
		String excelName = "query_risktag_"+des+"_"
				+ System.currentTimeMillis() + ".xls";
		try
		{
			os = new FileOutputStream(excelName);
			wwb = Workbook.createWorkbook(os);
			
			RiskTagBo.getInstance().exportTagData(wwb, type,riskid,contentid);
		}
		catch (Exception e)
		{
			log.error(e);
			
            // 写操作日志
			actionResult = false;
			actionDesc = "全量导出黑名单榜单元数据出错";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
		}
		finally
		{
			try
			{
				if (wwb != null)
				{
					wwb.write();
					wwb.close();
				}
				if (os != null)
				{
					os.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		response.setHeader("Content-disposition", "attachment;filename="
				+ excelName);
		response.setContentType("application/msexcel");
		try
		{
			FileInputStream fileInputStream = new FileInputStream(excelName);
			OutputStream out = response.getOutputStream();
			int i = 0;
			while ((i = fileInputStream.read()) != -1)
			{
				out.write(i);
			}
			fileInputStream.close();
			File file = new File(excelName);
			file.delete();
		}
		catch (FileNotFoundException e)
		{
			log.error(e);
			e.printStackTrace();
		}
		catch (IOException e)
		{
			log.error(e);
			e.printStackTrace();
		}
		
        // 写操作日志
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
		return null;
        
    }
}
