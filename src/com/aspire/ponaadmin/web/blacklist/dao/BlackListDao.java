package com.aspire.ponaadmin.web.blacklist.dao;

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
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.blacklist.vo.BlackListOperationVO;
import com.aspire.ponaadmin.web.blacklist.vo.BlackListVO;
import com.aspire.ponaadmin.web.blacklist.vo.ContentVO;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.repository.SearchParam;
import com.aspire.ponaadmin.web.repository.Searchor;

public class BlackListDao {

	protected static JLogger logger = LoggerFactory
			.getLogger(BlackListDao.class);

	private static BlackListDao bd = new BlackListDao();

	public static BlackListDao getInstance() {
		return bd;
	}

	/**
	 * 查询黑名单
	 * 
	 * @param page
	 * @param searchor
	 * @return
	 * @throws Exception
	 */
	public List queryBlackList(PageResult page, Searchor searchor,String aprovalStatus)
			throws Exception {
		String sql;
		sql = DB.getInstance().getSQLByCode(
				"blacklist.dao.BlackListDao.queryBlackList");
		StringBuffer bf = new StringBuffer(sql);
		List paraList = new ArrayList();
		// 设置查询参数。
		// 遍历表达式的各个参数，进行处理
		for (int i = 0; i < searchor.getParams().size(); i++) {
			SearchParam param = (SearchParam) searchor.getParams().get(i);
			bf.append(" ");
			bf.append(param.getMode());
			bf.append(" ");
			// 如果是左括号开始，要添加一个(
			if (param.getBracket().equals(
					RepositoryConstants.SEARCH_PARAM_BRACKET_LEFT)) {
				bf.append(RepositoryConstants.SEARCH_PARAM_BRACKET_LEFT);
			}
			// 从类字段对应到数据库表字段
			if (param.getOperator().equals(
					RepositoryConstants.OP_LIKE_IgnoreCase)) {
				bf.append("upper(");
				bf.append(param.getProperty());
				bf.append(") like ");
				bf.append("?");

			} else {
				bf.append(param.getProperty());
				bf.append(' ');
				bf.append(param.getOperator());
				bf.append(' ');
				bf.append('?');
			}

			// 如果是右括号结束，要添加一个)
			if (param.getBracket().equals(
					RepositoryConstants.SEARCH_PARAM_BRACKET_RIGHT)) {
				bf.append(RepositoryConstants.SEARCH_PARAM_BRACKET_RIGHT);
			}
			paraList.add(param.getValue());
		}
		
		 if(aprovalStatus != null && !"".equals(aprovalStatus) && !"4".equals(aprovalStatus)){
	        	String[] strs = aprovalStatus.split(",");
	        	if(strs.length > 1){
	        		bf.append(" and tcb.blacklist_status in ('" + strs[0] + "','"+strs[1]+"')");
	        	}else{
	        		bf.append(" and tcb.blacklist_status = '" +strs[0]+"'");
	        	}
		    	
		    }
        
        bf.append(" order by tcb.startDate desc,marketDate desc");
        
		// 查询本分类下的内容 page包含的节点类型为：nt:reference
		page.excute(bf.toString(), paraList.toArray(), new BlackListVO());
		return null;
	}

