package com.aspire.ponaadmin.web.comic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.comic.vo.CategoryVO;
import com.aspire.ponaadmin.web.db.TransactionDB;


public class CategoryDAO
{

    /**
     * 记录日志的实例对象
     */
    protected static JLogger logger = LoggerFactory.getLogger(CategoryDAO.class);

    /**
     * singleton模式的实例
     */
    private static CategoryDAO instance = new CategoryDAO();

    /**
     * 构造方法，由singleton模式调用
     */
    private CategoryDAO(){
    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static CategoryDAO getInstance()
    {
        return instance;
    }

    public CategoryVO queryCategoryVO(String categoryId)
                    throws DAOException
    {

        String sqlCode = "com.aspire.ponaadmin.web.comic.dao.CategoryDAO.queryCategoryVO.SELECT";
        ResultSet rs = null;
        CategoryVO vo = new CategoryVO();
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode,
                                                 new Object[] { categoryId });

            if (rs.next()){
                vo.setCategoryId(rs.getString("CATEGORYID"));
                vo.setCategoryName(rs.getString("CATEGORYNAME"));                                 
                vo.setCategoryValue(rs.getString("CATEGORYVALUE"));                    
                vo.setParentCategoryId(rs.getString("PARENTCATEGORYID"));                            
                vo.setType(rs.getString("TYPE"));           
                vo.setPicture(rs.getString("PICTURE"));                                    
                vo.setDelFlag(rs.getString("DELFLAG"));                                     
                vo.setCategoryDesc(rs.getString("CATEGORYDESC"));                                
                vo.setSortId(rs.getString("SORTID"));                                       
                vo.setLogo1(rs.getString("LOGO1"));                                    
                vo.setLogo2(rs.getString("LOGO2"));                                        
                vo.setLogo3(rs.getString("LOGO3"));
                vo.setAnime_status(rs.getString("ANIME_STATUS"));
                vo.setGoods_status(rs.getString("GOODS_STATUS"));
                vo.setDelpro_status(rs.getString("DELPRO_STATUS"));
            }
        }
        catch (DAOException e)
        {
            throw new DAOException("返回动漫货架信息查询发生异常:", e);
        }
        catch (SQLException e)
        {
            throw new DAOException("返回动漫货架信息查询发生异常:", e);
        }

