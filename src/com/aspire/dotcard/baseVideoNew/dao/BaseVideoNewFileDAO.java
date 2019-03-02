/*
 * �ļ�����BaseVideoNewFileDAO.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
 */

package com.aspire.dotcard.baseVideoNew.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseVideoNew.vo.NodeVO;
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
public class BaseVideoNewFileDAO
{
	protected static JLogger logger = LoggerFactory
			.getLogger(BaseVideoNewFileDAO.class);
	
	private static BaseVideoNewFileDAO dao = new BaseVideoNewFileDAO();
	
	private BaseVideoNewFileDAO()
	{}
	
	public static BaseVideoNewFileDAO getInstance()
	{
		return dao;
	}
	
	/**
	 * �õ�����У�����ݺϷ��Ե���ʽ�����м��ϱ�����
	 * 
	 * @param map
	 *            ��ʽ�픵����
	 * @param mapAdd
	 *            ���g������������
	 * @param mapDel
	 *            ���g��h��������
	 * @return �ϱ픵����
	 */
	public Map<String, String> getCheckMidIDMap(Map<String, String> map,
			Map<String, String> mapAdd, Map<String, String> mapDel)
	{
		map.putAll(mapAdd);
		
		Iterator<String> it = mapDel.keySet().iterator();
		while (it.hasNext())
		{
			String key = it.next();
			if (map.containsKey(key))
			{
				map.remove(key);
			}
		}
		
		return map;
	}
	
