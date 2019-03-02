package com.aspire.dotcard.syncAndroid.cssp;

import com.aspire.dotcard.syncAndroid.common.Constant;

public class PushReportBuilder implements IBuilder{
	
	public String getPrefix(){
		return "PUSHREPORT"; 
	}
	
	public int getFileType(){
		return Constant.CSSP_FILETYPE_PUSHREPORT;
	}
	
	public Bean getBean(String line,String fileName) {
		// TODO Auto-generated method stub
    	PushReportBean bean = new PushReportBean();
    	String[] arr = line.split((char)31+"");
    	bean.setId(arr[0]);
    	bean.setPushid(arr[1]);
    	bean.setStatus(arr[2]);
    	bean.setRecordtime(arr[3]);
    	bean.setFileName(fileName);
		return bean;
	}
    

	public DataDealer getDataDealer() {
		// TODO Auto-generated method stub
		return new PushReportDealer();
	}

}
