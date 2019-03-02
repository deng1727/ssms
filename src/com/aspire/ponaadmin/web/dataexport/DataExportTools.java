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
     * 日志对象
     */
    protected static JLogger logger = LoggerFactory.getLogger(DataExportTools.class);
  
	
	/**
	 * 
	 * @param list 包含所有数据的列表
	 * @param out 输出流
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
				 int rowNumber=i%maxSheetRowSize;//当前sheet的行数。
				 if(rowNumber==0)//如果大于sheet最大允许的行数，则顺序创建接下来的sheet
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
			throw e;//当用户选择取消的时候会出现这个异常，该异常不算错误。
		}
		catch (Exception e)
		{
			throw new BOException("创建excel文件出错",e);
		} finally
		{
			try
			{
				workbook.write();
				workbook.close();
			}catch(Exception e)
			{
				throw new BOException("保存excel数据有误",e);
			}
		}
	}
	

    /**
     * 将list（里面存放的是String对象）中的内容写到文件file中
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
	 * 写入CSV文件
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
				/*for (int j = 0; j < colunm.size(); j++)// 从第一行开始
				{
					temp[i] = (String) colunm.get(j);// rs.getString(i);
				}*/
				//writer.writeAll(colunm);
				writer.writeNext(temp);
				if (i % 10000 == 0)// 防止占用太多内存，每一万行刷新到文件一下。
				{
					writer.flush();
				}
			}
			writer.close();
		} catch (IOException e)
		{
			throw new BOException("写入文件异常 fileName=" + fileNamePath, e);
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
	 * 写入CSV文件
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
				if (i % 10000 == 0)// 防止占用太多内存，每一万行刷新到文件一下。
				{
					writer.flush();
				}
			}
			writer.close();
		} catch (IOException e) {
			throw new BOException("写入文件异常 fileName=" + fileNamePath, e);
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
	 * 写入CSV文件
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
				if (i % 10000 == 0)// 防止占用太多内存，每一万行刷新到文件一下。
				{
					writer.flush();
				}
			}
			writer.close();
		} catch (IOException e) {
			throw new BOException("写入文件异常 fileName=" + fileNamePath, e);
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
	 * 把blog字段转化为字符串
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
	 * 把约束条件放入sql语句中where子句之后
	 * 
	 * @param sqlCode
	 * @param condition
	 *            约束提交
	 * @return 最重的拼装的sql
	 * @throws DAOException
	 */
	public static String assembleSQL(String sql,String condition) throws DAOException
	{
		if(condition!=null)//需要把约束条件放入where子句的后面第一个位置
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
     * 取格式化后的游戏支持的手机型号列表字符串,在显示内容信息和编译信息的页面中使用  content_info.jsp,content_edit.jsp
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
     * 解析含有日期字符的文件名正则表达式，日期是以~d开始，以~结束。中间"[]"包含数字表示和当日的天数差别。比如[-1]表示昨天<br>
     * 常用的文明名正则表达式为：BOOK_~DyyyyMMdd[-1]~.txt
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
			if (c == '~' && fNameRegex.charAt(i + 1) == 'D')// 只有字符为~D为开头的字符才表示日期
			{
				dStart = true;
				i++;// 需要跳过下一个字符
				continue;
			}
			else if (dStart && c == '~')// 匹配日期特殊字符结束。
			{
				Calendar nowTime=Calendar.getInstance();
				int index=dateCharSequence.indexOf("[");//查看是否有对日期的调整
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

			if (dStart && !dEnd)// 特殊日期字符
			{
				dateCharSequence.append(c);
			}
			else
			// 添加非特殊日期字符
			{
				sb.append(c);
			}
		}
		return sb.toString();
	}
}
