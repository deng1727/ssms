/*
 * 文件名：CategoryPlatFormBO.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */

package com.aspire.ponaadmin.web.repository;


import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.gcontent.PlatFormVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.constant.Constants;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * 
 * @author wangminlong
 * @version
 */
public class CategoryPlatFormBO
{

    /**
     * 日志引用
     */
    protected static final JLogger logger = LoggerFactory.getLogger(CategoryPlatFormBO.class);

    private static CategoryPlatFormBO bo = new CategoryPlatFormBO();

    private CategoryPlatFormBO()
    {
    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static CategoryPlatFormBO getInstance()
    {
        return bo;
    }

    /**
     * 根据平台信息。得到平台列表
     * 
     * @param platForm 平台信息
     * @return
     * @throws BOException
     */
    public void queryPlatFormList(PageResult page, PlatFormVO platForm)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("CategoryPlatFormBO.queryPlatFormList("
                         + platForm.toString() + ") is start...");
        }

        try
        {
            // 调用CategoryDeviceDAO进行查询
            CategoryPlatFormDAO.getInstance().queryPlatFormList(page, platForm);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("根据机型信息。得到机型列表时发生数据库异常！");
        }
    }
    
    /**
     * 根据货架所存平台信息id组合。得到用于页面显示信息
     * @param PlatFormIds
     * @return
     */
    public String queryPlatFormByPlatFormIds(String platFormIds) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("CategoryPlatFormBO.queryPlatFormByPlatFormIds() is start...");
        }
        
        String[] platFormId = platFormIds.split(",");
        
        for (int i = 0; i < platFormId.length; i++)
        {
            platFormId[i] = platFormId[i].substring(1, platFormId[i].length()-1);
        }
        
        // 如果为0000，则为全平台通用.
        if(platFormId.length == 1 && Constants.ALL_CITY_PLATFORM.equals(platFormId[0]))
        {
            return "全平台通用.";
        }
        
        try
        {
            // 调用CategoryPlatFormDAO进行查询
            return CategoryPlatFormDAO.getInstance().queryPlatFormListByPlatFormId(platFormId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("根据货架所存平台信息id组合。得到用于页面显示信息时发生数据库异常！");
        }
    }
}
