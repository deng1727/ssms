package com.aspire.ponaadmin.web.channelUser.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.channelUser.vo.ChannelCategoryVO;
import com.aspire.ponaadmin.web.channeladmin.vo.OpenChannelsCategoryVo;

public class OpenChannelsCategoryDAO {
	 /**
     * 存储日志的实体对象
     */
    protected static JLogger logger = LoggerFactory.getLogger(OpenChannelsCategoryDAO.class);
    
    /**
     * singleton模式的实例
     */
    private static OpenChannelsCategoryDAO instance = new OpenChannelsCategoryDAO();
    
    /**
     * 构造方法，由singleton实例调用
     */
    private OpenChannelsCategoryDAO(){
    }
    
    /**
     * 获取实例的方法
     */
    public static OpenChannelsCategoryDAO getInstance(){
        return instance;
    }
    
	/**
     * 根据用户id查询其跟货架
     * @throws DAOException 
     */
    public List<ChannelCategoryVO> queryOpenChannelsCategoryList(String channelsId) throws DAOException{
        OpenChannelsCategoryVo vo = null;
        String sqlCode = "com.aspire.ponaadmin.web.channelUser.dao.OpenChannelsCategoryDAO.queryOpenChannelsCategoryList";
        List<ChannelCategoryVO> list = null;
        try
        {
            ResultSet rs = DB.getInstance().queryBySQLCode(sqlCode, new Object[]{channelsId});
            if(rs != null){
            	list = formatDataFromRs(rs);
            }
        }
        catch (DAOException e)
        {
            logger.debug("查询根货架信息失败", e);
            e.printStackTrace();
            throw new DAOException();
        }
        catch (SQLException e)
        {
            logger.debug("查询根货架信息失败", e);
            e.printStackTrace();
            throw new DAOException();
        }
        return list;
    }
    
    private List<ChannelCategoryVO> formatDataFromRs(ResultSet rs) throws SQLException{
        List<ChannelCategoryVO> list = new ArrayList<ChannelCategoryVO>();
        try
        {
            if(rs != null){
            	while(rs.next()){
            		ChannelCategoryVO vo = new ChannelCategoryVO();
            		vo.setId(rs.getString("Id"));
            		vo.setCategoryId(rs.getString("categoryId"));
                    vo.setCategoryName(rs.getString("name"));
                    vo.setParentcategoryId(rs.getString("parentcategoryId"));
                    list.add(vo);
            	}
               
            }
        }
        catch (SQLException e)
        {
            logger.debug("查询根货架信息失败", e);
           throw new SQLException("查询根货架信息失败", e);
        }
        return list;
    }
}
