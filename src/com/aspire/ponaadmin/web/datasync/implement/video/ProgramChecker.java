package com.aspire.ponaadmin.web.datasync.implement.video;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.implement.DataCheckerImp;

public class ProgramChecker extends DataCheckerImp
{
	/**
     * 日志引用
     */
    private static final JLogger logger = LoggerFactory.getLogger(ProgramChecker.class) ;

	public int checkDateRecord(DataRecord record) throws Exception
	{
		// programId
		String tmp=(String)record.get(1);
		String programId=tmp;
		if(logger.isDebugEnabled())
		{
			logger.debug("开始验证视频节目字段格式，节目ID="+programId);
		}
        
		if(!this.checkFieldLength(tmp, 10, true))
		{
			logger.error("节目ID="+tmp+",programId验证错误，该字段是必填字段，且不超过10个字符");
			return DataSyncConstants.CHECK_FAILED;
		}
        
		// ProgramNAME
		tmp=(String)record.get(2);
		if(!this.checkFieldLength(tmp, 128, true))
		{
			logger.error("节目ID="+programId+",节目中文名称验证错误，该字段是必填字段，且不超过128个字符!ProgramNAME="+tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
        
		// ContentID
        tmp=(String)record.get(3);
        if(!this.checkFieldLength(tmp, 21, true))
        {
            logger.error("节目ID="+programId+",视频ID验证错误，该字段是必填字段，且不超过11个字符!ContentID="+tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
		
		return DataSyncConstants.CHECK_SUCCESSFUL;
		
		
	}

}
