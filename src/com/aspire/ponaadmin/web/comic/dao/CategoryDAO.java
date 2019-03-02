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
     * ��¼��־��ʵ������
     */
    protected static JLogger logger = LoggerFactory.getLogger(CategoryDAO.class);

    /**
     * singletonģʽ��ʵ��
     */
    private static CategoryDAO instance = new CategoryDAO();

    /**
     * ���췽������singletonģʽ����
     */
    private CategoryDAO(){
    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
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
            throw new DAOException("���ض���������Ϣ��ѯ�����쳣:", e);
        }
        catch (SQLException e)
        {
            throw new DAOException("���ض���������Ϣ��ѯ�����쳣:", e);
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
            throw new DAOException("������������ʱ�����쳣:", e);
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
            throw new DAOException("�����������ʱ�����쳣:", e);
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
	            throw new DAOException("���ص�ǰ���ܵ��ӻ�����Ϣ�����ֻ�����Ϣ�����쳣:", e);
	        }
	        catch (SQLException e)
	        {
	            throw new DAOException("���ص�ǰ���ܵ��ӻ�����Ϣ�����ֻ�����Ϣ�����쳣:", e);
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
            throw new DAOException("���ص�ǰ���ܵ���Ʒ��Ŀʱ�����쳣:", e);
        } catch (DAOException e) {
			// TODO Auto-generated catch block
        	throw new DAOException("���ص�ǰ���ܵ���Ʒ��Ŀʱ�����쳣:", e);
		}
        return countNum;
	}

	public void delCategory(String categoryId) throws DAOException {
		// TODO Auto-generated method stub

         //delflag:0���Ż�չʾ1����չʾ2���߼�ɾ��
        // update t_cb_category c set c.delflag=2 where c.categoryid=?
        String sqlCode = "com.aspire.ponaadmin.web.comic.dao.CategoryDAO.delCategory";

        try
        {
            DB.getInstance().executeBySQLCode(sqlCode,
                                              new Object[] { categoryId });
        }
        catch (DAOException e)
        {
            throw new DAOException("���ݻ���IDɾ������ʱ�����쳣:", e);
        }
        
       
	}
	
	/**
	 * �ύ����
	 * @param tdb
	 * @param categoryId ���ܱ��
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
	            throw new DAOException("���ݻ���ID��������ʱ�����쳣:", e);
	        }
	 }

	/**
	 * ��������������
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
			logger.error("���¶��������������쳣", e);
			throw new BOException("���¶��������������쳣");
		} catch (SQLException e) {
			logger.error("���¶��������������쳣", e);
			throw new BOException("���¶��������������쳣");
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
