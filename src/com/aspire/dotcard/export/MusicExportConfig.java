package com.aspire.dotcard.export;

import com.aspire.dotcard.gcontent.GMusic;

/**
 * �������ֵ��������
 * @author zhangwei
 *
 */
public class MusicExportConfig extends TypeExportConfig
{
	public MusicExportConfig()
	{
		init();
	}

	public void init()
	{
		type=GMusic.TYPE_MUSIC;
		list.add(new ExportItem("id","����ID"));
		list.add(new ExportItem("name","��������"));
		list.add(new ExportItem("singer","��������"));
		list.add(new ExportItem("createDate","����ʱ��"));
		
	}
}
