package com.aspire.dotcard.gcontent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * ��ѯ��Ϸ���ݶ����Ӧ����������������ն˻��Ͷ���
 * @author x_liyouli
 *
 */
public class DeviceDAO
{

    /**
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(DeviceDAO.class);

    /**
     * ���췽������singletonģʽ����
     */
    private DeviceDAO()
    {

    }

    /**
     * singletonģʽ��ʵ��
     */
    private static DeviceDAO deviceDAO = new DeviceDAO();

    /**
     * ��ȡʵ��
     *
     * @return ʵ��
     */
    public static final DeviceDAO getInstance()
    {

        return deviceDAO;
    }

    /**
     * ���ն����ݱ��л�ȡȫ���б�
     */
    public  HashMap getDeviceList() throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("getDeviceList");
        }
        String sqlCode = "com.aspire.dotcard.gcontent.DeviceDAO.getDeviceList().SELECT";
        HashMap deviceMap = new HashMap();
        ResultSet rs = null;
        try
        {
            Object[] paras = null;
           

            rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
            while (rs != null && rs.next())
            {
            	DeviceVO vo=new DeviceVO();
            	vo.setDeviceId(rs.getString("device_id"));
            	vo.setDeviceName(rs.getString("device_name"));
            	vo.setBrand(rs.getString("brand_name"));
            	deviceMap.put(vo.getDeviceId(), vo);
            }
            return deviceMap;
        }
        catch (SQLException ex)
        {
            throw new DAOException("getDeviceList error.", ex);
        }finally{
        	//add by tungke for close db con or rs
        	DB.close(rs);
        	
        }

    }
    /**
     * ���ն����ݱ��л�ȡdevicename��brand��ӳ��
     */
    public  HashMap getDeviceNameMapBrand() throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("getDeviceNameMapBrand");
        }
        String sqlCode = "com.aspire.dotcard.gcontent.DeviceDAO.getDeviceList().SELECT";
        HashMap deviceMap = new HashMap();
        ResultSet rs = null;
        try
        {
            Object[] paras = null;
            

            rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
            while (rs != null && rs.next())
            {
            	DeviceVO vo=new DeviceVO();
            	vo.setDeviceName(rs.getString("device_name"));
            	vo.setBrand(rs.getString("brand_name"));
            	deviceMap.put(vo.getDeviceName(), vo.getBrand());
            }
            return deviceMap;
        }
        catch (SQLException ex)
        {
            throw new DAOException("getDeviceList error.", ex);
        }finally{
        	//add by tungke for close db con or rs
        	DB.close(rs);
        	
        }

    }
    
    
    /**
     * ���ն����ݱ��л�ȡdevicename��seriesid��ӳ��  add by aiyan 2013-04-22
     */
    public  HashMap getDeviceNameMapSeriesid() throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("getDeviceNameMapSeriesid");
        }
        String sqlCode = "com.aspire.dotcard.gcontent.DeviceDAO.getDeviceNameMapSeriesid";
        HashMap map = new HashMap();
        ResultSet rs = null;
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, null);
            String devicename,seriesid;
            while (rs != null && rs.next())
            {
            	
            	devicename = rs.getString("devicename");
            	seriesid = rs.getString("series_id");
            	map.put(devicename, seriesid);
            }
            return map;
        }
        catch (SQLException ex)
        {
            throw new DAOException("getDeviceList error.", ex);
        }finally{
        	//add by tungke for close db con or rs
        	DB.close(rs);
        	
        }

    }
    
    
   /* *//**
     * �����ݿ�DEVICE_PROPERTIES���в�ѯ
     * select t.useragent,t.devicepattern,t.producercname,t.producerename,t.devicetype from DEVICE_PROPERTIES t where t.devicename = ?
     * @param deviceName
     * @return
     *//*
    public DeviceVO getDeviceVO(String deviceName) throws DAOException
    {
		if(logger.isDebugEnabled())
	    {
	        logger.debug("DeviceDAO.getDeviceVO()");
	    }
		DeviceVO vo = null; 
	    ResultSet rs = null;
	    String sqlCode = "com.aspire.dotcard.gcontent.DeviceDAO.getDeviceVO.SELECT";
	    Object[] paras = {deviceName};
	    
	    try
	    {        	
	    	rs = DB.getInstance().queryBySQLCode(sqlCode,paras);
	        if(rs.next())
	        {
	        	vo = new DeviceVO();	
	        	vo.setDeviceName(deviceName);
	        	vo.setUA(rs.getString("useragent"));
	        	vo.setLUA(rs.getString("devicepattern"));
	        	vo.setProducerCName(rs.getString("producercname"));
	        	vo.setProducerEName(rs.getString("producerename"));
	        	vo.setDeviceType(rs.getString("devicetype"));
	        }
	    }
	    catch(Exception e)
	    {
	        throw new DAOException("DeviceDAO.getDeviceVO() error.", e);
	    }
	    finally
	    {
	        DB.close(rs);
	    }
	    
	    return vo;
    }*/
}
