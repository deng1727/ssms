package com.aspire.ponaadmin.web.pushadv.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.util.StringUtils;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.usermanager.UserManagerBO;
import com.aspire.ponaadmin.common.usermanager.UserSessionVO;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.channelUser.util.Common;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.constant.ResourceConstants;
import com.aspire.ponaadmin.web.datafield.UploadFileKeyResUtil;
import com.aspire.ponaadmin.web.pushadv.bo.PushAdvBO;
import com.aspire.ponaadmin.web.pushadv.dao.PushAdvDAO;
import com.aspire.ponaadmin.web.pushadv.vo.PushAdvVO;
import com.aspire.ponaadmin.web.repository.web.DataImportForm;
import com.aspire.ponaadmin.web.system.PageSizeConstants;
/**
 * <p>
 * Title:���͹�����
 * </p>
 * <p>
 * Description: ���͹���ѯ���������޸ġ�ɾ����ز���
 * </p>
 * <p>
 * Copyright: Copyright (c) 2015
 * </p>
 * 
 * @author baojun
 * @version
 */
public class PushAdvAction extends BaseAction {

	/**
	 * ��־����
	 */
	private static final JLogger LOG = LoggerFactory
			.getLogger(PushAdvAction.class);

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

		if (request.getSession() == null
				|| UserManagerBO.getInstance().getUserSessionVO(
						request.getSession()) == null) {
			this.saveMessages(request, "�û�δ��½���ߵ�½�ѳ�ʱ��");
			request.setAttribute(Constants.PARA_GOURL, "index.jsp");
			request.setAttribute("loginResult", new Integer(Common.LOGINRESULT));
			return mapping.findForward(Common.LOGIN_HINT_CHANNELUSER);
		}

		// ���������ͣ�0-��ʾ�ն˹�˾��1-���ն˹�˾
		// �������͹������ն˹�˾���еĹ��ܣ����ն˹�˾û��Ȩ�޷��ʸù���
		if (!"0".equals(UserManagerBO.getInstance()
				.getUserSessionVO(request.getSession()).getChannel()
				.getMoType())) {
			String forward = Constants.FORWARD_COMMON_FAILURE;
			this.saveMessagesValue(request, "�Բ�����û��Ȩ�޷��ʣ�");
			return mapping.findForward(forward);
		}

		// �������л�ȡ��������
		String perType = this.getParameter(request, "perType").trim();

		if ("query".equals(perType)) {
			return query(mapping, form, request, response);
		} else if ("add".equals(perType)) {
			return add(mapping, form, request, response);
		} else if ("save".equals(perType)) {
			return save(mapping, form, request, response);
		} else if ("remove".equals(perType)) {
			return remove(mapping, form, request, response);
		} else if ("content".equals(perType)) {
			return queryContent(mapping, form, request, response);
		} else if ("edit".equals(perType)) {
			return edit(mapping, form, request, response);
		}

