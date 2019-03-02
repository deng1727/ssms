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
 * <p>增加某分类下的内容action类</p>
 * <p>增加某分类下的内容action类.type = operation 为业务导入；type = content 为内容导入</p>
 * <p>
 * Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights
 * Reserved
 * </p>
 * 
 * @author 张敏
 * @version 1.0.0.0
 */
public class DataImportAction extends BaseAction
{

    /**
     * 日志引用
     */
    private static final JLogger LOG = LoggerFactory.getLogger(DataImportAction.class);

    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {
    	if(request.getSession() == null || UserManagerBO.getInstance()
    			.getUserSessionVO(request.getSession()) == null){
    		this.saveMessages(request, "用户未登陆或者登陆已超时！");
    		request.setAttribute(Constants.PARA_GOURL, "index.jsp");
    		 request.setAttribute("loginResult", new Integer(Common.LOGINRESULT));
    		return mapping.findForward(Common.LOGIN_HINT_CHANNELUSER);
    	}
        if (LOG.isDebugEnabled())
        {
            LOG.debug("DataImportAction doPerform()");
        }
        //定义返回页面
        String forward =Constants.FORWARD_COMMON_SUCCESS ;

        //取参数type
        String type =  this.getParameter(request, "type");
        LOG.debug("type = "+type);
        
        //type = operation 为业务导入；type = content 为内容导入
        if ("operation".equals(type))
        {
            //业务导入
            forward =  syncService(mapping,form,request,response);
        }
        else if("content".equals(type))
        {
            //内容导入
            forward = syncContent(mapping,form,request,response);
        }   
        else if("waken".equals(type))
        {
            //激活三大门户调用同步方法
            forward = syncWaken(mapping,form,request,response);
        }
        else if("open".equals(type))
        {
            // 同步开放运营数据
            forward = syncOpen(mapping,form,request,response);
        }   
        else if("countNodeNum".equals(type))
        {
            // 同步开放运营数据
            forward = countNodeNum(mapping,form,request,response);
        }  
        else if("colorComic".equals(type))
        {
            // 同步基地彩漫元数据
            forward = synColorComic(mapping,form,request,response);
        }
        else if("autoSyncCategory".equals(type))
        {
            // 向数据中心同步自动更新货架
            forward = autoSyncCategory(mapping,form,request,response);
        }
        else if("fullMusicData".equals(type))
        {
            // 同步音乐数据
            forward = synFullMic(mapping,form,request,response);
        }
        else if("wpInfo".equals(type))
        {
            // 同步音乐数据
            forward = synWpInfo(mapping,form,request,response);
        }

        return mapping.findForward(forward);
    }

