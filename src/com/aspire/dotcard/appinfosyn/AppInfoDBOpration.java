/**
 * SSMS
 * com.aspire.dotcard.basemusic BaseMusicDBOpration.java
 * May 8, 2010
 * @author tungke
 * @version 1.0
 *
 */
package com.aspire.dotcard.appinfosyn;


import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;



/**
 * @author tungke
 *
 */
public class AppInfoDBOpration
{

	/**
	 * 日志引用
	 */
	protected static JLogger logger = LoggerFactory.getLogger(AppInfoDBOpration.class);

	private AppInfoVO newVO = null;

	public AppInfoDBOpration(AppInfoVO vo)
	{
		this.newVO = vo;
	}

	/**
	 * 根据节目ID更新节目概览表的处理状态为已处理
	 * 
	 * @return
	 */
	public void insertAppInfo() {
		if (logger.isDebugEnabled())
		{
			logger.debug("添加应用信息,开始");
		}
		//update t_v_sprogram p set p.exestatus = '1'  where p.programid = ? and p.cmsid = ?
		String sqlCode = "com.aspire.dotcard.appinfosyn.impl.AppXMLFile.getInsertSqlCodeByAppInfo";
		try
		{
			DB.getInstance().executeBySQLCode(sqlCode, new String[]{
					newVO.getAppName(),newVO.getAppURL(),newVO.getAppID(),newVO.getAppLogo(),newVO.getAppType1(),
					newVO.getAppType2(),newVO.getAppDetail(),newVO.getAppSp(),newVO.getAppVersion(),String.valueOf(newVO.getAppPrice()),
					newVO.getAppScore(),String.valueOf(newVO.getAppScoreNum()),newVO.getAppPic(),newVO.getAppSize(),newVO.getAppUpdateDate(),
					newVO.getAppSupportSys(),newVO.getAppRelates(),newVO.getPic1(),newVO.getPic2(),newVO.getPic3(),newVO.getPic4(),newVO.getPic5(),newVO.getPic6(),newVO.getPic7(),newVO.getPic8()
			
			});
		}
		catch (DAOException e)
		{
			logger.error("添加应用信息处理失败:", e);
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("添加应用信息已处理,结束");
		}
	}
	
 
  public int deleteAppInfo(){
	  if (logger.isDebugEnabled())
		{
			logger.debug("删除应用信息,开始");
		}
		String sqlCode = "com.aspire.dotcard.appinfosyn.impl.AppXMLFile.deleteSqlCodeByAppInfo";
		int a =0;
		try {

			a= DB.getInstance().executeBySQLCode(sqlCode, new String[]{newVO.getAppID()});
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			logger.debug("删除应用信息失败,",e);
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("删除应用信息已处理,结束");
		}
		return a;

}
  public int updateAppInfo(){
	  if (logger.isDebugEnabled())
		{
			logger.debug("更新应用信息,开始");
		}
		String sqlCode = "com.aspire.dotcard.appinfosyn.impl.AppXMLFile.updateSqlCodeByAppInfo";
		int a =0;
		try {
			a= DB.getInstance().executeBySQLCode(sqlCode, new String[]{
					newVO.getAppName(),newVO.getAppURL(),newVO.getAppLogo(),newVO.getAppType1(),
					newVO.getAppType2(),newVO.getAppDetail(),newVO.getAppSp(),newVO.getAppVersion(),String.valueOf(newVO.getAppPrice()),
					newVO.getAppScore(),String.valueOf(newVO.getAppScoreNum()),newVO.getAppPic(),newVO.getAppSize(),newVO.getAppUpdateDate(),
					newVO.getAppSupportSys(),newVO.getAppRelates(),newVO.getPic1(),newVO.getPic2(),newVO.getPic3(),newVO.getPic4(),newVO.getPic5(),newVO.getPic6(),newVO.getPic7(),newVO.getPic8(),newVO.getAppID()
			
			});
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			logger.debug("更新应用信息失败,",e);
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("更新应用信息已处理,结束");
		}
		return a;
}
}
