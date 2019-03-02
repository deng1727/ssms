/*
 * 
 */

package com.aspire.ponaadmin.web.category.ui;


import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.category.CategoryRuleVO;
import com.aspire.ponaadmin.web.category.rule.RuleVO;

/**
 * @author x_wangml
 * 
 */
public class CategoryUpdateBO
{

    /**
     * 日志引用
     */
    protected static final JLogger logger = LoggerFactory.getLogger(CategoryUpdateBO.class);

    private static CategoryUpdateBO bo = new CategoryUpdateBO();

    private CategoryUpdateBO()
    {

    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static CategoryUpdateBO getInstance()
    {

        return bo;
    }

    /**
     * 根据货架内码，规则id，生效时间查出对应货架策略规则表信息
     * 
     * @param cid 货架的货架内码
     * @param ruleID 货架对应的规则ID
     * @param cName 货架Name
     * @param ruleName 货架对应的规则Name
     * @return
     * @throws BOException
     */
    public void queryCategoryUpdateList(PageResult page, String cid,
                                        String ruleID, String cName,
                                        String ruleName)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("CategoryUpdateBO.queryCategoryUpdateList() is start...");
        }

        try
        {
            // 调用CategoryUpdateDAO进行查询
            CategoryUpdateDAO.getInstance()
                                    .queryCategoryUpdateList(page,
                                                             cid,
                                                             ruleID,
                                                             cName,
                                                             ruleName);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("根据货架内码，规则id，生效时间查出对应货架策略规则表信息时发生数据库异常！");
        }
    }
    
    /**
     * 用于根据货架内码得到货架策略规则表信息
     * @param cid 货架内码
     * @return
     * @throws BOException 
     */
    public CategoryRuleVO getCategoryRuleVOByID(String cid) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("CategoryUpdateBO.getCategoryRuleVOByID() is start...");
        }

        try
        {
            // 调用CategoryUpdateDAO进行查询
            return CategoryUpdateDAO.getInstance()
                                    .getCategoryRuleVOByID(cid);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("根据货架内码查出对应货架策略规则表信息时发生数据库异常！");
        }
    }
    
    /**
     * 根据规则id得到相应规则
     * @param ruleId 规则id
     * @return
     * @throws BOException 
     */
    public RuleVO getCateRulesVOByID(String ruleId) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("CategoryUpdateBO.getCateRulesVOByID() is start...");
        }

        try
        {
            // 调用CategoryUpdateDAO进行查询
            return CategoryUpdateDAO.getInstance()
                                    .getCateRulesVOByID(ruleId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("根据规则id查出对应规则表信息时发生数据库异常！",e);
        }
    }
    
    /**
     * 用于根据货架内码得到相对应的规则信息
     * @param cid 货架内码
     * @return
     * @throws BOException 
     */
    public RuleVO getAllCateRules(String cid) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("CategoryUpdateBO.getAllCateRules() is start...");
        }

        try
        {
            // 调用CategoryUpdateDAO进行查询
            return CategoryUpdateDAO.getInstance()
                                    .getAllCateRules(cid);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("根据货架内码查出对应策略规则表信息时发生数据库异常！");
        }
    }
    
    
    /**
     * 用于根据货架内码删除相对应的策略规则信息
     * @param cid 货架内码
     * @return
     * @throws BOException 
     */
    public int dellCateRulesVOByID(String cid) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("CategoryUpdateBO.dellCateRulesVOByID() is start...");
        }

        try
        {
            // 调用CategoryUpdateDAO进行删除
            return CategoryUpdateDAO.getInstance()
                                    .dellCateRulesVOByID(cid);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("根据货架内码删除对应策略规则表信息时发生数据库异常！");
        }
    }
    
    
    /**
     * 用于修改策略规则信息
     * @param cid 货架内码
     * @param ruleId 规则id
     * @param effectiveTime 生效时间
     * @return
     * @throws BOException 
     */
    public int editCateRulesVOByID(String cid, String ruleId, String effectiveTime) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("CategoryUpdateBO.dellCateRulesVOByID() is start...");
        }

        try
        {
            // 调用CategoryUpdateDAO进行修改
            return CategoryUpdateDAO.getInstance()
                                    .editCateRulesVOByID(cid,
                                                         ruleId,
                                                         effectiveTime);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("根据货架内码删除对应策略规则表信息时发生数据库异常！");
        }
    }
    
    /**
     * 用于新增策略规则信息
     * @param cid 货架内码
     * @param ruleId 规则id
     * @param effectiveTime 生效时间
     * @return
     * @throws BOException 
     */
    public int addCateRulesVOByID(String cid, String ruleId, String effectiveTime) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("CategoryUpdateBO.addCateRulesVOByID() is start...");
        }

        try
        {
            // 调用CategoryUpdateDAO进行修改
            return CategoryUpdateDAO.getInstance()
                                    .addCateRulesVOByID(cid,
                                                         ruleId,
                                                         effectiveTime);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("新增策略规则表信息时发生数据库异常！");
        }
    }
}
