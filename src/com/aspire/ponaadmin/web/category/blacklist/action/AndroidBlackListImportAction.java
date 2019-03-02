package com.aspire.ponaadmin.web.category.blacklist.action;

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
import com.aspire.ponaadmin.web.category.blacklist.bo.AndroidBlackListBO;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.repository.web.DataImportForm;

/**
 * <p>
 * 榜单批量导入Action
 * </p>
 * <p>
 */
public class AndroidBlackListImportAction extends BaseAction{

	/**
     * 日志引用
     */
    private static final JLogger LOG = LoggerFactory.getLogger(AndroidBlackListImportAction.class);

    /* (non-Javadoc)
     * @see com.aspire.ponaadmin.web.BaseAction#doPerform(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
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
        DataImportForm iForm=(DataImportForm)form;
        FormFile dataFile = iForm.getDataFile();
        
		String actionType = "批量新增黑名单榜单元数据";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = dataFile.getFileName();
		
        // 校验文件后缀名
        if(!iForm.checkFileNameExtension(new String[]{"xls","xlsx"}))
        {
            this.saveMessages(request, "文件后缀名出错！");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        try
        {
            // 批量新增榜单元数据
            ret = AndroidBlackListBO.getInstance().importTagData(dataFile);
        }
        catch (BOException e)
        {
			// 写操作日志
			actionResult = false;
			actionDesc = "批量新增黑名单榜单元数据出错!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            LOG.error(e);
            
            this.saveMessagesValue(request, "批量新增黑名单榜单元数据出错");
              
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
		// 写操作日志
		actionDesc = "批量新增黑名单榜单元数据操作成功，" + ret;
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);

        this.saveMessagesValue(request, "批量新增黑名单榜单元数据操作成功，" + ret);
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "androidBlackList.do?perType=query");
        return mapping.findForward(forward);
        
    }
}
