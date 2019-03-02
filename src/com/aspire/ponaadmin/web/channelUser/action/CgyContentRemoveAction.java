package com.aspire.ponaadmin.web.channelUser.action ;

import java.util.StringTokenizer;

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
import com.aspire.ponaadmin.common.usermanager.UserManagerBO;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.channelUser.util.Common;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.constant.ResourceConstants;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.GoodsChanegHisBO;
import com.aspire.ponaadmin.web.repository.ReferenceNode;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.repository.goods.GoodsBO;

/**
 * <p>在一个分类下移除内容资源的Action</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class CgyContentRemoveAction extends BaseAction
{

    /**
     * 日志引用
     */
    private static final JLogger LOG = LoggerFactory.getLogger(CgyContentRemoveAction.class) ;

    /**
     *
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws BOException
     * @todo Implement this com.aspire.ponaadmin.web.BaseAction method
     */
    public ActionForward doPerform (ActionMapping mapping, ActionForm form,
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
        LOG.debug("doPerform()");
        String categoryID = this.getParameter(request, "categoryID");
        String[] dealContents = request.getParameterValues("dealContent");
        String backURL=this.getParameter(request, "backURL");
        if(backURL.equals(""))
        {
        	backURL="../../web/channelUser/cgyContentList.do?subSystem=ssms&categoryID="+categoryID;
        }
        String actionType = "商品下架";
        String actionTarget = "";
        String actionDesc = "";
        boolean actionResult = true;
        String forward = null;
        try
        {
            
        	this.actionLog(request, "开始商品下架", "货架ID:"+categoryID, true, "开始商品下架");
        	
            // 从目标分类下移除
            Category category = ( Category ) Repository.getInstance()
                                                       .getNode(categoryID,
                                                                RepositoryConstants.TYPE_CATEGORY);
            
            
            
	        //这里加上商品库优化，如果是商品库优化为根货架的货架，则要放消息进去。
	        //这里加点消息动作,就是这个新建货架的动作要通知到数据中心，先把数据加到T_a_messages表，然后专门线程发送消息。
			ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
			String rootCategoryId = module.getItemValue("ROOT_CATEGORYID");
			String operateCategoryId = module.getItemValue("OPERATE_ROOT_CATEGORYID");//不在商品库优化根货架下的运营货架
			String transactionID = null;
			 if(SSMSDAO.getInstance().isAndroidCategory(category.getId(), rootCategoryId)
				//||operateCategoryId.indexOf(category.getId())!=-1){//凡是商品库优化根货架或者需要处理的运营货架下都有有消息 modify by aiyan 2013-05-09		 
				||SSMSDAO.getInstance().isOperateCategory(category.getId(), operateCategoryId)){//modify by aiyan 2013-05-18
					 transactionID = ContextUtil.getTransactionID();
			 }
			//above add by aiyan 2013-04-18
			
			

            for(int i = 0; (dealContents != null ) && (i < dealContents.length); i++)
            {
                actionTarget = category.getName();
                StringTokenizer st = new StringTokenizer(dealContents[i], "#");
                st.nextToken();
                String refID = st.nextToken();

                // 记录商品内容,用于记日志
                ReferenceNode refNode = ( ReferenceNode ) Repository.getInstance()
                                                                    .getNode(refID,
                                                                             "nt:reference");
                GContent content = ( GContent ) Repository.getInstance()
                                                          .getNode(refNode.getRefNodeID(),
                                                                   "nt:gcontent");
                if (content != null)
                {
                    actionTarget = content.getName() + "，" + category.getName();
                }
                else
                {
                    LOG.error("从数据库中获取" + refNode.getRefNodeID() + "对应的内容失败！");
                    continue;
                }
                String contId = refNode.getRefNodeID();
                if (contId.charAt(0) >= '0' && contId.charAt(0) <= '9')// 应用。
                {
                    //GoodsBO.removeRefContentFromCategory(refID);//remve by aiyan 2013-04-18
                    
                	//modified by aiyan
                    if(transactionID!=null){
                    	GoodsBO.removeRefContentFromCategory(refID,transactionID);
                    }else{
                    	GoodsBO.removeRefContentFromCategory(refID);
                    }
                }
                else
                {
                    category.delNode(refNode);
                    category.saveNode();
                }
                // 写操作日志
                this.actionLog(request,
                               actionType,
                               actionTarget,
                               actionResult,
                               actionDesc);

                // 如果是紧急上线应用, 保存应用信息
                GoodsChanegHisBO.getInstance()
                                .addDelHisToList(category,
                                                 refNode,
                                                 content.getSubType());
                
////                Catogoryid	必须	String	货架ID
////                Contentid	必须	String	应用ID
////                Action	必须	String	0：新建
////                9：删除
////                Transactionid	必须	String	事务序列ID，用于处理同一事务请求时使用。
//                SSMSDAO.getInstance().addRefMessages(category.getId(), content.getContentID(), "9");  //add by aiyan 2013-03-12
            }
            
    		if(transactionID!=null){
    			MessageSendDAO.getInstance().changeStatus(transactionID,Constant.MESSAGE_HANDLE_STATUS_RUNNING, Constant.MESSAGE_HANDLE_STATUS_INIT);//add by aiyan 2013-05-20，在EXECLE导入的时候先写-5，而后把状态改为-1
    		}
    		
            this.saveMessages(request, "RESOURCE_COL_RESULT_002") ;
            request.setAttribute(Constants.PARA_GOURL,backURL) ;
            forward = Constants.FORWARD_COMMON_SUCCESS ;
        }
        catch(Exception e)
        {
            LOG.error(e);
            actionResult = false;
            //写操作日志
            this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
            this.saveMessages(request, ResourceConstants.WEB_ERR_SYSTEM) ;
            forward = Constants.FORWARD_COMMON_FAILURE;
        }
        
        return mapping.findForward(forward);
    }
}

