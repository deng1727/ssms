
package com.aspire.dotcard.syncAndroid.ppms;

import java.util.TimerTask;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.syncGoodsCenter.bo.GoodCenterIncrementBo;
import com.aspire.dotcard.syncGoodsCenter.bo.NewGCenterBo;


public class OtPPMSTimerTask extends TimerTask {

	JLogger LOG = LoggerFactory.getLogger(OtPPMSTimerTask.class);
	 /**
     * 执行任务
     */
    public void run()
    {
    	try {
    		LOG.info("OtPPMSTimerTask开始了！！！with Throwable,聚合应用开始....");
    		int s = 0;
    		
    		while(true){
    		    
    			s ++;
    			
    			LOG.info("handleAPPInfo OVER...idx="+s);
    			//update by fanqh 20131210 调整执行顺序，先检查数据量,在处理

				// 第一步:查出待处理的appid
				// select distinct ch.appid
				// from t_a_ppms_receive_change ch
				// where ch.status = -1
				// and ch.appid is not null

				// 第二步:针对每个appid相同的应用同一事务上下线处理
				// select *
				// from (select c.*,
				// row_number() over(partition by c.appid, c.entityid order by
				// c.createdate desc) rn
				// from t_a_ppms_receive_change c
				// where c.status = -1)
				// where rn = 1
				// and appid = '100000000001'
				// order by createdate desc, opt asc;

				// 注意:如果第一个应用是上线状态,设置标志位true需要根据appid找到所有在架应用,将第一个应用上架上去,sortid不变
    			
    			//如果标志位是true,后面还有上线应用,直接将该条消息的status修改为0.

				int leftContentNum = PPMSBO.getInstance().getLeftContentNum33And33WithAppid();
				
				LOG.info("leftContentNum OVER...leftContentNum="+leftContentNum);
				
				if(leftContentNum==0) {
				    LOG.info("t_a_ppms_receive_change表中-1状态的33数据为空,PPMSTimerTask循环结束!");
				    break;
				}else{
					//处理所有消息的上下架
					NewGCenterBo.getInstance().upOrDownCategory();
					LOG.info("OtPPMSTimerTask开始了！！！with Throwable,聚合应用结束....");
				}
				
    		}
			LOG.info("PPMSTimerTask结束了，每过5分钟又会来的！！！");
			
		} catch (Throwable e) {
			LOG.error("Throwable:PPMSTimerTask:this error may let timer object stop!!",e);
		}
    }

}
