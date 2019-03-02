package com.aspire.ponaadmin.web.rightmanager ;

import com.aspire.ponaadmin.web.tree.TreeNodesGenerate;
import com.aspire.ponaadmin.web.tree.TreeNode;
import java.util.Map;
import java.util.ArrayList;
import com.aspire.ponaadmin.common.rightmanager.RightManagerBO;
import com.aspire.ponaadmin.common.rightmanager.CompositeRightVO;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import java.util.List;
import com.aspire.ponaadmin.web.tree.TreeNodeBase;
import com.aspire.ponaadmin.common.rightmanager.RightVO;

/**
 * <p>����Ȩ����������������</p>
 * <p>ʵ����TreeNodesGenerate�ӿ�</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */

public class RightTreeNodesGenerate implements TreeNodesGenerate
{

    /**
     * ��־����
     */
    protected static JLogger LOGGER = LoggerFactory.getLogger(RightTreeNodesGenerate.class);

    /**
     * Ȩ�����ĸ��ڵ�id
     */
    public static final String TREE_ROOT_RIGHT_ID = "_tree_root_right_id";

    /**
     * Ȩ��ID
     */
    private String rightID;

    /**
     * ���캯��
     */
    public RightTreeNodesGenerate()
    {
    }

    /**
     * ϵͳ�ش�����
     * @param map map
     */
    public void setParameters(Map map)
    {
        this.rightID = (String) map.get(PARA_KEY);
    }

    /**
     * ȡ���ڵ㼯��
     * @return ArrayList �ڵ㼯��
     */
    public ArrayList getTreeNodeCollection()
    {
        LOGGER.debug("getTreeNodeCollection()");
        ArrayList result = null;
        if (this.rightID.equals(RightTreeNodesGenerate.TREE_ROOT_RIGHT_ID))
        {
            result = this.getNodesOfRoot();
        }
        else
        {
            result = this.getNodesOfComposite(this.rightID);
        }
        return result;
    }

    /**
     * ��ȡ���ڵ�������ڵ��б�
     * @return ArrayList ���ڵ�������ڵ��б�
     */
    private ArrayList getNodesOfRoot()
   {
       ArrayList result = new ArrayList() ;
       try
       {
           List rightList = RightManagerBO.getInstance().getAllRight();
           for (int i = 0 ; i < rightList.size() ; i++)
           {
               RightVO right = (RightVO) rightList.get(i) ;
               //ȡ������û�и��ڵ��
               if(right.getParentID() == null)
               {
                   result.add(this.convertRightToNode(right)) ;
               }
           }
       }
       catch(Exception e)
       {
           LOGGER.error(e);
           result = new ArrayList();
       }
       return result;
    }

     /**
      * ��ȡ����Ȩ�޵������ڵ��б�
      * @param compositeRightID String ����Ȩ��id
      * @return ArrayList ����Ȩ�޵������ڵ��б�
      */
     private ArrayList getNodesOfComposite(String compositeRightID)
    {
        ArrayList result = new ArrayList() ;
        try
        {
            CompositeRightVO compositeRight = (CompositeRightVO)
                RightManagerBO.
                getInstance().getRightByID(compositeRightID) ;
            List rightList = compositeRight.getRightList() ;
            for (int i = 0 ; i < rightList.size() ; i++)
            {
                RightVO right = (RightVO) rightList.get(i) ;
                result.add(this.convertRightToNode(right)) ;
            }
        }
        catch(Exception e)
        {
            LOGGER.error(e);
            result = new ArrayList();
        }
        return result;
    }

    /**
     * ��һ��RightVOת����TreeNodeBase�ڵ����
     * @param right RightVO
     * @return TreeNodeBase
     */
    private TreeNodeBase convertRightToNode(RightVO right)
    {
        TreeNodeBase node = new TreeNodeBase() ;
        node.setTreeKey(right.getRightID()) ;
        node.setTreeName(right.getName()) ;
        if (right instanceof CompositeRightVO)
        {
            node.setTreeFolder(true) ;
        }
        else
        {
            node.setTreeFolder(false) ;
        }
        return node;
    }

    /**
     * ȡ����չURL�ַ���
     * ��ʽΪ keyStr=Valuestr&....
     * @param treeNode TreeNode ��ǰ�ڵ�
     * @return String url��չ�ַ���
     */
    public String getTreeUrlParaExtendString(TreeNode treeNode)
    {
        return "";
    }
}

