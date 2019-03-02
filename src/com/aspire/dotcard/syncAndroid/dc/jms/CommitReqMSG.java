package com.aspire.dotcard.syncAndroid.dc.jms;

import java.util.HashMap;
import java.util.Map;

/**
 * 事务提交接口
 * @author aiyan
 *	Action	必须	String	0：开始执行9：撤销该事务
 *	Transactionid	必须	String	事务序列ID，用于处理同一事务请求时使用。
 *
 */
public class CommitReqMSG extends MSG{
	public CommitReqMSG(String message){
		super(MSGType.CommitReq,message);
	}

	@Override
	public Map getData() {
		// TODO Auto-generated method stub
		String[] arr = message.split(":");
		Map data = new HashMap();
		data.put("Type", type.toString());
		data.put("Action", arr[0]);
		data.put("Transactionid", arr[1]);
		return data;
	}
}
