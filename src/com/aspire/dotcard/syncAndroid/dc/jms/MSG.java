package com.aspire.dotcard.syncAndroid.dc.jms;

import java.util.Map;

public abstract class MSG {
	MSGType type;
	String message;
	public MSG(MSGType type,String message){
		this.type=type;
		this.message=message;
	}
	public abstract Map getData();

}
