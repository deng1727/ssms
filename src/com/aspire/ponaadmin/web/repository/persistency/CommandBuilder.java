
package com.aspire.ponaadmin.web.repository.persistency;

import com.aspire.ponaadmin.web.repository.Node;
import java.util.HashMap;
import java.util.List;
import com.aspire.ponaadmin.web.repository.Searchor;
import com.aspire.ponaadmin.web.repository.Taxis;
import com.aspire.ponaadmin.common.page.PageResult;

/**
 * <p>
 * �־û�������������ݲ�ͬ�ĳ־û��������͹����־û����
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
     * ��������ڵ������Եĳ־û������
     * 
     * @param node Node��Ŀ��ڵ�ʵ�����������map
     * @param changeProperties HashMap
     * @return Command���־û�����
     */
    public abstract Command buildSaveCommand(Node node, HashMap changeProperties);

    /**
     * �����ڵ���ӽڵ�������ĳ־û������
     * 
     * @param node Node��Ŀ��ڵ�ʵ��
     * @param addChilds List�����ӵ��ӽڵ��б�
     * @param delChilds List��ɾ�����ӽڵ��б�
     * @return Command���־û�����
     */
    public abstract Command buildSaveNodeCommand(Node node, List addChilds,
                                                 List delChilds);

    /**
     * �����ڽڵ���������ĳ־û������
     * 
     * @param node Node��Ŀ��ڵ�ʵ��
     * @param type String��Ҫ�����Ľڵ����ͣ�Ϊnull��ʾ�����������͵Ľڵ�
     * @param searchor Searchor�������������ʽ��Ϊnull��ʾ���趨����
     * @param taxis Taxis��������ʽ��Ϊnull��ʾ���趨����
     * @return Command���־û�����
     */
    public abstract Command buildSearchNodeCommand(Node node, String type,
                                                   Searchor searchor,
                                                   Taxis taxis);

    /**
     * �����ڽڵ������������ҳ�ĳ־û������
     * 
     * @param pager PageResult����ҳ��
     * @param node Node��Ŀ��ڵ�ʵ��
     * @param type String��Ҫ�����Ľڵ����ͣ�Ϊnull��ʾ�����������͵Ľڵ�
     * @param searchor Searchor�������������ʽ��Ϊnull��ʾ���趨����
     * @param taxis Taxis��������ʽ��Ϊnull��ʾ���趨����
     * @return Command���־û�����
     */
    public abstract Command buildPagerSearchNodeCommand(PageResult pager,
                                                        Node node, String type,
                                                        Searchor searchor,
                                                        Taxis taxis);

    /**
     * �����ڽڵ���м����ĳ־û������
     * 
     * @param node Node��Ŀ��ڵ�ʵ��
     * @param type String��Ҫ�����Ľڵ����ͣ�Ϊnull��ʾ�����������͵Ľڵ�
     * @param searchor Searchor�������������ʽ��Ϊnull��ʾ���趨����
     * @param taxis Taxis��������ʽ��Ϊnull��ʾ���趨����
     * @return Command���־û�����
     */
    public abstract Command buildCountNodeCommand(Node node, String type,
                                                  Searchor searchor, Taxis taxis);

    /**
     * ��������[�ڽڵ���/δ�ڽڵ���]���õ�����Ϊtype����Դ�ڵ�ĳ־û������
     * 
     * @param node Node��Ŀ��ڵ�ʵ��
     * @param type String��Ҫ�����Ľڵ����ͣ�һ�㲻��Ϊnt:base
     * @param searchor Searchor�������������ʽ��Ϊnull��ʾ���趨����
     * @param taxis Taxis��������ʽ��Ϊnull��ʾ���趨����
     * @return Command���־û�����
     */
    public abstract Command buildSearchRefCommand(Node node, String type,
                                                  Searchor searchor, Taxis taxis);

    /**
     * ��������[�ڽڵ���/δ�ڽڵ���]���õ�����Ϊtype����Դ�ڵ�ĳ־û�������ķ�ҳ�汾
     * 
     * @param pager PageResult����ҳ��
     * @param node Node��Ŀ��ڵ�ʵ��
     * @param type String��Ҫ�����Ľڵ����ͣ�һ�㲻��Ϊnt:base
     * @param searchor Searchor�������������ʽ��Ϊnull��ʾ���趨����
     * @param taxis Taxis��������ʽ��Ϊnull��ʾ���趨����
     * @return Command���־û�����
     */
    public abstract Command buildPagerSearchRefCommand(PageResult pager,
                                                       Node node, String type,
                                                       Searchor searchor,
                                                       Taxis taxis);

    /**
     * ��������[�ڽڵ���/δ�ڽڵ���]���õ�����Ϊtype����Դ�ڵ��sql����ִ����
     * 
     * @param node Node��Ŀ��ڵ�ʵ��
     * @param type String��Ҫ�����Ľڵ����ͣ�һ�㲻��Ϊnt:base
     * @param searchor Searchor�������������ʽ��Ϊnull��ʾ���趨����
     * @param taxis Taxis��������ʽ��Ϊnull��ʾ���趨����
     * @return Command���־û�����
     */
    public abstract Command buildSearchConvergeCommand(Node node, String type,
                                                       Searchor searchor,
                                                       String operate,
                                                       String column);

}
