package com.aspire.ponaadmin.web.repository.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.lockLocation.dao.LockLocationDAO;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.CategoryBO;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;

public class CgyContentImportAction extends BaseAction {

	/**
	 * ��־����
	 */
	private static final JLogger LOG = LoggerFactory
			.getLogger(DataImportAction.class);

	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("DataImportAction doPerform()");
		}

		// ���巵��ҳ��
		String forward = Constants.FORWARD_COMMON_SUCCESS;

		// ȡ����type
		String actione = this.getParameter(request, "action");
		LOG.debug("action = " + actione);
		if ("import".equals(actione)) {
			// ���ݵ���
			DataImportForm iForm = (DataImportForm) form;
			
			//removed by aiyan 2012-12-20 ���У����JS�Ѿ������ˡ�
////			 У���ļ���׺��
//	        if(!iForm.checkFileNameExtension(new String[]{"xls"}))
//	        {
//	            this.saveMessages(request, "�ļ���׺������");
//	            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
//	        } 

			forward = importContent(mapping, iForm, request, response);
		}

		return mapping.findForward(forward);
	}

	/**
	 * ���ݵ��봦��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws BOException
	 */
	private String importContent(ActionMapping mapping, DataImportForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {

		if (LOG.isDebugEnabled()) {
			LOG.debug("importSoftVersion() is start");
		}

		String forward = Constants.FORWARD_COMMON_SUCCESS;

		String addType = getParameter(request,"addType");
		String cateId = this.getParameter(request, "cateId");
		String cgyPath = this.getParameter(request, "cgyPath");
		String isSyn = this.getParameter(request, "isSyn");
		String menuStatus = this.getParameter(request, "menuStatus");
		
		try {
			FormFile dataFile = form.getDataFile();


			
			this.actionLog(request, "��ʼ������Ʒ", "����ID:" + cateId + ",�ļ���:"
					+ dataFile.getFileName(), true, "��ʼ������Ʒ");
			
			int successCount = 0;
			
			Category category=(Category)Repository.getInstance().getNode(cateId, RepositoryConstants.TYPE_CATEGORY);
			if(menuStatus != null && !"".equals(menuStatus) && "3".equals(menuStatus)){
				category.setGoodsStatus("0");
				category.save();
			}
			if("ADD".equals(addType)){
				successCount = CategoryBO.getInstance().importCateData2(
						dataFile, category, isSyn,menuStatus);
			}else if("ALL".equals(addType)){
				successCount = CategoryBO.getInstance().importCateData(
						dataFile, category, isSyn,menuStatus);
			}
			try {
				LockLocationDAO.getInstance().callProcedureAutoFixSortid(category.getCategoryID());
			} catch (Exception e) {
				LOG.error("�Զ��޸���������Ʒ��sortid�쳣",e);
			}
			String message = "���ݵ���ɹ������ɹ�����" + successCount + "������";
			LOG.info(message);
			this.actionLog(request, "����������Ʒ", dataFile.getFileName(), true,
					"��������" + successCount + "������");
			this.saveMessagesValue(request, message);

			
			
			//add by aiyan 2012-07-23 �ڱ��ط�������¼����Ӫ��Ա����������Ʒ���Ǹ�EXCEL����Ϊ֤��Ŷ��������
			BufferedInputStream bin = null;
			BufferedOutputStream bout = null;
			try {

				ModuleConfig module = ConfigFactory.getSystemConfig()
						.getModuleConfig("ssms");
				String localDir = module.getItemValue("localDir");

				bin = new java.io.BufferedInputStream(dataFile.getInputStream());
				File fout = new File(localDir + File.separator+dataFile.getFileName());
				if(!fout.getParentFile().exists()){
					fout.getParentFile().mkdirs();
				}
				bout = new java.io.BufferedOutputStream(new FileOutputStream(fout));
				byte[] buff = new byte[8192];
				while (bin.read(buff) != -1) {
					bout.write(buff);

				}
				bin.close();
				bout.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				LOG.error("�ļ�������", e);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				LOG.error(e);
			}

		} catch (BOException e) {
			forward = Constants.FORWARD_COMMON_FAILURE;
			this.saveMessages(request, "���ݵ���ʧ��");
		}
		request.setAttribute(Constants.PARA_GOURL,
				"cgyContentList.do?subSystem=ssms&categoryID=" + cateId
						+ "&cgyPath=" + cgyPath);
		return forward;
	}

}
