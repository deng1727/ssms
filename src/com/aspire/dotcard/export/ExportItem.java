package com.aspire.dotcard.export;
/**
 * �ڵ�������ѡ��ѡ� �������ݿ����ֶ����͵���excel����ʾ�������ơ�
 * @author zhangwei
 *
 */
public class ExportItem
{
	/**
	 * ���ݿ���ֶ���
	 */
	String columnName;
	/**
	 * ����ļ���չʾ����
	 */
	String displayName;
	
	public ExportItem(String cName,String dName)
	{
		this.columnName=cName;
		this.displayName=dName;
	}
	
	
	public String getColumnName()
	{
		return columnName;
	}
	public void setColumnName(String columnName)
	{
		this.columnName = columnName;
	}
	public String getDisplayName()
	{
		return displayName;
	}
	public void setDisplayName(String displayName)
	{
		this.displayName = displayName;
	}

}
