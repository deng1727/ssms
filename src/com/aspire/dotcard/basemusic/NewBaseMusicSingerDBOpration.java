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
public class NewBaseMusicSingerDBOpration
{

	/**
	 * ��־����
	 */
	protected static JLogger logger = LoggerFactory.getLogger(NewBaseMusicSingerDBOpration.class);

	private BNewMusicSingerVO newVO = null;

	public NewBaseMusicSingerDBOpration(BNewMusicSingerVO vo)
	{
		this.newVO = vo;
	}

	/**
	 * ����
	 * 
	 */
	public void InsertBMusicSingerVO()
	{
		//insert into T_MB_SINGER_NEW (SID,SINGERUPCASE,SINGERNAME,SINGERDESC,IMGURL,TYPE,DELFLAG,UPDDATE) values(?,?,?,?,?,?,?,sysdate);
		String insertSqlCode = "com.aspire.dotcard.basemusic.NewBaseMusicSingerDBOpration.insertSqlCode";
		Object paras[] = { newVO.getSingerId(), newVO.getSingerupcase(), newVO.getSingername(),newVO.getSingerdesc(),
				newVO.getSingerpicurl(),newVO.getSingertype(),new Integer(newVO.getDelFlag()) };
		try
		{
			int result = BMusicDAO.getInstance().execueSqlCode(insertSqlCode, paras);
			logger.debug("������������ָ��֣�" + this.newVO.getSingerId() + " �ɹ���");
		} catch (DAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("������������ָ��֣�" + this.newVO.getSingerId() + " ʧ�ܣ�", e);
		}

	}
	public void addUpdateBMusicSingerVO()
	{
		//update T_MB_SINGER_NEW t set t.SINGERUPCASE=?,t.SINGERNAME=?,t.SINGERDESC=?,t.IMGURL=?,t.DELFLAG=?, t.UPDDATE=sysdate where t.SID=?
		String updateSqlCode = "com.aspire.dotcard.basemusic.NewBaseMusicSingerDBOpration.addupdateSqlCode";
		Object paras[] = { newVO.getSingerupcase(), newVO.getSingername(), newVO.getSingerdesc(),
				newVO.getSingerpicurl(),new Integer(newVO.getDelFlag()),newVO.getSingerId() };
		try
		{
			int result = BMusicDAO.getInstance().execueSqlCode(updateSqlCode, paras);
		} catch (DAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("���»��������ָ��֣�" + this.newVO.getSingerId() + " ʧ�ܣ�", e);
		}
	}
/*	public void UpdateBMusicSingerVO()
	{
		String updateSqlCode = "com.aspire.dotcard.basemusic.NewBaseMusicSingerDBOpration.updateSqlCode";
		Object paras[] = { newVO.getSongname(), newVO.getSinger(), newVO.getValidity(),newVO.getProductMask(),
				 newVO.getMusicId() };
		try
		{
			int result = BMusicDAO.getInstance().execueSqlCode(updateSqlCode, paras);
		} catch (DAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("���»��������֣�" + this.newVO.getMusicId() + " ʧ�ܣ�", e);
		}
	}*/

	/**
	 * 
	 *@desc ɾ������
	 *@author dongke
	 *Sep 25, 2012
	 */
	public void DelBMusicSingerVO()
	{
		
		//update T_MB_SINGER_NEW t set t.DELFLAG='1' where t.sid=?
		String delBMusicSIngerSQLCode = "com.aspire.dotcard.basemusic.NewBaseMusicSingerDBOpration.delBMusicSingerSQLCode";
		Object paras1[] = { newVO.getSingerId() };
		
		try
		{
			
			int result2 = BMusicDAO.getInstance().execueSqlCode(delBMusicSIngerSQLCode, paras1);
		} catch (DAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("ɾ�����������ָ��֣�" + this.newVO.getSingerId() + " ʧ�ܣ�", e);
		}
	}
}
