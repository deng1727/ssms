package com.aspire.ponaadmin.web.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.page.PageVOInterface;
import com.aspire.ponaadmin.web.channelUser.vo.ChannelCategoryManageVO;

public class CategoryApprovalListDAO {
	private final static JLogger LOGGER = LoggerFactory.getLogger(CategoryApprovalListDAO.class);
	
	private static CategoryApprovalListDAO instance = new CategoryApprovalListDAO();
	private CategoryApprovalListDAO(){}
	public static CategoryApprovalListDAO getInstance(){
		return instance;
	}
	
	/**
	 * ��ҳ��ѯ����
	 * @param page
	 * @param map
	 * @throws DAOException
	 */
	public void queryCategoryApprovalList(PageResult page,Map map) throws DAOException{
		if (LOGGER.isDebugEnabled())
        {
			LOGGER.debug("queryCategoryApprovalList( )") ;
        }
		
        String sql = "SELECT CO.operator,CO.categoryid,CO.operator_time,CO.approval,CO.approval_time,CO.operation,C.ID,C.NAME,C.SORTID,C.classify_status,C.goods_status FROM T_R_CATEGORY_OPERATION CO,t_r_category C WHERE CO.categoryid=C.categoryid";
        
        List paras = new ArrayList();
        try
        {
//        	 sql = SQLCode.getInstance().getSQLStatement(sqlCode);
        	StringBuffer sqlBuffer = new StringBuffer(sql);
        	/**
        	 * ���ڱ�ʶ����ʽ�� 1 ���ܷ������; 2 ������Ʒ����
        	 */
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
     		if (!"".equals(categoryId))
     		{
     			sqlBuffer.append(" and C.categoryid in ("+categoryId+")");
     		}
     		if (!"".equals(categoryName))
     		{
     			sqlBuffer.append(" and C.Name like '%"+categoryName+"%'");
     		}
     		if (!"".equals(parentCategoryId))
     		{
     			sqlBuffer.append(" and C.PARENTCATEGORYID = ? ");
     			paras.add(parentCategoryId);
     		}
     		if (!"".equals(approvalStatus)&&!"-1".equals(approvalStatus))
     		{
     			/**
     			 * ��ͬ�Ĵ����ò��õ�����״̬��ʶ
     			 *  1 ���ܷ������; 2 ������Ʒ����
     			 */
     			if("1".equals(operation)){
     				sqlBuffer.append(" and C.classify_status = ? ");
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
     		//��ѯȫ���ʹ������ģ����ղ���ʱ�䵹�����У������ǰ�����ʱ�䵹������
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
					"��ҳ��ѯ�����쳣��", e);
		}
	}
	
	/**
     * Ӧ�����ҳ��ȡVO��ʵ����
     */
    private class CategoryApprovalPageVO implements PageVOInterface
    {
    	/**
    	 * ���ڱ�ʶ����ʽ�� 1 ���ܷ������; 2 ������Ʒ����
    	 */
    	private String operation;
    	
    	public CategoryApprovalPageVO(String operation){
    		this.operation = operation;
    	};
    	 public void CopyValFromResultSet(Object content, ResultSet rs)
         throws SQLException{
    		 CategoryApprovalVO categoryApproval = (CategoryApprovalVO)content;
    		 categoryApproval.setId(rs.getString("id"));
    		 categoryApproval.setCategoryId(rs.getString("categoryId"));
    		 categoryApproval.setCategoryName(rs.getString("Name"));
    		 categoryApproval.setSortId(rs.getString("sortId"));
    		 if("1".equals(this.operation)){
    			 categoryApproval.setApprovalStatus(rs.getString("classify_status"));
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
