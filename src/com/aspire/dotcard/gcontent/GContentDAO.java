package com.aspire.dotcard.gcontent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.implement.book.jidi.AuthorVO;
import com.aspire.ponaadmin.web.repository.Node;
import com.aspire.ponaadmin.web.repository.Repository;

/**
 * 对内容数据操作的类，对当前框架的一个补充；<br>
 * 以后可以逐渐增加该类的方法，以满足在特殊情况下对性能的要求。
 * @author zhangwei
 *
 */
public class GContentDAO
{
	private static GContentDAO dao=new GContentDAO();
	private static JLogger logger=LoggerFactory.getLogger(GContentDAO.class);
	
	/**
	 * 单例模式
	 * @return
	 */
	public static GContentDAO getInstance()
	{
		return dao;
	}
	/**
	 * 获取分类id为 cateId的分类下所有内容的id列表
	 * @param cateId 
	 * @return List
	 */
	public List getContentIdByCateId(String cateId)throws DAOException
	{
		  if (logger.isDebugEnabled())
	        {
	            logger.debug("getContentIdByCate, cate:"+cateId);
	        }
	        String sqlCode = "com.aspire.dotcard.gcontent.GContentDAO.getContentIdByCate.SELECT";
	        List deviceNameList = new ArrayList();
	        ResultSet rs = null;
	        try
	        {

	        	Node node;
				try
				{
					node = Repository.getInstance().getNode(cateId);
				} catch (BOException e)
				{
					logger.error("获取分类节点错误,cateId="+cateId,e);
					throw new DAOException("获取分类节点错误",e);
				}
	        	
	            // 参数列表为空，执行全量搜索
	            Object[] paras = {node.getPath()+"%"};
	            
	            rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
	            while (rs.next())
	            {
	                deviceNameList.add(rs.getString(1));
	            }
	            return deviceNameList;
	        }
	        catch (SQLException ex)
	        {
	            throw new DAOException("getContentIdByCate error.", ex);
	        }finally{
	        	//add by tungke for close db con or rs
	        	DB.close(rs);
	        	
	        }
		
	}
	/**
	 * 更新基地阅读中，读者信息
	 * @return
	 */
	public int updateContentAuhor(AuthorVO vo)throws DAOException
	{

        if (logger.isDebugEnabled())
        {
            logger.debug("updateContentAuhor");
        }
        String sqlCode = "com.aspire.dotcard.gcontent.GContentDAO.updateContentAuhor.update";
            // 参数列表为空，执行全量搜索
        Object[] paras = {vo.getName(),vo.getDesc(),vo.getId()};
        return DB.getInstance().executeBySQLCode(sqlCode, paras);
     
        
	}

	 /**
         * 根据contentid查询主键id
         * 
         * @return
         */
    public String getContentByID(String contentid) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("getContentByID, contentid:" + contentid);
        }
        String sqlCode = "com.aspire.dotcard.gcontent.GContentDAO.getContentByID.SELECT";
        ResultSet rs = null;
        String id = "";
        try
        {

            // 参数列表为空，执行全量搜索
            Object[] paras = { contentid };

            rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
            while (rs.next())
            {
                id = rs.getString("id");
            }

        }
        catch (SQLException ex)
        {
            throw new DAOException("getContentByID error.", ex);
        }
        finally
        {
            // add by tungke for close db con or rs
            DB.close(rs);

        }
        return id;
    }

}
