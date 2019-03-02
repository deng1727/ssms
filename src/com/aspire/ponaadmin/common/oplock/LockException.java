/*
 * @(#)LockException.java        1.0 2005-7-8
 *
 * Copyright (c) 2003-2005 ASPire Technologies, Inc.
 * 6/F,IER BUILDING, SOUTH AREA,SHENZHEN HI-TECH INDUSTRIAL PARK Mail Box:11# 12#.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * ASPire Technologies, Inc. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Aspire.
 */

package com.aspire.ponaadmin.common.oplock;

/**
 * �������쳣
 * 
 * @author Fan Jingjian
 * 
 */
public class LockException extends Throwable
{
    /**
     * ָ���������Ѿ�����
     */
    public static final int LOCK_EXIST = 1;
    /**
     * δ����ָ��������
     */
    public static final int LOCK_NOT_FOUND = 2;
    /**
     * �����쳣����
     */
    public static final int OTHER_EXCEPTION = -1;
    /**
     * �쳣�Ĵ�������
     */
    private int errorCode = -1;

    /**
     * ����һ���µ��쳣ʵ��
     * 
     * @param errorCode
     *            �쳣�Ĵ������ͣ�ȡֵ��ΧΪ
     */
    public LockException(int errorCode)
    {
        this.errorCode = errorCode;
    }

    /**
     * ��ȡ�쳣�Ĵ�������
     * 
     * @return �쳣�Ĵ�������
     */
    public int getErrorCode()
    {
        return errorCode;
    }

    public String getMessage()
    {
        return errorCode + "";
    }

}
