/*
 * 
 */

package com.aspire.ponaadmin.web.category.ui.rule;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.db.SQLUtil;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.DataAccessException;
import com.aspire.common.persistence.util.SQLCode;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.page.PageVOInterface;
import com.aspire.ponaadmin.web.category.rule.RuleVO;

/**
 * @author x_wangml
 * 
 */
public class RuleDAO
{

    /**
     * ��¼��־��ʵ������
     */
    protected static JLogger logger = LoggerFactory.getLogger(RuleDAO.class);

    /**
     * singletonģʽ��ʵ��
     */
    private static RuleDAO instance = new RuleDAO();

    /**
     * ���췽������singletonģʽ����
     */
    private RuleDAO()
    {

    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static RuleDAO getInstance()
    {

        return instance;
    }

    /**
     * Ӧ�����ҳ��ȡVO��ʵ����
     */
    private class RulePageVO implements PageVOInterface
    {

        public void CopyValFromResultSet(Object content, ResultSet rs)
                        throws SQLException
        {

            RuleVO vo = ( RuleVO ) content;
            vo.setRuleId(rs.getInt("ruleId"));
            vo.setRuleName(rs.getString("ruleName"));
            vo.setRuleType(rs.getInt("ruleType"));
            vo.setIntervalType(rs.getInt("intervalType"));
            vo.setExcuteInterval(rs.getInt("excuteInterval"));
            vo.setExcuteTime(rs.getInt("excuteTime"));
            vo.setRandomFactor(rs.getInt("randomFactor"));
        }

        public Object createObject()
        {

            return new RuleVO();
        }
    }

    /**
     * ���ڸ���������ѯ�����б�
     * 
     * @param page
     * @param ruleId ����id
     * @param ruleName ��������
     * @param ruleType ��������
     * @param intervalType ִ��ʱ��������
     * @throws BOException
     */
    public void queryRuleList(PageResult page, String ruleId, String ruleName,
                              String ruleType, String intervalType)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("queryRuleList(" + ruleId + "," + ruleName + ","
                         + ruleType + "," + intervalType + ") is starting ...");
        }
        // select * from t_caterule t where 1=1
        String sqlCode = "rule.RuleDAO.queryRuleList().SELECT";
        String sql = null;

