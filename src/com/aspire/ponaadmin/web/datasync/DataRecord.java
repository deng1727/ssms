package com.aspire.ponaadmin.web.datasync;

import java.util.HashMap;

public class DataRecord 
{
	
	private HashMap map = new HashMap();
	
	public DataRecord()
	{}
	
	public void put(int fieldnumber,Object value)
	{
		this.map.put("field"+fieldnumber,value);
	}
	
	public Object get(int fieldnumber)
	{
		return this.map.get("field"+fieldnumber);
	}
	public int size()
	{
		return this.map.size();
	}
	public void clear()
	{
		this.map.clear();
	}

}
