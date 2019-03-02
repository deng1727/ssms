package com.aspire.dotcard.syncAndroid.cssp;


public class PushDealer implements DataDealer{

	@Override
	public void insert(Object bean) {
		// TODO Auto-generated method stub
		CSSPDAO.getInstance().insertPush((PushBean)bean);
		
	}


}
