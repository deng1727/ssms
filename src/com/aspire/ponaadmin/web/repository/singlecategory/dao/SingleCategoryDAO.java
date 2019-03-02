/**
 * SSMS
 * com.aspire.ponaadmin.web.repository.camonitor CategoryMonitorDAO.java
 * Apr 20, 2010
 * @author tungke
 * @version 1.0
 *
 */
package com.aspire.ponaadmin.web.repository.singlecategory.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.db.SQLUtil;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.DataAccessException;
import com.aspire.common.persistence.util.SQLCode;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.page.PageVOInterface;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.CategoryDAO;
import com.aspire.ponaadmin.web.repository.singlecategory.vo.SingleCategoryUserGroupVO;
import com.aspire.ponaadmin.web.repository.singlecategory.vo.SingleCategoryVO;

/**
 * @author tungke
 *
 */
public class SingleCategoryDAO
{

	private static SingleCategoryDAO instance = new SingleCategoryDAO();

	private SingleCategoryDAO()
	{

	}

	public static SingleCategoryDAO getInstance()
	{
		return instance;
	}

	/**
	 * 日志引用
	 */
	private static final JLogger logger = LoggerFactory
			.getLogger(SingleCategoryDAO.class);

	/**
	 * 通过分类id去查找对应的货架编码
	 * @return categoryID 
	 * @throws DAOException
	 */
	public List getCategorySingle(String userId,String type) throws DAOException
	{
		List cmList = new ArrayList();
		if (logger.isDebugEnabled())
		{
			logger.debug("getCategorySingle is beginning ....");
		}
		Object[] paras = {userId,type};
		String sqlCode = "SingleCategoryDAO.getCategorySingle().SELECT";

		ResultSet rs = null;
		try
		{
			rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
			cmList = this.getCategoryMonitorVOByRS(rs);
		} catch (Exception e)
		{
			throw new DAOException(e);
		} finally
		{
			DB.close(rs);
		}
		return cmList;
	}

	
	
	
	/**
	 * 获取结果封装
	 * @param rs
	 * @return
	 * @throws SQLException
	 * @throws DAOException 
	 */
	public List getCategoryMonitorVOByRS(ResultSet rs) throws DAOException
	{
		List cmList = new ArrayList();
		try
		{
			while (rs.next())
			{
				SingleCategoryVO cm = new SingleCategoryVO();
				cm.setId(rs.getString("id"));
				cm.setCategoryid(rs.getString("categoryid"));
				cm.setParentcategoryid(rs.getString("parentcategoryid"));
				cm.setName(rs.getString("name"));
				cm.setCategorytype(rs.getString("categorytype"));
				cm.setFullName("货架"+rs.getString("fullname"));

				/*if (cm.getCategorytype() != null && cm.getCategorytype().equals("1"))
				{
					// 只查找一级子货架
					String sqlCode = "CategoryMonitorDAO.getCategoryMonitorVOByRS1().SELECT";
					Object[] paras = { cm.getFullName()+">>",cm.getCategoryid() };
					List sub1 = this.getSubCategoryMonitor(sqlCode, paras);
					cmList.addAll(sub1);
					cmList.add(cm);
				}
				else if (cm.getCategorytype() != null && cm.getCategorytype().equals("9"))
				{
					if(cm.getFullName() != null && cm.getFullName().indexOf(">>")>=0){
						String fullnametemp = cm.getFullName().substring(0,cm.getFullName().lastIndexOf(">>"));
						// 查找所有子货架
						String sqlCode = "SingleCategoryDAO.getCategoryMonitorVOByRS9().SELECT";
						Object[] paras = {  fullnametemp,cm.getCategoryid(),cm.getCategoryid()};
						List sub2 = this.getSubCategoryMonitor(sqlCode, paras);
						cmList.addAll(sub2);
						cmList.add(cm);
					}else{
						logger.error("获取货架全名称失败");
					}
					
				}
				else
				{
					// 只添加本身不处理
				}*/
				cmList.add(cm);

			}
		} catch (Exception e)
		{
			throw new DAOException(e);
		} finally
		{
			DB.close(rs);
		}
		return cmList;
	}

