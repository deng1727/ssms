/**
 * SSMS
 * com.aspire.dotcard.synczcom.dao ZcomDataSyncDAO.java
 * Apr 8, 2010
 * @author tungke
 * @version 1.0
 *
 */
package com.aspire.dotcard.synczcom.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.gcontent.GContent;
import com.aspire.dotcard.gcontent.GContentFactory;
import com.aspire.dotcard.syncData.dao.DataSyncDAO;
import com.aspire.dotcard.syncData.vo.ContentTmp;
import com.aspire.dotcard.synczcom.vo.ZcomContentTmp;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.system.Config;
import com.aspire.ponaadmin.web.util.DateUtil;
import com.aspire.ponaadmin.web.util.PublicUtil;


/**
 * @author tungke
 *
 */
public class ZcomDataSyncDAO {

	/**
	 * 日志引用
	 */
	JLogger logger = LoggerFactory.getLogger(ZcomDataSyncDAO.class);

	private static ZcomDataSyncDAO dao = new ZcomDataSyncDAO();

	/**
	 * 待同步内容机型适配列表缓存。key 表示 contentid，value  表示 适配机型的列表
	 */
	private HashMap ContentDevicesCache;

	/**
	 * 待同步内容资费信息缓存。key 表示 contentid，value  表示 适配机型的列表
	 */
	private HashMap ContentFeeCache;

	/**
	 * MM终端机型列表
	 */
	private HashMap deviceMappingCache;

	private ZcomDataSyncDAO() {

	}

	/**
	 * 单例模式
	 * 
	 * @return
	 */
	public static ZcomDataSyncDAO getInstance() {

		return dao;
	}

	/**
	 * 支持事务的数据库操作器，如果为空表示是非事务类型的操作
	 */
	private TransactionDB transactionDB;

	/**
	 * 获取事务类型TransactionDB的实例 如果已经指定了，用已经指定的。如果没有指定，自己创建一个，注意自己创建的直接用不支持事务类型的即可
	 * 
	 * @return TransactionDB
	 */
	private TransactionDB getTransactionDB() {

		if (this.transactionDB != null) {
			return this.transactionDB;
		}
		return TransactionDB.getInstance();
	}

	/**
	 * 获取事务类型的DAO实例
	 * 
	 * @return AwardDAO
	 */

	public static ZcomDataSyncDAO getTransactionInstance(
			TransactionDB transactionDB) {

		ZcomDataSyncDAO dao = new ZcomDataSyncDAO();
		dao.transactionDB = transactionDB;
		return dao;
	}

