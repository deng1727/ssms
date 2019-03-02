package com.aspire.ponaadmin.web.repository.persistency.db ;

import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.repository.persistency.Command;

/**
 * <p>查询并分页数据库持久化命令。其内部封装了一次数据库持久化需要执行的一条查询sql命令和一条count的sql命令。</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class QueryPagerDBCommand  extends Command
{

    /**
     * 分页对象器
     */
    private PageResult pager = null;

    /**
     * sql语句
     */
    private String sql;

    /**
     * count sql语句
     */
    private String countSQL;

    /**
     * sql语句的参数
     */
    private Object[] paras;

    /**
     * 需要返回的资源的类型
     */
    private String nodeType;

    /**
     * 构造方法
     * @param pager PageResult，分页对象器
     * @param sql String，sql语句
     * @param countSQL String，countSQL语句
     * @param paras Object[]，sql语句的参数
     * @param nodeType String，需要返回的资源的类型，为null表示返回基本类型
     */
    public QueryPagerDBCommand (PageResult pager, String sql, String countSQL, Object[] paras
    		                     , String nodeType)
    {
        this.pager = pager;
        this.sql = sql ;
        this.countSQL = countSQL;
        if(this.countSQL == null)
        {
            this.countSQL = "select count(*) from ( "+this.sql+" ) ";
        }
        this.paras = paras ;
        this.nodeType = nodeType ;
        if (nodeType == null)
        {
            this.nodeType = "nt:base" ;
        }
    }

    /**
     * 执行持久化操作的方法，覆盖Command.execute方法，请参见。
     * @return Object，持久化结果
     * @todo Implement this
     *   com.aspire.ponaadmin.web.repository.persistency.Command method
     */
    public Object execute ()
    {
        try
        {
            NodePager nodePager = new NodePager(this.nodeType);
            this.pager.excute(this.sql, this.countSQL, this.paras, nodePager);
        }
        catch (Exception e)
        {
            
        }
        return null;
    }

}
