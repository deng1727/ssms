/**
 * SSMS
 * com.aspire.dotcard.basemusic BaseMusicDBOpration.java
 * May 8, 2010
 * @author tungke
 * @version 1.0
 *
 */
package com.aspire.dotcard.basemusic;

import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;



/**
 * @author tungke
 *
 */
public class BaseMusicAlbumDBOpration
{

	/**
	 * 日志引用
	 */
	protected static JLogger logger = LoggerFactory.getLogger(BaseMusicAlbumDBOpration.class);

	private BNewMusicAlbumVO albumVo = null;

	public BaseMusicAlbumDBOpration(BNewMusicAlbumVO albumVo)
	{
		this.albumVo = albumVo;
	}
	
	
	
	/**
	 * 插入
	 * 
	 */
	public void InsertBMusicAlbumVO()
	{
		//insert into t_mb_category_new (CATEGORYID,PARENTCATEGORYID,CATEGORYNAME,CATEGORYDESC,TYPE,DELFLAG,SORTID,SUM,CREATETIME,ALBUM_ID,ALBUM_PIC,ALBUM_SINGER,ALBUM_UPCASE,PUBTIME,SINGERSID) values(SEQ_BM_CATEGORY_ID.nextval,?,?,?,1,0,?,0,to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),?,?,?,?,?,?)
		String insertSqlCode = "com.aspire.dotcard.basemusic.BaseMusicAlbumDBOpration.insertSqlCode";
		Object paras[] = { albumVo.getParentId(), albumVo.getAlbumName(),albumVo.getAlbumDesc(),  albumVo.getSortId(), albumVo.getAlbumId(),albumVo.getAlbumpicurl(),
				albumVo.getSinger(),albumVo.getAlbumupcase(),albumVo.getPubtime(),albumVo.getSingersid() };
		try
		{
			int result = BMusicDAO.getInstance().execueSqlCode(insertSqlCode, paras);
			logger.debug("插入基地新音乐专辑AlbumId：" + this.albumVo.getAlbumId() + " 成功！");
		} catch (DAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("插入基地新音乐专辑：" + this.albumVo.getAlbumId() + " 失败！", e);
		}

	}
	public void addUpdateBMusicAlbumVO()
	{
		//update t_mb_category_new t set t.CATEGORYNAME=?,t.ALBUM_PIC=?,t.ALBUM_SINGER=?,t.CATEGORYDESC=?,t.ALBUM_UPCASE=?,t.PUBTIME=?,t.SINGERSID=?, t.LUPDDATE=to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') where t.ALBUM_ID=?
		String updateSqlCode = "com.aspire.dotcard.basemusic.BaseMusicAlbumDBOpration.addupdateSqlCode";
		Object paras[] = { albumVo.getAlbumName(), albumVo.getAlbumpicurl(),albumVo.getSinger(),albumVo.getAlbumDesc(),
							albumVo.getAlbumupcase(),albumVo.getPubtime(),albumVo.getSingersid(),albumVo.getAlbumId() };
		try
		{
			int result = BMusicDAO.getInstance().execueSqlCode(updateSqlCode, paras);
			if(result != 1){
				logger.error("更新基地新音乐专辑"+ this.albumVo.getAlbumId()+"异常，更新结果集不为1：为="+result);
			}
		} catch (DAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("更新基地新音乐专辑：" + this.albumVo.getAlbumId() + " 失败！", e);
		}
	}
	
	

	/**
	 * 
	 *@desc 删除专辑
	 *@author dongke
	 *Sep 25, 2012
	 */
	public void DelBMusicAlbumVO()
	{
		
		//update  T_MB_CATEGORY_NEW t  set t.delflag=? where t.ALBUM_ID=?
		String delBMusicAlbumSQLCode = "com.aspire.dotcard.basemusic.BaseMusicAlbumDBOpration.delBMusicAlbumSQLCode";
		Object paras1[] = {new Integer(albumVo.getDelFlag()), albumVo.getAlbumId() };
		//delete from T_MB_REFERENCE_NEW t where  exists(select 1 from  T_MB_CATEGORY_NEW c where c.CATEGORYID=t.CATEGORYID and c.ALBUM_ID=?) 
		String delBMusicAlbumRefSQLCode = "com.aspire.dotcard.basemusic.BaseMusicAlbumDBOpration.delBMusicAlbumRefSQLCode";
		Object paras2[] = { albumVo.getAlbumId() };
		
		try
		{
			 int result2 = BMusicDAO.getInstance().execueSqlCode(delBMusicAlbumRefSQLCode, paras2);
			 logger.info("delete from T_MB_REFERENCE_NEW AlbumId="+albumVo.getAlbumId()+" ;num="+result2);
			int result1 = BMusicDAO.getInstance().execueSqlCode(delBMusicAlbumSQLCode, paras1);
			
		} catch (DAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("删除基地新音乐专辑：" + this.albumVo.getAlbumId() + " 失败！", e);
		}
	}
	
/**
 * 插入
 *
 *//*
	public void InsertNewBMusicAlbum()
	{
		String insertSqlCode = "com.aspire.dotcard.basemusic.BaseMusicAlbumDBOpration.InsertNewBMusicAlbum().insertSqlCode";
		int c = this.params.size();
		//{musicid,musicname,sortid,categoryid}
		String par[] = new String[c];
		
		for(int i =0 ; i < this.params.size();i++){
			par[i]=(String)this.params.get(i);
		}
	try
	{
		int result = BMusicDAO.getInstance().execueSqlCode(insertSqlCode,par);
		logger.debug("上架最新："+params+" 成功！");
	} catch (DAOException e)
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.error("插入基地音乐："+params+" 失败！",e);
	}
		
	}*/

	
}
