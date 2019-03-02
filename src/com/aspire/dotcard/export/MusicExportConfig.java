package com.aspire.dotcard.export;

import com.aspire.dotcard.gcontent.GMusic;

/**
 * 基地音乐导出配置项。
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
		list.add(new ExportItem("id","内容ID"));
		list.add(new ExportItem("name","歌曲名称"));
		list.add(new ExportItem("singer","歌手名称"));
		list.add(new ExportItem("createDate","上线时间"));
		
	}
}
