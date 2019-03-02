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
     * ��־����
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
     * ����������ݷֵ���Ӧ�Ĵ�С����
     * 
     * @param pCategoryId,�������
     * @param nodeID,���ݵĽڵ�id
     * @param tonebigtype,������������
     * @param cateName,����С������
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

        // �����������������⣬û����д���࣬�ϼܵ�����������ࣺ��������
        if (tonebigtype == null || tonebigtype.trim().equals(""))
        {
            Category category = (Category) Repository.getInstance().getNode(RepositoryConstants.ROOT_CHILDCATEGORY_COLORRING_SPECIALID, RepositoryConstants.TYPE_CATEGORY);
            ReferenceNode ref = new ReferenceNode();
            ref.setRefNodeID(nodeID);
            // ����id����0
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

        // �õ���������(���������)
        Category category =ColorContentBO.rootColorCagetory; /*( Category ) Repository.getInstance()
                                                   .getNode(pCategoryId,
                                                            RepositoryConstants.TYPE_CATEGORY);*/
        Searchor search = new Searchor();
        // �������������ͬ����������
        search.getParams().add(new SearchParam("name",
                                               RepositoryConstants.OP_EQUAL,
                                               tonebigtype.trim()));
        List list = category.searchNodes(RepositoryConstants.TYPE_CATEGORY, search, null);
        //�������
        Category childCategory = null;
        //С�����
        Category grandchildCategory = null;
        // �õ���ƥ��Ĵ������
        if (list != null && list.size() > 0)
        {
            if (logger.isDebugEnabled())
            {
                logger.debug("����������ڣ�������" + tonebigtype);
            }
            childCategory = (Category) list.get(0);
            // ����С��������ͬ����������
            Searchor childSearch = new Searchor();
            childSearch.getParams()
                       .add(new SearchParam("name",
                                            RepositoryConstants.OP_EQUAL,
                                            cateName.trim()));
            list = childCategory.searchNodes(RepositoryConstants.TYPE_CATEGORY,
                                             childSearch,
                                             null);
            // �õ���ƥ���С�����
            if (list != null && list.size() > 0)
            {
                // ʵ���ڵ�������ʱͬʱ���뵽��Ӧ�ķ�����
                grandchildCategory = (Category) list.get(0);
            }
            // С�಻����ʱҪ����С��
            else
            {
                if (logger.isDebugEnabled())
                {
                    logger.debug("����С�಻���ڣ���Ҫ����:" + cateName);
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
        // ���಻����ʱҪ����
        else
        {
            if (logger.isDebugEnabled())
            {
                logger.debug("�������಻���ڣ���Ҫ������" + tonebigtype);
            }
            // ��������
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
                logger.debug("����С�಻���ڣ���Ҫ������" + cateName);
            }
            // ����С��
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
