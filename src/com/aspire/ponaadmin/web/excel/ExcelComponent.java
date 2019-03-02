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
	 * 日志引用
	 */
	private static final JLogger logger = LoggerFactory.getLogger(ExcelComponent.class);

	/**
	 * 每帧表格最大行数（包含标题）最大不能超过6W
	 */
	static int ROWS_PER_SHEET = 20001;
	
	/**
	 * 帧名
	 */
	static String SHEET_NAME = "sheet";
	
	/**
	 * @param srcPath excel模板文件地址
	 * @param targetPath excel输出文件地址
	 * @param elements	需要填充到excel中的元素列表
	 * @throws ExcelException 
	 */
	public static String excelExport(String srcPath,String targetPath,List elements) throws ExcelException{
		
		Workbook src = null;
		WritableWorkbook target = null;
		 
		//模板文件列头
		Cell[] title = null;
		
		try {
			src = Workbook.getWorkbook(new File(srcPath));
			title = src.getSheet(0).getRow(0);
			if(title == null){
				logger.error("模板EXCEL标题为空！");
				throw new ExcelException("模板EXCEL标题为空");
			}
		} catch (BiffException e) {
			logger.error("原始文件不是Excel！", e);
			throw new ExcelException("原始文件不是Excel",e);
		} catch (IOException e) {
			logger.error("读取Excel文件IO异常！",e);
			throw new ExcelException("读取Excel文件IO异常",e);
		} 
		// 计算帧数
		int sheetNum = getNumOfSheet(elements.size());
		// 如果sheetNum = 1 说明只有总记录小于2W条，全放进去即可
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
			// 只要ROWS_PER_SHEET<6W，就不可能到这来，放心好了。
		} catch (Exception e) {
			logger.error("写EXCEL文件出异常！", e);
			throw new ExcelException("写EXCEL文件出异常", e);
		}finally{ 
			try {
				if(src!=null)
					src.close();
				if(target!=null)
					target.close();
			} catch (IOException e) {
				logger.error("Excel写入写出时关闭流时出错，错误原因：" + e.getMessage(), e);
			} 
		}
		return targetPath;
	}
	
	/**
	 * @param total
	 * @return 根据总数据量返回总帧数 
	 */
	private static int getNumOfSheet(int total){
		return total/ROWS_PER_SHEET + 1 ;
	}
	
	/**
	 * 写入单个帧
	 * @param title
	 * @param sheet
	 * @param elements
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	private static void excelExport(Cell[] title,WritableSheet  sheet,List  elements) throws RowsExceededException, WriteException{
	
		ArrayList  arrayList = null;
		String tmpStr = null;
		//加上标题
		for(int i=0;i<title.length;i++)
			sheet.addCell(new Label(i, 0,title[i].getContents(),title[i].getCellFormat()));
		//添加元素
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
