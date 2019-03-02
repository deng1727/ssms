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
     * ��¼��־ʵ��
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

        String actionType = "����������Ӫ����";
        boolean actionResult = true;
        String actionDesc = "����������Ӫ�����ɹ�";

      //������ID��������̨���ݿ��ѯ���Ƿ��ظ������ظ������������
        vo = OpenOperationChannelBo.getInstance().queryOpenOperationChannelVo(channelId);
        if(vo==null){
            actionResult = false;
            actionDesc = "������Ӫ����ID�Ѿ����ڣ�";
            this.actionLog(request,
                           actionType,
                           null,
                           actionResult,
                           actionDesc);
            
            this.saveMessagesValue(request, "������Ӫ����ID�Ѿ����ڣ�");
            return mapping.findForward(forward);
        }
        vo.setChannelId(channelId);
        vo.setChannelsId(channelsId);
        vo.setCreateDate(new Date());

        try {
            // ��������������Ӫ����
            OpenOperationChannelBo.getInstance().saveOpenOperationChannelVo(vo);

        } catch (BOException e) {
            logger.error(e);
            
            // д������־
            actionResult = false;
            actionDesc = "����������Ӫ��������";
            this.actionLog(request,
                           actionType,
                           null,
                           actionResult,
                           actionDesc);
            
            this.saveMessagesValue(request, "����������Ӫ��������");
            return mapping.findForward(forward);
        }

        // д������־
        this.actionLog(request,
                       actionType,
                       null,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "����������Ӫ�������óɹ�");
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

        //��ȡ��ѯ����
        String channelId = this.getParameter(request, "channelId");
        
        //��ȡ��������������ID
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
