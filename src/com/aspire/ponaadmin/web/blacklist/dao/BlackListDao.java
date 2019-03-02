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
	 * ��ѯ������
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
		// ���ò�ѯ������
		// �������ʽ�ĸ������������д���
		for (int i = 0; i < searchor.getParams().size(); i++) {
			SearchParam param = (SearchParam) searchor.getParams().get(i);
			bf.append(" ");
			bf.append(param.getMode());
			bf.append(" ");
			// ����������ſ�ʼ��Ҫ���һ��(
			if (param.getBracket().equals(
					RepositoryConstants.SEARCH_PARAM_BRACKET_LEFT)) {
				bf.append(RepositoryConstants.SEARCH_PARAM_BRACKET_LEFT);
			}
			// �����ֶζ�Ӧ�����ݿ���ֶ�
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

			// ����������Ž�����Ҫ���һ��)
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
        
		// ��ѯ�������µ����� page�����Ľڵ�����Ϊ��nt:reference
		page.excute(bf.toString(), paraList.toArray(), new BlackListVO());
		return null;
	}

	/**
	 * ��ѯ���ں������е�����
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
		// ���ò�ѯ������
		// �������ʽ�ĸ������������д���
		for (int i = 0; i < searchor.getParams().size(); i++) {
			SearchParam param = (SearchParam) searchor.getParams().get(i);
			bf.append(" ");
			bf.append(param.getMode());
			bf.append(" ");
			// ����������ſ�ʼ��Ҫ���һ��(
			if (param.getBracket().equals(
					RepositoryConstants.SEARCH_PARAM_BRACKET_LEFT)) {
				bf.append(RepositoryConstants.SEARCH_PARAM_BRACKET_LEFT);
			}
			// �����ֶζ�Ӧ�����ݿ���ֶ�
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

			// ����������Ž�����Ҫ���һ��)
			if (param.getBracket().equals(
					RepositoryConstants.SEARCH_PARAM_BRACKET_RIGHT)) {
				bf.append(RepositoryConstants.SEARCH_PARAM_BRACKET_RIGHT);
			}
			paraList.add(param.getValue());
		}
		// ��ѯ�������µ����� page�����Ľڵ�����Ϊ��nt:reference
		page.excute(bf.toString(), paraList.toArray(), new ContentVO());
		return null;
	}	
	
	public void add(BlackListVO vo) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("add Black(" + vo + ")");
		}
		String sqlCode = "blacklist.dao.BlackListDao.INSERT";
		// ������sql�����Ҫ�滻�Ĳ���,

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
		// ������sql�����Ҫ�滻�Ĳ���,

		Object[] paras = { vo.getContentId() };
		DB.getInstance().executeBySQLCode(sqlCode, paras);
	}
	
	public void deleteItem(BlackListVO vo) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("deleteItem Black(" + vo + ")");
		}
		String sqlCode = "blacklist.dao.BlackListDao.deleteItem";
		// ������sql�����Ҫ�滻�Ĳ���,

		Object[] paras = { vo.getBlacklistStatus(),vo.getContentId() };
		DB.getInstance().executeBySQLCode(sqlCode, paras);
	}
	

	public BlackListVO getBlackByContentId(String contentId) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("getBlackByContentId(" + contentId + ")");
		}
		String sqlCode = "blacklist.dao.BlackListDao.getBlackByContentId";
		// ������sql�����Ҫ�滻�Ĳ���
		Object[] paras = { contentId };

		ResultSet rs = null;
		BlackListVO black = new BlackListVO();
		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
			while (rs != null && rs.next()) {
				// ����ѯ����ŵ�vo��
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
		// ������sql�����Ҫ�滻�Ĳ���
		Object[] paras = { contentId };

		ResultSet rs = null;
		BlackListVO black = new BlackListVO();
		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
			while (rs != null && rs.next()) {
				// ����ѯ����ŵ�vo��
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
	 * ��������
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
			throw new BOException("������Ӻ������쳣");
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
        if(result<1){//���·���Ϊ0��û����ؼ�¼����������
        	 sqlCode = "blacklist.dao.BlackListDao.INSERT";
        	 result=DB.getInstance().executeBySQLCode(sqlCode, insertparas);
        }
        return result;		
	}
	
	/**
	 * �������ύ����
	 * 
	 * @param tdb
	 * @param contentId Ӧ������Id
	 * @throws BOException
	 */
	public void submitApproval(TransactionDB tdb,String contentId) throws BOException{
		try {
			tdb.executeBySQLCode("blacklist.dao.BlackListDao.submitApproval", new Object[]{contentId});
		} catch (DAOException e) {
			logger.error("�������ύ�����쳣",e);
			throw new BOException("������Ӻ������쳣");
		}
	}
	/**
	 * �������ݺ�����������
	 * 
	 * @param tdb
	 * @param contentId Ӧ������Id
	 * @param status ����״̬
	 * @param operator ������
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
			logger.error("�������ݺ������������쳣", e);
			throw new BOException("�������ݺ������������쳣");
		} catch (SQLException e) {
			logger.error("�������ݺ������������쳣", e);
			throw new BOException("�������ݺ������������쳣");
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
	 * ��ѯ������
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
		// ���ò�ѯ������
		// �������ʽ�ĸ������������д���
		for (int i = 0; i < searchor.getParams().size(); i++) {
			SearchParam param = (SearchParam) searchor.getParams().get(i);
			bf.append(" ");
			bf.append(param.getMode());
			bf.append(" ");
			// ����������ſ�ʼ��Ҫ���һ��(
			if (param.getBracket().equals(
					RepositoryConstants.SEARCH_PARAM_BRACKET_LEFT)) {
				bf.append(RepositoryConstants.SEARCH_PARAM_BRACKET_LEFT);
			}
			// �����ֶζ�Ӧ�����ݿ���ֶ�
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

			// ����������Ž�����Ҫ���һ��)
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
        
		// ��ѯ�������µ����� page�����Ľڵ�����Ϊ��nt:reference
		page.excute(bf.toString(), paraList.toArray(), new BlackListOperationVO());
		return null;
	}
	
	/**
	 * ��ѯ���ݺ�������
	 * 
	 * @param contentId Ӧ������Id
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
			logger.error("��ѯ���ݺ��������쳣", e);
			throw new BOException("��ѯ���ݺ��������쳣", e);
		} catch (DAOException e) {
			logger.error("��ѯ���ݺ������������쳣", e);
			throw new BOException("��ѯ���ݺ��������쳣", e);
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
	 * �������ύ����
	 * 
	 * @param tdb
	 * @param contentId Ӧ������Id
	 * @param approvalStatus ����״̬
	 * @param status ɾ��״̬
	 * @throws BOException
	 */
	public void approval(TransactionDB tdb,String contentId,String approvalStatus,String status) throws BOException{
		try {
			tdb.executeBySQLCode("blacklist.dao.BlackListDao.approval", new Object[]{approvalStatus,status,contentId});
		} catch (DAOException e) {
			logger.error("�������ύ�����쳣",e);
			throw new BOException("������Ӻ������쳣");
		}
	}
}
