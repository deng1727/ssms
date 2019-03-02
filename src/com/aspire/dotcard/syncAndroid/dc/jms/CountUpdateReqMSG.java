package com.aspire.dotcard.syncAndroid.dc.jms;

import java.util.HashMap;
import java.util.Map;

/**
 * 榜单数据更新接口
 * @author aiyan
 * Contentid	必须	String	应用ID
 * Count	必须 	Int	单位时间内的下载增加量
 * Action	必须	String	0：新增或直接更 1：增量增加 9：删除
 * Transactionid	必须	String	事务序列ID，用于处理同一事务请求时使用。
 *
 */
public class CountUpdateReqMSG extends MSG{
	public CountUpdateReqMSG(String message){
		super(MSGType.ContentModifyReq,message);
	}

	@Override
	public Map getData() {
		// TODO Auto-generated method stub
		String[] arr = message.split(":");
		Map data = new HashMap();
		data.put("Type", type.toString());
		data.put("Contentid", arr[0]);
		data.put("Count", arr[1]);
		data.put("Action", arr[2]);
		data.put("Transactionid", arr[3]);
		return data;
	}
}
