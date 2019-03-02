/*
 * �ļ�����CodeRateExportFile.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
 */

package com.aspire.dotcard.baseVideo.exportfile.impl;

import com.aspire.dotcard.baseVideo.conf.BaseVideoConfig;
import com.aspire.dotcard.baseVideo.exportfile.BaseExportFile;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2012
 * </p>
 * 
 * @author wangminlong
 * @version
 */
public class CodeRateExportFile extends BaseExportFile
{
    public CodeRateExportFile()
    {
        this.fileName = "i_v-codeRate_~DyyyyMMdd~_[0-9]{6}.txt";
        this.hasVerf = false;
        this.mailTitle = "���������ļ����ݵ�����";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#checkData(java.lang.String[])
     */
    protected String checkData(String[] data)
    {
        String codeRateId = data[0];
        String tmp = codeRateId;

        if (logger.isDebugEnabled())
        {
            logger.debug("��ʼ��֤�����ļ��ֶθ�ʽ��codeRateId=" + codeRateId);
        }

        if (data.length != 13)
        {
            logger.error("�ֶ���������13");
            return BaseVideoConfig.CHECK_FAILED;
        }

        if (!this.checkFieldLength(tmp, 60, true))
        {
            logger.error("codeRateId=" + codeRateId
                         + ",codeRateId��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����60���ַ�");
            return BaseVideoConfig.CHECK_FAILED;
        }
        // canonicalName
        tmp = data[1];
        if (!this.checkFieldLength(tmp, 512, true))
        {
            logger.error("codeRateId="
                         + codeRateId
                         + ",canonicalName��֤���󣬸��ֶ��Ǳ����ֶΣ��ҳ��Ȳ�����512���ַ�����canonicalName="
                         + tmp);
            return BaseVideoConfig.CHECK_FAILED;
        }
        // encodeFormat
        tmp = data[2];
        if (!this.checkFieldLength(tmp, 512, true))
        {
            logger.error("codeRateId="
                         + codeRateId
                         + ",encodeFormat��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����512���ַ���encodeFormat="
                         + tmp);
            return BaseVideoConfig.CHECK_FAILED;
        }
        // containerFormat
        tmp = data[3];
        if (!this.checkFieldLength(tmp, 512, true))
        {
            logger.error("codeRateId="
                         + codeRateId
                         + ",containerFormat��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����512���ַ���containerFormat="
                         + tmp);
            return BaseVideoConfig.CHECK_FAILED;
        }
        // codeRateLevel
        tmp = data[4];
        if (!this.checkFieldLength(tmp, 512, true))
        {
            logger.error("codeRateId="
                         + codeRateId
                         + ",codeRateLevel��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����512���ַ���codeRateLevel="
                         + tmp);
            return BaseVideoConfig.CHECK_FAILED;
        }
        // netType
        tmp = data[5];
        if (!this.checkFieldLength(tmp, 512, true))
        {
            logger.error("codeRateId=" + codeRateId
                         + ",netType��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����512���ַ���netType=" + tmp);
            return BaseVideoConfig.CHECK_FAILED;
        }
        // mediaMimeType
        tmp = data[6];
        if (!this.checkFieldLength(tmp, 512, true))
        {
            logger.error("codeRateId="
                         + codeRateId
                         + ",mediaMimeType��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����512���ַ���mediaMimeType="
                         + tmp);
            return BaseVideoConfig.CHECK_FAILED;
        }
        // resolutionType
        tmp = data[7];
        if (!this.checkFieldLength(tmp, 512, true))
        {
            logger.error("codeRateId="
                         + codeRateId
                         + ",resolutionType��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����512���ַ���resolutionType="
                         + tmp);
            return BaseVideoConfig.CHECK_FAILED;
        }
        // fileNameConvention
        tmp = data[8];
        if (!this.checkFieldLength(tmp, 512, true))
        {
            logger.error("codeRateId="
                         + codeRateId
                         + ",fileNameConvention��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����512���ַ���fileNameConvention="
                         + tmp);
            return BaseVideoConfig.CHECK_FAILED;
        }
        // codecName
        tmp = data[9];
        if (!this.checkFieldLength(tmp, 512, true))
        {
            logger.error("codeRateId=" + codeRateId
                         + ",codecName��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����512���ַ���codecName="
                         + tmp);
            return BaseVideoConfig.CHECK_FAILED;
        }
        // videoEncode
        tmp = data[10];
        if (!this.checkFieldLength(tmp, 512, true))
        {
            logger.error("codeRateId=" + codeRateId
                         + ",videoEncode��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����512���ַ���videoEncode="
                         + tmp);
            return BaseVideoConfig.CHECK_FAILED;
        }
        // netMilieu
        tmp = data[11];
        if (!this.checkFieldLength(tmp, 512, true))
        {
            logger.error("codeRateId=" + codeRateId
                         + ",netMilieu��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����512���ַ���netMilieu="
                         + tmp);
            return BaseVideoConfig.CHECK_FAILED;
        }
        // samplingRate
        tmp = data[12];
        if (!this.checkFieldLength(tmp, 512, true))
        {
            logger.error("codeRateId="
                         + codeRateId
                         + ",samplingRate��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����512���ַ���samplingRate="
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
        Object[] object = new Object[13];

        object[0] = data[1];
        object[1] = data[2];
        object[2] = data[3];
        object[3] = data[4];
        object[4] = data[5];
        object[5] = data[6];
        object[6] = data[7];
        object[7] = data[8];
        object[8] = data[9];
        object[9] = data[10];
        object[10] = data[11];
        object[11] = data[12];
        object[12] = data[0];

        return object;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#getInsertSqlCode()
     */
    protected String getInsertSqlCode()
    {
        // insert into t_vo_coderate (canonicalName, encodeformat,
        // containerformat, coderatelevel, nettype, mediamimetype,
        // resolutiontype, filenameconvention, codecname, videoencode,
        // netmilieu, samplingrate, coderateid) values
        // (?,?,?,?,?,?,?,?,?,?,?,?,?)
        return "baseVideo.exportfile.CodeRateExportFile.getInsertSqlCode";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#getUpdateSqlCode()
     */
    protected String getUpdateSqlCode()
    {
        // update t_vo_coderate c set c.canonicalname=?, c.encodeformat=?,
        // c.containerformat=?, c.coderatelevel=?, c.nettype=?,
        // c.mediamimetype=?, c.resolutiontype=?, c.filenameconvention=?,
        // c.codecname=?, c.videoencode=?, c.netmilieu, c.samplingrate where
        // c.coderateid=?
        return "baseVideo.exportfile.CodeRateExportFile.getUpdateSqlCode";
    }

    protected String getDelSqlCode()
    {
        // truncate table t_vo_coderate
        return "baseVideo.exportfile.CodeRateExportFile.getDelSqlCode";
    }

    protected Object[] getHasObject(String[] data)
    {
        Object[] object = new Object[1];
        object[0] = data[0];
        return object;
    }

    protected String getHasSqlCode()
    {
        // select 1 from t_vo_coderate c where c.coderateid=?
        return "baseVideo.exportfile.CodeRateExportFile.getHasSqlCode";
    }
    
    protected String getKey(String[] data)
    {
        return data[0];
    }
}
