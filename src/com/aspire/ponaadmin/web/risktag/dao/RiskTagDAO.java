package com.aspire.ponaadmin.web.risktag.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.db.SQLUtil;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.DataAccessException;
import com.aspire.common.persistence.util.SQLCode;
import com.aspire.common.util.StringUtils;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.page.PageVOInterface;
import com.aspire.ponaadmin.web.category.blacklist.dao.AndroidBlackListDAO;
import com.aspire.ponaadmin.web.channeladmin.vo.OpenChannelMoVo;
import com.aspire.ponaadmin.web.risktag.vo.RiskTagVO;

public class RiskTagDAO {
	/**
	 * ��־����
	 */
	protected static final JLogger logger = LoggerFactory
			.getLogger(RiskTagDAO.class);

	private static RiskTagDAO instance = new RiskTagDAO();

	private RiskTagDAO() {

	}

	public static RiskTagDAO getInstance() {
		return instance;
	}

	/**
	 * �������ڲ�ѯ�񵥺�����Ԫ�����б�
	 * 
	 * @param page
	 * @param vo
	 * @throws DAOException
	 */
	public void queryTagList(PageResult page, String riskid, String stats,
			String content, String contentid) throws DAOException {

		if (logger.isDebugEnabled()) {
			logger.debug("RiskTagDAO.queryTagList() is starting ...");
		}

		// select * from t_r_gcontent t left join t_r_blacklist r on
		// t.contentid=r.contentid
		// where t.risktag is not null and 1\=1
		String sqlCode = "tagmanager.dao.RiskTagDAO.queryTagList().SELECT";
		String sql = null;

		try {
			sql = SQLCode.getInstance().getSQLStatement(sqlCode);

			StringBuffer sqlBuffer = new StringBuffer(sql);
			List paras = new ArrayList();
			// ����������sql�Ͳ���
			if (!"".equals(contentid)) {
				// sql += " and contentid = ? ";
				sqlBuffer.append(" and t.contentid = ? ");
				paras.add(SQLUtil.escape(contentid));
			}
			// ���������
			if (!"".equals(content)) {
				// sql += " and contentid = ? ";
				sqlBuffer.append(" and t.name like ? ");
				paras.add("%" + SQLUtil.escape(content) + "%");
			}
			// ״̬:�Ƿ�����
			if (!"".equals(stats) && !("-1".equals(stats))) {
				// sql += " and contentid = ? ";
				sqlBuffer.append(" and r.isblack = ? ");
				paras.add(SQLUtil.escape(stats));
			}
			// ���ݷ��ձ�ǩ��1,2,3,4
			if (!"".equals(riskid)) {
				// sql += " and risktag like('%" + risktag
				// + "%')";
				sqlBuffer.append(" and t.risktag like  ? ");
				paras.add("%" + SQLUtil.escape(riskid) + "%");
			}
			
		
			// sql += " order by createdate desc ";
			sqlBuffer.append(" order by r.createtime asc ");

			page.excute(sqlBuffer.toString(), paras.toArray(),
					new RiskTagPageVO());
		} catch (DataAccessException e) {
			throw new DAOException(
					"��sql.properties�л�ȡsql���ʱ�������󣬶�ӦsqlCode�����ڡ�", e);
		}

	}

	private class RiskTagPageVO implements PageVOInterface {

		@Override
		public Object createObject() {
			return new RiskTagVO();
		}

		@Override
		public void CopyValFromResultSet(Object content, ResultSet rs)
				throws SQLException {
			RiskTagVO vo = (RiskTagVO) content;
			vo.setContentID(rs.getString("contentid"));
			vo.setName(rs.getString("name"));
			if ("".equals(rs.getString("isblack"))) {
				vo.setIsblack("0");
			} else {
				vo.setIsblack(rs.getString("isblack"));
			}
			vo.setCompany(rs.getString("spname"));
			vo.setType(rs.getString("catename"));
			vo.setTime(dateToString("yyyy-MM-dd", rs.getDate("createtime")));
		}
	}

