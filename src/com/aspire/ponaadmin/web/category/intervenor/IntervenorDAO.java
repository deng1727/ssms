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
 * �˹���Ԥ�������ݲ�����
 * 
 * @author x_wangml
 * 
 */
public class IntervenorDAO
{

    /**
     * ��¼��־��ʵ������
     */
    protected static JLogger logger = LoggerFactory.getLogger(IntervenorDAO.class);

    /**
     * singletonģʽ��ʵ��
     */
    private static IntervenorDAO instance = new IntervenorDAO();

    /**
     * ���췽������singletonģʽ����
     */
    private IntervenorDAO()
    {

    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static IntervenorDAO getInstance()
    {

        return instance;
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
        vo.setStartDate(rs.getDate("startDate").toString());
        vo.setEndDate(rs.getDate("endDate").toString());
        vo.setStartSortId(rs.getInt("startSortId"));
        vo.setEndSortId(rs.getInt("endSortId"));
    }

    /**
     * �����������Ʒ��������б�
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
            //����������sql�Ͳ���
            
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
            throw new DAOException("��sql.properties�л�ȡsql���ʱ�������󣬶�ӦsqlCode�����ڡ�",
                                   e);
        }
        catch (DAOException e)
        {
            throw new DAOException("���ݻ�������õ����ܲ��Թ������Ϣ��ѯ�����쳣:", e);
        }
        catch (SQLException e)
        {
            throw new DAOException("���ݻ�������õ����ܲ��Թ������Ϣ��ѯ�����쳣:", e);
        }
        finally
        {
            DB.close(rs);
        }

        return list;
    }

    /**
     * ��������id�õ���Ԥ����
     * 
     * @param id ����id
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
            throw new DAOException("��������id�õ���Ԥ������Ϣ��ѯ�����쳣:", e);
        }
        catch (SQLException e)
        {
            throw new DAOException("��������id�õ���Ԥ������Ϣ��ѯ�����쳣:", e);
        }
        finally
        {
            DB.close(rs);
        }
        
        return vo;
    }

    /**
     * �ͷŹ�������id�б�
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
            throw new DAOException("�ͷŹ�������id�б����쳣:", e);
        }
    }
    
    /**
     * �޸�������Ϣ
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
     * ��������idɾ������
     * 
     * @param id ����id
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
            throw new DAOException("��������id�õ���Ԥ������Ϣ��ѯ�����쳣:", e);
        }
    }

    /**
     * ����������Ϣ
     * 
     * @param vo ������Ϣ
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
     * �õ�SEQ���Ե�����id
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
     * �����������Ʋ鿴�Ƿ���ڴ�������
     * 
     * @param name ��������
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
            throw new DAOException("�����������Ʋ鿴�Ƿ���ڴ�������ʱ�����쳣:", e);
        }
        catch (SQLException e)
        {
            throw new DAOException("�����������Ʋ鿴�Ƿ���ڴ�������ʱ�����쳣:", e);
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
