package com.aspire.ponaadmin.web.catesyn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.db.ResultSetConvertor;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.repository.CategoryDAO;
import com.aspire.ponaadmin.web.repository.persistency.db.NodePersistencyDB;

public class CateSynBo {
	private TransactionDB db;

	StringBuffer sb = new StringBuffer("��Ӫ����ͬ�������<br>");

	public void doSynCate() throws Exception {
		try {
			db = TransactionDB.getTransactionInstance();
			List add = addCategory();
			db.commit();
			printInfo(add);
			sb.append("<br>");
			printUpdateInfo();

			List del = delCategory();
			db.commit();
			sb.append("<br>");
			printDelInfo(del);

		} catch (DAOException e) {
			sb.append("ͬ��ʧ�ܣ�ʧ��ԭ���ǣ�" + e.getMessage());
			db.rollback();
		} catch (SQLException e) {
			sb.append("ͬ��ʧ�ܣ�ʧ��ԭ���ǣ�" + e.getMessage());
			db.rollback();
		} catch (Exception e) {
			sb.append("ͬ��ʧ�ܣ�ʧ��ԭ���ǣ�" + e.getMessage());
			db.rollback();
			throw new Exception("ͬ��ʧ��" + e.getMessage());
		} finally {
			if (!db.getConnection().isClosed()) {
				db.close();
			}
		}
		if (Config.MAIL_TO == null) {
			logger.warn("�������ʼ�����Ŀ��!!");
		}
		Mail.sendMail("���з���ͬ�����", sb.toString(), Config.MAIL_TO);
	}

	private void printInfo(List add) {
		if (this.addS.size() > 0) {
			sb.append("��������Ӫ���ࣺ" + add.size() + " ��,����������<br>");
		}
		for (int i = 0; i < addS.size(); i++) {
			sb.append(addS.get(i)).append("<br>");
		}
	}

	private void printDelInfo(List del) {
		if (this.delS.size() > 0) {
			sb.append("��ɾ����Ӫ���ࣺ" + del.size() + " ��,����������<br>");
		}
		for (int i = 0; i < delS.size(); i++) {
			sb.append(delS.get(i)).append("<br>");
		}
	}

	private void printUpdateInfo() {
		if (this.updateItems.size() == 0) {
			return;
		}
		sb.append("��������������" + updateItems.size()).append("������������:<br> ");
		for (int i = 0; i < updateItems.size(); i++) {
			sb.append(updateItems.get(i)).append("<br>");
		}
	}

	protected static JLogger logger = LoggerFactory.getLogger(CateSynBo.class);

	private List addS = new ArrayList();

	private List delS = new ArrayList();

	private List updateItems = new ArrayList();

	private List addCategory() throws DAOException, SQLException {

		// �������Ķ������า�ǣ��������������)
		logger.debug("ִ�и�������.....");
		updateTVomDictionaryNames();
		// ִ�и������л��ܵ�����(��ͬ�����µ��������޸ķ������������)
		logger.debug("ִ�и������л��ܵ�����");
		db.executeBySQLCode("update_term_categoryname", null);
		db.executeBySQLCode("update_www_categoryname", null);
		db.executeBySQLCode("update_wap_categoryname", null);
		db.executeBySQLCode("update_www_sync_tactic", null);
		db.executeBySQLCode("update_wap_sync_tactic", null);
		db.executeBySQLCode("update_term_sync_tactic", null);
		logger.debug("��ѯ��������Ӫ����.....");
		List lstadd = new ArrayList();
		lstadd = queryAddCate();
		logger.debug("��������Ӫ���������" + lstadd.size());
		CateSynVo temp = null;
		for (int i = 0; i < lstadd.size(); i++) {
			temp = (CateSynVo) lstadd.get(i);
			try {
				logger.debug("����һ��" + "ID : " + temp.getAliasid() + ", ���ƣ�"
						+ temp.getAliasname());
				add(temp);// ���������������һ������
			} catch (DAOException e) {
				logger.debug(e);
				throw e;
			}
			addS.add("ID : " + temp.getAliasid() + ", ���ƣ�"
					+ temp.getAliasname());
			logger.debug("����һ��:" + "ID : " + temp.getAliasid() + ", ���ƣ�"
					+ temp.getAliasname() + "���!");
		}

		return lstadd;
	}

