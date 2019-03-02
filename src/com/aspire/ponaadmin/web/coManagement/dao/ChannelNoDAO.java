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
     * Ӧ�����ҳ��ȡVO��ʵ����
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
        	LOGGER.error("��sql.properties�л�ȡsql���ʱ�������󣬶�ӦsqlCode������:", e);
            throw new DAOException("��sql.properties�л�ȡsql���ʱ�������󣬶�ӦsqlCode�����ڡ�",
                                   e);
        } catch (DAOException e) {
        	LOGGER.error("��ѯ�������б����쳣:", e);
        	throw new DAOException("��ѯ�������б����쳣:",e);
		}
	}
	/**
	 * ���������
	 * @param userName �û���
	 * @param channelsNo ������
	 * @throws DAOException
	 */
	public void insertChannelsNo(String userName,String channelsNo) throws DAOException{
		String sqlCode = "com.aspire.ponaadmin.web.coManagement.dao.ChannelNoDAO.insertChannelsNo";
		try {
			DB.getInstance().executeBySQLCode(sqlCode, new Object[]{userName,channelsNo});
		} catch (DAOException e) {
			LOGGER.error("��������ŷ����쳣:", e);
			throw new DAOException("��������ŷ����쳣:",e);
		}
	}
	/**
	 * ��ѯ������
	 * 
	 * @param channelsNo ������
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
        	LOGGER.debug("��ѯ�����ŷ����쳣:", e);
            throw new DAOException("��ѯ�����ŷ����쳣:", e);
        }
        catch (SQLException e)
        {
        	LOGGER.debug("��ѯ�����ŷ����쳣:", e);
            throw new DAOException("��ѯ�����ŷ����쳣:", e);
        }
	}
	
	/**
	 * ��ѯ����������
	 * 
	 * @param status �����Ų���״̬
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
        	LOGGER.debug("��ѯ���������������쳣:", e);
            throw new DAOException("��ѯ���������������쳣:", e);
        }
        catch (SQLException e)
        {
        	LOGGER.debug("��ѯ���������������쳣:", e);
            throw new DAOException("��ѯ���������������쳣:", e);
        }
	}
	

}
