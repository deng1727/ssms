package com.aspire.ponaadmin.web.channeladmin.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.channeladmin.bo.CategoryQueryBO;
import com.aspire.ponaadmin.web.channeladmin.bo.OpenChannelsCategoryBo;
import com.aspire.ponaadmin.web.channeladmin.vo.OpenChannelsCategoryVo;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.repository.CategoryOperationBO;


public class OpenChannelsCategoryAction extends BaseAction
{
    /**
     * 记录日志实体
     */
    private static final JLogger logger = LoggerFactory.getLogger(OpenChannelsCategoryAction.class);

    @Override
    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {
        logger.debug("doPerform()");
        String method = this.getParameter(request, "method");
        if("add".equals(method)){
            return add(mapping, form, request, response);
        }else if("save".equals(method)){
            return save(mapping, form, request, response);
        }else{
            String forward = Constants.FORWARD_COMMON_FAILURE;
            this.saveMessagesValue(request, "对不起，您访问的路径不存在");
            return mapping.findForward(forward); 
        } 
        
    }

    private ActionForward save(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response)
    {
        String forward = Constants.FORWARD_COMMON_FAILURE;

        OpenChannelsCategoryVo vo = new OpenChannelsCategoryVo();
        String channelsId = this.getParameter(request, "channelsId").trim();
        String categoryId = this.getParameter(request, "categoryId").trim();

        String actionType = "新增根货架配置";
        boolean actionResult = true;
        String actionDesc = "新增根货架配置成功";

        vo.setChannelsId(channelsId);
        vo.setCategoryId(categoryId);

        try {
        	//先判断该货架ID是否存在于配置项的货架下。若不存在，则不能添加。
        	if(!CategoryQueryBO.getInstance().isExistInCategory(categoryId)){
        		actionType = "该货架ID不存在于对应的货架下";
        		actionDesc = "该货架ID不存在于对应的货架下";
        		this.saveMessagesValue(request, "该货架ID不存在于对应的货架下,请重新输入。");
        		request
                .setAttribute(Constants.PARA_GOURL,
                        "../channeladmin/openChannelsCategory.do?method=add&channelsId="+channelsId);
        		return mapping.findForward(forward);
        		
        	}
            // 用于新增客户端渠道
            OpenChannelsCategoryBo.getInstance().saveOpenChannelsCategoryVo(vo);

        } catch (BOException e) {
            logger.error(e);
            
            // 写操作日志
            actionResult = false;
            actionDesc = "根货架配置出错";
            this.actionLog(request,
                           actionType,
                           null,
                           actionResult,
                           actionDesc);
            
            this.saveMessagesValue(request, "根货架配置出错");
            return mapping.findForward(forward);
        }

        // 写操作日志
        this.actionLog(request,
                       actionType,
                       null,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "根货架配置成功");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request
                .setAttribute(Constants.PARA_GOURL,
                        "../channeladmin/openChannelMoList.do?method=queryList");
        return mapping.findForward(forward);
    }

    private ActionForward add(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response) throws BOException
    {

        String forward = "add";
        OpenChannelsCategoryVo vo = new OpenChannelsCategoryVo();
        String channelsId = this.getParameter(request, "channelsId");
        vo = OpenChannelsCategoryBo.getInstance().queryOpenChannelsCategoryVo(channelsId);
        if(vo == null){
            vo = new OpenChannelsCategoryVo();
            vo.setChannelsId(channelsId);
        }
        request.setAttribute("vo", vo);
        return mapping.findForward(forward);
    }
    

}
