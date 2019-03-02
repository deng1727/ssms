package com.aspire.dotcard.export;

import com.aspire.dotcard.gcontent.GRead;

/**
 * 基地读书导出配置项。
 * @author zhangwei
 *
 */
public class ReadExportConfig extends TypeExportConfig
{
	public ReadExportConfig()
	{
		init();
	}

	public void init()
	{
		type=GRead.TYPE_READ;
		list.add(new ExportItem("id","内容ID"));
		list.add(new ExportItem("name","图书名称"));
		list.add(new ExportItem("singer","作者名称"));
		list.add(new ExportItem("introduction","图书简介"));
		list.add(new ExportItem("marketdate","上线时间"));		
	}
	

}
