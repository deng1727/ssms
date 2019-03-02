package com.aspire.dotcard.basecomic.bo;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basecomic.common.BufferQueue;
import com.aspire.dotcard.basecomic.common.ReflectedTask;
import com.aspire.dotcard.basecomic.common.StatisticsCallback;
import com.aspire.dotcard.basecomic.dao.BaseComicDBOpration;
import com.aspire.dotcard.basecomic.template.DefaultImportTemplate;
import com.aspire.dotcard.basecomic.vo.CPVO;
import com.aspire.dotcard.basecomic.vo.VO;

public class CPImportBO extends DefaultImportTemplate{
	protected static JLogger logger = LoggerFactory.getLogger(CPImportBO.class);

	public CPImportBO() {
		super();
		this.nameRegex="cpNameRegex";
		this.tableName = "t_cb_cp";
		this.key = "cpid";
		this.fieldLength = 2;
	}

	public VO createVO(String[] field){
		return new CPVO(field);
		
	}

	public BufferQueue createQueue(){
		return new BufferQueue();
	}
	
	public void insert(BufferQueue queue,VO vo, StatisticsCallback statisticsCallback){
        // 构造异步任务
        ReflectedTask task = new ReflectedTask(new BaseComicDBOpration(statisticsCallback), "insertCP", 
        		new Object[]{vo},
        		new Class[]{com.aspire.dotcard.basecomic.vo.CPVO.class});
        // 将任务加到运行器中
        queue.addTask(task);
	}
	
	
	public void update(BufferQueue queue,VO vo, StatisticsCallback statisticsCallback){
        // 构造异步任务
        ReflectedTask task = new ReflectedTask(new BaseComicDBOpration(statisticsCallback), "updateCP", 
        		new Object[]{vo},
        		new Class[]{com.aspire.dotcard.basecomic.vo.CPVO.class});
        // 将任务加到运行器中
        queue.addTask(task);
	}
	

}
