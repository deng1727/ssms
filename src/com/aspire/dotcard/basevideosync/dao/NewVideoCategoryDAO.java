/*
 * 文件名：NewMusicCategoryDAO.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */

package com.aspire.dotcard.basevideosync.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datafield.vo.ResourceVO;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.newmusicsys.vo.NewMusicCategoryVO;

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
public class NewVideoCategoryDAO
{

    /**
     * 记录日志的实例对象
     */
    protected static JLogger logger = LoggerFactory.getLogger(NewVideoCategoryDAO.class);

    /**
     * singleton模式的实例
     */
    private static NewVideoCategoryDAO instance = new NewVideoCategoryDAO();

    /**
     * 构造方法，由singleton模式调用
     */
    private NewVideoCategoryDAO()
    {
    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static NewVideoCategoryDAO getInstance()
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
    private void fromNewMusicCategoryVOByRs(NewMusicCategoryVO vo, ResultSet rs)
                    throws SQLException
    {
        vo.setCategoryId(rs.getString("categoryid"));
        vo.setCategoryName(rs.getString("categoryname"));
        vo.setParentCategoryId(rs.getString("parentcategoryid"));
        vo.setType(rs.getString("type"));
        vo.setDesc(rs.getString("categorydesc"));
        vo.setSortId(rs.getInt("sortid"));
        vo.setSum(rs.getInt("sum"));
        vo.setAlbumId(rs.getString("album_id"));
        vo.setAlbumPic(rs.getString("album_pic"));
        vo.setAlbumSinger(rs.getString("album_singer"));
        vo.setRate(rs.getInt("rate"));
        vo.setCityId(rs.getString("cityid"));
        vo.setPlatForm(rs.getString("platform"));
        vo.setCateType(rs.getString("catetype"));
        vo.setMusic_status(rs.getString("music_status"));
    }
    /**
     * 为对象属性赋值
     * 
     * @param vo
     * @param rs
     * @throws SQLException
     */
    private void fromNewMusicCategoryKeyBaseVOByRs(ResourceVO vo, ResultSet rs,boolean insert)
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
     * 用于返回新音乐货架列表
     * 
     * @return
     * @throws BOException
     */
    public List queryNewMusicCategoryList() throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("queryNewMusicCategoryList( ) is starting ...");
        }

