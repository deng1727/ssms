package com.aspire.dotcard.export;

import java.util.ArrayList;
import java.util.List;
/**
 * ����������Ĺ����࣬
 * @author zhangwei
 *
 */
public abstract class TypeExportConfig
{
	/**
	 * �������е������ԡ�
	 */
	protected List list=new ArrayList();
	/**
	 * ���������͡�
	 */
	protected String type;
	/**
	 * ��ʼ�����������
	 */
	public abstract void init();
	/**
	 * ��ȡ���е�
	 * @return
	 */
	public  List getExportColumnNames()
	{
		return list;
	}
	
	public String getType()
	{
		return type;
	}

}
