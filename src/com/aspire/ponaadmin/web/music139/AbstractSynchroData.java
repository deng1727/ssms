package com.aspire.ponaadmin.web.music139;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.aspire.common.db.DAOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.db.ResultSetConvertor;

public abstract class AbstractSynchroData extends AbstractMusic139SynBo {
	private static final JLogger log = LoggerFactory
			.getLogger(AbstractSynchroData.class);

	/**
	 * 操作类型值所在|的下标
	 * 
	 * @return
	 */
	protected abstract int indexOfOperation();

	/**
	 * 根据行数据获取到对应插入t_mb_category表sql的参数数组
	 * 
	 * @param categoryId
	 *            货架ID
	 * @param line
	 *            行数据
	 * @return 对应插入t_mb_category表sql的参数数组
	 */
	protected abstract Object[] getInsertCategoryObjectParams(
			String categoryId, String[] line, int sum);

	/**
	 * 
	 * @return t_mb_category表sql
	 */
	protected abstract String getInsertCategorySQL();

	protected abstract boolean validateLine(String[] line);

	protected abstract String getParentCategoryId();

	protected abstract String getUpdateCategorySQL();

	protected abstract Object[] getUpdateCategoryParams(String categoryId,
			String[] line, int sum);

	public String getPrefixName() {
		return ".txt";
	}

	protected List invali = new ArrayList();

	protected List noMusic = new ArrayList();

	protected void addInvalidateMsg(String msg) {
		invali.add(msg);
	}

	List delNoCat = new ArrayList();

	protected void handleData(String localfile) throws DAOException,
			SQLException {
		long dc = 0, ac = 0, uc = 0, sum = 0;
		long p = System.currentTimeMillis();
		BufferedReader reader = null;
		List arrDataErrer = new ArrayList();
		List noOp = new ArrayList();
		List nullIdOrName = new ArrayList();
		try {
			r.setName(getOperationName());
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream((new File(localfile))), "utf-8"));
			String temp = null;
			String[] line = null;
			while ((temp = reader.readLine()) != null) {
				line = temp.split("\\|");
				sum++;
				if (line.length < this.indexOfOperation()+1) {//不能少于9个字段
					log.warn("数据有误!!\r\n" + temp);
					arrDataErrer.add(temp);
					continue;
				}
				if (!"1".equals(line[this.indexOfOperation()])
						&& !"2".equals(line[this.indexOfOperation()])) {
					log.warn("不明操作类型:\r\n" + temp);
					noOp.add(temp);
					continue;
				}
				if (line[0] == null || line[0].length() == 0) {
					log.warn("发现一项数据不完整：专辑ID为空!");
					nullIdOrName.add(temp);
					continue;
				}
				if (line[1] == null || line[1].length() == 0) {
					log.warn("发现一项数据不完整：专辑名称为空!");
					nullIdOrName.add(temp);
					continue;
				}
				if (!validateLine(line)) {
					continue;
				}
				String categoryId;
				categoryId = getCategoryId(line[0]);
				if ("2".equals(line[indexOfOperation()])) {
					int n = delete(line, categoryId);
					if (n == 1) {
						dc++;
					} else {
						delNoCat.add(categoryId);
					}
				} else {
					if (categoryId == null) {
						insert(line);
						ac++;
					} else {
						int n = update(line, categoryId);
						if (n == 1) {
							uc++;
						}
					}
				}
			}
			db.commit();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			r.setMsg("操作失败，读取本地文件时发生错误" + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			r.setMsg("操作失败，读取本地文件时发生错误" + e.getMessage());
		} catch (SQLException e) {
			db.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			db.close();
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		StringBuffer msg = new StringBuffer();
		msg.append("同步操作成功！共处理数据项数：" + sum + "，其中增加数量：" + ac + "，更新数量:" + uc
				+ "，删除数量:" + dc + "，耗时：" + (System.currentTimeMillis() - p)
				+ " 毫秒<br>");
		if (arrDataErrer.size() > 0) {
			msg.append("数据有误数量：" + arrDataErrer.size() + " ，以下是详细信息:<br>");
			listBuffer(arrDataErrer, msg);
		}
		if (noOp.size() > 0) {
			msg.append("不明操作类型：" + noOp.size() + "，以下是详细信息:<br>");
			listBuffer(noOp, msg);
		}
		if (nullIdOrName.size() > 0) {
			msg.append(this.getOperationName().substring(9, 11)
					+ "ID项或名称项为空项数：" + noOp.size() + " ，以下是详细信息:<br>");
			listBuffer(nullIdOrName, msg);
		}
		if (this.invali.size() > 0) {
			msg.append("数据校验不合格项数：" + this.invali.size() + "，以下是详细信息:<br>");
			listBuffer(this.invali, msg);
		}
		if (this.noMusic.size() > 0) {
			msg.append("音乐项不存在项数：" + this.noMusic.size() + "，以下是详细信息:<br>");
			listBuffer(this.noMusic, msg);
		}
		if (this.delNoCat.size() > 0) {
			msg.append("删除时，未找到对应的" + this.getOperationName().substring(9, 11)
					+ "ID，共有：" + this.delNoCat.size() + "，以下是:"
					+ this.getOperationName().substring(9, 11) + "ID:<br>");
			listBuffer(this.delNoCat, msg);
		}

		r.setSuccess(true);
		r.setMsg(msg.toString());
		r.setName(getOperationName());
	}

