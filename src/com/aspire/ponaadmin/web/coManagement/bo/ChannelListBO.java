package com.aspire.ponaadmin.web.coManagement.bo;

import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.coManagement.dao.ChannelListDao;
import com.aspire.ponaadmin.web.coManagement.vo.ChannelListVO;

public class ChannelListBO {
	
	private final static JLogger LOGGER = LoggerFactory
			.getLogger(ChannelListBO.class);

	private static ChannelListBO instance = new ChannelListBO();

	private ChannelListBO() {

	}

	public static ChannelListBO getInstance() {
		return instance;
	}
	
	/**
     * ��ѯ�����б�
     * 
     * @param page
     * @param map
	 * @throws BOException 
     */
    public void queryChannelList(PageResult page,Map<String,Object> map) throws BOException{
    	try {
			ChannelListDao.getInstance().queryChannelList(page, map);
		} catch (DAOException e) {
			LOGGER.error("��ѯ�����б����쳣:", e);
			throw new BOException("��ѯ�����б����쳣:", e);
		}
    }
    
    /**
     * ��ѯ������Ϣ
     * 
     * @param channelId ����Id
     * @return
     * @throws BOException 
     */
    public ChannelListVO getChannelDetail(String channelId) throws BOException{
    	try {
			return ChannelListDao.getInstance().getChannelDetail(channelId);
		} catch (DAOException e) {
			LOGGER.error("��ѯ������Ϣ�����쳣:", e);
			throw new BOException("��ѯ������Ϣ�����쳣:", e);
		}
    }

}
