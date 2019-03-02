package com.aspire.dotcard.syncAndroid.ppms;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.syncAndroid.common.Constant;
import com.aspire.dotcard.syncAndroid.dc.jms.MSGType;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.system.Config;

public class APPInfoDAO {
	/**
	 * 日志引用
	 */
	JLogger LOG = LoggerFactory.getLogger(APPInfoDAO.class);

	private static APPInfoDAO dao = new APPInfoDAO();

	private APPInfoDAO() {

	}

	/**
	 * 单例模式
	 * 
	 * @return
	 */
	public static APPInfoDAO getInstance() {

		return dao;
	}

	/**
	 * 获取事务类型TransactionDB的实例 如果已经指定了，用已经指定的。如果没有指定，自己创建一个，注意自己创建的直接用不支持事务类型的即可
	 * 
	 * @return TransactionDB
	 */
	private TransactionDB getTransactionDB() {//这里的方法和PPMSDAO的getTransactionDB写法有点不一样，主要原因是这个类里面的事物都是
											  //在本方法中，而PPMSDAO的的事务跨多个方法，故实例要NEW 一个，见它的getTransactionInstance方法。
		return TransactionDB.getInstance();
	}

	/**
	 * 插入APPInfo
	 * 
	 * @param vo
	 * @return
	 * @throws DAOException
	 */
	public void addAPPInfo(APPInfoVO vo) throws DAOException {
		String sql = "SyncAndroid.APPInfoDAO.addAPPInfo().INSERT";
		Object[] paras = { vo.getTransactionID(),vo.getType(), vo.getContentID(), Constant.MESSAGE_HANDLE_STATUS_INIT};
		DB.getInstance().executeBySQLCode(sql, paras);
	}

	public List<MessageVo> getMessageList(int status)
			throws DAOException {
		String sqlCode = "SyncAndroid.APPInfoDAO.getMessageList().SELECT";
		ResultSet rs = null;
		Object[] paras = {status };
		List<MessageVo> messageList = new ArrayList<MessageVo>();
		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
			while (rs != null && rs.next()) {
				MessageVo vo = new MessageVo();
				vo.setId(rs.getString("id"));
				vo.setType(rs.getString("type"));
				vo.setStatus(rs.getString("status"));
				vo.setMessage(rs.getString("message"));
				messageList.add(vo);
			}
		} catch (Exception ex) {
			throw new DAOException("getMessageList error!!", ex);
		} finally {
			DB.close(rs);
		}

