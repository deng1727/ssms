package com.aspire.ponaadmin.web.dataexport.sqlexport.exe;

import com.aspire.ponaadmin.web.dataexport.sqlexport.VO.DataExportGroupVO;

public interface ExportSqlInterface
{	
	public String createFile(DataExportGroupVO groupVO);
}
