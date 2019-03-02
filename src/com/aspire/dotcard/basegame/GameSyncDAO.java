package com.aspire.dotcard.basegame;

import java.io.Reader;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.RowSet;

import com.aspire.common.config.ArrayValue;
import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.DataAccessException;
import com.aspire.common.persistence.util.SQLCode;
import com.aspire.dotcard.gcontent.GAppGame;
import com.aspire.dotcard.syncData.dao.DataSyncDAO;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.implement.game.GameSyncTools;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.util.DateUtil;
import com.aspire.ponaadmin.web.util.PublicUtil;

public class GameSyncDAO
{
	/**
	 *  日志引用
	 */
	private static JLogger logger = LoggerFactory.getLogger(GameSyncDAO.class);

	private static GameSyncDAO dao = new GameSyncDAO();
	
	private static Map provinceMap = null;

	public static GameSyncDAO getInstance()
	{
		return dao;
	}

	/**
	 * 新增游戏分类
	 * 
	 * @param vo
	 *            游戏业务的vo类
	 * @return 返回新增结果
	 */
	public int insertGameType(GameType vo)throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("insertGameType(),id=" + vo.getId()
					+ ",name=" + vo.getName());
		}
		Object paras[] = {vo.getId(),vo.getName()};
		String sqlCode = "com.aspire.dotcard.basegame.insertGameType";
		DB.getInstance().executeBySQLCode(sqlCode, paras);
		return DataSyncConstants.SUCCESS_ADD;
	}

	/**
	 * 更新游戏业务信息
	 * 
	 * @return
	 */
	public void updateGameType(GameType vo) throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("updateGameType(),id=" + vo.getId()
					+ ",name=" + vo.getName());
		}
		Object paras[] = { vo.getName(),vo.getId()};
		String sqlCode = "com.aspire.dotcard.basegame.updateGameType";
		DB.getInstance().executeBySQLCode(sqlCode, paras);

	}
	
	public int insertGameStop(GameStop vo)throws DAOException
	{
		
		if (logger.isDebugEnabled())
		{
			logger.debug("insertGameStop(),id=" + vo.getServiceCode());
		}
		Object paras[] = {vo.getServiceCode(),vo.getPkgid(),vo.getContentCode(),vo.getIsStop(),vo.getProvinceId(),vo.getProvinceName(),
				vo.getStopTime(),vo.getOperateType(),getMMProvinceId(vo.getProvinceId())};
		String sqlCode = "com.aspire.dotcard.basegame.insertGameStop";
		DB.getInstance().executeBySQLCode(sqlCode, paras);
		return DataSyncConstants.SUCCESS_ADD;
	}
	
	public int updateGameStop(GameStop vo)throws DAOException
	{
		
		if (logger.isDebugEnabled())
		{
			logger.debug("updateGameStop(),id=" + vo.getServiceCode());
		}
		Object paras[] = {vo.getPkgid(),vo.getContentCode(),vo.getIsStop(),vo.getProvinceId(),vo.getProvinceName(),
				vo.getStopTime(),vo.getOperateType(),vo.getServiceCode(),getMMProvinceId(vo.getProvinceId())};
		// update t_gamestop t set t.pkgid=?,t.contentcode=?,t.isstop=?,t.provinceid=?,t.provincename=?,t.stoptime=?,t.operatetype=? where t.servicecode=? and t.mmprovinceid=? 
		String sqlCode = "com.aspire.dotcard.basegame.updateGameStop";
		DB.getInstance().executeBySQLCode(sqlCode, paras);
		return DataSyncConstants.SUCCESS_ADD;
	}
	
	public String getMMProvinceId(String provinceId){
		Object value = getProvinceMap().get(provinceId);
		if(value==null){
			return "";
		}else{
			return value.toString();
		}
	}
	
    /**
     * 获得省份对应关系：KEY:游戏基地的省份ID value:MM的省份ID
     * @return
     */
    private Map getProvinceMap() {
		if (provinceMap == null) {
			provinceMap = new HashMap();
			ModuleConfig module = ConfigFactory.getSystemConfig()
					.getModuleConfig("ssms");
			ArrayValue[] typeArray = module.getArrayItem(
					"BaseGameProvinceCtrol").getArrayValues();

			if (null != typeArray) {
				for (int i = 0; i < typeArray.length; i++) {
					String tmp = typeArray[i].getValue();
					String gamePro = tmp.split("[|]")[0];
					String moPro = tmp.split("[|]")[1];
					provinceMap.put(gamePro, moPro);
				}
			}
		}

		return provinceMap;
	}
	
