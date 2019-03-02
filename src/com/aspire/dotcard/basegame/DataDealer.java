package com.aspire.dotcard.basegame;

import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncBuilder;


public interface DataDealer extends DataSyncBuilder
{
	/**
	 * ��������ͬ��ǰ����Ҫ�����ݵ�׼��������
	 */
	void prepareData()throws Exception;
	/**
	 * �������ݵ�����ϵͳ��
	 * @param record
	 * @return ����ֵΪ������1��ʾ������2��ʾ���£�3��ʾɾ��
	 */
	int dealDataRecrod(DataRecord record);
	/**
	 * �������ͬ�����������õ����ݣ���֤�´�����ʱdataDealer��ֵ�������״̬��
	 */
	void clearDirtyData();
	
	/**Ϊ��֧��ɾ�������ݡ�add by aiyan 2012-09-05
	 * @throws Exception */
	void delOldData() throws Exception;

}
