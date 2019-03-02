
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
public class ProgramDetailDealer implements DataDealer
{
    /**
     * 日志引用
     */
    private static final JLogger logger = LoggerFactory.getLogger(ProgramDetailDealer.class);

    public int dealDataRecrod(DataRecord record)
    {
        ProgramDetailVO programDetail = new ProgramDetailVO();
        programDetail.setProgramId(( String ) record.get(1));
        programDetail.setProgramName(( String ) record.get(2));
        programDetail.setNodeId(( String ) record.get(3));
        programDetail.setDescription(( String ) record.get(4));
        programDetail.setSize(( String ) record.get(5));
        programDetail.setDuration(( String ) record.get(6));
        programDetail.setLogoUrl(( String ) record.get(7));
        programDetail.setProgramUrl(( String ) record.get(8));
        programDetail.setAccessType(( String ) record.get(9));

        String insertSqlCode = "com.aspire.ponaadmin.web.datasync.implement.video.ProgramDetailDealer.insert";
        String updateSqlCode = "com.aspire.ponaadmin.web.datasync.implement.video.ProgramDetailDealer.update";

        Object[] paras = { programDetail.getProgramName(),
                        programDetail.getDescription(),
                        programDetail.getSize(), 
                        programDetail.getDuration(),
                        programDetail.getLogoUrl(),
                        programDetail.getProgramUrl(),
                        programDetail.getAccessType(),
                        programDetail.getNodeId(),
                        programDetail.getProgramId() };

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
            logger.error("执行视频节目详情数据插入失败" + e);
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
        ProgramDetailChecker.clearList();
    }

    public void prepareData()
    {
        try
        {
            ProgramDetailChecker.setProgramIdMap(VideoDAO.getInstance().getProgramIdList());
            ProgramDetailChecker.setNodeIdMap(VideoDAO.getInstance().getNodeIdList());
        }
        catch (DAOException e)
        {
            logger.error("初始化ID列表至内存中失败" + e);
            e.printStackTrace();
        }
    }
}
