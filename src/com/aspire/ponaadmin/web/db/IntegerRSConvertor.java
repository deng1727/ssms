package com.aspire.ponaadmin.web.db ;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * <p>Title:ת��rs�еĵ�һ��������ֵ��һ������count����ͳ�ƺ�����ѯ����� </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class IntegerRSConvertor  implements ResultSetConvertor
{
    public Object convert(ResultSet rs) throws SQLException
    {
        int value = rs.getInt(1);
        return new Integer(value);
    }
}
