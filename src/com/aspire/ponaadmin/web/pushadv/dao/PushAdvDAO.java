package com.aspire.ponaadmin.web.pushadv.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.db.SQLUtil;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.DataAccessException;
import com.aspire.common.persistence.util.SQLCode;
import com.aspire.common.util.StringUtils;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.page.PageVOInterface;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.pushadv.vo.ContentVO;
import com.aspire.ponaadmin.web.pushadv.vo.PushAdvVO;

public class PushAdvDAO {

	/**
	 * 记录日志的实例对象
	 */
	protected static JLogger logger = LoggerFactory.getLogger(PushAdvDAO.class);

	/**
	 * singleton模式的实例
	 */
	private static PushAdvDAO instance = new PushAdvDAO();

	/**
	 * 构造方法，由singleton模式调用
	 */
	private PushAdvDAO() {
	}

	/**
	 * 获取实例
	 * 
	 * @return 实例
	 */
	public static PushAdvDAO getInstance() {
		return instance;
	}

	/**
	 * 用于查询应用推送列表
	 * 
	 * @param page
	 * @param vo
	 * @throws DAOException
	 */
	public void queryPushAdvList(PageResult page, PushAdvVO vo)
			throws DAOException {

		if (logger.isDebugEnabled()) {
			logger.debug("queryPushAdvList() is starting ...");
		}

		
		String sqlCode = "";
		String sql = null;
		try {
		   if ("0".equals(vo.getType())) {
				// select
				// p.id,p.contentid,g.name,p.title,p.subtitle,p.starttime,p.endtime
				// from t_content_push_adv p,t_r_gcontent g where p.contentid =
				// g.contentid
				sqlCode = "com.aspire.ponaadmin.web.pushadv.dao.PushAdvDAO.queryPushAdvList.SELECT0";
				sql = SQLCode.getInstance().getSQLStatement(sqlCode);
			}

			if ("1".equals(vo.getType())) {
				sqlCode = "com.aspire.ponaadmin.web.pushadv.dao.PushAdvDAO.queryPushAdvList.SELECT1";
				sql = SQLCode.getInstance().getSQLStatement(sqlCode);
			}

			if ("2".equals(vo.getType())) {
				sqlCode = "com.aspire.ponaadmin.web.pushadv.dao.PushAdvDAO.queryPushAdvList.SELECT2";
				sql = SQLCode.getInstance().getSQLStatement(sqlCode);
			}
			if ("3".equals(vo.getType())) {
				sqlCode = "com.aspire.ponaadmin.web.pushadv.dao.PushAdvDAO.queryPushAdvList.SELECT3";
				sql = SQLCode.getInstance().getSQLStatement(sqlCode);
			}
			
			//sql = SQLCode.getInstance().getSQLStatement(sqlCode);
			
			if ("-1".equals(vo.getType()) || "".equals(vo.getType())) {
				sqlCode = "com.aspire.ponaadmin.web.pushadv.dao.PushAdvDAO.queryPushAdvList.SELECT0";
				String sql0 = SQLCode.getInstance().getSQLStatement(sqlCode);
				sqlCode = "com.aspire.ponaadmin.web.pushadv.dao.PushAdvDAO.queryPushAdvList.SELECT1";
				String sql1 = SQLCode.getInstance().getSQLStatement(sqlCode);
				sqlCode = "com.aspire.ponaadmin.web.pushadv.dao.PushAdvDAO.queryPushAdvList.SELECT2";
				String sql2 = SQLCode.getInstance().getSQLStatement(sqlCode);
				sqlCode = "com.aspire.ponaadmin.web.pushadv.dao.PushAdvDAO.queryPushAdvList.SELECT3";
				String sql3 = SQLCode.getInstance().getSQLStatement(sqlCode);
				StringBuffer sb = new StringBuffer();
				sb.append(sql0).append(" union ").append(sql1).append(" union ").append(sql2).append(" union ").append(sql3);
				sql = "select * from ("+sb.toString()+") p where 1=1 ";
			}


			StringBuffer sqlBuffer = new StringBuffer(sql);
			List paras = new ArrayList();
			// 构造搜索的sql和参数

			if (!"".equals(vo.getContentId())) {
				// sql += " and t.contentId ='" + vo.getContentId() + "'";
				sqlBuffer.append(" and p.contentid =? ");
				paras.add(vo.getContentId());
			}

			if (!"".equals(vo.getStartTime())) {
				// sql += " and t.starttime ='" + vo.getStartTime() + "'";
				sqlBuffer.append(" and substr(p.starttime,1,10) = ? ");
				paras.add(vo.getStartTime());
			}

			if (!"".equals(vo.getEndTime())) {
				// sql += " and t.endtime ='" + vo.getEndTime() + "'";
				sqlBuffer.append(" and substr(p.endtime,1,10) = ? ");
				paras.add(vo.getEndTime());
			}

			

			// sql += " order by p.createtime asc";
			sqlBuffer.append(" order by p.createtime asc");

			page.excute(sqlBuffer.toString(), paras.toArray(),
					new PushAdvPageVO());
		} catch (DataAccessException e) {
			throw new DAOException(
					"从sql.properties中获取sql语句时发生错误，对应sqlCode不存在。", e);
		}
	}

