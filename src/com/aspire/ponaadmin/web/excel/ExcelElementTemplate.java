package com.aspire.ponaadmin.web.excel;

import java.util.ArrayList;

/**
 * @author zhanggaojing
 *
 */
public interface ExcelElementTemplate {
	 
	/**
	 * 返回一个队列（FIFO原则）。保证LinkedList中的String不为null，可以为空串
	 * @return
	 */
	public ArrayList  getExcelElement() ;
	
}
