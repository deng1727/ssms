package com.aspire.ponaadmin.web.channeladmin.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.channeladmin.bo.OpenOperationChannelBo;
import com.aspire.ponaadmin.web.channeladmin.vo.OpenChannelMoVo;
import com.aspire.ponaadmin.web.channeladmin.vo.OpenOperationChannelVo;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.system.PageSizeConstants;


public class OpenOperationListAction extends BaseAction
{
    /**
     * 记录日志实体
     */
    private static final JLogger logger = LoggerFactory.getLogger(OpenOperationListAction.class);

    @Override
    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {
        logger.debug("doPerform()");
        String method = this.getParameter(request, "method");
        try
        {
            if("add".equals(method)){
                return add(mapping, form, request, response);
            }else if("list".equals(method)){
                return list(mapping, form, request, response);
            }else if("save".equals(method)){
                return save(mapping, form, request, response);
            }else if("del".equals(method)){
                return del(mapping, form, request, response);
            }else{
                String forward = Constants.FORWARD_COMMON_FAILURE;
                this.saveMessagesValue(request, "对不起，您访问的路径不存在");
                return mapping.findForward(forward); 
            } 
        }
        catch (DAOException e)
        {
            logger.debug("查询数据失败！", e);
            e.printStackTrace();
            return null;
        }
        
    }

    private ActionForward del(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response)
    {
        String channelId = this.getParameter(request, "channelId");
        String channelsId = this.getParameter(request, "channelsId");
        String forward = Constants.FORWARD_COMMON_FAILURE;
        if(!"".equals(channelId)){
            OpenOperationChannelBo.getInstance().delOpenOperationChannelVoById(channelId);
        }
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request
                .setAttribute(Constants.PARA_GOURL,
                        "../channeladmin/openOperationList.do?method=list&channelsId="+channelsId);
        return mapping.findForward(forward);
    }

    private ActionForward save(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response)
    {
        String channelsId = this.getParameter(request, "channelsId");
        String forward = Constants.FORWARD_COMMON_FAILURE;

        OpenOperationChannelVo vo = new OpenOperationChannelVo();
        String channelId = this.getParameter(request, "channelId").trim();

        String actionType = "新增开放运营渠道";
        boolean actionResult = true;
        String actionDesc = "新增开放运营渠道成功";

      //新增的ID，传到后台数据库查询，是否重复，若重复，则不允许添加
        vo = OpenOperationChannelBo.getInstance().queryOpenOperationChannelVo(channelId);
        if(vo==null){
            actionResult = false;
            actionDesc = "开放运营渠道ID已经存在！";
            this.actionLog(request,
                           actionType,
                           null,
                           actionResult,
                           actionDesc);
            
            this.saveMessagesValue(request, "开放运营渠道ID已经存在！");
            return mapping.findForward(forward);
        }
        vo.setChannelId(channelId);
        vo.setChannelsId(channelsId);
        vo.setCreateDate(new Date());

        try {
            // 用于新增开放运营渠道
            OpenOperationChannelBo.getInstance().saveOpenOperationChannelVo(vo);

        } catch (BOException e) {
            logger.error(e);
            
            // 写操作日志
            actionResult = false;
            actionDesc = "新增开放运营渠道出错";
            this.actionLog(request,
                           actionType,
                           null,
                           actionResult,
                           actionDesc);
            
            this.saveMessagesValue(request, "新增开放运营渠道出错");
            return mapping.findForward(forward);
        }

        // 写操作日志
        this.actionLog(request,
                       actionType,
                       null,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "新增开放运营渠道配置成功");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request
                .setAttribute(Constants.PARA_GOURL,
                        "../channeladmin/openOperationList.do?method=list&channelsId="+channelsId);
        return mapping.findForward(forward);
    }

    private ActionForward list(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response) throws DAOException
    {

        //获取查询条件
        String channelId = this.getParameter(request, "channelId");
        
        //获取带过来的渠道商ID
        String channelsId = this.getParameter(request, "channelsId");
        
        OpenOperationChannelVo vo = new OpenOperationChannelVo();
        vo.setChannelId(channelId);
        vo.setChannelsId(channelsId);
        String forward = "list";
        
        PageResult page = new PageResult(request);
        page.setPageSize(Integer.parseInt(PageSizeConstants.page_DEFAULT));
        
        OpenOperationChannelBo.getInstance().listOpenOperationChannels(page,vo);
        
        request.setAttribute("channelId", channelId);
        request.setAttribute("channelsId", channelsId);
        request.setAttribute("PageResult", page);
        return mapping.findForward(forward);
    }

    private ActionForward add(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response)
    {

        String forward = "add";
        String channelsId = this.getParameter(request, "channelsId");
        OpenChannelMoVo vo = new OpenChannelMoVo();
        vo.setChannelsId(channelsId);
        request.setAttribute("vo", vo);
        return mapping.findForward(forward);
    }

}