	/**
	 * 基础查询
	 * 
	 * @return categoryID
	 * @throws DAOException
	 */
	public List getSubCategoryMonitor(String sqlCode, Object[] paras) throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("getSubCategoryMonitor(" + sqlCode + ") is beginning ....");
		}
		List result = new ArrayList();
		//Object[] paras = {categoryid};
		// String sqlCode = "CategoryDAO.getCategoryIDByID.SELECT";
		ResultSet rs = null;
		try
		{
			rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
			result = this.getCategoryMonitorVOByRS(rs);
			//            if(rs.next())
			//            {
			//                result = rs.getString("categoryID");
			//            }
		} catch (Exception e)
		{
			throw new DAOException(e);
		} finally
		{
			DB.close(rs);
		}
		return result;
	}

	public void querySingleCategoryUserGroupList(PageResult page, String code,
			String name,String type) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("queryCategoryUpdateList(" + code + "," + name+" ) is starting ...");
        }
        
        String sqlCode = "SingleCategoryDAO.querySingleCategoryUserGroupList().SELECT";
        String sql = null;

        try
        {
            sql = SQLCode.getInstance().getSQLStatement(sqlCode);
            
            StringBuffer sqlBuffer = new StringBuffer(sql) ;
            List paras = new ArrayList();
            //构造搜索的sql和参数

            if (!"".equals(code))
            {
                //sql += " and t.code ='" + code.trim() + "'";
            	sqlBuffer.append(" and t.code = ? ");
            	paras.add(code.trim());
            }
            if (!"".equals(name))
            {
                //sql += " and t.name like '%" + name.trim()+"%'";
            	sqlBuffer.append(" and t.name like ? ");
            	paras.add("%"+SQLUtil.escape(name.trim())+"%");
            }
            
            if (!"".equals(type))
            {
                //sql += " and t.type ='" + type.trim() + "'";
            	sqlBuffer.append(" and t.type = ? ");
            	paras.add("%"+SQLUtil.escape(type.trim())+"%");
            }
           
            //sql += " order by t.code desc";
            sqlBuffer.append(" order by t.code desc");
            

            //page.excute(sql, null, new SingleCategoryUserGroupPageVO());
            page.excute(sqlBuffer.toString(), paras.toArray(), new SingleCategoryUserGroupPageVO());
        }
        catch (DataAccessException e)
        {
            throw new DAOException("从sql.properties中获取sql语句时发生错误，对应sqlCode不存在。",
                                   e);
        }
	}
	
	/**
     * 应用类分页读取VO的实现类
     */
    private class SingleCategoryUserGroupPageVO implements PageVOInterface
    {

        public void CopyValFromResultSet(Object content, ResultSet rs)
                        throws SQLException
        {

        	SingleCategoryUserGroupVO vo = ( SingleCategoryUserGroupVO ) content;
            vo.setCode(rs.getString("code"));
            vo.setName(rs.getString("name"));
            vo.setType(rs.getString("type"));
            
        }

        public Object createObject()
        {
            return new SingleCategoryUserGroupVO();
        }
    }

	public void addSingleCategoryUserGroupByCode(String code, String name,String type) throws DAOException
    {

        String sqlCode = "SingleCategoryDAO.addSingleCategoryUserGroupByCode().INSERT";
        DB.getInstance().executeBySQLCode(sqlCode,new Object[] { code, name,type});
	}
	
	/**
	 * 通过分类id去查找对应的货架编码
	 * @return categoryID 
	 * @throws DAOException
	 */
	public SingleCategoryVO getCategoryByCategoryId(String id) throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("getCategoryByCategoryId is beginning ....");
		}
		Object[] paras = {id};
		String sqlCode = "SingleCategoryDAO.getCategoryByCategoryId().SELECT";
		SingleCategoryVO cm = new SingleCategoryVO();
		ResultSet rs = null;
		try
		{
			rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
			
			if(rs.next())
			{
				cm.setId(rs.getString("id"));
				cm.setCategoryid(rs.getString("categoryid"));
				cm.setParentcategoryid(rs.getString("parentcategoryid"));
				cm.setName(rs.getString("name"));
			}
			
		} catch (Exception e)
		{
			throw new DAOException(e);
		} finally
		{
			DB.close(rs);
		}
		return cm;
	}

	public void addSingleCategoryByCode(SingleCategoryVO vo, String code,String type)throws DAOException {
		String sqlCode = "SingleCategoryDAO.addSingleCategoryByCode().INSERT";
        DB.getInstance().executeBySQLCode(sqlCode,new Object[] { vo.getId(),vo.getName(),vo.getCategoryid(), code,type});
	}

	public void delSingleCategoryUserGroupByCode(String code,String type) throws DAOException {
		String sqlCode = "SingleCategoryDAO.delSingleCategoryUserGroupByCode().DELETE";
        DB.getInstance().executeBySQLCode(sqlCode,new Object[] {code,type});
		
	}

	public void delSingleCategoryByCode(String code,String type) throws DAOException {
		String sqlCode = "SingleCategoryDAO.delSingleCategoryByCode().DELETE";
        DB.getInstance().executeBySQLCode(sqlCode,new Object[] {code,type});
		
	}

	public String querySingleCategoryIdsByCode(String code,String type) throws DAOException  {
		if (logger.isDebugEnabled())
		{
			logger.debug("getCategoryByCategoryId is beginning ....");
		}
		Object[] paras = {code,type};
		String sqlCode = "SingleCategoryDAO.querySingleCategoryIdsByCode().SELECT";
		ResultSet rs = null;
		StringBuffer sb=new StringBuffer("");
		try
		{
			rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
			
			while(rs.next())
			{
				sb.append(rs.getString("id")+",");
			}
		
			
		} catch (Exception e)
		{
			throw new DAOException(e);
		} finally
		{
			DB.close(rs);
		}
		if(sb.length()>0)
		{
			return sb.substring(0,sb.length()-1);
		}
		return "";
	}
	
	
	 /**
     * 根据货架编码，货架名称查出对应货架表信息
     * 
     * @param categoryId 货架编码
     * @param categoryName 货架名称
     * @return
     * @throws DAOException
     */
    public void queryCategoryList(PageResult page, String categoryId, String categoryName,String relation)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("queryCategoryUpdateList(" + categoryId + "," + categoryName
                         + ") is starting ...");
        }
        
        // select * from t_r_category t where 1=1
        String sqlCode = "autoupdate.CategoryQueryDAO.queryCategoryList().SELECT";
        String sql = null;

        try
        {
            sql = SQLCode.getInstance().getSQLStatement(sqlCode);
            
            StringBuffer sqlBuffer = new StringBuffer(sql) ;
            List paras = new ArrayList();
            //构造搜索的sql和参数
            

            if (!"".equals(categoryId))
            {
                //sql += " and t.id in(" + categoryId + ")";
            	sqlBuffer.append(" and t.id in( ? ) ");
            	paras.add(categoryId);
            }
            if (!"".equals(categoryName))
            {
                //sql += " and t.name like('%" + categoryName + "%')";
            	sqlBuffer.append(" and t.name like ? ");
            	paras.add("%"+SQLUtil.escape(categoryName)+"%");
            }
            if(!"".equals(relation))
            {
            	//sql += " and t.relation ='" + relation.trim() + "'";
            	sqlBuffer.append(" and t.relation = ? ");
            	paras.add(relation.trim());
            }

            //page.excute(sql, null, new CategoryRulePageVO());
            page.excute(sqlBuffer.toString(), paras.toArray(), new CategoryRulePageVO());
        }
        catch (DataAccessException e)
        {
            throw new DAOException("从sql.properties中获取sql语句时发生错误，对应sqlCode不存在。",
                                   e);
        }
    }
    
    /**
     * 应用类分页读取VO的实现类
     */
    private class CategoryRulePageVO implements PageVOInterface
    {

        public void CopyValFromResultSet(Object content, ResultSet rs)
                        throws SQLException
        {

            Category vo = ( Category ) content;
            vo.setId(rs.getString("id"));
            vo.setCategoryID(rs.getString("categoryID"));
            vo.setName(rs.getString("name"));
            vo.setDesc(rs.getString("descs"));
            try
            {
                vo.setNamePath(CategoryDAO.getInstance().getCategoryNamePathByID(vo.getId()));
            }
            catch (DAOException e)
            {
                e.printStackTrace();
            }
        }

        public Object createObject()
        {

            return new Category();
        }
    }

}
