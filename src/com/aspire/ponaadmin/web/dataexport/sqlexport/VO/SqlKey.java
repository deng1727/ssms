package com.aspire.ponaadmin.web.dataexport.sqlexport.VO;

public enum SqlKey
{
	SELECT;
	
	public static boolean isKey(String key)
	{
		String temp = key.substring(0, key.indexOf(" "));
		for (SqlKey s : SqlKey.values())
		{
			if(s.toString().equals(temp.toUpperCase()))
			{
				return true;
			}
		}
		return false;
	}
}
