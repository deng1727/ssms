package com.aspire.common.db ;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * <p>Title:��ȡ�׼�¼��һ���ֶε�ת����</p>
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
public class OneFieldRSConbertor implements ResultSetConvertor
{
    private String fieldName;

    public OneFieldRSConbertor(String fieldName)
    {
        this.fieldName = fieldName;
    }

    public Object convert(ResultSet rs) throws SQLException
    {
        return rs.getString(this.fieldName);
    }
}
