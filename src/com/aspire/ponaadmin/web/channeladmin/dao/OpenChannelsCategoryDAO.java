package com.aspire.ponaadmin.web.channeladmin.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.RowSet;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.channeladmin.vo.OpenChannelsCategoryVo;


public class OpenChannelsCategoryDAO
{
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
    
    private OpenChannelsCategoryVo formatDataFromRs(ResultSet rs){
        OpenChannelsCategoryVo vo = new OpenChannelsCategoryVo();
        try
        {
            if(rs != null){
                vo.setCategoryId(rs.getString("categoryId"));
                vo.setChannelsId(rs.getString("channelsId"));
            }
        }
        catch (SQLException e)
        {
            logger.debug("查询根货架信息失败", e);
            e.printStackTrace();
        }
        return vo;
    }
    
    /**
     * 根据id查询实体
     * @throws DAOException 
     */
    public OpenChannelsCategoryVo queryOpenChannelsCategoryVo(String channelsId) throws DAOException{
        OpenChannelsCategoryVo vo = null;
        String sqlCode = "com.aspire.ponaadmin.web.channeladmin.dao.OpenChannelsCategoryDAO.select";
        try
        {
            ResultSet rs = DB.getInstance().queryBySQLCode(sqlCode, new Object[]{channelsId});
            if(rs.next()){
               vo = formatDataFromRs(rs);
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
        return vo;
    }
    
    /**
     * 查询全量渠道商对应跟货架
     * @throws DAOException 
     */
    public List queryOpenChannelsCategoryList(){
        List list = new ArrayList();
        String sqlCode = "com.aspire.ponaadmin.web.channeladmin.dao.OpenChannelsCategoryDAO.selectALL";
        try
        {
            ResultSet rs = DB.getInstance().queryBySQLCode(sqlCode,null);
            while(rs.next()){
            	OpenChannelsCategoryVo vo = formatDataFromRs(rs);
            	list.add(vo);
            }
        }
        catch (DAOException e)
        {
            logger.error("查询全量渠道商对应跟货架信息失败", e);
        }
        catch (SQLException e)
        {
            logger.error("查询全量渠道商对应跟货架信息失败", e);
        }
        return list;
    }
    
    /**
     * 保存OpenChannelsCategoryVo
     * @throws DAOException 
     */
    public void saveOpenChannelsCategoryVo(OpenChannelsCategoryVo vo) throws DAOException{
        if (logger.isDebugEnabled())
        {
            logger.debug("saveOpenChannelsCategoryVo() is starting ...");
        }
        String sqlCode = "com.aspire.ponaadmin.web.channeladmin.dao.OpenChannelsCategoryDAO.insert";
        try
        {
            if(queryOpenChannelsCategoryVo(vo.getChannelsId())!=null){
                sqlCode = "com.aspire.ponaadmin.web.channeladmin.dao.OpenChannelsCategoryDAO.update";
                DB.getInstance().executeBySQLCode(sqlCode, new Object[]{vo.getCategoryId(),vo.getChannelsId()});
            }else{
                DB.getInstance().executeBySQLCode(sqlCode, new Object[]{vo.getChannelsId(),vo.getCategoryId()});
            }
            
        }
        catch (DAOException e)
        {
            throw new DAOException("保存根货架配置失败:", e);
        }
    }
}
