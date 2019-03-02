package com.aspire.ponaadmin.web.datasync;

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
	 * @throws Exception
	 */
	int dealDataRecrod(DataRecord record) throws Exception;
	/**
	 * �������ͬ�����������õ����ݣ���֤�´�����ʱdataDealer��ֵ�������״̬��
	 */
	void clearDirtyData();

}
