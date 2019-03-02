package com.aspire.ponaadmin.web.repository.persistency.db ;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

/**
 * <p>��Դ�ֿ�־û��������࣬��ʾһ���־û������ݿ�table</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.0.0
 */

final class TableCFG implements Cloneable
{

    /**
     * ����Ψһ����ʱ�õ�����ֵ
     */
    private static int aliasNameIndex = 0;

    /**
     * ���ݿ������
     */
    private String name;

    /**
     * ϵͳΪ���������Ψһ����
     */
    private String aliasName;

    /**
     * ���ݿ��ؼ��ֶε�����
     */
    private String key;

    /**
     * ���Ӧ�������ֶ�
     */
    private List rows = new ArrayList();

    /**
     * ���ֶζ�Ӧ��������Ϊkey���������ֶ�
     */
    private HashMap rowsByField = new HashMap();

    /**
     * ���ֶ�����Ϊkey���������ֶ�
     */
    private HashMap RowsByName = new HashMap();

    /**
     * ���췽��
     */
    TableCFG()
    {
        this.aliasName = allocateAliasName();
    }

    /**
     * ���췽���������������ר�Ÿ�clone�����ṩ��
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
     * ����һ�����ݿ���ֶ�RowCFG����
     * @param row RowCFG�����ݿ���ֶ�����
     */
    public void addRow(RowCFG row)
    {
        //����row��tableName��tableAliasNameΪthis.name��this.aliasName;
        row.setTableName(this.name);
        row.setTableAliasName(this.aliasName);

        this.rows.add(row);
        this.RowsByName.put(row.getName(), row);
        this.rowsByField.put(row.getClassField(), row);
    }

    /**
     * ��ȡһ����Դ�����Զ�Ӧ�����ݿ���ֶεĳ־û�������Ϣ
     * @param fieldName String������������
     * @return RowCFG�����ݿ���ֶγ־û�������Ϣ
     */
    public RowCFG getRowByField(String fieldName)
    {
        return (RowCFG) this.rowsByField.get(fieldName);
    }

    /**
     * ��ȡһ���ֶ����ƶ�Ӧ�����ݿ���ֶεĳ־û�������Ϣ
     * @param name String���ֶ�����
     * @return RowCFG�����ݿ���ֶγ־û�������Ϣ
     */
    public RowCFG getRowByName(String name)
    {
        return (RowCFG) this.RowsByName.get(name);
    }

    /**
     * ����Ψһ����
     * @return String��Ψһ����
     */
    private synchronized static String allocateAliasName()
    {
        return "t" + (aliasNameIndex++) ;
    }

    /**
     * clone����
     * @return Object
     */
    public Object clone()
    {
        //Ҫ���ò���������Ĺ��캯��
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
