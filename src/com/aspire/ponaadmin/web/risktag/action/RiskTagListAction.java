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
	 * ��־����
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

		// �������л�ȡ��������
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
			this.saveMessagesValue(request, "�Բ��������ʵ�·��������");
			return mapping.findForward(forward);
		}
	}

	// ��ҳ��ѯ
	private ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		LOG.debug("doPerform()");
		String forward = "risktag";
		PageResult page = new PageResult(request);

		String id = this.getParameter(request, "id").trim();
		String stats = this.getParameter(request, "stats").trim();

		String actionType = "��ѯ���ձ�ǩ�����б�";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = stats;

		try {
			RiskBo.getInstance().query(page, id, stats);
		} catch (BOException e) {
			LOG.error(e);
			this.saveMessagesValue(request, "��ѯ���ձ�ǩ�����б����");
			// д������־
			actionResult = false;
			actionDesc = "��ѯ���ձ�ǩ�����б����";
			this.actionLog(request, actionType, actionTarget, actionResult,
					actionDesc);
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}

		// д������־
		actionDesc = "��ѯ�񵥺�����Ԫ�����б�ɹ�";
		this.actionLog(request, actionType, actionTarget, actionResult,
				actionDesc);

		request.setAttribute("PageResult", page);
		request.setAttribute("id", id);
		request.setAttribute("stats", stats);

		return mapping.findForward(forward);
	}

	/**
	 * ��ǩ��ϸ����
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
		String actionType = "��ѯ������Ԫ�����б�";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = riskid;

		try {
			RiskTagBo.getInstance().queryList(page,riskid,stats,content,contentid);
		} catch (BOException e) {
			LOG.error(e);
			this.saveMessagesValue(request, "��ѯ������Ԫ�����б����");
			// д������־
			actionResult = false;
			actionDesc = "��ѯ�񵥺�����Ԫ�����б����";
			this.actionLog(request, actionType, actionTarget, actionResult,
					actionDesc);
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}

		// д������־
		actionDesc = "��ѯ�񵥺�����Ԫ�����б�ɹ�";
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
		String isblack = this.getParameter(request, "isblack"); // isblackΪ1��ʱ������
																// Ϊ0��ʱ������
		String actionType = "��ѯ������Ԫ�����б�";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = contentids.toString();

		try {
			RiskTagBo.getInstance().doEdit(contentids, isblack);
		} catch (BOException e) {
			LOG.error(e);
			this.saveMessagesValue(request, "װ����ʧ��");
			// д������־
			actionResult = false;
			actionDesc = "���������ʱ����";
			this.actionLog(request, actionType, actionTarget, actionResult,
					actionDesc);
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		// д������־
		actionDesc = "״̬����ɹ�!";
		this.actionLog(request, actionType, actionTarget, actionResult,
				actionDesc);
		try {
			RiskTagBo.getInstance().sendmessage(contentids, isblack);
			actionDesc += "������Ϣ�ɹ�!";
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
		String actionType = "��ѯ������Ԫ�����б�";
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
			// д������־
			actionResult = false;
			actionDesc = "����ʧ��";
			this.actionLog(request, actionType, actionTarget, actionResult,
					actionDesc);
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		// д������־
		actionDesc = "��Ϣ����ɹ�!";
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
			log.debug("excel������ʼ.......");
		}
    	String type = this.getParameter(request, "type");
    	String riskid = this.getParameter(request, "riskid");
    	String contentid = this.getParameter(request, "contentid");
		String actionType = "��������";
		boolean actionResult = true;
		String actionDesc = "����Ԫ���ݳɹ�";
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
			
            // д������־
			actionResult = false;
			actionDesc = "ȫ��������������Ԫ���ݳ���";
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
		
        // д������־
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
		return null;
        
    }
}
