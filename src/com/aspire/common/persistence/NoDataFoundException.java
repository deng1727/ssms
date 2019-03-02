package com.aspire.common.persistence;

/**
 * 异常类，表示没有发现数据。
 * <p>Title: aspire xPortal project</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: www.aspire-tech.com</p>
 * @author achang
 * @version 1.0
 */

public class NoDataFoundException extends Exception {
    public NoDataFoundException(String pExceptionMsg) {
        super(pExceptionMsg);
    }
}