	/**
	 * 用于查询应用列表
	 * 
	 * @param page
	 * @param contentId
	 * @param contentName
	 * @throws DAOException
	 */
	public void queryContentNoInPushList(PageResult page, String contentId,
			String contentName, String channelsid) throws DAOException {

		if (logger.isDebugEnabled()) {
			logger.debug("queryContentNoInPushList() is starting ...");
		}

		// select t.contentid, t.name, t.spname,t.catename, t.marketdate from
		// t_r_gcontent t ,
		// (select icpcode from t_open_channels_category where channelsid = ?
		// and icpcode is not null and rownum =1) i
		// where t.icpcode = i.icpcode and t.contentid not in (select contentid
		// from t_content_push_adv ) and t.provider = 'O'
		String sqlCode = "com.aspire.ponaadmin.web.pushadv.dao.PushAdvDAO.queryContentNoInPushList.SELECT";
		String sql = null;

		try {
			sql = SQLCode.getInstance().getSQLStatement(sqlCode);

			StringBuffer sqlBuffer = new StringBuffer(sql);
			List paras = new ArrayList();
			// 构造搜索的sql和参数
			paras.add(channelsid);

			if (!"".equals(contentId)) {
				// sql += " and t.contentid ='" + contentId + "'";
				sqlBuffer.append(" and t.contentid =? ");
				paras.add(contentId);
			}
			if (!"".equals(contentName)) {
				// sql += " and t.name like('%"
				// + contentName + "%')";
				sqlBuffer.append(" and t.name like ? ");
				paras.add("%" + SQLUtil.escape(contentName) + "%");
			}

			// sql += " order by t.marketdate asc";
			sqlBuffer.append(" order by t.marketdate asc");

			page.excute(sqlBuffer.toString(), paras.toArray(),
					new ContentPageVO());
		} catch (DataAccessException e) {
			throw new DAOException(
					"从sql.properties中获取sql语句时发生错误，对应sqlCode不存在。", e);
		}
	}

