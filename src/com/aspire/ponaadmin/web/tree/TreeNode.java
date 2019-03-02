package com.aspire.ponaadmin.web.tree;

import java.util.ArrayList;

/**
 * 
 * 加载树节点接口
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

public interface TreeNode
{
    
    /**
     * 取出id的值 
     *@return id
     */
    public String getTreeKey();
    
    /**
     * 取出isFolder的值 
     *@return isFolder
     */
    public boolean isTreeFolder();

    /**
     * 判断节点是否自动加载类型
     * @return
     */
    public boolean isLoad();

    /**
     * 取出扩展标志
     * 可以根据此扩展标志进行操作
     * @return
     */
    public String getExtendFlag();

    /**
     * 取出name的值 
     *@return name
     */
    public String getTreeName();

    /**
     * 取出childs的值 
     *@return childs
     */
    public ArrayList getTreeChilds();
    
    
}
