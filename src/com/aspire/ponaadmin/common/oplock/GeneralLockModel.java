/*
 * @(#)GeneralLockModel.java        1.0 2005-7-8
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
 * 操作锁对象模型
 * 
 * @author Fan Jingjian
 * 
 */
public class GeneralLockModel extends AbstractLockModel
{
    /**
     * 构造方法，产生一个新的操作锁对象，该锁的主键为<i>owner的类名|owner的hashCode</i>
     * 
     * @param owner
     *            锁的属主
     */
    public GeneralLockModel(Object owner)
    {
        lockKey = owner.getClass().getName() + "|" + owner.hashCode();
    }
}