		return messageList;
	}

	public GContentVO getGContentVO(String contentId){
		//select id from t_r_gcontent where contentId=?
		long startTime = System.currentTimeMillis();
		String sqlCode = "SyncAndroid.APPInfoDAO.getGContentVO().SELECT";
		//String sqlCode2 = "SyncAndroid.APPInfoDAO.getGContentVO()appid.SELECT";
		ResultSet rs = null;
		GContentVO vo = null;
		Object[] paras = { contentId };
		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
			if (rs.next()) {
				vo = new GContentVO();
				vo.setId(rs.getString("id"));
				vo.setContentId(contentId);
				vo.setAppCateID(rs.getString("appCateID"));
				vo.setAppcatename(rs.getString("appcatename"));
				vo.setCatename(rs.getString("catename"));
				vo.setSubtype(rs.getString("subtype"));
				vo.setContenttag(rs.getString("contenttag"));
				vo.setAppid(rs.getString("appid"));
			}
		} catch (Exception ex) {
			//throw new DAOException("isExistsGContent error!!", ex);
			LOG.error(ex);
		} finally {
			DB.close(rs);
		}
		LOG.debug("cost time:从货架得到内容耗时间："+(System.currentTimeMillis()-startTime));
		return vo;
	}
	public CmContentVO getCmContent(String contentID) throws DAOException {
		long startTime = System.currentTimeMillis();
		CmContentVO vo = null;
		if (contentID == null) {
			return null;
		}
		String sqlCode = "SyncAndroid.APPInfoDAO.getDataCenterCmContentVoByContentID().SELECT";
	    String domainNameUrl=Config.getInstance().getModuleConfig().getItemValue("domainNameUrl");

		Object[] paras = {domainNameUrl,domainNameUrl,domainNameUrl,domainNameUrl,domainNameUrl,domainNameUrl,domainNameUrl,domainNameUrl,domainNameUrl,domainNameUrl,domainNameUrl,domainNameUrl,domainNameUrl,domainNameUrl, contentID };
		ResultSet rs = null;
		try {
		    rs = DB.getInstance().queryBySQLCode(sqlCode, paras);

			if (rs != null && rs.next()) {
				vo = new CmContentVO();
				vo.setHandbook(rs.getString("handbook"));
				vo.setHandbookPicture(rs.getString("handbookPicture"));
				vo.setPvcid(rs.getString("pvcid"));
				vo.setIsmmtoevent(rs.getString("ismmtoevent"));
				vo.setCopyrightFlag(rs.getString("CopyrightFlag"));
				vo.setTypename(rs.getString("typename"));
				vo.setCatalogid(rs.getString("catalogid"));
				vo.setCateName(rs.getString("cateName"));
				vo.setContentid(rs.getString("contentid"));
				vo.setName(rs.getString("name"));
				vo.setContentCode(rs.getString("contentCode"));
				vo.setKeywords(rs.getString("keywords"));
				vo.setServAttr(rs.getString("servAttr"));
				vo.setCreateDate(rs.getString("createDate"));
				vo.setMarketdate(rs.getString("marketdate"));
				vo.setIcpservid(rs.getString("icpservid"));
				vo.setProductID(rs.getString("productID"));
				vo.setIcpcode(rs.getString("icpcode"));
				vo.setCompanyid(rs.getString("companyid"));
				vo.setCompanyname(rs.getString("companyname"));
				vo.setLupddate(rs.getString("lupddate"));
				vo.setPlupddate(rs.getString("plupddate"));
				vo.setChargeTime(rs.getString("chargeTime"));
				vo.setCityid(rs.getString("cityid"));
				vo.setAppCateID(rs.getString("appCateID"));
				vo.setAppCateName(rs.getString("appCateName"));
				vo.setIntroduction(rs.getString("appdesc"));
				vo.setWWWPropaPicture1(rs.getString("wwwpropaPicture1"));
				vo.setWWWPropaPicture2(rs.getString("wwwpropaPicture2"));
				vo.setWWWPropaPicture3(rs.getString("wwwpropaPicture3"));
				//vo.setProvider(rs.getString("provider")); 这个对付视图没有！
				vo.setProvider("O");//add by aiyan 2013-05-06不加这个provider 会影响索引文件生成。
				vo.setLanguage(rs.getString("language"));
				vo.setLogo1(rs.getString("logo1"));
				vo.setLogo2(rs.getString("logo2"));
				vo.setLogo3(rs.getString("logo3"));
				vo.setLogo4(rs.getString("logo4"));
				vo.setLogo5(rs.getString("logo5"));
				vo.setLogo6(rs.getString("logo6"));//add fanqh 201310
				vo.setOnlinetype(rs.getString("onlinetype"));
				vo.setVersion(rs.getString("version"));
				vo.setPicture1(rs.getString("picture1"));
				vo.setPicture2(rs.getString("picture2"));
				vo.setPicture3(rs.getString("picture3"));
				vo.setPicture4(rs.getString("picture4"));
				vo.setSubtype(rs.getString("subtype"));
				vo.setMapname(rs.getString("mapName"));
				vo.setChanneldisptype(rs.getString("channeldisptype"));
				vo.setIsCmpassApp(rs.getString("isCmpassApp"));
				vo.setLogo7(rs.getString("logo7"));
				vo.setRisktag(rs.getString("risktag"));
				vo.setApptype(rs.getString("apptype"));
				vo.setCtrldev(rs.getString("ctrldev"));
				vo.setDeveloper(rs.getString("developer"));
				processKeywords(vo);
			}
			LOG.debug("cost time:从电子流得到内容耗时间："+(System.currentTimeMillis()-startTime));
		} catch (SQLException e) {
			LOG.error("getCmContent出错:"+e.getMessage());
			throw new DAOException(e.getSQLState());
		} finally {
			DB.close(rs);
		}
		return vo;
	}
	
	 /**
	  * 将keywords的格式换成PAS的格式
	  * 
	  * @param gc
	 * @throws DAOException 
	  */
	 private void processKeywords(CmContentVO gc) throws DAOException
	 {
	     if (LOG.isDebugEnabled())
	     {
	    	 LOG.debug("processKeywords:" + gc);
	     }
	     if (gc == null)
	         return;

	     String keywords = gc.getKeywords();
	     
	     if(keywords != null && !"".equals(keywords.trim())){
	    	 keywords =  getLimitOPTagByContentID(gc.getContentid()) + getLimitAPTagByContentID(gc.getContentid()) + keywords;
	     }else{
	    	 keywords =  getLimitOPTagByContentID(gc.getContentid()) + getLimitAPTagByContentID(gc.getContentid());
	     }
	     
	     
	     if (keywords != null && !"".equals(keywords))
	     {
	         // 处理最后的分号
	         while (keywords.endsWith(";"))
	         {
	             keywords = keywords.substring(0, keywords.length() - 1);
	         }
	         
	         //货架同步新标签“客户端重点应用”对应的应用数据，另外存放到一张表中，里面有标签名称、内容ID字段
	         //货架不将“客户端重点应用”标签放到内容信息中的keyword字段中，以达到客户端上内容详情页不展示该标签目的
	         if(keywords.toString().trim().contains("客户端重点应用")){
	        	 
	        	 //select * from t_key_content_tag where contentid = ?
	        	 String selectSqlCode = "com.aspire.dotcard.syncAndroid.ppms.APPInfoDAO.processKeywords.SELECT";
	        	 //insert into t_key_content_tag (contentid,tagname) values (?,?)
	        	 String insertSqlCode = "com.aspire.dotcard.syncAndroid.ppms.APPInfoDAO.processKeywords.INSERT";
	        	 
	        	 ResultSet rs = null;
	     		  try {
					rs = DB.getInstance().queryBySQLCode(selectSqlCode, new Object[]{gc.getContentid()});
					if(rs == null || !rs.next()){
						DB.getInstance().executeInsertBySQLCode(insertSqlCode, new Object[]{gc.getContentid(),"客户端重点应用"});
					}
				}catch (SQLException e) {
							
					 LOG.error("processKeywords出错:"+e.getMessage());
					 throw new DAOException(e.getSQLState());
				}finally {
					DB.close(rs);
				}
	     		 keywords = keywords.replace(";客户端重点应用", "");
	     		 keywords = keywords.replace("客户端重点应用", "");
	        	 if(keywords.startsWith(";")){
	        		 keywords = keywords.substring(1);
	        	 }
	         }
	            if(keywords != null && !"".equals(keywords)){
	            	keywords = "{" + keywords.replaceAll(";", "};{") + "}";
	            }
	        	 if (LOG.isDebugEnabled())
	    	     {
	    	    	 LOG.debug("keywords:" + keywords);
	    	     }
		         gc.setKeywords(keywords);	         
	     }
	 }
	
