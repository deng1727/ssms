package com.aspire.ponaadmin.web.datasync.implement.video;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.implement.DataCheckerImp;

public class VideoNodeChecker extends DataCheckerImp
{
	/**
     * 日志引用
     */
    private static final JLogger logger = LoggerFactory.getLogger(VideoNodeChecker.class) ;

	public int checkDateRecord(DataRecord record) throws Exception
	{
		// NodeId
		String tmp=(String)record.get(1);
		String nodeId=tmp;
		if(logger.isDebugEnabled())
		{
			logger.debug("开始验证视频栏目信息字段格式，NodeId="+nodeId);
		}
		if(!this.checkFieldLength(tmp, 10, true))
		{
			logger.error("NodeId="+tmp+",NodeId验证错误，该字段是必填字段，且不超过10个字符");
			return DataSyncConstants.CHECK_FAILED;
		}
		// NodeName
		tmp=(String)record.get(2);
		if(!this.checkFieldLength(tmp, 128, true))
		{
			logger.error("NodeId="+nodeId+",栏目中文名称验证错误，该字段是必填字段，且不超过128个字符!NodeName="+tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
        // ClassLevel
        tmp=(String)record.get(3);
        if(!this.checkIntegerField("ClassLevel",tmp, 2, true))
        {
            logger.error("NodeId="+nodeId+",层级验证错误，该字段是必填字段，且不超过2个字符!ClassLevel="+tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
        if("123".indexOf(tmp) == -1)
        {
            logger.error("NodeId="+nodeId+",层级验证错误，该字段是必填字段，且只能为1、2、3!ClassLevel="+tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
        
        // ShowPosition
        tmp=(String)record.get(4);
        if(!this.checkFieldLength(tmp, 3, false))
        {
            logger.error("NodeId="+nodeId+",MM客户端展示情况验证错误，该字段不是必填字段，且不超过3个字符!ShowPosition="+tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
        if(!"".equals(tmp) && "01".indexOf(tmp) == -1)
        {
            logger.error("NodeId="+nodeId+",层级验证错误，该字段不是必填字段，且只能为0、1!ShowPosition="+tmp);
            return DataSyncConstants.CHECK_FAILED;
        }

        // accessType
        tmp=(String)record.get(5);
        if(!this.checkIntegerField("accessType", tmp, 2, true))
        {
            logger.error("NodeId="+nodeId+",可适用门户验证错误，该字段是必填字段，且不超过2个字符!accessType="+tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
        
        if("01".indexOf(tmp) == -1)
        {
            logger.error("NodeId="+nodeId+",可适用门户验证错误，该字段是必填字段，且只能为0、1!accessType="+tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
        
		return DataSyncConstants.CHECK_SUCCESSFUL;
		
		
	}

}
