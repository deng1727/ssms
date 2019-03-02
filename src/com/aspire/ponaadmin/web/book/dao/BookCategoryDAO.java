/*
 * 文件名：BookCategoryDAO.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */

package com.aspire.ponaadmin.web.book.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.book.vo.BookCategoryVO;
import com.aspire.ponaadmin.web.datafield.vo.ResourceVO;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * 
 * @author wangminlong
 * @version
 */
public class BookCategoryDAO
{

    /**
     * 记录日志的实例对象
     */
    protected static JLogger logger = LoggerFactory.getLogger(BookCategoryDAO.class);

    /**
     * singleton模式的实例
     */
    private static BookCategoryDAO instance = new BookCategoryDAO();

    /**
     * 构造方法，由singleton模式调用
     */
    private BookCategoryDAO()
    {
    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static BookCategoryDAO getInstance()
    {
        return instance;
    }

    /**
     * 为对象属性赋值
     * 
     * @param vo
     * @param rs
     * @throws SQLException
     */
    private void fromBookCategoryVOByRs(BookCategoryVO vo, ResultSet rs)
                    throws SQLException
    {
        vo.setId(String.valueOf(rs.getInt("id")));
        vo.setCategoryId(rs.getString("categoryid"));
        vo.setCategoryName(rs.getString("categoryname"));
        vo.setParentId(rs.getString("parentId"));
        vo.setType(rs.getString("type"));
        vo.setCatalogType(rs.getString("catalogType"));
        vo.setDecrisption(rs.getString("decrisption"));
        vo.setPicUrl(rs.getString("picUrl"));
        vo.setLupDate(rs.getDate("lupDate"));
        vo.setTotal(rs.getString("total"));
        vo.setCityId(rs.getString("cityid"));
        vo.setPlatForm(rs.getString("platform"));
        vo.setSortId(rs.getInt("sortid"));
    }

    /**
     * 用于返回图书货架列表
     * 
     * @return
     * @throws BOException
     */
    public List queryBookCategoryList() throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("queryNewMusicCategoryList( ) is starting ...");
        }

