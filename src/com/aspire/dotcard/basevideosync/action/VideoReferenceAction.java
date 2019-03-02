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
	 * 日志引用
	 */
	private static final JLogger LOG = LoggerFactory
			.getLogger(VideoReferenceAction.class);
	//热点内容的跟货架
	private String hotContentPCategoryId="105";
	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		LOG.debug("doPerform()");

        // 从请求中获取操作类型
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
            this.saveMessagesValue(request, "对不起，您访问的路径不存在");
            return mapping.findForward(forward);
        }
	}
	
	/**
     * 查询视频货架下的商品列表
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

        // 从请求中获取货架内码
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
        
        String actionType = "查询新视频货架下商品";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = categoryId;

        if (LOG.isDebugEnabled())
        {
            LOG.debug("从请求中获取货架内码：" + categoryId);
        }
        Map<String,Object> map = new HashMap<String,Object>();
        try
        {
//        	subcateNameList = VideoBO.getInstance().getSubcateNameList(categoryId);
//        	if(!"".equals(subcateName))
//        		tagNameList = VideoBO.getInstance().getTagNameList(categoryId, subcateName);
        	map = CategoryApprovalBO.getInstance().queryCategoryListItem(categoryId);
        	//如果是热点货架，则查询的是热点内容表，否则是查询节目详情表
        	if(VideoBO.getInstance().isSubCategory(vo.getCategoryId(), hotContentPCategoryId)){
        		VideoBO.getInstance().queryHotContentReferenceList(page, vo);
        	}else{
        		 VideoBO.getInstance().queryVideoReferenceList(page, vo);
        	}
           
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "查询新视频指定货架下的商品列表出错");
            
			// 写操作日志
			actionResult = false;
			actionDesc = "查询新视频货架下商品出错";
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
		// 写操作日志
		actionDesc = "查询新视频货架下商品成功";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        return mapping.findForward(forward);
    }

    /**
     * 删除视频货架下的商品
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
            VideoBO.getInstance().delVideoReferences(categoryId, refId);
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
                             "videoReference.do?perType=query&categoryId="
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
     * 设置视频货架下视频商品排序值
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
            VideoBO.getInstance().setVideoReferenceSort(categoryId, setSortId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "用于设置视频货架下视频排序值时出错");
            
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
                             "videoReference.do?perType=query&categoryId="
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
     * 查询视频节目列表
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
        
        
        String actionType = "用于新增视频货架下商品的视频节目查询";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = "";

        // 从请求中获取货架内码
        String isFirst = this.getParameter(request, "isFirst").trim();
        String programName = this.getParameter(request, "programName").trim();
        String programId = this.getParameter(request, "programId").trim();
        String cmsId = this.getParameter(request, "cmsId").trim();
        ProgramVO vo = new ProgramVO();
        PageResult page = new PageResult(request);

        // 如果是第一次。跳过
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
            LOG.debug("从请求中获取视频条件：programName=" + programName + ", programId=" + programId);
        }

        try
        {
            // 查询视频节目列表。用以上架至新货架上
            VideoBO.getInstance().queryProgramList(page, vo);
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
        request.setAttribute("cmsId", cmsId);

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
     * 添加视频节目至货架中
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
        String addProgromId = this.getParameter(request, "addProgromId").trim();
        
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
            // 查看原货架是否存在用户将要新增的商品信息
            String ret = VideoBO.getInstance().isHasReferences(categoryId, addProgromId);
            if (!"".equals(ret))
            {
                this.saveMessagesValue(request, "原货架存在用户所选以下视频节目：" + ret);
                
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
            // 新增指定视频节目商品至指定货架上
            VideoBO.getInstance().addVideoReferences(categoryId, addProgromId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "用于添加指定的视频节目至货架时出错");
            
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

        this.saveMessagesValue(request, "添加视频货架下商品成功");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "videoReference.do?perType=query&categoryId="
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
	 * 添加标签集与标签关联
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
        	log.error("报错了:",e);
            this.saveMessages(request, "操作失败!");
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
                               HttpServletResponse response) throws BOException
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
        DataImportForm iForm=(DataImportForm)form;
        FormFile dataFile = iForm.getDataFile();
        
        String actionType = "导入基地视频货架商品";
		boolean actionResult = true;
		String actionDesc = "导入基地视频货架商品成功";
		String actionTarget = categoryId;
		
        
        // 校验文件后缀名  //removed by aiyan 2012-12-25 这东西要在JS页面上处理！
//        if(!iForm.checkFileNameExtension(new String[]{"xls"}))
//        {
//            this.saveMessages(request, "文件后缀名出错！");
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
//            // 新增指定视频至指定货架上
//            ret = NewMusicRefBO.getInstance().importContentExigence(dataFile, categoryId);
//        }
        catch (BOException e)
        {
            LOG.error(e);
            
            // 写操作日志
			actionResult = false;
			actionDesc = "导入基地视频货架商品出错";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            this.saveMessagesValue(request, "用于文件批量导入视频商品上架出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        // 写操作日志
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "文件批量导入视频商品上架操作成功，" + ret);
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "queryReference.do?perType=query&categoryId="
                             + categoryId);
        return mapping.findForward(forward);
    }
    /**
     * 用于查询新视频列表
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

        // 从请求中获取货架内码
        String isFirst = this.getParameter(request, "isFirst").trim();
        String programName = this.getParameter(request, "programName").trim();
        String programId = this.getParameter(request, "programId").trim();
        String cmsId = this.getParameter(request, "cmsId").trim();
        String categoryId = this.getParameter(request, "categoryId").trim();
        String subcateName = this.getParameter(request, "subcateName").trim();
        String keyName = this.getParameter(request, "keyName").trim();
        VideoReferenceVO vo = new VideoReferenceVO();
        PageResult page = new PageResult(request, 50);
        // 如果是第一次。跳过
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
        	
//        	 查询货架信息
        	videoCategoryVO = VideoCategoryBO.getInstance()
                                                   .queryNewMusicCategoryVO(categoryId);
            if(videoCategoryVO != null && videoCategoryVO.getCateType()!= null &&videoCategoryVO.getCateType().equals("1")){
            	//彩铃货架
            	vo.setColorType(new Integer(1));
            }
            
        }*/
        vo.setProgramId(programId);
        vo.setProgramName(programName);
        vo.setCmsId(cmsId);
        
        if (LOG.isDebugEnabled())
        {
            LOG.debug("从请求中获取视频条件：programName=" + programName + ", programId="
                      + programId + ", cmsId=" + cmsId);
        }

        try
        {
        	//如果是热点内容货架，需要查询热点内容表
        	if(VideoBO.getInstance().isSubCategory(vo.getCategoryId(), hotContentPCategoryId)){
        		VideoBO.getInstance().queryHotContentReferenceList(page, vo);
        	}else{
        		 // 查询新新视频列表。用以上架至新货架上
            	VideoBO.getInstance().queryVideoReferenceList(page, vo);
        	}
           
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "查询新新视频列表出错");
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
	 * 用于导出当前数据
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
			log.debug("excel导出.......");
		}
		
		String actionType = "导出视频列表信息";
		boolean actionResult = true;
		String actionDesc = "导出视频列表信息成功";
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
			
			// 写操作日志
			actionResult = false;
			actionDesc = "导出视频列表信息出错";
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
		
		// 写操作日志
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
		return null;
	}
	 /**
     * 用于查询新视频
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

        // 从请求中获取货架内码
     
        String videoId = this.getParameter(request, "videoId").trim();
       
        String actionType = "查看新视频货架下商品详情";
		boolean actionResult = true;
		String actionDesc = "查看新视频货架下商品详情成功";
		String actionTarget = videoId;
		
		
		ProgramVO vo = null;
//        List keyBaseList = null;
        try
        {
            // 查询新新视频详情
        	vo =  NewVideoRefBO.getInstance().queryNewVideoInfo(videoId);
//        	keyBaseList = NewMusicRefBO.getInstance().queryNewMusicKeyResource(musicId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            
            // 写操作日志
			actionResult = false;
			actionDesc = "查看新视频货架下商品详情出错";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            this.saveMessagesValue(request, "查询新新视频详情出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }    
        
        // 写操作日志
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
