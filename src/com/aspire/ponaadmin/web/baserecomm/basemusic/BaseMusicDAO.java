/*
 * 
 */

package com.aspire.ponaadmin.web.baserecomm.basemusic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class BaseMusicDAO
{

    /**
     * ��¼��־��ʵ������
     */
    protected static JLogger logger = LoggerFactory.getLogger(BaseMusicDAO.class);

    /**
     * singletonģʽ��ʵ��
     */
    private static BaseMusicDAO instance = new BaseMusicDAO();

    /**
     * ���췽������singletonģʽ����
     */
    private BaseMusicDAO()
    {
    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static BaseMusicDAO getInstance()
    {
        return instance;
    }

    /**
     * Ӧ�����ҳ��ȡVO��ʵ����
     */
    private class BaseMusicPageVO implements PageVOInterface
    {

        public void CopyValFromResultSet(Object content, ResultSet rs)
                        throws SQLException
        {
            BaseMusicVO vo = ( BaseMusicVO ) content;

            SimpleDateFormat fo = new SimpleDateFormat("yyyy-MM-dd");
            vo.setMusicId(rs.getString("musicId"));
            vo.setSongName(rs.getString("songName"));
            vo.setSinger(rs.getString("singer"));
            vo.setValidityData("��Ч����");
            if (rs.getInt("delflag") != 0)
            {
                vo.setValidityData("ʧЧ����");
            }

            Date date = new Date();
            Date temp;

            try
            {
                temp = fo.parse(rs.getString("validity"));
            }
            catch (ParseException e)
            {
                logger.error(e);

                throw new SQLException("ת����Ч��ʱ��ʱ����ת���쳣��");
            }

            if (date.after(temp))
            {
                vo.setValidityData("ʧЧ����");
            }
        }

        public Object createObject()
        {
            return new BaseMusicVO();
        }
    }

    /**
     * ���ڲ�ѯ���������������
     * 
     * @param page
     * @param songName
     * @param singer
     * @throws DAOException
     */
    public void queryBaseMusicList(PageResult page, String songName,
                                   String singer) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("queryBaseMusicList(" + songName + "," + singer
                         + ") is starting ...");
        }

        // select musicid, songname, singer from t_mb_music t where 1=1
        String sqlCode = "basegame.BaseMusicDAO.queryBaseMusicList().SELECT";
        String sql = null;

        try
        {
            sql = SQLCode.getInstance().getSQLStatement(sqlCode);

            StringBuffer sqlBuffer = new StringBuffer(sql) ;
            List paras = new ArrayList();
            //����������sql�Ͳ���
            
            if (!"".equals(songName))
            {
                //sql += " and t.songName like ('%" + songName + "%')";
            	sqlBuffer.append(" and t.songName like ? ");
            	paras.add("%"+SQLUtil.escape(songName)+"%");
            }
            if (!"".equals(singer))
            {
                //sql += " and t.singer like ('%" + singer + "%')";
            	sqlBuffer.append(" and t.singer like ? ");
            	paras.add("%"+SQLUtil.escape(singer)+"%");
            }

            //page.excute(sql, null, new BaseMusicPageVO());
            page.excute(sqlBuffer.toString(), paras.toArray(), new BaseMusicPageVO());
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
    public void queryBaseMusicTempList(PageResult page) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("queryBaseMusicTempList( ) is starting ...");
        }

        // select m.musicid, m.songname, m.singer, m.delflag, m.validity from
        // t_base_temp t, t_mb_music m where m.musicid = t.baseid and
        // t.basetype='baseMusic'
        String sqlCode = "basegame.BaseMusicDAO.queryBaseMusicTempList().SELECT";
        String sql = null;

        try
        {
            sql = SQLCode.getInstance().getSQLStatement(sqlCode);

            page.excute(sql, null, new BaseMusicPageVO());
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

        // select m.musicid,m.songname,m.singer from t_base_temp t, t_mb_music m
        // where m.musicid = t.baseid and t.basetype='baseMusic' and m.delflag =
        // 0 and to_date(m.validity,'yyyy-MM-dd') > sysdate
        String sqlCode = "basegame.BaseMusicDAO.queryToFileData().SELECT";
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

        temp.append("MS")
            .append(splitString())
            .append(rs.getString("musicId"))
            .append(splitString())
            .append(rs.getString("songName"));

        return temp.toString();
    }

    /**
     * ���طָ���ASCII��31(0x1F)
     * 
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