		else {
			String forward = Constants.FORWARD_COMMON_FAILURE;
			this.saveMessagesValue(request, "�Բ��������ʵ�·��������");
			return mapping.findForward(forward);
		}
	}

	/**
	 * ��ѯӦ�������б�
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws BOException
	 */
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws BOException {
		LOG.debug("doPerform()");
		String forward = "query";
		PageResult page = new PageResult(request);

		String contentId = this.getParameter(request, "contentId").trim();
		String type = this.getParameter(request, "catename").trim();
		String startTime = this.getParameter(request, "startTime").trim();
		String endTime = this.getParameter(request, "endTime").trim();

		try {
			PushAdvVO vo = new PushAdvVO();
			vo.setContentId(contentId);
			vo.setType(type);
			vo.setStartTime(startTime);
			vo.setEndTime(endTime);
			PushAdvBO.getInstance().queryPushAdvList(page, vo);
		} catch (BOException e) {
			LOG.error(e);
			this.saveMessagesValue(request, "��ѯӦ�������б�ʧ��");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}

		request.setAttribute("PageResult", page);
		request.setAttribute("contentId", contentId);
		request.setAttribute("type", type);
		request.setAttribute("startTime", startTime);
		request.setAttribute("endTime", endTime);
		return mapping.findForward(forward);
	}

	/**
	 * ��ѯӦ���б�
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws BOException
	 */
	public ActionForward queryContent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws BOException {
		LOG.debug("doPerform()");
		String forward = "content";
		PageResult page = new PageResult(request);

		String contentId = this.getParameter(request, "contentId").trim();
		String contentName = this.getParameter(request, "contentName").trim();
		String pageSize = this.getParameter(request, "pageSize");

		String isFirst = this.getParameter(request, "isFirst").trim();

		if ("".equals(pageSize.trim())) {
			pageSize = PageSizeConstants.page_DEFAULT;
		}
		if (isFirst.equals("1"))// ��һ�β���Ҫ��ѯ
		{
			page.excute(new ArrayList(0));
			request.setAttribute("notice", "������������ѯ");
		} else {
			try {
				UserSessionVO userSession = UserManagerBO.getInstance()
						.getUserSessionVO(request.getSession());

				PushAdvBO.getInstance().queryContentNoInPushList(page,
						contentId, contentName,
						userSession.getChannel().getChannelsId());
			} catch (BOException e) {
				LOG.error(e);
				this.saveMessagesValue(request, "��ѯӦ���б�ʧ��");
				return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
			}
		}

		request.setAttribute("PageResult", page);
		request.setAttribute("contentId", contentId);
		request.setAttribute("contentName", contentName);
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward(forward);
	}

	/**
	 * ��������
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws BOException
	 */
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws BOException {
		String forward = "add";
		HashMap<String, List<String>> maps = PushAdvBO.getInstance().getAll();
		List<String> versions = PushAdvBO.getInstance().getVersions();
		request.setAttribute("brandMap", maps);
		request.setAttribute("versions", versions);
		return mapping.findForward(forward);
	}

	/**
	 * ������������
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws BOException
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws BOException {
		String forward = "save";
		String style = request.getParameter("style");
		if (LOG.isDebugEnabled()) {
			LOG.debug("������������");
		}
		String picUrl = "";
		String id="";
		try {
			id = PushAdvBO.getInstance().sequences()+"";
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		DataImportForm iForm = (DataImportForm) form;
		
			FormFile dataFile = iForm.getDataFile();
			String fileName = dataFile.getFileName();
			boolean is = StringUtils.isEmpty(fileName);
			if(!is ){
				Date now = new Date();
				String resourcePicFilePath = ConfigFactory.getSystemConfig()
						.getModuleConfig("pushAdvConfig").getItemValue("DestFTPDir");


				picUrl = UploadFileKeyResUtil.getInstance()
						.upLoadfileToResServer(dataFile, resourcePicFilePath, "", String.valueOf(now.getTime()),
								"");

				if (!iForm.checkFileNameExtension(new String[] { "jpg", "jpeg", "png",
			       "bmp" })) {
				this.saveMessages(request, "��ͼƬ�ļ���");
				return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
			}
			}

		
	
		FormFile phone = iForm.getPhone();
		String fileNameph = phone.getFileName();
		boolean isph = StringUtils.isEmpty(fileNameph);
		

		
		if(!isph ){

			if (!iForm.checkphoneFileNameExtension(new String[] { "xlsx", "xls"})) {
				this.saveMessages(request, "��Excel�ļ���");
				return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
			}
			try {
				UploadFileKeyResUtil.getInstance().importData(phone, id);
			} catch (Exception e) {
				// TODO: handle exception
			}

		}else{
			PushAdvDAO.getInstance().addMsisdn(id);
		}


		

		// �������л�ȡ�ص����ID

		String contentId = this.getParameter(request, "contentId");
		String title = this.getParameter(request, "title");
		String subTitle = this.getParameter(request, "subTitle");
		String startTime = this.getParameter(request, "startTime");
		String endTime = this.getParameter(request, "endTime");
		String type = this.getParameter(request, "catename");
		String uri = this.getParameter(request, "uri");
		String[] reDevices = request.getParameterValues("reDevice");
		String[] bversions = request.getParameterValues("bversions");
	
	
		
		String actionType = "������������";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = contentId;
		String device = "";
		StringBuffer versions = new StringBuffer();
		try {
			String brands = PushAdvBO.getInstance().getBrand(reDevices);
			for (int i = 0; i < reDevices.length; i++) {
				device += reDevices[i] + "|";
			}
			for (int i = 0; i < bversions.length; i++) {
				versions.append(bversions[i] + "|");
			}
			// ��ѯӦ���Ƿ����
			if(!"3".equals(type) && "add".equals(style)){
				PushAdvBO.getInstance().isHasByContentId(contentId , type);
				this.saveMessagesValue(request, "�������ͳɹ�");
			}else{
				this.saveMessagesValue(request, "�޸����ͳɹ�");
			}


			PushAdvVO pushadv = new PushAdvVO();
			pushadv.setId(id);
			pushadv.setType(type);
			pushadv.setPicUrl(picUrl);
			pushadv.setContentId(contentId);
			pushadv.setTitle(title);
			pushadv.setSubTitle(subTitle);
			pushadv.setStartTime(startTime);
			pushadv.setEndTime(endTime);
			pushadv.setRebrand(brands);
			pushadv.setRedevice(device);
			pushadv.setUri(uri);
			pushadv.setVersions(versions.toString());


			PushAdvBO.getInstance().save(pushadv,style);



			forward = Constants.FORWARD_COMMON_SUCCESS;
		} catch (BOException e) {
			LOG.error(e);
			if (e.getErrorCode() == 1001) {
				actionDesc = "������id�Ѵ������ͣ�Id=" + contentId;
				this.saveMessages(request, actionDesc);
			} else if (e.getErrorCode() == 1002) {
				actionDesc = "������id�����ڣ���������ӻ�ѡ��Id=" + contentId;
				this.saveMessages(request, actionDesc);
			} else {
				actionDesc = "��������ʧ�ܣ�";
				this.saveMessages(request, ResourceConstants.WEB_ERR_SYSTEM);
			}
			actionResult = false;
			forward = Constants.FORWARD_COMMON_FAILURE;
		}
		// д������־
		this.actionLog(request, actionType, actionTarget, actionResult,
				actionDesc);

		request.setAttribute(Constants.PARA_GOURL, "pushAdv.do?perType=query");
		return mapping.findForward(forward);
	}

	/**
	 * �����Ƴ�ָ��TAC��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws BOException
	 */
	public ActionForward remove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws BOException {
		String forward = "remove";

		// �������л�ȡ�ص����ID
		String id = this.getParameter(request, "id").trim();
		String contentId = this.getParameter(request, "contentId").trim();

		String actionType = "����ɾ��ָ��Ӧ�����͹��";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = contentId;
		if (LOG.isDebugEnabled()) {
			LOG.debug("����ɾ��ָ��Ӧ�����͹��");
		}

		try {
			PushAdvBO.getInstance().delByContentId(id);
		} catch (BOException e) {
			// д������־
			actionResult = false;
			actionDesc = "����ɾ��ָ��Ӧ�����͹�����!";
			this.actionLog(request, actionType, actionTarget, actionResult,
					actionDesc);

			LOG.error(e);
			this.saveMessagesValue(request, "����ɾ��ָ��Ӧ�����͹�����");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		// д������־
		actionDesc = "ɾ��ָ��Ӧ�����͹��ɹ�!";
		this.actionLog(request, actionType, actionTarget, actionResult,
				actionDesc);

		this.saveMessagesValue(request, "ɾ��ָ��Ӧ�����͹��ɹ�");
		forward = Constants.FORWARD_COMMON_SUCCESS;
		request.setAttribute(Constants.PARA_GOURL, "pushAdv.do?perType=query");
		return mapping.findForward(forward);
	}

	/**
	 * �༭����
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws BOException
	 */
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws BOException {
		String forward = "edit";
		String id = this.getParameter(request, "id").trim();
		PushAdvVO editVo = PushAdvBO.getInstance().getVOById(id);
		String vs = editVo.getVersions();
		String devices = editVo.getRedevice();
		String[] handleVersions = vs.split("\\|");
		List<String> selectedVersions = new ArrayList<String>();
		String[] device = devices.split("\\|");
		List<String> versions = PushAdvBO.getInstance().getVersions();
		HashMap<String, List<String>> maps = PushAdvBO.getInstance().getNoSelected(devices);
		for(int i = 0 ;i<handleVersions.length ; i++ ){
			versions.remove(handleVersions[i]);
			selectedVersions.add(handleVersions[i]);
		}
		List<String> dev = new ArrayList<String >();
		for(int i = 0 ; i<device.length ; i++){
			dev.add(device[i]);
		}

		request.setAttribute("SelectedDevice", dev);
		request.setAttribute("brandMap", maps);
		request.setAttribute("selectedVersions", selectedVersions);
		request.setAttribute("versions", versions);
		request.setAttribute("editVO", editVo);
		return mapping.findForward(forward);
	}


}
