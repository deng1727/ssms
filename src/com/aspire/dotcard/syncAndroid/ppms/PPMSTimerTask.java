
package com.aspire.dotcard.syncAndroid.ppms;

import java.util.TimerTask;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.syncGoodsCenter.bo.GoodCenterIncrementBo;
import com.aspire.dotcard.syncGoodsCenter.bo.NewGCenterBo;


public class PPMSTimerTask extends TimerTask {

	JLogger LOG = LoggerFactory.getLogger(PPMSTimerTask.class);
	 /**
     * ִ������
     */
    public void run()
    {
    	try {
    		LOG.info("PPMSTimerTask��ʼ�ˣ�����with Throwable");
    		int s = 0;
    		
    		while(true){
    		    
    			s ++;
    			
    			//LOG.info("handleAPPInfo OVER...idx="+s);
    			//update  �˶�ʱ����ֻ����33,30�ķǾۺ�Ӧ��.
				int leftContentNum = PPMSBO.getInstance().getLeftContentNum30And33();
				
				LOG.info("leftContentNum OVER...leftContentNum="+leftContentNum);
				
				if(leftContentNum==0) {
				    LOG.info("t_a_ppms_receive_change30����-1״̬������Ϊ��,PPMSTimerTaskѭ������!");
				    break;
				}else{
					//����Ǿۺϵ�30Ӧ�õ�������
					PPMSBO.getInstance().handleAPPInfo();
					//����Ǿۺϵ�33Ӧ�õ�������
					NewGCenterBo.getInstance().handleAPPInfo();
					//����Ӧ���������Ժ�����¼��߼�;����һ����ʱ������
					//NewGCenterBo.getInstance().upOrDownCategory();
					LOG.info("PPMSTimerTask30And33Ӧ�ô������");
				}
				
//				//����33*��Ӧ��
//				NewGCenterBo.getInstance().handleAPPInfo();
//				LOG.info("PPMSTimerTask33Ӧ�ô������");
//				
				
				
    		}
			LOG.info("PPMSTimerTask�����ˣ�ÿ��5�����ֻ����ģ�����");
			
		} catch (Throwable e) {
			LOG.error("Throwable:PPMSTimerTask:this error may let timer object stop!!",e);
		}
    }

}
