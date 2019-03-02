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
     * ��־����
     */
    protected static final JLogger logger = LoggerFactory.getLogger(CategoryOperationDAO.class);

    private static CategoryOperationDAO dao = new CategoryOperationDAO();

    private CategoryOperationDAO()
    {
    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static CategoryOperationDAO getInstance()
    {
        return dao;
    }
    
    /**
     * ������Ʒ������������
     * 
     * @param categoryId ���ܱ���
     * @param dealContent ������Ʒ���id#Ӧ����Դid
     * @param userName �û���
     * @param status ����״̬
     * @throws BOException
     */
	public void operationCategoryGoods(Category category, String[] dealContent,
			String userName, String status) throws BOException {
		if (logger.isDebugEnabled()) {
			logger.debug("operationCategoryGoods()");
		}
		// �����������
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

					// ���������Ʒ���Ż����������Ʒ���Ż�Ϊ�����ܵĻ��ܣ���Ҫ����Ϣ��ȥ��
					// ����ӵ���Ϣ����,��������½����ܵĶ���Ҫ֪ͨ���������ģ��Ȱ����ݼӵ�T_a_messages��Ȼ��ר���̷߳�����Ϣ��
					ModuleConfig module = ConfigFactory.getSystemConfig()
							.getModuleConfig("syncAndroid");
					String rootCategoryId = module
							.getItemValue("ROOT_CATEGORYID");
					String operateCategoryId = module
							.getItemValue("OPERATE_ROOT_CATEGORYID");// ������Ʒ���Ż��������µ���Ӫ����
					if (SSMSDAO.getInstance().isAndroidCategory(
							category.getId(), rootCategoryId)
							// ||operateCategoryId.indexOf(cateid)!=-1){//������Ʒ���Ż������ܻ�����Ҫ�������Ӫ�����¶�������Ϣ
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
							if (contId.charAt(0) >= '0' && contId.charAt(0) <= '9')// Ӧ�á�
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
									// ���sortid�ĵط����������⡣modify by aiyan 2013-07-02
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
			// ִ�лع�
			tdb.rollback();
			logger.error("���»�����Ʒ�����쳣:", e);
			throw new BOException("���»�����Ʒ�����쳣:", e);
		} finally {
			if (tdb != null) {
				tdb.close();
			}
		}
	}
    
    /**
     * 
     * @param categoryId ���ܱ���
     * @param operationType ����״̬
     * @param type  ����ʽ: 1 ���ܷ������; 2 ������Ʒ����
     * @param userName  ������
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
				logger.error("�����������������쳣:", e);
			} catch (DAOException e) {
				logger.error("�����������������쳣:", e);
			}
		}
	}
    
    /**
     * 
     * ��ѯ����������
     * 
     * @param categoryId ���ܱ���
     * @param operationType ����ʽ
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
			logger.error("��ѯ�ʼ���ַ�����쳣:", e);
			throw new DAOException("��ѯ�ʼ���ַ�����쳣:", e);
		} catch (SQLException e) {
			logger.error("��ѯ�ʼ���ַ�����쳣:", e);
			throw new DAOException("��ѯ�ʼ���ַ�����쳣:", e);
		}
    	return false;
    }
    /**
     * 
     * @param categoryId ���ܱ���
     * @param status ״̬
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
			logger.error("��ѯ������Ʒ�����쳣:", e);
			throw new BOException("��ѯ������Ʒ�����쳣:", e);
		} catch (SQLException e) {
			logger.error("��ѯ������Ʒ�����쳣:", e);
			throw new BOException("��ѯ������Ʒ�����쳣:", e);
		}
    	return false;
    }
    
    /**
     * ���»�����Ʒ��
     * 
     * @param categoryId ���ܱ���
     * @throws BOException
     */
    public void updateCategoryGoodsInfo(String categoryId) throws BOException{
    	try {
			DB.getInstance().executeBySQLCode("com.aspire.ponaadmin.web.repository.CategoryOperationDAO.updateCategoryGoodsInfo", new Object[] {"0",categoryId});
		} catch (DAOException e) {
			logger.error("���»�����Ʒ�����쳣:", e);
			throw new BOException("���»�����Ʒ�����쳣:", e);
		}
    }
    
    /**
     * �����ύ����������Ʒ����
     * 
     * @param categoryId ���ܱ���
     * @param dealContent ������Ʒ���id#Ӧ����Դid
     * @throws BOException
     */
	public void operationCategoryGoods(String categoryId, String[] dealContent) throws BOException {
		if (logger.isDebugEnabled()) {
			logger.debug("operationCategoryGoods()");
		}
		// �����������
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
			// ִ�лع�
			tdb.rollback();
			logger.error("���»�����Ʒ�����쳣:", e);
			throw new BOException("���»�����Ʒ�����쳣:", e);
		} finally {
			if (tdb != null) {
				tdb.close();
			}
		}
	}
	
	/**
     * ���ݻ���ID������Ʒ��
     * @param categoryId
     * @param flag
     */
    public void verifyRefrenceByCategoryId(String categoryId, String flag) {
		String sqlCode = "com.aspire.ponaadmin.web.repository.CategoryOperationDAO.verifyRefrenceByCategoryId";
		// �����������
		TransactionDB tdb = null;
		try {
			tdb = TransactionDB.getTransactionInstance();
				tdb.executeBySQLCode(sqlCode, new Object[] { flag,categoryId  });
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
    /**
     * ���ݻ���ID��ѯ������Ʒ
     * @param categoryId ����ID
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
			throw new BOException(" ���ݻ���ID��ѯ������Ʒ�����쳣:", e);
		}catch (DAOException e) {
			logger.error(" ���ݻ���ID��ѯ������Ʒ�����쳣:", e);
			throw new BOException(" ���ݻ���ID��ѯ������Ʒ�����쳣:", e);
		}
    	
    }
    /**
     * 
     * ɾ����Ʒ
     * @param id ��ƷID
     * @return
     * @throws BOException
     */
    public int delReferenceById(String id)throws BOException{
    	String sqlCode = "com.aspire.ponaadmin.web.repository.CategoryOperationDAO.delReferenceById";
    	try {
			return DB.getInstance().executeBySQLCode(sqlCode, new Object[] {id});
		} catch (Exception e) {
			logger.error(" ɾ����Ʒ�����쳣:", e);
			throw new BOException(" ɾ����Ʒ�����쳣:", e);
		}
    }
    /**
     * 
     * �޸���Ʒ
     * @param id ��ƷID
     * @return
     * @throws BOException
     */
    public int updateReferenceById(String id,String verifyStatus,String delFlag)throws BOException{
    	String sqlCode = "com.aspire.ponaadmin.web.repository.CategoryOperationDAO.updateReferenceById";
    	try {
			return DB.getInstance().executeBySQLCode(sqlCode, new Object[] {verifyStatus,delFlag,id});
		} catch (Exception e) {
			logger.error(" �޸���Ʒ�����쳣:", e);
			throw new BOException(" �޸���Ʒ�����쳣:", e);
		}
    }
    private String modifyStr(String dateStr){
    	if(dateStr==null){
    		return "";
    	}
    	return dateStr.replaceAll(" ", "").replaceAll("-", "").replaceAll(":", "");
    }

}
