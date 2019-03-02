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
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(OpenChannelsBO.class);
    
    private static OpenChannelsBO instance = new OpenChannelsBO();
    
    /*
     * ��ȡʵ��
     */
    private OpenChannelsBO()
    {
    }
    
    public static OpenChannelsBO getInstance()
    {
        return instance;
    }
    
    /*
     * �����������б�
     */
    public void queryOpenChannelsList(PageResult page,OpenChannelsVO vo) throws DAOException{
        OpenChannelsDAO.getInstance().queryOpenChannelsList(page, vo);
     }

    /**
     * ��������������Ϣ
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
            throw new BOException("����������Ϣʱ�������ݿ��쳣��");
        }
    }
    
   
    
    /**
     * ���ڱ��������Ϣ
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
            throw new BOException("���������ʱ�������ݿ��쳣��");
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
            throw new BOException("������������Ϣʱ�������ݿ��쳣��");
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
            throw new BOException("�����˺�����ʱ�������ݿ��쳣��");
        }
    }
    
    /*
     * ������������
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
            throw new BOException("�������÷����쳣",e);
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
            throw new BOException("״̬���÷����쳣",e);
        }
    }
    
    public OpenChannelsVO queryChannelsVOChannels(String channelsNo) throws DAOException{
        return OpenChannelsDAO.getInstance().queryChannelsVOChannels(channelsNo);
    }
}
