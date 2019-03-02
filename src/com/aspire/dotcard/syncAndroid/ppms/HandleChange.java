package com.aspire.dotcard.syncAndroid.ppms;

import java.util.List;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.db.TransactionDB;

public class HandleChange {
	private PretreatmentVO vo = null  ;
	JLogger LOG = LoggerFactory.getLogger(HandleChange.class);
	public HandleChange (PretreatmentVO newVo){
		if (null == this.vo ) {
			this.vo = newVo;
		}
		
	}
	public void doHandlePretreatmentVO(){
		TransactionDB tdb = null;
		
			try {
				tdb = TransactionDB.getTransactionInstance();
				PPMSDAO dao = PPMSDAO.getTransactionInstance(tdb);
				
				List<PriorityOutputIncrVO> cList = dao.getCByAppid(vo
						.getAppid());
				dao.updateChange(0, vo.getId(), cList);
				tdb.commit();
			} catch (Exception e) {
				LOG.error("预处理表到change表失败",e);
			}finally {
				if (null != tdb) {
					tdb.close();
				}
			}
			
		
	}
}
