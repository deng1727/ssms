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
     * 记录日志的实例对象
     */
    protected static JLogger logger = LoggerFactory.getLogger(ChannelDAO.class);
    
    private static ChannelDAO instance = new ChannelDAO();
    
    private ChannelDAO(){
    	
    }
    public static ChannelDAO getInstance(){
    	return instance;
    }
    /**
     * 根据渠道商账号查找渠道商
     * @param channelNO 渠道商账号
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
     * 根据渠道商id（合作商id）查找渠道商
     * @param channelsId 渠道商Id
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
     * 修改渠道商账号信息
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
         //根据各个字段是否为null来构造set的sql语句
         StringBuffer setSQL = new StringBuffer() ;
         if (channelVO.getChannelsName() != null)
         {
        	 //账号名称
             appendSQLSep(setSQL) ;
             setSQL.append("CHANNELSNAME='" + channelVO.getChannelsName() + "'") ;
         }
         if (channelVO.getChannelsNO() != null)
         {
        	 //账号
             appendSQLSep(setSQL) ;
             setSQL.append("CHANNELSNO='" + channelVO.getChannelsNO() + "'") ;
         }
         if (channelVO.getChannelsPwd() != null)
         {
        	 //密码
             appendSQLSep(setSQL) ;
             setSQL.append("CHANNELSPWD='" + channelVO.getChannelsPwd() + "'") ;
         }
         if (channelVO.getChannelsDesc() != null)
         {
        	 //描述
             appendSQLSep(setSQL) ;
             setSQL.append("CHANNELSDESC='" + channelVO.getChannelsDesc() + "'") ;
         }
         if (channelVO.getStatus() != null)
         {
        	 //状态   0-正常；1-下线
             appendSQLSep(setSQL) ;
             setSQL.append("STATUS='" + channelVO.getStatus() + "'") ;
         }
         if (channelVO.getParentChannelsId() != null)
         {
        	 //所属父渠道商ID
             appendSQLSep(setSQL) ;
             setSQL.append("PARENTCHANNELSID='" + channelVO.getParentChannelsId() + "'") ;
         }
         if (channelVO.getParentChannelsName() != null)
         {
        	 //所属父渠道商名称
             appendSQLSep(setSQL) ;
             setSQL.append("PARENTCHANNELSNAME='" + channelVO.getParentChannelsName() + "'") ;
         }
         appendSQLSep(setSQL) ;
         //更新时间
         setSQL.append("MODIFYDATE= sysdate") ;
       //替换sql里面变量
         sql = replace(sql, "{1}", setSQL.toString()) ;
         Object[] paras =
             {channelVO.getChannelsNO()} ;
         return DB.getInstance().execute(sql, paras) ;
    }
    /**
     * 从数据库记录集获取数据并封装到一个ChannelVO对象
     * @param Channel ChannelVO ChannelVO对象
     * @param rs ResultSet 数据库记录集
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
     * 为update语句构造的时候，添加逗号分隔。
     * @param sb 保存sql语句的StringBuffer
     */
    private static void appendSQLSep (StringBuffer sb)
    {
        if (sb.length() != 0)
        {
            sb.append(",") ;
        }
    }
    /**
     * 用来实现在string 中的替换
     * @param line String 需要被处理的字符串
     * @param oldString String 被替换的内容
     * @param newString String 替换后的新内容
     * @return String 替换结果
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
