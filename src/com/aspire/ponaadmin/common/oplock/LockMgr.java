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
 * <p>��������������������ɶ��������У�顢��������������</p>
 * <p>һ����÷���
 * <blockquote><pre>
 * //Ϊ��Ҫ����������Ķ��󴴽�һ��������ʵ��
 * String s = "abcdefg";
 * GeneralLockModel model = new GeneralLockModel(s);
 * try
 * {
 *     //���������
 *     LockMgr.getInstance().requireLock(model);
 *     //.....................
 *     //do some thing
 *     //.....................
 *     //�ͷŲ�����
 *     LockMgr.getInstance().releaseLock(model);
 * } catch (LockException le)
 * {
 *     le.printStackTrace();
 * }
 * </pre></blockquote>
 * </p>
 * <p>
 * ���Ĭ�ϵ�GeneralLockModel����������Ҫ���������м̳�AbstractLockModel�����Լ��Ĳ������ࡣ
 * </p>
 * <p>
 * <strong>ע�⣺��ǰϵͳ��ʵ�ֵ����ڴ��������ֻ�����ڵ������������</strong>
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
