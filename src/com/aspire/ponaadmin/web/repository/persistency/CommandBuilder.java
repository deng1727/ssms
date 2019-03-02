
package com.aspire.ponaadmin.web.repository.persistency;

import com.aspire.ponaadmin.web.repository.Node;
import java.util.HashMap;
import java.util.List;
import com.aspire.ponaadmin.web.repository.Searchor;
import com.aspire.ponaadmin.web.repository.Taxis;
import com.aspire.ponaadmin.common.page.PageResult;

/**
 * <p>
 * 持久化命令构建器，根据不同的持久化操作类型构建持久化命令。
 * </p>
 * <p>
 * Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights
 * Reserved
 * </p>
 * 
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public abstract class CommandBuilder
{

    /**
     * 构建保存节点变更属性的持久化命令方法
     * 
     * @param node Node，目标节点实例，变更属性map
     * @param changeProperties HashMap
     * @return Command，持久化命令
     */
    public abstract Command buildSaveCommand(Node node, HashMap changeProperties);

    /**
     * 构建节点的子节点变更保存的持久化命令方法
     * 
     * @param node Node，目标节点实例
     * @param addChilds List，增加的子节点列表
     * @param delChilds List，删除的子节点列表
     * @return Command，持久化命令
     */
    public abstract Command buildSaveNodeCommand(Node node, List addChilds,
                                                 List delChilds);

    /**
     * 构建在节点进行搜索的持久化命令方法
     * 
     * @param node Node，目标节点实例
     * @param type String，要搜索的节点类型，为null表示搜索所有类型的节点
     * @param searchor Searchor，搜索条件表达式，为null表示不设定条件
     * @param taxis Taxis，排序表达式，为null表示不设定排序
     * @return Command，持久化命令
     */
    public abstract Command buildSearchNodeCommand(Node node, String type,
                                                   Searchor searchor,
                                                   Taxis taxis);

    /**
     * 构建在节点进行搜索并分页的持久化命令方法
     * 
     * @param pager PageResult，分页器
     * @param node Node，目标节点实例
     * @param type String，要搜索的节点类型，为null表示搜索所有类型的节点
     * @param searchor Searchor，搜索条件表达式，为null表示不设定条件
     * @param taxis Taxis，排序表达式，为null表示不设定排序
     * @return Command，持久化命令
     */
    public abstract Command buildPagerSearchNodeCommand(PageResult pager,
                                                        Node node, String type,
                                                        Searchor searchor,
                                                        Taxis taxis);

    /**
     * 构建在节点进行计数的持久化命令方法
     * 
     * @param node Node，目标节点实例
     * @param type String，要搜索的节点类型，为null表示搜索所有类型的节点
     * @param searchor Searchor，搜索条件表达式，为null表示不设定条件
     * @param taxis Taxis，排序表达式，为null表示不设定排序
     * @return Command，持久化命令
     */
    public abstract Command buildCountNodeCommand(Node node, String type,
                                                  Searchor searchor, Taxis taxis);

    /**
     * 构建搜索[在节点下/未在节点下]引用的类型为type的资源节点的持久化命令方法
     * 
     * @param node Node，目标节点实例
     * @param type String，要搜索的节点类型，一般不会为nt:base
     * @param searchor Searchor，搜索条件表达式，为null表示不设定条件
     * @param taxis Taxis，排序表达式，为null表示不设定排序
     * @return Command，持久化命令
     */
    public abstract Command buildSearchRefCommand(Node node, String type,
                                                  Searchor searchor, Taxis taxis);

    /**
     * 构建搜索[在节点下/未在节点下]引用的类型为type的资源节点的持久化命令方法的分页版本
     * 
     * @param pager PageResult，分页器
     * @param node Node，目标节点实例
     * @param type String，要搜索的节点类型，一般不会为nt:base
     * @param searchor Searchor，搜索条件表达式，为null表示不设定条件
     * @param taxis Taxis，排序表达式，为null表示不设定排序
     * @return Command，持久化命令
     */
    public abstract Command buildPagerSearchRefCommand(PageResult pager,
                                                       Node node, String type,
                                                       Searchor searchor,
                                                       Taxis taxis);

    /**
     * 构建搜索[在节点下/未在节点下]引用的类型为type的资源节点的sql命令执行器
     * 
     * @param node Node，目标节点实例
     * @param type String，要搜索的节点类型，一般不会为nt:base
     * @param searchor Searchor，搜索条件表达式，为null表示不设定条件
     * @param taxis Taxis，排序表达式，为null表示不设定排序
     * @return Command，持久化命令
     */
    public abstract Command buildSearchConvergeCommand(Node node, String type,
                                                       Searchor searchor,
                                                       String operate,
                                                       String column);

}
