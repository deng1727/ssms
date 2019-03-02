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
 * 锁操作管理器，用于完成对锁对象的校验、加锁、解锁操作
 * 
 * @author Fan Jingjian
 * 
 */
public abstract class AbstractLockMgr
{
    /**
     * 对锁模型请求解锁
     * 
     * @param model
     *            操作锁对象
     * @exception LockException
     *                如果指定操作锁不存在。
     */
    public abstract void releaseLock(AbstractLockModel model) throws LockException;

    /**
     * 对锁模型是否已经被锁定
     * 
     * @param model
     *            操作锁对象
     * @return True ：锁定 False：无锁
     */
    public abstract boolean isLocked(AbstractLockModel model);

    /**
     * 对锁模型请求加锁
     * 
     * @param model
     *            操作锁对象
     * @exception LockException
     *                如果指定操作锁已经存在。
     */
    public void requireLock(AbstractLockModel model) throws LockException
    {
        requireLock(model, AbstractLockModel.LOCK_ADD);
    }
    
    /**
     * 对锁模型请求加锁
     * 
     * @param model
     *            操作锁对象
     * @param lockType
     *            操作锁类型，取值范围为LockInterface.LOCK_ADD、AbstractLockModel.LOCK_EDIT、AbstractLockModel.LOCK_DEL、AbstractLockModel.UNLOCK。
     * @exception LockException
     *                如果指定操作锁已经存在。
     */
    public abstract void requireLock(AbstractLockModel model, int lockType)
            throws LockException;
    
    /**
     * 获取所有操作锁
     * 
     * @return 操作锁集合
     */
    public abstract Map getAll();
}
