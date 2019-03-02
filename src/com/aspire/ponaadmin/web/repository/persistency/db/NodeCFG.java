package com.aspire.ponaadmin.web.repository.persistency.db ;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

/**
 * <p>节点持久化配置信息类封装了某种类型的资源节点的持久化配置信息。</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.0.0
 */
public class NodeCFG
{

    /**
     * 特殊前缀，用于构造引用节点时候使用
     */
    public static final String SPEC_PREFIX = "x";

    /**
     * 资源类型
     */
    protected String type;
    
    /**
     * 资源类型的描述，注意本属性是可以不配置的。
     */
    protected String typeDesc;

    /**
     * 对应的实现类名称
     */
    protected String clazz;

    /**
     * 扩展的资源类型
     */
    protected String extend;

    /**
     * 引用类型，只有当类型为引用节点类型时才有用。
     */
    protected String refType;

    /**
     * 引用主键字段，只有当类型为引用节点类型时才有用。
     */
    protected String refKeyRow;

//    /**
//     * 资源对应的引用持久化数据库表
//     */
//    protected List refTables = new ArrayList();

    /**
     * 资源对应的所有持久化数据库表
     */
    protected List tables = new ArrayList();

    /**
     * 资源对应的所有持久化数据库字段
     */
    private List rows = new ArrayList();

    /**
     * 以字段对应的类属性为key保存所有持久化数据库字段
     */
    private HashMap tableRowsByField = new HashMap() ;

    public String getClazz ()
    {
        return clazz ;
    }

    public void setClazz (String clazz)
    {
        this.clazz = clazz ;
    }

    public void setType (String type)
    {
        this.type = type ;
    }

    public void setTypeDesc (String typeDesc)
    {
        this.typeDesc = typeDesc ;
    }
    
    public void setExtend (String extend)
    {
        this.extend = extend ;
    }

    public void setRefType (String refType)
    {
        this.refType = refType ;
    }

    public void setRefKeyRow (String refKeyRow)
    {
        this.refKeyRow = refKeyRow ;
    }

    public String getType ()
    {
        return type ;
    }

    public String getTypeDesc ()
    {
        return typeDesc ;
    }
    
    public List getTables ()
    {
        return tables ;
    }

    public List getRows ()
    {
        return this.rows ;
    }

    public String getExtend ()
    {
        return extend ;
    }

    public String getRefType()
    {
        return refType;
    }

    public String getRefKeyRow()
    {
        return this.refKeyRow;
    }

//    public List getRefTables()
//    {
//        return this.refTables;
//    }

    /**
     * 加入一个数据库表table配置
     * @param table TableCFG，数据库表table配置
     */
    public void addTable(TableCFG table)
    {
        //由于需要支持大表存储（即父类和子类都保存在同一张表），所以要考虑table重复的情况）
        TableCFG existedTable = this.isTableExisted(table);
        if(existedTable!=null)
        {
            //已经有同名的存在了
            //遍历table.getRows
            for(int i = 0; i < table.getRows().size(); i++)
            {
                RowCFG row = (RowCFG) table.getRows().get(i);
                //把当前的RowCFG加入到现有table中
                existedTable.addRow(row);
                //把当前的RowCFG加入到this.rows中
                this.rows.add(row);
                //把当前的RowCFG以其fieldName为key加入到this. tableRowsByField中
                this.tableRowsByField.put(row.getClassField(), row);
            }
        }
        else
        {
            //没有同名的存在
            //加入到tables中
            this.tables.add(table);
            //遍历table.getRows
            for(int i = 0; i < table.getRows().size(); i++)
            {
                RowCFG row = (RowCFG) table.getRows().get(i);
                //把当前的RowCFG加入到this.rows中
                this.rows.add(row);
                //把当前的RowCFG以其fieldName为key加入到this. tableRowsByField中
                this.tableRowsByField.put(row.getClassField(), row);
            }
        }
    }


    /**
     * 检查一个table是否已经在这个NodeCFG中存在了
     * @param table TableCFG
     * @return TableCFG,为null表示不存在，不为null就是存在的那个TableCFG
     */
    private TableCFG isTableExisted(TableCFG table)
    {
        String tableName = table.getName();
        for(int i=0;i<this.tables.size();i++)
        {
            TableCFG existedTable = (TableCFG)this.tables.get(i);
            if(existedTable.getName().equalsIgnoreCase(tableName))
            {
                return existedTable;
            }
        }
        return null;
    }

    /**
     * 获取资源实现类中的一个类属性对应的数据库表字段的持久化配置信息
     * @param fieldName String，类属性名称
     * @return RowCFG，数据库表字段持久化配置信息
     */
    public RowCFG getRowByField(String fieldName)
    {
        return (RowCFG) this.tableRowsByField.get(fieldName);
    }

//    /**
//     * 获取资源实现类中的一个引用类属性对应的数据库表字段的持久化配置信息
//     * @param fieldName String，类属性名称
//     * @return RowCFG，数据库表字段持久化配置信息
//     */
//    public RowCFG getRefRowByField(String fieldName)
//    {
//        for(int i=0;i<this.refTables.size();i++)
//        {
//            TableCFG table = (TableCFG)this.refTables.get(0);
//            RowCFG row = table.getRowByField(fieldName);
//            if(row!=null)
//            {
//                return row;
//            }
//        }
//        return null;
//    }

    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("NodeCFG[");
        sb.append("type:"+this.type);
        sb.append(',');
        sb.append("typeDesc:"+this.typeDesc);
        sb.append(',');
        sb.append("clazz:"+this.clazz);
        sb.append(',');
        sb.append("extend:"+this.extend);
        sb.append(',');
        sb.append("refType:"+this.refType);
        sb.append(',');
        sb.append("refKeyRow:"+this.refKeyRow);
        sb.append('\n');
        for(int i=0;i<this.tables.size();i++)
        {
            sb.append("table"+(i+1)+":"+this.tables.get(i));
            sb.append('\n');
        }
        sb.append("]");
        return sb.toString();
    }
}