	/**
	 * 查询不在黑名单中的内容
	 * 
	 * @param page
	 * @param searchor
	 * @return
	 * @throws Exception
	 */
	public List queryContentNoInBlackList(PageResult page, Searchor searchor)
			throws Exception {
		String sql;
		sql = DB.getInstance().getSQLByCode(
				"blacklist.dao.BlackListDao.queryContentNoInBlackList");
		StringBuffer bf = new StringBuffer(sql);
		List paraList = new ArrayList();
		// 设置查询参数。
		// 遍历表达式的各个参数，进行处理
		for (int i = 0; i < searchor.getParams().size(); i++) {
			SearchParam param = (SearchParam) searchor.getParams().get(i);
			bf.append(" ");
			bf.append(param.getMode());
			bf.append(" ");
			// 如果是左括号开始，要添加一个(
			if (param.getBracket().equals(
					RepositoryConstants.SEARCH_PARAM_BRACKET_LEFT)) {
				bf.append(RepositoryConstants.SEARCH_PARAM_BRACKET_LEFT);
			}
			// 从类字段对应到数据库表字段
			if (param.getOperator().equals(
					RepositoryConstants.OP_LIKE_IgnoreCase)) {
				bf.append("upper(");
				bf.append(param.getProperty());
				bf.append(") like ");
				bf.append("?");

			} else {
				bf.append(param.getProperty());
				bf.append(' ');
				bf.append(param.getOperator());
				bf.append(' ');
				bf.append('?');
			}

			// 如果是右括号结束，要添加一个)
			if (param.getBracket().equals(
					RepositoryConstants.SEARCH_PARAM_BRACKET_RIGHT)) {
				bf.append(RepositoryConstants.SEARCH_PARAM_BRACKET_RIGHT);
			}
			paraList.add(param.getValue());
		}
		// 查询本分类下的内容 page包含的节点类型为：nt:reference
		page.excute(bf.toString(), paraList.toArray(), new ContentVO());
		return null;
	}	
	
