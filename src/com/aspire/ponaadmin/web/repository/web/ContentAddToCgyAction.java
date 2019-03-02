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
 * <p>添加未归类内容资源到分类下的Action</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class ContentAddToCgyAction extends BaseAction
{

    /**
     * 日志引用
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

        //添加有两个步骤：1、选择资源；2、选择分类。步骤2后才真正要执行添加（isFinished=true）
        String isFinished = this.getParameter(request, "isFinished");
        String menuStatus = this.getParameter(request, "menuStatus");
        if(isFinished.equals("true"))
        {
            //步骤2、选择分类以后。
            //先从session中把用户选择的资源id列表得到
            String[] dealContents = (String[]) request.getSession().getAttribute(
                "addToCgyContents") ;
            String backURL=(String)request.getSession().getAttribute("backURL");
            
            String[] categoryIDs = request.getParameterValues("categoryIDs");
            String actionType = "未分类资源添加到栏目";
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
                        //放到目标分类中
                        Category category = (Category) Repository.getInstance().
                            getNode(cateID, RepositoryConstants.TYPE_CATEGORY) ;
                        //找出内容信息
                        Node node = Repository.getInstance().getNode(conID);
                        if (LOG.isDebugEnabled())
                        {
                            LOG.debug("the node type is :"+node.getType());
                        }
                        GContent content = (GContent)Repository.getInstance().getNode(conID,node.getType());
                        //构造商品编码，彩铃和其他数据的处理方式不一样
                        String goodsID = "";
                        /* if (node.getType().equals(RepositoryConstants.TYPE_COLORRING)
                        || node.getType().equals(RepositoryConstants.TYPE_NEWS)
                        || node.getType().equals(RepositoryConstants.TYPE_AUDIO))*/
                        //本地商品编码：商品编码＝货架编码＋企业代码＋业务代码＋内容编码
                        String id=node.getId();
                        if(id.charAt(0)>='0'&&id.charAt(0)<='9')
                        {
                        	 goodsID = CategoryID + content.getCompanyID() + content.getProductID() + content.getContentID();
                            
                        }
                        // 
                        //彩铃、资讯、音乐等id以非数字开发的内容: 商品编码=货架编码+"|"+内容ID+"|"，不够39位的话后补0；
                        else
                        {
                        	goodsID = PublicUtil.rPad(CategoryID + "|" + conID + "|", 39, "0");
                        }
                        // 放到目标分类中
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
                        
                        //资讯、彩铃、音乐数据上架后不写入商品历史表信息。
                       /* if (node.getType().equals(RepositoryConstants.TYPE_COLORRING)
                            || node.getType().equals(RepositoryConstants.TYPE_NEWS)
                            || node.getType().equals(RepositoryConstants.TYPE_AUDIO))*/
                        {
                          
                        }if(id.charAt(0)>='0'&&id.charAt(0)<='9')
                        {
                            // 调用GoodsBO中的addNodeAndInsertGoodsInfor保存节点并储存商品信息
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
                //跳回未分类页面
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
            //写操作日志
            this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
        }
        else
        {
            //步骤1、选择资源，
            //把选择的contentID列表保存到session中即可
            String[] dealContents = request.getParameterValues("dealContent");
            String backURL=request.getParameter("backURL");
            request.getSession().setAttribute("backURL", backURL);
            request.getSession().setAttribute("addToCgyContents", dealContents);
            //forward到分类树让用户选择分类
            forward = "showTree";
        }//if(isFinished.equals("true"))
        return mapping.findForward(forward);
    }
}