	/**
	 * 保存新增应用推送广告
	 * 
	 * @param PushAdvVO
	 *            pushadv
	 */
	public void save(PushAdvVO pushadv,String style) throws DAOException {

		if (logger.isDebugEnabled()) {
			logger.debug("PushAdvDAO.save() is starting ...");
		}
		
		if("edit".equals(style)){
			TransactionDB transactionDB = TransactionDB.getInstance();
			StringBuffer sql = new StringBuffer();
			sql.append("update t_content_push_adv set " );
			
			if(!StringUtils.isEmpty(pushadv.getTitle())){
				sql.append(" title =  " + " '"+  pushadv.getTitle()+ "'");
			}
			if(!StringUtils.isEmpty(pushadv.getSubTitle())){
				sql.append(" ,subtitle =  " +" '"+pushadv.getSubTitle()+" '");
			}
			if(!StringUtils.isEmpty(pushadv.getStartTime())){
				sql.append(" ,starttime =  " +" '"+pushadv.getStartTime()+ "'");
			}
			if(!StringUtils.isEmpty(pushadv.getEndTime())){
				sql.append(" ,endtime =  " +" '"+pushadv.getEndTime()+ "'");
			}
			if(!StringUtils.isEmpty(pushadv.getRebrand())){
				sql.append(" ,rebrand =  " +" '"+pushadv.getRebrand()+ "'");
			}
			if(!StringUtils.isEmpty(pushadv.getRedevice())){
				sql.append(" ,redevice =  " +" '"+pushadv.getRedevice()+ "'");
			}
			if(!StringUtils.isEmpty(pushadv.getType())){
				sql.append(" ,type =  " +" '"+pushadv.getType()+ "'");
			}
			if(!StringUtils.isEmpty(pushadv.getUri())){
				sql.append(" ,uri =  " +" '"+pushadv.getUri()+ "'");
			}
			if(!StringUtils.isEmpty(pushadv.getVersions())){
				sql.append(" ,versionname =  " +" '"+pushadv.getVersions()+ "'");
			}
			sql.append(",lupdate=sysdate");
			
			if(!StringUtils.isEmpty(pushadv.getContentId())){
				sql.append(" ,contentid =  " +" '"+pushadv.getContentId()+ "' ,");
			}
			
			if(StringUtils.isEmpty(pushadv.getPicUrl())){
				sql.append(" where id = ?");
			}else{ 
				sql.append(" push_pic = " + " '"+pushadv.getPicUrl()+ "'" +" where id = ?");
			}
			try {
				//DB.getInstance().execute(sql.toString(), new Object[] { pushadv.getId()});
				transactionDB.execute(sql.toString(), new String[] { pushadv.getId()});
				
				transactionDB.commit();
			} catch (Exception e) {
				e.printStackTrace();
				throw new DAOException("保存编辑应用推送广告时发生异常:", e);
			}
			
		} else {
			//新增
			// insert into
			// t_content_push_adv
			// (id,contentid,title,subtitle,starttime,endtime,rebrand,createtime,redevice,type,uri,push_pic,versionname)
			// values(SEQ_T_CONTENT_PUSH_ADV_ID.NEXTVAL,?,?,?,?,?,?,sysdate,?,?,?,?,?))
			String sqlCode = "com.aspire.ponaadmin.web.pushadv.dao.PushAdvDAO.save.insert";
			try {
				DB.getInstance().executeBySQLCode(
						sqlCode,
						new Object[] {pushadv.getId(), pushadv.getContentId(), pushadv.getTitle(),
								pushadv.getSubTitle(), pushadv.getStartTime(),
								pushadv.getEndTime(), pushadv.getRebrand(),
								pushadv.getRedevice(), pushadv.getType(),
								pushadv.getUri(), pushadv.getPicUrl(),
								pushadv.getVersions() });
			} catch (DAOException e) {
				throw new DAOException("保存新增应用推送广告时发生异常:", e);
			}
		}
		
		
		
	}

	/**
	 * 用于删除指定应用推送广告
	 * 
	 * @param id
	 * @param contentId
	 * @throws DAOException
	 */
	public void delByContentId(String id) throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("delByContentId() is starting ...");
		}

		// delete from t_content_push_adv c where c.id = ? 
		String sqlCode = "com.aspire.ponaadmin.web.pushadv.dao.PushAdvDAO.delByContentId.delete";
		String sqlCode1 = "com.aspire.ponaadmin.web.pushadv.dao.PushAdvDAO.delByContentId.delete1";

		try {
			DB.getInstance().executeBySQLCode(sqlCode1 , new Object[] { id });
			DB.getInstance().executeBySQLCode(sqlCode , new Object[] { id });
		} catch (DAOException e) {
			throw new DAOException("删除指定TAC码时发生异常:", e);
		}
	}

	/**
	 * 根据应用id查看是否已存在推送
	 * 
	 * @param contentId
	 * @throws DAOException
	 */
	public boolean isHasPushByContentId(String contentId ) throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("isHasPushByContentId() is starting ...");
		}

		boolean isHas = false;
		
	    // select c.contentid from t_content_push_adv c where c.contentid =?
		 String	sqlCode = "com.aspire.ponaadmin.web.pushadv.dao.PushAdvDAO.isHasPushByContentId.select";
		
		
		 

		ResultSet rs = null;

		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode,
					new Object[] { contentId });
			while (rs.next()) {
				isHas = true;
			}
		} catch (SQLException e) {
			throw new DAOException("根据应用id查看是否已存在推送时出错:", e);
		} catch (DAOException ex) {
			throw new DAOException("根据应用id查看是否已存在推送时出错:", ex);
		} finally {
			DB.close(rs);
		}
		return isHas;
	}

	/**
	 * 查看应用id在内容表里是否存在
	 * 
	 * @param contentId
	 * @throws DAOException
	 */
	public boolean isHasByContentId(String contentId ,String type ) throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("isHasPushByContentId() is starting ...");
		}
		String sqlCode = "";
		boolean isHas = false;
		
		if("0".equals(type)){
			// select c.contentid from t_r_gcontent c where c.contentid =?
			sqlCode = "com.aspire.ponaadmin.web.pushadv.dao.PushAdvDAO.isHasByContentId.select";

		}
		if("1".equals(type)){
			//select t.articleid from t_mediazation_article t where t.articleid = ? and t.type='4'
			sqlCode = "com.aspire.ponaadmin.web.pushadv.dao.PushAdvDAO.isHasByArticleId.select";

		}
