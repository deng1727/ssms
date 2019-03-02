package com.aspire.ponaadmin.web.channeladmin.action;

import java.io.UnsupportedEncodingException;
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
import com.aspire.ponaadmin.web.channeladmin.bo.OpenChannelMoBo;
import com.aspire.ponaadmin.web.channeladmin.vo.OpenChannelMoVo;
import com.aspire.ponaadmin.web.channeladmin.vo.OpenChannelsVO;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.system.PageSizeConstants;


public class OpenChannelMoAction extends BaseAction
{
    /**
     * ��¼��־ʵ��
     */
    private static final JLogger logger = LoggerFactory.getLogger(OpenChannelMoAction.class);

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
            }else if("queryList".equals(method)){
                return queryList(mapping, form, request, response);
            }else{
                String forward = Constants.FORWARD_COMMON_FAILURE;
                this.saveMessagesValue(request, "�Բ��������ʵ�·��������");
                return mapping.findForward(forward); 
            } 
        }
        catch (DAOException e)
        {
            logger.debug("��ѯ����ʧ�ܣ�", e);
            e.printStackTrace();
            return null;
        }
        
    }

    private ActionForward queryList(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws DAOException
    {

        //��ȡ��ѯ����
        String channelsId = this.getParameter(request, "channelsId");
        String channelsName = this.getParameter(request, "channelsName");
        
        OpenChannelsVO vo = new OpenChannelsVO();
        vo.setChannelsId(channelsId);
        vo.setChannelsName(channelsName);
        
        String forward = "queryList";
        
        PageResult page = new PageResult(request);
        page.setPageSize(Integer.parseInt(PageSizeConstants.page_DEFAULT));
        
        OpenChannelMoBo.getInstance().queryList(page,vo);
        
        request.setAttribute("channelsId", channelsId);
        request.setAttribute("channelsName", channelsName);
        request.setAttribute("PageResult", page);
        return mapping.findForward(forward);
    }

    private ActionForward del(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response)
    {
        String channelId = this.getParameter(request, "channelId");
        String channelsId = this.getParameter(request, "channelsId");
        String forward = Constants.FORWARD_COMMON_FAILURE;
        if(!"".equals(channelId)){
            OpenChannelMoBo.getInstance().delOpenChannelMoVoById(channelId);
        }
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request
                .setAttribute(Constants.PARA_GOURL,
                        "../channeladmin/openChannelMoList.do?method=list&channelsId="+channelsId);
        return mapping.findForward(forward);
    }

    private ActionForward save(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response)
    {
        String channelsId = this.getParameter(request, "channelsId");
        String forward = Constants.FORWARD_COMMON_FAILURE;

        OpenChannelMoVo vo = new OpenChannelMoVo();
        String channelId = this.getParameter(request, "channelId").trim();
        //������ID��������̨���ݿ��ѯ���Ƿ��ظ������ظ������������
        vo = OpenChannelMoBo.getInstance().queryOpenChannelMoVo(channelId);
        String channelName = this.getParameter(request, "channelName").trim();
        try
        {
            channelName = new String(channelName.getBytes("iso8859-1"));
        }
        catch (UnsupportedEncodingException e1)
        {
            e1.printStackTrace();
        }

        String actionType = "�����ͻ�������";
        boolean actionResult = true;
        String actionDesc = "�����ͻ��������ɹ�";

        if(vo == null){
            actionResult = false;
            actionDesc = "�ͻ�������ID�Ѿ����ڣ�";
            this.actionLog(request,
                           actionType,
                           null,
                           actionResult,
                           actionDesc);
            
            this.saveMessagesValue(request, "�ͻ�������ID�Ѿ����ڣ�");
            return mapping.findForward(forward);
        }
        vo.setChannelId(channelId);
        vo.setChannelName(channelName);
        vo.setChannelsId(channelsId);
        vo.setCreateDate(new Date());

        try {
            // ���������ͻ�������
            OpenChannelMoBo.getInstance().saveOpenChannelMoVo(vo);

        } catch (BOException e) {
            logger.error(e);
            
            // д������־
            actionResult = false;
            actionDesc = "�����ͻ�����������";
            this.actionLog(request,
                           actionType,
                           null,
                           actionResult,
                           actionDesc);
            
            this.saveMessagesValue(request, "�����ͻ�����������");
            return mapping.findForward(forward);
        }

        // д������־
        this.actionLog(request,
                       actionType,
                       null,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "�����ͻ����������óɹ�");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request
                .setAttribute(Constants.PARA_GOURL,
                        "../channeladmin/openChannelMoList.do?method=list&channelsId="+channelsId);
        return mapping.findForward(forward);
    }

    private ActionForward list(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response) throws DAOException
    {

        //��ȡ��ѯ����
        String channelId = this.getParameter(request, "channelId");
        String channelName = this.getParameter(request, "channelName");
        
        //��ȡ��������������ID
        String channelsId = this.getParameter(request, "channelsId");
        
        OpenChannelMoVo vo = new OpenChannelMoVo();
        vo.setChannelId(channelId);
        vo.setChannelName(channelName);
        vo.setChannelsId(channelsId);
        
        String forward = "list";
        
        PageResult page = new PageResult(request);
        page.setPageSize(Integer.parseInt(PageSizeConstants.page_DEFAULT));
        
        OpenChannelMoBo.getInstance().listOpenChannelMoVo(page,vo);
        
        request.setAttribute("channelId", channelId);
        request.setAttribute("channelsId", channelsId);
        request.setAttribute("channelName", channelName);
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