        try
        {
            sql = SQLCode.getInstance().getSQLStatement(sqlCode);
            
            StringBuffer sqlBuffer = new StringBuffer(sql) ;
            List paras = new ArrayList();
            //����������sql�Ͳ���

            if (!"".equals(ruleId))
            {
                //sql += " and t.ruleId =" + ruleId;
            	sqlBuffer.append("  and t.ruleId =? ");
            	paras.add(ruleId);
            }
            if (!"".equals(ruleName))
            {
                //sql += " and t.ruleName like ('%" + ruleName + "%')";
            	sqlBuffer.append(" and t.ruleName like ? ");
            	paras.add("%"+SQLUtil.escape(ruleName)+"%");
            }
            if (!"".equals(ruleType))
            {
                //sql += " and t.ruleType =" + ruleType;
            	sqlBuffer.append(" and t.ruleType = ? ");
            	paras.add(ruleType);
            }
            if (!"".equals(intervalType))
            {
                //sql += " and t.intervalType =" + intervalType;
            	sqlBuffer.append(" and t.intervalType = ? ");
            	paras.add(intervalType);
            }

            //sql += " order by t.ruleid desc";
            sqlBuffer.append(" order by t.ruleid desc");
            
            //page.excute(sql, null, new RulePageVO());
            page.excute(sqlBuffer.toString(), paras.toArray(), new RulePageVO());
        }
        catch (DataAccessException e)
        {
            throw new DAOException("��sql.properties�л�ȡsql���ʱ�������󣬶�ӦsqlCode�����ڡ�",
                                   e);
        }
    }

    /**
     * �����������Թ�����Ϣ
     * 
     * @param vo ������Ϣ
     * @return
     */
    public int addRuleVO(RuleVO vo) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("addCateRulesVO(vo=" + vo.toString()
                         + ") is starting ...");
        }

        // insert into t_caterule t
        // (t.ruleid,t.rulename,t.ruletype,t.intervaltype,t.excuteinterval,t.excutetime,t.randomfactor)
        // values (?,?,?,?,?,?,?)
        String sqlCode = "rule.RuleDAO.addRuleVO().INSERT";

        int num = DB.getInstance()
                    .executeBySQLCode(sqlCode,
                                      new Object[] {
                                                      String.valueOf(vo.getRuleId()),
                                                      vo.getRuleName(),
                                                      String.valueOf(vo.getRuleType()),
                                                      String.valueOf(vo.getIntervalType()),
                                                      String.valueOf(vo.getExcuteInterval()),
                                                      String.valueOf(vo.getExcuteTime()),
                                                      String.valueOf(vo.getRandomFactor()) });

        return num;

    }

    /**
     * �����޸Ĳ��Թ�����Ϣ
     * 
     * @param vo ������Ϣ
     * @return
     */
    public int editCateRulesVOByID(RuleVO vo) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("editCateRulesVOByID(vo=" + vo.toString()
                         + ") is starting ...");
        }

        // update t_caterule t set
        // rulename=?,ruleType=?,intervalType=?,excuteInterval=?,excuteTime=?,randomFactor=?
        // where ruleId = ?
        String sqlCode = "rule.RuleDAO.editCateRulesVOByID().UPDATE";
        
        int num = DB.getInstance()
                    .executeBySQLCode(sqlCode,
                                      new Object[] {
                                                      vo.getRuleName(),
                                                      String.valueOf(vo.getRuleType()),
                                                      String.valueOf(vo.getIntervalType()),
                                                      String.valueOf(vo.getExcuteInterval()),
                                                      String.valueOf(vo.getExcuteTime()),
                                                      String.valueOf(vo.getRandomFactor()),
                                                      String.valueOf(vo.getMaxGoodsNum()),
                                                      String.valueOf(vo.getRuleId())
                    });

        return num;

    }

    /**
     * ����ͨ������idɾ����Ӧ�Ĺ���
     * 
     * @param ruleId ����id
     */
    public void dellRuleVOByID(String ruleId) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("dellRuleVOByID(" + ruleId + ") is starting ...");
        }

        // delete from t_caterule t where t.ruleId = ?
        // delete from  t_caterule_cond t where t.ruleid = ?

        String[] mutiSQL = {
                        "rule.RuleDAO.dellRuleVOByID().DELETE",
                        "rule.RuleDAO.dellRuleVOByID().COND.DELETE" };
        
        Object paras[][] = { { ruleId }, { ruleId }};
        
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
     * ���ݹ������鿴�˹����Ƿ�󶨻���
     * 
     * @param ruleId �������
     * @return
     * @throws DAOException
     */
    public boolean isRuleBind(String ruleId) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("isRuleBind(" + ruleId + ") is starting ...");
        }

        // select * from t_category_rule t where t.ruleid=?
        String sqlCode = "rule.RuleDAO.isRuleBind().SELECT";

        ResultSet rs = null;

        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode,
                                                 new Object[] { ruleId });
            // ����
            if (rs.next())
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (SQLException e)
        {
            throw new DAOException("���ݹ������鿴�˹����Ƿ�󶨻��ܷ����쳣:", e);
        }
        finally
        {
            DB.close(rs);
        }
    }
    
    /**
     * �õ�SEQ���Ե�����id
     * 
     * @return
     * @throws DAOException
     */
    public int getRuleId() throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug(" getRuleId()");
        }
        int result = -1;

        try
        {
            result = DB.getSeqValue("SEQ_caterule_ID");
        }
        catch (Exception e)
        {
            logger.error("RuleDAO.getRuleId():", e);
            throw new DAOException(e);
        }
        return result;
    }

    /**
     * ���ڲ鿴�Ƿ��Ѵ���Ҫ�޸ĵ�����
     * 
     * @param ruleName ��������
     * @return
     */
    public boolean hasRuleName(String ruleName) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("hasRuleName(ruleName=" + ruleName
                         + ") is starting ...");
        }

        // select * from t_caterule t where t.rulename=?
        String sqlCode = "rule.RuleDAO.hasRuleName().SELECT";

        ResultSet rs = null;

        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode,
                                                 new Object[] { ruleName });
            // ����
            if (rs.next())
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (SQLException e)
        {
            throw new DAOException("���ݻ�������õ����ܲ��Թ������Ϣ��ѯ�����쳣:", e);
        }
        finally
        {
            DB.close(rs);
        }
    }

}
