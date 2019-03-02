package com.aspire.ponaadmin.web.channeladmin.bo;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.channeladmin.dao.OpenOperationChannelDAO;
import com.aspire.ponaadmin.web.channeladmin.vo.OpenOperationChannelVo;


public class OpenOperationChannelBo
{
    /**
     * 记录日志的实体
     */
    protected static final JLogger logger = LoggerFactory.getLogger(OpenOperationChannelBo.class);
    
    private static OpenOperationChannelBo instance = new OpenOperationChannelBo();
    
    private OpenOperationChannelBo(){
    }
    
    /**
     * 获取实例
     */
    public static OpenOperationChannelBo getInstance(){
        return instance;
    }
    
    public void listOpenOperationChannels(PageResult page,OpenOperationChannelVo vo) throws DAOException{
        OpenOperationChannelDAO.getInstance().listOpenOperationChannels(page,vo);
    }
    
    public void delOpenOperationChannelVoById(String channelId){
        OpenOperationChannelDAO.getInstance().delOpenOperationChannelVoById(channelId);
    }
    
    public OpenOperationChannelVo queryOpenOperationChannelVo(String channelId){
        return OpenOperationChannelDAO.getInstance().queryOpenOperationChannelVo(channelId);
    }
    
    public void saveOpenOperationChannelVo(OpenOperationChannelVo vo) throws BOException{
        try
        {
            OpenOperationChannelDAO.getInstance().saveOpenOperationChannelVo(vo);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("新增开放运营渠道时发生数据库异常！");
        }
    }
}
