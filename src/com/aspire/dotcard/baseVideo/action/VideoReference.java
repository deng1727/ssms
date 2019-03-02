
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
     * 日志引用
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

        // 从请求中获取操作类型
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
            this.saveMessagesValue(request, "对不起，您访问的路径不存在");
            return mapping.findForward(forward);
        }
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
		
		String actionType = "导出视频商品信息";
		boolean actionResult = true;
		String actionDesc = "导出视频商品信息成功";
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
			
			// 写操作日志
			actionResult = false;
			actionDesc = "导出视频商品信息出错";
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
     * 得到视频指定货架下的商品列表
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

        // 从请求中获取货架内码
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
        
        String actionType = "查询视频货架下商品";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = categoryId;
		 Map<String,Object> map = new HashMap<String,Object>();

        if (LOG.isDebugEnabled())
        {
            LOG.debug("从请求中获取货架内码：" + categoryId);
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
            this.saveMessagesValue(request, "查询视频指定货架下的商品列表出错");
            
			// 写操作日志
			actionResult = false;
			actionDesc = "查询视频货架下商品出错";
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
        
		// 写操作日志
		actionDesc = "查询视频货架下商品成功";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        return mapping.findForward(forward);
    }

    /**
     * 用于移除指定货架下指定的视频
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

        // 从请求中获取货架内码
        String categoryId = this.getParameter(request, "categoryId").trim();
        String[] refId = request.getParameterValues("dealRef");
        
        String actionType = "删除视频货架下商品";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = categoryId;

        if (LOG.isDebugEnabled())
        {
            LOG.debug("从请求中获取货架内码：" + categoryId);
        }

        try
        {
            VideoReferenceBO.getInstance().removeVideoRefs(categoryId, refId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "用于移除指定货架下指定的视频出错");
            
			// 写操作日志
			actionResult = false;
			actionDesc = "删除视频货架下商品出错";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        this.saveMessagesValue(request, "删除视频成功");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "queryReference.do?perType=query&categoryId="
                                             + categoryId);
        
		// 写操作日志
		actionDesc = "删除视频货架下商品成功";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        return mapping.findForward(forward);
    }

    /**
     * 用于设置视频货架下视频商品排序值
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

        // 从请求中获取货架内码
        String categoryId = this.getParameter(request, "categoryId").trim();
        String setSortId = this.getParameter(request, "setSortId").trim();
        
        String actionType = "修改视频货架下商品排序";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = categoryId;
		

        if (LOG.isDebugEnabled())
        {
            LOG.debug("从请求中获取货架内码：" + categoryId);
        }

        try
        {
            // 设置视频货架下视频商品排序值
            VideoReferenceBO.getInstance().setVideoSort(categoryId, setSortId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "用于设置视频货架下音乐视频排序值时出错");
            
			// 写操作日志
			actionResult = false;
			actionDesc = "修改视频货架下商品排序出错";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
	        
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        this.saveMessagesValue(request, "设置视频商品排序值成功");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "queryReference.do?perType=query&categoryId="
                                             + categoryId);
        
		// 写操作日志
		actionDesc = "修改视频货架下商品排序成功";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        return mapping.findForward(forward);
    }

    /**
     * 用于查询视频列表
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
        
        
        String actionType = "用于新增视频货架下商品的视频查询";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = "";
		

        // 从请求中获取货架内码
        String isFirst = this.getParameter(request, "isFirst").trim();
        String programName = this.getParameter(request, "programName").trim();
        String programId = this.getParameter(request, "programId").trim();
        String queryNodeId = this.getParameter(request, "queryNodeId").trim();
        String videoId = this.getParameter(request, "videoId").trim();
        ProgramVO vo = new ProgramVO();
        PageResult page = new PageResult(request);

        // 如果是第一次。跳过
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
            LOG.debug("从请求中获取视频条件：programName=" + programName + ", programId=" + programId);
        }

        try
        {
            // 查询视频列表。用以上架至新货架上
            VideoReferenceBO.getInstance().queryProgramVOList(page, vo);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "查询视频列表出错");
            
			// 写操作日志
			actionResult = false;
			actionDesc = "用于新增视频货架下商品的视频查询出错";
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

		// 写操作日志
		actionDesc = "用于新增视频货架下商品的视频查询成功";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        return mapping.findForward(forward);
    }

    /**
     * 用于添加指定的视频至货架中
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

        // 从请求中获取货架内码
        String categoryId = this.getParameter(request, "categoryId").trim();
        String addVideoId = this.getParameter(request, "addVideoId").trim();
        
        String actionType = "新增视频货架下商品";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = categoryId;
		

        if (LOG.isDebugEnabled())
        {
            LOG.debug("从请求中获取货架内码：" + categoryId);
        }

        try
        {
            // 查看原货架是否存在用户将要新增的音乐信息
            String ret = VideoReferenceBO.getInstance()
                                         .isHasVideoRefs(categoryId, addVideoId);
            if (!"".equals(ret))
            {
                this.saveMessagesValue(request, "原货架存在用户所选以下视频：" + ret);
                
    			// 写操作日志
    			actionResult = false;
    			actionDesc = "新增视频货架下商品出错!商品已存在";
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
            this.saveMessagesValue(request, "查看原货架是否存在用户将要新增时出错");
            
            // 写操作日志
			actionResult = false;
			actionDesc = "新增视频货架下商品出错!查看原货架是否存在用户将要新增时出错!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        try
        {
            // 新增指定视频至指定货架上
            VideoReferenceBO.getInstance().addVideoRefs(categoryId, addVideoId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "用于添加指定的视频至货架时出错");
            
            // 写操作日志
			actionResult = false;
			actionDesc = "新增视频货架下商品出错!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        this.saveMessagesValue(request, "添加视频成功");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "queryReference.do?perType=query&categoryId="
                                             + categoryId);
        
		// 写操作日志
		actionDesc = "新增视频货架下商品成功";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        return mapping.findForward(forward);
    }

    /**
     * 文件批量导入视频商品上架
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
            LOG.debug("action请求开始");
        }

        // 从请求中获取货架内码
        String categoryId = this.getParameter(request, "categoryId").trim();
        String addType = this.getParameter(request, "addType").trim();
        DataImportForm iForm = ( DataImportForm ) form;
        FormFile dataFile = iForm.getDataFile();
        
        String actionType = "导入视频商品到货架下";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = categoryId;
		
////      校验文件后缀名     //removed by aiyan 2012-12-24 这些东西在页面JS上弄的。
//        if(!iForm.checkFileNameExtension(new String[]{"xls"}))
//        {
//            this.saveMessages(request, "文件后缀名出错！");
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
            this.saveMessagesValue(request, "用于文件批量导入视频商品上架出错");
            
            // 写操作日志
			actionResult = false;
			actionDesc = "导入视频商品到货架下出错!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        this.saveMessagesValue(request, "文件批量导入视频商品上架操作成功，" + ret);
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "queryReference.do?perType=query&categoryId="
                                             + categoryId);
        
		// 写操作日志
		actionDesc = "导入视频商品到货架下成功";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        return mapping.findForward(forward);
    }

    /**
     * 用于查询视频详情
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

        // 从请求中获取货架内码
        String videoId = this.getParameter(request, "videoId").trim();
        ProgramVO vo = null;
        List keyBaseList = null;
        
        String actionType = "查看视频商品详情";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = videoId;
		
        try
        {
            // 查询视频详情
            vo = VideoReferenceBO.getInstance().queryVideoInfo(videoId);
            keyBaseList = VideoReferenceBO.getInstance().queryVideoKeyResource(videoId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "查询视频详情出错");
            
            // 写操作日志
			actionResult = false;
			actionDesc = "查看视频商品详情出错!";
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
        
		// 写操作日志
		actionDesc = "查看视频商品详情成功";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        return mapping.findForward(forward);
    }
    
    /**
     * 用于保存視頻扩展属性
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

        // 从请求中获取货架内码
     
        String videoId = this.getParameter(request, "videoId").trim();
        FileForm fileForm = (FileForm) form;
        
        String actionType = "修改视频商品详情";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = videoId;
		
		
        // 校验文件后缀名
        if(!fileForm.checkFileNameExtension(new String[]{"png"}))
        {
            this.saveMessages(request, "文件后缀名出错！");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        
        ProgramVO vo = null;
        List keyBaseList = null;
        try
        {
            // 查询视频详情
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
            this.saveMessagesValue(request, "保存视频扩展信息出错");
            
            // 写操作日志
			actionResult = false;
			actionDesc = "修改视频商品详情出错!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        } 
        this.saveMessages(request, "更新视频扩展属性值成功!");

        
        forward = Constants.FORWARD_COMMON_SUCCESS;  
        request.setAttribute("vo", vo);
        request.setAttribute("keyBaseList", keyBaseList);
        request.setAttribute("videoId", videoId);
        
		// 写操作日志
		actionDesc = "修改视频商品详情成功";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        return mapping.findForward(forward);
    }
    
    /**
     * 
     *@desc 保存扩展字段
     *@author dongke
     *Aug 8, 2011
     * @throws BOException 
     */
    public void saveVideoKeyResource(List keyBaseList,FileForm fileForm,String cid,HttpServletRequest request) throws BOException{

    	//资源服务器本模块路径
    	String resServerPath = ConfigFactory.getSystemConfig()
		.getModuleConfig("BaseVideoFileConfig").getItemValue("resServerPath");  
    	List delResourcelist = new ArrayList();
    	this.saveKeyResource(keyBaseList,delResourcelist,fileForm,cid,request,resServerPath,"video");  
    	
    	NewMusicRefBO.getInstance().saveKeyResource(keyBaseList);	
    	KeyBaseBO.getInstance().delKeyResourceListValue(delResourcelist);
    }
}