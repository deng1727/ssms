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
     * ��¼��־��ʵ��
     */
    protected static final JLogger looger = LoggerFactory.getLogger(OpenChannelsCategoryBo.class);
    
    private static OpenChannelsCategoryBo instance = new OpenChannelsCategoryBo();
    
    private OpenChannelsCategoryBo(){
    }
    
    /**
     * ��ȡʵ��
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
            throw new BOException("���������ò�ѯʧ��");
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
            throw new BOException("��������������ʱ�������ݿ��쳣��");
        }
    }
}
