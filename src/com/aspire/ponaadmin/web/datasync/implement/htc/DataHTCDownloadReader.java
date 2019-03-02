package com.aspire.ponaadmin.web.datasync.implement.htc;

import com.aspire.ponaadmin.web.datasync.DataReader;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConfig;

/**
 * 用于HTC数据的文件解析 格式为： <Applications> <Application>--下载时计费应用 <APCode>100164</APCode>
 * <APPID>1234567890</APPID> <MMAPPID>0453456356</MMAPPID > <DownCount>1201</DownCount >
 * </Application> </Applications>
 * 
 * @author wangminlong
 * 
 */
public class DataHTCDownloadReader implements DataReader
{
	@Override
	public void init(DataSyncConfig config) throws Exception
	{

	}
	
	@Override
	public DataRecord readDataRecord(Object source) throws Exception
	{
		if (source == null || source.equals(""))
		{
			return null;
		}
		String content = (String) source;
		String[] dataArray = content.split("\\|");
		DataRecord dr = new DataRecord();
		for (int i = 0; i < dataArray.length; i++)
		{
			dr.put(i + 1, dataArray[i]);
		}
		return dr;
	}
}
