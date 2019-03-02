/*
 * �ļ�����ContentExigenceDAO.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
 */

package com.aspire.ponaadmin.web.repository.web;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.syncData.vo.ContentTmp;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.page.PageVOInterface;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * 
 * @author wangminlong
 * @version
 */
public class ContentExigenceDAO
{
	/**
	 * ��¼��־��ʵ������
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(ContentExigenceDAO.class);
	
	/**
	 * singletonģʽ��ʵ��
	 */
	private static ContentExigenceDAO instance = new ContentExigenceDAO();
	
	/**
	 * ���췽������singletonģʽ����
	 */
	private ContentExigenceDAO()
	{}
	
	/**
	 * ��ȡʵ��
	 * 
	 * @return ʵ��
	 */
	public static ContentExigenceDAO getInstance()
	{
		return instance;
	}
	
	/**
	 * Ӧ�����ҳ��ȡVO��ʵ����
	 */
	private class ContentExigencePageVO implements PageVOInterface
	{
		public void CopyValFromResultSet(Object content, ResultSet rs)
				throws SQLException
		{
			ContentExigenceVO vo = (ContentExigenceVO) content;
			vo.setContentId(rs.getString("contentId"));
			vo.setSysdate(String.valueOf(rs.getDate("dateTime")));
			vo.setType(getTypeName(rs.getInt("type")));
			vo.setSubType((getSubTypeName(Integer.parseInt(rs
					.getString("subtype")))));
		}
		
		/**
		 * ����ͬ������ת����Ϣ
		 * 
		 * @param type
		 * @return
		 */
		private String getTypeName(int type)
		{
			String typeName = "";
			
			switch (type)
			{
				case 0:
					typeName = "��ͬ��";
					break;
				case 1:
					typeName = "����";
					break;
				case 2:
					typeName = "����";
					break;
				case 3:
					typeName = "����";
					break;
				case 4:
					typeName = "ͬ��ʧ��";
					break;
			}
			
			return typeName;
		}
		
		/**
		 * ������������ת����Ϣ
		 * 
		 * @param subType
		 * @return
		 */
		private String getSubTypeName(int subType)
		{
			String typeName = "";
			
			switch (subType)
			{
				case 1:
					typeName = "mm��ͨӦ��";
					break;
				case 2:
					typeName = "widgetӦ��";
					break;
				case 3:
					typeName = "ZCOMӦ��";
					break;
				case 4:
					typeName = "FMMӦ��";
					break;
				case 5:
					typeName = "jilӦ��";
					break;
				case 6:
					typeName = "MM����Ӧ��";
					break;
				case 7:
					typeName = "����Ӧ��";
					break;
				case 8:
					typeName = "��������Ӧ��";
					break;
				case 9:
					typeName = "���MM";
					break;
				case 10:
					typeName = "OVIӦ��";
					break;
				case 11:
					typeName = "�ײ�Ӧ��";
					break;
				default:
					typeName = String.valueOf(subType);
					break;
			}
			return typeName;
		}
		
		public Object createObject()
		{
			return new ContentExigenceVO();
		}
	}
	
	/**
	 * ��ѯ����Ľ�Ҫ�������ߵ������б�
	 * 
	 * @param page
	 * @throws DAOException
	 */
	public void queryContentExigenceList(PageResult page) throws DAOException
	{
		
		if (logger.isDebugEnabled())
		{
			logger.debug("queryContentExigenceList() is starting ...");
		}
		
		// select * from t_exigencecontent t
		String sqlCode = "exigence.ContentExigenceDAO.queryContentExigenceList().SELECT";
		
		try
		{
			page.excuteBySQLCode(sqlCode, null, new ContentExigencePageVO());
		}
		catch (DAOException e)
		{
			throw new DAOException("��ѯ����Ľ�Ҫ�������ߵ������б�ʱ�������ݿ��쳣��", e);
		}
	}
	
