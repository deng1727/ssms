package com.aspire.ponaadmin.web.game.action;

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
import org.apache.struts.upload.FormFile;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.game.bo.TWGameBO;
import com.aspire.ponaadmin.web.game.vo.TWGameVO;
import com.aspire.ponaadmin.web.repository.web.DataImportForm;

/**
 * @author wangminlong
 * 
 */
public class TWGameAction extends BaseAction
{
	
	/**
	 * 日志引用
	 */
	private static final JLogger LOG = LoggerFactory
			.getLogger(TWGameAction.class);
	
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
		else if ("input".equals(perType))
		{
			return input(mapping, form, request, response);
		}
		else if ("export".equals(perType))
		{
			return doExport(mapping, form, request, response, false);
		}
		else if ("allExport".equals(perType))
		{
			return doExport(mapping, form, request, response, true);
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
		String gameId = this.getParameter(request, "gameId").trim();
		String gameName = this.getParameter(request, "gameName").trim();
		
		String actionType = "查询图文游戏列表";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = gameId;
		
		TWGameVO vo = new TWGameVO();
		vo.setGameId(gameId);
		vo.setGameName(gameName);
		
		if (LOG.isDebugEnabled())
		{
			LOG.info("从请求中获取查询条件：gameId = " + gameId + ",gameName=" + gameName);
		}
		try
		{
			TWGameBO.getInstance().queryTWGameList(page, vo);
		}
		catch (BOException e)
		{
			LOG.error(e);
			
			// 写操作日志
			actionResult = false;
			actionDesc = "查询图文游戏列表出错";
			this.actionLog(request, actionType, actionTarget, actionResult,
					actionDesc);
			
			this.saveMessagesValue(request, "查询图文游戏列表出错");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		
		// 写操作日志
		actionDesc = "查询图文游戏列表成功";
		this.actionLog(request, actionType, actionTarget, actionResult,
				actionDesc);
		
		request.setAttribute("perType", forward);
		request.setAttribute("PageResult", page);
		request.setAttribute("gameId", gameId);
		request.setAttribute("gameName", gameName);
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
	public ActionForward input(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException
	{
		String forward = "input";
		String ret = "";
		
		if (LOG.isDebugEnabled())
		{
			LOG.debug("action请求开始");
		}
		
		String actionType = "导入修改图文游戏排序信息";
		boolean actionResult = true;
		String actionDesc = "导入修改图文游戏排序成功";
		String actionTarget = "";
		
		// 从请求中获取货架内码
		DataImportForm iForm = (DataImportForm) form;
		
		// 校验文件后缀名
		if (!iForm.checkFileNameExtension(new String[] { "xls" }))
		{
			this.saveMessages(request, "文件后缀名出错！");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		
		FormFile dataFile = iForm.getDataFile();
		
		try
		{
			// 导入修改图文游戏排序
			ret = TWGameBO.getInstance().importTWGameSortId(dataFile);
		}
		catch (BOException e)
		{
			LOG.error(e);
			
			// 写操作日志
			actionResult = false;
			actionDesc = "导入修改图文游戏排序信息出错";
			this.actionLog(request, actionType, actionTarget, actionResult,
					actionDesc);
			
			this.saveMessagesValue(request, "用于文件批量导入图文游戏排序出错");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		
		// 写操作日志
		this.actionLog(request, actionType, actionTarget, actionResult,
				actionDesc);
		
		this.saveMessagesValue(request, "文件批量导入图文游戏排序操作成功，" + ret);
		forward = Constants.FORWARD_COMMON_SUCCESS;
		request.setAttribute(Constants.PARA_GOURL,
				"queryGameTW.do?perType=query");
		return mapping.findForward(forward);
	}
	
	/**
	 * 用于导出当前数据
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @param isAll
	 * @return
	 * @throws BOException
	 */
	public ActionForward doExport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			boolean isAll) throws BOException
	{
		if (log.isDebugEnabled())
		{
			log.debug("doExport in......");
			log.debug("excel导出.......");
		}
		
		String actionType = "导出图文游戏信息";
		boolean actionResult = true;
		String actionDesc = "导出图文游戏信息成功";
		String actionTarget = "";
		
		OutputStream os = null;
		WritableWorkbook wwb = null;
		String excelName = "query_TW_GAME_" + System.currentTimeMillis()
				+ ".xls";
		try
		{
			os = new FileOutputStream(excelName);
			wwb = Workbook.createWorkbook(os);
			TWGameBO.getInstance().exportQueryData(request, wwb, isAll);
		}
		catch (Exception e)
		{
			log.error(e);
			
			// 写操作日志
			actionResult = false;
			actionDesc = "导出图文游戏信息出错";
			this.actionLog(request, actionType, actionTarget, actionResult,
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
			{}
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
		this.actionLog(request, actionType, actionTarget, actionResult,
				actionDesc);
		return null;
	}
}
