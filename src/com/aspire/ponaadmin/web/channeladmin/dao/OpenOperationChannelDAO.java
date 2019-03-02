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
     * �洢��־��ʵ�����
     */
    protected static JLogger logger = LoggerFactory.getLogger(OpenOperationChannelDAO.class);
    
    /**
     * singletonģʽ��ʵ��
     */
    private static OpenOperationChannelDAO instance = new OpenOperationChannelDAO();
    
    /**
     * ���췽������singletonʵ������
     */
    private OpenOperationChannelDAO(){
    }
    
    /**
     * ��ȡʵ���ķ���
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
                logger.debug("����ʱ��ת����ʽʧ��!", e);
                e.printStackTrace();
            }
        }
        
    }
    
    /**
     * ��ѯ��������
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
            logger.debug("��ѯ������Ӫ�����б����",e);
            e.printStackTrace();
        }
    }
    
    /**
     * ����idɾ��ѡ�е�����
     */
    public void delOpenOperationChannelVoById(String channelId){
        String sqlCode = "com.aspire.ponaadmin.web.channeladmin.dao.OpenOperationChannelDAO.del";
        try
        {
            DB.getInstance().executeBySQLCode(sqlCode, new Object[]{channelId});
        }
        catch (DAOException e)
        {
            logger.debug("ɾ������ʧ��", e);
            e.printStackTrace();
        } 
    }
    
    /**
     * ����id��ѯʵ��
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
     * ����OpenOperationChannelVo
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
            throw new DAOException("���濪����Ӫ����ʧ��:", e);
        }
    }
}
