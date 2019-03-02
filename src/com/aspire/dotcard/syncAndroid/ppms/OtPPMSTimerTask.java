
package com.aspire.dotcard.syncAndroid.ppms;

import java.util.TimerTask;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.syncGoodsCenter.bo.GoodCenterIncrementBo;
import com.aspire.dotcard.syncGoodsCenter.bo.NewGCenterBo;


public class OtPPMSTimerTask extends TimerTask {

	JLogger LOG = LoggerFactory.getLogger(OtPPMSTimerTask.class);
	 /**
     * ִ������
     */
    public void run()
    {
    	try {
    		LOG.info("OtPPMSTimerTask��ʼ�ˣ�����with Throwable,�ۺ�Ӧ�ÿ�ʼ....");
    		int s = 0;
    		
    		while(true){
    		    
    			s ++;
    			
    			LOG.info("handleAPPInfo OVER...idx="+s);
    			//update by fanqh 20131210 ����ִ��˳���ȼ��������,�ڴ���

				// ��һ��:����������appid
				// select distinct ch.appid
				// from t_a_ppms_receive_change ch
				// where ch.status = -1
				// and ch.appid is not null

				// �ڶ���:���ÿ��appid��ͬ��Ӧ��ͬһ���������ߴ���
				// select *
				// from (select c.*,
				// row_number() over(partition by c.appid, c.entityid order by
				// c.createdate desc) rn
				// from t_a_ppms_receive_change c
				// where c.status = -1)
				// where rn = 1
				// and appid = '100000000001'
				// order by createdate desc, opt asc;

				// ע��:�����һ��Ӧ��������״̬,���ñ�־λtrue��Ҫ����appid�ҵ������ڼ�Ӧ��,����һ��Ӧ���ϼ���ȥ,sortid����
    			
    			//�����־λ��true,���滹������Ӧ��,ֱ�ӽ�������Ϣ��status�޸�Ϊ0.

				int leftContentNum = PPMSBO.getInstance().getLeftContentNum33And33WithAppid();
				
				LOG.info("leftContentNum OVER...leftContentNum="+leftContentNum);
				
				if(leftContentNum==0) {
				    LOG.info("t_a_ppms_receive_change����-1״̬��33����Ϊ��,PPMSTimerTaskѭ������!");
				    break;
				}else{
					//����������Ϣ�����¼�
					NewGCenterBo.getInstance().upOrDownCategory();
					LOG.info("OtPPMSTimerTask��ʼ�ˣ�����with Throwable,�ۺ�Ӧ�ý���....");
				}
				
    		}
			LOG.info("PPMSTimerTask�����ˣ�ÿ��5�����ֻ����ģ�����");
			
		} catch (Throwable e) {
			LOG.error("Throwable:PPMSTimerTask:this error may let timer object stop!!",e);
		}
    }

}
