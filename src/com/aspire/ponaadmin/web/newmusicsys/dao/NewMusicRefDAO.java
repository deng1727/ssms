/*
 * 文件名：NewMusicRefDAO.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */

package com.aspire.ponaadmin.web.newmusicsys.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
import com.aspire.ponaadmin.web.datafield.vo.ResourceVO;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.newmusicsys.vo.NewMusicRefVO;

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
public class NewMusicRefDAO
{
	/**
	 * 记录日志的实例对象
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(NewMusicRefDAO.class);
	
	/**
	 * singleton模式的实例
	 */
	private static NewMusicRefDAO instance = new NewMusicRefDAO();
	
	/**
	 * 构造方法，由singleton模式调用
	 */
	private NewMusicRefDAO()
	{}
	
	/**
	 * 获取实例
	 * 
	 * @return 实例
	 */
	public static NewMusicRefDAO getInstance()
	{
		return instance;
	}
	
	/**
	 * 应用类分页读取VO的实现类
	 */
	private class NewMusicRefPageVO implements PageVOInterface
	{
		public void CopyValFromResultSet(Object content, ResultSet rs)
				throws SQLException
		{
			NewMusicRefVO vo = (NewMusicRefVO) content;
			
			vo.setCategoryId(rs.getString("categoryId"));
			vo.setMusicId(rs.getString("musicId"));
			vo.setMusicName(rs.getString("musicName"));
			vo.setMusicPic(rs.getString("music_pic"));
			vo.setSinger(rs.getString("singer"));
			vo.setTags(rs.getString("tags"));
			vo.setValidity(rs.getString("validity"));
			vo.setSortId(rs.getInt("sortId"));
			vo.setOnlineType(rs.getInt("onlineType"));
			vo.setColoType(rs.getInt("colorType"));
			vo.setRingType(rs.getInt("ringType"));
			vo.setSongType(rs.getInt("songType"));
			vo.setShowCreateTime(rs.getString("showcreatetime"));
			vo.setVerify_status(rs.getString("VERIFY_STATUS"));
		}
		
		public Object createObject()
		{
			return new NewMusicRefVO();
		}
	}
	
	/**
	 * 用于查询当前货架下商品列表
	 * 
	 * @param page
	 * @param vo
	 * @throws DAOException
	 */
	public void queryNewMusicRefList(PageResult page, NewMusicRefVO vo)
			throws DAOException
	{
		
		if (logger.isDebugEnabled())
		{
			logger.debug("queryNewMusicRefList(" + vo.getCategoryId()
					+ ") is starting ...");
		}
		
		// select t.*,n.*,n.createtime as showcreatetime from t_mb_reference_new
		// t, t_mb_music_new n where
		// n.musicid=t.musicid
		String sqlCode = "newmusicsys.NewMusicRefDAO.queryNewMusicRefList().SELECT";
		String sql = null;
		
		try
		{
			sql = SQLCode.getInstance().getSQLStatement(sqlCode);
			
			StringBuffer sqlBuffer = new StringBuffer(sql);
			List paras = new ArrayList();
			// 构造搜索的sql和参数
			
			if (!"".equals(vo.getCategoryId()))
			{
				// sql += " and t.categoryId ='" + vo.getCategoryId() + "'";
				sqlBuffer.append(" and t.categoryId = ? ");
				paras.add(vo.getCategoryId());
			}
			if (!"".equals(vo.getMusicId()))
			{
				// sql += " and t.musicid like('%" + vo.getMusicId() + "%')";
				sqlBuffer.append(" and t.musicid like ? ");
				paras.add("%" + SQLUtil.escape(vo.getMusicId()) + "%");
			}
			if (!"".equals(vo.getMusicName()))
			{
				// sql += " and t.musicname like('%" + vo.getMusicName() +
				// "%')";
				sqlBuffer.append(" and t.musicname like ? ");
				paras.add("%" + SQLUtil.escape(vo.getMusicName()) + "%");
			}
			if (!"".equals(vo.getSinger()))
			{
				// sql += " and n.singer like('%" + vo.getSinger() + "%')";
				sqlBuffer.append(" and n.singer like ? ");
				paras.add("%" + SQLUtil.escape(vo.getSinger()) + "%");
			}
			if (!"".equals(vo.getShowCreateTime()))
			{
				sqlBuffer.append(" and n.createtime like ? ");
				paras.add("%" + SQLUtil.escape(vo.getShowCreateTime()) + "%");
			}
			
			if (!"".equals(vo.getVerify_status())&&!"-1".equals(vo.getVerify_status()))
     		{
    			if(vo.getVerify_status().contains(",")){
    				String[] strs = vo.getVerify_status().split(",");
    				sqlBuffer.append(" and t.VERIFY_STATUS in (" + strs[0] + "," + strs[1] + ")");
    			}else{
    				sqlBuffer.append(" and t.VERIFY_STATUS = " + vo.getVerify_status());
    			}
     		}
			
			// sql += " order by sortid desc";
			sqlBuffer.append(" order by sortid desc");
			
			// page.excute(sql, null, new NewMusicRefPageVO());
			page.excute(sqlBuffer.toString(), paras.toArray(),
					new NewMusicRefPageVO());
		}
		catch (DataAccessException e)
		{
			throw new DAOException(
					"从sql.properties中获取sql语句时发生错误，对应sqlCode不存在。", e);
		}
	}
	
