/*
 * 
 */

package com.aspire.ponaadmin.web.category.intervenor.gcontent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.DataAccessException;
import com.aspire.common.persistence.util.SQLCode;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.page.PageVOInterface;
import com.aspire.ponaadmin.web.category.intervenor.IntervenorVO;

/**
 * 容器中存在的内容数据操作类
 * 
 * @author x_wangml
 * 
 */
public class IntervenorGcontentDAO
{

    /**
     * 记录日志的实例对象
     */
    protected static JLogger logger = LoggerFactory.getLogger(IntervenorGcontentDAO.class);

    /**
     * singleton模式的实例
     */
    private static IntervenorGcontentDAO instance = new IntervenorGcontentDAO();

    /**
     * 构造方法，由singleton模式调用
     */
    private IntervenorGcontentDAO()
    {

    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static IntervenorGcontentDAO getInstance()
    {

        return instance;
    }

    /**
     * 应用类分页读取VO的实现类
     */
    private class GcontentPageVO implements PageVOInterface
    {

        public void CopyValFromResultSet(Object content, ResultSet rs)
                        throws SQLException
        {

            IntervenorGcontentVO vo = ( IntervenorGcontentVO ) content;
            vo.setId(rs.getString("id"));
            vo.setIntervenorId(rs.getString("intervenorId"));
            vo.setSortId(rs.getInt("sortId"));
            vo.setName(rs.getString("name"));
            vo.setSpName(rs.getString("spName"));
            vo.setMarketDate(rs.getString("marketDate"));
            vo.setContentId(rs.getString("contentId"));
            vo.setAppId(rs.getString("appId"));
        }

        public Object createObject()
        {

            return new IntervenorGcontentVO();
        }
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
        vo.setStartDate(rs.getTimestamp("startDate").toString());
        vo.setEndDate(rs.getTimestamp("endDate").toString());
        vo.setStartSortId(rs.getInt("startSortId"));
        vo.setEndSortId(rs.getInt("endSortId"));
    }

    /**
     * 为对象属性赋值
     * 
     * @param vo
     * @param rs
     * @throws SQLException
     */
    private void fromIntervenorGcontentVOByRs(IntervenorGcontentVO vo,
                                              ResultSet rs) throws SQLException
    {

        vo.setId(rs.getString("id"));
        vo.setIntervenorId(rs.getString("intervenorId"));
        vo.setSortId(rs.getInt("sortId"));
        vo.setName(rs.getString("name"));
        vo.setSpName(rs.getString("spName"));
        vo.setMarketDate(rs.getString("marketDate"));
        vo.setContentId(rs.getString("contentId"));
    }

    /**
     * 根据容器id得到容器中内容列表
     * 
     * @return
     * @throws DAOException
     */
    public void queryGcontentListByIntervenorId(PageResult page, String id)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("queryRuleList(" + id + ") is starting ...");
        }
        // select
        // g.id,g.name,g.spname,g.marketdate,g.contentid,t.sortid,t.intervenorid
        // from t_intervenor_gcontent_map t ,t_r_gcontent g where
        // t.intervenorid=? and t.gcontentid=g.id
        String sqlCode = "intervenor.IntervenorGcontentDAO.queryGcontentListByIntervenorId().SELECT";
        String sql = null;

