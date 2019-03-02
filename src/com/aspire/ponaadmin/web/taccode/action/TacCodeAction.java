package com.aspire.ponaadmin.web.taccode.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.usermanager.UserManagerBO;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.channelUser.util.Common;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.repository.web.DataImportForm;
import com.aspire.ponaadmin.web.taccode.bo.TacCodeBO;
import com.aspire.ponaadmin.web.taccode.vo.TacVO;

/**
 * <p>Title:TAC码库管理 </p>
 * <p>Description: TAC码库查询、导入、删除相关操作</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * @author baojun
 * @version 
 */
public class TacCodeAction  extends BaseAction{

	/**
     * 日志引用
     */
    private static final JLogger LOG = LoggerFactory.getLogger(TacCodeAction.class);
    
    /* (non-Javadoc)
     * @see com.aspire.ponaadmin.web.BaseAction#doPerform(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {
        LOG.debug("doPerform()");

        if(request.getSession() == null || UserManagerBO.getInstance()
    			.getUserSessionVO(request.getSession()) == null){
    		this.saveMessages(request, "用户未登陆或者登陆已超时！");
    		request.setAttribute(Constants.PARA_GOURL, "index.jsp");
    		 request.setAttribute("loginResult", new Integer(Common.LOGINRESULT));
    		return mapping.findForward(Common.LOGIN_HINT_CHANNELUSER);
    	}
        
        //渠道商类型，0-表示终端公司，1-非终端公司
        //TAC码库管理是终端公司才有的功能，非终端公司没有权限访问该功能
        if(!"0".equals(UserManagerBO.getInstance()
    			.getUserSessionVO(request.getSession()).getChannel().getMoType())){
        	String forward = Constants.FORWARD_COMMON_FAILURE;
            this.saveMessagesValue(request, "对不起，您没有权限访问！");
            return mapping.findForward(forward);
        }
        
        // 从请求中获取操作类型
        String perType = this.getParameter(request, "perType").trim();

        if ("query".equals(perType))
        {
            return query(mapping, form, request, response);
        }
        else if("remove".equals(perType))
        {
            return remove(mapping, form, request, response);
        }
        else if("importData".equals(perType))
        {
            return importData(mapping, form, request, response);
        }
        else
        {
            String forward = Constants.FORWARD_COMMON_FAILURE;
            this.saveMessagesValue(request, "对不起，您访问的路径不存在");
            return mapping.findForward(forward);
        }
    }
    
    /**
     * 查询TAC码库列表
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
        LOG.debug("doPerform()");
        String forward = "query";
        PageResult page = new PageResult(request);
        
        String tacCode = this.getParameter(request, "tacCode").trim();
        String brand = this.getParameter(request, "brand").trim(); 
        String device = this.getParameter(request, "device").trim();
        String channelId = this.getParameter(request, "channelId").trim();
        String channelName = this.getParameter(request, "channelName").trim();
        TacVO vo = new TacVO();
        vo.setTacCode(tacCode);
        vo.setBrand(brand);
        vo.setDevice(device);
        vo.setChannelId(channelId);
        vo.setChannelName(channelName);
        
        try
        {
            TacCodeBO.getInstance().queryTacCodeList(page, vo);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "查询TAC码库列表出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        request.setAttribute("PageResult", page);
        request.setAttribute("tacCode", tacCode);
        request.setAttribute("brand", brand);
        request.setAttribute("device", device);
        request.setAttribute("channelId", channelId);
        request.setAttribute("channelName", channelName);

        return mapping.findForward(forward);
    }
    
    /**
     * 用于移除指定TAC码
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

        // 从请求中获取重点机型ID
        String id = this.getParameter(request, "id").trim(); 
        String tacCode = this.getParameter(request, "tacCode").trim();
        
		String actionType = "用于删除指定TAC码";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = tacCode;
        if (LOG.isDebugEnabled())
        {
            LOG.debug("用于删除指定TAC码");
        }

        try
        {
        	TacCodeBO.getInstance().delByTacCode(id,tacCode);
        }
        catch (BOException e)
        {
			// 写操作日志
			actionResult = false;
			actionDesc = "用于删除指定TAC码出错!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            LOG.error(e);
            this.saveMessagesValue(request, "用于删除指定TAC码出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
		// 写操作日志
		actionDesc = "删除TAC码成功!";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "删除TAC码成功");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "tacCode.do?perType=query");
        return mapping.findForward(forward);
    }
    
    /**
     * 导入TAC码列表
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
        DataImportForm iForm=(DataImportForm)form;
        FormFile dataFile = iForm.getDataFile();
        
		String actionType = "用于导入TAC码列表";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = dataFile.getFileName();
		
        
//      校验文件后缀名
        if(!iForm.checkFileNameExtension(new String[]{"xls","xlsx"}))
        {
            this.saveMessages(request, "文件后缀名出错！");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        } 
        
        try
        {
            // 导入TAC码库列表
            ret = TacCodeBO.getInstance().importTacCode(dataFile);
        }
        catch (BOException e)
        {
			// 写操作日志
			actionResult = false;
			actionDesc = e.getMessage();
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
	        
            LOG.error(e);
            
            this.saveMessagesValue(request, "用于导入TAC码列表出错,请检查导入的数据文件是否有误！");
            
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
		// 写操作日志
		actionDesc = "添加导入TAC码列表操作成功，" + ret;
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "添加导入TAC码列表操作成功，" + ret);
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "tacCode.do?perType=query");
        return mapping.findForward(forward);
    }
	
}
