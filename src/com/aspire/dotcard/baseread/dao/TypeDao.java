
package com.aspire.dotcard.baseread.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseread.vo.RTypeVO;

public class TypeDao
{

    protected static JLogger log = LoggerFactory.getLogger(TypeDao.class);

    private static TypeDao instance = new TypeDao();

    public synchronized static TypeDao getInstance()
    {

        return instance;
    }

    private TypeDao()
    {
    }

    /**
     * 新增图书分类
     * 
     * @param type
     * @throws Exception
     */
    public void addType(RTypeVO type) throws Exception
    {
        if (log.isDebugEnabled())
        {
            log.debug("add BaseBookType(" + type + ")");
        }
        String sqlCode = "com.aspire.dotcard.baseread.dao.TypeDao.addType";
        // 定义在sql语句中要替换的参数,

        Object[] paras = { type.getTypeId(), type.getTypeName() };
        DB.getInstance().executeBySQLCode(sqlCode, paras);
    }

    /**
     * 更新 修改
     * 
     * @param type
     * @throws Exception
     */
    public void update(RTypeVO type) throws Exception
    {
        if (log.isDebugEnabled())
        {
            log.debug("update BaseBookType(" + type + ")");
        }
        String sqlCode = "com.aspire.dotcard.baseread.dao.TypeDao.update";
        // 定义在sql语句中要替换的参数,

        Object[] paras = { type.getTypeName(), type.getTypeId() };
        DB.getInstance().executeBySQLCode(sqlCode, paras);
    }

    /**
     * 检查是否存在
     * 
     * @param type
     * @return false不存在 true存在
     * @throws Exception
     */
    public boolean isExist(RTypeVO type) throws Exception
    {
        int count = 0;
        String sqlCode = "com.aspire.dotcard.baseread.dao.TypeDao.isExist";

        ResultSet rs = null;
        try
        {
            Object[] paras = { type.getTypeId() };
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

    /**
     * 获取图书类型对象
     * 
     * @param type
     * @return
     * @throws Exception
     */
    public RTypeVO getRTypeVO(RTypeVO type) throws Exception
    {
        String sqlCode = "com.aspire.dotcard.baseread.dao.TypeDao.getTypeById";
        ResultSet rs = null;
        RTypeVO t = null;
        try
        {
            Object[] para = new Object[] { type.getTypeId() };
            rs = DB.getInstance().queryBySQLCode(sqlCode, para);
            while (rs.next())
            {
                t = new RTypeVO();
                t.setTypeName(rs.getString("typename"));
                t.setTypeId(rs.getString("typeid"));
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
        return t;
    }
    
    /**
     * 用于返回当前所有图书分类类型
     * @return
     * @throws Exception
     */
    public Map queryAllTypes() throws Exception
    {
    	// select t.typeid from t_rb_type_new t
        String sqlCode = "com.aspire.dotcard.baseread.dao.TypeDao.queryAllTypes";
        Map m = null;
        ResultSet rs = null;
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, null);
            m = new HashMap();
            while (rs.next())
            {
                m.put(rs.getString("typeid"), rs.getString("typeid"));
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
        return m;
    }
}
