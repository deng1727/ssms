package com.aspire.ponaadmin.web.repository ;

import java.util.List;

import com.aspire.common.exception.BOException;
import com.aspire.ponaadmin.web.repository.persistency.PersistencyFactory;

/**
 * <p>��Դ�ֿ��ࡣ</p>
 * <p>��Դ�ֿ��ࡣһ��ϵͳ�п��Դ��ڶ����Դ�ֿ⡣</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author zhangmin
 * @version 1.0.1.0
 * @since 1.0.0.0
 */

public class Repository
{

    /**
     * ��Դ�ֿ�����ơ�
     */
    protected String Name;

    /**
     * ���ڵ�ʵ��
     */
    protected Node rootNode;

    /**
     * singletonģʽ��ʵ��
     */
    private static Repository instance = new Repository() ;

    /**
     * ���췽������singletonģʽ����
     */
    private Repository ()
    {
        this.rootNode = new Node(RepositoryConstants.ROOT_NODE_ID);
        this.rootNode.setPath(RepositoryConstants.ROOT_NODE_PATH);
        //�������ø��ڵ��һЩ��Ϣ
        this.rootNode.setProperty(new Property("name", "��Դ��ĸ��ڵ�"));
    }

    /**
     * ��ȡʵ��
     * @return ʵ��
     */
    public static Repository getInstance ()
    {
        return instance ;
    }

    /**
     * ��Դ�ֿ�ĳ�ʼ������
     * @param configFile String�����ļ�
     */
    public void init(String configFile)
    {
        //��ʼ���־���
        PersistencyFactory.getPersistency(this.rootNode).init(configFile);
    }

    /**
     * ��ȡ��Դ�������
     * @return String
     */
    public String getName ()
    {
        return Name ;
    }

    /**
     * ȡ����Դ���ĸ��ڵ㡣
     * @return Node�����ڵ㡣
     */
    public Node getRootNode()
    {
        return this.rootNode;
    }

    /**
     * ���ݽڵ��ID��ȡ��Ӧ�Ľڵ㡣
     * ���ص�����ֻ��Node����
     * @param nodeID String���ڵ�ID
     * @return Node����Ӧ�Ľڵ㣬����Ҳ�����Ϊnull��
     * @throws BOException
     */
    public Node getNode(String nodeID) throws BOException
    {
        return this.rootNode.getNode(nodeID, true);
    }

    /**
     * ��������Ϊtype�Ľڵ�
     * ���ص����Ͳ�����type���������ͻ�
     * @param nodeID String���ڵ�ID
     * @param type String���ڵ�����
     * @return Node����Ӧ�Ľڵ㣬����Ҳ�����Ϊnull��
     * @throws BOException
     */
    public Node getNode(String nodeID, String type) throws BOException
    {
        return this.rootNode.getNode(nodeID, type, true);
    }

    /**
     * ���ݽڵ��ID��ȡ��Ӧ�Ľڵ㡣
     * @param path String���ڵ�·��
     * @return Node����Ӧ�Ľڵ㣬����Ҳ�����Ϊnull��
     * @throws BOException
     */
    public Node getNodeByPath(String path) throws BOException
    {
        //����������
        Searchor searchor = new Searchor();
        //����������path==?
        SearchParam param = new SearchParam();
        param.setProperty("path");
        param.setValue(path);
        param.setOperator(RepositoryConstants.OP_EQUAL);
        param.setMode(RepositoryConstants.SEARCH_PARAM_MODE_AND);
        searchor.getParams().add(param);
        searchor.setIsRecursive(true);

        //������������
        List list = this.rootNode.searchNodes(null, searchor, null);

        if(list.size() == 0)
        {
            //û���ҵ�
            return null;
        }
        return (Node) list.get(0);
    }

}
