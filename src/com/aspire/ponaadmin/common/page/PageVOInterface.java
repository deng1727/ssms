package com.aspire.ponaadmin.common.page ;

import java.sql.ResultSet ;
import java.sql.SQLException;

/**
 *
 * <p>��ҳ����VO�Ĺ���͸�ֵ�ӿ�</p>
 * <p>Copyright: Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author maobg
 * @version 1.0.0.0
 */
public interface PageVOInterface
{

    /**
     * ����VO����
     *
     * @return VOObject
     */
    public Object createObject () ;

    /**
     * ��ResultSetֵ����VO����
     *
     * @param vo VOObject
     * @param rs ResultSet
     * @throws  SQLException
     */
    public void CopyValFromResultSet (Object vo, ResultSet rs) throws SQLException ;
}
