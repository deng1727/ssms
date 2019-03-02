/**
 * com.aspire.ponaadmin.web.newmusic139.dao New139BaseSyncDAO.java
 * Apr 30, 2011
 *<p>
 * Copyright (c) 2003-2011 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights
 * Reserved
 * </p>
 * @author dongke
 * @version 1.0
 *
 */
package com.aspire.ponaadmin.web.newmusic139.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basemusic.BMusicDAO;

import com.aspire.ponaadmin.web.db.TransactionDB;


/**
 * @author tungke
 *139新音乐ID数据导入基础类
 */
public class New139BaseSyncDAO
{
	protected static JLogger logger = LoggerFactory.getLogger(New139BaseSyncDAO.class);

	private static New139BaseSyncDAO new13dao = new New139BaseSyncDAO();

	public static New139BaseSyncDAO getInstance()
	{
		return new13dao;
	}
	 /**
     * 支持事务的数据库操作器，如果为空表示是非事务类型的操作
     */
    private TransactionDB transactionDB;

    /**
     * 获取事务类型TransactionDB的实例 如果已经指定了，用已经指定的。如果没有指定，自己创建一个，注意自己创建的直接用不支持事务类型的即可
     * 
     * @return TransactionDB
     */
    private TransactionDB getTransactionDB()
    {

        if (this.transactionDB != null)
        {
            return this.transactionDB;
        }
        return TransactionDB.getInstance();
    }

    /**
     * 获取事务类型的DAO实例
     * 
     * @return AwardDAO
     */

    public static New139BaseSyncDAO getTransactionInstance(TransactionDB transactionDB)
    {

    	New139BaseSyncDAO dao = new New139BaseSyncDAO();
        dao.transactionDB = transactionDB;
        return dao;
    }
	
