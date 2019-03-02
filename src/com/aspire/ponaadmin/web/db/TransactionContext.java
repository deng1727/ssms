package com.aspire.ponaadmin.web.db ;

public interface TransactionContext 
{

    /**
     * 返回是否事务场景
     * @return boolean
     */
    boolean isTransaction();
    
    /**
     * 提交
     */
    void commit();

    /**
     * 回滚
     */
    void rollback();

    /**
     * 关闭
     */
    void close();
    
}
