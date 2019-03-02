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
	 * ��־����
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
		
		// �������л�ȡ��������
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
			this.saveMessagesValue(request, "�Բ��������ʵ�·��������");
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
		
		String actionType = "��ѯͼ����Ϸ�б�";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = gameId;
		
		TWGameVO vo = new TWGameVO();
		vo.setGameId(gameId);
		vo.setGameName(gameName);
		
		if (LOG.isDebugEnabled())
		{
			LOG.info("�������л�ȡ��ѯ������gameId = " + gameId + ",gameName=" + gameName);
		}
		try
		{
			TWGameBO.getInstance().queryTWGameList(page, vo);
		}
		catch (BOException e)
		{
			LOG.error(e);
			
			// д������־
			actionResult = false;
			actionDesc = "��ѯͼ����Ϸ�б����";
			this.actionLog(request, actionType, actionTarget, actionResult,
					actionDesc);
			
			this.saveMessagesValue(request, "��ѯͼ����Ϸ�б����");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		
		// д������־
		actionDesc = "��ѯͼ����Ϸ�б�ɹ�";
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
			LOG.debug("action����ʼ");
		}
		
		String actionType = "�����޸�ͼ����Ϸ������Ϣ";
		boolean actionResult = true;
		String actionDesc = "�����޸�ͼ����Ϸ����ɹ�";
		String actionTarget = "";
		
		// �������л�ȡ��������
		DataImportForm iForm = (DataImportForm) form;
		
		// У���ļ���׺��
		if (!iForm.checkFileNameExtension(new String[] { "xls" }))
		{
			this.saveMessages(request, "�ļ���׺������");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		
		FormFile dataFile = iForm.getDataFile();
		
		try
		{
			// �����޸�ͼ����Ϸ����
			ret = TWGameBO.getInstance().importTWGameSortId(dataFile);
		}
		catch (BOException e)
		{
			LOG.error(e);
			
			// д������־
			actionResult = false;
			actionDesc = "�����޸�ͼ����Ϸ������Ϣ����";
			this.actionLog(request, actionType, actionTarget, actionResult,
					actionDesc);
			
			this.saveMessagesValue(request, "�����ļ���������ͼ����Ϸ�������");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		
		// д������־
		this.actionLog(request, actionType, actionTarget, actionResult,
				actionDesc);
		
		this.saveMessagesValue(request, "�ļ���������ͼ����Ϸ��������ɹ���" + ret);
		forward = Constants.FORWARD_COMMON_SUCCESS;
		request.setAttribute(Constants.PARA_GOURL,
				"queryGameTW.do?perType=query");
		return mapping.findForward(forward);
	}
	
	/**
	 * ���ڵ�����ǰ����
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
			log.debug("excel����.......");
		}
		
		String actionType = "����ͼ����Ϸ��Ϣ";
		boolean actionResult = true;
		String actionDesc = "����ͼ����Ϸ��Ϣ�ɹ�";
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
			
			// д������־
			actionResult = false;
			actionDesc = "����ͼ����Ϸ��Ϣ����";
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
		
		// д������־
		this.actionLog(request, actionType, actionTarget, actionResult,
				actionDesc);
		return null;
	}
}