    /**
     * 业务导入处理
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

		String actionType = "开始执行业务导入";
		String actionTarget = "";
		boolean actionResult = true;
		String actionDesc = "开始执行业务导入";
		this.actionLog(request, actionType, actionTarget, actionResult,actionDesc);
		
		
		
        DataSyncBO.getInstance().syncService();
		this.saveMessagesValue(request, "业务导入完成!");
		return Constants.FORWARD_COMMON_SUCCESS;

	}

    /**
	 * 内容导入处理
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
        String actionType = "开始执行内容导入";
        String actionTarget = "";
        boolean actionResult = true;
        String actionDesc = "开始执行内容导入,内容数据:"
        	+ ("1".equals(classType)?"全量":"增量")
        	+",适配关系:"
        	+("1".equals(synclassResourceType)?"增量":("2".equals(synclassResourceType)?"全量":"不同步"))
        	+",是否紧急:"+
        	("0".equals(isSyn)?"否":"是");
        this.actionLog(request,
                actionType,
                actionTarget,
                actionResult,
                actionDesc);
        
        


        if (null != classType && classType.equals("1"))
        {
            // 全量同步 调用SyncBO中的syncContent方法；
            DataSyncBO.getInstance().syncConFull(synclassResourceType, isSyn);
        }
        else if (null != classType && classType.equals("0"))
        {
            // 增量同步 调用SyncBO中的syncConAdd 方法；
            DataSyncBO.getInstance().syncConAdd(synclassResourceType, isSyn);
        }
        else
        {
            LOG.info("parm is illegal");
        }

        // 调用SyncBO中的syncContent方法；
        // DataSyncBO.getInstance().syncContent();
        // 更新一下评分
        DataSyncDAO.getInstance().updateRemarks();
        // 内容导入完成
        this.saveMessagesValue(request, "内容导入完成!");
      
        forward = Constants.FORWARD_COMMON_SUCCESS;


        return forward;
    }
    
    /**
     * 同步开放运营数据方法
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

		String actionType = "开始执行同步开放运营数据";
		String actionTarget = "";
		boolean actionResult = true;
		String actionDesc = "开始执行同步开放运营数据";

		// 用于同步开放运营数据 add by wml 20120823
		SynOpenOperationData.getInstance().start();

		this.actionLog(request, actionType, actionTarget, actionResult,
				actionDesc);
		this.saveMessagesValue(request, "同步开放运营数据完成!");
		return Constants.FORWARD_COMMON_SUCCESS;
	}
    
    /**
     * 统计视频子栏目数与栏目下节目数
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

		String actionType = "开始执行统计视频子栏目数与栏目下节目数";
		String actionTarget = "";
		boolean actionResult = true;
		String actionDesc = "开始执行统计视频子栏目数与栏目下节目数";

		// 统计视频子栏目数与栏目下节目数 add by wml 20120823
		BaseVideoFileBO.getInstance().updateCategoryNodeNum();

		this.actionLog(request, actionType, actionTarget, actionResult,
				actionDesc);
		this.saveMessagesValue(request, "同步统计视频子栏目数与栏目下节目数完成!");
		return Constants.FORWARD_COMMON_SUCCESS;
	}
    
    /**
	 * 激活三大门户调用同步方法
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
        String actionType = "激活三大门户调用同步方法";
        String actionTarget = "";
        String actionDesc = "";
        boolean actionResult = true;


        String mo_url = Config.getInstance().getModuleConfig().getItemValue("MO_URL_Sync");
        String www_url = Config.getInstance().getModuleConfig().getItemValue("WWW_URL_Sync");
        String wap_url = Config.getInstance().getModuleConfig().getItemValue("WAP_URL_Sync");
        String search_url = Config.getInstance().getModuleConfig().getItemValue("SEARCH_URL_Sync");
        StringBuffer sb = new StringBuffer();

        // 通知MO门户
        sb.append("MO门户").append(waken(mo_url));
        
        // 通知WWW门户
        sb.append("WWW门户").append(waken(www_url));
        
        // 通知WAP门户
        sb.append("WAP门户").append(waken(wap_url));

        // 通知搜索门户
        sb.append("搜索门户").append(waken(search_url));

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
     * 通知门户方法
     * @param url 门户url
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
            LOG.info("激活三大门户调用同步方法失败");
        }
        
        if(code == 200)
        {
            return "通知成功. " + "<br>";
        }
        else
        {
            return " 通知失败. url=" + url + "<br>";
        }
    }
    
    /**
     * 同步基地彩漫元数据
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

		String actionType = "开始执行同步基地彩漫元数据";
		String actionTarget = "";
		boolean actionResult = true;
		String actionDesc = "开始执行同步基地彩漫元数据";

		new BaseColorComicTask().run();

		this.actionLog(request, actionType, actionTarget, actionResult,
				actionDesc);
		this.saveMessagesValue(request, "同步基地彩漫元数据完成!");
		return Constants.FORWARD_COMMON_SUCCESS;
	}
    
    /**
     * 向数据中心同步自动更新货架
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

		String actionType = "开始执行向数据中心同步自动更新货架";
		String actionTarget = "";
		boolean actionResult = true;
		String actionDesc = "开始执行向数据中心同步自动更新货架";

		// 向数据中心同步自动更新货架
		new AutoSyncTask().run();

		this.actionLog(request, actionType, actionTarget, actionResult,
				actionDesc);
		this.saveMessagesValue(request, "向数据中心同步自动更新货架完成!");
		return Constants.FORWARD_COMMON_SUCCESS;
	}
    /**
     * 同步全量音乐数据
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

		String actionType = "开始执行全量音乐数据";
		String actionTarget = "";
		boolean actionResult = true;
		String actionDesc = "开始执行全量音乐数据";

		new BaseMusicFullBO().run();

		this.actionLog(request, actionType, actionTarget, actionResult,
				actionDesc);
		this.saveMessagesValue(request, "同步全量音乐数据!");
		return Constants.FORWARD_COMMON_SUCCESS;
	}
	  /**
     * wp应用数据
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

		String actionType = "开始执行wp应用数据导入";
		String actionTarget = "";
		boolean actionResult = true;
		String actionDesc = "开始执行wp应用数据导入";
		new AppXmlTask().run();

		this.actionLog(request, actionType, actionTarget, actionResult,
				actionDesc);
		this.saveMessagesValue(request, "wp应用数据导入完成");
		return Constants.FORWARD_COMMON_SUCCESS;
	}
}
