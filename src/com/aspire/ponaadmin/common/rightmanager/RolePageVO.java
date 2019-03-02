package com.aspire.ponaadmin.common.rightmanager ;

import java.sql.ResultSet ;
import java.sql.SQLException ;
import com.aspire.ponaadmin.common.page.PageVOInterface ;

/**
 * <p>角色分页的封装VO类</p>
 * <p>角色分页的封装VO类</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */

public class RolePageVO
    implements PageVOInterface
{

    /**
     *
     * @param vo VOObject
     * @param rs ResultSet
     * @throws SQLException
     * @todo Implement this com.aspire.ponaadmin.PageVOInterface method
     */
    public void CopyValFromResultSet (Object vo, ResultSet rs)
        throws SQLException
    {
        RoleDAO.getRoleVOFromRS((RoleVO) vo, rs);
    }

    /**
     *
     * @return VOObject
     * @todo Implement this com.aspire.ponaadmin.PageVOInterface method
     */
    public Object createObject ()
    {
        return new RoleVO();
    }
}