        return vo;
    }

	public String getComicCategoryId() throws DAOException {
		// TODO Auto-generated method stub
         // select SEQ_MB_CATEGORY_NEW_ID.nextval from dual
         String sqlCode = "com.aspire.ponaadmin.web.comic.dao.CategoryDAO.getComicCategoryId.SELECT";
         String  categoryId = null;
         try
         {
        	 ResultSet rs = DB.getInstance().queryBySQLCode(sqlCode,null);
             if(rs.next())
             {
            	 categoryId = rs.getString(1);
             }
         }
         catch (Exception e)
         {
             throw new DAOException(e);
         }
         
         return categoryId;
	}

	public void saveCategory(CategoryVO categoryVO) throws DAOException {
		// TODO Auto-generated method stub
        if (logger.isDebugEnabled())
        {
            logger.debug("saveCategory() is starting ...");
        }
        //insert into t_cb_category (categoryid,categoryname,categoryDesc,parentcategoryid,picture,delflag,sortid,createtime) values (?,?,?,?,?,?,?,sysdate)

        String sqlCode = "com.aspire.ponaadmin.web.comic.dao.CategoryDAO.saveCategory.INSERT";

        try
        {
            DB.getInstance()
              .executeBySQLCode(sqlCode,
                                new Object[] { categoryVO.getCategoryId(),
            		  categoryVO.getCategoryName(),
            		  categoryVO.getCategoryDesc(),
            		  categoryVO.getParentCategoryId(),
            		  categoryVO.getPicture(),
            		  categoryVO.getDelFlag(),
            		  categoryVO.getSortId()
            		  							 });
        }
        catch (DAOException e)
        {
            throw new DAOException("新增动漫货架时发生异常:", e);
        }
		
	}

	public void updateCategory(CategoryVO categoryVO) throws DAOException {
		// TODO Auto-generated method stub

        //update t_cb_category set categoryname=?,categoryDesc=?,picture=?,delflag=?,sortid=?,lupdate=sysdate where categoryid=?
        String sqlCode = "com.aspire.ponaadmin.web.comic.dao.CategoryDAO.updateCategory.UPDATE";

        try
        {
            DB.getInstance()
              .executeBySQLCode(sqlCode,
                                new Object[] {
            		  categoryVO.getCategoryName(),
            		  categoryVO.getCategoryDesc(),
            		  categoryVO.getPicture(),
            		  categoryVO.getDelFlag(),
            		  categoryVO.getSortId(),
            		  categoryVO.getCategoryId()
            		  });
        }
        catch (DAOException e)
        {
            throw new DAOException("变更动漫货架时发生异常:", e);
        }
		
	}

	public int hasChild(String categoryId) throws DAOException {
		// TODO Auto-generated method stub

		 //select count(*) as countNum from t_cb_category c where c.delflag= '0' and c.parentcategoryid = ?
	        String sqlCode = "com.aspire.ponaadmin.web.comic.dao.CategoryDAO.hasChild";
	        int countNum = 0;

	        try
	        {
	        	ResultSet rs = DB.getInstance().queryBySQLCode(sqlCode,
	                                                 new Object[] { categoryId });

	            if (rs.next())
	            {
	                countNum = rs.getInt("countNum");
	            }
	        }
	        catch (DAOException e)
	        {
	            throw new DAOException("返回当前货架的子货架信息新音乐货架信息发生异常:", e);
	        }
	        catch (SQLException e)
	        {
	            throw new DAOException("返回当前货架的子货架信息新音乐货架信息发生异常:", e);
	        }

	        return countNum;
	}

	public int hasReference(String categoryId) throws DAOException {
		// TODO Auto-generated method stub
        //  select count(*) as countNum from t_cb_reference t where t.categoryId = ?
        String sqlCode = "com.aspire.ponaadmin.web.comic.dao.CategoryDAO.hasReference";
        int countNum = 0;
        try
        {
        	ResultSet rs = DB.getInstance().queryBySQLCode(sqlCode, new Object[]{categoryId});
            if (rs.next())
            {
                countNum = rs.getInt("countNum");
            }
        }
        catch (SQLException e)
        {
            throw new DAOException("返回当前货架的商品数目时发生异常:", e);
        } catch (DAOException e) {
			// TODO Auto-generated catch block
        	throw new DAOException("返回当前货架的商品数目时发生异常:", e);
		}
        return countNum;
	}

	public void delCategory(String categoryId) throws DAOException {
		// TODO Auto-generated method stub

         //delflag:0在门户展示1：不展示2：逻辑删除
        // update t_cb_category c set c.delflag=2 where c.categoryid=?
        String sqlCode = "com.aspire.ponaadmin.web.comic.dao.CategoryDAO.delCategory";

        try
        {
            DB.getInstance().executeBySQLCode(sqlCode,
                                              new Object[] { categoryId });
        }
        catch (DAOException e)
        {
            throw new DAOException("根据货架ID删除货架时发生异常:", e);
        }
        
       
	}
	
	/**
	 * 提交审批
	 * @param tdb
	 * @param categoryId 货架编号
	 * @throws DAOException
	 */
	 public void approvalCategory(TransactionDB tdb,String categoryId) throws DAOException {
		 String sqlCode = "com.aspire.ponaadmin.web.comic.dao.CategoryDAO.approvalCategory";

	        try
	        {
	        	tdb.executeBySQLCode(sqlCode,
	                                              new Object[] { categoryId });
	        }
	        catch (DAOException e)
	        {
	            throw new DAOException("根据货架ID审批货架时发生异常:", e);
	        }
	 }

	/**
	 * 动漫货架审批表
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
		ResultSet rs = null;
		try {
			rs = DB.getInstance()
					.queryBySQLCode(
							"com.aspire.ponaadmin.web.comic.dao.CategoryDAO.approvalCategory.SELECT",
							new Object[] { categoryId, operation });
			if (rs != null && rs.next()) {
				if ("2".equals(status)) {
					tdb.executeBySQLCode(
							"com.aspire.ponaadmin.web.comic.dao.CategoryDAO.approvalCategory.UPDATE1",
							new Object[] { operator, categoryId, operation });
				} else {
					tdb.executeBySQLCode(
							"com.aspire.ponaadmin.web.comic.dao.CategoryDAO.approvalCategory.UPDATE2",
							new Object[] { operator, categoryId, operation });
				}
			} else {
				if ("2".equals(status)) {
					tdb.executeBySQLCode(
							"com.aspire.ponaadmin.web.comic.dao.CategoryDAO.approvalCategory.INSERT1",
							new Object[] { operator, categoryId, operation });
				} else {
					tdb.executeBySQLCode(
							"com.aspire.ponaadmin.web.comic.dao.CategoryDAO.approvalCategory.INSERT2",
							new Object[] { operator, categoryId, operation });
				}
			}
		} catch (DAOException e) {
			logger.error("更新动漫货架审批表异常", e);
			throw new BOException("更新动漫货架审批表异常");
		} catch (SQLException e) {
			logger.error("更新动漫货架审批表异常", e);
			throw new BOException("更新动漫货架审批表异常");
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

 
}