	public int doCheckBlack(String contentid) throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("RiskTagDAO.doInsertBlack() is starting ...");
		}
		// �Ȳ�ѯ�Ƿ���ں�������
		// select * from t_r_blacklist where contentid = ?
		String sqlCode = "tagmanager.dao.RiskTagDAO.doInsertBlack().SELECT";
		String[] paras = null;
		ResultSet rs = null;
		ArrayList<String> lists = new ArrayList<String>();
		try {
			paras = new String[] { contentid };
			rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
			while (rs.next()) {
				lists.add(rs.getString("contentid"));
			}
		} catch (Exception e) {
			logger.error(e);
			throw new DAOException("��ѯ" + contentid + "�Ƿ����ʱ����", e);
		}
		return lists.size();
	}

	public int doInsert(String contentid, String isblack) throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("RiskTagDAO.doInsert() is starting ...");
		}
		int a = 0;
		// insert into t_r_blacklist(contentid,isblack,createtime)
		// values(?,?,sysdate)
		String insertCode = "tagmanager.dao.RiskTagDAO.doInsertBlack().INSERT";

		String[] paras = null;
		try {
			paras = new String[] { contentid, isblack };
			a = DB.getInstance().executeBySQLCode(insertCode, paras);
		} catch (DAOException e) {
			logger.error("�����񵥺�����ʱ�����쳣:", e);
			throw new DAOException("����" + contentid + "��������ʱ����", e);
		}
		return a;
	}

	public int doUpdate(String contentid, String isblack) throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("RiskTagDAO.doUpdate() is starting ...");
		}
		int a = 0;
		// update t_r_blacklist set isblack = ? where contentid =?
		String updateCode = "tagmanager.dao.RiskTagDAO.doInsertBlack().UPDATE";

		String[] paras = null;
		try {
			paras = new String[] { isblack, contentid };
			a = DB.getInstance().executeBySQLCode(updateCode, paras);
		} catch (DAOException e) {
			logger.error("�����񵥺�����ʱ�����쳣:", e);
			throw new DAOException("����" + contentid + "��������ʱ����", e);
		}
		return a;
	}

	private String dateToString(String dataFormat, Date date) {
		if (null != date) {
			SimpleDateFormat sd = new SimpleDateFormat(dataFormat);
			return sd.format(date);
		} else {
			return "";
		}
	}
	/**
	 * ����riskid������ѯ
	 * @param riskid
	 * @return
	 * @throws DAOException
	 */
	public ArrayList<String> doSelectBlack(String riskid) throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("RiskTagDAO.doInsertBlack() is starting ...");
		}
		// �Ȳ�ѯ�Ƿ���ں�������
		// select * from t_r_blacklist where isblack = 1;
		String sqlCode = "tagmanager.dao.RiskTagDAO.doSelectBlack().SELECT";
		ResultSet rs = null;
		ArrayList<String> lists = new ArrayList<String>();
		//ArrayList<String> paras = new ArrayList<String>();
		try {
			String sql = SQLCode.getInstance().getSQLStatement(sqlCode);
			StringBuffer sqlBuffer = new StringBuffer(sql);

			if (!"".equals(riskid)) {
				// sql += " and name like('%" + name
				// + "%')";
				sqlBuffer.append(" and t.risktag like   ?  ");
			}
	        String sqlKey = "%" + riskid + "%" ;
	        Object[] paras =
	            {sqlKey} ;
			// paras.add(riskid);
			//paras.add("%" + SQLUtil.escape(riskid) + "%");
			rs = DB.getInstance().query(sqlBuffer.toString(),
					paras);
			while (rs.next()) {
				lists.add(rs.getString("contentid"));
			}
		} catch (Exception e) {
			logger.error(e);
			throw new DAOException("��ѯ���к�����ʱ����", e);
		}
		return lists;
	}

	public void doDeleteBlack(String contentid) throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("RiskTagDAO.doInsertBlack() is starting ...");
		}
		// �Ȳ�ѯ�Ƿ���ں�������
		// select * from t_r_blacklist where contentid = ?
		String sqlCode = "tagmanager.dao.RiskTagDAO.doInsertBlack().DELETE";
		String[] paras = null;
		try {
			paras = new String[] { contentid };
			DB.getInstance().executeBySQLCode(sqlCode, paras);
		} catch (Exception e) {
			logger.error(e);
			throw new DAOException("ɾ��" + contentid + "ʱ����", e);
		}
	}

	public ArrayList<RiskTagVO> output(String type) throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("RiskDAO.output() is starting ...");
		}
		String sqlCode = "";
		if ("0".equals(type)) {
			// select * from t_r_gcontent t left join t_r_blacklist r on
			// t.contentid = r.contentid where t.risktag is not null and
			// (r.isblack != '1' or r.isblack is null)
			sqlCode = "tagmanager.dao.RiskDAO.output().SELECT1";
		} else {
			sqlCode = "tagmanager.dao.RiskDAO.output().SELECT2";

		}
		// �Ȳ�ѯ�Ƿ���ں�������
		String[] paras = null;
		ResultSet rs = null;
		ArrayList<RiskTagVO> lists = new ArrayList<RiskTagVO>();
		RiskTagVO vo = null;
		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
			while (rs.next()) {
				vo = new RiskTagVO();
				vo.setContentID(rs.getString("contentid"));
				vo.setName(rs.getString("name"));
				if ("".equals(rs.getString("isblack"))) {
					vo.setIsblack("0");
				} else {
					vo.setIsblack(rs.getString("isblack"));
				}
				vo.setCompany(rs.getString("spname"));
				vo.setType(rs.getString("catename"));
				vo.setTime( rs.getString("plupddate"));
				lists.add(vo);
			}
		} catch (Exception e) {
			logger.error(e);
			throw new DAOException("��ѯ���к�����ʱ����", e);
		}
		return lists;
	}

	public ArrayList<RiskTagVO> doOutput(String riskid, String contentid)
			throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("RiskTagDAO.doOutput() is starting ...");
		}
		String sql = "";
		String sqlCode = "";
		sqlCode = "tagmanager.dao.RiskTagDAO.doOutput().SELECT";
	
		
		// �Ȳ�ѯ�Ƿ���ں�������
		ResultSet rs = null;
		ArrayList<RiskTagVO> lists = new ArrayList<RiskTagVO>();
		RiskTagVO vo = null;
		List paras = new ArrayList();
		try {
			sql = SQLCode.getInstance().getSQLStatement(sqlCode);
			StringBuffer sqlBuffer = new StringBuffer(sql);
			paras.add("%" + SQLUtil.escape(riskid) + "%");
			if (!StringUtils.isEmpty(contentid)) {
				
				sqlBuffer.append(" and t.contentid = ?");
				paras.add(SQLUtil.escape(contentid));
			}
			
			
			rs = DB.getInstance().query(sqlBuffer.toString(), paras.toArray());
			while (rs.next()) {
				vo = new RiskTagVO();
				vo.setContentID(rs.getString("contentid"));
				vo.setName(rs.getString("name"));
				if (StringUtils.isEmpty(rs.getString("isblack"))) {
					vo.setIsblack("0");
				} else {
					vo.setIsblack(rs.getString("isblack"));
				}
				vo.setCompany(rs.getString("spname"));
				vo.setType(rs.getString("catename"));
				vo.setTime(rs.getString("plupddate"));
				lists.add(vo);
			}
		} catch (Exception e) {
			logger.error(e);
			throw new DAOException("��ѯ���к�����ʱ����", e);
		}
		return lists;
	}
}
