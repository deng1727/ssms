package com.aspire.ponaadmin.web.tree;

import java.util.ArrayList;

/**
 * �������ڵ��Ĭ��ʵ����
 * 
 * <p>Copyright: Copyright (c) 2003-2005 <p>
 * 
 * <p>ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * 
 * <p>Company: ׿�����뼼�������ڣ����޹�˾</p>
 * 
 *  @author    ������
 * 
 *  @version   1.0 *
 *
 */

public class TreeNodeBase implements TreeNode
{

    /**
     * �ؼ���ID
     */
    private String treeKey;

    /**
     * ������
     */
    private String treeName;

    /**
     * �Ƿ�Ϊ�ļ���
     */
    private boolean isTreeFolder;

    /**
     * �ӽڵ�
     */
    private ArrayList treeChilds;

    /**
     * ������
     */
    private TreeNodeBase  parent;
    
    /**
     * ��չ��־
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
     * ����extendFlag��ֵ
     * @param extendFlag ����ֵ
     */
    public void setExtendFlag(String extendFlag)
    {
    
        this.extendFlag = extendFlag;
    }

    /**
     * ����isTreeFolder��ֵ
     * @param isTreeFolder ����ֵ
     */
    public void setTreeFolder(boolean isTreeFolder)
    {

        this.isTreeFolder = isTreeFolder;
    }

    /**
     * ����treeChilds��ֵ
     * @param treeChilds ����ֵ
     */
    public void setTreeChilds(ArrayList treeChilds)
    {

        this.treeChilds = treeChilds;
    }

    /**
     * ����treeKey��ֵ
     * @param treeKey ����ֵ
     */
    public void setTreeKey(String treeKey)
    {

        this.treeKey = treeKey;
    }

    /**
     * ����treeName��ֵ
     * @param treeName ����ֵ
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
     * ȡ��parent��ֵ 
     *@return parent
     */
    public TreeNodeBase getParent()
    {
    
        return parent;
    }

    /**
     * ����parent��ֵ
     * @param parent ����ֵ
     */
    public void setParent(TreeNodeBase parent)
    {
    
        this.parent = parent;
    }
    
    

}
