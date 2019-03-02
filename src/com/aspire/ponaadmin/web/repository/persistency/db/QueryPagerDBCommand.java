package com.aspire.ponaadmin.web.repository.persistency.db ;

import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.repository.persistency.Command;

/**
 * <p>��ѯ����ҳ���ݿ�־û�������ڲ���װ��һ�����ݿ�־û���Ҫִ�е�һ����ѯsql�����һ��count��sql���</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class QueryPagerDBCommand  extends Command
{

    /**
     * ��ҳ������
     */
    private PageResult pager = null;

    /**
     * sql���
     */
    private String sql;

    /**
     * count sql���
     */
    private String countSQL;

    /**
     * sql���Ĳ���
     */
    private Object[] paras;

    /**
     * ��Ҫ���ص���Դ������
     */
    private String nodeType;

    /**
     * ���췽��
     * @param pager PageResult����ҳ������
     * @param sql String��sql���
     * @param countSQL String��countSQL���
     * @param paras Object[]��sql���Ĳ���
     * @param nodeType String����Ҫ���ص���Դ�����ͣ�Ϊnull��ʾ���ػ�������
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
     * ִ�г־û������ķ���������Command.execute��������μ���
     * @return Object���־û����
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