	/**
	 * 获取删除的数据
	 */
	public List getDelCmsNotExistZcomContent() throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("getDelCmsNotExistZcomContent()");
		}
		// 根据t_synctime_tmp表中内容更新时间的升序查询得到查询结果；
		String sqlCode = "SyncData.ZcomDataSyncDAO.getDelCmsNotExistZcomContent().SELECT";
		ResultSet rs = DB.getInstance().queryBySQLCode(sqlCode, null);
		ZcomContentTmp tmp;
		List list = new ArrayList();
		// 遍历结果,将每条记录置入类ContentTmp的各个属性中
		try {
			while (rs.next()) {
				tmp = new ZcomContentTmp();
				this.getContentTmpByRS(rs, tmp);
				logger.error("删除了"+tmp.getContentId());
				tmp.setOptype("数据完整");
				list.add(tmp);
			}

		} catch (SQLException e) {
			throw new DAOException("获取删除的数据", e);
		} finally {
			DB.getInstance().close(rs);
		}
		// 将各个ContentTmp放入list中返回
		return list;
	}
	/**
	 * 删除PPMS中不存在的
	 */
	public int delCmsNotExistZcomContent() throws DAOException {
		//    	删除货架存在的，电子流不存在的数据
		String sqlCodeDel = "SyncData.ZcomDataSyncDAO.deleteSysTime().DELETE";
		int re = DB.getInstance().executeBySQLCode(sqlCodeDel, null);
		return re;
	}

	/**
	 * 插入本地数据库
	 * @param contentId
	 * @param parentmap
	 * @throws DAOException 
	 */
	public int insertIntoZcomPPs(ZcomContentTmp tmp, Hashtable parentmap)
			throws DAOException {

		Hashtable ht = this.getZcomContentFromCMS(tmp.getContentId(), parentmap);
		//检查合法性
		boolean checkzcom = this.checkZcomContent(ht,tmp);
		if (checkzcom) {
			String updatesql = "SyncData.ZcomDataSyncDAO.updateZcomContent().UPDATE";
			Object[] parasupdate = { ht.get("maga_name"),
					ht.get("maga_periods"), ht.get("maga_office"),
					ht.get("maga_date"), ht.get("period"), ht.get("price"),
					ht.get("chargetype"), ht.get("uptime"),
					ht.get("cartoonpicurl"), ht.get("logo1"), ht.get("logo2"),
					ht.get("logo3"), ht.get("logo4"), ht.get("icpcode"),
					ht.get("icpserid"), ht.get("parent_id"),
					ht.get("maga_full_name"), ht.get("platform"),
					ht.get("size"), ht.get("prifix"), ht.get("full_device_id"),
					ht.get("contentid") };
			logger.debug("parasupdate=" + parasupdate);
			int rsupdate = DB.getInstance().executeBySQLCode(updatesql,
					parasupdate);
			if (rsupdate <= 0) {//更新失败则插入
				String insertsql = "SyncData.ZcomDataSyncDAO.updateZcomContent().INSERT";
				TransactionDB tdb = this.getTransactionDB();
				tdb.executeBySQLCode(insertsql, parasupdate);
				return 1;
			}
			return 2;
		} else {
			logger.error("参数不合法，contentid=" + tmp.getContentId()+",ht="+ht);
			//参数不合法
			return -1;
		}
	}

	/**
	 * 插入成功后 删除临时表数据
	 * @param contentId
	 * @return
	 * @throws DAOException 
	 */
	public int delZcomContentTemp(String contentId) throws DAOException {
		String deltempsql = "SyncData.ZcomDataSyncDAO.delZcomContentTemp().DELETE";
		Object[] parasdel = { contentId };
		TransactionDB tdb = this.getTransactionDB();
		tdb.executeBySQLCode(deltempsql, parasdel);
		return 1;
	}

	/**
	 * 根据内容id和内容类型从cms中查询得到内容对象，如果找不到该内容，则抛出异常
	 * 
	 * @param ContentId,内容id
	 * @return
	 * @throws DAOException 发生数据库异常。或者找不到该内容
	 */
	public Hashtable getZcomContentFromCMS(String contentId, Hashtable parentmap)
			throws DAOException {
		Hashtable zcomContent = null;
		if (logger.isDebugEnabled()) {
			logger.debug("getZcomContentFromCMS(" + contentId + ")");
		}
		String sqlCode = "SyncData.ZcomDataSyncDAO.getZcomContentFromCMS().SELECT";
		Object[] paras = { contentId };
		ResultSet rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
		try {
			if (rs.next()) {
				zcomContent = this.getZcomContentByRS(rs, parentmap);
			} else {
				throw new NullPointerException("从CMS找不到该内容。contentId="
						+ contentId);
			}
		} catch (Exception e) {
			throw new DAOException(e);
		} finally {
			DB.close(rs);
		}
		return zcomContent;
	}

	/**
	 * 获取适配关系等其他信息
	 * @param contentid
	 * @return
	 * @throws DAOException 
	 */
	public Hashtable getDeviceAndOther(String contentid) throws DAOException {
		Hashtable result = null;
		
		String size = null;
		String prifix = null;
		String full_device_id = null;
		StringBuffer device_id = new StringBuffer("");
		// 查找适配关系表
		ResultSet rs3 = null;
		String sql1 = "SyncData.ZcomDataSyncDAO.getDeviceAndOther().SELECT";
		Object[] parastemp = { contentid };
		rs3 = DB.getInstance().queryBySQLCode(sql1, parastemp);
		try {
			result = new Hashtable();
			while (rs3.next()) {
				String device = "{" + rs3.getString(1) + "}";
				device_id.append(device);
				device_id.append(",");
				size = rs3.getString(3);
			
				String url = rs3.getString(4);
				if(url.indexOf("/")>0 && url.indexOf("/")<url.length()-1){
					prifix = url.substring(url.lastIndexOf("/") + 1, url.length());
				}
			}
			if (device_id.length() > 1) {
				full_device_id = device_id.substring(0, device_id.length() - 1);
			}
			result.put("full_device_id", this.getNotNullStr(full_device_id));
		
			result.put("prifix", this.getNotNullStr(prifix));
			result.put("size", this.getNotNullStr(size));

		} catch (SQLException e) {
			logger.error("查找适配关系表出错：contentid=" + contentid);
			e.printStackTrace();
		} finally {
			DB.close(rs3);
		}
		return result;
	}

	/**
	 * 获取zcomParentId
	 * 
	 * @param Name
	 * @return
	 * @throws DAOException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public Hashtable getZcomParentId() throws DAOException {
		Hashtable ht = new Hashtable();

		// 获取分类
		String sql = "SyncData.ZcomDataSyncDAO.getZcomParentId().SELECT";
		ResultSet rs;
		rs = DB.getInstance().queryBySQLCode(sql, null);
		try {

			while (rs.next()) {
				ht.put(rs.getString(2), new Integer(rs.getInt(1)));// name,pid,
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DAOException("查询zcom分类异常，" + e);
		} finally {
			DB.getInstance().close(rs);
		}
		return ht;
	}

	/**
	 * 根据内容最后更新时间升序排列查询得到需要同步的内容id,内容状态和内容最后更新时间列表。
	 * 
	 * @return list
	 */
	public List getZcomSyncContentTmp() throws DAOException {

		if (logger.isDebugEnabled()) {
			logger.debug("getZcomSyncContentTmp()");
		}
		// 根据t_synctime_tmp表中内容更新时间的升序查询得到查询结果；
		String sqlCode = "SyncData.ZcomDataSyncDAO.getZcomSyncContentTmp().SELECT";
		ResultSet rs = DB.getInstance().queryBySQLCode(sqlCode, null);
		ZcomContentTmp tmp;
		List list = new ArrayList();
		// 遍历结果,将每条记录置入类ContentTmp的各个属性中
		try {
			while (rs.next()) {
				tmp = new ZcomContentTmp();
				this.getContentTmpByRS(rs, tmp);
				list.add(tmp);
			}

		} catch (SQLException e) {
			throw new DAOException("读取临时表数据异常", e);
		} finally {
			DB.getInstance().close(rs);
		}
		// 将各个ContentTmp放入list中返回
		return list;
	}

	 public void syncVCmDeviceResource()throws BOException
	    {
	    	if (logger.isDebugEnabled())
	        {
	            logger.debug("同步表 V_CM_DEVICE_RESOURC");
	        }
	    	String tempTable="v_cm_device_resource"+PublicUtil.getCurDateTime("HHmmssSSS");//防止同时操作的情况。
	    	
	    	String createTempSql = "create table "+tempTable+" as select * from ppms_CM_DEVICE_RESOURCE p ";
	    	String dropTableSql="drop table v_cm_device_resource";
	    	String renameSql ="alter table "+tempTable+" rename to v_cm_device_resource";
	    	String createIndex = "create index INDEX_v_device_resource_cID on v_cm_device_resource (contentid)";//add by tungke 2010-03-01 add index
	    	String dropTempSql="drop table "+tempTable;  
	    	
	    	logger.debug("开始创建临时表 "+tempTable);
	        try
			{
				DB.getInstance().execute(createTempSql, null);
			} catch (DAOException e)
			{
				throw new BOException("创建临时表出错：" + tempTable, e);
			}

			logger.debug("开始删除表 v_cm_device_resource");
			try
			{
				DB.getInstance().execute(dropTableSql, null);
			} catch (DAOException e)
			{
				try
				{
					DB.getInstance().execute(dropTempSql, null);
				} catch (DAOException e1)
				{
					logger.error(e1);
				}
				throw new BOException("删除表v_cm_device_resource出错：" + tempTable, e);
			}

			logger.debug("开始更改表名："+tempTable+"为v_cm_device_resource,并添加索引");
			try
			{
				DB.getInstance().execute(renameSql, null);
				DB.getInstance().execute(createIndex, null);
			} catch (DAOException e)
			{
				try
				{
					DB.getInstance().execute(dropTempSql, null);// 需要删除历史表。
				} catch (DAOException e1)
				{
					logger.error(e1);
				}
				throw new BOException("更改临时表名出错：" + tempTable, e);
			} 
	     }
	/**
	 * 视图初始化为表
	 *
	 */
	public void initZcomContentViewToTable() throws DAOException {

		// 将视图创建为表
		String dropTable1Sql = "drop table v_cm_content_zcom";
		String createTemp1Sql = "create table v_cm_content_zcom as select * from PPMS_V_CM_CONTENT_ZCOM ";
		try {
			DB.getInstance().execute(dropTable1Sql, null);
		} catch (DAOException e) {
			throw new DAOException("drop表v_cm_content_zcom出错：" + dropTable1Sql,
					e);
		}
		
		try {
			DB.getInstance().execute(createTemp1Sql, null);
		} catch (DAOException e) {
			throw new DAOException("创建表v_cm_content_zcom出错：" + createTemp1Sql,
					e);
		}
		
		// 数据初始化完成

	}

	/**
	 * 将需要同步的内容列表并将内容id和内容最后更新时间插入到数据库t_zcom_syncContent_tmp中。
	 * 
	 * @param systime,当前系统的时间
	 * @param isFull
	 *            boolean 是否是全量同步，true 表示全量同步，false 表示增量同步
	 */
	public void addZcomContentTmp(long systime, boolean isFull)
			throws DAOException {

		if (logger.isDebugEnabled()) {
			logger.debug("insertSysTime(" + systime + ")");
		}
		String sqlCode;
		Object[] paras;
		ResultSet rs = null;
		try {
			if (isFull)// 全量同步
			{
				paras = null;
				// 将内容id和最后跟新时间插入到表t_zcom_syncContent_tmp中.
				sqlCode = "SyncData.ZcomDataSyncDAO.addContentZcom().FULL";
			} else// 增量同步
			{
				// 在表t_zcom_lastsynctime中查询得到上次系统启动时间；
				sqlCode = "SyncData.ZcomDataSyncDAO.addContentZcom().SELECT";
				rs = DB.getInstance().queryBySQLCode(sqlCode, null);
				// 如果该记录不存在证明是首次同步,则将CMS内容表中的所有内容状态为发布和过期的查询出来.
				if (rs.next()) {
					paras = new Object[1];
					paras[0] = new Timestamp(systime);
					// 将内容id和最后跟新时间插入到表t_syncContent_tmp中.
					sqlCode = "SyncData.ZcomDataSyncDAO.addContentZcom().ADD";
				} else// 查不到同步时间则使用全量同步。
				{
					logger.info("t_lastsynctime表中没有上次同步记录。本次同步执行全量同步！");
					paras = null;
					// 将内容id和最后跟新时间插入到表t_syncContent_zcom_tmp中.
					sqlCode = "SyncData.ZcomDataSyncDAO.addContentZcom().FULL";
				}
				// 如果有上次启动时间,则在CMS中取内容最后更新时间在上次启动时间和systime之间的数据

			}
			//TransactionDB tdb = this.getTransactionDB();
			DB.getInstance().executeBySQLCode(sqlCode, paras);

		} catch (SQLException e) {
			throw new DAOException("将需要同步的内容列表放入历史表出错", e);
		} finally {
			DB.close(rs);
		}
	}

	/**
	 * 将本次系统执行内容同步时间插入到表t_lastsynctime_zcom
	 * 
	 * @param Systime,本次系统执行内容同步的时间
	 */
	public void insertSysTime(long systime) throws DAOException {

		if (logger.isDebugEnabled()) {
			logger.debug("insertSysTime(" + systime + ")");
		}
		// 将systime时间插入表t_lastsynctime中。
		String sqlCode = "SyncData.ZcomDataSyncDAO.insertSysTime().INSERT";
		Timestamp ts = new Timestamp(systime);
		Object[] paras = { ts };
		TransactionDB tdb = this.getTransactionDB();
		tdb.executeBySQLCode(sqlCode, paras);
	}

	/**
	 * 将查询出来的结果集
	 * 
	 * @param rs
	 * @param tmp
	 * @throws SQLException
	 */
	private void getContentTmpByRS(ResultSet rs, ZcomContentTmp tmp)
			throws SQLException {

		tmp.setContentId(rs.getString("contentid"));
		tmp.setName(rs.getString("name"));
		tmp.setLupdDate(rs.getDate("LupdDate"));
	}

	/**
	 * 将查询出来的结果集
	 * 
	 * @param rs
	 * @param zcomContent
	 * @throws SQLException 
	 * @throws SQLException
	 * @throws DAOException 
	 */
	private Hashtable  getZcomContentByRS(ResultSet rs2,
			Hashtable parentmap) throws SQLException, DAOException

	{
		Hashtable zcomContent = null;
		String fname = rs2.getString(1);
		
			String maga_name = fname; // 刊物名称
			String maga_periods = "";
			if(fname.indexOf("-")> 0 && fname.indexOf("-")<(fname.length()-1)){
		 maga_name = fname.substring(0, fname.lastIndexOf("-")); // 刊物名称
		 maga_periods = fname.substring(fname.lastIndexOf("-") + 1, fname
				.length());// 刊物期数
		}
		String appdesc[] = rs2.getString(2).split("#");
		
			String maga_office = ""; // 刊社
			String maga_date = ""; // 出刊日期
			String period = ""; // 出刊周期
		if(appdesc.length == 3){
		 maga_office = appdesc[0]; // 刊社
		 maga_date = appdesc[1]; // 出刊日期
		 period = appdesc[2]; // 出刊周期
		}
		Integer price = new Integer(rs2.getInt(3));
		String contentid = rs2.getString(4);
		String chargetype = rs2.getString(5);
		// String struptime = rs2.getString(6);
		String uptime = rs2.getString(6);
		String cartoonpicurl = rs2.getString(7);
		String logo1 = rs2.getString(8);
		String logo2 = rs2.getString(9);
		String logo3 = rs2.getString(10);
		String logo4 = rs2.getString(11);
		String icpcode = rs2.getString(12);
		String icpserid = rs2.getString(13);
		String maga_full_name = maga_name + " 第" + maga_periods + "期";
		zcomContent = new Hashtable();
		logger.debug("parentmap="+parentmap);
		zcomContent.put("maga_name", this.getNotNullStr(maga_name));
		zcomContent.put("maga_periods", this.getNotNullStr(maga_periods));
		zcomContent.put("maga_office", this.getNotNullStr(maga_office));
		zcomContent.put("maga_date", this.getNotNullStr(maga_date));
		zcomContent.put("period", this.getNotNullStr(period));
		zcomContent.put("price", price);
		zcomContent.put("contentid", this.getNotNullStr(contentid));
		zcomContent.put("chargetype", this.getNotNullStr(chargetype));
		zcomContent.put("uptime", this.getNotNullStr(uptime));
		zcomContent.put("cartoonpicurl", this.getNotNullStr(cartoonpicurl));
		zcomContent.put("logo1", this.getNotNullStr(logo1));
		zcomContent.put("logo2", this.getNotNullStr(logo2));
		zcomContent.put("logo3", this.getNotNullStr(logo3));
		zcomContent.put("logo4", this.getNotNullStr(logo4));
		zcomContent.put("icpcode", this.getNotNullStr(icpcode));
		zcomContent.put("icpserid", this.getNotNullStr(icpserid));
		zcomContent.put("maga_full_name", this.getNotNullStr(maga_full_name));
		zcomContent.put("parent_id", parentmap.get(maga_name));
		Hashtable deviceandother = this.getDeviceAndOther(contentid);
		zcomContent.putAll(deviceandother);
		String platForm = this.getPlatformByContentID(contentid);
		zcomContent.put("platform", this.getNotNullStr(platForm));
		logger.debug("zcomContent="+zcomContent);
		return zcomContent;
	}

	public String getNotNullStr(String str){
		if(str == null ){
			str = "";
		}
		return str;
	}
	
	/**
	 * 检查数据合法性
	 * @param zcomContent
	 * @return
	 */
	public boolean checkZcomContent(Hashtable zcomContent,ZcomContentTmp  tmp) {
		Integer parentid = (Integer) zcomContent.get("parent_id");
		String fulldevice = (String) zcomContent.get("full_device_id");
		String maga_periods = (String) zcomContent.get("maga_periods");
		String maga_office = (String) zcomContent.get("maga_office");
		String maga_date = (String) zcomContent.get("maga_date");
		String period = (String) zcomContent.get("period");
		StringBuffer sb = new StringBuffer("");
		if (parentid != null && parentid.intValue() > 0 && fulldevice != null
				&& fulldevice.length() > 0	&&
				maga_periods != null && maga_periods.length() > 0 &&
				maga_office != null && maga_office.length() > 0 &&
				maga_date != null && maga_date.length() > 0 && 
				period != null && period.length() > 0 ) {
			sb.append("数据完整,同步成功");
			tmp.setOptype(sb.toString());
			return true;
		} else {
			
			if(fulldevice == null || fulldevice.length() <= 0){
				  sb.append("同步失败：");
				  sb.append("适配机型为空");
			  }else{
				  sb.append("同步失败：应用名称或描述格式不正确");
			  }
//			  if(parentid == null || parentid.intValue() <= 0){
//				  sb.append("|");
//				  sb.append("找不到分类ID");
//			  }if(fulldevice == null || fulldevice.length() <= 0){
//				  sb.append("|");
//				  sb.append("适配机型为空");
//			  }if(maga_periods == null || maga_periods.length() <= 0){
//				  sb.append("|");
//				  sb.append("刊物期数为空");
//			  }if(maga_office == null || maga_office.length() <= 0){
//				  sb.append("|");
//				  sb.append("刊社为空");
//			  }if(maga_date == null || maga_date.length() <= 0){
//				  sb.append("|");
//				  sb.append("出刊日期为空");
//			  }if(period == null || period.length() <= 0){
//				  sb.append("|");
//				  sb.append("出刊周期为空");
//			  }
			  
			logger.error("数据不合法:+"+zcomContent);
			tmp.setOptype(sb.toString());
			return false;
		}
	}

	/**
     * 根据内容ID得到支持的所有平台集合，以{}作边界，逗号分隔
     * @param contentID
     * @return
     * @throws DAOException
     */
    private String getPlatformByContentID(String contentID) throws DAOException
    {
        //从配置项中获取kjava平台类型的扩展类型
        String platformExt = "";
        try
        {
            platformExt = Config.getInstance().getModuleConfig().getItemValue("platformExt");
        }
        catch (Exception e1)
        {
             logger.error("从配置项中获取kjava平台类型的扩展类型是出错！");
        }
        if (logger.isDebugEnabled())
        {
            logger.debug("getPlatformByContentID(" + contentID + ")");
        }
        if (contentID == null)
        {
            return null;
        }
        String sqlCode = "SyncData.DataSyncDAO.getPlatformByContentID().SELECT";
        Object[] paras = { contentID };
        ResultSet rs = null;
        rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
        String platform = "";
        // 统计支持的平台个数
        int i = 0;
        try
        {
            String tmp = null;
            while (rs != null && rs.next())
            {
                tmp = rs.getString("platform").toLowerCase();
                if (platform.indexOf(tmp) == -1)
                {
                    if (i >= 1)
                    {
                        platform = platform + ",";
                    }
                    platform = platform + "{" + tmp + "}";
                }
                if ("kjava".equalsIgnoreCase(tmp)
                    && "1".equals(rs.getString("platformExt"))
                    && platform.indexOf(platformExt) == -1)
                {
                    platform = platform + "," + "{" + platformExt + "}";
                }
                i++;
            }
        }
        catch (SQLException e)
        {
            throw new DAOException(e);
        }
        finally
        {
            DB.close(rs);
        }
        return platform;
    }
	/**
	 * 根据内容最后更新时间升序排列查询得到需要同步的内容id,内容状态和内容最后更新时间列表。
	 * 
	 * @return list
	 *//*
	public List updateZcomSyncContent() throws DAOException {

		if (logger.isDebugEnabled()) {
			logger.debug("updateZcomSyncContent()");
		}
		// 根据t_synctime_tmp表中内容更新时间的升序查询得到查询结果；
		String sqlCode = "SyncData.DataSyncDAO.getSyncContentTmp().SELECT";
		ResultSet rs = DB.getInstance().queryBySQLCode(sqlCode, null);
		ContentTmp tmp;
		List list = new ArrayList();
		// 遍历结果,将每条记录置入类ContentTmp的各个属性中
		try {
			while (rs.next()) {
				tmp = new ContentTmp();
				//this.getContentTmpByRS(rs, tmp);
				list.add(tmp);
			}

		} catch (SQLException e) {
			throw new DAOException("读取临时表数据异常", e);
		}
		// 将各个ContentTmp放入list中返回
		return list;
	}

	*//**
	 * 将需要同步的内容列表并将内容id和内容最后更新时间插入到数据库t_syncContent_tmp中。
	 * 
	 * @param systime,当前系统的时间
	 * @param isFull boolean 是否是全量同步，true 表示全量同步，false 表示增量同步
	 */
	/*    public void updateZcomContent(long systime,boolean isFull) throws DAOException
	 {

	 if (logger.isDebugEnabled()) {
	 logger.debug("insertSysTime(" + systime + ")");
	 }
	 
	 
	 //将试图创建为表
	 String dropTable1Sql="drop table v_cm_content_zcom";
	 String dropTable2Sql="drop table v_cm_device_resource_zcom";
	 String createTemp1Sql = "create table v_cm_content_zcom as select * from PPMS_V_CM_CONTENT_ZCOM ";
	 String createTemp2Sql = "create table v_cm_device_resource_zcom as select * from PPMS_V_CM_DEVICE_RESOURCE_ZCOM ";
	 try
	 {
	 DB.getInstance().execute(dropTable1Sql, null);
	 } catch (DAOException e)
	 {
	 throw new DAOException("drop表v_cm_content_zcom出错：" + dropTable1Sql, e);
	 }
	 try
	 {
	 DB.getInstance().execute(dropTable2Sql, null);
	 } catch (DAOException e)
	 {
	 throw new DAOException("drop表v_cm_device_resource_zcom出错：" + dropTable2Sql, e);
	 }
	 try
	 {
	 DB.getInstance().execute(createTemp1Sql, null);
	 } catch (DAOException e)
	 {
	 throw new DAOException("创建表v_cm_content_zcom出错：" + createTemp1Sql, e);
	 }
	 try
	 {
	 DB.getInstance().execute(createTemp2Sql, null);
	 } catch (DAOException e)
	 {
	 throw new DAOException("创建表v_cm_device_resource_zcom出错：" + createTemp2Sql, e);
	 }
	 //数据初始化完成
	 String sqlCode;
	 Object[] paras;
	 Hashtable ht = new Hashtable();
	 ResultSet rs = null;
	 ResultSet rs1 = null;
	 ResultSet rs2 = null;
	 ResultSet rs3 = null;
	 //try {
	 //获取分类
	 String sql = "SyncData.ZcomDataSyncDAO.addContentZcomgetclass().SELECT";
	 rs = DB.getInstance().queryBySQLCode(sql, null);
	 try {
	 while (rs.next()) {
	 ht.put(rs.getString(2), new Integer(rs.getInt(1)));//name,pid,
	 }
	 } catch (SQLException e1) {
	 // TODO Auto-generated catch block
	 e1.printStackTrace();
	 }

	 if (isFull)//全量同步
	 {
	 paras = null;
	 //全量同步获取所有数据
	 sqlCode = "SyncData.ZcomDataSyncDAO.addContentZcom().FULL";
	 } else//增量同步
	 {
	 // 在表t_lastsynctime_zcom中查询得到上次系统启动时间；
	 String sqlCode1 = "SyncData.ZcomDataSyncDAO.addContentZcom().SELECT";
	 rs1 = DB.getInstance().queryBySQLCode(sqlCode1, null);
	 //如果该记录不存在证明是首次同步,则将CMS内容表中的所有内容状态为发布和过期的查询出来.
	 try {
	 if (rs1.next()) {
	 paras = new Object[1];
	 paras[0] = new Timestamp(systime);
	 // 有记录，增量同步.
	 sqlCode = "SyncData.ZcomDataSyncDAO.addContentZcom().ADD";
	 } else// 查不到同步时间则使用全量同步。
	 {
	 logger.info("t_lastsynctime_zcom表中没有上次同步记录。本次同步执行全量同步！");
	 paras = null;
	 sqlCode = "SyncData.ZcomDataSyncDAO.addContentZcom().FULL";
	 }
	 } catch (SQLException e) {
	 // TODO Auto-generated catch block
	 e.printStackTrace();
	 }
	 // 如果有上次启动时间,则在CMS中取内容最后更新时间在上次启动时间和systime之间的数据

	 }
	 //获取需要同步的数据，开始同步
	 rs2 = DB.getInstance().queryBySQLCode(sqlCode, paras);
	 try {
	 while (rs2.next()) {
	 String fname = rs2.getString(1);
	 String maga_name = fname.substring(0, fname.lastIndexOf("-")); // 刊物名称
	 String maga_periods = fname.substring(
	 fname.lastIndexOf("-") + 1, fname.length());// 刊物期数
	 String appdesc[] = rs2.getString(2).split("#");

	 String maga_office = appdesc[0]; // 刊社
	 // String strmaga_date = appdesc[1]; //出刊日期
	 String maga_date = appdesc[1]; // 出刊日期
	 String period = appdesc[2]; // 出刊周期
	 Integer price = new Integer(rs2.getInt(3));
	 String contentid = rs2.getString(4);
	 String chargetype = rs2.getString(5);
	 // String struptime = rs2.getString(6);
	 String uptime = rs2.getString(6);
	 String cartoonpicurl = rs2.getString(7);
	 String logo1 = rs2.getString(8);
	 String logo2 = rs2.getString(9);
	 String logo3 = rs2.getString(10);
	 String logo4 = rs2.getString(11);
	 String icpcode = rs2.getString(12);
	 String icpserid = rs2.getString(13);
	 Integer parent_id = (Integer) ht.get(maga_name); // 获取分类
	 if (parent_id != null) {
	 // 找到分类
	 String maga_full_name = maga_name + " 第" + maga_periods
	 + "期";
	 String platform = "";
	 String size = "";
	 String prifix = "";
	 StringBuffer device_id = new StringBuffer("");
	 // 查找适配关系表
	 String sql1 = "SyncData.ZcomDataSyncDAO.getDevice().SELECT";
	 Object[] parastemp = { contentid };
	 rs3 = DB.getInstance().queryBySQLCode(sql1, parastemp);
	 try {
	 while (rs3.next()) {
	 String device = "{" + rs3.getString(1) + "}";
	 device_id.append(device);
	 device_id.append(",");
	 size = rs3.getString(4);
	 platform = "{" + rs3.getString(3) + "}";
	 String url = rs3.getString(5);
	 prifix = url.substring(url.lastIndexOf("/") + 1, url
	 .length());
	 }
	 } catch (SQLException e) {
	 logger.error("查找适配关系表出错：contentid="+contentid);
	 e.printStackTrace();
	 }finally {
	 DB.close(rs3);
	 }

	 if (device_id != null && device_id.length() > 0) {
	 // 有适配关系
	 String full_device_id = device_id.substring(0,
	 device_id.length() - 1);

	 String updatesql = "SyncData.ZcomDataSyncDAO.updateZcomContent().UPDATE";
	 Object[] parasupdate = { maga_name, maga_periods,
	 maga_office, maga_date, period, price,
	 chargetype, uptime, cartoonpicurl, logo1,
	 logo2, logo3, logo4, icpcode, icpserid,
	 parent_id, maga_full_name, platform, size,
	 prifix, full_device_id, contentid };
	 logger.debug("parasupdate=" + parasupdate);
	 int rsupdate = DB.getInstance().executeBySQLCode(
	 updatesql, parasupdate);
	 if (rsupdate <= 0) {//更新失败则插入
	 String insertsql = "SyncData.ZcomDataSyncDAO.updateZcomContent().INSERT";
	 DB.getInstance().executeBySQLCode(insertsql,
	 parasupdate);
	 }
	 } else {
	 // 无适配关系
	 logger.error("找不到适配关系：contentid=" + contentid
	 + ";name=" + maga_name);
	 }

	 } else {
	 // 找不到分类
	 logger.error("找不到分类：contentid=" + contentid + ";name="
	 + maga_name);
	 }

	 }
	 } catch (SQLException e) {
	 // TODO Auto-generated catch block
	 logger.error("获取需要同步的数据时出错" );
	 e.printStackTrace();
	 } catch (DAOException e) {
	 logger.error("获取需要同步的数据时出错" );
	 // TODO Auto-generated catch block
	 e.printStackTrace();
	 }finally {
	 DB.close(rs2);
	 }
	 
	 //更新完成后添加本次更新时间
	 String sqlCode3 = "SyncData.ZcomDataSyncDAO.insertSysTime().INSERT";
	 Timestamp ts = new Timestamp(systime);
	 Object[] paras3 = { ts };			
	 DB.getInstance().executeBySQLCode(sqlCode3, paras3);
	 //		} catch (SQLException e) {
	 //			throw new DAOException("将需要同步的内容列表放入历史表出错", e);
	 //		} 
	 //		finally {
	 //			DB.close(rs);
	 //			DB.close(rs1);
	 //			DB.close(rs2);
	 //			DB.close(rs3);
	 //		}
	 }
	 
	 
	 */
}
