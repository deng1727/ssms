
package com.aspire.ponaadmin.web.datafield.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.datafield.KeyBaseConfig;
import com.aspire.ponaadmin.web.datafield.bo.KeyBaseBO;
import com.aspire.ponaadmin.web.datafield.vo.ResourceVO;

/**
 * <p>
 * 查询属性字段详情的Action
 * </p>
 * Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights
 * Reserved
 * </p>
 * 
 * @author biran
 * @version 1.0.1.0
 * @since 1.0.1.0
 */

public class KeyBaseInfoAction extends BaseAction
{

    /**
     * 日志引用
     */
    private static final JLogger logger = LoggerFactory.getLogger(KeyBaseInfoAction.class);

    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("KeyBaseInfoAction...doPerform()");
        }

        String keyid = this.getParameter(request, "keyid");
        if (logger.isDebugEnabled())
        {
            logger.debug("keyid" + keyid);
        }
        ResourceVO vo = KeyBaseBO.getInstance().getKeyBaseByID(keyid);
        request.setAttribute("vo", vo);

        String[] keytableArr = KeyBaseConfig.get("keytable").trim().split(",");
        String[] keytype = {"1|普通文本","2|图片域","3|大文本"};
        request.setAttribute("keytableArr", keytableArr);
        request.setAttribute("keytypes", keytype);

        String forward = "showInfo";

        return mapping.findForward(forward);

    }
}
