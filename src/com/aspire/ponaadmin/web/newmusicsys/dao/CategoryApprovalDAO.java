package com.aspire.ponaadmin.web.newmusicsys.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.db.TransactionDB;

public class CategoryApprovalDAO {

	/**
	 * ��¼��־��ʵ������
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(CategoryApprovalDAO.class);

	/**
	 * singletonģʽ��ʵ��
	 */
	private static CategoryApprovalDAO instance = new CategoryApprovalDAO();

	/**
	 * ���췽������singletonģʽ����
	 */
	private CategoryApprovalDAO() {
	}

	/**
	 * ��ȡʵ��
	 * 
	 * @return ʵ��
	 */
	public static CategoryApprovalDAO getInstance() {
		return instance;
	}

	/**
	 * ��ѯ���ֻ���
	 * 
	 * @param categoryId
	 *            ���ܱ��
	 * @return
	 * @throws BOException
	 */
	public Map<String, Object> queryCategoryListItem(String categoryId)
			throws BOException {
		ResultSet rs = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			rs = DB.getInstance()
					.queryBySQLCode(
							"com.aspire.ponaadmin.web.newmusicsys.dao.CategoryApprovalDAO.queryCategoryListItem",
							new Object[] { categoryId });
			while (rs != null && rs.next()) {
				map.put("categoryId", rs.getString("categoryid"));
				map.put("goods_status", rs.getString("goods_status"));
				map.put("delpro_status", rs.getString("delpro_status"));
				map.put("music_status", rs.getString("music_status"));
			}
		} catch (SQLException e) {
			logger.error("��ѯ���ֻ��ܱ��쳣", e);
			throw new BOException("��ѯ���ֻ��ܱ��쳣", e);
		} catch (DAOException e) {
			logger.error("��ѯ���ֻ��ܱ��쳣", e);
			throw new BOException("��ѯ���ֻ��ܱ��쳣", e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					logger.error(e);
				}
			}
		}
		return map;
	}

	/**
	 * �������ֻ�������״̬
	 * 
	 * @param tdb
	 * @param categoryId
	 *            ���ܱ���
	 * @throws BOException
	 */
	public void categoryApprovalPass(TransactionDB tdb, String status,
			String categoryId) throws BOException {
		try {
			tdb.executeBySQLCode(
					"com.aspire.ponaadmin.web.newmusicsys.dao.CategoryApprovalDAO.categoryApprovalPass",
					new Object[] { status, categoryId });
		} catch (DAOException e) {
			logger.error("�������ֻ�������״̬�����쳣��", e);
			throw new BOException("�������ֻ�������״̬�����쳣��", e);
		}

	}

	/**
	 * ��˲�ͨ����ɾ�������ֻ��ܻع���ԭ����״̬
	 * 
	 * @param tdb
	 * @param categoryId
	 * @throws BOException
	 */
	public void deleteNoPass(TransactionDB tdb, String categoryId)
			throws BOException {
		try {
			tdb.executeBySQLCode(
					"com.aspire.ponaadmin.web.newmusicsys.dao.CategoryApprovalDAO.deleteNoPass",
					new Object[] { categoryId });
		} catch (DAOException e) {
			logger.error("��˲�ͨ����ɾ�������ֻ��ܻع���ԭ����״̬�����쳣", e);
			throw new BOException("��˲�ͨ����ɾ�������ֻ��ܻع���ԭ����״̬�����쳣", e);
		}
	}

	/**
	 * ����������ѯ������Ʒ���Ƿ���ڼ�¼
	 * 
	 * @param categoryId
	 *            ���ܱ���
	 * @param status
	 *            ״̬
	 * @return
	 * @throws BOException
	 */
	public boolean isApproval(String categoryId, String status)
			throws BOException {
		String sqlCode = "com.aspire.ponaadmin.web.newmusicsys.dao.CategoryApprovalDAO.isApproval";
		ResultSet rs = null;
		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode,
					new Object[] { categoryId, status, });
			while (rs.next()) {
				return true;
			}
		} catch (DAOException e) {
			logger.error("��ѯ������Ʒ�����쳣:", e);
			throw new BOException("��ѯ������Ʒ�����쳣:", e);
		} catch (SQLException e) {
			logger.error("��ѯ������Ʒ�����쳣:", e);
			throw new BOException("��ѯ������Ʒ�����쳣:", e);
		}
		return false;
	}

	/**
	 * ����������Ʒ�ύ����
	 * 
	 * @param tdb
	 * @param contentId
	 *            ������Ʒ���е�Id
	 * @param approvalStatus
	 *            ����״̬ 0 �༭��1 �ѷ�����2 ������;3 ������ͨ��
	 * @param categoryId
	 *            ���ֻ��ܱ��
	 * @throws BOException
	 */
	public void categoryGoodsApproval(TransactionDB tdb, String[] contentId,
			String approvalStatus, String categoryId) throws BOException {
		for (int i = 0; i < contentId.length; i++) {
			try {
				String[] strs = contentId[i].split("#");
				Map<String, Object> map = this.queryGoodsItem(strs[0],
						categoryId);
				if (!"2".equals(map.get("delflag"))) {
					if ("1".equals(approvalStatus)) {
						this.deleteCategoryGoodsItem(tdb, strs[0], categoryId);
					} else if("2".equals(approvalStatus)){
						this.approvalCategoryGoodsItem(tdb, strs[0], categoryId);
					} else{
						this.callBackCategoryGoodsItem(tdb, strs[0], categoryId);
					}
				} else {
					tdb.executeBySQLCode(
							"com.aspire.ponaadmin.web.newmusicsys.dao.CategoryApprovalDAO.categoryGoodsApproval",
							new Object[] { approvalStatus, categoryId, strs[0] });
				}
			} catch (DAOException e) {
				logger.error("����������Ʒ�ύ�����쳣:", e);
				throw new BOException("����������Ʒ�ύ�����쳣:", e);
			}
		}

	}

	/**
	 * �������ֻ��ܱ�����Ʒ��״̬
	 * 
	 * @param tdb
	 * @param categoryId
	 *            ���ֻ��ܱ���
	 * @param status
	 *            ��Ʒ��״̬ 0 �༭��1 �ѷ�����2 ������;3 ������ͨ��
	 * @throws BOException
	 */
	public void categoryApproval(TransactionDB tdb, String categoryId,
			String status) throws BOException {
		try {
			tdb.executeBySQLCode(
					"com.aspire.ponaadmin.web.newmusicsys.dao.CategoryApprovalDAO.categoryApproval",
					new Object[] { status, categoryId });
		} catch (DAOException e) {
			logger.error("�������ֻ��ܱ�����Ʒ��״̬�쳣:", e);
			throw new BOException("�������ֻ��ܱ�����Ʒ��״̬�쳣:", e);
		}
	}

	/**
	 * ȫ��������Ʒ�ύ����
	 * 
	 * @param tdb
	 * @param categoryId
	 *            ���ֻ��ܱ��
	 * @param approvalStatus
	 *            ����״̬ 0 �༭��1 �ѷ�����2 ������;3 ������ͨ��
	 * @param status
	 *            ��Ʒ��״̬ 0 �༭��1 �ѷ�����2 ������;3 ������ͨ��
	 * @throws BOException
	 */
	public void categoryGoodsAllApproval(TransactionDB tdb, String categoryId,
			String approvalStatus, String status) throws BOException {
		try {
			tdb.executeBySQLCode(
					"com.aspire.ponaadmin.web.newmusicsys.dao.CategoryApprovalDAO.categoryGoodsAllApproval",
					new Object[] { approvalStatus, categoryId, status });
		} catch (DAOException e) {
			logger.error("ȫ��������Ʒ�ύ�����쳣:", e);
			throw new BOException("ȫ��������Ʒ�ύ�����쳣:", e);
		}
		this.categoryApproval(tdb, categoryId, "2");

	}

	/**
	 * ���ݻ���id��ѯ������������Ʒ
	 * 
	 * @param categoryId
	 *            ���ܱ��
	 * @return
	 * @throws BOException
	 */
	public List<Map<String, Object>> queryCategoryGoodsList(String categoryId)
			throws BOException {
		ResultSet rs = null;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			rs = DB.getInstance()
					.queryBySQLCode(
							"com.aspire.ponaadmin.web.newmusicsys.dao.CategoryApprovalDAO.queryCategoryGoodsList",
							new Object[] { categoryId });
			while (rs != null && rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", rs.getString("id"));
				map.put("verify_status", rs.getString("verify_status"));
				map.put("delflag", rs.getString("delflag"));
				list.add(map);
			}
		} catch (SQLException e) {
			logger.error("���ݻ���id��ѯ������������Ʒ�쳣��", e);
			throw new BOException("���ݻ���id��ѯ������������Ʒ�쳣��", e);
		} catch (DAOException e) {
			logger.error("���ݻ���id��ѯ������������Ʒ�쳣��", e);
			throw new BOException("���ݻ���id��ѯ������������Ʒ�쳣��", e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					logger.error(e);
				}
			}
		}
		return list;
	}

	/**
	 * ɾ��һ��������Ʒ��¼
	 * 
	 * @param tdb
	 * @param id
	 *            ������Ʒid
	 * @throws BOException
	 */
	public void deleteCategoryGoodsItem(TransactionDB tdb, String id,
			String categoryId) throws BOException {
		try {
			tdb.executeBySQLCode(
					"com.aspire.ponaadmin.web.newmusicsys.dao.CategoryApprovalDAO.deleteCategoryGoodsItem",
					new Object[] { id, categoryId });
		} catch (DAOException e) {
			logger.error("ɾ��һ��������Ʒ�쳣��", e);
			throw new BOException("ɾ��һ��������Ʒ�쳣��", e);
		}

	}

	/**
	 * ����������Ʒ��˲�ͬ��ʱ��ɾ������Ʒ��״̬���лع�
	 * 
	 * @param tdb
	 * @param id
	 *            ������Ʒid
	 * @throws BOException
	 */
	public void callBackCategoryGoodsItem(TransactionDB tdb, String id,
			String categoryId) throws BOException {
		try {
			tdb.executeBySQLCode(
					"com.aspire.ponaadmin.web.newmusicsys.dao.CategoryApprovalDAO.callBackCategoryGoodsItem",
					new Object[] { id, categoryId });
		} catch (DAOException e) {
			logger.error("����������Ʒ��˲�ͬ��ʱ��ɾ������Ʒ��״̬���лع��쳣��", e);
			throw new BOException("����������Ʒ��˲�ͬ��ʱ��ɾ������Ʒ��״̬���лع��쳣��", e);
		}

	}

	/**
	 * ����������Ʒid��ѯ��������Ƶ��Ʒ
	 * 
	 * @param categoryId
	 *            ���ܱ��
	 * @return
	 * @throws BOException
	 */
	public Map<String, Object> queryGoodsItem(String contentId,
			String categoryId) throws BOException {
		ResultSet rs = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			rs = DB.getInstance()
					.queryBySQLCode(
							"com.aspire.ponaadmin.web.newmusicsys.dao.CategoryApprovalDAO.queryGoodsItem",
							new Object[] { contentId, categoryId });
			while (rs != null && rs.next()) {
				map.put("id", rs.getString("id"));
				map.put("verify_status", rs.getString("verify_status"));
				map.put("delflag", rs.getString("delflag"));
			}
		} catch (SQLException e) {
			logger.error("����������Ʒid��ѯ������������Ʒ�쳣��", e);
			throw new BOException("����������Ʒid��ѯ������������Ʒ�쳣��", e);
		} catch (DAOException e) {
			logger.error("����������Ʒid��ѯ������������Ʒ�쳣��", e);
			throw new BOException("����������Ʒid��ѯ������������Ʒ�쳣��", e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					logger.error(e);
				}
			}
		}
		return map;
	}
	
	/**
	 * һ��������Ʒ��¼�ύ����
	 * 
	 * @param tdb
	 * @param id
	 *            ������Ʒid
	 * @throws BOException
	 */
	public void approvalCategoryGoodsItem(TransactionDB tdb, String id,
			String categoryId) throws BOException {
		try {
			tdb.executeBySQLCode(
					"com.aspire.ponaadmin.web.newmusicsys.dao.CategoryApprovalDAO.approvalCategoryGoodsItem",
					new Object[] { id, categoryId });
		} catch (DAOException e) {
			logger.error("һ��������Ʒ��¼�ύ�����쳣��", e);
			throw new BOException("һ��������Ʒ��¼�ύ�����쳣��", e);
		}

	}

}
