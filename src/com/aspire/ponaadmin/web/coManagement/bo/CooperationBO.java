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
	 * ��ѯ�������б�
	 * 
	 * @param page
	 * @param cooperationId ������ID
	 * @param cooperationName ����������
	 * @throws DAOException
	 */
	public void queryCooperationList(PageResult page, String cooperationId, String cooperationName) throws BOException{
		try {
			CooperationDAO.getInstance().queryCooperationList(page, cooperationId, cooperationName);
		} catch (DAOException e) {
			LOGGER.error("��ѯ�������б��쳣:", e);
			throw new BOException("��ѯ�������б��쳣:",e);
		}
	}
	
	/**
     * �ж��������Ӧ�Ļ����Ƿ����
     * @param categoryId
     * @return
     * @throws DAOException
     */
    public boolean isExistInCategory()throws BOException{
    	try {
    		 return CooperationDAO.getInstance().isExistInCategory();
		} catch (Exception e) {
			LOGGER.error("�ж��������Ӧ�Ļ����Ƿ����:", e);
			throw new BOException("�ж��������Ӧ�Ļ����Ƿ����:",e);
		}
    }
	
	/**
	 * ��Ӻ�����
	 * 
	 * @param map
	 * @throws DAOException
	 */
	public void insertCooperation(Map<String,Object> map) throws BOException{
		try {
			CooperationDAO.getInstance().insertCooperation(map);
		} catch (DAOException e) {
			LOGGER.error("��Ӻ������쳣:", e);
			throw new BOException("��Ӻ������쳣:",e);
		}
	}
	
	/**
	 * ��ѯ��������Ϣ
	 * 
	 * @param cooperationId ������ID
	 * @return
	 * @throws DAOException
	 */
	public Map<String,Object> queryCooperation(String cooperationId) throws BOException{
		try {
			return CooperationDAO.getInstance().queryCooperation(cooperationId);
		} catch (DAOException e) {
			LOGGER.error("��ѯ��������Ϣ�쳣:", e);
			throw new BOException("��ѯ��������Ϣ�쳣:",e);
		}
	}
	
	/**
	 * ���º�������Ϣ
	 * @param map
	 * @throws BOException 
	 * @throws DAOException
	 */
	public void updateCooperation(Map<String,Object> map) throws BOException{
		 try {
			CooperationDAO.getInstance().updateCooperation(map);
		} catch (DAOException e) {
			LOGGER.error("���º�������Ϣ�쳣:", e);
			throw new BOException("���º�������Ϣ�쳣:",e);
		}
	}
	
	/**
	 * ���º�����״̬��Ϣ
	 * @param map
	 * @throws BOException 
	 * @throws DAOException
	 */
	public void operationCooperation(Map<String,Object> map) throws BOException{
		try {
			CooperationDAO.getInstance().operationCooperation(map);
		} catch (DAOException e) {
			LOGGER.error("���º�����״̬��Ϣ�쳣:", e);
			throw new BOException("���º�����״̬��Ϣ�쳣:",e);
		}
	}

}
