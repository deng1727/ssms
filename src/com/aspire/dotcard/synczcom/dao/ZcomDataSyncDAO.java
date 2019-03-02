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
	 * ��־����
	 */
	JLogger logger = LoggerFactory.getLogger(ZcomDataSyncDAO.class);

	private static ZcomDataSyncDAO dao = new ZcomDataSyncDAO();

	/**
	 * ��ͬ�����ݻ��������б��档key ��ʾ contentid��value  ��ʾ ������͵��б�
	 */
	private HashMap ContentDevicesCache;

	/**
	 * ��ͬ�������ʷ���Ϣ���档key ��ʾ contentid��value  ��ʾ ������͵��б�
	 */
	private HashMap ContentFeeCache;

	/**
	 * MM�ն˻����б�
	 */
	private HashMap deviceMappingCache;

	private ZcomDataSyncDAO() {

	}

	/**
	 * ����ģʽ
	 * 
	 * @return
	 */
	public static ZcomDataSyncDAO getInstance() {

		return dao;
	}

	/**
	 * ֧����������ݿ�����������Ϊ�ձ�ʾ�Ƿ��������͵Ĳ���
	 */
	private TransactionDB transactionDB;

	/**
	 * ��ȡ��������TransactionDB��ʵ�� ����Ѿ�ָ���ˣ����Ѿ�ָ���ġ����û��ָ�����Լ�����һ����ע���Լ�������ֱ���ò�֧���������͵ļ���
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
	 * ��ȡ�������͵�DAOʵ��
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
	 * ��ȡɾ��������
	 */
	public List getDelCmsNotExistZcomContent() throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("getDelCmsNotExistZcomContent()");
		}
		// ����t_synctime_tmp�������ݸ���ʱ��������ѯ�õ���ѯ�����
		String sqlCode = "SyncData.ZcomDataSyncDAO.getDelCmsNotExistZcomContent().SELECT";
		ResultSet rs = DB.getInstance().queryBySQLCode(sqlCode, null);
		ZcomContentTmp tmp;
		List list = new ArrayList();
		// �������,��ÿ����¼������ContentTmp�ĸ���������
		try {
			while (rs.next()) {
				tmp = new ZcomContentTmp();
				this.getContentTmpByRS(rs, tmp);
				logger.error("ɾ����"+tmp.getContentId());
				tmp.setOptype("��������");
				list.add(tmp);
			}

		} catch (SQLException e) {
			throw new DAOException("��ȡɾ��������", e);
		} finally {
			DB.getInstance().close(rs);
		}
		// ������ContentTmp����list�з���
		return list;
	}
	/**
	 * ɾ��PPMS�в����ڵ�
	 */
	public int delCmsNotExistZcomContent() throws DAOException {
		//    	ɾ�����ܴ��ڵģ������������ڵ�����
		String sqlCodeDel = "SyncData.ZcomDataSyncDAO.deleteSysTime().DELETE";
		int re = DB.getInstance().executeBySQLCode(sqlCodeDel, null);
		return re;
	}

	/**
	 * ���뱾�����ݿ�
	 * @param contentId
	 * @param parentmap
	 * @throws DAOException 
	 */
	public int insertIntoZcomPPs(ZcomContentTmp tmp, Hashtable parentmap)
			throws DAOException {

		Hashtable ht = this.getZcomContentFromCMS(tmp.getContentId(), parentmap);
		//���Ϸ���
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
			if (rsupdate <= 0) {//����ʧ�������
				String insertsql = "SyncData.ZcomDataSyncDAO.updateZcomContent().INSERT";
				TransactionDB tdb = this.getTransactionDB();
				tdb.executeBySQLCode(insertsql, parasupdate);
				return 1;
			}
			return 2;
		} else {
			logger.error("�������Ϸ���contentid=" + tmp.getContentId()+",ht="+ht);
			//�������Ϸ�
			return -1;
		}
	}

	/**
	 * ����ɹ��� ɾ����ʱ������
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
	 * ��������id���������ʹ�cms�в�ѯ�õ����ݶ�������Ҳ��������ݣ����׳��쳣
	 * 
	 * @param ContentId,����id
	 * @return
	 * @throws DAOException �������ݿ��쳣�������Ҳ���������
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
				throw new NullPointerException("��CMS�Ҳ��������ݡ�contentId="
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
	 * ��ȡ�����ϵ��������Ϣ
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
		// ���������ϵ��
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
			logger.error("���������ϵ�����contentid=" + contentid);
			e.printStackTrace();
		} finally {
			DB.close(rs3);
		}
		return result;
	}

	/**
	 * ��ȡzcomParentId
	 * 
	 * @param Name
	 * @return
	 * @throws DAOException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public Hashtable getZcomParentId() throws DAOException {
		Hashtable ht = new Hashtable();

		// ��ȡ����
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
			throw new DAOException("��ѯzcom�����쳣��" + e);
		} finally {
			DB.getInstance().close(rs);
		}
		return ht;
	}

	/**
	 * ��������������ʱ���������в�ѯ�õ���Ҫͬ��������id,����״̬������������ʱ���б�
	 * 
	 * @return list
	 */
	public List getZcomSyncContentTmp() throws DAOException {

		if (logger.isDebugEnabled()) {
			logger.debug("getZcomSyncContentTmp()");
		}
		// ����t_synctime_tmp�������ݸ���ʱ��������ѯ�õ���ѯ�����
		String sqlCode = "SyncData.ZcomDataSyncDAO.getZcomSyncContentTmp().SELECT";
		ResultSet rs = DB.getInstance().queryBySQLCode(sqlCode, null);
		ZcomContentTmp tmp;
		List list = new ArrayList();
		// �������,��ÿ����¼������ContentTmp�ĸ���������
		try {
			while (rs.next()) {
				tmp = new ZcomContentTmp();
				this.getContentTmpByRS(rs, tmp);
				list.add(tmp);
			}

		} catch (SQLException e) {
			throw new DAOException("��ȡ��ʱ�������쳣", e);
		} finally {
			DB.getInstance().close(rs);
		}
		// ������ContentTmp����list�з���
		return list;
	}

	 public void syncVCmDeviceResource()throws BOException
	    {
	    	if (logger.isDebugEnabled())
	        {
	            logger.debug("ͬ���� V_CM_DEVICE_RESOURC");
	        }
	    	String tempTable="v_cm_device_resource"+PublicUtil.getCurDateTime("HHmmssSSS");//��ֹͬʱ�����������
	    	
	    	String createTempSql = "create table "+tempTable+" as select * from ppms_CM_DEVICE_RESOURCE p ";
	    	String dropTableSql="drop table v_cm_device_resource";
	    	String renameSql ="alter table "+tempTable+" rename to v_cm_device_resource";
	    	String createIndex = "create index INDEX_v_device_resource_cID on v_cm_device_resource (contentid)";//add by tungke 2010-03-01 add index
	    	String dropTempSql="drop table "+tempTable;  
	    	
	    	logger.debug("��ʼ������ʱ�� "+tempTable);
	        try
			{
				DB.getInstance().execute(createTempSql, null);
			} catch (DAOException e)
			{
				throw new BOException("������ʱ�����" + tempTable, e);
			}

			logger.debug("��ʼɾ���� v_cm_device_resource");
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
				throw new BOException("ɾ����v_cm_device_resource����" + tempTable, e);
			}

			logger.debug("��ʼ���ı�����"+tempTable+"Ϊv_cm_device_resource,���������");
			try
			{
				DB.getInstance().execute(renameSql, null);
				DB.getInstance().execute(createIndex, null);
			} catch (DAOException e)
			{
				try
				{
					DB.getInstance().execute(dropTempSql, null);// ��Ҫɾ����ʷ��
				} catch (DAOException e1)
				{
					logger.error(e1);
				}
				throw new BOException("������ʱ��������" + tempTable, e);
			} 
	     }
	/**
	 * ��ͼ��ʼ��Ϊ��
	 *
	 */
	public void initZcomContentViewToTable() throws DAOException {

		// ����ͼ����Ϊ��
		String dropTable1Sql = "drop table v_cm_content_zcom";
		String createTemp1Sql = "create table v_cm_content_zcom as select * from PPMS_V_CM_CONTENT_ZCOM ";
		try {
			DB.getInstance().execute(dropTable1Sql, null);
		} catch (DAOException e) {
			throw new DAOException("drop��v_cm_content_zcom����" + dropTable1Sql,
					e);
		}
		
		try {
			DB.getInstance().execute(createTemp1Sql, null);
		} catch (DAOException e) {
			throw new DAOException("������v_cm_content_zcom����" + createTemp1Sql,
					e);
		}
		
		// ���ݳ�ʼ�����

	}

	/**
	 * ����Ҫͬ���������б�������id������������ʱ����뵽���ݿ�t_zcom_syncContent_tmp�С�
	 * 
	 * @param systime,��ǰϵͳ��ʱ��
	 * @param isFull
	 *            boolean �Ƿ���ȫ��ͬ����true ��ʾȫ��ͬ����false ��ʾ����ͬ��
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
			if (isFull)// ȫ��ͬ��
			{
				paras = null;
				// ������id��������ʱ����뵽��t_zcom_syncContent_tmp��.
				sqlCode = "SyncData.ZcomDataSyncDAO.addContentZcom().FULL";
			} else// ����ͬ��
			{
				// �ڱ�t_zcom_lastsynctime�в�ѯ�õ��ϴ�ϵͳ����ʱ�䣻
				sqlCode = "SyncData.ZcomDataSyncDAO.addContentZcom().SELECT";
				rs = DB.getInstance().queryBySQLCode(sqlCode, null);
				// ����ü�¼������֤�����״�ͬ��,��CMS���ݱ��е���������״̬Ϊ�����͹��ڵĲ�ѯ����.
				if (rs.next()) {
					paras = new Object[1];
					paras[0] = new Timestamp(systime);
					// ������id��������ʱ����뵽��t_syncContent_tmp��.
					sqlCode = "SyncData.ZcomDataSyncDAO.addContentZcom().ADD";
				} else// �鲻��ͬ��ʱ����ʹ��ȫ��ͬ����
				{
					logger.info("t_lastsynctime����û���ϴ�ͬ����¼������ͬ��ִ��ȫ��ͬ����");
					paras = null;
					// ������id��������ʱ����뵽��t_syncContent_zcom_tmp��.
					sqlCode = "SyncData.ZcomDataSyncDAO.addContentZcom().FULL";
				}
				// ������ϴ�����ʱ��,����CMS��ȡ����������ʱ�����ϴ�����ʱ���systime֮�������

			}
			//TransactionDB tdb = this.getTransactionDB();
			DB.getInstance().executeBySQLCode(sqlCode, paras);

		} catch (SQLException e) {
			throw new DAOException("����Ҫͬ���������б������ʷ�����", e);
		} finally {
			DB.close(rs);
		}
	}

	/**
	 * ������ϵͳִ������ͬ��ʱ����뵽��t_lastsynctime_zcom
	 * 
	 * @param Systime,����ϵͳִ������ͬ����ʱ��
	 */
	public void insertSysTime(long systime) throws DAOException {

		if (logger.isDebugEnabled()) {
			logger.debug("insertSysTime(" + systime + ")");
		}
		// ��systimeʱ������t_lastsynctime�С�
		String sqlCode = "SyncData.ZcomDataSyncDAO.insertSysTime().INSERT";
		Timestamp ts = new Timestamp(systime);
		Object[] paras = { ts };
		TransactionDB tdb = this.getTransactionDB();
		tdb.executeBySQLCode(sqlCode, paras);
	}

	/**
	 * ����ѯ�����Ľ����
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
	 * ����ѯ�����Ľ����
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
		
			String maga_name = fname; // ��������
			String maga_periods = "";
			if(fname.indexOf("-")> 0 && fname.indexOf("-")<(fname.length()-1)){
		 maga_name = fname.substring(0, fname.lastIndexOf("-")); // ��������
		 maga_periods = fname.substring(fname.lastIndexOf("-") + 1, fname
				.length());// ��������
		}
		String appdesc[] = rs2.getString(2).split("#");
		
			String maga_office = ""; // ����
			String maga_date = ""; // ��������
			String period = ""; // ��������
		if(appdesc.length == 3){
		 maga_office = appdesc[0]; // ����
		 maga_date = appdesc[1]; // ��������
		 period = appdesc[2]; // ��������
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
		String maga_full_name = maga_name + " ��" + maga_periods + "��";
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
	 * ������ݺϷ���
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
			sb.append("��������,ͬ���ɹ�");
			tmp.setOptype(sb.toString());
			return true;
		} else {
			
			if(fulldevice == null || fulldevice.length() <= 0){
				  sb.append("ͬ��ʧ�ܣ�");
				  sb.append("�������Ϊ��");
			  }else{
				  sb.append("ͬ��ʧ�ܣ�Ӧ�����ƻ�������ʽ����ȷ");
			  }
//			  if(parentid == null || parentid.intValue() <= 0){
//				  sb.append("|");
//				  sb.append("�Ҳ�������ID");
//			  }if(fulldevice == null || fulldevice.length() <= 0){
//				  sb.append("|");
//				  sb.append("�������Ϊ��");
//			  }if(maga_periods == null || maga_periods.length() <= 0){
//				  sb.append("|");
//				  sb.append("��������Ϊ��");
//			  }if(maga_office == null || maga_office.length() <= 0){
//				  sb.append("|");
//				  sb.append("����Ϊ��");
//			  }if(maga_date == null || maga_date.length() <= 0){
//				  sb.append("|");
//				  sb.append("��������Ϊ��");
//			  }if(period == null || period.length() <= 0){
//				  sb.append("|");
//				  sb.append("��������Ϊ��");
//			  }
			  
			logger.error("���ݲ��Ϸ�:+"+zcomContent);
			tmp.setOptype(sb.toString());
			return false;
		}
	}

	/**
     * ��������ID�õ�֧�ֵ�����ƽ̨���ϣ���{}���߽磬���ŷָ�
     * @param contentID
     * @return
     * @throws DAOException
     */
    private String getPlatformByContentID(String contentID) throws DAOException
    {
        //���������л�ȡkjavaƽ̨���͵���չ����
        String platformExt = "";
        try
        {
            platformExt = Config.getInstance().getModuleConfig().getItemValue("platformExt");
        }
        catch (Exception e1)
        {
             logger.error("���������л�ȡkjavaƽ̨���͵���չ�����ǳ���");
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
        // ͳ��֧�ֵ�ƽ̨����
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
	 * ��������������ʱ���������в�ѯ�õ���Ҫͬ��������id,����״̬������������ʱ���б�
	 * 
	 * @return list
	 *//*
	public List updateZcomSyncContent() throws DAOException {

		if (logger.isDebugEnabled()) {
			logger.debug("updateZcomSyncContent()");
		}
		// ����t_synctime_tmp�������ݸ���ʱ��������ѯ�õ���ѯ�����
		String sqlCode = "SyncData.DataSyncDAO.getSyncContentTmp().SELECT";
		ResultSet rs = DB.getInstance().queryBySQLCode(sqlCode, null);
		ContentTmp tmp;
		List list = new ArrayList();
		// �������,��ÿ����¼������ContentTmp�ĸ���������
		try {
			while (rs.next()) {
				tmp = new ContentTmp();
				//this.getContentTmpByRS(rs, tmp);
				list.add(tmp);
			}

		} catch (SQLException e) {
			throw new DAOException("��ȡ��ʱ�������쳣", e);
		}
		// ������ContentTmp����list�з���
		return list;
	}

	*//**
	 * ����Ҫͬ���������б�������id������������ʱ����뵽���ݿ�t_syncContent_tmp�С�
	 * 
	 * @param systime,��ǰϵͳ��ʱ��
	 * @param isFull boolean �Ƿ���ȫ��ͬ����true ��ʾȫ��ͬ����false ��ʾ����ͬ��
	 */
	/*    public void updateZcomContent(long systime,boolean isFull) throws DAOException
	 {

	 if (logger.isDebugEnabled()) {
	 logger.debug("insertSysTime(" + systime + ")");
	 }
	 
	 
	 //����ͼ����Ϊ��
	 String dropTable1Sql="drop table v_cm_content_zcom";
	 String dropTable2Sql="drop table v_cm_device_resource_zcom";
	 String createTemp1Sql = "create table v_cm_content_zcom as select * from PPMS_V_CM_CONTENT_ZCOM ";
	 String createTemp2Sql = "create table v_cm_device_resource_zcom as select * from PPMS_V_CM_DEVICE_RESOURCE_ZCOM ";
	 try
	 {
	 DB.getInstance().execute(dropTable1Sql, null);
	 } catch (DAOException e)
	 {
	 throw new DAOException("drop��v_cm_content_zcom����" + dropTable1Sql, e);
	 }
	 try
	 {
	 DB.getInstance().execute(dropTable2Sql, null);
	 } catch (DAOException e)
	 {
	 throw new DAOException("drop��v_cm_device_resource_zcom����" + dropTable2Sql, e);
	 }
	 try
	 {
	 DB.getInstance().execute(createTemp1Sql, null);
	 } catch (DAOException e)
	 {
	 throw new DAOException("������v_cm_content_zcom����" + createTemp1Sql, e);
	 }
	 try
	 {
	 DB.getInstance().execute(createTemp2Sql, null);
	 } catch (DAOException e)
	 {
	 throw new DAOException("������v_cm_device_resource_zcom����" + createTemp2Sql, e);
	 }
	 //���ݳ�ʼ�����
	 String sqlCode;
	 Object[] paras;
	 Hashtable ht = new Hashtable();
	 ResultSet rs = null;
	 ResultSet rs1 = null;
	 ResultSet rs2 = null;
	 ResultSet rs3 = null;
	 //try {
	 //��ȡ����
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

	 if (isFull)//ȫ��ͬ��
	 {
	 paras = null;
	 //ȫ��ͬ����ȡ��������
	 sqlCode = "SyncData.ZcomDataSyncDAO.addContentZcom().FULL";
	 } else//����ͬ��
	 {
	 // �ڱ�t_lastsynctime_zcom�в�ѯ�õ��ϴ�ϵͳ����ʱ�䣻
	 String sqlCode1 = "SyncData.ZcomDataSyncDAO.addContentZcom().SELECT";
	 rs1 = DB.getInstance().queryBySQLCode(sqlCode1, null);
	 //����ü�¼������֤�����״�ͬ��,��CMS���ݱ��е���������״̬Ϊ�����͹��ڵĲ�ѯ����.
	 try {
	 if (rs1.next()) {
	 paras = new Object[1];
	 paras[0] = new Timestamp(systime);
	 // �м�¼������ͬ��.
	 sqlCode = "SyncData.ZcomDataSyncDAO.addContentZcom().ADD";
	 } else// �鲻��ͬ��ʱ����ʹ��ȫ��ͬ����
	 {
	 logger.info("t_lastsynctime_zcom����û���ϴ�ͬ����¼������ͬ��ִ��ȫ��ͬ����");
	 paras = null;
	 sqlCode = "SyncData.ZcomDataSyncDAO.addContentZcom().FULL";
	 }
	 } catch (SQLException e) {
	 // TODO Auto-generated catch block
	 e.printStackTrace();
	 }
	 // ������ϴ�����ʱ��,����CMS��ȡ����������ʱ�����ϴ�����ʱ���systime֮�������

	 }
	 //��ȡ��Ҫͬ�������ݣ���ʼͬ��
	 rs2 = DB.getInstance().queryBySQLCode(sqlCode, paras);
	 try {
	 while (rs2.next()) {
	 String fname = rs2.getString(1);
	 String maga_name = fname.substring(0, fname.lastIndexOf("-")); // ��������
	 String maga_periods = fname.substring(
	 fname.lastIndexOf("-") + 1, fname.length());// ��������
	 String appdesc[] = rs2.getString(2).split("#");

	 String maga_office = appdesc[0]; // ����
	 // String strmaga_date = appdesc[1]; //��������
	 String maga_date = appdesc[1]; // ��������
	 String period = appdesc[2]; // ��������
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
	 Integer parent_id = (Integer) ht.get(maga_name); // ��ȡ����
	 if (parent_id != null) {
	 // �ҵ�����
	 String maga_full_name = maga_name + " ��" + maga_periods
	 + "��";
	 String platform = "";
	 String size = "";
	 String prifix = "";
	 StringBuffer device_id = new StringBuffer("");
	 // ���������ϵ��
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
	 logger.error("���������ϵ�����contentid="+contentid);
	 e.printStackTrace();
	 }finally {
	 DB.close(rs3);
	 }

	 if (device_id != null && device_id.length() > 0) {
	 // �������ϵ
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
	 if (rsupdate <= 0) {//����ʧ�������
	 String insertsql = "SyncData.ZcomDataSyncDAO.updateZcomContent().INSERT";
	 DB.getInstance().executeBySQLCode(insertsql,
	 parasupdate);
	 }
	 } else {
	 // �������ϵ
	 logger.error("�Ҳ��������ϵ��contentid=" + contentid
	 + ";name=" + maga_name);
	 }

	 } else {
	 // �Ҳ�������
	 logger.error("�Ҳ������ࣺcontentid=" + contentid + ";name="
	 + maga_name);
	 }

	 }
	 } catch (SQLException e) {
	 // TODO Auto-generated catch block
	 logger.error("��ȡ��Ҫͬ��������ʱ����" );
	 e.printStackTrace();
	 } catch (DAOException e) {
	 logger.error("��ȡ��Ҫͬ��������ʱ����" );
	 // TODO Auto-generated catch block
	 e.printStackTrace();
	 }finally {
	 DB.close(rs2);
	 }
	 
	 //������ɺ���ӱ��θ���ʱ��
	 String sqlCode3 = "SyncData.ZcomDataSyncDAO.insertSysTime().INSERT";
	 Timestamp ts = new Timestamp(systime);
	 Object[] paras3 = { ts };			
	 DB.getInstance().executeBySQLCode(sqlCode3, paras3);
	 //		} catch (SQLException e) {
	 //			throw new DAOException("����Ҫͬ���������б������ʷ�����", e);
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
