package com.aspire.ponaadmin.web.category;
/**
 * ��¼��ֵ�Ե�VO��
 * @author zhangwei
 *
 */
public class RecordVO
{
	String key;
	String value;
	public  String getKey()
	{
		return key;
	}
	public  void setKey(String key)
	{
		this.key = key;
	}
	public  String getValue()
	{
		return value;
	}
	public  void setValue(String value)
	{
		this.value = value;
	}
	public void put(String key,String value)
	{
		this.key=key;
		this.value=value;
	}

}
