package com.aspire.ponaadmin.web.repository.persistency.db ;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

/**
 * <p>�ڵ�־û�������Ϣ���װ��ĳ�����͵���Դ�ڵ�ĳ־û�������Ϣ��</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.0.0
 */
public class NodeCFG
{

    /**
     * ����ǰ׺�����ڹ������ýڵ�ʱ��ʹ��
     */
    public static final String SPEC_PREFIX = "x";

    /**
     * ��Դ����
     */
    protected String type;
    
    /**
     * ��Դ���͵�������ע�Ȿ�����ǿ��Բ����õġ�
     */
    protected String typeDesc;

    /**
     * ��Ӧ��ʵ��������
     */
    protected String clazz;

    /**
     * ��չ����Դ����
     */
    protected String extend;

    /**
     * �������ͣ�ֻ�е�����Ϊ���ýڵ�����ʱ�����á�
     */
    protected String refType;

    /**
     * ���������ֶΣ�ֻ�е�����Ϊ���ýڵ�����ʱ�����á�
     */
    protected String refKeyRow;

//    /**
//     * ��Դ��Ӧ�����ó־û����ݿ��
//     */
//    protected List refTables = new ArrayList();

    /**
     * ��Դ��Ӧ�����г־û����ݿ��
     */
    protected List tables = new ArrayList();

    /**
     * ��Դ��Ӧ�����г־û����ݿ��ֶ�
     */
    private List rows = new ArrayList();

    /**
     * ���ֶζ�Ӧ��������Ϊkey�������г־û����ݿ��ֶ�
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
     * ����һ�����ݿ��table����
     * @param table TableCFG�����ݿ��table����
     */
    public void addTable(TableCFG table)
    {
        //������Ҫ֧�ִ��洢������������඼������ͬһ�ű�������Ҫ����table�ظ��������
        TableCFG existedTable = this.isTableExisted(table);
        if(existedTable!=null)
        {
            //�Ѿ���ͬ���Ĵ�����
            //����table.getRows
            for(int i = 0; i < table.getRows().size(); i++)
            {
                RowCFG row = (RowCFG) table.getRows().get(i);
                //�ѵ�ǰ��RowCFG���뵽����table��
                existedTable.addRow(row);
                //�ѵ�ǰ��RowCFG���뵽this.rows��
                this.rows.add(row);
                //�ѵ�ǰ��RowCFG����fieldNameΪkey���뵽this. tableRowsByField��
                this.tableRowsByField.put(row.getClassField(), row);
            }
        }
        else
        {
            //û��ͬ���Ĵ���
            //���뵽tables��
            this.tables.add(table);
            //����table.getRows
            for(int i = 0; i < table.getRows().size(); i++)
            {
                RowCFG row = (RowCFG) table.getRows().get(i);
                //�ѵ�ǰ��RowCFG���뵽this.rows��
                this.rows.add(row);
                //�ѵ�ǰ��RowCFG����fieldNameΪkey���뵽this. tableRowsByField��
                this.tableRowsByField.put(row.getClassField(), row);
            }
        }
    }


    /**
     * ���һ��table�Ƿ��Ѿ������NodeCFG�д�����
     * @param table TableCFG
     * @return TableCFG,Ϊnull��ʾ�����ڣ���Ϊnull���Ǵ��ڵ��Ǹ�TableCFG
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
     * ��ȡ��Դʵ�����е�һ�������Զ�Ӧ�����ݿ���ֶεĳ־û�������Ϣ
     * @param fieldName String������������
     * @return RowCFG�����ݿ���ֶγ־û�������Ϣ
     */
    public RowCFG getRowByField(String fieldName)
    {
        return (RowCFG) this.tableRowsByField.get(fieldName);
    }

//    /**
//     * ��ȡ��Դʵ�����е�һ�����������Զ�Ӧ�����ݿ���ֶεĳ־û�������Ϣ
//     * @param fieldName String������������
//     * @return RowCFG�����ݿ���ֶγ־û�������Ϣ
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
