package com.aspire.ponaadmin.web.repository.persistency.db ;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

/**
 * <p>资源仓库持久化，辅助类，表示一个持久化的数据库table</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.0.0
 */

final class TableCFG implements Cloneable
{

    /**
     * 分配唯一别名时用的索引值
     */
    private static int aliasNameIndex = 0;

    /**
     * 数据库表名称
     */
    private String name;

    /**
     * 系统为这个表分配的唯一别名
     */
    private String aliasName;

    /**
     * 数据库表关键字段的名称
     */
    private String key;

    /**
     * 表对应的所有字段
     */
    private List rows = new ArrayList();

    /**
     * 以字段对应的类属性为key保存所有字段
     */
    private HashMap rowsByField = new HashMap();

    /**
     * 以字段名称为key保存所有字段
     */
    private HashMap RowsByName = new HashMap();

    /**
     * 构造方法
     */
    TableCFG()
    {
        this.aliasName = allocateAliasName();
    }

    /**
     * 构造方法，不分配别名，专门给clone方法提供的
     * @param notClone boolean
     */
    private TableCFG(boolean notClone)
    {}

    public String getKey ()
    {
        return key ;
    }

    public void setKey (String key)
    {
        this.key = key ;
    }

    public void setName (String name)
    {
        this.name = name ;
    }

    public String getName ()
    {
        return name ;
    }

    public List getRows ()
    {
        return rows ;
    }

    public String getAliasName ()
    {
        return aliasName ;
    }

    /**
     * 加入一个数据库表字段RowCFG配置
     * @param row RowCFG，数据库表字段配置
     */
    public void addRow(RowCFG row)
    {
        //设置row的tableName和tableAliasName为this.name和this.aliasName;
        row.setTableName(this.name);
        row.setTableAliasName(this.aliasName);

        this.rows.add(row);
        this.RowsByName.put(row.getName(), row);
        this.rowsByField.put(row.getClassField(), row);
    }

    /**
     * 获取一个资源类属性对应的数据库表字段的持久化配置信息
     * @param fieldName String，类属性名称
     * @return RowCFG，数据库表字段持久化配置信息
     */
    public RowCFG getRowByField(String fieldName)
    {
        return (RowCFG) this.rowsByField.get(fieldName);
    }

    /**
     * 获取一个字段名称对应的数据库表字段的持久化配置信息
     * @param name String，字段名称
     * @return RowCFG，数据库表字段持久化配置信息
     */
    public RowCFG getRowByName(String name)
    {
        return (RowCFG) this.RowsByName.get(name);
    }

    /**
     * 分配唯一别名
     * @return String，唯一别名
     */
    private synchronized static String allocateAliasName()
    {
        return "t" + (aliasNameIndex++) ;
    }

    /**
     * clone方法
     * @return Object
     */
    public Object clone()
    {
        //要调用不分配别名的构造函数
        TableCFG other = new TableCFG(false);
        other.key = this.key;
        other.name = this.name;
        other.aliasName = this.aliasName;
        for(int i=0;i<this.rows.size();i++)
        {
            RowCFG row = (RowCFG)this.rows.get(i);
            RowCFG newRow = (RowCFG)row.clone();
            other.rows.add(newRow);
            other.rowsByField.put(newRow.getClassField(),newRow);
            other.RowsByName.put(newRow.getName(),newRow);
        }
        return other;
    }

    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("TableCFG[");
        sb.append("name:"+this.name);
        sb.append(',');
        sb.append("aliasName:"+this.aliasName);
        sb.append(',');
        sb.append("key:"+this.key);
        sb.append('\n');
        for(int i=0;i<this.rows.size();i++)
        {
            sb.append("row"+(i+1)+":"+this.rows.get(i));
            sb.append('\n');
        }
        sb.append("]");
        return sb.toString();
    }

}
