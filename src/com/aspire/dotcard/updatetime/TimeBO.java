package com.aspire.dotcard.updatetime;

import java.util.List;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.syncData.bo.DataSyncBO;

public class TimeBO {
	 /**
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(TimeBO.class);
    
    private static TimeBO instance = new TimeBO();
	
private TimeBO(){
		
	}
    /**
     * �õ�����ģʽ
     * 
     */
    public static TimeBO getInstance()
    {

        return instance;
    }
    
    
    public void syncTime(){
    	
    	 logger.debug("syncTime()");
    	 
    	 try {
    		 /**
    		 	 *   ��t_syn_result���л�ȡ��contentid
    		 	 * 	2016-11-14 dengshaobo 
    		 	 */
    		 
    		 List<String> list = TimeDAO.getInstance().checkTSynResult();
        	 
        	 logger.debug("list===========================" + list);
        	 
        	 /**
        	     * ͨ��contentid�ֶΰ�T_CLMS_CONTENTTAG���е�opdate���³ɵ�ǰʱ��
        	     * @param list ��t_syn_result��ȡ����contentid
        	     */
        	 TimeDAO.getInstance().updateTClmsContentTag(list);
        	 
		} catch (Exception e) {
			logger.debug("ҵ���ѯ����ʧ��");
			logger.error(e);
			
			
		}
    	 
    	 
    	 
    	 
    }
    
    
    
    
    
    
    
    
    
    
	
}
