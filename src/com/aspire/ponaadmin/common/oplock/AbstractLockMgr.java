/*
 * @(#)AbstractLockMgr.java        1.0 2005-7-8
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

import java.util.Map;

/**
 * ��������������������ɶ��������У�顢��������������
 * 
 * @author Fan Jingjian
 * 
 */
public abstract class AbstractLockMgr
{
    /**
     * ����ģ���������
     * 
     * @param model
     *            ����������
     * @exception LockException
     *                ���ָ�������������ڡ�
     */
    public abstract void releaseLock(AbstractLockModel model) throws LockException;

    /**
     * ����ģ���Ƿ��Ѿ�������
     * 
     * @param model
     *            ����������
     * @return True ������ False������
     */
    public abstract boolean isLocked(AbstractLockModel model);

    /**
     * ����ģ���������
     * 
     * @param model
     *            ����������
     * @exception LockException
     *                ���ָ���������Ѿ����ڡ�
     */
    public void requireLock(AbstractLockModel model) throws LockException
    {
        requireLock(model, AbstractLockModel.LOCK_ADD);
    }
    
    /**
     * ����ģ���������
     * 
     * @param model
     *            ����������
     * @param lockType
     *            ���������ͣ�ȡֵ��ΧΪLockInterface.LOCK_ADD��AbstractLockModel.LOCK_EDIT��AbstractLockModel.LOCK_DEL��AbstractLockModel.UNLOCK��
     * @exception LockException
     *                ���ָ���������Ѿ����ڡ�
     */
    public abstract void requireLock(AbstractLockModel model, int lockType)
            throws LockException;
    
    /**
     * ��ȡ���в�����
     * 
     * @return ����������
     */
    public abstract Map getAll();
}
