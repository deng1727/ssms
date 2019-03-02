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
     * �洢��־��ʵ�����
     */
    protected static JLogger logger = LoggerFactory.getLogger(OpenChannelsCategoryDAO.class);
    
    /**
     * singletonģʽ��ʵ��
     */
    private static OpenChannelsCategoryDAO instance = new OpenChannelsCategoryDAO();
    
    /**
     * ���췽������singletonʵ������
     */
    private OpenChannelsCategoryDAO(){
    }
    
    /**
     * ��ȡʵ���ķ���
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
            logger.debug("��ѯ��������Ϣʧ��", e);
            e.printStackTrace();
        }
        return vo;
    }
    
    /**
     * ����id��ѯʵ��
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
            logger.debug("��ѯ��������Ϣʧ��", e);
            e.printStackTrace();
            throw new DAOException();
        }
        catch (SQLException e)
        {
            logger.debug("��ѯ��������Ϣʧ��", e);
            e.printStackTrace();
            throw new DAOException();
        }
        return vo;
    }
    
    /**
     * ��ѯȫ�������̶�Ӧ������
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
            logger.error("��ѯȫ�������̶�Ӧ��������Ϣʧ��", e);
        }
        catch (SQLException e)
        {
            logger.error("��ѯȫ�������̶�Ӧ��������Ϣʧ��", e);
        }
        return list;
    }
    
    /**
     * ����OpenChannelsCategoryVo
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
            throw new DAOException("�������������ʧ��:", e);
        }
    }
}
