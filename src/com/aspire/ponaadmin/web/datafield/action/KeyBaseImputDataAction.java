
package com.aspire.ponaadmin.web.datafield.action;

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
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.datafield.bo.KeyBaseBO;
import com.aspire.ponaadmin.web.repository.web.DataImportForm;

/**
 * <p>
 * 新增keybase表扩展属性的Action
 * </p>
 * <p>
 * Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights
 * Reserved
 * </p>
 * 
 * @author biran
 * @version 1.0.1.0
 * @since 1.0.1.0
 */

public class KeyBaseImputDataAction extends BaseAction
{

    /**
     * 日志引用
     */
    private static final JLogger logger = LoggerFactory.getLogger(KeyBaseImputDataAction.class);

    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("KeyBaseImputDataAction...doPerform()");
        }
        String forward;
        String ret = "";

        // 从请求中获取keyid
        String keytable = this.getParameter(request, "keytable").trim();
        String keyid = this.getParameter(request, "keyid").trim();
        
        DataImportForm iForm=(DataImportForm)form;
        FormFile dataFile = iForm.getDataFile();
        
		String actionType = "文件批量导入扩展属性内容信息";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = keytable + "-" + keyid + "-" + dataFile.getFileName();
        
        // 校验文件后缀名
        if(!iForm.checkFileNameExtension(new String[]{"xls"}))
        {
            this.saveMessages(request, "文件后缀名出错！");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        
        try
        {
            // 文件批量导入扩展属性内容信息
            ret = KeyBaseBO.getInstance().imputKeyBase(dataFile, keytable, keyid);
        }
        catch (BOException e)
        {
			// 写操作日志
			actionResult = false;
			actionDesc = "文件批量导入扩展属性内容信息出错!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            logger.error(e);
            this.saveMessagesValue(request, "文件批量导入扩展属性内容信息出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

		// 写操作日志
		actionDesc = "文件批量导入扩展属性内容信息操作成功!" + ret;
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "文件批量导入扩展属性内容信息操作成功，" + ret);
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "keyBaseList.do");

        return mapping.findForward(forward);

    }
}
