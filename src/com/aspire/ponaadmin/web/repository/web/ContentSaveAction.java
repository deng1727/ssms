package com.aspire.ponaadmin.web.repository.web ;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.gcontent.GContent;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.constant.ResourceConstants;
import com.aspire.ponaadmin.web.repository.Repository;

/**
 * <p>保存内容信息的Action</p>
 * <p>categoryID有表示修改，没有表示新增</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class ContentSaveAction extends BaseAction
{

    /**
     * 日志引用
     */
    private static final JLogger LOG = LoggerFactory.getLogger(ContentSaveAction.class) ;

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
    public ActionForward doPerform (ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request,
                                    HttpServletResponse response)
        throws BOException
    {
        String forward = null;
        String type = request.getParameter("type");
        String cateID = request.getParameter("categoryID");
        String contentID = request.getParameter("contentID");
        String tag = request.getParameter("tag");       
        GContent content = (GContent)Repository.getInstance().getNode(contentID,type) ;
        if(tag != null && !"".equals(tag))
        {
            tag = tag.trim();

            tag = tag.replaceAll("\n", "");
            tag = "{" + tag.replaceAll("\r", "};{") + "}";

            StringBuffer keywords= new StringBuffer("");
            int count=0;
            String tags[] = tag.split(";");
            for(int i=0;i<tags.length;i++)
            {
                if(keywords.indexOf(tags[i]) == -1)
                {
                    if (count != 0)
                    {
                        keywords.append(";");
                    }
                    keywords.append(tags[i]);
                    count++;
                }
            }
            
            tag = keywords.toString();
            if (count > 50 || tag.length() > 1500)
            {
                // 超范围了
                this.saveMessagesValue(request, "内容标签长度或个数超过限制!") ;
                return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
            }

            content.setKeywords(tag);
        }
        else
        {
            content.setKeywords("");
        }
        SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        String updateTime = sdf.format(new Date());
        content.setLupdDate(updateTime);
        
        String name = content.getName();
        String actionType = "";
        String actionTarget = "";
        String actionDesc = "";
        boolean actionResult = true;
        
        try
        {
            actionType = "修改内容标签";
            actionTarget = name;
            content.save();

            this.saveMessages(request, "RESOURCE_CONTENT_RESULT_002");
            request.setAttribute(Constants.PARA_GOURL,
                                 "../../web/resourcemgr/contentList.do?categoryID="
                                                 + cateID);
            forward = Constants.FORWARD_COMMON_SUCCESS;
        }
        catch(Exception e)
        {
            LOG.error("saveContent failed!", e);
            actionResult = false;
            this.saveMessages(request, ResourceConstants.WEB_ERR_SYSTEM) ;
            forward = Constants.FORWARD_COMMON_FAILURE;
        }
        //写操作日志
        this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
        return mapping.findForward(forward);
    }
    

}
