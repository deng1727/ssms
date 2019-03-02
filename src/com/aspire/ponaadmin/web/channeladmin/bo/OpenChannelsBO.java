package com.aspire.ponaadmin.web.channeladmin.bo;

import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.channeladmin.dao.OpenChannelMoDAO;
import com.aspire.ponaadmin.web.channeladmin.dao.OpenChannelsDAO;
import com.aspire.ponaadmin.web.channeladmin.vo.OpenChannelMoVo;
import com.aspire.ponaadmin.web.channeladmin.vo.OpenChannelsVO;
import com.aspire.ponaadmin.web.comic.dao.CategoryDAO;
import com.aspire.ponaadmin.web.comic.vo.CategoryVO;


public class OpenChannelsBO
{
    /*
     * 日志引用
     */
    protected static JLogger logger = LoggerFactory.getLogger(OpenChannelsBO.class);
    
    private static OpenChannelsBO instance = new OpenChannelsBO();
    
    /*
     * 获取实例
     */
    private OpenChannelsBO()
    {
    }
    
    public static OpenChannelsBO getInstance()
    {
        return instance;
    }
    
    /*
     * 返回渠道商列表
     */
    public void queryOpenChannelsList(PageResult page,OpenChannelsVO vo) throws DAOException{
        OpenChannelsDAO.getInstance().queryOpenChannelsList(page, vo);
     }

    /**
     * 用于新增渠道信息
     * 
     * @param OpenChannelsVO
     * @throws BOException
     */
    
    public void save(OpenChannelsVO openChannelsVO) throws BOException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("saveOpenChannels() is starting ...");
        }
        try
        {
            OpenChannelsDAO.getInstance().save(openChannelsVO);
        }
        catch(DAOException e)
        {
            logger.error(e);
            throw new BOException("新增渠道信息时发生数据库异常！");
        }
    }
    
   
    
    /**
     * 用于变更渠道信息
     * 
     * @throws BOException
     */
    public void updateChannels(OpenChannelsVO openChannelsVO) throws BOException {
        // TODO Auto-generated method stub
        try
        {
            OpenChannelsDAO.getInstance().updateChannels(openChannelsVO);
                                      
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("变更渠道商时发生数据库异常！");
        }
    }
    
   
    public OpenChannelsVO queryChannelsVO(String channelsId) throws BOException
    {
        
        try
        {
            return OpenChannelsDAO.getInstance().queryChannelsVO(channelsId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("返回渠道商信息时发生数据库异常！");
        }
    }
   
    public OpenChannelsVO list(String channelsId) throws BOException
    {
        
        try
        {
            return OpenChannelsDAO.getInstance().queryChannelsVO(channelsId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("返回账号详情时发生数据库异常！");
        }
    }
    
    /*
     * 用于密码重置
     */
    public void updatePwd(OpenChannelsVO openChannelsVO) throws BOException
    {
       
        try
        {
            OpenChannelsDAO.getInstance().updatePwd(openChannelsVO);
        }
        catch(DAOException e)
        {
            logger.error(e);
            throw new BOException("密码重置发生异常",e);
        }
    }
    
    public void offLine(OpenChannelsVO openChannelsVO) throws BOException
    {
       
        try
        {
            OpenChannelsDAO.getInstance().offLine(openChannelsVO);
        }
        catch(DAOException e)
        {
            logger.error(e);
            throw new BOException("状态重置发生异常",e);
        }
    }
    
    public OpenChannelsVO queryChannelsVOChannels(String channelsNo) throws DAOException{
        return OpenChannelsDAO.getInstance().queryChannelsVOChannels(channelsNo);
    }
}
