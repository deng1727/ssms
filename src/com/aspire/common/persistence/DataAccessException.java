package com.aspire.common.persistence;

/**
 * Data Access �쳣��DAOͨ���쳣
 * <p>Title: aspire xPortal project</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: www.aspire-tech.com</p>
 * @author achang
 * @version 1.0
 */

public class DataAccessException extends Exception {
    Throwable exceptionCause = null;

    public DataAccessException() {
        super();
    }

    public DataAccessException(String msg) {
        super(msg);
    }

    public DataAccessException(String msg, Throwable pException) {
        super(msg);
        this.exceptionCause = pException;
    }

    public void printStatckTrace() {
        if (exceptionCause != null) {
            System.err.println("�����쳣������ԭ��");
            exceptionCause.printStackTrace();
        }
    }

}