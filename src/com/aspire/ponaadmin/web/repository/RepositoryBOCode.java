package com.aspire.ponaadmin.web.repository ;

/**
 * <p>��Դ�����ҵ�������붨����</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class RepositoryBOCode
{

    /**
     * �ɹ�
     */
    public static final int SUCC = 0 ;

    /**
     * ϵͳ�ڲ�����
     */
    public static final int FAIL = 1 ;

    /**
     * �����Ƿ�
     */
    public static final int INVALID_PARA = 2 ;

    /**
     * ���������Ѿ���ͬ���ķ������
     */
    public static final int CATEGORY_NAME_EXISTED = 1001;

    /**
     * �����´�����Դ���ӷ��࣬����ɾ��
     */
    public static final int CATEGORY_CONTENT_EXISTED = 1002;

    /**
     * ��Դ�����ã�����ɾ��
     */
    public static final int CONTENT_REFERENCE = 1003;

    
    /**
     * ����˸����ܲ��������Ż���
     */
    public static final int CATEGORY_RELATION_PARERR = 1005;
    
    /**
     * ɾ�����ӻ��ܰ������Ż���
     */
    public static final int CATEGORY_RELATION_SUBERR = 1006;
    
    /**
     * ����Ԥ��ͼ�ϴ�ʧ��
     */
    public static final int CATEGORY_CATE_PIC_UPLOAD=1010;
    
    /**
     * ���ܹ������͹�ϵʱʧ��
     */
    public static final int CATEGORY_DEVICE=1020;
    
    /**
     * ɾ�����ܹ������͹�ϵʱʧ��
     */
    public static final int DEL_CATEGORY_DEVICE=1021;
    
    /**
     * ������ܵ���������ϢʱС���ӻ��ܵ�����Ϣ���϶�ʧ��
     */
    public static final int UPDATE_CATEGORY_CITY=1022;
    
    /**
     * �������ܵ���������Ϣʱ���ڸ����ܵ�����Ϣ���϶�ʧ��
     */
    public static final int ADD_CATEGORY_CITY=1023;
    
    /**
     * �������ܵ���������Ϣʱ���ڸ����ܵ�����Ϣ���϶�ʧ��
     */
    public static final int ADD_CATEGORY_PIC=1024;
    
    /**
     * �������ܵ���������Ϣʱ���ڸ����ܵ�����Ϣ���϶�ʧ��
     */
    public static final int UPDATE_CATEGORY_PIC=1025;
    
    /**
     * �˻���������ͬ���������Ѵ���
     */
    public static final int ADD_UPDATE_CATEGORY_NAME=1026;
    
}
