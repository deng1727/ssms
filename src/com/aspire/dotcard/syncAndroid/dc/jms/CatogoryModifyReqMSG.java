package com.aspire.dotcard.syncAndroid.dc.jms;

import java.util.HashMap;
import java.util.Map;


/**
 * ������Ϣ����ӿ�
 * @author aiyan
 * 
 * Catogoryid	����	String	����ID
 * Action	����	String	0���½� 1������������Ϣ�����������չ�ֶΣ�9��ɾ��
 * Transactionid	����	String	��������ID�����ڴ���ͬһ��������ʱʹ�á�
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
