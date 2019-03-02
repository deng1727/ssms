package com.aspire.ponaadmin.web.datafield.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.syncAndroid.dc.jms.MSGType;
import com.aspire.dotcard.syncAndroid.ppms.PPMSDAO;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.datafield.bo.ContentBO;
import com.aspire.ponaadmin.web.datafield.bo.KeyBaseBO;
import com.aspire.ponaadmin.web.datafield.dao.ContentDAO;
import com.aspire.ponaadmin.web.newmusicsys.action.FileForm;

/**
 * <p>
 * 根据应用更新resource表属性值的Action
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

public class ResourceUpdateAction extends BaseAction
{

    /**
     * 日志引用
     */
    private static final JLogger logger = LoggerFactory.getLogger(ResourceUpdateAction.class);

    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("ResourceUpdateAction...doPerform()");
        }
        String forward = "";

        try
        {
            // String[] keyid = request.getParameterValues("keyid");
            // String[] value = request.getParameterValues("value");
            String tid = this.getParameter(request, "tid");
            if (logger.isDebugEnabled())
            {
                logger.debug("tid" + tid);
            }
            // ContentBO.getInstance().updateResourceValue(keyid, value, tid);
            FileForm fileForm = ( FileForm ) form;

            // 校验文件后缀名
            if (!fileForm.checkFileNameExtension(new String[] { "png", "jpg",
                            "gif" }))
            {
                this.saveMessages(request, "文件后缀名出错！");
                return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
            }

            List keyBaseList = ContentBO.getInstance()
                                        .queryContentKeyBaseList(tid);

            if (keyBaseList != null)
            {
                this.saveContentKeyResource(keyBaseList, fileForm, tid, request);

            }
            // add by aiyan 2013-07-11为应用扩展属性内容变更发消息。
            if(validateContent(tid)){//如果这个应用在ANDROID商品库优化没有适配资源，就不用发消息了。
            	PPMSDAO.addMessagesStatic(MSGType.ContentModifyReq,null,tid+":1");//应用信息更新
            	logger.info("success send msg...");
            }else{
            	logger.info("oh...no have this resource,so can't send msg...");
            }
            
            this.saveMessages(request, "更新应用属性值成功!");

            forward = Constants.FORWARD_COMMON_SUCCESS;
        }
        catch (Exception e)
        {
            logger.error(e);
            forward = Constants.FORWARD_COMMON_FAILURE;// 转向错误处理页
            this.saveMessages(request, "更新应用属性值失败!");
        }
        request.setAttribute(Constants.PARA_GOURL, "contentList.do");
        return mapping.findForward(forward);

    }

    private boolean validateContent(String tid) {
		// TODO Auto-generated method stub
		return ContentDAO.getInstance().validateContent(tid);
	}

	/**
     * 
     * @desc 保存扩展字段
     * @author dongke Aug 8, 2011
     * @throws BOException
     */
    public void saveContentKeyResource(List keyBaseList, FileForm fileForm,
                                       String cid, HttpServletRequest request)
                    throws BOException
    {

        // 资源服务器本模块路径
        String resServerPath = ConfigFactory.getSystemConfig()
                                            .getModuleConfig("ssms")
                                            .getItemValue("contentExpPicPath");
        List delResourcelist = new ArrayList();
        this.saveKeyResource(keyBaseList,
                             delResourcelist,
                             fileForm,
                             cid,
                             request,
                             resServerPath,
                             "contentpic");

        ContentBO.getInstance().saveKeyResource(keyBaseList);
        KeyBaseBO.getInstance().delKeyResourceListValue(delResourcelist);
    }
}
