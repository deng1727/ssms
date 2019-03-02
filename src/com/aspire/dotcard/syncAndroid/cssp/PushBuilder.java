package com.aspire.dotcard.syncAndroid.cssp;

import com.aspire.dotcard.syncAndroid.common.Constant;



public class PushBuilder implements IBuilder{
	public String getPrefix(){
		return "PUSH"; 
	}
	
	public int getFileType(){
		return Constant.CSSP_FILETYPE_PUSH;
	}
	
	public Bean getBean(String line,String fileName) {
		// TODO Auto-generated method stub
    	PushBean bean = new PushBean();
    	String[] arr = line.split((char)31+"");
    	bean.setId(arr[0]);
    	bean.setPushid(arr[23]);
    	bean.setContentid(arr[9]);
    	bean.setUa(arr[10]);
    	bean.setFileName(fileName);
		return bean;
	}
    
	@Override
	public DataDealer getDataDealer() {
		// TODO Auto-generated method stub
		return new PushDealer();
	}




}
