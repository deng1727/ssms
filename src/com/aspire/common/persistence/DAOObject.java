package com.aspire.common.persistence;

/**
 * DAO Object interface,define DAO Object contract that implement
 * this interface.
 * <p>Title: aspire xPortal project</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: www.aspire-tech.com</p>
 * @author achang
 * @version 1.0
 */

public interface DAOObject {
    public ValueObject findByPrimaryKey(String pPrimaryKey)
            throws DataAccessException, NoDataFoundException,
            ServiceLocatorException;

    public void insert(ValueObject pValueObject) throws DataAccessException,
            ServiceLocatorException;

    public void update(ValueObject pValueObject) throws DataAccessException,
            OptimisticLockException, ServiceLocatorException;

    public void delete(ValueObject pValueObject) throws DataAccessException,
            ServiceLocatorException;

    public ValueObject createValueObject() throws DataAccessException,
            ServiceLocatorException;

}