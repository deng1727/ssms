package com.aspire.dotcard.syncAndroid.unifiedPackage.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.syncAndroid.dc.jms.MSGType;
import com.aspire.dotcard.syncAndroid.dc.jms.MSGUtil;
import com.aspire.dotcard.syncAndroid.ppms.PPMSDAO;
import com.aspire.dotcard.syncAndroid.ppms.ReceiveChangeVO;
import com.aspire.dotcard.syncAndroid.unifiedPackage.bo.UnifiedBo;
import com.aspire.dotcard.syncAndroid.unifiedPackage.vo.GoodsCenterMessagesVo;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.repository.goods.GoodsVO;

public class UnifiedDao {
	/**
	 * ��־����
	 */
	JLogger LOG = LoggerFactory.getLogger(UnifiedDao.class);
	private UnifiedDao() {
	}
	private TransactionDB transactionDB;
	private Map<MSGType, List<String>> feedback;//add by aiyan 2013-02-25 Ϊ����һ�������м�¼�����¼ܵ����ݶ���Ϣ��
	
	/**
	 * ����ģʽ
	 * 
	 * @return
	 */
	public static UnifiedDao getTransactionInstance( TransactionDB transactionDB) {
		UnifiedDao dao = new UnifiedDao();
		dao.transactionDB = transactionDB;
		dao.feedback = new HashMap<MSGType, List<String>>();
		return dao;
	}
	
	private boolean checkTransactionDB() {
		return transactionDB == null;
	}
	
	public void insertIntoProHandle(GoodsCenterMessagesVo  vo)
			throws DAOException {

		if (LOG.isDebugEnabled()) {
			LOG.debug("UnifiedDao.insertIntoProHandle() is beginning...");
		}
		if (checkTransactionDB()) {
			throw new DAOException("�������Ϊ�գ�����");
		}
		//������Ϣ��
		 
		String sqlCode3 = "UnifiedDao.insertIntoProHandle.INSERT";
		Object[] paras3 = { 
				vo.getAppId(),vo.getContentId(),vo.getAction(),vo.getChangeTime(),vo.getSendTime(),vo.getStatus()
		};
		transactionDB.executeBySQLCode(sqlCode3, paras3);
		
	}
	
	public void insertIntoChange(ReceiveChangeVO vo) throws DAOException{

		if (LOG.isDebugEnabled()) {
			LOG.debug("UnifiedDao.insertIntoChange() is beginning...");
		}
		if (checkTransactionDB()) {
			throw new DAOException("�������Ϊ�գ�����");
		}
		String sqlCode ="UnifiedDao.insertIntoChange().INSERT";
		Object[] paras3 = { 
				vo.getAppid(),vo.getEntityid(),vo.getId(),vo.getImagetype(),vo.getStatus(),vo.getType()
		};
		transactionDB.executeBySQLCode(sqlCode, paras3);
	}
	//   ����appid��Ӧ��C������;
	public void insertIntoGapp(ReceiveChangeVO vo) throws DAOException{

		if (LOG.isDebugEnabled()) {
			LOG.debug("UnifiedDao.insertIntoChange() is beginning...");
		}
		if (checkTransactionDB()) {
			throw new DAOException("�������Ϊ�գ�����");
		}
		String sqlCode ="UnifiedDao.insertIntoGapp().INSERT";
		Object[] paras3 = { 
				vo.getAppid(),vo.getEntityid(),vo.getId(),vo.getImagetype(),vo.getStatus(),vo.getType()
		};
		transactionDB.executeBySQLCode(sqlCode, paras3);
	}
	
	
	public List<ReceiveChangeVO> createReceiveChangeVOByMessage(GoodsCenterMessagesVo  vo) throws DAOException, SQLException{
		
		if (LOG.isDebugEnabled()) {
			LOG.debug("UnifiedDao.createReceiveChangeVOByMessage() is beginning...");
		}
		if (checkTransactionDB()) {
			throw new DAOException("�������Ϊ�գ�����");
		}
		ResultSet rs = null;
		String sqlCode ="SyncAndroid.APPInfoDAO.putPPMSReceive().INSERT";
		Object[] paras3 = { 
				
		};
		rs = DB.getInstance().queryBySQLCode(sqlCode, paras3);
		while (rs != null && rs.next()) {
			
			
			
		}
		return null;
	}
	
	
}
