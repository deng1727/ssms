package com.aspire.dotcard.cysyncdata.task;

import java.util.TimerTask;

import com.aspire.dotcard.cysyncdata.bo.CYDataSyncBO;





/**
 * <p>����ͬ��������</p>
 * <p>Copyright (c) 2003-2007 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author zhangmin
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class CYDataSyncTask extends TimerTask
{
    /**
     * ִ������ҵ��ͬ��������ͬ����
     */
    public void run()
    {

        

        //����DataSyncBO�е�����ͬ��
    	new CYDataSyncBO().syncConAdd();
        
    	//����Ӧ������
    	//DataSyncDAO.getInstance().updateRemarks();
    }
}
