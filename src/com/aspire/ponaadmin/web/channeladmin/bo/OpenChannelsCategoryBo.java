package com.aspire.ponaadmin.web.channeladmin.bo;

import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.channeladmin.dao.OpenChannelsCategoryDAO;
import com.aspire.ponaadmin.web.channeladmin.vo.OpenChannelsCategoryVo;


public class OpenChannelsCategoryBo
{
    /**
     * 记录日志的实体
     */
    protected static final JLogger looger = LoggerFactory.getLogger(OpenChannelsCategoryBo.class);
    
    private static OpenChannelsCategoryBo instance = new OpenChannelsCategoryBo();
    
    private OpenChannelsCategoryBo(){
    }
    
    /**
     * 获取实例
     */
    public static OpenChannelsCategoryBo getInstance(){
        return instance;
    }
    
    public OpenChannelsCategoryVo queryOpenChannelsCategoryVo(String channelsId) throws BOException{
        try
        {
            return OpenChannelsCategoryDAO.getInstance().queryOpenChannelsCategoryVo(channelsId);
        }
        catch (DAOException e)
        {
            e.printStackTrace();
            throw new BOException("根货架配置查询失败");
        }
    }
    
    public void saveOpenChannelsCategoryVo(OpenChannelsCategoryVo vo) throws BOException{
        try
        {
            OpenChannelsCategoryDAO.getInstance().saveOpenChannelsCategoryVo(vo);
        }
        catch (DAOException e)
        {
            e.printStackTrace();
            throw new BOException("新增根货架配置时发生数据库异常！");
        }
    }
}
