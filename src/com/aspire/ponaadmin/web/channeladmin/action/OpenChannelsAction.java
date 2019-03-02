package com.aspire.ponaadmin.web.channeladmin.action;

import static com.aspire.common.util.MD5.getHexMD5Str;

import java.io.UnsupportedEncodingException;

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
import com.aspire.ponaadmin.web.channeladmin.bo.OpenChannelMoBo;
import com.aspire.ponaadmin.web.channeladmin.bo.OpenChannelsBO;
import com.aspire.ponaadmin.web.channeladmin.vo.OpenChannelsVO;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.system.PageSizeConstants;



public class OpenChannelsAction extends BaseAction
{
    
    private static final JLogger LOG = LoggerFactory.getLogger(OpenChannelsAction.class);
   
    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {
        LOG.debug("doPerform");
        String method = this.getParameter(request, "method").trim();
        if ("queryOpenChannelsList".equals(method))
        {
            return queryOpenChannelsList(mapping, form, request, response);
        }
        else if("add".equals(method))
        {
            return add(mapping, form, request, response);
        }
        else if("updateChannels".equals(method))
        {
            return updateChannels(mapping, form, request, response);
        }
        else if("save".equals(method))
        {
            return save(mapping, form, request, response);
        }
        else if("update".equals(method))
        {
            return update(mapping, form, request, response);
        }
        else if("updatePwd".equals(method))
        {
            return updatePwd(mapping, form, request, response);
        }
        else if("updatePwdChannels".equals(method))
        {
            return updatePwdChannels(mapping, form, request, response);
        }
        else if("list".equals(method))
        {
            return list(mapping, form, request, response);
        }
        else if("offLine".equals(method))
        {
            return offLine(mapping, form, request, response);
        }
        else if("regain".equals(method))
        {
            return regain(mapping, form, request, response);
        }
        else
        {
            String forward = Constants.FORWARD_COMMON_FAILURE;
            this.saveMessagesValue(request, "对不起，您访问的路径不存在");
            return mapping.findForward(forward);
        }
    }


