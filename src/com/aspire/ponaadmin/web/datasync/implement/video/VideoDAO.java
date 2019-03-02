/*
 * �ļ�����VideoDAO.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
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
     * ���ݴ���ֵ�ж��Ƿ����¼ӵ��������ͣ�ɾ�����ݿ�����
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
            throw new DAOException("���ݴ���ֵ�ж��Ƿ����¼ӵ��������ͣ�ɾ�����ݿ����ݷ����쳣:", e);
        }
    }
    
    /**
     * ��ѯ��Ŀ��Ϣ���Ƿ���ڴ���Ŀ��Ϣ
     * @param id ��Ŀid
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
            throw new DAOException("��ѯ��Ŀ��Ϣ���Ƿ���ڴ���Ŀ��Ϣʱ������ѯ�쳣:", e);
        }
        catch (SQLException e)
        {
            throw new DAOException("��ѯ��Ŀ��Ϣ���Ƿ���ڴ���Ŀ��Ϣʱ������ѯ�쳣:", e);
        }
        finally
        {
            DB.close(rs);
        }
    }
    
    /**
     * ��ѯ��Ŀ��Ϣ���Ƿ���ڴ˽�Ŀ��Ϣ
     * @param id ��Ŀid
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
            throw new DAOException("��ѯ��Ŀ��Ϣ���Ƿ���ڴ���Ŀ��Ϣʱ������ѯ�쳣:", e);
        }
        catch (SQLException e)
        {
            throw new DAOException("��ѯ��Ŀ��Ϣ���Ƿ���ڴ���Ŀ��Ϣʱ������ѯ�쳣:", e);
        }
        finally
        {
            DB.close(rs);
        }
    }
    
    /**
     * �õ���Ŀ��ϢID�б�
     * @return
     * @throws DAOException
     */
    public Map getProgramIdList() throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("�õ���Ŀ��ϢID�б�,��ʼ");
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
            logger.error("���ݿ�SQLִ���쳣����ѯʧ��", e);
        } catch (DAOException ex)
        {
            logger.error("���ݿ�����쳣����ѯʧ��", ex);
        } finally
        {
            DB.close(rs);
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("�õ���Ŀ��ϢID�б�,����");
        }
        return programIdMap;
    }
    
    /**
     * �õ���Ŀ��Ϣ��ID�б�
     * @return
     * @throws DAOException
     */
    public Map getNodeIdList() throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("�õ���Ŀ��Ϣ��ID�б�,��ʼ");
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
            logger.error("���ݿ�SQLִ���쳣����ѯʧ��", e);
        } catch (DAOException ex)
        {
            logger.error("���ݿ�����쳣����ѯʧ��", ex);
        } finally
        {
            DB.close(rs);
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("�õ���Ŀ��Ϣ��ID�б�,����");
        }
        return nodeIdMap;
    }
}
