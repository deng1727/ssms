package com.aspire.dotcard.syncData.util;


public interface SyncDataConstant
{
    //����״̬������
    final String CONTENT_TYPE_RELEASE = "0006";
    
    //����״̬������
    final String CONTENT_TYPE_OVERDUE = "0008";
    
    //��������
    final String CONTENT_TYPE = "content";
    
    //ҵ������
    final String SERVICE_TYPE = "service";
    
    //��Ʒ����
    final String GOODS_CODE = "��Ʒ����:";
    
    //���ܱ���
    final String CATEGORY_CODE = "���ܱ���:";
    
    //��������
    final String CATEGORY_NAME = "��������:";
    
    //���ݱ���
    final String CONTENT_CODE = "���ݱ���:";
    
    //��������
    final String CONTENT_NAME = "��������:";
    
    //��ҵ����
    final String ICPCODE = "��ҵ����:";
    
    //ҵ�����
    final String ICPSERVID = "ҵ�����:";
    
    //ҵ������
    final String SERVICE_NAME = "ҵ������:";
    
    //���з���
    final String CHANGE_LINE = "<br>";
    /**
     * ͬ������
     */
    final int SYNC_UPDATE=1;
    /**
     * ͬ������
     */
    final int SYNC_ADD=2;
    /**
     * ͬ������
     */
    final int SYNC_DEL=3;
    
    /**
     * ����Ӧ��������mmӦ��
     */
    final String DEL_CONTENT_TYPE_MM = "1";
    
    /**
     * ����Ӧ�������Ǵ�ҵ����Ӧ��
     */
    final String DEL_CONTENT_TYPE_CY = "2";
    
    /**
     * ����Ӧ����������Ϸ����
     */
    final String DEL_CONTENT_TYPE_GAME = "3";
    
    /**
     * ����ͬ��ʱ���������ϵ��ͬ��
     */
    final String SYN_RESOURCE_TYPE_NO = "0";
    
    /**
     * ����ͬ��ʱ���������ϵ����ͬ��
     */    
    final String SYN_RESOURCE_TYPE_ADD = "1";
    
    /**
     * ����ͬ��ʱ���������ϵȫ��ͬ��
     */
    final String SYN_RESOURCE_TYPE_ALL = "2";
    
}
