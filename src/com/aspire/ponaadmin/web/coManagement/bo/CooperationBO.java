package com.aspire.ponaadmin.web.coManagement.bo;

import java.util.HashMap;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.coManagement.dao.CooperationDAO;

public class CooperationBO {

	private final static JLogger LOGGER = LoggerFactory
			.getLogger(CooperationBO.class);

	private static CooperationBO instance = new CooperationBO();

	private CooperationBO() {

	}

	public static CooperationBO getInstance() {
		return instance;
	}
	
	/**
	 * 查询合作商列表
	 * 
	 * @param page
	 * @param cooperationId 合作商ID
	 * @param cooperationName 合作商名称
	 * @throws DAOException
	 */
	public void queryCooperationList(PageResult page, String cooperationId, String cooperationName) throws BOException{
		try {
			CooperationDAO.getInstance().queryCooperationList(page, cooperationId, cooperationName);
		} catch (DAOException e) {
			LOGGER.error("查询合作商列表异常:", e);
			throw new BOException("查询合作商列表异常:",e);
		}
	}
	
	/**
     * 判断配置项对应的货架是否存在
     * @param categoryId
     * @return
     * @throws DAOException
     */
    public boolean isExistInCategory()throws BOException{
    	try {
    		 return CooperationDAO.getInstance().isExistInCategory();
		} catch (Exception e) {
			LOGGER.error("判断配置项对应的货架是否存在:", e);
			throw new BOException("判断配置项对应的货架是否存在:",e);
		}
    }
	
	/**
	 * 添加合作商
	 * 
	 * @param map
	 * @throws DAOException
	 */
	public void insertCooperation(Map<String,Object> map) throws BOException{
		try {
			CooperationDAO.getInstance().insertCooperation(map);
		} catch (DAOException e) {
			LOGGER.error("添加合作商异常:", e);
			throw new BOException("添加合作商异常:",e);
		}
	}
	
	/**
	 * 查询合作商信息
	 * 
	 * @param cooperationId 合作商ID
	 * @return
	 * @throws DAOException
	 */
	public Map<String,Object> queryCooperation(String cooperationId) throws BOException{
		try {
			return CooperationDAO.getInstance().queryCooperation(cooperationId);
		} catch (DAOException e) {
			LOGGER.error("查询合作商信息异常:", e);
			throw new BOException("查询合作商信息异常:",e);
		}
	}
	
	/**
	 * 更新合作商信息
	 * @param map
	 * @throws BOException 
	 * @throws DAOException
	 */
	public void updateCooperation(Map<String,Object> map) throws BOException{
		 try {
			CooperationDAO.getInstance().updateCooperation(map);
		} catch (DAOException e) {
			LOGGER.error("更新合作商信息异常:", e);
			throw new BOException("更新合作商信息异常:",e);
		}
	}
	
	/**
	 * 更新合作商状态信息
	 * @param map
	 * @throws BOException 
	 * @throws DAOException
	 */
	public void operationCooperation(Map<String,Object> map) throws BOException{
		try {
			CooperationDAO.getInstance().operationCooperation(map);
		} catch (DAOException e) {
			LOGGER.error("更新合作商状态信息异常:", e);
			throw new BOException("更新合作商状态信息异常:",e);
		}
	}

}