    /**
     * 
     *@desc 支持事务的update 专辑
     *@author dongke
     *Apr 30, 2011
     * @param Id
     * @throws DAOException
     */
    public void updateAlbum(Object[] paras) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("updateAlbum()");
        }
        // 通过传入的id字段找到相应的记录进行删除
        String sqlCode = "com.aspire.ponaadmin.web.newmusic139.dao.New139BaseSyncDAO.updateAlbum().update";
   //update  T_MB_CATEGORY_new t set t.lupddate=to_char(sysdate,'yyyy-mm-DD hh24:mm:ss'),t.CATEGORYNAME=?,t.CATEGORYDESC=?,t.SORTID=?,t.RATE=?,t.ALBUM_SINGER=? where t.CATEGORYID=? and t.ALBUM_ID=?
        TransactionDB tdb = this.getTransactionDB();
        tdb.executeBySQLCode(sqlCode, paras);
    }
    /**
     * 
     *@desc 支持事务的insert 专辑
     *@author dongke
     *Apr 30, 2011
     * @param Id
     * @throws DAOException
     */
    public void insertAlbum(Object[] paras) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("insertAlbum()");
        }
        String sqlCode = "com.aspire.ponaadmin.web.newmusic139.dao.New139BaseSyncDAO.insertAlbum().insert";
       //insert into t_mb_category_new (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, DELFLAG, CREATETIME, CATEGORYDESC, SORTID, SUM, ALBUM_ID, ALBUM_PIC, RATE, ALBUM_SINGER)values (?, ?, ?, '1', '0', to_char(sysdate,'yyyy-MM-dd hh24:mm:ss'), ?, ?, 0, ?, ?, ?, ?); 
        TransactionDB tdb = this.getTransactionDB();
        tdb.executeBySQLCode(sqlCode, paras);
    }
    
    /**
     * 
     *@desc 支持事务的update 专辑
     *@author dongke
     *Apr 30, 2011
     * @param Id
     * @throws DAOException
     */
    public void updateBillboard(Object[] paras) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("updateAlbum()");
        }
        // 通过传入的id字段找到相应的记录进行删除
        String sqlCode = "com.aspire.ponaadmin.web.newmusic139.dao.New139BaseSyncDAO.updateBillboard().update";
   //update  T_MB_CATEGORY_new t set t.lupddate=to_char(sysdate,'yyyy-mm-DD hh24:mm:ss'),t.CATEGORYNAME=?,t.CATEGORYDESC=? where t.CATEGORYID=? and t.ALBUM_ID=?
        TransactionDB tdb = this.getTransactionDB();
        tdb.executeBySQLCode(sqlCode, paras);
    }
    /**
     * 
     *@desc 支持事务的insert 专辑
     *@author dongke
     *Apr 30, 2011
     * @param Id
     * @throws DAOException
     */
    public void insertBillboard(Object[] paras) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("insertAlbum()");
        }
        String sqlCode = "com.aspire.ponaadmin.web.newmusic139.dao.New139BaseSyncDAO.insertBillboard().insert";
       //insert into t_mb_category_new (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, DELFLAG, CREATETIME,lupddate, CATEGORYDESC, SORTID, SUM, ALBUM_ID, ALBUM_PIC, RATE, ALBUM_SINGER)values (?, ?, ?, '1', '0', to_char(sysdate,'yyyy-MM-dd hh24:mm:ss'),to_char(sysdate,'yyyy-MM-dd hh24:mm:ss'), ?, 0, 0, ?, ?, 0, ''); 
        TransactionDB tdb = this.getTransactionDB();
        tdb.executeBySQLCode(sqlCode, paras);
    }
    /**
     * 
     *@desc 支持事务的delete 专辑
     *@author dongke
     *Apr 30, 2011
     * @param Id
     * @throws DAOException
     */
    public void delete139Category(String ParcategoryId,String albumId) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("deleteAlbum(ParcategoryId=" + ParcategoryId + ";albumId="+albumId+")");
        }
        String sqlCode = "com.aspire.ponaadmin.web.newmusic139.dao.New139BaseSyncDAO.deleteAlbum().DELETE";
        //delete from T_MB_CATEGORY_new t where t.PARENTCATEGORYID=? and t.ALBUM_ID=?
        Object[] paras = { ParcategoryId,albumId };
        TransactionDB tdb = this.getTransactionDB();
        tdb.executeBySQLCode(sqlCode, paras);
    }
    /**
     * 
     *@desc 支持事务的insert 音乐商品
     *@author dongke
     *Apr 30, 2011
     * @param Id
     * @throws DAOException
     */
    public void insertMusicRefence(Object [] paras) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("insertMusicRefence:musicId=();");
        }
        String sqlCode = "com.aspire.ponaadmin.web.newmusic139.dao.New139BaseSyncDAO.insertMusicRefence().insert";
       //insert into  T_MB_REFERENCE_new (MUSICID,CATEGORYID,MUSICNAME,CREATETIME,SORTID) values(?,?,?,to_char(sysdate,'yyyy-MM-dd hh24:mm:ss'),?)
        TransactionDB tdb = this.getTransactionDB();
        tdb.executeBySQLCode(sqlCode, paras);
    }
    /**
     * 
     *@desc 支持事务的delete 音乐商品
     *@author dongke
     *Apr 30, 2011
     * @param Id
     * @throws DAOException
     */
    public void deleteMusicCategoryRefence(String categoryId) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("deleteMusicRefence;categoryId="+categoryId);
        }
        String sqlCode = "com.aspire.ponaadmin.web.newmusic139.dao.New139BaseSyncDAO.deleteMusicCategoryRefence().DELETE";
        //delete from T_MB_REFERENCE_new t where t.CATEGORYID=?
        Object[] paras = { categoryId };
        TransactionDB tdb = this.getTransactionDB();
        tdb.executeBySQLCode(sqlCode, paras);
    }
	/**
	 * 
	 *@desc  获取货架ID
	 *@author dongke
	 *Apr 30, 2011
	 * @param ablumId
	 * @param parentCategoryId
	 * @return
	 * @throws DAOException
	 */
	
	public String getCategoryId(String ablumId,String parentCategoryId) throws DAOException {
		ResultSet rs = null;
		Object [] paras = {ablumId};
		String categoryId = null;
		String sqlCode = "com.aspire.ponaadmin.web.newmusic139.dao.New139BaseSyncDAO.getCategoryId.select";
		DB db = DB.getInstance();
		//SELECT categoryid FROM T_MB_CATEGORY_new t WHERE t.PARENTCATEGORYID=? and  t.album_id=?
//		SELECT categoryid FROM T_MB_CATEGORY_new t WHERE   t.album_id=?             //20110621 changed by dongke 
		try {
			rs = db.queryBySQLCode(sqlCode, paras);
			
			while (rs != null && rs.next()) {
				// 
				categoryId = rs.getString("categoryid");
			}
		} catch (SQLException e) {
			throw new DAOException("getCategoryId error.", e);

		} finally {
			db.close(rs);
		}
		return categoryId;
	}
	
	/**
	 * 
	 *@desc  获取音乐名称
	 *@author dongke
	 *Apr 30, 2011
	 * @param musicId
	 * @return
	 * @throws DAOException
	 */
	public String getMusicName(String musicId) throws DAOException {
		ResultSet rs = null;
		Object [] paras = {musicId};
		String songname = null;
		String sqlCode = "com.aspire.ponaadmin.web.newmusic139.dao.New139BaseSyncDAO.queryMusicName.select";
		DB db = DB.getInstance();
		//select t.songname from T_MB_MUSIC_new t where t.DELFLAG=0 and t.musicid=?
		try {
			rs = db.queryBySQLCode(sqlCode, paras);
			while (rs != null && rs.next()) {
				songname = rs.getString("songname");
			}
		} catch (SQLException e) {
			throw new DAOException("songname error.", e);

		} finally {
			db.close(rs);
		}
		return songname;
	}
	
	/**
	 * 
	 *@desc 根据seq 获取新的货架ID 
	 *@author dongke
	 *Apr 30, 2011
	 * @return
	 */
	public int getNewCategryId() {
		DB db = DB.getInstance();
		int cid = 0;
		String sqlCode = "com.aspire.ponaadmin.web.newmusic139.dao.New139BaseSyncDAO.getNewCategryId.seq";
		
		//SEQ_RB_CATEGORY_ID
		try {
			String sql = db.getSQLByCode(sqlCode);
			cid = db.getSeqValue(sql);
			
		} catch (DAOException e) {

			logger.error("获取getNewCategryId失败:",e);
			e.printStackTrace();

		}
		return cid;
		
		
	}
	
	/**
	 * 
	 *@desc 根据更新音乐标签信息 
	 *@author dongke
	 *Apr 30, 2011
	 * @return
	 */
	public int updateMusicTags(String musicId,String tags) {
		DB db = DB.getInstance();
		int cid = 0;
		//update T_MB_MUSIC_new t   set  t.TAGS=? where t.DELFLAG=0 and t.musicid=?
		Object [] paras = {tags,musicId};
		String sqlCode = "com.aspire.ponaadmin.web.newmusic139.dao.New139BaseSyncDAO.updateMusicTags.update";
		try {
			cid = db.executeBySQLCode(sqlCode,paras);
			
		} catch (DAOException e) {

			logger.error("更新音乐标签信息失败:",e);
			e.printStackTrace();

		}
		return cid;
		
		
	}
	
	/**
	 * 
	 *@desc 获取关键字 
	 *@author dongke
	 *Apr 30, 2011
	 * @return
	 */
	public int addUpdateKeyWorld(String  keyWorld) {
		DB db = DB.getInstance();
		int cid = 0;
		int re = this.selectKeyWorld(keyWorld);
		if(re<1){
			Object [] paras = {keyWorld};
			String sqlCodeInsert = "com.aspire.ponaadmin.web.newmusic139.dao.New139BaseSyncDAO.addUpdateKeyWorld.insert";
			//insert into T_MB_KEYWORD_new(keyword,key_id)values(?,SEQ_MB_new_KEYWORD.Nextval)
			try {
				cid = db.executeBySQLCode(sqlCodeInsert,paras);
				
			} catch (DAOException e) {

				logger.error("插入音乐搜索关键字失败:",e);
				e.printStackTrace();

			}
		}
		
		return cid;
		
		
	}
	/**
	 * 
	 *@desc 获取关键字 
	 *@author dongke
	 *Apr 30, 2011
	 * @return
	 */
	public int selectKeyWorld(String  keyWorld) {
		DB db = DB.getInstance();
		int cid = 0;
		ResultSet rs = null;
		//update T_MB_MUSIC_new t   set  t.TAGS=? where t.DELFLAG=0 and t.musicid=?
		Object [] paras = {keyWorld};
		String sqlCode = "com.aspire.ponaadmin.web.newmusic139.dao.New139BaseSyncDAO.selectKeyWorld.select";
		try {
			rs = db.queryBySQLCode(sqlCode,paras);
			
				if(rs.next()){
					cid = 1;
				}
			
		} catch (DAOException e) {

			logger.error("查询音乐搜索关键字信息失败:",e);
			e.printStackTrace();

		} catch (SQLException e2)
		{
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}finally{			
			db.close(rs);
		}
		return cid;
		
		
	}
	/**
	 * 
	 *@desc 删除关键字 
	 *@author dongke
	 *Apr 30, 2011
	 * @return
	 */
	public int deleteKeyWorld(String keyWorld) {
		DB db = DB.getInstance();
		int cid = 0;
		
		Object [] paras = {keyWorld};
		String sqlCode = "com.aspire.ponaadmin.web.newmusic139.dao.New139BaseSyncDAO.deleteKeyWorld.delete";
		//delete T_MB_KEYWORD_new where keyword=?
		try {
			cid = db.executeBySQLCode(sqlCode,paras);
			
		} catch (DAOException e) {

			logger.error("删除音乐搜索关键字失败:",e);
			e.printStackTrace();

		}
		return cid;
		
		
	}
	/**
	 * 
	 *@desc 查询新的音乐基地的音乐ID
	 *@author dongke
	 *Apr 28, 2011
	 * @param musicname
	 * @param singer
	 * @return
	 * select t.songname,t.musicid,t.singer,t.VALIDITY from T_MB_MUSIC_new t where t.singer like ? and t.songname like ? and t.delflag=0 and to_date(t.validity,'yyyy-mm-dd')>= sysdate
	 */
	public  List listNewMusicInfo(String musicname, String singer) {
		ResultSet rs = null;
		DB db = DB.getInstance();
		List resultList = null;
		try {
			resultList = new ArrayList();
			String sqlCode = "com.aspire.ponaadmin.web.newmusic139.dao.New139BaseSyncDAO.listNewMusicInfo.select";
			Object[] paras = { "%"+singer+"%","%"+musicname+"%" };
			rs = db.queryBySQLCode(sqlCode,paras);			
				while (rs.next()){
					String [] musicline = {rs.getString("songname"),rs.getString("musicid"),rs.getString("singer"),rs.getString("VALIDITY")};
					resultList.add(musicline);
				}
			
		} catch (DAOException e) {
			logger.debug("数据库查询异常", e);
			
		}catch (SQLException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
			logger.debug("数据库查询异常", e1);
		}finally{
			db.close(rs);
		}
		return resultList;
	}
	
	
	/**
	 * 
	 *@desc 根据更新音乐图片URL 
	 *@author dongke
	 *Apr 30, 2011
	 * @return
	 */
	public int updateMusicPic(Object[] paras) {
		DB db = DB.getInstance();
		int cid = 0;
		//update T_MB_MUSIC_new t   set  t.TAGS=? where t.DELFLAG=0 and t.musicid=?
		//Object [] paras = {url,musicId};
		String sqlCode = "com.aspire.ponaadmin.web.newmusic139.dao.New139BaseSyncDAO.updateMusicPic.update";
		try {
			cid = db.executeBySQLCode(sqlCode,paras);			
		} catch (DAOException e) {
			logger.error("更新音乐图片信息失败:",e);
			e.printStackTrace();
		}
		return cid;	
	}
	
	/**
	 * 
	 *@desc 更新专辑图片URL 
	 *@author dongke
	 *Apr 30, 2011
	 * @return
	 */
	public int updateAlbumPic(Object[] paras) {
		DB db = DB.getInstance();
		int cid = 0;
		//update T_MB_MUSIC_new t   set  t.TAGS=? where t.DELFLAG=0 and t.musicid=?
		//Object [] paras = {url,musicId};
		String sqlCode = "com.aspire.ponaadmin.web.newmusic139.dao.New139BaseSyncDAO.updateAlbumPic().update";
		try {
			cid = db.executeBySQLCode(sqlCode,paras);			
		} catch (DAOException e) {
			logger.error("更新专辑图片信息失败:",e);
			e.printStackTrace();
		}
		return cid;	
	}
	/**
	 * 
	 *@desc 更新榜单图片URL 
	 *@author dongke
	 *Apr 30, 2011
	 * @return
	 */
	public int updateBillboardPic(Object[] paras) {
		DB db = DB.getInstance();
		int cid = 0;
		//update T_MB_MUSIC_new t   set  t.TAGS=? where t.DELFLAG=0 and t.musicid=?
		//Object [] paras = {url,musicId};
		String sqlCode = "com.aspire.ponaadmin.web.newmusic139.dao.New139BaseSyncDAO.updateBillboardPic().update";
		try {
			cid = db.executeBySQLCode(sqlCode,paras);			
		} catch (DAOException e) {
			logger.error("更新榜单图片信息失败:",e);
			e.printStackTrace();
		}
		return cid;	
	}

	/**
	 * 
	 *@desc 下架过去的新基地音乐商品
	 *@author dongke
	 *May 5, 2011
	 * @return
	 * @throws DAOException 
	 */
public int delInvalNewBMusicRef() throws DAOException{
	DB db = DB.getInstance();
		String delSqlCode = "com.aspire.ponaadmin.web.newmusic139.dao.New139BaseSyncDAO.delInvalNewBMusicRef().DELETE";
		int result = 0;		
			 result = db.executeBySQLCode(delSqlCode, null);		
			return result;
		
	}
	
/**
 * 
 *@desc  更新新音乐 货架上商品数量
 *@author dongke
 *May 5, 2011
 * @return
 * @throws DAOException
 */
public int updateAllNewCategoryRefSum() throws DAOException{
//	更新货架上架商品数量
	String updateAllSqlCode = "com.aspire.ponaadmin.web.newmusic139.dao.New139BaseSyncDAO.updateAllNewCategoryRefSum().UPDATE";
	//Object paras1[] = { new Integer(sum), categoryID };
	int result = BMusicDAO.getInstance().execueSqlCode(updateAllSqlCode, null);
	return result;
	
}
}
