package com.aspire.ponaadmin.web.datasync;

import java.util.HashMap;
import java.util.Iterator;

public class DataSyncConfig 
{
	private HashMap map = new HashMap();
	
	public DataSyncConfig()
	{}
	
	void put(String name,String value)
	{
		this.map.put(name,value);
	}
	
	public String get(String name)
	{
		String value =(String)this.map.get(name);
		if(value==null)
		{
			throw new RuntimeException("config itme: ["+name+"] not found!!!");
		}
		return value;
	}
	
	public String toString()
	{
		Iterator keys = map.keySet().iterator();
		StringBuffer sb = new StringBuffer();
		while(keys.hasNext())
		{
			String key = (String)keys.next();
			String value = (String)map.get(key);
			sb.append(key+':'+value+'\n');
		}
		return sb.toString();
	}
}
