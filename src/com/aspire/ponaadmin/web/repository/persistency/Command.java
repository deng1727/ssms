package com.aspire.ponaadmin.web.repository.persistency ;

/**
 * <p>持久化命令，封装了一个持久化请求对应需要执行的持久化操作。</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */

public abstract class Command
{

    /**
     * 执行持久化操作的方法
     * @return Object，持久化结果
     */
    public abstract Object execute();
}
