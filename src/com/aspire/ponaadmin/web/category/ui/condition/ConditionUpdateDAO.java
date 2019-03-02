/*
 * 
 */

package com.aspire.ponaadmin.web.category.ui.condition;

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
import com.aspire.ponaadmin.web.category.rule.condition.ConditionVO;

/**
 * @author x_wangml
 * 
 */
public class ConditionUpdateDAO
{

    /**
     * ��¼��־��ʵ������
     */
    protected static JLogger logger = LoggerFactory.getLogger(ConditionUpdateDAO.class);

    /**
     * singletonģʽ��ʵ��
     */
    private static ConditionUpdateDAO instance = new ConditionUpdateDAO();

    /**
     * ���췽������singletonģʽ����
     */
    private ConditionUpdateDAO()
    {

    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static ConditionUpdateDAO getInstance()
    {

        return instance;
    }

    /**
     * Ӧ�����ҳ��ȡVO��ʵ����
     */
    private class ConditionPageVO implements PageVOInterface
    {

        public void CopyValFromResultSet(Object content, ResultSet rs)
                        throws SQLException
        {

            ConditionVO vo = ( ConditionVO ) content;
            vo.setId(rs.getInt("id"));
            vo.setRuleId(rs.getInt("ruleId"));
            vo.setCid(rs.getString("cid"));
            vo.setCondType(rs.getInt("basecondid"));
            vo.setWSql(rs.getString("wSql"));
            vo.setOSql(rs.getString("oSql"));
            vo.setCount(rs.getInt("count"));
            vo.setSortId(rs.getInt("sortId"));
            vo.setBaseCondName(rs.getString("base_name"));
        }

        public Object createObject()
        {

            return new ConditionVO();
        }
    }

    /**
     * ���ڸ��ݹ������õ����ڴ˹��������
     * 
     * @param page
     * @param ruleId ����id
     * @throws DAOException
     */
    public void queryCondListByID(PageResult page, String ruleId)
                    throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("queryRuleList(" + ruleId + ") is starting ...");
        }
        // select t.*,b.base_name from t_caterule_cond t, t_caterule_cond_base b
        // where t.ruleid=? and t.basecondid=b.base_id
        String sqlCode = "cond.ConditionUpdateDAO.queryCondListByID().SELECT";
        String sql = null;

