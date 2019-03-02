/**
 * * <p>ZCOM数据同步任务器</p>
 * <p>Copyright (c) 2003-2010 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * SSMS
 * com.aspire.dotcard.synczcom.task ZcomDataSyncTask.java
 * Apr 8, 2010
 * @author tungke
 * @version 1.0
 *
 */
package com.aspire.dotcard.synczcom.task;

import java.util.TimerTask;

import com.aspire.dotcard.synczcom.bo.ZcomDataSyncBO;



/**
 * @author tungke
 *
 */
public class ZcomDataSyncTask extends TimerTask {

	 /**
     * 执行任务（业务同步和内容同步）
     */
    public void run()
    {

        //调用DataSyncBO中的syncService方法；
        //DataSyncBO.getInstance().syncService();

        //调用DataSyncBO中的增量同步
    	ZcomDataSyncBO.getInstance().syncZcomConAdd();
    }

}
