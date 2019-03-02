package com.aspire.dotcard.export;

import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import jxl.CellView;
import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class ContentExportBO
{
	/**
	 * 记录日志的实例对象
	 */
	protected static JLogger LOG = LoggerFactory.getLogger(ContentExportBO.class);
	/**
	 * singleton模式的实例
	 */
	private static ContentExportBO instance = new ContentExportBO();
	/**
	 * 定义excel最大允许的行数。
	 */
	private int maxSheetRowSize=65535;

	private final static String curDate = getCurDateTime();

	/**
	 * 基地音乐导出文件名
	 */
	private final static String musicFileName = "Music_ExportData_" + curDate + ".xls";

	/**
	 * 基地图书导出文件名
	 */
	private final static String bookFileName = "Book_ExportData_" + curDate + ".xls";
	/**
	 * 获取实例
	 *
	 * @return 实例
	 */
	public static ContentExportBO getInstance()
	{

		return instance;
	}

	public void ExportDate(TypeExportConfig config,OutputStream out)throws BOException,SocketException
	{
		List list=null;
		try
		{
			list=ContentExportDAO.getInstance().getExportDate(config);
		} catch (DAOException e)
		{
			throw new BOException("导出数据出错",e);
		}
		WritableWorkbook workbook=null;
		WritableSheet sheet=null;
		int sheetNumber=0;
		try
		{
			 workbook= Workbook.createWorkbook(out);
			 for(int i=0;i<list.size();i++)
			 {
				 int rowNumber=i%maxSheetRowSize;//当前sheet的行数。
				 if(rowNumber==0)//如果大于sheet最大允许的行数，则顺序创建接下来的sheet
				 {
					 sheet= workbook.createSheet("sheet"+(sheetNumber+1), sheetNumber);
					 sheetNumber++;
					 //设置当前sheet的列标题。
					 for(int n=0;n<config.getExportColumnNames().size();n++)
					 {
						 ExportItem item=(ExportItem)config.getExportColumnNames().get(n);

				         WritableFont wfc = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, false,
						 UnderlineStyle.SINGLE_ACCOUNTING, jxl.format.Colour.RED);
						 WritableCellFormat wcfFC = new WritableCellFormat(wfc);
						 wcfFC.setAlignment(jxl.format.Alignment.CENTRE);
						 Label  labelCFC = new Label(n, 0,item.getDisplayName() , wcfFC);
						 
						 sheet.getSettings().setVerticalFreeze(1);//第一行标题冻结。
						 CellView cv = new CellView();
						 cv.setSize(20 * 256);//设置列宽20的像素
						 sheet.setColumnView(n, cv);
						 sheet.addCell(labelCFC);

					 }
				 }
				 List record=(List)list.get(i);
				 for(int n=0;n<record.size();n++)//写入一行数据。从第二行开始写起。
				 {
					 sheet.addCell(new Label(n,rowNumber+1,record.get(n).toString()));
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
			}catch(SocketException e)
			{
				throw e;
			}catch (IOException e)
			{
				throw new BOException("保存excel数据有误",e);
			}
		}
	}

	/**
	 * 由type得到要导出的文件的名称
	 *
	 * @param type
	 *            导出的文件标识
	 * @return 导出文件的名称
	 */
	public String getExportFileName(String type)
	{

		String exportFileName = null;
		if (type == null)
		{
			return exportFileName;
		}
		// 基地音乐导出文件名
		if (type.equals("music"))
		{
			exportFileName = musicFileName;
		}
		else if (type.equals("book"))
		{
			// 基地图书导出文件名
			exportFileName = bookFileName;
		}
		return exportFileName;
	}

	/**
	 * 由type得到相应的导出配置项
	 *
	 * @param type，导出的文件标识
	 * @return 导出配置项
	 */
	public TypeExportConfig getExportConfig(String type)
	{

		TypeExportConfig config = null;
		if (type == null)
		{
			return config;
		}
		// 基地音乐导出
		if (type.equals("music"))
		{
			config = new MusicExportConfig();
		}
		else if (type.equals("book"))
		{
			// 基地图书导出
			config = new ReadExportConfig();
		}
		return config;
	}

	/**
	 * 由type得到要导出标识信息
	 *
	 * @param type
	 *            导出的文件标识
	 * @return 导出文件的名称
	 */
	public String getExportInfo(String type)
	{

		String exportInfo = null;
		if (type == null)
		{
			return exportInfo;
		}
		// 基地音乐导出文件名
		if (type.equals("music"))
		{
			exportInfo = "基地音乐导出";
		}
		else if (type.equals("book"))
		{
			// 基地图书导出文件名
			exportInfo = "基地图书导出";
		}
		return exportInfo;
	}

	/**
	 * 得到当前日期字符
	 *
	 * @return String，当前日期字符
	 */
	public static String getCurDateTime()
	{
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		String DATE_FORMAT = "yyyyMMddHHmmss";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		sdf.setTimeZone(TimeZone.getDefault());
		return sdf.format(now.getTime());
	}

}
