package com.aspire.ponaadmin.web.repository.web ;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.tree.TreeNode;
import com.aspire.ponaadmin.web.tree.TreeNodeBase;
import com.aspire.ponaadmin.web.tree.TreeNodesGenerate;
import com.aspire.ponaadmin.web.repository.Node;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.SearchParam;
import com.aspire.ponaadmin.web.repository.Searchor;
import com.aspire.ponaadmin.web.repository.Taxis;
import com.aspire.ponaadmin.web.repository.TaxisParam;

/**
 * <p>���ܹ������Ļ�������xml����������</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class CategoryTreeGenerate implements TreeNodesGenerate
{

    /**
     * ��־����ʵ��
     */
    protected static JLogger logger = LoggerFactory.getLogger(CategoryTreeGenerate.class);

    /**
     * ������������ݵ���ʼ��֧�ڵ�ģɣ�
     */
    private String startid;

    /**
     * ���캯��
     */
    public CategoryTreeGenerate()
    {
    }

    /**
     * ���ò���
     * @param map Map
     */
    public void setParameters(Map map)
    {
        startid = (String) map.get(TreeNodesGenerate.PARA_KEY);
    }

    /**
     * ҵ���ö���ֻҪ��ʾһ��Ŀ¼��������ҵ���������Ҫչʾ���еķ���
     * @return ArrayList �ڵ��б�
     */
    public ArrayList getTreeNodeCollection()
    {
        logger.debug("getTreeNodeCollection()");
        ArrayList retlist = new ArrayList();
        try
        {
            Node node = new Node(this.startid);
            Taxis taxis = new Taxis();
            taxis.getParams().add(new TaxisParam("sortID", RepositoryConstants.ORDER_TYPE_DESC));
            taxis.getParams().add(new TaxisParam("ctype", RepositoryConstants.ORDER_TYPE_DESC));
            taxis.getParams().add(new TaxisParam("name", RepositoryConstants.ORDER_TYPE_ASC));
            Searchor searchor = new Searchor();
            searchor.getParams().add(new SearchParam("delFlag",RepositoryConstants.OP_NOT_EQUAL,String.valueOf(1)));
            List nodeList = node.searchNodes(RepositoryConstants.TYPE_CATEGORY, searchor, taxis);
            for(int i = 0; (nodeList != null ) && (i < nodeList.size()); i++)
            {
                Category category = (Category) nodeList.get(i);
                TreeNodeBase treeNode = new TreeNodeBase() ;
                treeNode.setTreeKey(category.getId()) ;
                treeNode.setTreeName(category.getName());
                treeNode.setTreeFolder(true) ;
                retlist.add(treeNode) ;
            }
        }
        catch (Exception ex)
        {
            logger.error(ex);
        }
        return retlist;
    }

    /**
     * ȡ�����ڵ����չ����
     * @param treeNode TreeNode
     * @return String
     */
    public String getTreeUrlParaExtendString(TreeNode treeNode)
    {
        return "";
    }

}
