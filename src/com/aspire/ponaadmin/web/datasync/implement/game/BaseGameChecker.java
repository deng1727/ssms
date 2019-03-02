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
import com.aspire.ponaadmin.web.datasync.implement.book.BookChecker;


/**
 * @author x_wangml
 *
 */
public class BaseGameChecker extends DataCheckerImp implements DataChecker
{
    /**
     * ��־����
     */
    private static final JLogger logger = LoggerFactory.getLogger(BookChecker.class) ;


    public int checkDateRecord(DataRecord record)
    {
        //pkgid
        String tmp=(String)record.get(1);
        String pkgid=tmp;
        if(logger.isDebugEnabled())
        {
            logger.debug("��ʼ��֤��Ϸ���ֶθ�ʽ��pkgid="+pkgid);
        }   
        if(record.size()!=13 && record.size()!=12)
        {
            logger.error("�ֶ���������13����Ҳ������12");
            return DataSyncConstants.CHECK_FAILED;
        }
        
        if(!this.checkFieldLength(tmp, 25, true))
        {
            logger.error("pkgid="+tmp+",pkgid��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����25���ַ�");
            return DataSyncConstants.CHECK_FAILED;
        }
        //pkgName
        tmp=(String)record.get(2);
        if(!this.checkFieldLength(tmp, 64, true))
        {
            logger.error("pkgid="+pkgid+",pkgName��֤���󣬸��ֶ��Ǳ����ֶΣ��ҳ��Ȳ�����64���ַ�����pkgName="+tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
        //pkgDesc
        tmp=(String)record.get(3);
        if(!this.checkFieldLength(tmp, 512, true))
        {
            logger.error("pkgid="+pkgid+",pkgDesc��֤�������Ȳ�����512���ַ���pkgDesc="+tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
        //CPname
        tmp=(String)record.get(4);
        if(!this.checkFieldLength(tmp, 64, false))
        {
            logger.error("pkgid="+pkgid+",CPname���Ȳ�����64���ַ���CPname="+tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
        //ServiceCode
        tmp=(String)record.get(5);
        if(!this.checkFieldLength(tmp, 30, false))
        {
            logger.error("pkgid="+pkgid+",ServiceCode���Ȳ�����30���ַ���ServiceCode="+tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
        //Fee
        tmp=(String)record.get(6);
        if(!this.checkIntegerField("Fee", tmp, 5, true))
        {
            return DataSyncConstants.CHECK_FAILED;
        }
        //pkgURL
        tmp=(String)record.get(7);
        if(!this.checkFieldLength(tmp, 300, true))
        {
            logger.error("pkgid="+pkgid+",pkgURL���Ȳ�����300���ַ���pkgURL="+tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
        //PICURL1
        tmp=(String)record.get(8);
        if(!this.checkFieldLength(tmp, 255, true))
        {
            logger.error("pkgid="+pkgid+",PICURL1���Ȳ�����255���ַ���PICURL1="+tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
        //PICURL2
        tmp=(String)record.get(9);
        if(!this.checkFieldLength(tmp, 255, true))
        {
            logger.error("pkgid="+pkgid+",PICURL2���Ȳ�����255���ַ���PICURL2="+tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
        //PICURL3
        tmp=(String)record.get(10);
        if(!this.checkFieldLength(tmp, 255, true))
        {
            logger.error("pkgid="+pkgid+",CPname���Ȳ�����255���ַ���PICURL3="+tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
        //PICURL4
        tmp=(String)record.get(11);
        if(!this.checkFieldLength(tmp, 255, true))
        {
            logger.error("pkgid="+pkgid+",CPname���Ȳ�����255���ַ���PICURL4="+tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
        //Changetype
        tmp=(String)record.get(12);
        if(!this.checkFieldLength(tmp, 2, true))
        {
            logger.error("pkgid="+pkgid+",Changetype���Ȳ�����2���ַ���Changetype="+tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
        
        // �½ӿ�
        if(record.size() == 13)
        {
            // ProvinceCtrol
            tmp = ( String ) record.get(13);
            if (!this.checkFieldLength(tmp, 500, false))
            {
                logger.error("pkgid=" + pkgid
                             + ",ProvinceCtrol���Ȳ�����500���ַ���ProvinceCtrol=" + tmp);
                return DataSyncConstants.CHECK_FAILED;
            }
        }
        
        return DataSyncConstants.CHECK_SUCCESSFUL;
    }

    public void init(DataSyncConfig config)
    {

    }

}
