
package com.aspire.ponaadmin.web.channelUser.action;

import java.util.ArrayList;
import java.util.List;

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
import com.aspire.dotcard.syncAndroid.dc.jms.MSGType;
import com.aspire.dotcard.syncAndroid.ssms.SSMSDAO;
import com.aspire.ponaadmin.common.usermanager.UserManagerBO;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.channelUser.util.Common;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.constant.ResourceConstants;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.Node;
import com.aspire.ponaadmin.web.repository.ReferenceNode;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.repository.SearchParam;
import com.aspire.ponaadmin.web.repository.Searchor;

public class CgyContentTaxisAction extends BaseAction
{

    /**
     * log��־ʵ�� 
     */
    protected static JLogger log = LoggerFactory.getLogger(CgyContentTaxisAction.class);

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
    	ActionForward forward = null;
        String action = request.getParameter("action");
        if ("query".equals(action))
        {
            forward = getTaxis(mapping, form, request, response);
        }
        else if ("save".equals(action))
        {
            forward = saveTaxis(mapping, form, request, response);
        }
        else if ("mod".equals(action))
        {
            forward = modTaxis(mapping, form, request, response);
        }
        return forward;
    }

    /**
     * ��ȡ��ǰ���ݵ��������
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward getTaxis(ActionMapping mapping, ActionForm form,
                           HttpServletRequest request,
                           HttpServletResponse response) throws BOException
    {

        log.debug("CgyContentTaxisAction.getTaxis is start!");
        String forward = "showTaxis";
        String cateid = request.getParameter("cateid");
        String refid = request.getParameter("refid");
        // ��������������
        Searchor searchor = new Searchor();
        Searchor convergeSearchor = new Searchor();
        if (!refid.equals(""))
        {
            searchor.getParams()
                    .add(new SearchParam("id",
                                         RepositoryConstants.OP_EQUAL,
                                         refid));
        }

        // ��ȡ������Ϣ
        Category category = ( Category ) Repository.getInstance()
                                                   .getNode(cateid,
                                                            RepositoryConstants.TYPE_CATEGORY);

        // ��ѯ��Դ����
        List list = new ArrayList();
        list = category.searchNodes(RepositoryConstants.TYPE_REFERENCE,
                                    searchor,
                                    null);
        String maxTaxis = String.valueOf(category.searchConverge(RepositoryConstants.TYPE_REFERENCE,
                                                                 convergeSearchor,
                                                                 RepositoryConstants.CONVERGE_TYPE_MAX,
                                                                 "sortID"));
        String minTaxis = String.valueOf(category.searchConverge(RepositoryConstants.TYPE_REFERENCE,
                                                                 convergeSearchor,
                                                                 RepositoryConstants.CONVERGE_TYPE_MIN,
                                                                 "sortID"));
        log.debug("CgyContentTaxisAction.getTaxis.maxTaxis==" + maxTaxis + ","
                  + "minTaxis==" + minTaxis);
        ReferenceNode refNode = null;
        if (list != null)
        {
            refNode = ( ReferenceNode ) list.get(0);
            log.debug("CgyContentTaxisAction.getTaxis.sortid=="
                      + refNode.getSortID());
        }
        log.debug("CgyContentTaxisAction.getTaxis.refNode=="
                  + refNode.toString());
        log.debug("CgyContentTaxisAction.getTaxis.maxTaxis==" + maxTaxis + ","
                  + "minTaxis==" + minTaxis);
        request.setAttribute("maxTaxis", maxTaxis);
        request.setAttribute("minTaxis", minTaxis);
        request.setAttribute("refNode", refNode);
        request.setAttribute("category", category);

        return mapping.findForward(forward);
    }

    /**
     * �������ݵ��������
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */

    public ActionForward saveTaxis(ActionMapping mapping, ActionForm form,
                           HttpServletRequest request,
                           HttpServletResponse response) throws BOException
    {

        log.debug("CgyContentTaxisAction.saveTaxis is start!");
        String forward = "commonSuccess";
        String cateid = request.getParameter("cateid");
        String refid = request.getParameter("refid");
        String sortid = request.getParameter("taxis");
        log.debug("CgyContentTaxisAction.modTaxis.parameters:" + cateid + ","
                  + refid + "," + sortid);
        ReferenceNode refNodeRoot = new ReferenceNode();
        refNodeRoot.setId(refid);// ע�ⲻ�Ƕ�Ӧ���ݵ�id���������ýڵ��Լ���id
        refNodeRoot.setSortID(Integer.parseInt(sortid));

        refNodeRoot.save();

        this.saveMessages(request, "CONTENT_TAXIS_MOD_SUCC");
        request.setAttribute(Constants.PARA_GOURL,
                             request.getContextPath()
                                             + "/web/channelUser/cgyContentList.do?subSystem=ssms&categoryID="
                                             + cateid);
        log.debug("CgyContentTaxisAction.saveTaxis.gourl=="
                  + request.getContextPath()
                  + "/web/channelUser/cgyContentList.do?subSystem=ssms&categoryID=" + cateid);

        return mapping.findForward(forward);
    }

    /**
     * �޸����ݵ��������
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */

    public ActionForward modTaxis(ActionMapping mapping, ActionForm form,
                           HttpServletRequest request,
                           HttpServletResponse response) throws BOException
    {

        log.debug("CgyContentTaxisAction.modTaxis is start!");
        String forward = "commonSuccess";
        String cateid=request.getParameter("cateid");
        /* 
        String refid = request.getParameter("refid");
        String contentid = request.getParameter("contentid");
        String sortid = request.getParameter("taxis");
        String changedValues=request.getParameter("changedValues");
        System.out.println(changedValues);*/
        //֧��ͬʱ�޸Ķ������š�ÿ��Ҫ�޸ĵ��������"��"�ָÿ�������������������ɣ���Ʒid������id���޸ĺ������ţ���Щ�����С�#���ָ�
        String changedValues=this.getParameter(request, "changedValues");
        if("".equals(changedValues.trim()))//û�б仯��ֱ�ӷ��سɹ�
        {
        	this.saveMessages(request, "CONTENT_TAXIS_MOD_SUCC");
        	return mapping.findForward(forward);
        }
        String refid=null;
        String contentid=null;
        String sortid=null;
        String changed[]=changedValues.split(":");
 
        
        if(log.isDebugEnabled())
        {
        	  log.debug("CgyContentTaxisAction.modTaxis ����������ŵ����������"+changed); 
        }
        
        XBusValueVo[] xBus = new XBusValueVo[changed.length];
           
        String backURL=this.getParameter(request, "backURL");
        if("".equals(backURL.trim()))
        {
        	backURL="/web/channelUser/cgyContentList.do?subSystem=ssms&categoryID=" + cateid;
        }
		String logActionType = "��Ʒ��������趨";
		boolean actionResult = true;
		String logActionTarget = "";
		String transactionID = null;
        try {
        	
            //���������Ʒ���Ż����������Ʒ���Ż�Ϊ�����ܵĻ��ܣ���Ҫ����Ϣ��ȥ��
            //����ӵ���Ϣ����,��������½����ܵĶ���Ҫ֪ͨ���������ģ��Ȱ����ݼӵ�T_a_messages��Ȼ��ר���̷߳�����Ϣ��
    		ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
    		String rootCategoryId = module.getItemValue("ROOT_CATEGORYID");
    		String operateCategoryId = module.getItemValue("OPERATE_ROOT_CATEGORYID");//������Ʒ���Ż��������µ���Ӫ����
    		if(SSMSDAO.getInstance().isAndroidCategory(cateid, rootCategoryId)
    			//||operateCategoryId.indexOf(cateid)!=-1){//������Ʒ���Ż������ܻ�����Ҫ�������Ӫ�����¶�������Ϣ modify by aiyan 2013-05-10		
    			||SSMSDAO.getInstance().isOperateCategory(cateid, operateCategoryId)){//modify by aiyan 2013-05-18
    			 transactionID = ContextUtil.getTransactionID();
    		 }
    		//above add by aiyan 2013-05-10	
    		 
			//�ҳ�������Ϣ
        	 for(int i=0;i<changed.length;i++)
             {
             		String temp[]=changed[i].split("#");
             		contentid=temp[0];
             		refid=temp[1];
             		sortid=temp[2];
             		
                    ReferenceNode refNode = (ReferenceNode)Repository.getInstance().getNode(refid, RepositoryConstants.TYPE_REFERENCE);
                    Node node = Repository.getInstance().getNode(contentid);
        			GContent content = (GContent)Repository.getInstance().getNode(contentid,node.getType());
        			logActionTarget = refid + " " + content.getName();
        			//�����޸�
        			ReferenceNode refNodeRoot = new ReferenceNode();
        			refNodeRoot.setId(refid);// ע�ⲻ�Ƕ�Ӧ���ݵ�id���������ýڵ��Լ���id
        			refNodeRoot.setSortID(Integer.parseInt(sortid));
        			refNodeRoot.save();

        			if(transactionID!=null){
            			SSMSDAO.getInstance().addMessages(MSGType.RefModifyReq
                    			,transactionID
                    			//,refNode.getGoodsID()+":"+refNode.getCategoryID()+":"+cateid+":"+refNode.getRefNodeID()+":"+refNode.getSortID()+":"+modifyStr(refNode.getLoadDate())+":0"
                    			//���sortid�ĵط����������⡣modify by aiyan 2013-07-02
                    			,refNode.getGoodsID()+":"+refNode.getCategoryID()+":"+cateid+":"+refNode.getRefNodeID()+":"+sortid+":"+modifyStr(refNode.getLoadDate())+":0"
                    			,Constant.MESSAGE_HANDLE_STATUS_RUNNING);

        			}

        			
        			this.actionLog(request, logActionType, logActionTarget, actionResult,"���=" + sortid );
        			
        			if(log.isDebugEnabled())
        			{
        				log.debug("CgyContentTaxisAction.modTaxis ��ƷidΪ��"+refid+",���ĺ���������Ϊ��"+sortid);
        			}
        			
                    xBus[i] = new XBusValueVo();
                    xBus[i].setId(contentid);
                    xBus[i].setSortId(sortid);
                    xBus[i].setGoodId(refNode.getGoodsID());
                    xBus[i].setPath(refNode.getPath());
                    xBus[i].setType(node.getType());
             }
        	 
     		if(transactionID!=null){
    			MessageSendDAO.getInstance().changeStatus(transactionID,Constant.MESSAGE_HANDLE_STATUS_RUNNING, Constant.MESSAGE_HANDLE_STATUS_INIT);//add by aiyan 2013-05-20����EXECLE�����ʱ����д-5�������״̬��Ϊ-1
    		}
        	 
        	 
        	 this.saveMessages(request, "CONTENT_TAXIS_MOD_SUCC");
        	/* request.setAttribute(Constants.PARA_GOURL,
                     request.getContextPath()
                                     + "/web/channelUser/cgyContentList.do?categoryID="
                                     + cateid);*/
        	 request.setAttribute(Constants.PARA_GOURL, backURL);
        	 //request.setAttribute(Constants.PARA_GOURL,"javascript:window.history.go(-1);window.location.reload();");
			
             XBusValueBO.getInstance().sendXBusValue(xBus);
			
		} catch (Exception e) {
            log.error(e);
            actionResult = false;
			this.saveMessages(request, ResourceConstants.WEB_ERR_SYSTEM);
			forward = Constants.FORWARD_COMMON_FAILURE;
		}
//		if(transactionID!=null){
//			MessageSendDAO.getInstance().changeStatus(transactionID,Constant.MESSAGE_HANDLE_STATUS_RUNNING, Constant.MESSAGE_HANDLE_STATUS_INIT);//add by aiyan 2013-05-20����EXECLE�����ʱ����д-5�������״̬��Ϊ-1
//		}
		return mapping.findForward(forward);
    }    
    
    //2013-05-10 10:51:31ת����20130510105131
    private String modifyStr(String dateStr){
    	if(dateStr==null){
    		return "";
    	}
    	return dateStr.replaceAll(" ", "").replaceAll("-", "").replaceAll(":", "");
    }
    
    public static void main(String[] argv){
    	String a = "2013-05-10 10:51:31";
    	CgyContentTaxisAction c = new CgyContentTaxisAction();
    	System.out.println(c.modifyStr(a));
    }
    
}
