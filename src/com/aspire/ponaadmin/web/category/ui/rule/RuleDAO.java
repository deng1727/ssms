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
     * 记录日志的实例对象
     */
    protected static JLogger logger = LoggerFactory.getLogger(RuleDAO.class);

    /**
     * singleton模式的实例
     */
    private static RuleDAO instance = new RuleDAO();

    /**
     * 构造方法，由singleton模式调用
     */
    private RuleDAO()
    {

    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static RuleDAO getInstance()
    {

        return instance;
    }

    /**
     * 应用类分页读取VO的实现类
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
     * 用于根据条件查询规则列表
     * 
     * @param page
     * @param ruleId 规则id
     * @param ruleName 规则名称
     * @param ruleType 规则类型
     * @param intervalType 执行时间间隔类型
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
            //构造搜索的sql和参数

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
            throw new DAOException("从sql.properties中获取sql语句时发生错误，对应sqlCode不存在。",
                                   e);
        }
    }

    /**
     * 用于新增策略规则信息
     * 
     * @param vo 规则信息
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
     * 用于修改策略规则信息
     * 
     * @param vo 规则信息
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
     * 用于通过规则id删除相应的规则
     * 
     * @param ruleId 规则id
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
            throw new DAOException("根据容器id得到干预容器信息查询发生异常:", e);
        }
    }

    /**
     * 根据规则编码查看此规则是否绑定货架
     * 
     * @param ruleId 规则编码
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
            // 存在
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
            throw new DAOException("根据规则编码查看此规则是否绑定货架发生异常:", e);
        }
        finally
        {
            DB.close(rs);
        }
    }
    
    /**
     * 得到SEQ用以当规则id
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
     * 用于查看是否已存在要修改的名称
     * 
     * @param ruleName 规则名称
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
            // 存在
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
            throw new DAOException("根据货架内码得到货架策略规则表信息查询发生异常:", e);
        }
        finally
        {
            DB.close(rs);
        }
    }

}
