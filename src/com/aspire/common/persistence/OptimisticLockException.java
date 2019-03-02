package com.aspire.common.persistence;

/**
 * “Ï≥£¿‡
 * <p>Title: aspire xPortal project</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: www.aspire-tech.com</p>
 * @author achang
 * @version 1.0
 */
public class OptimisticLockException extends Exception {
    public OptimisticLockException(String pExceptionMsg) {
        super(pExceptionMsg);
    }

}