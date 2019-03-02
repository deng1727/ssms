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
 * �����д��ڵ��������ݲ�����
 * 
 * @author x_wangml
 * 
 */
public class IntervenorGcontentDAO
{

    /**
     * ��¼��־��ʵ������
     */
    protected static JLogger logger = LoggerFactory.getLogger(IntervenorGcontentDAO.class);

    /**
     * singletonģʽ��ʵ��
     */
    private static IntervenorGcontentDAO instance = new IntervenorGcontentDAO();

    /**
     * ���췽������singletonģʽ����
     */
    private IntervenorGcontentDAO()
    {

    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static IntervenorGcontentDAO getInstance()
    {

        return instance;
    }

    /**
     * Ӧ�����ҳ��ȡVO��ʵ����
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
     * Ϊ�������Ը�ֵ
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
     * Ϊ�������Ը�ֵ
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
     * ��������id�õ������������б�
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
            throw new DAOException("��sql.properties�л�ȡsql���ʱ�������󣬶�ӦsqlCode�����ڡ�",
                                   e);
        }
    }

    /**
     * ��������id�õ����а��������ݵ�����
     * 
     * @param id ����id
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
            throw new DAOException("��������id�õ����а��������ݵ�������Ϣ��ѯ�����쳣:", e);
        }
        finally
        {
            DB.close(rs);
        }

        return list;
    }

    /**
     * ��������id������id�õ�����������Ϣ
     * 
     * @param id ����id
     * @param contentId ����id
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
            throw new DAOException("��������id������id�õ�����������Ϣ��ѯ�����쳣:", e);
        }
        finally
        {
            DB.close(rs);
        }

        return vo;
    }

    /**
     * ���ڲ�ѯ�񵥶�Ӧ���������������
     * 
     * @param categoryId ��id
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
            throw new DAOException("��������id�õ���Ԥ������Ϣ��ѯ�����쳣:", e);
        }
        finally
        {
            DB.close(rs);
        }

        return retList;
    }

    /**
     * ��ָ�������������
     * 
     * @param id ����id
     * @param contentId ����id
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
            throw new DAOException("��ָ�������������ʱ�����쳣:", e);
        }
    }

    /**
     * ��ָ��������ɾ��ָ������
     * 
     * @param id ����id
     * @param contentId ����id
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
     * �����������������ݵ�����
     * 
     * @param id ����id
     * @param sortId ����id
     * @param sortNum ����ֵ
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
            throw new DAOException("�������������ݵ��������쳣:", e);
        }
    }

    /**
     * ɾ��ʧЧ����
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
     * ɾ��ָ��������ȫ������
     * @param id ����id
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
     * ��֤�ļ����������Ƿ���ȷ��������Ӧ��id
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
            throw new DAOException("���ڼ�֤�ļ����������Ƿ���ȷ��������Ӧ��id�Ĳ�ѯʱ�����쳣:", e);
        }
        finally
        {
            DB.close(rs);
        }

        return temp;
    }
    
    /**
     * ��������id�õ������������б�
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
            throw new DAOException("��������id�õ���Ԥ������Ϣ��ѯ�����쳣:", e);
        }
        finally
        {
            DB.close(rs);
        }
        
        return retList;
    }
}