	/**
	 * ɾ���������ߵ������б�
	 * 
	 * @param ids
	 * @throws DAOException
	 */
	public void delContentExigence(String[] ids) throws DAOException
	{
		
		if (logger.isDebugEnabled())
		{
			logger.debug("delContentExigence() is starting ...");
		}
		
		// delete from t_exigencecontent e where e.contentId = ?
		String sql = "exigence.ContentExigenceDAO.delContentExigence().del";
		String sqlCode[] = new String[ids.length];;
		Object[][] object = new Object[ids.length][1];
		
		for (int i = 0; i < ids.length; i++)
		{
			sqlCode[i] = sql;
			object[i][0] = ids[i];
		}
		
		try
		{
			DB.getInstance().executeMutiBySQLCode(sqlCode, object);
		}
		catch (DAOException e)
		{
			throw new DAOException("��ѯ����Ľ�Ҫ�������ߵ������б�ʱ�������ݿ��쳣��", e);
		}
	}
	
	/**
	 * ���ԭ�б�
	 * 
	 * @throws DAOException
	 */
	public void delAllContentExigence() throws DAOException
	{
		
		if (logger.isDebugEnabled())
		{
			logger.debug("delAllContentExigence() is starting ...");
		}
		
		// delete from t_exigencecontent e
		String sql = "exigence.ContentExigenceDAO.delAllContentExigence().del";
		
		try
		{
			DB.getInstance().executeBySQLCode(sql, null);
		}
		catch (DAOException e)
		{
			throw new DAOException("��ѯ����Ľ�Ҫ�������ߵ������б�ʱ�������ݿ��쳣��", e);
		}
	}
	
