package com.aspire.dotcard.updatetime;

import java.util.List;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.syncData.bo.DataSyncBO;

public class TimeBO {
	 /**
     * 日志引用
     */
    protected static JLogger logger = LoggerFactory.getLogger(TimeBO.class);
    
    private static TimeBO instance = new TimeBO();
	
private TimeBO(){
		
	}
    /**
     * 得到单例模式
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
    		 	 *   从t_syn_result表中获取到contentid
    		 	 * 	2016-11-14 dengshaobo 
    		 	 */
    		 
    		 List<String> list = TimeDAO.getInstance().checkTSynResult();
        	 
        	 logger.debug("list===========================" + list);
        	 
        	 /**
        	     * 通过contentid字段把T_CLMS_CONTENTTAG表中的opdate更新成当前时间
        	     * @param list 从t_syn_result获取到的contentid
        	     */
        	 TimeDAO.getInstance().updateTClmsContentTag(list);
        	 
		} catch (Exception e) {
			logger.debug("业务查询更新失败");
			logger.error(e);
			
			
		}
    	 
    	 
    	 
    	 
    }
    
    
    
    
    
    
    
    
    
    
	
}
