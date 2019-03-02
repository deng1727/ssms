/*
 * 
 */

package com.aspire.ponaadmin.web.category.ui;

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
import com.aspire.ponaadmin.web.category.CategoryRuleVO;
import com.aspire.ponaadmin.web.category.rule.RuleVO;
import com.aspire.ponaadmin.web.repository.CategoryDAO;

/**
 * @author x_wangml
 * 
 */
public class CategoryUpdateDAO
{

    /**
     * 记录日志的实例对象
     */
    protected static JLogger logger = LoggerFactory.getLogger(CategoryUpdateDAO.class);

    /**
     * singleton模式的实例
     */
    private static CategoryUpdateDAO instance = new CategoryUpdateDAO();

    /**
     * 构造方法，由singleton模式调用
     */
    private CategoryUpdateDAO()
    {

    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static CategoryUpdateDAO getInstance()
    {

        return instance;
    }

    /**
     * 应用类分页读取VO的实现类
     */
    private class CategoryRulePageVO implements PageVOInterface
    {

        public void CopyValFromResultSet(Object content, ResultSet rs)
                        throws SQLException
        {

            CategoryRuleVO vo = ( CategoryRuleVO ) content;
            vo.setCid(rs.getString("cid"));
            vo.setRuleId(rs.getInt("ruleId"));
            vo.setRuleName(rs.getString("ruleName"));
            vo.setEffectiveTime(rs.getTimestamp("effectiveTime"));
            vo.setLastExcuteTime(rs.getTimestamp("lastExcuteTime"));
            try
            {
                vo.setCidPath(CategoryDAO.getInstance()
                                         .getCategoryNamePathByID(vo.getCid()));
            }
            catch (DAOException e)
            {
                logger.error("获取货架的path出错。id=" + vo.getCid());
            }
        }

        public Object createObject()
        {

            return new CategoryRuleVO();
        }
    }

    /**
     * 根据货架内码，规则id，生效时间查出对应货架策略规则表信息
     * 
     * @param cid 货架的货架内码
     * @param ruleID 货架对应的规则ID
     * @param cName 货架Name
     * @param ruleName 货架对应的规则Name
     * @return
     * @throws DAOException
     */
    public void queryCategoryUpdateList(PageResult page, String cid,
                                        String ruleID, String cName,
                                        String ruleName) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("queryCategoryUpdateList(" + cid + "," + ruleID + ","
                         + cName + "," + ruleName + ") is starting ...");
        }
        // select t.cid, t.ruleid, t.lastexcutetime, t.effectivetime, c.rulename
        // from t_category_rule t, t_caterule c, t_r_category y where t.ruleid =
        // c.ruleid and t.cid = y.id
        String sqlCode = "autoupdate.CategoryUpdateDAO.queryCategoryUpdateList().SELECT";
        String sql = null;

