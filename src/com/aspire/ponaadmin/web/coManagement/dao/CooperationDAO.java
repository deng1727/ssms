package com.aspire.ponaadmin.web.coManagement.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.DataAccessException;
import com.aspire.common.persistence.util.SQLCode;
import com.aspire.common.util.MD5;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.page.PageVOInterface;
import com.aspire.ponaadmin.web.coManagement.vo.CooperationVO;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.CategoryTools;
import com.aspire.ponaadmin.web.repository.web.SEQCategoryUtil;
import com.aspire.ponaadmin.web.system.Config;


public class CooperationDAO {
	
	private final static JLogger LOGGER = LoggerFactory.getLogger(CooperationDAO.class);
	
	private static CooperationDAO instance = new CooperationDAO();
	
	private CooperationDAO(){
		
	}
	
	public static CooperationDAO getInstance(){
		return instance;
	}
	
	 /**
     * 应用类分页读取VO的实现类
     */
    private class CooperationPageVO implements PageVOInterface
    {

        public void CopyValFromResultSet(Object content, ResultSet rs)
                        throws SQLException
        {

        	CooperationVO vo = ( CooperationVO ) content;
        	vo.setCooperationId(rs.getString("CHANNELSID"));
        	vo.setCooperationName(rs.getString("CHANNELSNAME"));
        	vo.setStatus(rs.getString("status"));
        	vo.setCooperationDate(rs.getTimestamp("codate"));
        }

        public Object createObject()
        {

            return new CooperationVO();
        }
    }
    
	/**
	 * 查询合作商列表
	 * 
	 * @param page
	 * @param cooperationId 合作商ID
	 * @param cooperationName 合作商名称
	 * @throws DAOException
	 */
	public void queryCooperationList(PageResult page, String cooperationId, String cooperationName) throws DAOException{
		
		if (LOGGER.isDebugEnabled())
        {
			LOGGER.debug("queryCooperationList(" + cooperationId + "," + cooperationName 
                         + ") is starting ...");
        }

        // select c.channelsid,c.channelsname,c.status,c.codate from T_OPEN_CHANNELS c where 1=1
        String sqlCode = "com.aspire.ponaadmin.web.coManagement.dao.CooperationDAO.queryCooperationList";
        StringBuffer sb = new StringBuffer("");

        try
        {
        	sb.append(SQLCode.getInstance().getSQLStatement(sqlCode));
            
            if (cooperationId != null && !"".equals(cooperationId))
            {
            	 sb.append(" and c.CHANNELSID= '" + cooperationId + "'");
            	 
            }
            if (cooperationName != null && !"".equals(cooperationName))
            {
            	
            	sb.append(" and c.channelsname like '%" + cooperationName + "%'");
                
            }
            page.excute(sb.toString(), new Object[]{}, new CooperationPageVO());
        }
        catch (DataAccessException e)
        {
        	LOGGER.error("从sql.properties中获取sql语句时发生错误，对应sqlCode不存在:", e);
            throw new DAOException("从sql.properties中获取sql语句时发生错误，对应sqlCode不存在。",
                                   e);
        } catch (DAOException e) {
        	LOGGER.error("查询合作商列表发生异常:", e);
        	throw new DAOException("查询合作商列表发生异常:",e);
		}
		
	}
	
	 /**
     * 判断配置项对应的货架是否存在
     * @param categoryId
     * @return
     * @throws DAOException
     */
    public boolean isExistInCategory()throws DAOException{
    	StringBuffer sqlBuffer = new StringBuffer("select count(1) from t_r_category t where t.DELFLAG=0 ") ;
    	List paras = new ArrayList();
         //从配置项中获取渠道商根货架ID
         String channelRootCategoryId = Config.getInstance().getModuleConfig()
			.getItemValue("channelRootCategoryId");
         if(channelRootCategoryId != null && !"".equals(channelRootCategoryId.trim())){
         	 sqlBuffer.append(" and t.categoryID =?");
         	 paras.add(channelRootCategoryId);
         }
         boolean flag = false;
         try {
        	ResultSet rSet = DB.getInstance().query(sqlBuffer.toString(), paras.toArray());
        	if(rSet != null&&rSet.next()){
        		int count = rSet.getInt(1);
        		if(count >0){
        			flag = true;
        		}
        	}
        	 return flag;
		} catch (Exception e) {
			LOGGER.error("判断配置项对应的货架是否存在：", e);
			throw new DAOException("判断配置项对应的货架是否存在：",e);
		}
         
    }
    
