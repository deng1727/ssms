package com.aspire.dotcard.syncAndroid.dc.jms;

import java.util.HashMap;
import java.util.Map;

/**
 * 3.1.3 ���¼���Ϣ����ӿ�
 * @author aiyan
 * 
 * Catogoryid	����	String	����ID
 * Contentid	����	String	Ӧ��ID
 * Action	����	String	0���½� 9��ɾ��
 * Transactionid	����	String	��������ID�����ڴ���ͬһ��������ʱʹ�á�
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
