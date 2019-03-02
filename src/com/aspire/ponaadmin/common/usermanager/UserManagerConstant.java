package com.aspire.ponaadmin.common.usermanager;

/**
 * <p>�û���������ĳ���������</p>
 * <p>�û���������ĳ���������</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class UserManagerConstant
{
    /////��ŵ�session�еĳ�������

    /**
     * ��ŵ�session�е��û���Ϣ�������
     */
    static final String USER_SESSION_KEY = "PONAADMIN_WEB_USERINFO";

    /**
     * ������ɵ�����ͼƬ��ֵ
     */
    static final String IMAGE_CODE = "image_code";

    /////�û�״̬����//////

    /**
     * �û�״̬-����
     */
    public static final int STATE_NORMAL = 10;

    /**
     * �û�״̬-Ԥע��
     */
    public static final int STATE_PRE_REGISTER = 11;

    /**
     * �û�״̬-�����
     */
    public static final int STATE_TO_BE_CHECK = 12;

    /**
     * �û�״̬-��˲�ͨ��
     */
    public static final int STATE_CHECK_FAIL = 13;

    /**
     * �û�״̬-������
     */
    public static final int STATE_LOCKED = 14;

    /**
     * �û�״̬-���뱻��λ
     */
    public static final int STATE_PWD_RESET = 15;

	////// �û��Ա��� ////////

	/**
	 * �Ա�--��
	 */
    public static final String SEX_MALE = "M";

	/**
	 * �Ա�--Ů
	 */
    public static final String SEX_FEMALE = "F";

	////// ֤�����Ͷ��� ///////

	/**
	 * ֤������--���֤
	 */
    public static final String CERT_TYPE_IDCARD = "10";

    ///////////�û�������������   ��ʼ/////////////

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
     * �û�������
     */
    public static final int USER_NOT_EXISTED = 1002 ;

    /**
     * �������
     */
    public static final int INVALID_PWD = 1003 ;

    /**
     * �û���������
     */
    public static final int USER_LOCKED = 1004 ;

    /**
     * ע����˲�ͨ��
     */
    public static final int REGISTER_CHECK_FAILED = 1005 ;

    /**
     * ע������δ���
     */
    public static final int REGISTER_NOT_CHECK = 1006 ;

    /**
     * ���뱻��λ
     */
    public static final int PWD_RESETED = 1007 ;

    /**
     * �û��Ѿ�����
     */
    public static final int USER_EXISTED = 1008 ;
    
    /**
     * ������״̬--����
     */
    public static final String CHANNEL_STATUS_NORMAL = "0";
    /**
     * ������״̬--����
     */
    public static final String CHANNEL_STATUS_DOWN = "1";
    ///////////�û�������������   ����/////////////

}
