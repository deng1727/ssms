/*
 * �ļ�����DataExportAction.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
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
	 * ��־����
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
		
		// �������л�ȡ��������
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
		String exportByUser = this.getParameter(request, "exportByUser").trim();
		
		String actionType = "��ѯ���������б���Ϣ";
		boolean actionResult = true;
		String actionDesc = "��ѯ���������б�ɹ�";
		String actionTarget = exportByUser;
		
		try
		{
			DataExportBO.getInstance().queryDataExportList(page, exportByUser);
		}
		catch (BOException e)
		{
			LOG.error(e);
			
			// д������־
			actionResult = false;
			actionDesc = "��ѯ���������б����";
			this.actionLog(request, actionType, actionTarget, actionResult,
					actionDesc);
			
			this.saveMessagesValue(request, "��ѯ���������б����");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		
		// д������־
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
		
		String actionType = "ɾ�����������б���Ϣ";
		boolean actionResult = true;
		String actionDesc = "ɾ�����������б�ɹ�";
		String actionTarget = id;
		
		try
		{
			// ����ɾ��ָ������
			DataExportBO.getInstance().delDataExport(id);
		}
		catch (BOException e)
		{
			LOG.error(e);
			
			// д������־
			actionResult = false;
			actionDesc = "ɾ�������������";
			this.actionLog(request, actionType, actionTarget, actionResult,
					actionDesc);
			
			this.saveMessagesValue(request, "ɾ�������������");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		
		// д������־
		this.actionLog(request, actionType, actionTarget, actionResult,
				actionDesc);
		this.saveMessagesValue(request, "ɾ�����������б�ɹ�");
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
		
		String actionType = "��֤����sql������ȷ��";
		boolean actionResult = true;
		String actionDesc = "��֤����sql������ȷ�Գɹ�";
		String actionTarget = exportSql;
		
		try
		{
			// �õ���ǰ������Ϣ
			isTrue = DataExportBO.getInstance().hasSqlTrue(exportSql,
					exportLine);
		}
		catch (BOException e)
		{
			LOG.error(e);
			
			// д������־
			actionResult = false;
			actionDesc = "��֤����sql������ȷ�Գ���";
			this.actionLog(request, actionType, actionTarget, actionResult,
					actionDesc);
			
			this.saveMessagesValue(request, "��֤����sql������ȷ�Գ���");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		
		// д������־
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
		
		String actionType = "ִ�е���������Ϣ";
		boolean actionResult = true;
		String actionDesc = "ִ�е�������ɹ�";
		String actionTarget = id;
		
		DataExportVO dataExportVO = new DataExportVO();
		String returnStr;
		
		try
		{
			// �õ���ǰ����������Ϣ
			dataExportVO = DataExportBO.getInstance().queryDataExportVO(id);
			returnStr = ExportSqlFactory.getExportSql(dataExportVO).createFile(
					DataExportGroupDAO.getInstance().queryDataExportGroup(
							dataExportVO.getGroupId()));
		}
		catch (BOException e)
		{
			LOG.error(e);
			
			// д������־
			actionResult = false;
			actionDesc = "ִ�е����������";
			this.actionLog(request, actionType, actionTarget, actionResult,
					actionDesc);
			
			this.saveMessagesValue(request, "ִ�е����������");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		catch (DAOException e)
		{
			LOG.error(e);
			
			// д������־
			actionResult = false;
			actionDesc = "��ȡ����������Ϣʱ����";
			this.actionLog(request, actionType, actionTarget, actionResult,
					actionDesc);
			
			this.saveMessagesValue(request, "��ȡ����������Ϣʱ����");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		
		// д������־
		this.actionLog(request, actionType, actionTarget, actionResult,
				actionDesc);
		
		this.saveMessagesValue(request, "ִ�е�����������" + returnStr);
		request.setAttribute(Constants.PARA_GOURL,
				"../exportData/queryExport.do?perType=query&exportByUser="
						+ exportByUser);
		forward = Constants.FORWARD_COMMON_SUCCESS;
		return mapping.findForward(forward);
	}
}
