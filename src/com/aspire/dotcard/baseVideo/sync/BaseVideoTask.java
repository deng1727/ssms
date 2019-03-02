package com.aspire.dotcard.baseVideo.sync;

import java.util.TimerTask;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseVideo.bo.BaseVideoFileBO;

public class BaseVideoTask extends TimerTask
{
    protected static JLogger logger = LoggerFactory.getLogger(BaseVideoTask.class);
    
    public void run()
    {
        StringBuffer sb = new StringBuffer();
        boolean isDiyTrue = false; 
    	
        // 同步各种表 
        BaseVideoFileBO.getInstance().fileDataSync();
        
        // 用于清空旧的模拟表数据，以用于全量更新
        BaseVideoFileBO.getInstance().cleanOldSimulationData(sb);
        
        // 新增查询应用的三张表的索引
        sb.append(BaseVideoFileBO.getInstance().addIndex());
        
        // 根据现有表情况，组装模拟表
        isDiyTrue = BaseVideoFileBO.getInstance().diyDataTable(sb);
        
        // 删除同步临时表的索引
        sb.append(BaseVideoFileBO.getInstance().dropIndex());
		
        // 组装模拟表出错
        if(!isDiyTrue)
        {
        	sb.append("组装模拟表数据时发生错误，后续动作取消！");
        }
        
        BaseVideoFileBO.getInstance().sendResultMail("基地视频组装模拟货架结果邮件", sb);
        
        // 模拟表组建情况是否正确
        if(isDiyTrue)
        {
        	// 删除表中重复的数据
        	BaseVideoFileBO.getInstance().delRepeatData();
        	
            //修改同步临时表为正式表
            BaseVideoFileBO.getInstance().renameDataSync();
            
            // 用于统计子栏目数与栏目下节目数
            BaseVideoFileBO.getInstance().updateCategoryNodeNum();
        }
    }
}
