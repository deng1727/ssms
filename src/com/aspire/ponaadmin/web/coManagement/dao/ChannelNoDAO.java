package com.aspire.ponaadmin.web.coManagement.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.DataAccessException;
import com.aspire.common.persistence.util.SQLCode;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.page.PageVOInterface;
import com.aspire.ponaadmin.web.coManagement.vo.ChannelsNoVO;
import com.aspire.ponaadmin.web.coManagement.vo.CooperationVO;

public class ChannelNoDAO {
	
private final static JLogger LOGGER = LoggerFactory.getLogger(CooperationDAO.class);
	
	private static ChannelNoDAO instance = new ChannelNoDAO();
	
	private ChannelNoDAO(){
		
	}
	
	public static ChannelNoDAO getInstance(){
		return instance;
	}
	
	 /**
     * 应用类分页读取VO的实现类
     */
    private class ChannelNoPageVO implements PageVOInterface
    {

        public void CopyValFromResultSet(Object content, ResultSet rs)
                        throws SQLException
        {

        	ChannelsNoVO vo = ( ChannelsNoVO ) content;
        	vo.setCreateDate(rs.getTimestamp("createdate"));
        	vo.setOperator(rs.getString("operator"));
        	vo.setTotal(rs.getString("total"));
        }

        public Object createObject()
        {

            return new ChannelsNoVO();
        }
    }
	
	public void queryChannelsNoList(PageResult page) throws DAOException{
		if (LOGGER.isDebugEnabled())
        {
			LOGGER.debug("queryChannelsNo() is starting ...");
        }
        String sqlCode = "com.aspire.ponaadmin.web.coManagement.dao.ChannelNoDAO.queryChannelsNoList";
        try
        {
            page.excute(SQLCode.getInstance().getSQLStatement(sqlCode), new Object[]{}, new ChannelNoPageVO());
        }
        catch (DataAccessException e)
        {
        	LOGGER.error("从sql.properties中获取sql语句时发生错误，对应sqlCode不存在:", e);
            throw new DAOException("从sql.properties中获取sql语句时发生错误，对应sqlCode不存在。",
                                   e);
        } catch (DAOException e) {
        	LOGGER.error("查询渠道号列表发生异常:", e);
        	throw new DAOException("查询渠道号列表发生异常:",e);
		}
	}
	/**
	 * 添加渠道号
	 * @param userName 用户名
	 * @param channelsNo 渠道号
	 * @throws DAOException
	 */
	public void insertChannelsNo(String userName,String channelsNo) throws DAOException{
		String sqlCode = "com.aspire.ponaadmin.web.coManagement.dao.ChannelNoDAO.insertChannelsNo";
		try {
			DB.getInstance().executeBySQLCode(sqlCode, new Object[]{userName,channelsNo});
		} catch (DAOException e) {
			LOGGER.error("添加渠道号发生异常:", e);
			throw new DAOException("添加渠道号发生异常:",e);
		}
	}
	/**
	 * 查询渠道号
	 * 
	 * @param channelsNo 渠道号
	 * @return
	 * @throws DAOException
	 */
	public boolean queryChannelsNo(String channelsNo) throws DAOException{
		String sqlCode = "com.aspire.ponaadmin.web.coManagement.dao.ChannelNoDAO.queryChannelsNo";
        try
        {
            ResultSet rs = DB.getInstance().queryBySQLCode(sqlCode, new Object[]{channelsNo});
            if(rs.next()){
            	return true;
            }
            return false;
        }
        catch (DAOException e)
        {
        	LOGGER.debug("查询渠道号发生异常:", e);
            throw new DAOException("查询渠道号发生异常:", e);
        }
        catch (SQLException e)
        {
        	LOGGER.debug("查询渠道号发生异常:", e);
            throw new DAOException("查询渠道号发生异常:", e);
        }
	}
	
	/**
	 * 查询渠道号总数
	 * 
	 * @param status 渠道号操作状态
	 * @return
	 * @throws DAOException
	 */
	public int queryChannelsNoTotal(String status) throws DAOException{
		String sqlCode = "com.aspire.ponaadmin.web.coManagement.dao.ChannelNoDAO.queryChannelsNoTotal";
		int number = 0;
        try
        {
            ResultSet rs = DB.getInstance().queryBySQLCode(sqlCode, new Object[]{status});
            if(rs.next()){
            	number = Integer.parseInt(rs.getString("total"));
            	return number;
            }
            return number;
        }
        catch (DAOException e)
        {
        	LOGGER.debug("查询渠道号总数发生异常:", e);
            throw new DAOException("查询渠道号总数发生异常:", e);
        }
        catch (SQLException e)
        {
        	LOGGER.debug("查询渠道号总数发生异常:", e);
            throw new DAOException("查询渠道号总数发生异常:", e);
        }
	}
	

}
