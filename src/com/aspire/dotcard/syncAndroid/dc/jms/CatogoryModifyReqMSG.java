package com.aspire.dotcard.syncAndroid.dc.jms;

import java.util.HashMap;
import java.util.Map;


/**
 * 货架信息变更接口
 * @author aiyan
 * 
 * Catogoryid	必须	String	货架ID
 * Action	必须	String	0：新建 1：货架描述信息变更（包含扩展字段）9：删除
 * Transactionid	必须	String	事务序列ID，用于处理同一事务请求时使用。
 *
 */
public class CatogoryModifyReqMSG extends MSG{
	
	public CatogoryModifyReqMSG(String message){
		super(MSGType.CatogoryModifyReq,message);
	}

	@Override
	public Map getData() {
		// TODO Auto-generated method stub
		String[] arr = message.split(":");
		Map data = new HashMap();
		data.put("Type", type.toString());
		data.put("Catogoryid", arr[0]);
		data.put("Action", arr[1]);
		data.put("Transactionid", arr[2]);
		return data;
	}
}
