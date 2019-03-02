package com.aspire.ponaadmin.web.dataexport.sqlexport.VO;

public enum FileSuffixKey
{
	CSV(".csv"), TXT(".txt"), EXCEL97(".xls"), EXCEL07(".xlsx"),VERF(".verf");
	
	private final String fileSuffix;
	
	private FileSuffixKey(String fileSuffix)
	{
		this.fileSuffix = fileSuffix;
	}
	
	public String getFileSuffix()
	{
		return fileSuffix;
	}
}
