package com.aspire.dotcard.basecomic.bo;


import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basecomic.common.BufferQueue;
import com.aspire.dotcard.basecomic.common.ReflectedTask;
import com.aspire.dotcard.basecomic.common.StatisticsCallback;
import com.aspire.dotcard.basecomic.dao.BaseComicDBOpration;
import com.aspire.dotcard.basecomic.template.DefaultImportTemplate;
import com.aspire.dotcard.basecomic.vo.TopicVO;
import com.aspire.dotcard.basecomic.vo.VO;

public class TopicImportBO extends DefaultImportTemplate{
	protected static JLogger logger = LoggerFactory.getLogger(BrandImportBO.class);

	public TopicImportBO() {
		super();
		this.nameRegex="topicNameRegex";
		this.tableName = "t_cb_category";
		this.key = "categoryValue|parentCategoryValue";
		this.fieldLength = 3;
	}

	public VO createVO(String[] field){
		return new TopicVO(field);
		
	}

	public BufferQueue createQueue(){
		return new BufferQueue();
	}
	
	public void insert(BufferQueue queue,VO vo, StatisticsCallback statisticsCallback){
        // 构造异步任务
        ReflectedTask task = new ReflectedTask(new BaseComicDBOpration(statisticsCallback), "insertTopic", 
        		new Object[]{vo},
        		new Class[]{com.aspire.dotcard.basecomic.vo.TopicVO.class});
        // 将任务加到运行器中
        queue.addTask(task);
	}
	
	
	public void update(BufferQueue queue,VO vo, StatisticsCallback statisticsCallback){
        // 构造异步任务
        ReflectedTask task = new ReflectedTask(new BaseComicDBOpration(statisticsCallback), "updateTopic", 
        		new Object[]{vo},
        		new Class[]{com.aspire.dotcard.basecomic.vo.TopicVO.class});
        // 将任务加到运行器中
        queue.addTask(task);
	}
	

}
