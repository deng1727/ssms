/*
 * �ļ�����MusicDescDAO.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
 */

package com.aspire.ponaadmin.web.datasync.implement.music;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2010
 * </p>
 * 
 * @author x_wangml
 * @version
 */
public class MusicDescDAO
{

    /**
     * ��־����
     */
    private static JLogger logger = LoggerFactory.getLogger(MusicDescDAO.class);

    private static MusicDescDAO dao = new MusicDescDAO();

    public static MusicDescDAO getInstance()
    {
        return dao;
    }

    private MusicDescDAO()
    {
    }

    /**
     * ��ȡ��ǰϵͳ���л�������id�б�
     * 
     * @return
     */
    public List getAllBaseMusicId() throws DAOException
    {
        List musicList = new ArrayList();

        // select t.musicid from t_mb_music t
        String sqlCode = "datasync.implement.music.MusicDescDAO.getAllBaseMusicId";

        ResultSet rs = DB.getInstance().queryBySQLCode(sqlCode, null);

        try
        {
            while (rs.next())
            {
                musicList.add(rs.getString("musicid"));
            }
        }
        catch (SQLException e)
        {
            throw new DAOException("��ȡ����ӳ���ϵ����");
        }
        finally
        {
            DB.close(rs);
        }
        return musicList;
    }

    /**
     * ��ȡ��ǰϵͳ����contnetid�б�
     * 
     * @return
     */
    public List getAllContentId() throws DAOException
    {
        List contentIdList = new ArrayList();

        // select t.contentid from t_r_gcontent t
        String sqlCode = "datasync.implement.music.MusicDescDAO.getAllContentId";

        ResultSet rs = DB.getInstance().queryBySQLCode(sqlCode, null);

        try
        {
            while (rs.next())
            {
                contentIdList.add(rs.getString("contentid"));
            }
        }
        catch (SQLException e)
        {
            throw new DAOException("��ȡ����ӳ���ϵ����");
        }
        finally
        {
            DB.close(rs);
        }
        return contentIdList;
    }

    /**
     * ��������ר����Ϣ
     * 
     * @param vo ����ר��vo��
     * @return �����������
     */
    public int insertMusicDesc(MusicDescVO vo) throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("insertMusicDesc(),musicid=" + vo.getMusicId()
                         + ",contentid=" + vo.getContentId());
        }

        // �鿴�����Ƿ���ڴ�����id��������ڡ��޸�
        if (hasMusicId(vo.getMusicId()))
        {
            Object paras[] = { vo.getSongName(), vo.getSinger(),
                            vo.getSpecialName(), vo.getSpecialDesc(),
                            vo.getImageName(), vo.getContentId(),
                            vo.getContentName(), vo.getMusicId() };

            // update t_mb_music_desc t set
            // songname=?,singer=?,specialname=?,specialdesc=?,imagename=?,contentid=?,contentname=?,editdate=sysdate
            // where musicid = ?
            String sqlCode = "datasync.implement.music.MusicDescDAO.updateMusicDesc";
            DB.getInstance().executeBySQLCode(sqlCode, paras);
        }
        // ��������ڡ�����
        else
        {
            Object paras[] = { vo.getMusicId(), vo.getSongName(),
                            vo.getSinger(), vo.getSpecialName(),
                            vo.getSpecialDesc(), vo.getImageName(),
                            vo.getContentId(), vo.getContentName() };

            // insert into t_mb_music_desc
            // (musicid,songname,singer,specialname,specialdesc,imagename,contentid,contentname)
            // values(?,?,?,?,?,?,?,?)
            String sqlCode = "datasync.implement.music.MusicDescDAO.insertMusicDesc";
            DB.getInstance().executeBySQLCode(sqlCode, paras);
        }

        return DataSyncConstants.SUCCESS_ADD;
    }

    /**
     * �鿴�����Ƿ��Ѵ���Щmusicid��������
     * 
     * @param musicId
     * @return
     * @throws DAOException
     */
    public boolean hasMusicId(String musicId) throws DAOException
    {
        // select 1 from t_mb_music_desc t where t.musicid = ?
        String sqlCode = "datasync.implement.music.MusicDescDAO.hasMusicId";

        ResultSet rs = DB.getInstance()
                         .queryBySQLCode(sqlCode, new Object[] { musicId });

        try
        {
            if (rs.next())
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (SQLException e)
        {
            throw new DAOException("��ȡ����ӳ���ϵ����");
        }
        finally
        {
            DB.close(rs);
        }
    }
}
