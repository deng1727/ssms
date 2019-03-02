
package com.aspire.dotcard.syncData.task;

import java.util.Date;
import java.util.TimerTask;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.syncData.bo.DataSyncBO;
import com.aspire.dotcard.syncData.dao.DataSyncDAO;
import com.aspire.dotcard.syncData.util.SyncDataConstant;
import com.aspire.ponaadmin.web.category.SynCategoryMgr;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;

/**
 * <p>
 * ����ͬ��������
 * </p>
 * <p>
 * Copyright (c) 2003-2007 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights
 * Reserved
 * </p>
 * 
 * @author zhangmin
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class DataSyncTask extends TimerTask
{
    /**
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(DataSyncTask.class);

    /**
     * ִ������ҵ��ͬ��������ͬ����
     */
    public void run()
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("syncService start...");
        }

        // ����DataSyncBO�е�syncService������ҵ��ͬ��,��Ҫ����
        DataSyncBO.getInstance().syncService();
        Date start = new Date();
        if (logger.isDebugEnabled())
        {
            logger.debug("syncService end...");
            logger.debug("syncConAdd start...");
        }

        // ����DataSyncBO�е�����ͬ��
//        DataSyncBO.getInstance()
//                  .syncConAdd(SyncDataConstant.SYN_RESOURCE_TYPE_ALL,
//                              RepositoryConstants.SYN_HIS_NO);

        if (logger.isDebugEnabled())
        {
            logger.debug("syncConAdd end...");
            logger.debug("����Ӧ������ start...");
        }

        // ����Ӧ������
        DataSyncDAO.getInstance().updateRemarks();
       
        
        // ͬ����������Ӧ�ô�����Ϣ
		// DataSyncDAO.getInstance().refreshContetExt();

		// �Զ������
        DataSyncDAO.getInstance().refreshIteMmark ();
         
         
        if (logger.isDebugEnabled())
        {
            logger.debug("����Ӧ������ end...");
            // logger.debug("Zcom������ͬ�� start...");
        }

        // ����Zcom������ͬ��
        // ZcomsDataSyncBO.getInstance().syncZcomConAdd();

        // if (logger.isDebugEnabled())
        // {
        // logger.debug("Zcom������ͬ�� end...");
        // logger.debug("�Զ����� start...");
        // }

        // �����Զ����� add by wml 20101228
        // CategoryMgr.getInstance().start();

        if (logger.isDebugEnabled())
        {
            // logger.debug("�Զ����� end...");
            logger.debug("���ྫƷ����� start...");
        }

        // ���÷��ྫƷ����� add by wml 20101228
        //SynCategoryMgr.getInstance().start();

        if (logger.isDebugEnabled())
        {
            logger.debug("���ྫƷ����� end...");
            // logger.debug("�˹���Ԥ start...");
        }

        if (logger.isDebugEnabled())
        {
            // logger.debug("�Զ����� end...");
            logger.debug("ͬ��������Ӫ���� start...");
        }

        // ����ͬ��������Ӫ���� add by wml 20120823
        //SynOpenOperationData.getInstance().start();

        //if (logger.isDebugEnabled())
        //{
        //    logger.debug("ͬ��������Ӫ���� end...");
        //}
        if (logger.isDebugEnabled())
        {
            // logger.debug("�Զ����� end...");
            logger.debug("������ɺ��ʼ�׼�� start...");
        }
        DataSyncBO.getInstance().toMail(start);
    }
}
