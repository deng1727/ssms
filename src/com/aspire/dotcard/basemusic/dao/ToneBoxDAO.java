
package com.aspire.dotcard.basemusic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basemusic.BMusicDAO;
import com.aspire.dotcard.basemusic.vo.ToneBoxVO;



/**
 *¡Â“Ù∫–DAO
 */
public class ToneBoxDAO
{

	/**
	 * »’÷æ“˝”√
	 */
	protected static JLogger logger = LoggerFactory.getLogger(ToneBoxDAO.class);

	private ToneBoxVO newVO = null;
	
	public ToneBoxDAO()
	{
		
	}

	public ToneBoxDAO(ToneBoxVO vo)
	{
		this.newVO = vo;
	}

	/**
	 * ≤Â»Î¡Â“Ù∫–
	 * 
	 */
	public void insertToneBoxVO()
	{
		//insert into T_M_TONEBOX (ID,NAME,DESCRIPTION,CHARGE,VALID,updateTime) values(?,?,?,?,?,sysdate);
		
		String insertSqlCode = "com.aspire.dotcard.basemusic.dao.ToneBoxDAO.insertToneBoxVO.insert";
		Object paras[] = { newVO.getId(), newVO.getName(), newVO.getDescription(),newVO.getCharge(),newVO.getValid()};
		try
		{
			int result = BMusicDAO.getInstance().execueSqlCode(insertSqlCode, paras);
			logger.debug("≤Â»Î¡Â“Ù∫–£∫" + this.newVO.getId() + " ≥…π¶£°");
		} catch (DAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("≤Â»Î¡Â“Ù∫–£∫" + this.newVO.getId() + "  ß∞‹£°", e);
		}

	}
	
	/**
	 * ∏¸–¬¡Â“Ù∫–
	 */
	public void updateToneBoxVO()
	{
		//update T_M_TONEBOX t set t.NAME=?,t.DESCRIPTION=?,t.CHARGE=?,t.VALID=?,t.updateTime=sysdate where t.ID=?
		String updateSqlCode = "com.aspire.dotcard.basemusic.dao.ToneBoxDAO.updateToneBoxVO.update";
		Object paras[] = { newVO.getName(), newVO.getDescription(),newVO.getCharge(),newVO.getValid(),newVO.getId()};
		try
		{
			int result = BMusicDAO.getInstance().execueSqlCode(updateSqlCode, paras);
		} catch (DAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("∏¸–¬¡Â“Ù∫–£∫" + this.newVO.getId() + "  ß∞‹£°", e);
		}
	}


	/**
	 * 
	 *@desc …æ≥˝¡Â“Ù∫–
	 */
	public void delToneBoxVO()
	{
		
		//delete from T_M_TONEBOX  where ID=?
		String delBMusicSIngerSQLCode = "com.aspire.dotcard.basemusic.dao.ToneBoxDAO.delToneBoxVO.del";
		Object paras1[] = { newVO.getId() };
		
		try
		{
			
			int result2 = BMusicDAO.getInstance().execueSqlCode(delBMusicSIngerSQLCode, paras1);
		} catch (DAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("…æ≥˝¡Â“Ù∫–£∫" + this.newVO.getId() + "  ß∞‹£°", e);
		}
	}
	
	
	
	
	/**
	 * ¡Â“Ù∫–ID
	 * @return
	 * @throws DAOException 
	 * @throws SQLException 
	 */
	public HashMap getAllExistToneBoxID() throws DAOException
	{
		
		//select t.id,t.name from T_M_TONEBOX t 
		String sqlCode = "com.aspire.dotcard.basemusic.dao.ToneBoxDAO.getAllExistToneBoxID.SELECT";
		HashMap allExistToneBoxMap = null;
		ResultSet rs = null;
		try
		{
			
			rs = DB.getInstance().queryBySQLCode(sqlCode, null);
			//ResultSet rs = this.querySqlCode(sqlCode, );
			allExistToneBoxMap = new HashMap();
			while (rs.next())
			{
				allExistToneBoxMap.put(rs.getString("id"), rs.getString("name"));

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

	
	/**
	 * ¡Â“Ù∫–ID
	 * @return
	 * @throws DAOException 
	 * @throws SQLException 
	 */
	public boolean existToneBoxID(String id) throws DAOException
	{
		
		//select t.id,t.name from T_M_TONEBOX t where t.id=? 
		String sqlCode = "com.aspire.dotcard.basemusic.dao.ToneBoxDAO.existToneBoxID.SELECT";
		ResultSet rs = null;
		try
		{
			Object paras[] = {id};
			rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
			//ResultSet rs = this.querySqlCode(sqlCode, );
			if (rs.next())
			{
				return true;
			}
		} catch (SQLException e)
		{
			logger.error(" ˝æ›ø‚≤Ÿ◊˜ ß∞‹");
			throw new DAOException(" ˝æ›ø‚≤Ÿ◊˜ ß∞‹£¨" + e);

		}finally
		{
			 DB.close(rs);
			
		}
		return  false;
	}
	
	
}
