/*
 * 
 */

package com.aspire.ponaadmin.web.baserecomm;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * @author x_wangml
 * 
 */
public class BaseRecommDAO
{

    /**
     * 记录日志的实例对象
     */
    protected static JLogger logger = LoggerFactory.getLogger(BaseRecommDAO.class);

    /**
     * singleton模式的实例
     */
    private static BaseRecommDAO instance = new BaseRecommDAO();

    /**
     * 构造方法，由singleton模式调用
     */
    private BaseRecommDAO()
    {
    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static BaseRecommDAO getInstance()
    {
        return instance;
    }

    /**
     * 把数据存入临时表中
     * 
     * @param baseIds 数据id集合
     * @param baseType 数据类型
     * @throws DAOException
     */
    public void addBaseData(String[] baseIds, String baseType)
                    throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("addBaseData(" + baseType + ") is starting ...");
        }

        // insert into t_base_temp (baseid, basetype) values (?, ?)
        String sqlCode = "basegame.BaseGameDAO.addBaseData().INSERT";
        String[] mutiSQL = new String[baseIds.length];
        Object paras[][] = new String[baseIds.length][2];

        for (int i = 0; i < baseIds.length; i++)
        {
            mutiSQL[i] = sqlCode;
            paras[i][0] = baseIds[i];
            paras[i][1] = baseType;
        }

        try
        {
            DB.getInstance().executeMutiBySQLCode(mutiSQL, paras);
        }
        catch (DAOException e)
        {
            throw new DAOException("把数据存入临时表时发生异常:", e);
        }
    }
    
    /**
     * 用于把数据从临时表中删除
     * 
     * @param baseIds 数据id集合
     * @param baseType 数据类型
     * @throws DAOException
     */
    public void delBaseData(String[] baseIds, String baseType)
                    throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("delBaseData(" + baseType + ") is starting ...");
        }

        // delete from t_base_temp t where baseType=? and baseid=?
        String sqlCode = "basegame.BaseGameDAO.delBaseData().DELETE";
        String[] mutiSQL = new String[baseIds.length];
        Object paras[][] = new String[baseIds.length][2];

        for (int i = 0; i < baseIds.length; i++)
        {
            mutiSQL[i] = sqlCode;
            paras[i][0] = baseType;
            paras[i][1] = baseIds[i];
        }

        try
        {
            DB.getInstance().executeMutiBySQLCode(mutiSQL, paras);
        }
        catch (DAOException e)
        {
            throw new DAOException("把数据从临时表中删除时发生异常:", e);
        }
    }

    /**
     * 用于判断数据是否存在列表中
     * 
     * @param baseId
     * @param baseType
     * @return
     * @throws DAOException
     */
    public boolean isHasBaseDate(String baseId, String baseType)
                    throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("isHasBaseDate(" + baseId + ", " + baseType
                         + ") is starting ...");
        }

        // select 1 from t_base_temp t where t.baseid=? and t.basetype=?
        String sqlCode = "basegame.BaseGameDAO.isHasBaseDate().SELECT";

        ResultSet rs = DB.getInstance()
                         .queryBySQLCode(sqlCode, new Object[] { baseId, baseType });

        try
        {
            // 如果存在next说明存在数据
            if (rs.next())
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (SQLException e)
        {
            throw new DAOException("读取数据临时表时发生数据库错误");
        }
        finally
        {
            DB.close(rs);
        }
    }
}