	/**
	 * ��д�û�������е�����ͬ������
	 * 
	 * @param contentList
	 *            ����id��
	 * @param type
	 *            ��������
	 * 
	 * @throws DAOException
	 */
	public void updateContentExigenceType(List contentList, String type)
			throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("updateContentExigenceType() is starting ...");
		}
		
		// update t_exigencecontent e set e.type = ? where e.contentid = ?
		String sql = "exigence.ContentExigenceDAO.updateContentExigenceType().update";
		String sqlCode[] = new String[contentList.size()];
		Object[][] object = new Object[contentList.size()][2];
		
		for (int i = 0; i < contentList.size(); i++)
		{
			sqlCode[i] = sql;
			object[i][0] = type;
			ContentTmp temp = (ContentTmp) contentList.get(i);
			object[i][1] = temp.getContentId();
		}
		
		try
		{
			DB.getInstance().executeMutiBySQLCode(sqlCode, object);
		}
		catch (DAOException e)
		{
			throw new DAOException("��ѯ����Ľ�Ҫ�������ߵ������б�ʱ�������ݿ��쳣��", e);
		}
		
	}
	
	/**
	 * У��ļ��Д����Ƿ���ҕ�D�д���
	 * 
	 * @param list
	 * @throws DAOException
	 */
	public String verifyContentExigence(List list) throws DAOException
	{
		
		if (logger.isDebugEnabled())
		{
			logger.debug("delAllContentExigence() is starting ...");
		}
		
		// select 1 from ppms_v_cm_content c where c.contentid = ?
		String sql = "exigence.ContentExigenceDAO.verifyContentExigence().select";
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		
		// ������ԃ
		for (int i = 0; i < list.size(); i++)
		{
			String temp = (String) list.get(i);
			try
			{
				rs = DB.getInstance().queryBySQLCode(sql.toString(),
						new Object[] { temp });
				// �����������������
				if (!rs.next())
				{
					list.remove(i);
					i--;
					sb.append(temp).append(". ");
				}
			}
			catch (SQLException e)
			{
				throw new DAOException("�鿴ָ���������Ƿ����ָ������ʱ�����쳣:", e);
			}
			finally
			{
				DB.close(rs);
			}
		}
		
		return sb.toString();
	}
	
	/**
	 * ����������ߵ������б�
	 * 
	 * @param list
	 * @throws BOException
	 */
	public void importContentExigence(List list) throws DAOException
	{
		
		if (logger.isDebugEnabled())
		{
			logger.debug("importContentExigence() is starting ...");
		}
		
		// insert into t_exigencecontent (contentid, dateTime, subtype) select
		// c.contentid, sysdate, c.servtype from ppms_v_service c where
		// c.contentid = ?
		String sql = "exigence.ContentExigenceDAO.importContentExigence().add";
		String sqlCode[] = new String[list.size()];
		Object[][] object = new Object[list.size()][1];
		
		for (int i = 0; i < list.size(); i++)
		{
			sqlCode[i] = sql;
			object[i][0] = list.get(i);
		}
		
		try
		{
			DB.getInstance().executeMutiBySQLCode(sqlCode, object);
		}
		catch (DAOException e)
		{
			throw new DAOException("��ѯ����Ľ�Ҫ�������ߵ������б�ʱ�������ݿ��쳣��", e);
		}
	}
	
	/**
	 * �õ��������ϴε���������������ݵ����ʱ��
	 * 
	 * @return
	 * @throws DAOException
	 */
	public String getSysContentExigenceDate() throws DAOException
	{
		
		if (logger.isDebugEnabled())
		{
			logger.debug("sysContentExigence() is starting ...");
		}
		
		// select decode(MAX(l.lasttime), '', '1999-01-01',
		// to_char(MAX(l.lasttime), 'YYYY-MM-DD HH24:MI:SS')) from
		// t_exigence_lasttime l
		String sql = "exigence.ContentExigenceDAO.getSysContentExigenceDate().sys";
		ResultSet rs = null;
		String date = "1999-01-01 00:00:00";
		
		try
		{
			rs = DB.getInstance().queryBySQLCode(sql, null);
			
			if (rs.next())
			{
				date = rs.getString(1);
			}
		}
		catch (DAOException e)
		{
			throw new DAOException("�õ��������ϴε���������������ݵ����ʱ��ʱ�������ݿ��쳣��", e);
		}
		catch (SQLException e)
		{
			throw new DAOException("�õ��������ϴε���������������ݵ����ʱ��ʱ�������ݿ��쳣��", e);
		}
		
		return date;
	}
	
	/**
	 * ���ڶ�ʱִ�н�������������ʱ��ȡ�õ������������������ݣ�������ʱ����
	 * 
	 * @throws DAOException
	 */
	public void sysContentExigence(String date) throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("sysContentExigence() is starting ...");
		}
		
		// insert into t_exigencecontent (contentid, dateTime, subtype) select
		// c.contentid, sysdate, c.thirdapptype from ppms_v_cm_content c,
		// PPMS_V_CM_CONTENT_UPGRADE u where c.contentid = u.contentid and
		// u.conlupddate > to_date(?,'YYYY-MM-DD HH24:MI:SS')
		String sql = "exigence.ContentExigenceDAO.sysContentExigence().sys";
		
		try
		{
			DB.getInstance().executeBySQLCode(sql, new Object[] { date });
		}
		catch (DAOException e)
		{
			throw new DAOException("ȡ�õ������������������ݣ�������ʱ����ʱ�������ݿ��쳣��", e);
		}
	}
	
	/**
	 * ���ڶ�ʱִ�н�������������ʱ��д�����һ��ִ��ϵͳ����������������ʱ��
	 * 
	 * @throws DAOException
	 */
	public void addExigenceLastTime() throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("addExigenceLastTime() is starting ...");
		}
		
		// insert into t_exigence_lasttime (lasttime) values (sysdate)
		String sql = "exigence.ContentExigenceDAO.addExigenceLastTime().add";
		
		try
		{
			DB.getInstance().executeBySQLCode(sql, null);
		}
		catch (DAOException e)
		{
			throw new DAOException(
					"���ڶ�ʱִ�н�������������ʱ��д�����һ��ִ��ϵͳ����������������ʱ��ʱ�������ݿ��쳣��", e);
		}
	}
	
	/**
	 * �жϵ�ǰ��ʱ�����Ƿ�������ݡ�
	 * 
	 * @throws DAOException
	 */
	public boolean hasExigenceContent() throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("hasExigenceContent() is starting ...");
		}
		
		// select 1 from t_exigencecontent t
		String sql = "exigence.ContentExigenceDAO.hasExigenceContent().select";
		
		ResultSet rs = null;
		
		try
		{
			rs = DB.getInstance().queryBySQLCode(sql.toString(), null);
			
			// ���������������
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
			throw new DAOException("�鿴ָ���������Ƿ����ָ������ʱ�����쳣:", e);
		}
		finally
		{
			DB.close(rs);
		}
	}
	
	/**
	 * ��ս��������߲�����ʷ��������Ϣ
	 * 
	 * @throws DAOException
	 */
	public void delGoodsChangeHis() throws DAOException
	{
		
		if (logger.isDebugEnabled())
		{
			logger.debug("delGoodsChangeHis() is starting ...");
		}
		
		// delete from t_goods_change_his
		String sql = "exigence.ContentExigenceDAO.delGoodsChangeHis().del";
		
		try
		{
			DB.getInstance().executeBySQLCode(sql, null);
		}
		catch (DAOException e)
		{
			throw new DAOException("��ѯ����Ľ�Ҫ�������ߵ������б�ʱ�������ݿ��쳣��", e);
		}
	}
	
	/**
	 * ���ڷ��ص�ǰ���д��ڴ�����״̬������
	 * 
	 * @return
	 * @throws DAOException
	 */
	public List<String> queryExigenceIdListByType() throws DAOException
	{
		
		if (logger.isDebugEnabled())
		{
			logger.debug("delAllContentExigence() is starting ...");
		}
		
		List<String> list = new ArrayList<String>();
		
		// select e.contentid from t_exigencecontent e where e.type = 0
		String sql = "exigence.ContentExigenceDAO.queryExigenceIdListByType().query";
		
		ResultSet rs = null;
		
		try
		{
			rs = DB.getInstance().queryBySQLCode(sql, null);
			
			while (rs.next())
			{
				list.add(rs.getString("contentid"));
			}
		}
		catch (SQLException e)
		{
			throw new DAOException("���ڷ��ص�ǰ���д��ڴ�����״̬������ʱ�������ݿ��쳣��", e);
		}
		finally
		{
			DB.close(rs);
		}
		
		return list;
	}
	
	/**
	 * ����������ǰ���v_service����������Ϣ
	 * 
	 * @throws DAOException
	 */
	public void updateServiceDate(List<String> contentIdList) throws DAOException
	{
		logger.info("updateServiceDate() is starting ...");
		
		// delete from v_service s where s.contentid in (select e.contentid from
		// t_exigencecontent e where e.type = 0)
		String sqlD = "exigence.ContentExigenceDAO.updateServiceDate().del";
		
		// insert into v_service select * from ppms_v_service s where s.contentid = ?
		String sql = "exigence.ContentExigenceDAO.updateServiceDate().add";
		
		String[] sqlO = new String[contentIdList.size()+1];
		Object[][] sqlV = new Object[contentIdList.size()+1][1];
		
		sqlO[0] = sqlD;
		sqlV[0] = null;
		
		for (int i = 0; i < contentIdList.size(); i++)
		{
			sqlO[i+1] = sql;
			sqlV[i+1][0] = contentIdList.get(i);
		}
		
		try
		{
			DB.getInstance().executeMutiBySQLCode(sqlO, sqlV);
		}
		catch (DAOException e)
		{
			throw new DAOException("��ѯ����Ľ�Ҫ�������ߵ������б�ʱ�������ݿ��쳣��", e);
		}

		logger.info("updateServiceDate() is end ...");
	}
	
	/**
	 * ����������ǰ����������ͼv_cm_content��������Ϣ
	 * 
	 * @throws DAOException
	 */
	public void updateVcontentDate(List<String> contentIdList) throws DAOException
	{
		
		logger.info("updateVcontentDate() is starting ...");
		
		// delete from v_cm_content s where s.contentid in (select e.contentid
		// from t_exigencecontent e where e.type = 0)
		String sqlD = "exigence.ContentExigenceDAO.updateVcontentDate().del";
		
		
		// insert into v_cm_content select * from PPMS_V_CM_CONTENT s where s.contentid = ?
		String sql = "exigence.ContentExigenceDAO.updateVcontentDate().add";
		
		String[] sqlO = new String[contentIdList.size()+1];
		Object[][] sqlV = new Object[contentIdList.size()+1][1];
		
		sqlO[0] = sqlD;
		sqlV[0] = null;
		
		for (int i = 0; i < contentIdList.size(); i++)
		{
			sqlO[i+1] = sql;
			sqlV[i+1][0] = contentIdList.get(i);
		}
		
		try
		{
			DB.getInstance().executeMutiBySQLCode(sqlO, sqlV);
		}
		catch (DAOException e)
		{
			throw new DAOException("����������ǰ����������ͼv_cm_content��������Ϣʱ�������ݿ��쳣��", e);
		}
		
		logger.info("updateVcontentDate() is end ...");
	}
	
	/**
	 * ����������ǰ���cm_ct_appgame��չ����������Ϣ
	 * 
	 * @throws DAOException
	 */
	public void updateCMCTAPPDate(List<String> contentIdList) throws DAOException
	{
		logger.info("updateCMCTAPPDate() is starting ...");
		
		// delete from cm_ct_appgame s where s.contentid in (select e.contentid
		// from
		// t_exigencecontent e where e.type = 0)
		String sql_game_d = "exigence.ContentExigenceDAO.updateCMCTAPPDate().delGame";
		
		// insert into cm_ct_appgame select * from s_cm_ct_appgame s where
		// s.contentid  = ?
		String sql_game_a = "exigence.ContentExigenceDAO.updateCMCTAPPDate().addGame";
		
		String[] sqlO = new String[contentIdList.size() + 1];
		Object[][] sqlV = new Object[contentIdList.size() + 1][1];
		
		sqlO[0] = sql_game_d;
		sqlV[0] = null;
		
		for (int i = 0; i < contentIdList.size(); i++)
		{
			sqlO[i + 1] = sql_game_a;
			sqlV[i + 1][0] = contentIdList.get(i);
		}
		
		try
		{
			DB.getInstance().executeMutiBySQLCode(sqlO, sqlV);
		}
		catch (DAOException e)
		{
			throw new DAOException("����������ǰ��������cm_ct_appgame��������Ϣʱ�������ݿ��쳣��", e);
		}
		
		logger.info("cm_ct_appgame syn end ...");
		
		// delete from cm_ct_appsoftware s where s.contentid in (select
		// e.contentid from
		// t_exigencecontent e where e.type = 0)
		String sql_soft_d = "exigence.ContentExigenceDAO.updateCMCTAPPDate().delSoftware";
		
		// insert into cm_ct_appsoftware select * from s_cm_ct_appsoftware s
		// where
		// s.contentid  = ?
		String sql_soft_a = "exigence.ContentExigenceDAO.updateCMCTAPPDate().addSoftware";
		
		sqlO = new String[contentIdList.size() + 1];
		sqlV = new Object[contentIdList.size() + 1][1];
		
		sqlO[0] = sql_soft_d;
		sqlV[0] = null;
		
		for (int i = 0; i < contentIdList.size(); i++)
		{
			sqlO[i + 1] = sql_soft_a;
			sqlV[i + 1][0] = contentIdList.get(i);
		}
		
		try
		{
			DB.getInstance().executeMutiBySQLCode(sqlO, sqlV);
		}
		catch (DAOException e)
		{
			throw new DAOException("����������ǰ��������cm_ct_appsoftware��������Ϣʱ�������ݿ��쳣��", e);
		}
		
		logger.info("cm_ct_appsoftware syn end ...");
		
		// delete from cm_ct_apptheme s where s.contentid in (select e.contentid
		// from
		// t_exigencecontent e where e.type = 0)
		String sql_app_d = "exigence.ContentExigenceDAO.updateCMCTAPPDate().delTheme";
		
		// insert into cm_ct_apptheme select * from s_cm_ct_apptheme s where
		// s.contentid = ?
		String sql_app_a = "exigence.ContentExigenceDAO.updateCMCTAPPDate().addTheme";
		
		sqlO = new String[contentIdList.size() + 1];
		sqlV = new Object[contentIdList.size() + 1][1];
		
		sqlO[0] = sql_app_d;
		sqlV[0] = null;
		
		for (int i = 0; i < contentIdList.size(); i++)
		{
			sqlO[i + 1] = sql_app_a;
			sqlV[i + 1][0] = contentIdList.get(i);
		}
		
		try
		{
			DB.getInstance().executeMutiBySQLCode(sqlO, sqlV);
		}
		catch (DAOException e)
		{
			throw new DAOException("����������ǰ��������cm_ct_apptheme��������Ϣʱ�������ݿ��쳣��", e);
		}
		
		logger.info("cm_ct_apptheme syn end ...");
		
		logger.info("updateCMCTAPPDate() is end ...");
	}
}
