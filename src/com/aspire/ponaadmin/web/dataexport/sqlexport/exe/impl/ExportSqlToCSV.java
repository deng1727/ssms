package com.aspire.ponaadmin.web.dataexport.sqlexport.exe.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.List;

import au.com.bytecode.opencsv.CSVWriter;

import com.aspire.common.exception.BOException;
import com.aspire.ponaadmin.web.dataexport.sqlexport.VO.DataExportVO;
import com.aspire.ponaadmin.web.dataexport.sqlexport.VO.FileSuffixKey;
import com.aspire.ponaadmin.web.dataexport.sqlexport.exe.ExportSqlAbstract;

public class ExportSqlToCSV extends ExportSqlAbstract
{
	private CSVWriter writer = null;
	private OutputStreamWriter out = null;
	
	public ExportSqlToCSV(DataExportVO vo)
	{
		super(vo);
		vo.setFileSuffix(FileSuffixKey.CSV.getFileSuffix());
	}
	
	@Override
	public void createFileByName() throws BOException
	{
		try
		{
			out = new OutputStreamWriter(new FileOutputStream(vo
					.getFileAllName()), Charset.forName(vo.getEncoder()));
			writer = new CSVWriter(out, vo.getExportTypeOther().charAt(0));
		}
		catch (IOException e)
		{
			throw new BOException("�����ļ��쳣 fileName=" + vo.getFileAllName(), e);
		}
	}
	
	@Override
	public String writerFile(List<String[]> dataList) throws BOException
	{
		StringBuffer sb = new StringBuffer();
		try
		{
			if(dataList!=null&&dataList.size()>0)
			for (String[] temps : dataList)
			{
				writer.writeNext(temps);
				
				// ��ֹռ��̫���ڴ棬ÿһ����ˢ�µ��ļ�һ�¡�
				if (rowNum % 10000 == 0)
				{
					writer.flush();
				}
				rowNum++;
			}
		}
		catch (IOException e)
		{
			Log.error("��ǰ�������񣬵����������ļ�ʱ��������!", e);
			throw new BOException("д���ļ��쳣 fileName=" + vo.getFileAllName(), e);
		}
		return sb.toString();
	}
	
	@Override
	public void closeFile() throws BOException
	{
		if (writer != null)
		{
			try
			{
				writer.close();
			}
			catch (IOException e)
			{
				try
				{
					out.close();
				}
				catch (IOException e1)
				{
					throw new BOException("fileName=" + vo.getFileAllName(), e);
				}
			}
		}
		
		if(out != null)
		{
			try
			{
				out.close();
			}
			catch (IOException e)
			{
				throw new BOException("fileName=" + vo.getFileAllName(), e);
			}
		}
	}

	@Override
	public void createVeriFile(int size) throws BOException {
		// TODO Auto-generated method stub
		
	}
}
