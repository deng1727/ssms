package com.aspire.ponaadmin.web.datasync;

public interface DataChecker extends DataSyncBuilder
{
	int checkDateRecord(DataRecord record) throws Exception;

}
