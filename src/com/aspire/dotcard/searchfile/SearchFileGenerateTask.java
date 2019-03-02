package com.aspire.dotcard.searchfile;

import com.aspire.common.exception.BOException;
import com.aspire.ponaadmin.web.util.ArrangedTask;

public class SearchFileGenerateTask extends ArrangedTask
{

	public void init()throws BOException
	{
		this.setExecuteTime(SearchFileConfig.STARTTIME);
		this.setTaskDesc("���������ļ����ɶ�ʱ����");
	}

	public void doTask()
	{
		SearchFileGenerateBO.getInstance().generateAllSearchFile();
	}

}
