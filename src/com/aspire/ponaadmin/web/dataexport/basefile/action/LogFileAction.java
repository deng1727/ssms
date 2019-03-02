/*
 * 文件名：BaseFileAction.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */

package com.aspire.ponaadmin.web.dataexport.basefile.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.dataexport.basefile.impl.CategoryBaseFile;
import com.aspire.ponaadmin.web.dataexport.basefile.impl.ReferenceBaseFile;

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
public class LogFileAction extends BaseAction
{

    /**
     * 记录日志的实例对象
     */
    private static JLogger log = LoggerFactory.getLogger(LogFileAction.class);

    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {
        log.debug("begin  LogFileAction");

        String fileType = this.getParameter(request, "exportType");
        String forward;

        // 生成基础内容数据
        if (fileType.equals("categoryFile"))
        {
            forward = exportCategoryBase(mapping, form, request, response);
        }
        // 生成机型信息数据
        else if (fileType.equals("referenceFile"))
        {
            forward = exportReferenceBase(mapping, form, request, response);
        }
        else
        {
            forward = Constants.FORWARD_COMMON_FAILURE;
            this.saveMessages(request, "没有此导出类型");
        }

        return mapping.findForward(forward);
    }

    /**
     * 用于生成基础内容数据
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    private String exportCategoryBase(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
    {
        String forward = Constants.FORWARD_COMMON_SUCCESS;
        String actionType = "生成货架维表日志文件导出";

        try
        {
            CategoryBaseFile cate = new CategoryBaseFile();
            cate.createFile();
        }
        catch (Exception e)
        {
            forward = Constants.FORWARD_COMMON_FAILURE;
            this.saveMessages(request, "生成货架维表日志文件导出操作失败");
            this.actionLog(request, actionType, actionType, false, "");
            log.error(e);
            return forward;
        }
        forward = Constants.FORWARD_COMMON_SUCCESS;
        this.saveMessages(request, "生成货架维表日志文件导出操作成功");
        this.actionLog(request, actionType, actionType, true, "");

        log.debug("end  LogFileAction");
        return forward;
    }

    /**
     * 用于生成机型信息同步数据
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    private String exportReferenceBase(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request,
                                    HttpServletResponse response)
    {
        String forward = Constants.FORWARD_COMMON_SUCCESS;
        String actionType = "生成商品维表日志文件导出";

        try
        {
            ReferenceBaseFile reference = new ReferenceBaseFile();
            reference.createFile();
        }
        catch (Exception e)
        {
            forward = Constants.FORWARD_COMMON_FAILURE;
            this.saveMessages(request, "生成商品维表日志文件导出操作失败");
            this.actionLog(request, actionType, actionType, false, "");
            log.error(e);
            return forward;
        }
        forward = Constants.FORWARD_COMMON_SUCCESS;
        this.saveMessages(request, "生成商品维表日志文件导出操作成功");
        this.actionLog(request, actionType, actionType, true, "");

        log.debug("end  LogFileAction");
        return forward;
    }

}