	/**
	 * 添加合作商
	 * 
	 * @param map
	 * @throws DAOException
	 */
	public void insertCooperation(Map<String,Object> map) throws DAOException{
		String sqlCode = "com.aspire.ponaadmin.web.coManagement.dao.CooperationDAO.insertCooperation";
		// 进行事务操作
		TransactionDB tdb = null;
		try {
			tdb = TransactionDB.getTransactionInstance();
			tdb.executeBySQLCode(sqlCode, new Object[]{map.get("cooperationId"),map.get("cooperationName"),map.get("cooperationType"),map.get("channelNumber"),"admin" + map.get("cooperationId") ,MD5.getHexMD5Str("admin" + map.get("cooperationId"))});
			 //从配置项中获取渠道商根货架ID
	         String channelRootCategoryId = Config.getInstance().getModuleConfig()
				.getItemValue("channelRootCategoryId");
	         Category category = new Category();
	         category.setName(map.get("cooperationName").toString());
	         category.setRelation("W,O,P,A");
	         category.setSortID(SEQCategoryUtil.getInstance()
 					.getSEQByCategoryType(1));
	         category.setCtype(0);
             category.setChangeDate(new Date());
             category.setDelFlag(0);
             category.setState(1);
             category.setCityId("{0000}");
             category.setPlatForm("{0000}");
             category.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
             category.setEndDate("2099-01-01");
             category.setClassifyStatus("1");
             String categoryId = this.queryCategory(channelRootCategoryId);
             CategoryTools.createCategory(categoryId,
                     category);
	        if(this.queryOpenChannelsCategoryVo(map.get("cooperationId").toString())){
	        	tdb.executeBySQLCode("com.aspire.ponaadmin.web.coManagement.dao.CooperationDAO.insertCooperation.UPDATE", new Object[]{category.getCategoryID(),map.get("cooperationId")});
	        }else{
	        	tdb.executeBySQLCode("com.aspire.ponaadmin.web.coManagement.dao.CooperationDAO.insertCooperation.INSERT", new Object[]{map.get("cooperationId"),category.getCategoryID()});
	        }
			tdb.commit();
		} catch (DAOException e) {
			tdb.rollback();
			LOGGER.error("添加合作商发生异常:", e);
			throw new DAOException("添加合作商发生异常:",e);
		} catch (BOException e) {
			tdb.rollback();
			LOGGER.error("添加合作商发生异常:", e);
			throw new DAOException("添加合作商发生异常:",e);
		} catch (Exception e) {
			tdb.rollback();
			LOGGER.error("添加合作商发生异常:", e);
			throw new DAOException("添加合作商发生异常:",e);
		}finally {
			if (tdb != null) {
				tdb.close();
			}
		}
	}
	
	 /**
     * 查询合作商货架信息失败
     * @throws DAOException 
     */
    public boolean queryOpenChannelsCategoryVo(String channelsId) throws DAOException{
        String sqlCode = "com.aspire.ponaadmin.web.coManagement.dao.CooperationDAO.queryOpenChannelsCategoryVo";
        try
        {
            ResultSet rs = DB.getInstance().queryBySQLCode(sqlCode, new Object[]{channelsId});
            if(rs.next()){
            	return true;
            }
            return false;
        }
        catch (DAOException e)
        {
        	LOGGER.debug("查询合作商货架信息失败:", e);
            throw new DAOException("查询合作商货架信息失败:", e);
        }
        catch (SQLException e)
        {
        	LOGGER.debug("查询合作商货架信息失败:", e);
            throw new DAOException("查询合作商货架信息失败:", e);
        }
    }
	/**
	 * 查询合作商信息
	 * 
	 * @param cooperationId 合作商ID
	 * @return
	 * @throws DAOException
	 */
	public Map<String,Object> queryCooperation(String cooperationId) throws DAOException{
		Map<String,Object> map = new HashMap<String,Object>();
		String sqlCode = "com.aspire.ponaadmin.web.coManagement.dao.CooperationDAO.queryCooperation";
		ResultSet rs;
		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode, new Object[]{cooperationId});
			while(rs.next()){
				map.put("cooperationId", rs.getString("channelsid"));
				map.put("cooperationName", rs.getString("channelsname"));
				map.put("channelsnumber", rs.getString("channelsnumber") == null ? 0 : rs.getString("channelsnumber"));
				map.put("cotype", rs.getString("cotype") == null ? "" : rs.getString("cotype"));
				map.put("categoryId", rs.getString("categoryId"));
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				map.put("codate", format.format(rs.getTimestamp("codate")));
			}
		} catch (DAOException e) {
			LOGGER.debug("查询合作商信息失败:", e);
            throw new DAOException("查询合作商信息失败:", e);
		} catch (SQLException e) {
			LOGGER.debug("查询合作商信息失败:", e);
            throw new DAOException("查询合作商信息失败:", e);
		}
		return map;
	}
	/**
	 * 更新合作商信息
	 * @param map
	 * @throws DAOException
	 */
	public void updateCooperation(Map<String,Object> map) throws DAOException{
		String sqlCode = "com.aspire.ponaadmin.web.coManagement.dao.CooperationDAO.updateCooperation";
		try {
			DB.getInstance().executeBySQLCode(sqlCode, new Object[]{map.get("cooperationType"),map.get("channelNumber"),map.get("cooperationId")});
		} catch (DAOException e) {
			LOGGER.debug("更新合作商信息失败:", e);
            throw new DAOException("更新合作商信息失败:", e);
		}
	}
	
	/**
	 * 更新合作商状态信息
	 * @param map
	 * @throws DAOException
	 */
	public void operationCooperation(Map<String,Object> map) throws DAOException{
		String sqlCode = "com.aspire.ponaadmin.web.coManagement.dao.CooperationDAO.operationCooperation";
		try {
			DB.getInstance().executeBySQLCode(sqlCode, new Object[]{map.get("type"),map.get("cooperationId")});
		} catch (DAOException e) {
			LOGGER.debug("更新合作商状态信息失败:", e);
            throw new DAOException("更新合作商状态信息失败:", e);
		}
	}
	
	/**
	 * 查询货架
	 * 
	 * @param cooperationId 合作商ID
	 * @return
	 * @throws DAOException
	 */
	public String queryCategory(String categoryId) throws DAOException{
		String sqlCode = "select id from t_r_category c where c.categoryid = ?";
		ResultSet rs;
		try {
			rs = DB.getInstance().query(sqlCode, new Object[]{categoryId});
			while(rs.next()){
				return rs.getString("id");
			}
		} catch (DAOException e) {
			LOGGER.debug("查询货架信息失败:", e);
            throw new DAOException("查询货架信息失败:", e);
		} catch (SQLException e) {
			LOGGER.debug("查询货架信息失败:", e);
            throw new DAOException("查询货架信息失败:", e);
		}
		throw new DAOException("查询货架信息失败:");
	}

}
