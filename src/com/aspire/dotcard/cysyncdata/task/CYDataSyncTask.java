package com.aspire.dotcard.cysyncdata.task;

import java.util.TimerTask;

import com.aspire.dotcard.cysyncdata.bo.CYDataSyncBO;





/**
 * <p>数据同步任务器</p>
 * <p>Copyright (c) 2003-2007 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author zhangmin
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class CYDataSyncTask extends TimerTask
{
    /**
     * 执行任务（业务同步和内容同步）
     */
    public void run()
    {

        

        //调用DataSyncBO中的增量同步
    	new CYDataSyncBO().syncConAdd();
        
    	//更新应用评分
    	//DataSyncDAO.getInstance().updateRemarks();
    }
}
