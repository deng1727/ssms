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
import com.aspire.dotcard.basecomic.vo.DeviceGroupVO;
import com.aspire.dotcard.basecomic.vo.VO;
import com.aspire.ponaadmin.web.db.TransactionDB;

public class DeviceGroupImportBO extends InsertImportTemplate{
	protected static JLogger logger = LoggerFactory.getLogger(DeviceGroupImportBO.class);

	public DeviceGroupImportBO() {
		super();
		this.nameRegex="deviceGroupNameRegex";
		this.fieldLength = 4;
	}

	public VO createVO(String[] field){
		return new DeviceGroupVO(field);
		
	}

	public BufferQueue createQueue(){
		return new BufferQueue();
	}
	
	
	protected int delete() {
		// String sql ="select count(1) from t_cb_devicegroup where status=1";
		String sqlCode = "com.aspire.dotcard.basecomic.bo.DeviceGroupImportBO.delete.SELECT";
		int count = BaseComicDAO.getInstance().count(sqlCode, null);
		int rowNum = 0;
		if (count > 0) {// 表示今天有全量的数据,则删除不是今天的数据。

			TransactionDB tdb = null;
			try {
				// sql = " delete from t_cb_devicegroup where status=0";
				String sqlCode1 = "com.aspire.dotcard.basecomic.bo.DeviceGroupImportBO.delete.DELETE";
				// sql="update t_cb_devicegroup c set status=0 where status=1";
				String sqlCode2 = "com.aspire.dotcard.basecomic.bo.DeviceGroupImportBO.delete.UPDATE";
				
				//事务模式
				tdb = TransactionDB.getTransactionInstance();
				tdb.executeBySQLCode(sqlCode1, null);
				tdb.executeBySQLCode(sqlCode2, null);
				tdb.commit();
	
				//非事务模式
//				rowNum = BaseComicDAO.getInstance().executeBySQLCode(sqlCode1,
//						null);
//				BaseComicDAO.getInstance().executeBySQLCode(sqlCode2, null);
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
	
	protected void addData(VO vo, StatisticsCallback statisticsCallback) {
		// TODO Auto-generated method stub
		new BaseComicDBOpration(statisticsCallback)
				.insertDeviceGroup((DeviceGroupVO) vo);
	}
	

}
