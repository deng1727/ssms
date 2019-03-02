package com.aspire.dotcard.syncAndroid.dc.jms;

import java.util.HashMap;
import java.util.Map;

/**
 * �����ύ�ӿ�
 * @author aiyan
 *	Action	����	String	0����ʼִ��9������������
 *	Transactionid	����	String	��������ID�����ڴ���ͬһ��������ʱʹ�á�
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
