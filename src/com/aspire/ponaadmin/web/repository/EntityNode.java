package com.aspire.ponaadmin.web.repository ;

/**
 * <p>资源树中的实体资源节点类</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class EntityNode extends Node
{

    /**
     * 构造方法
     */
	public EntityNode()
    {}

    /**
     * 构造方法
     * @param nodeID，资源id
     */
	public EntityNode(String nodeID)
    {
        super(nodeID);
    }
}
