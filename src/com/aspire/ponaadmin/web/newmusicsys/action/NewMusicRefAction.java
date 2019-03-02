/*
 * 文件名：NewMusicCategoryAction.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
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
     * 日志引用
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

        // 从请求中获取操作类型
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
            this.saveMessagesValue(request, "对不起，您访问的路径不存在");
            return mapping.findForward(forward);
        }
    }

    /**
     * 得到新音乐指定货架下的商品列表
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

        // 从请求中获取货架内码
        String categoryId = this.getParameter(request, "categoryId").trim();
        String musicId = this.getParameter(request, "musicId").trim();
        String musicName = this.getParameter(request, "musicName").trim();
        String singer = this.getParameter(request, "singer").trim();
        String showCreateTime = this.getParameter(request, "showCreateTime").trim();
        String approvalStatus = this.getParameter(request, "approvalStatus");

        String actionType = "查询音乐货架下商品信息";
		boolean actionResult = true;
		String actionDesc = "查询音乐货架下商品信息成功";
		String actionTarget = categoryId;
		
        vo.setCategoryId(categoryId);
        vo.setMusicId(musicId);
        vo.setMusicName(musicName);
        vo.setSinger(singer);
        vo.setShowCreateTime(showCreateTime);
        vo.setVerify_status(approvalStatus);

        if (LOG.isDebugEnabled())
        {
            LOG.debug("从请求中获取货架内码：" + categoryId);
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
            
            // 写操作日志
			actionResult = false;
			actionDesc = "查询音乐货架下商品信息出错";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            this.saveMessagesValue(request, "查询新音乐指定货架下的商品列表出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        // 写操作日志
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
     * 用于移除指定货架下指定的新音乐
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
        String[] musicId = request.getParameterValues("dealRef");

        String actionType = "删除音乐货架下商品";
		boolean actionResult = true;
		String actionDesc = "删除音乐货架下商品成功";
		String actionTarget = categoryId;
		
        if (LOG.isDebugEnabled())
        {
            LOG.debug("从请求中获取货架内码：" + categoryId);
        }

        try
        {
            NewMusicRefBO.getInstance().removeNewMusicRefs(categoryId, musicId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            
            // 写操作日志
			actionResult = false;
			actionDesc = "删除音乐货架下商品出错";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            this.saveMessagesValue(request, "用于移除指定货架下指定的新音乐出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        
        // 写操作日志
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);

        this.saveMessagesValue(request, "删除新音乐成功");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "queryReference.do?perType=query&categoryId="
                             + categoryId);
        return mapping.findForward(forward);
    }

    /**
     * 用于添加指定的新音乐至货架中
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
        String addMusicId = this.getParameter(request, "addMusicId").trim();

        String actionType = "新增音乐货架下商品";
		boolean actionResult = true;
		String actionDesc = "新增音乐货架下商品成功";
		String actionTarget = categoryId;
		
        if (LOG.isDebugEnabled())
        {
            LOG.debug("从请求中获取货架内码：" + categoryId);
        }

        try
        {
            // 查看原货架是否存在用户将要新增的音乐信息
            String ret = NewMusicRefBO.getInstance().isHasMusicRefs(categoryId,
                                                                    addMusicId);
            if (!"".equals(ret))
            {
                this.saveMessagesValue(request, "原货架存在用户所选以下音乐：" + ret);
                return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
            }
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "查看原货架是否存在用户将要新增时出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        try
        {
            // 新增指定音乐至指定货架上
            NewMusicRefBO.getInstance().addNewMusicRefs(categoryId, addMusicId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            
            // 写操作日志
			actionResult = false;
			actionDesc = "新增音乐货架下商品出错";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            this.saveMessagesValue(request, "用于添加指定的新音乐至货架时出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        
        // 写操作日志
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);

        this.saveMessagesValue(request, "添加新音乐成功");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "queryReference.do?perType=query&categoryId="
                             + categoryId);
        return mapping.findForward(forward);
    }

    /**
     * 用于查询新音乐列表
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

        // 从请求中获取货架内码
        String isFirst = this.getParameter(request, "isFirst").trim();
        String musicName = this.getParameter(request, "musicName").trim();
        String musicId = this.getParameter(request, "musicId").trim();
        String singer = this.getParameter(request, "singer").trim();
        String categoryId = this.getParameter(request, "categoryId").trim();
        NewMusicRefVO vo = new NewMusicRefVO();
        PageResult page = new PageResult(request, 50);
        NewMusicCategoryVO newMusicCategoryVO;
        // 如果是第一次。跳过
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
        	
//        	 查询货架信息
            newMusicCategoryVO = NewMusicCategoryBO.getInstance()
                                                   .queryNewMusicCategoryVO(categoryId);
            if(newMusicCategoryVO != null && newMusicCategoryVO.getCateType()!= null &&newMusicCategoryVO.getCateType().equals("1")){
            	//彩铃货架
            	vo.setColorType(new Integer(1));
            }
            
        }
        vo.setMusicId(musicId);
        vo.setMusicName(musicName);
        vo.setSinger(singer);
        
        if (LOG.isDebugEnabled())
        {
            LOG.debug("从请求中获取音乐条件：musicName=" + musicName + ", musicId="
                      + musicId + ", singer=" + singer);
        }

        try
        {
            // 查询新音乐列表。用以上架至新货架上
            NewMusicRefBO.getInstance().queryNewMusicList(page, vo);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "查询新音乐列表出错");
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
     * 用于查询新音乐列表
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

        // 从请求中获取货架内码
     
        String musicId = this.getParameter(request, "musicId").trim();
       
        String actionType = "查看音乐货架下商品详情";
		boolean actionResult = true;
		String actionDesc = "查看音乐货架下商品详情成功";
		String actionTarget = musicId;
		
		
        NewMusicRefVO vo = null;
        List keyBaseList = null;
        try
        {
            // 查询新音乐详情
        	vo =  NewMusicRefBO.getInstance().queryNewMusicInfo(musicId);
        	keyBaseList = NewMusicRefBO.getInstance().queryNewMusicKeyResource(musicId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            
            // 写操作日志
			actionResult = false;
			actionDesc = "查看音乐货架下商品详情出错";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            this.saveMessagesValue(request, "查询新音乐详情出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }    
        
        // 写操作日志
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
     * 用于保存新音乐扩展属性
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

        // 从请求中获取货架内码
     
        String musicId = this.getParameter(request, "musicId").trim();
        
        String actionType = "修改音乐货架下商品详情";
		boolean actionResult = true;
		String actionDesc = "修改音乐货架下商品详情成功";
		String actionTarget = musicId;
		
        FileForm fileForm = (FileForm) form;
        NewMusicRefVO vo = null;
        List keyBaseList = null;
        try
        {
            // 查询新音乐详情
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
			
            // 写操作日志
			actionResult = false;
			actionDesc = "修改音乐货架下商品详情出错";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            this.saveMessagesValue(request, "保存新音乐扩展信息出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        } 
        
        // 写操作日志
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessages(request, "更新音乐扩展属性值成功!");

        forward = Constants.FORWARD_COMMON_SUCCESS;  
        request.setAttribute("vo", vo);
        request.setAttribute("keyBaseList", keyBaseList);
        request.setAttribute("musicId", musicId);
        return mapping.findForward(forward);
    }
    /**
     * 用于设置新音乐货架下音乐商品排序值
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

        // 从请求中获取货架内码
        String categoryId = this.getParameter(request, "categoryId").trim();
        String setSortId = this.getParameter(request, "setSortId").trim();

        String actionType = "修改音乐货架下商品排序号";
		boolean actionResult = true;
		String actionDesc = "修改音乐货架下商品排序号成功";
		String actionTarget = categoryId;
		
        if (LOG.isDebugEnabled())
        {
            LOG.debug("从请求中获取货架内码：" + categoryId);
        }

        try
        {
            // 新增指定音乐至指定货架上
            NewMusicRefBO.getInstance().setNewMusicSort(categoryId, setSortId);
        }
        catch (BOException e)
        {
            LOG.error(e);
            
            // 写操作日志
			actionResult = false;
			actionDesc = "修改音乐货架下商品排序号出错";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            this.saveMessagesValue(request, "用于设置新音乐货架下音乐商品排序值时出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        // 写操作日志
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "设置音乐商品排序值成功");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "queryReference.do?perType=query&categoryId="
                             + categoryId);
        return mapping.findForward(forward);
    }
    
    /**
     * 文件批量导入音乐商品上架
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
        
        String actionType = "导入基地音乐货架商品";
		boolean actionResult = true;
		String actionDesc = "导入基地音乐货架商品成功";
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
        		ret =NewMusicRefBO.getInstance().importContentADD(dataFile, categoryId);
        	}else if("ALL".equals(addType)){
        		ret = NewMusicRefBO.getInstance().importContentALL(dataFile, categoryId);
        	}
            
        }
//        
//        try
//        {
//            // 新增指定音乐至指定货架上
//            ret = NewMusicRefBO.getInstance().importContentExigence(dataFile, categoryId);
//        }
        catch (BOException e)
        {
            LOG.error(e);
            
            // 写操作日志
			actionResult = false;
			actionDesc = "导入基地音乐货架商品出错";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            this.saveMessagesValue(request, "用于文件批量导入音乐商品上架出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        // 写操作日志
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "文件批量导入音乐商品上架操作成功，" + ret);
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "queryReference.do?perType=query&categoryId="
                             + categoryId);
        return mapping.findForward(forward);
    }
    
    /**
     * 
     *@desc 保存扩展字段
     *@author dongke
     *Aug 8, 2011
     * @throws BOException 
     */
    public void saveNewMusicKeyResource(List keyBaseList,FileForm fileForm,String cid,HttpServletRequest request) throws BOException{

    	//资源服务器本模块路径
    	String resServerPath = ConfigFactory.getSystemConfig()
		.getModuleConfig("basemusic").getItemValue("resServerPath");  	
    	List delResourcelist = new ArrayList();
    	this.saveKeyResource(keyBaseList,delResourcelist,fileForm,cid,request,resServerPath,"newmusic"); 
    	
    	NewMusicRefBO.getInstance().saveKeyResource(keyBaseList);	
    	KeyBaseBO.getInstance().delKeyResourceListValue(delResourcelist);
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
		
		String actionType = "导出歌曲列表信息";
		boolean actionResult = true;
		String actionDesc = "导出歌曲列表信息成功";
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
			
			// 写操作日志
			actionResult = false;
			actionDesc = "导出歌曲列表信息出错";
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
}
