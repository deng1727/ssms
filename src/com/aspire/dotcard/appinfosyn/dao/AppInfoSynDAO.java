package com.aspire.dotcard.appinfosyn.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.DataAccessException;
import com.aspire.common.persistence.util.SQLCode;
import com.aspire.dotcard.appinfosyn.AppInfoVO;

public class AppInfoSynDAO {
	protected static JLogger logger = LoggerFactory
	.getLogger(AppInfoSynDAO.class);

private static AppInfoSynDAO dao = new AppInfoSynDAO();

private AppInfoSynDAO() {
}

public static AppInfoSynDAO getInstance() {
return dao;
}
	/**
	 * 
	 * @return
	 */
	public void insertAppInfo(AppInfoVO appinfo) {
		if (logger.isDebugEnabled())
		{
			logger.debug("添加应用信息,开始");
		}
		//update t_v_sprogram p set p.exestatus = '1'  where p.programid = ? and p.cmsid = ?
		String sqlCode = "com.aspire.dotcard.appinfosyn.impl.AppXMLFile.getInsertSqlCodeByAppInfo";
		try
		{
			DB.getInstance().executeBySQLCode(sqlCode, new String[]{
					appinfo.getAppName(),appinfo.getAppURL(),appinfo.getAppID(),appinfo.getAppLogo(),appinfo.getAppType1(),
					appinfo.getAppType2(),appinfo.getAppDetail(),appinfo.getAppSp(),appinfo.getAppVersion(),String.valueOf(appinfo.getAppPrice()),
					appinfo.getAppScore(),String.valueOf(appinfo.getAppScoreNum()),appinfo.getAppPic(),appinfo.getAppSize(),appinfo.getAppUpdateDate(),
					appinfo.getAppSupportSys(),appinfo.getAppRelates(),appinfo.getPic1(),appinfo.getPic2(),appinfo.getPic3(),appinfo.getPic4(),appinfo.getPic5(),appinfo.getPic6(),appinfo.getPic7(),appinfo.getPic8()
			
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
	
  public Map<String, String> queryAppId(){
		String sqlCode = "com.aspire.dotcard.appinfosyn.impl.AppXMLFile.querySqlCodeByAppInfo";
		ResultSet rs =null;
		Map<String, String> appIDMap = new HashMap<String, String>();

			try
			{
				// select t.nodeid,t.programid from t_vo_program_mid t where
				// t.status = ?
				rs = DB
						.getInstance()
						.queryBySQLCode(
								sqlCode,
								null);
				while (rs.next())
				{
					appIDMap.put(rs.getString("NUMBE"), "");
				}
			}
			catch (SQLException e)
			{
				logger.error("数据库SQL执行异常，查询失败", e);
			}
			catch (DAOException ex)
			{
				logger.error("数据库操作异常，查询失败", ex);
			}
			finally
			{
				DB.close(rs);
			}
			
			return appIDMap;
		
  }
  public int deleteAppInfo(String appid){
		String sqlCode = "com.aspire.dotcard.appinfosyn.impl.AppXMLFile.deleteSqlCodeByAppInfo";
		int a =0;
		try {

			a= DB.getInstance().executeBySQLCode(sqlCode, new String[]{appid});
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return a;

}
  public int updateAppInfo(AppInfoVO appinfo){
		String sqlCode = "com.aspire.dotcard.appinfosyn.impl.AppXMLFile.updateSqlCodeByAppInfo";
		int a =0;
		try {
			a= DB.getInstance().executeBySQLCode(sqlCode, new String[]{
					appinfo.getAppName(),appinfo.getAppURL(),appinfo.getAppLogo(),appinfo.getAppType1(),
					appinfo.getAppType2(),appinfo.getAppDetail(),appinfo.getAppSp(),appinfo.getAppVersion(),String.valueOf(appinfo.getAppPrice()),
					appinfo.getAppScore(),String.valueOf(appinfo.getAppScoreNum()),appinfo.getAppPic(),appinfo.getAppSize(),appinfo.getAppUpdateDate(),
					appinfo.getAppSupportSys(),appinfo.getAppRelates(),appinfo.getPic1(),appinfo.getPic2(),appinfo.getPic3(),appinfo.getPic4(),appinfo.getPic5(),appinfo.getPic6(),appinfo.getPic7(),appinfo.getPic8(),appinfo.getAppID()
			
			});
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return a;
}
}