        // select * from t_rb_category t order by t.categoryid
        String sqlCode = "book.BookCategoryDAO.queryBookCategoryList().SELECT";
        ResultSet rs = null;
        List list = new ArrayList();

        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, null);

            while (rs.next())
            {
                BookCategoryVO vo = new BookCategoryVO();

                fromBookCategoryVOByRs(vo, rs);

                list.add(vo);
            }
        }
        catch (DAOException e)
        {
            throw new DAOException("得到图书货架表信息查询发生异常:", e);
        }
        catch (SQLException e)
        {
            throw new DAOException("得到图书货架表信息查询发生异常:", e);
        }
        finally
        {
            DB.close(rs);
        }

        return list;
    }

    /**
     * 用于返回图书货架信息
     * 
     * @param categoryId
     * @return
     * @throws BOException
     */
    public BookCategoryVO queryBookCategoryVO(String categoryId)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("queryBookCategoryVO(" + categoryId
                         + ") is starting ...");
        }

        // select * from t_rb_category t where t.id = ?
        String sqlCode = "book.BookCategoryDAO.queryBookCategoryVO().SELECT";
        ResultSet rs = null;
        BookCategoryVO vo = new BookCategoryVO();

        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode,
                                                 new Object[] { categoryId });

            if (rs.next())
            {
                fromBookCategoryVOByRs(vo, rs);
            }
        }
        catch (DAOException e)
        {
            throw new DAOException("得到图书货架表信息查询发生异常:", e);
        }
        catch (SQLException e)
        {
            throw new DAOException("得到图书货架表信息查询发生异常:", e);
        }
        finally
        {
            DB.close(rs);
        }

        return vo;
    }

    /**
     * 返回当前货架的子货架信息图书货架信息
     * 
     * @param categoryId
     * @return
     * @throws BOException
     */
    public int hasBookChild(String categoryId) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("hasBookChild(" + categoryId + ") is starting ...");
        }

        // select count(*) as countNum from t_rb_category t where t.parentid = ?
        String sqlCode = "book.BookCategoryDAO.hasBookChild().SELECT";
        ResultSet rs = null;
        int countNum = 0;

        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode,
                                                 new Object[] { categoryId });

            if (rs.next())
            {
                countNum = rs.getInt("countNum");
            }
        }
        catch (DAOException e)
        {
            throw new DAOException("返回当前货架的子货架信息图书货架信息发生异常:", e);
        }
        catch (SQLException e)
        {
            throw new DAOException("返回当前货架的子货架信息图书货架信息发生异常:", e);
        }
        finally
        {
            DB.close(rs);
        }

        return countNum;
    }

    /**
     * 返回唯一主键id
     * 
     * @return
     * @throws DAOException
     */
    public String getBookVOId() throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug(" getBookVOId()");
        }
        
        String temp = "";
        try
        {
            temp = String.valueOf(DB.getSeqValue("SEQ_RB_CATEGORY_ID"));
        }
        catch (Exception e)
        {
            logger.error("saveBookCategory.getBookVOId():", e);
            throw new DAOException(e);
        }
        return temp;
    }
    
    /**
     * 用于新增图书货架
     * 
     * @param BookCategoryVO
     * @throws BOException
     */
    public void saveBookCategory(BookCategoryVO bookVO) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("saveBookCategory() is starting ...");
        }

        // insert into t_rb_category c (id, categoryid, categoryname, parentid,
        // catalogtype, type, decrisption, lupdate, picurl, total, platform,
        // cityid, sortid) values (?, '', ?, ?, ?, ?, ?, sysdate, ?, 0, ?, ?, ?)
        String sqlCode = "book.BookCategoryDAO.saveBookCategory().save";

        try
        {
            DB.getInstance()
              .executeBySQLCode(sqlCode,
                                new Object[] { bookVO.getCategoryId(),
                                                bookVO.getCategoryName(),
                                                bookVO.getParentId(),
                                                bookVO.getCatalogType(),
                                                bookVO.getType(),
                                                bookVO.getDecrisption(),
                                                bookVO.getPicUrl(),
                                                bookVO.getPlatForm(),
                                                bookVO.getCityId(),
                                                String.valueOf(bookVO.getSortId())});
        }
        catch (DAOException e)
        {
            throw new DAOException("新增图书货架时发生异常:", e);
        }
    }

    /**
     * 用于删除指定货架
     * 
     * @return
     * @throws BOException
     */
    public void delBookCategory(String categoryId) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("delBookCategory(" + categoryId + ") is starting ...");
        }

        // delete from t_rb_category r where r.id = ?
        String sqlCode = "book.BookCategoryDAO.delBookCategory().del";

        try
        {
            DB.getInstance().executeBySQLCode(sqlCode,
                                              new Object[] { categoryId });
        }
        catch (DAOException e)
        {
            throw new DAOException("删除指定货架发生异常:", e);
        }
    }

    /**
     * 用于变更图书货架
     * 
     * @param newMusicCategory
     * @throws BOException
     */
    public void updateBookCategory(BookCategoryVO bookVO) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("updateBookCategory() is starting ...");
        }

        // update t_rb_category c set c.categoryname = ?, c.type = ?, c.lupdate
        // = sysdate, c.decrisption = ?, c.catalogtype = ?, c.platform = ?,
        // c.cityid = ?, c.picurl=?, c.sortid=? where c.id = ?
        String sqlCode = "book.BookCategoryDAO.updateBookCategory().update";

        try
        {
            DB.getInstance()
              .executeBySQLCode(sqlCode,
                                new Object[] { bookVO.getCategoryName(),
                                                bookVO.getType(),
                                                bookVO.getDecrisption(),
                                                bookVO.getCatalogType(),
                                                bookVO.getPlatForm(),
                                                bookVO.getCityId(),
                                                bookVO.getPicUrl(),
                                                String.valueOf(bookVO.getSortId()),
                                                bookVO.getCategoryId() });
        }
        catch (DAOException e)
        {
            throw new DAOException("变更图书货架时发生异常:", e);
        }
    }

    /**
     * 当前父货架下是否已存在相同名称货架
     * 
     * @param parentId
     * @return 货架名称
     */
    public int hasBookNameInParentId(String parentId, String name) throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("hasBookNameInParentId(" + parentId + ") is start...");
        }
        
        // select count(*) as countNum from t_rb_category t where t.parentid = ? and t.categoryname = ?
        String sqlCode = "book.BookCategoryDAO.hasBookNameInParentId().select";
        ResultSet rs = null;
        int countNum = 0;

        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode,
                                                 new Object[] { parentId, name});

            if (rs.next())
            {
                countNum = rs.getInt("countNum");
            }
        }
        catch (DAOException e)
        {
            throw new DAOException("返回当前父货架下是否已存在相同名称货架发生异常:", e);
        }
        catch (SQLException e)
        {
            throw new DAOException("返回当前父货架下是否已存在相同名称货架生异常:", e);
        }
        finally
        {
            DB.close(rs);
        }

        return countNum;
    }
    
    
    /****************************/
    
    
    /**
     * 用于返回新音乐货架扩展字段列表
     * 
     * @return
     * @throws BOException
     */
    public List queryBookCategoryKeyBaseList(String tid) throws DAOException
    {
		if (logger.isDebugEnabled())
		{
			logger.debug("queryBookCategoryKeyBaseList( ) is starting ...");
		}
		// select * from t_rb_category t where t.delflag = '0' order by
		// t.sortid
		String sqlCode = null;
		ResultSet rs = null;
		List list = new ArrayList();
		boolean insert = true;
		try
		{
			if (tid != null && !tid.equals(""))
			{
				//select *  from t_key_base b,     (select *   from t_key_resource r   where r.tid = ?) y   where b.keytable = 't_rb_category' and b.keyid = y.keyid(+)
				sqlCode = "book.BookCategoryDAO.queryBookCategoryKeyBaseResList().SELECT";
				Object[] paras = { tid };
				rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
				insert = false;
			}
			else
			{
				//select *  from t_key_base b   where b.keytable = 't_rb_category' 
				sqlCode = "book.BookCategoryDAO.queryBookCategoryKeyBaseList().SELECT";
				rs = DB.getInstance().queryBySQLCode(sqlCode, null);
				insert = true;
			}
			while (rs.next())
			{
				ResourceVO vo = new ResourceVO();
				fromBookCategoryKeyBaseVOByRs(vo, rs, insert);
				list.add(vo);
			}
		} catch (DAOException e)
		{
			throw new DAOException("获取音乐货架扩展信息表信息查询发生异常:", e);
		} catch (SQLException e)
		{
			throw new DAOException("获取音乐货架扩展信息表信息查询发生异常:", e);
		} finally
		{
			DB.close(rs);
		}

		return list;
	}
    /**
     * 为对象属性赋值
     * 
     * @param vo
     * @param rs
     * @throws SQLException
     */
    private void fromBookCategoryKeyBaseVOByRs(ResourceVO vo, ResultSet rs,boolean insert)
                    throws SQLException
    {
        vo.setKeyid(rs.getString("keyid"));
        vo.setKeyname(rs.getString("keyname"));
        vo.setKeytable(rs.getString("keytable"));
        vo.setKeydesc(rs.getString("keydesc"));
        vo.setKeyType(rs.getString("keytype"));
        if(!insert){
        	vo.setTid(rs.getString("tid"));
            vo.setValue(rs.getString("value"));
        }
        
   
       
    }
    /**
     * 
     *@desc  根据seq 获取图书货架ID
     *@author dongke
     *Aug 8, 2011
     * @return
     * @throws DAOException
     */
    public String getBookCategoryId() throws DAOException{
    	
    	 if (logger.isDebugEnabled())
         {
             logger.debug("getBookCategoryId() is starting ...");
         }
         // select SEQ_RB_CATEGORY_ID.nextval from dual
         String sqlCode = "book.BookCategoryDAO.getBookCategoryId().SELECT";
         DB db = DB.getInstance();
         String  newMusicCategoryId = null;
         ResultSet rs = null;
         try
         {
             rs = db.queryBySQLCode(sqlCode,null);
             if(rs.next())
             {
            	 newMusicCategoryId = rs.getString(1);
             }
         }
         catch (Exception e)
         {
             throw new DAOException(e);
         }
         finally
         {
        	 db.close(rs) ;
         }
         return newMusicCategoryId;
    }
    
   
    /**
     * 
     *@desc 删除图书货架扩展字段
     *@author dongke
     *Aug 8, 2011
     * @param vo
     * @return
     * @throws DAOException 
     */
    public int delBookCategoryKeyResource(String tid) throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("delBookCategoryKeyResource() is starting ...");
		}
		// delete from  t_key_resource  where   tid=? and keyid in (select keyid from t_key_base b where b.keytable='t_rb_category')
		String sqlCode = "book.BookCategoryDAO.delBookCategoryKeyResource().delete";
		int r = 0;
		try
		{
			r = DB.getInstance().executeBySQLCode(sqlCode,
					new Object[] { tid });
		} catch (DAOException e)
		{
			throw new DAOException("删除图书货架扩展字段值时发生异常:", e);
		}
		return r;
	}
}
