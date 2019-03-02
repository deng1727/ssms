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
 * 锁对象模型，是进行锁操的基本对象
 * 
 * @author Fan Jingjian
 * 
 */
public abstract class AbstractLockModel
{
    /**
     * 添加行为的锁操作
     */
    public final static int LOCK_ADD = 1;
    /**
     * 编辑行为的锁操作
     */
    public final static int LOCK_EDIT = 2;
    /**
     * 删除行为的锁操作
     */
    public final static int LOCK_DEL = 3;
    /**
     * 没有被锁定
     */
    public final static int UNLOCK = 0;
    /**
     * 加锁时间
     */
    protected long lockTime = -1;
    /**
     * 解锁时间
     */
    protected long releaseTime = -1;
    /**
     * 操作锁的主键
     */
    String lockKey = null;

    /**
     * 取出锁对象关键字
     * 
     * @return 关键字
     */
    public String getLockKey()
    {
        return lockKey;
    }
}
