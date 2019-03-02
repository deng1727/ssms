package com.aspire.ponaadmin.web.channelUser.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.appinfosyn.AppXmlTask;
import com.aspire.dotcard.appinfosyn.SynBaseAppXml;
import com.aspire.dotcard.baseVideo.bo.BaseVideoFileBO;
import com.aspire.dotcard.basecolorcomic.sync.BaseColorComicTask;
import com.aspire.dotcard.basemusic.BaseMusicFullBO;
import com.aspire.dotcard.syncAndroid.autosync.task.AutoSyncTask;
import com.aspire.dotcard.syncData.bo.DataSyncBO;
import com.aspire.dotcard.syncData.dao.DataSyncDAO;
import com.aspire.ponaadmin.common.usermanager.UserManagerBO;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.category.SynOpenOperationData;
import com.aspire.ponaadmin.web.channelUser.util.Common;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.system.Config;
import com.aspire.ponaadmin.web.util.HttpUtil;

/**
 * <p>����ĳ�����µ�����action��</p>
 * <p>����ĳ�����µ�����action��.type = operation Ϊҵ���룻type = content Ϊ���ݵ���</p>
 * <p>
 * Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights
 * Reserved
 * </p>
 * 
 * @author ����
 * @version 1.0.0.0
 */
public class DataImportAction extends BaseAction
{

    /**
     * ��־����
     */
    private static final JLogger LOG = LoggerFactory.getLogger(DataImportAction.class);

    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {
    	if(request.getSession() == null || UserManagerBO.getInstance()
    			.getUserSessionVO(request.getSession()) == null){
    		this.saveMessages(request, "�û�δ��½���ߵ�½�ѳ�ʱ��");
    		request.setAttribute(Constants.PARA_GOURL, "index.jsp");
    		 request.setAttribute("loginResult", new Integer(Common.LOGINRESULT));
    		return mapping.findForward(Common.LOGIN_HINT_CHANNELUSER);
    	}
        if (LOG.isDebugEnabled())
        {
            LOG.debug("DataImportAction doPerform()");
        }
        //���巵��ҳ��
        String forward =Constants.FORWARD_COMMON_SUCCESS ;

        //ȡ����type
        String type =  this.getParameter(request, "type");
        LOG.debug("type = "+type);
        
        //type = operation Ϊҵ���룻type = content Ϊ���ݵ���
        if ("operation".equals(type))
        {
            //ҵ����
            forward =  syncService(mapping,form,request,response);
        }
        else if("content".equals(type))
        {
            //���ݵ���
            forward = syncContent(mapping,form,request,response);
        }   
        else if("waken".equals(type))
        {
            //���������Ż�����ͬ������
            forward = syncWaken(mapping,form,request,response);
        }
        else if("open".equals(type))
        {
            // ͬ��������Ӫ����
            forward = syncOpen(mapping,form,request,response);
        }   
        else if("countNodeNum".equals(type))
        {
            // ͬ��������Ӫ����
            forward = countNodeNum(mapping,form,request,response);
        }  
        else if("colorComic".equals(type))
        {
            // ͬ�����ز���Ԫ����
            forward = synColorComic(mapping,form,request,response);
        }
        else if("autoSyncCategory".equals(type))
        {
            // ����������ͬ���Զ����»���
            forward = autoSyncCategory(mapping,form,request,response);
        }
        else if("fullMusicData".equals(type))
        {
            // ͬ����������
            forward = synFullMic(mapping,form,request,response);
        }
        else if("wpInfo".equals(type))
        {
            // ͬ����������
            forward = synWpInfo(mapping,form,request,response);
        }

        return mapping.findForward(forward);
    }

