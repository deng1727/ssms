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
     * ��¼��־ʵ��
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
            this.saveMessagesValue(request, "�Բ��������ʵ�·��������");
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

        String actionType = "��������������";
        boolean actionResult = true;
        String actionDesc = "�������������óɹ�";

        vo.setChannelsId(channelsId);
        vo.setCategoryId(categoryId);

        try {
        	//���жϸû���ID�Ƿ������������Ļ����¡��������ڣ�������ӡ�
        	if(!CategoryQueryBO.getInstance().isExistInCategory(categoryId)){
        		actionType = "�û���ID�������ڶ�Ӧ�Ļ�����";
        		actionDesc = "�û���ID�������ڶ�Ӧ�Ļ�����";
        		this.saveMessagesValue(request, "�û���ID�������ڶ�Ӧ�Ļ�����,���������롣");
        		request
                .setAttribute(Constants.PARA_GOURL,
                        "../channeladmin/openChannelsCategory.do?method=add&channelsId="+channelsId);
        		return mapping.findForward(forward);
        		
        	}
            // ���������ͻ�������
            OpenChannelsCategoryBo.getInstance().saveOpenChannelsCategoryVo(vo);

        } catch (BOException e) {
            logger.error(e);
            
            // д������־
            actionResult = false;
            actionDesc = "���������ó���";
            this.actionLog(request,
                           actionType,
                           null,
                           actionResult,
                           actionDesc);
            
            this.saveMessagesValue(request, "���������ó���");
            return mapping.findForward(forward);
        }

        // д������־
        this.actionLog(request,
                       actionType,
                       null,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "���������óɹ�");
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
