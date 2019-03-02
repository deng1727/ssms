package com.aspire.ponaadmin.web.repository.persistency ;

import java.util.List;
import com.aspire.ponaadmin.web.repository.Searchor;
import com.aspire.ponaadmin.web.repository.Taxis;
import com.aspire.ponaadmin.web.repository.Node;
import java.util.HashMap;
import com.aspire.ponaadmin.common.page.PageResult;

/**
 * <p>持久化器的抽象类</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public abstract class NodePersistency
{

    /**
     * 构造方法
     * @param node，对应的节点实例
     */
	public NodePersistency(Node node)
    {
        this.node = node;
    }

    /**
     * 对应的节点实例。
     */
    protected Node node;

    /**
     * 持久化器的初始化方法
     * @param configFile String配置文件
     */
    public abstract void init(String configFile);

    /**
     * 分配一个新的节点ID
     * @return String，新的节点id
     */
    public abstract String allocateNewNodeID();

    /**
     * 保存对节点属性的变动
     * @param changeProperties HashMap
     */
    public abstract void save(HashMap changeProperties);

    /**
     * 保存对本节点的子节点的变动。
     * @param addChilds List,增加的节点列表
     * @param delChilds List,删除的节点列表
     */
    public abstract void saveNode(List addChilds, List delChilds);

    /**
     * 在本节点下查询符合条件的子节点
     * @param type String,要查找的子节点类型，如果为null表示查找所有类型的子节点。
     * @param searchor Searchor,搜索器。
     * @param taxis Taxis,排序器，如果为null表示不用排序。
     * @return List
     */
    public abstract List searchNodes(String type, Searchor searchor, Taxis taxis);

    /**
     * 在本节点下查询符合条件的子节点（分页版）
     * @param pager PageResult，分页器
     * @param type String,要查找的子节点类型，如果为null表示查找所有类型的子节点。
     * @param searchor Searchor,搜索器。
     * @param taxis Taxis,排序器，如果为null表示不用排序。
     */
    public abstract void searchNodes(PageResult pager, String type, Searchor searchor, Taxis taxis);

    /**
     * 在本节点下计数符合条件的子节点
     * @param type String,要查找的子节点类型，如果为null表示查找所有类型的子节点。
     * @param searchor Searchor,搜索器。
     * @param taxis Taxis,排序器，如果为null表示不用排序。
     * @return List
     */
    public abstract int countNodes(String type, Searchor searchor, Taxis taxis);

    /**
     * 搜索[被本节点引用/未被本节点引用]引用的某类的资源
     * @param type String，引用类型
     * @param searchor Searchor
     * @param taxis Taxis
     * @return List
     */
    public abstract List searchRefNodes(String type, Searchor searchor, Taxis taxis);

    /**
     * 搜索[被本节点引用/未被本节点引用]的某类的资源
     * @param pager PageResult，分页器
     * @param type String，引用类型
     * @param searchor Searchor
     * @param taxis Taxis
     */
    public abstract void searchRefNodes(PageResult pager, String type, Searchor searchor
    		                            , Taxis taxis);
    
    /**
     * 搜索内容的排序ID的最大值和最小值
     * @param refid
     * @param searchor
     * @return
     */
    
    public abstract int searchConverge(String type,Searchor searchor,String operate,String column);

}
