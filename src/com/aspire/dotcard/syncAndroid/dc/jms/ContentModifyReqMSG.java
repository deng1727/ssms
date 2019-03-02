package com.aspire.dotcard.syncAndroid.dc.jms;

import java.util.HashMap;
import java.util.Map;


/**
 * 货架应用数据变更通知接口
 * @author aiyan
 * ContentType	必须	String	内容类型 APP：应用 RESOURCE：程序包变更
 * Contentid	必须	String	当为APP时是内容ID 当为RESOURCE时是资源ID
 * Action	必须	String	0：新上线1：应用信息变更（包含扩展字段）2：适配关系变更（当Type为RESOURCE时才会存在）9：下线
 * Transactionid	必须	String	事务序列ID，用于处理同一事务请求时使用。
 * 
 *
 */
public class ContentModifyReqMSG extends MSG{
	public ContentModifyReqMSG(String message){
		super(MSGType.ContentModifyReq,message);
	}

	@Override
	public Map getData() {
		// TODO Auto-generated method stub
		String[] arr = message.split(":");
		Map data = new HashMap();
		data.put("Type", type.toString());
		data.put("ContentType", arr[0]);
		data.put("Contentid", arr[1]);
		data.put("Action", arr[2]);
		data.put("Transactionid", arr[3]);
		return data;
	}

}
