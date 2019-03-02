/**
 * SSMS
 * com.aspire.ponaadmin.web.repository.camonitor MocontentListBO.java
 * Apr 22, 2010
 * @author tungke
 * @version 1.0
 *
 */
package com.aspire.ponaadmin.web.repository.camonitor;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * @author tungke
 *
 */
public class MoContentListBO
{

	
	/**
     * 日志引用
     */
    private static final JLogger logger = LoggerFactory.getLogger(MoContentListBO.class) ;

    private static MoContentListBO instance = new MoContentListBO();
    private MoContentListBO(){}
    public static MoContentListBO getInstance(){
    	return instance;
    }
    
    /**
     * 根据机型获取机型货架
     * @param categoryID 在当前货架下查找机型货架
     * @param deviceName
     * @return
     * @throws BOException 
     */
    public String getDeviceCategory(String categoryID,String deviceName) throws BOException{
    	 String newCategoryID = null;
		try
		{
			newCategoryID = MoContentListDAO.getInstance().getDeviceCategoryByDeviceName(categoryID,deviceName);
		} catch (DAOException e)
		{
			e.printStackTrace();
			throw new BOException("数据库操作失败",e);
		}
    	return newCategoryID;
    }
    
    
}
