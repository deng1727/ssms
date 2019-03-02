
package com.aspire.dotcard.reportdata.cystatistic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * @author tungke
 * 
 */
public class CyListDAO
{

    /**
     * 记录日志的实例对象
     */
    protected static JLogger LOG = LoggerFactory.getLogger(CyListDAO.class);

    /**
     * singleton模式的实例
     */
    private static CyListDAO instance = new CyListDAO();

    /**
     * 构造方法，由singleton模式调用
     */
    private CyListDAO()
    {

    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static CyListDAO getInstance() throws DAOException
    {

        return instance;
    }

    /**
     * 执行数据库查询
     * 
     * @param sql
     * @param parm
     * @return
     * @throws DAOException
     */
    public void updateCyListVO(String[] mutiSQL, Object paras[][])
                    throws DAOException, Exception
    {

        int count =0;
        ResultSet rs = DB.getInstance().queryBySQLCode(mutiSQL[0], paras[0]);
        try
        {
            if (rs.next())
            {
                count = 1;
            }
        }
        catch (SQLException e)
        {
            LOG.error("查询创业大赛数据失败", e);
            throw new DAOException("查询创业大赛数据失败", e);
        }
        finally
        {
            DB.close(rs);
        }

        if (count == 0)
        {
            // 插入数据
            DB.getInstance().executeBySQLCode(mutiSQL[1], paras[1]);
        }
        else if (count == 1)
        {
            // 更新数据
            DB.getInstance().executeBySQLCode(mutiSQL[2], paras[2]);
        }

    }

}
