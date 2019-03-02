/*
 * �ļ�����DeviceExperience.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������ ������Ϣ
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
 * Title: ����Ӫ��������Ϣ
 * </p>
 * <p>
 * Description: ����Ӫ��������Ϣ
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

        this.setName("������Ϣ���ݵ���");
        this.constraint = "0";//Ĭ��Ϊ0������;1,ȫ��
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
            // �õ�����
             List allTopLists = this.getAllDeviceList(constraint);
            finalList =  allTopLists;  
//            for (int i = 0; i < allTopLists.size(); i++)
//            {
//
//                DeviceVO vo = ( DeviceVO ) allTopLists.get(i);
//                // д������
//                finalList.addAll(getDataByDeviceList(vo));
//            }

        }
        catch (DAOException e)
        {
        	LOG.error("��ȡ������Ϣ����ʧ�ܡ�"+e);
            throw new BOException("��ȡ������Ϣ����ʧ�ܡ�", e);
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
				 LOG.error("д��TXT����" + this.fileName+"��"+e);
				e.printStackTrace();
			}
            LOG.info("export file=" + this.exportedFileName);
            
            copyFileToFTP();
            LOG.info("д��FTP" + this.fileName);
        
        
    }
    /**
     * д������
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
     * ���ն����ݱ��л�ȡȫ���б�
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
             	//����
        		 sqlValue = SQLCode.getInstance().getSQLStatement(addSqlCode);
             }else if(fulloradd.equals("1")){
             	//ȫ��
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
            throw new DAOException("ȫ����ȡ�ն����ݱ����", e);
        }
        finally
        {
            DB.close(rs);
        }
        return list;
    }
}
