package com.aspire.ponaadmin.web.channelUser.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.DataAccessException;
import com.aspire.common.persistence.util.SQLCode;
import com.aspire.dotcard.awms.CategoryVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.page.PageVOInterface;
import com.aspire.ponaadmin.web.channelUser.vo.ChannelCategoryManageVO;
import com.aspire.ponaadmin.web.channelUser.vo.ChannelVO;

public class ChannelCategoryManageDAO {

	private final static JLogger logger = LoggerFactory.getLogger(ChannelCategoryManageDAO.class);
	
	private static ChannelCategoryManageDAO instance = new ChannelCategoryManageDAO();
	private ChannelCategoryManageDAO(){
		
	}
	public static ChannelCategoryManageDAO getInstance(){
		return instance;
	}
	/**
	 * �����������˺ţ���ѯ�����б�
	 * @param ChannelNO
	 * @return
	 */
	public void getCategoryListByChannelNO(PageResult page,String channelNO) throws DAOException{
		if (logger.isDebugEnabled())
        {
            logger.debug("getCategoryListByChannelNO("+page+"," + channelNO + ")") ;
        }
		String sqlCode = "channelUser.dao.ChannelCategoryManageDAO.getCategoryListByChannelNO().QUERY" ;
        String sql = null;
        
        List paras = new ArrayList();
        try
        {
        	 sql = SQLCode.getInstance().getSQLStatement(sqlCode);
        	 StringBuffer sqlBuffer = new StringBuffer(sql);
     		
     		// ����������sql�Ͳ���
     		if (!"".equals(channelNO))
     		{
     			// sql += " and t.id ='" + id + "'";
     			sqlBuffer.append(" and t.channelsno =? ");
     			paras.add(channelNO);
     		}
     		sqlBuffer.append(" order by t.categoryid asc");
        	page.excute(sqlBuffer.toString(), paras.toArray(),
					new ChannelCategoryPageVO());

        }
        catch (DataAccessException e)
		{
			throw new DAOException(
					"��sql.properties�л�ȡsql���ʱ�������󣬶�ӦsqlCode�����ڡ�", e);
		}
	}
	public CategoryVO getCategoryInfoByID(String categoryId) throws DAOException{
		if (logger.isDebugEnabled())
        {
            logger.debug("getCategoryInfoByID(" + categoryId + ")") ;
        }
		String sqlCode = "channelUser.dao.ChannelCategoryManageDAO.getCategoryInfoByID().QUERY" ;
		Object[] paras =
        {categoryId} ;
    ResultSet rs = null ;
    CategoryVO categoryVO = null;
    try
    {
        rs = DB.getInstance().queryBySQLCode(sqlCode, paras) ;
        if (rs != null && rs.next()){
        	categoryVO = new CategoryVO();
        	categoryVO.setCategoryid(rs.getString("categoryid"));
        	categoryVO.setChangedate(rs.getString("changedate"));
        	categoryVO.setCityid(rs.getString("cityid"));
        	categoryVO.setCtype(rs.getString("ctype"));
        	categoryVO.setDelflag(rs.getString("delflag"));
        	categoryVO.setDescs(rs.getString("descs"));
        	categoryVO.setDevicecategory(rs.getString("devicecategory"));
        	categoryVO.setEnddate(rs.getString("enddate"));
        }
        return categoryVO;
    }
    catch (SQLException e)
    {
        throw new DAOException("getCategoryInfoByID error.", e) ;

    }
    finally
    {
        if (rs != null)
        {
            try
            {
                rs.close() ;
            }
            catch (Exception e)
            {
                logger.error(e) ;
            }
        }
    }
	}
	
	 /**
     * Ӧ�����ҳ��ȡVO��ʵ����
     */
    private class ChannelCategoryPageVO implements PageVOInterface
    {
    	 public void CopyValFromResultSet(Object content, ResultSet rs)
         throws SQLException{
    		 ChannelCategoryManageVO channelCategoryManageVO = (ChannelCategoryManageVO)content;
    		 channelCategoryManageVO.setCategoryId("categoryId");
         	channelCategoryManageVO.setCategoryName("CategoryName");
         	channelCategoryManageVO.setChannelsId("channelsId");
         	channelCategoryManageVO.setChannelsName("channelsName");
    	 }
    	 public Object createObject()
         {

             return new ChannelCategoryManageVO();
         }
    }
}
