package com.aspire.dotcard.basevideosync.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.util.PublicUtil;

public class BaseVideoDAO {

	protected static JLogger logger = LoggerFactory
			.getLogger(BaseVideoDAO.class);

	private static BaseVideoDAO dao = new BaseVideoDAO();

	private BaseVideoDAO() {
	}

	public static BaseVideoDAO getInstance() {
		return dao;
	}
	
	/**
	 * �õ���Ƶ��Ŀ�������ĿID�б�
	 * 
	 * @return
	 */
	public Map<String, String> getsProgramIDMap()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("�õ���Ƶ��Ŀ�������ĿID�б�,��ʼ��");
		}
		Map<String, String> programIDMap = new HashMap<String, String>();
		ResultSet rs = null;
		
		try
		{
			// select t.programid from t_v_sprogram t
			rs = DB.getInstance().queryBySQLCode(
					"com.aspire.dotcard.basevideosync.dao.BaseVideoDAO.getsProgramIDMap",
					null);
			while (rs.next())
			{
				String keyid = rs.getString("programid");
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
			logger.debug("�õ���Ƶ��Ŀ�������ĿID�б�,����");
		}
		return programIDMap;
	}
	
	/**
	 * �õ��ȵ������б��ȵ�����ID�б�
	 * 
	 * @return
	 */
	public Map<String, String> getTitleIDMap()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("�õ��ȵ������б��ȵ�����ID�б�,��ʼ��");
		}
		Map<String, String> titleIDMap = new HashMap<String, String>();
		ResultSet rs = null;
		
		try
		{
			// select h.titleid from t_v_hotcontent h
			rs = DB.getInstance().queryBySQLCode(
					"com.aspire.dotcard.basevideosync.dao.BaseVideoDAO.getTitleIDMap",
					null);
			while (rs.next())
			{
				String keyid = rs.getString("titleid");
				titleIDMap.put(keyid, "");
			}
		}
		catch (SQLException e)
		{
			logger.error("�õ��ȵ������б��ȵ�����ID�б�ִ�����ݿ�SQLִ���쳣����ѯʧ��", e);
		}
		catch (DAOException ex)
		{
			logger.error("�õ��ȵ������б��ȵ�����ID�б�ִ�����ݿ�����쳣����ѯʧ��", ex);
		}
		finally
		{
			DB.close(rs);
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("�õ��ȵ������б��ȵ�����ID�б�,����");
		}
		return titleIDMap;
	}
	
	/**
	 * �õ���Ƶҵ���ƷID�б�
	 * 
	 * @return
	 */
	public Map<String, String> getServiceIDMap()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("�õ���Ƶҵ���ƷID�б�,��ʼ��");
		}
		Map<String, String> serviceIDMap = new HashMap<String, String>();
		ResultSet rs = null;
		
		try
		{
			// select t.Servid from t_v_propkg t
			rs = DB.getInstance().queryBySQLCode(
					"com.aspire.dotcard.basevideosync.dao.BaseVideoDAO.getServiceIDMap",
					null);
			while (rs.next())
			{
				String keyid = rs.getString("Servid");
				serviceIDMap.put(keyid, "");
			}
		}
		catch (SQLException e)
		{
			logger.error("�õ���Ƶҵ���ƷID�б�ִ�����ݿ�SQLִ���쳣����ѯʧ��", e);
		}
		catch (DAOException ex)
		{
			logger.error("�õ���Ƶҵ���ƷID�б�ִ�����ݿ�����쳣����ѯʧ��", ex);
		}
		finally
		{
			DB.close(rs);
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("�õ���Ƶҵ���ƷID�б�,����");
		}
		return serviceIDMap;
	}
	
	/**
	 * �õ���Ƶ�Ʒ����ݼƷѱ����б�
	 * 
	 * @return
	 */
	public Map<String,String> getFEEcodeMap(){
		if (logger.isDebugEnabled())
		{
			logger.debug("�õ���Ƶ�Ʒ����ݼƷѱ����б�,��ʼ��");
		}
		Map<String, String> FEEcodeMap = new HashMap<String, String>();
		ResultSet rs = null;
		
		try
		{
			// select p.feecode from t_v_product p
			rs = DB.getInstance().queryBySQLCode(
					"com.aspire.dotcard.basevideosync.dao.BaseVideoDAO.getFEEcodeMap",
					null);
			while (rs.next())
			{
				String keyid = rs.getString("feecode");
				FEEcodeMap.put(keyid, "");
			}
		}
		catch (SQLException e)
		{
			logger.error("�õ���Ƶ�Ʒ����ݼƷѱ����б�ִ�����ݿ�SQLִ���쳣����ѯʧ��", e);
		}
		catch (DAOException ex)
		{
			logger.error("�õ���Ƶ�Ʒ����ݼƷѱ����б�ִ�����ݿ�����쳣����ѯʧ��", ex);
		}
		finally
		{
			DB.close(rs);
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("�õ���Ƶ�Ʒ����ݼƷѱ����б�,����");
		}
		return FEEcodeMap;
	}
	
	/**
	 * �õ���Ƶ��Ʒ�������Ʒ�ID�б�
	 * 
	 * @return
	 */
	public Map<String,String> getPkgSalesIDMap(){
		if (logger.isDebugEnabled())
		{
			logger.debug("�õ���Ƶ��Ʒ�������Ʒ�ID�б�,��ʼ��");
		}
		Map<String, String> PkgSalesIDMap = new HashMap<String, String>();
		ResultSet rs = null;
		
		try
		{
			// select p.prdpack_id,p.salesproductid from t_v_pkgsales p
			rs = DB.getInstance().queryBySQLCode(
					"com.aspire.dotcard.basevideosync.dao.BaseVideoDAO.getPkgSalesIDMap",
					null);
			while (rs.next())
			{
				String keyid = rs.getString("prdpack_id")+"|"+rs.getString("salesproductid");
				PkgSalesIDMap.put(keyid, "");
			}
		}
		catch (SQLException e)
		{
			logger.error("�õ���Ƶ��Ʒ�������Ʒ�ID�б�ִ�����ݿ�SQLִ���쳣����ѯʧ��", e);
		}
		catch (DAOException ex)
		{
			logger.error("�õ���Ƶ��Ʒ�������Ʒ�ID�б�ִ�����ݿ�����쳣����ѯʧ��", ex);
		}
		finally
		{
			DB.close(rs);
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("�õ���Ƶ��Ʒ�������Ʒ�ID�б�,����");
		}
		return PkgSalesIDMap;
	}
	
	/**
	 * ����ɾ��ָ��������
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
			logger.error("ɾ��������ʧ��:", e);
			throw new BOException("ɾ��������:" + PublicUtil.GetCallStack(e)
					+ "<br>");
		}
		
		return ret;
	}
	
	/**
	 * ɾ����ǰͬ��������
	 * 
	 * @param tableName
	 * @throws BOException
	 */
	public void truncateTable(String[] tableName)
			throws BOException
	{
		
		if (logger.isDebugEnabled())
		{
			logger.debug("truncateTable ɾ����ǰͬ��������,��ʼ");
		}
		
		if(null == tableName)
			return;
		
		try
		{
			String[] truncateSqls = new String[tableName.length];
			String truncateSql = " truncate table ";
			for(int i=0; i < tableName.length ; i++){
				truncateSqls[i] = truncateSql + tableName[i];
			}
			DB.getInstance().executeMutiFaild(truncateSqls, null);
		}
		catch (DAOException e)
		{
			logger.error("truncateTable,ɾ����ǰͬ��������ʧ��:", e);
			throw new BOException("ɾ����ǰͬ��������:" + PublicUtil.GetCallStack(e)
					+ "<br>");
		}
	}
	
	/**
	 * ���ڱ�������ִ��ʱ��
	 * 
	 * @param date
	 * @return
	 * @throws BOException
	 */
	public void saveLastTime(String date) throws BOException
	{
		try
		{
			//insert into t_v_lasttime(id,lupdate) values(SEQ_T_V_LASTTIME_ID.nextval,to_date(?,'yyyy-MM-dd hh24:mi:ss'))
			DB.getInstance().executeBySQLCode(
					"com.aspire.dotcard.basevideosync.dao.BaseVideoDAO.saveLastTime.insert", new Object[]{date});
		}
		catch (DAOException e)
		{
			logger.error("���ڱ�������ִ��ʱ��ʧ��:", e);
			throw new BOException("���ڱ�������ִ��ʱ��:" + PublicUtil.GetCallStack(e)
					+ "<br>");
		}
	}
	
	/**
	 * ���ڻ�ȡ�������һ��ִ��ʱ��
	 * 
	 * return 
	 */
	public String getLastUpdateTime(){
		
		if (logger.isDebugEnabled())
		{
			logger.debug("���ڻ�ȡ�������һ��ִ��ʱ��,��ʼ��");
		}
		String lastUpTime = null;
		ResultSet rs = null;
		
		try
		{
			// select to_char(max(l.lupdate-1/24),'yyyy-MM-dd HH24:mi:ss') lupdate from t_v_lasttime l
			rs = DB.getInstance().queryBySQLCode(
					"com.aspire.dotcard.basevideosync.dao.BaseVideoDAO.getLastUpdateTime",
					null);
			if (rs.next())
			{
				lastUpTime =rs.getString("lupdate");
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
			logger.debug("���ڻ�ȡ�������һ��ִ��ʱ��,����");
		}
		return lastUpTime;
	}
	
	/**
	 * �õ���Ƶ��Ŀ���������ѷ�����δ����Ľ�Ŀ�б�
	 * 
	 * @return
	 */
	public List<String> getProgramIDList()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("�õ���Ƶ��Ŀ���������ѷ�����δ����Ľ�Ŀ�б�,��ʼ��");
		}
		List<String> programIDList = new ArrayList<String>();
		ResultSet rs = null;
		
		try
		{
			// select p.programid,p.cmsid from t_v_sprogram p where p.status ='12' and p.exestatus = '0'
			rs = DB.getInstance().queryBySQLCode(
					"com.aspire.dotcard.basevideosync.dao.BaseVideoDAO.getProgramIDList",
					null);
			while (rs.next())
			{
				String keyid = rs.getString("programid") + "|" + rs.getString("cmsid");
				programIDList.add(keyid);
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
			logger.debug("�õ���Ƶ��Ŀ���������ѷ�����δ����Ľ�Ŀ�б�,����");
		}
		return programIDList;
	}
	
	/**
	 * �õ���Ƶ��Ŀ������ĿID�б�
	 * 
	 * @return
	 */
	public Map<String, String> getdProgramIDMap()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("�õ���Ƶ��Ŀ������ĿID�б�,��ʼ��");
		}
		Map<String, String> programIDMap = new HashMap<String, String>();
		ResultSet rs = null;
		
		try
		{
			// select p.programid from t_v_dprogram p 
			rs = DB.getInstance().queryBySQLCode(
					"com.aspire.dotcard.basevideosync.dao.BaseVideoDAO.getdProgramIDMap",
					null);
			while (rs.next())
			{
				String keyid = rs.getString("programid");
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
			logger.debug("�õ���Ƶ��Ŀ������ĿID�б�,����");
		}
		return programIDMap;
	}
	
	/**
	 * ��ǩ��ID���ǩ������key-value�б�
	 * 
	 * @return
	 */
	public Map<String, String> getTagGroupIDMap()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("��ǩ��ID���ǩ������key-value�б�,��ʼ��");
		}
		Map<String, String> tagGroupIDMap = new HashMap<String, String>();
		ResultSet rs = null;
		
		try
		{
			// select t.taggruid,t.taggroup from t_v_type t 
			rs = DB.getInstance().queryBySQLCode(
					"com.aspire.dotcard.basevideosync.dao.BaseVideoDAO.getTagGroupIDMap",
					null);
			while (rs.next())
			{
				tagGroupIDMap.put(rs.getString("taggruid"), rs.getString("taggroup"));
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
			logger.debug("��ǩ��ID���ǩ������key-value�б�,����");
		}
		return tagGroupIDMap;
	}
	
	/**
	 * �ȵ����ݵ�λ��Id�б�
	 * 
	 * @return
	 */
	public Map<String, String> getHotcontentIDMap()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("�ȵ����ݵ�λ��Id�б�,��ʼ��");
		}
		Map<String, String> titleCategoryIDMap = new HashMap<String, String>();
		ResultSet rs = null;
		
		try
		{
			// select locationId from t_v_hotcontent_location
			rs = DB.getInstance().queryBySQLCode(
					"com.aspire.dotcard.basevideosync.dao.BaseVideoDAO.getHotcontentIDMap",
					null);
			while (rs.next())
			{
				titleCategoryIDMap.put(rs.getString("locationId"), "");
			}
		}
		catch (SQLException e)
		{
			logger.error("�ȵ����ݵ�λ��Id�б�ִ�����ݿ�SQLִ���쳣����ѯʧ��", e);
		}
		catch (DAOException ex)
		{
			logger.error("�ȵ����ݵ�λ��Id�б�ִ�����ݿ�����쳣����ѯʧ��", ex);
		}
		finally
		{
			DB.close(rs);
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("�ȵ����ݵ�λ��Id�б�,����");
		}
		return titleCategoryIDMap;
	}
	
	/**
	 * ���ô洢���� ����ִ���м������ʽ��������ת��
	 */
	public int callSyncMidTableData()
	{
		CallableStatement cs = null;
		
		try
		{
			Connection conn = DB.getInstance().getConnection();
			
			cs = conn.prepareCall("{call p_v_pdetail_delete_insert()}");
			cs.execute();
			return 1;
		}
		catch (Exception e)
		{
			logger.error("���ô洢���� ����ִ���м������ʽ��������ת��ʱ����������", e);
			return 0;
		}
	}
	
	/**
	 * ���ô洢����  ����ִ�н�Ŀ��Ʒ�ϼܸ��²���
	 */
	public int callUpdateCategoryReference()
	{
		CallableStatement cs = null;
		
		try
		{
			Connection conn = DB.getInstance().getConnection();
			
			cs = conn.prepareCall("{call p_v_program_update()}");
			cs.execute();
			return 1;
		}
		catch (Exception e)
		{
			logger.error("���ô洢���� ����ִ�н�Ŀ��Ʒ�ϼܸ��²���ʱ����������", e);
			return 0;
		}
	}
	
	/**
	 * ���ô洢����  ����ִ���ȵ�������ܸ��²���
	 */
	public int callUpdateHotcontentCategoryMap()
	{
		CallableStatement cs = null;
		
		try
		{
			Connection conn = DB.getInstance().getConnection();
			
			cs = conn.prepareCall("{call p_v_hotcatemap_update()}");
			cs.execute();
			return 1;
		}
		catch (Exception e)
		{
			logger.error("���ô洢���� ����ִ���ȵ�������ܸ��²���ʱ����������", e);
			return 0;
		}
	}
	
	/**
	 * ��ȡ��ĿID��Ӧ����ID�ͽ�Ŀ���Ƶ�key-value�б�
	 * 
	 * @return
	 */
	public Map<String, String[]> getProgramIDAndNameMap()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("��ȡ��ĿID��Ӧ����ID�ͽ�Ŀ���Ƶ�key-value�б�,��ʼ��");
		}
		Map<String, String[]> programIDAndNameMap = new HashMap<String, String[]>();
		ResultSet rs = null;
		
		try
		{
			// select p.programid,p.cmsid,p.name from t_v_dprogram p 
			rs = DB.getInstance().queryBySQLCode(
					"com.aspire.dotcard.basevideosync.dao.BaseVideoDAO.getProgramIDAndNameMap",
					null);
			while (rs.next())
			{
				programIDAndNameMap.put(rs.getString("programid"), new String[]{rs.getString("cmsid"),rs.getString("name")});
			}
		}
		catch (SQLException e)
		{
			logger.error("��ȡ��ĿID��Ӧ����ID�ͽ�Ŀ���Ƶ�key-value�б�ִ�����ݿ�SQLִ���쳣����ѯʧ��", e);
		}
		catch (DAOException ex)
		{
			logger.error("��ȡ��ĿID��Ӧ����ID�ͽ�Ŀ���Ƶ�key-value�б�ִ�����ݿ�����쳣����ѯʧ��", ex);
		}
		finally
		{
			DB.close(rs);
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("��ȡ��ĿID��Ӧ����ID�ͽ�Ŀ���Ƶ�key-value�б�,����");
		}
		return programIDAndNameMap;
	}
	
	/**
	 * ���ݽ�ĿID���½�Ŀ������Ĵ���״̬Ϊ�Ѵ���
	 * 
	 * @return
	 */
	public void updateSProgramExestatus(String programId,String cmsID,String stauts) {
		if (logger.isDebugEnabled())
		{
			logger.debug("���ݽ�ĿID���½�Ŀ������Ĵ���״̬Ϊ�Ѵ���,��ʼ");
		}
		//update t_v_sprogram p set p.exestatus = ?  where p.programid = ? and p.cmsid = ?
		String sqlCode = "com.aspire.dotcard.basevideosync.dao.BaseVideoDAO.updateSProgramExestatus.update";
		try
		{
			DB.getInstance().executeBySQLCode(sqlCode, new String[]{stauts,programId,cmsID});
		}
		catch (DAOException e)
		{
			logger.error("���ݽ�ĿID���½�Ŀ������Ĵ���״̬Ϊ�Ѵ���ʧ��:", e);
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("���ݽ�ĿID���½�Ŀ������Ĵ���״̬Ϊ�Ѵ���,����");
		}
	}
	
	/**
	 * �õ���Ƶֱ����ĿID�б�
	 * 
	 * @return
	 */
	public List<String> getLiveIDList()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("�õ���Ƶֱ����ĿID�б�,��ʼ��");
		}
		List<String> liveIDList = new ArrayList<String>();
		ResultSet rs = null;
		
		try
		{
			// select p.programid,p.cmsid from t_v_dprogram p where p.LIVESTATUS='0' and p.TYPE in (12,9,8)
			rs = DB.getInstance().queryBySQLCode(
					"com.aspire.dotcard.basevideosync.dao.BaseVideoDAO.getLiveIDList",
					null);
			while (rs.next())
			{
				String keyid = rs.getString("programid")+"|"+rs.getString("cmsid");
				liveIDList.add(keyid);
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
			logger.debug("�õ���Ƶֱ����ĿID�б�,����");
		}
		return liveIDList;
	}
	
	/**
	 * �õ��ȵ�����ID�б�
	 * 
	 * @return
	 */
	public List<String> getHotcontentIDList()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("�õ��ȵ�����ID�б�,��ʼ��");
		}
		List<String> hotcontentList = new ArrayList<String>();
		ResultSet rs = null;
		
		try
		{
			// select locationId from t_v_hotcontent_location
			rs = DB.getInstance().queryBySQLCode(
					"com.aspire.dotcard.basevideosync.dao.BaseVideoDAO.getHotcontentIDList",
					null);
			while (rs.next())
			{
				String keyid = rs.getString("locationId");
				hotcontentList.add(keyid);
			}
		}
		catch (SQLException e)
		{
			logger.error("�õ��ȵ�����ID�б�ִ�����ݿ�SQLִ���쳣����ѯʧ��", e);
		}
		catch (DAOException ex)
		{
			logger.error("�õ��ȵ�����ID�б�ִ�����ݿ�����쳣����ѯʧ��", ex);
		}
		finally
		{
			DB.close(rs);
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("�õ��ȵ�����ID�б�,����");
		}
		return hotcontentList;
	}
	
	
	/**
	 * �õ���Ƶ�񵥱�����ƺͽ�Ŀid�б�
	 * 
	 * @return
	 */
	public Map<String, String> getBankNameAndProgramIDMap()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("�õ���Ƶ�񵥱�����ƺͽ�Ŀid�б�,��ʼ��");
		}
		Map<String, String> bankNameAndProgramIDMap = new HashMap<String, String>();
		ResultSet rs = null;
		
		try
		{
			// select p.platform,p.programid from T_V_LIST_PUBLISH p
			rs = DB.getInstance().queryBySQLCode(
					"com.aspire.dotcard.basevideosync.dao.BaseVideoDAO.getBankNameAndProgramIDMap",
					null);
			while (rs.next())
			{
				bankNameAndProgramIDMap.put(rs.getString("programid")+"|"+rs.getString("platform"), "");
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
			logger.debug("�õ���Ƶ�񵥱�����ƺͽ�Ŀid�б�,����");
		}
		return bankNameAndProgramIDMap;
	}
	
	
	/**
	 * �õ�api�������
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getApiRequestParamter(String str)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("�õ�api�������,��ʼ��");
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		ResultSet rs = null;
		
		try
		{
			// select content,contentType from t_v_apiRequestParamter where content=?
			rs = DB.getInstance().queryBySQLCode(
					"com.aspire.dotcard.basevideosync.dao.BaseVideoDAO.getApiRequestParamter",
					new Object[]{str});
			while (rs.next())
			{
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("content", rs.getString("content"));
				map.put("contentType", rs.getString("contentType"));
				list.add(map);
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
			logger.debug("�õ�api�������,����");
		}
		return list;
	}
}
