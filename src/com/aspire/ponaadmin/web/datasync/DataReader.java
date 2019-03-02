package com.aspire.ponaadmin.web.datasync;

public interface DataReader extends DataSyncBuilder
{
	DataRecord readDataRecord(Object source) throws Exception;
	
}
