package com.aspire.ponaadmin.web.taccode.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.usermanager.UserManagerBO;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.channelUser.util.Common;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.repository.web.DataImportForm;
import com.aspire.ponaadmin.web.taccode.bo.TacCodeBO;
import com.aspire.ponaadmin.web.taccode.vo.TacVO;

/**
 * <p>Title:TAC������ </p>
 * <p>Description: TAC����ѯ�����롢ɾ����ز���</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * @author baojun
 * @version 
 */
public class TacCodeAction  extends BaseAction{

	/**
     * ��־����
     */
    private static final JLogger LOG = LoggerFactory.getLogger(TacCodeAction.class);
    
    /* (non-Javadoc)
     * @see com.aspire.ponaadmin.web.BaseAction#doPerform(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {
        LOG.debug("doPerform()");

        if(request.getSession() == null || UserManagerBO.getInstance()
    			.getUserSessionVO(request.getSession()) == null){
    		this.saveMessages(request, "�û�δ��½���ߵ�½�ѳ�ʱ��");
    		request.setAttribute(Constants.PARA_GOURL, "index.jsp");
    		 request.setAttribute("loginResult", new Integer(Common.LOGINRESULT));
    		return mapping.findForward(Common.LOGIN_HINT_CHANNELUSER);
    	}
        
        //���������ͣ�0-��ʾ�ն˹�˾��1-���ն˹�˾
        //TAC���������ն˹�˾���еĹ��ܣ����ն˹�˾û��Ȩ�޷��ʸù���
        if(!"0".equals(UserManagerBO.getInstance()
    			.getUserSessionVO(request.getSession()).getChannel().getMoType())){
        	String forward = Constants.FORWARD_COMMON_FAILURE;
            this.saveMessagesValue(request, "�Բ�����û��Ȩ�޷��ʣ�");
            return mapping.findForward(forward);
        }
        
        // �������л�ȡ��������
        String perType = this.getParameter(request, "perType").trim();

        if ("query".equals(perType))
        {
            return query(mapping, form, request, response);
        }
        else if("remove".equals(perType))
        {
            return remove(mapping, form, request, response);
        }
        else if("importData".equals(perType))
        {
            return importData(mapping, form, request, response);
        }
        else
        {
            String forward = Constants.FORWARD_COMMON_FAILURE;
            this.saveMessagesValue(request, "�Բ��������ʵ�·��������");
            return mapping.findForward(forward);
        }
    }
    
    /**
     * ��ѯTAC����б�
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward query(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws BOException
    {
        LOG.debug("doPerform()");
        String forward = "query";
        PageResult page = new PageResult(request);
        
        String tacCode = this.getParameter(request, "tacCode").trim();
        String brand = this.getParameter(request, "brand").trim(); 
        String device = this.getParameter(request, "device").trim();
        String channelId = this.getParameter(request, "channelId").trim();
        String channelName = this.getParameter(request, "channelName").trim();
        TacVO vo = new TacVO();
        vo.setTacCode(tacCode);
        vo.setBrand(brand);
        vo.setDevice(device);
        vo.setChannelId(channelId);
        vo.setChannelName(channelName);
        
        try
        {
            TacCodeBO.getInstance().queryTacCodeList(page, vo);
        }
        catch (BOException e)
        {
            LOG.error(e);
            this.saveMessagesValue(request, "��ѯTAC����б����");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        request.setAttribute("PageResult", page);
        request.setAttribute("tacCode", tacCode);
        request.setAttribute("brand", brand);
        request.setAttribute("device", device);
        request.setAttribute("channelId", channelId);
        request.setAttribute("channelName", channelName);

        return mapping.findForward(forward);
    }
    
    /**
     * �����Ƴ�ָ��TAC��
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward remove(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response)
                    throws BOException
    {
        String forward = "remove";

        // �������л�ȡ�ص����ID
        String id = this.getParameter(request, "id").trim(); 
        String tacCode = this.getParameter(request, "tacCode").trim();
        
		String actionType = "����ɾ��ָ��TAC��";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = tacCode;
        if (LOG.isDebugEnabled())
        {
            LOG.debug("����ɾ��ָ��TAC��");
        }

        try
        {
        	TacCodeBO.getInstance().delByTacCode(id,tacCode);
        }
        catch (BOException e)
        {
			// д������־
			actionResult = false;
			actionDesc = "����ɾ��ָ��TAC�����!";
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
            LOG.error(e);
            this.saveMessagesValue(request, "����ɾ��ָ��TAC�����");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
		// д������־
		actionDesc = "ɾ��TAC��ɹ�!";
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "ɾ��TAC��ɹ�");
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "tacCode.do?perType=query");
        return mapping.findForward(forward);
    }
    
    /**
     * ����TAC���б�
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward importData(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws BOException
    {
        String forward = "importData";
        String ret = "";

        if (LOG.isDebugEnabled())
        {
            LOG.debug("action����ʼ");
        }
        DataImportForm iForm=(DataImportForm)form;
        FormFile dataFile = iForm.getDataFile();
        
		String actionType = "���ڵ���TAC���б�";
		boolean actionResult = true;
		String actionDesc = "";
		String actionTarget = dataFile.getFileName();
		
        
//      У���ļ���׺��
        if(!iForm.checkFileNameExtension(new String[]{"xls","xlsx"}))
        {
            this.saveMessages(request, "�ļ���׺������");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        } 
        
        try
        {
            // ����TAC����б�
            ret = TacCodeBO.getInstance().importTacCode(dataFile);
        }
        catch (BOException e)
        {
			// д������־
			actionResult = false;
			actionDesc = e.getMessage();
	        this.actionLog(request,
	                       actionType,
	                       actionTarget,
	                       actionResult,
	                       actionDesc);
	        
	        
            LOG.error(e);
            
            this.saveMessagesValue(request, "���ڵ���TAC���б����,���鵼��������ļ��Ƿ�����");
            
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
		// д������־
		actionDesc = "��ӵ���TAC���б�����ɹ���" + ret;
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        
        this.saveMessagesValue(request, "��ӵ���TAC���б�����ɹ���" + ret);
        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL,
                             "tacCode.do?perType=query");
        return mapping.findForward(forward);
    }
	
}
