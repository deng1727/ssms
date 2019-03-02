/*
 * 
 */

package com.aspire.ponaadmin.web.category.ui.condition;

import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.category.rule.condition.ConditionVO;

/**
 * @author x_wangml
 * 
 */
public class ConditionUpdateBO
{

    /**
     * 日志引用
     */
    protected static final JLogger logger = LoggerFactory.getLogger(ConditionUpdateBO.class);

    private static ConditionUpdateBO bo = new ConditionUpdateBO();

    private ConditionUpdateBO()
    {

    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static ConditionUpdateBO getInstance()
    {

        return bo;
    }

    /**
     * 用于根据规则编码得到属于此规则的条件
     * 
     * @param page
     * @param ruleId 规则id
     * @throws BOException
     */
    public void queryCondListByID(PageResult page, String ruleId)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("CategoryUpdateBO.queryCategoryUpdateList(" + ruleId
                         + ") is start...");
        }

        try
        {
            // 调用ConditionUpdateDAO进行查询
            ConditionUpdateDAO.getInstance().queryCondListByID(page, ruleId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("根据根据规则编码得到属于此规则的条件信息时发生数据库异常！");
        }
    }
    
    /**
     * 用于新增规则条件信息
     * 
     * @param vo 规则条件信息
     * @return
     * @throws BOException
     */
    public int addConditeionVO(ConditionVO vo) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("ConditionUpdateBO.addConditeionVO() is start...");
        }

        try
        {
            // 调用ConditionUpdateDAO进行新增
            return ConditionUpdateDAO.getInstance().addConditeionVO(vo);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("新增规则条件信息时发生数据库异常！");
        }
    }
    
    /**
     * 用于删除规则条件信息
     * 
     * @param id 规则条件编码
     * @return
     * @throws BOException
     */
    public int deleteConditeionVO(String id) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("ConditionUpdateBO.deleteConditeionVO() is start...");
        }

        try
        {
            // 调用ConditionUpdateDAO进行新增
            return ConditionUpdateDAO.getInstance().deleteConditeionVO(id);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("删除规则条件信息时发生数据库异常！");
        }
    }
    
    /**
     * 用于根据编码查询规则条件信息
     * 
     * @param id 规则条件编码
     * @return
     * @throws BOException
     */
    public ConditionVO getConditionVOById(String id) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("ConditionUpdateBO.getConditionVOById() is start...");
        }

        try
        {
            // 调用ConditionUpdateDAO进行查询
            return ConditionUpdateDAO.getInstance().getConditionVOById(id);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("根据编码查询规则条件信息时发生数据库异常！");
        }
    }
    
    /**
     * 用于修改规则条件信息
     * 
     * @param vo 规则条件信息
     * @return
     * @throws BOException
     */
    public int editConditeionVO(ConditionVO vo) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("ConditionUpdateBO.editConditeionVO() is start...");
        }

        try
        {
            // 调用ConditionUpdateDAO进行新增
            return ConditionUpdateDAO.getInstance().editConditeionVO(vo);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("修改规则条件信息时发生数据库异常！");
        }
    }
    
    /**
     * 得到条件基础语句
     * @return
     * @throws DAOException
     */
    public List queryBaseCondList() throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("ConditionUpdateBO.queryBaseCondList() is start...");
        }

        try
        {
            // 调用ConditionUpdateDAO进行新增
            return ConditionUpdateDAO.getInstance().queryBaseCondList();
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("得到条件基础语句时发生数据库异常！");
        }
    }
    
    /**
     * 得到条件基础语句
     * @return
     * @throws DAOException
     */
    public BaseCondVO queryBaseCondVO(String id) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("ConditionUpdateBO.queryBaseCondVO() is start...");
        }

        try
        {
            // 调用ConditionUpdateDAO进行新增
            return ConditionUpdateDAO.getInstance().queryBaseCondVO(id);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("得到条件基础语句时发生数据库异常！");
        }
    }
}
