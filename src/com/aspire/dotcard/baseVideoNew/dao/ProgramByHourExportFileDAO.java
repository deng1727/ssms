/*
 * �ļ�����BaseVideoFileDAO.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
 */

package com.aspire.dotcard.baseVideoNew.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2012
 * </p>
 * 
 * @author 
 * @version
 */
public class ProgramByHourExportFileDAO
{
    protected static JLogger logger = LoggerFactory.getLogger(ProgramByHourExportFileDAO.class);

    private static ProgramByHourExportFileDAO dao = new ProgramByHourExportFileDAO();

    private ProgramByHourExportFileDAO()
    {
    }

    public static ProgramByHourExportFileDAO getInstance()
    {
        return dao;
    }
    /**
     * �����õ������ȵ��Ƽ�ID�б�
     * 
     * @return
     */
    public Map getRecommendIDMap()
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("�õ������ȵ��Ƽ�ID�б�,��ʼ");
        }
        Map recommendIDMap = new HashMap();
        ResultSet rs = null;

        try
        {
            // select recommendId from t_vo_recommend
            rs = DB.getInstance()
                   .queryBySQLCode("baseVideo.dao.ProgramByHourExportFileDAO.getRecommendIDMap",
                                   null);
            while (rs.next())
            {
            	recommendIDMap.put(rs.getString("recommendId"), "");
            }
        }
        catch (SQLException e)
        {
            logger.error("���ݿ�SQLִ���쳣����ѯʧ��", e);
        }
        catch (DAOException ex)
        {
            logger.error("���ݿ�����쳣����ѯʧ��", ex);
        }
        finally
        {
            DB.close(rs);
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("�õ������ȵ��Ƽ�ID�б�,����");
        }
        return recommendIDMap;
    }
    
    

    
    
    /**
     * �����õ���Ŀ����ID�б�
     * 
     * @return
     */
    public Map getProgramIDMap()
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("�õ���Ŀ����ID�б�,��ʼ");
        }
        Map programIDMap = new HashMap();
        ResultSet rs = null;

        try
        {
            // select t.programid from t_vo_program t
            rs = DB.getInstance()
                   .queryBySQLCode("baseVideo.dao.ProgramByHourExportFileDAO.getProgramIDMap",
                                   null);
            while (rs.next())
            {
            	String keyid = rs.getString("programid")+"|"+rs.getString("nodeid");
				programIDMap.put(keyid, "");
            	//programIDMap.put(rs.getString("programid"), "");
            }
        }
        catch (SQLException e)
        {
            logger.error("���ݿ�SQLִ���쳣����ѯʧ��", e);
        }
        catch (DAOException ex)
        {
            logger.error("���ݿ�����쳣����ѯʧ��", ex);
        }
        finally
        {
            DB.close(rs);
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("�õ���Ŀ����ID�б�,����");
        }
        return programIDMap;
    }
    
    
    /**
     * �����õ���ĿID�б�
     * 
     * @return ��ĿID�б�
     */
    public Map getNodeIDMap()
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("�õ���Ŀ��Ϣ��ID�б�,��ʼ");
        }
        Map nodeIdMap = new HashMap();
        ResultSet rs = null;

        try
        {
            // select n.nodeid from t_vo_node n
            rs = DB.getInstance()
                   .queryBySQLCode("baseVideo.dao.ProgramByHourExportFileDAO.getNodeIDMap",
                                   null);
            while (rs.next())
            {
                nodeIdMap.put(rs.getString("nodeid"), "");
            }
        }
        catch (SQLException e)
        {
            logger.error("���ݿ�SQLִ���쳣����ѯʧ��", e);
        }
        catch (DAOException ex)
        {
            logger.error("���ݿ�����쳣����ѯʧ��", ex);
        }
        finally
        {
            DB.close(rs);
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("�õ���Ŀ��Ϣ��ID�б�,����");
        }
        return nodeIdMap;
    }

    /**
     * �����õ���ƵID�б�
     * 
     * @return
     */
    public Map getVideoIDMap()
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("�õ���ƵID�б�,��ʼ");
        }
        Map videoIDMap = new HashMap();
        ResultSet rs = null;

        try
        {
            // select v.videoid from t_vo_video v
            rs = DB.getInstance()
                   .queryBySQLCode("baseVideo.dao.ProgramByHourExportFileDAO.getVideoIDMap",
                                   null);
            while (rs.next())
            {
                videoIDMap.put(rs.getString("videoid"), "");
            }
        }
        catch (SQLException e)
        {
            logger.error("���ݿ�SQLִ���쳣����ѯʧ��", e);
        }
        catch (DAOException ex)
        {
            logger.error("���ݿ�����쳣����ѯʧ��", ex);
        }
        finally
        {
            DB.close(rs);
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("�õ���ƵID�б�,����");
        }
        return videoIDMap;
    }


	/**
	 * �����õ���ƵID,�����б�
	 * 
	 * @return
	 */
	public Map getAllVideoIDMap()
	{
	    if (logger.isDebugEnabled())
	    {
	        logger.debug("�õ���ƵID�б�,��ʼ");
	    }
	    Map videoIDMap = new HashMap();
	    ResultSet rs = null;
	
	    try
	    {
	        //select videoId,codeRateID from T_VO_VIDEO 
	        rs = DB.getInstance()
	               .queryBySQLCode("baseVideo.exportfile.VideoByHourExportFile.getAllVideoIDMap",
	                               null);
	        while (rs.next())
	        {
	            videoIDMap.put(rs.getString("videoId")+"|"+rs.getString("codeRateID"), "");
	        }
	    }
	    catch (SQLException e)
	    {
	        logger.error("���ݿ�SQLִ���쳣����ѯʧ��", e);
	    }
	    catch (DAOException ex)
	    {
	        logger.error("���ݿ�����쳣����ѯʧ��", ex);
	    }
	    finally
	    {
	        DB.close(rs);
	    }
	
	    if (logger.isDebugEnabled())
	    {
	        logger.debug("�õ���ƵID�б�,����");
	    }
	    return videoIDMap;
	}
}

