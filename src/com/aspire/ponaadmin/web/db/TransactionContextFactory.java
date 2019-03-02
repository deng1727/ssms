package com.aspire.ponaadmin.web.db ;

import com.aspire.common.db.DAOException;

public class TransactionContextFactory
{
    /**
     * ���ݿ����ӷ�ʽ���壺jndi
     */
    public static final int MODE_JNDI = 0 ;

    /**
     * ���ݿ����ӷ�ʽ���壺jdbc����
     */
    public static final int MODE_JDBC_DIRECT = 1 ;
    
    /**
     * ��ǰʹ�õ����ݿ����ӷ�ʽ��Ĭ��Ϊjndi
     */
	public static int mode = MODE_JNDI;
	
    public static TransactionContext getTransactionContext() 
    {
        TransactionContext tsc = null;
        try
        {
            tsc = TransactionDB.getTransactionInstance();
        }
        catch (DAOException e)
        {
            
        }
        return tsc;
    }
    
    public static TransactionContext getNonTransactionContext()
    {
        return TransactionDB.getInstance();
    }
}
