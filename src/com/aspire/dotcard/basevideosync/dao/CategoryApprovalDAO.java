package com.aspire.dotcard.basevideosync.dao;

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
/**
 * 
 * @author duyongchun
 *
 */
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
	 * ��ѯ��Ƶ����
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
							"com.aspire.ponaadmin.web.newvideosys.dao.CategoryApprovalDAO.queryCategoryListItem",
		//select c.categoryid,c.goods_status,c.delpro_status,c.video_status from t_v_category c where c.categoryid = ?
							new Object[] { categoryId });
			while (rs != null && rs.next()) {
				map.put("categoryId", rs.getString("categoryid"));
				map.put("goods_status", rs.getString("goods_status"));
				map.put("delpro_status", rs.getInt("delpro_status")+"");
				map.put("video_status", rs.getString("video_status"));
			}
		} catch (SQLException e) {
			logger.error("��ѯPOMS���ܱ��쳣", e);
			throw new BOException("��ѯPOMS���ܱ��쳣", e);
		} catch (DAOException e) {
			logger.error("��ѯPOMS���ܱ��쳣", e);
			throw new BOException("��ѯPOMS���ܱ��쳣", e);
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
					"com.aspire.ponaadmin.web.newvideosys.dao.CategoryApprovalDAO.categoryApprovalPass",
					//sql:update t_v_category c set c.video_status = ?,c.LUPDATE=sysdate,c.DELPRO_STATUS=2
					//where c.categoryid = ?
					new Object[] { status, categoryId });
		} catch (DAOException e) {
			logger.error("������Ƶ��������״̬�����쳣��", e);
			throw new BOException("������Ƶ��������״̬�����쳣��", e);
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
					"com.aspire.ponaadmin.web.newvideosys.dao.CategoryApprovalDAO.deleteNoPass",
					//sql:update t_v_category c set c.video_status = c.delpro_status,c.delpro_status = 2 ,c.LUPDDATE=sysdate
					//where c.categoryid = ?
					new Object[] { categoryId });
		} catch (DAOException e) {
			logger.error("��˲�ͨ����ɾ������Ƶ���ܻع���ԭ����״̬�����쳣", e);
			throw new BOException("��˲�ͨ����ɾ������Ƶ���ܻع���ԭ����״̬�����쳣", e);
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
		String sqlCode = "com.aspire.dotcard.basevideosync.dao.CategoryApprovalDAO.isApproval";
		ResultSet rs = null;
		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode,
					new Object[] { categoryId, status, });
			while (rs.next()) {
				return true;
			}
		} catch (DAOException e) {
			logger.error("��ѯPOMS��Ʒ�����쳣:", e);
			throw new BOException("��ѯPOMS��Ʒ�����쳣:", e);
		} catch (SQLException e) {
			logger.error("��ѯPOMS��Ʒ�����쳣:", e);
			throw new BOException("��ѯPOMS��Ʒ�����쳣:", e);
		}
		return false;
	}

	/**
	 * ����POMS��Ʒ�ύ����
	 * 
	 * @param tdb
	 * @param contentId
	 *            POMS��Ʒ���е�Id
	 * @param approvalStatus
	 *            ����״̬ 0 �༭��1 �ѷ�����2 ������;3 ������ͨ��
	 * @param categoryId
	 *            POMS���ܱ��
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
							"com.aspire.dotcard.basevideosync.dao.CategoryApprovalDAO.categoryGoodsApproval",
							new Object[] { approvalStatus, categoryId, strs[0] });
				}
			} catch (DAOException e) {
				logger.error("����POMS��Ʒ�ύ�����쳣:", e);
				throw new BOException("����POMS��Ʒ�ύ�����쳣:", e);
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
					"com.aspire.dotcard.basevideosync.dao.CategoryApprovalDAO.categoryApproval",
					new Object[] { status, categoryId });
		} catch (DAOException e) {
			logger.error("����POMS���ܱ�����Ʒ��״̬�쳣:", e);
			throw new BOException("����POMS���ܱ�����Ʒ��״̬�쳣:", e);
		}
	}

	/**
	 * ȫ��POMS��Ʒ�ύ����
	 * 
	 * @param tdb
	 * @param categoryId
	 *            POMS���ܱ��
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
					"com.aspire.dotcard.basevideosync.dao.CategoryApprovalDAO.categoryGoodsAllApproval",
					new Object[] { approvalStatus, categoryId, status });
		} catch (DAOException e) {
			logger.error("ȫ��POMS��Ʒ�ύ�����쳣:", e);
			throw new BOException("ȫ��POMS��Ʒ�ύ�����쳣:", e);
		}
		this.categoryApproval(tdb, categoryId, "2");

	}

	/**
	 * ���ݻ���id��ѯ������POMS��Ʒ
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
							"com.aspire.dotcard.basevideosync.dao.CategoryApprovalDAO.queryCategoryGoodsList",
							new Object[] { categoryId });
			while (rs != null && rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", rs.getString("id"));
				map.put("verify_status", rs.getString("verify_status"));
				map.put("delflag", rs.getString("delflag"));
				list.add(map);
			}
		} catch (SQLException e) {
			logger.error("���ݻ���id��ѯ������POMS��Ʒ�쳣��", e);
			throw new BOException("���ݻ���id��ѯ������POMS��Ʒ�쳣��", e);
		} catch (DAOException e) {
			logger.error("���ݻ���id��ѯ������POMS��Ʒ�쳣��", e);
			throw new BOException("���ݻ���id��ѯ������POMS��Ʒ�쳣��", e);
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
	 * ɾ��һ��POMS��Ʒ��¼
	 * 
	 * @param tdb
	 * @param id
	 *            POMS��Ʒid
	 * @throws BOException
	 */
	public void deleteCategoryGoodsItem(TransactionDB tdb, String id,
			String categoryId) throws BOException {
		try {
			tdb.executeBySQLCode(
					"com.aspire.dotcard.basevideosync.dao.CategoryApprovalDAO.deleteCategoryGoodsItem",
					new Object[] { id, categoryId });
		} catch (DAOException e) {
			logger.error("ɾ��һ��POMS��Ʒ�쳣��", e);
			throw new BOException("ɾ��һ��POMS��Ʒ�쳣��", e);
		}

	}

	/**
	 * ����POMS��Ʒ��˲�ͬ��ʱ��ɾ������Ʒ��״̬���лع�
	 * 
	 * @param tdb
	 * @param id
	 *            POMS��Ʒid
	 * @throws BOException
	 */
	public void callBackCategoryGoodsItem(TransactionDB tdb, String id,
			String categoryId) throws BOException {
		try {
			tdb.executeBySQLCode(
					"com.aspire.dotcard.basevideosync.dao.CategoryApprovalDAO.callBackCategoryGoodsItem",
					new Object[] { id, categoryId });
		} catch (DAOException e) {
			logger.error("����POMS��Ʒ��˲�ͬ��ʱ��ɾ����POMS��״̬���лع��쳣��", e);
			throw new BOException("����POMS��Ʒ��˲�ͬ��ʱ��ɾ����POMS��״̬���лع��쳣��", e);
		}

	}

	/**
	 * ����POMS��Ʒid��ѯ������POMS��Ʒ
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
							"com.aspire.dotcard.basevideosync.dao.CategoryApprovalDAO.CategoryApprovalDAO.queryGoodsItem",
							new Object[] { contentId, categoryId });
			while (rs != null && rs.next()) {
				map.put("id", rs.getString("id"));
				map.put("verify_status", rs.getString("verify_status"));
				map.put("delflag", rs.getString("delflag"));
			}
		} catch (SQLException e) {
			logger.error("����POMS��Ʒid��ѯ������POMS��Ʒ�쳣��", e);
			throw new BOException("����POMS��Ʒid��ѯ������POMS��Ʒ�쳣��", e);
		} catch (DAOException e) {
			logger.error("����POMS��Ʒid��ѯ������POMS��Ʒ�쳣��", e);
			throw new BOException("����POMS��Ʒid��ѯ������POMS��Ʒ�쳣��", e);
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
	 * һ��POMS��Ʒ��¼�ύ����
	 * 
	 * @param tdb
	 * @param id
	 *            POMS��Ʒid
	 * @throws BOException
	 */
	public void approvalCategoryGoodsItem(TransactionDB tdb, String id,
			String categoryId) throws BOException {
		try {
			tdb.executeBySQLCode(
					"com.aspire.dotcard.basevideosync.dao.CategoryApprovalDAO.approvalCategoryGoodsItem",
					new Object[] { id, categoryId });
		} catch (DAOException e) {
			logger.error("һ��POMS��Ʒ��¼�ύ�����쳣��", e);
			throw new BOException("һ��POMS��Ʒ��¼�ύ�����쳣��", e);
		}

	}

}
