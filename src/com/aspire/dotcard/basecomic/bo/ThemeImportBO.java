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
import com.aspire.dotcard.basecomic.vo.ThemeVO;
import com.aspire.dotcard.basecomic.vo.VO;

public class ThemeImportBO extends DefaultImportTemplate{
	protected static JLogger logger = LoggerFactory.getLogger(ThemeImportBO.class);

	public ThemeImportBO() {
		super();
		this.nameRegex="themeNameRegex";
		this.fieldLength = 27;
		this.tableName = "t_cb_content";
		this.key = "id";
	}

	public VO createVO(String[] field){
		return new ThemeVO(field);
		
	}
	
	public BufferQueue createQueue(){
		return new BufferQueue();
	}

	protected void addData(VO vo, StatisticsCallback statisticsCallback) {
		// TODO Auto-generated method stub
		new BaseComicDBOpration(statisticsCallback).insertTheme((ThemeVO)vo);
		
	}

//	protected int delete() {
//		// TODO Auto-generated method stub
//		//String sql ="select count(1) from t_cb_content where type="101" and sync_status=1";
//		String sqlCode = "com.aspire.dotcard.basecomic.bo.ThemeImportBO.delete.SELECT";
//		int count = BaseComicDAO.getInstance().count(sqlCode,null);
//		
//		int rowNum=0;
//		if(count>0){//ȷʵ�Ѿ������˱���ҵ������ݡ����ǿ���ɾ����ǰ�������ˡ���������Ŀ�����»��ظ����ǿ��ļ�����û�е�������ݣ�
//			//äĿɾ��֮ǰ�����ݣ��γ��Ż������ݵĿ����ԡ�
//			
//			
//			try {
//				//sql="delete from t_cb_content c where type="101" and sync_status=1";
//				String sqlCode1 = "com.aspire.dotcard.basecomic.bo.ThemeImportBO.delete.DELETE";
//				//sql="update t_cb_content c set sync_status=0 where type="101" and sync_status=1";
//				String sqlCode2 = "com.aspire.dotcard.basecomic.bo.ThemeImportBO.delete.UPDATE";
//				rowNum = BaseComicDAO.getInstance().executeBySQLCode(sqlCode1,null);
//				BaseComicDAO.getInstance().executeBySQLCode(sqlCode2,null);
//			} catch (BOException e) {
//				// TODO Auto-generated catch block
//				logger.error(e);
//			}
//		}
//		return rowNum;
//	}

	protected void insert(BufferQueue queue,VO vo, StatisticsCallback statisticsCallback){
		// TODO Auto-generated method stub
        // �����첽����
        ReflectedTask task = new ReflectedTask(new BaseComicDBOpration(statisticsCallback), "insertTheme", new Object[]{vo}, new Class[]{com.aspire.dotcard.basecomic.vo.ThemeVO.class});
        // ������ӵ���������
        queue.addTask(task);
	}
	
	
	protected void update(BufferQueue queue,VO vo,StatisticsCallback statisticsCallback){
		// TODO Auto-generated method stub
        // �����첽����
        ReflectedTask task = new ReflectedTask(new BaseComicDBOpration(statisticsCallback), "updateTheme", new Object[]{vo}, new Class[]{com.aspire.dotcard.basecomic.vo.ThemeVO.class});
        // ������ӵ���������
        queue.addTask(task);
	}
	
	//ɾ�����ǽ���ͬ�������ݣ����⣩��
	protected int delete() {
		// TODO Auto-generated method stub
		String[] para = new String[]{Const.TYPE_THEME};
		//String sql ="select count(1) from t_cb_content c where r.type=? and trunc(flow_time)=trunc(sysdate)";
		String sqlCode = "com.aspire.dotcard.basecomic.bo.content.delete.SELECT";
		int count = BaseComicDAO.getInstance().count(sqlCode, para);
		
		int rowNum=0;
		if(count>0){//ȷʵ�Ѿ������˱���ҵ������ݡ����ǿ���ɾ���׷���ǰ�������ˡ���������Ŀ�����»��ظ����ǿ��ļ�����û�е�������ݣ�
			//äĿɾ��֮ǰ�����ݣ��γ��Ż������ݵĿ����ԡ�
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
