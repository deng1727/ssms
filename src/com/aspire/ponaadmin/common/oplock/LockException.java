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
 * 操作锁异常
 * 
 * @author Fan Jingjian
 * 
 */
public class LockException extends Throwable
{
    /**
     * 指定操作锁已经存在
     */
    public static final int LOCK_EXIST = 1;
    /**
     * 未发现指定操作锁
     */
    public static final int LOCK_NOT_FOUND = 2;
    /**
     * 其他异常类型
     */
    public static final int OTHER_EXCEPTION = -1;
    /**
     * 异常的错误类型
     */
    private int errorCode = -1;

    /**
     * 生成一个新的异常实例
     * 
     * @param errorCode
     *            异常的错误类型，取值范围为
     */
    public LockException(int errorCode)
    {
        this.errorCode = errorCode;
    }

    /**
     * 获取异常的错误类型
     * 
     * @return 异常的错误类型
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
