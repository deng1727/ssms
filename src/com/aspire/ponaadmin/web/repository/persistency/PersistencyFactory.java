package com.aspire.ponaadmin.web.repository.persistency ;

import com.aspire.ponaadmin.web.repository.Node;
import com.aspire.ponaadmin.web.repository.persistency.db.NodePersistencyDB;

/**
 * <p>持久化器的厂类</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class PersistencyFactory
{

	/**
	 * 构造方法
	 */
	private PersistencyFactory()
	{}
	
    /**
     * 厂类方法，获取对应的持久化器实例
     * @param node，节点实例
     * @return，持久化器实例
     */
	public static NodePersistency getPersistency(Node node)
    {
        return new NodePersistencyDB(node);
    }
}
