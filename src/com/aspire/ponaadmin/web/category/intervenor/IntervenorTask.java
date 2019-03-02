/*
 * 
 */
package com.aspire.ponaadmin.web.category.intervenor;

import com.aspire.common.exception.BOException;
import com.aspire.ponaadmin.web.util.ArrangedTask;


/**
 * @author x_wangml
 *
 */
public class IntervenorTask extends ArrangedTask
{
    /**
     * 初始化字段
     */
    public void init()throws BOException
    {
        this.setExecuteTime(IntervenorConfig.STARTTIME);
        this.setTaskDesc("人工干预榜单定时任务");
    }
    
    /**
     * 定时执行任务
     */
    public void doTask()
    {
        IntervenorTools.getInstance().intervenorCategoryAll();
    }
}
