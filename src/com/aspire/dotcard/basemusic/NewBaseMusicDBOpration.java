/**
 * SSMS
 * com.aspire.dotcard.basemusic BaseMusicDBOpration.java
 * May 8, 2010
 * @author tungke
 * @version 1.0
 *
 */
package com.aspire.dotcard.basemusic;

import com.aspire.common.db.DAOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;



/**
 * @author tungke
 *
 */
public class NewBaseMusicDBOpration
{

	/**
	 * 日志引用
	 */
	protected static JLogger logger = LoggerFactory.getLogger(BaseMusicDBOpration.class);

	private BMusicVO newVO = null;

	public NewBaseMusicDBOpration(BMusicVO vo)
	{
		this.newVO = vo;
	}

	/**
	 * 插入
	 * 
	 */
	public void InsertBMusicVO()
	{
		
		//insert into  t_mb_music_new (SONGNAME,SINGER,VALIDITY,CREATETIME,UPDATETIME,DELFLAG,PRODUCTMASK,MUSICID) values(?,?,?,to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),?,?,?)
		
		//insert into  t_mb_music_new (SONGNAME,SINGER,VALIDITY,CREATETIME,UPDATETIME,DELFLAG,PRODUCTMASK,SINGERSID,PUBTIME,ONLINETYPE,COLORTYPE,RINGTYPE,SONGTYPE,MUSICID) values(?,?,?,to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),?,?,?,?,?,?,?,?,?)
		
		String insertSqlCode = "com.aspire.dotcard.basemusic.NewBaseMusicDBOpration.insertSqlCode";
		Object paras[] = { newVO.getSongname(), newVO.getSinger(), newVO.getValidity(),new Integer(newVO.getDelFlag()),  newVO.getProductMask(),
				newVO.getSingersId(),newVO.getPubtime(),newVO.getOnlinetype(),newVO.getColortype(),newVO.getRingtype(),newVO.getSongtype(),newVO.getDolbytype(),
				newVO.getMusicImage(),newVO.getLosslessmusic(),newVO.getFormat320kbps(),newVO.getMusicId() };
//		Object paras[] = { newVO.getSongname(), newVO.getSinger(), newVO.getValidity(),new Integer(newVO.getDelFlag()),  newVO.getProductMask(),
//				
//				newVO.getMusicId() };
		try
		{
			int result = BMusicDAO.getInstance().execueSqlCode(insertSqlCode, paras);
			logger.debug("插入基地新音乐：" + this.newVO.getMusicId() + " 成功！");
		} catch (DAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("插入基地新音乐：" + this.newVO.getMusicId() + " 失败！", e);
		}

	}
	public void addUpdateBMusicVO()
	{
		//update t_mb_music_new  t set t.SONGNAME=?,t.SINGER=?,t.VALIDITY=?,t.delflag=?,UPDATETIME=to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),PRODUCTMASK=?,SINGERSID=?,PUBTIME=?,ONLINETYPE=?,COLORTYPE=?,RINGTYPE=?,SONGTYPE=?,DOLBYTYPE=?,music_pic=?  where t.MUSICID=?
		String updateSqlCode = "com.aspire.dotcard.basemusic.NewBaseMusicDBOpration.addupdateSqlCode";
		
		//update t_mb_music_new  t set t.SONGNAME=?,t.SINGER=?,t.VALIDITY=?,t.delflag=?,UPDATETIME=to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),PRODUCTMASK=?,SINGERSID=?,PUBTIME=?,ONLINETYPE=?,COLORTYPE=?,RINGTYPE=?,SONGTYPE=?,DOLBYTYPE=? where t.MUSICID=?
		String updateSqlCode_notPic = "com.aspire.dotcard.basemusic.NewBaseMusicDBOpration.addupdateSqlCode_notPic";
		
		
		Object paras[] = { newVO.getSongname(), newVO.getSinger(), newVO.getValidity(),
				new Integer(newVO.getDelFlag()),newVO.getProductMask(),newVO.getSingersId(),newVO.getPubtime(),
				newVO.getOnlinetype(),newVO.getColortype(),newVO.getRingtype(),newVO.getSongtype(),newVO.getDolbytype(), newVO.getMusicImage(),newVO.getLosslessmusic(),newVO.getFormat320kbps(), newVO.getMusicId() };
		Object paras_notPic[] = { newVO.getSongname(), newVO.getSinger(), newVO.getValidity(),
				new Integer(newVO.getDelFlag()),newVO.getProductMask(),newVO.getSingersId(),newVO.getPubtime(),
				newVO.getOnlinetype(),newVO.getColortype(),newVO.getRingtype(),newVO.getSongtype(),newVO.getDolbytype(),newVO.getLosslessmusic(),newVO.getFormat320kbps(),newVO.getMusicId() };
		try
		{
			if("".equals(newVO.getMusicImage()))
			{
				BMusicDAO.getInstance().execueSqlCode(updateSqlCode_notPic, paras_notPic);
			}
			else
			{
				BMusicDAO.getInstance().execueSqlCode(updateSqlCode, paras);
			}
			
		} catch (DAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("更新基地新音乐：" + this.newVO.getMusicId() + " 失败！", e);
		}
	}
	public void UpdateBMusicVO()
	{
		String updateSqlCode = "com.aspire.dotcard.basemusic.NewBaseMusicDBOpration.updateSqlCode";
		Object paras[] = { newVO.getSongname(), newVO.getSinger(), newVO.getValidity(),newVO.getProductMask(),
				newVO.getSingersId(),newVO.getPubtime(),
				newVO.getOnlinetype(),newVO.getColortype(),newVO.getRingtype(),newVO.getSongtype(),newVO.getDolbytype(),newVO.getLosslessmusic(),newVO.getFormat320kbps(),
				 newVO.getMusicId() };
		try
		{
			int result = BMusicDAO.getInstance().execueSqlCode(updateSqlCode, paras);
		} catch (DAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("更新基地新音乐：" + this.newVO.getMusicId() + " 失败！", e);
		}
	}

	public void DelBMusicVO()
	{
		String delBMusicReferSQLCode = "com.aspire.dotcard.basemusic.NewBaseMusicDBOpration.delBMusicReferSQLCode";
		String delBMusicSQLCode = "com.aspire.dotcard.basemusic.NewBaseMusicDBOpration.delBMusicSQLCode";
		Object paras1[] = { newVO.getMusicId() };
		Object paras2[] = { new Integer(newVO.getDelFlag()), newVO.getMusicId() };
		try
		{
			// 删除商品
			int result = BMusicDAO.getInstance().execueSqlCode(delBMusicReferSQLCode,
					paras1);
			int result2 = BMusicDAO.getInstance().execueSqlCode(delBMusicSQLCode, paras2);
		} catch (DAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("删除基地新音乐：" + this.newVO.getMusicId() + " 失败！", e);
		}
	}
	public void DelFullMusicVO(String musicid)
	{
		String delBMusicReferSQLCode = "com.aspire.dotcard.basemusic.NewBaseMusicDBOpration.delBFullMusicReferSQLCode";
		String delBMusicSQLCode = "com.aspire.dotcard.basemusic.NewBaseMusicDBOpration.delBFullMusicSQLCode";
		Object paras1[] = { musicid };
		Object paras2[] = { musicid };
		try
		{
			// 删除商品
			int result = BMusicDAO.getInstance().execueSqlCode(delBMusicReferSQLCode,
					paras1);
			int result2 = BMusicDAO.getInstance().execueSqlCode(delBMusicSQLCode, paras2);
		} catch (DAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("删除基地新音乐：" + musicid + " 失败！", e);
		}
	}
}