        try
        {
            sql = SQLCode.getInstance().getSQLStatement(sqlCode);

            page.excute(sql, new Object[] { id }, new GcontentPageVO());
        }
        catch (DataAccessException e)
        {
            throw new DAOException("从sql.properties中获取sql语句时发生错误，对应sqlCode不存在。",
                                   e);
        }
    }

    /**
     * 根据内容id得到所有包含此内容的容器
     * 
     * @param id 内容id
     * @return
     * @throws DAOException
     */
    public List queryIntervenorListByGcontentId(String contentId)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("queryIntervenorListByGcontentId(" + contentId
                         + ") is starting ...");
        }
        // select * from t_intervenor_gcontent_map t, t_intervenor i where
        // t.intervenorid = i.id and t.gcontentid = ?
        String sqlCode = "intervenor.IntervenorGcontentDAO.queryIntervenorListByGcontentId().SELECT";
        ResultSet rs = null;
        List list = new ArrayList();

        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode,
                                                 new Object[] { contentId });

            while (rs.next())
            {
                IntervenorVO vo = new IntervenorVO();

                fromIntervenorVOByRs(vo, rs);

                list.add(vo);
            }
        }
        catch (SQLException e)
        {
            throw new DAOException("根据内容id得到所有包含此内容的容器信息查询发生异常:", e);
        }
        finally
        {
            DB.close(rs);
        }

        return list;
    }

    /**
     * 根据内容id与容器id得到具体内容信息
     * 
     * @param id 容器id
     * @param contentId 内容id
     * @return
     * @throws DAOException
     */
    public IntervenorGcontentVO queryGcontentVO(String id, String contentId)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("queryGcontentVO(" + contentId + ") is starting ...");
        }
        // select
        // g.id,g.name,g.spname,g.marketdate,g.contentid,t.sortid,t.intervenorid
        // from t_intervenor_gcontent_map t ,t_r_gcontent g where
        // t.intervenorid=? and t.gcontentid=? and t.gcontentid=g.id

        String sqlCode = "intervenor.IntervenorGcontentDAO.queryGcontentVO().SELECT";
        ResultSet rs = null;
        IntervenorGcontentVO vo = new IntervenorGcontentVO();

        try
        {
            rs = DB.getInstance()
                   .queryBySQLCode(sqlCode, new Object[] { id, contentId });

            if (rs.next())
            {
                fromIntervenorGcontentVOByRs(vo, rs);
            }
        }
        catch (SQLException e)
        {
            throw new DAOException("根据内容id与容器id得到具体内容信息查询发生异常:", e);
        }
        finally
        {
            DB.close(rs);
        }

        return vo;
    }

    /**
     * 用于查询榜单对应的容器的内容情况
     * 
     * @param categoryId 榜单id
     * @return
     * @throws DAOException
     */
    public List getIntervenorData(String categoryId) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("getIntervenorDate(" + categoryId
                         + ") is starting ...");
        }

        /*
         * select i.id, i.name, i.startsortid, g.gcontentid, g.sortid, c.sortid
         * as categorySort from t_intervenor i, t_intervenor_gcontent_map g,
         * t_intervenor_category_map c, t_r_gcontent r where c.categoryid =
         * '1006' and i.startdate <= sysdate and i.enddate >= sysdate and i.id =
         * c.intervenorid and g.intervenorid = i.id and r.id = g.gcontentid
         * order by c.sortid asc, g.sortid desc;
         */
        String sqlCode = "intervenor.IntervenorGcontentDAO.getIntervenorDate().SELECT";

        ResultSet rs = null;
        List retList = new ArrayList();

        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode,
                                                 new Object[] { categoryId });

            while (rs.next())
            {
                IntervenorGcontentVO vo = new IntervenorGcontentVO();
                vo.setIntervenorId(rs.getString("id"));
                vo.setName(rs.getString("name"));
                vo.setStartSortid(rs.getInt("startSortid"));
                vo.setContentId(rs.getString("gcontentid"));
                vo.setSortId(rs.getInt("sortId"));
                vo.setCategorySort(rs.getInt("categorySort"));
                retList.add(vo);
            }
        }
        catch (Exception e)
        {
            throw new DAOException("根据容器id得到干预容器信息查询发生异常:", e);
        }
        finally
        {
            DB.close(rs);
        }

        return retList;
    }

    /**
     * 向指定容器添加内容
     * 
     * @param id 容器id
     * @param contentId 内容id
     * @return
     * @throws DAOException
     */
    public void addGcontentToIntervenorId(String id, String[] contentId)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("addGcontentToIntervenorId(" + id + ", " + contentId
                         + ") is starting ...");
        }

        // insert into t_intervenor_gcontent_map (intervenorid, gcontentid,
        // sortid) values select ?,?, decode(max(sortid), null, 1, max(sortid)+
        // 1) from t_intervenor_gcontent_map where intervenorid=?
        String sqlCode = "intervenor.IntervenorGcontentDAO.addGcontentToIntervenorId().INSERT";

        String[] mutiSQL = new String[contentId.length];

        Object paras[][] = new String[contentId.length][3];

        for (int i = 0; i < contentId.length; i++)
        {
            mutiSQL[i] = sqlCode;
            paras[i][0] = id;
            paras[i][1] = contentId[i];
            paras[i][2] = id;
        }

        try
        {
            DB.getInstance().executeMutiBySQLCode(mutiSQL, paras);
        }
        catch (DAOException e)
        {
            throw new DAOException("向指定容器添加内容时发生异常:", e);
        }
    }

    /**
     * 在指定容器中删除指定内容
     * 
     * @param id 容器id
     * @param contentId 内容id
     * @return
     */
    public int deleteGcontentById(String id, String contentId)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("deleteGcontentById(" + id + ", " + contentId
                         + ") is starting ...");
        }

        // delete from t_intervenor_gcontent_map t where t.intervenorid=? and
        // t.gcontentid=?
        String sqlCode = "intervenor.IntervenorDAO.deleteGcontentById().DELETE";

        int num = DB.getInstance()
                    .executeBySQLCode(sqlCode, new Object[] { id, contentId });

        return num;
    }

    /**
     * 用于设置容器内内容的排序
     * 
     * @param id 容器id
     * @param sortId 内容id
     * @param sortNum 排序值
     */
    public void editContentSort(String id, String[] sortValue)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("editContentSort(" + id + ") is starting ...");
        }
        
        String[] mutiSQL = new String[sortValue.length];
        
        Object paras[][] = new Object[sortValue.length][3];

        for (int i = 0; i < sortValue.length; i++)
        {
            String[] value = sortValue[i].split("_");
            
            // update t_intervenor_gcontent_map set sortid=? where intervenorid=? and gcontentid=?
            mutiSQL[i] = "intervenor.IntervenorGcontentDAO.editContentSort().UPDATE";
            
            paras[i][0] = value[1];
            paras[i][1] = id;
            paras[i][2] = value[0];
        }
        
        try
        {
            DB.getInstance().executeMutiBySQLCode(mutiSQL, paras);
        }
        catch (DAOException e)
        {
            throw new DAOException("设置容器内内容的排序发生异常:", e);
        }
    }

    /**
     * 删除失效内容
     * 
     */
    public void delInvalidationContent() throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("delInvalidationContent( ) is starting ...");
        }

        // delete from t_intervenor_gcontent_map t where not exists ( select 1
        // from t_r_gcontent g where t.gcontentid = g.id)
        String sqlCode = "intervenor.IntervenorGcontentDAO.delInvalidationContent().DELETE";

        DB.getInstance().executeBySQLCode(sqlCode, null);
    }
    
    /**
     * 删除指定容器下全部内容
     * @param id 容器id
     * @throws DAOException
     */
    public void delAllContentById(String id) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("delAllContentById( ) is starting ...");
        }

        // delete from t_intervenor_gcontent_map t where t.intervenorid = ?
        String sqlCode = "intervenor.IntervenorGcontentDAO.delAllContentById().DELETE";

        DB.getInstance().executeBySQLCode(sqlCode, new Object[] { id});
    }
    
    /**
     * 检证文件导入内容是否正确。返回相应的id
     * @param contentId
     * @return
     * @throws DAOException
     */
    public String queryIdByContentId(String contentId) throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("queryIdByContentId( ) is starting ...");
        }

        // select t.id from t_r_gcontent t where t.contentid =? and t.subtype != '6'
        String sqlCode = "intervenor.IntervenorGcontentDAO.queryIdByContentId().SELECT";
        ResultSet rs = null;
        String temp = "";
        
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode,
                                                 new Object[] { contentId });

            if (rs.next())
            {
                temp = rs.getString("id");
            }
        }
        catch (SQLException e)
        {
            throw new DAOException("用于检证文件导入内容是否正确。返回相应的id的查询时发生异常:", e);
        }
        finally
        {
            DB.close(rs);
        }

        return temp;
    }
    
    /**
     * 根据容器id得到容器中内容列表
     * 
     * @return
     * @throws DAOException
     */
    public List<IntervenorGcontentVO> queryGcontentListByIntervenorIdAndroid(String androidIntervenorId)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("queryGcontentListByIntervenorIdAndroid(" + androidIntervenorId + ") is starting ...");
        }
        
        // select c.contentid id, g.* from t_intervenor_gcontent_map g, t_r_gcontent c where g.intervenorid = ? and g.gcontentid = c.id order by g.sortid 
        String sqlCode = "intervenor.IntervenorGcontentDAO.queryGcontentListByIntervenorIdAndroid().SELECT";
        List<IntervenorGcontentVO> retList = new ArrayList<IntervenorGcontentVO>();
        ResultSet rs = null;
        
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode,
                                                 new Object[] { androidIntervenorId });

            while (rs.next())
            {
                IntervenorGcontentVO vo = new IntervenorGcontentVO();
                vo.setIntervenorId(rs.getString("intervenorid"));
                vo.setContentId(rs.getString("id"));
                vo.setSortId(rs.getInt("sortId"));
                retList.add(vo);
            }
        }
        catch (Exception e)
        {
            throw new DAOException("根据容器id得到干预容器信息查询发生异常:", e);
        }
        finally
        {
            DB.close(rs);
        }
        
        return retList;
    }
}
