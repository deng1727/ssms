package com.aspire.dotcard.basecomic.bo;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basecomic.common.BufferQueue;
import com.aspire.dotcard.basecomic.common.ReflectedTask;
import com.aspire.dotcard.basecomic.common.StatisticsCallback;
import com.aspire.dotcard.basecomic.dao.BaseComicDAO;
import com.aspire.dotcard.basecomic.dao.BaseComicDBOpration;
import com.aspire.dotcard.basecomic.template.DefaultImportTemplate;
import com.aspire.dotcard.basecomic.vo.ChapterVO;
import com.aspire.dotcard.basecomic.vo.VO;

public class ChapterImportBO extends DefaultImportTemplate{
	protected static JLogger logger = LoggerFactory.getLogger(ChapterImportBO.class);

	public ChapterImportBO() {
		super();
		this.nameRegex="chapterNameRegex";
		this.tableName = "t_cb_chapter";
		this.key = "CHAPTERID";
		this.fieldLength = 13;
	}

	public VO createVO(String[] field){
		return new ChapterVO(field);
		
	}

	public BufferQueue createQueue(){
		return new BufferQueue(10);
	}
	
	

	@Override
	protected void insert(BufferQueue queue, VO vo,
			StatisticsCallback statisticsCallback) {
		// �����첽����
        ReflectedTask task = new ReflectedTask(new BaseComicDBOpration(statisticsCallback), "insertChapter", new Object[]{vo}, new Class[]{com.aspire.dotcard.basecomic.vo.ChapterVO.class});
        // ������ӵ���������
        queue.addTask(task);
	}

	@Override
	protected void update(BufferQueue queue, VO vo,
			StatisticsCallback statisticsCallback) {
		// �����첽����
        ReflectedTask task = new ReflectedTask(new BaseComicDBOpration(statisticsCallback), "updateChapter", new Object[]{vo}, new Class[]{com.aspire.dotcard.basecomic.vo.ChapterVO.class});
        // ������ӵ���������
        queue.addTask(task);
	}
	
	//ɾ�����ǽ���ͬ�������ݣ���������
	protected int delete() {
		//String sql ="select count(1) from t_cb_chapter c where  trunc(flow_time)=trunc(sysdate)";
		String sqlCode = "com.aspire.dotcard.basecomic.bo.ChapterImportBO.delete.SELECT";
		int count = BaseComicDAO.getInstance().count(sqlCode,null);
		
		int rowNum=0;
		if(count>0){//ȷʵ�Ѿ������˱���ҵ������ݡ����ǿ���ɾ���׷���ǰ�������ˡ���������Ŀ�����»��ظ����ǿ��ļ�����û�е�������ݣ�
			//äĿɾ��֮ǰ�����ݣ��γ��Ż������ݵĿ����ԡ�
			//sql="delete from t_cb_chapter c where trunc(flow_time)<trunc(sysdate)";
			sqlCode = "com.aspire.dotcard.basecomic.bo.ChapterImportBO.delete.DELETE";
			
			try {
				rowNum = BaseComicDAO.getInstance().executeBySQLCode(sqlCode, null);
			} catch (BOException e) {
				// TODO Auto-generated catch block
				logger.error(e);
			}
		}
		return rowNum;
	}
	
	protected void addData(VO vo,StatisticsCallback statisticsCallback) {
		// TODO Auto-generated method stub
		new BaseComicDBOpration(statisticsCallback).insertChapter((ChapterVO)vo);
	}

	/*protected int delete() { 
		// TODO Auto-generated method stub
		//String sql ="select count(1) from t_cb_chapter where status=1";
		String sqlCode = "com.aspire.dotcard.basecomic.bo.ChapterImportBO.delete.SELECT";
		int count = BaseComicDAO.getInstance().count(sqlCode,null);
		
		int rowNum=0;
		if(count>0){//ȷʵ�Ѿ������˱���ҵ������ݡ����ǿ���ɾ���׷���ǰ�������ˡ���������Ŀ�����»��ظ����ǿ��ļ�����û�е�������ݣ�
			//äĿɾ��֮ǰ�����ݣ��γ��Ż������ݵĿ����ԡ�
			
			TransactionDB tdb = null;
			try {
				//sql="delete from t_cb_chapter c where status=0";
				String sqlCode1 = "com.aspire.dotcard.basecomic.bo.ChapterImportBO.delete.DELETE";
				//sql="update t_cb_chapter c set status=0 where status=1";
				String sqlCode2 = "com.aspire.dotcard.basecomic.bo.ChapterImportBO.delete.UPDATE";
				
				//����ģʽ
				tdb = TransactionDB.getTransactionInstance();
				tdb.executeBySQLCode(sqlCode1, null);
				tdb.executeBySQLCode(sqlCode2, null);
				tdb.commit();
				
//				rowNum = BaseComicDAO.getInstance().executeBySQLCode(sqlCode1,null);
//				BaseComicDAO.getInstance().executeBySQLCode(sqlCode2,null);
			}catch(DAOException e){
				logger.error(e);
			}finally{
				if(tdb!=null){
					tdb.close();
				}
			}
		}
		return rowNum;
	}*/

}