// public int updateGameStop(GameStop vo)throws DAOException
//	{
//		
//		if (logger.isDebugEnabled())
//		{
//			logger.debug("updateGameStop(),id=" + vo.getServiceCode());
//		}
//		Object paras[] = {vo.getPkgid(),vo.getContentCode(),vo.getIsStop(),vo.getProvinceId(),vo.getProvinceName(),
//				vo.getStopTime(),vo.getOperateType(),vo.getServiceCode()};
//		String sqlCode = "com.aspire.dotcard.basegame.updateGameStop";
//		DB.getInstance().executeBySQLCode(sqlCode, paras);
//		return DataSyncConstants.SUCCESS_ADD;
//	}
	
//	/**
//	 * 获取当前系统所有基地游戏的列表。
//	 * @return
//	 */
//	public Set getAllGameTypeKeyId()throws DAOException
//	{
//		String sqlCode="com.aspire.dotcard.basegame.getAllGameTypeKeyId";
//		ResultSet rs= DB.getInstance().queryBySQLCode(sqlCode, null);
//		Set set=new HashSet();
//		try
//		{
//			while(rs.next())
//			{
//				set.add(rs.getString("id"));
//			}
//		} catch (SQLException e)
//		{
//			throw new DAOException("读取游戏分类错误");
//		}finally
//		{
//			DB.close(rs);
//		}
//		return set;
//	}
//	
	
	/**
	 * 新增游戏内容
	 * 
	 * @param vo
	 *            游戏业务的vo类
	 * @return 返回新增结果
	 */
	public void insertGameContent(GameContent vo,GAppGame game)throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("insertGameContent(),id=" + vo.getContentCode()
					+ ",name=" + vo.getName());
		}
		Object paras[] = { vo.getContentCode(),vo.getName(),vo.getShortName(),vo.getDesc(),vo.getCpId(),vo.getCpName(),vo.getOpera(),
				vo.getEffectiveDate(),vo.getInvalidDate(),vo.getLogo(),vo.getDeviceua(),vo.getGameTypeId(),vo.getFileSize(),
				vo.getContentType(),vo.getPkgType(),vo.getGamePool(),vo.getFreeDownloadNum(),vo.getChargeType(),vo.getScale(),
				
				
				game.getLOGO1(),game.getLOGO2(),game.getLOGO3(),game.getLOGO4(),game.getLOGO6(),game.getWWWPropaPicture1(),game.getWWWPropaPicture2(),game.getWWWPropaPicture3(),
				game.getClientPreviewPicture1(),game.getClientPreviewPicture2(),game.getClientPreviewPicture3(),game.getClientPreviewPicture4(),
				game.getPicture1(),game.getPicture2(),game.getPicture3(),game.getPicture4(),game.getPicture5(),
				game.getBrand(),vo.getPrice()};
		String sqlCode = "com.aspire.dotcard.basegame.insertGameContent";
		try{
			
			String sqlClobQuery="select t.fulldeviceid,fulldevicename from t_game_content t where t.contentcode='"+vo.getContentCode()+"' and status = 1 for update";
			String sqlClobUpdate="update t_game_content set fulldeviceid=?,fulldevicename=? where contentcode='"+vo.getContentCode()+"'  and status = 1";
			DB.getInstance().executeBySQLCodeWithClob(sqlCode, paras, sqlClobQuery, sqlClobUpdate, new String[]{game.getFulldeviceID(),game.getFulldeviceName()});
		}catch(Exception e){
			logger.error("数据库操作失败" + e);
			try {
				logger.error("出错的执行语句："+getDebugSql(SQLCode.getInstance().getSQLStatement(sqlCode),paras));
			} catch (DataAccessException e1) {
				// TODO Auto-generated catch block
				logger.error(e1);
			}
			throw new DAOException("新增游戏内容",e);
		}
	}
	
	private  String getDebugSql(String sql,Object[] para){
		if(para!=null){
			for(int i=0;i<para.length;i++){
				sql = sql.replaceFirst("\\?", "'"+para[i]+"'");
			}
		}
		return sql;
	}
	
	
	/**
	 * 更新游戏内容
	 * 
	 * @return
	 */
	public void clearGameContent()
	{
		try {
			String sqlCode = "com.aspire.dotcard.basegame.clearGameContent";
			DB.getInstance().executeBySQLCode(sqlCode, null);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			logger.error("清理游戏内容出错！",e);
		}

	}
	
	public void clearGameStatistcs() throws DAOException
	{
		try {
			String sqlCode = "com.aspire.dotcard.basegame.clearGameStatistcs";
			DB.getInstance().executeBySQLCode(sqlCode, null);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			logger.error("清理游戏统计出错！",e);
			throw new DAOException("清理游戏统计出错！",e);
		}

	}
	
	
	/**
	 * 获取当前系统所有基地游戏的列表。
	 * @return
	 */
	public Set getAllKeyId(String sqlCode)throws DAOException
	{
		ResultSet rs= DB.getInstance().queryBySQLCode(sqlCode, null);
		Set set=new HashSet();
		try
		{
			while(rs.next())
			{
				set.add(rs.getString(1));
			}
		} catch (SQLException e)
		{
			throw new DAOException("读取游戏分类错误");
		}finally
		{
			DB.close(rs);
		}
		return set;
	}
	
	public Map getAllMap(String sqlCode) throws DAOException {
		// TODO Auto-generated method stub
		ResultSet rs= DB.getInstance().queryBySQLCode(sqlCode, null);
		Map map=new HashMap();
		try
		{
			while(rs.next())
			{
				map.put(rs.getString(1), rs.getString(2));
			}
		} catch (SQLException e)
		{
			throw new DAOException(e);
		}finally
		{
			DB.close(rs);
		}
		return map;
	}
	
	
