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
     * 记录日志的实例对象
     */
    protected static JLogger logger = LoggerFactory.getLogger(CategoryApprovalDAO.class);

    /**
     * singleton模式的实例
     */
    private static CategoryApprovalDAO instance = new CategoryApprovalDAO();

    /**
     * 构造方法，由singleton模式调用
     */
    private CategoryApprovalDAO(){
    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static CategoryApprovalDAO getInstance()
    {
        return instance;
    }
    
	/**
	 * 查询动漫货架
	 * 
	 * @param categoryId 货架编号
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
			logger.error("查询动漫货架表异常", e);
			throw new BOException("查询动漫货架表异常", e);
		} catch (DAOException e) {
			logger.error("查询动漫货架表异常", e);
			throw new BOException("查询动漫货架表异常", e);
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
	 * 刪除动漫货架
	 * 
	 * @param tdb
	 * @param categoryId 货架编码
	 * @throws BOException
	 */
	public void delete(TransactionDB tdb, String categoryId) throws BOException {
		try {
			tdb.executeBySQLCode(
					"com.aspire.ponaadmin.web.comic.dao.CategoryApprovalDAO.delete",
					new Object[] {categoryId});
		} catch (DAOException e) {
			logger.error("更新动漫货架表异常", e);
			throw new BOException("更新动漫货架表异常", e);
		}

	}
	
	/**
	 * 刪除动漫货架
	 * 
	 * @param tdb
	 * @param categoryId 货架编码
	 * @throws BOException
	 */
	public void categoryApprovalPass(TransactionDB tdb, String status,String categoryId) throws BOException {
		try {
			tdb.executeBySQLCode(
					"com.aspire.ponaadmin.web.comic.dao.CategoryApprovalDAO.categoryApprovalPass",
					new Object[] {status,categoryId});
		} catch (DAOException e) {
			logger.error("更新动漫货架表异常", e);
			throw new BOException("更新动漫货架表异常", e);
		}

	}
	
	public void deleteNoPass(TransactionDB tdb, String categoryId) throws BOException {
		try {
			tdb.executeBySQLCode(
					"com.aspire.ponaadmin.web.comic.dao.CategoryApprovalDAO.deleteNoPass",
					new Object[] {categoryId});
		} catch (DAOException e) {
			logger.error("更新动漫货架表异常", e);
			throw new BOException("更新动漫货架表异常", e);
		}
	}
	
	 /**
     * 
     * @param categoryId 货架编码
     * @param status 状态
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
			logger.error("查询动漫商品表发生异常:", e);
			throw new BOException("查询动漫商品表发生异常:", e);
		} catch (SQLException e) {
			logger.error("查询动漫商品表发生异常:", e);
			throw new BOException("查询动漫商品表发生异常:", e);
		}
    	return false;
    }
    
    /**
     * 单个动漫商品提交审批
     * 
     * @param tdb
     * @param contentId 动漫商品表中的Id+verify_status
     * @param approvalStatus 审批状态 0 编辑；1 已发布；2 待审批;3 审批不通过
     * @param categoryId 动漫货架编号
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
				logger.error("更新动漫商品表异常:", e);
				throw new BOException("更新动漫商品表异常:", e);
			}
    	}
    	
    }
    /**
     * 更新动漫货架表中商品的状态
     * 
     * @param tdb
     * @param categoryId 动漫货架编码
     * @param status 商品的状态 0 编辑；1 已发布；2 待审批;3 审批不通过
     * @throws BOException
     */
    public void categoryApproval(TransactionDB tdb,String categoryId,String status) throws BOException{
    	try {
			tdb.executeBySQLCode("com.aspire.ponaadmin.web.comic.dao.CategoryApprovalDAO.categoryApproval", new Object[]{status,categoryId});
		} catch (DAOException e) {
			logger.error("更新动漫货架表异常:", e);
			throw new BOException("更新动漫货架表异常:", e);
		}
    }
    
    /**
     * 全部动漫商品提交审批
     * 
     * @param tdb
     * @param categoryId 动漫货架编号
     * @param approvalStatus 审批状态 0 编辑；1 已发布；2 待审批;3 审批不通过
     * @param status 商品的状态 0 编辑；1 已发布；2 待审批;3 审批不通过
     * @throws BOException
     */
    public void categoryGoodsAllApproval(TransactionDB tdb,String categoryId,String approvalStatus,String status) throws BOException{
    	try {
			tdb.executeBySQLCode("com.aspire.ponaadmin.web.comic.dao.CategoryApprovalDAO.categoryGoodsAllApproval", new Object[]{approvalStatus,categoryId,status});
		} catch (DAOException e) {
			logger.error("更新动漫商品表异常:", e);
			throw new BOException("更新动漫商品表异常:", e);
		}
    	this.categoryApproval(tdb, categoryId, "2");
    	
    }
    
    /**
	 * 查询动漫商品
	 * 
	 * @param categoryId 货架编号
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
			logger.error("查询动漫商品表异常：", e);
			throw new BOException("查询动漫商品表异常：", e);
		} catch (DAOException e) {
			logger.error("查询动漫商品表异常：", e);
			throw new BOException("查询动漫商品表异常：", e);
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
	 * 删除一条动漫商品记录
	 * @param tdb
	 * @param id 动漫商品id
	 * @throws BOException
	 */
	public void deleteCategoryGoodsItem(TransactionDB tdb,String id) throws BOException{
		try {
			tdb.executeBySQLCode("com.aspire.ponaadmin.web.comic.dao.CategoryApprovalDAO.deleteCategoryGoodsItem", new Object[]{id});
		} catch (DAOException e) {
			logger.error("删除一条动漫商品异常：", e);
			throw new BOException("删除一条动漫商品异常：", e);
		}
		
	}
	/**
	 * 更新一条动漫商品表记录
	 * @param tdb
	 * @param id 动漫商品id
	 * @throws BOException
	 */
	public void callBackCategoryGoodsItem(TransactionDB tdb,String id) throws BOException{
		try {
			tdb.executeBySQLCode("com.aspire.ponaadmin.web.comic.dao.CategoryApprovalDAO.callBackCategoryGoodsItem", new Object[]{id});
		} catch (DAOException e) {
			logger.error("更新一条动漫商品表记录异常：", e);
			throw new BOException("更新一条动漫商品表记录异常：", e);
		}
		
	}
	
	/**
	 * 根据动漫商品Id查询动漫商品
	 * 
	 * @param categoryId 货架编号
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
			logger.error("根据动漫商品Id查询动漫商品异常：", e);
			throw new BOException("根据动漫商品Id查询动漫商品异常：", e);
		} catch (DAOException e) {
			logger.error("根据动漫商品Id查询动漫商品异常：", e);
			throw new BOException("根据动漫商品Id查询动漫商品异常：", e);
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
	 * 一条动漫商品记录提交审批
	 * 
	 * @param tdb
	 * @param id
	 *            动漫商品id
	 * @throws BOException
	 */
	public void approvalCategoryGoodsItem(TransactionDB tdb, String id) throws BOException {
		try {
			tdb.executeBySQLCode(
					"com.aspire.ponaadmin.web.comic.dao.CategoryApprovalDAO.approvalCategoryGoodsItem",
					new Object[] { id });
		} catch (DAOException e) {
			logger.error("一条动漫商品记录提交审批异常：", e);
			throw new BOException("一条动漫商品记录提交审批异常：", e);
		}

	}

}
