package com.aspire.dotcard.baseVideo.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseVideo.bo.BlackBO;
import com.aspire.dotcard.baseVideo.bo.CollectionBO;
import com.aspire.dotcard.baseVideo.vo.CollectionVO;
import com.aspire.dotcard.baseVideo.vo.ProgramBlackVO;
import com.aspire.dotcard.baseVideoNew.sync.UpdateIsshowFieldTask;
import com.aspire.dotcard.basemusic.bo.BaseMusicPicBO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.repository.web.DataImportForm;


/**
 * ��Ƶ���ݼ����룬��������ѯ����
 */
public class CollectionAction extends BaseAction
{

    private static final JLogger LOG = LoggerFactory.getLogger(BlackAction.class);

    /**
     * ACTION
     */
    
    public ActionForward doPerform(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BOException
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("����doPerform()����");
        }

        // �������л�ȡ��������
        String method = this.getParameter(request, "method");
        String perType=this.getParameter(request, "perType");
        boolean flag=false;
        if("export".equals(perType)){
        	flag=false;
        }else{
        	flag=true;
        }
        if ("export".equals(method))
        {
            return doExport(mapping, form, request, response, flag);
        }  
        if ("query".equals(method))
        {
            return query(mapping, form, request, response);
        }  
        else if ("importData".equals(method))
        {
            return importData(mapping, form, request, response);
            
        }else if("update".equals(method)){
        	
        	return updateData(mapping, form, request, response);
        }
        else
        {
            this.saveMessagesValue(request, "�Բ��������ʵ�·��������");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
    }

