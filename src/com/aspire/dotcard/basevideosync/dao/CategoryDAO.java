package com.aspire.dotcard.basevideosync.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.RowSet;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basevideosync.vo.POMSCategoryQueryVO;
import com.aspire.ponaadmin.web.db.TransactionDB;
/**
 * 
 * @author duyongchun
 *
 */
public class CategoryDAO {

	/**
	 * ��¼��־��ʵ������
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(CategoryDAO.class);

	/**
	 * singletonģʽ��ʵ��
	 */
	private static CategoryDAO instance = new CategoryDAO();

	/**
	 * ���췽������singletonģʽ����
	 */
	private CategoryDAO() {
	}

	/**
	 * ��ȡʵ��
	 * 
	 * @return ʵ��
	 */
	public static CategoryDAO getInstance() {
		return instance;
	}

	/**
	 * �ύ����
	 * 
	 * @param tdb
	 * @param categoryId
	 *            ���ܱ��
	 * @throws DAOException
	 */
	public void approvalCategory(TransactionDB tdb, String categoryId)
			throws DAOException {
		String sqlCode = "com.aspire.dotcard.basevideosync.dao.CategoryDAO.approvalCategory";

		try {
			tdb.executeBySQLCode(sqlCode, new Object[] { categoryId });
		} catch (DAOException e) {
			logger.error("���ݻ���ID��������ʱ�����쳣:", e);
			throw new DAOException("���ݻ���ID��������ʱ�����쳣:", e);
		}
	}

	/**
	 * ��Ƶ����������
	 * 
	 * @param tdb
	 * @param categoryId
	 *            ���ܱ��ID
	 * @param status
	 *            ����״̬
	 * @param operation
	 *            ��������
	 * @param operator
	 *            ������
	 * @throws BOException
	 */
	public void approvalCategory(TransactionDB tdb, String categoryId,
			String status, String operation, String operator)
			throws BOException {
		//status=2�ǶԲ����˵Ĳ���,status!=2�Ƕ������˵Ĳ���
		ResultSet rs = null;
		try {
			rs = DB.getInstance()
					.queryBySQLCode(
							"com.aspire.ponaadmin.web.newvideosys.dao.CategoryDAO.approvalCategory.SELECT",
							//sql:select id from T_V_CATEGORY_OPERATION o where o.categoryid =? and o.OPERATION=?
							new Object[] { categoryId, operation });
			if (rs != null && rs.next()) {
				if ("2".equals(status)) {
					tdb.executeBySQLCode(
							"com.aspire.ponaadmin.web.newvideosys.dao.CategoryDAO.approvalCategory.UPDATE1",
							//sql:update T_V_CATEGORY_OPERATION o set o.operator =? ,o.operator_time=sysdate
							//where o.categoryid = ? and o.OPERATION=?
							new Object[] { operator, categoryId, operation });
				} else {
					tdb.executeBySQLCode(
							"com.aspire.ponaadmin.web.newvideosys.dao.CategoryDAO.approvalCategory.UPDATE2",
							//sql:update T_V_CATEGORY_OPERATION o set o.approval =? ,o.approval_time=sysdate 
							//where o.categoryid = ? and o.OPERATION=?
							new Object[] { operator, categoryId, operation });
				}
			} else {
				//����û�в鵽����������
				if ("2".equals(status)) {
					tdb.executeBySQLCode(
							"com.aspire.ponaadmin.web.newvideosys.dao.CategoryDAO.approvalCategory.INSERT1",
							//sql:insert into T_V_CATEGORY_OPERATION(id,operator,categoryid,Operator_Time,OPERATION)
							//values(SEQ_V_APPROVAL_ID.Nextval,?,?,sysdate,?)
							new Object[] { operator, categoryId, operation });
				} else {
					tdb.executeBySQLCode(
							"com.aspire.ponaadmin.web.newvideosys.dao.CategoryDAO.approvalCategory.INSERT2",
							//SQL:insert into T_V_CATEGORY_OPERATION(id,approval,categoryid,approval_time,OPERATION)
							//values(SEQ_V_APPROVAL_ID.Nextval,?,?,sysdate,?)
							new Object[] { operator, categoryId, operation });
				}
			}
		} catch (DAOException e) {
			logger.error("������Ƶ�����������쳣", e);
			throw new BOException("������Ƶ�����������쳣");
		} catch (SQLException e) {
			logger.error("������Ƶ�����������쳣", e);
			throw new BOException("������Ƶ�����������쳣");
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					logger.error(e);
				}
			}

		}
	}
	public POMSCategoryQueryVO query(Object[]obj)throws BOException {
		ResultSet rs = null;
		POMSCategoryQueryVO pcq=null;
		String sql="select cname,categoryid,cdesc,pic,sortid,video_status,parentcid from t_v_category where categoryid=?";
		try {
			rs=DB.getInstance().query(sql, obj);
			if(rs.next()){
				pcq=new POMSCategoryQueryVO();
				pcq.setCategoryID(rs.getString("CATEGORYID"));
				pcq.setcDesc(rs.getString("CDESC")!=null?rs.getString("CDESC"):"");
				pcq.setCname(rs.getString("CNAME"));
				pcq.setPic(rs.getString("PIC")!=null?rs.getString("PIC"):"");
				pcq.setSortID(String.valueOf(rs.getInt("SORTID")));
				pcq.setVideo_status(rs.getString("VIDEO_STATUS"));
				if("-1"==rs.getString("PARENTCID")){
					pcq.setPath("������");
				}else{
					String sql2="select cname from t_v_category where categoryid=?";
					String[] obj2={rs.getString("PARENTCID")};
					RowSet rs2=DB.getInstance().query(sql2, obj2);
					if(rs2.next()){
						pcq.setPath(rs2.getString("CNAME")+">>"+rs.getString("CNAME"));
					}
				}
			}
		
		} catch (Exception e) {
			logger.error("��Ƶ��ѯʧ��");
			e.printStackTrace();
		}
		return pcq;
	}
	/**
	 * �ж�categoryId�Ƿ���Pid�µ��ӻ���
	 * @param categoryId
	 * @param Pid
	 * @return
	 */
	public boolean isSubCategory(String categoryId,String Pid){
		ResultSet rs = null;
		String sql="select 1 from (select categoryid from t_v_category c start with c.categoryid = ? connect by prior PARENTCID = categoryid)a where a.categoryid=?";
		Object[] param = {categoryId,Pid};
		boolean flag= false;
		try {
			rs = DB.getInstance().query(sql, param);
			if(rs!=null&&rs.next()){
				flag=true;
			}
		} catch (Exception e) {
			
		}finally{
			DB.close(rs);
		}
		return flag;
	}
}
