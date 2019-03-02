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
		
		// CSV�����ļ�
		if (ExoprtSqlConfig.FILE_TYPE_CSV.equals(vo.getExportType()))
		{	
			iner = new ExportSqlToCSV(vo);
		}
		// TXT�����ļ�
		else if (ExoprtSqlConfig.FILE_TYPE_TXT.equals(vo.getExportType()))
		{
			iner = new ExportSqlToTXT(vo);
		}
		// EXCEL�����ļ�
		else if (ExoprtSqlConfig.FILE_TYPE_EXCEL.equals(vo.getExportType()))
		{
			if(ExoprtSqlConfig.FILE_TYPE_EXCEL_97.equals(vo.getExportTypeOther()))
			{
				iner = new ExportSqlToExcel97(vo);
			}
			// ����97-03��Ļ����б��ֵҲ��Ĭ��07��
			else
			{
				iner = new ExportSqlToExcel07(vo);
			}
		}
		// �û���д�ļ����ͳ���Ĭ��CSV���͡�
		else
		{
			iner = new ExportSqlToCSV(vo);
		}
		return iner;
	}
}
