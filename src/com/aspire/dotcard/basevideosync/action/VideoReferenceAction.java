package com.aspire.dotcard.basevideosync.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.WritableWorkbook;
import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basevideosync.bo.CategoryApprovalBO;
import com.aspire.dotcard.basevideosync.bo.NewVideoRefBO;
import com.aspire.dotcard.basevideosync.bo.VideoBO;
import com.aspire.dotcard.basevideosync.vo.ProgramVO;
import com.aspire.dotcard.basevideosync.vo.VideoReferenceVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.repository.web.DataImportForm;

public class VideoReferenceAction extends BaseAction{

	/**
	 * ��־����
	 */
	private static final JLogger LOG = LoggerFactory
			.getLogger(VideoReferenceAction.class);
	//�ȵ����ݵĸ�����
	private String hotContentPCategoryId="105";
	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		LOG.debug("doPerform()");

        // �������л�ȡ��������
        String perType = this.getParameter(request, "perType").trim();
        if ("query".equals(perType))
        {
            return query(mapping, form, request, response);
        }
        else if ("remove".equals(perType))
        {
            return remove(mapping, form, request, response);
        }
        else if ("queryVideo".equals(perType))
        {
            return queryVideo(mapping, form, request, response);
        }
        else if ("setSort".equals(perType))
        {
            return setSort(mapping, form, request, response);
        }
        else if ("queryProgram".equals(perType))
        {
            return queryProgram(mapping, form, request, response);
        }
        else if ("add".equals(perType))
        {
            return add(mapping, form, request, response);
        }
        else if ("getTagNameList".equals(perType))
        {
            return getTagNameList(mapping, form, request, response);
        }else if ("showVideo".equals(perType))
        {
            return showVideo(mapping, form, request, response);
        }
        else if ("importData".equals(perType))
        {
            return importData(mapping, form, request, response);
        }
        else if ("exportData".equals(perType))
        {
            return exportData(mapping, form, request, response);
        }
        else
        {
            String forward = Constants.FORWARD_COMMON_FAILURE;
            this.saveMessagesValue(request, "�Բ��������ʵ�·��������");
            return mapping.findForward(forward);
        }
	}
	
	/**
     * ��ѯ��Ƶ�����µ���Ʒ�б�
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
        VideoReferenceVO vo = new VideoReferenceVO();
        PageResult page = new PageResult(request);

        // �������л�ȡ��������
        String refId = this.getParameter(request, "refId").trim();
        String categoryId = this.getParameter(request, "categoryId").trim();
        String programId = this.getParameter(request, "programId").trim();
        String programName = this.getParameter(request, "programName").trim();
        String cmsId = this.getParameter(request, "cmsId").trim();
        String subcateName = this.getParameter(request, "subcateName").trim();
        String keyName = this.getParameter(request, "keyName").trim();
        String approvalStatus = this.getParameter(request, "approvalStatus").trim();
        
        vo.setRefId(refId);
        vo.setCategoryId(categoryId);
        vo.setProgramId(programId);
        vo.setProgramName(programName);
        vo.setCmsId(cmsId);
        vo.setSubcateName(subcateName);
        vo.setKeyName(keyName);
        vo.setVerifyStatus(approvalStatus);
        
        String actionType = "��ѯ����Ƶ��������Ʒ";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = categoryId;

        if (LOG.isDebugEnabled())
        {
            LOG.debug("�������л�ȡ�������룺" + categoryId);
        }
        Map<String,Object> map = new HashMap<String,Object>();
        try
        {
//        	subcateNameList = VideoBO.getInstance().getSubcateNameList(categoryId);
//        	if(!"".equals(subcateName))
//        		tagNameList = VideoBO.getInstance().getTagNameList(categoryId, subcateName);
        	map = CategoryApprovalBO.getInstance().queryCategoryListItem(categoryId);
        	//������ȵ���ܣ����ѯ�����ȵ����ݱ������ǲ�ѯ��Ŀ�����
        	if(VideoBO.getInstance().isSubCategory(vo.getCategoryId(), hotContentPCategoryId)){
        		VideoBO.getInstance().queryHotContentReferenceList(page, vo);
        	}else{
        		 VideoBO.getInstance().queryVideoReferenceList(page, vo);
        	}
           
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "��ѯ����Ƶָ�������µ���Ʒ�б����");
            
			// д������־
			actionResult = false;
			actionDesc = "��ѯ����Ƶ��������Ʒ����";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        request.setAttribute("perType", forward);
        request.setAttribute("PageResult", page);
        request.setAttribute("categoryId", categoryId);
        request.setAttribute("programId", programId);
        request.setAttribute("programName", programName);
        request.setAttribute("cmsId", cmsId);
        request.setAttribute("subcateName", subcateName);
        request.setAttribute("keyName", keyName);
        request.setAttribute("categoryContent", map);
        request.setAttribute("approvalStatus", approvalStatus);
		// д������־
		actionDesc = "��ѯ����Ƶ��������Ʒ�ɹ�";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        return mapping.findForward(forward);
    }

    /**
     * ɾ����Ƶ�����µ���Ʒ
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
            VideoBO.getInstance().delVideoReferences(categoryId, refId);
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
                             "videoReference.do?perType=query&categoryId="
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
     * ������Ƶ��������Ƶ��Ʒ����ֵ
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
            VideoBO.getInstance().setVideoReferenceSort(categoryId, setSortId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "����������Ƶ��������Ƶ����ֵʱ����");
            
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
                             "videoReference.do?perType=query&categoryId="
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
     * ��ѯ��Ƶ��Ŀ�б�
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward queryProgram(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {
        String forward = "queryProgram";
        
        
        String actionType = "����������Ƶ��������Ʒ����Ƶ��Ŀ��ѯ";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = "";

        // �������л�ȡ��������
        String isFirst = this.getParameter(request, "isFirst").trim();
        String programName = this.getParameter(request, "programName").trim();
        String programId = this.getParameter(request, "programId").trim();
        String cmsId = this.getParameter(request, "cmsId").trim();
        ProgramVO vo = new ProgramVO();
        PageResult page = new PageResult(request);

        // ����ǵ�һ�Ρ�����
        if ("1".equals(isFirst))
        {
            request.setAttribute("isFirst", isFirst);
            request.setAttribute("PageResult", page);
            request.setAttribute("programName", programName);
            request.setAttribute("programId", programId);
            request.setAttribute("cmsId", cmsId);

            return mapping.findForward(forward);
        }

        vo.setProgramId(programId);
        vo.setName(programName);
        vo.setCMSID(cmsId);

        if (LOG.isDebugEnabled())
        {
            LOG.debug("�������л�ȡ��Ƶ������programName=" + programName + ", programId=" + programId);
        }

        try
        {
            // ��ѯ��Ƶ��Ŀ�б������ϼ����»�����
            VideoBO.getInstance().queryProgramList(page, vo);
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
        request.setAttribute("cmsId", cmsId);

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
     * �����Ƶ��Ŀ��������
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
        String addProgromId = this.getParameter(request, "addProgromId").trim();
        
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
            // �鿴ԭ�����Ƿ�����û���Ҫ��������Ʒ��Ϣ
            String ret = VideoBO.getInstance().isHasReferences(categoryId, addProgromId);
            if (!"".equals(ret))
            {
                this.saveMessagesValue(request, "ԭ���ܴ����û���ѡ������Ƶ��Ŀ��" + ret);
                
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
            // ����ָ����Ƶ��Ŀ��Ʒ��ָ��������
            VideoBO.getInstance().addVideoReferences(categoryId, addProgromId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "�������ָ������Ƶ��Ŀ������ʱ����");
            
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

        this.saveMessagesValue(request, "�����Ƶ��������Ʒ�ɹ�");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "videoReference.do?perType=query&categoryId="
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
	 * ��ӱ�ǩ�����ǩ����
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward getTagNameList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws BOException{
		if (log.isDebugEnabled())
        {
			log.debug("doAddTag in......");
        }
        String categoryId = this.getParameter(request, "categoryId");
        String subcateName = this.getParameter(request, "subcateName");
        
        List <String> tagNameList=new ArrayList<String>();
        try {
        	
        	tagNameList = VideoBO.getInstance().getTagNameList(categoryId,subcateName);
            
        } catch (Exception e) {
        	log.error("������:",e);
            this.saveMessages(request, "����ʧ��!");
        }
		JSONArray json=JSONArray.fromObject(tagNameList);
        PrintWriter writer;
		try {
			response.setContentType("text/html;charset=utf-8");
			writer = response.getWriter();
			writer.print(json);
	        writer.flush();
	        writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
       
		return null;
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
                               HttpServletResponse response) throws BOException
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
        DataImportForm iForm=(DataImportForm)form;
        FormFile dataFile = iForm.getDataFile();
        
        String actionType = "���������Ƶ������Ʒ";
		boolean actionResult = true;
		String actionDesc = "���������Ƶ������Ʒ�ɹ�";
		String actionTarget = categoryId;
		
        
        // У���ļ���׺��  //removed by aiyan 2012-12-25 �ⶫ��Ҫ��JSҳ���ϴ���
//        if(!iForm.checkFileNameExtension(new String[]{"xls"}))
//        {
//            this.saveMessages(request, "�ļ���׺������");
//            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
//        } 

        
        try
        {
        	if("ADD".equals(addType)){
        		ret =VideoBO.getInstance().importContentADD(dataFile, categoryId);
        	}else if("ALL".equals(addType)){
        		ret = VideoBO.getInstance().importContentALL(dataFile, categoryId);
        	}
            
        }
//        
//        try
//        {
//            // ����ָ����Ƶ��ָ��������
//            ret = NewMusicRefBO.getInstance().importContentExigence(dataFile, categoryId);
//        }
        catch (BOException e)
        {
            LOG.error(e);
            
            // д������־
			actionResult = false;
			actionDesc = "���������Ƶ������Ʒ����";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            this.saveMessagesValue(request, "�����ļ�����������Ƶ��Ʒ�ϼܳ���");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        // д������־
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "�ļ�����������Ƶ��Ʒ�ϼܲ����ɹ���" + ret);
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "queryReference.do?perType=query&categoryId="
                             + categoryId);
        return mapping.findForward(forward);
    }
    /**
     * ���ڲ�ѯ����Ƶ�б�
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward queryVideo(ActionMapping mapping, ActionForm form,
                             HttpServletRequest request,
                             HttpServletResponse response) throws BOException
    {
        String forward = "queryVideo";

        // �������л�ȡ��������
        String isFirst = this.getParameter(request, "isFirst").trim();
        String programName = this.getParameter(request, "programName").trim();
        String programId = this.getParameter(request, "programId").trim();
        String cmsId = this.getParameter(request, "cmsId").trim();
        String categoryId = this.getParameter(request, "categoryId").trim();
        String subcateName = this.getParameter(request, "subcateName").trim();
        String keyName = this.getParameter(request, "keyName").trim();
        VideoReferenceVO vo = new VideoReferenceVO();
        PageResult page = new PageResult(request, 50);
        // ����ǵ�һ�Ρ�����
        if("1".equals(isFirst))
        {
            request.setAttribute("isFirst", isFirst);
            request.setAttribute("perType", forward);
            request.setAttribute("PageResult", page);
            request.setAttribute("programName", programName);
            request.setAttribute("programId", programId);
            request.setAttribute("cmsId", cmsId);
            
            return mapping.findForward(forward);
        }
        /*if(categoryId != null){
        	
//        	 ��ѯ������Ϣ
        	videoCategoryVO = VideoCategoryBO.getInstance()
                                                   .queryNewMusicCategoryVO(categoryId);
            if(videoCategoryVO != null && videoCategoryVO.getCateType()!= null &&videoCategoryVO.getCateType().equals("1")){
            	//�������
            	vo.setColorType(new Integer(1));
            }
            
        }*/
        vo.setProgramId(programId);
        vo.setProgramName(programName);
        vo.setCmsId(cmsId);
        
        if (LOG.isDebugEnabled())
        {
            LOG.debug("�������л�ȡ��Ƶ������programName=" + programName + ", programId="
                      + programId + ", cmsId=" + cmsId);
        }

        try
        {
        	//������ȵ����ݻ��ܣ���Ҫ��ѯ�ȵ����ݱ�
        	if(VideoBO.getInstance().isSubCategory(vo.getCategoryId(), hotContentPCategoryId)){
        		VideoBO.getInstance().queryHotContentReferenceList(page, vo);
        	}else{
        		 // ��ѯ������Ƶ�б������ϼ����»�����
            	VideoBO.getInstance().queryVideoReferenceList(page, vo);
        	}
           
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "��ѯ������Ƶ�б����");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        request.setAttribute("perType", forward);
        request.setAttribute("PageResult", page);
        request.setAttribute("categoryId", categoryId);
        request.setAttribute("programId", programId);
        request.setAttribute("programName", programName);
        request.setAttribute("cmsId", cmsId);
        request.setAttribute("subcateName", subcateName);
        request.setAttribute("keyName", keyName);
