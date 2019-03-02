package com.aspire.ponaadmin.web.repository.persistency.db ;

import java.sql.ResultSet;

import com.aspire.common.db.DB;
import com.aspire.ponaadmin.web.repository.persistency.Command;

/**
 * <p>计数数据库持久化命令。其内部封装了一次数据库持久化需要执行的一条查询sql命令。</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class CountDBCommand  extends Command
{

    /**
     * sql语句
     */
    private String sql;

    /**
     * sql语句的参数
     */
    private Object[] paras;

    /**
     * 构造方法
     * @param sql String，sql语句
     * @param paras Object[]，sql语句的参数
     */
    public CountDBCommand (String sql, Object[] paras)
    {
        this.sql = sql ;
        this.paras = paras ;
    }

    /**
     * 执行持久化操作的方法，覆盖Command.execute方法，请参见。
     * @return Object，持久化结果
     * @todo Implement this
     *   com.aspire.ponaadmin.web.repository.persistency.Command method
     */
    public Object execute ()
    {
        //定义返回的结果，是一个列表
        Integer result = null;
        ResultSet rs = null;
        try
        {
            //调用com.aspire.ponaadmin.common.db.query方法得到结果集
            rs = DB.getInstance().query(this.sql, this.paras);

            if (rs != null && rs.next())
            {
                result = new Integer(rs.getInt(1));
            }//遍历结果集
        }
        catch (Exception e)
        {
            
        }
        finally
        {
            DB.close(rs);
        }
        return result;
    }

}
