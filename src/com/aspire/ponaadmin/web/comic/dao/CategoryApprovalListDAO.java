package com.aspire.ponaadmin.web.comic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.page.PageVOInterface;
import com.aspire.ponaadmin.web.comic.vo.CategoryApprovalVO;

public class CategoryApprovalListDAO {
	

	private final static JLogger LOGGER = LoggerFactory.getLogger(CategoryApprovalListDAO.class);
	
	private static CategoryApprovalListDAO instance = new CategoryApprovalListDAO();
	private CategoryApprovalListDAO(){}
	public static CategoryApprovalListDAO getInstance(){
		return instance;
	}
	
	/**
	 * 分页查询审批
	 * @param page
	 * @param map
	 * @throws DAOException
	 */
	public void queryCategoryApprovalList(PageResult page,Map map) throws DAOException{
		if (LOGGER.isDebugEnabled())
        {
			LOGGER.debug("queryCategoryApprovalList( )") ;
        }
		
        String sqlCode = "com.aspire.ponaadmin.web.comic.dao.CategoryApprovalListDAO.queryCategoryApprovalList";
        
        List paras = new ArrayList();
        try
        {
//        	 sql = SQLCode.getInstance().getSQLStatement(sqlCode);
        	StringBuffer sqlBuffer = new StringBuffer(DB.getInstance().getSQLByCode(sqlCode));
        	/**
        	 * 用于标识处理方式： 1 货架分类管理; 2 货架商品管理
        	 */
        	String operation = (String) map.get("operation");
        	/**
        	 * 货架ID
        	 */
     		String categoryId = (String) map.get("categoryId");
     		/**
     		 * 货架名称
     		 */
     		String categoryName = (String) map.get("categoryName");
     		/**
     		 * 父货架ID
     		 */
     		String parentCategoryId = (String) map.get("parentCategoryId");
     		/**
     		 * 审批状态
     		 */
     		String approvalStatus = (String) map.get("approvalStatus");
     		// 构造搜索的sql和参数
     		if (!"".equals(categoryId))
     		{
     			sqlBuffer.append(" and C.categoryid in ("+categoryId+")");
     		}
     		if (!"".equals(categoryName))
     		{
     			sqlBuffer.append(" and C.CATEGORYNAME like '%"+categoryName+"%'");
     		}
     		if (!"".equals(parentCategoryId))
     		{
     			sqlBuffer.append(" and C.PARENTCATEGORYID = ? ");
     			paras.add(parentCategoryId);
     		}
     		if (!"".equals(approvalStatus)&&!"-1".equals(approvalStatus))
     		{
     			/**
     			 * 不同的处理。用不用的审批状态标识
     			 *  1. 动漫货架管理; 2. 动漫商品管理
     			 */
     			if("1".equals(operation)){
     				sqlBuffer.append(" and C.ANIME_STATUS = ? ");
     			}else if("2".equals(operation)){
     				sqlBuffer.append(" and C.goods_status = ? ");
     				sqlBuffer.append(" and c.delflag=0");
     			}
     			
     			paras.add(approvalStatus);
     		}
     		
     		if(!"".equals(operation)){
 				sqlBuffer.append(" and CO.operation = ? ");
 				paras.add(operation);
 			}
     		//查询全部和待审批的，按照操作时间倒序排列，否则是按审批时间倒序排列
     		if("-1".equals(approvalStatus)||"2".equals(approvalStatus)){
     			sqlBuffer.append(" order by operator_time desc");
     		}else{
     			sqlBuffer.append(" order by approval_time desc");
     		}
     		
        	page.excute(sqlBuffer.toString(), paras.toArray(),
					new CategoryApprovalPageVO(operation));

        }
        catch (Exception e)
		{
			throw new DAOException(
					"分页查询审批异常。", e);
		}
	}
	
	/**
     * 应用类分页读取VO的实现类
     */
    private class CategoryApprovalPageVO implements PageVOInterface
    {
    	/**
    	 * 用于标识处理方式： 1 货架分类管理; 2 货架商品管理
    	 */
    	private String operation;
    	
    	public CategoryApprovalPageVO(String operation){
    		this.operation = operation;
    	};
    	 public void CopyValFromResultSet(Object content, ResultSet rs)
         throws SQLException{
    		 CategoryApprovalVO categoryApproval = (CategoryApprovalVO)content;
    		 categoryApproval.setCategoryId(rs.getString("categoryId"));
    		 categoryApproval.setCategoryName(rs.getString("CATEGORYNAME"));
    		 categoryApproval.setSortId(rs.getString("sortId"));
    		 if("1".equals(this.operation)){
    			 categoryApproval.setApprovalStatus(rs.getString("ANIME_STATUS"));
    		 }else if("2".equals(this.operation)){
    			 categoryApproval.setApprovalStatus(rs.getString("goods_status"));
    		 }
    		 categoryApproval.setOperation(this.operation);
    		 String operator = rs.getString("operator");
    		 categoryApproval.setOperator(operator==null?"":operator);
    		 categoryApproval.setOperatorTime(rs.getTimestamp("operator_Time"));
    		 String approval = rs.getString("approval");
    		 categoryApproval.setApproval(approval==null?"":approval);
    		 categoryApproval.setApprovalTime(rs.getTimestamp("approval_Time"));
    		 
    	 }
    	 public Object createObject()
         {

             return new CategoryApprovalVO();
         }
    }

}
