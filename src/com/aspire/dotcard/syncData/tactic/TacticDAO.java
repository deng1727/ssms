package com.aspire.dotcard.syncData.tactic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.gcontent.GContent;
import com.aspire.ponaadmin.web.channeladmin.vo.OpenChannelsCategoryVo;

/**
 * CMS����ͬ������DAO
 * 
 * @author x_liyouli
 * 
 */
public class TacticDAO {

	/**
	 * ��־����
	 */
	JLogger logger = LoggerFactory.getLogger(TacticDAO.class);

	/**
	 * ���һ��ͬ������
	 * 
	 * @param vo
	 */
	public void addTactic(TacticVO vo) throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("TacticDAO.addTactic()");
		}

		String sqlCode = "com.aspire.dotcard.syncData.tactic.TacticDAO.addTactic.INSERT";
		Object[] paras = new Object[6];
		paras[0] = vo.getCategoryID();
		paras[1] = vo.getContentType();
		paras[2] = vo.getUmFlag();
		paras[3] = vo.getContentTag();
		paras[4] = new Integer(vo.getTagRelation());
		paras[5] = vo.getAppCateName();

		try {
			DB.getInstance().executeBySQLCode(sqlCode, paras);
		} catch (Exception e) {
			throw new DAOException("TacticDAO.addTactic() error.", e);
		}
	}

	/**
	 * ��ѯ����ͬ������
	 * 
	 * @param id
	 * @return
	 */
	public TacticVO queryByID(int id) throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("TacticDAO.queryByID()");
		}
		TacticVO vo = null;
		ResultSet rs = null;
		String sqlCode = "com.aspire.dotcard.syncData.tactic.TacticDAO.queryByID.SELECT";
		Object[] paras = { new Integer(id) };

		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
			if (rs.next()) {
				vo = new TacticVO();
				setVOFromRS(vo, rs);
			}
		} catch (Exception e) {
			throw new DAOException("TacticDAO.queryByID() error.", e);
		} finally {
			DB.close(rs);
		}
		return vo;
	}

	/**
	 * ��ѯһ�����������е�ͬ�����ԣ�����Ҫ��ҳ��������޸�ʱ�併������
	 * 
	 * @return
	 */
	public List queryByCategoryID(String categoryID) throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("TacticDAO.queryByCategoryID()");
		}
		List tacticList = new ArrayList();
		ResultSet rs = null;
		String sqlCode = "com.aspire.dotcard.syncData.tactic.TacticDAO.queryByCategoryID.SELECT";
		Object[] paras = { categoryID };
		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
			while (rs.next()) {
				TacticVO vo = new TacticVO();
				setVOFromRS(vo, rs);
				tacticList.add(vo);
			}
		} catch (Exception e) {
			throw new DAOException("TacticDAO.queryByCategoryID() error.", e);
		} finally {
			DB.close(rs);
		}
		return tacticList;
	}

	/**
	 * ��ѯ���е�ͬ������
	 * 
	 * @return
	 */
	public List queryAll() throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("TacticDAO.queryAll()");
		}
		List tacticList = new ArrayList();
		ResultSet rs = null;
		String sqlCode = "com.aspire.dotcard.syncData.tactic.TacticDAO.queryAll.SELECT";

		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode, null);
			while (rs.next()) {
				TacticVO vo = new TacticVO();
				setVOFromRS(vo, rs);
				tacticList.add(vo);
			}
		} catch (Exception e) {
			throw new DAOException("TacticDAO.queryAll() error.", e);
		} finally {
			DB.close(rs);
		}
		return tacticList;
	}

	/**
	 * ��ѯ���е�ͬ������
	 * 
	 * @return
	 */
	public List queryMOTOAll() throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("TacticDAO.queryAll()");
		}
		List tacticList = new ArrayList();
		ResultSet rs = null;
		String sqlCode = "com.aspire.dotcard.syncData.tactic.TacticDAO.queryMOTOAll.SELECT";

		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode, null);
			while (rs.next()) {
				TacticVO vo = new TacticVO();
				setVOFromRS(vo, rs);
				tacticList.add(vo);
			}
		} catch (Exception e) {
			throw new DAOException("TacticDAO.queryMOTOAll() error.", e);
		} finally {
			DB.close(rs);
		}
		return tacticList;
	}

	/**
	 * ��ѯ���е�HTCͬ������
	 * 
	 * @return
	 */
	public List queryHTCAll() throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("TacticDAO.queryHTCAll()");
		}
		List tacticList = new ArrayList();
		ResultSet rs = null;
		String sqlCode = "com.aspire.dotcard.syncData.tactic.TacticDAO.queryHTCAll.SELECT";

		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode, null);
			while (rs.next()) {
				TacticVO vo = new TacticVO();
				setVOFromRS(vo, rs);
				tacticList.add(vo);
			}
		} catch (Exception e) {
			throw new DAOException("TacticDAO.queryHTCAll() error.", e);
		} finally {
			DB.close(rs);
		}
		return tacticList;
	}

	/**
	 * ��ѯ���㷺�����������̶�Ӧ�������б�
	 * 
	 * @return
	 */
	public List queryChannelsCategoryAll() throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("TacticDAO.queryChannelsCategoryAll()");
		}
		List tacticList = new ArrayList();
		ResultSet rs = null;
		String sqlCode = "com.aspire.dotcard.syncData.tactic.TacticDAO.queryChannelsCategoryAll.SELECT";

		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode, null);
			while (rs.next()) {
				OpenChannelsCategoryVo vo = new OpenChannelsCategoryVo();
				vo.setCategoryId(rs.getString("categoryId"));
				vo.setChannelsId(rs.getString("channelsId"));
				tacticList.add(vo);
			}
		} catch (Exception e) {
			throw new DAOException(
					"TacticDAO.queryChannelsCategoryAll() error.", e);
		} finally {
			DB.close(rs);
		}
		return tacticList;
	}

	// androidʵʱ���ܵĲ���
	public List queryAndroidAll() throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("TacticDAO.queryAndroidAll()");
		}
		List tacticList = new ArrayList();
		ResultSet rs = null;
		String sqlCode = "com.aspire.dotcard.syncData.tactic.TacticDAO.queryAndroidAll.SELECT";

		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode, null);
			while (rs.next()) {
				TacticVO vo = new TacticVO();
				setVOFromRS(vo, rs);
				tacticList.add(vo);
			}
		} catch (Exception e) {
			throw new DAOException("TacticDAO.queryAndroidAll() error.", e);
		} finally {
			DB.close(rs);
		}
		return tacticList;
	}

	/**
	 * �޸�һ��ͬ������
	 * 
	 * @param vo
	 */
	public void modifyTactic(TacticVO vo) throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("TacticDAO.modifyTactic()");
		}

		String sqlCode = "com.aspire.dotcard.syncData.tactic.TacticDAO.modifyTactic.UPDATE";
		Object[] paras = new Object[6];
		paras[0] = vo.getContentType();
		paras[1] = vo.getUmFlag();
		paras[2] = vo.getContentTag();
		paras[3] = new Integer(vo.getTagRelation());
		paras[4] = vo.getAppCateName();
		paras[5] = new Integer(vo.getId());

		try {
			DB.getInstance().executeBySQLCode(sqlCode, paras);
		} catch (Exception e) {
			throw new DAOException("TacticDAO.modifyTactic() error.", e);
		}
	}

	/**
	 * ɾ��һ��ͬ������
	 * 
	 * @param id
	 */
	public void delTactic(int id) throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("TacticDAO.delTactic()");
		}

		String sqlCode = "com.aspire.dotcard.syncData.tactic.TacticDAO.delTactic.DELETE";
		Object[] paras = { new Integer(id) };
		try {
			DB.getInstance().executeBySQLCode(sqlCode, paras);
		} catch (Exception e) {
			throw new DAOException("TacticDAO.delTactic() error.", e);
		}
	}

	/**
	 * ��ResultSet���ݸ�ֵ��VO������
	 * 
	 * @param vo
	 * @param rs
	 * @throws SQLException
	 */
	private void setVOFromRS(TacticVO vo, ResultSet rs) throws SQLException {
		vo.setId(rs.getInt("id"));
		vo.setCategoryID(rs.getString("categoryID"));
		vo.setContentType(rs.getString("contentType"));
		vo.setUmFlag(rs.getString("umFlag"));
		vo.setContentTag(rs.getString("contentTag"));
		vo.setTagRelation(rs.getInt("tagRelation"));
		vo.setAppCateName(rs.getString("appCateName"));
		vo.setCreatTime(rs.getString("crateTime"));
		vo.setLastUpdateTime(rs.getString("lastUpdateTime"));
	}

	/**
	 * ����ͬ�����Ի���ϼ���Ʒ�б�
	 * 
	 * @param tacticList
	 * @return
	 * @throws DAOException
	 */
	public List categoryTactic(List tacticList) throws DAOException {

		if (logger.isDebugEnabled()) {
			logger.debug("TacticDAO.categoryTactic()");
		}

		// select t.id,t.fulldeviceid from t_r_gcontent t, t_r_base b where t.id
		// = b.id
		String sql = DB
				.getInstance()
				.getSQLByCode(
						"com.aspire.dotcard.syncData.tactic.TacticDAO.categoryTactic.query");
		StringBuffer sqlCode = new StringBuffer(sql);
		ResultSet rs = null;
		List conList = new ArrayList();
		if (tacticList.size() > 0) {
			sqlCode.append(" and (");
			for (Iterator iter = tacticList.iterator(); iter.hasNext();) {
				TacticVO tactic = (TacticVO) iter.next();
				StringBuffer temp = new StringBuffer("");

				// ������Ͳ�Ϊall�Ҳ�Ϊ��
				if (!TacticConstants.CONTENT_TYPE_ALL.equals(tactic
						.getContentType())) {
					temp.append("b.type = '" + tactic.getContentType() + "'");
				}

				// ��������
				if (null != tactic.getAppCateName()) {
					// ���ǰ��������
					if (temp.length() > 1) {
						temp.append(" and ");
					}

					temp.append("t.appcatename like '%"
							+ tactic.getAppCateName() + "%'");
				}

				if (temp.length() > 1) {
					// ����������
					sqlCode.append("(").append(temp).append(") or  ");
				}
			}
			sqlCode.append(" 1=2 )");
		}

		try {
			rs = DB.getInstance().query(sqlCode.toString(), new Object[] {});
			while (rs.next()) {
				GContent content = new GContent();
				content.setId(rs.getString("id"));
				content.setFulldeviceID(DB.getClobValue(rs, "fulldeviceId"));
				conList.add(content);
			}
		} catch (Exception e) {
			throw new DAOException("TacticDAO.delTactic() error.", e);
		} finally {
			DB.close(rs);
		}

		return conList;
	}

	public String getContentName(String contentid) {
		String name = "";
		ResultSet rs = null;
		try {
			String sqlCode ="com.aspire.dotcard.syncData.tactic.TacticDAO.getContentName().select";
			rs = DB.getInstance()
					.queryBySQLCode(sqlCode, new Object[] { contentid });
			if (rs.next()) {
				name = rs.getString("name");
			}
		} catch (Exception e) {
			logger.error("��ѯ���Ƴ���",e);
		}finally{
			DB.close(rs);
		}
		return name;

	}
}
