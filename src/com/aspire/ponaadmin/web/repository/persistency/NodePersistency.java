package com.aspire.ponaadmin.web.repository.persistency ;

import java.util.List;
import com.aspire.ponaadmin.web.repository.Searchor;
import com.aspire.ponaadmin.web.repository.Taxis;
import com.aspire.ponaadmin.web.repository.Node;
import java.util.HashMap;
import com.aspire.ponaadmin.common.page.PageResult;

/**
 * <p>�־û����ĳ�����</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public abstract class NodePersistency
{

    /**
     * ���췽��
     * @param node����Ӧ�Ľڵ�ʵ��
     */
	public NodePersistency(Node node)
    {
        this.node = node;
    }

    /**
     * ��Ӧ�Ľڵ�ʵ����
     */
    protected Node node;

    /**
     * �־û����ĳ�ʼ������
     * @param configFile String�����ļ�
     */
    public abstract void init(String configFile);

    /**
     * ����һ���µĽڵ�ID
     * @return String���µĽڵ�id
     */
    public abstract String allocateNewNodeID();

    /**
     * ����Խڵ����Եı䶯
     * @param changeProperties HashMap
     */
    public abstract void save(HashMap changeProperties);

    /**
     * ����Ա��ڵ���ӽڵ�ı䶯��
     * @param addChilds List,���ӵĽڵ��б�
     * @param delChilds List,ɾ���Ľڵ��б�
     */
    public abstract void saveNode(List addChilds, List delChilds);

    /**
     * �ڱ��ڵ��²�ѯ�����������ӽڵ�
     * @param type String,Ҫ���ҵ��ӽڵ����ͣ����Ϊnull��ʾ�����������͵��ӽڵ㡣
     * @param searchor Searchor,��������
     * @param taxis Taxis,�����������Ϊnull��ʾ��������
     * @return List
     */
    public abstract List searchNodes(String type, Searchor searchor, Taxis taxis);

    /**
     * �ڱ��ڵ��²�ѯ�����������ӽڵ㣨��ҳ�棩
     * @param pager PageResult����ҳ��
     * @param type String,Ҫ���ҵ��ӽڵ����ͣ����Ϊnull��ʾ�����������͵��ӽڵ㡣
     * @param searchor Searchor,��������
     * @param taxis Taxis,�����������Ϊnull��ʾ��������
     */
    public abstract void searchNodes(PageResult pager, String type, Searchor searchor, Taxis taxis);

    /**
     * �ڱ��ڵ��¼��������������ӽڵ�
     * @param type String,Ҫ���ҵ��ӽڵ����ͣ����Ϊnull��ʾ�����������͵��ӽڵ㡣
     * @param searchor Searchor,��������
     * @param taxis Taxis,�����������Ϊnull��ʾ��������
     * @return List
     */
    public abstract int countNodes(String type, Searchor searchor, Taxis taxis);

    /**
     * ����[�����ڵ�����/δ�����ڵ�����]���õ�ĳ�����Դ
     * @param type String����������
     * @param searchor Searchor
     * @param taxis Taxis
     * @return List
     */
    public abstract List searchRefNodes(String type, Searchor searchor, Taxis taxis);

    /**
     * ����[�����ڵ�����/δ�����ڵ�����]��ĳ�����Դ
     * @param pager PageResult����ҳ��
     * @param type String����������
     * @param searchor Searchor
     * @param taxis Taxis
     */
    public abstract void searchRefNodes(PageResult pager, String type, Searchor searchor
    		                            , Taxis taxis);
    
    /**
     * �������ݵ�����ID�����ֵ����Сֵ
     * @param refid
     * @param searchor
     * @return
     */
    
    public abstract int searchConverge(String type,Searchor searchor,String operate,String column);

}