    /**
     * ҵ���봦��
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    private String syncService(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("DataImportAction syncService()");
		}

		String actionType = "��ʼִ��ҵ����";
		String actionTarget = "";
		boolean actionResult = true;
		String actionDesc = "��ʼִ��ҵ����";
		this.actionLog(request, actionType, actionTarget, actionResult,actionDesc);
		
		
		
        DataSyncBO.getInstance().syncService();
		this.saveMessagesValue(request, "ҵ�������!");
		return Constants.FORWARD_COMMON_SUCCESS;

	}

    /**
	 * ���ݵ��봦��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws BOException
	 */
    private String syncContent(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request,
                                      HttpServletResponse response)
                    throws BOException
    {

        if (LOG.isDebugEnabled())
        {
            LOG.debug("DataImportAction syncContent()");
        }
        
        String classType = this.getParameter(request, "classType");
        String synclassResourceType = this.getParameter(request, "synclassResourceType");
        String isSyn = this.getParameter(request, "isSyn");

        
        
        String forward = Constants.FORWARD_COMMON_SUCCESS;
        String actionType = "��ʼִ�����ݵ���";
        String actionTarget = "";
        boolean actionResult = true;
        String actionDesc = "��ʼִ�����ݵ���,��������:"
        	+ ("1".equals(classType)?"ȫ��":"����")
        	+",�����ϵ:"
        	+("1".equals(synclassResourceType)?"����":("2".equals(synclassResourceType)?"ȫ��":"��ͬ��"))
        	+",�Ƿ����:"+
        	("0".equals(isSyn)?"��":"��");
        this.actionLog(request,
                actionType,
                actionTarget,
                actionResult,
                actionDesc);
        
        


        if (null != classType && classType.equals("1"))
        {
            // ȫ��ͬ�� ����SyncBO�е�syncContent������
            DataSyncBO.getInstance().syncConFull(synclassResourceType, isSyn);
        }
        else if (null != classType && classType.equals("0"))
        {
            // ����ͬ�� ����SyncBO�е�syncConAdd ������
            DataSyncBO.getInstance().syncConAdd(synclassResourceType, isSyn);
        }
        else
        {
            LOG.info("parm is illegal");
        }

        // ����SyncBO�е�syncContent������
        // DataSyncBO.getInstance().syncContent();
        // ����һ������
        DataSyncDAO.getInstance().updateRemarks();
        // ���ݵ������
        this.saveMessagesValue(request, "���ݵ������!");
      
        forward = Constants.FORWARD_COMMON_SUCCESS;


        return forward;
    }
    
    /**
     * ͬ��������Ӫ���ݷ���
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    private String syncOpen(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request,
                                      HttpServletResponse response)
                    throws BOException
    {
		if (LOG.isDebugEnabled())
		{
			LOG.debug("DataImportAction syncOpen()");
		}

		String actionType = "��ʼִ��ͬ��������Ӫ����";
		String actionTarget = "";
		boolean actionResult = true;
		String actionDesc = "��ʼִ��ͬ��������Ӫ����";

		// ����ͬ��������Ӫ���� add by wml 20120823
		SynOpenOperationData.getInstance().start();

		this.actionLog(request, actionType, actionTarget, actionResult,
				actionDesc);
		this.saveMessagesValue(request, "ͬ��������Ӫ�������!");
		return Constants.FORWARD_COMMON_SUCCESS;
	}
    
    /**
     * ͳ����Ƶ����Ŀ������Ŀ�½�Ŀ��
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    private String countNodeNum(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request,
                                      HttpServletResponse response)
                    throws BOException
    {
		if (LOG.isDebugEnabled())
		{
			LOG.debug("DataImportAction countNodeNum()");
		}

		String actionType = "��ʼִ��ͳ����Ƶ����Ŀ������Ŀ�½�Ŀ��";
		String actionTarget = "";
		boolean actionResult = true;
		String actionDesc = "��ʼִ��ͳ����Ƶ����Ŀ������Ŀ�½�Ŀ��";

		// ͳ����Ƶ����Ŀ������Ŀ�½�Ŀ�� add by wml 20120823
		BaseVideoFileBO.getInstance().updateCategoryNodeNum();

		this.actionLog(request, actionType, actionTarget, actionResult,
				actionDesc);
		this.saveMessagesValue(request, "ͬ��ͳ����Ƶ����Ŀ������Ŀ�½�Ŀ�����!");
		return Constants.FORWARD_COMMON_SUCCESS;
	}
    
    /**
	 * ���������Ż�����ͬ������
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws BOException
	 */
    private String syncWaken(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request,
                                      HttpServletResponse response)
                    throws BOException
    {

        if (LOG.isDebugEnabled())
        {
            LOG.debug("DataImportAction syncWaken()");
        }

        String forward = Constants.FORWARD_COMMON_FAILURE;
        String actionType = "���������Ż�����ͬ������";
        String actionTarget = "";
        String actionDesc = "";
        boolean actionResult = true;


        String mo_url = Config.getInstance().getModuleConfig().getItemValue("MO_URL_Sync");
        String www_url = Config.getInstance().getModuleConfig().getItemValue("WWW_URL_Sync");
        String wap_url = Config.getInstance().getModuleConfig().getItemValue("WAP_URL_Sync");
        String search_url = Config.getInstance().getModuleConfig().getItemValue("SEARCH_URL_Sync");
        StringBuffer sb = new StringBuffer();

        // ֪ͨMO�Ż�
        sb.append("MO�Ż�").append(waken(mo_url));
        
        // ֪ͨWWW�Ż�
        sb.append("WWW�Ż�").append(waken(www_url));
        
        // ֪ͨWAP�Ż�
        sb.append("WAP�Ż�").append(waken(wap_url));

        // ֪ͨ�����Ż�
        sb.append("�����Ż�").append(waken(search_url));

        this.saveMessagesValue(request, sb.toString());
        actionDesc = sb.toString();
        actionResult = true;
        forward = Constants.FORWARD_COMMON_SUCCESS;

        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        return forward;
    }
    
