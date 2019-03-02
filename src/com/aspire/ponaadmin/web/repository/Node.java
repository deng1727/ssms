
package com.aspire.ponaadmin.web.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.repository.persistency.NodePersistency;
import com.aspire.ponaadmin.web.repository.persistency.PersistencyFactory;

/**
 * <p>
 * 资源树中的节点类，继承于Item
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

public class Node extends Item
{

    /**
     * 日志引用
     */
    private static final JLogger LOG = LoggerFactory.getLogger(Node.class);

    /**
     * 子节点属性变化的锁对象
     */
    private Object propertiesLock = new Object();

    /**
     * 节点的属性列表。
     */
    protected HashMap properties = new HashMap();

    /**
     * 节点的属性列表。
     */
    protected HashMap changeProperties = new HashMap();

    /**
     * 持久化操作器
     */
    protected NodePersistency persistency = null;

    /**
     * 子节点变化的锁对象
     */
    private Object childNodeLock = new Object();

    /**
     * 被增加的下级子节点
     */
    protected List addChilds = new ArrayList();

    /**
     * 被移除的下级子节点
     */
    protected List delChilds = new ArrayList();

    /**
     * 构造方法
     */
    public Node()
    {

        this.type = "nt:base";
        this.persistency = PersistencyFactory.getPersistency(this);
    }

    /**
     * 构造器
     * 
     * @param nodeID String，节点id
     */
    public Node(String nodeID)
    {

        this();
        if (LOG.isDebugEnabled())
        {
            LOG.debug("Node(" + nodeID + ")");
        }
        this.id = nodeID;
    }

    /**
     * 分配一个新的资源id
     * 
     * @return，新的资源id
     */
    public String getNewAllocateID()
    {

        return this.persistency.allocateNewNodeID();
    }

    /**
     * 在本节点下增加一个节点。
     * 
     * @param childNode Node，要加入的子节点
     * @return String，新分配的节点ID
     */
    public String addNode(Node childNode)
    {

        if ((childNode == null) || (childNode.getType() == null))
        {
            throw new RuntimeException("invalid parameter childNode.(in Node.addNode method)");

        }
        if (childNode.getId() == null)
        {
            String newNodeID = this.persistency.allocateNewNodeID();
            childNode.setId(newNodeID);
        }
        childNode.setParentID(this.id);
        childNode.setPath(this.path + ".{" + childNode.getId() + "}");
        synchronized (this.childNodeLock)
        {
            this.addChilds.add(childNode);
        }
        return childNode.getId();
    }

    /**
     * 在本节点下移除一个子节点。
     * 
     * @param childNode Node，要移除的子节点的ID
     */
    public void delNode(Node childNode)
    {

        if ((childNode == null) || (childNode.getType() == null)
            || (childNode.getId() == null))
        {
            throw new RuntimeException("invalid parameter childNode.(in Node.delNode method)");

        }
        synchronized (this.childNodeLock)
        {
            this.delChilds.add(childNode);
        }
    }

    /**
     * 在本节点下查找一个子节点。
     * 
     * @param nodeID String，要查找的子节点的ID
     * @param isRecursive boolean，是否支持深度搜索
     * @return Node，找到的子节点，如果找到对应的子节点则返回null。
     * @throws BOException
     */
    public Node getNode(String nodeID, boolean isRecursive) throws BOException
    {

        // 构造搜索器
        Searchor searchor = new Searchor();
        // 搜索条件：id==?
        SearchParam param = new SearchParam();
        param.setProperty("id");
        param.setValue(nodeID);
        param.setOperator(RepositoryConstants.OP_EQUAL);
        param.setMode(RepositoryConstants.SEARCH_PARAM_MODE_AND);
        searchor.getParams().add(param);
        searchor.setIsRecursive(isRecursive);

        // 调用搜索方法
        List list = this.searchNodes(null, searchor, null);

        if ((list == null) || (list.size() == 0))
        {
            // 没有找到
            return null;
        }
        return ( Node ) list.get(0);
    }

    /**
     * 在本节点下查找一个类型为type的子节点。 返回的类型并根据type进行子类型化
     * 
     * @param nodeID String，要查找的子节点的ID
     * @param type String，要查找的类型
     * @param isRecursive boolean，是否支持深度搜索
     * @return Node，找到的子节点，如果找到对应的子节点则返回null。
     * @throws BOException
     */
    public Node getNode(String nodeID, String type, boolean isRecursive)
                    throws BOException
    {

        // 构造搜索器
        Searchor searchor = new Searchor();
        // 搜索条件：id==?
        SearchParam param = new SearchParam();
        param.setProperty("id");
        param.setValue(nodeID);
        param.setOperator(RepositoryConstants.OP_EQUAL);
        param.setMode(RepositoryConstants.SEARCH_PARAM_MODE_AND);
        searchor.getParams().add(param);
        searchor.setIsRecursive(isRecursive);

        // 调用搜索方法
        List list = this.searchNodes(type, searchor, null);

        if ((list == null) || (list.size() == 0))
        {
            // 没有找到
            return null;
        }
        return ( Node ) list.get(0);
    }

    /**
     * 获取本节点的某种子节点。
     * 
     * @param type String，要查找的子节点类型，如果为null表示查找所有类型的子节点
     * @param taxis Taxis， 排序器，如果为null表示不用排序。
     * @return List，找到的子节点的列表。
     * @throws BOException
     */
    public List getNode(String type, Taxis taxis) throws BOException
    {

        try
        {
            return this.persistency.searchNodes(type, null, taxis);
        }
        catch (Exception e)
        {
            throw new BOException("getNode failed!", e);
        }
    }

    /**
     * 在本节点下查询符合条件的子节点。
     * 
     * @param type String，要查找的子节点类型，如果为null表示查找所有类型的子节点
     * @param searchor Searchor ， 搜索器。
     * @param taxis Taxis， 排序器，如果为null表示不用排序。
     * @return List，找到的子节点的列表。
     * @throws BOException
     */
    public List searchNodes(String type, Searchor searchor, Taxis taxis)
                    throws BOException
    {

        try
        {
            return this.persistency.searchNodes(type, searchor, taxis);
        }
        catch (Exception e)
        {
        	e.printStackTrace();
            throw new BOException("searchNodes failed", e);
        }
    }

    /**
     * 在本节点下查询符合条件的子节点并分页。
     * 
     * @param pager PageResult，分页器
     * @param type String，要查找的子节点类型，如果为null表示查找所有类型的子节点
     * @param searchor Searchor ， 搜索器。
     * @param taxis Taxis， 排序器，如果为null表示不用排序。
     */
    public void searchNodes(PageResult pager, String type, Searchor searchor,
                            Taxis taxis)
    {

        this.persistency.searchNodes(pager, type, searchor, taxis);
    }

    /**
     * 在本节点下计数符合条件的子节点。
     * 
     * @param type String，要查找的子节点类型，如果为null表示查找所有类型的子节点
     * @param searchor Searchor ， 搜索器。
     * @param taxis Taxis， 排序器，如果为null表示不用排序。
     * @return List，找到的子节点的列表。
     * @throws BOException
     */
    public int countNodes(String type, Searchor searchor, Taxis taxis)
                    throws BOException
    {

        try
        {
            return this.persistency.countNodes(type, searchor, taxis);
        }
        catch (Exception e)
        {
            throw new BOException("countNodes error!", e);
        }
    }

    /**
     * 搜索[被本节点引用/未被本节点引用]的某类的资源
     * 
     * @param type String，引用类型
     * @param searchor Searchor
     * @param taxis Taxis
     * @return List
     */
    public List searchRefNodes(String type, Searchor searchor, Taxis taxis)
    {

        return this.persistency.searchRefNodes(type, searchor, taxis);
    }

    /**
     * 搜索[被本节点引用/未被本节点引用]的某类的资源
     * 
     * @param pager PageResult，分页器
     * @param type String，引用类型
     * @param searchor Searchor
     * @param taxis Taxis
     */
    public void searchRefNodes(PageResult pager, String type,
                               Searchor searchor, Taxis taxis)
    {

        this.persistency.searchRefNodes(pager, type, searchor, taxis);
    }

    /**
     * 保存对本节点的子节点的变动。当用户调用addNode、delNode后，需要调用saveNode才会保存。
     * 
     * @throws BOException
     */
    public void saveNode() throws BOException
    {

        synchronized (this.childNodeLock)
        {
            try
            {
                this.persistency.saveNode(this.addChilds, this.delChilds);
            }
            catch (Exception ex)
            {
                throw new BOException("saveNode failed!", ex);
            }finally
            {
            	this.addChilds.clear();
                this.delChilds.clear();
            }
            
        }
    }

    /**
     * 设置节点的一个属性。
     * 
     * @param prop Property，要设置的属性。
     */
    public void setProperty(Property prop)
    {

        synchronized (this.propertiesLock)
        {
            this.properties.put(prop.getName(), prop);
            this.changeProperties.put(prop.getName(), prop);
        }
    }

    /**
     * 根据属性名称查找节点的一个属性。
     * 
     * @param propName String，要查找的属性名称。
     * @return Property，对应的属性，如果没有发现则返回null。
     */
    public Property getProperty(String propName)
    {

        synchronized (this.propertiesLock)
        {
            Property prop = ( Property ) this.properties.get(propName);
            if (prop == null)
            {
                prop = new Property(propName, null);
            }
            return prop;
        }
    }

    /**
     * 保存对节点属性的变动。
     * 
     * @throws BOException
     */
    public void save() throws BOException
    {

        synchronized (this.propertiesLock)
        {
            try
            {
                this.persistency.save(this.changeProperties);
            }
            catch (Exception ex)
            {
                throw new BOException("save failed.", ex);
            }
            this.changeProperties.clear();
        }
    }

    /**
     * toString方法
     * 
     * @return 描述信息
     */
    public String toString()
    {

        StringBuffer sb = new StringBuffer();
        sb.append("Node[");
        sb.append(super.toString());
        sb.append("]");
        return sb.toString();
    }

    /**
     * 搜索[被本节点引用/未被本节点引用]的某类的资源
     * 
     * @param type String，引用类型
     * @param searchor Searchor
     * @param taxis Taxis
     * @return List
     */
    public int searchConverge(String type, Searchor searchor, String operate,
                              String column)
    {

        return this.persistency.searchConverge(type, searchor, operate, column);
    }

}