	private void updateTVomDictionaryNames() throws DAOException, SQLException {
		List lst = db.queryBySQLCode("query_new_names", null,
				new ResultSetConvertor() {
					public Object convert(ResultSet rs) throws SQLException {
						CateSynVo vo = new CateSynVo();
						vo.setAliasid(rs.getString(1));
						vo.setAliasname(rs.getString(2));
						vo.setFirstid(rs.getString(3));
						vo.setOldAliasname(rs.getString(4));
						return vo;
					}
				});
		logger.debug("������Ҫ��������������" + lst.size());
		CateSynVo temp = null;
		Connection con = db.getConnection();
		PreparedStatement pre = null;
		try {
			pre = con.prepareStatement(db
					.getSQLByCode("update_T_V_OM_DECTIONARY_name"));
			for (int i = 0; i < lst.size(); i++) {
				temp = (CateSynVo) lst.get(i);
				pre.setString(1, temp.getAliasname());
				pre.setString(2, temp.getAliasid());
				pre.setString(3, temp.getFirstid());
				pre.addBatch();
				logger.debug("Aliasid:" + temp.getAliasid() + ",�����ƣ�"
						+ temp.getOldAliasname() + ",�����ƣ�"
						+ temp.getAliasname());
				updateItems.add("Aliasid:" + temp.getAliasid() + ",�����ƣ�"
						+ temp.getOldAliasname() + ",�����ƣ�"
						+ temp.getAliasname());
			}
			pre.executeBatch();
		} catch (SQLException e) {
			logger.debug(e);
			throw e;
		}finally{
			pre.close();
		}

	}

	private void add(CateSynVo temp) throws DAOException {
		NodePersistencyDB nodedb = new NodePersistencyDB(null);
		ParentCids pcids = getParenId(temp.getFirstid());
		logger.debug(pcids);
		// terminal
		String termid = nodedb.allocateNewNodeID();
		String termcategoryid = String.valueOf(CategoryDAO.getInstance()
				.getSeqCategoryID());
		saveData(termid, pcids.getTermParentCategoryid(), termcategoryid, temp,
				pcids.getTermpath(), pcids.getTermBaseParentId(), "O");
		// wap
		String wapid = nodedb.allocateNewNodeID();
		String wapcategoryid = String.valueOf(CategoryDAO.getInstance()
				.getSeqCategoryID());
		saveData(wapid, pcids.getWapParentCategoryid(), wapcategoryid, temp,
				pcids.getWappath(), pcids.getWapBaseParentId(), "A");
		// www
		String wwwid = nodedb.allocateNewNodeID();
		String wwwcategoryid = String.valueOf(CategoryDAO.getInstance()
				.getSeqCategoryID());
		saveData(wwwid, pcids.getWwwParentCategoryid(), wwwcategoryid, temp,
				pcids.getWwwpath(), pcids.getWwwBaseParentId(), "W");

		db.executeBySQLCode("INSERT_T_V_OM_DECTIONARY", new Object[] { termid,
				wapid, wwwid, pcids.getTermparentcid(),
				pcids.getWapparentcid(), pcids.getWwwparentcid(),
				temp.getFirstid(), temp.getAliasid(), temp.getAliasname() });
	}

	private void saveData(String id, String parentCategoryId,
			String categoryid, CateSynVo temp, String parentPath,
			String baseParentid, String relation) throws DAOException {
		db.executeBySQLCode("inserBaseSql", new Object[] { id, baseParentid,
				parentPath + ",{" + id + "}", "nt:category" });
		db.executeBySQLCode("inserCategorySql", new Object[] { id,
				temp.getAliasname(), temp.getAliasname(), categoryid,
				parentCategoryId, relation });
		db.executeBySQLCode("insertSyntaic", new Object[] { id,
				getContentType(temp.getFirstid()), temp.getAliasname() });

	}

