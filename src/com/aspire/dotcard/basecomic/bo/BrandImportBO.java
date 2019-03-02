package com.aspire.dotcard.basecomic.bo;


import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basecomic.common.BufferQueue;
import com.aspire.dotcard.basecomic.common.ReflectedTask;
import com.aspire.dotcard.basecomic.common.StatisticsCallback;
import com.aspire.dotcard.basecomic.dao.BaseComicDBOpration;
import com.aspire.dotcard.basecomic.template.DefaultImportTemplate;
import com.aspire.dotcard.basecomic.vo.BrandVO;
import com.aspire.dotcard.basecomic.vo.VO;

public class BrandImportBO extends DefaultImportTemplate{
	protected static JLogger logger = LoggerFactory.getLogger(BrandImportBO.class);

	public BrandImportBO() {
		super();
		this.nameRegex="brandNameRegex";
		this.tableName = "t_cb_category";
		this.key = "categoryValue|parentCategoryValue";
		this.fieldLength = 8;
	}

	public VO createVO(String[] field){
		return new BrandVO(field);
		
	}

	public BufferQueue createQueue(){
		return new BufferQueue();
	}
	
	public void insert(BufferQueue queue,VO vo, StatisticsCallback statisticsCallback){
        // 构造异步任务
        ReflectedTask task = new ReflectedTask(new BaseComicDBOpration(statisticsCallback), "insertBrand", 
        		new Object[]{vo},
        		new Class[]{com.aspire.dotcard.basecomic.vo.BrandVO.class});
        // 将任务加到运行器中
        queue.addTask(task);
	}
	
	
	public void update(BufferQueue queue,VO vo, StatisticsCallback statisticsCallback){
        // 构造异步任务
        ReflectedTask task = new ReflectedTask(new BaseComicDBOpration(statisticsCallback), "updateBrand", 
        		new Object[]{vo},
        		new Class[]{com.aspire.dotcard.basecomic.vo.BrandVO.class});
        // 将任务加到运行器中
        queue.addTask(task);
	}
	

}
