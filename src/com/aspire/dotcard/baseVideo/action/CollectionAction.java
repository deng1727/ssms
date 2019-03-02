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
 * 视频内容集导入，导出，查询操作
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
            LOG.debug("进入doPerform()方法");
        }

        // 从请求中获取操作类型
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
            this.saveMessagesValue(request, "对不起，您访问的路径不存在");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
    }

    /**
     * 查询视频黑名单列表信息
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
            LOG.debug("进入 query() 方法");
        }
        String actionType = "查询视频内容信息";
        String actionDesc = "查询视频内容集信息成功!";
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
            actionDesc = "查询视频三级节点出错";
            e.printStackTrace();
            LOG.error(actionDesc, e);
            // 写操作日志
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
        // 写操作日志
        this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);

        if (LOG.isDebugEnabled())
        {
            LOG.debug("离开 query() 方法");
        }
        return mapping.findForward(Constants.FORWARD_QUERY_TOKEN);
    }

    /**
     * 视频黑名单信息EXCEL导入
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
            LOG.debug("进入 importData() 方法");
        }
        String actionType = "导入视频三级节点信息";
        String actionDesc = "导入视频三级节点成功!";
        String actionTarget = "CollectionAction.importData()";
        DataImportForm iForm = (DataImportForm)form;
        String addType = this.getParameter(request, "addType");
        boolean actionResult = true;

        if (StringUtils.isBlank(addType))
        {
            actionResult = false;
            actionDesc = "导入失败，参数错误，内容ID为空!";
            LOG.warn(actionDesc);
            // 写操作日志
            this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
            this.saveMessagesValue(request, actionDesc);
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        // 校验文件后缀名
        if (!iForm.checkFileNameExtension(new String[] { "xls","xlsx"}))
        {
            actionResult = false;
            actionDesc = "文件后缀名出错!";
            LOG.warn(actionDesc);
            // 写操作日志
            this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
            this.saveMessagesValue(request, actionDesc);
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        FormFile dataFile = iForm.getDataFile();

        if (dataFile == null)
        {
            actionResult = false;
            actionDesc = "导入对象为空!";
            LOG.warn(actionDesc);
            // 写操作日志
            this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
            this.saveMessagesValue(request, actionDesc);
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        if (LOG.isDebugEnabled())
        {
            LOG.debug("导入文件名称：" + dataFile.getFileName() + ";文件大小：" + dataFile.getFileSize());
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
            actionDesc = "导入视频三级节点出错!";
            LOG.error(actionDesc, e);
            // 写操作日志
            this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
            this.saveMessagesValue(request, actionDesc);

            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        if (LOG.isDebugEnabled())
        {
            LOG.debug(actionDesc);
        }
        request.setAttribute(Constants.PARA_GOURL, "collection.do?method=query");

        // 写操作日志
        this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
        this.saveMessagesValue(request, actionDesc);

         if (LOG.isDebugEnabled())
        {
            LOG.debug("离开 importData() 方法");
        }
        return mapping.findForward(Constants.FORWARD_COMMON_SUCCESS);
    }
    /**
     * 导出数据
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
			log.debug("excel导出.......");
		}
		
		String actionType = "导出视频内容集";
		boolean actionResult = true;
		String actionDesc = "导出视频内容集成功";
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
			
			// 写操作日志
			actionResult = false;
			actionDesc = "导出视频内容集出错";
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
		
		// 写操作日志
		this.actionLog(request, actionType, actionTarget, actionResult,
				actionDesc);
		
		return null;
	}
    
    /**
     * 更新数据
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
             LOG.debug("进入 updateData() 方法");
         }
         String actionType = "更新视频三级节点信息";
         String actionDesc = "更新视频三级节点信息成功!";
         String actionTarget = "CollectionAction.importData()";
         boolean actionResult = true;
         
         UpdateIsshowFieldTask isShowTask= new UpdateIsshowFieldTask();
         try {
			isShowTask.updateIsshowField();
		} catch (DAOException e) {
			LOG.error("批量更新指定内容集(isShow字段)为是时发生异常！", e);
		}
         
         if (LOG.isDebugEnabled())
         {
             LOG.debug(actionDesc);
         }
         
         // 写操作日志
         this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
         this.saveMessagesValue(request, actionDesc);
         
         if (LOG.isDebugEnabled())
         {
             LOG.debug("离开 updateData() 方法");
         }
    	return mapping.findForward(Constants.FORWARD_COMMON_SUCCESS);
    }
}

