package com.aspire.ponaadmin.web.coManagement.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.DataAccessException;
import com.aspire.common.persistence.util.SQLCode;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.page.PageVOInterface;
import com.aspire.ponaadmin.web.coManagement.vo.ChannelListVO;

public class ChannelListDao {

	private final static JLogger LOGGER = LoggerFactory
			.getLogger(ChannelListDao.class);

	private static ChannelListDao instance = new ChannelListDao();

	private ChannelListDao() {

	}

	public static ChannelListDao getInstance() {
		return instance;
	}
	
	 /**
     * 应用类分页读取VO的实现类
     */
    private class ChannelListPageVO implements PageVOInterface
    {

        public void CopyValFromResultSet(Object content, ResultSet rs)
                        throws SQLException
        {

        	ChannelListVO vo = ( ChannelListVO ) content;
        	vo.setChannelId(rs.getString("channelid"));
        	vo.setChannelName(rs.getString("channelsname"));
        	vo.setChannelType(rs.getString("channeltype"));
        	vo.setChannelsId(rs.getString("channelsid"));
        	vo.setChannelsName(rs.getString("channelsname"));
        	vo.setCreateDate(rs.getTimestamp("createdate"));
        }

        public Object createObject()
        {

            return new ChannelListVO();
        }
    }
    
    /**
     * 查询渠道列表
     * 
     * @param page
     * @param map
     * @throws DAOException
     */
    public void queryChannelList(PageResult page,Map<String,Object> map) throws DAOException{
		if (LOGGER.isDebugEnabled())
        {
			LOGGER.debug("queryChannelList() is starting ...");
        }
		StringBuffer sb = new StringBuffer("");
        String sqlCode = "com.aspire.ponaadmin.web.coManagement.dao.ChannelNoDAO.queryChannelList";
        List paras = new ArrayList();
        try
        {
        	sb.append(DB.getInstance().getSQLByCode(sqlCode));
        	/**
			 * 渠道Id
			 */
        	String channelId = (String) map.get("channelId");
			/**
			 * 渠道名称
			 */
			String channelName = (String) map.get("channelName");
			/**
			 * 渠道商Id
			 */
			String distributorId = (String) map.get("distributorId");
			/**
			 * 渠道商名称
			 */
			String distributorName = (String) map.get("distributorName");
			/**
			 * 渠道类型
			 */
			String channelType = (String) map.get("channelType");
			
			if(channelId != null && !"".equals(channelId)){
				sb.append(" and i.channelid = ?");
				paras.add(channelId);
			}
			
			if(channelName != null && !"".equals(channelName)){
				sb.append(" and i.channelname like '%" + channelName + "%'");
			}
			
			if(channelType != null && !"".equals(channelType)){
				sb.append(" and i.channeltype = ?");
				paras.add(channelType);
			}
			
			if(distributorId != null && !"".equals(distributorId)){
				sb.append(" and c.channelsid = ?");
				paras.add(distributorId);
			}
			
			if(distributorName != null && !"".equals(distributorName)){
				sb.append(" and c.channelsname like '%" + distributorName + " %'");
			}
			
			
			
            page.excute(sb.toString(), paras.toArray(), new ChannelListPageVO());
        }
        catch (DAOException e) {
        	LOGGER.error("查询渠道列表发生异常:", e);
        	throw new DAOException("查询渠道列表发生异常:",e);
		}
	}
    
    /**
     * 查询渠道信息
     * 
     * @param channelId 渠道Id
     * @return
     * @throws DAOException
     */
    public ChannelListVO getChannelDetail(String channelId) throws DAOException{
    	String sqlCode = "com.aspire.ponaadmin.web.coManagement.dao.ChannelNoDAO.getChannelDetail";
    	ResultSet rs;
    	ChannelListVO vo = new ChannelListVO();
    	try {
			rs = DB.getInstance().queryBySQLCode(sqlCode, new Object[]{channelId});
			while(rs.next()){
				vo.setChannelId(rs.getString("channelid"));
	        	vo.setChannelName(rs.getString("channelsname"));
	        	vo.setChannelType(rs.getString("channeltype"));
	        	vo.setChannelsId(rs.getString("channelsid"));
	        	vo.setChannelsName(rs.getString("channelsname"));
	        	vo.setCreateDate(rs.getTimestamp("createdate"));
	        	vo.setChannelDesc(rs.getString("channelDesc"));
			}
		} catch (DAOException e) {
			LOGGER.error("查询渠道信息发生异常:", e);
        	throw new DAOException("查询渠道信息发生异常:",e);
		} catch (SQLException e) {
			LOGGER.error("查询渠道信息发生异常:", e);
        	throw new DAOException("查询渠道信息发生异常:",e);
		}
    	
    	return vo;
    }

}
