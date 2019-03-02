/*
 * 文件名：DeviceExperience.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述： 机型信息
 */

package com.aspire.ponaadmin.web.dataexport.experience.ext;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.util.SQLCode;
import com.aspire.dotcard.gcontent.DeviceVO;
import com.aspire.ponaadmin.web.dataexport.DataExportTools;
import com.aspire.ponaadmin.web.dataexport.experience.CommonExperience;
import com.aspire.ponaadmin.web.dataexport.experience.ExperienceConfig;
import com.aspire.ponaadmin.web.dataexport.marketing.CommonAppExport;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * <p>
 * Title: 体验营销机型信息
 * </p>
 * <p>
 * Description: 体验营销机型信息
 * </p>
 * <p>
 * Copyright: Copyright (c) 2010
 * </p>
 * 
 * @author x_wangml
 * @version
 */
public class DeviceExperience extends CommonExperience
{
	private static final JLogger LOG = LoggerFactory.getLogger(DeviceExperience.class);
	  public static  String lineSeparator ="\n\r";
	  public static String deviceColumnSep = "|";
	  public static  String encoding ="UTF-8";
	  
    public DeviceExperience()
    {

        this.setName("机型信息数据导出");
        this.constraint = "0";//默认为0，增量;1,全量
        this.fileName = DataExportTools.parseFileName(ExperienceConfig.deviceName);
        if(!CommonExperience.FileWriteDIR.endsWith(File.separator)){
        	this.exportedFileName = CommonExperience.FileWriteDIR + File.separator +  this.fileName;
        }else{
        	this.exportedFileName = CommonExperience.FileWriteDIR +  this.fileName;
        }
        this.setSize(5);
        lineSeparator = ExperienceConfig.deviceLineSep;
        deviceColumnSep = ExperienceConfig.deviceColumnSep;
        encoding = ExperienceConfig.ExperEncoding;
    }

   
    protected List getDBData() throws BOException
    {

        List finalList = new ArrayList();
        try
        {
            // 得到数据
             List allTopLists = this.getAllDeviceList(constraint);
            finalList =  allTopLists;  
//            for (int i = 0; i < allTopLists.size(); i++)
//            {
//
//                DeviceVO vo = ( DeviceVO ) allTopLists.get(i);
//                // 写入数据
//                finalList.addAll(getDataByDeviceList(vo));
//            }

        }
        catch (DAOException e)
        {
        	LOG.error("获取机型信息数据失败。"+e);
            throw new BOException("获取机型信息数据失败。", e);
        }
        return finalList;
    }
    protected void writeToFile(List list) throws BOException
    {

       
        	LOG.debug("list.size="+list.size());
            //DataExportTools.ExportDate(list, this.exportedFileName);
        	try
			{
				DataExportTools.writeToTXTFile(this.exportedFileName,list,lineSeparator,encoding);
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				 LOG.error("写入TXT出错：" + this.fileName+"，"+e);
				e.printStackTrace();
			}
            LOG.info("export file=" + this.exportedFileName);
            
            copyFileToFTP();
            LOG.info("写入FTP" + this.fileName);
        
        
    }
    /**
     * 写入数据
     * 
     * @param vo
     * @return
     * @throws BOException
     */
    protected List getDataByDeviceList(DeviceVO vo) throws BOException
    {

        List list = new ArrayList();
        List record = new ArrayList(this.getSize());
        record.add(String.valueOf(vo.getDeviceId()));
        record.add(vo.getDeviceName());
        record.add(vo.getBrand());
        record.add(new Integer(0));
        list.add(record);

        return list;
    }

    /**
     * 从终端数据表中获取全部列表
     * 
     * @return
     * @throws DAOException
     */
    private List getAllDeviceList(String fulloradd) throws DAOException
    {
        ResultSet rs = null;
        List list = new ArrayList();
//dataexport.experience.DeviceExperience.getAddDeviceList
//        select d.device_id, d.device_name, b.brand_name,'1' num
//        from t_device d, t_device_brand b
//       where d.brand_id = b.brand_id and d.create_date >(sysdate-100000)
        String fullSqlCode = "dataexport.experience.DeviceExperience.getAllDeviceList";
        String addSqlCode = "dataexport.experience.DeviceExperience.getAddDeviceList";
        String sqlValue = "";      
        try
        { 	
        	 if(fulloradd.equals("0")){
             	//增量
        		 sqlValue = SQLCode.getInstance().getSQLStatement(addSqlCode);
             }else if(fulloradd.equals("1")){
             	//全量
            	 sqlValue = SQLCode.getInstance().getSQLStatement(fullSqlCode);
             }
            rs = DB.getInstance().query(sqlValue, null);
            while (rs.next())
            {           	
            	StringBuffer ca = new StringBuffer("");
            	ca.append(rs.getString("device_id"));
            	ca.append(deviceColumnSep);
            	ca.append(rs.getString("device_name"));
            	ca.append(deviceColumnSep);
            	ca.append(rs.getString("brand_name"));
            	ca.append(deviceColumnSep);
            	ca.append(rs.getString("num"));
                list.add(ca.toString());
            }
        }
        catch (Exception e)
        {
            throw new DAOException("全量读取终端数据表出错", e);
        }
        finally
        {
            DB.close(rs);
        }
        return list;
    }
}
