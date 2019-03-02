
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
 * �Խ��������Ƶ���ݽ��д����BO��
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
     * ��־����
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
                // ���½����Ϊ0,�����
                DB.getInstance().executeBySQLCode(insertSqlCode, paras);
            }
        }
        catch (DAOException e)
        {
            logger.error("ִ����Ƶ��Ŀ�������ݲ���ʧ��" + e);
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
            logger.error("��ʼ��ID�б����ڴ���ʧ��" + e);
            e.printStackTrace();
        }
    }
}
