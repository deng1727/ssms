/*
 * 文件名：VideoDAO.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */
package com.aspire.ponaadmin.web.datasync.implement.video;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2012</p>
 * @author wangminlong
 * @version 
 */
public class VideoDAO
{
    protected static JLogger logger = LoggerFactory.getLogger(VideoDAO.class);
    
    private static VideoDAO videoDAO = new VideoDAO();

    public static VideoDAO getInstance()
    {
        return videoDAO;
    }
    
    private VideoDAO()
    {
        
    }
    
    /**
     * 根据传入值判断是否是新加的三种类型，删了数据库数据
     * @param object
     */
    public void delVideoData(Object dataDealer) throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("delVideoData(" + dataDealer.getClass() + ") is starting ...");
        }

        String sql = null ;
        
        if (dataDealer instanceof ProgramDetailDealer)
        {
            // delete from t_vb_programdetail
            sql = "com.aspire.ponaadmin.web.datasync.implement.video.VideoDAO.delProgramDetail";
        }
        else if (dataDealer instanceof ProgramDealer)
        {
            // delete from t_vb_program
            sql = "com.aspire.ponaadmin.web.datasync.implement.video.VideoDAO.delProgram";
        }
        else if (dataDealer instanceof VideoNodeDealer)
        {
            // delete from t_vb_node
            sql = "com.aspire.ponaadmin.web.datasync.implement.video.VideoDAO.delVideoNode";
        }
        else
        {
            return;
        }

        try
        {
            DB.getInstance().executeBySQLCode(sql, null);
        }
        catch (DAOException e)
        {
            throw new DAOException("根据传入值判断是否是新加的三种类型，删了数据库数据发生异常:", e);
        }
    }
    
    /**
     * 查询栏目信息表是否存在此栏目信息
     * @param id 栏目id
     * @return
     */
    public boolean hasNodeId(String id) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("hasNodeId(" + id + ") is starting ...");
        }
        
        ResultSet rs = null;
        String sql = "com.aspire.ponaadmin.web.datasync.implement.video.VideoDAO.hasNodeId" ;
        
        try
        {
            rs = DB.getInstance().queryBySQLCode(sql, new Object[]{id});
            
            if(rs.next())
            {
                return true;
            }
            
            return false;
        }
        catch (DAOException e)
        {
            throw new DAOException("查询栏目信息表是否存在此栏目信息时发生查询异常:", e);
        }
        catch (SQLException e)
        {
            throw new DAOException("查询栏目信息表是否存在此栏目信息时发生查询异常:", e);
        }
        finally
        {
            DB.close(rs);
        }
    }
    
    /**
     * 查询节目信息表是否存在此节目信息
     * @param id 节目id
     * @return
     */
    public boolean hasProgramId(String id) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("hasProgramId(" + id + ") is starting ...");
        }
        
        ResultSet rs = null;
        String sql = "com.aspire.ponaadmin.web.datasync.implement.video.VideoDAO.hasProgramId" ;
        
        
        try
        {
            rs = DB.getInstance().queryBySQLCode(sql, new Object[]{id});
            
            if(rs.next())
            {
                return true;
            }
            
            return false;
        }
        catch (DAOException e)
        {
            throw new DAOException("查询节目信息表是否存在此栏目信息时发生查询异常:", e);
        }
        catch (SQLException e)
        {
            throw new DAOException("查询栏目信息表是否存在此栏目信息时发生查询异常:", e);
        }
        finally
        {
            DB.close(rs);
        }
    }
    
    /**
     * 得到节目信息ID列表
     * @return
     * @throws DAOException
     */
    public Map getProgramIdList() throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("得到节目信息ID列表,开始");
        }
        Map programIdMap = new HashMap();
        ResultSet rs = null;

        try
        {
            rs = DB.getInstance().queryBySQLCode(
                    "com.aspire.ponaadmin.web.datasync.implement.video.VideoDAO.getProgramIdList", null);
            while (rs.next())
            {
                programIdMap.put(rs.getString("programid"),"");
            }
        } catch (SQLException e)
        {
            logger.error("数据库SQL执行异常，查询失败", e);
        } catch (DAOException ex)
        {
            logger.error("数据库操作异常，查询失败", ex);
        } finally
        {
            DB.close(rs);
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("得到节目信息ID列表,结束");
        }
        return programIdMap;
    }
    
    /**
     * 得到栏目信息表ID列表
     * @return
     * @throws DAOException
     */
    public Map getNodeIdList() throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("得到栏目信息表ID列表,开始");
        }
        Map nodeIdMap = new HashMap();
        ResultSet rs = null;

        try
        {
            rs = DB.getInstance().queryBySQLCode(
                    "com.aspire.ponaadmin.web.datasync.implement.video.VideoDAO.getNodeIdList", null);
            while (rs.next())
            {
                nodeIdMap.put(rs.getString("nodeid"),"");
            }
        } catch (SQLException e)
        {
            logger.error("数据库SQL执行异常，查询失败", e);
        } catch (DAOException ex)
        {
            logger.error("数据库操作异常，查询失败", ex);
        } finally
        {
            DB.close(rs);
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("得到栏目信息表ID列表,结束");
        }
        return nodeIdMap;
    }
}
