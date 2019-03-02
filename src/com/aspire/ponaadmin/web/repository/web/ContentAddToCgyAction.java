package com.aspire.ponaadmin.web.repository.web ;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.gcontent.GContent;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.constant.ResourceConstants;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.CategoryDAO;
import com.aspire.ponaadmin.web.repository.Node;
import com.aspire.ponaadmin.web.repository.ReferenceNode;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.repository.goods.GoodsBO;
import com.aspire.ponaadmin.web.repository.goods.GoodsVO;
import com.aspire.ponaadmin.web.util.DateUtil;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * <p>���δ����������Դ�������µ�Action</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class ContentAddToCgyAction extends BaseAction
{

    /**
     * ��־����
     */
    private static final JLogger LOG = LoggerFactory.getLogger(ContentAddToCgyAction.class) ;

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
        String forward = null;

        //������������裺1��ѡ����Դ��2��ѡ����ࡣ����2�������Ҫִ����ӣ�isFinished=true��
        String isFinished = this.getParameter(request, "isFinished");
        String menuStatus = this.getParameter(request, "menuStatus");
        if(isFinished.equals("true"))
        {
            //����2��ѡ������Ժ�
            //�ȴ�session�а��û�ѡ�����Դid�б�õ�
            String[] dealContents = (String[]) request.getSession().getAttribute(
                "addToCgyContents") ;
            String backURL=(String)request.getSession().getAttribute("backURL");
            
            String[] categoryIDs = request.getParameterValues("categoryIDs");
            String actionType = "δ������Դ��ӵ���Ŀ";
            String actionTarget = "";
            String actionDesc = "";
            boolean actionResult = true;
            try
            {
                for(int i = 0; (categoryIDs != null ) && (i < categoryIDs.length); i++)
                {
                    String cateID = categoryIDs[i];
                    String CategoryID;
                    try
                    {
                        CategoryID = CategoryDAO.getInstance().getCategoryIDByID(cateID);
                    }
                    catch (DAOException ex)
                    {
                        throw new BOException("ContentAddToCgyAction.getCategoryIDByID is failed!!");
                    }
                    for (int j = 0 ; (dealContents != null) && (j < dealContents.length) ; j++)
                    {
                        String conID = dealContents[j];
                        //�ŵ�Ŀ�������
                        Category category = (Category) Repository.getInstance().
                            getNode(cateID, RepositoryConstants.TYPE_CATEGORY) ;
                        //�ҳ�������Ϣ
                        Node node = Repository.getInstance().getNode(conID);
                        if (LOG.isDebugEnabled())
                        {
                            LOG.debug("the node type is :"+node.getType());
                        }
                        GContent content = (GContent)Repository.getInstance().getNode(conID,node.getType());
                        //������Ʒ���룬������������ݵĴ���ʽ��һ��
                        String goodsID = "";
                        /* if (node.getType().equals(RepositoryConstants.TYPE_COLORRING)
                        || node.getType().equals(RepositoryConstants.TYPE_NEWS)
                        || node.getType().equals(RepositoryConstants.TYPE_AUDIO))*/
                        //������Ʒ���룺��Ʒ���룽���ܱ��룫��ҵ���룫ҵ����룫���ݱ���
                        String id=node.getId();
                        if(id.charAt(0)>='0'&&id.charAt(0)<='9')
                        {
                        	 goodsID = CategoryID + content.getCompanyID() + content.getProductID() + content.getContentID();
                            
                        }
                        // 
                        //���塢��Ѷ�����ֵ�id�Է����ֿ���������: ��Ʒ����=���ܱ���+"|"+����ID+"|"������39λ�Ļ���0��
                        else
                        {
                        	goodsID = PublicUtil.rPad(CategoryID + "|" + conID + "|", 39, "0");
                        }
                        // �ŵ�Ŀ�������
                        String newNodeID = category.getNewAllocateID();
                        if (LOG.isDebugEnabled())
                        {
                            LOG.debug("The new nodeid is " + newNodeID);
                        }
                        ReferenceNode refNodeRoot = new ReferenceNode();
                        refNodeRoot.setId(newNodeID);
                        refNodeRoot.setParentID(category.getId());
                        refNodeRoot.setPath(category.getPath() + ".{" + newNodeID + "}");
                        String Type = refNodeRoot.getType();
                        if (LOG.isDebugEnabled())
                        {
                            LOG.debug("The addednode's type is " + Type);
                        }
                        refNodeRoot.setRefNodeID(conID);
                        refNodeRoot.setSortID(0);
                        refNodeRoot.setGoodsID(goodsID);
                        refNodeRoot.setCategoryID(CategoryID); 
                        refNodeRoot.setVariation(0);
                        if(menuStatus != null && !"".equals(menuStatus) && "1".equals(menuStatus)){
                        	refNodeRoot.setVerifyStatus("3");
                        	category.setGoodsStatus("0");
                        	category.save();
                        }else{
                        	refNodeRoot.setVerifyStatus("1");
                        }
                        GoodsVO goodsVO = new GoodsVO();
                        goodsVO.setGoodsID(goodsID);
                        goodsVO.setIcpCode(content.getIcpCode());
                        goodsVO.setIcpServId(content.getIcpServId());
                        goodsVO.setContentID(content.getContentID());
                        goodsVO.setCategoryID(CategoryID);
                        goodsVO.setGoodsName(content.getName());
                        goodsVO.setState(1);
                        goodsVO.setChangeDate(new Date());
                        goodsVO.setActionType(1);
                        
                        //��Ѷ�����塢���������ϼܺ�д����Ʒ��ʷ����Ϣ��
                       /* if (node.getType().equals(RepositoryConstants.TYPE_COLORRING)
                            || node.getType().equals(RepositoryConstants.TYPE_NEWS)
                            || node.getType().equals(RepositoryConstants.TYPE_AUDIO))*/
                        {
                          
                        }if(id.charAt(0)>='0'&&id.charAt(0)<='9')
                        {
                            // ����GoodsBO�е�addNodeAndInsertGoodsInfor����ڵ㲢������Ʒ��Ϣ
                            GoodsBO.addNodeAndInsertGoodsInfo(refNodeRoot,goodsVO);
                        }else
                        {
                    	  refNodeRoot.setLoadDate(DateUtil.formatDate(new Date(),
                          "yyyy-MM-dd HH:mm:ss"));    
                    	  category.addNode(refNodeRoot);
                    	  category.saveNode();
                        }
                    }
                }
                this.saveMessages(request, "RESOURCE_COL_RESULT_003") ;
                //����δ����ҳ��
               /* request.setAttribute(Constants.PARA_GOURL,
                                     "../../web/resourcemgr/cgyNotContentList.do?categoryID=" +
                                     RepositoryConstants.ROOT_CATEGORY_ID) ;*/
                request.setAttribute(Constants.PARA_GOURL, backURL);
                forward = Constants.FORWARD_COMMON_SUCCESS ;
            }
            catch(BOException e)
            {
                LOG.error(e);
                actionResult = false;
                this.saveMessages(request, ResourceConstants.WEB_ERR_SYSTEM) ;
                forward = Constants.FORWARD_COMMON_FAILURE;
            }
            //д������־
            this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
        }
        else
        {
            //����1��ѡ����Դ��
            //��ѡ���contentID�б��浽session�м���
            String[] dealContents = request.getParameterValues("dealContent");
            String backURL=request.getParameter("backURL");
            request.getSession().setAttribute("backURL", backURL);
            request.getSession().setAttribute("addToCgyContents", dealContents);
            //forward�����������û�ѡ�����
            forward = "showTree";
        }//if(isFinished.equals("true"))
        return mapping.findForward(forward);
    }
}

