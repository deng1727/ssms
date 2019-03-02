package com.aspire.common.db ;

/**
 * <p>���ݿ��ִ����</p>
 * <p>��װһ����Ҫ��ִ�е�sql�������Ӧ�Ĳ���</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class Executor
{
    /**
     * ִ�е�sql���
     */
    private String sql;

    /**
     * sql����?������Ӧ��ֵ
     */
    private Object[] paras;

    public Object[] getParas ()
    {
        return paras ;
    }

    public void setParas (Object[] paras)
    {
        this.paras = paras ;
    }

    public void setSql (String sql)
    {
        this.sql = sql ;
    }

    public String getSql ()
    {
        return sql ;
    }

    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(this.sql) ;
        sb.append(",[");
        for (int i = 0 ; this.paras != null && i < this.paras.length ; i++)
        {
            if(i>0)
            {
                sb.append(',') ;
            }
            sb.append(this.paras[i]);
        }
        sb.append(']');
        return sb.toString();
    }
}
