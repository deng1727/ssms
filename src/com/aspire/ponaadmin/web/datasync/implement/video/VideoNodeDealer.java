
package com.aspire.ponaadmin.web.datasync.implement.video;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataDealer;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConfig;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;

/**
 * <p>
 * 对解析后的视频数据进行处理的BO类
 * </p>
 * <p>
 * Copyright (c) 2008 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved
 * </p>
 * 
 * @author bihui
 * @version 1.1.1.0
 */
public class VideoNodeDealer implements DataDealer
{

    /**
     * 日志引用
     */
    private static final JLogger logger = LoggerFactory.getLogger(VideoNodeDealer.class);

    public int dealDataRecrod(DataRecord record)
    {
        VideoNodeVO videoNode = new VideoNodeVO();
        videoNode.setNodeId(( String ) record.get(1));
        videoNode.setNodeName(( String ) record.get(2));
        videoNode.setClassLevel(( String ) record.get(3));
        videoNode.setShowPosition(( String ) record.get(4));
        videoNode.setAccessType(( String ) record.get(5));

        // 为了确保系统不会出错，检验同一产品包ID是否存在于系统中
        String insertSqlCode = "com.aspire.ponaadmin.web.datasync.implement.video.VideoNodeDealer.insert";
        String updateSqlCode = "com.aspire.ponaadmin.web.datasync.implement.video.VideoNodeDealer.update";
        
        Object[] paras = { videoNode.getNodeName(), 
                        videoNode.getClassLevel(),
                        videoNode.getShowPosition(), 
                        videoNode.getAccessType(),
                        videoNode.getNodeId() };
        
        int updateresult = 0;
        try
        {
            updateresult = DB.getInstance().executeBySQLCode(updateSqlCode,
                                                             paras);
            if (updateresult == 0)
            {
                // 更新结果集为0,则插入
                DB.getInstance().executeBySQLCode(insertSqlCode, paras);
            }
        }
        catch (DAOException e)
        {
            logger.error("执行视频栏目信息数据插入失败" + e);
            e.printStackTrace();
            return DataSyncConstants.FAILURE_ADD;
        }

        return DataSyncConstants.SUCCESS_ADD;
    }

    public void init(DataSyncConfig config)
    {
    }

    public void clearDirtyData()
    {
    }

    public void prepareData()
    {
    }
}
