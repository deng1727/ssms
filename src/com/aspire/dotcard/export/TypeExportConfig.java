package com.aspire.dotcard.export;

import java.util.ArrayList;
import java.util.List;
/**
 * 导出配置项的公共类，
 * @author zhangwei
 *
 */
public abstract class TypeExportConfig
{
	/**
	 * 保存所有导出属性。
	 */
	protected List list=new ArrayList();
	/**
	 * 导出的类型。
	 */
	protected String type;
	/**
	 * 初始化导出配置项。
	 */
	public abstract void init();
	/**
	 * 获取所有的
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
