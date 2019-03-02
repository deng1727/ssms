package com.aspire.ponaadmin.web.repository.web ;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.syncAndroid.dc.jms.MSGType;
import com.aspire.dotcard.syncAndroid.ssms.SSMSDAO;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.constant.ResourceConstants;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.CategoryBO;
import com.aspire.ponaadmin.web.repository.CategoryOperationBO;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryBOCode;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;

/**
 * <p>删除分类的Action</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class CategoryDelAction extends BaseAction
{

    /**
     * 日志引用
     */
    private static final JLogger LOG = LoggerFactory.getLogger(CategoryDelAction.class) ;

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
        LOG.debug("doPerform()");
        String categoryID = this.getParameter(request, "categoryID");
        String pCategoryID = this.getParameter(request, "pCategoryID");
        String cgyPath=this.getParameter(request, "cgyPath");
        /**
		 * 审批标识。当approvalFlag=="yes"时。表示该操作需要经过审批。
		 */
        String approvalFlag = this.getParameter(request, "approvalFlag");
        
        
        //cgyPath=cgyPath.substring(0, cgyPath.lastIndexOf("&gt;&gt;"));
        cgyPath=cgyPath.substring(0, cgyPath.lastIndexOf(">>"));
        String actionType = "删除货架";
        String actionTarget = "";
        String actionDesc = "";
        boolean actionResult = true;
        String forward = null;
        try
        {
        	 Category category = ( Category ) Repository.getInstance()
             .getNode(categoryID,
                      RepositoryConstants.TYPE_CATEGORY);       	
        		 if("yes".equals(approvalFlag)){
        			//备份删除前的审批状态
        			category.setDelproStatus(category.getClassifyStatus());
        			category.setClassifyStatus("2");
        		 }else{
        			category.setClassifyStatus("1");
        		 }
        			 actionTarget = CategoryBO.getInstance().getCategory(categoryID).getName();
        	            CategoryBO.getInstance().delCategory(pCategoryID, category);
        	            if("yes".equals(approvalFlag)){
        	            	Map<String,Object> map = new HashMap<String,Object>();
             		       
                	        //处理MM货架
                	        map.put("operation", "10");
                	       
                	        map.put("operationobj", "货架");
                			CategoryOperationBO.getInstance().operateInfoforCategory(request, category.getId(), "2", "1", map);
        	            }
        	            this.saveMessages(request, "RESOURCE_CATE_RESULT_002") ;
        	            request.setAttribute(Constants.PARA_GOURL,
        	                                 "../../web/resourcemgr/categoryInfo.do?categoryID=" +
        	                                 pCategoryID+"&cgyPath="+cgyPath) ;
        	            request.setAttribute(Constants.PARA_REFRESH_TREE_KEY, pCategoryID) ;
        	            
        	            request.setAttribute("del_node_cgyPath", this.getParameter(request, "cgyPath")) ;
        	            
        	            forward = Constants.FORWARD_COMMON_SUCCESS ;
            

            if(!"yes".equals(approvalFlag)){
            	 //这里加点消息动作,就是这个修改货架的动作要通知到数据中心，先把数据加到T_a_messages表，然后专门线程发送消息。
    			ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
    			String rootCategoryId = module.getItemValue("ROOT_CATEGORYID");
    			String operateCategoryId = module.getItemValue("OPERATE_ROOT_CATEGORYID");//不在商品库优化根货架下的运营货架
                if(SSMSDAO.getInstance().isAndroidCategory(categoryID, rootCategoryId)
                	||SSMSDAO.getInstance().isOperateCategory(categoryID, operateCategoryId)){//modify by aiyan 2013-05-18	 
//                	Catogoryid	必须	String	货架ID
//                	Action	必须	String	0：新建
//                	1：货架描述信息变更（包含扩展字段）
//                	9：删除
//                	Transactionid	必须	String	事务序列ID，用于处理同一事务请求时使用。

                	try {
    					SSMSDAO.getInstance().addMessages(MSGType.CatogoryModifyReq, categoryID+":9");
    				} catch (DAOException e) {
    					// TODO Auto-generated catch block
    					LOG.error("修改货架记录消息是出错，被修改的货架ID:"+categoryID,e);					
    				}
                }
            }
            
        }
        catch(BOException e)
        {
            LOG.error(e);
            if(e.getErrorCode() == RepositoryBOCode.CATEGORY_CONTENT_EXISTED)
            {
                //有资源或子货架存在，不能删除！
                actionDesc = "货架下不为空";
                this.saveMessages(request, "RESOURCE_CATE_BO_CHECK_002") ;
            }
            else
            {
                this.saveMessages(request, ResourceConstants.WEB_ERR_SYSTEM) ;
            }
            actionResult = false;
            forward = Constants.FORWARD_COMMON_FAILURE;
        }
        //写操作日志
        this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
        return mapping.findForward(forward);
    }
}

