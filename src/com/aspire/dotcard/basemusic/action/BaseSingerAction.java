package com.aspire.dotcard.basemusic.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

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
import com.aspire.dotcard.basemusic.bo.BaseSingerBO;
import com.aspire.dotcard.basemusic.vo.BaseSingerVO;
import com.aspire.dotcard.baseread.bo.ReadCategoryBO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.datafield.bo.KeyBaseBO;
import com.aspire.ponaadmin.web.newmusicsys.action.FileForm;
import com.aspire.ponaadmin.web.repository.web.DataImportForm;

/**
 * @author wangminlong
 * 
 */
public class BaseSingerAction extends BaseAction
{

	/**
	 * 日志引用
	 */
	private static final JLogger LOG = LoggerFactory
			.getLogger(BaseSingerAction.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.ponaadmin.web.BaseAction#doPerform(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException
	{
		LOG.debug("doPerform()");

		// 从请求中获取操作类型
		String perType = this.getParameter(request, "perType").trim();

		if ("query".equals(perType))
		{
			return query(mapping, form, request, response);
		}
		else if ("mod".equals(perType))
		{
			return mod(mapping, form, request, response);
		}
		else if ("update".equals(perType))
		{
			return update(mapping, form, request, response);
		}
		else if ("input".equals(perType))
		{
			return input(mapping, form, request, response);
		}
		else if ("export".equals(perType))
		{
			return doExport(mapping, form, request, response, false);
		}
		else if ("allExport".equals(perType))
		{
			return doExport(mapping, form, request, response, true);
		}
		else
		{
			String forward = Constants.FORWARD_COMMON_FAILURE;
			this.saveMessagesValue(request, "对不起，您访问的路径不存在");
			return mapping.findForward(forward);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.ponaadmin.web.BaseAction#doPerform(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException
	{
		LOG.debug("doPerform()");
		String forward = "query";
		PageResult page = new PageResult(request);
		String singerId = this.getParameter(request, "singerId").trim();
		String singerName = this.getParameter(request, "singerName").trim();
		String isFirst = this.getParameter(request, "isFirst").trim();

        String actionType = "查询歌手列表";
		boolean actionResult = true;
		String actionDesc = "查询歌手列表成功";
		String actionTarget = singerId;
		
		BaseSingerVO vo = new BaseSingerVO();
		vo.setSingerId(singerId);
		vo.setSingerName(singerName);

		if (LOG.isDebugEnabled())
		{
			LOG.info("从请求中获取查询条件：singerId = " + singerId + ",singerName="
					+ singerName);
		}

		if (!"1".equals(isFirst))
		{
			try
			{
				BaseSingerBO.getInstance().queryBaseSingerList(page, vo);
			}
			catch (BOException e)
			{
				LOG.error(e);
				
	            // 写操作日志
				actionResult = false;
				actionDesc = "查询歌手列表出错";
		        this.actionLog(request,
		                       actionType,
		                       actionTarget,
		                       actionResult,
		                       actionDesc);
		        
				this.saveMessagesValue(request, "查询基地音乐歌手信息列表出错");
				return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
			}
		}
		
        // 写操作日志
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
		request.setAttribute("isFirst", isFirst);
		request.setAttribute("perType", forward);
		request.setAttribute("PageResult", page);
		request.setAttribute("singerId", singerId);
		request.setAttribute("singerName", singerName);
		return mapping.findForward(forward);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.ponaadmin.web.BaseAction#doPerform(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward mod(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException
	{
		LOG.debug("doPerform()");
		String forward = "mod";
		String singerId = this.getParameter(request, "singerId").trim();
		List keyBaseList = null;
		BaseSingerVO vo = null;
		try
		{
			// 得到当前货架信息
			vo = BaseSingerBO.getInstance().queryBaseSingerVO(singerId);
			keyBaseList = BaseSingerBO.getInstance().querySingerIdKeyResource(
					singerId);
		}
		catch (BOException e)
		{
			LOG.error(e);
			this.saveMessagesValue(request, "查询基地音乐歌手详情信息出错");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}

		request.setAttribute("keyBaseList", keyBaseList);
		request.setAttribute("baseSingerVO", vo);
		return mapping.findForward(forward);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.ponaadmin.web.BaseAction#doPerform(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException
	{
		LOG.debug("doPerform()");

		String forward = Constants.FORWARD_COMMON_FAILURE;
		FileForm fileForm = (FileForm) form;

		BaseSingerVO singerVO = new BaseSingerVO();
		String singerId = this.getParameter(request, "singerId").trim();
		String type = this.getParameter(request, "type").trim();
		
        String actionType = "修改歌手信息";
		boolean actionResult = true;
		String actionDesc = "修改歌手信息成功";
		String actionTarget = singerId;

		List keyBaseList = BaseSingerBO.getInstance().querySingerKeyBaseList(
				singerId);

		singerVO.setSingerId(singerId);
		singerVO.setType(type);

		try
		{
			// 用于变更图书货架
			BaseSingerBO.getInstance().updateBaseSinger(singerVO);

			if (keyBaseList != null && keyBaseList.size() > 0)
			{
				this.saveBaseSingerKeyResource(keyBaseList, fileForm, singerId,
						request);
			}
		}
		catch (BOException e)
		{
			LOG.error(e);
			
            // 写操作日志
			actionResult = false;
			actionDesc = "修改歌手信息出错";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
			this.saveMessagesValue(request, "更新基地音乐歌手详情出错");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}

        // 写操作日志
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
		this.saveMessagesValue(request, "更新基地音乐歌手成功");
		request.setAttribute(Constants.PARA_GOURL,
				"querySinger.do?perType=query&isFirst=1");
		forward = Constants.FORWARD_COMMON_SUCCESS;
		return mapping.findForward(forward);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.ponaadmin.web.BaseAction#doPerform(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward input(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException
	{
        String forward = "input";
        String ret = "";

        if (LOG.isDebugEnabled())
        {
            LOG.debug("action请求开始");
        }
        
        String actionType = "文件批量导入基地音乐歌手";
		boolean actionResult = true;
		String actionDesc = "文件批量导入基地音乐歌手成功";
		String actionTarget = "";
        
        // 从请求中获取货架内码
        DataImportForm iForm=(DataImportForm)form;
        
        // 校验文件后缀名
        if(!iForm.checkFileNameExtension(new String[]{"xls"}))
        {
            this.saveMessages(request, "文件后缀名出错！");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        
        FormFile dataFile = iForm.getDataFile();
        
        try
        {
            // 文件批量导入图书商品上架
            ret = BaseSingerBO.getInstance().importSingerData(dataFile);
        }
        catch (BOException e)
        {
            LOG.error(e);
            
            // 写操作日志
			actionResult = false;
			actionDesc = "文件批量导入基地音乐歌手出错";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            this.saveMessagesValue(request, "用于文件批量导入基地音乐歌手出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        // 写操作日志
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "文件批量导入基地音乐歌手操作成功，" + ret);
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "querySinger.do?perType=query&isFirst=1");
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
	public ActionForward doExport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			boolean isAll) throws BOException
	{
		if (log.isDebugEnabled())
		{
			log.debug("doExport in......");
			log.debug("excel导出.......");
		}
		
		String actionType = "导出基地音乐歌手";
		boolean actionResult = true;
		String actionDesc = "导出基地音乐歌手成功";
		String actionTarget = "";

		OutputStream os = null;
		WritableWorkbook wwb = null;
		String excelName = "query_singer_"
				+ System.currentTimeMillis() + ".xls";
		try
		{
			os = new FileOutputStream(excelName);
			wwb = Workbook.createWorkbook(os);
			BaseSingerBO.getInstance().exportQueryData(request, wwb, isAll);
		}
		catch (Exception e)
		{
			log.error(e);
			
            // 写操作日志
			actionResult = false;
			actionDesc = "导出基地音乐歌手出错";
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
	 * @desc 保存扩展字段
	 * @author dongke Aug 8, 2011
	 * @throws BOException
	 */
	public void saveBaseSingerKeyResource(List keyBaseList, FileForm fileForm,
			String cid, HttpServletRequest request) throws BOException
	{
		// 资源服务器本模块路径
		String resServerPath = ConfigFactory.getSystemConfig().getModuleConfig(
				"basemusic").getItemValue("resServerPath");
		List delResourcelist = new ArrayList();

		this.saveKeyResource(keyBaseList, delResourcelist, fileForm, cid,
				request, resServerPath, "singer");

		ReadCategoryBO.getInstance().saveKeyResource(keyBaseList);

		KeyBaseBO.getInstance().delKeyResourceListValue(delResourcelist);
	}
}
