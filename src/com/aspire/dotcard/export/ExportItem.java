package com.aspire.dotcard.export;
/**
 * 在导出属性选项选项。 包含数据库中字段名和导出excel中显示的列名称。
 * @author zhangwei
 *
 */
public class ExportItem
{
	/**
	 * 数据库的字段名
	 */
	String columnName;
	/**
	 * 结果文件的展示名称
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
