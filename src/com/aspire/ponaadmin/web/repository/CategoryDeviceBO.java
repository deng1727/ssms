/*
 * �ļ�����CategoryDeviceBO.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
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
     * ��־����
     */
    protected static final JLogger logger = LoggerFactory.getLogger(CategoryDeviceBO.class);

    private static CategoryDeviceBO bo = new CategoryDeviceBO();

    private CategoryDeviceBO()
    {
    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static CategoryDeviceBO getInstance()
    {
        return bo;
    }

    /**
     * ���ݻ�����Ϣ���õ������б�
     * 
     * @param device ������Ϣ
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
            // ����CategoryDeviceDAO���в�ѯ
            CategoryDeviceDAO.getInstance().queryDeviceList(page, device);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("���ݻ�����Ϣ���õ������б�ʱ�������ݿ��쳣��");
        }
    }
    
    /**
     * ���ڵõ���ǰ���ܹ�������Щ������Ϣ
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
            // ����CategoryDeviceDAO���в�ѯ
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
            throw new BOException("�õ���ǰ���ܹ�������Щ������Ϣʱ�������ݿ��쳣��");
        }
        
        return sb.toString();
    }
    
    /**
     * ���ڵõ���ǰ���ܹ����Ļ�����Ϣ
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
            // ����CategoryDeviceDAO���в�ѯ
            list = CategoryDeviceDAO.getInstance().queryDeviceByCategoryId(categoryId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("�õ���ǰ���ܹ�������Щ������Ϣʱ�������ݿ��쳣��");
        }
        
        return list;
    }

    /**
     * ���ڱ��������Ϣ����ܵĹ�����ϵ
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
            // ����CategoryDeviceDAO
            CategoryDeviceDAO.getInstance().saveDeviceToCategory(categoryId, devices);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("���ڱ��������Ϣ����ܵĹ�����ϵʱ�������ݿ��쳣��",RepositoryBOCode.CATEGORY_DEVICE);
        }
    }
    
    /**
     * ����ɾ����������͵Ĺ�����ϵ����
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
            // ����CategoryDeviceDAO
            CategoryDeviceDAO.getInstance().delDeviceToCategory(categoryId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("ɾ����������͵Ĺ�����ϵ����ʱ�������ݿ��쳣��",RepositoryBOCode.DEL_CATEGORY_DEVICE);
        }
    }
}