    /**
     * ֪ͨ�Ż�����
     * @param url �Ż�url
     * @return
     */
    private String waken(String url)
    {
        int code = 0;
        
        try
        {
            code = HttpUtil.getResponseCodeFromURL(url, "utf-8");
        }
        catch (Exception e)
        {
            LOG.info("���������Ż�����ͬ������ʧ��");
        }
        
        if(code == 200)
        {
            return "֪ͨ�ɹ�. " + "<br>";
        }
        else
        {
            return " ֪ͨʧ��. url=" + url + "<br>";
        }
    }
    
    /**
     * ͬ�����ز���Ԫ����
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    private String synColorComic(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request,
                                      HttpServletResponse response)
                    throws BOException
    {
		if (LOG.isDebugEnabled())
		{
			LOG.debug("DataImportAction synColorComic()");
		}

		String actionType = "��ʼִ��ͬ�����ز���Ԫ����";
		String actionTarget = "";
		boolean actionResult = true;
		String actionDesc = "��ʼִ��ͬ�����ز���Ԫ����";

		new BaseColorComicTask().run();

		this.actionLog(request, actionType, actionTarget, actionResult,
				actionDesc);
		this.saveMessagesValue(request, "ͬ�����ز���Ԫ�������!");
		return Constants.FORWARD_COMMON_SUCCESS;
	}
    
    /**
     * ����������ͬ���Զ����»���
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    private String autoSyncCategory(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request,
                                      HttpServletResponse response)
                    throws BOException
    {
		if (LOG.isDebugEnabled())
		{
			LOG.debug("DataImportAction autoSyncCategory()");
		}

		String actionType = "��ʼִ������������ͬ���Զ����»���";
		String actionTarget = "";
		boolean actionResult = true;
		String actionDesc = "��ʼִ������������ͬ���Զ����»���";

		// ����������ͬ���Զ����»���
		new AutoSyncTask().run();

		this.actionLog(request, actionType, actionTarget, actionResult,
				actionDesc);
		this.saveMessagesValue(request, "����������ͬ���Զ����»������!");
		return Constants.FORWARD_COMMON_SUCCESS;
	}
    /**
     * ͬ��ȫ����������
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
	private String synFullMic(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request,
                                      HttpServletResponse response)
                    throws BOException
    {
		if (LOG.isDebugEnabled())
		{
			LOG.debug("DataImportAction synFullMic()");
		}

		String actionType = "��ʼִ��ȫ����������";
		String actionTarget = "";
		boolean actionResult = true;
		String actionDesc = "��ʼִ��ȫ����������";

		new BaseMusicFullBO().run();

		this.actionLog(request, actionType, actionTarget, actionResult,
				actionDesc);
		this.saveMessagesValue(request, "ͬ��ȫ����������!");
		return Constants.FORWARD_COMMON_SUCCESS;
	}
	  /**
     * wpӦ������
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
	private String synWpInfo(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request,
                                      HttpServletResponse response)
                    throws BOException
    {
		if (LOG.isDebugEnabled())
		{
			LOG.debug("DataImportAction synWpInfo()");
		}

		String actionType = "��ʼִ��wpӦ�����ݵ���";
		String actionTarget = "";
		boolean actionResult = true;
		String actionDesc = "��ʼִ��wpӦ�����ݵ���";
		new AppXmlTask().run();

		this.actionLog(request, actionType, actionTarget, actionResult,
				actionDesc);
		this.saveMessagesValue(request, "wpӦ�����ݵ������");
		return Constants.FORWARD_COMMON_SUCCESS;
	}
}
