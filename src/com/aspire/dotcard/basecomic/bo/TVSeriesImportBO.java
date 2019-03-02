package com.aspire.dotcard.basecomic.bo;

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
import com.aspire.dotcard.basecomic.vo.TVSeriesVO;
import com.aspire.dotcard.basecomic.vo.VO;

public class TVSeriesImportBO extends DefaultImportTemplate{
	protected static JLogger logger = LoggerFactory.getLogger(TVSeriesImportBO.class);

	public TVSeriesImportBO() {
		super();
		this.nameRegex="tvSeriesNameRegex";
		this.tableName = "t_cb_content";
		this.key = "id";
		this.fieldLength = 39;
	}

	public VO createVO(String[] field){
		return new TVSeriesVO(field);
		
	}
	public BufferQueue createQueue(){
		return new BufferQueue(10);
	}

	public void insert(BufferQueue queue,VO vo,StatisticsCallback statisticsCallback){
        // 构造异步任务
        ReflectedTask task = new ReflectedTask(new BaseComicDBOpration(statisticsCallback), "insertTVSeries", new Object[]{vo}, new Class[]{com.aspire.dotcard.basecomic.vo.TVSeriesVO.class});
        // 将任务加到运行器中
        queue.addTask(task);
	}
	
	
	public void update(BufferQueue queue,VO vo,StatisticsCallback statisticsCallback){
        // 构造异步任务
        ReflectedTask task = new ReflectedTask(new BaseComicDBOpration(statisticsCallback), "updateTVSeries", new Object[]{vo}, new Class[]{com.aspire.dotcard.basecomic.vo.TVSeriesVO.class});
        // 将任务加到运行器中
        queue.addTask(task);
	}

	//删除不是今天同步的数据（漫画）。
	protected int delete() {
		// TODO Auto-generated method stub
		String[] para = new String[]{Const.TYPE_TVSERIES};
		//String sql ="select count(1) from t_cb_content c where r.type=? and trunc(flow_time)=trunc(sysdate)";
		String sqlCode = "com.aspire.dotcard.basecomic.bo.content.delete.SELECT";
		int count = BaseComicDAO.getInstance().count(sqlCode, para);
		
		int rowNum=0;
		if(count>0){//确实已经导入了本次业务的数据。于是可以删除首发以前的数据了。这样做的目的是怕基地给的是空文件或者没有导入进数据，
			//盲目删除之前的数据，形成门户无数据的可能性。
			//sql="delete from t_cb_content c where c.type=? and trunc(flow_time)<trunc(sysdate)";
			sqlCode = "com.aspire.dotcard.basecomic.bo.content.delete.DELETE";
			
			try {
				rowNum = BaseComicDAO.getInstance().executeBySQLCode(sqlCode, para);
			} catch (BOException e) {
				// TODO Auto-generated catch block
				logger.error(e);
			}
		}
		return rowNum;
		
		
	}

}
