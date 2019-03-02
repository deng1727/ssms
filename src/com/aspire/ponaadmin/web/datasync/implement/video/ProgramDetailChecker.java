
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
     * ��־����
     */
    private static final JLogger logger = LoggerFactory.getLogger(ProgramDetailChecker.class);
    
    /**
     * ��Ŀ��ϢID�б�
     */
    private static Map programIdMap = new HashMap();
    
    /**
     * ��Ŀ��Ϣ��ID�б�
     */
    private static Map nodeIdMap = new HashMap();
    

    public int checkDateRecord(DataRecord record) throws Exception
    {
        // programID
        String tmp = ( String ) record.get(1);
        String programID = tmp;
        if (logger.isDebugEnabled())
        {
            logger.debug("��ʼ��֤��Ŀ�����ֶθ�ʽ����ĿID=" + programID);
        }
        if (!this.checkFieldLength(tmp, 10, true))
        {
            logger.error("��ĿID=" + tmp + ",programID��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����10���ַ�");
            return DataSyncConstants.CHECK_FAILED;
        }
        
        // ��ѯ��Ŀ��Ϣ���Ƿ���ڴ˽�Ŀ��Ϣ
        //if(!VideoDAO.getInstance().hasProgramId(tmp))
        if(!programIdMap.containsKey(tmp))
        {
            logger.error("��ĿID=" + programID
                         + ",programID��֤���󣬽�Ŀ��Ϣ���в��������ProgramId! ProgramId=" + tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
        
        // programNAME
        tmp = ( String ) record.get(2);
        if (!this.checkFieldLength(tmp, 128, true))
        {
            logger.error("��ĿID=" + programID
                         + ",programNAME��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����128���ַ�!programNAME=" + tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
        
        // nodeID
        tmp = ( String ) record.get(3);
        if (!this.checkFieldLength(tmp, 21, true))
        {
            logger.error("��ĿID=" + programID
                         + ",nodeID��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����21���ַ�!nodeID=" + tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
        
        // ��ѯ��Ŀ��Ϣ���Ƿ���ڴ���Ŀ��Ϣ
        //if(!VideoDAO.getInstance().hasNodeId(tmp))
        if(!nodeIdMap.containsKey(tmp))
        {
            logger.error("��ĿID=" + programID
                         + ",nodeID��֤������Ŀ��Ϣ���в��������nodeID!nodeID=" + tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
        
        // description
        tmp = ( String ) record.get(4);
        if (!this.checkFieldLength(tmp, 4000, true))
        {
            logger.error("��ĿID=" + programID
                         + ",description��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����4000���ַ�!description=" + tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
        
        // size
        tmp = ( String ) record.get(5);
        if (!this.checkIntegerField("size", tmp, 12, false))
        {
            logger.error("��ĿID=" + programID
                         + ",size��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����12���ַ�!size=" + tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
        
        // duration
        tmp = ( String ) record.get(6);
        if (!this.checkFieldLength(tmp, 21, false))
        {
            logger.error("��ĿID=" + programID
                         + ",duration��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����21���ַ�!duration=" + tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
        
        // logoUrl
        tmp = ( String ) record.get(7);
        if (!this.checkFieldLength(tmp, 512, true))
        {
            logger.error("��ĿID=" + programID
                         + ",logoUrl��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����512���ַ�!logoUrl=" + tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
        
        // programUrl
        tmp = ( String ) record.get(8);
        if (!this.checkFieldLength(tmp, 512, true))
        {
            logger.error("��ĿID=" + programID
                         + ",programUrl��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����512���ַ�!programUrl=" + tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
        
        // accessType
        tmp = ( String ) record.get(9);
        if (!this.checkFieldLength(tmp, 2, true))
        {
            logger.error("��ĿID=" + programID
                         + ",accessType��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����2���ַ�!accessType=" + tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
        if("01".indexOf(tmp) == -1)
        {
            logger.error("��ĿID="+programID+",accessType��֤���󣬸��ֶ��Ǳ����ֶΣ���ֻ��Ϊ0��1!accessType="+tmp);
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
