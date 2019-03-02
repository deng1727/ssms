/*
 * 文件名：CategoryDeviceBO.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */

package com.aspire.ponaadmin.web.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.gcontent.DeviceVO;
import com.aspire.ponaadmin.common.page.PageResult;

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
public class CategoryDeviceBO
{

    /**
     * 日志引用
     */
    protected static final JLogger logger = LoggerFactory.getLogger(CategoryDeviceBO.class);

    private static CategoryDeviceBO bo = new CategoryDeviceBO();

    private CategoryDeviceBO()
    {
    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static CategoryDeviceBO getInstance()
    {
        return bo;
    }

    /**
     * 根据机型信息。得到机型列表
     * 
     * @param device 机型信息
     * @return
     * @throws BOException
     */
    public void queryDeviceList(PageResult page, DeviceVO device)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("CategoryDeviceBO.queryDeviceList("
                         + device.toString() + ") is start...");
        }

        try
        {
            // 调用CategoryDeviceDAO进行查询
            CategoryDeviceDAO.getInstance().queryDeviceList(page, device);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("根据机型信息。得到机型列表时发生数据库异常！");
        }
    }
    
    /**
     * 用于得到当前货架关联了哪些机型信息
     * @param categoryId
     * @return
     */
    public String queryDeviceByCategoryId(String categoryId) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("CategoryDeviceBO.queryDeviceByCategoryId("
                         + categoryId.toString() + ") is start...");
        }
        StringBuffer sb = new StringBuffer();
        
        try
        {
            // 调用CategoryDeviceDAO进行查询
            List device = CategoryDeviceDAO.getInstance().queryDeviceByCategoryId(categoryId);
            for (Iterator iter = device.iterator(); iter.hasNext();)
            {
                DeviceVO temp = ( DeviceVO ) iter.next();
                sb.append(temp.getDeviceName()).append(".");
            }
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("得到当前货架关联了哪些机型信息时发生数据库异常！");
        }
        
        return sb.toString();
    }
    
    /**
     * 用于得到当前货架关联的机型信息
     * @param categoryId
     * @return
     */
    public List queryDeviceList(String categoryId) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("CategoryDeviceBO.queryDeviceList("
                         + categoryId.toString() + ") is start...");
        }
        List list = new ArrayList();
        
        try
        {
            // 调用CategoryDeviceDAO进行查询
            list = CategoryDeviceDAO.getInstance().queryDeviceByCategoryId(categoryId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("得到当前货架关联了哪些机型信息时发生数据库异常！");
        }
        
        return list;
    }

    /**
     * 用于保存机型信息与货架的关联关系
     * @param categoryId
     * @param devices
     * @throws BOException
     */
    public void saveDeviceToCategory(String categoryId, String[] devices)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("CategoryDeviceBO.saveDeviceToCategory("
                         + categoryId + ") is start...");
        }
        try
        {
            // 调用CategoryDeviceDAO
            CategoryDeviceDAO.getInstance().saveDeviceToCategory(categoryId, devices);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("用于保存机型信息与货架的关联关系时发生数据库异常！",RepositoryBOCode.CATEGORY_DEVICE);
        }
    }
    
    /**
     * 用于删除货架与机型的关联关系数据
     * @param categoryId
     */
    public void delDeviceToCategory(String categoryId) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("CategoryDeviceBO.delDeviceToCategory("
                         + categoryId + ") is start...");
        }
        try
        {
            // 调用CategoryDeviceDAO
            CategoryDeviceDAO.getInstance().delDeviceToCategory(categoryId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("删除货架与机型的关联关系数据时发生数据库异常！",RepositoryBOCode.DEL_CATEGORY_DEVICE);
        }
    }
}
