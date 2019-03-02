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
public class BaseMusicDBOpration
{

	/**
	 * ��־����
	 */
	protected static JLogger logger = LoggerFactory.getLogger(BaseMusicDBOpration.class);

	private BMusicVO newVO = null;

	public BaseMusicDBOpration(BMusicVO vo)
	{
		this.newVO = vo;
	}

	/**
	 * ����
	 * 
	 */
	public void InsertBMusicVO()
	{
		String insertSqlCode = "com.aspire.dotcard.basemusic.BaseMusicDBOpration.insertSqlCode";
		String paras[] = { newVO.getSongname(), newVO.getSinger(), newVO.getValidity(),
				newVO.getMusicId() };
		try
		{
			int result = BMusicDAO.getInstance().execueSqlCode(insertSqlCode, paras);
			logger.debug("����������֣�" + this.newVO.getMusicId() + " �ɹ���");
		} catch (DAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("����������֣�" + this.newVO.getMusicId() + " ʧ�ܣ�", e);
		}

	}
	public void addUpdateBMusicVO()
	{
		String updateSqlCode = "com.aspire.dotcard.basemusic.BaseMusicDBOpration.addupdateSqlCode";
		Object paras[] = { newVO.getSongname(), newVO.getSinger(), newVO.getValidity(),
				new Integer(newVO.getDelFlag()), newVO.getMusicId() };
		try
		{
			int result = BMusicDAO.getInstance().execueSqlCode(updateSqlCode, paras);
		} catch (DAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("���»������֣�" + this.newVO.getMusicId() + " ʧ�ܣ�", e);
		}
	}
	public void UpdateBMusicVO()
	{
		String updateSqlCode = "com.aspire.dotcard.basemusic.BaseMusicDBOpration.updateSqlCode";
		Object paras[] = { newVO.getSongname(), newVO.getSinger(), newVO.getValidity(),
				 newVO.getMusicId() };
		try
		{
			int result = BMusicDAO.getInstance().execueSqlCode(updateSqlCode, paras);
		} catch (DAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("���»������֣�" + this.newVO.getMusicId() + " ʧ�ܣ�", e);
		}
	}

	public void DelBMusicVO()
	{
		String delBMusicReferSQLCode = "com.aspire.dotcard.basemusic.BaseMusicDBOpration.delBMusicReferSQLCode";
		String delBMusicSQLCode = "com.aspire.dotcard.basemusic.BaseMusicDBOpration.delBMusicSQLCode";
		String delMOPasMusicSqlCode="com.aspire.dotcard.basemusic.BaseMusicDBOpration.delMOPasMusicSqlCode";
		//select * from t_firstpage_recommend where tabid in(select tabid from t_tab_manage where cutname='music') and contentid='3000001001'

		Object paras1[] = { newVO.getMusicId() };
		Object paras2[] = { new Integer(newVO.getDelFlag()), newVO.getMusicId() };
		try
		{
			// ɾ����Ʒ
			int result = BMusicDAO.getInstance().execueSqlCode(delBMusicReferSQLCode,
					paras1);
			int result2 = BMusicDAO.getInstance().execueSqlCode(delBMusicSQLCode, paras2);
			
			int result3 = BMusicDAO.getInstance().execueSqlCode(delMOPasMusicSqlCode, paras1);
			
		} catch (DAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("ɾ���������֣�" + this.newVO.getMusicId() + " ʧ�ܣ�", e);
		}
	}
}
