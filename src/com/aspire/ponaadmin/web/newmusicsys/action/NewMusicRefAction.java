/*
 * �ļ�����NewMusicCategoryAction.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
 */

package com.aspire.ponaadmin.web.newmusicsys.action;

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
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.datafield.bo.KeyBaseBO;
import com.aspire.ponaadmin.web.newmusicsys.bo.CategoryApprovalBO;
import com.aspire.ponaadmin.web.newmusicsys.bo.NewMusicCategoryBO;
import com.aspire.ponaadmin.web.newmusicsys.bo.NewMusicRefBO;
import com.aspire.ponaadmin.web.newmusicsys.vo.NewMusicCategoryVO;
import com.aspire.ponaadmin.web.newmusicsys.vo.NewMusicRefVO;
import com.aspire.ponaadmin.web.repository.web.DataImportForm;

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
public class NewMusicRefAction extends BaseAction
{

    /**
     * ��־����
     */
    private static final JLogger LOG = LoggerFactory.getLogger(NewMusicRefAction.class);

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

        if ("query".equals(perType))
        {
            return query(mapping, form, request, response);
        }
        else if ("setSort".equals(perType))
        {
            return setSort(mapping, form, request, response);
        }
        else if ("queryMusic".equals(perType))
        {
            return queryMusic(mapping, form, request, response);
        }
        else if ("remove".equals(perType))
        {
            return remove(mapping, form, request, response);
        }
        else if ("add".equals(perType))
        {
            return add(mapping, form, request, response);
        }
        else if ("importData".equals(perType))
        {
            return importData(mapping, form, request, response);
        }else if ("showMusic".equals(perType))
        {
            return showMusic(mapping, form, request, response);
        }else if ("saveMusic".equals(perType))
        {
            return saveMusic(mapping, form, request, response);
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
     * �õ�������ָ�������µ���Ʒ�б�
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
        NewMusicRefVO vo = new NewMusicRefVO();
        PageResult page = new PageResult(request);

        // �������л�ȡ��������
        String categoryId = this.getParameter(request, "categoryId").trim();
        String musicId = this.getParameter(request, "musicId").trim();
        String musicName = this.getParameter(request, "musicName").trim();
        String singer = this.getParameter(request, "singer").trim();
        String showCreateTime = this.getParameter(request, "showCreateTime").trim();
        String approvalStatus = this.getParameter(request, "approvalStatus");

        String actionType = "��ѯ���ֻ�������Ʒ��Ϣ";
		boolean actionResult = true;
		String actionDesc = "��ѯ���ֻ�������Ʒ��Ϣ�ɹ�";
		String actionTarget = categoryId;
		
        vo.setCategoryId(categoryId);
        vo.setMusicId(musicId);
        vo.setMusicName(musicName);
        vo.setSinger(singer);
        vo.setShowCreateTime(showCreateTime);
        vo.setVerify_status(approvalStatus);

        if (LOG.isDebugEnabled())
        {
            LOG.debug("�������л�ȡ�������룺" + categoryId);
        }
        
        Map<String,Object> map = new HashMap<String,Object>();

        try
        {
        	map = CategoryApprovalBO.getInstance().queryCategoryListItem(categoryId);
            NewMusicRefBO.getInstance().queryNewMusicRefList(page, vo);
        }
        catch (BOException e)
        {
            LOG.error(e);
            
            // д������־
			actionResult = false;
			actionDesc = "��ѯ���ֻ�������Ʒ��Ϣ����";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            this.saveMessagesValue(request, "��ѯ������ָ�������µ���Ʒ�б����");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        // д������־
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        request.setAttribute("perType", forward);
        request.setAttribute("PageResult", page);
        request.setAttribute("categoryId", categoryId);
        request.setAttribute("musicId", musicId);
        request.setAttribute("musicName", musicName);
        request.setAttribute("singer", singer);
        request.setAttribute("showCreateTime", showCreateTime);
        request.setAttribute("categoryContent", map);
        request.setAttribute("approvalStatus", approvalStatus);
        return mapping.findForward(forward);
    }

    /**
     * �����Ƴ�ָ��������ָ����������
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
        String[] musicId = request.getParameterValues("dealRef");

        String actionType = "ɾ�����ֻ�������Ʒ";
		boolean actionResult = true;
		String actionDesc = "ɾ�����ֻ�������Ʒ�ɹ�";
		String actionTarget = categoryId;
		
        if (LOG.isDebugEnabled())
        {
            LOG.debug("�������л�ȡ�������룺" + categoryId);
        }

        try
        {
            NewMusicRefBO.getInstance().removeNewMusicRefs(categoryId, musicId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            
            // д������־
			actionResult = false;
			actionDesc = "ɾ�����ֻ�������Ʒ����";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            this.saveMessagesValue(request, "�����Ƴ�ָ��������ָ���������ֳ���");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        
        // д������־
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);

        this.saveMessagesValue(request, "ɾ�������ֳɹ�");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "queryReference.do?perType=query&categoryId="
                             + categoryId);
        return mapping.findForward(forward);
    }

    /**
     * �������ָ������������������
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
        String addMusicId = this.getParameter(request, "addMusicId").trim();

        String actionType = "�������ֻ�������Ʒ";
		boolean actionResult = true;
		String actionDesc = "�������ֻ�������Ʒ�ɹ�";
		String actionTarget = categoryId;
		
        if (LOG.isDebugEnabled())
        {
            LOG.debug("�������л�ȡ�������룺" + categoryId);
        }

        try
        {
            // �鿴ԭ�����Ƿ�����û���Ҫ������������Ϣ
            String ret = NewMusicRefBO.getInstance().isHasMusicRefs(categoryId,
                                                                    addMusicId);
            if (!"".equals(ret))
            {
                this.saveMessagesValue(request, "ԭ���ܴ����û���ѡ�������֣�" + ret);
                return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
            }
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "�鿴ԭ�����Ƿ�����û���Ҫ����ʱ����");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        try
        {
            // ����ָ��������ָ��������
            NewMusicRefBO.getInstance().addNewMusicRefs(categoryId, addMusicId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            
            // д������־
			actionResult = false;
			actionDesc = "�������ֻ�������Ʒ����";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            this.saveMessagesValue(request, "�������ָ����������������ʱ����");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        
        // д������־
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);

        this.saveMessagesValue(request, "��������ֳɹ�");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "queryReference.do?perType=query&categoryId="
                             + categoryId);
        return mapping.findForward(forward);
    }

    /**
     * ���ڲ�ѯ�������б�
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward queryMusic(ActionMapping mapping, ActionForm form,
                             HttpServletRequest request,
                             HttpServletResponse response) throws BOException
    {
        String forward = "queryMusic";

        // �������л�ȡ��������
        String isFirst = this.getParameter(request, "isFirst").trim();
        String musicName = this.getParameter(request, "musicName").trim();
        String musicId = this.getParameter(request, "musicId").trim();
        String singer = this.getParameter(request, "singer").trim();
        String categoryId = this.getParameter(request, "categoryId").trim();
        NewMusicRefVO vo = new NewMusicRefVO();
        PageResult page = new PageResult(request, 50);
        NewMusicCategoryVO newMusicCategoryVO;
        // ����ǵ�һ�Ρ�����
        if("1".equals(isFirst))
        {
            request.setAttribute("isFirst", isFirst);
            request.setAttribute("perType", forward);
            request.setAttribute("PageResult", page);
            request.setAttribute("musicName", musicName);
            request.setAttribute("musicId", musicId);
            request.setAttribute("singer", singer);
            
            return mapping.findForward(forward);
        }
        if(categoryId != null){
        	
//        	 ��ѯ������Ϣ
            newMusicCategoryVO = NewMusicCategoryBO.getInstance()
                                                   .queryNewMusicCategoryVO(categoryId);
            if(newMusicCategoryVO != null && newMusicCategoryVO.getCateType()!= null &&newMusicCategoryVO.getCateType().equals("1")){
            	//�������
            	vo.setColorType(new Integer(1));
            }
            
        }
        vo.setMusicId(musicId);
        vo.setMusicName(musicName);
        vo.setSinger(singer);
        
        if (LOG.isDebugEnabled())
        {
            LOG.debug("�������л�ȡ����������musicName=" + musicName + ", musicId="
                      + musicId + ", singer=" + singer);
        }

        try
        {
            // ��ѯ�������б������ϼ����»�����
            NewMusicRefBO.getInstance().queryNewMusicList(page, vo);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "��ѯ�������б����");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        request.setAttribute("perType", forward);
        request.setAttribute("PageResult", page);
        request.setAttribute("musicName", musicName);
        request.setAttribute("musicId", musicId);
        request.setAttribute("singer", singer);
        
        return mapping.findForward(forward);
    }
    
    
    
    /**
     * ���ڲ�ѯ�������б�
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward showMusic(ActionMapping mapping, ActionForm form,
                             HttpServletRequest request,
                             HttpServletResponse response) throws BOException
    {
        String forward = "showMusic";

        // �������л�ȡ��������
     
        String musicId = this.getParameter(request, "musicId").trim();
       
        String actionType = "�鿴���ֻ�������Ʒ����";
		boolean actionResult = true;
		String actionDesc = "�鿴���ֻ�������Ʒ����ɹ�";
		String actionTarget = musicId;
		
		
        NewMusicRefVO vo = null;
        List keyBaseList = null;
        try
        {
            // ��ѯ����������
        	vo =  NewMusicRefBO.getInstance().queryNewMusicInfo(musicId);
        	keyBaseList = NewMusicRefBO.getInstance().queryNewMusicKeyResource(musicId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            
            // д������־
			actionResult = false;
			actionDesc = "�鿴���ֻ�������Ʒ�������";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            this.saveMessagesValue(request, "��ѯ�������������");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }    
        
        // д������־
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        request.setAttribute("vo", vo);
        request.setAttribute("keyBaseList", keyBaseList);
        request.setAttribute("musicId", musicId);
        return mapping.findForward(forward);
    }
    
    /**
     * ���ڱ�����������չ����
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward saveMusic(ActionMapping mapping, ActionForm form,
                             HttpServletRequest request,
                             HttpServletResponse response) throws BOException
    {
        String forward = "saveMusic";

        // �������л�ȡ��������
     
        String musicId = this.getParameter(request, "musicId").trim();
        
        String actionType = "�޸����ֻ�������Ʒ����";
		boolean actionResult = true;
		String actionDesc = "�޸����ֻ�������Ʒ����ɹ�";
		String actionTarget = musicId;
		
        FileForm fileForm = (FileForm) form;
        NewMusicRefVO vo = null;
        List keyBaseList = null;
        try
        {
            // ��ѯ����������
        	vo =  NewMusicRefBO.getInstance().queryNewMusicInfo(musicId);
        	keyBaseList = NewMusicRefBO.getInstance().queryNewMusicKeyResource(musicId);
        	
        	if (keyBaseList != null)
    		{
    			this.saveNewMusicKeyResource(keyBaseList,fileForm,musicId,request);
    			
    		}
        }
        catch (BOException e)
        {
            LOG.error(e);
			
            // д������־
			actionResult = false;
			actionDesc = "�޸����ֻ�������Ʒ�������";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            this.saveMessagesValue(request, "������������չ��Ϣ����");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        } 
        
        // д������־
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessages(request, "����������չ����ֵ�ɹ�!");

        forward = Constants.FORWARD_COMMON_SUCCESS;  
        request.setAttribute("vo", vo);
        request.setAttribute("keyBaseList", keyBaseList);
        request.setAttribute("musicId", musicId);
        return mapping.findForward(forward);
    }
    /**
     * �������������ֻ�����������Ʒ����ֵ
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
                             HttpServletResponse response) throws BOException
    {
        String forward = "add";

        // �������л�ȡ��������
        String categoryId = this.getParameter(request, "categoryId").trim();
        String setSortId = this.getParameter(request, "setSortId").trim();

        String actionType = "�޸����ֻ�������Ʒ�����";
		boolean actionResult = true;
		String actionDesc = "�޸����ֻ�������Ʒ����ųɹ�";
		String actionTarget = categoryId;
		
        if (LOG.isDebugEnabled())
        {
            LOG.debug("�������л�ȡ�������룺" + categoryId);
        }

        try
        {
            // ����ָ��������ָ��������
            NewMusicRefBO.getInstance().setNewMusicSort(categoryId, setSortId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            
            // д������־
			actionResult = false;
			actionDesc = "�޸����ֻ�������Ʒ����ų���";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            this.saveMessagesValue(request, "�������������ֻ�����������Ʒ����ֵʱ����");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        // д������־
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "����������Ʒ����ֵ�ɹ�");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "queryReference.do?perType=query&categoryId="
                             + categoryId);
        return mapping.findForward(forward);
    }
    
    /**
     * �ļ���������������Ʒ�ϼ�
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
        
        String actionType = "����������ֻ�����Ʒ";
		boolean actionResult = true;
		String actionDesc = "����������ֻ�����Ʒ�ɹ�";
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
        		ret =NewMusicRefBO.getInstance().importContentADD(dataFile, categoryId);
        	}else if("ALL".equals(addType)){
        		ret = NewMusicRefBO.getInstance().importContentALL(dataFile, categoryId);
        	}
            
        }
//        
//        try
//        {
//            // ����ָ��������ָ��������
//            ret = NewMusicRefBO.getInstance().importContentExigence(dataFile, categoryId);
//        }
        catch (BOException e)
        {
            LOG.error(e);
            
            // д������־
			actionResult = false;
			actionDesc = "����������ֻ�����Ʒ����";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            this.saveMessagesValue(request, "�����ļ���������������Ʒ�ϼܳ���");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        // д������־
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "�ļ���������������Ʒ�ϼܲ����ɹ���" + ret);
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "queryReference.do?perType=query&categoryId="
                             + categoryId);
        return mapping.findForward(forward);
    }
    
    /**
     * 
     *@desc ������չ�ֶ�
     *@author dongke
     *Aug 8, 2011
     * @throws BOException 
     */
    public void saveNewMusicKeyResource(List keyBaseList,FileForm fileForm,String cid,HttpServletRequest request) throws BOException{

    	//��Դ��������ģ��·��
    	String resServerPath = ConfigFactory.getSystemConfig()
		.getModuleConfig("basemusic").getItemValue("resServerPath");  	
    	List delResourcelist = new ArrayList();
    	this.saveKeyResource(keyBaseList,delResourcelist,fileForm,cid,request,resServerPath,"newmusic"); 
    	
    	NewMusicRefBO.getInstance().saveKeyResource(keyBaseList);	
    	KeyBaseBO.getInstance().delKeyResourceListValue(delResourcelist);
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
		
		String actionType = "���������б���Ϣ";
		boolean actionResult = true;
		String actionDesc = "���������б���Ϣ�ɹ�";
		String actionTarget = "";

		OutputStream os = null;
		WritableWorkbook wwb = null;
		String excelName = "query_music_"
				+ System.currentTimeMillis() + ".xls";
		try
		{
			os = new FileOutputStream(excelName);
			wwb = Workbook.createWorkbook(os);
			NewMusicRefBO.getInstance().exportQueryData(request, wwb);
		}
		catch (Exception e)
		{
			log.error(e);
			
			// д������־
			actionResult = false;
			actionDesc = "���������б���Ϣ����";
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
