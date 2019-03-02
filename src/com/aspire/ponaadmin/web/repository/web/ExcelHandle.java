package com.aspire.ponaadmin.web.repository.web;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.upload.FormFile;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class ExcelHandle {
	/**
	 * 日志引用
	 */
	private static final JLogger LOG = LoggerFactory
			.getLogger(ExcelHandle.class);


	/**
	 * 
	 * @param dataFile
	 * @param convert 如果不想转换，可以把这个值为空
	 * @return
	 * @throws BOException
	 */
	public List paraseDataFile(FormFile dataFile,ExcelRowConvert convert) throws BOException {
		LOG.info("paraseDataFile is start!");
		List list = new ArrayList();

		String fileName = dataFile.getFileName();

		org.apache.poi.ss.usermodel.Workbook book = null;
		try {
			InputStream stream = dataFile.getInputStream();
			if (fileName.endsWith("xls")) {
				book = new HSSFWorkbook(stream);
			} else if (fileName.endsWith("xlsx")) {
				book = new XSSFWorkbook(stream);
			}

			int sheetNum = book.getNumberOfSheets();
			if (LOG.isDebugEnabled()) {
				LOG.debug("paraseSoftVersion.sheetNum==" + sheetNum);
			}

			for (int i = 0; i < sheetNum; i++) {
				org.apache.poi.ss.usermodel.Sheet sheet = book.getSheetAt(i);
				int rows = sheet.getLastRowNum();
				org.apache.poi.ss.usermodel.Row row = sheet.getRow(0);
				if (row == null) {
					LOG.error("sheet(" + i + ")是空的！");
					continue;
				}
				int cells =  row.getLastCellNum();
				for (int j = 0; j <= rows; j++) {
					Map m = new HashMap();
					row = sheet.getRow(j);
					for (int k = 0; k < cells; k++) {
						org.apache.poi.ss.usermodel.Cell cell = row.getCell(k);
						String value = null;
						if(cell == null){
							value = "";
						}else{
							value = getCellValue(cell);
						}			
						m.put(k, value);
					}
					if(convert == null){
						list.add(m);
					}else{
						try{
							list.add(convert.convertData(m));
						}catch(Exception e){
							LOG.error("转换EXCEL的行数据出错，文件名："+fileName+",行数:"+j);
						}
						
					}
					
				}
			}

		} catch (Exception e) {
			LOG.error("解析导入文件出现异常,fineName:" + dataFile.getFileName(), e);
			throw new BOException("解析导入文件出现异常", e);
		}
		return list;

	}

	//判断从Excel文件中解析出来数据的格式  
	private static String getCellValue(Cell cell) {
		String value = null;
		//简单的查检列类型  
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING://字符串  
			value = cell.getRichStringCellValue().getString();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC://数字  
			long dd = (long) cell.getNumericCellValue();
			value = dd + "";
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			value = "";
			break;
		default:
			break;
		}
		return value;
	}
	
	
	public List paraseDataMap(String[] contentids,ExcelRowConvert convert ) throws BOException{

		LOG.info("paraseDataFile is start!");
		List list = new ArrayList();
		try {
			int k = 1 ;
			for (int i = 0; i < contentids.length; i++) {
					Map m = new HashMap();
					m.put(1, contentids[i]);
					m.put(0, k);
					
					if(convert == null){
						list.add(m);
					}else{
						try{
							list.add(convert.convertData(m));
						}catch(Exception e){
							LOG.debug(e);
						}
					}
				}
			}
		catch (Exception e) {
			throw new BOException("解析contentids", e);
		}
		return list;

	
		
		
	}
}