//	public void updateGContent(CmContentVO vo)
//	 throws DAOException {
//		if (LOG.isDebugEnabled()) {
//			LOG.debug("updateGContent(" + vo + ")");
//		}
//	//update t_r_gcontent t set  t.catename=?,t.name=?,t.contenttag=?,
////		t.keywords=?,t.servattr=?,t.createdate=?,t.lupddate=?,t.plupddate=?,
////		t.marketdate=?,t.icpcode=?,t.icpservid=?,t.companyid=?,t.productid=?,t.companyname=?
//		//t.chargetime=?,
//		//t.cityid=?,t.appcatename=?,
////		t.appcateid=?,t.introduction=?,t.wwwpropapicture1=?,t.wwwpropapicture2=?,
////		t.wwwpropapicture3=?,t.provider=?,t.language=?,t.logo1=?,t.logo2=?,
////		t.logo3=?,t.logo4=?,t.logo5=?,t.onlinetype=?,t.version=?,t.picture1=?,
////		t.picture2=?,t.picture3=?,t.picture4=? where t.contentid=?
//		String sqlCode = "SyncAndroid.APPInfoDAO.updateGContentByDataCenterCmContentVo().UPDATE";
//		Object[] paras = {vo.getCateName(),vo.getName(),vo.getContentCode(),
//				vo.getKeywords(),vo.getServAttr(),vo.getCreateDate(),vo.getLupddate(),vo.getPlupddate(),
//				vo.getMarketdate(),vo.getIcpcode(),vo.getIcpservid(),vo.getCompanyid(),vo.getProductID(),vo.getCompanyname(),
//				vo.getChargeTime(),vo.getCityid(),vo.getAppCateName(),vo.getAppCateID(),vo.getIntroduction(),vo.getWWWPropaPicture1(),vo.getWWWPropaPicture2(),
//				vo.getWWWPropaPicture3(),vo.getProvider(),vo.getLanguage(),vo.getLogo1(),vo.getLogo2(),
//				vo.getLogo3(),vo.getLogo4(),vo.getLogo5(),vo.getOnlinetype(),vo.getVersion(),vo.getPicture1(),
//				vo.getPicture2(),vo.getPicture3(),vo.getPicture4(),vo.getContentid()
//		                 };
//		DB.getInstance().executeBySQLCode(sqlCode, paras);
//	}

