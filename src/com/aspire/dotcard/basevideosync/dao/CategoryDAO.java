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
	 * 记录日志的实例对象
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(CategoryDAO.class);

	/**
	 * singleton模式的实例
	 */
	private static CategoryDAO instance = new CategoryDAO();

	/**
	 * 构造方法，由singleton模式调用
	 */
	private CategoryDAO() {
	}

	/**
	 * 获取实例
	 * 
	 * @return 实例
	 */
	public static CategoryDAO getInstance() {
		return instance;
	}

	/**
	 * 提交审批
	 * 
	 * @param tdb
	 * @param categoryId
	 *            货架编号
	 * @throws DAOException
	 */
	public void approvalCategory(TransactionDB tdb, String categoryId)
			throws DAOException {
		String sqlCode = "com.aspire.dotcard.basevideosync.dao.CategoryDAO.approvalCategory";

		try {
			tdb.executeBySQLCode(sqlCode, new Object[] { categoryId });
		} catch (DAOException e) {
			logger.error("根据货架ID审批货架时发生异常:", e);
			throw new DAOException("根据货架ID审批货架时发生异常:", e);
		}
	}

	/**
	 * 视频货架审批表
	 * 
	 * @param tdb
	 * @param categoryId
	 *            货架编号ID
	 * @param status
	 *            审批状态
	 * @param operation
	 *            操作对象
	 * @param operator
	 *            操作人
	 * @throws BOException
	 */
	public void approvalCategory(TransactionDB tdb, String categoryId,
			String status, String operation, String operator)
			throws BOException {
		//status=2是对操作人的操作,status!=2是对审批人的操作
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
				//假如没有查到结果的情况下
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
			logger.error("更新视频货架审批表异常", e);
			throw new BOException("更新视频货架审批表异常");
		} catch (SQLException e) {
			logger.error("更新视频货架审批表异常", e);
			throw new BOException("更新视频货架审批表异常");
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
					pcq.setPath("根货架");
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
			logger.error("视频查询失败");
			e.printStackTrace();
		}
		return pcq;
	}
	/**
	 * 判断categoryId是否是Pid下的子货架
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
