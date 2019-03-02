
package com.aspire.ponaadmin.web.pivot.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
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
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.page.PageVOInterface;
import com.aspire.ponaadmin.web.pivot.vo.PivotContentVO;
import com.aspire.ponaadmin.web.pivot.vo.PivotDownloadVO;

public class PivotContentDAO
{
    /**
     * 记录日志的实例对象
     */
    protected static JLogger logger = LoggerFactory.getLogger(PivotDeviceDAO.class);

    /**
     * singleton模式的实例
     */
    private static PivotContentDAO instance = new PivotContentDAO();

    /**
     * 构造方法，由singleton模式调用
     */
    private PivotContentDAO()
    {
    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static PivotContentDAO getInstance()
    {
        return instance;
    }

    /**
     * 应用类分页读取VO的实现类
     */
    private class PivotContentPageVO implements PageVOInterface
    {
        public void CopyValFromResultSet(Object content, ResultSet rs)
                        throws SQLException
        {
            PivotContentVO vo = ( PivotContentVO ) content;

            vo.setContentId(rs.getString("content_id"));
            vo.setContentName(rs.getString("content_name"));
            vo.setApCode(rs.getString("ap_id"));
            vo.setApName(rs.getString("ap_name"));
            vo.setCreDate(String.valueOf(rs.getDate("CreDate")));
        }

        public Object createObject()
        {
            return new PivotContentVO();
        }
    }

    /**
     * 用于用于查询重点内容列表
     * 
     * @param page
     * @param vo
     * @throws DAOException
     */
    public void queryPivotContentList(PageResult page, PivotContentVO vo)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("queryBookRefList() is starting ...");
        }

        // select * from t_pivot_content t where 1=1
        String sqlCode = "pivot.PivotContentDAO.queryPivotContentList().SELECT";
        String sql = null;

