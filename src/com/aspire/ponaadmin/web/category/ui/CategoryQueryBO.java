/*
 * 
 */

package com.aspire.ponaadmin.web.category.ui;



import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;

/**
 * @author x_wangml
 * 
 */
public class CategoryQueryBO
{

    /**
     * 日志引用
     */
    protected static final JLogger logger = LoggerFactory.getLogger(CategoryQueryBO.class);

    private static CategoryQueryBO bo = new CategoryQueryBO();

    private CategoryQueryBO()
    {

    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static CategoryQueryBO getInstance()
    {

        return bo;
    }

    /**
     * 根据货架编码，货架名称查出对应货架表信息
     * 
     * @param categoryId 货架编码
     * @param categoryName 货架名称
     * @return
     * @throws BOException
     */
    public void queryCategoryList(PageResult page, String categoryId,
                                        String categoryName)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("CategoryQueryBO.queryCategoryList("+categoryId+","+categoryName+") is start...");
        }

        try
        {
            // 调用CategoryQueryDAO进行查询
            CategoryQueryDAO.getInstance()
                                    .queryCategoryList(page,
                                                       categoryId,
                                                       categoryName);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("根据货架编码，货架名称查出对应货架表信息时发生数据库异常！");
        }
    }
}
