/*
 * �ļ�����CategoryPlatFormBO.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
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
     * ��־����
     */
    protected static final JLogger logger = LoggerFactory.getLogger(CategoryPlatFormBO.class);

    private static CategoryPlatFormBO bo = new CategoryPlatFormBO();

    private CategoryPlatFormBO()
    {
    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static CategoryPlatFormBO getInstance()
    {
        return bo;
    }

    /**
     * ����ƽ̨��Ϣ���õ�ƽ̨�б�
     * 
     * @param platForm ƽ̨��Ϣ
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
            // ����CategoryDeviceDAO���в�ѯ
            CategoryPlatFormDAO.getInstance().queryPlatFormList(page, platForm);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("���ݻ�����Ϣ���õ������б�ʱ�������ݿ��쳣��");
        }
    }
    
    /**
     * ���ݻ�������ƽ̨��Ϣid��ϡ��õ�����ҳ����ʾ��Ϣ
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
        
        // ���Ϊ0000����Ϊȫƽ̨ͨ��.
        if(platFormId.length == 1 && Constants.ALL_CITY_PLATFORM.equals(platFormId[0]))
        {
            return "ȫƽ̨ͨ��.";
        }
        
        try
        {
            // ����CategoryPlatFormDAO���в�ѯ
            return CategoryPlatFormDAO.getInstance().queryPlatFormListByPlatFormId(platFormId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("���ݻ�������ƽ̨��Ϣid��ϡ��õ�����ҳ����ʾ��Ϣʱ�������ݿ��쳣��");
        }
    }
}
