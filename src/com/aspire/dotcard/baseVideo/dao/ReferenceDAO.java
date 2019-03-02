package com.aspire.dotcard.baseVideo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class ReferenceDAO
{
    /**
     * 记录日志的实例对象
     */
    protected static JLogger logger = LoggerFactory.getLogger(ReferenceDAO.class);

    /**
     * singleton模式的实例
     */
    private static ReferenceDAO instance = new ReferenceDAO();

    /**
     * 构造方法，由singleton模式调用
     */
    private ReferenceDAO()
    {
    }
    
    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static ReferenceDAO getInstance()
    {
        return instance;
    }
    
    /**
     * 查看当前货架下是否还存在着商品
     * 
     * @param page
     * @param vo
     * @throws DAOException
     */
    public int hasVideo(String categoryId)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("hasVideo( ) is starting ...");
        }

        // select count(*) as countNum from t_vo_reference t where t.categoryid = ?
        String sqlCode = "baseVideo.dao.ReferenceDAO.hasVideo.SELECT";
        ResultSet rs = null;
        int countNum = 0;
        
        rs = DB.getInstance().queryBySQLCode(sqlCode, new Object[]{categoryId});

        try
        {
            if (rs.next())
            {
                countNum = rs.getInt("countNum");
            }
        }
        catch (SQLException e)
        {
            throw new DAOException("查看当前货架下是否还存在着商品信息时发生异常:", e);
        }
        
        finally
        {
            DB.close(rs);
        }

        return countNum;
    }
}
