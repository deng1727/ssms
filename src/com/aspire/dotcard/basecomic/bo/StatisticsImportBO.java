package com.aspire.dotcard.basecomic.bo;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basecomic.common.BufferQueue;
import com.aspire.dotcard.basecomic.common.ReflectedTask;
import com.aspire.dotcard.basecomic.common.StatisticsCallback;
import com.aspire.dotcard.basecomic.dao.BaseComicDBOpration;
import com.aspire.dotcard.basecomic.template.UpdateImportTemplate;
import com.aspire.dotcard.basecomic.vo.StatisticsVO;
import com.aspire.dotcard.basecomic.vo.VO;

public class StatisticsImportBO extends UpdateImportTemplate{
	protected static JLogger logger = LoggerFactory.getLogger(StatisticsImportBO.class);

	public StatisticsImportBO() {
		super();
		this.nameRegex="statisticsNameRegex";
		this.tableName = "t_cb_content";
		this.key = "id";
		this.fieldLength = 10;
	}

	public VO createVO(String[] field){
		return new StatisticsVO(field);
		
	}

	public BufferQueue createQueue(){
		return new BufferQueue();
	}

	public void update(BufferQueue queue,VO vo, StatisticsCallback statisticsCallback){
        // 构造异步任务
        ReflectedTask task = new ReflectedTask(new BaseComicDBOpration(statisticsCallback), "updateStatistics", 
        		new Object[]{vo},
        		new Class[]{com.aspire.dotcard.basecomic.vo.StatisticsVO.class});
        // 将任务加到运行器中
        queue.addTask(task);
	}

}
