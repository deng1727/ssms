package com.aspire.ponaadmin.web.excel;

import java.util.ArrayList;
import java.util.LinkedList;

public class ExcelTemplateDemo implements ExcelElementTemplate{

	private String first;
	private String second;
	private String third;
	
	public String getFirst() {
		return first == null ? "" : first;
	}
	public void setFirst(String first) {
		this.first = first;
	}
	public String getSecond() {
		return second == null ? "" : second;
	}
	public void setSecond(String second) {
		this.second = second;
	}
	public String getThird() {
		return third == null ? "" : third;
	}
	public void setThird(String third) {
		this.third = third;
	}
 
	//Excel组件从这里取值
	public ArrayList getExcelElement() {
		ArrayList queue = new ArrayList();
		//压入队列 FIFO
		queue.add(getFirst());
		queue.add(getSecond());
		queue.add(getThird());
		return queue;
	}

}
