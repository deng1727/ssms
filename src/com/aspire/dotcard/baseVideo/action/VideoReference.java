
package com.aspire.dotcard.baseVideo.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.WritableWorkbook;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseVideo.bo.CategoryApprovalBO;
import com.aspire.dotcard.baseVideo.bo.CollectionBO;
import com.aspire.dotcard.baseVideo.bo.VideoReferenceBO;
import com.aspire.dotcard.baseVideo.vo.ProgramVO;
import com.aspire.dotcard.baseVideo.vo.VideoRefVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.datafield.bo.KeyBaseBO;
import com.aspire.ponaadmin.web.newmusicsys.action.FileForm;
import com.aspire.ponaadmin.web.newmusicsys.bo.NewMusicRefBO;
import com.aspire.ponaadmin.web.repository.web.DataImportForm;

public class VideoReference extends BaseAction
{
    /**
     * ��־����
     */
    private static final JLogger LOG = LoggerFactory.getLogger(VideoReference.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.aspire.ponaadmin.web.BaseAction#doPerform(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm,
     *      javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {
        LOG.debug("doPerform()");

        // �������л�ȡ��������
        String perType = this.getParameter(request, "perType").trim();
        String perTypeFlag=this.getParameter(request, "perTypeFlag");
        boolean flag=false;
        if("export".equals(perTypeFlag)){
        	flag=false;
        }else{
        	flag=true;
        }
        if ("export".equals(perType))
        {
            return doExport(mapping, form, request, response, flag);
        }  
        if ("query".equals(perType))
        {
            return query(mapping, form, request, response);
        }
        else if ("remove".equals(perType))
        {
            return remove(mapping, form, request, response);
        }
        else if ("setSort".equals(perType))
        {
            return setSort(mapping, form, request, response);
        }
        else if ("queryVideo".equals(perType))
        {
            return queryVideo(mapping, form, request, response);
        }
        else if ("add".equals(perType))
        {
            return add(mapping, form, request, response);
        }
        else if ("importData".equals(perType))
        {
            return importData(mapping, form, request, response);
        }
        else if ("showVideo".equals(perType))
        {
            return showVideo(mapping, form, request, response);
        }
        else if ("saveVideo".equals(perType))
        {
            return saveVideo(mapping, form, request, response);
        }
        
        else
        {
            String forward = Constants.FORWARD_COMMON_FAILURE;
            this.saveMessagesValue(request, "�Բ��������ʵ�·��������");
            return mapping.findForward(forward);
        }
    }
    /**
     * ��������
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
		
		String actionType = "������Ƶ��Ʒ��Ϣ";
		boolean actionResult = true;
		String actionDesc = "������Ƶ��Ʒ��Ϣ�ɹ�";
		String actionTarget = "";
		
		OutputStream os = null;
		WritableWorkbook wwb = null;
		String excelName = "vo_videoreference_" + System.currentTimeMillis()
				+ ".xls";
		try
		{
			os = new FileOutputStream(excelName);
			wwb = Workbook.createWorkbook(os);
			VideoReferenceBO.getInstance()
					.exportVideoReferenceData(request, wwb, isAll);
		}
		catch (Exception e)
		{
			log.error(e);
			
			// д������־
			actionResult = false;
			actionDesc = "������Ƶ��Ʒ��Ϣ����";
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

    /**
     * �õ���Ƶָ�������µ���Ʒ�б�
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward query(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws BOException
    {
        String forward = "query";
        VideoRefVO vo = new VideoRefVO();
        PageResult page = new PageResult(request);

        // �������л�ȡ��������
        String refId = this.getParameter(request, "refId").trim();
        String categoryId = this.getParameter(request, "categoryId").trim();
        String programId = this.getParameter(request, "programId").trim();
        String programName = this.getParameter(request, "programName").trim();
        String queryNodeId = this.getParameter(request, "queryNodeId").trim();
        String videoId = this.getParameter(request, "videoId").trim();
        String startTime = this.getParameter(request, "startTime").trim();
        String endTime = this.getParameter(request, "endTime").trim();
        String approvalStatus = this.getParameter(request, "approvalStatus");

        vo.setRefId(refId);
        vo.setCategoryId(categoryId);
        vo.setProgramId(programId);
        vo.setProgramName(programName);
        vo.setVideoId(videoId);
        vo.setNodeId(queryNodeId);
        vo.setStartTime(startTime);
        vo.setEndTime(endTime);
        vo.setVerify_status(approvalStatus);
        
        String actionType = "��ѯ��Ƶ��������Ʒ";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = categoryId;
		 Map<String,Object> map = new HashMap<String,Object>();

        if (LOG.isDebugEnabled())
        {
            LOG.debug("�������л�ȡ�������룺" + categoryId);
        }

        try
        {
        	map = CategoryApprovalBO.getInstance().queryCategoryListItem(categoryId);
            VideoReferenceBO.getInstance().queryVideoRefList(page, vo);
        }
        catch (BOException e)
        {
            LOG.error(e);
            System.out.println(e);
            this.saveMessagesValue(request, "��ѯ��Ƶָ�������µ���Ʒ�б����");
            
			// д������־
			actionResult = false;
			actionDesc = "��ѯ��Ƶ��������Ʒ����";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        request.setAttribute("categoryContent", map);
        request.setAttribute("perType", forward);
        request.setAttribute("PageResult", page);
        request.setAttribute("categoryId", categoryId);
        request.setAttribute("programId", programId);
        request.setAttribute("programName", programName);
        request.setAttribute("queryNodeId", queryNodeId);
        request.setAttribute("videoId", videoId);
        request.setAttribute("startTime", startTime);
        request.setAttribute("endTime", endTime);
        request.setAttribute("approvalStatus", approvalStatus);
        
		// д������־
		actionDesc = "��ѯ��Ƶ��������Ʒ�ɹ�";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        return mapping.findForward(forward);
    }

    /**
     * �����Ƴ�ָ��������ָ������Ƶ
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward remove(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response)
                    throws BOException
    {
        String forward = "remove";

        // �������л�ȡ��������
        String categoryId = this.getParameter(request, "categoryId").trim();
        String[] refId = request.getParameterValues("dealRef");
        
        String actionType = "ɾ����Ƶ��������Ʒ";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = categoryId;

        if (LOG.isDebugEnabled())
        {
            LOG.debug("�������л�ȡ�������룺" + categoryId);
        }

        try
        {
            VideoReferenceBO.getInstance().removeVideoRefs(categoryId, refId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "�����Ƴ�ָ��������ָ������Ƶ����");
            
			// д������־
			actionResult = false;
			actionDesc = "ɾ����Ƶ��������Ʒ����";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        this.saveMessagesValue(request, "ɾ����Ƶ�ɹ�");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "queryReference.do?perType=query&categoryId="
                                             + categoryId);
        
		// д������־
		actionDesc = "ɾ����Ƶ��������Ʒ�ɹ�";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        return mapping.findForward(forward);
    }

    /**
     * ����������Ƶ��������Ƶ��Ʒ����ֵ
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward setSort(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
                    throws BOException
    {
        String forward = "setSort";

        // �������л�ȡ��������
        String categoryId = this.getParameter(request, "categoryId").trim();
        String setSortId = this.getParameter(request, "setSortId").trim();
        
        String actionType = "�޸���Ƶ��������Ʒ����";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = categoryId;
		

        if (LOG.isDebugEnabled())
        {
            LOG.debug("�������л�ȡ�������룺" + categoryId);
        }

        try
        {
            // ������Ƶ��������Ƶ��Ʒ����ֵ
            VideoReferenceBO.getInstance().setVideoSort(categoryId, setSortId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "����������Ƶ������������Ƶ����ֵʱ����");
            
			// д������־
			actionResult = false;
			actionDesc = "�޸���Ƶ��������Ʒ�������";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
	        
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        this.saveMessagesValue(request, "������Ƶ��Ʒ����ֵ�ɹ�");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "queryReference.do?perType=query&categoryId="
                                             + categoryId);
        
		// д������־
		actionDesc = "�޸���Ƶ��������Ʒ����ɹ�";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        return mapping.findForward(forward);
    }

    /**
     * ���ڲ�ѯ��Ƶ�б�
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward queryVideo(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {
        String forward = "queryVideo";
        
        
        String actionType = "����������Ƶ��������Ʒ����Ƶ��ѯ";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = "";
		

        // �������л�ȡ��������
        String isFirst = this.getParameter(request, "isFirst").trim();
        String programName = this.getParameter(request, "programName").trim();
        String programId = this.getParameter(request, "programId").trim();
        String queryNodeId = this.getParameter(request, "queryNodeId").trim();
        String videoId = this.getParameter(request, "videoId").trim();
        ProgramVO vo = new ProgramVO();
        PageResult page = new PageResult(request);

        // ����ǵ�һ�Ρ�����
        if ("1".equals(isFirst))
        {
            request.setAttribute("isFirst", isFirst);
            request.setAttribute("PageResult", page);
            request.setAttribute("programName", programName);
            request.setAttribute("programId", programId);
            request.setAttribute("queryNodeId", queryNodeId);
            request.setAttribute("videoId", videoId);

            return mapping.findForward(forward);
        }

        vo.setProgramId(programId);
        vo.setProgramName(programName);
        vo.setNodeId(queryNodeId);
        vo.setVideoId(videoId);

        if (LOG.isDebugEnabled())
        {
            LOG.debug("�������л�ȡ��Ƶ������programName=" + programName + ", programId=" + programId);
        }

        try
        {
            // ��ѯ��Ƶ�б������ϼ����»�����
            VideoReferenceBO.getInstance().queryProgramVOList(page, vo);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "��ѯ��Ƶ�б����");
            
			// д������־
			actionResult = false;
			actionDesc = "����������Ƶ��������Ʒ����Ƶ��ѯ����";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        request.setAttribute("perType", forward);
        request.setAttribute("PageResult", page);
        request.setAttribute("programName", programName);
        request.setAttribute("programId", programId);
        request.setAttribute("queryNodeId", queryNodeId);
        request.setAttribute("videoId", videoId);

		// д������־
		actionDesc = "����������Ƶ��������Ʒ����Ƶ��ѯ�ɹ�";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        return mapping.findForward(forward);
    }

    /**
     * �������ָ������Ƶ��������
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward add(ActionMapping mapping, ActionForm form,
                             HttpServletRequest request,
                             HttpServletResponse response) throws BOException
    {
        String forward = "add";

        // �������л�ȡ��������
        String categoryId = this.getParameter(request, "categoryId").trim();
        String addVideoId = this.getParameter(request, "addVideoId").trim();
        
        String actionType = "������Ƶ��������Ʒ";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = categoryId;
		

        if (LOG.isDebugEnabled())
        {
            LOG.debug("�������л�ȡ�������룺" + categoryId);
        }

        try
        {
            // �鿴ԭ�����Ƿ�����û���Ҫ������������Ϣ
            String ret = VideoReferenceBO.getInstance()
                                         .isHasVideoRefs(categoryId, addVideoId);
            if (!"".equals(ret))
            {
                this.saveMessagesValue(request, "ԭ���ܴ����û���ѡ������Ƶ��" + ret);
                
    			// д������־
    			actionResult = false;
    			actionDesc = "������Ƶ��������Ʒ����!��Ʒ�Ѵ���";
    	        this.actionLog(request,
    	                       actionType,
    	                       actionTarget,
    	                       actionResult,
    	                       actionDesc);
    	        
                return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
            }
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "�鿴ԭ�����Ƿ�����û���Ҫ����ʱ����");
            
            // д������־
			actionResult = false;
			actionDesc = "������Ƶ��������Ʒ����!�鿴ԭ�����Ƿ�����û���Ҫ����ʱ����!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        try
        {
            // ����ָ����Ƶ��ָ��������
            VideoReferenceBO.getInstance().addVideoRefs(categoryId, addVideoId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "�������ָ������Ƶ������ʱ����");
            
            // д������־
			actionResult = false;
			actionDesc = "������Ƶ��������Ʒ����!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        this.saveMessagesValue(request, "�����Ƶ�ɹ�");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "queryReference.do?perType=query&categoryId="
                                             + categoryId);
        
		// д������־
		actionDesc = "������Ƶ��������Ʒ�ɹ�";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        return mapping.findForward(forward);
    }

    /**
     * �ļ�����������Ƶ��Ʒ�ϼ�
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward importData(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request,
                                    HttpServletResponse response)
                    throws BOException
    {
        String forward = "importData";
        String ret = "";

        if (LOG.isDebugEnabled())
        {
            LOG.debug("action����ʼ");
        }

        // �������л�ȡ��������
        String categoryId = this.getParameter(request, "categoryId").trim();
        String addType = this.getParameter(request, "addType").trim();
        DataImportForm iForm = ( DataImportForm ) form;
        FormFile dataFile = iForm.getDataFile();
        
        String actionType = "������Ƶ��Ʒ��������";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = categoryId;
		
////      У���ļ���׺��     //removed by aiyan 2012-12-24 ��Щ������ҳ��JS��Ū�ġ�
//        if(!iForm.checkFileNameExtension(new String[]{"xls"}))
//        {
//            this.saveMessages(request, "�ļ���׺������");
//            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
//        }

        try
        {
        	if("ADD".equals(addType)){
        		ret =VideoReferenceBO.getInstance().importContentADD(dataFile, categoryId);
        	}else if("ALL".equals(addType)){
        		ret = VideoReferenceBO.getInstance().importContentALL(dataFile, categoryId);
        	}
        	VideoReferenceBO.getInstance().approvalCategoryGoods(categoryId);
            
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "�����ļ�����������Ƶ��Ʒ�ϼܳ���");
            
            // д������־
			actionResult = false;
			actionDesc = "������Ƶ��Ʒ�������³���!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        this.saveMessagesValue(request, "�ļ�����������Ƶ��Ʒ�ϼܲ����ɹ���" + ret);
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "queryReference.do?perType=query&categoryId="
                                             + categoryId);
        
		// д������־
		actionDesc = "������Ƶ��Ʒ�������³ɹ�";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        return mapping.findForward(forward);
    }

    /**
     * ���ڲ�ѯ��Ƶ����
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward showVideo(ActionMapping mapping, ActionForm form,
                                  HttpServletRequest request,
                                  HttpServletResponse response)
                    throws BOException
    {
        String forward = "showVideo";

        // �������л�ȡ��������
        String videoId = this.getParameter(request, "videoId").trim();
        ProgramVO vo = null;
        List keyBaseList = null;
        
        String actionType = "�鿴��Ƶ��Ʒ����";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = videoId;
		
        try
        {
            // ��ѯ��Ƶ����
            vo = VideoReferenceBO.getInstance().queryVideoInfo(videoId);
            keyBaseList = VideoReferenceBO.getInstance().queryVideoKeyResource(videoId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "��ѯ��Ƶ�������");
            
            // д������־
			actionResult = false;
			actionDesc = "�鿴��Ƶ��Ʒ�������!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        request.setAttribute("vo", vo);
        request.setAttribute("keyBaseList", keyBaseList);
        request.setAttribute("videoId", videoId);
        
		// д������־
		actionDesc = "�鿴��Ƶ��Ʒ����ɹ�";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        return mapping.findForward(forward);
    }
    
    /**
     * ���ڱ���ҕ�l��չ����
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward saveVideo(ActionMapping mapping, ActionForm form,
                             HttpServletRequest request,
                             HttpServletResponse response) throws BOException
    {
        String forward = "saveVideo";

        // �������л�ȡ��������
     
        String videoId = this.getParameter(request, "videoId").trim();
        FileForm fileForm = (FileForm) form;
        
        String actionType = "�޸���Ƶ��Ʒ����";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = videoId;
		
		
        // У���ļ���׺��
        if(!fileForm.checkFileNameExtension(new String[]{"png"}))
        {
            this.saveMessages(request, "�ļ���׺������");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        
        ProgramVO vo = null;
        List keyBaseList = null;
        try
        {
            // ��ѯ��Ƶ����
            vo = VideoReferenceBO.getInstance().queryVideoInfo(videoId);
        	keyBaseList = VideoReferenceBO.getInstance().queryVideoKeyResource(videoId);
        	
        	if (keyBaseList != null && keyBaseList.size() > 0)
    		{
    			this.saveVideoKeyResource(keyBaseList,fileForm,videoId,request);
    			
    		}
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "������Ƶ��չ��Ϣ����");
            
            // д������־
			actionResult = false;
			actionDesc = "�޸���Ƶ��Ʒ�������!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        } 
        this.saveMessages(request, "������Ƶ��չ����ֵ�ɹ�!");

        
        forward = Constants.FORWARD_COMMON_SUCCESS;  
        request.setAttribute("vo", vo);
        request.setAttribute("keyBaseList", keyBaseList);
        request.setAttribute("videoId", videoId);
        
		// д������־
		actionDesc = "�޸���Ƶ��Ʒ����ɹ�";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        return mapping.findForward(forward);
    }
    
    /**
     * 
     *@desc ������չ�ֶ�
     *@author dongke
     *Aug 8, 2011
     * @throws BOException 
     */
    public void saveVideoKeyResource(List keyBaseList,FileForm fileForm,String cid,HttpServletRequest request) throws BOException{

    	//��Դ��������ģ��·��
    	String resServerPath = ConfigFactory.getSystemConfig()
		.getModuleConfig("BaseVideoFileConfig").getItemValue("resServerPath");  
    	List delResourcelist = new ArrayList();
    	this.saveKeyResource(keyBaseList,delResourcelist,fileForm,cid,request,resServerPath,"video");  
    	
    	NewMusicRefBO.getInstance().saveKeyResource(keyBaseList);	
    	KeyBaseBO.getInstance().delKeyResourceListValue(delResourcelist);
    }
}