//	public String insertGContent(CmContentVO vo)  throws DAOException {
//		
//		if (LOG.isDebugEnabled()) {
//			LOG.debug("insertGContentByDataCenterCmContentVo(" + vo + " )");
//		}
//		String id=new NodePersistencyDB(null).allocateNewNodeID();
//		
//		String sqlCode = "SyncAndroid.APPInfoDAO.insertGContentByDataCenterCmContentVo().INSERT";
//		Object[] paras = {id,vo.getCateName(),vo.getName(),vo.getContentCode(),vo.getKeywords(),vo.getServAttr(),vo.getCreateDate(),
//							vo.getLupddate(),vo.getPlupddate(),vo.getMarketdate(),vo.getIcpcode(),vo.getIcpservid(),vo.getCompanyid(),vo.getProductID(),vo.getCompanyname(),
//							vo.getChargeTime(),vo.getPvcid(),vo.getCityid(),vo.getAppCateName(),vo.getAppCateID(),vo.getIntroduction(),vo.getWWWPropaPicture1(),vo.getWWWPropaPicture2(),vo.getWWWPropaPicture3(),
//							vo.getProvider(),vo.getLanguage(),vo.getLogo1(),vo.getLogo2(),vo.getLogo3(),vo.getLogo4(),vo.getLogo5(),vo.getOnlinetype(),vo.getVersion(),vo.getPicture1(),vo.getPicture2(),vo.getPicture3(),vo.getPicture4(),vo.getContentid()
//		                 };
//		
//		try{
//		Object[] paras_base = {id,RepositoryConstants.ROOT_CONTENT_ID,"{100}.{702}.{"+id+"}",vo.getTypename()};
//		TransactionDB tdb = this.getTransactionDB();
//		
//		//inserBaseSql=insert into T_R_BASE (ID, PARENTID, PATH, TYPE)values (?, ?, ?, ?)
//		tdb.executeBySQLCode("inserBaseSql", paras_base);
//		tdb.executeBySQLCode(sqlCode, paras);
//		
//		tdb.commit();
//		}catch(DAOException e){
//			try {
//				LOG.error("出错的执行语句："+getDebugSql(SQLCode.getInstance().getSQLStatement(sqlCode),paras));
//			} catch (DataAccessException e1) {
//				// TODO Auto-generated catch block
//				LOG.error(e1);
//			}
//			throw new DAOException(e);
//		}
//		
//		return id;
//	}
	
//	private  String getDebugSql(String sql,Object[] para){
//		if(para!=null){
//			for(int i=0;i<para.length;i++){
//				sql = sql.replaceFirst("\\?", "'"+para[i]+"'");
//			}
//		}
//		return sql;
//	}

//	public void updateDeviceResourseByCid(String cid) throws DAOException {
//		// TODO Auto-generated method stub
//		String sqlCodeInsert = "SyncAndroid.APPInfoDAO.updateDeviceResourseByCid().INSERT";
//		String sqlCodeDel = "SyncAndroid.APPInfoDAO.updateDeviceResourseByCid().DEL";
//		TransactionDB tdb = null;
//		try{
//			tdb = this.getTransactionDB();
//			Object[] paras = {cid};
//			tdb.executeBySQLCode(sqlCodeDel, paras);
//			tdb.executeBySQLCode(sqlCodeInsert, paras);
//			tdb.commit();
//		}finally{
//			if(tdb!=null){
//				tdb.close();
//			}
//		}
//	}