	/**
	 * 用于移除指定货架下指定的新音乐
	 * 
	 * @param categoryId
	 *            货架id
	 * @param musicId
	 *            新音乐id列
	 * @throws DAOException
	 */
	public void removeNewMusicRefs(String categoryId, String[] musicId)
			throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("removeNewMusicRefs(" + categoryId
					+ ") is starting ...");
		}
		
		// update  t_mb_reference_new r set r.delflag = r.verify_status,r.verify_status = 0,r.VERIFY_DATE=sysdate where r.categoryid = ? and r.musicid = ?
		String sqlCode = "newmusicsys.NewMusicRefDAO.removeNewMusicRefs().remove";
		
		TransactionDB tdb = null;
        try
        {
        	 tdb = TransactionDB.getTransactionInstance();
        	 for (int i = 0; i < musicId.length; i++)
             {
        		 String[] temp = musicId[i].split("#");
        		 tdb.executeBySQLCode(sqlCode, new Object[]{categoryId,temp[0]});
             }
            this.setCategoryGoodsApproval(tdb, categoryId);
            tdb.commit();
        }
        catch (DAOException e)
        {
        	logger.error("移除指定货架下指定的音乐时发生异常:", e);
            // 执行回滚
            tdb.rollback();
            throw new DAOException("移除指定货架下指定的音乐时发生异常:", e);
        } finally {
            if (tdb != null) {
                tdb.close();
              }
        }
	}
	
	/**
	 * 用于查看指定货架中是否存在指定音乐
	 * 
	 * @param categoryId
	 *            货架id
	 * @param musicId
	 *            新音乐id列
	 * @throws DAOException
	 */
	public String isHasMusicRefs(String categoryId, String[] musicId)
			throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("isHasMusicRefs(" + categoryId + ") is starting ...");
		}
		
		// select * from t_mb_reference_new t, t_mb_music_new n where
		// n.musicid=t.musicid
		String sqlCode = "newmusicsys.NewMusicRefDAO.queryNewMusicRefList().SELECT";
		// StringBuffer sql;
		String sql;
		ResultSet rs = null;
		String ret = "";
		StringBuffer temp = new StringBuffer("");
		
		try
		{
			sql = SQLCode.getInstance().getSQLStatement(sqlCode);
			
			StringBuffer sqlBuffer = new StringBuffer(sql);
			List paras = new ArrayList();
			// 构造搜索的sql和参数
			
			// sql.append(" and t.categoryId='").append(categoryId).append("'
			// ");
			sqlBuffer.append(" and t.categoryId= ? ");
			paras.add(categoryId);
			
			for (int i = 0; i < musicId.length; i++)
			{
				temp.append("'").append(musicId[i]).append("'").append(",");
			}
			
			if (temp.length() > 0)
			{
				temp.deleteCharAt(temp.length() - 1);
				// sql.append(" and t.musicid in ( ").append(temp).append(" )");
				sqlBuffer.append(" and t.musicid in ( ? )");
				paras.add(temp.toString());
			}
			
			// rs = DB.getInstance().query(sql.toString(), null);
			rs = DB.getInstance().query(sqlBuffer.toString(), paras.toArray());
			
			while (rs.next())
			{
				ret += rs.getString("musicid") + ". ";
			}
			
		}
		catch (DataAccessException e)
		{
			throw new DAOException(
					"从sql.properties中获取sql语句时发生错误，对应sqlCode不存在。", e);
		}
		catch (SQLException e)
		{
			throw new DAOException("查看指定货架中是否存在指定音乐时发生异常:", e);
		}
		finally
		{
			DB.close(rs);
		}
		
		return ret;
	}
	
	/**
	 * 用于添加指定的新音乐至货架中
	 * 
	 * @param categoryId
	 *            货架id
	 * @param musicId
	 *            新音乐id列
	 * @throws DAOException
	 */
	public void addNewMusicRefs(String categoryId, String[] musicId)
			throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("addNewMusicRefs(" + categoryId + ") is starting ...");
		}
		
		// insert into t_mb_reference_new (musicid, categoryid, musicname, createtime, sortid,verify_status,VERIFY_DATE) 
		//values (?, ?, (select m.songname from t_mb_music_new m where m.musicid = ?), 
		//to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'), (select decode(max(sortid), null, 1, max(sortid) + 1) 
		//from t_mb_reference_new n where n.categoryid = ?),0,sysdate)
		String sqlCode = "newmusicsys.NewMusicRefDAO.addNewMusicRefs().add";
		 // 进行事务操作
        TransactionDB tdb = null;
        try {
       		tdb = TransactionDB.getTransactionInstance();
       		 for (int i = 0; i < musicId.length; i++)
               {
                   tdb.executeBySQLCode(sqlCode, new Object[]{musicId[i],categoryId, musicId[i],categoryId});
               }
       		   this.setCategoryGoodsApproval(tdb,categoryId);
              tdb.commit();
          }
          catch (DAOException e)
          {
          	logger.error("添加指定的新音乐至货架中时发生异常:", e);
  			// 执行回滚
  			tdb.rollback();
              throw new DAOException("添加指定的新音乐至货架中时发生异常:", e);
          } finally {
  			if (tdb != null) {
  				tdb.close();
  			}
  		}
	}
	
	/**
	 * 用于设置新音乐货架下音乐商品排序值
	 * 
	 * @param categoryId
	 *            货架id
	 * @param setSortId
	 *            新音乐排序id
	 * @throws DAOException
	 */
	public void setNewMusicSort(String categoryId, String[] setSortId)
			throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("setNewMusicSort(" + categoryId + ") is starting ...");
		}
		
		// update t_mb_reference_new r set r.sortid=?, r.verify_status = 0 ,r.VERIFY_DATE=sysdate where r.musicid=? and r.categoryid=?
		String sqlCode = "newmusicsys.NewMusicRefDAO.setNewMusicSort().set";
		 // 进行事务操作
        TransactionDB tdb = null;
        try {
       		tdb = TransactionDB.getTransactionInstance();
       		 for (int i = 0; i < setSortId.length; i++)
               {
                   String[] temp = setSortId[i].split("_");
                   tdb.executeBySQLCode(sqlCode, new Object[]{temp[1],temp[0],categoryId});
               }
       		   this.setCategoryGoodsApproval(tdb,categoryId);
              tdb.commit();
          }
          catch (DAOException e)
          {
          	logger.error("设置新音乐货架下音乐商品排序值时发生异常:", e);
  			// 执行回滚
  			tdb.rollback();
              throw new DAOException("设置新音乐货架下音乐商品排序值时发生异常:", e);
          } finally {
  			if (tdb != null) {
  				tdb.close();
  			}
  		}
	}
	
	/**
	 * 应用类分页读取VO的实现类
	 */
	private class NewMusicPageVO implements PageVOInterface
	{
		public void CopyValFromResultSet(Object content, ResultSet rs)
				throws SQLException
		{
			NewMusicRefVO vo = (NewMusicRefVO) content;
			
			vo.setMusicId(rs.getString("musicId"));
			vo.setMusicName(rs.getString("songname"));
			vo.setMusicPic(rs.getString("music_pic"));
			vo.setSinger(rs.getString("singer"));
			vo.setTags(rs.getString("tags"));
			vo.setValidity(rs.getString("validity"));
			vo.setOnlineType(rs.getInt("onlineType"));
			vo.setColoType(rs.getInt("colorType"));
			vo.setRingType(rs.getInt("ringType"));
			vo.setSongType(rs.getInt("songType"));
		}
		
		public Object createObject()
		{
			return new NewMusicRefVO();
		}
	}
	
	/**
	 * 用于查询新音乐列表
	 * 
	 * @param page
	 * @param vo
	 * @throws DAOException
	 */
	public void queryNewMusicList(PageResult page, NewMusicRefVO vo)
			throws DAOException
	{
		
		if (logger.isDebugEnabled())
		{
			logger.debug("queryNewMusicList( ) is starting ...");
		}
		
		// select * from t_mb_music_new t where
		// to_date(t.validity,'yyyy-MM-dd')>=trunc(sysdate)
		String sqlCode = "newmusicsys.NewMusicRefDAO.queryNewMusicList().SELECT";
		String sql = null;
		
		try
		{
			sql = SQLCode.getInstance().getSQLStatement(sqlCode);
			
			StringBuffer sqlBuffer = new StringBuffer(sql);
			List paras = new ArrayList();
			// 构造搜索的sql和参数
			
			if (!"".equals(vo.getMusicId()))
			{
				// sql += " and t.musicid like('%" + vo.getMusicId() + "%')";
				sqlBuffer.append(" and t.musicid like ? ");
				paras.add("%" + SQLUtil.escape(vo.getMusicId()) + "%");
			}
			if (!"".equals(vo.getMusicName()))
			{
				// sql += " and t.songname like('%" + vo.getMusicName() + "%')";
				sqlBuffer.append(" and t.songname like ? ");
				paras.add("%" + SQLUtil.escape(vo.getMusicName()) + "%");
			}
			if (!"".equals(vo.getSinger()))
			{
				// sql += " and t.singer like('%" + vo.getSinger() + "%')";
				sqlBuffer.append(" and t.singer like ? ");
				paras.add("%" + SQLUtil.escape(vo.getSinger()) + "%");
			}
			if (vo.getColorType() != null)
			{
				// sql += " and t.singer like('%" + vo.getSinger() + "%')";
				sqlBuffer.append(" and t.colortype = ?");
				paras.add("" + vo.getColorType() + "");
			}
			
			// sql += " order by musicid";
			sqlBuffer.append(" order by musicid");
			
			// page.excute(sql, null, new NewMusicPageVO());
			page.excute(sqlBuffer.toString(), paras.toArray(),
					new NewMusicPageVO());
		}
		catch (DataAccessException e)
		{
			throw new DAOException(
					"从sql.properties中获取sql语句时发生错误，对应sqlCode不存在。", e);
		}
	}
	
	/**
	 * 用于查询当前货架下是否有新音乐商品
	 * 
	 * @param page
	 * @param vo
	 * @throws DAOException
	 */
	public int hasNewMusic(String categoryId) throws DAOException
	{
		
		if (logger.isDebugEnabled())
		{
			logger.debug("queryNewMusicList( ) is starting ...");
		}
		
		// select count(*) as countNum from t_mb_reference_new t where
		// t.categoryId = ?
		String sqlCode = "newmusicsys.NewMusicRefDAO.hasNewMusic().SELECT";
		ResultSet rs = null;
		int countNum = 0;
		
		rs = DB.getInstance().queryBySQLCode(sqlCode,
				new Object[] { categoryId });
		
		try
		{
			if (rs.next())
			{
				countNum = rs.getInt("countNum");
			}
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
	 * 用于清空原货架下新音乐
	 * 
	 * @param categoryId
	 *            货架id
	 * @throws DAOException
	 */
	public void delNewMusicRef(String categoryId) throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("delNewMusicRef(" + categoryId + ") is starting ...");
		}
		
		// delete from t_mb_reference_new r where r.categoryid = ?
		String sql = "newmusicsys.NewMusicRefDAO.delNewMusicRef().remove";
		
		try
		{
			DB.getInstance().executeBySQLCode(sql, new Object[] { categoryId });
		}
		catch (DAOException e)
		{
			throw new DAOException("用于清空原货架下新音乐时发生异常:", e);
		}
	}
	
	/**
	 * 校驗文件中數據是否在新音乐表中存在
	 * 
	 * @param list
	 * @throws DAOException
	 */
	public String verifyNewMusic(List list) throws DAOException
	{
		
		if (logger.isDebugEnabled())
		{
			logger.debug("verifyBook() is starting ...");
		}
		
		// select 1 from t_mb_music_new c where c.musicid = ?
		String sql = "newmusicsys.NewMusicRefDAO.verifyNewMusic().select";
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		
		// 迭代查詢
		for (int i = 0; i < list.size(); i++)
		{
			String temp = (String) list.get(i);
			try
			{
				rs = DB.getInstance().queryBySQLCode(sql.toString(),
						new Object[] { temp });
				// 如果不存在相應數據
				if (!rs.next())
				{
					list.remove(i);
					i--;
					sb.append(temp).append(". ");
				}
			}
			catch (SQLException e)
			{
				throw new DAOException("查看指定货架中是否存在指定音乐时发生异常:", e);
			}
			finally
			{
				DB.close(rs);
			}
		}
		
		return sb.toString();
	}
	
	/**
	 * 用于查询新音乐详情
	 * 
	 * @param page
	 * @param vo
	 * @throws DAOException
	 */
	public NewMusicRefVO queryNewMusicInfo(String musicId) throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("queryNewMusicInfo( ) is starting ...");
		}
		// select * from t_mb_music_new t where t.musicid=?
		String sqlCode = "newmusicsys.NewMusicRefDAO.queryNewMusicInfo().SELECT";
		DB db = DB.getInstance();
		ResultSet rs = null;
		Object paras[] = { musicId };
		NewMusicRefVO vo = null;
		try
		{
			rs = db.queryBySQLCode(sqlCode, paras);
			if (rs.next())
			{
				vo = new NewMusicRefVO();
				vo.setMusicId(musicId);
				vo.setMusicName(rs.getString("SONGNAME"));
				vo.setSinger(rs.getString("SINGER"));
				vo.setValidity(rs.getString("VALIDITY"));
			}
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DAOException("执行查询音乐详情失败", e);
		}
		finally
		{
			db.close(rs);
		}
		return vo;
	}
	
	/**
	 * 
	 * @desc 获取音乐的扩展字段
	 * @author dongke Aug 9, 2011
	 * @param musicId
	 * @return
	 * @throws DAOException
	 */
	public List queryNewMusicKeyResource(String musicId) throws DAOException
	{
		List keyResourceList = null;
		if (logger.isDebugEnabled())
		{
			logger.debug("queryNewMusicKeyResource( ) is starting ...");
		}
		// select * from t_key_base b, (select * from t_key_resource r where
		// r.tid = ?) y where b.keytable = 't_mb_music_new' and b.keyid =
		// y.keyid(+)
		String sqlCode = "newmusicsys.NewMusicRefDAO.queryNewMusicKeyResource().SELECT";
		DB db = DB.getInstance();
		ResultSet rs = null;
		Object paras[] = { musicId };
		try
		{
			keyResourceList = new ArrayList();
			rs = db.queryBySQLCode(sqlCode, paras);
			while (rs.next())
			{
				ResourceVO vo = new ResourceVO();
				vo.setKeyid(rs.getString("keyid"));
				vo.setKeyname(rs.getString("keyname"));
				vo.setKeytable(rs.getString("keytable"));
				vo.setKeydesc(rs.getString("keydesc"));
				vo.setKeyType(rs.getString("keytype"));
				vo.setTid(rs.getString("tid"));
				vo.setValue(rs.getString("value"));
				
				keyResourceList.add(vo);
			}
		}
		catch (SQLException e)
		{
			
			e.printStackTrace();
			throw new DAOException("执行查询音乐扩展字段详情失败", e);
		}
		finally
		{
			db.close(rs);
		}
		return keyResourceList;
	}
	
	// add by aiyan 2012-12-25 begin
	public void addReference(String categoryId, String musicid, String sortId)
			throws Exception
	{
		
		if (!isExistContent(musicid))
		{// 在音乐内容表如果没有找到这个内容就抛异常！
			throw new Exception("音乐商品导入的时候，内容id在t_mb_music_new表不存在：musicid:"
					+ musicid);
		}
		String sqlCode = "";
		String[] paras = null;
		if (isExistRef(categoryId, musicid))
		{
			sqlCode = "com.aspire.ponaadmin.web.newmusicsys.dao.NewMusicRefDAO.addReference.UPDATE";
			paras = new String[] { sortId, categoryId, musicid };
		}
		else
		{
			// insert into t_mb_reference_new (musicid, categoryid, musicname,createtime, sortid,
			//verify_status,verify_date,delflag) values (?, ?, (select m.songname from t_mb_music_new m 
			//where m.musicid = ?), to_char(sysdate,'YYYY-MM-DD HH24:MI:SS'), ?,0,sysdate,2)
			sqlCode = "com.aspire.ponaadmin.web.newmusicsys.dao.NewMusicRefDAO.addReference.INSERT";
			paras = new String[] { musicid, categoryId, musicid, sortId };
		}
		try
		{
			DB.getInstance().executeBySQLCode(sqlCode, paras);
		}
		catch (DAOException e)
		{
			logger.error("添加指定的内容至货架中时发生异常:", e);
			throw new DAOException("添加指定的内容至货架中时发生异常:", e);
		}
	}
	
	private boolean isExistRef(String categoryId, String contentId)
			throws Exception
	{
		String sqlCode = "com.aspire.ponaadmin.web.newmusicsys.dao.NewMusicRefDAO.isExistRef";
		String[] paras = new String[] { categoryId, contentId };
		ResultSet rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
		return rs.next();
	}
	
	private boolean isExistContent(String contentId) throws Exception
	{
		String sqlCode = "com.aspire.ponaadmin.web.newmusicsys.dao.NewMusicRefDAO.verifyContentId";
		String[] paras = new String[] { contentId };
		ResultSet rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
		return rs.next();
	}
	
	// add by aiyan 2012-12-25 end
	
	/**
	 * 用于查询当前货架下商品列表
	 * 
	 * @param page
	 * @param vo
	 * @throws DAOException
	 */
	public List queryListByExport(String categoryId) throws DAOException
	{
		
		if (logger.isDebugEnabled())
		{
			logger.debug("queryListByExport( ) is starting ...");
		}
		
		// select n.* from t_mb_reference_new t, t_mb_music_new n where n.musicid = t.musicid
		String sqlCode = "newmusicsys.NewMusicRefDAO.queryListByExport().SELECT";
		String sql = null;
		ResultSet rs = null;
		List list = new ArrayList();
		
		try
		{
			List paras = new ArrayList();
			sql = SQLCode.getInstance().getSQLStatement(sqlCode);
			StringBuffer sqlBuffer = new StringBuffer(sql);
			
			// 构造搜索的sql和参数
			if (!"".equals(categoryId))
			{
				// sql += " and t.categoryId ='" + vo.getCategoryId() + "'";
				sqlBuffer.append(" and t.categoryId = ? ");
				paras.add(categoryId);
			}
			
			sqlBuffer.append(" and t.verify_status = 1 ");
			
			// sql += " order by sortid desc";
			sqlBuffer.append(" order by sortid desc");
			
			rs = DB.getInstance().query(sqlBuffer.toString(), new Object[]{categoryId});
			
			while (rs.next())
			{
				NewMusicRefVO vo = new NewMusicRefVO();
				vo.setMusicId(rs.getString("musicid"));
				vo.setMusicName(rs.getString("SONGNAME"));
				vo.setSinger(rs.getString("SINGER"));
				list.add(vo);
			}
		}
		catch (DataAccessException e)
		{
			logger.error("从sql.properties中获取sql语句时发生异常:", e);
			throw new DAOException(
					"从sql.properties中获取sql语句时发生错误，对应sqlCode不存在。", e);
		}
		catch (SQLException e)
		{
			logger.error("执行查询音乐列表时发生异常:", e);
			throw new DAOException("执行查询音乐列表时失败", e);
		}
		finally
		{
			DB.close(rs);
		}
		
		return list;
	}
	
	/**
	 * 音乐商品货架提交审批
	 * 
	 * @param tdb
	 * @param categoryId 音乐货架编码
	 * @throws DAOException
	 */
	public void setCategoryGoodsApproval(TransactionDB tdb,String categoryId)
			throws DAOException {
		String sql = "com.aspire.ponaadmin.web.newmusicsys.dao.NewMusicRefDAO.setCategoryGoodsApproval";
		try {
			tdb.executeBySQLCode(sql, new Object[]{categoryId});
		} catch (DAOException e) {
			logger.error("设置音乐商品货架发生异常:", e);
            throw new DAOException("设置音乐商品货架发生异常:", e);
		}
	}
	
	/**
	 * 编辑音乐商品货架
	 * 
	 * @param categoryId 音乐货架编码
	 * @throws DAOException
	 */
	public void approvalCategoryGoods(String categoryId)
			throws BOException {
		String sql = "com.aspire.ponaadmin.web.newmusicsys.dao.NewMusicRefDAO.approvalCategoryGoods";
		try {
			DB.getInstance().executeBySQLCode(sql, new Object[]{categoryId});
		} catch (DAOException e) {
			logger.error("编辑音乐商品货架发生异常:", e);
            throw new BOException("编辑音乐商品货架发生异常:", e);
		}
	}
}
