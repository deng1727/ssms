/*
 * 
 */

package com.aspire.ponaadmin.web.datasync.implement.game;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataChecker;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConfig;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.implement.DataCheckerImp;

/**
 * @author x_wangml
 * 
 */
public class BaseGameStatChecker extends DataCheckerImp implements DataChecker
{
    /**
     * ��־����
     */
    private static final JLogger logger = LoggerFactory.getLogger(BaseGameStatChecker.class);

    public int checkDateRecord(DataRecord record)
    {
        // ��ϷID
        String tmp = ( String ) record.get(1);
        String pkgid = tmp;
        if (logger.isDebugEnabled())
        {
            logger.debug("��ʼ��֤��ϷID�θ�ʽ����ϷID=" + pkgid);
        }
        int size = 12;
        if (record.size() != size)
        {
            logger.error("�ֶ���������" + size);
            return DataSyncConstants.CHECK_FAILED;
        }

        // 1        ��ϷID
        tmp = ( String ) record.get(1);
        if (!this.checkFieldLength("��ϷID", tmp, 32, true))
        {
            return DataSyncConstants.CHECK_FAILED;
        }
        // 2        ��������
        tmp = ( String ) record.get(2);
        if (!this.checkIntegerField("��������", tmp, 2, true))
        {
            return DataSyncConstants.CHECK_FAILED;
        }
        // 3        �û�����
        tmp = ( String ) record.get(3);
        if (!this.checkIntegerField("�û�����", tmp, 2, true))
        {
            return DataSyncConstants.CHECK_FAILED;
        }
        // 4 ��������
        tmp = ( String ) record.get(4);
        if (!this.checkIntegerField("��������", tmp, 10, true))
        {
            return DataSyncConstants.CHECK_FAILED;
        }
        // 5 ��������
        tmp = ( String ) record.get(5);
        if (!this.checkIntegerField("��������", tmp, 10, true))
        {
            return DataSyncConstants.CHECK_FAILED;
        }
        // 6 ��������
        tmp = ( String ) record.get(6);
        if (!this.checkIntegerField("��������", tmp, 10, true))
        {
            return DataSyncConstants.CHECK_FAILED;
        }
        // 7 ǰ7������
        tmp = ( String ) record.get(7);
        if (!this.checkIntegerField("ǰ7������", tmp, 10, true))
        {
            return DataSyncConstants.CHECK_FAILED;
        }
        // 8 ǰ30������
        tmp = ( String ) record.get(8);
        if (!this.checkIntegerField("ǰ30������", tmp, 10, true))
        {
            return DataSyncConstants.CHECK_FAILED;
        }
        // 9 ������
        tmp = ( String ) record.get(9);
        if (!this.checkIntegerField("������", tmp, 10, true))
        {
            return DataSyncConstants.CHECK_FAILED;
        }
        // // 10 ���������ʡ�
        // tmp = ( String ) record.get(10);
        // if (!this.checkIntegerField("����������", tmp, 10, true))
        // {
        // return DataSyncConstants.CHECK_FAILED;
        // }
        // // 11 ���û���Ծ��
        // tmp = ( String ) record.get(11);
        // if (!this.checkIntegerField("���û���Ծ��", tmp, 1, true))
        //        {
        //            return DataSyncConstants.CHECK_FAILED;
        //        }
        // 12 �Ƽ�����
        tmp = ( String ) record.get(12);
        if (!this.checkIntegerField("�Ƽ�����", tmp, 10, true))
        {
            return DataSyncConstants.CHECK_FAILED;
        }

        return DataSyncConstants.CHECK_SUCCESSFUL;
    }

    public void init(DataSyncConfig config)
    {

    }
}
