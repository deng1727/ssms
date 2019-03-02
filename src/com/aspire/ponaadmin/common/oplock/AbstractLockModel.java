/*
 * @(#)AbstractLockModel.java        1.0 2005-7-8
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
 * ������ģ�ͣ��ǽ������ٵĻ�������
 * 
 * @author Fan Jingjian
 * 
 */
public abstract class AbstractLockModel
{
    /**
     * �����Ϊ��������
     */
    public final static int LOCK_ADD = 1;
    /**
     * �༭��Ϊ��������
     */
    public final static int LOCK_EDIT = 2;
    /**
     * ɾ����Ϊ��������
     */
    public final static int LOCK_DEL = 3;
    /**
     * û�б�����
     */
    public final static int UNLOCK = 0;
    /**
     * ����ʱ��
     */
    protected long lockTime = -1;
    /**
     * ����ʱ��
     */
    protected long releaseTime = -1;
    /**
     * ������������
     */
    String lockKey = null;

    /**
     * ȡ��������ؼ���
     * 
     * @return �ؼ���
     */
    public String getLockKey()
    {
        return lockKey;
    }
}
