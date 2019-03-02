/*
 * 文件名：BaseVideoFileDAO.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
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
                   .queryBySQLCode("baseVideo.dao.BaseVideoFileDAO.getNodeIDMap",
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
                   .queryBySQLCode("baseVideo.dao.BaseVideoFileDAO.getVideoIDMap",
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

    // add by dongke 2012-07-14 得到栏目(t_vo_node)中的nodeid|LOGOPATH的LIST
    public List getAllVideoNode()
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("得到栏目中nodeID|LOGOPATH的列表,为了集中处理这些图片的下载上传的事情。");
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
            logger.debug("得到栏目中nodeID|LOGOPATH的列表,结束,但是这玩意正正正占用着大量的内存哦。。。，请及时清理！");
        }
        return allVideoNode;
    }

    // add by aiyan 2012-07-14 得到节目详情(t_vo_program)中的PROGRAMID|LOGOPATH的LIST
    public List getAllVideoDetail()
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("得到节目详情中PROGRAMID|LOGOPATH的列表,为了集中处理这些图片的下载上传的事情。");
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
            logger.debug("得到节目详情中PROGRAMID|LOGOPATH的列表,结束,但是这玩意正正正占用着大量的内存哦。。。，请及时清理！");
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

            logger.error("删除索引失败:", e);
            throw new DAOException("删除索引失败:" + "<br>"
                                   + PublicUtil.GetCallStack(e) + "<br>");
        }
        if (logger.isDebugEnabled())
        {
            logger.debug("dropIndex...end");
        }
    }
    
    /**
     * 建立临时索引，用于增量操作
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
			logger.error("建立临时索引，用于增量操作数据出错，表名为t_vo_video_tra:", e);
			throw new DAOException("建立临时索引，用于增量操作数据出错，表名为t_vo_video_tra:<br>"
					+ PublicUtil.GetCallStack(e) + "<br>");
		}

		if (logger.isDebugEnabled()) 
		{
			logger.debug("createVideoIndexByAdd...end");
		}
    }
    
    /**
     * 删除表中临时索引
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
			logger.error("删除表中临时索引出错，表名为t_vo_video_tra:", e);
			throw new DAOException("删除表中临时索引出错，表名为t_vo_video_tra:<br>"
					+ PublicUtil.GetCallStack(e) + "<br>");
		}

		if (logger.isDebugEnabled()) 
		{
			logger.debug("delVideoIndexByAdd...end");
		}
    }
    
    /**
     * 建立临时索引，用于删除表中重复数据
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
			logger.error("建立临时索引，用于删除表中重复数据出错，表名为t_vo_video_tra:", e);
			throw new DAOException("建立临时索引，用于删除表中重复数据出错，表名为t_vo_video_tra:<br>"
					+ PublicUtil.GetCallStack(e) + "<br>");
		}

		if (logger.isDebugEnabled()) 
		{
			logger.debug("createVideoIndex...end");
		}
    }
    
    /**
     * 删除video表中重复数据记录
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
			logger.error("删除重复数据记录出错，表名为t_vo_video_tra:", e);
			throw new DAOException("删除重复数据记录出错，表名为t_vo_video_tra:<br>"
					+ PublicUtil.GetCallStack(e) + "<br>");
		}

		if (logger.isDebugEnabled()) 
		{
			logger.debug("delRepeatData...end");
		}
	}
    
    /**
     * 删除表中临时索引
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
			logger.error("删除表中临时索引出错，表名为t_vo_video_tra:", e);
			throw new DAOException("删除表中临时索引出错，表名为t_vo_video_tra:<br>"
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
            logger.error("创建索引失败:" + key, e);
            throw new DAOException("创建索引失败:" + key + "<br>"
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
            logger.debug("准备更新T_VO_PROGRAME表的logopath了");
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
            logger.error("数据库操作异常，查询失败", ex);
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("更新完毕(programeid:" + programeid + ",logopath:"
                         + logoPath + ")，更新影响行数" + n);
        }

    }

    // add by dongke 2012-07-16
    // alter table t_vo_node add truelogopath varchar2(512)
    public void updateNodeLogoPath(String nodeId, String logoPath)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("准备更新T_VO_Node表的logopath了");
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
            logger.error("数据库操作异常，查询失败", ex);
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("更新完毕(nodeId:" + nodeId + ",logopath:" + logoPath
                         + ")，更新影响行数" + n);
        }

    }

    /**
     * 用于清空旧的模拟表数据，进行每天全量同步
     */
    public String cleanOldSimulationData() throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("用于清空旧的模拟表数据,开始");
        }
        
        StringBuffer sf = new StringBuffer();

        // 删除模拟商品表
        String sqlCode = "baseVideo.dao.BaseVideoFileDAO.cleanOldSimulationData.reference";

        // 删除模拟货架表
        String delRefSqlCode = "baseVideo.dao.BaseVideoFileDAO.cleanOldSimulationData.category";

        try
        {
            int ret1 = DB.getInstance().executeBySQLCode(sqlCode, null);
            sf.append("删除基地视频模拟商品表中数据" + ret1 + "条。<br>");
            int ret2 = DB.getInstance().executeBySQLCode(delRefSqlCode, null);
            sf.append("删除基地视频模拟货架表中数据" + ret2 + "条。<br>");
        }
        catch (DAOException e)
        {
            logger.error("执行清空旧的模拟表数据SQL发生异常，删除旧表失败", e);
            throw new BOException("执行清空旧的模拟表数据SQL发生异常", e);
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("用于清空旧的模拟表数据,结束。");
        }
        
        logger.info(sf.toString());
        return sf.toString();
    }

    /**
     * 自定义组装模拟树结构表
     * 
     */
    public String insertDataToTree() throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("用于自定义组装模拟树结构表,开始");
        }
        StringBuffer sf = new StringBuffer();
        
        // 加入栏目信息
        String sqlCode = "baseVideo.dao.BaseVideoFileDAO.insertDataToTree.node";

        // 更新栏目父id
        String sqlCode1 = "baseVideo.dao.BaseVideoFileDAO.insertDataToTree.updateNode";

        try
        {
            int ret1 = DB.getInstance().executeBySQLCode(sqlCode, null);
            sf.append("自定义组装模拟树结构加入数据" + ret1 + "条。<br>");
            int ret2 = DB.getInstance().executeBySQLCode(sqlCode1, null);
            sf.append("更新模拟树结构更新数据" + ret2 + "条。<br>");
        }
        catch (DAOException e)
        {
            logger.error("执行自定义组装模拟树结构表时发生异常，创建表失败", e);
            throw new BOException("执行自定义组装模拟树结构表时发生异常", e);
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("用于自定义组装模拟树结构表,结束");
        }
        
        logger.info(sf.toString());
        return sf.toString();
    }

    /**
     * 自定义组装模拟商品结构表
     * 
     */
    public String insertDataToReference() throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("用于自定义组装模拟商品结构表,开始");
        }
        StringBuffer sf = new StringBuffer();
        
        // 加入栏目商品信息
        String sqlCode = "baseVideo.dao.BaseVideoFileDAO.insertDataToReference.node";

        try
        {
        	int ret = DB.getInstance().executeBySQLCode(sqlCode,null);
        	sf.append("执行自定义组装模拟商品结构表加入数据" + ret + "条。<br>");
        }
        catch (DAOException e)
        {
            logger.error("执行自定义组装模拟商品结构表时发生异常，创建表失败", e);
            throw new BOException("执行自定义组装模拟商品结构表时发生异常", e);
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("用于自定义组装模拟树商品构表,结束");
        }
        
        logger.info(sf.toString());
        return sf.toString();
    }

    /**
     * 用来删除指定节目详情。其中包括直播节目单，商品信息，节目详情。
     * 
     * @param programId 节目ID
     */
    public void delProgramById(String programId) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("用来删除指定节目详情,开始");
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
            logger.error("执行用来删除指定节目详情。其中包括直播节目单，商品信息，节目详情时发生异常，创建表失败", e);
            throw new BOException("执行用来删除指定节目详情。其中包括直播节目单，商品信息，节目详情时发生异常", e);
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("用来删除指定节目详情,结束");
        }
    }

    /**
     * 用来得到视频产品ID列表
     * 
     * @return
     */
    public Map getVideoProductMap()
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("得到视频ID列表,开始");
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
            logger.debug("得到视频产品ID列表,结束");
        }
        return productIDMap;
    }

    public Map getProgramNodeIDMap()
    {
        // TODO Auto-generated method stub
        if (logger.isDebugEnabled())
        {
            logger.debug("得到节目详情列表,开始！");
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
            logger.debug("得到视频产品ID列表,结束");
        }
        return programIDMap;
    }
    
    public Map getVideoDetailIDMap()
    {
        // TODO Auto-generated method stub
        if (logger.isDebugEnabled())
        {
            logger.debug("得到节目统计列表,开始！");
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
            logger.debug("得到节目统计列表,结束");
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
            logger.debug("得到AllLogopath列表,结束");
        }
        return allLogopath;
    }

    /**
     * 备份同步表
     * 
     * @param backupSql
     * @throws BOException
     */
    public void createBackupSql(String bakTable, String defTable)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("备份同步表,开始: bakTable" + bakTable + ",defTable:"
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
            logger.error("创建备份表失败:bakTable" + bakTable + ",defTable:"
                         + defTable, e);
            throw new BOException("创建备份表失败:bakTable" + bakTable + ",defTable:"
                                  + defTable + "<br>"
                                  + PublicUtil.GetCallStack(e) + "<br>");
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("备份同步表,结束");
        }
    }

    public void renameTable(String renameTable, String tempTable,
                            String defTable) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("改表名: " + renameTable + "," + tempTable + ","
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
            logger.debug("开始改表名: ");
        }
        try
        {
            DB.getInstance().executeMuti(renameSql, null);

        }
        catch (DAOException e)
        {
            logger.error("改同步表名失败: renameTable:" + renameTable + ",tempTable:"
                         + tempTable + ",defTable:" + defTable + "<br>", e);
            sb.append("改同步表名失败: renameTable:" + renameTable + ",tempTable:"
                      + tempTable + ",defTable:" + defTable + "<br>");
            sb.append("修改基地业务数据同步表名出错。出错时间：");
            sb.append(PublicUtil.getCurDateTime("yyyy-MM-dd HH:mm:ss") + "<br>");
            sb.append(PublicUtil.GetCallStack(e) + "<br>");
            // 更改表名失败
            throw new BOException(sb.toString());
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("改表名结束");
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
            logger.error("DAOException 查询失败", e);
            throw new BOException("isExist 查询失败:" + PublicUtil.GetCallStack(e)
                                  + "<br>");
        }
        catch (SQLException e)
        {
            logger.error("SQLException 查询失败", e);
            throw new BOException("isExist 查询失败:" + PublicUtil.GetCallStack(e)
                                  + "<br>");
        }
        finally
        {
            DB.close(rs);
        }
        return count == 0 ? false : true;
    }

    /**
     * 删除备份表
     * 
     * @param backupSql
     * @throws BOException
     */
    public void delBackupSql(String bakTable) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("删除备份表,开始: " + bakTable);
        }
        String backupSql = "drop table " + bakTable;
        try
        {
            DB.getInstance().execute(backupSql, null);
        }
        catch (DAOException e)
        {
            logger.error("删除备份表失败:" + bakTable, e);
            throw new BOException("删除备份表失败:" + bakTable
                                  + PublicUtil.GetCallStack(e) + "<br>");
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("删除备份表,结束");
        }
    }

    /**
     * 查询同步表关联的所有索引名称
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
            logger.debug("查询同步表索引,开始 renameTables: " + renameTables
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
        // 查询索引必须大写
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
            logger.debug("truncateTempSync 删除所有同步临时表数据,开始 renameTables: "
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
            logger.error("truncateTempSync,删除所有同步临时表数据失败:", e);
            throw new BOException("删除所有同步临时表数据:" + PublicUtil.GetCallStack(e)
                                  + "<br>");
        }
    }

    public void insertTempSync(String renameTables, String defSuffix)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("insertTempSync 导入同步正式表数据到临时表,开始 renameTables: "
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
            logger.error("insertTempSync,导入同步正式表数据到临时表失败:", e);
            throw new BOException("导入同步正式表数据到临时表失败:"
                                  + PublicUtil.GetCallStack(e) + "<br>");
        }
    }

    /**
     * 查询当前货架中所有的货架id
     * 
     * @return
     */
    public List queryCategoryIdList() throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("查询当前货架中所有的货架id, 开始");
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
            logger.error("DAOException 查询失败", e);
            throw new BOException("queryCategoryIdList 查询失败:"
                                  + PublicUtil.GetCallStack(e) + "<br>");
        }
        catch (SQLException e)
        {
            logger.error("SQLException 查询失败", e);
            throw new BOException("queryCategoryIdList 查询失败:"
                                  + PublicUtil.GetCallStack(e) + "<br>");
        }
        finally
        {
            DB.close(rs);
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("查询当前货架中所有的货架id, 结束");
        }
        return list;
    }

    /**
     * 用于返回当前货架id的所有子栏目数
     * 
     * @param categoryId
     * @return
     */
    public int queryNodeNumByCategoryId(String categoryId) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("返回当前货架id的所有子栏目数, 开始");
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
            logger.error("DAOException 查询失败", e);
            throw new BOException("queryNodeNumByCategoryId 查询失败:"
                                  + PublicUtil.GetCallStack(e) + "<br>");
        }
        catch (SQLException e)
        {
            logger.error("SQLException 查询失败", e);
            throw new BOException("queryNodeNumByCategoryId 查询失败:"
                                  + PublicUtil.GetCallStack(e) + "<br>");
        }
        finally
        {
            DB.close(rs);
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("返回当前货架id的所有子栏目数, 结束");
        }
        return count;
    }

    /**
     * 用于返回当前货架id的所有商品数
     * 
     * @param categoryId
     * @return
     */
    public int queryRefNumByCategoryId(String categoryId) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("返回当前货架id的所有商品数, 开始");
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
            logger.error("DAOException 查询失败", e);
            throw new BOException("queryRefNumByCategoryId 查询失败:"
                                  + PublicUtil.GetCallStack(e) + "<br>");
        }
        catch (SQLException e)
        {
            logger.error("SQLException 查询失败", e);
            throw new BOException("queryRefNumByCategoryId 查询失败:"
                                  + PublicUtil.GetCallStack(e) + "<br>");
        }
        finally
        {
            DB.close(rs);
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("返回当前货架id的所有商品数, 结束");
        }
        return count;
    }

    /**
     * 更新货架的子栏目数与栏目下节目数
     * 
     * @param categoryId
     * @param nodeNum
     * @param refNum
     */
    public void updateCategoryNodeNum(String categoryId, int nodeNum, int refNum) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("更新货架的子栏目数与栏目下节目数,开始: " + categoryId);
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
            logger.error("执行更新货架的子栏目数与栏目下节目数SQL发生异常，更新失败", e);
            throw new BOException("执行更新货架的子栏目数与栏目下节目数SQL发生异常", e);
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("更新货架的子栏目数与栏目下节目数,结束");
        }
    }
    
    /**
     * 
     *@desc 执行删除老的产品数据
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
            logger.error("执行删除老的产品SQL发生异常，更新失败", e);
            throw new BOException("执行执行删除老的产品数据SQL发生异常", e);
        }
    }
    
    public int callUpdateCategoryNum(){
    	 CallableStatement cs = null;
        
         try
         {  Connection conn = DB.getInstance().getConnection();
            
              cs = conn.prepareCall("{?=call f_update_video_rnum}");  
             cs.registerOutParameter(1, Types.INTEGER);  
             cs.execute();  
             int intValue = cs.getInt(1); //获取函数返回结果
             return intValue;
         }catch(Exception e){
        	e.printStackTrace(); 
        	//throw new BOException();
        	return 0;
         }
    }
    
}
