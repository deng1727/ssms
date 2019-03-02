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
     * ��¼��־��ʵ������
     */
    protected static JLogger logger = LoggerFactory.getLogger(BaseVideoDAO.class);

    /**
     * singletonģʽ��ʵ��
     */
    private static BaseVideoDAO instance = new BaseVideoDAO();

    /**
     * ���췽������singletonģʽ����
     */
    private BaseVideoDAO()
    {
    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static BaseVideoDAO getInstance()
    {
        return instance;
    }

    /**
     * ���ڲ�ѯ������Ƶ�������
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
     * Ӧ�����ҳ��ȡVO��ʵ����
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
     * ���ڲ�ѯ������Ƶ�������
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
            //����������sql�Ͳ���

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
            throw new DAOException("��sql.properties�л�ȡsql���ʱ�������󣬶�ӦsqlCode�����ڡ�",
                                   e);
        }
    }

    /**
     * ���ڲ�ѯ���������������
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
            throw new DAOException("��sql.properties�л�ȡsql���ʱ�������󣬶�ӦsqlCode�����ڡ�",
                                   e);
        }
    }

    /**
     * ���������ļ�Ӧ������
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

            // �������next˵����������
            while (rs.next())
            {
                list.add(formatDataLine(rs));
            }
        }
        catch (SQLException e)
        {
            throw new DAOException("��ȡ������ʱ��ʱ�������ݿ����");
        }
        finally
        {
            DB.close(rs);
        }

        return list;
    }

    /**
     * ��������ÿ����ʾ����
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
     * ���طָ���ASCII��31(0x1F)
     * @return
     */
    private char splitString()
    {
        String tmp = "0x1F";

        // 0x��ͷ�ģ���ʾ��16���Ƶģ���Ҫת��
        String s = tmp.substring(2, tmp.length());
        int i = Integer.parseInt(s, 16);
        
        return ( char ) i;
    }
}
