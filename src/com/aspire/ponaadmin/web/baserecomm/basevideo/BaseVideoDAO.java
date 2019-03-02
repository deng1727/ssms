/*
 * 
 */

package com.aspire.ponaadmin.web.baserecomm.basevideo;

import java.sql.ResultSet;
import java.sql.SQLException;
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

/**
 * @author x_wangml
 * 
 */
public class BaseVideoDAO
{

    /**
     * 记录日志的实例对象
     */
    protected static JLogger logger = LoggerFactory.getLogger(BaseVideoDAO.class);

    /**
     * singleton模式的实例
     */
    private static BaseVideoDAO instance = new BaseVideoDAO();

    /**
     * 构造方法，由singleton模式调用
     */
    private BaseVideoDAO()
    {
    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static BaseVideoDAO getInstance()
    {
        return instance;
    }

    /**
     * 用于查询基地视频数据情况
     * 
     * @param videoName
     * @param videoUrl
     * @throws DAOException
     */
    public void addBaseVideo(String videoName, String videoUrl)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("queryBaseVideoList(" + videoName
                         + ", " + videoUrl + ") is starting ...");
        }

        // insert into t_base_video (id, videoname, videourl) values (SEQ_BASEVIDEO_ID.NEXTVAL, ?, ?)
        String sqlCode = "basegame.BaseVideoDAO.addBaseVideo().INSERT";

        Object paras[] = { videoName, videoUrl };
        
        DB.getInstance().executeBySQLCode(sqlCode, paras);
    }
    
    /**
     * 应用类分页读取VO的实现类
     */
    private class BaseVideoPageVO implements PageVOInterface
    {

        public void CopyValFromResultSet(Object content, ResultSet rs)
                        throws SQLException
        {
            BaseVideoVO vo = ( BaseVideoVO ) content;
            vo.setContentId(rs.getString("id"));
            vo.setVideoName(rs.getString("videoName"));
            vo.setVideoURL(rs.getString("videoUrl"));
        }

        public Object createObject()
        {

            return new BaseVideoVO();
        }
    }

    /**
     * 用于查询基地视频数据情况
     * 
     * @param page
     * @param videoName
     * @throws DAOException
     */
    public void queryBaseVideoList(PageResult page, String videoName)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("queryBaseVideoList(" + videoName
                         + ") is starting ...");
        }

        // select * from t_base_video t where 1=1
        String sqlCode = "basegame.BaseVideoDAO.queryBaseVideoList().SELECT";
        String sql = null;

        try
        {
            sql = SQLCode.getInstance().getSQLStatement(sqlCode);
            
            StringBuffer sqlBuffer = new StringBuffer(sql) ;
            List paras = new ArrayList();
            //构造搜索的sql和参数

            if (!"".equals(videoName))
            {
                //sql += " and t.videoName like ('%" + videoName + "%')";
            	sqlBuffer.append(" and t.videoName like ? ");
            	paras.add("%"+SQLUtil.escape(videoName)+"%");
            }

            //page.excute(sql, null, new BaseVideoPageVO());
            page.excute(sqlBuffer.toString(), paras.toArray(), new BaseVideoPageVO());
        }
        catch (DataAccessException e)
        {
            throw new DAOException("从sql.properties中获取sql语句时发生错误，对应sqlCode不存在。",
                                   e);
        }
    }

    /**
     * 用于查询基地音乐数据情况
     * 
     * @param page
     * @throws DAOException
     */
    public void queryBaseVideoTempList(PageResult page) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("queryBaseVideoTempList( ) is starting ...");
        }

        // select * from t_base_temp t, t_base_video v where v.id = t.baseid and
        // t.basetype='baseVideo'
        String sqlCode = "basegame.BaseVideoDAO.queryBaseVideoTempList().SELECT";
        String sql = null;

        try
        {
            sql = SQLCode.getInstance().getSQLStatement(sqlCode);

            page.excute(sql, null, new BaseVideoPageVO());
        }
        catch (DataAccessException e)
        {
            throw new DAOException("从sql.properties中获取sql语句时发生错误，对应sqlCode不存在。",
                                   e);
        }
    }

    /**
     * 用于生成文件应用数据
     * 
     * @throws DAOException
     */
    public List queryToFileData() throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("queryToFileData( ) is starting ...");
        }

        // select * from t_base_temp t, t_base_video v where v.id = t.baseid and
        // t.basetype='baseVideo'
        String sqlCode = "basegame.BaseVideoDAO.queryBaseVideoTempList().SELECT";
        List list = new ArrayList();
        ResultSet rs = null;

        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, new Object[] {});

            // 如果存在next说明存在数据
            while (rs.next())
            {
                list.add(formatDataLine(rs));
            }
        }
        catch (SQLException e)
        {
            throw new DAOException("读取数据临时表时发生数据库错误");
        }
        finally
        {
            DB.close(rs);
        }

        return list;
    }

    /**
     * 用于整理每条显示数据
     * 
     * @param rs
     * @return
     * @throws SQLException
     */
    private String formatDataLine(ResultSet rs) throws SQLException
    {
        StringBuffer temp = new StringBuffer();

        temp.append("VD")
            .append(splitString())
            .append(rs.getString("id"))
            .append(splitString())
            .append(rs.getString("videoUrl"));

        return temp.toString();
    }

    /**
     * 返回分隔符ASCII码31(0x1F)
     * @return
     */
    private char splitString()
    {
        String tmp = "0x1F";

        // 0x开头的，表示是16进制的，需要转换
        String s = tmp.substring(2, tmp.length());
        int i = Integer.parseInt(s, 16);
        
        return ( char ) i;
    }
}