	/**
	 * �����õ���ƵID�б�
	 * 
	 * @return
	 */
	public Map<String, String> getVideoIDMap()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("�õ���ƵID�б�,��ʼ");
		}
		Map<String, String> videoIDMap = new HashMap<String, String>();
		ResultSet rs = null;
		
		try
		{
			// select v.videoid from t_vo_video v
			rs = DB.getInstance().queryBySQLCode(
					"baseVideoNew.dao.BaseVideoNewFileDAO.getVideoIDMap", null);
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
	 * ���ݲ������͵õ���Ƶ�м��ID�б�
	 * 
	 * @return
	 */
	public Map<String, String> getVideoMidIDMap(String status)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("���ݲ������͵õ���Ƶ�м��ID�б�,��ʼ");
		}
		Map<String, String> videoIDMap = new HashMap<String, String>();
		ResultSet rs = null;
		
		try
		{
			// select v.videoid from t_vo_video_mid v where v.status = ?
			rs = DB.getInstance().queryBySQLCode(
					"baseVideoNew.dao.BaseVideoNewFileDAO.getVideoMidIDMap",
					new Object[] { status });
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
			logger.debug("���ݲ������͵õ���Ƶ�м��ID�б�,����");
		}
		return videoIDMap;
	}
	
	/**
	 * �����õ���ĿID�б�
	 * 
	 * @return ��ĿID�б�
	 */
	public Map<String, String> getNodeIDMap()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("�õ���Ŀ��Ϣ��ID�б�,��ʼ");
		}
		Map<String, String> nodeIdMap = new HashMap<String, String>();
		ResultSet rs = null;
		
		try
		{
			// select n.nodeid from t_vo_node n
			rs = DB.getInstance().queryBySQLCode(
					"baseVideoNew.dao.BaseVideoNewFileDAO.getNodeIDMap", null);
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
	 * ���ݲ������͵õ���Ŀ�м��ID�б�
	 * 
	 * @return
	 */
	public Map<String, String> getNodeMidIDMap(String status)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("���ݲ������͵õ���Ŀ�м��ID�б�,��ʼ");
		}
		Map<String, String> nodeIdMap = new HashMap<String, String>();
		ResultSet rs = null;
		try
		{
			// select n.nodeid from t_vo_node_mid n where n.status = ?
			rs = DB.getInstance().queryBySQLCode(
					"baseVideoNew.dao.BaseVideoNewFileDAO.getNodeMidIDMap",
					new Object[] { status });
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
			logger.debug("���ݲ������͵õ���Ŀ�м��ID�б�,����");
		}
		return nodeIdMap;
	}
	
	/**
	 * ������ĿID��ȡ��Ŀ��Ϣ
	 * 
	 * @return ��Ŀ��Ϣ
	 */
	public NodeVO getNodeDataByNodeId(String nodeId)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("getNodeDataByNodeId(" + nodeId + ") is starting ...");
		}
		
		// select
		// n.nodeid,n.nodename,n.description,n.logopath,n.productid,n.collectflag
		// from t_vo_node n where n.nodeid=?
		String sqlCode = "baseVideoNew.dao.BaseVideoNewFileDAO.getNodeDataByNodeId.SELECT";
		ResultSet rs = null;
		NodeVO vo = null;
		
		try
		{
			rs = DB.getInstance().queryBySQLCode(sqlCode,
					new Object[] { nodeId });
			
			if (rs.next())
			{
				vo = new NodeVO();
				vo.setNodeId(rs.getString("nodeid"));
				vo.setNodename(rs.getString("nodename"));
				vo.setDescription(rs.getString("description"));
				vo.setLogopath(rs.getString("logopath"));
				vo.setProductid(rs.getString("productid"));
				vo.setCollectflag(rs.getString("collectflag") == null ? 1 : Integer.parseInt(rs.getString("collectflag")));
			}
		}
		catch (SQLException e)
		{
			logger.error("���ݿ�SQLִ���쳣����ѯʧ��", e);
			vo = null;
		}
		catch (DAOException ex)
		{
			logger.error("���ݿ�����쳣����ѯʧ��", ex);
			vo = null;
		}
		finally
		{
			DB.close(rs);
		}
		return vo;
	}
	
	/**
	 * �����õ����ݼ��ڵ�ID�б�
	 * 
	 * @return ��ĿID�б�
	 */
	public Map<String, String> getCollectNodeIDMap()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("�õ����ݼ��ڵ��ID�б�,��ʼ");
		}
		Map<String, String> collectNodeIdMap = new HashMap<String, String>();
		ResultSet rs = null;
		
		try
		{
			// select n.nodeid,n.parentnodeid from t_vo_collect_node n
			rs = DB.getInstance().queryBySQLCode(
					"baseVideoNew.dao.BaseVideoNewFileDAO.getCollectNodeIDMap", null);
			while (rs.next())
			{
				collectNodeIdMap.put(rs.getString("nodeid")+"|"+rs.getString("parentnodeid"), "");
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
			logger.debug("�õ����ݼ��ڵ��ID�б�,����");
		}
		return collectNodeIdMap;
	}
	
	/**
	 * �����õ����ݼ�ID�б�
	 * 
	 * @return ���ݼ�ID�б�
	 */
	public Map<String, String> getCollectIDMap()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("�õ����ݼ���ID�б�,��ʼ");
		}
		Map<String, String> collectIdMap = new HashMap<String, String>();
		ResultSet rs = null;
		
		try
		{
			// select c.collectid from t_vo_collect 
			rs = DB.getInstance().queryBySQLCode(
					"baseVideoNew.dao.BaseVideoNewFileDAO.getCollectIDMap", null);
			while (rs.next())
			{
				collectIdMap.put(rs.getString("collectid"), "");
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
			logger.debug("�õ����ݼ���ID�б�,����");
		}
		return collectIdMap;
	}
	/**
	 * �����õ���ƷID�б�
	 * 
	 * @return ��Ʒ��ID�б�
	 */
	public Map<String, String> getCostProductIDMap()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("�õ���ƷID�б�,��ʼ");
		}
		Map<String, String> collectIdMap = new HashMap<String, String>();
		ResultSet rs = null;
		
		try
		{
			rs = DB.getInstance().queryBySQLCode(
					"baseVideoNew.dao.BaseVideoNewFileDAO.getCostProductIDMap", null);
			while (rs.next())
			{
				collectIdMap.put(rs.getString("productid"), "");
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
			logger.debug("�õ���ƷID�б�,����");
		}
		return collectIdMap;
	}
	
	/**
	 * �õ�����У�ֱ����Ŀ�ε���Ƶ����ID�б�
	 * 
	 * @return
	 */
	public Map<String, String> getProgramNodeIDMap()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("�õ�����У�ֱ����Ŀ�ε���Ƶ����ID�б�,��ʼ��");
		}
		Map<String, String> programIDMap = new HashMap<String, String>();
		ResultSet rs = null;
		
		try
		{
			// select t.nodeid,t.programid from t_vo_program t
			rs = DB.getInstance().queryBySQLCode(
					"baseVideoNew.dao.BaseVideoNewFileDAO.getProgramIDMap",
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
			logger.debug("�õ�����У�ֱ����Ŀ�ε���Ƶ����ID�б�,����");
		}
		return programIDMap;
	}
	
	/**
	 * ���ݲ������͵õ�����У�ֱ����Ŀ�ε���Ƶ�����м��ID�б�
	 * 
	 * @return
	 */
	public Map<String, String> getProgramNodeMidIDMap(String status)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("���ݲ������͵õ�����У�ֱ����Ŀ�ε���Ƶ�����м��ID�б�,��ʼ��");
		}
		Map<String, String> programIDMap = new HashMap<String, String>();
		ResultSet rs = null;
		
		try
		{
			// select t.nodeid,t.programid from t_vo_program_mid t where
			// t.status = ?
			rs = DB
					.getInstance()
					.queryBySQLCode(
							"baseVideoNew.dao.BaseVideoNewFileDAO.getProgramNodeMidIDMap",
							new Object[] { status });
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
			logger.debug("���ݲ������͵õ�����У�ֱ����Ŀ�ε���Ƶ�����м��ID�б�,����");
		}
		return programIDMap;
	}
	
	/**
	 * �����õ���Ƶ��ƷID�б�
	 * 
	 * @return
	 */
	public Map<String, String> getVideoProductMap()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("���õ���Ƶ��ƷID�б�,��ʼ");
		}
		Map<String, String> productIDMap = new HashMap<String, String>();
		ResultSet rs = null;
		
		try
		{
			// select v.PRODUCTID from T_VO_PRODUCT v
			rs = DB.getInstance().queryBySQLCode(
					"baseVideoNew.dao.BaseVideoNewFileDAO.getVideoProductMap",
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
			logger.debug("���õ���Ƶ��ƷID�б�,����");
		}
		return productIDMap;
	}
	
	/**
	 * �õ���Ƶ����ID�б�
	 * 
	 * @return
	 */
	public Map<String, String> getProgramIDMap()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("�õ���Ƶ����ID�б�,��ʼ��");
		}
		Map<String, String> programIDMap = new HashMap<String, String>();
		ResultSet rs = null;
		
		try
		{
			// select t.programid from t_vo_program t
			       //baseVideoNew.dao.BaseVideoNewFileDAO.getProgramIDMap=select t.nodeid,t.programid from t_vo_program t
			rs = DB.getInstance().queryBySQLCode(
					"baseVideoNew.dao.BaseVideoNewFileDAO.getProgramIDMap",
					null);
			while (rs.next())
			{
				String keyid = rs.getString("programid")+"|"+rs.getString("nodeid");
				programIDMap.put(keyid, "");
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
			logger.debug("�õ���Ƶ����ID�б�,����");
		}
		return programIDMap;
	}
	
	/**
	 * �õ���Ƶֱ����Ŀ��Key�б�
	 * 
	 * @return
	 */
	public Map<String, String> getLiveIDMap()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("�õ���Ƶֱ����Ŀ��Key�б�,��ʼ��");
		}
		Map<String, String> programIDMap = new HashMap<String, String>();
		ResultSet rs = null;
		
		try
		{
			// select l.videoid, l.starttime from t_vo_live l
			rs = DB.getInstance().queryBySQLCode(
					"baseVideoNew.dao.BaseVideoNewFileDAO.getLiveIDMap", null);
			while (rs.next())
			{
				programIDMap.put(rs.getString("videoid") + "|"
						+ rs.getString("starttime"), "");
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
			logger.debug("�õ���Ƶֱ����Ŀ��Key�б�,����");
		}
		return programIDMap;
	}
	
	/**
	 * �õ���Ƶ��Ŀͳ��Key�б�
	 * 
	 * @return
	 */
	public Map<String, String> getVideoDetailIDMap()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("�õ���Ŀͳ���б�,��ʼ��");
		}
		Map<String, String> programIDMap = new HashMap<String, String>();
		ResultSet rs = null;
		
		try
		{
			// select v.programid from t_Vo_Videodetail v
			rs = DB.getInstance().queryBySQLCode(
					"baseVideoNew.dao.BaseVideoNewFileDAO.getVideoDetailIDMap",
					null);
			while (rs.next())
			{
				programIDMap.put(rs.getString("programid"), "0");
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
	
	/**
	 * ɾ��ͬ���м��ǰ����
	 * 
	 * @param renameTable
	 * @param defSuffix
	 * @throws BOException
	 */
	public void truncateTempSync(String renameTable, String defSuffix)
			throws BOException
	{
		
		if (logger.isDebugEnabled())
		{
			logger.debug("truncateTempSync ɾ������ͬ����ʱ������,��ʼ renameTables: "
					+ renameTable + ",defSuffix:" + defSuffix);
		}
		
		String truncateSql = " truncate table " + renameTable + defSuffix;
		
		try
		{
			DB.getInstance().execute(truncateSql, null);
		}
		catch (DAOException e)
		{
			logger.error("truncateTempSync,ɾ������ͬ����ʱ������ʧ��:", e);
			throw new BOException("ɾ������ͬ����ʱ������:" + PublicUtil.GetCallStack(e)
					+ "<br>");
		}
	}
	
	/**
	 * ���ڲ���Ԥɾ��������ָ���м��
	 * 
	 * @param sql
	 * @param key
	 * @return
	 * @throws BOException
	 */
	public int delDataByKey(String sql, String[] key) throws BOException
	{
		int ret = 0;
		
		try
		{
			ret = DB.getInstance().executeBySQLCode(sql, key);
		}
		catch (DAOException e)
		{
			logger.error("truncateTempSync,ɾ������ͬ����ʱ������ʧ��:", e);
			throw new BOException("ɾ������ͬ����ʱ������:" + PublicUtil.GetCallStack(e)
					+ "<br>");
		}
		
		return ret;
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
		// delete from t_vo_reference r where r.baseid in (select c.baseid from
		// t_vo_category c start with c.parentid = '101' connect by prior c.id =
		// c.parentid)
		String sqlCode = "baseVideoNew.dao.BaseVideoNewFileDAO.cleanOldSimulationData.reference";
		
		// ɾ��ģ����ܱ�
		// delete from t_vo_category v where v.id in (select c.id from
		// t_vo_category c start with c.parentid = '101' connect by prior c.id =
		// c.parentid union select c.id from t_vo_category c where c.parentid is
		// null and c.id not in('101','202','303','404'))
		String delRefSqlCode = "baseVideoNew.dao.BaseVideoNewFileDAO.cleanOldSimulationData.category";
		
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
		// insert into t_vo_category (id, parentid, baseid, baseparentid,
		// basetype, basename) select SEQ_VO_CATEGORY_ID.NEXTVAL,
		// decode(n.parentnodeid,'-1','101',null), n.nodeid, n.parentnodeid, '1'
		// as type, n.nodename from t_vo_node n start with n.parentnodeid = '-1'
		// connect by prior n.nodeid = n.parentnodeid
		String sqlCode = "baseVideoNew.dao.BaseVideoNewFileDAO.insertDataToTree.node";
		
		// ������Ŀ��id
		// update t_vo_category c set c.parentid = (select v.id from
		// t_vo_category v where v.baseid = c.baseparentid and v.basetype = '1')
		// where c.parentid is null and c.basetype = '1'
		String sqlCode1 = "baseVideoNew.dao.BaseVideoNewFileDAO.insertDataToTree.updateNode";
		
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
		// insert into t_vo_reference (id, categoryid, baseid, basetype,
		// programid, programname,sortid) select SEQ_VO_REFERENCE_ID.NEXTVAL,
		// c.id, baseid, basetype, p.programid, p.programname,rownum from
		// t_vo_node n, t_vo_category c, t_vo_program p where n.nodeid =
		// c.baseid and p.nodeid = n.nodeid and c.basetype = '1'
		String sqlCode = "baseVideoNew.dao.BaseVideoNewFileDAO.insertDataToReference.node";
		
		try
		{
			int ret = DB.getInstance().executeBySQLCode(sqlCode, null);
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
	 * �õ���ͬ�м�������ݵķ�����Ϣ�����Է������ʼ�
	 * 
	 * @return
	 */
	public String getMidTableGroupBy(String tableName)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("�õ���ͬ�м�������ݵķ�����Ϣ,��ʼ��");
		}
		StringBuffer sb = new StringBuffer();
		ResultSet rs = null;
		
		try
		{
			// select decode(status,'D','delete','A','insert','U','update',status) status, count(1) num from XXX group by status
			String sql = DB.getInstance().getSQLByCode(
					"baseVideoNew.dao.BaseVideoNewFileDAO.getMidTableGroupBy");
			sql = sql.replaceAll("XXX", tableName);
			rs = DB.getInstance().query(sql, null);
			while (rs.next())
			{
				sb.append(rs.getString("status"));
				sb.append(":");
				sb.append(rs.getInt("num")).append("��").append("<br>");
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
			logger.debug("�õ���ͬ�м�������ݵķ�����Ϣ,����");
		}
		return sb.toString();
	}
	
	/**
	 * ���ô洢���� ����ִ���м������ʽ��������ת��
	 */
	public int callSyncVideoData()
	{
		CallableStatement cs = null;
		Connection conn = null;
		try
		{
			conn = DB.getInstance().getConnection();
			
			cs = conn.prepareCall("{call p_delete_insert()}");
			cs.execute();
			return 1;
		}
		catch (Exception e)
		{
			logger.error("���ô洢���� ����ִ���м������ʽ��������ת��ʱ����������", e);
			return 0;
		}finally {
			if (cs != null) {
				try {
					cs.close();
				} catch (Exception e) {
					logger.error("�ر�CallableStatement=ʧ��",e);
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					logger.error("�ر�Connection=ʧ��",e);
				}
			}
		}
	}
	
	/**
	 * ���ô洢���� ����ִ����Ƶȫ����ʱ�����м��������ת��
	 */
	public int callSyncVideoFullData()
	{
		CallableStatement cs = null;
		Connection conn = null;
		try
		{
			conn = DB.getInstance().getConnection();
			
			cs = conn.prepareCall("{call p_videofull_delete_insert()}");
			cs.execute();
			return 1;
		}
		catch (Exception e)
		{
			logger.error("���ô洢���� ����ִ����Ƶȫ����ʱ�����м��������ת��ʱ����������", e);
			return 0;
		}finally {
			if (cs != null) {
				try {
					cs.close();
				} catch (Exception e) {
					logger.error("�ر�CallableStatement=ʧ��",e);
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					logger.error("�ر�Connection=ʧ��",e);
				}
			}
		}
	}
	
	/**
	 * ���ô洢���� ����ִ��ͳ������Ŀ������Ŀ�½�Ŀ��
	 */
	public int callUpdateCategoryNum()
	{
		CallableStatement cs = null;
		Connection conn = null;
		try
		{
			conn = DB.getInstance().getConnection();
			
			cs = conn.prepareCall("{?=call f_update_video_rnum}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.execute();
			int intValue = cs.getInt(1); // ��ȡ�������ؽ��
			return intValue;
		}
		catch (Exception e)
		{
			logger.error("���ô洢���� ����ִ��ͳ������Ŀ������Ŀ�½�Ŀ��ʱ����������", e);
			return 0;
		}finally {
			if (cs != null) {
				try {
					cs.close();
				} catch (Exception e) {
					logger.error("�ر�CallableStatement=ʧ��",e);
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					logger.error("�ر�Connection=ʧ��",e);
				}
			}
		}
	}
	
	public void updateCollectflag() {
		if (logger.isDebugEnabled())
		{
			logger.debug("������Ŀ������ݼ��ڵ������ݼ���ʶ�ֶ��趨Ϊ1,��ʼ");
		}
		//update t_vo_node n set n.collectflag = 1 
		//where ( n.collectflag is null or n.collectflag <> 1 ) and 
		//exists (select 1 from t_vo_collect c where c.collectid = n.nodeid)
		String sqlNodeCode = "baseVideoNew.dao.BaseVideoNewFileDAO.updateNodeCollectflag.update";
		String sqlCollectCode = "baseVideoNew.dao.BaseVideoNewFileDAO.updateCollectNodeCollectflag.update";
		try
		{
			DB.getInstance().executeMutiBySQLCodeFaild(new String[]{sqlNodeCode,sqlCollectCode}, null);
		}
		catch (DAOException e)
		{
			logger.error("������Ŀ������ݼ��ڵ������ݼ���ʶ�ֶ�ʧ��:", e);
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("������Ŀ������ݼ��ڵ������ݼ���ʶ�ֶ��趨Ϊ1,����");
		}
	}
	
	public int updateNodeCollectflag() {
		if (logger.isDebugEnabled())
		{
			logger.debug("������Ŀ������ݼ���ʶ�ֶ��趨Ϊ1,��ʼ");
		}
		//update t_vo_node n set n.collectflag = 1 
		//where ( n.collectflag is null or n.collectflag <> 1 ) and 
		//exists (select 1 from t_vo_collect c where c.collectid = n.nodeid)
		String sqlCode = "baseVideoNew.dao.BaseVideoNewFileDAO.updateNodeCollectflag.update";
		int number = 0 ;
		try
		{
			number = DB.getInstance().executeBySQLCode(sqlCode, null);
		}
		catch (DAOException e)
		{
			logger.error("������Ŀ������ݼ���ʶ�ֶ�ʧ��:", e);
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("������Ŀ������ݼ���ʶ�ֶ��趨Ϊ1,����");
		}
		return number;
	}
	
	public int updateCollectNodeCollectflag(){
		
		if (logger.isDebugEnabled())
		{
			logger.debug("�������ݼ��ڵ������ݼ���ʶ�ֶ��趨Ϊ1,��ʼ");
		}
		//update t_vo_collect_node n set n.collectflag = 1 
		//where ( n.collectflag is null or n.collectflag <> 1 ) and  
		//exists (select 1 from t_vo_collect c where c.collectid = n.nodeid)
		String sqlCode = "baseVideoNew.dao.BaseVideoNewFileDAO.updateCollectNodeCollectflag.update";
		int number = 0 ;
		try
		{
			number = DB.getInstance().executeBySQLCode(sqlCode, null);
		}
		catch (DAOException e)
		{
			logger.error("�������ݼ��ڵ������ݼ���ʶ�ֶ�ʧ��:", e);
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("�������ݼ��ڵ������ݼ���ʶ�ֶ��趨Ϊ1,����");
		}
		return number;
	}
	
	/**
	 * ���������Ƶ�ļ���ʽ���ڴ���ʽ��Ƶ����
	 */
	public String deleteVideoData() throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("���������Ƶ�ļ���ʽ���ڴ���ʽ��Ƶ����,��ʼ");
		}
		
		StringBuffer sf = new StringBuffer();
		
		// ɾ����Ƶ��ʽ���ڴ���ʽ��Ƶ����
		//  delete  t_vo_video t where exists (select 1 from v_vo_video_delete v where v.videoid = t.videoid );
		String sqlCode = "baseVideoNew.dao.BaseVideoNewFileDAO.deleteVideoData.delete";
		
		try
		{
			int ret1 = DB.getInstance().executeBySQLCode(sqlCode, null);
			sf.append("ɾ����Ƶ�ļ���ʽ���ڴ���ʽ��Ƶ����" + ret1 + "����<br>");
		}
		catch (DAOException e)
		{
			logger.error("ִ�������Ƶ�ļ���ʽ���ڴ���ʽ��Ƶ����SQL�����쳣��ɾ���ɱ�ʧ��", e);
			throw new BOException("ִ�������Ƶ�ļ���ʽ���ڴ���ʽ��Ƶ����SQL�����쳣", e);
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("���������Ƶ�ļ���ʽ���ڴ���ʽ��Ƶ����,������");
		}
		
		logger.info(sf.toString());
		return sf.toString();
	}
	
	/**
	 * ���������Ƶ��Ŀ��ʽ���ڴ���ʽ��Ƶ����
	 */
	public String deleteProgramData() throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("���������Ƶ��Ŀ��ʽ���ڴ���ʽ��Ƶ����,��ʼ");
		}
		
		StringBuffer sf = new StringBuffer();
		
		// ɾ����Ƶ��Ŀ��ʽ���ڴ���ʽ��Ƶ����
		//  delete T_VO_PROGRAM g
		//  where not exists (select 1 from t_vo_video v where g.videoid=v.videoid);
		String sqlCode = "baseVideoNew.dao.BaseVideoNewFileDAO.deleteProgramData.delete";
		
		try
		{
			int ret1 = DB.getInstance().executeBySQLCode(sqlCode, null);
			sf.append("ɾ����Ƶ��Ŀ��ʽ���ڴ���ʽ��Ƶ����" + ret1 + "����<br>");
		}
		catch (DAOException e)
		{
			logger.error("ִ�������Ƶ��Ŀ��ʽ���ڴ���ʽ��Ƶ����SQL�����쳣��ɾ���ɱ�ʧ��", e);
			throw new BOException("ִ�������Ƶ��Ŀ��ʽ���ڴ���ʽ��Ƶ����SQL�����쳣", e);
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("���������Ƶ��Ŀ��ʽ���ڴ���ʽ��Ƶ����,������");
		}
		
		logger.info(sf.toString());
		return sf.toString();
	}
	
	/**
	 * ���������Ƶ�ļ��м���ڴ���ʽ��Ƶ����
	 */
	public String deleteVideoMidData() throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("���������Ƶ�ļ��м���ڴ���ʽ��Ƶ����,��ʼ");
		}
		
		StringBuffer sf = new StringBuffer();
		
		// ɾ����Ƶ�ļ��м���ڴ���ʽ��Ƶ����
		//   delete  t_vo_video_mid t where exists (select 1 from v_vo_video_delete v where v.videoid = t.videoid );
		String sqlCode = "baseVideoNew.dao.BaseVideoNewFileDAO.deleteVideoMidData.delete";
		
		try
		{
			int ret1 = DB.getInstance().executeBySQLCode(sqlCode, null);
			sf.append("ɾ����Ƶ�ļ��м���ڴ���ʽ��Ƶ����" + ret1 + "����<br>");
		}
		catch (DAOException e)
		{
			logger.error("ִ�������Ƶ�ļ��м���ڴ���ʽ��Ƶ����SQL�����쳣��ɾ���ɱ�ʧ��", e);
			throw new BOException("ִ�������Ƶ�ļ��м���ڴ���ʽ��Ƶ����SQL�����쳣", e);
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("���������Ƶ�ļ��м���ڴ���ʽ��Ƶ����,������");
		}
		
		logger.info(sf.toString());
		return sf.toString();
	}
	
	/**
	 * ���ô洢���� ����ִ��ɾ�����ݼ���ĿΪ�յ����ݼ������ݼ��ڵ�
	 */
	public int deleteCollectData()
	{
		CallableStatement cs = null;
		Connection conn = null;
		try
		{
			conn = DB.getInstance().getConnection();
			
			cs = conn.prepareCall("{call p_vo_collect_delete()}");
			cs.execute();
			return 1;
		}
		catch (Exception e)
		{
			logger.error("���ô洢���� ����ִ��ɾ�����ݼ���ĿΪ�յ����ݼ������ݼ��ڵ�ʱ����������", e);
			return 0;
		}finally {
			if (cs != null) {
				try {
					cs.close();
				} catch (Exception e) {
					logger.error("�ر�CallableStatement=ʧ��",e);
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					logger.error("�ر�Connection=ʧ��",e);
				}
			}
		}
	}
	
}
