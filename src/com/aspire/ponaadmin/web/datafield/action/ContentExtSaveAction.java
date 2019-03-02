
package com.aspire.ponaadmin.web.datafield.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.usermanager.UserManagerBO;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.datafield.bo.ContentExtBO;

/**
 * <p>
 * 保存应用活动属性Action
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

public class ContentExtSaveAction extends BaseAction
{

    /**
     * 日志引用
     */
    private static final JLogger logger = LoggerFactory.getLogger(ContentExtSaveAction.class);

    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("ContentExtSaveAction...doPerform()");
        }
        String forward = Constants.FORWARD_COMMON_FAILURE;

        try
        {

            String contentID = this.getParameter(request, "contentID");
            String name = this.getParameter(request, "name");
            String spName = this.getParameter(request, "spName");
            String icpcode = this.getParameter(request, "icpcode");
            String type = this.getParameter(request, "type");
            String mobilePrice = this.getParameter(request, "mobilePrice");
            String discount = this.getParameter(request, "discount");

            String dateStart = this.getParameter(request, "dateStart");
            String dateEnd = this.getParameter(request, "dateEnd");
            String timeStart = this.getParameter(request, "timeStart");
            String timeEnd = this.getParameter(request, "timeEnd");
            String isrecomm = this.getParameter(request, "isrecomm");
            String userid = UserManagerBO.getInstance()
                                         .getUserSessionVO(request.getSession())
                                         .getUser()
                                         .getUserID();
            double expPrice = Double.parseDouble(mobilePrice)
                              * Double.parseDouble(discount) / 100.0;
            if (logger.isDebugEnabled())
            {
                logger.debug("contentID=" + contentID + " name=" + name
                             + " spName=" + spName + " icpcode=" + icpcode
                             + " icpcode=" + icpcode + " type=" + type
                             + " mobilePrice=" + mobilePrice + " discount="
                             + discount + " dateStart=" + dateStart
                             + " dateEnd=" + dateEnd + " timeStart="
                             + timeStart + " timeEnd=" + timeEnd + " userid="
                             + userid + " expPrice=" + expPrice + " isrecomm="
                             + isrecomm);
            }
            dateStart = dateStart.replaceAll("-", "");
            dateEnd = dateEnd.replaceAll("-", "");
            int i = ContentExtBO.getInstance().contentIsOnly(contentID,
                                                             dateStart,
                                                             dateEnd,
                                                             timeStart,
                                                             timeEnd);
            if (i == 0)
            {
                ContentExtBO.getInstance().contentExtSave(contentID,
                                                          name,
                                                          spName,
                                                          icpcode,
                                                          type,
                                                          mobilePrice,
                                                          discount,
                                                          dateStart,
                                                          dateEnd,
                                                          timeStart,
                                                          timeEnd,
                                                          userid,
                                                          expPrice,
                                                          isrecomm);

                this.saveMessages(request, "保存应用活动属性成功!");
                forward = Constants.FORWARD_COMMON_SUCCESS;
            }
            else
            {

                this.saveMessages(request, "应用活动时间设置重复，请重新填写!");
                request.setAttribute(Constants.PARA_GOURL, "contentExtAdd.do");
                return mapping.findForward(forward);
            }

        }
        catch (Exception e)
        {
            logger.error(e);
            this.saveMessages(request, "保存应用活动属性失败!");
        }
        request.setAttribute(Constants.PARA_GOURL, "contentExtList.do");
        return mapping.findForward(forward);

    }
}
