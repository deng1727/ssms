/*
 * 
 */

package com.aspire.ponaadmin.web.category.intervenor.category;

import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.category.intervenor.IntervenorVO;
import com.aspire.ponaadmin.web.category.intervenor.gcontent.IntervenorGcontentDAO;
import com.aspire.ponaadmin.web.category.intervenor.gcontent.IntervenorGcontentVO;
import com.aspire.ponaadmin.web.repository.Category;

/**
 * @author x_wangml
 * 
 */
public class IntervenorCategoryBO
{

    /**
     * 日志引用
     */
    protected static final JLogger logger = LoggerFactory.getLogger(IntervenorCategoryBO.class);

    private static IntervenorCategoryBO instance = new IntervenorCategoryBO();

    private IntervenorCategoryBO()
    {

    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static IntervenorCategoryBO getInstance()
    {

        return instance;
    }

    /**
     * 查询榜单列表信息
     * 
     * @param page
     * @param id 榜单编号
     * @param name 榜单名称
     * @throws BOException
     */
    public void queryCategoryVOList(PageResult page, String id, String name)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("IntervenorCategoryBO.queryCategoryVOList(" + id
                         + name + ") is start...");
        }

        try
        {
            // 调用IntervenorCategoryDAO进行查询
            IntervenorCategoryDAO.getInstance().queryCategoryVOList(page,
                                                                    id,
                                                                    name);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("根据容器id得到容器中内容列表时发生数据库异常！");
        }
    }
    /**
     * 查询榜单列表信息
     * 
     * @param list 装载榜单vo对象
     */
    public void queryCategoryVOList(List<Category> list)
    		throws Exception
    		{
    	
    	try
    	{
    		// 调用IntervenorCategoryDAO进行查询
    		IntervenorCategoryDAO.getInstance().queryCategoryVOList(list);
    	}
    	catch (DAOException e)
    	{
    		logger.error(e);
    		throw new BOException("根据容器id得到容器中内容列表时发生数据库异常！");
    	}
    		}
    
    /**
     * 查询榜单列表信息
     * 
     * @param page
     * @param id 榜单编号
     * @param categoryid categoryid
     * @throws BOException
     */
    public void queryCategoryList(PageResult page, String id, String categoryid)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("IntervenorCategoryBO.queryCategoryList(" + id
                         + categoryid + ") is start...");
        }

        try
        {
            // 调用IntervenorCategoryDAO进行查询
            IntervenorCategoryDAO.getInstance().queryCategoryList(page,
                                                                    id,
                                                                    categoryid);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("根据容器id得到容器中内容列表时发生数据库异常！");
        }
    }

    /**
     * 查询榜单对应的干预容器
     * 
     * @param page
     * @param id 榜单id
     * @throws BOException
     */
    public void queryIntervenorVOByCategory(PageResult page, String id)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("IntervenorCategoryBO.queryIntervenorVOByCategory("
                         + id + ") is start...");
        }

        try
        {
            // 调用IntervenorCategoryDAO进行查询
            IntervenorCategoryDAO.getInstance()
                                 .queryIntervenorVOByCategory(page, id);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("查询榜单对应的干预容器时发生数据库异常！");
        }
    }

    /**
     * 用于添加容器时显示的容器列表
     * 
     * @param page
     * @param id 查询编码
     * @param name 查询名称
     * @throws BOException
     */
    public void queryIntervenorVOList(PageResult page, String id, String name)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("IntervenorCategoryBO.queryIntervenorVOList(id = "
                         + id + ", name = " + name + ") is start...");
        }

        try
        {
            // 调用IntervenorCategoryDAO进行查询
            IntervenorCategoryDAO.getInstance().queryIntervenorVOList(page,
                                                                      id,
                                                                      name);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("查询用于添加容器显示的容器列表时发生数据库异常！");
        }
    }

    /**
     * 设置榜单内容器的排序信息
     * 
     * @param categoryId 榜单编码
     * @param intervenorId 容器编码
     * @param sortId 设置排序
     * @throws BOException
     */
    public void setSortId(String categoryId, String intervenorId, String sortId)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("IntervenorCategoryBO.setSortId(categoryId："
                         + categoryId + ", intervenorId=" + intervenorId
                         + ", sortId=" + sortId + ") is start...");
        }

        try
        {
            // 调用IntervenorCategoryDAO进行设置排序
            IntervenorCategoryDAO.getInstance().setSortId(categoryId,
                                                          intervenorId,
                                                          sortId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("设置榜单内容器的排序信息时发生数据库异常！");
        }
    }

    /**
     * 用于删除榜单中的容器信息
     * 
     * @param categoryId 榜单编码
     * @param intervenorId 容器编码
     * @throws BOException
     */
    public void delIntervenorIdByCategoryId(String categoryId,
                                            String intervenorId)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("IntervenorCategoryBO.setSortId(categoryId："
                         + categoryId + ", intervenorId=" + intervenorId
                         + ") is start...");
        }

        try
        {
            // 调用IntervenorCategoryDAO进行删除榜单中的容器信息
            IntervenorCategoryDAO.getInstance()
                                 .delIntervenorIdByCategoryId(categoryId,
                                                              intervenorId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("用于删除榜单中的容器信息时发生数据库异常！");
        }
    }

    /**
     * 查看榜单中是否存在指定人工干预容器
     * 
     * @param categoryId 榜单id
     * @param intervenorId 容器id
     * @return
     * @throws BOException
     */
    public boolean hasInternor(String categoryId, String intervenorId)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("IntervenorCategoryBO.hasInternor(categoryId："
                         + categoryId + ", intervenorId=" + intervenorId
                         + ") is start...");
        }

        try
        {
            // 调用IntervenorCategoryDAO查看榜单中是否存在指定人工干预容器
            return IntervenorCategoryDAO.getInstance()
                                        .hasInternor(categoryId, intervenorId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("用于删除榜单中的容器信息时发生数据库异常！");
        }
    }

    /**
     * 用于关联容器至榜单
     * 
     * @param categoryId 榜单id
     * @param intervenorId 容器id
     * @return
     * @throws BOException
     */
    public int addCategoryVOByIntervenor(String categoryId, String intervenorId, String type)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("IntervenorCategoryBO.addCategoryVOByIntervenor(categoryId："
                         + categoryId
                         + ", intervenorId="
                         + intervenorId
                         + ") is start...");
        }

        try
        {
            // 调用IntervenorCategoryDAO关联容器至榜单
            return IntervenorCategoryDAO.getInstance()
                                        .addCategoryVOByIntervenor(categoryId,
                                                                   intervenorId,type);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("关联容器至榜单时发生数据库异常！");
        }
    }

    /**
     * 得到所有被人工干预的榜单
     * 
     * @return
     * @throws DAOException
     */
    public List getIntervenorCategory() throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("IntervenorCategoryBO.getIntervenorCategory() is start...");
        }

        try
        {
            return IntervenorCategoryDAO.getInstance().getIntervenorCategory();
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("得到所有被人工干预的榜单时发生数据库异常！");
        }
    }
    
    /**
     * 得到商品库货架对应的人工干预列表
     * @param androidCategoryId
     * @return
     * @throws BOException 
     */
    public List<IntervenorVO> getIntervenorCategoryVO(String androidCategoryId) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("IntervenorCategoryBO.getIntervenorCategoryVO() is start...");
        }

        try
        {
            return IntervenorCategoryDAO.getInstance().getIntervenorCategoryVO(androidCategoryId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("得到商品库货架对应的人工干预列表时发生数据库异常！");
        }
    }
    
    /**
     * 得到商品库货架对应的人工干预列表
     * @param androidCategoryId
     * @return
     * @throws BOException 
     */
    public List<IntervenorGcontentVO> queryGcontentListByIntervenorIdAndroid(String androidCategoryId) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("IntervenorCategoryBO.getIntervenorCategoryVO() is start...");
        }

        try
        {
            return IntervenorGcontentDAO.getInstance().queryGcontentListByIntervenorIdAndroid(androidCategoryId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("得到商品库货架对应的人工干预列表时发生数据库异常！");
        }
    }
    /**
     * 
     * @param id
     * @throws BOException 
     */
    public String  queryCategoryListById(String id) throws BOException {
    	if (logger.isDebugEnabled())
        {
            logger.debug("IntervenorCategoryBO.queryCategoryListById(" + id + ") is start...");
        }

        try
        {
            return IntervenorCategoryDAO.getInstance().queryCategoryListById(id);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("通过id查询Category表异常！");
        }
    }
    /**
     * 往Messages表中添加一条信息
     * @param categoryid
     * @return
     * @throws BOException 
     */
    public int insertMessagesList(String categoryid) throws BOException {
    	if (logger.isDebugEnabled())
        {
            logger.debug("IntervenorCategoryBO.insertMessagesList(" + categoryid + ") is start...");
        }

        try
        {
            return IntervenorCategoryDAO.getInstance().insertMessagesList(categoryid);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("往Messages表中添加一条信息出现异常"+ e);
        }
	}
}
