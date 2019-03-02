package com.aspire.ponaadmin.web.channelUser.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.util.SQLCode;
import com.aspire.ponaadmin.web.channelUser.vo.ChannelVO;
/**
 * 
 * @author shiyangwang
 *
 */
public class ChannelDAO {

	/**
     * ��¼��־��ʵ������
     */
    protected static JLogger logger = LoggerFactory.getLogger(ChannelDAO.class);
    
    private static ChannelDAO instance = new ChannelDAO();
    
    private ChannelDAO(){
    	
    }
    public static ChannelDAO getInstance(){
    	return instance;
    }
    /**
     * �����������˺Ų���������
     * @param channelNO �������˺�
     * @return
     * @throws DAOException
     */
    public final static ChannelVO getChannelByNO(String channelNO) throws DAOException{
    	if (logger.isDebugEnabled())
        {
            logger.debug("getChannelByNO(" + channelNO + ")") ;
        }
    	String sqlCode = "channelUser.dao.ChannelDAO.getChannelByNO().QUERY" ;
        Object[] paras =
            {channelNO} ;
        ResultSet rs = null ;
        ChannelVO channel = null ;
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, paras) ;
            if (rs != null && rs.next())
            {
            	channel = new ChannelVO() ;
                getChannelVOFromRS(channel, rs) ;
            }
        }
        catch (SQLException e)
        {
            throw new DAOException("getChannelByNO error.", e) ;

        }
        finally
        {
            if (rs != null)
            {
                try
                {
                    rs.close() ;
                }
                catch (Exception e)
                {
                    logger.error(e) ;
                }
            }
        }
        return channel ;
    }
    /**
     * ����������id��������id������������
     * @param channelsId ������Id
     * @return
     * @throws DAOException
     */
    public ChannelVO getChannelByChannelsId(String channelsId) throws DAOException{
    	if (logger.isDebugEnabled())
        {
            logger.debug("getChannelByChannelsId(" + channelsId + ")") ;
        }
    	String sqlCode = "channelUser.dao.ChannelDAO.getChannelByChannelsId().QUERY" ;
        Object[] paras =
            {channelsId} ;
        ResultSet rs = null ;
        ChannelVO channel = null ;
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, paras) ;
            if (rs != null && rs.next())
            {
            	channel = new ChannelVO() ;
                getChannelVOFromRS(channel, rs) ;
            }
        }
        catch (SQLException e)
        {
            throw new DAOException("getChannelByNO error.", e) ;

        }
        finally
        {
            if (rs != null)
            {
                try
                {
                    rs.close() ;
                }
                catch (Exception e)
                {
                    logger.error(e) ;
                }
            }
        }
        return channel ;
    }
    /**
     * �޸��������˺���Ϣ
     */
    public static int updateChannel(ChannelVO channelVO) throws DAOException{
    	if (logger.isDebugEnabled())
        {
            logger.debug("UpdateChannel(" + channelVO + ")") ;
        }
    	 String sqlCode = "channelUser.dao.ChannelDAO.updateChannel().UPDATE" ;
         String sql = "" ;
         try
         {
             sql = SQLCode.getInstance().getSQLStatement(sqlCode) ;
         }
         catch (Exception e)
         {
             throw new DAOException("get sql error", e) ;
         }
         //���ݸ����ֶ��Ƿ�Ϊnull������set��sql���
         StringBuffer setSQL = new StringBuffer() ;
         if (channelVO.getChannelsName() != null)
         {
        	 //�˺�����
             appendSQLSep(setSQL) ;
             setSQL.append("CHANNELSNAME='" + channelVO.getChannelsName() + "'") ;
         }
         if (channelVO.getChannelsNO() != null)
         {
        	 //�˺�
             appendSQLSep(setSQL) ;
             setSQL.append("CHANNELSNO='" + channelVO.getChannelsNO() + "'") ;
         }
         if (channelVO.getChannelsPwd() != null)
         {
        	 //����
             appendSQLSep(setSQL) ;
             setSQL.append("CHANNELSPWD='" + channelVO.getChannelsPwd() + "'") ;
         }
         if (channelVO.getChannelsDesc() != null)
         {
        	 //����
             appendSQLSep(setSQL) ;
             setSQL.append("CHANNELSDESC='" + channelVO.getChannelsDesc() + "'") ;
         }
         if (channelVO.getStatus() != null)
         {
        	 //״̬   0-������1-����
             appendSQLSep(setSQL) ;
             setSQL.append("STATUS='" + channelVO.getStatus() + "'") ;
         }
         if (channelVO.getParentChannelsId() != null)
         {
        	 //������������ID
             appendSQLSep(setSQL) ;
             setSQL.append("PARENTCHANNELSID='" + channelVO.getParentChannelsId() + "'") ;
         }
         if (channelVO.getParentChannelsName() != null)
         {
        	 //����������������
             appendSQLSep(setSQL) ;
             setSQL.append("PARENTCHANNELSNAME='" + channelVO.getParentChannelsName() + "'") ;
         }
         appendSQLSep(setSQL) ;
         //����ʱ��
         setSQL.append("MODIFYDATE= sysdate") ;
       //�滻sql�������
         sql = replace(sql, "{1}", setSQL.toString()) ;
         Object[] paras =
             {channelVO.getChannelsNO()} ;
         return DB.getInstance().execute(sql, paras) ;
    }
    /**
     * �����ݿ��¼����ȡ���ݲ���װ��һ��ChannelVO����
     * @param Channel ChannelVO ChannelVO����
     * @param rs ResultSet ���ݿ��¼��
     */
    public static void getChannelVOFromRS (ChannelVO channel, ResultSet rs) throws SQLException
    {
    	channel.setChannelsId(rs.getString("channelsid"));
    	channel.setChannelsName(rs.getString("channelsname"));
    	channel.setParentChannelsId(rs.getString("parentchannelsid"));
    	channel.setParentChannelsName(rs.getString("parentChannelsName"));
    	channel.setChannelsNO(rs.getString("channelsNO"));
    	channel.setChannelsPwd(rs.getString("channelsPwd"));
    	channel.setChannelsDesc(rs.getString("channelsDesc"));
    	channel.setStatus(rs.getString("status"));
    	channel.setCreateDate(rs.getTimestamp("createDate"));
    	channel.setModifyDate(rs.getTimestamp("modifyDate"));
    	channel.setMoType(rs.getString("motype"));
    }
    /**
     * Ϊupdate��乹���ʱ����Ӷ��ŷָ���
     * @param sb ����sql����StringBuffer
     */
    private static void appendSQLSep (StringBuffer sb)
    {
        if (sb.length() != 0)
        {
            sb.append(",") ;
        }
    }
    /**
     * ����ʵ����string �е��滻
     * @param line String ��Ҫ��������ַ���
     * @param oldString String ���滻������
     * @param newString String �滻���������
     * @return String �滻���
     */
    private static final String replace(String line, String oldString,
                                       String newString)
    {
      if (line == null)
      {
        return null;
      }
      int i = 0;
      if ( (i = line.indexOf(oldString, i)) >= 0)
      {
        char[] line2 = line.toCharArray();
        char[] newString2 = newString.toCharArray();
        int oLength = oldString.length();
        StringBuffer buf = new StringBuffer(line2.length);
        buf.append(line2, 0, i).append(newString2);
        i += oLength;
        int j = i;
        while ( (i = line.indexOf(oldString, i)) > 0)
        {
          buf.append(line2, j, i - j).append(newString2);
          i += oLength;
          j = i;
        }
        buf.append(line2, j, line2.length - j);
        return buf.toString();
      }
      return line;
  }
}
