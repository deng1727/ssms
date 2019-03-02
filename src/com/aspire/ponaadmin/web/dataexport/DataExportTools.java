package com.aspire.ponaadmin.web.dataexport;


import java.io.File;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import java.io.PrintWriter;
import java.net.SocketException;
import java.sql.Clob;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import au.com.bytecode.opencsv.CSVWriter;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.dataexport.experience.task.ExperienceTimer;
import com.aspire.ponaadmin.web.util.PublicUtil;

public class DataExportTools
{
	
	/**
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(DataExportTools.class);
  
	
	/**
	 * 
	 * @param list �����������ݵ��б�
	 * @param out �����
	 * @throws BOException
	 * @throws SocketException
	 */
	public static void ExportDate(List list,String fileName)throws BOException,SocketException
	{
	    int maxSheetRowSize=65535;

		WritableWorkbook workbook=null;
		WritableSheet sheet=null;
		int sheetNumber=0;
		try
		{
			 workbook= Workbook.createWorkbook(new File(fileName));
			 for(int i=0;i<list.size();i++)
			 {
				 int rowNumber=i%maxSheetRowSize;//��ǰsheet��������
				 if(rowNumber==0)//�������sheet����������������˳�򴴽���������sheet
				 {
					 sheet= workbook.createSheet("sheet"+(sheetNumber+1), sheetNumber);
					 sheetNumber++;
				 }
				 List record=(List)list.get(i);
				 for(int n=0;n<record.size();n++)
				 {
					 sheet.addCell(new Label(n,rowNumber,(String)record.get(n)));
				 }
			 }

		}catch(SocketException e )
		{
			throw e;//���û�ѡ��ȡ����ʱ����������쳣�����쳣�������
		}
		catch (Exception e)
		{
			throw new BOException("����excel�ļ�����",e);
		} finally
		{
			try
			{
				workbook.write();
				workbook.close();
			}catch(Exception e)
			{
				throw new BOException("����excel��������",e);
			}
		}
	}
	

