package com.aspire.dotcard.reportdata.a8statistic;

import java.util.List;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.reportdata.TopDataConfig;
import com.aspire.ponaadmin.web.util.ArrangedTask;

public class A8StatisticImportTask extends ArrangedTask
{

	private static final JLogger LOG = LoggerFactory.getLogger(A8StatisticImportTask.class);
	/**
     * 报表专用日志
     */
    protected static JLogger REPORT_LOG = LoggerFactory.getLogger("reportimp.client");
	public void doTask()
	{
		try
		{
			LOG.info("音乐货架歌曲试听日统计数据报表导入开始");
			List list=A8StatisticImportBO.getInstance().downLoadReportFile();
			int count=A8StatisticImportBO.getInstance().saveCategoryStatistic(list);
			LOG.info("音乐货架歌曲试听日统计数据报表导入完成，共成功导入"+count+"个统计记录");
			REPORT_LOG.info("音乐货架歌曲试听日统计数据报表导入完成，共成功导入"+count+"个统计记录");
		} catch (Exception e)
		{
			LOG.error("音乐货架歌曲试听日统计数据报表导入出错。",e);
			REPORT_LOG.error("音乐货架歌曲试听日统计数据报表导入出错。",e);
		}

	}

	public void init() throws BOException
	{

		executeTime=TopDataConfig.get("A8_ImportTime");
		//this.setExecuteTime(executeTime);
		this.setTaskDesc("音乐货架歌曲试听日统计数据报表导入");
	}

}
