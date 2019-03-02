package com.aspire.dotcard.syncAndroid.dc.jms;

import java.util.HashMap;
import java.util.Map;

/**
 * �����ݸ��½ӿ�
 * @author aiyan
 * Contentid	����	String	Ӧ��ID
 * Count	���� 	Int	��λʱ���ڵ�����������
 * Action	����	String	0��������ֱ�Ӹ� 1���������� 9��ɾ��
 * Transactionid	����	String	��������ID�����ڴ���ͬһ��������ʱʹ�á�
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
