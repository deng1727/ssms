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
        // �����첽����
        ReflectedTask task = new ReflectedTask(new BaseComicDBOpration(statisticsCallback), "insertAdapter", new Object[]{vo}, new Class[]{com.aspire.dotcard.basecomic.vo.AdapterVO.class});
        // ������ӵ���������
        queue.addTask(task);
	}
	
	
	public void update(BufferQueue queue,VO vo,StatisticsCallback statisticsCallback){
        // �����첽����
        ReflectedTask task = new ReflectedTask(new BaseComicDBOpration(statisticsCallback), "updateAdapter", new Object[]{vo}, new Class[]{com.aspire.dotcard.basecomic.vo.AdapterVO.class});
        // ������ӵ���������
        queue.addTask(task);
	}

	protected int delete() {

		String sqlCode = "com.aspire.dotcard.basecomic.bo.AdapterImportBO.delete.SELECT";
		int count = BaseComicDAO.getInstance().count(sqlCode, null);
		
		int rowNum=0;
		if(count>0){//ȷʵ�Ѿ������˱���ҵ������ݡ����ǿ���ɾ���׷���ǰ�������ˡ���������Ŀ�����»��ظ����ǿ��ļ�����û�е�������ݣ�
			//äĿɾ��֮ǰ�����ݣ��γ��Ż������ݵĿ����ԡ�
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
