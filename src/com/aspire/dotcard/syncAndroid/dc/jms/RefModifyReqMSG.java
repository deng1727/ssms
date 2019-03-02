package com.aspire.dotcard.syncAndroid.dc.jms;

import java.util.HashMap;
import java.util.Map;

/**
 * 3.1.3 上下架信息变更接口
 * @author aiyan
 * 
 * Catogoryid	必须	String	货架ID
 * Contentid	必须	String	应用ID
 * Action	必须	String	0：新建 9：删除
 * Transactionid	必须	String	事务序列ID，用于处理同一事务请求时使用。
 *
 */
public class RefModifyReqMSG extends MSG{
	public RefModifyReqMSG(String message){
		super(MSGType.RefModifyReq,message);
	}

	@Override
	public Map getData() {
		// TODO Auto-generated method stub
		String[] arr = message.split(":");
		Map data = new HashMap();
		data.put("Type", type.toString());
		data.put("Catogoryid", arr[0]);
		data.put("Contentid", arr[1]);
		data.put("Action", arr[2]);
		data.put("Transactionid", arr[3]);
		return data;
	}
}
