package com.aspire.dotcard.baseread.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseread.vo.BookUpdateVO;

public class RUpdateDao
{
    protected static JLogger log = LoggerFactory.getLogger(RUpdateDao.class);

    private static RUpdateDao instance = new RUpdateDao();

    public synchronized static RUpdateDao getInstance()
    {

        return instance;
    }

    private RUpdateDao()
    {
    }

    /**
     * 新增图书分类
     * 
     * @param type
     * @throws Exception
     */
    public void addUpdate(BookUpdateVO update) throws Exception
    {
        if (log.isDebugEnabled())
        {
            log.debug("add RUpdateDao(" + update + ")");
        }
        // insert into T_RB_STATISTICS_NEW (Contentid, READERNUM, FLOWERSNUM,
        // CLICKNUM, FAVORITESNUM, ORDERNUM, VOTENUM) values (?,?,?,?,?,?,?)
        String sqlCode = "com.aspire.dotcard.baseread.dao.RUpdateDao.addUpdate";

        // 定义在sql语句中要替换的参数,
        Object[] paras = { update.getContentId(), update.getUpdatTime() };
        DB.getInstance().executeBySQLCode(sqlCode, paras);
    }

    /**
     * 更新 修改
     * 
     * @param type
     * @throws Exception
     */
    public void update(BookUpdateVO update) throws Exception
    {
        if (log.isDebugEnabled())
        {
            log.debug("update RUpdateDao(" + update + ")");
        }
        String sqlCode = "com.aspire.dotcard.baseread.dao.RUpdateDao.update";
        
        // 定义在sql语句中要替换的参数,
        Object[] paras = { update.getUpdatTime(), update.getContentId() };
        DB.getInstance().executeBySQLCode(sqlCode, paras);
    }

    /**
     * 检查是否存在
     * 
     * @param type
     * @return false不存在 true存在
     * @throws Exception
     */
    public boolean isExist(BookUpdateVO update) throws Exception
    {
        int count = 0;
        String sqlCode = "com.aspire.dotcard.baseread.dao.RUpdateDao.isExist";

        ResultSet rs = null;
        try
        {
            Object[] paras = { update.getContentId()};
            rs = DB.getInstance().queryBySQLCode(sqlCode, paras);

            while (rs.next())
            {
                count = rs.getInt("count");
            }
        }
        catch (SQLException e)
        {
            log.error("数据库操作失败");
            throw new DAOException("数据库操作失败，" + e);

        }
        finally
        {
            DB.close(rs);
        }
        return count == 0 ? false : true;
    }

}
