package com.aspire.ponaadmin.web.dataexport.sqlexport.exe;

import com.aspire.ponaadmin.web.dataexport.sqlexport.VO.DataExportVO;
import com.aspire.ponaadmin.web.dataexport.sqlexport.VO.ExoprtSqlConfig;
import com.aspire.ponaadmin.web.dataexport.sqlexport.exe.impl.ExportSqlToCSV;
import com.aspire.ponaadmin.web.dataexport.sqlexport.exe.impl.ExportSqlToExcel07;
import com.aspire.ponaadmin.web.dataexport.sqlexport.exe.impl.ExportSqlToExcel97;
import com.aspire.ponaadmin.web.dataexport.sqlexport.exe.impl.ExportSqlToTXT;

public class ExportSqlFactory
{
	public static ExportSqlInterface getExportSql(DataExportVO vo)
	{
		ExportSqlInterface iner = null;
		
		// CSV类型文件
		if (ExoprtSqlConfig.FILE_TYPE_CSV.equals(vo.getExportType()))
		{	
			iner = new ExportSqlToCSV(vo);
		}
		// TXT类型文件
		else if (ExoprtSqlConfig.FILE_TYPE_TXT.equals(vo.getExportType()))
		{
			iner = new ExportSqlToTXT(vo);
		}
		// EXCEL类型文件
		else if (ExoprtSqlConfig.FILE_TYPE_EXCEL.equals(vo.getExportType()))
		{
			if(ExoprtSqlConfig.FILE_TYPE_EXCEL_97.equals(vo.getExportTypeOther()))
			{
				iner = new ExportSqlToExcel97(vo);
			}
			// 不是97-03版的话。有别的值也是默认07版
			else
			{
				iner = new ExportSqlToExcel07(vo);
			}
		}
		// 用户填写文件类型出错。默认CSV类型。
		else
		{
			iner = new ExportSqlToCSV(vo);
		}
		return iner;
	}
}