    /**
     * ��list�������ŵ���String�����е�����д���ļ�file��
     * @param fullFileName
     * @param list [String1[string|String],String2[string|String]...]
     * @param lineSeparator  
     * @param encoding  
     * @throws IOException
     */
    public static void writeToTXTFile(String fullFileName, List list,String lineSeparator,String encoding) throws IOException
    {
		BufferedWriter bw = null;
		if (null == lineSeparator || lineSeparator.equals(""))
		{
			lineSeparator = "\r\n";
		}
		if (null == encoding || encoding.equals(""))
		{
			lineSeparator = "UTF-8";
		}
	
		FileOutputStream fos = new FileOutputStream(fullFileName);
		OutputStreamWriter osw = new OutputStreamWriter(fos, encoding);
		bw = new BufferedWriter(osw);
		String text = "";
			for (int i = 0; i < list.size(); i++)
			{
				text = (String) list.get(i);
				bw.write(text);
				bw.write(13);// /r	
				bw.write(10); // /n
				
				
			}
			
			/*bw = new BufferedWriter(new FileWriter(new File(fullFileName), true), 8048);
	        String text = "";
	        for (int i = 0; i < list.size(); i++)
	        {
	            text = ( String ) list.get(i);
	            bw.write(text);
	           bw.write("\r\n");
	            //bw.write(10); // /n
				//bw.write(13);// /r
				System.out.println(text);	
	        }*/
	        

		bw.flush();
		if (bw != null)
		{
			bw.close();
		}
	}
  /**
	 * д��CSV�ļ�
	 * 
	 * @param fileNamePath
	 * @param list
	 *            [String[string1,string2],String[string1,string2]]
	 * @param colunmeSize
	 * @throws BOException
	 */
    public static void writeToCSVFile( String fileNamePath, List list
                           ) throws BOException
    {

		CSVWriter writer = null;
		try
		{
			writer = new CSVWriter(new FileWriter(fileNamePath));
			
			//int lineNumber = 0;
			
			for (int i = 0; i < list.size(); i++)
			{   
				
				// String id = rs.getString(1);
			//List colunm = (List) list.get(i);
				String temp[] = (String[])list.get(i);
				//String temp[] = new String[colunm.size()];
				/*for (int j = 0; j < colunm.size(); j++)// �ӵ�һ�п�ʼ
				{
					temp[i] = (String) colunm.get(j);// rs.getString(i);
				}*/
				//writer.writeAll(colunm);
				writer.writeNext(temp);
				if (i % 10000 == 0)// ��ֹռ��̫���ڴ棬ÿһ����ˢ�µ��ļ�һ�¡�
				{
					writer.flush();
				}
			}
			writer.close();
		} catch (IOException e)
		{
			throw new BOException("д���ļ��쳣 fileName=" + fileNamePath, e);
		} catch (Throwable e)
		{
			throw new BOException("fileName=" + fileNamePath, e);
		} finally
		{
			try
			{
				if (writer != null)
				{
					writer.close();
				}
			} catch (Exception e1)
			{

			}
		}
	}
    /**
	 * д��CSV�ļ�
	 * 
	 * @param fileNamePath
	 * @param list
	 *            [String[string1,string2],String[string1,string2]]
	 * @param colunmeSize
	 * @throws BOException
	 */
	public static void writeToCSVFile(String fileNamePath, List list,
			char sep) throws BOException {

		CSVWriter writer = null;
		try {
			writer = new CSVWriter(new FileWriter(fileNamePath),sep);
			for (int i = 0; i < list.size(); i++) {
				String temp[] = (String[]) list.get(i);
				writer.writeNext(temp);
				if (i % 10000 == 0)// ��ֹռ��̫���ڴ棬ÿһ����ˢ�µ��ļ�һ�¡�
				{
					writer.flush();
				}
			}
			writer.close();
		} catch (IOException e) {
			throw new BOException("д���ļ��쳣 fileName=" + fileNamePath, e);
		} catch (Throwable e) {
			throw new BOException("fileName=" + fileNamePath, e);
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (Exception e1) {

			}
		}
	} 
	/**
	 * д��CSV�ļ�
	 * 
	 * @param fileNamePath
	 * @param list
	 *            [String[string1,string2],String[string1,string2]]
	 * @param colunmeSize
	 * @throws BOException
	 */
	public static void writeToCSVFile(String fileNamePath, List list,
			String enCoding) throws BOException {

		CSVWriter writer = null;
		try {
			writer = new CSVWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileNamePath),enCoding)));
			for (int i = 0; i < list.size(); i++) {
				String temp[] = (String[]) list.get(i);
				writer.writeNext(temp);
				if (i % 10000 == 0)// ��ֹռ��̫���ڴ棬ÿһ����ˢ�µ��ļ�һ�¡�
				{
					writer.flush();
				}
			}
			writer.close();
		} catch (IOException e) {
			throw new BOException("д���ļ��쳣 fileName=" + fileNamePath, e);
		} catch (Throwable e) {
			throw new BOException("fileName=" + fileNamePath, e);
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (Exception e1) {

			}
		}
	}    
	/**
	 * ��blog�ֶ�ת��Ϊ�ַ���
	 * 
	 * @param clob
	 * @return
	 * @throws SQLException
	 */
	public static String getClobString(Clob clob)throws SQLException
    {
    	if(clob==null)
    	{
    		return "";
    	}
    	long len =clob.length();
  	    return clob.getSubString(0,(int)len);
    }
	/**
	 * 
	 * ��Լ����������sql�����where�Ӿ�֮��
	 * 
	 * @param sqlCode
	 * @param condition
	 *            Լ���ύ
	 * @return ���ص�ƴװ��sql
	 * @throws DAOException
	 */
	public static String assembleSQL(String sql,String condition) throws DAOException
	{
		if(condition!=null)//��Ҫ��Լ����������where�Ӿ�ĺ����һ��λ��
		{
			String temp=sql.toLowerCase();
			int whereIndex=temp.indexOf("where");
			if(whereIndex==-1)
			{
				sql+=sql+" where "+ condition;
			}else
			{
				sql=sql.substring(0, whereIndex)+"where " +condition+" and "+sql.substring(whereIndex+5);
			}
		}
		return sql;
	}
	 /**
     * ȡ��ʽ�������Ϸ֧�ֵ��ֻ��ͺ��б��ַ���,����ʾ������Ϣ�ͱ�����Ϣ��ҳ����ʹ��  content_info.jsp,content_edit.jsp
     * @return
     * @throws BOException
     */
    public static String[] getDeviceNameToArray(String deviceName)
    {
        String deviceNameItem = "";      
        String[] vDeviceName = deviceName.split(",");
        for(int i = 0; i < vDeviceName.length; i++)
        {
        	deviceNameItem = vDeviceName[i];
        	try
			{
				vDeviceName[i] = deviceNameItem.substring(deviceNameItem.indexOf("{")+1,deviceNameItem.lastIndexOf("}"));
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return vDeviceName;
    }
    
    /**
     * �������������ַ����ļ���������ʽ����������~d��ʼ����~�������м�"[]"�������ֱ�ʾ�͵��յ�������𡣱���[-1]��ʾ����<br>
     * ���õ�������������ʽΪ��BOOK_~DyyyyMMdd[-1]~.txt
     * @param fNameRegex 
     * @return
     */
    public static  String parseFileName(String fNameRegex)
	{
		if (fNameRegex == null)
		{
			return "";//
		}
		StringBuffer dateCharSequence = new StringBuffer();
		boolean dStart = false;
		boolean dEnd = false;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < fNameRegex.length(); i++)
		{
			char c = fNameRegex.charAt(i);
			if (c == '~' && fNameRegex.charAt(i + 1) == 'D')// ֻ���ַ�Ϊ~DΪ��ͷ���ַ��ű�ʾ����
			{
				dStart = true;
				i++;// ��Ҫ������һ���ַ�
				continue;
			}
			else if (dStart && c == '~')// ƥ�����������ַ�������
			{
				Calendar nowTime=Calendar.getInstance();
				int index=dateCharSequence.indexOf("[");//�鿴�Ƿ��ж����ڵĵ���
				if(index!=-1)
				{
					int difference=Integer.parseInt(dateCharSequence.subSequence(index+1, dateCharSequence.lastIndexOf("]")).toString());
					nowTime.add(Calendar.DAY_OF_MONTH,difference);
					dateCharSequence.delete(index, dateCharSequence.length());
				}
				String date = PublicUtil.getDateString(nowTime.getTime(), dateCharSequence.toString());
				//getCurDateTime(dateCharSequence.toString());

				sb.append(date);
				dEnd = true;
				continue;
			}

			if (dStart && !dEnd)// ���������ַ�
			{
				dateCharSequence.append(c);
			}
			else
			// ��ӷ����������ַ�
			{
				sb.append(c);
			}
		}
		return sb.toString();
	}
}
