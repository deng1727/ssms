package com.aspire.ponaadmin.web.channelUser.bo;

import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.channelUser.dao.OpenChannelsCategoryDAO;
import com.aspire.ponaadmin.web.channelUser.vo.ChannelCategoryVO;

public class OpenChannelsCategoryBO {
	/**
     * 记录日志的实体
     */
    protected static final JLogger looger = LoggerFactory.getLogger(OpenChannelsCategoryBO.class);
    
    private static OpenChannelsCategoryBO instance = new OpenChannelsCategoryBO();
    
    private OpenChannelsCategoryBO(){
    }
    
    /**
     * 获取实例
     */
    public static OpenChannelsCategoryBO getInstance(){
        return instance;
    }
    public List<ChannelCategoryVO> queryOpenChannelsCategoryList(String channelsId) throws BOException{
        try
        {
            return OpenChannelsCategoryDAO.getInstance().queryOpenChannelsCategoryList(channelsId);
        }
        catch (DAOException e)
        {
            e.printStackTrace();
            throw new BOException("根货架配置查询失败");
        }
    }
}
