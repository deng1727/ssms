/*
 * @(#)LockMgr.java        1.0 2005-7-8
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
 * <p>锁操作管理器，用于完成对锁对象的校验、加锁、解锁操作</p>
 * <p>一般的用法：
 * <blockquote><pre>
 * //为需要申请操作锁的对象创建一个锁对象实例
 * String s = "abcdefg";
 * GeneralLockModel model = new GeneralLockModel(s);
 * try
 * {
 *     //申请操作锁
 *     LockMgr.getInstance().requireLock(model);
 *     //.....................
 *     //do some thing
 *     //.....................
 *     //释放操作锁
 *     LockMgr.getInstance().releaseLock(model);
 * } catch (LockException le)
 * {
 *     le.printStackTrace();
 * }
 * </pre></blockquote>
 * </p>
 * <p>
 * 如果默认的GeneralLockModel不能满足需要，可以自行继承AbstractLockModel创建自己的操作锁类。
 * </p>
 * <p>
 * <strong>注意：当前系统中实现的是内存操作锁，只适用于单机部署情况。</strong>
 * </p>
 * @author Fan Jingjian
 * 
 */
public abstract class LockMgr
{
    private static AbstractLockMgr instance;

    private LockMgr()
    {
    }

    public static AbstractLockMgr getInstance()
    {
        if (instance == null)
        {
            instance = new LockMgrMemoryImpl();
        }
        return instance;
    }
}