//        request.setAttribute("categoryContent", map);
        
        return mapping.findForward(forward);
    }
    /**
	 * ���ڵ�����ǰ����
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @param isAll
	 * @return
	 * @throws BOException
	 */
	public ActionForward exportData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws BOException
	{
		if (log.isDebugEnabled())
		{
			log.debug("exportData in......");
			log.debug("excel����.......");
		}
		
		String actionType = "������Ƶ�б���Ϣ";
		boolean actionResult = true;
		String actionDesc = "������Ƶ�б���Ϣ�ɹ�";
		String actionTarget = "";

		OutputStream os = null;
		WritableWorkbook wwb = null;
		String excelName = "query_video_"
				+ System.currentTimeMillis() + ".xls";
		try
		{
			os = new FileOutputStream(excelName);
			wwb = Workbook.createWorkbook(os);
			NewVideoRefBO.getInstance().exportQueryData(request, wwb);
		}
		catch (Exception e)
		{
			log.error(e);
			
			// д������־
			actionResult = false;
			actionDesc = "������Ƶ�б���Ϣ����";
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
	 /**
     * ���ڲ�ѯ����Ƶ
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward showVideo(ActionMapping mapping, ActionForm form,
                             HttpServletRequest request,
                             HttpServletResponse response) throws BOException
    {
        String forward = "showVideo";

        // �������л�ȡ��������
     
        String videoId = this.getParameter(request, "videoId").trim();
       
        String actionType = "�鿴����Ƶ��������Ʒ����";
		boolean actionResult = true;
		String actionDesc = "�鿴����Ƶ��������Ʒ����ɹ�";
		String actionTarget = videoId;
		
		
		ProgramVO vo = null;
//        List keyBaseList = null;
        try
        {
            // ��ѯ������Ƶ����
        	vo =  NewVideoRefBO.getInstance().queryNewVideoInfo(videoId);
//        	keyBaseList = NewMusicRefBO.getInstance().queryNewMusicKeyResource(musicId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            
            // д������־
			actionResult = false;
			actionDesc = "�鿴����Ƶ��������Ʒ�������";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            this.saveMessagesValue(request, "��ѯ������Ƶ�������");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }    
        
        // д������־
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        request.setAttribute("vo", vo);
//        request.setAttribute("keyBaseList", keyBaseList);
//        request.setAttribute("musicId", musicId);
        return mapping.findForward(forward);
    }

}