	private String getContentType(String type) {
		if ("1001".equals(type)) {
			return "nt:gcontent:appSoftWare";
		}
		if ("1002".equals(type)) {
			return "nt:gcontent:appTheme";
		}
		if ("1003".equals(type)) {
			return "nt:gcontent:appGame";
		}
		return null;
	}

	private List delCategory() throws DAOException, SQLException {
		List lstdel = queryDelCate();
		logger.debug("��ѯ��Ҫɾ���ķ������ݣ�" + lstdel.size() + " ��");
		CateSynVo temp = null;
		for (int i = 0; i < lstdel.size(); i++) {
			temp = (CateSynVo) lstdel.get(i);
			try {
				logger.debug("��ʼɾ�������Ӧ���ն��Ż�����");
				del(temp.getTermcid());
				logger.debug("��ʼɾ�������Ӧ��wap�Ż�����");
				del(temp.getWapcid());
				logger.debug("��ʼɾ�������Ӧ��www�Ż�����");
				del(temp.getWwwcid());
				db.executeBySQLCode("delete_T_V_OM_DECTIONARY", new Object[]{temp.getAliasid(),temp.getFirstid()});
			} catch (DAOException e) {
				logger.debug(e);
				throw e;
			}
			logger.debug("ɾ���������.");
			delS.add("ID : " + temp.getAliasid() + ", ���ƣ�"
					+ temp.getAliasname());
		}
		return lstdel;
	}

	private void del(String id) throws DAOException {
		// ɾ����Ҫɾ�����ܶ�Ӧ��Ʒ��base��Ϣ
		logger.debug("ɾ����Ҫɾ�����ܶ�Ӧ��Ʒ��base��Ϣ:" + id);
		db.executeBySQLCode("DEL_BASE_SQL", new Object[] { id });
		// ɾ����Ҫɾ�����ܶ�Ӧ����Ʒ��Ϣ
		logger.debug("ɾ����Ҫɾ�����ܶ�Ӧ����Ʒ��Ϣ" + id);
		db.executeBySQLCode("DEL_REFERENCE_SQL", new Object[] { id });
		// ����t_r_category��ɾ����־
		logger.debug("����t_r_category��ɾ����־" + id);
		db.executeBySQLCode("DEL_CATEGORY_SQL", new Object[] { id });
		logger.debug("ɾ������ͬ������" + id);
		// ɾ������ͬ������
		db.executeBySQLCode("DEL_SYNTATIC", new Object[] { id });
	}

	private static Map parentCidMap = new Hashtable();

	public ParentCids getParenId(String type) throws DAOException {
		if (parentCidMap.get(type) != null) {
			return (ParentCids) parentCidMap.get(type);
		}
		return setParentCid(type);
	}

	private synchronized ParentCids setParentCid(String type)
			throws DAOException {
		List lst = db.queryBySQLCode("cache_path_parentCategoryPath",
				new Object[] { type }, new ResultSetConvertor() {
					public Object convert(ResultSet rs) throws SQLException {
						ParentCids c = new ParentCids(rs.getString(3), rs
								.getString(1), rs.getString(2));
						c.setWappath(rs.getString(5));
						c.setWwwpath(rs.getString(6));
						c.setTermpath(rs.getString(7));
						c.setWapBaseParentId(rs.getString(8));
						c.setWwwBaseParentId(rs.getString(9));
						c.setTermBaseParentId(rs.getString(10));
						return c;
					}
				});
		if (lst.size() == 0 || lst.get(0) == null) {
			logger.warn(type + "û���ҵ�������");
		}
		if (parentCidMap.get(type) != null) {
			return (ParentCids) parentCidMap.get(type);
		}
		parentCidMap.put(type, lst.get(0));
		return (ParentCids) lst.get(0);
	}

	class ParentCids {
		String wapParentCategoryid;

		String wwwParentCategoryid;

		String termParentCategoryid;

		String wappath;

		String wwwpath;

		String termpath;

		String termBaseParentId;

		String wapBaseParentId;

		String wwwBaseParentId;

