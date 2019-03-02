package com.aspire.dotcard.syncAndroid.dc.jms;

import java.util.HashMap;
import java.util.Map;


/**
 * ����Ӧ�����ݱ��֪ͨ�ӿ�
 * @author aiyan
 * ContentType	����	String	�������� APP��Ӧ�� RESOURCE����������
 * Contentid	����	String	��ΪAPPʱ������ID ��ΪRESOURCEʱ����ԴID
 * Action	����	String	0��������1��Ӧ����Ϣ�����������չ�ֶΣ�2�������ϵ�������TypeΪRESOURCEʱ�Ż���ڣ�9������
 * Transactionid	����	String	��������ID�����ڴ���ͬһ��������ʱʹ�á�
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
