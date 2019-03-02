package com.aspire.dotcard.syncAndroid.cssp;


public class PushReportDealer implements DataDealer{

	@Override
	public void insert(Object bean) {
		// TODO Auto-generated method stub
		CSSPDAO.getInstance().insertPushReport((PushReportBean)bean);
		
	}


}
