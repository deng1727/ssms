package com.aspire.ponaadmin.web.tree;

import java.util.ArrayList;

/**
 * 加载树节点的默认实现类
 * 
 * <p>Copyright: Copyright (c) 2003-2005 <p>
 * 
 * <p>ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * 
 * <p>Company: 卓望数码技术（深圳）有限公司</p>
 * 
 *  @author    胡春雨
 * 
 *  @version   1.0 *
 *
 */

public class TreeNodeBase implements TreeNode
{

    /**
     * 关键字ID
     */
    private String treeKey;

    /**
     * 描述名
     */
    private String treeName;

    /**
     * 是否为文件夹
     */
    private boolean isTreeFolder;

    /**
     * 子节点
     */
    private ArrayList treeChilds;

    /**
     * 父对象
     */
    private TreeNodeBase  parent;
    
    /**
     * 扩展标志
     */
    private String extendFlag;

    /**
     * @return boolean
     */
    public boolean isLoad()
    {
        return true;
    }

    /**
     * @return
     * @see com.aspire.ponaadmin.web.tree.TreeNode#getExtendFlag()
     */
    public String getExtendFlag()
    {
        if(this.extendFlag == null || this.extendFlag.trim().equals("")) {
            return "0";
        }else {
            return this.extendFlag;
        }
    }

    /**
     * 设置extendFlag的值
     * @param extendFlag 属性值
     */
    public void setExtendFlag(String extendFlag)
    {
    
        this.extendFlag = extendFlag;
    }

    /**
     * 设置isTreeFolder的值
     * @param isTreeFolder 属性值
     */
    public void setTreeFolder(boolean isTreeFolder)
    {

        this.isTreeFolder = isTreeFolder;
    }

    /**
     * 设置treeChilds的值
     * @param treeChilds 属性值
     */
    public void setTreeChilds(ArrayList treeChilds)
    {

        this.treeChilds = treeChilds;
    }

    /**
     * 设置treeKey的值
     * @param treeKey 属性值
     */
    public void setTreeKey(String treeKey)
    {

        this.treeKey = treeKey;
    }

    /**
     * 设置treeName的值
     * @param treeName 属性值
     */
    public void setTreeName(String treeName)
    {

        this.treeName = treeName;
    }

    /* (non-Javadoc)
     * @see com.aspire.ponaadmin.web.tree.TreeNodeInterface#getTreeChilds()
     */

    public ArrayList getTreeChilds()
    {

        return this.treeChilds;
    }

    /* (non-Javadoc)
     * @see com.aspire.ponaadmin.web.tree.TreeNodeInterface#getTreeKey()
     */

    public String getTreeKey()
    {

        return this.treeKey;
    }

    /* (non-Javadoc)
     * @see com.aspire.ponaadmin.web.tree.TreeNodeInterface#getTreeName()
     */

    public String getTreeName()
    {

        return this.treeName;
    }

    /* (non-Javadoc)
     * @see com.aspire.ponaadmin.web.tree.TreeNodeInterface#isTreeFolder()
     */

    /**
     * @return boolean
     */
    public boolean isTreeFolder()
    {

        return this.isTreeFolder;
    }

    /**
     * 取出parent的值 
     *@return parent
     */
    public TreeNodeBase getParent()
    {
    
        return parent;
    }

    /**
     * 设置parent的值
     * @param parent 属性值
     */
    public void setParent(TreeNodeBase parent)
    {
    
        this.parent = parent;
    }
    
    

}
