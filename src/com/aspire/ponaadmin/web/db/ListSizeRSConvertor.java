package com.aspire.ponaadmin.web.db ;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * <p>Title:列表size转换器，把一个rs转换为一个列表，列表中都是object对象，只是用来记录rs的记录数 </p>
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
public class ListSizeRSConvertor implements ResultSetConvertor
{
    public Object convert(ResultSet rs) throws SQLException
    {
        return new Object();
    }
}
