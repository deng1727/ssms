/*
 * @(#)LockMgrMemoryImpl.java        1.0 2005-7-8
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

import java.util.HashMap;
import java.util.Map;

/**
 * 操作锁的内存实现
 * 
 * @author Fan Jingjian
 * 
 */
public class LockMgrMemoryImpl extends AbstractLockMgr
{
    /**
     * 操作锁集合
     */
    private Map lockMap = null;

    protected LockMgrMemoryImpl()
    {
        lockMap = new HashMap();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aspire.ponaadmin.common.oplock.AbstractLockMgr#releaseLock(com.aspire.ponaadmin.common.oplock.AbstractLockModel)
     */
    public void releaseLock(AbstractLockModel model) throws LockException
    {
        if (!isLocked(model))
            throw new LockException(LockException.LOCK_NOT_FOUND);
        Object o = null;
        synchronized (lockMap)
        {
            o = lockMap.remove(model.getLockKey());
        }
        ((AbstractLockModel)o).releaseTime = System.currentTimeMillis();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aspire.ponaadmin.common.oplock.AbstractLockMgr#isLocked(com.aspire.ponaadmin.common.oplock.AbstractLockModel)
     */
    public boolean isLocked(AbstractLockModel model)
    {
        return lockMap.containsKey(model.getLockKey());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aspire.ponaadmin.common.oplock.AbstractLockMgr#requireLock(com.aspire.ponaadmin.common.oplock.AbstractLockModel,
     *      int)
     */
    public void requireLock(AbstractLockModel model, int lockType)
            throws LockException
    {
        if (isLocked(model))
            throw new LockException(LockException.LOCK_EXIST);
        synchronized (lockMap)
        {
            lockMap.put(model.getLockKey(), model);
        }
        model.lockTime = System.currentTimeMillis();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aspire.ponaadmin.common.oplock.AbstractLockMgr#getAll()
     */
    public Map getAll()
    {
        return lockMap;
    }

}
