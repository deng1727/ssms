/*
 * 
 */

package com.aspire.ponaadmin.web.category.intervenor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.db.SQLUtil;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.DataAccessException;
import com.aspire.common.persistence.util.SQLCode;

/**
 * 人工干预容器数据操作类
 * 
 * @author x_wangml
 * 
 */
public class IntervenorDAO
{

    /**
     * 记录日志的实例对象
     */
    protected static JLogger logger = LoggerFactory.getLogger(IntervenorDAO.class);

    /**
     * singleton模式的实例
     */
    private static IntervenorDAO instance = new IntervenorDAO();

    /**
     * 构造方法，由singleton模式调用
     */
    private IntervenorDAO()
    {

    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static IntervenorDAO getInstance()
    {

        return instance;
    }

    /**
     * 为对象属性赋值
     * 
     * @param vo
     * @param rs
     * @throws SQLException
     */
    private void fromIntervenorVOByRs(IntervenorVO vo, ResultSet rs)
                    throws SQLException
    {

        vo.setId(rs.getInt("id"));
        vo.setName(rs.getString("name"));
        vo.setStartDate(rs.getDate("startDate").toString());
        vo.setEndDate(rs.getDate("endDate").toString());
        vo.setStartSortId(rs.getInt("startSortId"));
        vo.setEndSortId(rs.getInt("endSortId"));
    }

    /**
     * 根据容器名称返回容器列表
     * 
     * @param page
     * @param name
     * @throws DAOException
     */
    public List queryIntervenorVOList(String name) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("queryIntervenorVOList(" + name + ") is starting ...");
        }

        // select * from t_intervenor t where 1=1
        String sqlCode = "intervenor.IntervenorDAO.queryIntervenorVOList().SELECT";
        String sql = null;
        ResultSet rs = null;
        List list = new ArrayList();

        try
        {
            sql = SQLCode.getInstance().getSQLStatement(sqlCode);

            StringBuffer sqlBuffer = new StringBuffer(sql) ;
            List paras = new ArrayList();
            //构造搜索的sql和参数
            
            if (!"".equals(name))
            {
                //sql += " and t.name like '%" + name + "%'";
            	sqlBuffer.append( " and t.name like ? ");
            	paras.add("%"+SQLUtil.escape(name)+"%");
            }

            //rs = DB.getInstance().query(sql, null);
            rs = DB.getInstance().query(sqlBuffer.toString(), paras.toArray());

            while (rs.next())
            {
                IntervenorVO vo = new IntervenorVO();

                fromIntervenorVOByRs(vo, rs);

                list.add(vo);
            }
        }
        catch (DataAccessException e)
        {
            throw new DAOException("从sql.properties中获取sql语句时发生错误，对应sqlCode不存在。",
                                   e);
        }
        catch (DAOException e)
        {
            throw new DAOException("根据货架内码得到货架策略规则表信息查询发生异常:", e);
        }
        catch (SQLException e)
        {
            throw new DAOException("根据货架内码得到货架策略规则表信息查询发生异常:", e);
        }
        finally
        {
            DB.close(rs);
        }

