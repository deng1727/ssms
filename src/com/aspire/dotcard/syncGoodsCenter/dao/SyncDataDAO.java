package com.aspire.dotcard.syncGoodsCenter.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.util.StringUtils;
import com.aspire.dotcard.syncAndroid.dc.jms.MSGType;
import com.aspire.dotcard.syncAndroid.ppms.PPMSDAO;
import com.aspire.dotcard.syncAndroid.ppms.ReceiveChangeVO;
import com.aspire.dotcard.syncGoodsCenter.vo.GcAppHotInfo;
import com.aspire.dotcard.syncGoodsCenter.vo.RefModifyReqVo;
import com.aspire.ponaadmin.web.db.ResultSetConvertor;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.repository.persistency.db.NodePersistencyDB;
import com.aspire.ponaadmin.web.util.PublicUtil;

public class SyncDataDAO {
	protected static JLogger LOG = LoggerFactory.getLogger(SyncDataDAO.class);
	/**
	 * 支持事务的数据库操作器，如果为空表示是非事务类型的操作
	 */
	private TransactionDB transactionDB;
	private Map<MSGType, List<String>> feedback;
	public static SyncDataDAO getTransactionInstance(TransactionDB transactionDB) {
		SyncDataDAO dao = new SyncDataDAO();
		dao.transactionDB = transactionDB;
		dao.feedback = new HashMap<MSGType, List<String>>();
		return dao;
	}
	/**
	 * 添加消息
	 * @param type
	 * @param transactionID
	 * @throws DAOException
	 */
	public void addMessages(MSGType type, String transactionID)
			throws DAOException {
		List<String> msgList = feedback.get(type);
		if (msgList != null) {
			for (int i = 0; i < msgList.size(); i++) {
				addMessages(type, transactionID, msgList.get(i));
			}
		}

	}

