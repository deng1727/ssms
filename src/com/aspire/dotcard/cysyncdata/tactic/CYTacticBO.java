package com.aspire.dotcard.cysyncdata.tactic;

import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * CMS内容同步策略管理
 * @author x_liyouli
 *
 */
public class CYTacticBO
{
    /**
     * 日志引用
     */
    JLogger logger = LoggerFactory.getLogger(CYTacticBO.class);
    
	/**
	 * 添加一个同步策略
	 * @param vo
	 */
	public void addTactic(CYTacticVO vo) throws BOException
	{
		if (logger.isDebugEnabled())
	    {
			logger.debug("CYTacticBO.addTactic(). CYTacticVO=" + vo);
	    }
		try
        {
            new CYTacticDAO().addTactic(vo);
        }
        catch (DAOException e)
        {
            throw new BOException("添加一个同步策略失败！",e);
        }
	}
	
	/**
	 * 查询单个同步策略
	 * @param id
	 * @return
	 */
	public CYTacticVO queryByID(int id) throws BOException
	{
		if (logger.isDebugEnabled())
	    {
			logger.debug("CYTacticBO.queryByID(). ID=" + id);
	    }
		try
        {
            return new CYTacticDAO().queryByID(id);
        }
        catch (DAOException e)
        {
            throw new BOException("查询单个同步策略失败！",e);
        }
	}
	
	/**
	 * 查询所有的同步策略，不需要分页，按最后修改时间降序排列
	 * @return
	 */
	public List queryByCategoryID(String categoryID) throws BOException
	{
		if (logger.isDebugEnabled())
	    {
			logger.debug("CYTacticBO.queryByCategoryID(). categoryID=" + categoryID);
	    }
		try
        {
            return new CYTacticDAO().queryByCategoryID(categoryID);
        }
        catch (DAOException e)
        {
            throw new BOException("查询所有的同步策略失败！",e);
        }
	}
	
	/**
	 * 查询所有的同步策略
	 * @return
	 */
	public List queryAll() throws BOException
	{
		if (logger.isDebugEnabled())
	    {
			logger.debug("CYTacticBO.queryAll()." );
	    }
		try
        {
            return new CYTacticDAO().queryAll();
        }
        catch (DAOException e)
        {
            throw new BOException("查询所有的同步策略失败！",e);
        }
	}
	
	/**
	 * 修改一个同步策略
	 * @param vo
	 */
	public void modifyTactic(CYTacticVO vo) throws BOException
	{
		if (logger.isDebugEnabled())
	    {
			logger.debug("CYTacticBO.modifyTactic(). CYTacticVO=" + vo);
	    }
		try
        {
            new CYTacticDAO().modifyTactic(vo);
        }
        catch (DAOException e)
        {
            throw new BOException("修改一个同步策略失败！",e);
        }
	}
	
	/**
	 * 删除一个同步策略
	 * @param id
	 */
	public void delTactic(int id) throws BOException
	{
		if (logger.isDebugEnabled())
	    {
			logger.debug("CYTacticBO.delTactic(). id=" + id);
	    }
		try
        {
            new CYTacticDAO().delTactic(id);
        }
        catch (DAOException e)
        {
            throw new BOException("删除一个同步策略失败！",e);
        }
	}
}