//	public String getMMCateName() throws DAOException {
//		
//	}
	
	
	/**
	 * 更新游戏业务信息
	 * 
	 * @return
	 */
	public void delOldData(String sql) throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("delOldData(),sql=" + sql);
		}
		DB.getInstance().execute(sql, null);
	}
	

	/**
	 * 根据contentCode得到单个GameContent对象
	 * @param contentCode
	 * @return
	 * @throws DAOException
	 */
	public GameContent getGameContentById(String contentCode)throws DAOException
	{
		String sqlCode="com.aspire.dotcard.basegame.GameSyncDAO.getGameContentById";
		String[] param = {contentCode};
		ResultSet rs= DB.getInstance().queryBySQLCode(sqlCode, param);
		GameContent content=null;
		try
		{
			if(rs.next())
			{
				content=new GameContent();
				content.setContentCode(rs.getString("contentCode"));
				content.setName(rs.getString("name"));
				content.setShortName(rs.getString("shortName"));
				content.setDesc(rs.getString("descr"));
				content.setCpId(rs.getString("cpid"));
				content.setCpName(rs.getString("cpName"));
				content.setOpera(rs.getString("opera"));
				content.setEffectiveDate(rs.getString("effectiveDate"));
				content.setInvalidDate(rs.getString("invalidDate"));
				content.setLogo(rs.getString("logo"));
				content.setDeviceua(rs.getString("deviceua"));
				content.setGameTypeId(rs.getString("gameTypeId"));
				content.setFileSize(rs.getString("fileSize"));
				content.setContentType(rs.getString("contentType"));
				content.setPkgType(rs.getString("pkgType"));
				if(content.getPkgType()==null){
					content.setPkgType("");
				}
				content.setGamePool(rs.getString("gamePool"));
				content.setFreeDownloadNum(rs.getString("freeDownloadNum"));
				
				if(content.getFreeDownloadNum()==null){
					content.setFreeDownloadNum("");
				}
				content.setChargeType(rs.getString("chargeType"));
				content.setScale(rs.getString("scale"));
				
				content.setLogo1(rs.getString("logo1"));
				content.setLogo2(rs.getString("logo2"));
				content.setLogo3(rs.getString("logo3"));
				content.setLogo4(rs.getString("logo4"));
				content.setLogo6(rs.getString("logo6"));
				
				content.setWWWPropaPicture1(rs.getString("WWWPropaPicture1"));
				content.setWWWPropaPicture2(rs.getString("WWWPropaPicture2"));
				content.setWWWPropaPicture3(rs.getString("WWWPropaPicture3"));
				
				content.setClientPreviewPicture1(rs.getString("clientPreviewPicture1"));
				content.setClientPreviewPicture2(rs.getString("clientPreviewPicture2"));
				content.setClientPreviewPicture3(rs.getString("clientPreviewPicture3"));
				content.setClientPreviewPicture4(rs.getString("clientPreviewPicture4"));
				
				content.setPicture1(rs.getString("picture1"));
				content.setPicture2(rs.getString("picture2"));
				content.setPicture3(rs.getString("picture3"));
				content.setPicture4(rs.getString("picture4"));
				content.setPicture5(rs.getString("picture5"));
				
				try {
					content.setFulldeviceid(clob2String(rs
							.getClob("fulldeviceid")));
				} catch (Exception e) {
					logger.error("clob转换出错，写成空。");
					content.setFulldeviceid("");
				}
				content.setBrand(rs.getString("brand"));

				try {
					content.setFulldevicename(clob2String(rs
							.getClob("fulldevicename")));
				} catch (Exception e) {
					logger.error("clob转换出错，写成空。");
					content.setFulldevicename("");
				}
				
			}
		} catch (SQLException e)
		{			throw new DAOException("读取游戏UA映射关系错误",e);
		}finally
		{
			DB.close(rs);
		}
		return content;
	}
	
	 /**
     * 将CLOB转成String ,静态方法
     * @param clob 字段
     * @return 内容字串，如果出现错误，返回null
     */
	
    public final static String clob2String(Clob clob)
    {
		if (clob == null)
		{
			return "";
		}

		StringBuffer sb = new StringBuffer(65535);//64K
		Reader clobStream = null;//创建一个输入流对象
		try
		{
			clobStream = clob.getCharacterStream();
			char[] b = new char[60000];//每次获取60K
			int i = 0;
			while ((i = clobStream.read(b)) != -1)
			{
				sb.append(b, 0, i);
			}
		} catch (Exception ex)
		{
			sb = null;
		} finally
		{
			try
			{
				if (clobStream != null) clobStream.close();
			} catch (Exception e)
			{
			}
		}
		if (sb == null)
			return "";
		else
			return sb.toString();
	}

	public void insertTWGame(GameService service, GameContent content,GameTypeEntity entity) throws DAOException, BOException {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled())
		{
			logger.debug("insertTWGame(),id=" + service.getServiceCode());
		}
