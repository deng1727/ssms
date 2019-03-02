/*
 * 
 */

package com.aspire.ponaadmin.web.category.ui.rule;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.category.rule.RuleVO;

/**
 * @author x_wangml
 * 
 */
public class RuleBO
{

    /**
     * 日志引用
     */
    protected static final JLogger logger = LoggerFactory.getLogger(RuleBO.class);

    private static RuleBO bo = new RuleBO();

    private RuleBO()
    {

    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static RuleBO getInstance()
    {

        return bo;
    }

    /**
     * 用于根据条件查询规则列表
     * @param page 
     * @param ruleId 规则id
     * @param ruleName 规则名称
     * @param ruleType 规则类型
     * @param intervalType 执行时间间隔类型
     * @throws BOException
     */
    public void queryRuleList(PageResult page, String ruleId, String ruleName,
                              String ruleType, String intervalType)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("CategoryUpdateBO.queryCategoryUpdateList() is start...");
        }

        try
        {
            // 调用CategoryUpdateDAO进行查询
            RuleDAO.getInstance().queryRuleList(page,
                                                ruleId,
                                                ruleName,
                                                ruleType,
                                                intervalType);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("根据货架内码，规则id，生效时间查出对应货架策略规则表信息时发生数据库异常！");
        }
    }
    
    /**
     * 用于新增策略规则信息
     * 
     * @param vo 规则信息
     * @return
     * @throws BOException
     */
    public int addRuleVO(RuleVO vo) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("RuleBO.addRuleVO() is start...");
        }

        try
        {
            // 调用RuleBO进行新增
            return RuleDAO.getInstance().addRuleVO(vo);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("新增策略规则信息时发生数据库异常！");
        }
    }

    /**
     * 用于修改规则信息
     * 
     * @param vo 页面输入的修改后规则信息县
     * @return
     * @throws BOException
     */
    public int editRuleVO(RuleVO vo) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("RuleBO.editRuleVO() is start...");
        }

        try
        {
            // 调用RuleBO进行修改
            return RuleDAO.getInstance().editCateRulesVOByID(vo);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("根据规则编码修改对应规则信息时发生数据库异常！");
        }
    }
    
    /**
     * 用于通过规则id删除相应的规则
     * @param ruleId 规则id
     * @return
     * @throws BOException 
     */
    public void dellRuleVOByID(String ruleId) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("RuleBO.dellRuleVOByID() is start...");
        }

        try
        {
            // 调用RuleDAO进行删除
            RuleDAO.getInstance()
                                    .dellRuleVOByID(ruleId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("根据货架内码删除对应策略规则表信息时发生数据库异常！");
        }
    }
    
    /**
     * 根据规则编码查看此规则是否绑定货架
     * @param ruleId 规则编码
     * @return
     * @throws BOException
     */
    public boolean isRuleBind(String ruleId) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("RuleBO.isRuleBind() is start...");
        }

        try
        {
            // 调用RuleBO进行查看此规则是否绑定货架
            return RuleDAO.getInstance().isRuleBind(ruleId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("根据规则编码查看此规则是否绑定货架发生异常！");
        }
    }

    /**
     * 用于查看是否已存在要修改的名称
     * 
     * @param ruleName 规则名称
     * @return
     * @throws BOException
     */
    public boolean hasRuleName(String ruleName) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("RuleBO.hasRuleName() is start...");
        }

        try
        {
            // 调用RuleBO进行修改
            return RuleDAO.getInstance().hasRuleName(ruleName);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("根据规则名称得到是否已存在时发生数据库异常！");
        }
    }
    
    /**
     * 返回规则id
     * @return
     * @throws BOException
     */
    public int getRuleId() throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("RuleBO.hasRuleName() is start...");
        }

        try
        {
            // 返回规则id
            return RuleDAO.getInstance().getRuleId();
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("根据规则名称得到是否已存在时发生数据库异常！");
        }
    }

}
