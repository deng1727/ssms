package com.aspire.ponaadmin.web.excel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.category.export.ftp.FTPUtil;

import jxl.Cell;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * @author zhanggaojing
 *
 */
public class ExcelComponent {
	/**
	 * ��־����
	 */
	private static final JLogger logger = LoggerFactory.getLogger(ExcelComponent.class);

	/**
	 * ÿ֡�������������������⣩����ܳ���6W
	 */
	static int ROWS_PER_SHEET = 20001;
	
	/**
	 * ֡��
	 */
	static String SHEET_NAME = "sheet";
	
	/**
	 * @param srcPath excelģ���ļ���ַ
	 * @param targetPath excel����ļ���ַ
	 * @param elements	��Ҫ��䵽excel�е�Ԫ���б�
	 * @throws ExcelException 
	 */
	public static String excelExport(String srcPath,String targetPath,List elements) throws ExcelException{
		
		Workbook src = null;
		WritableWorkbook target = null;
		 
		//ģ���ļ���ͷ
		Cell[] title = null;
		
		try {
			src = Workbook.getWorkbook(new File(srcPath));
			title = src.getSheet(0).getRow(0);
			if(title == null){
				logger.error("ģ��EXCEL����Ϊ�գ�");
				throw new ExcelException("ģ��EXCEL����Ϊ��");
			}
		} catch (BiffException e) {
			logger.error("ԭʼ�ļ�����Excel��", e);
			throw new ExcelException("ԭʼ�ļ�����Excel",e);
		} catch (IOException e) {
			logger.error("��ȡExcel�ļ�IO�쳣��",e);
			throw new ExcelException("��ȡExcel�ļ�IO�쳣",e);
		} 
		// ����֡��
		int sheetNum = getNumOfSheet(elements.size());
		// ���sheetNum = 1 ˵��ֻ���ܼ�¼С��2W����ȫ�Ž�ȥ����
		try {
			target = Workbook.createWorkbook(new File(targetPath));
			if (sheetNum < 2) {
				WritableSheet sheet = target.createSheet(SHEET_NAME, 0);
				excelExport(title, sheet, elements);
			} else {
				for (int i = 1; i <= sheetNum; i++) {
					WritableSheet sheet = target.createSheet(SHEET_NAME + i, i);
					if (i != sheetNum)
						excelExport(title, sheet, elements.subList((i - 1)
								* ROWS_PER_SHEET, i * ROWS_PER_SHEET - 1));
					else
						excelExport(title, sheet, elements.subList((i - 1)
								* ROWS_PER_SHEET, elements.size()));
				}
			}
			
			target.write();
			
		} catch (RowsExceededException e) {
			// ֻҪROWS_PER_SHEET<6W���Ͳ����ܵ����������ĺ��ˡ�
		} catch (Exception e) {
			logger.error("дEXCEL�ļ����쳣��", e);
			throw new ExcelException("дEXCEL�ļ����쳣", e);
		}finally{ 
			try {
				if(src!=null)
					src.close();
				if(target!=null)
					target.close();
			} catch (IOException e) {
				logger.error("Excelд��д��ʱ�ر���ʱ��������ԭ��" + e.getMessage(), e);
			} 
		}
		return targetPath;
	}
	
	/**
	 * @param total
	 * @return ������������������֡�� 
	 */
	private static int getNumOfSheet(int total){
		return total/ROWS_PER_SHEET + 1 ;
	}
	
	/**
	 * д�뵥��֡
	 * @param title
	 * @param sheet
	 * @param elements
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	private static void excelExport(Cell[] title,WritableSheet  sheet,List  elements) throws RowsExceededException, WriteException{
	
		ArrayList  arrayList = null;
		String tmpStr = null;
		//���ϱ���
		for(int i=0;i<title.length;i++)
			sheet.addCell(new Label(i, 0,title[i].getContents(),title[i].getCellFormat()));
		//���Ԫ��
		for (int i=0;i<elements.size();i++){
			//int j = 0;
			arrayList =  ((ExcelElementTemplate)elements.get(i)).getExcelElement();
			for(int k= 0 ; k<arrayList.size(); k++){
				 tmpStr = (String)arrayList.get(k);
				sheet.addCell(new Label(k, i+1, tmpStr));
			}
//			while((tmpStr = (String) arrayList.poll())!=null){
//				sheet.addCell(new Label(j, i+1, tmpStr));
//				j++;
//			}
		} 
	}
	
//	public static void main(String [] args) throws Exception{
//		ExcelTemplateDemo demo1 = new ExcelTemplateDemo();
//		demo1.setFirst("123");
//		demo1.setSecond("456");
//		demo1.setThird("789");
//		List ll = new ArrayList();
//		for(int i=0;i<50000;i++)
//			ll.add(demo1);
//		 
//	String targetPath=	ExcelComponent.excelExport("C:/template.xls", "C:/test1.xls", ll);
//		
//		FTPUtil fu = new FTPUtil("10.1.3.201",21,"max","1qazZAQ!");			
//		fu.putFiles("temp/ssms",targetPath,"test1.xls");
//	}
	
}