	/**
	 * 添加消息
	 * @param transactionID
	 * @throws DAOException 
	 */
	public void addMessages(MSGType type, String transactionID, String message)
			throws DAOException {
		String sqlCode = "PPMSDAO.addMessages";
		Object[] paras = { type.toString(), transactionID, message };
		transactionDB.executeBySQLCode(sqlCode, paras);
	}
	/**
	 * 从 v_gc_app_huojia_synonym中提取数据，然后填入GcAppHotInfo这个vo对象，然后再把vo封装到list中
	 * @return GcAppHotInfo
	 * @throws DAOException
	 */
	public static  List<GcAppHotInfo> findGcAppHotInfoDataByInsrementAdd(){
		Connection conn = null;
		//		String sql="select * from v_gc_huojia_info_syn where content_id like '33%' ";
		//		String sql="select * from t_gc_app_hot_info where content_id like '33%' ";//开发库中没有v_gc_huojia_info_syn,测试只好建表往里插数据
		PreparedStatement statement = null;
		ResultSet result = null;
		List<GcAppHotInfo> list=null;
		DB db=DB.getInstance();
		try {
			String sql=db.getSQLByCode("com.aspire.dotcard.syncGoodsCenter.dao.SyncDataDAO.findGcAppHotInfoDataByInsrementAdd");//开发库中没有v_gc_huojia_info_syn,测试只好建表往里插数据
			list=new ArrayList<GcAppHotInfo>();
			conn = db.getConnection();
			if (LOG.isDebugEnabled()) {
				LOG.debug("execute sql is:" + sql);
			}
			statement = conn.prepareStatement(sql);
			result=statement.executeQuery();
			while(result.next()){
				GcAppHotInfo g=new GcAppHotInfo();
				g.setCONTENT_ID(result.getString("CONTENT_ID"));
				g.setCONTENT_NAME(result.getString("CONTENT_NAME"));
				g.setPRODUCT_TYPE_ID(result.getString("PRODUCT_TYPE_ID"));
				g.setPRODUCT_SUBTYPE_ID(result.getString("PRODUCT_SUBTYPE_ID"));
				g.setONLINE_DATE(result.getDate("ONLINE_DATE"));
				g.setOFFLINE_DATE(result.getDate("OFFLINE_DATE"));
				g.setCHARGE_TYPE_ID(result.getString("CHARGE_TYPE_ID"));
				g.setICP_TYPE(result.getString("ICP_TYPE"));
				g.setCOMPANY_ID(result.getString("COMPANY_ID"));
				g.setSTATUS(result.getInt("STATUS"));
				g.setSUBSTATUS(result.getInt("SUBSTATUS"));
				g.setISLOCK(result.getInt("ISLOCK"));
				g.setFEECOMMERCIALDATE(result.getDate("FEECOMMERCIALDATE"));
				g.setCONTENTSTATUS(result.getString("CONTENTSTATUS"));
				g.setCONTATTR(result.getString("CONTATTR"));
				g.setFIRSTCOMMERCIALDATE(result.getDate("FIRSTCOMMERCIALDATE"));
				g.setGRADE(result.getString("GRADE"));
				g.setTHIRDAPPTYPE(result.getString("THIRDAPPTYPE"));
				g.setHATCHAPPID(result.getString("HATCHAPPID"));
				g.setFEE(result.getString("FEE"));
				g.setCONTENTCODE(result.getString("CONTENTCODE"));
				g.setCONT_STAT(result.getString("CONT_STAT"));
				g.setFLOW_TIME(result.getDate("FLOW_TIME"));
				g.setPROGRAM_SIZE(result.getString("PROGRAM_SIZE"));
				g.setCON_LEVEL(result.getString("CON_LEVEL"));
				g.setISCONVERGE(result.getString("ISCONVERGE"));
				g.setLUPDDATE(result.getDate("LUPDDATE"));
				g.setVERSION(result.getString("VERSION"));
				g.setVERSIONCODE(result.getString("VERSIONCODE"));
				g.setAP_NAME(result.getString("AP_NAME"));
				g.setAPPSIZE(result.getString("APPSIZE"));
				g.setPACKAGE_URL(result.getString("PACKAGE_URL"));
				g.setDOWNLOAD_NUM(result.getString("DOWNLOAD_NUM"));
				g.setGRADE1_TYPE(result.getString("GRADE1_TYPE"));
				g.setGRADE2_TYPE(result.getString("GRADE2_TYPE"));
				g.setUSAGE_DESC(result.getString("USAGE_DESC"));
				g.setUPDATEDATE(result.getString("UPDATEDATE"));
				g.setLOGO_URL(result.getString("LOGO_URL"));
				g.setSCREEN_URL1(result.getString("SCREEN_URL1"));
				g.setSCREEN_URL2(result.getString("SCREEN_URL2"));
				g.setSCREEN_URL3(result.getString("SCREEN_URL3"));
				g.setSCREEN_URL4(result.getString("SCREEN_URL4"));
				g.setCONTENT_DESC(result.getString("CONTENT_DESC"));
				g.setSOURCE(result.getString("SOURCE"));
				g.setPACKAGENAME(result.getString("PACKAGENAME"));
				g.setCOPYRIGHTFLAG(result.getString("COPYRIGHTFLAG"));
				list.add(g);
			}
		}catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			// 释放资源
			if (result != null) {
				try {
					result.close();
				} catch (Exception e) {
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (Exception e) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
				}
			}
		}
		return list;
	}
	
	
	/**
	 * 从 v_gc_app_huojia_synonym中提取数据，然后填入GcAppHotInfo这个vo对象，然后再把vo封装到list中
	 * @return GcAppHotInfo
	 * @throws DAOException
	 */
	public static  List<GcAppHotInfo> findGcAppHotInfoDataByInsrementUpdate(){
		Connection conn = null;
		//		String sql="select * from v_gc_huojia_info_syn where content_id like '33%' ";
		//		String sql="select * from t_gc_app_hot_info where content_id like '33%' ";//开发库中没有v_gc_huojia_info_syn,测试只好建表往里插数据
		PreparedStatement statement = null;
		ResultSet result = null;
		List<GcAppHotInfo> list=null;
		DB db=DB.getInstance();
		try {
			String sql=db.getSQLByCode("com.aspire.dotcard.syncGoodsCenter.dao.SyncDataDAO.findGcAppHotInfoDataByInsrementUpdate");//开发库中没有v_gc_huojia_info_syn,测试只好建表往里插数据
			list=new ArrayList<GcAppHotInfo>();
			conn = db.getConnection();
			if (LOG.isDebugEnabled()) {
				LOG.debug("execute sql is:" + sql);
			}
			statement = conn.prepareStatement(sql);
			result=statement.executeQuery();
			while(result.next()){
				GcAppHotInfo g=new GcAppHotInfo();
				g.setCONTENT_ID(result.getString("CONTENT_ID"));
				g.setCONTENT_NAME(result.getString("CONTENT_NAME"));
				g.setPRODUCT_TYPE_ID(result.getString("PRODUCT_TYPE_ID"));
				g.setPRODUCT_SUBTYPE_ID(result.getString("PRODUCT_SUBTYPE_ID"));
				g.setONLINE_DATE(result.getDate("ONLINE_DATE"));
				g.setOFFLINE_DATE(result.getDate("OFFLINE_DATE"));
				g.setCHARGE_TYPE_ID(result.getString("CHARGE_TYPE_ID"));
				g.setICP_TYPE(result.getString("ICP_TYPE"));
				g.setCOMPANY_ID(result.getString("COMPANY_ID"));
				g.setSTATUS(result.getInt("STATUS"));
				g.setSUBSTATUS(result.getInt("SUBSTATUS"));
				g.setISLOCK(result.getInt("ISLOCK"));
				g.setFEECOMMERCIALDATE(result.getDate("FEECOMMERCIALDATE"));
				g.setCONTENTSTATUS(result.getString("CONTENTSTATUS"));
				g.setCONTATTR(result.getString("CONTATTR"));
				g.setFIRSTCOMMERCIALDATE(result.getDate("FIRSTCOMMERCIALDATE"));
				g.setGRADE(result.getString("GRADE"));
				g.setTHIRDAPPTYPE(result.getString("THIRDAPPTYPE"));
				g.setHATCHAPPID(result.getString("HATCHAPPID"));
				g.setFEE(result.getString("FEE"));
				g.setCONTENTCODE(result.getString("CONTENTCODE"));
				g.setCONT_STAT(result.getString("CONT_STAT"));
				g.setFLOW_TIME(result.getDate("FLOW_TIME"));
				g.setPROGRAM_SIZE(result.getString("PROGRAM_SIZE"));
				g.setCON_LEVEL(result.getString("CON_LEVEL"));
				g.setISCONVERGE(result.getString("ISCONVERGE"));
				g.setLUPDDATE(result.getDate("LUPDDATE"));
				g.setVERSION(result.getString("VERSION"));
				g.setVERSIONCODE(result.getString("VERSIONCODE"));
				g.setAP_NAME(result.getString("AP_NAME"));
				g.setAPPSIZE(result.getString("APPSIZE"));
				g.setPACKAGE_URL(result.getString("PACKAGE_URL"));
				g.setDOWNLOAD_NUM(result.getString("DOWNLOAD_NUM"));
				g.setGRADE1_TYPE(result.getString("GRADE1_TYPE"));
				g.setGRADE2_TYPE(result.getString("GRADE2_TYPE"));
				g.setUSAGE_DESC(result.getString("USAGE_DESC"));
				g.setUPDATEDATE(result.getString("UPDATEDATE"));
				g.setLOGO_URL(result.getString("LOGO_URL"));
				g.setSCREEN_URL1(result.getString("SCREEN_URL1"));
				g.setSCREEN_URL2(result.getString("SCREEN_URL2"));
				g.setSCREEN_URL3(result.getString("SCREEN_URL3"));
				g.setSCREEN_URL4(result.getString("SCREEN_URL4"));
				g.setCONTENT_DESC(result.getString("CONTENT_DESC"));
				g.setSOURCE(result.getString("SOURCE"));
				g.setPACKAGENAME(result.getString("PACKAGENAME"));
				g.setCOPYRIGHTFLAG(result.getString("COPYRIGHTFLAG"));
				list.add(g);
			}
		}catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			// 释放资源
			if (result != null) {
				try {
					result.close();
				} catch (Exception e) {
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (Exception e) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
				}
			}
		}
		return list;
	}
	

	/**
	 * 从 v_gc_app_huojia_synonym中提取数据，然后填入GcAppHotInfo这个vo对象，然后再把vo封装到list中
	 * @return GcAppHotInfo
	 * @throws DAOException
	 */
	public static  GcAppHotInfo findGcAppHotInfoData( ReceiveChangeVO receiveChangeVO){
		Connection conn = null;
		//		String sql="select * from v_gc_huojia_info_syn where content_id like '33%' ";
		//		String sql="select * from t_gc_app_hot_info where content_id like '33%' ";//开发库中没有v_gc_huojia_info_syn,测试只好建表往里插数据
		ResultSet result = null;
		GcAppHotInfo g=null;
		try {
			String sqlCode="com.aspire.dotcard.syncGoodsCenter.dao.SyncDataDAO.findGcAppHotInfoData";//开发库中没有v_gc_huojia_info_syn,测试只好建表往里插数据
			Object[] paras={receiveChangeVO.getEntityid()};//change	
			result=DB.getInstance().queryBySQLCode(sqlCode, paras);
			if(result.next()){
				g=new GcAppHotInfo();
				g.setCONTENT_ID(result.getString("CONTENT_ID"));
				g.setCONTENT_NAME(result.getString("CONTENT_NAME"));
				g.setPRODUCT_TYPE_ID(result.getString("PRODUCT_TYPE_ID"));
				g.setPRODUCT_SUBTYPE_ID(result.getString("PRODUCT_SUBTYPE_ID"));
				g.setONLINE_DATE(result.getDate("ONLINE_DATE"));
				g.setOFFLINE_DATE(result.getDate("OFFLINE_DATE"));
				g.setCHARGE_TYPE_ID(result.getString("CHARGE_TYPE_ID"));
				g.setICP_TYPE(result.getString("ICP_TYPE"));
				g.setCOMPANY_ID(result.getString("COMPANY_ID"));
				g.setSTATUS(result.getInt("STATUS"));
				g.setSUBSTATUS(result.getInt("SUBSTATUS"));
				g.setISLOCK(result.getInt("ISLOCK"));
				g.setFEECOMMERCIALDATE(result.getDate("FEECOMMERCIALDATE"));
				g.setCONTENTSTATUS(result.getString("CONTENTSTATUS"));
				g.setCONTATTR(result.getString("CONTATTR"));
				g.setFIRSTCOMMERCIALDATE(result.getDate("FIRSTCOMMERCIALDATE"));
				g.setGRADE(result.getString("GRADE"));
				g.setTHIRDAPPTYPE(result.getString("THIRDAPPTYPE"));
				g.setHATCHAPPID(result.getString("HATCHAPPID"));
				g.setFEE(result.getString("FEE"));
				g.setCONTENTCODE(result.getString("CONTENTCODE"));
				g.setCONT_STAT(result.getString("CONT_STAT"));
				g.setFLOW_TIME(result.getDate("FLOW_TIME"));
				g.setPROGRAM_SIZE(result.getString("PROGRAM_SIZE"));
				g.setCON_LEVEL(result.getString("CON_LEVEL"));
				g.setISCONVERGE(result.getString("ISCONVERGE"));
				g.setLUPDDATE(result.getDate("LUPDDATE"));
				g.setVERSION(result.getString("VERSION"));
				g.setVERSIONCODE(result.getString("VERSIONCODE"));
				g.setAP_NAME(result.getString("AP_NAME"));
				g.setAPPSIZE(result.getString("APPSIZE"));
				g.setPACKAGE_URL(result.getString("PACKAGE_URL"));
				g.setDOWNLOAD_NUM(result.getString("DOWNLOAD_NUM"));
				g.setGRADE1_TYPE(result.getString("GRADE1_TYPE"));
				g.setGRADE2_TYPE(result.getString("GRADE2_TYPE"));
				g.setUSAGE_DESC(result.getString("USAGE_DESC"));
				g.setUPDATEDATE(result.getString("UPDATEDATE"));
				g.setLOGO_URL(result.getString("LOGO_URL"));
				g.setSCREEN_URL1(result.getString("SCREEN_URL1"));
				g.setSCREEN_URL2(result.getString("SCREEN_URL2"));
				g.setSCREEN_URL3(result.getString("SCREEN_URL3"));
				g.setSCREEN_URL4(result.getString("SCREEN_URL4"));
				g.setCONTENT_DESC(result.getString("CONTENT_DESC"));
				g.setSOURCE(result.getString("SOURCE"));
				g.setPACKAGENAME(result.getString("PACKAGENAME"));
				g.setCOPYRIGHTFLAG(result.getString("COPYRIGHTFLAG"));
				g.setAPPREALNAME(result.getString("APPREALNAME"));
				g.setWASHPACKSTATUS(result.getString("WASHPACKSTATUS"));
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.close(result);
		}
		return g;
	}
	
	
	/**
	 * 从某个表中提取出某字段，然后把字段统一封装到list集合中
	 * @return 
	 */
	public static  List<String> getContentIdByDelete(){
		Connection conn = null;
		String sql=" select v.goodsid from v_gc_app_info_ssms_increment v where action=3 and  exists(select 1 from t_r_gcontent g where g.contentid=v.goodsid)";
		List<String> list=new ArrayList<String>();
		Statement statement = null;
		ResultSet result = null;
		try {
			conn = DB.getInstance().getConnection();
			if (LOG.isDebugEnabled()) {
				LOG.debug("execute sql is:" + sql);
			}
			statement = conn.createStatement();
			result=statement.executeQuery(sql);
			while(result.next()){
					list.add(result.getString("goodsid"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			// 释放资源
			if (result != null) {
				try {
					result.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		return list;
	}
	
	

	/**
	 * 从某个表中提取出某字段，然后把字段统一封装到list集合中
	 * @param 字段名
	 * @param 表名
	 * @param 是否 distinct
	 * @return 
	 * @throws DAOException
	 * @throws Exception 
	 */
	public static  List<String> getColumnFromTable(String Column,String table,boolean distinct){
		Connection conn = null;
		String sql=null;
		List<String> list=new ArrayList<String>();
		if(distinct==true){
			sql="select   distinct "+Column+" from "+table+" where contentid like '33%'";
		}else{
			sql="select "+ Column +" from "+table+" where contentid like '33%'" ;
		}
		Statement statement = null;
		ResultSet result = null;
		try {
			conn = DB.getInstance().getConnection();
			if (LOG.isDebugEnabled()) {
				LOG.debug("execute sql is:" + sql);
			}
			statement = conn.createStatement();
			result=statement.executeQuery(sql);
			while(result.next()){
					list.add(result.getString(Column));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			// 释放资源
			if (result != null) {
				try {
					result.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		return list;
	}
	/**
	 * 从t_a_cm_device_resource找出全部的device_id,device_name字段
	 * @return
	 * @throws DAOException
	 * @throws SQLException 
	 */

	/**
	 * 根据contentid从应用表中删除数据
	 *
	 * @throws DAOException
	 */

	public List  tRGcontentDelete(String g) throws Exception{
		String sqlCode1="com.aspire.dotcard.syncGoodsCenter.dao.SyncDataDAO.TRGcontentDelete1";
		//sql=select id from t_r_gcontent where contentid=?
		Object[] paras = {g};
		List<String> gcontentIdList=new ArrayList<String>();
		List<GcAppHotInfo> gcontentList=transactionDB.queryBySQLCode(sqlCode1, paras, new ResultSetConvertor(){
			@Override
			public Object convert(ResultSet rs) throws SQLException {
				GcAppHotInfo g=new GcAppHotInfo();
				g.setID(rs.getString("id"));
				return g;
			}
		});
		for (Iterator iterator = gcontentList.iterator(); iterator.hasNext();) {
			GcAppHotInfo gcAppHotInfo = (GcAppHotInfo) iterator.next();
			gcontentIdList.add(gcAppHotInfo.getID());
		}
		String sqlCode2="com.aspire.dotcard.syncGoodsCenter.dao.SyncDataDAO.TRGcontentDelete2";
//		sql=delete from t_r_gcontent where contentid=?
		transactionDB.executeBySQLCode(sqlCode2, paras);
		return gcontentIdList;
	}
	/**
	 *  把GcAppHotInfo这vo数据填入到t_r_gcontent（应用表）
	 * @param g
	 * @throws Exception
	 */
	public List<String> tRGcontentInsert(GcAppHotInfo g) throws Exception{
		String pattern ="yyyy-MM-dd HH:mm:ss";
		List<String> idList=new ArrayList<String>();
		String id=new NodePersistencyDB(null).allocateNewNodeID();
		idList.add(id);
		Long size=null;
		String programSize=g.getPROGRAM_SIZE();
		if(null!=programSize||!"".equals(programSize)){
			size=(long) (Float.parseFloat(programSize)/1024);
		}
		String sqlCode="com.aspire.dotcard.syncGoodsCenter.dao.SyncDataDAO.TRGcontentInsert";
		
		//将33应用的二级分类与30应有的二级分类做好映射
//		String type2 = PPMSDAO.getTransactionInstance(null).getSecCategory(g.getGRADE2_TYPE());
//		g.setGRADE2_TYPE(type2);
		//336开头的加官方标识
//		String copyrightflag="0";
//		if(g.getCONTENT_ID().startsWith("336") || g.getCONTENT_ID().startsWith("338"))
//		{
//			copyrightflag="1";
//		}
		//sql:insert into t_r_gcontent(copyrightflag,appcateid,subtype,id,contentid,VERSION,name,MARKETDATE,LUPDDATE,INTRODUCTION,PROGRAMSIZE,COMPANYID,CATENAME,APPCATENAME,icpcode,icpservid,spname,logo5,logo6,picture1,picture2,picture3,picture4,plupddate,servattr,fulldeviceid,fulldevicename,provider,platform,brand,pvcid,cityid) SELECT ?,?,?,?,?,?,?,?,?,?,?,?,?,?,'330000','1000330000',?,?,?,?,?,?,?,to_char(sysdate,'yyyy--mm-dd hh24:mi:ss'),'G',T1.fulldeviceid,,T2.fulldevicename,'G','{android}','{Android},{android}','0000','{0000}' FROM (select  wmsys.wm_concat( distinct '{'||device_id||'}') fulldeviceid from t_a_cm_device_resource) T1,(select  wmsys.wm_concat( distinct '{'||device_name||'}') fulldeviceid from t_a_cm_device_resource) T2		

		Object[] paras2 = {g.getCOPYRIGHTFLAG(),g.getPRODUCT_SUBTYPE_ID(),g.getTHIRDAPPTYPE(),id,g.getCONTENT_ID(),g.getVERSION(),g.getCONTENT_NAME(),(null==g.getONLINE_DATE()?null:StringUtils.toString(g.getONLINE_DATE(), pattern)),
				(null==g.getLUPDDATE()?null:StringUtils.toString(g.getLUPDDATE(), pattern)),g.getCONTENT_DESC(),size,g.getGRADE1_TYPE(),
				g.getGRADE2_TYPE(),g.getAP_NAME(),g.getLOGO_URL(),g.getLOGO_URL(),g.getSCREEN_URL1(),g.getSCREEN_URL2(),g.getSCREEN_URL3(),g.getSCREEN_URL4(),g.getAPPID()};
		transactionDB.executeBySQLCode(sqlCode, paras2);
		
		return idList;
	}
	/**
	 * 根据contentid从应用业务表中删除数据
	 * 
	 * @throws DAOException
	 */
	public List tRGcontentUpdate(GcAppHotInfo g) throws Exception{
		String id=null;
		String pattern ="yyyy-MM-dd HH:mm:ss";
		Object[] paras1={g.getCONTENT_ID()};
		List<String> idList=new ArrayList<String>();
		ResultSetConvertor rc1=new ResultSetConvertor(){
			public Object convert(ResultSet rs) throws SQLException {
				GcAppHotInfo g=new GcAppHotInfo();
				g.setID(rs.getString("id"));
				return g;
			}
		};
		String sqlcode1="com.aspire.dotcard.syncGoodsCenter.dao.SyncDataDAO.tRGcontentUpdate";
		//sql=select id from t_r_gcontent where contentid=?
		List<GcAppHotInfo> list1=transactionDB.queryBySQLCode(sqlcode1, paras1, rc1);
		for (int i = 0; i < list1.size(); i++) {
			GcAppHotInfo g1 = list1.get(i);
			id=g1.getID();
			idList.add(id);
		}
		Long size=null;
		String programSize=g.getPROGRAM_SIZE();
		if(null!=programSize||!"".equals(programSize)){
			size=(long) (Float.parseFloat(programSize)/1024);
		}
		String sqlCode="com.aspire.dotcard.syncGoodsCenter.dao.SyncDataDAO.tRGcontentUpdate2";
		
		//336开头的加官方标识
//		String copyrightflag="0";
//		if(g.getCONTENT_ID().startsWith("336"))
//		{
//			copyrightflag="1";
//		}
		//sql:update t_r_gcontent set copyrightflag=?,subtype=?,contentid=?, VERSION=?, name=?, MARKETDATE=?,LUPDDATE=?,INTRODUCTION=?, PROGRAMSIZE=?,COMPANYID=?, CATENAME=?,APPCATENAME=?,spname=?,logo5=?,logo6=?, picture1=?,picture2=?,picture3=?, picture4=?,plupddate=to_char(sysdate, 'yyyy--mm-dd hh24\:mi\:ss') where id = ? 	
		

		Object[] paras2 = {g.getCOPYRIGHTFLAG(),g.getPRODUCT_SUBTYPE_ID(),g.getTHIRDAPPTYPE(),g.getCONTENT_ID(),g.getVERSION(),g.getCONTENT_NAME(),(null==g.getONLINE_DATE()?null:StringUtils.toString(g.getONLINE_DATE(), pattern)),
				(null==g.getLUPDDATE()?null:StringUtils.toString(g.getLUPDDATE(), pattern)),g.getCONTENT_DESC(),size,g.getGRADE1_TYPE(),
				g.getGRADE2_TYPE(),g.getAP_NAME(),g.getLOGO_URL(),g.getLOGO_URL(),g.getSCREEN_URL1(),g.getSCREEN_URL2(),g.getSCREEN_URL3(),g.getSCREEN_URL4(),g.getAPPID(),id};
		transactionDB.executeBySQLCode(sqlCode, paras2);
		return idList;
	}

	public void tADCPPMSServiceDelete(String g) throws Exception{
		String sqlcode="com.aspire.dotcard.syncGoodsCenter.dao.SyncDataDAO.TADCPPMSServiceDelete";
		//sql=delete from t_a_dc_ppms_service where contentid=?
		Object[] paras = {g};
		transactionDB.executeBySQLCode(sqlcode, paras);//如果全量数据和t_a_dc_ppms_service表都有那条数据先删除那条数据
	}
	/**
	 * 把GcAppHotInfo这vo数据填入到t_a_dc_ppms_service（应用业务表）
	 * @param g
	 * @throws Exception
	 */
	public void tADCPPMSServiceInsert(GcAppHotInfo g) throws Exception{
		String sqlcode="com.aspire.dotcard.syncGoodsCenter.dao.SyncDataDAO.TADCPPMSServiceInsert";
		//sql=insert into t_a_dc_ppms_service(contentid,icpservid,mobileprice,chargetype,icpcode,servtype) values (?,'1000330000',?,'02','330000',8)
		Object[] paras2 = {g.getCONTENT_ID(),null==g.getFEE()?0:Integer.parseInt(g.getFEE())};
		transactionDB.executeBySQLCode(sqlcode, paras2);
	}
	/**
	 * 根据contentid从机型适配表中删除数据
	 * @throws DAOException
	 */

	public void tACmDeviceResourceDelete(String g) throws Exception{
		String sqlcode="com.aspire.dotcard.syncGoodsCenter.dao.SyncDataDAO.TACmDeviceResourceDelete";
		Object[] paras = {g};
		transactionDB.executeBySQLCode(sqlcode, paras);//如果全量数据和t_a_cm_device_resource表都有那条数据先删除那条数据
		
	}
	/**
	 * 把GcAppHotInfo这vo数据填入到t_a_cm_device_resource（机型适配表），由于商品中心应用目前没有机型适配，所以在接入到MM做全机型适配（查询t_a_cm_device_resource表里的所有机型）
	 * @param g
	 * @throws Exception
	 */
	public void tACmDeviceResourceInsert(GcAppHotInfo g) throws Exception{
		String sqlcode="com.aspire.dotcard.syncGoodsCenter.dao.SyncDataDAO.TACmDeviceResourceInsert";
		//sql=insert into t_a_cm_device_resource(device_id,device_name,contentid,contentname,programsize,pid,createdate,version,versionname,ISCDN,clientid,CDNUrl,iswhitelist) select t.device_id,t.device_name,?,?,? ,'3330000',sysdate,?,?,1,?,?,0 from  (select distinct device_id,device_name from t_a_cm_device_resource where device_name is not null) t 
		Long size=null;
		String programSize=g.getPROGRAM_SIZE();
		if(null!=programSize||!"".equals(programSize)){
			size=(long) (Float.parseFloat(programSize)/1024);
		}
		Object[] paras2 ={g.getCONTENT_ID(),g.getCONTENT_NAME(),size,g.getVERSIONCODE(),g.getVERSION(),g.getPACKAGENAME(),g.getPACKAGE_URL(),g.getPACKAGE_URL(),g.getAPPREALNAME(),g.getWASHPACKSTATUS()};
		transactionDB.executeBySQLCode(sqlcode, paras2);
	}
	/**
	 * 删除上架的应用
	 * 	
	 * @throws Exception 
	 */

	public List<String> newStockDelete(String g) throws Exception{
		String sqlcode1="com.aspire.dotcard.syncGoodsCenter.dao.SyncDataDAO.newStockDelete1";
		//sql=select r.id from t_r_reference r where r.REFNODEID in (select id from t_r_gcontent where contentid=?) and r.categoryid in (select c.categoryid from t_r_category c,t_sync_tactic_android t where t.categoryid=c.id)
		String sqlcode2="com.aspire.dotcard.syncGoodsCenter.dao.SyncDataDAO.newStockDelete2";
		//sql=delete from t_r_reference r where r.REFNODEID in (select id from t_r_gcontent where contentid=?) and r.categoryid in (select c.categoryid from t_r_category c,t_sync_tactic_android t where t.categoryid=c.id)

		Object[] paras ={g};
		List<String> refenceIdList=new ArrayList<String>();
		List<GcAppHotInfo> refenceList=transactionDB.queryBySQLCode(sqlcode1, paras, new ResultSetConvertor(){
			@Override
			public Object convert(ResultSet rs) throws SQLException {
				GcAppHotInfo g=new GcAppHotInfo();
				g.setID(rs.getString("id"));
				return g;
			}
		});
		for (Iterator iterator = refenceList.iterator(); iterator.hasNext();) {
			GcAppHotInfo gcAppHotInfo = (GcAppHotInfo) iterator.next();
			refenceIdList.add(gcAppHotInfo.getID());
			
		}
		transactionDB.executeBySQLCode(sqlcode2, paras);
		return refenceIdList;
	}
	/**
	 * 上架应用
	 * 上架到对应的二级分类货架上，根据二级分类名称，从t_sync_tactic_android表里拿到对应的二级分类货架ID（拿不到ID的，忽略不上架），然后将该应用上架到该货架里（即插入到t_r_reference，商品表中）。
	 * @param g
	 * @throws Exception
	 */
	public List<String> newStockInsert(GcAppHotInfo g) throws Exception{
		String id=null;
		List<String>idList=new ArrayList<String>();
		String sqlcode1="com.aspire.dotcard.syncGoodsCenter.dao.SyncDataDAO.newStockInsert1";
		//sql:select categoryid from t_r_category where id in (select categoryid from t_sync_tactic_android where appcatename=? and contenttype=?)

		String contentType=null;
		contentType=g.getGRADE1_TYPE().trim().equals("软件")?"nt:gcontent:appSoftWare":g.getGRADE1_TYPE().trim().equals("游戏")?"nt:gcontent:appGame":"nt:gcontent:appTheme";
		Object[] paras1 ={g.getGRADE2_TYPE(),contentType};
		ResultSetConvertor rc=new ResultSetConvertor(){
			public Object convert(ResultSet rs) throws SQLException {
				GcAppHotInfo g=new GcAppHotInfo();
				g.setCATEGORYID(rs.getString("categoryid"));
				return g;
			}
		};
		String goodsID;
		List<GcAppHotInfo> list=transactionDB.queryBySQLCode(sqlcode1, paras1, rc);
		for (int i = 0; i < list.size(); i++) {
			id=new NodePersistencyDB(null).allocateNewNodeID();
			idList.add(id);
			String sqlcode2="com.aspire.dotcard.syncGoodsCenter.dao.SyncDataDAO.newStockInsert2";
			//sql=insert into t_r_reference(id,REFNODEID,categoryid,goodsid) select ?,t2.id,?,? from (select id from t_r_gcontent where contentid=?)t2
			
			if (StringUtils.isEmpty(g.getAPPID())) {
				goodsID = g.getCATEGORYID()
						+ PublicUtil.lPad("330000", 6)
						+ PublicUtil.lPad("100033003300", 12)
						+ PublicUtil.lPad(g.getCONTENT_ID(), 12);
			} else {
				goodsID = g.getCATEGORYID()
						+ "000000"
						+ PublicUtil.lPad(g.getAPPID(), 12)
						+ PublicUtil.lPad(g.getCONTENT_ID(), 12);
			}
			g.setGOODSID(goodsID);
			Object[] paras2 ={id,list.get(i).getCATEGORYID(),g.getGOODSID(),g.getCONTENT_ID()};//GOODSID唯一约束
			//transactionDB.executeBySQLCode(sqlcode2, paras2);
		}
		return idList;
	}
	
	public List<String> newStockUpdate(GcAppHotInfo g,String trgcontentId) throws Exception{
		Object[]paras1={g.getCONTENT_ID()};
		List<String>idList=new ArrayList<String>();
		String id=null;
		ResultSetConvertor rc1=new ResultSetConvertor(){
			public Object convert(ResultSet rs) throws SQLException {
				GcAppHotInfo g=new GcAppHotInfo();
				g.setID(rs.getString("id"));
				return g;
			}
		};
		String sqlcode1="com.aspire.dotcard.syncGoodsCenter.dao.SyncDataDAO.newStockUpdate";
		//sql=select r.id from t_r_reference r where r.refnodeid =(select id from t_r_gcontent where contentid=?)and r.categoryid in (select c.categoryid from t_r_category c, t_sync_tactic_android t where t.categoryid = c.id)
		List<GcAppHotInfo> list1=transactionDB.queryBySQLCode(sqlcode1, paras1, rc1);
		String sqlcode="com.aspire.dotcard.syncGoodsCenter.dao.SyncDataDAO.newStockUpdate3";
		//sql=delete from t_r_base where id=?
		String sqlCodeDel="com.aspire.dotcard.syncGoodsCenter.dao.SyncDataDAO.baseDelete";
		for (int i = 0; i < list1.size(); i++) {
			GcAppHotInfo g1 = list1.get(i);
			id=g1.getID();
			Object[] paras={id};
			transactionDB.executeBySQLCode(sqlcode, paras);
			transactionDB.executeBySQLCode(sqlCodeDel, paras);
//			idList.add(id);
		}
		Object[] paras6={trgcontentId};
		transactionDB.executeBySQLCode(sqlCodeDel, paras6);
		String sqlcode2="com.aspire.dotcard.syncGoodsCenter.dao.SyncDataDAO.newStockInsert1";
		//sql:select categoryid from t_r_category where id in (select categoryid from t_sync_tactic_android where appcatename=? and contenttype=?)
		String contentType=null;
		contentType=g.getGRADE1_TYPE().trim().equals("软件")?"nt:gcontent:appSoftWare":g.getGRADE1_TYPE().trim().equals("游戏")?"nt:gcontent:appGame":"nt:gcontent:appTheme";
		Object[] paras2 ={g.getGRADE2_TYPE(),contentType};
		ResultSetConvertor rc2=new ResultSetConvertor(){
			public Object convert(ResultSet rs) throws SQLException {
				GcAppHotInfo g=new GcAppHotInfo();
				g.setCATEGORYID(rs.getString("categoryid"));
				return g;
			}
		};  
		List<GcAppHotInfo> list2=transactionDB.queryBySQLCode(sqlcode2, paras2, rc2);
		String goodsID;
		for (int i1 = 0; i1 < list2.size(); i1++) {
			id=new NodePersistencyDB(null).allocateNewNodeID();
			if (StringUtils.isEmpty(g.getAPPID())) {
				goodsID = g.getCATEGORYID()
						+ PublicUtil.lPad("330000", 6)
						+ PublicUtil.lPad("100033003300", 12)
						+ PublicUtil.lPad(g.getCONTENT_ID(), 12);
			} else {
				goodsID = g.getCATEGORYID()
						+ "000000"
						+ PublicUtil.lPad(g.getAPPID(), 12)
						+ PublicUtil.lPad(g.getCONTENT_ID(), 12);
			}
			g.setGOODSID(goodsID);
			String sqlcode3="com.aspire.dotcard.syncGoodsCenter.dao.SyncDataDAO.newStockUpdate2";
			//sql=insert into t_r_reference(id,REFNODEID,categoryid,goodsid,SORTID,loaddate,variation) select ?,t2.id,?,?,0,to_char(sysdate,'yyyy--mm-dd hh24:mi:ss'),99999 from (select id from t_r_gcontent where contentid=?)t2
			Object[] paras3 ={id,list2.get(i1).getCATEGORYID(),g.getGOODSID(),g.getCONTENT_ID()};
			//transactionDB.executeBySQLCode(sqlcode3, paras3);
			idList.add(id);
		}
		
		return idList;

	}
	/**
	 * 
	 * 找出已经上架的33应用的contentid
	 * @return  
	 */
	public static List<String> findContenId(){
		String sqlcode="com.aspire.dotcard.syncGoodsCenter.dao.SyncDataDAO.findContenId";
		
		List<String> list=new ArrayList<String>();
		
		ResultSet result = null;
		try{
			if (LOG.isDebugEnabled()) {
				LOG.debug("execute sql is:" + sqlcode);
			}
			
			result=DB.getInstance().queryBySQLCode(sqlcode, null);
			while(result.next()){
				list.add(result.getString("contentid"));
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
		e.printStackTrace();
		
		}
		return list;
	}
	
	
//	public void deleteFromRefenceByContentid(String g) throws Exception{
//		String sqlcode="com.aspire.dotcard.syncGoodsCenter.dao.SyncDataDAO.deleteFromRefenceByContentid";
//		Object[] paras ={g};
//		transactionDB.executeBySQLCode(sqlcode, paras);
//	}
	public static  List<RefModifyReqVo> findRefModifyReqVo(String contentid){
		Connection conn = null;
		DB db=DB.getInstance();
		
		PreparedStatement statement = null;
		ResultSet result = null;
		List<RefModifyReqVo> list=null;
		try {
			String sql=db.getSQLByCode("com.aspire.dotcard.syncGoodsCenter.dao.SyncDataDAO.findRefModifyReqVo");
			//sql=select t.goodsid, t.categoryid, t.id, t.refnodeid, t.sortid, t.loaddate from (select t1.goodsid,t1.categoryid,t1.refnodeid, t1.sortid, t1.loaddate,t2.id from t_r_reference t1, t_r_category t2,(select id from t_r_gcontent where contentid =?)t3 where t1.refnodeid = t3.id and t2.categoryid =any(select categoryid from t_r_reference where refnodeid = t3.id)) t
			list=new ArrayList<RefModifyReqVo>();
			conn = db.getConnection();
			if (LOG.isDebugEnabled()) {
				LOG.debug("execute sql is:" + sql);
			}
			statement = conn.prepareStatement(sql);
			statement.setString(1,contentid);
			
			result=statement.executeQuery();
			
			while(result.next()){
				RefModifyReqVo g=new RefModifyReqVo();
				g.setGoodsid(result.getString("goodsid"));
				g.setCategoryid(result.getString("categoryid"));
				g.setId(result.getString("id"));
				g.setRefnodeid(result.getString("refnodeid"));
				g.setSortid(result.getInt("sortid"));
				g.setLoaddate(result.getString("loaddate"));
				list.add(g);
			}
		}catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			// 释放资源
			if (result!= null) {
				try {
					result.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (Exception e) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
				}
			}
		}
		return list;
	}
	public String createGoodsid(GcAppHotInfo g){
		ResultSet rs = null;
		String s = null ;
        try
        {	String type=g.getGRADE1_TYPE().trim().equals("软件")?"nt:gcontent:appSoftWare":g.getGRADE1_TYPE().trim().equals("游戏")?"nt:gcontent:appGame":"nt:gcontent:appTheme";
        	Object[]paras={g.getGRADE2_TYPE(),type};
        	String sqlCode="com.aspire.dotcard.syncGoodsCenter.dao.SyncDataDAO.createGoodsid";
 //sql=select categoryid from t_r_category where id in (select categoryid from t_sync_tactic_android where appcatename=? and contenttype=?)
            rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
            while(rs.next()){
            	s=rs.getString(1);
            }
            
        }
        catch(Exception e)
        {
        	e.printStackTrace();
            LOG.error("", e);
        }
        finally
        {
            DB.close(rs);
        }
		return s;
		
	}
	public String baseInsert(GcAppHotInfo g,List gcontentIdList,List referenceIdList) throws Exception {
		String path=null;
		String parentcategoryId=null;
		String type=null;
		Connection c=null;
		String goodsID="";
		
		type=g.getGRADE1_TYPE().trim().equals("软件")?"nt:gcontent:appSoftWare":g.getGRADE1_TYPE().trim().equals("游戏")?"nt:gcontent:appGame":"nt:gcontent:appTheme";
		Object[] obj={g.getGRADE2_TYPE(),type};//change		
		String sqlCode1="com.aspire.dotcard.syncGoodsCenter.dao.SyncDataDAO.baseInsert1";
		//sql:select categoryid,contenttype from t_sync_tactic_android where appcatename=? and contenttype=?
		String sqlCode4="com.aspire.dotcard.syncGoodsCenter.dao.SyncDataDAO.baseInsert4";
		//sql:insert into t_r_base(id,parentid,path,type) values(?,?,?,?)
		try{
			c=transactionDB.getConnection();
			

			
			List<GcAppHotInfo> list=transactionDB.queryBySQLCode(sqlCode1,obj, new ResultSetConvertor(){
				@Override
				public Object convert(ResultSet rs) throws SQLException {
					GcAppHotInfo g=new GcAppHotInfo();
					g.setCATEGORYID(rs.getString("categoryid"));
					return g;
				}
			});
			for (int i = 0; i < gcontentIdList.size(); i++) {
				String id=(String) gcontentIdList.get(i);
				path="{100}.{702}"+".{"+id+"}";
				Object[] obj3={id,"702",path,type};
				transactionDB.executeBySQLCode(sqlCode4, obj3);
			}
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				GcAppHotInfo gcAppHotInfo = (GcAppHotInfo) iterator.next();
				String sqlCode2="com.aspire.dotcard.syncGoodsCenter.dao.SyncDataDAO.baseInsert2";
					//sql:select parentcategoryid from t_r_category where id=?
				path=".{"+gcAppHotInfo.getCATEGORYID()+"}.";
				parentcategoryId=gcAppHotInfo.getCATEGORYID();
				String sql2=transactionDB.getSQLByCode(sqlCode2);
				PreparedStatement p2=null;
				PreparedStatement p3=null;
				ResultSet rs2=null;
				ResultSet rs3=null;
				try{
					p2=c.prepareStatement(sql2);
					p2.setString(1, gcAppHotInfo.getCATEGORYID());
					rs2=p2.executeQuery();
					rs2.next();
					String sqlCode3="com.aspire.dotcard.syncGoodsCenter.dao.SyncDataDAO.baseInsert3";
						//sql:select id, parentcategoryid from t_r_category where categoryid=?
					String sql3=transactionDB.getSQLByCode(sqlCode3);
					p3=c.prepareStatement(sql3);
					p3.setString(1, rs2.getString("parentcategoryid"));
					rs3=p3.executeQuery();
					rs3.next();
					path=".{"+rs3.getString("id")+"}"+path;
					String pid=rs3.getString("parentcategoryid");
					while(pid!=null||(pid==null?false:!pid.equals(""))){
						p3.setString(1, rs3.getString("parentcategoryid"));
						rs3=p3.executeQuery();
						rs3.next();
						pid=rs3.getString("parentcategoryid");
						path=".{"+rs3.getString("id")+"}"+path;//
					}
				}finally{
					if(rs2!=null)rs2.close();
					if(rs3!=null)rs3.close();
					if(p2!=null)p2.close();
					if(p3!=null)p3.close();
					
				}
				for (int i = 0; i < referenceIdList.size(); i++) {
					String id=(String) referenceIdList.get(i);
					path="{100}.{701}"+path+"{"+id+"}";
					Object[] obj2={id,parentcategoryId,path,"nt:reference"};
					transactionDB.executeBySQLCode(sqlCode4, obj2);
				}
				
			}
		}catch(Exception e){
			  	LOG.error("baseInsert:", e);
				e.printStackTrace();
		}
		return type;
	}
	public void baseDelete(List gcontentIdList,List referenceIdList) throws Exception{
		String sqlCode="com.aspire.dotcard.syncGoodsCenter.dao.SyncDataDAO.baseDelete";
		//sql=delete from t_r_base where id\=?
		for (Iterator iterator = referenceIdList.iterator(); iterator.hasNext();) {
			String s = (String) iterator.next();
			Object[] paras={s};
			transactionDB.executeBySQLCode(sqlCode, paras);
		}
		for (Iterator iterator = gcontentIdList.iterator(); iterator.hasNext();) {
			String s = (String) iterator.next();
			Object[] paras={s};
			transactionDB.executeBySQLCode(sqlCode, paras);
		}	}
	public void vserviceInsert(GcAppHotInfo g)throws Exception{
		String sqlCode="com.aspire.dotcard.syncGoodsCenter.dao.SyncDataDAO.vserviceInsert";
//sql=insert into v_service (CONTENTID,icpcode,icpservid,servtype,chargetype,mobileprice,servattr,lupddate) values (?,'330000','1000330000',8,'02',?,'G',sysdate)
		Object[]paras={g.getCONTENT_ID(),null==g.getFEE()?0:Integer.parseInt(g.getFEE())};
		transactionDB.executeBySQLCode(sqlCode, paras);
	}
	public void vserviceDelete(String s)throws Exception{
		String sqlCode="com.aspire.dotcard.syncGoodsCenter.dao.SyncDataDAO.vserviceDelete";
		Object[]paras={s};
		transactionDB.executeBySQLCode(sqlCode, paras);
	}
	public void vcontentlastInsert(GcAppHotInfo g) throws Exception{
		String sqlCode="com.aspire.dotcard.syncGoodsCenter.dao.SyncDataDAO.vcontentlastInsert";
		Object[] paras={g.getCONTENT_ID()};
		transactionDB.executeBySQLCode(sqlCode, paras);
	}
	public void vcontentlastDelete(String s) throws Exception{
		String sqlCode="com.aspire.dotcard.syncGoodsCenter.dao.SyncDataDAO.vcontentlastDelete";
		Object[] paras={s};
		transactionDB.executeBySQLCode(sqlCode, paras);
	}
	
	
	
}
