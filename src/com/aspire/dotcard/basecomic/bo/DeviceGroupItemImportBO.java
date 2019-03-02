package com.aspire.dotcard.basecomic.bo;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basecomic.common.BufferQueue;
import com.aspire.dotcard.basecomic.common.ReflectedTask;
import com.aspire.dotcard.basecomic.common.StatisticsCallback;
import com.aspire.dotcard.basecomic.dao.BaseComicDAO;
import com.aspire.dotcard.basecomic.dao.BaseComicDBOpration;
import com.aspire.dotcard.basecomic.template.InsertImportTemplate;
import com.aspire.dotcard.basecomic.vo.DeviceGroupItemVO;
import com.aspire.dotcard.basecomic.vo.DeviceGroupVO;
import com.aspire.dotcard.basecomic.vo.VO;
import com.aspire.ponaadmin.web.db.TransactionDB;


public class DeviceGroupItemImportBO extends InsertImportTemplate{
	protected static JLogger logger = LoggerFactory.getLogger(CPImportBO.class);

	public DeviceGroupItemImportBO() {
		super();
		this.nameRegex="deviceGroupItemNameRegex";
//		this.tableName = "t_cb_deviceGroupItem";
//		this.key = "groupId|deviceId";
		this.fieldLength = 2;
	}

	public VO createVO(String[] field){
		return new DeviceGroupItemVO(field);
		
	}

	public BufferQueue createQueue(){
		return new BufferQueue();
	}
	
	public void insert(BufferQueue queue,VO vo, StatisticsCallback statisticsCallback){
        // 构造异步任务
        ReflectedTask task = new ReflectedTask(new BaseComicDBOpration(statisticsCallback), "insertDeviceGroupItem", 
        		new Object[]{vo},
        		new Class[]{com.aspire.dotcard.basecomic.vo.DeviceGroupItemVO.class});
        // 将任务加到运行器中
        queue.addTask(task);
	}
	
	protected int delete() {
		// String sql ="select count(1) from t_cb_devicegroupitem where status=1";
		String sqlCode = "com.aspire.dotcard.basecomic.bo.DeviceGroupItemImportBO.delete.SELECT";
		int count = BaseComicDAO.getInstance().count(sqlCode, null);
		int rowNum = 0;
		if (count > 0) {// 表示今天有全量的数据,则删除不是今天的数据。

			TransactionDB tdb = null;
			try {
				// sql = " delete from t_cb_devicegroupitem where status=0";
				String sqlCode1 = "com.aspire.dotcard.basecomic.bo.DeviceGroupItemImportBO.delete.DELETE";
				// sql="update t_cb_devicegroupitem c set status=0 where status=1";
				String sqlCode2 = "com.aspire.dotcard.basecomic.bo.DeviceGroupItemImportBO.delete.UPDATE";
				
				//事务模式
				tdb = TransactionDB.getTransactionInstance();
				tdb.executeBySQLCode(sqlCode1, null);
				tdb.executeBySQLCode(sqlCode2, null);
				tdb.commit();
			}catch(DAOException e){
				logger.error(e);
			}finally{
				if(tdb!=null){
					tdb.close();
				}
			}

		}
		return rowNum;
	}
	
//	protected int delete(){
//		//String sql = "select count(1) from t_cb_devicegroupitem where trunc(flow_time)=trunc(sysdate) ";
//		
//		String sqlCode = "com.aspire.dotcard.basecomic.bo.DeviceGroupItemImportBO.delete.SELECT";
//		int count = BaseComicDAO.getInstance().count(sqlCode, null);
//		int rowNum = 0;
//		if(count>0){//表示今天有全量的数据。删除不是今天的数据。
//			//sql = " delete from t_cb_devicegroupitem where  trunc(flow_time)!=trunc(sysdate)";
//			sqlCode = "com.aspire.dotcard.basecomic.bo.DeviceGroupItemImportBO.delete.DELETE";
//			try {
//				rowNum= BaseComicDAO.getInstance().executeBySQLCode(sqlCode, null);
//			} catch (BOException e) {
//				// TODO Auto-generated catch block
//				logger.error("删除非今天的机型出错！",e);
//			}
//			
//		}
//		return rowNum;
//	}
//	
	protected void addData(VO vo, StatisticsCallback statisticsCallback) {
		// TODO Auto-generated method stub
		new BaseComicDBOpration(statisticsCallback)
				.insertDeviceGroupItem((DeviceGroupItemVO) vo);
	}
	

}