	public static void listBuffer(List lst, StringBuffer sb) {
		for (int i = 0; i < lst.size(); i++) {
			if (i > 100) {
				sb.append(".....");
				return;
			}
			sb.append(lst.get(i)).append("<br>");
		}
	}

	static final String INSERT_T_MB_REFERENCE = "insert into T_MB_REFERENCE(Musicid, CATEGORYID, MUSICNAME, CREATETIME, SORTID)values"
			+ "(?, ?,?, to_char(sysdate,'yyyy-MM-dd HH24:mi:ss'), ?)";

	// static final String INSERT_T_MB_REFERENCE = "insert into
	// T_MB_REFERENCE(Musicid, CATEGORYID, MUSICNAME, CREATETIME, SORTID)values"
	// + "(?, ?,(select a.songname from t_mb_music a where musicid = ?),
	// to_char(sysdate,'yyyy-MM-dd HH24:mi:ss'), ?)";

	private boolean insert(String[] line) throws DAOException {
		try {
			insertNotTransaction(line);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return true;

	}

	private int updateCategory(String categoryId, String[] line)
			throws SQLException, DAOException {

		PreparedStatement p = null;
		String[] musics = line[indexOfOperation() - 1].split(",");
		p = db.getConnection().prepareStatement(INSERT_T_MB_REFERENCE);
		String mname = null;
		int count = 0;
		Set s = new HashSet();
		for (int i = 0, j = musics.length; i < j; i++) {
			mname = queryMusicName(musics[i]);
			if (mname == null) {
				log.warn("缺少音乐元数据!" + musics[i]);
				this.noMusic.add("音乐ID：" + musics[i] + "缺少音乐元数据!对应的"
						+ this.getOperationName().substring(9, 11) + "ID是:"
						+ line[0]);
				continue;
			}
			if (s.contains(musics[i])) {
				continue;
			} else {
				if (i == j - 1) {
					s.clear();
					s = null;
				} else {
					s.add(musics[i]);
				}
			}
			p.setString(1, musics[i]);
			p.setString(2, categoryId);
			p.setString(3, mname);
			p.setInt(4, i + 1);// 排序
			count++;
			p.addBatch();
		}
		p.executeBatch();
		if(p != null){
			p.close();
		}
		return db.execute(getUpdateCategorySQL(), this.getUpdateCategoryParams(
				categoryId, line, count));
	}

	private void insertNotTransaction(String[] line) throws SQLException,
			DAOException {

		PreparedStatement p = null;
		String categoryId = String
				.valueOf(db.getSeqValue("SEQ_BM_CATEGORY_ID"));
		String[] musics = line[indexOfOperation() - 1].split(",");
		p = db.getConnection().prepareStatement(INSERT_T_MB_REFERENCE);
		String mname = null;
		int count = 0;
		Set s = new HashSet();
		for (int i = 0, j = musics.length; i < j; i++) {
			mname = queryMusicName(musics[i]);
			if (mname == null) {
				log.warn("缺少音乐元数据!" + musics[i]);
				this.noMusic.add("音乐ID：" + musics[i] + "缺少音乐元数据!对应的"
						+ this.getOperationName().substring(9, 11) + "ID是:"
						+ line[0]);
				continue;
			}
			if (s.contains(musics[i])) {
				continue;
			} else {
				if (i == j - 1) {
					s.clear();
					s = null;
				} else {
					s.add(musics[i]);
				}
			}
			p.setString(1, musics[i]);
			p.setString(2, categoryId);
			p.setString(3, mname);
			p.setInt(4, i + 1);// 排序
			count++;
			p.addBatch();
		}
		p.executeBatch();
		db.execute(getInsertCategorySQL(), getInsertCategoryObjectParams(
				categoryId, line, count));
		if(p != null){
			p.close();
		}
	}

	static final String DELETE_T_MB_CATEGORY = "DELETE T_MB_CATEGORY WHERE categoryid=?";

	static final String DELETE_T_MB_REFERENCE = "DELETE T_MB_REFERENCE WHERE categoryid=?";

	private int update(String[] line, String categoryId) throws DAOException {
		try {
			deleteNotTransaction(line, categoryId);
			return updateCategory(categoryId, line);
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return 0;
	}

	private int deleteAllNotTransaction(String[] line, String categoryId)
			throws SQLException {
		try {
			db.execute(DELETE_T_MB_REFERENCE, new String[] { categoryId });
			return db
					.execute(DELETE_T_MB_CATEGORY, new String[] { categoryId });
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return -1;
	}

	private int deleteNotTransaction(String[] line, String categoryId)
			throws SQLException {
		try {
			// db.execute(DELETE_T_MB_CATEGORY, new String[] { categoryId });
			return db.execute(DELETE_T_MB_REFERENCE,
					new String[] { categoryId });
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	private String getCategoryId(String ablumId) throws DAOException {
		return (String) queryUniqueObject(
				"SELECT categoryid FROM T_MB_CATEGORY WHERE album_id=? ",
				new String[] { ablumId });
	}

	private String queryMusicName(String musicId) throws DAOException {
		List list;
		list = db
				.query(
						"select t.songname from T_MB_MUSIC t where t.DELFLAG=0 and t.musicid=?",
						new String[] { musicId }, new ResultSetConvertor() {
							public Object convert(ResultSet rs)
									throws SQLException {
								return rs.getString(1);
							}
						});
		if (list == null || list.size() == 0) {
			return null;
		}
		return (String) list.get(0);
	}

	private int delete(String[] line, String categoryId) throws SQLException {
		return deleteAllNotTransaction(line, categoryId);
	}
}
