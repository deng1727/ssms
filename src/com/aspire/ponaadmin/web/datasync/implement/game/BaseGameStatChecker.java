/*
 * 
 */

package com.aspire.ponaadmin.web.datasync.implement.game;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataChecker;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConfig;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.implement.DataCheckerImp;

/**
 * @author x_wangml
 * 
 */
public class BaseGameStatChecker extends DataCheckerImp implements DataChecker
{
    /**
     * 日志引用
     */
    private static final JLogger logger = LoggerFactory.getLogger(BaseGameStatChecker.class);

    public int checkDateRecord(DataRecord record)
    {
        // 游戏ID
        String tmp = ( String ) record.get(1);
        String pkgid = tmp;
        if (logger.isDebugEnabled())
        {
            logger.debug("开始验证游戏ID段格式，游戏ID=" + pkgid);
        }
        int size = 12;
        if (record.size() != size)
        {
            logger.error("字段数不等于" + size);
            return DataSyncConstants.CHECK_FAILED;
        }

        // 1        游戏ID
        tmp = ( String ) record.get(1);
        if (!this.checkFieldLength("游戏ID", tmp, 32, true))
        {
            return DataSyncConstants.CHECK_FAILED;
        }
        // 2        测试评分
        tmp = ( String ) record.get(2);
        if (!this.checkIntegerField("测试评分", tmp, 2, true))
        {
            return DataSyncConstants.CHECK_FAILED;
        }
        // 3        用户好评
        tmp = ( String ) record.get(3);
        if (!this.checkIntegerField("用户好评", tmp, 2, true))
        {
            return DataSyncConstants.CHECK_FAILED;
        }
        // 4 日下载量
        tmp = ( String ) record.get(4);
        if (!this.checkIntegerField("日下载量", tmp, 10, true))
        {
            return DataSyncConstants.CHECK_FAILED;
        }
        // 5 周下载量
        tmp = ( String ) record.get(5);
        if (!this.checkIntegerField("周下载量", tmp, 10, true))
        {
            return DataSyncConstants.CHECK_FAILED;
        }
        // 6 月下载量
        tmp = ( String ) record.get(6);
        if (!this.checkIntegerField("月下载量", tmp, 10, true))
        {
            return DataSyncConstants.CHECK_FAILED;
        }
        // 7 前7天下载
        tmp = ( String ) record.get(7);
        if (!this.checkIntegerField("前7天下载", tmp, 10, true))
        {
            return DataSyncConstants.CHECK_FAILED;
        }
        // 8 前30天下载
        tmp = ( String ) record.get(8);
        if (!this.checkIntegerField("前30天下载", tmp, 10, true))
        {
            return DataSyncConstants.CHECK_FAILED;
        }
        // 9 总下载
        tmp = ( String ) record.get(9);
        if (!this.checkIntegerField("总下载", tmp, 10, true))
        {
            return DataSyncConstants.CHECK_FAILED;
        }
        // // 10 下载增长率。
        // tmp = ( String ) record.get(10);
        // if (!this.checkIntegerField("下载增长率", tmp, 10, true))
        // {
        // return DataSyncConstants.CHECK_FAILED;
        // }
        // // 11 日用户活跃率
        // tmp = ( String ) record.get(11);
        // if (!this.checkIntegerField("日用户活跃率", tmp, 1, true))
        //        {
        //            return DataSyncConstants.CHECK_FAILED;
        //        }
        // 12 推荐次数
        tmp = ( String ) record.get(12);
        if (!this.checkIntegerField("推荐次数", tmp, 10, true))
        {
            return DataSyncConstants.CHECK_FAILED;
        }

        return DataSyncConstants.CHECK_SUCCESSFUL;
    }

    public void init(DataSyncConfig config)
    {

    }
}
