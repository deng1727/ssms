package com.aspire.ponaadmin.web.datasync.implement.game;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataChecker;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConfig;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.implement.DataCheckerImp;

public class TwGameChecker extends DataCheckerImp implements DataChecker
{
	private static JLogger logger = LoggerFactory.getLogger(TwGameChecker.class);

	public int checkDateRecord(DataRecord record) throws Exception
	{
		//������Ҫ�Ȼ�ԭ�ַ��к��С�0x1F��Ϊ��\n��
		String field;
		for(int i=1;i<=record.size();i++)
		{
			field=revertCarriageReturn((String)record.get(i));
			record.put(i, field);
		}
        
		// cpid
		String tmp=(String)record.get(1);
		String cpid=tmp;
		
		logger.info("��ʼ��֤ͼ����Ϸ�ֶθ�ʽ��cpid="+cpid);
			
		if(record.size()!=20)
		{
			logger.error("�ֶ���������20,�������ݵ��ֶ���Ϊ��"+record.size());
			return DataSyncConstants.CHECK_FAILED;
		}
        
		// 1 cpid
        tmp=(String)record.get(1);
        if(!this.checkFieldLength("��Ʒ������CP", tmp, 16, true))
        {
            return DataSyncConstants.CHECK_FAILED;
        }
		//2 cpName
		tmp=(String)record.get(2);
		if(!this.checkFieldLength("cp����", tmp, 200, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		//3 cpServiceId
		tmp=(String)record.get(3);
		if(!this.checkFieldLength("��Ʒ��ҵ�����", tmp, 16, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		//4 serviceName
		tmp=(String)record.get(4);
		if(!this.checkFieldLength("��Ʒ����", tmp, 400, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		//5 serviceShotName
		tmp=(String)record.get(5);
		if(!this.checkFieldLength("��Ʒ���", tmp, 400, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		//6 serviceDesc
		tmp=(String)record.get(6);
		if(!this.checkFieldLength("��Ʒ���", tmp, 1000, false))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		//7 operationDesc
		tmp=(String)record.get(7);
		if(!this.checkFieldLength("�������", tmp, 1000, false))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		//8 ҵ������
		tmp=(String)record.get(8);
		if(!this.checkIntegerField("ҵ������", tmp, 2, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		//9 ֧����ʽ
		tmp=(String)record.get(9);
		if(!this.checkIntegerField("֧����ʽ",tmp, 2, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		//10 ��Ʒ��Чʱ�䡣
		tmp=(String)record.get(10);
		if(!this.checkFieldLength("��Ʒ��Чʱ��", tmp, 10, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
        //11 ��ƷʧЧ����
        tmp=(String)record.get(11);
        if(!this.checkFieldLength("��ƷʧЧʱ��", tmp, 10, true))
        {
            return DataSyncConstants.CHECK_FAILED;
        }
		//12 ҵ��״̬
		tmp=(String)record.get(12);
		if(!this.checkIntegerField("ҵ��״̬", tmp, 1, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
        // Ŀǰֻ֧��3:�������͵�ҵ��״̬ 
        if(!"3".equals(tmp))
        {
            return DataSyncConstants.CHECK_FAILED;
        }
		//13 �ʷ�
		tmp=(String)record.get(13);
		if(!this.checkIntegerField("�ʷ�", tmp, 10, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		//14 servicefeedesc �ʷ�����
		tmp=(String)record.get(14);
		if(!this.checkFieldLength("�ʷ�����", tmp, 400, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		//15 service_url ͼ����Ϸ�����ӵ�ַ
		tmp=(String)record.get(15);
		if(!this.checkFieldLength("ͼ����Ϸ�����ӵ�ַ", tmp, 400, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		//16 �Ʒѷ�ʽ
		tmp=(String)record.get(16);
		if(!this.checkIntegerField("�Ʒѷ�ʽ", tmp, 2, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
        // Ŀǰֻ֧��0\3\6�������͵ļƷѷ�ʽ  
        if(!"0".equals(tmp) && !"3".equals(tmp) && !"6".equals(tmp))
        {
            return DataSyncConstants.CHECK_FAILED;
        }
		//17 ��Ϸ����
		tmp=(String)record.get(17);
        if(!this.checkIntegerField("��Ϸ����", tmp, 4, true))
        {
            return DataSyncConstants.CHECK_FAILED;
        }
		//18 ��Ϸ��������
		tmp=(String)record.get(18);
		if(!this.checkFieldLength("��Ϸ��������", tmp, 40, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		//19 ҵ���ʶ
		tmp=(String)record.get(19);
        if(!this.checkIntegerField("ҵ���ƹ㷽ʽ", tmp, 1, false))
        {
            return DataSyncConstants.CHECK_FAILED;
        }
        if(!"".equals(tmp) && (!"0".equals(tmp) && !"1".equals(tmp)))
        {
            // ֻ��Ϊ0��Ϊ1��ҵ��ſ��ԡ�
            return DataSyncConstants.CHECK_FAILED;
        }
        
		//20 ҵ���ƹ㷽ʽ
		tmp=(String)record.get(20);
		if(!this.checkIntegerField("ҵ���ƹ㷽ʽ", tmp, 2, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
        
		return DataSyncConstants.CHECK_SUCCESSFUL;
		
	}

	public void init(DataSyncConfig config) throws Exception
	{

	}
	/**
	 * ��ԭ��\n���ַ�. ԭ���ַ��ġ�\n'�Ѿ������ַ�0x1F �ַ��滻��
	 * @param field  carriageReturn 
	 * @return
	 */
	private String revertCarriageReturn(String field)
	{
		return field.replace((char)0x1F, '\n'); 
	}

}
