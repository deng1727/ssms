package com.aspire.ponaadmin.webpps.mychannel.bo;

import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.webpps.mychannel.dao.MyChannelDAO;
import com.aspire.ponaadmin.webpps.mychannel.vo.MyChannelVO;

public class MyChannelBO {
	
	private final static JLogger LOGGER = LoggerFactory
			.getLogger(MyChannelBO.class);

	private static MyChannelBO instance = new MyChannelBO();

	private MyChannelBO() {

	}

	public static MyChannelBO getInstance() {
		return instance;
	}
	
	 /**
     * ��ѯ������Ϣ
     * 
     * @param page
     * @param channelsNo �û���¼Id
	 * @throws BOException 
     */
    public void queryChannelInfoList(PageResult page,String channelsNo) throws BOException {
    	try {
			MyChannelDAO.getInstance().queryChannelInfoList(page, channelsNo);
		} catch (DAOException e) {
			LOGGER.error("��ѯ������Ϣ�����쳣:", e);
        	throw new BOException("��ѯ������Ϣ�����쳣:",e);
		}
    }
    
    /**
	 * ��ѯ��������
	 * 
	 * @param channelsNo �û���¼Id
	 * @return
     * @throws BOException 
	 */
	public Map<String,Object> queryChannelsNoTotal(String channelsNo) throws BOException{
		try {
			return MyChannelDAO.getInstance().queryChannelsNoTotal(channelsNo);
		} catch (DAOException e) {
			LOGGER.error("��ѯ������Ϣ�����쳣:", e);
        	throw new BOException("��ѯ������Ϣ�����쳣:",e);
		}
	}
	
	/**
	 * ���������Ϣ
	 * @param map
	 * @throws BOException 
	 */
	public void insertChannelContent(Map<String,Object> map) throws BOException{
		try {
			MyChannelDAO.getInstance().insertChannelContent(map);
		} catch (DAOException e) {
			LOGGER.error("���������Ϣ�����쳣:", e);
        	throw new BOException("���������Ϣ�����쳣:",e);
		}
	}
	
	 /**
     * ��ѯ������Ϣ
     * 
     * @param channelId ����Id
     * @return
	 * @throws BOException 
     */
    public MyChannelVO getChannelDetail(String channelId) throws BOException{
    	try {
			return MyChannelDAO.getInstance().getChannelDetail(channelId);
		} catch (DAOException e) {
			LOGGER.error("��ѯ������Ϣ�����쳣:", e);
        	throw new BOException("��ѯ������Ϣ�����쳣:",e);
		}
    }
    
    /**
	 * �༭������Ϣ
	 * @param map
     * @throws BOException 
	 * @throws DAOException
	 */
	public void updateChannelContent(Map<String,Object> map) throws BOException{
		try {
			MyChannelDAO.getInstance().updateChannelContent(map);
		} catch (DAOException e) {
			LOGGER.error("�༭������Ϣ�����쳣:", e);
        	throw new BOException("�༭������Ϣ�����쳣:",e);
		}
	}
	/**
	 * ��ѯδ�õ�������
	 * 
	 * @return
	 * @throws BOException 
	 */
	public String queryChannelsNo() throws BOException {
		try {
			return MyChannelDAO.getInstance().queryChannelsNo();
		} catch (DAOException e) {
			LOGGER.debug("��ѯδ�õ������ŷ����쳣:", e);
			throw new BOException("��ѯδ�õ������ŷ����쳣:", e);
		}
	}

}
