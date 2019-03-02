package com.aspire.ponaadmin.web.dataexport.sqlexport.exe.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.aspire.common.exception.BOException;
import com.aspire.ponaadmin.web.dataexport.sqlexport.VO.DataExportVO;
import com.aspire.ponaadmin.web.dataexport.sqlexport.VO.FileSuffixKey;
import com.aspire.ponaadmin.web.dataexport.sqlexport.exe.ExportSqlAbstract;
import com.aspire.ponaadmin.web.util.DateUtil;

public class ExportSqlToTXT extends ExportSqlAbstract
{
	private BufferedWriter writer = null;
	
	private BufferedWriter verfWriter = null ;
	
	private static final String isVerf="1";	
	
	public ExportSqlToTXT(DataExportVO vo)
	{
		super(vo);
		vo.setFileSuffix(FileSuffixKey.TXT.getFileSuffix());
	}
	
	@Override
	public void createFileByName() throws BOException
	{
		try
		{
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(vo
					.getFileAllName()), Charset.forName(vo.getEncoder())));
			
			
			if (isVerf.equals(vo.getIsVerf())) {
				verfWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(vo
						.getFileAllName().replace(FileSuffixKey.TXT.getFileSuffix(), FileSuffixKey.VERF.getFileSuffix())), Charset.forName(vo.getEncoder())));
			}
			
		}
		catch (IOException e)
		{
			throw new BOException("�����ļ��쳣 fileName=" + vo.getFileAllName(), e);
		}
	}
	
	@Override
	public String writerFile(List<String[]> dataList) throws BOException
	{
		// �������ݼ���
		Iterator<String[]> it = dataList.iterator();
		
		try
		{
			while (it.hasNext())
			{
				String[] temp = it.next();
				String str = objectToString(temp);
				writer.write(str);
				// /r/n
				writer.write(13);
				writer.write(10);

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
			//throw new BOException("д�������ļ��쳣 fileName=" + vo.getFileAllName(), e);
		}
		return null;
	}
	
	/**
	 * ������Ҫ��д��txt�С����������¸Ķ�
	 * 
	 * @return
	 */
	private String objectToString(Object[] obj)
	{
		StringBuffer sb = new StringBuffer();
		
		for (int j = 0; j < obj.length; j++)
		{
			sb.append(this.checkFileldNull(obj[j]));
			
			// ����ָ���
			if (j == obj.length - 1)
			{}
			else
			{
				sb.append(getSeparatorBy16(vo.getExportTypeOther()));
			}
		}
		return sb.toString();
	}
	
	/**
	 * �õ�ʮ�����Ƽ����
	 * @param separator
	 * @return
	 */
	private Object getSeparatorBy16(String separator)
	{
		if(separator.startsWith("0x"))
		{
			return ( char ) Integer.parseInt(separator.substring(2), 16);
		}
		return separator;
	}
	
	/**
	 * �жϸ��ַ��Ƿ�Ϊnull��
	 * 
	 * @param temp
	 *            ��������ֵ
	 * @return
	 */
	private Object checkFileldNull(Object temp)
	{
		if (temp == null)
		{
			return "";
		}
		else
		{
			return temp;
		}
	}
	
	@Override
	public void closeFile() throws BOException
	{	
		if (null != verfWriter) {
			try {
				verfWriter.close();
			} catch (IOException e) {
				Log.error("fileName=" + vo.getFileAllName(), e);
			}
		}
		if (writer != null)
		{
			try
			{
				writer.close();
				
			}
			catch (IOException e)
			{
				Log.error("fileName=" + vo.getFileAllName(), e);
				throw new BOException("fileName=" + vo.getFileAllName(), e);
			}
		}
	}

	@Override
	public void createVeriFile(int size) throws BOException {
		//дУ���ļ�
		if (isVerf.equals(vo.getIsVerf())) {
			long fileSize = 0;
			try {
				File targetFile = new File(vo
						.getFileAllName());
				fileSize = targetFile.length();
				String str =targetFile.getName()+"|"+ fileSize+"|"+size+"|"+DateUtil.formatDate(new Date(), "yyyyMMdd")+"|"+DateUtil.formatDate(new Date(), "yyyyMMddHHmmss")+"|"+getSeparatorBy16("0x0D0A");
				verfWriter.write(str);
			} catch (Exception e) {
				Log.error("д��У���ļ��쳣",e);
			}
		}
		
	}
}
