/*
 * �ļ�����RankExportFile.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
 */
package com.aspire.dotcard.baseVideo.exportfile.impl;

import com.aspire.dotcard.baseVideo.conf.BaseVideoConfig;
import com.aspire.dotcard.baseVideo.exportfile.BaseExportFile;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2012</p>
 * @author wangminlong
 * @version 
 */
public class RankExportFile extends BaseExportFile
{

    public RankExportFile()
    {
        this.fileName = "i_v-rank_~DyyyyMMdd~_[0-9]{6}.txt";
        this.verfFileName = "i_v-rank_~DyyyyMMdd~.verf";
        this.mailTitle = "�������а����ݵ�����";
    }
    
    /* (non-Javadoc)
     * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#checkData(java.lang.String[])
     */
    protected String checkData(String[] data)
    {
        String rankID = data[0];
        String tmp = rankID;

        if (logger.isDebugEnabled())
        {
            logger.debug("��ʼ��֤���а������ֶθ�ʽ��rankID=" + rankID);
        }

        if (data.length != 4)
        {
            logger.error("�ֶ���������4");
            return BaseVideoConfig.CHECK_FAILED;
        }

        if (!this.checkFieldLength(tmp, 60, true))
        {
            logger.error("rankID=" + rankID + ",rankID��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����60���ַ�");
            return BaseVideoConfig.CHECK_FAILED;
        }
        // rankName
        tmp = data[1];
        if (!this.checkFieldLength(tmp, 60, true))
        {
            logger.error("rankID=" + rankID
                         + ",rankName��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����60����ֵ���ȣ�rankName="
                         + tmp);
            return BaseVideoConfig.CHECK_FAILED;
        }

        // programID
        tmp = data[2];
        if (!this.checkFieldLength(tmp, 60, true))
        {
            logger.error("rankID=" + rankID
                         + ",programID��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����60���ַ���programID=" + tmp);
            return BaseVideoConfig.CHECK_FAILED;
        }
        // sortID
        tmp = data[3];
        if (!this.checkIntegerField("�����", tmp, 6, false))
        {
            logger.error("rankID=" + rankID
                         + ",sortID��֤�������Ȳ�����6λ��sortID="
                         + tmp);
            return BaseVideoConfig.CHECK_FAILED;
        }

        return BaseVideoConfig.CHECK_DATA_SUCCESS;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#getObject(java.lang.String[])
     */
    protected Object[] getObject(String[] data)
    {
        Object[] object = new Object[4];

        object[0] = data[1];
        object[1] = data[2];
        object[2] = data[3];
        object[3] = data[0];

        return object;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#getInsertSqlCode()
     */
    protected String getInsertSqlCode()
    {
        // insert into t_vo_rank (rankname, programid, sortid, rankid) values (?,?,?,?)
        return "baseVideo.exportfile.RankExportFile.getInsertSqlCode";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#getUpdateSqlCode()
     */
    protected String getUpdateSqlCode()
    {
        // update t_vo_rank r set r.rankname=?, r.programid=?, r.sortid=? where r.rankid=?
        return "baseVideo.exportfile.RankExportFile.getUpdateSqlCode";
    }

    protected String getDelSqlCode()
    {
        // truncate table t_vo_rank
        return "baseVideo.exportfile.RankExportFile.getDelSqlCode";
    }

    protected Object[] getHasObject(String[] data)
    {
        Object[] object = new Object[1];
        object[0] = data[0];
        return object;
    }

    protected String getHasSqlCode()
    {
        // select 1 from t_vo_rank r where r.rankid=?
        return "baseVideo.exportfile.RankExportFile.getHasSqlCode";
    }

    protected String getKey(String[] data)
    {
        return data[0];
    }
}
