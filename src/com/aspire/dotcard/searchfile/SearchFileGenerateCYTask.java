package com.aspire.dotcard.searchfile;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.exception.BOException;
import com.aspire.ponaadmin.web.util.ArrangedTask;

public class SearchFileGenerateCYTask extends ArrangedTask
{

	public void init()throws BOException
	{
		String starttime = ConfigFactory.getSystemConfig()
		.getModuleConfig("searchFileGenerate").getItemValue("CYStartTime");
		this.setExecuteTime(starttime);
		this.setTaskDesc("CY搜索数据文件生成定时任务");
	}

	public void doTask()
	{
		SearchFileGenerateCYBO.getInstance().generateAllSearchFile();
	}

}
