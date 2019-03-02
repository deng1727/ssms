package com.aspire.ponaadmin.web.category.blacklist.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.db.SQLUtil;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.DataAccessException;
import com.aspire.common.persistence.util.SQLCode;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.page.PageVOInterface;
import com.aspire.ponaadmin.web.category.blacklist.vo.AndroidBlackListVO;
import com.aspire.ponaadmin.web.util.PublicUtil;

public class AndroidBlackListDAO {

	 /**
     * 日志引用
     */
    protected static final JLogger logger = LoggerFactory.getLogger(AndroidBlackListDAO.class);

    private static AndroidBlackListDAO instance = new AndroidBlackListDAO();
    
    private AndroidBlackListDAO(){
    	
    }
    
    public static AndroidBlackListDAO getInstance(){
    	return instance;
    }
    
    /**
     * 应用类分页读取VO的实现类
     */
    private class TagPageVO implements PageVOInterface
    {
        public void CopyValFromResultSet(Object content, ResultSet rs)
                        throws SQLException
        {
            AndroidBlackListVO vo = ( AndroidBlackListVO ) content;

            vo.setContentID(rs.getString("contentId"));
            vo.setName(rs.getString("name"));
            vo.setOpDate(rs.getDate("createdate"));
        }

        public Object createObject()
        {
            return new AndroidBlackListVO();
        }
    }
    
