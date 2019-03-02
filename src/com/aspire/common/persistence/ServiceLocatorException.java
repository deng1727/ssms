package com.aspire.common.persistence;

/** *
 * <p>Title: aspire xPortal project</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: www.aspire-tech.com</p>
 * @author achang
 * @version 1.0
 *
 * The ServiceLocatorException class is an exception that is
 * thrown whenever a user requests an EJB or database connection from the
 * ServiceLocator and the ServiceLocator can not find the item the user is
 * loogkinh for.
 */
public class ServiceLocatorException extends Exception {
    public ServiceLocatorException(String pExceptionMsg) {
        super(pExceptionMsg);
    }
}