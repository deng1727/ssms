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
 * ��һ�����������������Դ��Action 
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
	 * ��־����
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
        //��form�л�ȡ����ID
		String cateid = this.getParameter(request, "categoryID");
		
        //��form�л�ȡҪ��ӵ����ݼ���
		String[] dealContents = request.getParameterValues("dealContent");
        //��form�л�ȡ����·��
		String backURL = request.getParameter("backURL");
		String menuStatus = this.getParameter(request, "menuStatus");
		
        if (LOG.isDebugEnabled())
		{
            LOG.debug("backURL==" + backURL);
        }
		if (("".equals(backURL)) || (backURL == null)) {
			backURL = "cgyNotContentList.do?subSystem=ssms&categoryID=" + cateid + "&menuStatus" + menuStatus;
		}
		String actionType = "��Ʒ�ϼ�";
		String actionTarget = "";
		String actionDesc = "";
		boolean actionResult = true;
		String forward = null;
		String transactionID = null;
		try 
        {

			Category category = (Category) Repository.getInstance().getNode(
					cateid, RepositoryConstants.TYPE_CATEGORY);	
			
	        //���������Ʒ���Ż����������Ʒ���Ż�Ϊ�����ܵĻ��ܣ���Ҫ����Ϣ��ȥ��
	        //����ӵ���Ϣ����,��������½����ܵĶ���Ҫ֪ͨ���������ģ��Ȱ����ݼӵ�T_a_messages��Ȼ��ר���̷߳�����Ϣ��
			ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
			String rootCategoryId = module.getItemValue("ROOT_CATEGORYID");//��Ʒ���Ż�������
			String operateategoryId = module.getItemValue("OPERATE_ROOT_CATEGORYID");//������Ʒ���Ż��������µ���Ӫ����
			 if(SSMSDAO.getInstance().isAndroidCategory(category.getId(), rootCategoryId)
				//||operateCategoryId.indexOf(category.getId())!=-1){//������Ʒ���Ż������ܻ�����Ҫ�������Ӫ�����¶�������Ϣ modify by aiyan 2013-05-09
				||SSMSDAO.getInstance().isOperateCategory(category.getId(), operateategoryId)){
					 transactionID = ContextUtil.getTransactionID();         
			 }

			//above add by aiyan 2013-04-18
			 
			 
			for (int i = 0; (dealContents != null) && (i < dealContents.length); i++) 
            {
                actionTarget = category.getName();
                //ȡ������id(conID)
				String conID = dealContents[i];

				//�ҳ�������Ϣ
                Node node = Repository.getInstance().getNode(conID);
                
                if (LOG.isDebugEnabled())
                {
                    LOG.debug("the node type is :"+node.getType());
                }
                GContent content = (GContent)Repository.getInstance().getNode(conID,node.getType());
                // �����������
                actionTarget = content.getName() + "��" + category.getName();         
                //CategoryTools.addGood(category, conID);//removed by aiyan 2013-04-18
                //below modified
                if(transactionID!=null){
                	CategoryTools.addGoodTran(category, conID,transactionID,menuStatus);
                }else{
                	CategoryTools.addGood(category, conID,menuStatus);
                }
                // д������־
                this.actionLog(request, actionType, actionTarget, actionResult,
                                actionDesc);
                // ����ǽ�������Ӧ��, ����Ӧ����Ϣ
                GoodsChanegHisBO.getInstance().addAddHisToList(category, conID, content.getSubType());
			}
			
			//add 
			if (!(menuStatus != null && !"".equals(menuStatus) && "1"
					.equals(menuStatus)))
				if (transactionID != null) {
					MessageSendDAO.getInstance().changeStatus(transactionID,
							Constant.MESSAGE_HANDLE_STATUS_RUNNING,
							Constant.MESSAGE_HANDLE_STATUS_INIT);// add by aiyan
																	// 2013-05-20����EXECLE�����ʱ����д-5�������״̬��Ϊ-1
				}

			request.setAttribute("category", category);
			request.setAttribute("backURL", backURL);
			request.setAttribute("menuStatus", menuStatus);
			
			this.saveMessages(request, "RESOURCE_COL_RESULT_001");
			forward = "showResult";
			try {
    			//�Զ��޸�һ�±�������Ʒ��sortid
    			LockLocationDAO.getInstance().callProcedureAutoFixSortid(category.getCategoryID());
			} catch (Exception e) {
				LOG.error("�Զ��޸���������Ʒ��sortid�쳣");
			}
		} 
        catch (Exception e) 
        {
            if (LOG.isDebugEnabled())
			{
                LOG.error(e);
            }
			actionResult = false;
            // д������־
            this.actionLog(request, actionType, actionTarget, actionResult,
                            actionDesc);
			this.saveMessages(request, ResourceConstants.WEB_ERR_SYSTEM);
			forward = Constants.FORWARD_COMMON_FAILURE;
		}
        //removed
//		if(transactionID!=null){
//			MessageSendDAO.getInstance().changeStatus(transactionID,Constant.MESSAGE_HANDLE_STATUS_RUNNING, Constant.MESSAGE_HANDLE_STATUS_INIT);//add by aiyan 2013-05-20����EXECLE�����ʱ����д-5�������״̬��Ϊ-1
//		}
		
		return mapping.findForward(forward);
	}
}
