package com.aspire.ponaadmin.web.comic.dao;

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
    protected static JLogger logger = LoggerFactory.getLogger(CategoryApprovalDAO.class);

    /**
     * singletonģʽ��ʵ��
     */
    private static CategoryApprovalDAO instance = new CategoryApprovalDAO();

    /**
     * ���췽������singletonģʽ����
     */
    private CategoryApprovalDAO(){
    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static CategoryApprovalDAO getInstance()
    {
        return instance;
    }
    
	/**
	 * ��ѯ��������
	 * 
	 * @param categoryId ���ܱ��
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
							"com.aspire.ponaadmin.web.comic.dao.CategoryApprovalDAO.queryCategoryListItem",
							new Object[] { categoryId });
			while (rs != null && rs.next()) {
				map.put("categoryId", rs.getString("categoryid"));
				map.put("goods_status", rs.getString("goods_status"));
				map.put("delpro_status", rs.getString("delpro_status"));
				map.put("anime_status", rs.getString("anime_status"));
			}
		} catch (SQLException e) {
			logger.error("��ѯ�������ܱ��쳣", e);
			throw new BOException("��ѯ�������ܱ��쳣", e);
		} catch (DAOException e) {
			logger.error("��ѯ�������ܱ��쳣", e);
			throw new BOException("��ѯ�������ܱ��쳣", e);
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
	 * �h����������
	 * 
	 * @param tdb
	 * @param categoryId ���ܱ���
	 * @throws BOException
	 */
	public void delete(TransactionDB tdb, String categoryId) throws BOException {
		try {
			tdb.executeBySQLCode(
					"com.aspire.ponaadmin.web.comic.dao.CategoryApprovalDAO.delete",
					new Object[] {categoryId});
		} catch (DAOException e) {
			logger.error("���¶������ܱ��쳣", e);
			throw new BOException("���¶������ܱ��쳣", e);
		}

	}
	
	/**
	 * �h����������
	 * 
	 * @param tdb
	 * @param categoryId ���ܱ���
	 * @throws BOException
	 */
	public void categoryApprovalPass(TransactionDB tdb, String status,String categoryId) throws BOException {
		try {
			tdb.executeBySQLCode(
					"com.aspire.ponaadmin.web.comic.dao.CategoryApprovalDAO.categoryApprovalPass",
					new Object[] {status,categoryId});
		} catch (DAOException e) {
			logger.error("���¶������ܱ��쳣", e);
			throw new BOException("���¶������ܱ��쳣", e);
		}

	}
	
	public void deleteNoPass(TransactionDB tdb, String categoryId) throws BOException {
		try {
			tdb.executeBySQLCode(
					"com.aspire.ponaadmin.web.comic.dao.CategoryApprovalDAO.deleteNoPass",
					new Object[] {categoryId});
		} catch (DAOException e) {
			logger.error("���¶������ܱ��쳣", e);
			throw new BOException("���¶������ܱ��쳣", e);
		}
	}
	
	 /**
     * 
     * @param categoryId ���ܱ���
     * @param status ״̬
     * @return
     * @throws BOException
     */
    public boolean isApproval(String categoryId,String status) throws BOException{
    	String sqlCode = "com.aspire.ponaadmin.web.comic.dao.CategoryApprovalDAO.isApproval";
		ResultSet rs = null;
		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode, new Object[] {categoryId,status});
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
     * @param contentId ������Ʒ���е�Id+verify_status
     * @param approvalStatus ����״̬ 0 �༭��1 �ѷ�����2 ������;3 ������ͨ��
     * @param categoryId �������ܱ��
     * @throws BOException
     */
    public void categoryGoodsApproval(TransactionDB tdb,String[] contentId,String approvalStatus,String categoryId) throws BOException{
    	for(int i = 0;i < contentId.length;i++){
    		String[] strs = contentId[i].split("#");
    		try {
    			Map<String,Object> map = this.queryGoodsItem(strs[0]);
    			if(!"2".equals(map.get("delflag"))){
    				if("1".equals(approvalStatus)){  					
    					this.deleteCategoryGoodsItem(tdb, strs[0]);
    				}else if("2".equals(approvalStatus)){
    					this.approvalCategoryGoodsItem(tdb, strs[0]);
    				}else{
    					this.callBackCategoryGoodsItem(tdb, strs[0]);
    				}
    			}else{  				
    				tdb.executeBySQLCode("com.aspire.ponaadmin.web.comic.dao.CategoryApprovalDAO.categoryGoodsApproval", new Object[]{approvalStatus,categoryId,strs[0]});
    			}		
			} catch (DAOException e) {
				logger.error("���¶�����Ʒ���쳣:", e);
				throw new BOException("���¶�����Ʒ���쳣:", e);
			}
    	}
    	
    }
    /**
     * ���¶������ܱ�����Ʒ��״̬
     * 
     * @param tdb
     * @param categoryId �������ܱ���
     * @param status ��Ʒ��״̬ 0 �༭��1 �ѷ�����2 ������;3 ������ͨ��
     * @throws BOException
     */
    public void categoryApproval(TransactionDB tdb,String categoryId,String status) throws BOException{
    	try {
			tdb.executeBySQLCode("com.aspire.ponaadmin.web.comic.dao.CategoryApprovalDAO.categoryApproval", new Object[]{status,categoryId});
		} catch (DAOException e) {
			logger.error("���¶������ܱ��쳣:", e);
			throw new BOException("���¶������ܱ��쳣:", e);
		}
    }
    
    /**
     * ȫ��������Ʒ�ύ����
     * 
     * @param tdb
     * @param categoryId �������ܱ��
     * @param approvalStatus ����״̬ 0 �༭��1 �ѷ�����2 ������;3 ������ͨ��
     * @param status ��Ʒ��״̬ 0 �༭��1 �ѷ�����2 ������;3 ������ͨ��
     * @throws BOException
     */
    public void categoryGoodsAllApproval(TransactionDB tdb,String categoryId,String approvalStatus,String status) throws BOException{
    	try {
			tdb.executeBySQLCode("com.aspire.ponaadmin.web.comic.dao.CategoryApprovalDAO.categoryGoodsAllApproval", new Object[]{approvalStatus,categoryId,status});
		} catch (DAOException e) {
			logger.error("���¶�����Ʒ���쳣:", e);
			throw new BOException("���¶�����Ʒ���쳣:", e);
		}
    	this.categoryApproval(tdb, categoryId, "2");
    	
    }
    
    /**
	 * ��ѯ������Ʒ
	 * 
	 * @param categoryId ���ܱ��
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
							"com.aspire.ponaadmin.web.comic.dao.CategoryApprovalDAO.queryCategoryGoodsList",
							new Object[] { categoryId });
			while (rs != null && rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", rs.getString("id"));
				map.put("verify_status", rs.getString("verify_status"));
				map.put("delflag", rs.getString("delflag"));
				list.add(map);
			}
		} catch (SQLException e) {
			logger.error("��ѯ������Ʒ���쳣��", e);
			throw new BOException("��ѯ������Ʒ���쳣��", e);
		} catch (DAOException e) {
			logger.error("��ѯ������Ʒ���쳣��", e);
			throw new BOException("��ѯ������Ʒ���쳣��", e);
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
	 * @param tdb
	 * @param id ������Ʒid
	 * @throws BOException
	 */
	public void deleteCategoryGoodsItem(TransactionDB tdb,String id) throws BOException{
		try {
			tdb.executeBySQLCode("com.aspire.ponaadmin.web.comic.dao.CategoryApprovalDAO.deleteCategoryGoodsItem", new Object[]{id});
		} catch (DAOException e) {
			logger.error("ɾ��һ��������Ʒ�쳣��", e);
			throw new BOException("ɾ��һ��������Ʒ�쳣��", e);
		}
		
	}
	/**
	 * ����һ��������Ʒ���¼
	 * @param tdb
	 * @param id ������Ʒid
	 * @throws BOException
	 */
	public void callBackCategoryGoodsItem(TransactionDB tdb,String id) throws BOException{
		try {
			tdb.executeBySQLCode("com.aspire.ponaadmin.web.comic.dao.CategoryApprovalDAO.callBackCategoryGoodsItem", new Object[]{id});
		} catch (DAOException e) {
			logger.error("����һ��������Ʒ���¼�쳣��", e);
			throw new BOException("����һ��������Ʒ���¼�쳣��", e);
		}
		
	}
	
	/**
	 * ���ݶ�����ƷId��ѯ������Ʒ
	 * 
	 * @param categoryId ���ܱ��
	 * @return
	 * @throws BOException
	 */
	public Map<String, Object> queryGoodsItem(String contentId)
			throws BOException {
		ResultSet rs = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			rs = DB.getInstance()
					.queryBySQLCode(
							"com.aspire.ponaadmin.web.comic.dao.CategoryApprovalDAO.queryGoodsItem",
							new Object[] { contentId });
			while (rs != null && rs.next()) {
				map.put("id", rs.getString("id"));
				map.put("verify_status", rs.getString("verify_status"));
				map.put("delflag", rs.getString("delflag"));
			}
		} catch (SQLException e) {
			logger.error("���ݶ�����ƷId��ѯ������Ʒ�쳣��", e);
			throw new BOException("���ݶ�����ƷId��ѯ������Ʒ�쳣��", e);
		} catch (DAOException e) {
			logger.error("���ݶ�����ƷId��ѯ������Ʒ�쳣��", e);
			throw new BOException("���ݶ�����ƷId��ѯ������Ʒ�쳣��", e);
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
	public void approvalCategoryGoodsItem(TransactionDB tdb, String id) throws BOException {
		try {
			tdb.executeBySQLCode(
					"com.aspire.ponaadmin.web.comic.dao.CategoryApprovalDAO.approvalCategoryGoodsItem",
					new Object[] { id });
		} catch (DAOException e) {
			logger.error("һ��������Ʒ��¼�ύ�����쳣��", e);
			throw new BOException("һ��������Ʒ��¼�ύ�����쳣��", e);
		}

	}

}
