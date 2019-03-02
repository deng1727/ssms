/**
 * SSMS
 * com.aspire.ponaadmin.web.repository.camonitor CategoryMonitorBO.java
 * Apr 20, 2010
 * @author tungke
 * @version 1.0
 *
 */
package com.aspire.ponaadmin.web.repository.camonitor;

import java.util.HashMap;
import java.util.List;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * @author tungke
 *
 */
public class CategoryMonitorBO {

	private  static CategoryMonitorBO instance = new CategoryMonitorBO();
	private CategoryMonitorBO(){
		
	}
	
	public static CategoryMonitorBO getInstance(){
		return instance;
	}
	/**
     * 日志引用
     */
    private static final JLogger logger = LoggerFactory.getLogger(CategoryMonitorBO.class) ;

    
    /**
     * 获取所有货架
     * @return
     * @throws BOException
     */
    public List getAllCategoryList() throws BOException{
    	List allCategorylist = null;
    	try {
			 allCategorylist = CategoryMonitorDAO.getInstance().getCategoryMonitor();
		} catch(Exception e)
        {
			logger.error("CategoryMonitorBO.getAllCategoryList failed!"+ e);
            throw new BOException("CategoryMonitorBO.getAllCategoryList failed!", e);
        }
		return allCategorylist;
    }
    /**
     * 获取所有货架
     * @return
     * @throws BOException
     */
    public HashMap getAllDeviceList() throws BOException{
    	HashMap hm = null;
    	try {
    		hm = CategoryMonitorDAO.getInstance().getAllDeviceMonitor();
		} catch(Exception e)
        {
			logger.error("CategoryMonitorBO.getAllDeviceList failed!"+ e);
            throw new BOException("CategoryMonitorBO.getAllDeviceList failed!", e);
        }
		return hm;
    }
}
