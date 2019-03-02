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
import com.aspire.ponaadmin.web.channeladmin.vo.OpenChannelMoVo;
import com.aspire.ponaadmin.web.channeladmin.vo.OpenChannelsVO;


public class OpenChannelMoDAO
{
    /**
     * �洢��־��ʵ�����
     */
    protected static JLogger logger = LoggerFactory.getLogger(OpenChannelMoDAO.class);
    
    /**
     * singletonģʽ��ʵ��
     */
    private static OpenChannelMoDAO instance = new OpenChannelMoDAO();
    
    /**
     * ���췽������singletonʵ������
     */
    private OpenChannelMoDAO(){
    }
    
    /**
     * ��ȡʵ���ķ���
     */
    public static OpenChannelMoDAO getInstance(){
        return instance;
    }
    
    /**
     * ����id��ѯʵ��
     */
    public OpenChannelMoVo queryOpenChannelMoVo(String channelId){
        String sqlCode = "com.aspire.ponaadmin.web.channeladmin.dao.OpenChannelMoDAO.select";
        String sql = null;
        OpenChannelMoVo vo = new OpenChannelMoVo();
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
            logger.debug("��ѯ�����ͻ���ʧ��", e);
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            logger.debug("��ѯ�����ͻ���ʧ��", e);
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * ��ѯ��������
     */
    public List<OpenChannelMoVo> queryOpenChannelMoVos(){
        return null;
    }
    
    /**
     * ����idɾ��ѡ�е�����
     */
    public void delOpenChannelMoVoById(String channelId){
        String sqlCode = "com.aspire.ponaadmin.web.channeladmin.dao.OpenChannelMoDAO.del";
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
    
    private class OpenChannelMoPageVo implements PageVOInterface{

        @Override
        public Object createObject()
        {
            return new OpenChannelMoVo();
        }

        @Override
        public void CopyValFromResultSet(Object content, ResultSet rs)
                        throws SQLException
        {
            OpenChannelMoVo vo = (OpenChannelMoVo)content;
            vo.setChannelId(rs.getString("channelId"));
            vo.setChannelName(rs.getString("channelName"));
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
     * ��ѯOpenChannelMoVo�ͻ��������б�
     * @throws DAOException 
     */
    public void listOpenChannelMoVo(PageResult page,OpenChannelMoVo vo) throws DAOException{
        String sqlCode = "com.aspire.ponaadmin.web.channeladmin.dao.OpenChannelMoDAO.list";
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
            if(!"".equals(vo.getChannelName())){
                sb.append(" and t.channelName like ?");
                paras.add("%"+SQLUtil.escape(vo.getChannelName())+"%");
            }
            sb.append(" order by t.channelId asc");
            page.excute(sb.toString(), paras.toArray(), new OpenChannelMoPageVo());
        }
        catch (DataAccessException e)
        {
            logger.debug("��ѯ�ͻ��������б����",e);
            e.printStackTrace();
        }
    }
    
    /**
     * ����OpenChannelMoVo
     * @throws DAOException 
     */
    public void saveOpenChannelMoVo(OpenChannelMoVo vo) throws DAOException{
        if (logger.isDebugEnabled())
        {
            logger.debug("saveOpenChannelMo() is starting ...");
        }
        String sqlCode = "com.aspire.ponaadmin.web.channeladmin.dao.OpenChannelMoDAO.insert";

        try
        {
            DB.getInstance()
            .executeBySQLCode(sqlCode,
                              new Object[] { vo.getChannelId(),
                    vo.getChannelName(),
                    vo.getChannelsId()});
        }
        catch (DAOException e)
        {
            throw new DAOException("����ͻ�������ʧ��:", e);
        }
    }
    
    private class OpenChannelsPageVo implements PageVOInterface{

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
            
        }
        
    }
    
    /**
     * ��ѯ���������̵���Ϣ
     * @throws DAOException 
     */
    public void queryList(PageResult page,OpenChannelsVO vo) throws DAOException{
        String sqlCode = "com.aspire.ponaadmin.web.channeladmin.dao.OpenChannelMoDAO.queryList";
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
            sb.append(" order by t.channelsId asc");
            page.excute(sb.toString(), paras.toArray(), new OpenChannelsPageVo());
        }
        catch (DataAccessException e)
        {
            logger.debug("��ѯ��������Ϣ����",e);
            e.printStackTrace();
        }
    }
    
}
