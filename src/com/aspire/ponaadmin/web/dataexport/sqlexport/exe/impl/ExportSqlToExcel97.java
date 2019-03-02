package com.aspire.ponaadmin.web.dataexport.sqlexport.exe.impl;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.aspire.common.exception.BOException;
import com.aspire.ponaadmin.web.dataexport.sqlexport.VO.DataExportVO;
import com.aspire.ponaadmin.web.dataexport.sqlexport.VO.FileSuffixKey;
import com.aspire.ponaadmin.web.dataexport.sqlexport.exe.ExportSqlAbstract;

public class ExportSqlToExcel97 extends ExportSqlAbstract
{
	private int maxSheetRowSize = 65535;
	private int sheetNumber = 0;
	private WritableWorkbook workbook = null;
	private WritableSheet sheet = null;
	
	public ExportSqlToExcel97(DataExportVO vo)
	{
		super(vo);
		vo.setFileSuffix(FileSuffixKey.EXCEL97.getFileSuffix());
	}
	
	@Override
	public void createFileByName() throws BOException
	{
		try
		{
			workbook = Workbook.createWorkbook(new File(vo.getFileAllName()));
		}
		catch (IOException e)
		{
			throw new BOException("创建文件异常 fileName=" + vo.getFileAllName(), e);
		}
		sheet = workbook.createSheet("sheet" + (sheetNumber + 1), sheetNumber++);
	}
	
	@Override
	public String writerFile(List<String[]> dataList) throws BOException
	{
		Iterator<String[]> it = dataList.iterator();
		
		while (it.hasNext())
		{
			String[] temp = it.next();
			
			for (int n = 0; n < Integer.parseInt(vo.getExportLine()); n++)
			{
				try
				{
					sheet.addCell(new Label(n, rowNum, temp[n]));
				}
				catch (RowsExceededException e)
				{
					Log.error("写数据至excel时发生错误!", e);
					throw new BOException("写数据至excel时发生错误!", e);
				}
				catch (WriteException e)
				{
					Log.error("写数据至excel时发生错误!", e);
					throw new BOException("写数据至excel时发生错误!", e);
				}
			}
			
			rowNum++;
			
			// 如果大于sheet最大允许的行数，则顺序创建接下来的sheet
			if(rowNum%maxSheetRowSize == 0)
			{
				sheet = workbook.createSheet("sheet" + (sheetNumber + 1), sheetNumber++);
				rowNum = 0;
			}
		}
		
		return null;
	}
	
	@Override
	public void closeFile() throws BOException
	{
		if (workbook != null)
		{
			try
			{
				workbook.write();
				workbook.close();
			}
			catch (Exception e)
			{
				try
				{
					workbook.close();
				}
				catch (IOException e1)
				{
					Log.error("保存excel数据有误", e);
					throw new BOException("保存excel数据有误", e1);
				}
			}
		}
	}

	@Override
	public void createVeriFile(int size) throws BOException {
		// TODO Auto-generated method stub
		
	}
	
}
