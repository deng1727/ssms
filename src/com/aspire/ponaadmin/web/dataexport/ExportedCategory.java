package com.aspire.ponaadmin.web.dataexport;


/**
 * 导出类型基础类
 * @author zhangwei
 *
 */
public class ExportedCategory
{
	/**
	 * 导出类型名称
	 */
	private String name;

	/**
	 * 输出元素的个数
	 */
	private int size;
	/**
	 * 获取数据来源的sqlCode
	 */
	private String fromSqlCode;
	/**
	 * 定义clobIndex字段位置，本次只有适配机型才可能是
	 * clob字段，所以就定义一个
	 */
	private int clobIndex=-1;
	

	
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public int getSize()
	{
		return size;
	}
	public void setSize(int size)
	{
		this.size = size;
	}
	public String getFromSqlCode()
	{
		return fromSqlCode;
	}
	public void setFromSqlCode(String fromSql)
	{
		this.fromSqlCode = fromSql;
	}
	public int getClobIndex()
	{
		return clobIndex;
	}
	public void setClobIndex(int clobIndex)
	{
		this.clobIndex = clobIndex;
	}

}
