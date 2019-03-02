package com.aspire.dotcard.basecomic.bo;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basecomic.common.BufferQueue;
import com.aspire.dotcard.basecomic.common.Const;
import com.aspire.dotcard.basecomic.common.ReflectedTask;
import com.aspire.dotcard.basecomic.common.StatisticsCallback;
import com.aspire.dotcard.basecomic.dao.BaseComicDAO;
import com.aspire.dotcard.basecomic.dao.BaseComicDBOpration;
import com.aspire.dotcard.basecomic.template.DefaultImportTemplate;
import com.aspire.dotcard.basecomic.template.InsertImportTemplate;
import com.aspire.dotcard.basecomic.vo.AdapterVO;
import com.aspire.dotcard.basecomic.vo.TVSeriesVO;
import com.aspire.dotcard.basecomic.vo.VO;
import com.aspire.ponaadmin.web.db.TransactionDB;

public class AdapterImportBO extends DefaultImportTemplate {
	
	
	
	protected static JLogger logger = LoggerFactory.getLogger(AdapterImportBO.class);

	public AdapterImportBO() {
		super();
		this.nameRegex="adapterNameRegex";
		this.tableName = "t_cb_adapter";
		this.key = "id";
		this.fieldLength = 8;
	}

	public VO createVO(String[] field) {
	return new AdapterVO(field);

}

public BufferQueue createQueue() {
	return new BufferQueue(10);
}


	public void insert(BufferQueue queue,VO vo,StatisticsCallback statisticsCallback){
        // 构造异步任务
        ReflectedTask task = new ReflectedTask(new BaseComicDBOpration(statisticsCallback), "insertAdapter", new Object[]{vo}, new Class[]{com.aspire.dotcard.basecomic.vo.AdapterVO.class});
        // 将任务加到运行器中
        queue.addTask(task);
	}
	
	
	public void update(BufferQueue queue,VO vo,StatisticsCallback statisticsCallback){
        // 构造异步任务
        ReflectedTask task = new ReflectedTask(new BaseComicDBOpration(statisticsCallback), "updateAdapter", new Object[]{vo}, new Class[]{com.aspire.dotcard.basecomic.vo.AdapterVO.class});
        // 将任务加到运行器中
        queue.addTask(task);
	}

	protected int delete() {

		String sqlCode = "com.aspire.dotcard.basecomic.bo.AdapterImportBO.delete.SELECT";
		int count = BaseComicDAO.getInstance().count(sqlCode, null);
		
		int rowNum=0;
		if(count>0){//确实已经导入了本次业务的数据。于是可以删除首发以前的数据了。这样做的目的是怕基地给的是空文件或者没有导入进数据，
			//盲目删除之前的数据，形成门户无数据的可能性。
			sqlCode = "com.aspire.dotcard.basecomic.bo.AdapterImportBO.delete.DELETE";
			
			try {
				rowNum = BaseComicDAO.getInstance().executeBySQLCode(sqlCode, null);
			} catch (BOException e) {
				// TODO Auto-generated catch block
				logger.error(e);
			}
		}
		return rowNum;
		
	}

	protected void addData(VO vo, StatisticsCallback statisticsCallback) {
		// TODO Auto-generated method stub
		new BaseComicDBOpration(statisticsCallback)
				.insertAdapter((AdapterVO) vo);
	}

}
