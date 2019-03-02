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
	 * ��¼��־��ʵ������
	 */
	protected static JLogger LOG = LoggerFactory.getLogger(ContentExportBO.class);
	/**
	 * singletonģʽ��ʵ��
	 */
	private static ContentExportBO instance = new ContentExportBO();
	/**
	 * ����excel��������������
	 */
	private int maxSheetRowSize=65535;

	private final static String curDate = getCurDateTime();

	/**
	 * �������ֵ����ļ���
	 */
	private final static String musicFileName = "Music_ExportData_" + curDate + ".xls";

	/**
	 * ����ͼ�鵼���ļ���
	 */
	private final static String bookFileName = "Book_ExportData_" + curDate + ".xls";
	/**
	 * ��ȡʵ��
	 *
	 * @return ʵ��
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
			throw new BOException("�������ݳ���",e);
		}
		WritableWorkbook workbook=null;
		WritableSheet sheet=null;
		int sheetNumber=0;
		try
		{
			 workbook= Workbook.createWorkbook(out);
			 for(int i=0;i<list.size();i++)
			 {
				 int rowNumber=i%maxSheetRowSize;//��ǰsheet��������
				 if(rowNumber==0)//�������sheet����������������˳�򴴽���������sheet
				 {
					 sheet= workbook.createSheet("sheet"+(sheetNumber+1), sheetNumber);
					 sheetNumber++;
					 //���õ�ǰsheet���б��⡣
					 for(int n=0;n<config.getExportColumnNames().size();n++)
					 {
						 ExportItem item=(ExportItem)config.getExportColumnNames().get(n);

				         WritableFont wfc = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, false,
						 UnderlineStyle.SINGLE_ACCOUNTING, jxl.format.Colour.RED);
						 WritableCellFormat wcfFC = new WritableCellFormat(wfc);
						 wcfFC.setAlignment(jxl.format.Alignment.CENTRE);
						 Label  labelCFC = new Label(n, 0,item.getDisplayName() , wcfFC);
						 
						 sheet.getSettings().setVerticalFreeze(1);//��һ�б��ⶳ�ᡣ
						 CellView cv = new CellView();
						 cv.setSize(20 * 256);//�����п�20������
						 sheet.setColumnView(n, cv);
						 sheet.addCell(labelCFC);

					 }
				 }
				 List record=(List)list.get(i);
				 for(int n=0;n<record.size();n++)//д��һ�����ݡ��ӵڶ��п�ʼд��
				 {
					 sheet.addCell(new Label(n,rowNumber+1,record.get(n).toString()));
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
			}catch(SocketException e)
			{
				throw e;
			}catch (IOException e)
			{
				throw new BOException("����excel��������",e);
			}
		}
	}

	/**
	 * ��type�õ�Ҫ�������ļ�������
	 *
	 * @param type
	 *            �������ļ���ʶ
	 * @return �����ļ�������
	 */
	public String getExportFileName(String type)
	{

		String exportFileName = null;
		if (type == null)
		{
			return exportFileName;
		}
		// �������ֵ����ļ���
		if (type.equals("music"))
		{
			exportFileName = musicFileName;
		}
		else if (type.equals("book"))
		{
			// ����ͼ�鵼���ļ���
			exportFileName = bookFileName;
		}
		return exportFileName;
	}

	/**
	 * ��type�õ���Ӧ�ĵ���������
	 *
	 * @param type���������ļ���ʶ
	 * @return ����������
	 */
	public TypeExportConfig getExportConfig(String type)
	{

		TypeExportConfig config = null;
		if (type == null)
		{
			return config;
		}
		// �������ֵ���
		if (type.equals("music"))
		{
			config = new MusicExportConfig();
		}
		else if (type.equals("book"))
		{
			// ����ͼ�鵼��
			config = new ReadExportConfig();
		}
		return config;
	}

	/**
	 * ��type�õ�Ҫ������ʶ��Ϣ
	 *
	 * @param type
	 *            �������ļ���ʶ
	 * @return �����ļ�������
	 */
	public String getExportInfo(String type)
	{

		String exportInfo = null;
		if (type == null)
		{
			return exportInfo;
		}
		// �������ֵ����ļ���
		if (type.equals("music"))
		{
			exportInfo = "�������ֵ���";
		}
		else if (type.equals("book"))
		{
			// ����ͼ�鵼���ļ���
			exportInfo = "����ͼ�鵼��";
		}
		return exportInfo;
	}

	/**
	 * �õ���ǰ�����ַ�
	 *
	 * @return String����ǰ�����ַ�
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
