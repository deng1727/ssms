package com.aspire.dotcard.baseVideo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.db.SQLUtil;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.DataAccessException;
import com.aspire.common.persistence.util.SQLCode;
import com.aspire.dotcard.baseVideo.vo.BlackVO;
import com.aspire.dotcard.baseVideo.vo.CollectionResultVO;
import com.aspire.dotcard.baseVideo.vo.CollectionVO;
import com.aspire.dotcard.baseVideo.vo.ProgramBlackVO;
import com.aspire.dotcard.basemusic.vo.BaseMusicVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.page.PageVOInterface;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.util.PublicUtil;

public class CollectionDAO {
	/**
	 * 记录日志的实例对象
	 */
	protected static JLogger LOG = LoggerFactory.getLogger(CollectionDAO.class);

	/**
	 * singleton模式的实例
	 */
	private static CollectionDAO instance = new CollectionDAO();

	/**
	 * 构造方法，由singleton模式调用
	 */
	private CollectionDAO() {
	}

	/**
	 * 获取实例
	 * 
	 * @return 实例
	 */
	public static CollectionDAO getInstance() {
		return instance;
	}

	/**
	 * 用于查询当前黑名单列表
	 * 
	 * @param page
	 * @param vo
	 * @throws DAOException
	 */
	public void queryCollectionList(PageResult page, CollectionVO vo)
			throws DAOException {

		String sqlCode = "collection.CollectionDAO.queryCollectionList";
		List<CollectionResultVO> list = new ArrayList<CollectionResultVO>();
		ResultSet rs = null;
		String sql = null;

		try {
			sql = SQLCode.getInstance().getSQLStatement(sqlCode);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringBuffer sqlsb = new StringBuffer(sql);

		// sqlsb.append(sql);

		List<String> paras = new ArrayList<String>(2);

		// 构造搜索的sql和参数
		if (!"".equals(vo.getCollectionId())) {
			sqlsb.append(" and n.nodeid like ? ");
			paras.add("%" + SQLUtil.escape(vo.getCollectionId()) + "%");

		}
		if (!"".equals(vo.getNodeName())) {
			sqlsb.append(" and  n.nodeName like ? ");
			paras.add("%" + SQLUtil.escape(vo.getNodeName()) + "%");
		}
		if (!"".equals(vo.getParentNodeId())) {
			sqlsb.append(" and n.parentnodeid like ? ");
			paras.add("%" + SQLUtil.escape(vo.getParentNodeId()) + "%");
		}

		sqlsb.append(" order by n.nodeid ASC");
		page.excute(sqlsb.toString(), paras.toArray(), new PageVOInterface() {
			public void CopyValFromResultSet(Object vo, ResultSet rs)
					throws SQLException {
				CollectionResultVO collectionResultVO = (CollectionResultVO) vo;

				collectionResultVO.setCollectionId(rs.getString("nodeid"));
				collectionResultVO.setNodeName(rs.getString("nodename"));
				if ("1".equals(rs.getString("isshow"))) {
					collectionResultVO.setIsShow("是");

				} else {
					collectionResultVO.setIsShow("否");

				}
				collectionResultVO.setParentNodeId(rs.getString("parentnodeid"));
				collectionResultVO.setReName(rs.getString("nodebyname"));

			}

			public Object createObject() {
				return new CollectionResultVO();
			}
		});
	}

	/**
	 * 查询动漫内容信息列表
	 * 
	 * @param page
	 * @param vo
	 * @throws DAOException
	 */
	@SuppressWarnings("unchecked")
	public void queryContentList(PageResult page, BlackVO vo)
			throws DAOException {
		String sql = null;
		sql = "select a.nodeid,a.programid,a.programname,a.videoid,a.EXPORTTIME from t_vo_program a where 1=1 ";
		StringBuffer sqlsb = new StringBuffer(sql);
		List<String> paras = new ArrayList<String>();
		if (StringUtils.isNotBlank(vo.getNodeId())) {
			sqlsb.append(" and a.nodeid like ? ");
			paras.add("%" + SQLUtil.escape(vo.getNodeId().trim()) + "%");
		}
		if (StringUtils.isNotBlank(vo.getProgramId())) {
			sqlsb.append(" and a.programid like ? ");
			paras.add("%" + SQLUtil.escape(vo.getProgramId().trim()) + "%");
		}
		if (StringUtils.isNotBlank(vo.getProgramName())) {
			sqlsb.append(" and a.programname like ? ");
			paras.add("%" + SQLUtil.escape(vo.getProgramName().trim()) + "%");
		}
		if (StringUtils.isNotBlank(vo.getVideoId())) {
			sqlsb.append(" and a.videoid like ? ");
			paras.add("%" + SQLUtil.escape(vo.getVideoId().trim()) + "%");
		}
		sqlsb.append(" order by a.programname desc");

		page.excute(sqlsb.toString(), paras.toArray(), new PageVOInterface() {
			public void CopyValFromResultSet(Object vo, ResultSet rs)
					throws SQLException {
				BlackVO blackVO = (BlackVO) vo;
				// blackVO.setId(rs.getString("id"));
				blackVO.setNodeId(rs.getString("nodeid"));
				blackVO.setProgramId(rs.getString("programid"));
				blackVO.setProgramName(rs.getString("programname"));
				blackVO.setVideoId(rs.getString("videoid"));
				// blackVO.setLastUpTime(rs.getTimestamp("lupdate"));
				blackVO.setLastUpTime(rs.getTimestamp("EXPORTTIME"));
			}

			public Object createObject() {
				return new BlackVO();
			}
		});
	}

	/**
	 * 检查视频展示表是否存在节点ID和父节点ID
	 * 
	 * @param nodeid
	 * @param parentNodeId
	 * @return
	 * @throws DAOException
	 * @throws Exception
	 */
	public boolean isExistCollection(String nodeid, String parentNodeId)
			throws DAOException {
		if (StringUtils.isEmpty(parentNodeId)) {
			parentNodeId = "-1";
		}
		String sqlCode = "collection.CollectionDAO.isExistCollection.SELETE";
		String[] paras = new String[] { nodeid, parentNodeId };
		ResultSet rs = null;
		boolean result = false;
		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
			if (rs != null) {
				result = rs.next();
			}
		} catch (DAOException e) {
			e.printStackTrace();
			LOG.warn("检查视频展示表是否存在节点ID:" + nodeid + "和父节点ID:" + parentNodeId + "发生异常:", e);
			throw new DAOException("检查视频展示表是否存在节点ID:" + nodeid + "和父节点ID:" + parentNodeId + "发生异常:", e);
		} catch (SQLException e) {
			LOG.warn("检查视频展示表是否存在节点ID:" + nodeid + "和父节点ID:" + parentNodeId + "发生异常:", e);
			throw new DAOException("检查视频展示表是否存在节点ID:" + nodeid + "和父节点ID:" + parentNodeId + "发生异常:", e);
		} finally {
			DB.close(rs);
		}

		return result;
	}

