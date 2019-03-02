package com.aspire.ponaadmin.web.repository.persistency.db ;

/**
 * <p>资源仓库持久化，辅助类，表示一个持久化的数据库table的row</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.0.0
 */

final class RowCFG
{

    /**
     * 字段名称
     */
    private String name ;

    /**
     * 字段对应的资源类属性名称
     */
    private String classField ;

    /**
     * 字段类型，支持的类型请见资源模板的定义
     */
    private String type ;

    /**
     * 字段所属的表的名称
     */
    private String tableName;

    /**
     * 字段所属的表的别名
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
     * clone方法
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
