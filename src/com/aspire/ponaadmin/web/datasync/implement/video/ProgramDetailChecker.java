
package com.aspire.ponaadmin.web.datasync.implement.video;

import java.util.HashMap;
import java.util.Map;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.implement.DataCheckerImp;

public class ProgramDetailChecker extends DataCheckerImp
{
    /**
     * 日志引用
     */
    private static final JLogger logger = LoggerFactory.getLogger(ProgramDetailChecker.class);
    
    /**
     * 节目信息ID列表
     */
    private static Map programIdMap = new HashMap();
    
    /**
     * 栏目信息表ID列表
     */
    private static Map nodeIdMap = new HashMap();
    

    public int checkDateRecord(DataRecord record) throws Exception
    {
        // programID
        String tmp = ( String ) record.get(1);
        String programID = tmp;
        if (logger.isDebugEnabled())
        {
            logger.debug("开始验证节目详情字段格式，节目ID=" + programID);
        }
        if (!this.checkFieldLength(tmp, 10, true))
        {
            logger.error("节目ID=" + tmp + ",programID验证错误，该字段是必填字段，且不超过10个字符");
            return DataSyncConstants.CHECK_FAILED;
        }
        
        // 查询节目信息表是否存在此节目信息
        //if(!VideoDAO.getInstance().hasProgramId(tmp))
        if(!programIdMap.containsKey(tmp))
        {
            logger.error("节目ID=" + programID
                         + ",programID验证错误，节目信息表中不存在这个ProgramId! ProgramId=" + tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
        
        // programNAME
        tmp = ( String ) record.get(2);
        if (!this.checkFieldLength(tmp, 128, true))
        {
            logger.error("节目ID=" + programID
                         + ",programNAME验证错误，该字段是必填字段，且不超过128个字符!programNAME=" + tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
        
        // nodeID
        tmp = ( String ) record.get(3);
        if (!this.checkFieldLength(tmp, 21, true))
        {
            logger.error("节目ID=" + programID
                         + ",nodeID验证错误，该字段是必填字段，且不超过21个字符!nodeID=" + tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
        
        // 查询栏目信息表是否存在此栏目信息
        //if(!VideoDAO.getInstance().hasNodeId(tmp))
        if(!nodeIdMap.containsKey(tmp))
        {
            logger.error("节目ID=" + programID
                         + ",nodeID验证错误，栏目信息表中不存在这个nodeID!nodeID=" + tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
        
        // description
        tmp = ( String ) record.get(4);
        if (!this.checkFieldLength(tmp, 4000, true))
        {
            logger.error("节目ID=" + programID
                         + ",description验证错误，该字段是必填字段，且不超过4000个字符!description=" + tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
        
        // size
        tmp = ( String ) record.get(5);
        if (!this.checkIntegerField("size", tmp, 12, false))
        {
            logger.error("节目ID=" + programID
                         + ",size验证错误，该字段是必填字段，且不超过12个字符!size=" + tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
        
        // duration
        tmp = ( String ) record.get(6);
        if (!this.checkFieldLength(tmp, 21, false))
        {
            logger.error("节目ID=" + programID
                         + ",duration验证错误，该字段是必填字段，且不超过21个字符!duration=" + tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
        
        // logoUrl
        tmp = ( String ) record.get(7);
        if (!this.checkFieldLength(tmp, 512, true))
        {
            logger.error("节目ID=" + programID
                         + ",logoUrl验证错误，该字段是必填字段，且不超过512个字符!logoUrl=" + tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
        
        // programUrl
        tmp = ( String ) record.get(8);
        if (!this.checkFieldLength(tmp, 512, true))
        {
            logger.error("节目ID=" + programID
                         + ",programUrl验证错误，该字段是必填字段，且不超过512个字符!programUrl=" + tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
        
        // accessType
        tmp = ( String ) record.get(9);
        if (!this.checkFieldLength(tmp, 2, true))
        {
            logger.error("节目ID=" + programID
                         + ",accessType验证错误，该字段是必填字段，且不超过2个字符!accessType=" + tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
        if("01".indexOf(tmp) == -1)
        {
            logger.error("节目ID="+programID+",accessType验证错误，该字段是必填字段，且只能为0、1!accessType="+tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
        
        return DataSyncConstants.CHECK_SUCCESSFUL;

    }

    public static void setNodeIdMap(Map nodeIdMap)
    {
        ProgramDetailChecker.nodeIdMap = nodeIdMap;
    }

    public static void setProgramIdMap(Map programIdMap)
    {
        ProgramDetailChecker.programIdMap = programIdMap;
    }
    
    public static void clearList()
    {
        nodeIdMap.clear();
        programIdMap.clear();
    }
}
