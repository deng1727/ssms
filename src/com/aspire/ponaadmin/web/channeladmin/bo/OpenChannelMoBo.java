package com.aspire.ponaadmin.web.channeladmin.bo;

import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.channeladmin.dao.OpenChannelMoDAO;
import com.aspire.ponaadmin.web.channeladmin.vo.OpenChannelMoVo;
import com.aspire.ponaadmin.web.channeladmin.vo.OpenChannelsVO;


public class OpenChannelMoBo
{
    /**
     * 记录日志的实体
     */
    protected static final JLogger logger = LoggerFactory.getLogger(OpenChannelMoBo.class);
    
    private static OpenChannelMoBo instance = new OpenChannelMoBo();
    
    private OpenChannelMoBo(){
    }
    
    /**
     * 获取实例
     */
    public static OpenChannelMoBo getInstance(){
        return instance;
    }
    
    public OpenChannelMoVo queryOpenChannelMoVo(String channelId){
        return OpenChannelMoDAO.getInstance().queryOpenChannelMoVo(channelId);
    }
    
    public List<OpenChannelMoVo> queryOpenChannelMoVos(){
        return OpenChannelMoDAO.getInstance().queryOpenChannelMoVos();
    }
    
    public void delOpenChannelMoVoById(String channelId){
        OpenChannelMoDAO.getInstance().delOpenChannelMoVoById(channelId);
    }
    
    public void listOpenChannelMoVo(PageResult page,OpenChannelMoVo vo) throws DAOException{
        OpenChannelMoDAO.getInstance().listOpenChannelMoVo(page,vo);
    }
    
    public void saveOpenChannelMoVo(OpenChannelMoVo vo) throws BOException{
        try
        {
            OpenChannelMoDAO.getInstance().saveOpenChannelMoVo(vo);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("新增客户端渠道时发生数据库异常！");
        }
    }
    
    public void queryList(PageResult page,OpenChannelsVO vo) throws DAOException{
       OpenChannelMoDAO.getInstance().queryList(page,vo);
    }
}