    /**
     * ��ѯ��Ƶ�������б���Ϣ
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward query(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BOException
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("���� query() ����");
        }
        String actionType = "��ѯ��Ƶ������Ϣ";
        String actionDesc = "��ѯ��Ƶ���ݼ���Ϣ�ɹ�!";
        String actionTarget = "BlackAction.query()";
        boolean actionResult = true;


         String collectionId= request.getParameter("collectionId") == null ? "" : request.getParameter("collectionId");
         String parentNodeId= request.getParameter("parentNodeId") == null ? "" : request.getParameter("parentNodeId");
         String nodeName= request.getParameter("nodeName") == null ? "" : request.getParameter("nodeName");

        PageResult page = new PageResult(request);
        CollectionVO vo = new CollectionVO();
        vo.setCollectionId(collectionId);
        vo.setNodeName(nodeName);
        vo.setParentNodeId(parentNodeId);
        
        try
        {
        	CollectionBO.getInstance().queryCollectionList(page, vo);
        }
        catch (BOException e)
        {
            actionResult = false;
            actionDesc = "��ѯ��Ƶ�����ڵ����";
            e.printStackTrace();
            LOG.error(actionDesc, e);
            // д������־
            this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
            this.saveMessagesValue(request, actionDesc);
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        if (LOG.isDebugEnabled())
        {
            LOG.debug(actionDesc);
        }
        request.setAttribute("PageResult", page);
        request.setAttribute("collectionId", collectionId);
        request.setAttribute("parentNodeId", parentNodeId);
        request.setAttribute("nodeName", nodeName);
        // д������־
        this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);

        if (LOG.isDebugEnabled())
        {
            LOG.debug("�뿪 query() ����");
        }
        return mapping.findForward(Constants.FORWARD_QUERY_TOKEN);
    }

    /**
     * ��Ƶ��������ϢEXCEL����
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    @SuppressWarnings("unchecked")
    public ActionForward importData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BOException
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("���� importData() ����");
        }
        String actionType = "������Ƶ�����ڵ���Ϣ";
        String actionDesc = "������Ƶ�����ڵ�ɹ�!";
        String actionTarget = "CollectionAction.importData()";
        DataImportForm iForm = (DataImportForm)form;
        String addType = this.getParameter(request, "addType");
        boolean actionResult = true;

        if (StringUtils.isBlank(addType))
        {
            actionResult = false;
            actionDesc = "����ʧ�ܣ�������������IDΪ��!";
            LOG.warn(actionDesc);
            // д������־
            this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
            this.saveMessagesValue(request, actionDesc);
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        // У���ļ���׺��
        if (!iForm.checkFileNameExtension(new String[] { "xls","xlsx"}))
        {
            actionResult = false;
            actionDesc = "�ļ���׺������!";
            LOG.warn(actionDesc);
            // д������־
            this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
            this.saveMessagesValue(request, actionDesc);
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        FormFile dataFile = iForm.getDataFile();

        if (dataFile == null)
        {
            actionResult = false;
            actionDesc = "�������Ϊ��!";
            LOG.warn(actionDesc);
            // д������־
            this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
            this.saveMessagesValue(request, actionDesc);
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        if (LOG.isDebugEnabled())
        {
            LOG.debug("�����ļ����ƣ�" + dataFile.getFileName() + ";�ļ���С��" + dataFile.getFileSize());
        }
        try
        {
        	List arrList = CollectionBO.getInstance().getImportCollectionParaseDataList(dataFile);

            if ("ADD".equals(addType))
            {
                actionDesc = CollectionBO.getInstance().importCollectionADD(arrList);
            }
        }
        catch (BOException e)
        {
            actionResult = false;
            actionDesc = "������Ƶ�����ڵ����!";
            LOG.error(actionDesc, e);
            // д������־
            this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
            this.saveMessagesValue(request, actionDesc);

            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        if (LOG.isDebugEnabled())
        {
            LOG.debug(actionDesc);
        }
        request.setAttribute(Constants.PARA_GOURL, "collection.do?method=query");

        // д������־
        this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
        this.saveMessagesValue(request, actionDesc);

         if (LOG.isDebugEnabled())
        {
            LOG.debug("�뿪 importData() ����");
        }
        return mapping.findForward(Constants.FORWARD_COMMON_SUCCESS);
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
		
		String actionType = "������Ƶ���ݼ�";
		boolean actionResult = true;
		String actionDesc = "������Ƶ���ݼ��ɹ�";
		String actionTarget = "";
		
		OutputStream os = null;
		WritableWorkbook wwb = null;
		String excelName = "vo_collection_" + System.currentTimeMillis()
				+ ".xls";
		try
		{
			os = new FileOutputStream(excelName);
			wwb = Workbook.createWorkbook(os);
			CollectionBO.getInstance()
					.exportCollectionData(request, wwb, isAll);
		}
		catch (Exception e)
		{
			log.error(e);
			
			// д������־
			actionResult = false;
			actionDesc = "������Ƶ���ݼ�����";
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
     * ��������
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    @SuppressWarnings("unchecked")
    public ActionForward updateData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BOException
    {
    	 if (LOG.isDebugEnabled())
         {
             LOG.debug("���� updateData() ����");
         }
         String actionType = "������Ƶ�����ڵ���Ϣ";
         String actionDesc = "������Ƶ�����ڵ���Ϣ�ɹ�!";
         String actionTarget = "CollectionAction.importData()";
         boolean actionResult = true;
         
         UpdateIsshowFieldTask isShowTask= new UpdateIsshowFieldTask();
         try {
			isShowTask.updateIsshowField();
		} catch (DAOException e) {
			LOG.error("��������ָ�����ݼ�(isShow�ֶ�)Ϊ��ʱ�����쳣��", e);
		}
         
         if (LOG.isDebugEnabled())
         {
             LOG.debug(actionDesc);
         }
         
         // д������־
         this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
         this.saveMessagesValue(request, actionDesc);
         
         if (LOG.isDebugEnabled())
         {
             LOG.debug("�뿪 updateData() ����");
         }
    	return mapping.findForward(Constants.FORWARD_COMMON_SUCCESS);
    }
}

