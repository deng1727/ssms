
package com.aspire.dotcard.basemusic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basemusic.BMusicDAO;
import com.aspire.dotcard.basemusic.vo.ToneBoxSongVO;



/**
 *¡Â“Ù∫–∏Ë«˙DAO
 */
public class ToneBoxSongDAO
{

	/**
	 * »’÷æ“˝”√
	 */
	protected static JLogger logger = LoggerFactory.getLogger(ToneBoxSongDAO.class);

	private ToneBoxSongVO newVO = null;
	
	public ToneBoxSongDAO()
	{
		
	}

	public ToneBoxSongDAO(ToneBoxSongVO vo)
	{
		this.newVO = vo;
	}

	/**
	 * ≤Â»Î¡Â“Ù∫–∏Ë«˙
	 * 
	 */
	public void insertToneBoxSongVO()
	{
		//insert into T_M_TONEBOX_SONG (ID,BOXID,SORTID,updateTime) values(?,?,?,sysdate)
		
		String insertSqlCode = "com.aspire.dotcard.basemusic.dao.ToneBoxSongDAO.insertToneBoxSongVO.insert";
		Object paras[] = { newVO.getId(), newVO.getBoxId(), newVO.getSortId()};
		try
		{
			int result = BMusicDAO.getInstance().execueSqlCode(insertSqlCode, paras);
			logger.debug("≤Â»Î¡Â“Ù∫–∏Ë«˙£∫" + this.newVO.getId() + " ≥…π¶£°");
		} catch (DAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("≤Â»Î¡Â“Ù∫–∏Ë«˙£∫" + this.newVO.getId() + "  ß∞‹£°", e);
		}

	}
	
	/**
	 * ∏¸–¬¡Â“Ù∫–∏Ë«˙
	 */
	public void updateToneBoxSongVO()
	{
		//update T_M_TONEBOX_SONG t set t.SORTID=?,t.updateTime=sysdate where t.ID=? and t.boxId=? 
		String updateSqlCode = "com.aspire.dotcard.basemusic.dao.ToneBoxSongDAO.updateToneBoxSongVO.update";
		Object paras[] = {newVO.getSortId(),newVO.getId(), newVO.getBoxId()};
		try
		{
			int result = BMusicDAO.getInstance().execueSqlCode(updateSqlCode, paras);
		} catch (DAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("∏¸–¬¡Â“Ù∫–∏Ë«˙£∫" + this.newVO.getId() + "  ß∞‹£°", e);
		}
	}


	/**
	 * 
	 *@desc …æ≥˝¡Â“Ù∫–∏Ë«˙
	 */
	public void delToneBoxSongVO()
	{
		
		//delete from T_M_TONEBOX_SONG  where ID=? and boxId=? 
		String delBMusicSIngerSQLCode = "com.aspire.dotcard.basemusic.dao.ToneBoxSongDAO.delToneBoxSongVO.del";
		Object paras1[] = { newVO.getId(),newVO.getBoxId() };
		
		try
		{
			
			int result2 = BMusicDAO.getInstance().execueSqlCode(delBMusicSIngerSQLCode, paras1);
		} catch (DAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("…æ≥˝¡Â“Ù∫–∏Ë«˙£∫" + this.newVO.getId() + "  ß∞‹£°", e);
		}
	}
	
	
	
	
	/**
	 * ¡Â“Ù∫–∏Ë«˙ID
	 * @return
	 * @throws DAOException 
	 * @throws SQLException 
	 */
	public HashMap getAllExistToneBoxSongID() throws DAOException
	{
		
		//select t.id,t.boxId from T_M_TONEBOX_SONG t 
		String sqlCode = "com.aspire.dotcard.basemusic.dao.ToneBoxSongDAO.getAllExistToneBoxSongID.SELECT";
		HashMap allExistToneBoxMap = null;
		ResultSet rs = null;
		try
		{
			
			rs = DB.getInstance().queryBySQLCode(sqlCode, null);
			//ResultSet rs = this.querySqlCode(sqlCode, );
			allExistToneBoxMap = new HashMap();
			while (rs.next())
			{
				allExistToneBoxMap.put(rs.getString("id")+"_"+rs.getString("boxId"), rs.getString("id")+"_"+rs.getString("boxId"));

			}
		} catch (SQLException e)
		{
			logger.error(" ˝æ›ø‚≤Ÿ◊˜ ß∞‹");
			throw new DAOException(" ˝æ›ø‚≤Ÿ◊˜ ß∞‹£¨" + e);

		}finally
		{
			 DB.close(rs);
			
		}
		return allExistToneBoxMap;
	}

	
	
	
	
}