        // select * from t_mb_category_new t where t.delflag = '0' order by
        // t.sortid
        String sqlCode = "newmusicsys.NewMusicCategoryDAO.queryNewMusicCategoryList().SELECT";
        ResultSet rs = null;
        List list = new ArrayList();

        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, null);

            while (rs.next())
            {
                NewMusicCategoryVO vo = new NewMusicCategoryVO();

                fromNewMusicCategoryVOByRs(vo, rs);

                list.add(vo);
            }
        }
        catch (DAOException e)
        {
            throw new DAOException("根据货架内码得到货架策略规则表信息查询发生异常:", e);
        }
        catch (SQLException e)
        {
            throw new DAOException("根据货架内码得到货架策略规则表信息查询发生异常:", e);
        }
        finally
        {
            DB.close(rs);
        }

        return list;
    }
   
    /**
	 * 用于返回新音乐货架信息
	 * 
	 * @param categoryId
	 * @return
	 * @throws BOException
	 */
    public NewMusicCategoryVO queryNewMusicCategoryVO(String categoryId)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("queryNewMusicCategoryVO(" + categoryId
                         + ") is starting ...");
        }

        // select * from t_mb_category_new t where t.delflag = '0' and
        // t.categoryId = ?
        String sqlCode = "newmusicsys.NewMusicCategoryDAO.queryNewMusicCategoryVO().SELECT";
        ResultSet rs = null;
        NewMusicCategoryVO vo = new NewMusicCategoryVO();

        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode,
                                                 new Object[] { categoryId });

            if (rs.next())
            {
                fromNewMusicCategoryVOByRs(vo, rs);
            }
        }
        catch (DAOException e)
        {
            throw new DAOException("返回新音乐货架信息查询发生异常:", e);
        }
        catch (SQLException e)
        {
            throw new DAOException("返回新音乐货架信息查询发生异常:", e);
        }
        finally
        {
            DB.close(rs);
        }

        return vo;
    }

    /**
     * 返回当前货架的子货架信息新音乐货架信息
     * 
     * @param categoryId
     * @return
     * @throws BOException
     */
    public int hasNewMusicChild(String categoryId) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("hasNewMusicChild(" + categoryId + ") is starting ...");
        }

        // select count(*) as countNum from t_mb_category_new t where t.delflag
        // = '0' and t.PARENTCATEGORYID = ?
        String sqlCode = "newmusicsys.NewMusicCategoryDAO.queryNewMusicChild().SELECT";
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
            throw new DAOException("返回当前货架的子货架信息新音乐货架信息发生异常:", e);
        }
        catch (SQLException e)
        {
            throw new DAOException("返回当前货架的子货架信息新音乐货架信息发生异常:", e);
        }
        finally
        {
            DB.close(rs);
        }

        return countNum;
    }

    /**
     * 用于删除指定货架
     * @param tdb
     * @param categoryId 货架编码
     * @return
     * @throws BOException
     */
    public void delNewVideoCategory(TransactionDB tdb,String categoryId) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("delNewMusicCategory(" + categoryId
                         + ") is starting ...");
        }

        // update t_v_category c set c.delflag=1 where c.categoryid=?
        String sqlCode = "newvideosys.NewVideoCategoryDAO.delNewVideoCategory().del";

        try
        {
        	tdb.executeBySQLCode(sqlCode,
                                              new Object[] { categoryId });
        }
        catch (DAOException e)
        {
            throw new DAOException("根据货架内码得到货架策略规则表信息查询发生异常:", e);
        }
    }

    /**
     * 用于新增新音乐货架
     * 
     * @param newMusicCategory
     * @throws BOException
     */
    public void saveNewMusicCategory(NewMusicCategoryVO newMusicCategory)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("saveNewMusicCategory() is starting ...");
        }

        // insert into t_mb_category_new c (categoryid, categoryname,
        // parentcategoryid, type, delflag, createtime, lupddate, categorydesc,
        // sortid, sum, album_id, album_pic, album_singer, platform, cityid,catetype)
        // values
        // (?,?,?,?,'0',sysdate,sysdate,?,?,0,'','','',?,?)
        String sqlCode = "newmusicsys.NewMusicCategoryDAO.saveNewMusicCategory().save";

        try
        {
            DB.getInstance()
              .executeBySQLCode(sqlCode,
                                new Object[] {
            		  							newMusicCategory.getCategoryId(),
                                                newMusicCategory.getCategoryName(),
                                                newMusicCategory.getParentCategoryId(),
                                                newMusicCategory.getType(),
                                                newMusicCategory.getDesc(),
                                                String.valueOf(newMusicCategory.getSortId()),
                                                newMusicCategory.getAlbumPic(),
                                                newMusicCategory.getPlatForm(),
                                                newMusicCategory.getCityId()
                                                ,newMusicCategory.getCateType()
                                                
              });
        }
        catch (DAOException e)
        {
            throw new DAOException("新增新音乐货架时发生异常:", e);
        }
    }

    /**
     * 用于变更新音乐货架
     * 
     * @param newMusicCategory
     * @throws BOException
     */
    public void updateNewMusicCategory(NewMusicCategoryVO newMusicCategory)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("updateNewMusicCategory() is starting ...");
        }

        // update t_mb_category_new c set
        // c.categoryname=?,c.type=?,c.lupddate=to_char(sysdate,'yyyy-MM-dd hh24:mi:ss'),c.categorydesc=?,c.sortid=?,c.platform=?,c.cityid=?,album_pic=?,catetype=?
        // where c.categoryid=?
        String sqlCode = "newmusicsys.NewMusicCategoryDAO.updateNewMusicCategory().update";

        try
        {
            DB.getInstance()
              .executeBySQLCode(sqlCode,
                                new Object[] {
                                                newMusicCategory.getCategoryName(),
                                                newMusicCategory.getType(),
                                                newMusicCategory.getDesc(),
                                                String.valueOf(newMusicCategory.getSortId()),
                                                newMusicCategory.getPlatForm(),
                                                newMusicCategory.getCityId(),
                                                newMusicCategory.getAlbumPic(),
                                                newMusicCategory.getCateType(),
                                                newMusicCategory.getCategoryId() });
        }
        catch (DAOException e)
        {
            throw new DAOException("变更新音乐货架时发生异常:", e);
        }
    }
    
    
    /**
     * 用于返回新音乐货架列表
     * 
     * @return
     * @throws BOException
     */
    public List queryNewMusicCategoryKeyBaseList(String tid) throws DAOException
    {
		if (logger.isDebugEnabled())
		{
			logger.debug("queryNewMusicCategoryKeyBaseList( ) is starting ...");
		}
		// select * from t_mb_category_new t where t.delflag = '0' order by
		// t.sortid
		String sqlCode = null;
		ResultSet rs = null;
		List list = new ArrayList();
		boolean insert = true;
		try
		{
			if (tid != null && !tid.equals(""))
			{
				sqlCode = "newmusicsys.NewMusicCategoryDAO.queryNewMusicCategoryKeyBaseResList().SELECT";
				Object[] paras = { tid };
				rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
				insert = false;
			}
			else
			{
				sqlCode = "newmusicsys.NewMusicCategoryDAO.queryNewMusicCategoryKeyBaseList().SELECT";
				rs = DB.getInstance().queryBySQLCode(sqlCode, null);
				insert = true;
			}
			while (rs.next())
			{
				ResourceVO vo = new ResourceVO();
				fromNewMusicCategoryKeyBaseVOByRs(vo, rs, insert);
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
     * 
     *@desc  根据seq 获取新的音乐货架ID
     *@author dongke
     *Aug 8, 2011
     * @return
     * @throws DAOException
     */
    public String getNewMusicCategoryId() throws DAOException{
    	
    	 if (logger.isDebugEnabled())
         {
             logger.debug("getNewMusicCategoryId() is starting ...");
         }
         // select SEQ_MB_CATEGORY_NEW_ID.nextval from dual
         String sqlCode = "newmusicsys.NewMusicCategoryDAO.getNewMusicCategoryId().SELECT";
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
    
//    
//    /**
//     * 
//     *@desc 新增保存音乐货架扩展字段
//     *@author dongke
//     *Aug 8, 2011
//     * @param vo
//     * @return
//     * @throws DAOException 
//     */
//    public int delNewVideoCategoryKeyResource(TransactionDB tdb,String tid) throws DAOException
//	{
//		if (logger.isDebugEnabled())
//		{
//			logger.debug("updateNewVideoCategory() is starting ...");
//		}
//		// delete from  t_key_resource  where   tid=? and keyid in (select keyid from t_key_base b where b.keytable='t_mb_category_new')
//		String sqlCode = "newmusicsys.NewMusicCategoryDAO.delNewMusicCategoryKeyResource().delete";
//		int r = 0;
//		try
//		{
//			r = tdb.executeBySQLCode(sqlCode,
//					new Object[] { tid });
//		} catch (DAOException e)
//		{
//			throw new DAOException("删除音乐货架扩展字段值时发生异常:", e);
//		}
//		return r;
//	}
    
    /**
     * 用于删除指定货架
     * @param categoryId 货架编码
     * @return
     * @throws DAOException
     */
    public void delMusicCategoryItem(String categoryId) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
        	logger.debug("delMusicCategoryItem(" + categoryId + ") is starting ...");
        }

        String sqlCode = "com.aspire.ponaadmin.web.newmusicsys.dao.NewMusicCategoryDAO.delMusicCategoryItem";

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
}