        try
        {
            sql = SQLCode.getInstance().getSQLStatement(sqlCode);

            StringBuffer sqlBuffer = new StringBuffer(sql) ;
            List paras = new ArrayList();
            //构造搜索的sql和参数
            
            if (!"".equals(vo.getContentId()))
            {
                //sql += " and t.content_id ='" + vo.getContentId() + "'";
            	sqlBuffer.append(" and t.content_id = ? ");
            	paras.add(vo.getContentId());
            }
            if (!"".equals(vo.getContentName()))
            {
//                sql += " and t.content_name like('%" + vo.getContentName()
//                       + "%')";
            	sqlBuffer.append(" and t.content_name like  ? ");
            	paras.add("%"+SQLUtil.escape(vo.getContentName())+"%");
            }
            if (!"".equals(vo.getApCode()))
            {
                //sql += " and t.ap_id like('%" + vo.getApCode() + "%')";
            	sqlBuffer.append(" and t.ap_id like ? ");
            	paras.add("%"+SQLUtil.escape(vo.getApCode())+"%");
            }
            if (!"".equals(vo.getApName()))
            {
                //sql += " and t.ap_name like('%" + vo.getApName() + "%')";
            	sqlBuffer.append(" and t.ap_name like ? ");
            	paras.add("%"+vo.getApName()+"%");
            }

            //sql += " order by t.content_id asc";
            sqlBuffer.append(" order by t.content_id asc");

            //page.excute(sql, null, new PivotContentPageVO());
            page.excute(sqlBuffer.toString(), paras.toArray(), new PivotContentPageVO());
        }
        catch (DataAccessException e)
        {
            throw new DAOException("从sql.properties中获取sql语句时发生错误，对应sqlCode不存在。",
                                   e);
        }
    }

    /**
     * 用于移除指定重点内容
     * 
     * @param contentId 重点内容id列
     * @throws DAOException
     */
    public void removeContentID(String[] contentId) throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("removeContentID() is starting ...");
        }

        // delete from t_pivot_content r where r.content_id = ?
        String sql = "pivot.PivotContentDAO.removeContentID().remove";
        String sqlCode[] = new String[contentId.length];
        Object[][] object = new Object[contentId.length][1];

        for (int i = 0; i < contentId.length; i++)
        {
            sqlCode[i] = sql;
            object[i][0] = contentId[i];
        }

        try
        {
            DB.getInstance().executeMutiBySQLCode(sqlCode, object);
        }
        catch (DAOException e)
        {
            throw new DAOException("移除指定重点内容时发生异常:", e);
        }
    }

    /**
     * 校驗文件中數據是否在内容表中存在
     * 
     * @param list
     * @throws DAOException
     */
    public String verifyContentId(List list) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("verifyContentId() is starting ...");
        }

        // select 1 from t_r_gcontent c where c.contentid = ?
        String sql = "pivot.PivotContentDAO.verifyContentId().select";
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();

        // 迭代查詢
        for (int i = 0; i < list.size(); i++)
        {
            String temp = ( String ) list.get(i);
            try
            {
                rs = DB.getInstance().queryBySQLCode(sql,
                                                     new Object[] { temp });
                // 如果不存在相應數據
                if (!rs.next())
                {
                    list.remove(i);
                    i--;
                    sb.append(temp).append(". ");
                }
            }
            catch (SQLException e)
            {
                throw new DAOException("校驗文件中數據是否在内容表中存在时发生异常:", e);
            }
            finally
            {
                DB.close(rs);
            }
        }

        return sb.toString();
    }

    /**
     * 检验列表中是否存在原表数据
     * 
     * @param list
     * @throws DAOException
     */
    public void hasContentId(List list) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("hasContentId() is starting ...");
        }

        // select 1 from t_pivot_content c where c.content_id = ?
        String sql = "pivot.PivotContentDAO.hasContentId().select";
        ResultSet rs = null;

        // 迭代查詢
        for (int i = 0; i < list.size(); i++)
        {
            String temp = ( String ) list.get(i);
            try
            {
                rs = DB.getInstance().queryBySQLCode(sql,
                                                     new Object[] { temp });
                // 如果存在相應數據
                if (rs.next())
                {
                    list.remove(i);
                    i--;
                }
            }
            catch (SQLException e)
            {
                throw new DAOException("检验列表中是否存在原表数据时发生异常:", e);
            }
            finally
            {
                DB.close(rs);
            }
        }
    }

    /**
     * 添加内容id列表
     * 
     * @param String
     */
    public void addContentId(String[] contentId) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("addContentId() is starting ...");
        }

        // insert into t_pivot_content (content_id, content_name, ap_id,
        // ap_name) select c.contentid, c.name, c.icpcode, c.spname from
        // t_r_Gcontent c where c.contentid=?
        String sql = "pivot.PivotContentDAO.addContentId().add";

        String sqlCode[] = new String[contentId.length];
        Object[][] object = new Object[contentId.length][1];

        for (int i = 0; i < contentId.length; i++)
        {
            sqlCode[i] = sql;
            object[i][0] = contentId[i];
        }

        try
        {
            DB.getInstance().executeMutiBySQLCode(sqlCode, object);
        }
        catch (DAOException e)
        {
            throw new DAOException("添加重点内容id列表时发生异常:", e);
        }
    }

    /**
     * 根据内容编码。得到内容主键
     * 
     * @param contentId
     * @return
     * @throws DAOException
     */
    public String queryContentId(String contentId) throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("queryContentId() is starting ...");
        }

        // select id from t_r_gcontent c where c.contentid = ?
        String sql = "pivot.PivotContentDAO.queryContentId().select";
        ResultSet rs = null;
        String temp = "";

        try
        {
            rs = DB.getInstance().queryBySQLCode(sql,
                                                 new Object[] { contentId });
            // 如果存在相應數據
            if (rs.next())
            {
                temp = rs.getString("id");
            }
        }
        catch (SQLException e)
        {
            throw new DAOException("根据内容编码。得到内容主键时发生异常:", e);
        }
        finally
        {
            DB.close(rs);
        }

        return temp;
    }

    /**
     * 调用存储过程
     * 
     */
    public void prepareCallDownload() throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("prepareCallDownload() is starting ...");
        }
        
        Connection conn = DB.getInstance().getConnection();
        CallableStatement cs = null;
        
        try
        {
            cs = conn.prepareCall("{call P_IMPORTANT_NOMATCH()}");
            cs.execute();
        }
        catch (Exception ex)
        {
            throw new DAOException(ex);
        }
        finally
        {
            try
            {
                cs.close();
                DB.close(conn);
            }
            catch (Exception ex)
            {
                logger.error("关闭CallableStatement=失败");
            }
        }
    }
    
    /**
     * 查询生成下载文件应用的指定表数据
     * 
     * @return
     * @throws DAOException
     */
    public List queryDownloadData() throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("queryDownloadData() is starting ...");
        }

        // select * from t_Important_NOMatch t
        String sql = "pivot.PivotContentDAO.queryDownloadData().select";
        ResultSet rs = null;
        List list = new ArrayList();

        try
        {
            rs = DB.getInstance().queryBySQLCode(sql, null);

            while (rs.next())
            {
                PivotDownloadVO vo = new PivotDownloadVO();
                vo.setContentId(rs.getString("content_id"));
                vo.setContentName(rs.getString("content_name"));
                vo.setApId(rs.getString("ap_id"));
                vo.setApName(rs.getString("ap_name"));
                vo.setDeviceId(String.valueOf(rs.getInt("device_id")));
                vo.setDeviceName(rs.getString("device_name"));
                vo.setBrandName(rs.getString("brand_name"));
                vo.setOsName(rs.getString("os_name"));
                
                list.add(vo);
            }
        }
        catch (SQLException e)
        {
            throw new DAOException("查询生成下载文件应用的指定表数据时发生异常:", e);
        }
        finally
        {
            DB.close(rs);
        }

        return list;
    }
}