        try
        {
            sql = SQLCode.getInstance().getSQLStatement(sqlCode);

            page.excute(sql, new Object[] { ruleId }, new ConditionPageVO());
        }
        catch (DataAccessException e)
        {
        	e.printStackTrace();
            throw new DAOException("��sql.properties�л�ȡsql���ʱ�������󣬶�ӦsqlCode�����ڡ�",
                                   e);
        }
    }

    /**
     * ������������������Ϣ
     * 
     * @param vo ����������Ϣ
     * @return
     */
    public int addConditeionVO(ConditionVO vo) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("addConditeionVO(vo=" + vo.toString()
                         + ") is starting ...");
        }

        String sqlCode = "";

        Object[] object;

        if (vo.getCount() == 0)
        {
            // insert into t_caterule_cond t
            // (t.ruleid,t.cid,t.condtype,t.wsql,t.osql,t.sortid) values
            // (?,?,?,?,?,?)
            sqlCode = "cond.ConditionUpdateDAO.addConditeionVONotCount().INSERT";

            object = new Object[] { String.valueOf(vo.getRuleId()),
                            vo.getCid(), String.valueOf(vo.getCondType()),
                            vo.getWSql(), vo.getOSql(),
                            String.valueOf(vo.getSortId()) };
        }
        else
        {
            // insert into t_caterule_cond t
            // (t.ruleid,t.cid,t.condtype,t.wsql,t.osql,t.count,t.sortid) values
            // (?,?,?,?,?,?,?)
            sqlCode = "cond.ConditionUpdateDAO.addConditeionVO().INSERT";

            object = new Object[] { String.valueOf(vo.getRuleId()),
                            vo.getCid(), String.valueOf(vo.getCondType()),
                            vo.getWSql(), vo.getOSql(),
                            String.valueOf(vo.getCount()),
                            String.valueOf(vo.getSortId()) };
        }

        return DB.getInstance().executeBySQLCode(sqlCode, object);
    }
    
    /**
     * ����ɾ������������Ϣ
     * 
     * @param id ������������
     * @return
     */
    public int deleteConditeionVO(String id) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("deleteConditeionVO(id=" + id + ") is starting ...");
        }

        // delete from t_caterule_cond t where t.id = ?
        String sqlCode = "cond.ConditionUpdateDAO.deleteConditeionVO().DELETE";

        return DB.getInstance().executeBySQLCode(sqlCode, new Object[]{id});
    }
    
    /**
     * ���ڸ��ݱ����ѯ����������Ϣ
     * 
     * @param id ������������
     * @throws DAOException
     */
    public ConditionVO getConditionVOById(String id) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("getConditionVOById(" + id + ") is starting ...");
        }
        // select * from t_caterule_cond t where t.id=?
        String sqlCode = "cond.ConditionUpdateDAO.getConditionVOById().SELECT";
        ResultSet rs = null;
        ConditionVO vo = new ConditionVO();

        rs = DB.getInstance().queryBySQLCode(sqlCode,
                                             new Object[] { id });
        // ����
        try
        {
            if (rs.next())
            {
                vo.setId(rs.getInt("id"));
                vo.setRuleId(rs.getInt("ruleId"));
                vo.setCid(rs.getString("cid"));
                vo.setCondType(rs.getInt("baseCondId"));
                vo.setWSql(rs.getString("wSql"));
                vo.setOSql(rs.getString("oSql"));
                vo.setCount(rs.getInt("count"));
                vo.setSortId(rs.getInt("sortId"));
            }
        }
        catch (SQLException e)
        {
        	e.printStackTrace();
            throw new DAOException("���ݱ����ѯ����������Ϣʱ�����쳣:", e);
        }
        finally
        {
            DB.close(rs);
        }
        
        return vo;
    }
    

    /**
     * �����޸Ĺ���������Ϣ
     * 
     * @param vo ����������Ϣ
     * @return
     */
    public int editConditeionVO(ConditionVO vo) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("editConditeionVO(vo=" + vo.toString()
                         + ") is starting ...");
        }

        String sqlCode = "";

        Object[] object;

        if (vo.getCount() == 0)
        {
            // update t_caterule_cond t set
            // t.ruleid=?,t.cid=?,t.basecondid=?,t.wsql=?,t.osql=?,t.sortid=?
            // where t.id = ?
            sqlCode = "cond.ConditionUpdateDAO.editConditeionVONotCount().UPDATE";

            object = new Object[] { String.valueOf(vo.getRuleId()),
                            vo.getCid(), String.valueOf(vo.getCondType()),
                            vo.getWSql(), vo.getOSql(),
                            String.valueOf(vo.getSortId()),
                            String.valueOf(vo.getId()) };
        }
        else
        {
            // update t_caterule_cond t set
            // t.ruleid=?,t.cid=?,t.basecondid=?,t.wsql=?,t.osql=?,t.count=?,t.sortid=?
            // where t.id = ?
            sqlCode = "cond.ConditionUpdateDAO.editConditeionVO().UPDATE";

            object = new Object[] { String.valueOf(vo.getRuleId()),
                            vo.getCid(), String.valueOf(vo.getCondType()),
                            vo.getWSql(), vo.getOSql(),
                            String.valueOf(vo.getCount()),
                            String.valueOf(vo.getSortId()),
                            String.valueOf(vo.getId()) };
        }

        return DB.getInstance().executeBySQLCode(sqlCode, object);
    }
    
    /**
     * �õ������������
     * @return
     * @throws DAOException
     */
    public List queryBaseCondList() throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("queryBaseCondList() is starting ...");
        }
        // select * from t_caterule_cond_base
        String sqlCode = "cond.ConditionUpdateDAO.queryBaseCondList().SELECT";
        ResultSet rs = null;
        List list = new ArrayList();
        
        // ����
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode,null);
            
            while (rs.next())
            {
                BaseCondVO vo = new BaseCondVO();
                
                vo.setBaseId(rs.getString("base_id"));
                vo.setBaseName(rs.getString("base_name"));
                vo.setBaseSQL(rs.getString("base_sql"));
                
                list.add(vo);
            }
        }
        catch (SQLException e)
        {
        	e.printStackTrace();
            throw new DAOException("�õ������������ʱ�����쳣:", e);
        }
        catch (DAOException e)
        {
        	e.printStackTrace();
            throw new DAOException("�õ������������ʱ�����쳣:", e);
        }
        finally
        {
            DB.close(rs);
        }
        
        return list;
    }
    
    /**
     * �õ������������
     * @return
     * @throws DAOException
     */
    public BaseCondVO queryBaseCondVO(String id) throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("queryBaseCondVO(" + id + ") is starting ...");
        }
        // select * from t_caterule_cond_base c where c.id=?
        String sqlCode = "cond.ConditionUpdateDAO.queryBaseCondVO().SELECT";
        ResultSet rs = null;
        BaseCondVO vo = null;
        
        // ����
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode,new Object[] {id});
            
            if (rs.next())
            {
                vo = new BaseCondVO();
                vo.setBaseId(rs.getString("base_id"));
                vo.setBaseName(rs.getString("base_name"));
                vo.setBaseSQL(rs.getString("base_sql"));
            }
        }
        catch (SQLException e)
        {
        	e.printStackTrace();
            throw new DAOException("�õ������������ʱ�����쳣:", e);
        }
        catch (DAOException e)
        {
        	e.printStackTrace();
            throw new DAOException("�õ������������ʱ�����쳣:", e);
        }
        finally
        {
            DB.close(rs);
        }
        
        return vo;
    }
}