//		if("2".equals(type)){
//			// select f.suspendingid from t_d_float_window f where f.suspendingid = ? and f.type='3'
//			sqlCode = "com.aspire.ponaadmin.web.pushadv.dao.PushAdvDAO.isHasByFloadId.select";
//
//		}
		
		if("2".equals(type)){
			return true ;
		}
		
		
		
		
		ResultSet rs = null;

		//
		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode,
					new Object[] { contentId });
			while (rs.next()) {
				isHas = true;
			}
		} catch (SQLException e) {
			throw new DAOException("查看应用id在内容表里是否存在时出错:", e);
		} catch (DAOException ex) {
			throw new DAOException("查看应用id在内容表里是否存在时出错:", ex);
		} finally {
			DB.close(rs);
		}
		return isHas;
	}

	public List<String> getDevice(String brand) throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("getDevice() is starting ...");
		}
		String sqlCode = "com.aspire.ponaadmin.web.pushadv.dao.PushAdvDAO.getDevice.select";
		List<String> list = new ArrayList<String>();
		ResultSet rs = null;
		Object[] paras = { brand };
		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
			while (rs.next()) {
				list.add(rs.getString("device"));
			}
		} catch (SQLException e) {
			throw new DAOException("获得全部品牌时出错:", e);
		} catch (DAOException ex) {
			throw new DAOException("获得全部品牌时出错:", ex);
		} finally {
			DB.close(rs);
		}
		return list;

	}

	/**
	 * 获得全部品牌
	 * 
	 * @throws DAOException
	 */
	public List<String> getAllBrand() throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("getAllBrand() is starting ...");
		}

		boolean isHas = false;
		// select distinct(t.brand) from t_tac_code_base t
		String sqlCode = "com.aspire.ponaadmin.web.pushadv.dao.PushAdvDAO.getAllBrand.select";
		List<String> list = new ArrayList<String>();
		ResultSet rs = null;

		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode, null);
			while (rs.next()) {
				list.add(rs.getString("brand"));
			}
		} catch (SQLException e) {
			throw new DAOException("获得全部品牌时出错:", e);
		} catch (DAOException ex) {
			throw new DAOException("获得全部品牌时出错:", ex);
		} finally {
			DB.close(rs);
		}
		return list;
	}

	/**
	 * 根据型号查询手机品牌
	 */
	public String getBrand(String redevice) throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("getBrand() is starting ...");
		}

		boolean isHas = false;
		// select t.brand from t_tac_code_base t where t.device=?
		String sqlCode = "com.aspire.ponaadmin.web.pushadv.dao.PushAdvDAO.getBrand.select";
		String brand = "";
		Object[] paras = { redevice };
		ResultSet rs = null;

		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
			while (rs.next()) {
				brand = rs.getString("brand");
			}
		} catch (SQLException e) {
			throw new DAOException("获得全部型号时出错:", e);
		} catch (DAOException ex) {
			throw new DAOException("获得全部型号时出错:", ex);
		} finally {
			DB.close(rs);
		}
		return brand;
	}

	/**
	 * 应用类分页读取VO的实现类
	 */
	private class PushAdvPageVO implements PageVOInterface {
		public void CopyValFromResultSet(Object content, ResultSet rs)
				throws SQLException {
			PushAdvVO vo = (PushAdvVO) content;
			vo.setId(rs.getString("id"));
			vo.setContentId(rs.getString("contentid"));
			vo.setType(rs.getString("type"));
			vo.setTitle(rs.getString("title"));
			vo.setSubTitle(rs.getString("subtitle"));
			vo.setStartTime(rs.getString("starttime"));
			vo.setEndTime(rs.getString("endtime"));
		}

		public Object createObject() {
			return new PushAdvVO();
		}
	}

	/**
	 * 应用类分页读取VO的实现类
	 */
	private class ContentPageVO implements PageVOInterface {
		public void CopyValFromResultSet(Object content, ResultSet rs)
				throws SQLException {
			ContentVO vo = (ContentVO) content;
			vo.setContentId(rs.getString("contentid"));
			vo.setName(rs.getString("name"));
			vo.setSpName(rs.getString("spname"));
			vo.setCatename(rs.getString("catename"));
			vo.setMarketDate(rs.getString("marketdate"));
		}

		public Object createObject() {
			return new ContentVO();
		}
	}

	public List<String> getVersion() throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("getVersion() is starting ...");
		}
		List<String> versions = new ArrayList<String>();
		// select t.verified_version from appname_verified_version t where
		// t.verified_version like 'TMMHelper%'
		String sqlCode = "com.aspire.ponaadmin.web.pushadv.dao.PushAdvDAO.getVersion.select";
		String version = "";
		ResultSet rs = null;

		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode, null);
			while (rs.next()) {
				version = rs.getString("verified_version");
				versions.add(version);
			}
		} catch (SQLException e) {
			throw new DAOException("获取必备版本号出错", e);
		} catch (DAOException ex) {
			throw new DAOException("获取必备版本号出错", ex);
		} finally {
			DB.close(rs);
		}
		return versions;
	}
	
	
	public PushAdvVO getVoBycontentid(String id){
		//select * from t_content_push_adv where id = ?
		String sqlCode = "com.aspire.ponaadmin.web.pushadv.dao.PushAdvDAO.getVoBycontentid.select";
		ResultSet rs = null;
		Object[] paras = { id};
		PushAdvVO pav= null;
		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
			while(rs.next()){
				pav=new PushAdvVO();
				pav.setId(rs.getString("id"));
				pav.setContentId(rs.getString("contentid"));
				pav.setTitle(rs.getString("title"));
				pav.setSubTitle(rs.getString("subtitle"));
				pav.setRebrand(rs.getString("rebrand"));
				pav.setRedevice(rs.getString("redevice"));
				pav.setType(rs.getString("type"));
				pav.setUri(rs.getString("uri"));
				pav.setPicUrl(rs.getString("push_pic"));
				pav.setVersions(rs.getString("versionname"));
				pav.setStartTime(rs.getString("starttime"));
				pav.setEndTime(rs.getString("endtime"));
			}
			
		} catch (Exception e) {
			logger.error(e);
		}
		return pav;
		
	}
	
	public List<String> getHandleDevice(String brand,String devices) throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("getDevice() is starting ...");
		}
		String[] handleDevices = devices.split("\\|");
		String sqlCode = "com.aspire.ponaadmin.web.pushadv.dao.PushAdvDAO.getDevice.select";
		List<String> list = new ArrayList<String>();
		ResultSet rs = null;
		Object[] paras = { brand };
		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
			while (rs.next()) {
				String device = rs.getString("device");
				for(int i =0 ; i< handleDevices.length ; i++ ){
					if( !(device.equals(handleDevices[i]))){
						list.add(device);
					}
				}
				
			}
		} catch (SQLException e) {
			throw new DAOException("获得全部品牌时出错:", e);
		} catch (DAOException ex) {
			throw new DAOException("获得全部品牌时出错:", ex);
		} finally {
			DB.close(rs);
		}
		return list;

	}
	/**
	 * 查询系列
	 * @return
	 * @throws Exception 
	 */
	public int sequences() throws Exception{

		if (logger.isDebugEnabled()) {
			logger.debug("sequences() is starting ...");
		}
		int sequences =0;
		// select t.verified_version from appname_verified_version t where
		// t.verified_version like 'TMMHelper%'
		String sqlCode = "com.aspire.ponaadmin.web.pushadv.dao.PushAdvDAO.getVersion.sequences";
		String version = "";
		ResultSet rs = null;

		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode, null);
			while (rs.next()) {
				version = rs.getString("NEXTVAL");
				sequences=Integer.parseInt(version);
			}
		} catch (Exception e) {
			throw new Exception("获取必备版本号出错", e);
		}  finally {
			DB.close(rs);
		}
		return sequences;
	
	}
	public void addMsisdn(List<String> list, String id)
    {

        String sql = "insert into T_Push_MSISDN(id,msisdn) values(?,?)";
        for (int i = 0; i < list.size(); i++)
        {
            if (null != list.get(i) && !"".equals(list.get(i)))
            {
                Object[] paras = new Object[] { id, list.get(i) };
                try
                {
                    DB.getInstance().execute(sql, paras);
                }
                catch (Exception e)
                {
                    logger.error(e);
                }
            }
        }
    }
	public void addMsisdn(String id)
    {

        String sql = "insert into T_Push_MSISDN(id,msisdn) values(?,?)";
        Object[] paras = new Object[] { id, "all" };
        try
        {
            DB.getInstance().execute(sql, paras);
        }
        catch (Exception e)
        {
            logger.error(e);
        }
    }
}
