
package com.aspire.ponaadmin.web.datafield.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * <p>
 * 扩展属性值表的DAO类
 * </p>
 * Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights
 * Reserved
 * </p>
 * 
 * @author biran
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class KeyResourceDAO
{

    /**
     * 日志引用
     */
    protected static JLogger logger = LoggerFactory.getLogger(KeyResourceDAO.class);

    /**
     * singleton模式的实例
     */
    private static KeyResourceDAO instance = new KeyResourceDAO();

    /**
     * 构造方法，由singleton模式调用
     */
    private KeyResourceDAO()
    {

    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static final KeyResourceDAO getInstance()
    {

        return instance;
    }

    /**
     * 根据keyid,tid查询扩展属性字段值
     * 
     * @param keyid
     * @param tid
     * @return
     * @throws DAOException
     */
    public final Object[] getResourceValueByID(String keyid, String tid)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("getResourceValueByID(" + keyid + "," + tid + ")");
        }
        String sqlCode = "datafield.KeyResourceDAO.getResourceValueByID().SELECT";
        Object[] paras = { keyid, tid };
        ResultSet rs = null;
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
            paras[0] = "";
            paras[1] = "";
            if (rs.next())
            {
                paras[0] = rs.getString("value");
                paras[1] = rs.getString("lupdate");

            }
        }
        catch (SQLException e)
        {
            throw new DAOException("getResourceValueByID error", e);
        }
        finally
        {
            DB.close(rs);
        }
        return paras;
    }

    /**
     * 插入或更新应用关联扩展属性
     * @param keyid
     * @param value
     * @param tid
     * @return
     * @throws DAOException
     */
    public final int updateResourceValue(String keyid, String value, String tid)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("updateResourceValue(" + keyid + "," + tid + ","
                         + value + ")");
        }
        String sqlCode = "datafield.KeyResourceDAO.updateResourceValue().SELECT";
        Object[] paras = { keyid, tid };
        ResultSet rs = null;
        // true为插入数据，否则更新
        boolean ret = true;
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
            if (rs.next())
            {
                ret = false;
            }
        }
        catch (SQLException e)
        {
            throw new DAOException("updateResourceValue error", e);
        }
        finally
        {
            DB.close(rs);
        }
        try
        {
            if (ret)
            {
                sqlCode = "datafield.KeyResourceDAO.updateResourceValue().INSERT";
                return DB.getInstance()
                         .executeBySQLCode(sqlCode,
                                           new Object[] { keyid, tid, value });
            }
            else
            {
                sqlCode = "datafield.KeyResourceDAO.updateResourceValue().UPDATE";
                return DB.getInstance().executeBySQLCode(sqlCode,
                                                         new Object[] { value,
                                                                         keyid,
                                                                         tid });
            }
        }
        catch (Exception e)
        {
            throw new DAOException("updateResourceValue error", e);
        }
    }
    
    
    
    /**
     * 删除应用关联扩展属性
     * @param keyid
     * @param tid
     * @return
     * @throws DAOException
     */
    public  int delResourceValue(String keyid,  String tid)
                    throws DAOException
    {
		if (logger.isDebugEnabled())
		{
			logger.debug("delResourceValue(" + keyid + "," + tid);
		}
		//delete from T_KEY_RESOURCE k where k.keyid=? and k.tid=?
		String sqlCode = "datafield.KeyResourceDAO.delResourceValue().DELETE";
		Object[] paras = { keyid, tid };
		int rs = 0;
		rs = DB.getInstance().executeBySQLCode(sqlCode, paras);
		return rs;
	}
    
    /**
     * 判断数据库中是否存在此类型的此id对应扩展内容
     * @param keyid 扩展类型
     * @param tid 扩展类型下的指定商品id
     * @return true/存在 false/不存在
     */
    public boolean hasKeyResource(String keyid, String tid) throws DAOException
    {
        String sqlCode = "datafield.KeyResourceDAO.updateResourceValue().SELECT";
        Object[] paras = { keyid, tid };
        ResultSet rs = null;
        
        boolean ret = false;
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
            if (rs.next())
            {
                ret = true;
            }
        }
        catch (SQLException e)
        {
            throw new DAOException("updateResourceValue error", e);
        }
        finally
        {
            DB.close(rs);
        }

        return ret;
    }
    
    /**
     * 文件批量导入扩展属性内容信息
     * 
     * @param keyid keyid
     * @param map 
     * @throws DAOException
     */
    public void addKeyResource(String keyid, Map map)
                    throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("addKeyResource(" + keyid + ") is starting ...");
        }

        // insert into t_key_resource (value, tid, keyid, lupdate) values (?, ?, ?, sysdate)
        String sql_add = "datafield.KeyResourceDAO.addKeyResource().add";
        
        // update t_key_resource t set t.value=?, t.lupdate=sysdate where t.tid=? and t.keyid=?
        String sql_update = "datafield.KeyResourceDAO.addKeyResource().update";
        
        Set set = map.keySet();
        String sqlCode[] = new String[set.size()];
        Object[][] object = new Object[set.size()][3];
        
        int i = 0;
        for (Iterator iter = set.iterator(); iter.hasNext();i++)
        {
            String tid = ( String ) iter.next();
            String desc = ( String )map.get(tid);
            object[i][0] = desc;
            object[i][1] = tid;
            object[i][2] = keyid;
            
            if(hasKeyResource(keyid,tid))
            {
                sqlCode[i] = sql_update;
            }
            else
            {
                sqlCode[i] = sql_add;
            }
        }

        try
        {
            DB.getInstance().executeMutiBySQLCode(sqlCode, object);
        }
        catch (DAOException e)
        {
            throw new DAOException("添加指定的新音乐至货架中时发生异常:", e);
        }
    }
    
    /**
     * 校验批量导入文件中数据正确性
     * 
     * @param map
     * @param sql
     * @throws DAOException
     */
    public String verifyNewMusic(Map map, String sql) throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("verifyBook(" + sql + ") is starting ...");
        }
        
        ResultSet rs = null;
        Set set = map.keySet();
        StringBuffer sb = new StringBuffer();

        // 迭代查詢
        for (Iterator iter = set.iterator(); iter.hasNext();)
        {
			String tid = (String) iter.next();

			try
			{
				if (map.get(tid) == null || map.get(tid).equals(""))
				{
					sb.append(tid).append(". ");

					logger.info("：此id 对应的值为空id=" + tid);
				}
				else
				{
					rs = DB.getInstance().query(sql.toString(), new Object[] { tid });
					// 如果不存在相應數據
					if (!rs.next())
					{
						iter.remove();
						sb.append(tid).append(". ");

						logger.info("verifyBook：此id不存在相应的商品数据不予导入id=" + tid);
					}
				}
			} catch (SQLException e)
			{
				throw new DAOException("查看校验批量导入文件中数据正确性时发生异常:", e);
			} finally
			{
				DB.close(rs);
			}
		}
        
        return sb.toString();
    }
}
