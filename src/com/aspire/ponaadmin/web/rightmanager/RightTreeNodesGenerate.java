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
 * <p>操作权限树的数据生成类</p>
 * <p>实现了TreeNodesGenerate接口</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */

public class RightTreeNodesGenerate implements TreeNodesGenerate
{

    /**
     * 日志引用
     */
    protected static JLogger LOGGER = LoggerFactory.getLogger(RightTreeNodesGenerate.class);

    /**
     * 权限树的根节点id
     */
    public static final String TREE_ROOT_RIGHT_ID = "_tree_root_right_id";

    /**
     * 权限ID
     */
    private String rightID;

    /**
     * 够造函数
     */
    public RightTreeNodesGenerate()
    {
    }

    /**
     * 系统回传参数
     * @param map map
     */
    public void setParameters(Map map)
    {
        this.rightID = (String) map.get(PARA_KEY);
    }

    /**
     * 取出节点集合
     * @return ArrayList 节点集合
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
     * 获取根节点的下属节点列表
     * @return ArrayList 根节点的下属节点列表
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
               //取出所有没有父节点的
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
      * 获取复合权限的下属节点列表
      * @param compositeRightID String 复合权限id
      * @return ArrayList 复合权限的下属节点列表
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
     * 把一个RightVO转换成TreeNodeBase节点对象
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
     * 取出扩展URL字符串
     * 格式为 keyStr=Valuestr&....
     * @param treeNode TreeNode 当前节点
     * @return String url扩展字符串
     */
    public String getTreeUrlParaExtendString(TreeNode treeNode)
    {
        return "";
    }
}

