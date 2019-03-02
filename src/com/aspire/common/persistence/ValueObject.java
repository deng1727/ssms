package com.aspire.common.persistence;

import java.io.Serializable;

/**
 * <p>Title: aspire xPortal project</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: www.aspire-tech.com</p>
 * @author achang
 * @version 1.0
 *
 * The ValueObject is the abstract class that all ValueObjects in our
 * data persistence framework descend off of.  It provides a number of methods,
 * including get/set methods for the VO status flags and the rowVersion method.
 */

public abstract class ValueObject implements Serializable {
    /*Below are the VO status flags*/
    private boolean insertFlag = false;
    private boolean updateFlag = false;
    private boolean deleteFlag = false;
    private int rowVersion = 0;

    /*
     * Retrieves the insertFlag.  The insertFlag is used to tell the DAO that data in the
     * the ValueObject should be inserted into the database.
     *
     */
    public boolean getInsertFlag() {
        return insertFlag;
    }

    /*
     * Retrieves the deleteFlag.  The deleteFlag is used to tell the DAO that the data
     * in the ValueObject should be deleted from the database.
     *
     */
    public boolean getDeleteFlag() {
        return deleteFlag;
    }

    /*
     * Retrieves the updateFlag.  The updateFlag is to tell the DAO that the data in the
     * ValueObject should be deleted from the database.
     *
     */
    public boolean getUpdateFlag() {
        return updateFlag;
    }

    /*
     * Retrieves the rowVersion.  The rowVersion holds the current rowVersion of the record
     * and is used by the DAO.update() method to enforce optimistic locking exceptions.
     *
     */
    public int getRowVersion() {
        return rowVersion;
    }

    /*Sets the insertFlag and sets all other status flags to false*/
    public void setInsertFlag(boolean pFlag) {
        insertFlag = pFlag;
        deleteFlag = false;
        updateFlag = false;
    }

    /*Sets the updateFlag and sets all other status flags to false*/
    public void setUpdateFlag(boolean pFlag) {
        insertFlag = false;
        deleteFlag = false;
        updateFlag = pFlag;

    }

    /*Sets the deleteFlag and sets all other status flags to false*/
    public void setDeleteFlag(boolean pFlag) {
        insertFlag = false;
        deleteFlag = pFlag;
        updateFlag = false;
    }

    /*Sets the rowVersion property*/
    public void setRowVersion(int pRowVersion) {
        rowVersion = pRowVersion;
    }

    /*Resets all VO status flags to false*/
    public void resetFlags() {
        insertFlag = false;
        deleteFlag = false;
        updateFlag = false;
    }

}