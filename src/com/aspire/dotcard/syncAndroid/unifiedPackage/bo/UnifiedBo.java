package com.aspire.dotcard.syncAndroid.unifiedPackage.bo;

import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.syncAndroid.unifiedPackage.dao.UnifiedDao;
import com.aspire.dotcard.syncAndroid.unifiedPackage.vo.GoodsCenterMessagesVo;
import com.aspire.ponaadmin.web.db.TransactionDB;

public class UnifiedBo {
	/**
	 * ��־����
	 */
	JLogger LOG = LoggerFactory.getLogger(UnifiedBo.class);
	private static UnifiedBo bo = new UnifiedBo();
	private UnifiedBo() {
	}

	/**
	 * ����ģʽ
	 * 
	 * @return
	 */
	public static UnifiedBo getInstance() {

		return bo;
	}
	
	
	public void HandleGoodsCenterMessagesVos(List<GoodsCenterMessagesVo> vos) throws DAOException{
		TransactionDB tdb = null;
		try {
		    tdb = TransactionDB.getTransactionInstance();// һ���µ�JDBC����ʼ�ˡ�
            UnifiedDao dao = UnifiedDao.getTransactionInstance(tdb);
		for (GoodsCenterMessagesVo vo : vos) {
			vo.setStatus(-1);
			dao.insertIntoProHandle(vo);
		}
		tdb.commit();
		} catch (Exception e) {
			LOG.debug(e);
		}finally{
			if (null != tdb) {
				tdb.close();
			}
		}
            
		
		
		
	}
}
