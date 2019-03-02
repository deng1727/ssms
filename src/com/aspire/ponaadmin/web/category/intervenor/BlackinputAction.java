package com.aspire.ponaadmin.web.category.intervenor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.category.intervenor.category.IntervenorCategoryAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.repository.web.DataImportForm;

public class BlackinputAction  extends BaseAction {

	/**
	 * 日志引用
	 */
	protected static final JLogger logger = LoggerFactory
			.getLogger(BlackinputAction.class);

	@Override
	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		if (logger.isDebugEnabled()) {
			logger.debug("doPerform()");
		}

		// 从请求中获取操作类型
		String action = this.getParameter(request, "actionType").trim();
		if ("blackinput".equals(action))
	        {
	            return blackinput(mapping, form, request, response);
	        }
		if ("importfile".equals(action))
        {
            return importfile(mapping, form, request, response);
        }
	        else
	        {
	            String forward = Constants.FORWARD_COMMON_FAILURE;
	            this.saveMessagesValue(request, "对不起，您访问的路径不存在");
	            return mapping.findForward(forward);
	        }

}

	private ActionForward blackinput(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String forward = "blackinput";
		return mapping.findForward(forward);
	}
	private ActionForward importfile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String forward = Constants.FORWARD_COMMON_FAILURE;


        // 得到导入内容文件信息
        DataImportForm iForm = ( DataImportForm ) form;
        int a=1;
        FormFile dataFile = iForm.getDataFile();

		String actionType = "导入榜单黑名单中";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = dataFile.getFileName();
		
        // 校验文件后缀名
        if(!iForm.checkFileNameExtension(new String[]{"xls"}))
        {
            this.saveMessages(request, "文件后缀名出错！");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        

        try
        {
            // 容器内容文件导入
            BlackinputBo.getInstance().importFile( dataFile);
        }
        catch (Exception e)
        {
        	e.printStackTrace();
			// 写操作日志
			actionResult = false;
			actionDesc = "导入榜单黑名单时出错!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            logger.error(e);
            this.saveMessagesValue(request, "导入榜单黑名单时出错");
            return mapping.findForward(forward);
        }
		// 写操作日志
		actionDesc = "导入榜单黑名单数据文件信息成功";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "导入榜单黑名单文件信息成功！");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "blackinput.do?actionType=blackinput");
        return mapping.findForward(forward);
	}
}
