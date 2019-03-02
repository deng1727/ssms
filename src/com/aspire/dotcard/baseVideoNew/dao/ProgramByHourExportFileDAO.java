/*
 * 文件名：BaseVideoFileDAO.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
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
     * 用来得到内容热点推荐ID列表
     * 
     * @return
     */
    public Map getRecommendIDMap()
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("得到内容热点推荐ID列表,开始");
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
            logger.error("数据库SQL执行异常，查询失败", e);
        }
        catch (DAOException ex)
        {
            logger.error("数据库操作异常，查询失败", ex);
        }
        finally
        {
            DB.close(rs);
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("得到内容热点推荐ID列表,结束");
        }
        return recommendIDMap;
    }
    
    

    
    
    /**
     * 用来得到节目详情ID列表
     * 
     * @return
     */
    public Map getProgramIDMap()
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("得到节目详情ID列表,开始");
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
            logger.error("数据库SQL执行异常，查询失败", e);
        }
        catch (DAOException ex)
        {
            logger.error("数据库操作异常，查询失败", ex);
        }
        finally
        {
            DB.close(rs);
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("得到节目详情ID列表,结束");
        }
        return programIDMap;
    }
    
    
    /**
     * 用来得到栏目ID列表
     * 
     * @return 栏目ID列表
     */
    public Map getNodeIDMap()
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("得到栏目信息表ID列表,开始");
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
            logger.error("数据库SQL执行异常，查询失败", e);
        }
        catch (DAOException ex)
        {
            logger.error("数据库操作异常，查询失败", ex);
        }
        finally
        {
            DB.close(rs);
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("得到栏目信息表ID列表,结束");
        }
        return nodeIdMap;
    }

    /**
     * 用来得到视频ID列表
     * 
     * @return
     */
    public Map getVideoIDMap()
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("得到视频ID列表,开始");
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
            logger.error("数据库SQL执行异常，查询失败", e);
        }
        catch (DAOException ex)
        {
            logger.error("数据库操作异常，查询失败", ex);
        }
        finally
        {
            DB.close(rs);
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("得到视频ID列表,结束");
        }
        return videoIDMap;
    }


	/**
	 * 用来得到视频ID,码率列表
	 * 
	 * @return
	 */
	public Map getAllVideoIDMap()
	{
	    if (logger.isDebugEnabled())
	    {
	        logger.debug("得到视频ID列表,开始");
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
	        logger.error("数据库SQL执行异常，查询失败", e);
	    }
	    catch (DAOException ex)
	    {
	        logger.error("数据库操作异常，查询失败", ex);
	    }
	    finally
	    {
	        DB.close(rs);
	    }
	
	    if (logger.isDebugEnabled())
	    {
	        logger.debug("得到视频ID列表,结束");
	    }
	    return videoIDMap;
	}
}