//	public boolean isExistsPPMS(String contentid){
//		// TODO Auto-generated method stub
//		String sqlCode = "SyncAndroid.APPInfoDAO.isExistsPPMS().SELECT";
//		ResultSet rs = null;
//		Object[] paras = { contentid };
//		try {
//			rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
//			if (rs != null && rs.next()) {
//				return true;
//			}
//		} catch (Exception ex) {
//			//throw new DAOException("isExistsPPMS error!!", ex);
//			LOG.error(ex);
//		} finally {
//			DB.close(rs);
//		}
//
//		return false;
//	}


	//是把T_A_PPMS_RECEIVE的一条电子流的消息转成T_A_PPMS_RECEIVE_change
	public void putPPMSReceive(APPInfoVO vo) throws DAOException{
		// TODO Auto-generated method stub
		//System.out.println("hhe....."+vo);
		String sqlCode1 = "SyncAndroid.APPInfoDAO.putPPMSReceive().INSERT";
		String sqlCode2 = "SyncAndroid.APPInfoDAO.putPPMSReceive().UPDATE";
		String sqlCode3 = "SyncAndroid.APPInfoDAO.putPPMSReceive().DELETE";
		String sqlCode4 = "SyncAndroid.APPInfoDAO.putPPMSReceive().INSERT2";

		TransactionDB tdb = null;
//		TransactionDB td = null;

		try{
			String ids = vo.getContentID();
			if(ids!=null&&ids.split(",").length>0){
				tdb = this.getTransactionDB();
				String[] id = ids.split(",");
				//TransactionID,type,entityid
				for(int i=0;i<id.length;i++){
					if(id[i].contains(":")){
						String a[] = id[i].split(":");
						
						Object[] paras = {a[1],vo.getTransactionID(),vo.getType(),a[0],Constant.MESSAGE_HANDLE_STATUS_INIT};
						tdb.executeBySQLCode(sqlCode1, paras);
						
						Object[] paras2 = {a[0]};
                         //如果状态为1，则删除和向T_r_content_pic插入数据
//		                td = TransactionDB.getTransactionInstance();// 一个新的JDBC事务开始了。

						if("1".equals(a[1])){
							//删除和向T_r_content_pic插入数据
							tdb.executeBySQLCode(sqlCode3, paras2);
							tdb.executeBySQLCode(sqlCode4, paras2);

//			                PPMSDAO dao = PPMSDAO.getTransactionInstance(td);
//			                //调用应用数据变更通知接口
//		                    dao.addMessages(MSGType.ContentModifyReq, null,a[0] + ":4");
						}
//						td.commit();
					}else{
					
					Object[] paras = {"0",vo.getTransactionID(),vo.getType(),id[i],Constant.MESSAGE_HANDLE_STATUS_INIT};
					tdb.executeBySQLCode(sqlCode1, paras);
				    }
					}
				tdb.executeBySQLCode(sqlCode2, new Object[]{Constant.MESSAGE_HANDLE_STATUS_SUCC,vo.getTransactionID()});
				
				tdb.commit();
			}

		} catch (Exception e) {
			
			LOG.error(e);
			throw new DAOException(vo+"从T_A_PPMS_RECEIVE的一条电子流的消息转成T_A_PPMS_RECEIVE_change失败！");
		}finally{
			if(tdb!=null){
				tdb.close();
			}
		}}

	public List<ReceiveChangeVO> getReceiveChangeList(int status,int limit,String contentType) throws DAOException {
		// TODO Auto-generated method stub
		String sqlCode = "SyncAndroid.APPInfoDAO.getReceiveChangeList().SELECT";
		ResultSet rs = null;
		Object[] paras = {status,contentType,limit};
		List<ReceiveChangeVO> receiveChangeList = new ArrayList<ReceiveChangeVO>();
		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
			while (rs != null && rs.next()) {
				ReceiveChangeVO vo = new ReceiveChangeVO();
				vo.setId(rs.getString("id"));
				vo.setType(rs.getString("type"));
				vo.setStatus(rs.getString("status"));
				vo.setEntityid(rs.getString("entityid"));
				vo.setImagetype(rs.getString("imagetype"));
				// 2018-09-11 表里新增opt和appid字段
				vo.setOpt(rs.getString("opt"));
				vo.setAppid(rs.getString("appid"));
				receiveChangeList.add(vo);
			}
		} catch (Exception ex) {
			throw new DAOException("getMessageList error!!", ex);
		} finally {
			DB.close(rs);
		}
		return receiveChangeList;
	}

	public int getLeftContentNum30And33() {
		// TODO Auto-generated method stub
		String sqlCode = "SyncAndroid.APPInfoDAO.getLeftContentNum30And33";
		ResultSet rs = null;
		int num = 0;
		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode,null);
			if(rs.next()){
				 num = rs.getInt(1);
				LOG.debug("getLeftContentNum:"+num);
			}
		} catch (Exception ex) {
			LOG.error("getLeftContentNum:",ex);
		} finally {
			DB.close(rs);
		}
		return num;
	}	
	
	public int getLeftContentNum33And33WithAppid() {
		// TODO Auto-generated method stub
		String sqlCode = "SyncAndroid.APPInfoDAO.getLeftContentNum33And33WithAppid";
		ResultSet rs = null;
		int num = 0;
		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode,null);
			if(rs.next()){
				 num = rs.getInt(1);
				LOG.debug("getLeftContentNum:"+num);
			}
		} catch (Exception ex) {
			LOG.error("getLeftContentNum:",ex);
		} finally {
			DB.close(rs);
		}
		return num;
	}	
	
	/**
	 * 获取运营标签
	 * 
	 * @param contentid
	 * @return
	 * @throws DAOException
	 */
	private String getLimitOPTagByContentID(String contentid)
			throws DAOException {
		StringBuffer sb = new StringBuffer("");
		if (contentid != null && contentid.length() > 0) {
			String querySQLCode = "DataSyncDAO.getLimitOPTagByContentID().SELECT";
			Object[] paras = { contentid};
			ResultSet rs = null;
			try {
				rs = DB.getInstance().queryBySQLCode(querySQLCode, paras);
				// sb = new StringBuffer("");
				while (rs.next()) {
					String tagName = rs.getString(1);
					if(tagName != null && !"".equals(tagName)){
						sb.append(tagName + ";");
					}
				}
			} catch (SQLException ex) {
				LOG.error("获取标签数据库操作失败," + ex);
				throw new DAOException(ex);
			} finally {
				DB.close(rs);
			}
		}
		return sb.toString();
	}
	
	/**
	 * 获取应用标签
	 * 
	 * @param contentid
	 * @return
	 * @throws DAOException
	 */
	private String getLimitAPTagByContentID(String contentid)
			throws DAOException {
		StringBuffer sb = new StringBuffer("");
		if (contentid != null && contentid.length() > 0) {
			String querySQLCode = "DataSyncDAO.getLimitAPTagByContentID().SELECT";
			Object[] paras = {contentid};
			ResultSet rs = null;
			try {
				rs = DB.getInstance().queryBySQLCode(querySQLCode, paras);
				// sb = new StringBuffer("");
				while (rs.next()) {
					String tagName = rs.getString(1);
					if(tagName != null && !"".equals(tagName)){
					         sb.append(tagName + ";");
					}
				}
			} catch (SQLException ex) {
				LOG.error("获取标签数据库操作失败," + ex);
				throw new DAOException(ex);
			} finally {
				DB.close(rs);
			}
		}
		return sb.toString();
	}
	
	public List<String> getAppidsNotNull(){
		List<String> appids = new ArrayList<String>();
		String sqlCode = "SyncAndroid.APPInfoDAO.getAppidsNotNull.SELECT";
		ResultSet rs = null;
		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode,null);
			while(rs.next()){
				appids.add(rs.getString("appid"));
			}
		} catch (Exception e) {
			LOG.error("getProHandleVO:",e);
		} finally {
			DB.close(rs);
		}
		return appids ;
	}
	public List<ReceiveChangeVO > getProHandleVO(String appid){
		ReceiveChangeVO vo ;
		List<ReceiveChangeVO> vos = new ArrayList<ReceiveChangeVO>();
		String sqlCode = "SyncAndroid.APPInfoDAO.getProHandleVO.SELECT";
		ResultSet rs = null;
		Object[] paras = {appid};
		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode,paras);
			while(rs.next()){
				vo = new ReceiveChangeVO();
				vo.setId(rs.getString("id"));
				vo.setType(rs.getString("type"));
				vo.setStatus(rs.getString("status"));
				vo.setEntityid(rs.getString("entityid"));
				vo.setImagetype(rs.getString("imagetype"));
				vo.setOpt(rs.getString("opt"));
				vo.setAppid(rs.getString("appid"));
				vos.add(vo);
			}
		} catch (Exception ex) {
			LOG.error("getProHandleVO:",ex);
		} finally {
			DB.close(rs);
		}
		return vos;
	}
	
}

