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
     * ����ר����־
     */
    protected static JLogger REPORT_LOG = LoggerFactory.getLogger("reportimp.client");
	public void doTask()
	{
		try
		{
			LOG.info("���ֻ��ܸ���������ͳ�����ݱ����뿪ʼ");
			List list=A8StatisticImportBO.getInstance().downLoadReportFile();
			int count=A8StatisticImportBO.getInstance().saveCategoryStatistic(list);
			LOG.info("���ֻ��ܸ���������ͳ�����ݱ�������ɣ����ɹ�����"+count+"��ͳ�Ƽ�¼");
			REPORT_LOG.info("���ֻ��ܸ���������ͳ�����ݱ�������ɣ����ɹ�����"+count+"��ͳ�Ƽ�¼");
		} catch (Exception e)
		{
			LOG.error("���ֻ��ܸ���������ͳ�����ݱ��������",e);
			REPORT_LOG.error("���ֻ��ܸ���������ͳ�����ݱ��������",e);
		}

	}

	public void init() throws BOException
	{

		executeTime=TopDataConfig.get("A8_ImportTime");
		//this.setExecuteTime(executeTime);
		this.setTaskDesc("���ֻ��ܸ���������ͳ�����ݱ�����");
	}

}
