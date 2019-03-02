package com.aspire.dotcard.searchfile;

import com.aspire.common.exception.BOException;
import com.aspire.ponaadmin.web.util.ArrangedTask;

public class SearchFileGenerateTask extends ArrangedTask
{

	public void init()throws BOException
	{
		this.setExecuteTime(SearchFileConfig.STARTTIME);
		this.setTaskDesc("搜索数据文件生成定时任务");
	}

	public void doTask()
	{
		SearchFileGenerateBO.getInstance().generateAllSearchFile();
	}

}
