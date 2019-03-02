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
import com.aspire.ponaadmin.web.dataexport.basefile.impl.AppBaseFile;
import com.aspire.ponaadmin.web.dataexport.basefile.impl.DeviceBaseFile;

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
public class BaseFileAction extends BaseAction
{

    /**
     * 记录日志的实例对象
     */
    private static JLogger log = LoggerFactory.getLogger(BaseFileAction.class);

    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {
        log.debug("begin  BaseFileAction");

        String fileType = this.getParameter(request, "exportType");
        String forward;

        // 生成基础内容数据
        if (fileType.equals("appBase"))
        {
            forward = exportAppBase(mapping, form, request, response);
        }
        // 生成机型信息数据
        else if (fileType.equals("deviceBase"))
        {
            forward = exportDeviceBase(mapping, form, request, response);
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
    private String exportAppBase(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
    {
        String forward = Constants.FORWARD_COMMON_SUCCESS;
        String actionType = "生成基础内容文件导出";

        try
        {
            AppBaseFile app = new AppBaseFile();
            app.createFile();
        }
        catch (Exception e)
        {
            forward = Constants.FORWARD_COMMON_FAILURE;
            this.saveMessages(request, "生成基础内容文件导出操作失败");
            this.actionLog(request, actionType, actionType, false, "");
            log.error(e);
            return forward;
        }
        forward = Constants.FORWARD_COMMON_SUCCESS;
        this.saveMessages(request, "生成基础内容文件导出操作成功");
        this.actionLog(request, actionType, actionType, true, "");

        log.debug("end  BaseFileAction");
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
    private String exportDeviceBase(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request,
                                    HttpServletResponse response)
    {
        String forward = Constants.FORWARD_COMMON_SUCCESS;
        String actionType = "机信息同步文件导出";

        try
        {
            DeviceBaseFile device = new DeviceBaseFile();
            device.createFile();
        }
        catch (Exception e)
        {
            forward = Constants.FORWARD_COMMON_FAILURE;
            this.saveMessages(request, "机信息同步文件导出操作失败");
            this.actionLog(request, actionType, actionType, false, "");
            log.error(e);
            return forward;
        }
        forward = Constants.FORWARD_COMMON_SUCCESS;
        this.saveMessages(request, "机信息同步文件导出操作成功");
        this.actionLog(request, actionType, actionType, true, "");

        log.debug("end  BaseFileAction");
        return forward;
    }

}