        return list;
    }

    /**
     * 根据容器id得到干预容器
     * 
     * @param id 容器id
     * @return
     * @throws DAOException
     */
    public IntervenorVO queryInternorVOById(String id) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("queryInternorVOById(" + id + ") is starting ...");
        }

        // select * from t_intervenor t where t.id=?
        String sqlCode = "intervenor.IntervenorDAO.queryInternorVOById().SELECT";
        ResultSet rs = null;
        IntervenorVO vo = new IntervenorVO();

        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, new Object[] { id });

            if (rs.next())
            {
                fromIntervenorVOByRs(vo, rs);
            }
        }
        catch (DAOException e)
        {
            throw new DAOException("根据容器id得到干预容器信息查询发生异常:", e);
        }
        catch (SQLException e)
        {
            throw new DAOException("根据容器id得到干预容器信息查询发生异常:", e);
        }
        finally
        {
            DB.close(rs);
        }
        
        return vo;
    }

    /**
     * 释放过期容器id列表
     */
    public void overdueIntervenorId() throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("overdueIntervenorId() is starting ...");
        }

        // update t_intervenor t set t.isdel = '1' where t.enddate <= sysdate
        String sqlCode ="intervenor.IntervenorDAO.overdueIntervenorId().internor.DELETE";
        Object paras[] = null;
        
        try
        {
            DB.getInstance().executeBySQLCode(sqlCode, paras);
        }
        catch (DAOException e)
        {
            throw new DAOException("释放过期容器id列表发生异常:", e);
        }
    }
    
    /**
     * 修改容器信息
     * 
     * @param vo
     * @return
     */
    public int editInternorVO(IntervenorVO vo) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("editInternorVO(vo=" + vo.toString()
                         + ") is starting ...");
        }

        // update t_intervenor t set
        // name=?,startDate=?,endDate=?,startSortId=?,endSortId=? where id = ?
        String sqlCode = "intervenor.IntervenorDAO.editInternorVO().UPDATE";

        int num = DB.getInstance()
                    .executeBySQLCode(sqlCode,
                                      new Object[] {
                                                      vo.getName(),
                                                      String.valueOf(vo.getStartDate()),
                                                      String.valueOf(vo.getEndDate()),
                                                      String.valueOf(vo.getStartSortId()),
                                                      String.valueOf(vo.getEndSortId()),
                                                      String.valueOf(vo.getId()) });

        return num;

    }

    /**
     * 根据容器id删除容器
     * 
     * @param id 容器id
     * @return
     */
    public void deleteInternorVOById(String id) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("deleteInternorVOById(" + id + ") is starting ...");
        }

        // delete from t_intervenor
        String[] mutiSQL = {
                        "intervenor.IntervenorDAO.deleteInternorVOById().category.DELETE",
                        "intervenor.IntervenorDAO.deleteInternorVOById().gcontent.DELETE",
                        "intervenor.IntervenorDAO.deleteInternorVOById().internor.DELETE" };
        Object paras[][] = { { id }, { id }, { id } };
        try
        {
            DB.getInstance().executeMutiBySQLCode(mutiSQL, paras);
        }
        catch (DAOException e)
        {
            throw new DAOException("根据容器id得到干预容器信息查询发生异常:", e);
        }
    }

    /**
     * 新增容器信息
     * 
     * @param vo 容器信息
     * @return
     */
    public int addInternorVO(IntervenorVO vo) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("addInternorVO(vo=" + vo.toString()
                         + ") is starting ...");
        }

        // insert into
        // t_intervenor(id,name,startdate,enddate,startsortid,endsortid)values(?,?,to_date(?,'yyyyMMddhh'),to_date(?,'yyyyMMddhh'),?,?)
        String sqlCode = "intervenor.IntervenorDAO.addInternorVO().INSERT";

        int num = DB.getInstance()
                    .executeBySQLCode(sqlCode,
                                      new Object[] {
                                                      String.valueOf(vo.getId()),
                                                      vo.getName(),
                                                      String.valueOf(vo.getStartDate()),
                                                      String.valueOf(vo.getEndDate()),
                                                      String.valueOf(vo.getStartSortId()),
                                                      String.valueOf(vo.getEndSortId()), });

        return num;

    }

    /**
     * 得到SEQ用以当容器id
     * 
     * @return
     * @throws DAOException
     */
    public int getInternorId() throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug(" getInternorId()");
        }
        int result = -1;

        try
        {
            result = DB.getSeqValue("SEQ_INTERVENOR_ID");
        }
        catch (Exception e)
        {
            logger.error("IntervenorDAO.getInternorId():", e);
            throw new DAOException(e);
        }
        return result;
    }
    
    /**
     * 根据容器名称查看是否存在此容器名
     * 
     * @param name 容器名称
     * @return
     * @throws DAOException
     */
    public boolean hasInternorName(String name) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug(" hasInternorName("+name+")");
        }

        // select count(*) from t_intervenor t where t.name=?
        String sqlCode = "intervenor.IntervenorDAO.hasInternorName().SELECT";
        ResultSet rs = null;
        int i = 0;

        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, new Object[] { name });

            if (rs.next())
            {
                i = rs.getInt(1);
            }
        }
        catch (DAOException e)
        {
            throw new DAOException("根据容器名称查看是否存在此容器名时发生异常:", e);
        }
        catch (SQLException e)
        {
            throw new DAOException("根据容器名称查看是否存在此容器名时发生异常:", e);
        }
        finally
        {
            DB.close(rs);
        }

        if(i == 0)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}
