package com.aspire.ponaadmin.web.dataexport.sqlexport.exe.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.aspire.common.exception.BOException;
import com.aspire.ponaadmin.web.dataexport.sqlexport.VO.DataExportVO;
import com.aspire.ponaadmin.web.dataexport.sqlexport.VO.FileSuffixKey;
import com.aspire.ponaadmin.web.dataexport.sqlexport.exe.ExportSqlAbstract;

public class ExportSqlToExcel07 extends ExportSqlAbstract
{
	private XSSFWorkbook workbook = null;
	private Sheet sheet = null;
	private FileOutputStream fOut = null;
	private int maxSheetRowSize = 65535;
	private int sheetNumber = 0;
	
	public ExportSqlToExcel07(DataExportVO vo)
	{
		super(vo);
		vo.setFileSuffix(FileSuffixKey.EXCEL07.getFileSuffix());
	}
	
	@Override
	public void createFileByName() throws BOException
	{
		try
		{
			fOut = new FileOutputStream(vo.getFileAllName());
		}
		catch (FileNotFoundException e)
		{
			throw new BOException("创建文件异常 fileName=" + vo.getFileAllName(), e);
		}
		
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet("sheet" + (sheetNumber++ + 1));
	}
	
	@Override
	public String writerFile(List<String[]> dataList) throws BOException
	{
		Iterator<String[]> it = dataList.iterator();
		
		while (it.hasNext())
		{
			String[] temp = it.next();
			
			Row row = sheet.createRow(rowNum);
			
			for (int n = 0; n < Integer.parseInt(vo.getExportLine()); n++)
			{
				Cell cell = row.createCell(n);
				cell.setCellValue(temp[n]);
			}
			
			rowNum++;
			
			// 如果大于sheet最大允许的行数，则顺序创建接下来的sheet
			if(rowNum%maxSheetRowSize == 0)
			{
				sheet = workbook.createSheet("sheet" + (sheetNumber++ + 1));
				rowNum = 0;
			}
		}
		
		return null;
	}
	
	@Override
	public void closeFile() throws BOException
	{
		if (fOut != null)
		{
			try
			{
				workbook.write(fOut);
				fOut.flush();
			}
			catch (IOException e)
			{
				Log.error("保存excel数据有误", e);
				throw new BOException("保存excel数据有误", e);
			}
			finally
			{
				try
				{
					fOut.close();
				}
				catch (IOException e)
				{
					Log.error("保存excel数据有误", e);
					throw new BOException("保存excel数据有误", e);
				}
			}
		}
		
	}

	@Override
	public void createVeriFile(int size) throws BOException {
		// TODO Auto-generated method stub
		
	}
}
