package com.aspire.dotcard.syncAndroid.cssp;


public interface IBuilder {
	 public String getPrefix();
	 //public int getFileType();
	 public Bean getBean(String line,String fileName);
	 public DataDealer getDataDealer();
}
