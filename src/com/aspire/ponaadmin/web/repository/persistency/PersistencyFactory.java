package com.aspire.ponaadmin.web.repository.persistency ;

import com.aspire.ponaadmin.web.repository.Node;
import com.aspire.ponaadmin.web.repository.persistency.db.NodePersistencyDB;

/**
 * <p>�־û����ĳ���</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class PersistencyFactory
{

	/**
	 * ���췽��
	 */
	private PersistencyFactory()
	{}
	
    /**
     * ���෽������ȡ��Ӧ�ĳ־û���ʵ��
     * @param node���ڵ�ʵ��
     * @return���־û���ʵ��
     */
	public static NodePersistency getPersistency(Node node)
    {
        return new NodePersistencyDB(node);
    }
}
