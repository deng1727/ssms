package com.aspire.ponaadmin.web.auditManagement.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.aspire.common.db.DAOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.DataAccessException;
import com.aspire.common.persistence.util.SQLCode;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.page.PageVOInterface;
import com.aspire.ponaadmin.web.auditManagement.vo.ChannelsInfoVO;
import com.aspire.ponaadmin.web.db.TransactionDB;

public class ChanelsInfoDao {

	/**
	 * �洢��־��ʵ�����
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(ChanelsInfoDao.class);

	/**
	 * singletonģʽ��ʵ��
	 */
	private static ChanelsInfoDao instance = new ChanelsInfoDao();

	/**
	 * ���췽������singletonʵ������
	 */
	private ChanelsInfoDao() {
	}

	/**
	 * ��ȡʵ���ķ���
	 */
	public static ChanelsInfoDao getInstance() {
		return instance;
	}

	private class ChannelInfoPageVo implements PageVOInterface {

		@Override
		public Object createObject() {
			return new ChannelsInfoVO();
		}

		@Override
		public void CopyValFromResultSet(Object content, ResultSet rs)
				throws SQLException {
			ChannelsInfoVO vo = (ChannelsInfoVO) content;
			vo.setCategoryId(rs.getString("categoryid"));
			vo.setCategoryName(rs.getString("name"));
			vo.setChannelsId(rs.getString("channelsid"));
			vo.setChannelsName(rs.getString("channelsname"));
		}

	}

	/**
	 * ��ѯ��������
	 * 
	 * @throws DAOException
	 */
	public void channelsInfoList(PageResult page) throws DAOException {
		String sqlCode = "com.aspire.ponaadmin.web.auditManagement.dao.ChanelsInfoDao.channelsInfoList";
		String sql = null;
		try {
			sql = SQLCode.getInstance().getSQLStatement(sqlCode);
			page.excute(sql, null, new ChannelInfoPageVo());
		} catch (DataAccessException e) {
			logger.debug("��ѯ������������ܹ�ϵ�б����", e);
			throw new DAOException("��ѯ������������ܹ�ϵ�б����", e);
		}
	}

	public void toAudit(String[] categoryId, String flag) {
		String sqlCode = "com.aspire.ponaadmin.web.auditManagement.dao.ChanelsInfoDao.toAudit";
		// �����������
		TransactionDB tdb = null;
		try {
			tdb = TransactionDB.getTransactionInstance();
			for (int i = 0; i < categoryId.length; i++) {
				tdb.executeBySQLCode(sqlCode, new Object[] { flag,categoryId[i]  });
			}
			// �ύ�������
			tdb.commit();
		} catch (Exception e) {
			// ִ�лع�
			tdb.rollback();
		} finally {
			if (tdb != null) {
				tdb.close();
			}
		}

	}

}