        try
        {
            sql = SQLCode.getInstance().getSQLStatement(sqlCode);

            
            StringBuffer sqlBuffer = new StringBuffer(sql) ;
            List paras = new ArrayList();
            //构造搜索的sql和参数
            
            if (!"".equals(cid))
            {
                //sql += " and t.cid ='" + cid + "'";
            	sqlBuffer.append(" and t.cid = ? ");
            	paras.add(cid);
            }
            if (!"".equals(ruleID))
            {
                //sql += " and t.ruleId =" + ruleID;
            	sqlBuffer.append(" and t.ruleId =? ");
            	paras.add(ruleID);
            }
            if (!"".equals(cName))
            {
                //sql += " and y.name like ('%" + cName + "%')";
            	sqlBuffer.append( " and y.name like ? ");
            	paras.add("%"+SQLUtil.escape(cName)+"%");
            }
            if (!"".equals(ruleName))
            {
                //sql += " and c.rulename like ('%" + ruleName + "%')";
            	sqlBuffer.append(" and c.rulename like ? ");
            	paras.add("%"+SQLUtil.escape(ruleName)+"%");
            }

            //sql += " order by t.ruleid desc";
            sqlBuffer.append(" order by t.ruleid desc");

            //page.excute(sql, null, new CategoryRulePageVO());
            page.excute(sqlBuffer.toString(), paras.toArray(), new CategoryRulePageVO());
        }
        catch (DataAccessException e)
        {
            throw new DAOException("从sql.properties中获取sql语句时发生错误，对应sqlCode不存在。",
                                   e);
        }
    }

    /**
     * 用于根据货架内码得到货架策略规则表信息
     * 
     * @param cid 货架内码
     * @return
     */
    public CategoryRuleVO getCategoryRuleVOByID(String cid) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("getCategoryRuleVOByID(" + cid + ") is starting ...");
        }

        // select t.cid, t.ruleid, t.lastexcutetime, t.effectivetime, c.rulename
        // from t_category_rule t, t_caterule c where t.ruleid = c.ruleid and
        // t.cid=?
        String sqlCode = "autoupdate.CategoryUpdateDAO.getCategoryRuleVOByID().SELECT";

        ResultSet rs = null;

        CategoryRuleVO vo = new CategoryRuleVO();

        try
        {
            String sql = SQLCode.getInstance().getSQLStatement(sqlCode);

            rs = DB.getInstance().query(sql, new Object[] { cid });

            if (rs.next())
            {
                vo.setCid(rs.getString("cid"));
                vo.setRuleId(rs.getInt("ruleId"));
                vo.setRuleName(rs.getString("ruleName"));
                vo.setEffectiveTime(rs.getTimestamp("effectiveTime"));
                vo.setLastExcuteTime(rs.getTimestamp("lastExcuteTime"));
            }
        }
        catch (SQLException e)
        {
            throw new DAOException("根据货架内码得到货架策略规则表信息查询发生异常:", e);
        }
        catch (DataAccessException e)
        {
            throw new DAOException("没有找到相应的sql语句", e);
        }
        finally
        {
            DB.close(rs);
        }

        return vo;
    }

    /**
     * 用于根据货架内码得到货架策略规则表信息
     * 
     * @param cid 货架内码
     * @return
     */
    public int dellCateRulesVOByID(String cid) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("dellCateRulesVOByID(" + cid + ") is starting ...");
        }

        // delete from t_category_rule t where t.cid = ?
        String sqlCode = "autoupdate.CategoryUpdateDAO.dellCateRulesVOByID().DELETE";

        int num = DB.getInstance().executeBySQLCode(sqlCode,
                                                    new Object[] { cid });

        return num;
    }

    /**
     * 用于修改策略规则信息
     * 
     * @param cid 货架内码
     * @param ruleId 规则id
     * @param effectiveTime 生效时间
     * @return
     */
    public int editCateRulesVOByID(String cid, String ruleId,
                                   String effectiveTime) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("editCateRulesVOByID(cid=" + cid + " ruleId=" + ruleId
                         + " effectiveTime=" + effectiveTime
                         + ") is starting ...");
        }

        // update t_category_rule t set
        // ruleId=?,effectiveTime=to_date(?,'yyyyMMddhh24miss') where t.cid = ?
        String sqlCode = "autoupdate.CategoryUpdateDAO.editCateRulesVOByID().UPDATE";

        int num = DB.getInstance()
                    .executeBySQLCode(sqlCode,
                                      new Object[] { ruleId, effectiveTime, cid });

        return num;
    }

    /**
     * 用于新增策略规则信息
     * 
     * @param cid 货架内码
     * @param ruleId 规则id
     * @param effectiveTime 生效时间
     * @return
     */
    public int addCateRulesVOByID(String cid, String ruleId,
                                  String effectiveTime) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("editCateRulesVOByID(cid=" + cid + " ruleId=" + ruleId
                         + " effectiveTime=" + effectiveTime
                         + ") is starting ...");
        }

        // insert into t_category_rule t (t.cid, t.ruleid, t.effectivetime)
        // values (?,?,to_date(?,'yyyyMMddhh24miss'))
        String sqlCode = "autoupdate.CategoryUpdateDAO.addCateRulesVOByID().INSERT";

        int num = DB.getInstance()
                    .executeBySQLCode(sqlCode,
                                      new Object[] { cid, ruleId, effectiveTime });

        return num;
    }

    /**
     * 根据货架id得到此货架下所有规则
     * 
     * @param cid 货架id
     * @return
     * @throws DAOException
     */
    public RuleVO getAllCateRules(String cid) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("getCategoryRuleVOByID(" + cid + ") is starting ...");
        }

        // select t.ruleid, t.rulename, t.ruletype, t.intervaltype,
        // t.excuteinterval, t.excutetime, t.randomfactor from t_caterule t,
        // t_category_rule r where r.ruleid = t.ruleid and r.cid = ?
        String sqlCode = "autoupdate.CategoryUpdateDAO.getAllCateRules().SELECT";

        ResultSet rs = null;

        RuleVO vo = new RuleVO();

        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, new Object[] { cid });

            if (rs.next())
            {
                fromRuleVOByRs(vo, rs);
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

        return vo;
    }

    /**
     * 根据规则id得到相应规则
     * 
     * @param ruleId 规则id
     * @return
     * @throws DAOException
     */
    public RuleVO getCateRulesVOByID(String ruleId) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("getCateRulesVOByID(" + ruleId + ") is starting ...");
        }
        // select * from t_caterule t where ruleid = ?
        String sqlCode = "autoupdate.CategoryUpdateDAO.getCateRulesVOByID().SELECT";

        ResultSet rs = null;

        RuleVO vo = new RuleVO();

        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode,
                                                 new Object[] { ruleId });

            if (rs.next())
            {
                fromRuleVOByRs(vo, rs);
            }
        }
        catch (SQLException e)
        {
            throw new DAOException("根据规则id得到相应规则信息查询发生异常:", e);
        }
        finally
        {
            DB.close(rs);
        }

        return vo;
    }

    /**
     * 为对象属性赋值
     * 
     * @param vo
     * @param rs
     * @throws SQLException
     */
    private void fromRuleVOByRs(RuleVO vo, ResultSet rs) throws SQLException
    {

        vo.setRuleId(rs.getInt("ruleid"));
        vo.setRuleName(rs.getString("rulename"));
        vo.setRuleType(rs.getInt("ruletype"));
        vo.setIntervalType(rs.getInt("intervaltype"));
        vo.setExcuteInterval(rs.getInt("excuteinterval"));
        vo.setExcuteTime(rs.getInt("excuteTime"));
        vo.setRandomFactor(rs.getInt("randomfactor"));
        if(rs.getString("maxGoodsNum")==null){//add by aiyan 2011-12-21
        	vo.setMaxGoodsNum(-1);
        }else{
        	vo.setMaxGoodsNum(rs.getInt("maxGoodsNum"));
        }
    }
}
