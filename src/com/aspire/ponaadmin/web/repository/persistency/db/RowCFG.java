package com.aspire.ponaadmin.web.repository.persistency.db ;

/**
 * <p>��Դ�ֿ�־û��������࣬��ʾһ���־û������ݿ�table��row</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.0.0
 */

final class RowCFG
{

    /**
     * �ֶ�����
     */
    private String name ;

    /**
     * �ֶζ�Ӧ����Դ����������
     */
    private String classField ;

    /**
     * �ֶ����ͣ�֧�ֵ����������Դģ��Ķ���
     */
    private String type ;

    /**
     * �ֶ������ı������
     */
    private String tableName;

    /**
     * �ֶ������ı�ı���
     */
    private String tableAliasName;

    public String getClassField ()
    {
        return classField ;
    }

    public void setClassField (String classField)
    {
        this.classField = classField ;
    }

    public void setType (String type)
    {
        this.type = type ;
    }

    public void setName (String name)
    {
        this.name = name ;
    }

    public void setTableName (String tableName)
    {
        this.tableName = tableName ;
    }

    public void setTableAliasName (String tableAliasName)
    {
        this.tableAliasName = tableAliasName ;
    }

    public String getName ()
    {
        return name ;
    }

    public String getType ()
    {
        return type ;
    }

    public String getTableName ()
    {
        return tableName ;
    }

    public String getTableAliasName ()
    {
        return tableAliasName ;
    }

    /**
     * clone����
     * @return Object
     */
    public Object clone ()
    {
        RowCFG other = new RowCFG() ;
        other.name = this.name ;
        other.classField = this.classField ;
        other.type = this.type ;
        other.tableName = this.tableName ;
        other.tableAliasName = this.tableAliasName ;
        return other ;
    }

    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("RowCFG[");
        sb.append("name:"+this.name);
        sb.append(',');
        sb.append("classField:"+this.classField);
        sb.append(',');
        sb.append("type:"+this.type);
        sb.append(',');
        sb.append("tableName:"+this.tableName);
        sb.append(',');
        sb.append("tableAliasName:"+this.tableAliasName);
        sb.append("]");
        return sb.toString();
    }

}
