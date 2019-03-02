
package com.aspire.dotcard.colorringmgr.clrLoad;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * <p>
 * 彩铃内容管理DAO类
 * </p>
 * <p>
 * Copyright (c) 2009 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved
 * </p>
 * 
 * @author bihui
 * @version 1.0.0.0
 */
public class ColorContentDAO
{

    /**
     * 日志引用
     */
    protected static JLogger logger = LoggerFactory.getLogger(ColorContentDAO.class);

    /**
     * 构造方法，由singleton模式调用
     */
    private ColorContentDAO()
    {

    }

    /**
     * singleton模式的实例
     */
    private static ColorContentDAO colorContentDAO = new ColorContentDAO();

    /**
     * 获取实例
     * 
     * @return 实例
     */
    static final ColorContentDAO getInstance()
    {

        return colorContentDAO;
    }
     
    /**
     * 查询数据库中存在的所有彩铃的ID
     * @return
     * @throws DAOException
     */
    public HashMap getAllColorringID() throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("getAllColorringID()");
        }
        //select t0.id from t_r_base t0, t_r_gcontent t3 where t0.id = t3.id and t0.type like 'nt:gcontent:colorring%' and t0.parentid = 702;
        String sqlCode = "colorringmgr.ColorContentDAO.getAllColorringID().SELECT";
        ResultSet rs = DB.getInstance().queryBySQLCode(sqlCode, null);
        HashMap res = new HashMap(1000000);//初始化话百万
        try
        {
            while (rs.next())
            {
            	ColorSyncVO vo=new ColorSyncVO();
            	vo.setLupDate(rs.getString("lupddate"));
            	vo.setFlag(0);
               
                String url=rs.getString("cartoonpicture");
                if(url==null||url.equals(""))
                {
                	vo.setConvert(false);
                }else
                {
                	vo.setConvert(true);
                }
                res.put(rs.getString("id"), vo);
            }
        }
        catch (SQLException e)
        {
            throw new DAOException(e);
        }
        finally
        {
            DB.close(rs);
        }

        return res;
    }
}