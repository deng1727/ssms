/**
 * * <p>ZCOM����ͬ��������</p>
 * <p>Copyright (c) 2003-2010 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * SSMS
 * com.aspire.dotcard.synczcom.task ZcomDataSyncTask.java
 * Apr 8, 2010
 * @author tungke
 * @version 1.0
 *
 */
package com.aspire.dotcard.synczcoms.task;

import java.util.TimerTask;

import com.aspire.dotcard.synczcoms.bo.ZcomsDataSyncBO;



/**
 * @author tungke
 *
 */
public class ZcomsDataSyncTask extends TimerTask {

	 /**
     * ִ������ҵ��ͬ��������ͬ����
     */
    public void run()
    {
        //����DataSyncBO�е�����ͬ��
     ZcomsDataSyncBO.getInstance().syncZcomConAdd();
    			
    		
    	
    }

}