	/**
	 * 检查内容表t_cb_content是否存在conntentId
	 * 
	 * @param contentId
	 * @return
	 * @throws DAOException
	 */
	public boolean isExistContent(String contentId) throws DAOException {
		if (StringUtils.isEmpty(contentId)) {
			LOG.warn("检查视频内容表contentid参数为空!");
			throw new DAOException("检查视频内容表contentid参数为空!");
		}

		String sqlCode = "videoblack.BlackDAO.isExistContent.SELETE";
		String[] paras = new String[] { contentId };
		ResultSet rs = null;
		boolean result = false;
		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
			if (rs != null) {
				result = rs.next();
			}
		} catch (DAOException e) {
			LOG.error("检查动漫内容表是否存在内容contentid:" + contentId + " 发生异常:", e);
			throw new DAOException("检查内容表是否存在内容contentid:" + contentId
					+ " 发生异常:", e);
		} catch (SQLException e) {
			LOG.error("检查动漫内容表是否存在内容contentid:" + contentId + " 发生异常:", e);
			throw new DAOException("检查内容表是否存在内容contentid:" + contentId
					+ " 发生异常:", e);
		} finally {
			DB.close(rs);
		}
		return result;
	}

	/**
	 * 新增多条视频内容
	 * 
	 * @param contentId
	 * @throws DAOException
	 */
	@SuppressWarnings("null")
	public String addImCollection(List collectionNodeRow) throws DAOException {
		String returnResult = null;
		if (collectionNodeRow.size() == 0) {
			LOG.warn("新增视频三级节点黑名单programId为空!");
			returnResult = "增量导入视频三级节点0条记录。";
			return returnResult;
		}

		String insertSqlCode = "collection.CollectionDAO.addImCollection.insert";
		String updateSqlCode = "collection.CollectionDAO.addImCollection.update";
		int number = 0;
		for (int i = 0; i < collectionNodeRow.size(); i++) {
			try {
				boolean result = false;
				if (StringUtils.isEmpty(((Map) collectionNodeRow.get(i)).get(0)
						.toString())) {
					LOG.error("导入视频三級节点第：" + (i + 1) + "行节点ID为空");
					if (returnResult == null) {
						returnResult = "增量导入视频三级节点第" + (i + 1) + "行导入出错";
					} else {
						returnResult = returnResult + "，增量导入视频三级节点第" + (i + 1)
								+ "行导入出错";
					}
					continue;
				}
				Object[] object2 = new Object[] { new String(), new String(),
						new String(), new String() };
				Object[] object3 = new Object[] { new String(), new String(),
						new String(), new String() };

				if ("是".equals(((Map) collectionNodeRow.get(i)).get(2).toString())) {
					object3[0] = "1";
				} else {
					object3[0] = "0";
				}
				object3[1] = ((Map) collectionNodeRow.get(i)).get(3).toString();
				object3[2] = ((Map) collectionNodeRow.get(i)).get(0).toString();
				object3[3] = ((Map) collectionNodeRow.get(i)).get(1).toString();

				object2[0] = ((Map) collectionNodeRow.get(i)).get(0).toString();
				object2[1] = ((Map) collectionNodeRow.get(i)).get(1).toString();

				if ("是".equals(((Map) collectionNodeRow.get(i)).get(2).toString())) {

					object2[2] = "1";
				} else {

					object2[2] = "0";
				}
				object2[3] = ((Map) collectionNodeRow.get(i)).get(3).toString();

				result = CollectionDAO.getInstance().isExistCollection(
						((Map) collectionNodeRow.get(i)).get(0).toString(),
						((Map) collectionNodeRow.get(i)).get(1).toString());

				if (result) {
					DB.getInstance().executeInsertImBySQLCode(updateSqlCode,
							object3);
				} else {
					DB.getInstance().executeInsertImBySQLCode(insertSqlCode,
							object2);
				}
				number += 1;
			} catch (DAOException e) {
				LOG.warn("导入视频三級节点第：" + (i + 1) + "行出错");
				if (returnResult == null) {
					returnResult = "增量导入视频三级节点第" + (i + 1) + "行导入出错";
				} else {
					returnResult = returnResult + "，增量导入视频三级节点第" + (i + 1)
							+ "行导入出错";
				}
			}
		}
		if (returnResult == null) {
			returnResult = "增量导入视频三级节点" + number + "条记录成功！";
		} else {
			returnResult = "增量导入视频三级节点" + number + "条记录成功！" + returnResult;
		}
		return returnResult;
	}

	/**
	 * 
	 * @return
	 * @throws DAOException
	 */
	public List<CollectionResultVO> queryCollectionListByExport(CollectionVO vo)
			throws DAOException {

		if (LOG.isDebugEnabled()) {
			LOG.debug("queryCollectionListByExport(" + vo.getCollectionId()
					+ ", " + vo.getNodeName() + ") is starting ...");
		}

		// select * from t_mb_music_new t where t.delflag=0
		String sqlCode = "collection.CollectionDAO.queryCollectionList";
		List<CollectionResultVO> list = new ArrayList<CollectionResultVO>();
		ResultSet rs = null;
		String sql;
		try {
			sql = SQLCode.getInstance().getSQLStatement(sqlCode);

			StringBuffer sqlBuffer = new StringBuffer(sql);
			List<String> paras = new ArrayList<String>();

			// 构造搜索的sql和参数
			if (!"".equals(vo.getCollectionId())) {
				sqlBuffer.append(" and n.nodeid like ? ");
				paras.add("%" + SQLUtil.escape(vo.getCollectionId()) + "%");
			}
			if (!"".equals(vo.getNodeName())) {
				sqlBuffer.append(" and n.nodeName like ? ");
				paras.add("%" + SQLUtil.escape(vo.getNodeName()) + "%");
			}
			if (!"".equals(vo.getParentNodeId())) {
				sqlBuffer.append(" and n.parentnodeid like ? ");
				paras.add("%" + SQLUtil.escape(vo.getParentNodeId()) + "%");
			}

			rs = DB.getInstance().query(sqlBuffer.toString(), paras.toArray());

			while (rs.next()) {
				list.add(baseCollectionVoData(rs));
			}
		} catch (SQLException e) {
			LOG.error("执行获取当前条件下所有查询视频三级节点失败", e);
			e.printStackTrace();
		} catch (DataAccessException e) {
			LOG.error("执行获取当前条件下所有查询视频三级节点失败", e);
			e.printStackTrace();
		} finally {
			DB.close(rs);
		}

		return list;
	}

	private CollectionResultVO baseCollectionVoData(ResultSet rs)
			throws SQLException {
		CollectionResultVO vo = new CollectionResultVO();
		vo.setCollectionId(rs.getString("nodeid"));
		vo.setNodeName(rs.getString("nodename"));
		vo.setIsShow(rs.getString("isshow"));
		vo.setParentNodeId(rs.getString("parentnodeid"));
		vo.setReName(rs.getString("nodebyname"));
		return vo;
	}

	public int updateCollections(String sql, String[] key) throws BOException {
		int ret = 0;

		try {
			ret = DB.getInstance().executeBySQLCode(sql, key);
		} catch (DAOException e) {
			LOG.error("删除视频表数据失败:", e);
			throw new BOException("删除视频临时表数据:" + PublicUtil.GetCallStack(e)
					+ "<br>");
		}

		return ret;
	}
}
