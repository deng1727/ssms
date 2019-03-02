package com.aspire.ponaadmin.common.usermanager ;

import java.sql.ResultSet ;
import java.sql.SQLException ;

import com.aspire.ponaadmin.common.page.PageVOInterface ;

/**
 * <p>用户个人信息分页封装类</p>
 * <p>用户个人信息分页封装类</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class UserPageVO
    implements PageVOInterface
{

    /**
     *
     * @param vo VOObject
     * @param rs ResultSet
     * @throws SQLException
     */
    public void CopyValFromResultSet (Object vo, ResultSet rs)
        throws SQLException
    {
        UserDAO.getUserVOFromRS((UserVO) vo, rs);
    }

    /**
     *
     * @return VOObject
     */
    public Object createObject ()
    {
        return new UserVO() ;
    }
}
