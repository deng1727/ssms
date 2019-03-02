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
 * <p>��һ���������Ƴ�������Դ��Action</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class CgyContentRemoveAction extends BaseAction
{

    /**
     * ��־����
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
    		this.saveMessages(request, "�û�δ��½���ߵ�½�ѳ�ʱ��");
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
        String actionType = "��Ʒ�¼�";
        String actionTarget = "";
        String actionDesc = "";
        boolean actionResult = true;
        String forward = null;
        try
        {
            
        	this.actionLog(request, "��ʼ��Ʒ�¼�", "����ID:"+categoryID, true, "��ʼ��Ʒ�¼�");
        	
            // ��Ŀ��������Ƴ�
            Category category = ( Category ) Repository.getInstance()
                                                       .getNode(categoryID,
                                                                RepositoryConstants.TYPE_CATEGORY);
            
            
            
	        //���������Ʒ���Ż����������Ʒ���Ż�Ϊ�����ܵĻ��ܣ���Ҫ����Ϣ��ȥ��
	        //����ӵ���Ϣ����,��������½����ܵĶ���Ҫ֪ͨ���������ģ��Ȱ����ݼӵ�T_a_messages��Ȼ��ר���̷߳�����Ϣ��
			ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
			String rootCategoryId = module.getItemValue("ROOT_CATEGORYID");
			String operateCategoryId = module.getItemValue("OPERATE_ROOT_CATEGORYID");//������Ʒ���Ż��������µ���Ӫ����
			String transactionID = null;
			 if(SSMSDAO.getInstance().isAndroidCategory(category.getId(), rootCategoryId)
				//||operateCategoryId.indexOf(category.getId())!=-1){//������Ʒ���Ż������ܻ�����Ҫ�������Ӫ�����¶�������Ϣ modify by aiyan 2013-05-09		 
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

                // ��¼��Ʒ����,���ڼ���־
                ReferenceNode refNode = ( ReferenceNode ) Repository.getInstance()
                                                                    .getNode(refID,
                                                                             "nt:reference");
                GContent content = ( GContent ) Repository.getInstance()
                                                          .getNode(refNode.getRefNodeID(),
                                                                   "nt:gcontent");
                if (content != null)
                {
                    actionTarget = content.getName() + "��" + category.getName();
                }
                else
                {
                    LOG.error("�����ݿ��л�ȡ" + refNode.getRefNodeID() + "��Ӧ������ʧ�ܣ�");
                    continue;
                }
                String contId = refNode.getRefNodeID();
                if (contId.charAt(0) >= '0' && contId.charAt(0) <= '9')// Ӧ�á�
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
                // д������־
                this.actionLog(request,
                               actionType,
                               actionTarget,
                               actionResult,
                               actionDesc);

                // ����ǽ�������Ӧ��, ����Ӧ����Ϣ
                GoodsChanegHisBO.getInstance()
                                .addDelHisToList(category,
                                                 refNode,
                                                 content.getSubType());
                
////                Catogoryid	����	String	����ID
////                Contentid	����	String	Ӧ��ID
////                Action	����	String	0���½�
////                9��ɾ��
////                Transactionid	����	String	��������ID�����ڴ���ͬһ��������ʱʹ�á�
//                SSMSDAO.getInstance().addRefMessages(category.getId(), content.getContentID(), "9");  //add by aiyan 2013-03-12
            }
            
    		if(transactionID!=null){
    			MessageSendDAO.getInstance().changeStatus(transactionID,Constant.MESSAGE_HANDLE_STATUS_RUNNING, Constant.MESSAGE_HANDLE_STATUS_INIT);//add by aiyan 2013-05-20����EXECLE�����ʱ����д-5�������״̬��Ϊ-1
    		}
    		
            this.saveMessages(request, "RESOURCE_COL_RESULT_002") ;
            request.setAttribute(Constants.PARA_GOURL,backURL) ;
            forward = Constants.FORWARD_COMMON_SUCCESS ;
        }
        catch(Exception e)
        {
            LOG.error(e);
            actionResult = false;
            //д������־
            this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
            this.saveMessages(request, ResourceConstants.WEB_ERR_SYSTEM) ;
            forward = Constants.FORWARD_COMMON_FAILURE;
        }
        
        return mapping.findForward(forward);
    }
}

