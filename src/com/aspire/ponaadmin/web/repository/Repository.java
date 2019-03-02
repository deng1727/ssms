package com.aspire.ponaadmin.web.repository ;

import java.util.List;

import com.aspire.common.exception.BOException;
import com.aspire.ponaadmin.web.repository.persistency.PersistencyFactory;

/**
 * <p>资源仓库类。</p>
 * <p>资源仓库类。一个系统中可以存在多个资源仓库。</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author zhangmin
 * @version 1.0.1.0
 * @since 1.0.0.0
 */

public class Repository
{

    /**
     * 资源仓库的名称。
     */
    protected String Name;

    /**
     * 根节点实例
     */
    protected Node rootNode;

    /**
     * singleton模式的实例
     */
    private static Repository instance = new Repository() ;

    /**
     * 构造方法，由singleton模式调用
     */
    private Repository ()
    {
        this.rootNode = new Node(RepositoryConstants.ROOT_NODE_ID);
        this.rootNode.setPath(RepositoryConstants.ROOT_NODE_PATH);
        //可以设置根节点的一些信息
        this.rootNode.setProperty(new Property("name", "资源库的根节点"));
    }

    /**
     * 获取实例
     * @return 实例
     */
    public static Repository getInstance ()
    {
        return instance ;
    }

    /**
     * 资源仓库的初始化方法
     * @param configFile String配置文件
     */
    public void init(String configFile)
    {
        //初始化持久器
        PersistencyFactory.getPersistency(this.rootNode).init(configFile);
    }

    /**
     * 获取资源库的名称
     * @return String
     */
    public String getName ()
    {
        return Name ;
    }

    /**
     * 取得资源树的根节点。
     * @return Node，根节点。
     */
    public Node getRootNode()
    {
        return this.rootNode;
    }

    /**
     * 根据节点的ID获取对应的节点。
     * 返回的类型只是Node基类
     * @param nodeID String，节点ID
     * @return Node，对应的节点，如果找不到就为null。
     * @throws BOException
     */
    public Node getNode(String nodeID) throws BOException
    {
        return this.rootNode.getNode(nodeID, true);
    }

    /**
     * 查找类型为type的节点
     * 返回的类型并根据type进行子类型化
     * @param nodeID String，节点ID
     * @param type String，节点类型
     * @return Node，对应的节点，如果找不到就为null。
     * @throws BOException
     */
    public Node getNode(String nodeID, String type) throws BOException
    {
        return this.rootNode.getNode(nodeID, type, true);
    }

    /**
     * 根据节点的ID获取对应的节点。
     * @param path String，节点路径
     * @return Node，对应的节点，如果找不到就为null。
     * @throws BOException
     */
    public Node getNodeByPath(String path) throws BOException
    {
        //构造搜索器
        Searchor searchor = new Searchor();
        //搜索条件：path==?
        SearchParam param = new SearchParam();
        param.setProperty("path");
        param.setValue(path);
        param.setOperator(RepositoryConstants.OP_EQUAL);
        param.setMode(RepositoryConstants.SEARCH_PARAM_MODE_AND);
        searchor.getParams().add(param);
        searchor.setIsRecursive(true);

        //调用搜索方法
        List list = this.rootNode.searchNodes(null, searchor, null);

        if(list.size() == 0)
        {
            //没有找到
            return null;
        }
        return (Node) list.get(0);
    }

}
