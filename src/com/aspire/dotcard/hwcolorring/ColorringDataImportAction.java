
package com.aspire.dotcard.hwcolorring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.hwcolorring.clrLoad.ColorringLoadTask;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.constant.ErrorCode;

/**
 * <p>彩铃数据的导入</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author dongxk
 * @version SD168
 */
public class ColorringDataImportAction extends BaseAction
{
    /**
     * 日志对象实例
     */
    protected static JLogger log = LoggerFactory.getLogger(ColorringDataImportAction.class) ;
    /**
    *
    * @param mapping ActionMapping
    * @param form ActionForm
    * @param request HttpServletRequest
    * @param response HttpServletResponse
    * @return ActionForward
    * @throws BOException
    * @todo Implement this com.aspire.ponaadmin.web.BaseAction method
    */
    public ActionForward doPerform(ActionMapping actionMapping,
                                   ActionForm actionForm,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
    throws BOException
    {

        log.debug("ColorringDataImportAction execute begin") ;
        String forward = "";
        long begintime = System.currentTimeMillis();
        int ret = ErrorCode.SUCC;
        try
        {
            ColorringLoadTask task = new ColorringLoadTask();
            task.run();
        }
        catch (Exception ex)
        {
            log.error(ex);
            ret = ErrorCode.FAIL;
        }
        long endtime = System.currentTimeMillis();
        // 生成log时间
        String time = (endtime - begintime) / 1000 + "秒";
        if (ErrorCode.SUCC == ret)
        {
            forward = Constants.FORWARD_COMMON_SUCCESS;
            this.saveMessagesValue(request, "导入彩铃数据成功！");
            this.saveMessagesValue(request, "耗时：" + time);
        }
        else
        {
            forward = Constants.FORWARD_COMMON_FAILURE;
        }
        return actionMapping.findForward(forward) ;
    }
}
