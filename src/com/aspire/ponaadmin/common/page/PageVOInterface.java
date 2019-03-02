package com.aspire.ponaadmin.common.page ;

import java.sql.ResultSet ;
import java.sql.SQLException;

/**
 *
 * <p>分页对象VO的构造和赋值接口</p>
 * <p>Copyright: Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author maobg
 * @version 1.0.0.0
 */
public interface PageVOInterface
{

    /**
     * 创建VO对象
     *
     * @return VOObject
     */
    public Object createObject () ;

    /**
     * 从ResultSet值赋给VO对象
     *
     * @param vo VOObject
     * @param rs ResultSet
     * @throws  SQLException
     */
    public void CopyValFromResultSet (Object vo, ResultSet rs) throws SQLException ;
}
