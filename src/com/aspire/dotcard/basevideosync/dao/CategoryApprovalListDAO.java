package com.aspire.dotcard.basevideosync.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basevideosync.vo.CategoryApprovalVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.page.PageVOInterface;
/**
 * 
 * @author duyongchun
 *
 */

public class CategoryApprovalListDAO {


	private final static JLogger LOGGER = LoggerFactory
			.getLogger(CategoryApprovalListDAO.class);

	private static CategoryApprovalListDAO instance = new CategoryApprovalListDAO();

	private CategoryApprovalListDAO() {
	}

	public static CategoryApprovalListDAO getInstance() {
		return instance;
	}

	/**
	 * ��ҳ��ѯ����
	 * 
	 * @param page
	 * @param map
	 * @throws DAOException
	 */
	public void queryCategoryApprovalList(PageResult page, Map map)
			throws DAOException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("queryCategoryApprovalList( )");
		}

		String sqlCode = "com.aspire.dotcard.basevideosync.dao.queryCategoryApprovalList";

		List paras = new ArrayList();
		try {
			// sql = SQLCode.getInstance().getSQLStatement(sqlCode);
			StringBuffer sqlBuffer = new StringBuffer(DB.getInstance()
					.getSQLByCode(sqlCode));
			/**
			 * ���ڱ�ʶ����ʽ�� 1 ���ܷ������; 2 ������Ʒ����
			 */
//			System.err.println("�д����"+sqlBuffer);
			String operation = (String) map.get("operation");
			/**
			 * ����ID
			 */
			String categoryId = (String) map.get("categoryId");
			/**
			 * ��������
			 */
			String categoryName = (String) map.get("categoryName");
			/**
			 * ������ID
			 */
			String parentCategoryId = (String) map.get("parentCategoryId");
			/**
			 * ����״̬
			 */
			String approvalStatus = (String) map.get("approvalStatus");
			// ����������sql�Ͳ���
			if (!"".equals(categoryId)) {
				sqlBuffer.append(" and C.categoryid in (" + categoryId + ")");
			}
			if (!"".equals(categoryName)) {
				sqlBuffer.append(" and C.CNAME like '%" + categoryName
						+ "%'");
			}
			if (!"".equals(parentCategoryId)) {
				sqlBuffer.append(" and C.PARENTCID = ? ");
				paras.add(parentCategoryId);
			}
			if (!"".equals(approvalStatus)&& !"-1".equals(approvalStatus)) {
				/**
				 * ��ͬ�Ĵ����ò��õ�����״̬��ʶ 1. video���ܹ���; 2. video��Ʒ����
				 */
				if ("1".equals(operation)) {
					sqlBuffer.append(" and C.video_status = ? ");
				} else if ("2".equals(operation)) {
					sqlBuffer.append(" and C.goods_status = ? ");
				}
				paras.add(approvalStatus);
			}

			if (!"".equals(operation)) {
				sqlBuffer.append(" and CO.operation = ? ");
				paras.add(operation);
			}
			// ��ѯȫ���ʹ������ģ����ղ���ʱ�䵹�����У������ǰ�����ʱ�䵹������
			if ("-1".equals(approvalStatus) || "2".equals(approvalStatus)) {
				sqlBuffer.append(" order by operator_time desc");
			} else {
				sqlBuffer.append(" order by approval_time desc");
			}
		//	System.err.println("����"+sqlBuffer.toString());
			page.excute(sqlBuffer.toString(), paras.toArray(),new CategoryApprovalPageVO(operation));

		} catch (Exception e) {
			throw new DAOException("��ҳ��ѯ�����쳣��", e);
		}
	}

	/**
	 * Ӧ�����ҳ��ȡVO��ʵ����
	 */
	private class CategoryApprovalPageVO implements PageVOInterface {
		/**
		 * ���ڱ�ʶ����ʽ�� 1 ���ܷ������; 2 ������Ʒ����
		 */
		private String operation;

		public CategoryApprovalPageVO(String operation) {
			this.operation = operation;
		};

		public void CopyValFromResultSet(Object content, ResultSet rs)
				throws SQLException {
			CategoryApprovalVO categoryApproval = (CategoryApprovalVO) content;
			categoryApproval.setCategoryId(rs.getString("categoryid"));
			categoryApproval.setCategoryName(rs.getString("CNAME"));
			categoryApproval.setSortId(String.valueOf(rs.getInt("sortId")));
			if ("1".equals(this.operation)) {
				categoryApproval
						.setApprovalStatus(rs.getString("video_status"));
			} else if ("2".equals(this.operation)) {
				categoryApproval
						.setApprovalStatus(rs.getString("goods_status"));
			}
			categoryApproval.setOperation(this.operation);
			String operator = rs.getString("operator");
			categoryApproval.setOperator(operator == null ? "" : operator);
			categoryApproval.setOperatorTime(rs.getTimestamp("operator_Time"));
			String approval = rs.getString("approval");
			categoryApproval.setApproval(approval == null ? "" : approval);
			categoryApproval.setApprovalTime(rs.getTimestamp("approval_Time"));

		}

		public Object createObject() {

			return new CategoryApprovalVO();
		}
	}

}
