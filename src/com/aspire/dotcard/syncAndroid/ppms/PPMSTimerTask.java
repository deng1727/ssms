
package com.aspire.dotcard.syncAndroid.ppms;

import java.util.TimerTask;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.syncGoodsCenter.bo.GoodCenterIncrementBo;
import com.aspire.dotcard.syncGoodsCenter.bo.NewGCenterBo;


public class PPMSTimerTask extends TimerTask {

	JLogger LOG = LoggerFactory.getLogger(PPMSTimerTask.class);
	 /**
     * 执行任务
     */
    public void run()
    {
    	try {
    		LOG.info("PPMSTimerTask开始了！！！with Throwable");
    		int s = 0;
    		
    		while(true){
    		    
    			s ++;
    			
    			//LOG.info("handleAPPInfo OVER...idx="+s);
    			//update  此定时任务只处理33,30的非聚合应用.
				int leftContentNum = PPMSBO.getInstance().getLeftContentNum30And33();
				
				LOG.info("leftContentNum OVER...leftContentNum="+leftContentNum);
				
				if(leftContentNum==0) {
				    LOG.info("t_a_ppms_receive_change30表中-1状态的数据为空,PPMSTimerTask循环结束!");
				    break;
				}else{
					//处理非聚合的30应用的上下线
					PPMSBO.getInstance().handleAPPInfo();
					//处理非聚合的33应用的上下线
					NewGCenterBo.getInstance().handleAPPInfo();
					//处理应用上下线以后的上下架逻辑;另起一个定时任务处理
					//NewGCenterBo.getInstance().upOrDownCategory();
					LOG.info("PPMSTimerTask30And33应用处理完毕");
				}
				
//				//处理33*的应用
//				NewGCenterBo.getInstance().handleAPPInfo();
//				LOG.info("PPMSTimerTask33应用处理完毕");
//				
				
				
    		}
			LOG.info("PPMSTimerTask结束了，每过5分钟又会来的！！！");
			
		} catch (Throwable e) {
			LOG.error("Throwable:PPMSTimerTask:this error may let timer object stop!!",e);
		}
    }

}
