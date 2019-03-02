package com.aspire.dotcard.baseread.dao;

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
	 * ��ѯͼ�����
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
							"com.aspire.dotcard.baseread.dao.CategoryApprovalDAO.queryCategoryListItem",
							new Object[] { categoryId });
			while (rs != null && rs.next()) {
				map.put("categoryId", rs.getString("id"));
				map.put("goods_status", rs.getString("goods_status"));
				map.put("delpro_status", rs.getString("delpro_status"));
				map.put("read_status", rs.getString("read_status"));
			}
		} catch (SQLException e) {
			logger.error("��ѯͼ����ܱ��쳣", e);
			throw new BOException("��ѯͼ����ܱ��쳣", e);
		} catch (DAOException e) {
			logger.error("��ѯͼ����ܱ��쳣", e);
			throw new BOException("��ѯͼ����ܱ��쳣", e);
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
	 * ����ͼ���������״̬
	 * 
	 * @param tdb
	 * @param categoryId ���ܱ���
	 * @throws BOException
	 */
	public void categoryApprovalPass(TransactionDB tdb, String status,String categoryId) throws BOException {
		try {
			tdb.executeBySQLCode(
					"com.aspire.dotcard.baseread.dao.CategoryApprovalDAO.categoryApprovalPass",
					new Object[] {status,categoryId});
		} catch (DAOException e) {
			logger.error("����ͼ���������״̬�����쳣��", e);
			throw new BOException("����ͼ���������״̬�����쳣��", e);
		}

	}
	/**
	 * ��˲�ͨ����ɾ����ͼ����ܻع���ԭ����״̬
	 * @param tdb
	 * @param categoryId
	 * @throws BOException
	 */
	public void deleteNoPass(TransactionDB tdb, String categoryId) throws BOException {
		try {
			tdb.executeBySQLCode(
					"com.aspire.dotcard.baseread.dao.CategoryApprovalDAO.deleteNoPass",
					new Object[] {categoryId});
		} catch (DAOException e) {
			logger.error("��˲�ͨ����ɾ����ͼ����ܻع���ԭ����״̬�����쳣", e);
			throw new BOException("��˲�ͨ����ɾ����ͼ����ܻع���ԭ����״̬�����쳣", e);
		}
	}
	
	 /**
     * ����������ѯͼ����Ʒ���Ƿ���ڼ�¼
     * 
     * @param categoryId ���ܱ���
     * @param status ״̬
     * @return
     * @throws BOException
     */
    public boolean isApproval(String categoryId,String status) throws BOException{
    	String sqlCode = "com.aspire.dotcard.baseread.dao.CategoryApprovalDAO.isApproval";
		ResultSet rs = null;
		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode, new Object[] {categoryId,status});
			while (rs.next()) {
				return true;
			}
		} catch (DAOException e) {
			logger.error("��ѯͼ����Ʒ�����쳣:", e);
			throw new BOException("��ѯͼ����Ʒ�����쳣:", e);
		} catch (SQLException e) {
			logger.error("��ѯͼ����Ʒ�����쳣:", e);
			throw new BOException("��ѯͼ����Ʒ�����쳣:", e);
		}
    	return false;
    }
    
    /**
     * ����ͼ����Ʒ�ύ����
     * 
     * @param tdb
     * @param contentId ͼ����Ʒ���е�Id
     * @param approvalStatus ����״̬ 0 �༭��1 �ѷ�����2 ������;3 ������ͨ��
     * @param categoryId ͼ����ܱ��
     * @throws BOException
     */
    public void categoryGoodsApproval(TransactionDB tdb,String[] contentId,String approvalStatus,String categoryId) throws BOException{
    	for(int i = 0;i < contentId.length;i++){
    		try {
    		   String[] strs = contentId[i].split("#");
    			Map<String,Object> map = this.queryGoodsItem(strs[0],categoryId);
    			if(!"2".equals(map.get("delflag"))){
    				if("1".equals(approvalStatus)){					
    					this.deleteCategoryGoodsItem(tdb, strs[0],categoryId);
    				}else if("2".equals(approvalStatus)){
    					this.approvalCategoryGoodsItem(tdb, strs[0], categoryId);
    				}else{
    					this.callBackCategoryGoodsItem(tdb, strs[0],categoryId);
    				}
    			}else{  				
    				tdb.executeBySQLCode("com.aspire.dotcard.baseread.dao.CategoryApprovalDAO.categoryGoodsApproval", new Object[]{approvalStatus,categoryId,strs[0]});
    			}
			} catch (DAOException e) {
				logger.error("����ͼ����Ʒ�ύ�����쳣:", e);
				throw new BOException("����ͼ����Ʒ�ύ�����쳣:", e);
			}
    	}
    	
    }
    /**
     * ����ͼ����ܱ�����Ʒ��״̬
     * 
     * @param tdb
     * @param categoryId ͼ����ܱ���
     * @param status ��Ʒ��״̬ 0 �༭��1 �ѷ�����2 ������;3 ������ͨ��
     * @throws BOException
     */
    public void categoryApproval(TransactionDB tdb,String categoryId,String status) throws BOException{
    	try {
			tdb.executeBySQLCode("com.aspire.dotcard.baseread.dao.CategoryApprovalDAO.categoryApproval", new Object[]{status,categoryId});
		} catch (DAOException e) {
			logger.error("����ͼ����ܱ�����Ʒ��״̬�쳣:", e);
			throw new BOException("����ͼ����ܱ�����Ʒ��״̬�쳣:", e);
		}
    }
    
    /**
     * ȫ��ͼ����Ʒ�ύ����
     * 
     * @param tdb
     * @param categoryId ͼ����ܱ��
     * @param approvalStatus ����״̬ 0 �༭��1 �ѷ�����2 ������;3 ������ͨ��
     * @param status ��Ʒ��״̬ 0 �༭��1 �ѷ�����2 ������;3 ������ͨ��
     * @throws BOException
     */
    public void categoryGoodsAllApproval(TransactionDB tdb,String categoryId,String approvalStatus,String status) throws BOException{
    	try {
			tdb.executeBySQLCode("com.aspire.dotcard.baseread.dao.CategoryApprovalDAO.categoryGoodsAllApproval", new Object[]{approvalStatus,categoryId,status});
		} catch (DAOException e) {
			logger.error("ȫ��ͼ����Ʒ�ύ�����쳣:", e);
			throw new BOException("ȫ��ͼ����Ʒ�ύ�����쳣:", e);
		}
    	this.categoryApproval(tdb, categoryId, "2");
    	
    }
    
    /**
	 * ���ݻ���id��ѯ������ͼ����Ʒ
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
							"com.aspire.dotcard.baseread.dao.CategoryApprovalDAO.queryCategoryGoodsList",
							new Object[] { categoryId });
			while (rs != null && rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", rs.getString("id"));
				map.put("verify_status", rs.getString("verify_status"));
				map.put("delflag", rs.getString("delflag"));
				list.add(map);
			}
		} catch (SQLException e) {
			logger.error("���ݻ���id��ѯ������ͼ����Ʒ�쳣��", e);
			throw new BOException("���ݻ���id��ѯ������ͼ����Ʒ�쳣��", e);
		} catch (DAOException e) {
			logger.error("���ݻ���id��ѯ������ͼ����Ʒ�쳣��", e);
			throw new BOException("���ݻ���id��ѯ������ͼ����Ʒ�쳣��", e);
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
	 * ɾ��һ��ͼ����Ʒ��¼
	 * @param tdb
	 * @param id ������Ʒid
	 * @throws BOException
	 */
	public void deleteCategoryGoodsItem(TransactionDB tdb,String id,String categoryId) throws BOException{
		try {
			tdb.executeBySQLCode("com.aspire.dotcard.baseread.dao.CategoryApprovalDAO.deleteCategoryGoodsItem", new Object[]{id,categoryId});
		} catch (DAOException e) {
			logger.error("ɾ��һ��ͼ����Ʒ�쳣��", e);
			throw new BOException("ɾ��һ��ͼ����Ʒ�쳣��", e);
		}
		
	}
	/**
	 * ����ͼ����Ʒ��˲�ͬ��ʱ��ɾ������Ʒ��״̬���лع�
	 * 
	 * @param tdb
	 * @param id ͼ����Ʒid
	 * @throws BOException
	 */
	public void callBackCategoryGoodsItem(TransactionDB tdb,String id,String categoryId) throws BOException{
		try {
			tdb.executeBySQLCode("com.aspire.dotcard.baseread.dao.CategoryApprovalDAO.callBackCategoryGoodsItem", new Object[]{id,categoryId});
		} catch (DAOException e) {
			logger.error("����ͼ����Ʒ��˲�ͬ��ʱ��ɾ������Ʒ��״̬���лع��쳣��", e);
			throw new BOException("����ͼ����Ʒ��˲�ͬ��ʱ��ɾ������Ʒ��״̬���лع��쳣��", e);
		}
		
	}
	
	
	/**
	 * ����ͼ����Ʒid��ѯ��������Ƶ��Ʒ
	 * 
	 * @param categoryId ���ܱ��
	 * @return
	 * @throws BOException
	 */
	public Map<String, Object> queryGoodsItem(String contentId,String categoryId)
			throws BOException {
		ResultSet rs = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			rs = DB.getInstance()
					.queryBySQLCode(
							"com.aspire.dotcard.baseread.dao.CategoryApprovalDAO.queryGoodsItem",
							new Object[] { contentId,categoryId });
			while (rs != null && rs.next()) {
				map.put("id", rs.getString("id"));
				map.put("verify_status", rs.getString("verify_status"));
				map.put("delflag", rs.getString("delflag"));
			}
		} catch (SQLException e) {
			logger.error("����ͼ����Ʒid��ѯ������ͼ����Ʒ�쳣��", e);
			throw new BOException("����ͼ����Ʒid��ѯ������ͼ����Ʒ�쳣��", e);
		} catch (DAOException e) {
			logger.error("����ͼ����Ʒid��ѯ������ͼ����Ʒ�쳣��", e);
			throw new BOException("����ͼ����Ʒid��ѯ������ͼ����Ʒ�쳣��", e);
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
	 * һ��ͼ����Ʒ��¼�ύ����
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
					"com.aspire.dotcard.baseread.dao.CategoryApprovalDAO.approvalCategoryGoodsItem",
					new Object[] { id, categoryId });
		} catch (DAOException e) {
			logger.error("һ��ͼ����Ʒ��¼�ύ�����쳣��", e);
			throw new BOException("һ��ͼ����Ʒ��¼�ύ�����쳣��", e);
		}

	}

}
