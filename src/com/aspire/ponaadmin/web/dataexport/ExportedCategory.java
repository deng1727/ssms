package com.aspire.ponaadmin.web.dataexport;


/**
 * �������ͻ�����
 * @author zhangwei
 *
 */
public class ExportedCategory
{
	/**
	 * ������������
	 */
	private String name;

	/**
	 * ���Ԫ�صĸ���
	 */
	private int size;
	/**
	 * ��ȡ������Դ��sqlCode
	 */
	private String fromSqlCode;
	/**
	 * ����clobIndex�ֶ�λ�ã�����ֻ��������Ͳſ�����
	 * clob�ֶΣ����ԾͶ���һ��
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
