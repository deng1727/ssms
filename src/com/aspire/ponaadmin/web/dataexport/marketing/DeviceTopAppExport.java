package com.aspire.ponaadmin.web.dataexport.marketing;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.ponaadmin.web.dataexport.DataExportTools;
import com.aspire.ponaadmin.web.util.PublicUtil;

public class DeviceTopAppExport extends CommonAppExport
{
	/**
	 * 每个机型适配的输出的最大应用数
	 */
	private int topCount=10;
	public DeviceTopAppExport()throws DAOException
	{
		this.setName("终端适配应用信息同步");
		this.setFromSqlCode("dataexport.marketing.DeviceTopAppExport.getDBData");
		this.setSize(13);
		this.exportedFileName=CommonAppExport.FileWriteDIR+"DEVICE_APP_"+PublicUtil.getCurDateTime("yyyyMMdd")+".XLS";
        this.fileName="DEVICE_APP_"+PublicUtil.getCurDateTime("yyyyMMdd")+".XLS";
	}
	protected List getDBData() throws BOException
	{
		ResultSet rs=null;
		List finalList=new ArrayList();
		try
		{
			Map deviceMap = this.getAllMMSupportedDeviceList();

			rs = DB.getInstance().queryBySQLCode(getFromSqlCode(), this.importParams);
			while (rs.next())
			{
				int columnSize = this.getSize();
				List record = new ArrayList(columnSize);
				for (int i = 1; i <= columnSize; i++)
				{
					if(i==1)
					{
						record.add(DataExportTools.getClobString(rs.getClob(i)));
					}else
					{
						record.add(rs.getString(i));
					}
					
				}

				String deviceName = (String) record.get(0);

				if (deviceName == null || "".equals(deviceName))
				{
					continue;
				}
				String supportedDevice[] = DataExportTools
						.getDeviceNameToArray(deviceName);
				for (int i = 0; i < supportedDevice.length; i++)
				{
					List contentList = (List) deviceMap.get(supportedDevice[i]);
					if (contentList != null)
					{
						if (contentList.size() <= topCount)
						{
							List row=new ArrayList(this.getSize());
							row.add(supportedDevice[i]);
							row.addAll(record.subList(1, record.size()));
							contentList.add(row);
						}
					}
				}
			}
			for(Iterator ite=deviceMap.keySet().iterator();ite.hasNext();)
			{
				List contentList=(List)deviceMap.get(ite.next());
				if(contentList.size()>0)
				{
					finalList.addAll(contentList);
				}
				
			}
			
		} catch (Exception e)
		{

			throw new BOException("读取终端适配应用信息失败",e);
			
		}finally
		{
			DB.close(rs);
		}
		return finalList;
	}
	/**
	 * 获取MM应用支持的终端
	 * @return
	 * @throws DAOException
	 */
	private Map getAllMMSupportedDeviceList()throws DAOException
	{
		ResultSet rs=null;
		Map map=new TreeMap();
		String sqlCode="dataexport.marketing.DeviceTopAppExport.getAllMMSupportedDeviceList";		
		try
		{
			rs= DB.getInstance().queryBySQLCode(sqlCode, null);
			
			while(rs.next())
			{
				String deviceName=rs.getString("device_name");
				if(deviceName!=null)
				{
					map.put(deviceName, new ArrayList(topCount));
				}			
			}
		} catch (Exception e)
		{
            throw new DAOException("获取MM应用支持所有应用机型出错",e);
		}finally
		{
			DB.close(rs);
		}
		return map;
	}
}
