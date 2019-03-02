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
     * ��־����
     */
    private static final JLogger logger = LoggerFactory.getLogger(MoContentListBO.class) ;

    private static MoContentListBO instance = new MoContentListBO();
    private MoContentListBO(){}
    public static MoContentListBO getInstance(){
    	return instance;
    }
    
    /**
     * ���ݻ��ͻ�ȡ���ͻ���
     * @param categoryID �ڵ�ǰ�����²��һ��ͻ���
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
			throw new BOException("���ݿ����ʧ��",e);
		}
    	return newCategoryID;
    }
    
    
}
