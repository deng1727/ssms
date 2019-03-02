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
     * 查询渠道信息
     * 
     * @param page
     * @param channelsNo 用户登录Id
	 * @throws BOException 
     */
    public void queryChannelInfoList(PageResult page,String channelsNo) throws BOException {
    	try {
			MyChannelDAO.getInstance().queryChannelInfoList(page, channelsNo);
		} catch (DAOException e) {
			LOGGER.error("查询渠道信息发生异常:", e);
        	throw new BOException("查询渠道信息发生异常:",e);
		}
    }
    
    /**
	 * 查询渠道总数
	 * 
	 * @param channelsNo 用户登录Id
	 * @return
     * @throws BOException 
	 */
	public Map<String,Object> queryChannelsNoTotal(String channelsNo) throws BOException{
		try {
			return MyChannelDAO.getInstance().queryChannelsNoTotal(channelsNo);
		} catch (DAOException e) {
			LOGGER.error("查询渠道信息发生异常:", e);
        	throw new BOException("查询渠道信息发生异常:",e);
		}
	}
	
	/**
	 * 添加渠道信息
	 * @param map
	 * @throws BOException 
	 */
	public void insertChannelContent(Map<String,Object> map) throws BOException{
		try {
			MyChannelDAO.getInstance().insertChannelContent(map);
		} catch (DAOException e) {
			LOGGER.error("添加渠道信息发生异常:", e);
        	throw new BOException("添加渠道信息发生异常:",e);
		}
	}
	
	 /**
     * 查询渠道信息
     * 
     * @param channelId 渠道Id
     * @return
	 * @throws BOException 
     */
    public MyChannelVO getChannelDetail(String channelId) throws BOException{
    	try {
			return MyChannelDAO.getInstance().getChannelDetail(channelId);
		} catch (DAOException e) {
			LOGGER.error("查询渠道信息发生异常:", e);
        	throw new BOException("查询渠道信息发生异常:",e);
		}
    }
    
    /**
	 * 编辑渠道信息
	 * @param map
     * @throws BOException 
	 * @throws DAOException
	 */
	public void updateChannelContent(Map<String,Object> map) throws BOException{
		try {
			MyChannelDAO.getInstance().updateChannelContent(map);
		} catch (DAOException e) {
			LOGGER.error("编辑渠道信息发生异常:", e);
        	throw new BOException("编辑渠道信息发生异常:",e);
		}
	}
	/**
	 * 查询未用的渠道号
	 * 
	 * @return
	 * @throws BOException 
	 */
	public String queryChannelsNo() throws BOException {
		try {
			return MyChannelDAO.getInstance().queryChannelsNo();
		} catch (DAOException e) {
			LOGGER.debug("查询未用的渠道号发生异常:", e);
			throw new BOException("查询未用的渠道号发生异常:", e);
		}
	}

}