    private ActionForward list(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response)
    {
        String forward = "list";
        
        String channelsId = this.getParameter(request, "channelsId"); 

            // 得到当前渠道商信息
           OpenChannelsVO channelsVO = null;
           
        try
        {
            channelsVO = OpenChannelsBO.getInstance().queryChannelsVO(channelsId);
        }
        catch (BOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
           request.setAttribute("vo", channelsVO);
           if(this.getParameter(request, "flag")!= null && "channelMo".equals(this.getParameter(request, "flag"))){
               request.setAttribute("flag", "channelMo");
           }
           return mapping.findForward(forward);
    }


    public ActionForward queryOpenChannelsList(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request,
                                         HttpServletResponse response) throws BOException
    {
        String channelsId = this.getParameter(request, "channelsId");
        String channelsName = this.getParameter(request, "channelsName");
        
        OpenChannelsVO vo = new OpenChannelsVO();
        vo.setChannelsId(channelsId);
        vo.setChannelsName(channelsName);
        
        String forward = "queryOpenChannelsList";
        
        PageResult page = new PageResult(request);
        page.setPageSize(Integer.parseInt(PageSizeConstants.page_DEFAULT));
        
        try
        {
            OpenChannelsBO.getInstance().queryOpenChannelsList(page, vo);
        }
        catch (DAOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        request.setAttribute("channelsName", channelsName);
        request.setAttribute("PageResult", page);
        return mapping.findForward(forward);
    }


private ActionForward save(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response)
    {
        String forward = Constants.FORWARD_COMMON_FAILURE;

        OpenChannelsVO vo = new OpenChannelsVO();
        
        String channelsName = this.getParameter(request, "channelsName").trim();
      
        String parentChannelsId = this.getParameter(request, "parentChannelsId").trim();
        String parentChannelsName = this.getParameter(request, "parentChannelsName").trim();
        String channelsDesc = this.getParameter(request, "channelsDesc").trim();
        String channelsNo = this.getParameter(request, "channelsNo").trim();
      //新增的ID，传到后台数据库查询，是否重复，若重复，则不允许添加
        try
        {
            vo = OpenChannelsBO.getInstance().queryChannelsVOChannels(channelsNo);
        }
        catch (DAOException e2)
        {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        String channelsPwd = this.getParameter(request, "channelsPwd").trim();
        try
        {
            channelsPwd= getHexMD5Str(channelsPwd);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            LOG.debug("加密出错......"+e.getMessage());
        }
        String status = this.getParameter(request, "status").trim();
        
       
        String actionType = "新增渠道商";
        boolean actionResult = true;
        String actionDesc = "新增渠道商成功";
        
        if(vo == null){
            actionResult = false;
            actionDesc = "客户端渠道商账号已经存在！";
            this.actionLog(request,
                           actionType,
                           null,
                           actionResult,
                           actionDesc);
            
            this.saveMessagesValue(request, "客户端渠道商账号已经存在！");
            return mapping.findForward(forward);
        }
        
        vo.setChannelsName(channelsName);
        vo.setParentChannelsId(parentChannelsId);
        vo.setParentChannelsName(parentChannelsName);
        vo.setChannelsDesc(channelsDesc);
        vo.setChannelsNo(channelsNo);
        vo.setChannelsPwd(channelsPwd);
        vo.setStatus(status);

        try {
            // 用于新增客户端渠道
            OpenChannelsBO.getInstance().save(vo);

        } catch (BOException e) {
            log.error(e);
            
            // 写操作日志
            actionResult = false;
            actionDesc = "新增客户端渠道出错";
            this.actionLog(request,
                           actionType,
                           null,
                           actionResult,
                           actionDesc);
            
            this.saveMessagesValue(request, "新增渠道商出错");
            return mapping.findForward(forward);
        }

        // 写操作日志
        this.actionLog(request,
                       actionType,
                       null,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "新增渠道商成功");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request
                .setAttribute(Constants.PARA_GOURL,
                        "../channeladmin/queryOpenChannelsList.do?method=queryOpenChannelsList");
        return mapping.findForward(forward);
    }
    
    private ActionForward add(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response)
    {

        String forward = "add";
        String channelsName = this.getParameter(request, "channelsName");
        String parentChannelsId  = this.getParameter(request, "parentChannelsId");
        String parentChannelsName  = this.getParameter(request, "parentChannelsName");
        String channelsDesc = this.getParameter(request, "channelsDesc");
        String channelsNo = this.getParameter(request, "channelsNo");
        String channelsPwd = this.getParameter(request, "channelsPwd");
       
        String status = this.getParameter(request, "status");
        
        
        OpenChannelsVO vo = new OpenChannelsVO();
        
        vo.setChannelsName(channelsName);
        vo.setParentChannelsId(parentChannelsId);
        vo.setParentChannelsName(parentChannelsName);
        vo.setChannelsDesc(channelsDesc);
        vo.setChannelsNo(channelsNo);
        vo.setChannelsPwd(channelsPwd);
        vo.setStatus(status);
        
        request.setAttribute("vo", vo);
        
        return mapping.findForward(forward);
    }

    private ActionForward updatePwdChannels(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
    {
        
        String forward = "updatePwd";
        
        String channelsId = this.getParameter(request, "channelsId"); 
        

            // 得到当前账号信息
           OpenChannelsVO oldChannelsVO = null;
           
        try
        {
            oldChannelsVO = OpenChannelsBO.getInstance().queryChannelsVO(channelsId);
        }
        catch (BOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
           request.setAttribute("vo", oldChannelsVO);
           
           return mapping.findForward(forward);

    }
    
    
    private ActionForward updatePwd(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request,
                                    HttpServletResponse response)
    {

        String forward = Constants.FORWARD_COMMON_FAILURE;

        OpenChannelsVO vo = new OpenChannelsVO();
        String channelsId=this.getParameter(request, "channelsId").trim();
        String channelsPwd=this.getParameter(request, "channelsPwd").trim();
        try
        {
            channelsPwd= getHexMD5Str(channelsPwd);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            LOG.debug("加密出错......"+e.getMessage());
        }
        String actionType = "修改用户名密码";
        boolean actionResult = true;
        String actionDesc = "修改用户名密码成功";

        vo.setChannelsPwd(channelsPwd);
        vo.setChannelsId(channelsId);
        try {
            // 用于新增客户端渠道
            OpenChannelsBO.getInstance().updatePwd(vo);

        } catch (BOException e) {
            log.error(e);
            
            // 写操作日志
            actionResult = false;
            actionDesc = "修改用户名密码出错";
            this.actionLog(request,
                           actionType,
                           null,
                           actionResult,
                           actionDesc);
            
            this.saveMessagesValue(request, "修改用户名密码出错");
            return mapping.findForward(forward);
        }

        // 写操作日志
        this.actionLog(request,
                       actionType,
                       null,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "修改用户名密码成功");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request
        .setAttribute(Constants.PARA_GOURL,
                        "../channeladmin/queryOpenChannelsList.do?method=queryOpenChannelsList");
        return mapping.findForward(forward);
    }



    private ActionForward update(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
    {
        
        String forward = "update";
        
        String channelsId = this.getParameter(request, "channelsId"); 

            // 得到当前渠道商信息
           OpenChannelsVO oldChannelsVO = null;
           
        try
        {
            oldChannelsVO = OpenChannelsBO.getInstance().queryChannelsVO(channelsId);
        }
        catch (BOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
           request.setAttribute("vo", oldChannelsVO);
           
           return mapping.findForward(forward);

    }
    
   
    private ActionForward updateChannels(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response)
    {
        String forward = Constants.FORWARD_COMMON_FAILURE;

        OpenChannelsVO vo = new OpenChannelsVO();
        String channelsId=this.getParameter(request, "channelsId").trim();
        String channelsName = this.getParameter(request, "channelsName").trim();
        String channelsDesc = this.getParameter(request, "channelsDesc").trim();
        
        String actionType = "修改渠道商";
        boolean actionResult = true;
        String actionDesc = "修改渠道商成功";

        vo.setChannelsName(channelsName);
        vo.setChannelsDesc(channelsDesc);
        vo.setChannelsId(channelsId);
        try {
            // 用于新增客户端渠道
            OpenChannelsBO.getInstance().updateChannels(vo);

        } catch (BOException e) {
            log.error(e);
            
            // 写操作日志
            actionResult = false;
            actionDesc = "修改客户端渠道出错";
            this.actionLog(request,
                           actionType,
                           null,
                           actionResult,
                           actionDesc);
            
            this.saveMessagesValue(request, "修改渠道商出错");
            return mapping.findForward(forward);
        }

        // 写操作日志
        this.actionLog(request,
                       actionType,
                       null,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "修改渠道商成功");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request
                .setAttribute(Constants.PARA_GOURL,
                        "../channeladmin/queryOpenChannelsList.do?method=queryOpenChannelsList");
        return mapping.findForward(forward);
    }
    
    
    private ActionForward regain(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
    {

        // TODO Auto-generated method stub
        return null;
    }


    private ActionForward offLine(ActionMapping mapping, ActionForm form,
                                  HttpServletRequest request,
                                  HttpServletResponse response)
    {

        String forward = Constants.FORWARD_COMMON_FAILURE;

        OpenChannelsVO vo = new OpenChannelsVO();
        String channelsId=this.getParameter(request, "channelsId").trim();
        String status=this.getParameter(request, "status").trim();
       
        String actionType = "修改渠道商状态";
        boolean actionResult = true;
        String actionDesc = "修改渠道商状态成功";

        if("0".equals(status)){
            vo.setStatus("1");
        }else{
            vo.setStatus("0");
        }
        vo.setChannelsId(channelsId);
        try {
            
            OpenChannelsBO.getInstance().offLine(vo);

        } catch (BOException e) {
            log.error(e);
            
            // 写操作日志
            actionResult = false;
            actionDesc = "修改渠道商状态出错";
            this.actionLog(request,
                           actionType,
                           null,
                           actionResult,
                           actionDesc);
            
            this.saveMessagesValue(request, "修改渠道商状态出错");
            return mapping.findForward(forward);
        }

        // 写操作日志
        this.actionLog(request,
                       actionType,
                       null,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "修改渠道商状态成功");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request
        .setAttribute(Constants.PARA_GOURL,
                        "../channeladmin/queryOpenChannelsList.do?method=queryOpenChannelsList");
        return mapping.findForward(forward);
    }
    
}
