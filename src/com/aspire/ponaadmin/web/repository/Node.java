
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
 * ��Դ���еĽڵ��࣬�̳���Item
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
     * ��־����
     */
    private static final JLogger LOG = LoggerFactory.getLogger(Node.class);

    /**
     * �ӽڵ����Ա仯��������
     */
    private Object propertiesLock = new Object();

    /**
     * �ڵ�������б�
     */
    protected HashMap properties = new HashMap();

    /**
     * �ڵ�������б�
     */
    protected HashMap changeProperties = new HashMap();

    /**
     * �־û�������
     */
    protected NodePersistency persistency = null;

    /**
     * �ӽڵ�仯��������
     */
    private Object childNodeLock = new Object();

    /**
     * �����ӵ��¼��ӽڵ�
     */
    protected List addChilds = new ArrayList();

    /**
     * ���Ƴ����¼��ӽڵ�
     */
    protected List delChilds = new ArrayList();

    /**
     * ���췽��
     */
    public Node()
    {

        this.type = "nt:base";
        this.persistency = PersistencyFactory.getPersistency(this);
    }

    /**
     * ������
     * 
     * @param nodeID String���ڵ�id
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
     * ����һ���µ���Դid
     * 
     * @return���µ���Դid
     */
    public String getNewAllocateID()
    {

        return this.persistency.allocateNewNodeID();
    }

    /**
     * �ڱ��ڵ�������һ���ڵ㡣
     * 
     * @param childNode Node��Ҫ������ӽڵ�
     * @return String���·���Ľڵ�ID
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
     * �ڱ��ڵ����Ƴ�һ���ӽڵ㡣
     * 
     * @param childNode Node��Ҫ�Ƴ����ӽڵ��ID
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
     * �ڱ��ڵ��²���һ���ӽڵ㡣
     * 
     * @param nodeID String��Ҫ���ҵ��ӽڵ��ID
     * @param isRecursive boolean���Ƿ�֧���������
     * @return Node���ҵ����ӽڵ㣬����ҵ���Ӧ���ӽڵ��򷵻�null��
     * @throws BOException
     */
    public Node getNode(String nodeID, boolean isRecursive) throws BOException
    {

        // ����������
        Searchor searchor = new Searchor();
        // ����������id==?
        SearchParam param = new SearchParam();
        param.setProperty("id");
        param.setValue(nodeID);
        param.setOperator(RepositoryConstants.OP_EQUAL);
        param.setMode(RepositoryConstants.SEARCH_PARAM_MODE_AND);
        searchor.getParams().add(param);
        searchor.setIsRecursive(isRecursive);

        // ������������
        List list = this.searchNodes(null, searchor, null);

        if ((list == null) || (list.size() == 0))
        {
            // û���ҵ�
            return null;
        }
        return ( Node ) list.get(0);
    }

    /**
     * �ڱ��ڵ��²���һ������Ϊtype���ӽڵ㡣 ���ص����Ͳ�����type���������ͻ�
     * 
     * @param nodeID String��Ҫ���ҵ��ӽڵ��ID
     * @param type String��Ҫ���ҵ�����
     * @param isRecursive boolean���Ƿ�֧���������
     * @return Node���ҵ����ӽڵ㣬����ҵ���Ӧ���ӽڵ��򷵻�null��
     * @throws BOException
     */
    public Node getNode(String nodeID, String type, boolean isRecursive)
                    throws BOException
    {

        // ����������
        Searchor searchor = new Searchor();
        // ����������id==?
        SearchParam param = new SearchParam();
        param.setProperty("id");
        param.setValue(nodeID);
        param.setOperator(RepositoryConstants.OP_EQUAL);
        param.setMode(RepositoryConstants.SEARCH_PARAM_MODE_AND);
        searchor.getParams().add(param);
        searchor.setIsRecursive(isRecursive);

        // ������������
        List list = this.searchNodes(type, searchor, null);

        if ((list == null) || (list.size() == 0))
        {
            // û���ҵ�
            return null;
        }
        return ( Node ) list.get(0);
    }

    /**
     * ��ȡ���ڵ��ĳ���ӽڵ㡣
     * 
     * @param type String��Ҫ���ҵ��ӽڵ����ͣ����Ϊnull��ʾ�����������͵��ӽڵ�
     * @param taxis Taxis�� �����������Ϊnull��ʾ��������
     * @return List���ҵ����ӽڵ���б�
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
     * �ڱ��ڵ��²�ѯ�����������ӽڵ㡣
     * 
     * @param type String��Ҫ���ҵ��ӽڵ����ͣ����Ϊnull��ʾ�����������͵��ӽڵ�
     * @param searchor Searchor �� ��������
     * @param taxis Taxis�� �����������Ϊnull��ʾ��������
     * @return List���ҵ����ӽڵ���б�
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
     * �ڱ��ڵ��²�ѯ�����������ӽڵ㲢��ҳ��
     * 
     * @param pager PageResult����ҳ��
     * @param type String��Ҫ���ҵ��ӽڵ����ͣ����Ϊnull��ʾ�����������͵��ӽڵ�
     * @param searchor Searchor �� ��������
     * @param taxis Taxis�� �����������Ϊnull��ʾ��������
     */
    public void searchNodes(PageResult pager, String type, Searchor searchor,
                            Taxis taxis)
    {

        this.persistency.searchNodes(pager, type, searchor, taxis);
    }

    /**
     * �ڱ��ڵ��¼��������������ӽڵ㡣
     * 
     * @param type String��Ҫ���ҵ��ӽڵ����ͣ����Ϊnull��ʾ�����������͵��ӽڵ�
     * @param searchor Searchor �� ��������
     * @param taxis Taxis�� �����������Ϊnull��ʾ��������
     * @return List���ҵ����ӽڵ���б�
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
     * ����[�����ڵ�����/δ�����ڵ�����]��ĳ�����Դ
     * 
     * @param type String����������
     * @param searchor Searchor
     * @param taxis Taxis
     * @return List
     */
    public List searchRefNodes(String type, Searchor searchor, Taxis taxis)
    {

        return this.persistency.searchRefNodes(type, searchor, taxis);
    }

    /**
     * ����[�����ڵ�����/δ�����ڵ�����]��ĳ�����Դ
     * 
     * @param pager PageResult����ҳ��
     * @param type String����������
     * @param searchor Searchor
     * @param taxis Taxis
     */
    public void searchRefNodes(PageResult pager, String type,
                               Searchor searchor, Taxis taxis)
    {

        this.persistency.searchRefNodes(pager, type, searchor, taxis);
    }

    /**
     * ����Ա��ڵ���ӽڵ�ı䶯�����û�����addNode��delNode����Ҫ����saveNode�Żᱣ�档
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
     * ���ýڵ��һ�����ԡ�
     * 
     * @param prop Property��Ҫ���õ����ԡ�
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
     * �����������Ʋ��ҽڵ��һ�����ԡ�
     * 
     * @param propName String��Ҫ���ҵ��������ơ�
     * @return Property����Ӧ�����ԣ����û�з����򷵻�null��
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
     * ����Խڵ����Եı䶯��
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
     * toString����
     * 
     * @return ������Ϣ
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
     * ����[�����ڵ�����/δ�����ڵ�����]��ĳ�����Դ
     * 
     * @param type String����������
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
