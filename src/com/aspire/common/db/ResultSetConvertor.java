package com.aspire.common.db ;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * <p>Title:把一个ResultSet转换为对应的值对象类实例 </p>
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
     * 转换方法
     * @param rs ResultSet
     * @return Object
     * @throws SQLException
     */
    Object convert(ResultSet rs) throws SQLException;
}
