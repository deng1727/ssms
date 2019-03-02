package com.aspire.ponaadmin.web.channeladmin.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.db.SQLUtil;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.DataAccessException;
import com.aspire.common.persistence.util.SQLCode;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.page.PageVOInterface;
import com.aspire.ponaadmin.web.channeladmin.vo.OpenOperationChannelVo;


public class OpenOperationChannelDAO
{
    /**
     * 存储日志的实体对象
     */
    protected static JLogger logger = LoggerFactory.getLogger(OpenOperationChannelDAO.class);
    
    /**
     * singleton模式的实例
     */
    private static OpenOperationChannelDAO instance = new OpenOperationChannelDAO();
    
    /**
     * 构造方法，由singleton实例调用
     */
    private OpenOperationChannelDAO(){
    }
    
    /**
     * 获取实例的方法
     */
    public static OpenOperationChannelDAO getInstance(){
        return instance;
    }
    
    private class OpenOperationChannelPageVo implements PageVOInterface{

        @Override
        public Object createObject()
        {
            return new OpenOperationChannelVo();
        }

        @Override
        public void CopyValFromResultSet(Object content, ResultSet rs)
                        throws SQLException
        {
            OpenOperationChannelVo vo = (OpenOperationChannelVo)content;
            vo.setChannelId(rs.getString("channelId"));
            vo.setChannelsId(rs.getString("channelsId"));
            try
            {
                vo.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("createDate")));
            }
            catch (ParseException e)
            {
                logger.debug("创建时间转化格式失败!", e);
                e.printStackTrace();
            }
        }
        
    }
    
    /**
     * 查询所有数据
     * @throws DAOException 
     */
    public void listOpenOperationChannels(PageResult page,OpenOperationChannelVo vo) throws DAOException{
        String sqlCode = "com.aspire.ponaadmin.web.channeladmin.dao.OpenOperationChannelDAO.list";
        String sql = null;
        try
        {
            sql = SQLCode.getInstance().getSQLStatement(sqlCode);
            StringBuffer sb = new StringBuffer(sql);
            List paras = new ArrayList();
            
            if(!"".equals(vo.getChannelsId())){
                sb.append(" and t.channelsId = ?");
                paras.add(SQLUtil.escape(vo.getChannelsId()));
            }
            if(!"".equals(vo.getChannelId())){
                sb.append(" and t.channelId = ?");
                paras.add(SQLUtil.escape(vo.getChannelId()));
            }
            sb.append(" order by t.channelId asc");
            page.excute(sb.toString(), paras.toArray(), new OpenOperationChannelPageVo());
        }
        catch (DataAccessException e)
        {
            logger.debug("查询开放运营渠道列表出错！",e);
            e.printStackTrace();
        }
    }
    
    /**
     * 根据id删除选中的数据
     */
    public void delOpenOperationChannelVoById(String channelId){
        String sqlCode = "com.aspire.ponaadmin.web.channeladmin.dao.OpenOperationChannelDAO.del";
        try
        {
            DB.getInstance().executeBySQLCode(sqlCode, new Object[]{channelId});
        }
        catch (DAOException e)
        {
            logger.debug("删除数据失败", e);
            e.printStackTrace();
        } 
    }
    
    /**
     * 根据id查询实体
     */
    public OpenOperationChannelVo queryOpenOperationChannelVo(String channelId){
        String sqlCode = "com.aspire.ponaadmin.web.channeladmin.dao.OpenOperationChannelDAO.select";
        String sql = null;
        OpenOperationChannelVo vo = new OpenOperationChannelVo();
        try
        {
            sql = DB.getInstance().getSQLByCode(sqlCode);
            ResultSet rs = DB.getInstance().query(sql, new Object[]{channelId});
            if(!rs.next()){
                vo.setChannelId(channelId);
                return vo;
            }
        }
        catch (DAOException e)
        {
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    
    /**
     * 保存OpenOperationChannelVo
     * @throws DAOException 
     */
    public void saveOpenOperationChannelVo(OpenOperationChannelVo vo) throws DAOException{
        if (logger.isDebugEnabled())
        {
            logger.debug("saveOpenChannelMo() is starting ...");
        }
        String sqlCode = "com.aspire.ponaadmin.web.channeladmin.dao.OpenOperationChannelDAO.insert";

        try
        {
            DB.getInstance()
            .executeBySQLCode(sqlCode,
                              new Object[] { vo.getChannelId(),
                    vo.getChannelsId()});
        }
        catch (DAOException e)
        {
            throw new DAOException("保存开放运营渠道失败:", e);
        }
    }
}
