package com.aspire.ponaadmin.web.db ;

import com.aspire.common.db.DAOException;

public class TransactionContextFactory
{
    /**
     * 数据库链接方式定义：jndi
     */
    public static final int MODE_JNDI = 0 ;

    /**
     * 数据库链接方式定义：jdbc访问
     */
    public static final int MODE_JDBC_DIRECT = 1 ;
    
    /**
     * 当前使用的数据库链接方式，默认为jndi
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