    /**
     * 用于用于查询榜单黑名单元数据列表
     * 
     * @param page
     * @param vo
     * @throws DAOException
     */
    public void queryTagList(PageResult page, String name, String contentid)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("AndroidBlackListDAO.queryTagList() is starting ...");
        }

        //select * from V_CLMS_TAG_BLACKLIST where 1=1
        String sqlCode = "tagmanager.dao.AndroidBlackListDAO.queryTagList().SELECT";
        String sql = null;

        try
        {
            sql = SQLCode.getInstance().getSQLStatement(sqlCode);

            StringBuffer sqlBuffer = new StringBuffer(sql) ;
            List paras = new ArrayList();
            //构造搜索的sql和参数
            if (!"".equals(contentid))
            {
//                sql += " and contentid = ? ";
            	sqlBuffer.append(" and contentid = ? ");
            	paras.add(SQLUtil.escape(contentid));
            }
            
            if (!"".equals(name))
            {
//                sql += " and name like('%" + name
//                       + "%')";
            	sqlBuffer.append(" and name like  ? ");
            	paras.add("%"+SQLUtil.escape(name)+"%");
            }

            //sql += " order by createdate desc ";
            sqlBuffer.append(" order by createdate desc ");

            page.excute(sqlBuffer.toString(), paras.toArray(), new TagPageVO());
        }
        catch (DataAccessException e)
        {
            throw new DAOException("从sql.properties中获取sql语句时发生错误，对应sqlCode不存在。",
                                   e);
        }
      
    }
    
    public void addTag(String tagId)
         throws DAOException
    {
    	if (logger.isDebugEnabled())
        {
            logger.debug("TagManagerDAO.addTag() is starting ...");
        }
    	
        // insert into T_CLMS_TAG_BLACKLIST(TAGID,OPDATE) values(?,sysdate)
        String sqlCode = "tagmanager.dao.AndroidBlackListDAO.addTag().insert";
        
        String[] paras = null;
        try
		{
        	paras = new String[] {tagId};
			DB.getInstance().executeBySQLCode(sqlCode, paras);
		}
		catch (DAOException e)
		{
			logger.error("新增榜单黑名单时发生异常:", e);
			throw new DAOException("新增榜单黑名单时发生异常:", e);
		}
    }
    
    public void updateTag(String tagId)
            throws DAOException
       {
       	if (logger.isDebugEnabled())
           {
               logger.debug("TagManagerDAO.updateTag() is starting ...");
           }
       	
           // update T_CLMS_TAG_BLACKLIST set  OPDATE \= sysdate where tagid \= ? 
           String sqlCode = "tagmanager.dao.AndroidBlackListDAO.updateTag().update";
           
           String[] paras = null;
           try
	   		{
	           	paras = new String[] {tagId};
	   			DB.getInstance().executeBySQLCode(sqlCode, paras);
	   		}
	   		catch (DAOException e)
	   		{
	   			logger.error("修改榜单黑名单时发生异常:", e);
	   			throw new DAOException("修改榜单黑名单时发生异常:", e);
	   		}
       }
    
    /**
	 * 用于删除榜单黑名单元数据表
	 * 
	 * @param tagId
	 * @return
	 * @throws BOException
	 */
	public int delTag(String tagId) throws BOException
	{
		int ret = 0;
		//delete from T_CLMS_TAG_BLACKLIST where tagid = ?
		String sql = "tagmanager.dao.AndroidBlackListDAO.delTag().delete";
		try
		{
			ret = DB.getInstance().executeBySQLCode(sql, new Object[]{tagId});
		}
		catch (DAOException e)
		{
			logger.error("删除榜单黑名单表数据失败:", e);
			throw new BOException("删除榜单黑名单表数据失败:" + PublicUtil.GetCallStack(e)
					+ "<br>");
		}
		
		return ret;
	}
    
	public int getSeqValue() throws DAOException {
		return DB.getInstance().getSeqValue("SEQ_CLMS_TAG_ID");
	}
    
    /**
	 * 得到榜单黑名单ID列表
	 * 
	 * @return 榜单黑名单ID列表
	 */
	public Map<String, String> getTagIDMap()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("得到榜单黑名单ID列表,开始");
		}
		Map<String, String> tagIdMap = new HashMap<String, String>();
		ResultSet rs = null;
		
		try
		{
			// select tagid,tagname from V_CLMS_TAG_BLACKLIST 
			String sqlCode = "tagmanager.dao.AndroidBlackListDAO.getTagIDMap().select";
			rs = DB.getInstance().queryBySQLCode(sqlCode, null);
			while (rs.next())
			{
				tagIdMap.put(rs.getString("contentid"), rs.getString("name"));
			}
		}
		catch (SQLException e)
		{
			logger.error("数据库SQL执行异常，查询失败", e);
		}
		catch (DAOException ex)
		{
			logger.error("数据库操作异常，查询失败", ex);
		}
		finally
		{
			DB.close(rs);
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("得到榜单黑名单ID列表,结束");
		}
		return tagIdMap;
	}
	
	/**
	 * 得到榜单黑名单ID列表
	 * 
	 * @return 榜单黑名单ID列表
	 */
	public Map<String, String> getTagIDMapByLevel(String tagLevel)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("得到榜单黑名单ID列表,开始");
		}
		Map<String, String> tagIdMap = new HashMap<String, String>();
		ResultSet rs = null;
		String[] paras = null;
		String sql;
		try
		{
			// select tagid,tagname from T_CLMS_TAG where taglevel = ?
			String sqlCode = "tagmanager.dao.TagManagerDAO.getTagIDMap().select";
			sql = SQLCode.getInstance().getSQLStatement(sqlCode);

			StringBuffer sqlBuffer = new StringBuffer(sql);
			if(null != tagLevel && !"".equals(tagLevel)){
				paras = new String[]{tagLevel};
				sqlBuffer.append(" where taglevel = ? ");
			}
			rs = DB.getInstance().query(sqlBuffer.toString(), paras);
			while (rs.next())
			{
				tagIdMap.put(rs.getString("tagid"), "");
			}
		}
		catch (SQLException e)
		{
			logger.error("数据库SQL执行异常，查询失败", e);
		}
		catch (DAOException ex)
		{
			logger.error("数据库操作异常，查询失败", ex);
		}catch (DataAccessException e)
        {
			logger.error("从sql.properties中获取sql语句时发生错误，对应sqlCode不存在。", e);
        }
		finally
		{
			DB.close(rs);
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("得到榜单黑名单ID列表,结束");
		}
		return tagIdMap;
	}
	
	public List queryAllTagListByExport() throws DAOException{
		if (logger.isDebugEnabled())
		{
			logger.debug("queryAllTagListByExport is starting ...");
		}

		// select tagid,tagname,opdate from t_clms_tag 
		String sqlCode = "tagmanager.dao.AndroidBlackListDAO.queryTagList().SELECT";
		List list = new ArrayList();
		ResultSet rs = null;
		String sql;
		try
		{
			sql = SQLCode.getInstance().getSQLStatement(sqlCode);

			StringBuffer sqlBuffer = new StringBuffer(sql);

			//sql += " order by createdate desc ";
            sqlBuffer.append(" order by createdate desc ");
			
			rs = DB.getInstance().query(sqlBuffer.toString(), null);

			while (rs.next())
			{
				list.add(tagVoData(rs));
			}
		}
		catch (SQLException e)
		{
			logger.error("执行查询榜单黑名单元数据信息失败", e);
			e.printStackTrace();
		}
		catch (DataAccessException e)
		{
			logger.error("执行查询查询榜单黑名单元数据信息失败", e);
			e.printStackTrace();
		}
		finally
		{
			DB.close(rs);
		}

		return list;
	}
	
	public List getAllTagListByExport() throws DAOException{
		if (logger.isDebugEnabled())
		{
			logger.debug("getAllTagListByExport is starting ...");
		}

		// select tagid,tagname,opdate from t_clms_tag 
		String sqlCode = "tagmanager.dao.TagManagerDAO.getAllTagListByExport().SELECT";
		List list = new ArrayList();
		ResultSet rs = null;
		String sql;
		try
		{
			sql = SQLCode.getInstance().getSQLStatement(sqlCode);

			StringBuffer sqlBuffer = new StringBuffer(sql);

			//sql += " order by createdate desc ";
            sqlBuffer.append(" order by createdate desc ");
			
			rs = DB.getInstance().query(sqlBuffer.toString(), null);

			while (rs.next())
			{
				AndroidBlackListVO vo = new AndroidBlackListVO();
				vo.setContentID(rs.getString("contentId"));
	            vo.setName(rs.getString("name"));
	            vo.setOpDate(rs.getDate("createdate"));
				list.add(vo);
			}
		}
		catch (SQLException e)
		{
			logger.error("执行查询榜单黑名单元数据信息失败", e);
			e.printStackTrace();
		}
		catch (DataAccessException e)
		{
			logger.error("执行查询查询榜单黑名单元数据信息失败", e);
			e.printStackTrace();
		}
		finally
		{
			DB.close(rs);
		}

		return list;
	}
	
	
	private AndroidBlackListVO tagVoData(ResultSet rs) throws SQLException
	{
		AndroidBlackListVO vo = new AndroidBlackListVO();
		vo.setContentID(rs.getString("contentId"));
        vo.setName(rs.getString("name"));
        vo.setOpDate(rs.getDate("createdate"));
		/*vo.setTagOwner(rs.getString("tagowner"));
		vo.setParentTagID(rs.getString("parenttagid"));
		vo.setCreateDate(rs.getDate("createdate"));*/
		return vo;
	}
}
