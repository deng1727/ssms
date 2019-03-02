package com.aspire.ponaadmin.web.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.syncAndroid.common.Constant;
import com.aspire.dotcard.syncAndroid.common.ContextUtil;
import com.aspire.dotcard.syncAndroid.dc.jms.MSGType;
import com.aspire.dotcard.syncAndroid.ssms.SSMSDAO;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.repository.goods.GoodsBO;

public class CategoryOperationDAO {
	
	 /**
     * 日志引用
     */
    protected static final JLogger logger = LoggerFactory.getLogger(CategoryOperationDAO.class);

    private static CategoryOperationDAO dao = new CategoryOperationDAO();

    private CategoryOperationDAO()
    {
    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static CategoryOperationDAO getInstance()
    {
        return dao;
    }
    
    /**
     * 货架商品单条内容审批
     * 
     * @param categoryId 货架编码
     * @param dealContent 货架商品表的id#应用资源id
     * @param userName 用户名
     * @param status 处理状态
     * @throws BOException
     */
	public void operationCategoryGoods(Category category, String[] dealContent,
			String userName, String status) throws BOException {
		if (logger.isDebugEnabled()) {
			logger.debug("operationCategoryGoods()");
		}
		// 进行事务操作
		TransactionDB tdb = null;
		try {
			tdb = TransactionDB.getInstance();
			for (int i = 0; i < dealContent.length; i++) {
				String[] strs = dealContent[i].split("#");
				if(!(strs.length == 3 && "1".equals(strs[2]))){
					tdb.executeBySQLCode(
							"com.aspire.ponaadmin.web.repository.CategoryOperationDAO.operationCategoryGoods.update",
							new Object[] { status, strs[0], strs[1] });
				}
				
		    		
					String transactionID = null;

					// 这里加上商品库优化，如果是商品库优化为根货架的货架，则要放消息进去。
					// 这里加点消息动作,就是这个新建货架的动作要通知到数据中心，先把数据加到T_a_messages表，然后专门线程发送消息。
					ModuleConfig module = ConfigFactory.getSystemConfig()
							.getModuleConfig("syncAndroid");
					String rootCategoryId = module
							.getItemValue("ROOT_CATEGORYID");
					String operateCategoryId = module
							.getItemValue("OPERATE_ROOT_CATEGORYID");// 不在商品库优化根货架下的运营货架
					if (SSMSDAO.getInstance().isAndroidCategory(
							category.getId(), rootCategoryId)
							// ||operateCategoryId.indexOf(cateid)!=-1){//凡是商品库优化根货架或者需要处理的运营货架下都有有消息
							// modify by aiyan 2013-05-10
							|| SSMSDAO.getInstance().isOperateCategory(
									category.getId(), operateCategoryId)) {// modify
																			// by
																			// aiyan
																			// 2013-05-18
						transactionID = ContextUtil.getTransactionID();
					}

					ReferenceNode refNode = (ReferenceNode) Repository
							.getInstance().getNode(strs[1],
									RepositoryConstants.TYPE_REFERENCE);
					
					String contId = refNode.getRefNodeID();
					if(strs.length == 3 && ("1".equals(strs[2]) || "2".equals(strs[2]) || "3".equals(strs[2]) )){
						if(Integer.parseInt(status) == 1){
							if (contId.charAt(0) >= '0' && contId.charAt(0) <= '9')// 应用。
		                    {
		                        //GoodsBO.removeRefContentFromCategory(refID);//remve by aiyan 2013-04-18
		                        
		                    	//modified by aiyan
		                        if(transactionID!=null){
		                        	GoodsBO.removeRefContentFromCategory(strs[1],transactionID);
		                        }else{
		                        	GoodsBO.removeRefContentFromCategory(strs[1]);
		                        }
		                    }
		                    else
		                    {
		                        category.delNode(refNode);
		                        category.saveNode();
		                    }
						}else{
							if("3".equals(strs[2])){
								category.setGoodsStatus("0");
								category.save();
							}
							refNode.setVerifyStatus(refNode.getDelFlag());
							refNode.setDelFlag("0");
							refNode.save();
						}
						
					}else{
						if (transactionID != null) {
							SSMSDAO.getInstance().addMessages(
									MSGType.RefModifyReq,
									transactionID
									// ,refNode.getGoodsID()+":"+refNode.getCategoryID()+":"+cateid+":"+refNode.getRefNodeID()+":"+refNode.getSortID()+":"+modifyStr(refNode.getLoadDate())+":0"
									// 这个sortid的地方设置有问题。modify by aiyan 2013-07-02
									,
									refNode.getGoodsID() + ":"
											+ refNode.getCategoryID() + ":"
											+ category.getId() + ":"
											+ refNode.getRefNodeID() + ":"
											+ refNode.getSortID() + ":"
											+ modifyStr(refNode.getLoadDate())
											+ ":0",
									Constant.MESSAGE_HANDLE_STATUS_INIT);
						}
					}
					
				
		    		
			}		
			if (!(isApproval(category.getCategoryID(),"0") || isApproval(category.getCategoryID(),"3"))) {
				tdb.executeBySQLCode(
						"com.aspire.ponaadmin.web.repository.CategoryOperationDAO.operationCategory.updateOperation1",
						new Object[] { userName, category.getCategoryID(), "2" });
				if(Integer.parseInt(status) == 2){
					tdb.executeBySQLCode(
							"com.aspire.ponaadmin.web.repository.CategoryOperationDAO.operationCategory.updateCategory",
							new Object[] { "3", category.getCategoryID() });
				}else{
					tdb.executeBySQLCode(
							"com.aspire.ponaadmin.web.repository.CategoryOperationDAO.operationCategory.updateCategory",
							new Object[] { status, category.getCategoryID() });
				}
				
			}
			tdb.commit();
		} catch (DAOException e) {
			// 执行回滚
			tdb.rollback();
			logger.error("更新货架商品表发生异常:", e);
			throw new BOException("更新货架商品表发生异常:", e);
		} finally {
			if (tdb != null) {
				tdb.close();
			}
		}
	}
    
    /**
     * 
     * @param categoryId 货架编码
     * @param operationType 审批状态
     * @param type  处理方式: 1 货架分类管理; 2 货架商品管理
     * @param userName  处理人
     */
	public void operationCategory(String categoryId, String operationType,
			String type, String userName) {
		if (logger.isDebugEnabled()) {
			logger.debug("operationCategory()");
		}

		if (Integer.parseInt(operationType) != 0) {
			try {
				if (isExists(categoryId, type)) {
					if (Integer.parseInt(operationType) == 2) {
						DB.getInstance()
								.executeBySQLCode(
										"com.aspire.ponaadmin.web.repository.CategoryOperationDAO.operationCategory.updateOperation2",
										new Object[] { userName, categoryId,
												type });
					} else {
						DB.getInstance()
								.executeBySQLCode(
										"com.aspire.ponaadmin.web.repository.CategoryOperationDAO.operationCategory.updateOperation1",
										new Object[] { userName, categoryId,
												type });
					}
				} else {
					DB.getInstance()
							.executeBySQLCode(
									"com.aspire.ponaadmin.web.repository.CategoryOperationDAO.operationCategory.insert",
									new Object[] { categoryId, userName, type });

				}
				
			} catch (NumberFormatException e) {
				logger.error("操作货架审批表发生异常:", e);
			} catch (DAOException e) {
				logger.error("操作货架审批表发生异常:", e);
			}
		}
	}
    
    /**
     * 
     * 查询货架审批表
     * 
     * @param categoryId 货架编码
     * @param operationType 处理方式
     * @return
     * @throws DAOException
     */
    public boolean isExists(String categoryId,String operationType) throws DAOException{
    	String sqlCode = "com.aspire.ponaadmin.web.repository.CategoryOperationDAO.isExists";
		ResultSet rs = null;
		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode, new Object[] {categoryId, operationType });
			while (rs.next()) {
				return true;
			}
		} catch (DAOException e) {
			logger.error("查询邮件地址表发生异常:", e);
			throw new DAOException("查询邮件地址表发生异常:", e);
		} catch (SQLException e) {
			logger.error("查询邮件地址表发生异常:", e);
			throw new DAOException("查询邮件地址表发生异常:", e);
		}
    	return false;
    }
    /**
     * 
     * @param categoryId 货架编码
     * @param status 状态
     * @return
     * @throws BOException
     */
    public boolean isApproval(String categoryId,String status) throws BOException{
    	String sqlCode = "com.aspire.ponaadmin.web.repository.CategoryOperationDAO.isApproval";
		ResultSet rs = null;
		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode, new Object[] {categoryId,status});
			while (rs.next()) {
				return true;
			}
		} catch (DAOException e) {
			logger.error("查询货架商品表发生异常:", e);
			throw new BOException("查询货架商品表发生异常:", e);
		} catch (SQLException e) {
			logger.error("查询货架商品表发生异常:", e);
			throw new BOException("查询货架商品表发生异常:", e);
		}
    	return false;
    }
    
    /**
     * 更新货架商品表
     * 
     * @param categoryId 货架编码
     * @throws BOException
     */
    public void updateCategoryGoodsInfo(String categoryId) throws BOException{
    	try {
			DB.getInstance().executeBySQLCode("com.aspire.ponaadmin.web.repository.CategoryOperationDAO.updateCategoryGoodsInfo", new Object[] {"0",categoryId});
		} catch (DAOException e) {
			logger.error("更新货架商品表发生异常:", e);
			throw new BOException("更新货架商品表发生异常:", e);
		}
    }
    
    /**
     * 批量提交审批货架商品内容
     * 
     * @param categoryId 货架编码
     * @param dealContent 货架商品表的id#应用资源id
     * @throws BOException
     */
	public void operationCategoryGoods(String categoryId, String[] dealContent) throws BOException {
		if (logger.isDebugEnabled()) {
			logger.debug("operationCategoryGoods()");
		}
		// 进行事务操作
		TransactionDB tdb = null;
		try {
			tdb = TransactionDB.getInstance();
			for (int i = 0; i < dealContent.length; i++) {
				String[] strs = dealContent[i].split("#");
				tdb.executeBySQLCode(
						"com.aspire.ponaadmin.web.repository.CategoryOperationDAO.operationCategoryGoods.update",
						new Object[] { "0", strs[0], strs[1] });
			}
			if (!isApproval(categoryId,"3")) {
				tdb.executeBySQLCode(
						"com.aspire.ponaadmin.web.repository.CategoryOperationDAO.operationCategory.updateCategory",
						new Object[] { "2", categoryId });
			}
			tdb.commit();
		} catch (DAOException e) {
			// 执行回滚
			tdb.rollback();
			logger.error("更新货架商品表发生异常:", e);
			throw new BOException("更新货架商品表发生异常:", e);
		} finally {
			if (tdb != null) {
				tdb.close();
			}
		}
	}
	
	/**
     * 根据货架ID审批商品表
     * @param categoryId
     * @param flag
     */
    public void verifyRefrenceByCategoryId(String categoryId, String flag) {
		String sqlCode = "com.aspire.ponaadmin.web.repository.CategoryOperationDAO.verifyRefrenceByCategoryId";
		// 进行事务操作
		TransactionDB tdb = null;
		try {
			tdb = TransactionDB.getTransactionInstance();
				tdb.executeBySQLCode(sqlCode, new Object[] { flag,categoryId  });
			// 提交事务操作
			tdb.commit();
		} catch (Exception e) {
			// 执行回滚
			tdb.rollback();
		} finally {
			if (tdb != null) {
				tdb.close();
			}
		}

	}
    /**
     * 根据货架ID查询所有商品
     * @param categoryId 货架ID
     * @return
     * @throws BOException
     */
    public List<ReferenceVO> getReferenceListByCategoryId(String categoryId) throws BOException{
    	List<ReferenceVO> list = null;
    	String sqlCode = "com.aspire.ponaadmin.web.repository.CategoryOperationDAO.getReferenceListByCategoryId";
    	ResultSet rs = null;
    	try {
    		rs = DB.getInstance().queryBySQLCode(sqlCode, new Object[] {categoryId});
    		if(rs != null){
    			list = new ArrayList<ReferenceVO>();
    			while (rs.next()) {
					ReferenceVO rVo = new ReferenceVO();
					rVo.setId(rs.getString("id"));
					rVo.setCategoryId(rs.getString("categoryId"));
					rVo.setGoodsId(rs.getString("goodsId"));
					rVo.setSortId(rs.getString("sortId"));
					rVo.setVerify_status(rs.getString("verify_Status"));
					rVo.setDelFlag(rs.getString("delFlag"));
					rVo.setRefNodeID(rs.getString("refNodeID"));
					list.add(rVo);
				}
    		}
    		return list;
		}catch (SQLException e) {
			throw new BOException(" 根据货架ID查询所有商品发生异常:", e);
		}catch (DAOException e) {
			logger.error(" 根据货架ID查询所有商品发生异常:", e);
			throw new BOException(" 根据货架ID查询所有商品发生异常:", e);
		}
    	
    }
    /**
     * 
     * 删除商品
     * @param id 商品ID
     * @return
     * @throws BOException
     */
    public int delReferenceById(String id)throws BOException{
    	String sqlCode = "com.aspire.ponaadmin.web.repository.CategoryOperationDAO.delReferenceById";
    	try {
			return DB.getInstance().executeBySQLCode(sqlCode, new Object[] {id});
		} catch (Exception e) {
			logger.error(" 删除商品发生异常:", e);
			throw new BOException(" 删除商品发生异常:", e);
		}
    }
    /**
     * 
     * 修改商品
     * @param id 商品ID
     * @return
     * @throws BOException
     */
    public int updateReferenceById(String id,String verifyStatus,String delFlag)throws BOException{
    	String sqlCode = "com.aspire.ponaadmin.web.repository.CategoryOperationDAO.updateReferenceById";
    	try {
			return DB.getInstance().executeBySQLCode(sqlCode, new Object[] {verifyStatus,delFlag,id});
		} catch (Exception e) {
			logger.error(" 修改商品发生异常:", e);
			throw new BOException(" 修改商品发生异常:", e);
		}
    }
    private String modifyStr(String dateStr){
    	if(dateStr==null){
    		return "";
    	}
    	return dateStr.replaceAll(" ", "").replaceAll("-", "").replaceAll(":", "");
    }

}