		public String getTermParentCategoryid() {
			return termParentCategoryid;
		}

		public void setTermParentCategoryid(String termParentCategoryid) {
			this.termParentCategoryid = termParentCategoryid;
		}

		public String getWapParentCategoryid() {
			return wapParentCategoryid;
		}

		public void setWapParentCategoryid(String wapParentCategoryid) {
			this.wapParentCategoryid = wapParentCategoryid;
		}

		public String getWwwParentCategoryid() {
			return wwwParentCategoryid;
		}

		public void setWwwParentCategoryid(String wwwParentCategoryid) {
			this.wwwParentCategoryid = wwwParentCategoryid;
		}

		public String getTermBaseParentId() {
			return termBaseParentId;
		}

		public void setTermBaseParentId(String termBaseParentId) {
			this.termBaseParentId = termBaseParentId;
		}

		public String getWapBaseParentId() {
			return wapBaseParentId;
		}

		public void setWapBaseParentId(String wapBaseParentId) {
			this.wapBaseParentId = wapBaseParentId;
		}

		public String getWwwBaseParentId() {
			return wwwBaseParentId;
		}

		public void setWwwBaseParentId(String wwwBaseParentId) {
			this.wwwBaseParentId = wwwBaseParentId;
		}

		public String getTermpath() {
			return termpath;
		}

		public void setTermpath(String termpath) {
			this.termpath = termpath;
		}

		public String getWappath() {
			return wappath;
		}

		public void setWappath(String wappath) {
			this.wappath = wappath;
		}

		public String getWwwpath() {
			return wwwpath;
		}

		public void setWwwpath(String wwwpath) {
			this.wwwpath = wwwpath;
		}

		public ParentCids(String t, String wap, String www) {
			this.termParentCategoryid = t;
			this.wapParentCategoryid = wap;
			this.wwwParentCategoryid = www;
		}

		public String getTermparentcid() {
			return termParentCategoryid;
		}

		public void setTermparentcid(String termparentcid) {
			this.termParentCategoryid = termparentcid;
		}

		public String getWapparentcid() {
			return wapParentCategoryid;
		}

		public void setWapparentcid(String wapparentcid) {
			this.wapParentCategoryid = wapparentcid;
		}

		public String getWwwparentcid() {
			return wwwParentCategoryid;
		}

		public void setWwwparentcid(String wwwparentcid) {
			this.wwwParentCategoryid = wwwparentcid;
		}

		public String toString() {
			StringBuffer sb = new StringBuffer();
			sb.append("[termpcid:").append(this.termParentCategoryid).append(
					"|path:").append(this.termpath).append("]");
			sb.append("[wappcid:").append(this.wapParentCategoryid).append(
					"|path:").append(this.wappath).append("]");
			sb.append("[wwwpcid:").append(this.wwwParentCategoryid).append(
					"|path:").append(this.wwwpath).append("]");
			return sb.toString();
		}

	}

	private List queryAddCate() throws DAOException {
		return db.queryBySQLCode("SQL_QUERY_ADD", null,
				new ResultSetConvertor() {
					public Object convert(ResultSet rs) throws SQLException {
						CateSynVo vo = new CateSynVo();
						vo.setAliasid(rs.getString(1));
						vo.setAliasname(rs.getString(2));
						vo.setFirstid(rs.getString(3));
						return vo;
					}

				});
	}

	private List queryDelCate() throws DAOException {
		return db.queryBySQLCode("SQL_QUERY_DEL", null,
				new ResultSetConvertor() {
					public Object convert(ResultSet rs) throws SQLException {
						CateSynVo vo = new CateSynVo();
						vo.setAliasid(rs.getString(1));
						vo.setAliasname(rs.getString(2));
						vo.setWapcid(rs.getString(3));
						vo.setWappid(rs.getString(4));
						vo.setFirstid(rs.getString(5));
						vo.setWwwcid(rs.getString(6));
						vo.setWwwpid(rs.getString(7));
						vo.setTermcid(rs.getString(8));
						vo.setTermpid(rs.getString(9));
						return vo;
					}
				});
	}
}
