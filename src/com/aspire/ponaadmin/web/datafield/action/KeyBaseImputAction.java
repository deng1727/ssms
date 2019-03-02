
package com.aspire.ponaadmin.web.datafield.action;

import java.util.ArrayList;
import java.util.List;

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

public class KeyBaseImputAction extends BaseAction
{

    /**
     * 日志引用
     */
    private static final JLogger logger = LoggerFactory.getLogger(KeyBaseImputAction.class);

    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("KeyBaseImputAction...doPerform()");
        }
        String forward = "imput";

        String[] keytableArr = KeyBaseConfig.get("keytable").trim().split(",");
        request.setAttribute("keytableArr", keytableArr);
        
        String keyTable = request.getParameter("keyTable");
        
        if(keyTable!=null && !keyTable.equals(""))
        {
            List keyNameList = KeyBaseBO.getInstance().getKeytableByText(keyTable);
            request.setAttribute("keyNameList", keyNameList);
            request.setAttribute("keyTable", keyTable);
        }
        else
        {
            request.setAttribute("keyNameList", new ArrayList());
            request.setAttribute("keyTable", "");
        }

        return mapping.findForward(forward);

    }
}