//		cpid, Cpname, CPSERVICEID, SERVICENAME, SERVICESHORTNAME, 
//		SERVICEDESC, OPERATIONDESC, SERVICETYPE, SERVICEPAYTYPE, SERVICESTARTDATE, 
//		SERVICEENDDATE, FEE,SERVICE_URL, SERVICEFEETYPE, GAMETYPE, GAMETYPE_DESC, 
//		SERVICEFLAG, MMGAMETYPE,FEETYPE,FIRSTTYPE,OLDPRICE
		

		
		Object paras[] = { content.getCpId(),content.getCpName(),service.getServiceCode(),
				content.getName(),content.getShortName(),content.getDesc(),content.getOpera(),
				service.getServiceType(),service.getPayType(),
				DateUtil.formatDate(DateUtil.stringToDate(content.getEffectiveDate(), "yyyyMMddHHmmss"),"yyyy-MM-dd"),
				DateUtil.formatDate(DateUtil.stringToDate(content.getInvalidDate(), "yyyyMMddHHmmss"),"yyyy-MM-dd"),
				service.getPrice(),service.getTwUrl(),service.getBillType(),content.getGameTypeId(),
				//GameSyncTools.getInstance().getBaseName(content.getGameTypeId()) 
				entity.getName(),
				service.getDiscountType(),
				//GameSyncTools.getInstance().getMMCateName2(content.getGameTypeId()),
				entity.getMmname(),
				service.getFeeType(),service.getFirstType(),service.getOldPrice(),service.getPkgId(),service.getConnectionType(),
				service.getScale(),
//				用于表示游戏包内业务是否计数。
//				0：计数
//				1：不计数
				service.getCountFlag()//是否计数  add by aiyan 2012-11-10
				
				};
	
		
		String sqlCode = "com.aspire.dotcard.basegame.insertTWGame";
		DB.getInstance().executeBySQLCode(sqlCode, paras);
	}

	public void updateTWGame(GameService service, GameContent content,GameTypeEntity entity) throws DAOException {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled())
		{
			logger.debug("updateTWGame(),id=" + service.getServiceCode());
		}
