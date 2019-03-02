
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
 * 数据同步任务器
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
     * 日志引用
     */
    protected static JLogger logger = LoggerFactory.getLogger(DataSyncTask.class);

    /**
     * 执行任务（业务同步和内容同步）
     */
    public void run()
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("syncService start...");
        }

        // 调用DataSyncBO中的syncService方法；业务同步,需要保留
        DataSyncBO.getInstance().syncService();
        Date start = new Date();
        if (logger.isDebugEnabled())
        {
            logger.debug("syncService end...");
            logger.debug("syncConAdd start...");
        }

        // 调用DataSyncBO中的增量同步
//        DataSyncBO.getInstance()
//                  .syncConAdd(SyncDataConstant.SYN_RESOURCE_TYPE_ALL,
//                              RepositoryConstants.SYN_HIS_NO);

        if (logger.isDebugEnabled())
        {
            logger.debug("syncConAdd end...");
            logger.debug("更新应用评分 start...");
        }

        // 更新应用评分
        DataSyncDAO.getInstance().updateRemarks();
       
        
        // 同步电子流的应用促销信息
		// DataSyncDAO.getInstance().refreshContetExt();

		// 自动打徽章
        DataSyncDAO.getInstance().refreshIteMmark ();
         
         
        if (logger.isDebugEnabled())
        {
            logger.debug("更新应用评分 end...");
            // logger.debug("Zcom的增量同步 start...");
        }

        // 调用Zcom的增量同步
        // ZcomsDataSyncBO.getInstance().syncZcomConAdd();

        // if (logger.isDebugEnabled())
        // {
        // logger.debug("Zcom的增量同步 end...");
        // logger.debug("自动更新 start...");
        // }

        // 调用自动更新 add by wml 20101228
        // CategoryMgr.getInstance().start();

        if (logger.isDebugEnabled())
        {
            // logger.debug("自动更新 end...");
            logger.debug("分类精品库更新 start...");
        }

        // 调用分类精品库更新 add by wml 20101228
        //SynCategoryMgr.getInstance().start();

        if (logger.isDebugEnabled())
        {
            logger.debug("分类精品库更新 end...");
            // logger.debug("人工干预 start...");
        }

        if (logger.isDebugEnabled())
        {
            // logger.debug("自动更新 end...");
            logger.debug("同步开放运营数据 start...");
        }

        // 用于同步开放运营数据 add by wml 20120823
        //SynOpenOperationData.getInstance().start();

        //if (logger.isDebugEnabled())
        //{
        //    logger.debug("同步开放运营数据 end...");
        //}
        if (logger.isDebugEnabled())
        {
            // logger.debug("自动更新 end...");
            logger.debug("下线完成后发邮件准备 start...");
        }
        DataSyncBO.getInstance().toMail(start);
    }
}
