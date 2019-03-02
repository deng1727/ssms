package com.aspire.ponaadmin.web.channeladmin.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.aspire.ponaadmin.web.channeladmin.vo.OpenChannelsVO;
import com.aspire.ponaadmin.web.util.TimeUtils;

public class OpenChannelsDAO
{
    /**
     * 记录日志的实例对象
     */
    
    protected static JLogger logger = LoggerFactory.getLogger(OpenChannelsDAO.class);
    
    /**
     * singleton模式的实例
     */
    
    private static OpenChannelsDAO instance = new OpenChannelsDAO();
   
    /**
     * 构造方法，由singleton模式调用
     */
    
    private OpenChannelsDAO()
    {
    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    
    public static OpenChannelsDAO getInstance()
    {
        return instance;
    }
    /*
     * 为对象设值
     */
    public void fromOpenChannelsVOByRs(OpenChannelsVO vo,ResultSet rs) throws SQLException
    {
        vo.setChannelsId(rs.getString("channelsid "));
        vo.setChannelsName(rs.getString("channelsname "));
        vo.setParentChannelsId(rs.getString("parentchannelsid "));
        vo.setParentChannelsName(rs.getString("parentchannelsname "));
        vo.setChannelsDesc(rs.getString("channelsdesc "));
        vo.setChannelsNo(rs.getString("channelsno "));
        vo.setChannelsPwd(rs.getString("channelspwd "));
        vo.setStatus(rs.getString("status "));
        vo.setModifyDate(rs.getDate("modifydate "));
        vo.setCreateDate(rs.getDate("createdate "));
    }
    /*
     * 返回渠道商列表
     */
    /**
     * 查询所有渠道商的信息
     * @throws DAOException 
     */
    public void queryOpenChannelsList(PageResult page,OpenChannelsVO vo) throws DAOException{
        String sqlCode = "com.aspire.ponaadmin.web.channeladmin.dao.OpenChannelsDAO.queryOpenChannelsList().select";
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
            if(!"".equals(vo.getChannelsName())){
                sb.append(" and t.channelsName like ?");
                paras.add("%"+SQLUtil.escape(vo.getChannelsName())+"%");
            }
            sb.append(" order by t.channelsId asc ");
            page.excute(sb.toString(), paras.toArray(), new OpenChannelsPageVO());
        }
        catch (DataAccessException e)
        {
            logger.debug("查询渠道商信息出错！",e);
            e.printStackTrace();
        }
    }

 
    /**
     * 用于新增渠道商信息
     * 
     * @param OpenChannelsVO
     */
    public void save(OpenChannelsVO openChannelsVO) throws DAOException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("addOpenChannels() is starting ...");
        }
       
        String sqlCode="com.aspire.ponaadmin.web.channeladmin.dao.OpenChannelsDAO.addOpenChannels().add";
        
        try
        {
            DB.getInstance().executeBySQLCode(sqlCode, 
                                              new Object[]{openChannelsVO.getChannelsName(),
                                                           openChannelsVO.getChannelsDesc(),
                                                           openChannelsVO.getChannelsNo(),
                                                           openChannelsVO.getChannelsPwd()});
        }
        catch ( DAOException e)
        {
            throw new DAOException("新增渠道商信息发生异常",e);
        }
    }
    
   
    
    /*
     * 用于更新渠道商信息
     */
    public void updateChannels(OpenChannelsVO openChannelsVO) throws DAOException {
        // TODO Auto-generated method stub

        String sqlCode = "com.aspire.ponaadmin.web.channeladmin.dao.OpenChannelsDAO.updateChannels().update";

        try
        {
            DB.getInstance()
              .executeBySQLCode(sqlCode,
                                new Object[] {
                              openChannelsVO.getChannelsName(),
                              openChannelsVO.getChannelsDesc(),
                              openChannelsVO.getChannelsId()
                      });
        }
        catch (DAOException e)
        {
            throw new DAOException("变更渠道商时发生异常:", e);
        }
        
    }
    public OpenChannelsVO queryChannelsVO(String channelsId)
                    throws DAOException
    {

        String sqlCode = "com.aspire.ponaadmin.web.channeladmin.dao.OpenChannelsDAO.queryChannelsVO().select";
        ResultSet rs = null;
        OpenChannelsVO vo = new OpenChannelsVO();
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode,
                                                 new Object[] { channelsId });

            if (rs.next()){
                vo.setChannelsId(rs.getString("channelsid"));
                vo.setChannelsName(rs.getString("channelsname"));
                vo.setChannelsDesc(rs.getString("channelsdesc")); 
                vo.setChannelsNo(rs.getString("channelsNo"));
                vo.setParentChannelsId(rs.getString("parentChannelsId"));
                vo.setParentChannelsName(rs.getString("parentChannelsName"));
                vo.setStatus(rs.getString("status"));
                vo.setCreateDate(rs.getDate("CREATEDATE"));
            }
        }
        catch (DAOException e)
        {
            throw new DAOException("返回渠道商信息查询发生异常:", e);
        }
        catch (SQLException e)
        {
            throw new DAOException("返回渠道商信息查询发生异常:", e);
        }

        return vo;
    }
    /*
     * 密码重置
     */
    public void updatePwd(OpenChannelsVO openChannelsVO) throws DAOException
    {
       
        String sqlCode="com.aspire.ponaadmin.web.channeladmin.dao.OpenChannelsDAO.updatePwd().update";
    
        try
        {
            DB.getInstance().executeBySQLCode(sqlCode, new Object[]{
                            openChannelsVO.getChannelsPwd(),
                            openChannelsVO.getChannelsId()
                            });
        }
        catch( DAOException e)
        {
            throw new DAOException("密码重置发生异常",e);
        }
    }
   
    
    
    public void offLine(OpenChannelsVO openChannelsVO) throws DAOException
    {
       
        String sqlCode="com.aspire.ponaadmin.web.channeladmin.dao.OpenChannelsDAO.offLine().update";
    
        try
        {
            DB.getInstance().executeBySQLCode(sqlCode, new Object[]{
                            openChannelsVO.getStatus(),
                            openChannelsVO.getChannelsId()
                            });
        }
        catch( DAOException e)
        {
            throw new DAOException("密码重置发生异常",e);
        }
    }
    
    
    /**
     * 根据No查询实体
     */
    public OpenChannelsVO queryChannelsVOChannels(String channelsNo){
        String sqlCode = "com.aspire.ponaadmin.web.channeladmin.dao.OpenChannelsDAO.queryOpenChannelsVO().select";
        String sql = null;
        OpenChannelsVO vo = new OpenChannelsVO();
        try
        {
            sql = DB.getInstance().getSQLByCode(sqlCode);
            ResultSet rs = DB.getInstance().query(sql, new Object[]{channelsNo});
            System.out.println(rs);
            if(!rs.next()){
                
                vo.setChannelsNo(channelsNo);
                return vo;
            }
        }
        catch (DAOException e)
        {
            logger.debug("查询渠道客户端失败", e);
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            logger.debug("查询渠道客户端失败", e);
            e.printStackTrace();
        }
        return null;
    }
    
    
    
    
    
    
    
    /*
     * 
     */
    private class OpenChannelsPageVO implements PageVOInterface{

        @Override
        public Object createObject()
        {
            return new OpenChannelsVO();
        }

        @Override
        public void CopyValFromResultSet(Object content, ResultSet rs)
                        throws SQLException
        {
            OpenChannelsVO vo = (OpenChannelsVO)content;
            vo.setChannelsId(rs.getString("channelsId"));
            vo.setChannelsName(rs.getString("channelsName"));
            vo.setChannelsNo(rs.getString("channelsNo"));
            vo.setParentChannelsId(rs.getString("parentChannelsId"));
            vo.setParentChannelsName(rs.getString("parentChannelsName"));
            vo.setStatus(rs.getString("status"));
            try
            {
                //vo.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("createDate").toString()));
            	vo.setCreateDate(TimeUtils.parseToDate(TimeUtils.format(rs.getTimestamp("createDate"))));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        
    }
    
}
