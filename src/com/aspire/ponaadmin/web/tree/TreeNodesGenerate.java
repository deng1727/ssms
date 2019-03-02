package com.aspire.ponaadmin.web.tree;

import java.util.ArrayList;
import java.util.Map;

/**
 * 加载树节点集合的的生成接口
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

public interface TreeNodesGenerate
{
    
    /**
     * 关键字：key
     */    
    static final public String PARA_KEY = "key";
    
    /**
     * 系统回传参数
     * @param map
     */
    public void setParameters(Map map);

    /**
     * 取出节点集合
     * @return
     */
    public ArrayList getTreeNodeCollection();
    
    /**
     * 取出扩展URL字符串
     * 格式为 keyStr=Valuestr&....
     * 一队键值之间用"&"区分
     *
     * @return
     * @param treeNode 
     */
    public String getTreeUrlParaExtendString(TreeNode treeNode);
}
