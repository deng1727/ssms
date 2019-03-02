package com.aspire.ponaadmin.web.datasync.implement;

import com.aspire.ponaadmin.web.datasync.DataReader;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConfig;

public class DataReaderImp implements DataReader
{
	protected String separator;
	
	public void init(DataSyncConfig config)
	{
		String sep = config.get("data-reader.file-separator");
		if (sep.startsWith("0x")) 
		{
			// 0x开头的，表示是16进制的，需要转换
			String s = sep.substring(2,sep.length());
			int i = Integer.parseInt(s,16);
			char c = (char)i;
			this.separator = String.valueOf(c);
		} 
		else 
		{
			this.separator = sep;
		}
	}

	public DataRecord readDataRecord(Object source)
	{
		if (source == null || source.equals(""))
		{
			return null;
		}
		String content=(String)source;
		String[] dataArray = splitToArray(content);
		DataRecord dr=new DataRecord();
		for(int i=0;i<dataArray.length;i++)
		{
			dr.put(i + 1, dataArray[i]);
		}
		return dr;
	}
	/**
	 * 根据分隔符，把content转化为数据的形式。
	 * @param content
	 * @return
	 */
	protected String [] splitToArray(String content)
	{
		return content.split(separator);

	}

}