//		cpid, Cpname, CPSERVICEID, SERVICENAME, SERVICESHORTNAME, SERVICEDESC, OPERATIONDESC, 
//		SERVICETYPE, SERVICEPAYTYPE, SERVICESTARTDATE, SERVICEENDDATE,
//		FEE, SERVICEFEEDESC, SERVICE_URL, SERVICEFEETYPE, GAMETYPE, GAMETYPE_DESC, SERVICEFLAG, MMGAMETYPE
		Object paras[] = { content.getCpId(),content.getCpName(),
				content.getName(),content.getShortName(),content.getDesc(),content.getOpera(),
				service.getServiceType(),service.getPayType(),
				DateUtil.formatDate(DateUtil.stringToDate(content.getEffectiveDate(), "yyyyMMddHHmmss"),"yyyy-MM-dd"),
				DateUtil.formatDate(DateUtil.stringToDate(content.getInvalidDate(), "yyyyMMddHHmmss"),"yyyy-MM-dd"),
				service.getPrice(),service.getTwUrl(),service.getBillType(),content.getGameTypeId(),
				//GameSyncTools.getInstance().getBaseName(content.getGameTypeId()),
				entity.getName(),
				service.getDiscountType(),
				//GameSyncTools.getInstance().getMMCateName2(content.getGameTypeId()),
				entity.getMmname(),
				service.getFeeType(),service.getFirstType(),service.getOldPrice(),service.getPkgId(),
				service.getConnectionType(),//接入方式 add by aiyan 2012-11-2
				service.getScale(),//比例分成 add by aiyan 2012-11-3
				
//				用于表示游戏包内业务是否计数。
//				0：计数
//				1：不计数
				service.getCountFlag(),//是否计数 add by aiyan 2012-11-10
				service.getServiceCode()
				};
		String sqlCode = "com.aspire.dotcard.basegame.updateTWGame";
		DB.getInstance().executeBySQLCode(sqlCode, paras);		
	}

	
	public void insertPkgGameRef(String pkgId, String serviceCode) throws DAOException{
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled())
		{
			logger.debug("insertPkgGameRef(),pkgId="+pkgId+" serviceCode=" + serviceCode);
		}
		Object paras[] = {pkgId,serviceCode};
		String sqlCode = "com.aspire.dotcard.basegame.insertPkgGameRef";
		DB.getInstance().executeBySQLCode(sqlCode, paras);
		
	}

	public void updatePkgGame(GameContent content) throws DAOException {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled())
		{
			logger.debug("updatePkgGame(),pkgId="+content.getContentCode());
		}
		//pkgname,pkgdesc,cpname,fee,picture1,picture2,picture3,picture4,PROVINCECTROL,pkgtype,gamepool,
		//freedownloadnum,cpid,pkgid
		Object paras[] = {content.getName(),content.getDesc(),content.getCpName(),content.getPrice(),
				content.getLogo1(),content.getLogo2(),content.getLogo3(),content.getLogo4(),content.getScale(),content.getPkgType(),content.getGamePool(),
				content.getFreeDownloadNum(),content.getCpId(),content.getContentCode()};
		String sqlCode = "com.aspire.dotcard.basegame.updatePkgGame";
		DB.getInstance().executeBySQLCode(sqlCode, paras);
	}

	public void insertPkgGame(GameContent content) throws DAOException {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled())
		{
			logger.debug("insertPkgGame(),pkgId="+content.getContentCode());
		}
		
		String sqlCode = "";
		Object paras[] = null;
		try{
			//pkgid,pkgname,pkgdesc,cpname,fee,
			//picture1,picture2,picture3,picture4,PROVINCECTROL,pkgtype,gamepool,
			//freedownloadnum,cpid
			paras = new Object[]{content.getContentCode(),content.getName(),content.getDesc(),content.getCpName(),content.getPrice(),
					content.getLogo1(),content.getLogo2(),content.getLogo3(),content.getLogo4(),content.getScale(),content.getPkgType(),
					content.getGamePool(),content.getFreeDownloadNum(),content.getCpId()
					};
			sqlCode = "com.aspire.dotcard.basegame.insertPkgGame";
			DB.getInstance().executeBySQLCode(sqlCode, paras);
		}catch(Exception e){
			try {
				logger.error("出错的执行语句："+getDebugSql(SQLCode.getInstance().getSQLStatement(sqlCode),paras));
			} catch (DataAccessException e1) {
				// TODO Auto-generated catch block
				logger.error(e1);
			}
			throw new DAOException("新增游戏包出错",e);
		}
	}
	
	/**
	 * 删除游戏业务信息
	 * 
	 * @return
	 */
	public void delGameService(String icpServId) throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("delGameService");
		}
		String paras[] = { icpServId };
		String sqlCode = "datasync.implement.game.GameSyncDAO.delGameService";
		DB.getInstance().executeBySQLCode(sqlCode, paras);

	}
	
	/**
	 * 新增游戏统计
	 * 
	 * @param vo
	 *            游戏统计的vo类
	 * @return 返回新增结果
	 */
	public int insertGameStatistcs(GameStatistcs vo)throws DAOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("insertGameStatistcs(),id=" + vo.getSeriveCode());
		}
		Object paras[] = {vo.getSeriveCode(),vo.getDownloadNum(),vo.getLoginNum(),vo.getBroadcastNum(),vo.getPayNum(),
				vo.getGameLevel(),vo.getCommentNum(),vo.getScore(),vo.getStartTime(),vo.getStartNum()};
		String sqlCode = "com.aspire.dotcard.basegame.insertGameStatistcs";
		DB.getInstance().executeBySQLCode(sqlCode, paras);
		return DataSyncConstants.SUCCESS_ADD;
	}
	
	
	/**
     * 刷新v_service 物化视图。
     * 
     */
    public void refreshVService() throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("refreshVService()");
        }
        // 将需求中罗列的字段查询出来；
        // 写一个sql语句将一期的业务表和cms中查出来的信息合成一个物化视图即可。
        
        try{
        DataSyncDAO.getInstance().syncVService();
        }catch(Exception e){
        	
        	logger.error("重建v_service表失败"+e);
        	throw new DAOException("重建v_service表失败",e);
        }
    }

    //得到需要处理的PKGID
	public List queryGamePkgid() {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		String sqlCode="com.aspire.dotcard.basegame.GameSyncDAO.queryGamePkgid";
		ResultSet rs = null;
		try
		{
			rs = DB.getInstance().queryBySQLCode(sqlCode, null);
			while(rs.next())
			{
				list.add(rs.getString(1));
				
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			logger.error(e1);
		}finally
		{
			DB.close(rs);
		}
		return list;
	}

	public void updateGamePkgUA(String pkgid) {
		// TODO Auto-generated method stub
		try {
			String[] ua=getUA(pkgid);
			if(ua!=null&&ua.length==2){
				String sqlClobQuery="select fulldeviceid,fulldevicename from t_game_pkg t where t.pkgid='"+pkgid+"' for update";
				String sqlClobUpdate="update t_game_pkg set fulldeviceid=?,fulldevicename=? where pkgid='"+pkgid+"'";
				DB.getInstance().executeBySQLCodeWithClob(sqlClobQuery, sqlClobUpdate, new String[]{ua[0],ua[1]});
			}
			
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			logger.error("更新游戏包的适配关系出错，具体游戏包ID（pkgid）:"+pkgid);
		}

		
	}
	private String[] getUA(String pkgid) throws DAOException {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled())
		{
			logger.debug("getUA(),pkgId="+pkgid);
		}
		
		String sqlCode = "";
		Object paras[] = null;
		String[] ua= {"",""};
		Set fullDeviceIdSet = new HashSet();
		Set fullDeviceNameSet = new HashSet();
		try{
			paras = new Object[]{pkgid};
			sqlCode = "com.aspire.dotcard.basegame.getUA";
			ResultSet rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
			while(rs.next()){
				// 因为weblogic版本问题这里要作处理
				//fullDeviceIdSet.addAll(string2Set(clob2String(rs.getClob("fulldeviceid")),","));
				//fullDeviceNameSet.addAll(string2Set(clob2String(rs.getClob("fulldevicename")),","));
				fullDeviceIdSet.addAll(string2Set(DB.getClobValue(rs, "fulldeviceid"),","));
				fullDeviceNameSet.addAll(string2Set(DB.getClobValue(rs, "fulldevicename"),","));
			}
		}catch(Exception e){
			logger.error(e);
			throw new DAOException(e);
		}
		
		String fullDeviceId = list2String(fullDeviceIdSet);
		String fullDeviceName = list2String(fullDeviceNameSet);
		ua[0]=fullDeviceId;ua[1]=fullDeviceName;
		return ua;
	}
	
	private Set string2Set(String str,String regex){
		if(str==null||str.trim().equals("")){
			return new HashSet();
		}
		Set set = new HashSet();
		String[] arr = str.split(regex);
		if(arr!=null&&arr.length>0){
			for(int i=0;i<arr.length;i++){
				set.add(arr[i]);
			}
		}
		return set;
		
	}

	/**
	 * 将list中的数据拼装为 {devicename},{devicename}… 的样式
	 * @param list 保存deviceName的集合
	 * @return 形如：{devicename1},{devicename2}… 的字符串
	 */
	private String list2String(Collection collection)
	{
		StringBuffer sb = new StringBuffer();
		Iterator iterator = collection.iterator();
		boolean dotFlag = false;//第一次不需要插入逗号
		while (iterator.hasNext())
		{
			if (dotFlag)
			{
				sb.append(",");
			}
			else
			{
				dotFlag = true;
			}
			sb.append(iterator.next());
		}
		return sb.toString();
	}

	public List queryGameContent() {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		//select c.contentcode,c.name,c.shortname,c.descr,c.cpid,c.cpname,c.effectivedate,
		//c.invaliddate,c.pkgtype,c.gamepool,c.freedownloadnum,c.logo1,c.logo2,c.logo3,c.logo4,c.price,c.scale 
		//from t_game_content c where c.contenttype='3'
		String sqlCode="com.aspire.dotcard.basegame.GameSyncDAO.queryGameContent";
		ResultSet rs = null;
		try
		{
			rs = DB.getInstance().queryBySQLCode(sqlCode, null);
			while(rs.next())
			{
				GameContent c = new GameContent();
				c.setContentCode(rs.getString("contentCode"));
				c.setName(rs.getString("name"));
				c.setShortName(rs.getString("shortname"));
				c.setDesc(rs.getString("descr"));
				c.setCpId(rs.getString("cpid"));
				c.setCpName(rs.getString("cpname"));
				c.setEffectiveDate(rs.getString("effectivedate"));
				c.setInvalidDate(rs.getString("invaliddate"));
				c.setPkgType(rs.getString("pkgtype"));
				c.setGamePool(rs.getString("gamepool"));
				c.setFreeDownloadNum(rs.getString("freedownloadnum"));
				c.setLogo1(rs.getString("logo1"));
				c.setLogo2(rs.getString("logo2"));
				c.setLogo3(rs.getString("logo3"));
				c.setLogo4(rs.getString("logo4"));
				c.setPrice(rs.getString("price"));
				c.setScale(rs.getString("scale"));
				
				list.add(c);
				
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			logger.error(e1);
		}finally
		{
			DB.close(rs);
		}
		return list;
	}

	public List queryGameStatistcs() {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		String sqlCode="com.aspire.dotcard.basegame.GameSyncDAO.queryServiceCode";
		ResultSet rs = null;
		try
		{
			rs = DB.getInstance().queryBySQLCode(sqlCode, null);
			while(rs.next())
			{
				GameStatistcs vo = new GameStatistcs();
				vo.setSeriveCode(rs.getString("servicecode"));
				vo.setDownloadNum(rs.getString("downloadnum"));
				vo.setStartTime(rs.getString("starttime"));
				list.add(vo);
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			logger.error(e1);
		}finally
		{
			DB.close(rs);
		}
		return list;
	}

	public void updateGameStatistcs(GameStatistcs vo) {
		// TODO Auto-generated method stub
		try {
			String sqlCode="com.aspire.dotcard.basegame.GameSyncDAO.updateGameStatistcs";
			DB.getInstance().executeBySQLCode(sqlCode, new String[]{vo.getDownloadNum(),vo.getStartTime(),vo.getSeriveCode()});
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			logger.error("用游戏的统计量（t_game_statistcs）对t_r_gcontent修改时出错！",e);
		}
		
	}

	public void insertContentSeries(String contentCode, String seriesid){
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled())
		{
			logger.debug("insertContentSeries(),contentCode=" + contentCode+"seriesid="+seriesid);
		}
		try {
			Object paras[] = {contentCode,seriesid};
			String sqlCode = "com.aspire.dotcard.basegame.insertContentSeries";
			DB.getInstance().executeBySQLCode(sqlCode, paras);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			logger.error("插入（t_a_content_seriesid）时出错！"+contentCode+"|"+seriesid,e);
		}
		
	}

	public void clearContentSeries() {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled())
		{
			logger.debug("clearContentSeries");
		}
		try{
			String sqlCode = "com.aspire.dotcard.basegame.clearContentSeries";
			DB.getInstance().executeBySQLCode(sqlCode, null);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			logger.error("清理（t_a_content_seriesid）时出错！",e);
		}
		
	}

	public Set getAndroidContentSet() {
		// TODO Auto-generated method stub
		Set set = new HashSet();
		String sqlCode="com.aspire.dotcard.basegame.getAndroidContentSet";
		ResultSet rs = null;
		try
		{
			rs = DB.getInstance().queryBySQLCode(sqlCode, null);
			while(rs.next()){
				set.add(rs.getString("contentid"));
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			logger.error(e1);
		}finally
		{
			DB.close(rs);
		}
		return set;
	}

	public void setDeviceResource(String serviceid,String contentid) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled())
		{
			logger.debug("setDeviceResource" + serviceid+"|"+contentid);
		}
		TransactionDB tdb = null;
		try {
			tdb = TransactionDB.getTransactionInstance();
			String sqlCode_del = "com.aspire.dotcard.basegame.deleteDeviceResource";
			String sqlCode_insert = "com.aspire.dotcard.basegame.insertDeviceResource";
			tdb.executeBySQLCode(sqlCode_del, new String[]{serviceid});
			tdb.executeBySQLCode(sqlCode_insert, new String[]{serviceid,contentid});
			tdb.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("插入（setDeviceResource）时出错！"+serviceid+"|"+contentid,e);
		}finally{
			if(tdb!=null){
				tdb.close();
			}
			
		}
		
	}

	public void updateFullDevice(String contentId,
			String fullDeviceid, String fullDeviceName) {
		// TODO Auto-generated method stub
		
		if(fullDeviceid==null||fullDeviceid.length()==0){
			return ;
		}
		try {
			//update t_r_gcontent set fulldeviceid=empty_clob() where contentid='"+contentId+"'"
			
			String sqlCode = "PPMSDAO.updateFullDevice";
			String sqlClobQuery="select fulldeviceid,fulldevicename from t_r_gcontent  where contentid='"+contentId+"' for update";
			String sqlClobUpdate="update t_r_gcontent set fulldeviceid=?,fulldevicename=? where contentid='"+contentId+"'";
			DB.getInstance().executeBySQLCodeWithClob(sqlCode, new String[]{contentId},sqlClobQuery, sqlClobUpdate, new String[]{fullDeviceid,fullDeviceName});
			logger.debug("处理 "+contentId+" 成功！！updateFullDevice:hehe...");
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			logger.error("在处理FULLDEVICEID的时候出现问题。"+contentId,e);
		}
		
	}

	public Map<String, List<String>> getFullDeviceAppend(String contentid) {
		// TODO Auto-generated method stub
		Map<String,List<String>> fullDeviceMap = new HashMap<String,List<String>>();
		List<String> fullDeviceIdList = new ArrayList<String>();
		List<String> fullDeviceNameList = new ArrayList<String>();
        try{
        	String sqlCode="com.aspire.dotcard.basegame.getFullDeviceAppend";
            RowSet rs = DB.getInstance().queryBySQLCode(sqlCode, new String[]{contentid});
            while(rs.next()){
            	fullDeviceIdList.add(rs.getString("device_id"));
            	fullDeviceNameList.add(rs.getString("device_name"));
            }
            
        }
        catch (Exception e)
        {
        	logger.error(e);
        }
        fullDeviceMap.put("fullDeviceIdList", fullDeviceIdList);
        fullDeviceMap.put("fullDeviceNameList", fullDeviceNameList);
        return fullDeviceMap;
	}

	public Map<String, String> getFullDevice(String serviceid) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		String sqlCode="com.aspire.dotcard.basegame.getFullDevice";
		ResultSet rs = null;
		try
		{
			rs = DB.getInstance().queryBySQLCode(sqlCode, new String[]{serviceid});
			if(rs.next()){
				map.put("fulldeviceid", DB.getClobValue(rs, "fulldeviceid"));
				map.put("fulldevicename", DB.getClobValue(rs, "fulldevicename"));
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			logger.error(e1);
		}finally
		{
			DB.close(rs);
		}
		return map;
	}

//	public void putGamePkgRefNum() throws DAOException {
//		// TODO Auto-generated method stub
//		if (logger.isDebugEnabled())
//		{
//			logger.debug("putGamePkgRefNum");
//		}
//		String sqlCode = "com.aspire.dotcard.basegame.GameSyncDAO.putGamePkgRefNum";
//		DB.getInstance().executeBySQLCode(sqlCode, null);
//		
//	}

}
