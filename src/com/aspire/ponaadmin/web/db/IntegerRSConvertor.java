package com.aspire.ponaadmin.web.db ;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * <p>Title:转换rs中的第一个整型数值，一般用于count、和统计函数查询情况下 </p>
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
