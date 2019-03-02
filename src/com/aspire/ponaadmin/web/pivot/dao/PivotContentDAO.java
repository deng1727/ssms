
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
     * ��¼��־��ʵ������
     */
    protected static JLogger logger = LoggerFactory.getLogger(PivotDeviceDAO.class);

    /**
     * singletonģʽ��ʵ��
     */
    private static PivotContentDAO instance = new PivotContentDAO();

    /**
     * ���췽������singletonģʽ����
     */
    private PivotContentDAO()
    {
    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static PivotContentDAO getInstance()
    {
        return instance;
    }

    /**
     * Ӧ�����ҳ��ȡVO��ʵ����
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
     * �������ڲ�ѯ�ص������б�
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
            //����������sql�Ͳ���
            
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
            throw new DAOException("��sql.properties�л�ȡsql���ʱ�������󣬶�ӦsqlCode�����ڡ�",
                                   e);
        }
    }

    /**
     * �����Ƴ�ָ���ص�����
     * 
     * @param contentId �ص�����id��
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
            throw new DAOException("�Ƴ�ָ���ص�����ʱ�����쳣:", e);
        }
    }

    /**
     * У��ļ��Д����Ƿ������ݱ��д���
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

        // ������ԃ
        for (int i = 0; i < list.size(); i++)
        {
            String temp = ( String ) list.get(i);
            try
            {
                rs = DB.getInstance().queryBySQLCode(sql,
                                                     new Object[] { temp });
                // �����������������
                if (!rs.next())
                {
                    list.remove(i);
                    i--;
                    sb.append(temp).append(". ");
                }
            }
            catch (SQLException e)
            {
                throw new DAOException("У��ļ��Д����Ƿ������ݱ��д���ʱ�����쳣:", e);
            }
            finally
            {
                DB.close(rs);
            }
        }

        return sb.toString();
    }

    /**
     * �����б����Ƿ����ԭ������
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

        // ������ԃ
        for (int i = 0; i < list.size(); i++)
        {
            String temp = ( String ) list.get(i);
            try
            {
                rs = DB.getInstance().queryBySQLCode(sql,
                                                     new Object[] { temp });
                // ���������������
                if (rs.next())
                {
                    list.remove(i);
                    i--;
                }
            }
            catch (SQLException e)
            {
                throw new DAOException("�����б����Ƿ����ԭ������ʱ�����쳣:", e);
            }
            finally
            {
                DB.close(rs);
            }
        }
    }

    /**
     * �������id�б�
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
            throw new DAOException("����ص�����id�б�ʱ�����쳣:", e);
        }
    }

    /**
     * �������ݱ��롣�õ���������
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
            // ���������������
            if (rs.next())
            {
                temp = rs.getString("id");
            }
        }
        catch (SQLException e)
        {
            throw new DAOException("�������ݱ��롣�õ���������ʱ�����쳣:", e);
        }
        finally
        {
            DB.close(rs);
        }

        return temp;
    }

    /**
     * ���ô洢����
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
                logger.error("�ر�CallableStatement=ʧ��");
            }
        }
    }
    
    /**
     * ��ѯ���������ļ�Ӧ�õ�ָ��������
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
            throw new DAOException("��ѯ���������ļ�Ӧ�õ�ָ��������ʱ�����쳣:", e);
        }
        finally
        {
            DB.close(rs);
        }

        return list;
    }
}
