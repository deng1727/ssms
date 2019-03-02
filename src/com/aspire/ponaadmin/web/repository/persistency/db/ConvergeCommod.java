
package com.aspire.ponaadmin.web.repository.persistency.db;

import java.sql.ResultSet;

import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.repository.persistency.Command;

public class ConvergeCommod extends Command
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
     * 需要返回的资源的类型
     */
    private String nodeType;

    /**
     * 日志引用
     */
    private static final JLogger LOG = LoggerFactory.getLogger(ConvergeCommod.class);

    /**
     * 构造方法
     * 
     * @param sql String，sql语句
     * @param paras Object[]，sql语句的参数
     * @param nodeType String，需要返回的资源的类型，为null表示返回基本类型
     */
    public ConvergeCommod(String sql, Object[] paras, String nodeType)
    {

        this.sql = sql;
        this.paras = paras;
        this.nodeType = nodeType;
        if (nodeType == null)
        {
            this.nodeType = "nt:base";
        }
    }

    /*
     * (non-Javadoc)
     * 
     */
    public Object execute()
    {

        ResultSet rs = null;
        Integer result = null;
        try
        {
            LOG.debug("ConvergeCommod.sql==" + sql);
            // 调用com.aspire.ponaadmin.common.db.query方法得到结果集
            rs = DB.getInstance().query(this.sql, this.paras);
            if (rs != null && rs.next())
            {
                result = new Integer(rs.getInt(1));
            }// 遍历结果集

        }
        catch (Exception e)
        {
            LOG.error(e);
        }
        finally
        {
            DB.close(rs);
        }

        return result;
    }

}
