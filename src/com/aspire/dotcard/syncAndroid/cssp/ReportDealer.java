package com.aspire.dotcard.syncAndroid.cssp;


public class ReportDealer implements DataDealer{

	@Override
	public void insert(Object bean) {
		// TODO Auto-generated method stub
		CSSPDAO.getInstance().insertReport((ReportBean)bean);
		
	}


}
