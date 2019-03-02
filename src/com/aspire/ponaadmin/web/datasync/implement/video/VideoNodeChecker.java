package com.aspire.ponaadmin.web.datasync.implement.video;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.implement.DataCheckerImp;

public class VideoNodeChecker extends DataCheckerImp
{
	/**
     * ��־����
     */
    private static final JLogger logger = LoggerFactory.getLogger(VideoNodeChecker.class) ;

	public int checkDateRecord(DataRecord record) throws Exception
	{
		// NodeId
		String tmp=(String)record.get(1);
		String nodeId=tmp;
		if(logger.isDebugEnabled())
		{
			logger.debug("��ʼ��֤��Ƶ��Ŀ��Ϣ�ֶθ�ʽ��NodeId="+nodeId);
		}
		if(!this.checkFieldLength(tmp, 10, true))
		{
			logger.error("NodeId="+tmp+",NodeId��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����10���ַ�");
			return DataSyncConstants.CHECK_FAILED;
		}
		// NodeName
		tmp=(String)record.get(2);
		if(!this.checkFieldLength(tmp, 128, true))
		{
			logger.error("NodeId="+nodeId+",��Ŀ����������֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����128���ַ�!NodeName="+tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
        // ClassLevel
        tmp=(String)record.get(3);
        if(!this.checkIntegerField("ClassLevel",tmp, 2, true))
        {
            logger.error("NodeId="+nodeId+",�㼶��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����2���ַ�!ClassLevel="+tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
        if("123".indexOf(tmp) == -1)
        {
            logger.error("NodeId="+nodeId+",�㼶��֤���󣬸��ֶ��Ǳ����ֶΣ���ֻ��Ϊ1��2��3!ClassLevel="+tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
        
        // ShowPosition
        tmp=(String)record.get(4);
        if(!this.checkFieldLength(tmp, 3, false))
        {
            logger.error("NodeId="+nodeId+",MM�ͻ���չʾ�����֤���󣬸��ֶβ��Ǳ����ֶΣ��Ҳ�����3���ַ�!ShowPosition="+tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
        if(!"".equals(tmp) && "01".indexOf(tmp) == -1)
        {
            logger.error("NodeId="+nodeId+",�㼶��֤���󣬸��ֶβ��Ǳ����ֶΣ���ֻ��Ϊ0��1!ShowPosition="+tmp);
            return DataSyncConstants.CHECK_FAILED;
        }

        // accessType
        tmp=(String)record.get(5);
        if(!this.checkIntegerField("accessType", tmp, 2, true))
        {
            logger.error("NodeId="+nodeId+",�������Ż���֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����2���ַ�!accessType="+tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
        
        if("01".indexOf(tmp) == -1)
        {
            logger.error("NodeId="+nodeId+",�������Ż���֤���󣬸��ֶ��Ǳ����ֶΣ���ֻ��Ϊ0��1!accessType="+tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
        
		return DataSyncConstants.CHECK_SUCCESSFUL;
		
		
	}

}
