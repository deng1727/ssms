package com.aspire.dotcard.colorringmgr.clrLoad;

import java.util.Date;
import java.util.List;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.threadtask.Task;
import com.aspire.dotcard.gcontent.GColorring;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.CategoryBO;
import com.aspire.ponaadmin.web.repository.ReferenceNode;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.repository.SearchParam;
import com.aspire.ponaadmin.web.repository.Searchor;
import com.aspire.ponaadmin.web.util.DateUtil;
import com.aspire.ponaadmin.web.util.PublicUtil;

public class ColorringAddTask extends Task
{
	 /**
     * 日志引用
     */
    protected static JLogger logger = LoggerFactory.getLogger(ColorringAddTask.class);
    
    private GColorring gColorringVO;
    
    public ColorringAddTask(GColorring clrVO)
    {
    	this.gColorringVO=clrVO;
    }
    public void task() throws Throwable
	{
		Category node = new Category();
        node.setId("702");
        node.setPath("{100}.{702}");

        node.addNode(gColorringVO);
        node.saveNode();
        this.addIntoCategory(RepositoryConstants.ROOT_CATEGORY_GCOLORRING_ID, gColorringVO.getId(), gColorringVO.getTonebigtype(), gColorringVO.getCateName(), 0) ;
		
	}
	 /**
     * 将导入的内容分到相应的大、小类中
     * 
     * @param pCategoryId,彩铃分类
     * @param nodeID,内容的节点id
     * @param tonebigtype,铃音大类名称
     * @param cateName,铃音小类名称
     * @throws BOException
     */
    private void addIntoCategory(String pCategoryId, String nodeID,
            String tonebigtype, String cateName, int i) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("addIntoCategory(" + pCategoryId + "," + nodeID + ","
                    + tonebigtype + "," + cateName + "," + i + ")");
        }

        // 如果导入的数据有问题，没有填写大类，上架到彩铃特殊分类：其他分类
        if (tonebigtype == null || tonebigtype.trim().equals(""))
        {
            Category category = (Category) Repository.getInstance().getNode(RepositoryConstants.ROOT_CHILDCATEGORY_COLORRING_SPECIALID, RepositoryConstants.TYPE_CATEGORY);
            ReferenceNode ref = new ReferenceNode();
            ref.setRefNodeID(nodeID);
            // 排序id都填0
            ref.setSortID(0);
            ref.setCategoryID(category.getCategoryID());
            ref.setGoodsID(PublicUtil.rPad(category.getCategoryID() + "|"
                                           + nodeID + "|", 39, "0"));
            ref.setLoadDate(DateUtil.formatDate(new Date(),
                                                "yyyy-MM-dd HH:mm:ss"));
            category.addNode(ref);
            category.saveNode();
            return;
        }

        // 得到铃音分类(彩铃根分类)
        Category category =ColorContentBO.rootColorCagetory; /*( Category ) Repository.getInstance()
                                                   .getNode(pCategoryId,
                                                            RepositoryConstants.TYPE_CATEGORY);*/
        Searchor search = new Searchor();
        // 构造大类名称相同的搜索条件
        search.getParams().add(new SearchParam("name",
                                               RepositoryConstants.OP_EQUAL,
                                               tonebigtype.trim()));
        List list = category.searchNodes(RepositoryConstants.TYPE_CATEGORY, search, null);
        //大类对象
        Category childCategory = null;
        //小类对象
        Category grandchildCategory = null;
        // 得到相匹配的大类对象
        if (list != null && list.size() > 0)
        {
            if (logger.isDebugEnabled())
            {
                logger.debug("铃音大类存在：：：：" + tonebigtype);
            }
            childCategory = (Category) list.get(0);
            // 构造小类名称相同的搜索条件
            Searchor childSearch = new Searchor();
            childSearch.getParams()
                       .add(new SearchParam("name",
                                            RepositoryConstants.OP_EQUAL,
                                            cateName.trim()));
            list = childCategory.searchNodes(RepositoryConstants.TYPE_CATEGORY,
                                             childSearch,
                                             null);
            // 得到相匹配的小类对象
            if (list != null && list.size() > 0)
            {
                // 实现在导入内容时同时导入到相应的分类下
                grandchildCategory = (Category) list.get(0);
            }
            // 小类不存在时要创建小类
            else
            {
                if (logger.isDebugEnabled())
                {
                    logger.debug("铃音小类不存在，需要创建:" + cateName);
                }
                grandchildCategory = new Category();
                grandchildCategory.setName(cateName);
                grandchildCategory.setDesc(cateName);
                grandchildCategory.setRelation("O");
                String grandchildCategoryID = CategoryBO.getInstance()
                                                        .addCategory(childCategory.getId(),
                                                                     grandchildCategory);
                if (logger.isDebugEnabled())
                {
                    logger.debug("the grandchildCategoryID is " + grandchildCategoryID);
                }
                grandchildCategory = ( Category ) Repository.getInstance()
                                                            .getNode(grandchildCategoryID,
                                                                     RepositoryConstants.TYPE_CATEGORY);
            }
        }
        // 大类不存在时要创建
        else
        {
            if (logger.isDebugEnabled())
            {
                logger.debug("铃音大类不存在，需要创建：" + tonebigtype);
            }
            // 创建大类
            childCategory = new Category();
            childCategory.setName(tonebigtype);
            childCategory.setDesc(tonebigtype);
            childCategory.setRelation("O");
            String childCategoryID = CategoryBO.getInstance()
                                               .addCategory(pCategoryId,
                                                            childCategory);
            childCategory = ( Category ) Repository.getInstance()
                                                   .getNode(childCategoryID,
                                                            RepositoryConstants.TYPE_CATEGORY);
            if (logger.isDebugEnabled())
            {
                logger.debug("铃音小类不存在，需要创建：" + cateName);
            }
            // 创建小类
            grandchildCategory = new Category();
            grandchildCategory.setName(cateName);
            grandchildCategory.setDesc(cateName);
            grandchildCategory.setRelation("O");
            String grandchildCategoryID = CategoryBO.getInstance()
                                                    .addCategory(childCategoryID,
                                                                 grandchildCategory);
            grandchildCategory = ( Category ) Repository.getInstance()
                                                        .getNode(grandchildCategoryID,
                                                                 RepositoryConstants.TYPE_CATEGORY);
            if (logger.isDebugEnabled())
            {
                logger.debug("the grandchildCategoryID======" + grandchildCategoryID);
            }
        }
        ReferenceNode ref = new ReferenceNode();
        ref.setRefNodeID(nodeID);
        ref.setSortID(i);
        ref.setCategoryID(grandchildCategory.getCategoryID());
        ref.setGoodsID(PublicUtil.rPad(grandchildCategory.getCategoryID() + "|"
                                       + nodeID + "|", 39, "0"));
        ref.setLoadDate(DateUtil.formatDate(new Date(),
                                            "yyyy-MM-dd HH:mm:ss"));
        grandchildCategory.addNode(ref);
        grandchildCategory.saveNode();
        if (logger.isDebugEnabled())
        {
            logger.debug("addIntoCategory success![" + nodeID + "].");
        }
    }

	

}
