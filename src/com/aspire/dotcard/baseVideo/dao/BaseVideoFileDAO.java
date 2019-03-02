/*
 * �ļ�����BaseVideoFileDAO.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
 */

package com.aspire.dotcard.baseVideo.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseVideo.conf.BaseVideoConfig;
import com.aspire.ponaadmin.web.util.PublicUtil;

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
 * @author wangminlong
 * @version
 */
public class BaseVideoFileDAO
{
    protected static JLogger logger = LoggerFactory.getLogger(BaseVideoFileDAO.class);

    private static BaseVideoFileDAO dao = new BaseVideoFileDAO();

    private BaseVideoFileDAO()
    {
    }

    public static BaseVideoFileDAO getInstance()
    {
        return dao;
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
                   .queryBySQLCode("baseVideo.dao.BaseVideoFileDAO.getNodeIDMap",
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
                   .queryBySQLCode("baseVideo.dao.BaseVideoFileDAO.getVideoIDMap",
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

    // add by dongke 2012-07-14 �õ���Ŀ(t_vo_node)�е�nodeid|LOGOPATH��LIST
    public List getAllVideoNode()
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("�õ���Ŀ��nodeID|LOGOPATH���б�,Ϊ�˼��д�����ЩͼƬ�������ϴ������顣");
        }
        List allVideoNode = new ArrayList();
        ResultSet rs = null;
        try
        {
            // delete from T_VO_NODEPic;
            DB.getInstance()
                      .executeBySQLCode("com.aspire.dotcard.baseVideo.dao.BaseVideoFileDAO.getAllVideoNode.delete",
                                        null);
            // select nodeid,logopath from t_vo_node t where t.logopath!='' and
            // t.logopath is not null order by t.logopath asc
            rs = DB.getInstance()
                   .queryBySQLCode("com.aspire.dotcard.baseVideo.dao.BaseVideoFileDAO.getAllVideoNode.select",
                                   null);
            while (rs.next())
            {

                allVideoNode.add(rs.getString("nodeid") + "|"
                                 + rs.getString("logopath"));
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
            logger.debug("�õ���Ŀ��nodeID|LOGOPATH���б�,����,����������������ռ���Ŵ������ڴ�Ŷ���������뼰ʱ����");
        }
        return allVideoNode;
    }

    // add by aiyan 2012-07-14 �õ���Ŀ����(t_vo_program)�е�PROGRAMID|LOGOPATH��LIST
    public List getAllVideoDetail()
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("�õ���Ŀ������PROGRAMID|LOGOPATH���б�,Ϊ�˼��д�����ЩͼƬ�������ϴ������顣");
        }
        List allVideoDetail = new ArrayList();
        ResultSet rs = null;
        try
        {

            // delete from T_VO_programPic;
            DB.getInstance()
                      .executeBySQLCode("com.aspire.dotcard.baseVideo.dao.BaseVideoFileDAO.getAllVideoDetail.delete",
                                        null);
            // select programid,logopath from t_vo_program t order by t.logopath
            // asc
            rs = DB.getInstance()
                   .queryBySQLCode("com.aspire.dotcard.baseVideo.dao.BaseVideoFileDAO.getAllVideoDetail.select",
                                   null);
            while (rs.next())
            {

                allVideoDetail.add(rs.getString("programid") + "|"
                                   + rs.getString("ftplogopath"));
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
            logger.debug("�õ���Ŀ������PROGRAMID|LOGOPATH���б�,����,����������������ռ���Ŵ������ڴ�Ŷ���������뼰ʱ����");
        }
        return allVideoDetail;
    }

    public void dropIndex(List indexNames) throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("dropIndex...start");
        }
        //
        // drop index T_IDX_VO_VIDEOID ;
        String dropIndexSqlCode = "drop index ";
        int length = indexNames.size();
        String[] mutiSQLCode = new String[length];
        for (int i = 0; i < length; i++)
        {
            mutiSQLCode[i] = dropIndexSqlCode + indexNames.get(i);
        }
        try
        {

            DB.getInstance().executeMuti(mutiSQLCode, null);
        }
        catch (DAOException e)
        {

            logger.error("ɾ������ʧ��:", e);
            throw new DAOException("ɾ������ʧ��:" + "<br>"
                                   + PublicUtil.GetCallStack(e) + "<br>");
        }
        if (logger.isDebugEnabled())
        {
            logger.debug("dropIndex...end");
        }
    }
    
    /**
     * ������ʱ������������������
     * @throws DAOException 
     */
    public void createVideoIndexByAdd() throws DAOException
    {
		if (logger.isDebugEnabled())
		{
			logger.debug("createVideoIndexByAdd...start");
		}

		// create index IDX_VO_VIDEOID_TEMPADD on t_vo_video_tra (videoid)
		String sqlCode = "com.aspire.dotcard.baseVideo.dao.BaseVideoFileDAO.createVideoIndexByAdd";
		try 
		{
			DB.getInstance().executeBySQLCode(sqlCode, null);
		}
		catch (DAOException e) 
		{
			logger.error("������ʱ���������������������ݳ�������Ϊt_vo_video_tra:", e);
			throw new DAOException("������ʱ���������������������ݳ�������Ϊt_vo_video_tra:<br>"
					+ PublicUtil.GetCallStack(e) + "<br>");
		}

		if (logger.isDebugEnabled()) 
		{
			logger.debug("createVideoIndexByAdd...end");
		}
    }
    
    /**
     * ɾ��������ʱ����
     * @throws DAOException 
     */
    public void delVideoIndexByAdd() throws DAOException
    {
		if (logger.isDebugEnabled())
		{
			logger.debug("delVideoIndexByAdd...start");
		}

		// drop index IDX_VO_VIDEOID_TEMPADD
		String sqlCode = "com.aspire.dotcard.baseVideo.dao.BaseVideoFileDAO.delVideoIndexByAdd";
		try 
		{
			DB.getInstance().executeBySQLCode(sqlCode, null);
		}
		catch (DAOException e) 
		{
			logger.error("ɾ��������ʱ������������Ϊt_vo_video_tra:", e);
			throw new DAOException("ɾ��������ʱ������������Ϊt_vo_video_tra:<br>"
					+ PublicUtil.GetCallStack(e) + "<br>");
		}

		if (logger.isDebugEnabled()) 
		{
			logger.debug("delVideoIndexByAdd...end");
		}
    }
    
    /**
     * ������ʱ����������ɾ�������ظ�����
     * @throws DAOException 
     */
    public void createVideoIndex() throws DAOException
    {
		if (logger.isDebugEnabled())
		{
			logger.debug("createVideoIndex...start");
		}

		// create index IDX_VO_VIDEOID_TEMP on t_vo_video_tra (videoid, coderateid)
		String sqlCode = "com.aspire.dotcard.baseVideo.dao.BaseVideoFileDAO.createVideoIndex";
		try 
		{
			DB.getInstance().executeBySQLCode(sqlCode, null);
		}
		catch (DAOException e) 
		{
			logger.error("������ʱ����������ɾ�������ظ����ݳ�������Ϊt_vo_video_tra:", e);
			throw new DAOException("������ʱ����������ɾ�������ظ����ݳ�������Ϊt_vo_video_tra:<br>"
					+ PublicUtil.GetCallStack(e) + "<br>");
		}

		if (logger.isDebugEnabled()) 
		{
			logger.debug("createVideoIndex...end");
		}
    }
    
    /**
     * ɾ��video�����ظ����ݼ�¼
     * 
     * @throws DAOException 
     */
    public void delRepeatData() throws DAOException
    {
		if (logger.isDebugEnabled())
		{
			logger.debug("delRepeatData...start");
		}

		// delete from t_vo_video_tra v where v.rowid > (select min(vt.rowid)
		// from t_vo_video_tra vt where vt.videoid = v.videoid and vt.coderateid
		// = v.coderateid)
		String sqlCode = "com.aspire.dotcard.baseVideo.dao.BaseVideoFileDAO.delRepeatData";
		try 
		{
			DB.getInstance().executeBySQLCode(sqlCode, null);
		}
		catch (DAOException e) 
		{
			logger.error("ɾ���ظ����ݼ�¼��������Ϊt_vo_video_tra:", e);
			throw new DAOException("ɾ���ظ����ݼ�¼��������Ϊt_vo_video_tra:<br>"
					+ PublicUtil.GetCallStack(e) + "<br>");
		}

		if (logger.isDebugEnabled()) 
		{
			logger.debug("delRepeatData...end");
		}
	}
    
    /**
     * ɾ��������ʱ����
     * @throws DAOException 
     */
    public void delVideoIndex() throws DAOException
    {
		if (logger.isDebugEnabled())
		{
			logger.debug("delVideoIndex...start");
		}

		// drop index IDX_VO_VIDEOID_TEMP
		String sqlCode = "com.aspire.dotcard.baseVideo.dao.BaseVideoFileDAO.delVideoIndex";
		try 
		{
			DB.getInstance().executeBySQLCode(sqlCode, null);
		}
		catch (DAOException e) 
		{
			logger.error("ɾ��������ʱ������������Ϊt_vo_video_tra:", e);
			throw new DAOException("ɾ��������ʱ������������Ϊt_vo_video_tra:<br>"
					+ PublicUtil.GetCallStack(e) + "<br>");
		}

		if (logger.isDebugEnabled()) 
		{
			logger.debug("delVideoIndex...end");
		}
    }

    public void createIndex(String key, int videoSync_ID) throws BOException,
                    DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("createIndex...start");
        }

        try
        {
            String SQLByCode = "";
            // t_vo_video,t_vo_node,t_vo_program,t_vo_live,t_vo_product,t_vo_videodetail,t_vo_reference,t_vo_category

            if ("t_vo_video".equals(key.toLowerCase()))
            {
                SQLByCode = "com.aspire.dotcard.baseVideo.dao.BaseVideoFileDAO.createIndex.video_tra";
            }
            else if ("t_vo_node".equals(key.toLowerCase()))
            {
                SQLByCode = "com.aspire.dotcard.baseVideo.dao.BaseVideoFileDAO.createIndex.node_tra";
            }
            else if ("t_vo_program".equals(key.toLowerCase()))
            {
                SQLByCode = "com.aspire.dotcard.baseVideo.dao.BaseVideoFileDAO.createIndex.program_tra";
            }
            else if ("t_vo_program_nodeid".equals(key.toLowerCase()))
            {
                SQLByCode = "com.aspire.dotcard.baseVideo.dao.BaseVideoFileDAO.createIndex.program_nodeid_tra";
            }
            else if ("t_vo_live".equals(key.toLowerCase()))
            {
                SQLByCode = "com.aspire.dotcard.baseVideo.dao.BaseVideoFileDAO.createIndex.live_tra";
            }
            else if ("t_vo_live_id".equals(key.toLowerCase()))
            {
                SQLByCode = "com.aspire.dotcard.baseVideo.dao.BaseVideoFileDAO.createIndex.live_id_tra";
            }
            else if ("t_vo_product".equals(key.toLowerCase()))
            {
                SQLByCode = "com.aspire.dotcard.baseVideo.dao.BaseVideoFileDAO.createIndex.product_tra";
            }
            else if ("t_vo_videodetail".equals(key.toLowerCase()))
            {
                SQLByCode = "com.aspire.dotcard.baseVideo.dao.BaseVideoFileDAO.createIndex.videodetail_tra";
            }
            else if ("t_vo_reference".equals(key.toLowerCase()))
            {
                SQLByCode = "com.aspire.dotcard.baseVideo.dao.BaseVideoFileDAO.createIndex.reference_tra";
            }
            else if ("t_vo_category".equals(key.toLowerCase()))
            {
                SQLByCode = "com.aspire.dotcard.baseVideo.dao.BaseVideoFileDAO.createIndex.category_tra";
            } else if ("t_vo_category_id".equals(key.toLowerCase()))
            {
                SQLByCode = "com.aspire.dotcard.baseVideo.dao.BaseVideoFileDAO.createIndex.category_id_tra";
            } else if ("t_vo_category_pid".equals(key.toLowerCase()))
            {
                SQLByCode = "com.aspire.dotcard.baseVideo.dao.BaseVideoFileDAO.createIndex.category_pid_tra";
            }
            else if ("t_vo_reference_cid".equals(key.toLowerCase()))
            {
            	//create index t_idx_vo_refere_cid<$1>  on t_vo_reference_tra(CATEGORYID)
                SQLByCode = "com.aspire.dotcard.baseVideo.dao.BaseVideoFileDAO.createIndex.reference_cid_tra";
            }


            String sql = DB.getInstance().getSQLByCode(SQLByCode);
            sql = PublicUtil.replace(sql, "<$1>", String.valueOf(videoSync_ID));

            DB.getInstance().execute(sql, null);
        }
        catch (DAOException e)
        {
            logger.error("��������ʧ��:" + key, e);
            throw new DAOException("��������ʧ��:" + key + "<br>"
                                   + PublicUtil.GetCallStack(e) + "<br>");
        }
        if (logger.isDebugEnabled())
        {
            logger.debug("createIndex...end");
        }
    }

    // add by aiyan 2012-07-16
    // alter table t_vo_program add truelogopath varchar2(512)
    public void updateLogoPath(String programeid, String logoPath)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("׼������T_VO_PROGRAME���logopath��");
        }
        int n = 0;
        try
        {
            // update t_vo_program t set t.logopath=? where t.programid=?;
            // insert into T_VO_programPic(logopath,programid) values(?,?)
            n = DB.getInstance()
                  .executeBySQLCode("com.aspire.dotcard.baseVideo.dao.BaseVideoFileDAO.updateLogoPath.insert",
                                    new String[] { logoPath, programeid });
        }
        catch (DAOException ex)
        {
            logger.error("���ݿ�����쳣����ѯʧ��", ex);
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("�������(programeid:" + programeid + ",logopath:"
                         + logoPath + ")������Ӱ������" + n);
        }

    }

    // add by dongke 2012-07-16
    // alter table t_vo_node add truelogopath varchar2(512)
    public void updateNodeLogoPath(String nodeId, String logoPath)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("׼������T_VO_Node���logopath��");
        }
        int n = 0;
        try
        {
            // update t_vo_program t set t.logopath=? where t.programid=?;
            // insert into T_VO_NODEPic (logopath,nodeid) values(?,?)
            n = DB.getInstance()
                  .executeBySQLCode("com.aspire.dotcard.baseVideo.dao.BaseVideoFileDAO.updateNodeLogoPath.insert",
                                    new String[] { logoPath, nodeId });
        }
        catch (DAOException ex)
        {
            logger.error("���ݿ�����쳣����ѯʧ��", ex);
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("�������(nodeId:" + nodeId + ",logopath:" + logoPath
                         + ")������Ӱ������" + n);
        }

    }

    /**
     * ������վɵ�ģ������ݣ�����ÿ��ȫ��ͬ��
     */
    public String cleanOldSimulationData() throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("������վɵ�ģ�������,��ʼ");
        }
        
        StringBuffer sf = new StringBuffer();

        // ɾ��ģ����Ʒ��
        String sqlCode = "baseVideo.dao.BaseVideoFileDAO.cleanOldSimulationData.reference";

        // ɾ��ģ����ܱ�
        String delRefSqlCode = "baseVideo.dao.BaseVideoFileDAO.cleanOldSimulationData.category";

        try
        {
            int ret1 = DB.getInstance().executeBySQLCode(sqlCode, null);
            sf.append("ɾ��������Ƶģ����Ʒ��������" + ret1 + "����<br>");
            int ret2 = DB.getInstance().executeBySQLCode(delRefSqlCode, null);
            sf.append("ɾ��������Ƶģ����ܱ�������" + ret2 + "����<br>");
        }
        catch (DAOException e)
        {
            logger.error("ִ����վɵ�ģ�������SQL�����쳣��ɾ���ɱ�ʧ��", e);
            throw new BOException("ִ����վɵ�ģ�������SQL�����쳣", e);
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("������վɵ�ģ�������,������");
        }
        
        logger.info(sf.toString());
        return sf.toString();
    }

    /**
     * �Զ�����װģ�����ṹ��
     * 
     */
    public String insertDataToTree() throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("�����Զ�����װģ�����ṹ��,��ʼ");
        }
        StringBuffer sf = new StringBuffer();
        
        // ������Ŀ��Ϣ
        String sqlCode = "baseVideo.dao.BaseVideoFileDAO.insertDataToTree.node";

        // ������Ŀ��id
        String sqlCode1 = "baseVideo.dao.BaseVideoFileDAO.insertDataToTree.updateNode";

        try
        {
            int ret1 = DB.getInstance().executeBySQLCode(sqlCode, null);
            sf.append("�Զ�����װģ�����ṹ��������" + ret1 + "����<br>");
            int ret2 = DB.getInstance().executeBySQLCode(sqlCode1, null);
            sf.append("����ģ�����ṹ��������" + ret2 + "����<br>");
        }
        catch (DAOException e)
        {
            logger.error("ִ���Զ�����װģ�����ṹ��ʱ�����쳣��������ʧ��", e);
            throw new BOException("ִ���Զ�����װģ�����ṹ��ʱ�����쳣", e);
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("�����Զ�����װģ�����ṹ��,����");
        }
        
        logger.info(sf.toString());
        return sf.toString();
    }

    /**
     * �Զ�����װģ����Ʒ�ṹ��
     * 
     */
    public String insertDataToReference() throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("�����Զ�����װģ����Ʒ�ṹ��,��ʼ");
        }
        StringBuffer sf = new StringBuffer();
        
        // ������Ŀ��Ʒ��Ϣ
        String sqlCode = "baseVideo.dao.BaseVideoFileDAO.insertDataToReference.node";

        try
        {
        	int ret = DB.getInstance().executeBySQLCode(sqlCode,null);
        	sf.append("ִ���Զ�����װģ����Ʒ�ṹ���������" + ret + "����<br>");
        }
        catch (DAOException e)
        {
            logger.error("ִ���Զ�����װģ����Ʒ�ṹ��ʱ�����쳣��������ʧ��", e);
            throw new BOException("ִ���Զ�����װģ����Ʒ�ṹ��ʱ�����쳣", e);
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("�����Զ�����װģ������Ʒ����,����");
        }
        
        logger.info(sf.toString());
        return sf.toString();
    }

    /**
     * ����ɾ��ָ����Ŀ���顣���а���ֱ����Ŀ������Ʒ��Ϣ����Ŀ���顣
     * 
     * @param programId ��ĿID
     */
    public void delProgramById(String programId) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("����ɾ��ָ����Ŀ����,��ʼ");
        }

        // delete from t_vo_reference r where r.programid=?
        String sqlCode1 = "baseVideo.dao.BaseVideoFileDAO.delProgramById.reference";

        // delete from t_vo_live l where l.programid=?
        String sqlCode2 = "baseVideo.dao.BaseVideoFileDAO.delProgramById.live";

        // delete from t_vo_program p where p.programid=?
        String sqlCode = "baseVideo.dao.BaseVideoFileDAO.delProgramById.program";

        Object[] paras = new Object[] { programId };

        try
        {
            DB.getInstance()
              .executeMutiBySQLCode(new String[] { sqlCode1, sqlCode2, sqlCode },
                                    new Object[][] { paras, paras, paras });
        }
        catch (DAOException e)
        {
            logger.error("ִ������ɾ��ָ����Ŀ���顣���а���ֱ����Ŀ������Ʒ��Ϣ����Ŀ����ʱ�����쳣��������ʧ��", e);
            throw new BOException("ִ������ɾ��ָ����Ŀ���顣���а���ֱ����Ŀ������Ʒ��Ϣ����Ŀ����ʱ�����쳣", e);
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("����ɾ��ָ����Ŀ����,����");
        }
    }

    /**
     * �����õ���Ƶ��ƷID�б�
     * 
     * @return
     */
    public Map getVideoProductMap()
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("�õ���ƵID�б�,��ʼ");
        }
        Map productIDMap = new HashMap();
        ResultSet rs = null;

        try
        {
            // select v.PRODUCTID from T_VO_PRODUCT v
            rs = DB.getInstance()
                   .queryBySQLCode("baseVideo.dao.BaseVideoFileDAO.getVideoProductMap",
                                   null);
            while (rs.next())
            {
                productIDMap.put(rs.getString("PRODUCTID"), "");
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
            logger.debug("�õ���Ƶ��ƷID�б�,����");
        }
        return productIDMap;
    }

    public Map getProgramNodeIDMap()
    {
        // TODO Auto-generated method stub
        if (logger.isDebugEnabled())
        {
            logger.debug("�õ���Ŀ�����б�,��ʼ��");
        }
        Map programIDMap = new HashMap();
        ResultSet rs = null;

        try
        {
            // select t.nodeid,t.programid from t_vo_program t
            rs = DB.getInstance()
                   .queryBySQLCode("baseVideo.dao.BaseVideoFileDAO.getProgramIDMap",
                                   null);
            while (rs.next())
            {
                programIDMap.put(rs.getString("nodeid") + "|"
                        + rs.getString("programid"), "");
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
            logger.debug("�õ���Ƶ��ƷID�б�,����");
        }
        return programIDMap;
    }
    
    public Map getVideoDetailIDMap()
    {
        // TODO Auto-generated method stub
        if (logger.isDebugEnabled())
        {
            logger.debug("�õ���Ŀͳ���б�,��ʼ��");
        }
        Map programIDMap = new HashMap();
        ResultSet rs = null;

        try
        {
            // select v.programid from t_Vo_Videodetail_Tra v
            rs = DB.getInstance()
                   .queryBySQLCode("baseVideo.dao.BaseVideoFileDAO.getVideoDetailIDMap",
                                   null);
            while (rs.next())
            {
                programIDMap.put(rs.getString("programid"), "");
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
            logger.debug("�õ���Ŀͳ���б�,����");
        }
        return programIDMap;
    }

    public Map getAllLogopath()
    {
        // TODO Auto-generated method stub
        Map allLogopath = new HashMap();
        ResultSet rs = null;

        try
        {
            // select v.PRODUCTID from T_VO_PRODUCT v
            rs = DB.getInstance()
                   .queryBySQLCode("baseVideo.dao.BaseVideoFileDAO.getAllLogopath",
                                   new String[] { BaseVideoConfig.logoPath });
            while (rs.next())
            {
                allLogopath.put(rs.getString("programid"),
                                rs.getString("logopath"));
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
            logger.debug("�õ�AllLogopath�б�,����");
        }
        return allLogopath;
    }

    /**
     * ����ͬ����
     * 
     * @param backupSql
     * @throws BOException
     */
    public void createBackupSql(String bakTable, String defTable)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("����ͬ����,��ʼ: bakTable" + bakTable + ",defTable:"
                         + defTable);
        }
        String backupSql = "create table " + bakTable + " as "
                           + " select * from " + defTable;
        try
        {

            DB.getInstance().execute(backupSql, null);
        }
        catch (DAOException e)
        {
            logger.error("�������ݱ�ʧ��:bakTable" + bakTable + ",defTable:"
                         + defTable, e);
            throw new BOException("�������ݱ�ʧ��:bakTable" + bakTable + ",defTable:"
                                  + defTable + "<br>"
                                  + PublicUtil.GetCallStack(e) + "<br>");
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("����ͬ����,����");
        }
    }

    public void renameTable(String renameTable, String tempTable,
                            String defTable) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("�ı���: " + renameTable + "," + tempTable + ","
                         + defTable);
        }
        StringBuffer sb = new StringBuffer();
        String[] renameSql = new String[3];
        // a-->a_temp
        renameSql[0] = "alter table " + renameTable + " rename to " + tempTable;
        // a_tra-->a
        renameSql[1] = "alter table " + defTable + " rename to " + renameTable;
        // a_temp-->a_tra
        renameSql[2] = "alter table " + tempTable + " rename to " + defTable;

        if (logger.isDebugEnabled())
        {
            logger.debug("��ʼ�ı���: ");
        }
        try
        {
            DB.getInstance().executeMuti(renameSql, null);

        }
        catch (DAOException e)
        {
            logger.error("��ͬ������ʧ��: renameTable:" + renameTable + ",tempTable:"
                         + tempTable + ",defTable:" + defTable + "<br>", e);
            sb.append("��ͬ������ʧ��: renameTable:" + renameTable + ",tempTable:"
                      + tempTable + ",defTable:" + defTable + "<br>");
            sb.append("�޸Ļ���ҵ������ͬ��������������ʱ�䣺");
            sb.append(PublicUtil.getCurDateTime("yyyy-MM-dd HH:mm:ss") + "<br>");
            sb.append(PublicUtil.GetCallStack(e) + "<br>");
            // ���ı���ʧ��
            throw new BOException(sb.toString());
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("�ı�������");
        }
    }

    public boolean isExist() throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("BaseVideoFileDAO.isExist()");
        }
        int count = 0;
        ResultSet rs = null;
        try
        {
            // select 1 from dual where 0 <> (select count(v.rowid) from
            // t_vo_video v) and 0 <> (select count(n.rowid) from t_vo_node n)

            rs = DB.getInstance()
                   .queryBySQLCode("baseVideo.dao.BaseVideoFileDAO.isExist",
                                   null);

            if (rs.next())
            {
                count = rs.getInt(1);
            }
        }
        catch (DAOException e)
        {
            logger.error("DAOException ��ѯʧ��", e);
            throw new BOException("isExist ��ѯʧ��:" + PublicUtil.GetCallStack(e)
                                  + "<br>");
        }
        catch (SQLException e)
        {
            logger.error("SQLException ��ѯʧ��", e);
            throw new BOException("isExist ��ѯʧ��:" + PublicUtil.GetCallStack(e)
                                  + "<br>");
        }
        finally
        {
            DB.close(rs);
        }
        return count == 0 ? false : true;
    }

    /**
     * ɾ�����ݱ�
     * 
     * @param backupSql
     * @throws BOException
     */
    public void delBackupSql(String bakTable) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("ɾ�����ݱ�,��ʼ: " + bakTable);
        }
        String backupSql = "drop table " + bakTable;
        try
        {
            DB.getInstance().execute(backupSql, null);
        }
        catch (DAOException e)
        {
            logger.error("ɾ�����ݱ�ʧ��:" + bakTable, e);
            throw new BOException("ɾ�����ݱ�ʧ��:" + bakTable
                                  + PublicUtil.GetCallStack(e) + "<br>");
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("ɾ�����ݱ�,����");
        }
    }

    /**
     * ��ѯͬ���������������������
     * 
     * @param renameTables
     * @return
     * @throws DAOException
     */
    public List queryDropIndex(String renameTables, String defSuffix)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("��ѯͬ��������,��ʼ renameTables: " + renameTables
                         + ",defSuffix:" + defSuffix);
        }
        if ("".equals(renameTables))
        {
            throw new DAOException("renameTables is null<br>");
        }
        String sqlCode = "com.aspire.dotcard.baseVideo.dao.BaseVideoFileDAO.queryDropIndex";
        String sql = DB.getInstance().getSQLByCode(sqlCode);
        String[] renameTable = renameTables.split(",");
        int length = renameTable.length;
        StringBuffer sqlIn = new StringBuffer();
        for (int i = 0; i < length; i++)
        {
            if (i > 0)
            {
                sqlIn.append(',');
            }
            sqlIn.append('\'');
            sqlIn.append(renameTable[i]);
            sqlIn.append(defSuffix);
            sqlIn.append('\'');
        }
        // ��ѯ���������д
        sql = PublicUtil.replace(sql, "<$1>", sqlIn.toString().toUpperCase());

        ResultSet rs = null;
        List list = new ArrayList();
        try
        {
            rs = DB.getInstance().query(sql, null);
            while (rs != null && rs.next())
            {

                list.add(rs.getString("index_name"));
            }
        }
        catch (SQLException e)
        {
            logger.error(e);
            throw new DAOException("queryDropIndex error.", e);

        }
        finally
        {
            DB.close(rs);
        }
        return list;
    }

    public void truncateTempSync(String renameTables, String defSuffix)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("truncateTempSync ɾ������ͬ����ʱ������,��ʼ renameTables: "
                         + renameTables + ",defSuffix:" + defSuffix);
        }

        String[] renameTable = renameTables.split(",");
        int length = renameTable.length;

        String[] truncateSql = new String[length];
        for (int i = 0; i < length; i++)
        {
            truncateSql[i] = " truncate table " + renameTable[i] + defSuffix;
        }

        try
        {
            DB.getInstance().executeMuti(truncateSql, null);
        }
        catch (DAOException e)
        {
            logger.error("truncateTempSync,ɾ������ͬ����ʱ������ʧ��:", e);
            throw new BOException("ɾ������ͬ����ʱ������:" + PublicUtil.GetCallStack(e)
                                  + "<br>");
        }
    }

    public void insertTempSync(String renameTables, String defSuffix)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("insertTempSync ����ͬ����ʽ�����ݵ���ʱ��,��ʼ renameTables: "
                         + renameTables + ",defSuffix:" + defSuffix);
        }

        String[] renameTable = renameTables.split(",");
        int length = renameTable.length;

        String[] insertSql = new String[length];
        for (int i = 0; i < length; i++)
        {

            insertSql[i] = "insert into " + renameTable[i] + defSuffix
                           + " select * from " + renameTable[i];
        }

        try
        {
            DB.getInstance().executeMuti(insertSql, null);
        }
        catch (DAOException e)
        {
            logger.error("insertTempSync,����ͬ����ʽ�����ݵ���ʱ��ʧ��:", e);
            throw new BOException("����ͬ����ʽ�����ݵ���ʱ��ʧ��:"
                                  + PublicUtil.GetCallStack(e) + "<br>");
        }
    }

    /**
     * ��ѯ��ǰ���������еĻ���id
     * 
     * @return
     */
    public List queryCategoryIdList() throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("��ѯ��ǰ���������еĻ���id, ��ʼ");
        }

        List list = new ArrayList();
        ResultSet rs = null;

        try
        {
            // select t.id from t_vo_category t
            rs = DB.getInstance()
                   .queryBySQLCode("baseVideo.dao.BaseVideoFileDAO.queryCategoryIdList",
                                   null);
            while (rs.next())
            {
                list.add(rs.getString("id"));
            }
        }
        catch (DAOException e)
        {
            logger.error("DAOException ��ѯʧ��", e);
            throw new BOException("queryCategoryIdList ��ѯʧ��:"
                                  + PublicUtil.GetCallStack(e) + "<br>");
        }
        catch (SQLException e)
        {
            logger.error("SQLException ��ѯʧ��", e);
            throw new BOException("queryCategoryIdList ��ѯʧ��:"
                                  + PublicUtil.GetCallStack(e) + "<br>");
        }
        finally
        {
            DB.close(rs);
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("��ѯ��ǰ���������еĻ���id, ����");
        }
        return list;
    }

    /**
     * ���ڷ��ص�ǰ����id����������Ŀ��
     * 
     * @param categoryId
     * @return
     */
    public int queryNodeNumByCategoryId(String categoryId) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("���ص�ǰ����id����������Ŀ��, ��ʼ");
        }

        int count = 0;
        ResultSet rs = null;

        try
        {
            // select count(1) as count from t_vo_category t where t.parentid =
            // ?
            rs = DB.getInstance()
                   .queryBySQLCode("baseVideo.dao.BaseVideoFileDAO.queryNodeNumByCategoryId",
                                   new Object[] { categoryId });
            if (rs.next())
            {
                count = rs.getInt(1);
            }
        }
        catch (DAOException e)
        {
            logger.error("DAOException ��ѯʧ��", e);
            throw new BOException("queryNodeNumByCategoryId ��ѯʧ��:"
                                  + PublicUtil.GetCallStack(e) + "<br>");
        }
        catch (SQLException e)
        {
            logger.error("SQLException ��ѯʧ��", e);
            throw new BOException("queryNodeNumByCategoryId ��ѯʧ��:"
                                  + PublicUtil.GetCallStack(e) + "<br>");
        }
        finally
        {
            DB.close(rs);
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("���ص�ǰ����id����������Ŀ��, ����");
        }
        return count;
    }

    /**
     * ���ڷ��ص�ǰ����id��������Ʒ��
     * 
     * @param categoryId
     * @return
     */
    public int queryRefNumByCategoryId(String categoryId) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("���ص�ǰ����id��������Ʒ��, ��ʼ");
        }

        int count = 0;
        ResultSet rs = null;

        try
        {
            // select count(1) as count from t_vo_reference r where
            // r.categoryid=?
            rs = DB.getInstance()
                   .queryBySQLCode("baseVideo.dao.BaseVideoFileDAO.queryRefNumByCategoryId",
                                   new Object[] { categoryId });
            if (rs.next())
            {
                count = rs.getInt(1);
            }
        }
        catch (DAOException e)
        {
            logger.error("DAOException ��ѯʧ��", e);
            throw new BOException("queryRefNumByCategoryId ��ѯʧ��:"
                                  + PublicUtil.GetCallStack(e) + "<br>");
        }
        catch (SQLException e)
        {
            logger.error("SQLException ��ѯʧ��", e);
            throw new BOException("queryRefNumByCategoryId ��ѯʧ��:"
                                  + PublicUtil.GetCallStack(e) + "<br>");
        }
        finally
        {
            DB.close(rs);
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("���ص�ǰ����id��������Ʒ��, ����");
        }
        return count;
    }

    /**
     * ���»��ܵ�����Ŀ������Ŀ�½�Ŀ��
     * 
     * @param categoryId
     * @param nodeNum
     * @param refNum
     */
    public void updateCategoryNodeNum(String categoryId, int nodeNum, int refNum) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("���»��ܵ�����Ŀ������Ŀ�½�Ŀ��,��ʼ: " + categoryId);
        }

        // update t_vo_category c set c.nodenum=?, c.refnum=? where c.id= ?
        String sqlCode = "baseVideo.dao.BaseVideoFileDAO.updateCategoryNodeNum";

        try
        {
            DB.getInstance()
              .executeBySQLCode(sqlCode,
                                new Object[] { String.valueOf(nodeNum),
                                                String.valueOf(refNum),
                                                categoryId });
        }
        catch (DAOException e)
        {
            logger.error("ִ�и��»��ܵ�����Ŀ������Ŀ�½�Ŀ��SQL�����쳣������ʧ��", e);
            throw new BOException("ִ�и��»��ܵ�����Ŀ������Ŀ�½�Ŀ��SQL�����쳣", e);
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("���»��ܵ�����Ŀ������Ŀ�½�Ŀ��,����");
        }
    }
    
    /**
     * 
     *@desc ִ��ɾ���ϵĲ�Ʒ����
     *@author dongke
     *Aug 28, 2012
     * @param productID
     * @throws BOException
     */
    public void delProduct(String productID) throws BOException{
    	 // delete from t_vo_product t where t.productid=?
        String sqlCode = "baseVideo.dao.BaseVideoFileDAO.delProduct.delete";

        try
        {
            DB.getInstance()
              .executeBySQLCode(sqlCode,
                                new Object[] { productID });
        }
        catch (DAOException e)
        {
            logger.error("ִ��ɾ���ϵĲ�ƷSQL�����쳣������ʧ��", e);
            throw new BOException("ִ��ִ��ɾ���ϵĲ�Ʒ����SQL�����쳣", e);
        }
    }
    
    public int callUpdateCategoryNum(){
    	 CallableStatement cs = null;
        
         try
         {  Connection conn = DB.getInstance().getConnection();
            
              cs = conn.prepareCall("{?=call f_update_video_rnum}");  
             cs.registerOutParameter(1, Types.INTEGER);  
             cs.execute();  
             int intValue = cs.getInt(1); //��ȡ�������ؽ��
             return intValue;
         }catch(Exception e){
        	e.printStackTrace(); 
        	//throw new BOException();
        	return 0;
         }
    }
    
}