	public void add(BlackListVO vo) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("add Black(" + vo + ")");
		}
		String sqlCode = "blacklist.dao.BlackListDao.INSERT";
		// 定义在sql语句中要替换的参数,

		Object[] paras = { vo.getContentId(), vo.getStartDate(),vo.getEndDate(),new Integer(vo.getType()), vo.getContentType() };
		DB.getInstance().executeBySQLCode(sqlCode, paras);
	}

	public void update(BlackListVO vo) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("add Black(" + vo + ")");
		}
		String sqlCode = "blacklist.dao.BlackListDao.UPDATE";

		Object[] paras = { vo.getStartDate(),vo.getEndDate(),new Integer(vo.getType()),
				vo.getContentId() };
		DB.getInstance().executeBySQLCode(sqlCode, paras);
	}

	public void delete(BlackListVO vo) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("add Black(" + vo + ")");
		}
		String sqlCode = "blacklist.dao.BlackListDao.DELETE";
		// 定义在sql语句中要替换的参数,

		Object[] paras = { vo.getContentId() };
		DB.getInstance().executeBySQLCode(sqlCode, paras);
	}
	
	public void deleteItem(BlackListVO vo) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("deleteItem Black(" + vo + ")");
		}
		String sqlCode = "blacklist.dao.BlackListDao.deleteItem";
		// 定义在sql语句中要替换的参数,

		Object[] paras = { vo.getBlacklistStatus(),vo.getContentId() };
		DB.getInstance().executeBySQLCode(sqlCode, paras);
	}
	

	public BlackListVO getBlackByContentId(String contentId) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("getBlackByContentId(" + contentId + ")");
		}
		String sqlCode = "blacklist.dao.BlackListDao.getBlackByContentId";
		// 定义在sql语句中要替换的参数
		Object[] paras = { contentId };

		ResultSet rs = null;
		BlackListVO black = new BlackListVO();
		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
			while (rs != null && rs.next()) {
				// 将查询结果放到vo中
				black.CopyValFromResultSet(black, rs);
			}
		} catch (SQLException e) {
			throw new DAOException("getBlackByContentId error.", e);

		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					logger.error(e);
				}
			}

		}
		return black;
	}

	public BlackListVO getContentFromTGContentByContentId(String contentId)
			throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("getBlackByContentId(" + contentId + ")");
		}
		String sqlCode = "blacklist.dao.BlackListDao.getContentFromTGContentByContentId";
		// 定义在sql语句中要替换的参数
		Object[] paras = { contentId };

		ResultSet rs = null;
		BlackListVO black = new BlackListVO();
		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
			while (rs != null && rs.next()) {
				// 将查询结果放到vo中
				black.setContentId(rs.getString("contentid"));
				black.setName(rs.getString("name"));
				black.setSpName(rs.getString("spname"));
                black.setContentType(rs.getString("subtype"));
			}
		} catch (SQLException e) {
			throw new DAOException("getContentFromTGContentByContentId error.",
					e);

		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					logger.error(e);
				}
			}

		}
		return black;
	}

	/**
	 * 批量导入
	 * 
	 * @param list
	 * @throws Exception
	 */
	public void batchImport(List list) throws Exception {
		BlackListVO vo = null;
		String sqlCode = "blacklist.dao.BlackListDao.INSERT";
		Object mutiParas[][] = new Object[list.size()][4];
		for (int i = 0; i < list.size(); i++) {
			vo = (BlackListVO) list.get(i);
			mutiParas[i][0] = vo.getContentId();
			mutiParas[i][1] = vo.getStartDate();
			mutiParas[i][1] = vo.getEndDate();
			mutiParas[i][1] = new Integer(vo.getType());
		}
		try {
			DB.getInstance().executeBatchBySQLCode(sqlCode, mutiParas);
		} catch (DAOException e) {
			throw new BOException("批量添加黑名单异常");
		}
	}
	
	public int importUpBlack(BlackListVO vo)throws Exception{
		Object[] updateparas = new Object[]{vo.getStartDate(),vo.getEndDate(),new Integer(vo.getType()),vo.getContentId()};
		Object[] insertparas = new Object[]{vo.getContentId(),vo.getStartDate(),vo.getEndDate(),new Integer(vo.getType()),vo.getContentType()};
		if (logger.isDebugEnabled()){
            logger.debug("importUpBlack(" + updateparas +','+insertparas+ ")");
        }
        String sqlCode="blacklist.dao.BlackListDao.UPDATE";
        int result=DB.getInstance().executeBySQLCode(sqlCode, updateparas);
        if(result<1){//更新返回为0，没有相关记录，插入数据
        	 sqlCode = "blacklist.dao.BlackListDao.INSERT";
        	 result=DB.getInstance().executeBySQLCode(sqlCode, insertparas);
        }
        return result;		
	}
	
	/**
	 * 黑名单提交审批
	 * 
	 * @param tdb
	 * @param contentId 应用内容Id
	 * @throws BOException
	 */
	public void submitApproval(TransactionDB tdb,String contentId) throws BOException{
		try {
			tdb.executeBySQLCode("blacklist.dao.BlackListDao.submitApproval", new Object[]{contentId});
		} catch (DAOException e) {
			logger.error("黑名单提交审批异常",e);
			throw new BOException("批量添加黑名单异常");
		}
	}
	/**
	 * 更新内容黑名单审批表
	 * 
	 * @param tdb
	 * @param contentId 应用内容Id
	 * @param status 审批状态
	 * @param operator 操作人
	 * @throws BOException
	 */
	public void approvalBlackList(TransactionDB tdb, String contentId,
			String status,String operator) throws BOException {
		ResultSet rs = null;
		try {
			rs = DB.getInstance().queryBySQLCode(
					"blacklist.dao.approvalBlackList.SELECT",
					new Object[] { contentId });
			if (rs != null && rs.next()) {
				if ("2".equals(status)) {
					tdb.executeBySQLCode("blacklist.dao.approvalBlackList.UPDATE1", new Object[] { operator,contentId });
				} else {
					tdb.executeBySQLCode("blacklist.dao.approvalBlackList.UPDATE2", new Object[] { operator,contentId });
				}
			} else {
				if ("2".equals(status)) {
					tdb.executeBySQLCode("blacklist.dao.approvalBlackList.INSERT1", new Object[] { operator,contentId });
				} else {
					tdb.executeBySQLCode("blacklist.dao.approvalBlackList.INSERT2", new Object[] { operator,contentId });
				}
			}
		} catch (DAOException e) {
			logger.error("更新内容黑名单审批表异常", e);
			throw new BOException("更新内容黑名单审批表异常");
		} catch (SQLException e) {
			logger.error("更新内容黑名单审批表异常", e);
			throw new BOException("更新内容黑名单审批表异常");
		}finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					logger.error(e);
				}
			}

		}
	}
	
	/**
	 * 查询黑名单
	 * 
	 * @param page
	 * @param searchor
	 * @return
	 * @throws Exception
	 */
	public List queryBlackListOperation(PageResult page, Searchor searchor,String aprovalStatus)
			throws Exception {
		String sql;
		sql = DB.getInstance().getSQLByCode(
				"blacklist.dao.BlackListDao.queryBlackListOperation");
		StringBuffer bf = new StringBuffer(sql);
		List paraList = new ArrayList();
		// 设置查询参数。
		// 遍历表达式的各个参数，进行处理
		for (int i = 0; i < searchor.getParams().size(); i++) {
			SearchParam param = (SearchParam) searchor.getParams().get(i);
			bf.append(" ");
			bf.append(param.getMode());
			bf.append(" ");
			// 如果是左括号开始，要添加一个(
			if (param.getBracket().equals(
					RepositoryConstants.SEARCH_PARAM_BRACKET_LEFT)) {
				bf.append(RepositoryConstants.SEARCH_PARAM_BRACKET_LEFT);
			}
			// 从类字段对应到数据库表字段
			if (param.getOperator().equals(
					RepositoryConstants.OP_LIKE_IgnoreCase)) {
				bf.append("upper(");
				bf.append(param.getProperty());
				bf.append(") like ");
				bf.append("?");

			} else {
				bf.append(param.getProperty());
				bf.append(' ');
				bf.append(param.getOperator());
				bf.append(' ');
				bf.append('?');
			}

			// 如果是右括号结束，要添加一个)
			if (param.getBracket().equals(
					RepositoryConstants.SEARCH_PARAM_BRACKET_RIGHT)) {
				bf.append(RepositoryConstants.SEARCH_PARAM_BRACKET_RIGHT);
			}
			paraList.add(param.getValue());
		}
		
		 if(aprovalStatus != null && !"".equals(aprovalStatus) && !"4".equals(aprovalStatus)){
	        	String[] strs = aprovalStatus.split(",");
	        	if(strs.length > 1){
	        		bf.append(" and tcb.blacklist_status in ('" + strs[0] + "','"+strs[1]+"')");
	        	}else{
	        		bf.append(" and tcb.blacklist_status = '" +strs[0]+"'");
	        	}
		    	
		    }
        
        bf.append(" order by tcb.startDate desc,marketDate desc");
        
		// 查询本分类下的内容 page包含的节点类型为：nt:reference
		page.excute(bf.toString(), paraList.toArray(), new BlackListOperationVO());
		return null;
	}
	
	/**
	 * 查询内容黑名单表
	 * 
	 * @param contentId 应用内容Id
	 * @return
	 * @throws BOException
	 */
	public Map<String,Object> queryBlackListItem(String contentId) throws BOException{
		ResultSet rs = null;
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			rs = DB.getInstance().queryBySQLCode("blacklist.dao.BlackListDao.queryBlackListItem", new Object[]{contentId});
			while (rs != null && rs.next()) {
				map.put("contentId", rs.getString("contentid"));
				map.put("blacklist_status", rs.getString("blacklist_status"));
				map.put("delpro_status", rs.getString("delpro_status"));
			}
		} catch (SQLException e) {
			logger.error("查询内容黑名单表异常", e);
			throw new BOException("查询内容黑名单表异常", e);
		} catch (DAOException e) {
			logger.error("查询内容黑名单审批表异常", e);
			throw new BOException("查询内容黑名单表异常", e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					logger.error(e);
				}
			}
	}
		return map;
  }
	
	/**
	 * 黑名单提交审批
	 * 
	 * @param tdb
	 * @param contentId 应用内容Id
	 * @param approvalStatus 审批状态
	 * @param status 删除状态
	 * @throws BOException
	 */
	public void approval(TransactionDB tdb,String contentId,String approvalStatus,String status) throws BOException{
		try {
			tdb.executeBySQLCode("blacklist.dao.BlackListDao.approval", new Object[]{approvalStatus,status,contentId});
		} catch (DAOException e) {
			logger.error("黑名单提交审批异常",e);
			throw new BOException("批量添加黑名单异常");
		}
	}
}
