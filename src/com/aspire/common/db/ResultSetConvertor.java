package com.aspire.common.db ;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * <p>Title:��һ��ResultSetת��Ϊ��Ӧ��ֵ������ʵ�� </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author shidr
 * @version 1.0
 */
public interface ResultSetConvertor
{
    /**
     * ת������
     * @param rs ResultSet
     * @return Object
     * @throws SQLException
     */
    Object convert(ResultSet rs) throws SQLException;
}
