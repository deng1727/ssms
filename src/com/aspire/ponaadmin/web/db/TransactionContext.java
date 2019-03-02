package com.aspire.ponaadmin.web.db ;

public interface TransactionContext 
{

    /**
     * �����Ƿ����񳡾�
     * @return boolean
     */
    boolean isTransaction();
    
    /**
     * �ύ
     */
    void commit();

    /**
     * �ع�
     */
    void rollback();

    /**
     * �ر�
     */
    void close();
    
}
