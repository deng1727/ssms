package com.aspire.ponaadmin.web.repository.web;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.gcontent.GContent;
import com.aspire.dotcard.syncAndroid.common.Constant;
import com.aspire.dotcard.syncAndroid.common.ContextUtil;
import com.aspire.dotcard.syncAndroid.dc.MessageSendDAO;
import com.aspire.dotcard.syncAndroid.ssms.SSMSDAO;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.constant.ResourceConstants;
import com.aspire.ponaadmin.web.lockLocation.dao.LockLocationDAO;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.CategoryTools;
import com.aspire.ponaadmin.web.repository.GoodsChanegHisBO;
import com.aspire.ponaadmin.web.repository.Node;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;

/**
 * <p>
 * 在一个货架下添加内容资源的Action 
 * </p>
 * <p>
 * Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights
 * Reserved
 * </p>
 * 
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class CgyContentAddAction extends BaseAction {

	/**
	 * 日志引用
	 */
	private static final JLogger LOG = LoggerFactory.getLogger(CgyContentAddAction.class);

	/**
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward
	 * @throws BOException
	 * @todo Implement this com.aspire.ponaadmin.web.BaseAction method
	 */
	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("doPerform()");
        }
        //从form中获取分类ID
		String cateid = this.getParameter(request, "categoryID");
		
        //从form中获取要添加的内容集合
		String[] dealContents = request.getParameterValues("dealContent");
        //从form中获取返回路径
		String backURL = request.getParameter("backURL");
		String menuStatus = this.getParameter(request, "menuStatus");
		
        if (LOG.isDebugEnabled())
		{
            LOG.debug("backURL==" + backURL);
        }
		if (("".equals(backURL)) || (backURL == null)) {
			backURL = "cgyNotContentList.do?subSystem=ssms&categoryID=" + cateid + "&menuStatus" + menuStatus;
		}
		String actionType = "商品上架";
		String actionTarget = "";
		String actionDesc = "";
		boolean actionResult = true;
		String forward = null;
		String transactionID = null;
		try 
        {

			Category category = (Category) Repository.getInstance().getNode(
					cateid, RepositoryConstants.TYPE_CATEGORY);	
			
	        //这里加上商品库优化，如果是商品库优化为根货架的货架，则要放消息进去。
	        //这里加点消息动作,就是这个新建货架的动作要通知到数据中心，先把数据加到T_a_messages表，然后专门线程发送消息。
			ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
			String rootCategoryId = module.getItemValue("ROOT_CATEGORYID");//商品库优化根货架
			String operateategoryId = module.getItemValue("OPERATE_ROOT_CATEGORYID");//不在商品库优化根货架下的运营货架
			 if(SSMSDAO.getInstance().isAndroidCategory(category.getId(), rootCategoryId)
				//||operateCategoryId.indexOf(category.getId())!=-1){//凡是商品库优化根货架或者需要处理的运营货架下都有有消息 modify by aiyan 2013-05-09
				||SSMSDAO.getInstance().isOperateCategory(category.getId(), operateategoryId)){
					 transactionID = ContextUtil.getTransactionID();         
			 }

			//above add by aiyan 2013-04-18
			 
			 
			for (int i = 0; (dealContents != null) && (i < dealContents.length); i++) 
            {
                actionTarget = category.getName();
                //取出内容id(conID)
				String conID = dealContents[i];

				//找出内容信息
                Node node = Repository.getInstance().getNode(conID);
                
                if (LOG.isDebugEnabled())
                {
                    LOG.debug("the node type is :"+node.getType());
                }
                GContent content = (GContent)Repository.getInstance().getNode(conID,node.getType());
                // 构造操作对象
                actionTarget = content.getName() + "，" + category.getName();         
                //CategoryTools.addGood(category, conID);//removed by aiyan 2013-04-18
                //below modified
                if(transactionID!=null){
                	CategoryTools.addGoodTran(category, conID,transactionID,menuStatus);
                }else{
                	CategoryTools.addGood(category, conID,menuStatus);
                }
                // 写操作日志
                this.actionLog(request, actionType, actionTarget, actionResult,
                                actionDesc);
                // 如果是紧急上线应用, 保存应用信息
                GoodsChanegHisBO.getInstance().addAddHisToList(category, conID, content.getSubType());
			}
			
			//add 
			if (!(menuStatus != null && !"".equals(menuStatus) && "1"
					.equals(menuStatus)))
				if (transactionID != null) {
					MessageSendDAO.getInstance().changeStatus(transactionID,
							Constant.MESSAGE_HANDLE_STATUS_RUNNING,
							Constant.MESSAGE_HANDLE_STATUS_INIT);// add by aiyan
																	// 2013-05-20，在EXECLE导入的时候先写-5，而后把状态改为-1
				}

			request.setAttribute("category", category);
			request.setAttribute("backURL", backURL);
			request.setAttribute("menuStatus", menuStatus);
			
			this.saveMessages(request, "RESOURCE_COL_RESULT_001");
			forward = "showResult";
			try {
    			//自动修复一下被锁定商品的sortid
    			LockLocationDAO.getInstance().callProcedureAutoFixSortid(category.getCategoryID());
			} catch (Exception e) {
				LOG.error("自动修复被锁定商品的sortid异常");
			}
		} 
        catch (Exception e) 
        {
            if (LOG.isDebugEnabled())
			{
                LOG.error(e);
            }
			actionResult = false;
            // 写操作日志
            this.actionLog(request, actionType, actionTarget, actionResult,
                            actionDesc);
			this.saveMessages(request, ResourceConstants.WEB_ERR_SYSTEM);
			forward = Constants.FORWARD_COMMON_FAILURE;
		}
        //removed
//		if(transactionID!=null){
//			MessageSendDAO.getInstance().changeStatus(transactionID,Constant.MESSAGE_HANDLE_STATUS_RUNNING, Constant.MESSAGE_HANDLE_STATUS_INIT);//add by aiyan 2013-05-20，在EXECLE导入的时候先写-5，而后把状态改为-1
//		}
		
		return mapping.findForward(forward);
	}
}
