/**
 * SSMS
 * com.aspire.dotcard.synczcom.dao ZcomDataSyncDAO.java
 * Apr 8, 2010
 * @author tungke
 * @version 1.0
 *
 */

package com.aspire.dotcard.synczcoms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.util.SQLCode;
import com.aspire.dotcard.synczcom.vo.ZcomContentTmp;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.system.Config;
import com.aspire.ponaadmin.web.util.PublicUtil;
import com.sun.org.apache.regexp.internal.recompile;

/**
 * @author tungke
 * 
 */
public class ZcomsDataSyncDAO
{

    /**
     * ��־����
     */
    JLogger logger = LoggerFactory.getLogger(ZcomsDataSyncDAO.class);

    private static ZcomsDataSyncDAO dao = new ZcomsDataSyncDAO();

    private ZcomsDataSyncDAO()
    {

    }

    /**
     * ����ģʽ
     * 
     * @return
     */
    public static ZcomsDataSyncDAO getInstance()
    {

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
    private TransactionDB getTransactionDB()
    {

        if (this.transactionDB != null)
        {
            return this.transactionDB;
        }
        return TransactionDB.getInstance();
    }

    /**
     * ��ȡ�������͵�DAOʵ��
     * 
     * @return AwardDAO
     */

    public static ZcomsDataSyncDAO getTransactionInstance(
                                                          TransactionDB transactionDB)
    {

        ZcomsDataSyncDAO dao = new ZcomsDataSyncDAO();
        dao.transactionDB = transactionDB;
        return dao;
    }

    /**
     * ��ȡɾ��������
     */
    public List getDelCmsNotExistZcomsContent() throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("getDelCmsNotExistZcomsContent()");
        }
        // ����t_synctime_tmp�������ݸ���ʱ��������ѯ�õ���ѯ�����
        String sqlCode = "SyncData.ZcomsDataSyncDAO.getDelCmsNotExistZcomsContent().SELECT";
        ResultSet rs = DB.getInstance().queryBySQLCode(sqlCode, null);
        ZcomContentTmp tmp;
        List list = new ArrayList();
        // �������,��ÿ����¼������ContentTmp�ĸ���������
        try
        {
            while (rs.next())
            {
                tmp = new ZcomContentTmp();
                this.getContentTmpByRS(rs, tmp);
                logger.error("ɾ����" + tmp.getContentId());
                tmp.setOptype("��������");
                list.add(tmp);
            }

        }
        catch (SQLException e)
        {
            throw new DAOException("��ȡɾ��������", e);
        }
        finally
        {
            DB.close(rs);
        }
        // ������ContentTmp����list�з���
        return list;
    }

    /**
     * ɾ��PPMS�в����ڵ�
     */
    public int delCmsNotExistZcomsContent() throws DAOException
    {
        // ɾ�����ܴ��ڵģ������������ڵ�����
        String sqlCodeDel = "SyncData.ZcomsDataSyncDAO.deleteSysTime().DELETE";
        int re = DB.getInstance().executeBySQLCode(sqlCodeDel, null);
        return re;
    }

    /**
     * ���뱾�����ݿ�
     * 
     * @param contentId
     * @param parentmap
     * @throws DAOException
     */
    public int insertIntoZcomPPs(ZcomContentTmp tmp, Hashtable parentmap)
                    throws DAOException
    {

        Hashtable ht = this.getZcomContentFromCMS(tmp.getContentId(), parentmap);
        // ���Ϸ���
        boolean checkzcom = this.checkZcomContent(ht, tmp);
        if (checkzcom)
        {
            String updatesql = "SyncData.ZcomsDataSyncDAO.updateZcomsContent().UPDATE";
            Object[] parasupdate = { ht.get("maga_name"),
                            ht.get("maga_periods"), ht.get("maga_office"),
                            ht.get("maga_date"), ht.get("period"),
                            ht.get("price"), ht.get("chargetype"),
                            ht.get("uptime"), ht.get("cartoonpicurl"),
                            ht.get("logo1"), ht.get("logo2"), ht.get("logo3"),
                            ht.get("logo4"), ht.get("icpcode"),
                            ht.get("icpserid"), ht.get("parent_id"),
                            ht.get("maga_full_name"), ht.get("platform"),
                            ht.get("size"), ht.get("prifix"),
                            ht.get("full_device_id"), ht.get("businessid"),
                            ht.get("isformal"), ht.get("typename"),
                            ht.get("typeid"), ht.get("contentid") };
            logger.debug("parasupdate=" + parasupdate);
            int rsupdate = DB.getInstance().executeBySQLCode(updatesql,
                                                             parasupdate);
            if (rsupdate <= 0)
            {// ����ʧ�������
                String insertsql = "SyncData.ZcomsDataSyncDAO.updateZcomsContent().INSERT";
                TransactionDB tdb = this.getTransactionDB();
                tdb.executeBySQLCode(insertsql, parasupdate);
                return 1;
            }
            return 2;
        }
        else
        {
            logger.error("�������Ϸ���contentid=" + tmp.getContentId() + ",ht=" + ht);
            // �������Ϸ�
            return -1;
        }
    }

    /**
     * ����ɹ��� ɾ����ʱ������
     * 
     * @param contentId
     * @return
     * @throws DAOException
     */
    public int delZcomsContentTemp(String contentId) throws DAOException
    {
        String deltempsql = "SyncData.ZcomsDataSyncDAO.delZcomsContentTemp().DELETE";
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
                    throws DAOException
    {
        Hashtable zcomContent = null;
        if (logger.isDebugEnabled())
        {
            logger.debug("getZcomContentFromCMS(" + contentId + ")");
        }
        String sqlCode = "SyncData.ZcomsDataSyncDAO.getZcomsContentFromCMS().SELECT";
        Object[] paras = { contentId };
        ResultSet rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
        try
        {
            if (rs.next())
            {
                zcomContent = this.getZcomContentByRS(rs, parentmap);
            }
            else
            {
                throw new NullPointerException("��CMS�Ҳ��������ݡ�contentId="
                                               + contentId);
            }
        }
        catch (Exception e)
        {
            throw new DAOException(e);
        }
        finally
        {
            DB.close(rs);
        }
        return zcomContent;
    }

    /**
     * ��ȡ�����ϵ��������Ϣ
     * 
     * @param contentid
     * @return
     * @throws DAOException
     */
    public Hashtable getDeviceAndOther(String contentid) throws DAOException
    {
        Hashtable result = null;

        String size = null;
        String prifix = null;
        String full_device_id = null;
        StringBuffer device_id = new StringBuffer("");
        // ���������ϵ��
        ResultSet rs3 = null;
        String sql1 = "SyncData.ZcomsDataSyncDAO.getDeviceAndOther().SELECT";
        Object[] parastemp = { contentid };
        rs3 = DB.getInstance().queryBySQLCode(sql1, parastemp);
        try
        {
            result = new Hashtable();
            while (rs3.next())
            {
                String device = "{" + rs3.getString(1) + "}";
                device_id.append(device);
                device_id.append(",");
                size = rs3.getString(3);

                String url = rs3.getString(4);
                if (url.indexOf("/") > 0 && url.indexOf("/") < url.length() - 1)
                {
                    prifix = url.substring(url.lastIndexOf("/") + 1,
                                           url.length());
                }
            }
            if (device_id.length() > 1)
            {
                full_device_id = device_id.substring(0, device_id.length() - 1);
            }
            result.put("full_device_id", this.getNotNullStr(full_device_id));

            result.put("prifix", this.getNotNullStr(prifix));
            result.put("size", this.getNotNullStr(size));

        }
        catch (SQLException e)
        {
            logger.error("���������ϵ�����contentid=" + contentid);
            e.printStackTrace();
        }
        finally
        {
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
    public Hashtable getZcomsParentId() throws DAOException
    {
        Hashtable ht = new Hashtable();

        // ��ȡ����
        String sql = "SyncData.ZcomsDataSyncDAO.getZcomsParentId().SELECT";
        ResultSet rs;
        rs = DB.getInstance().queryBySQLCode(sql, null);
        try
        {
            while (rs.next())
            {
                ht.put(rs.getString(2), new Integer(rs.getInt(1)));// name,pid,
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new DAOException("��ѯzcom�����쳣��" + e);
        }
        finally
        {
            DB.close(rs);
        }
        return ht;
    }

    /**
     * ��������������ʱ���������в�ѯ�õ���Ҫͬ��������id,����״̬������������ʱ���б�
     * 
     * @return list
     */
    public List getZcomsSyncContentTmp() throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("getZcomSyncContentTmp()");
        }
        // ����t_synctime_tmp�������ݸ���ʱ��������ѯ�õ���ѯ�����
        String sqlCode = "SyncData.ZcomsDataSyncDAO.getZcomsSyncContentTmp().SELECT";
        ResultSet rs = DB.getInstance().queryBySQLCode(sqlCode, null);
        ZcomContentTmp tmp;
        List list = new ArrayList();
        // �������,��ÿ����¼������ContentTmp�ĸ���������
        try
        {
            while (rs.next())
            {
                tmp = new ZcomContentTmp();
                this.getContentTmpByRS(rs, tmp);
                list.add(tmp);
            }

        }
        catch (SQLException e)
        {
            throw new DAOException("��ȡ��ʱ�������쳣", e);
        }
        finally
        {
            DB.close(rs);
        }
        // ������ContentTmp����list�з���
        return list;
    }

    public void syncVCmDeviceResource() throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("ͬ���� V_CM_DEVICE_RESOURC");
        }
        String tempTable = "v_cm_device_resource"
                           + PublicUtil.getCurDateTime("HHmmssSSS");// ��ֹͬʱ�����������

        String createTempSql = "create table " + tempTable
                               + " as select * from ppms_CM_DEVICE_RESOURCE p ";
        String dropTableSql = "drop table v_cm_device_resource";
        String renameSql = "alter table " + tempTable
                           + " rename to v_cm_device_resource";
        String createIndex = "create index INDEX_v_device_resource_cID on v_cm_device_resource (contentid)";// add
        // index
        String dropTempSql = "drop table " + tempTable;

        logger.debug("��ʼ������ʱ�� " + tempTable);
        try
        {
            DB.getInstance().execute(createTempSql, null);
        }
        catch (DAOException e)
        {
            throw new BOException("������ʱ�����" + tempTable, e);
        }

        logger.debug("��ʼɾ���� v_cm_device_resource");
        try
        {
            DB.getInstance().execute(dropTableSql, null);
        }
        catch (DAOException e)
        {
            try
            {
                DB.getInstance().execute(dropTempSql, null);
            }
            catch (DAOException e1)
            {
                logger.error(e1);
            }
            throw new BOException("ɾ����v_cm_device_resource����" + tempTable, e);
        }

        logger.debug("��ʼ���ı�����" + tempTable + "Ϊv_cm_device_resource,���������");
        try
        {
            DB.getInstance().execute(renameSql, null);
            DB.getInstance().execute(createIndex, null);
        }
        catch (DAOException e)
        {
            try
            {
                DB.getInstance().execute(dropTempSql, null);// ��Ҫɾ����ʷ��
            }
            catch (DAOException e1)
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
    public void initZcomsContentViewToTable() throws DAOException
    {

        // ����ͼ����Ϊ��
        String dropTable1Sql = "drop table v_cm_content_zcom_v2";
        String createTemp1Sql = "create table v_cm_content_zcom_v2 as select * from PPMS_V_CM_CONTENT_ZCOM_v2";
        try
        {
            DB.getInstance().execute(dropTable1Sql, null);
        }
        catch (DAOException e)
        {
            throw new DAOException("drop��v_cm_content_zcom_v2����"
                                   + dropTable1Sql, e);
        }

        try
        {
            DB.getInstance().execute(createTemp1Sql, null);
        }
        catch (DAOException e)
        {
            throw new DAOException("������v_cm_content_zcom_v2����"
                                   + createTemp1Sql, e);
        }

        // ���ݳ�ʼ�����

    }

    /**
     * ����Ҫͬ���������б�������id������������ʱ����뵽���ݿ�t_zcom_syncContent_tmp�С�
     * 
     * @param systime,��ǰϵͳ��ʱ��
     * @param isFull boolean �Ƿ���ȫ��ͬ����true ��ʾȫ��ͬ����false ��ʾ����ͬ��
     */
    public void addZcomsContentTmp(long systime, boolean isFull)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("insertSysTime(" + systime + ")");
        }
        String sqlCode;
        Object[] paras;
        ResultSet rs = null;
        try
        {
            if (isFull)// ȫ��ͬ��
            {
                paras = null;
                // ������id��������ʱ����뵽��t_zcoms_syncContent_tmp��.
                sqlCode = "SyncData.ZcomsDataSyncDAO.addContentZcom().FULL";
            }
            else
            // ����ͬ��
            {
                // �ڱ�t_zcom_lastsynctime�в�ѯ�õ��ϴ�ϵͳ����ʱ�䣻
                sqlCode = "SyncData.ZcomsDataSyncDAO.addContentZcom().SELECT";
                rs = DB.getInstance().queryBySQLCode(sqlCode, null);
                // ����ü�¼������֤�����״�ͬ��,��CMS���ݱ��е���������״̬Ϊ�����͹��ڵĲ�ѯ����.
                if (rs.next())
                {
                    paras = new Object[1];
                    paras[0] = new Timestamp(systime);
                    // ������id��������ʱ����뵽��t_syncContent_tmp��.
                    sqlCode = "SyncData.ZcomsDataSyncDAO.addContentZcom().ADD";
                }
                else
                // �鲻��ͬ��ʱ����ʹ��ȫ��ͬ����
                {
                    logger.info("t_lastsynctime����û���ϴ�ͬ����¼������ͬ��ִ��ȫ��ͬ����");
                    paras = null;
                    // ������id��������ʱ����뵽��t_syncContent_zcom_tmp��.
                    sqlCode = "SyncData.ZcomsDataSyncDAO.addContentZcom().FULL";
                }
                // ������ϴ�����ʱ��,����CMS��ȡ����������ʱ�����ϴ�����ʱ���systime֮�������

            }
            // TransactionDB tdb = this.getTransactionDB();
            DB.getInstance().executeBySQLCode(sqlCode, paras);

        }
        catch (SQLException e)
        {
            throw new DAOException("����Ҫͬ���������б������ʷ�����", e);
        }
        finally
        {
            DB.close(rs);
        }
    }

    /**
     * ������ϵͳִ������ͬ��ʱ����뵽��t_lastsynctime_zcom
     * 
     * @param Systime,����ϵͳִ������ͬ����ʱ��
     */
    public void insertSysTime(long systime) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("insertSysTime(" + systime + ")");
        }
        // ��systimeʱ������t_lastsynctime�С�
        String sqlCode = "SyncData.ZcomsDataSyncDAO.insertSysTime().INSERT";
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
                    throws SQLException
    {
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
    private Hashtable getZcomContentByRS(ResultSet rs2, Hashtable parentmap)
                    throws SQLException, DAOException

    {
        Hashtable zcomContent = null;
        String fname = rs2.getString(1);

        String maga_name = fname; // ��������
        String maga_periods = "";
        if (fname.indexOf("-") > 0 && fname.indexOf("-") < (fname.length() - 1))
        {
            maga_name = fname.substring(0, fname.lastIndexOf("-")); // ��������
            maga_periods = fname.substring(fname.lastIndexOf("-") + 1,
                                           fname.length());// ��������
        }
        String appdesc[] = rs2.getString(2).split("#");

        String maga_office = ""; // ����
        String maga_date = ""; // ��������
        String period = ""; // ��������
        if (appdesc.length == 3)
        {
            maga_office = appdesc[0]; // ����
            maga_date = appdesc[1]; // ��������
            period = appdesc[2]; // ��������
        }
        Integer price = new Integer(rs2.getInt(3));
        String contentid = rs2.getString(4);
        String chargetype = rs2.getString(5);
        String uptime = rs2.getString(6);
        String cartoonpicurl = rs2.getString(7);
        String logo1 = rs2.getString(8);
        String logo2 = rs2.getString(9);
        String logo3 = rs2.getString(10);
        String logo4 = rs2.getString(11);
        String icpcode = rs2.getString(12);
        String icpserid = rs2.getString(13);
        String businessid = rs2.getString(14);
        String isformal = rs2.getString(15);
        String typename = rs2.getString(16);
        String typeid = rs2.getString(17);
        String maga_full_name = maga_name + " ��" + maga_periods + "��";

        zcomContent = new Hashtable();
        logger.debug("parentmap=" + parentmap);

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
        zcomContent.put("businessid", this.getNotNullStr(businessid));
        zcomContent.put("isformal", this.getNotNullStr(isformal));
        zcomContent.put("typename", this.getNotNullStr(typename));
        zcomContent.put("typeid", this.getNotNullStr(typeid));

        Hashtable deviceandother = this.getDeviceAndOther(contentid);
        zcomContent.putAll(deviceandother);

        String platForm = this.getPlatformByContentID(contentid);
        zcomContent.put("platform", this.getNotNullStr(platForm));

        logger.debug("zcomContent=" + zcomContent);
        return zcomContent;
    }

    public String getNotNullStr(String str)
    {
        if (str == null)
        {
            str = "";
        }
        return str;
    }

    /**
     * ������ݺϷ���
     * 
     * @param zcomContent
     * @return
     */
    public boolean checkZcomContent(Hashtable zcomContent, ZcomContentTmp tmp)
    {
        Integer parentid = ( Integer ) zcomContent.get("parent_id");
        String fulldevice = ( String ) zcomContent.get("full_device_id");
        String maga_periods = ( String ) zcomContent.get("maga_periods");
        String maga_office = ( String ) zcomContent.get("maga_office");
        String maga_date = ( String ) zcomContent.get("maga_date");
        String period = ( String ) zcomContent.get("period");
        StringBuffer sb = new StringBuffer("");
        if (parentid != null && parentid.intValue() > 0 && fulldevice != null
            && fulldevice.length() > 0 && maga_periods != null
            && maga_periods.length() > 0 && maga_office != null
            && maga_office.length() > 0 && maga_date != null
            && maga_date.length() > 0 && period != null && period.length() > 0)
        {
            sb.append("��������,ͬ���ɹ�");
            tmp.setOptype(sb.toString());
            return true;
        }
        else
        {

            if (fulldevice == null || fulldevice.length() <= 0)
            {
                sb.append("ͬ��ʧ�ܣ�");
                sb.append("�������Ϊ��");
            }
            else
            {
                sb.append("ͬ��ʧ�ܣ�Ӧ�����ƻ�������ʽ����ȷ");
            }

            logger.error("���ݲ��Ϸ�:+" + zcomContent);
            tmp.setOptype(sb.toString());
            return false;
        }
    }

    /**
     * ��������ID�õ�֧�ֵ�����ƽ̨���ϣ���{}���߽磬���ŷָ�
     * 
     * @param contentID
     * @return
     * @throws DAOException
     */
    private String getPlatformByContentID(String contentID) throws DAOException
    {
        // ���������л�ȡkjavaƽ̨���͵���չ����
        String platformExt = "";
        try
        {
            platformExt = Config.getInstance()
                                .getModuleConfig()
                                .getItemValue("platformExt");
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
    

    
    
    
    
    
}
