package com.aspire.dotcard.baseVideo.action;

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

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseVideo.bo.VideoProgramBO;
import com.aspire.dotcard.baseVideo.vo.ProgramVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;

public class VideoProgramAction extends BaseAction
{
	/**
	 * ��־����
	 */
	private static final JLogger LOG = LoggerFactory
			.getLogger(VideoProgramAction.class);
	
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
		else if ("export".equals(perType))
		{
			return doExport(mapping, form, request, response, false);
		}
		else if ("exportAll".equals(perType))
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
		String programId = this.getParameter(request, "programId").trim();
		String programName = this.getParameter(request, "programName").trim();
		String videoId = this.getParameter(request, "videoId").trim();
		String nodeId = this.getParameter(request, "nodeId").trim();
		String isFirst = this.getParameter(request, "isFirst").trim();
		
		String actionType = "��ѯ��Ƶ��Ŀ�б�";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = programId;
		
		if (LOG.isDebugEnabled())
		{
			LOG.info("�������л�ȡ��ѯ������programId = " + programId + ",programName = "
					+ programName + ",videoId = " + videoId + ",nodeId = "
					+ nodeId);
		}
		
		if (!"1".equals(isFirst))
		{
			ProgramVO vo = new ProgramVO();
			vo.setProgramId(programId);
			vo.setProgramName(programName);
			vo.setNodeId(nodeId);
			vo.setVideoId(videoId);
			
			try
			{
				// ��ѯ��Ƶ��Ŀ�б�
				VideoProgramBO.getInstance().queryVideoProgramList(page, vo);
			}
			catch (BOException e)
			{
				LOG.error(e);
				this.saveMessagesValue(request, "��ѯ��Ƶ��Ŀ�б����");
				
				// д������־
				actionResult = false;
				actionDesc = "��ѯ��Ƶ��Ŀ�б����";
				this.actionLog(request, actionType, actionTarget, actionResult,
						actionDesc);
				
				return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
			}
		}
		
		// д������־
		actionDesc = "��ѯ��Ƶ��Ŀ�б�ɹ�";
		this.actionLog(request, actionType, actionTarget, actionResult,
				actionDesc);
		
		request.setAttribute("programId", programId);
		request.setAttribute("programName", programName);
		request.setAttribute("videoId", videoId);
		request.setAttribute("nodeId", nodeId);
		request.setAttribute("isFirst", isFirst);
		request.setAttribute("perType", forward);
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
	public ActionForward doExport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			boolean isAll) throws BOException
	{
		if (log.isDebugEnabled())
		{
			log.debug("doExport in......");
			log.debug("excel����.......");
		}
		
		String actionType = "������Ƶ��Ŀ�б���Ϣ";
		boolean actionResult = true;
		String actionDesc = "������Ƶ��Ŀ�б���Ϣ�ɹ�";
		String actionTarget = "";
		
		OutputStream os = null;
		WritableWorkbook wwb = null;
		String excelName = "query_program_" + System.currentTimeMillis()
				+ ".xls";
		try
		{
			os = new FileOutputStream(excelName);
			wwb = Workbook.createWorkbook(os);
			VideoProgramBO.getInstance().exportQueryData(request, wwb, isAll);
		}
		catch (Exception e)
		{
			log.error(e);
			
			// д������־
			actionResult = false;
			actionDesc = "������Ƶ��Ŀ��Ϣ����";
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
		
		// д������־
		this.actionLog(request, actionType, actionTarget, actionResult,
				actionDesc);
		return null;
	}
}
