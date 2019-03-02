
package com.aspire.dotcard.cysyncdata.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.cysyncdata.vo.CYToMMMappingVO;
import com.aspire.dotcard.gcontent.DeviceDAO;
import com.aspire.dotcard.gcontent.DeviceVO;
import com.aspire.dotcard.gcontent.GAppContent;
import com.aspire.dotcard.gcontent.GAppGame;
import com.aspire.dotcard.gcontent.GAppSoftWare;
import com.aspire.dotcard.gcontent.GAppTheme;
import com.aspire.dotcard.gcontent.GContent;
import com.aspire.dotcard.syncData.vo.ContentTmp;
import com.aspire.dotcard.syncData.vo.DeviceResourceVO;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.system.Config;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * <p>
 * ����ͬ��������ҵ�����ݺ��������ݣ�
 * </p>
 * <p>
 * Copyright (c) 2003-2007 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights
 * Reserved
 * </p>
 * 
 * @author zhangmin
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class CYDataSyncDAO
{

    /**
     * ��־����
     */
    JLogger logger = LoggerFactory.getLogger(CYDataSyncDAO.class);

    private static CYDataSyncDAO dao = new CYDataSyncDAO();

    /**
     * ��ͬ�����ݻ��������б��档key ��ʾ contentid��value ��ʾ ������͵��б�
     */
    private HashMap ContentDevicesCache;

    /**
     * ��ͬ�������ʷ���Ϣ���档key ��ʾ contentid��value ��ʾ ������͵��б�
     */
    // private HashMap ContentFeeCache;
    /**
     * MM�ն˻����б�
     */
    private HashMap deviceMappingCache;

    private CYDataSyncDAO()
    {

    }

    /**
     * ����ģʽ
     * 
     * @return
     */
    public static CYDataSyncDAO getInstance()
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

    public static CYDataSyncDAO getTransactionInstance(
                                                       TransactionDB transactionDB)
    {

        CYDataSyncDAO dao = new CYDataSyncDAO();
        dao.transactionDB = transactionDB;
        return dao;
    }

    /**
     * ����ͬ��ǰ������׼������Ҫ�Ƕ�ȡ������Ϣ��
     */
    public void prepareDate() throws BOException
    {
        try
        {
            ContentDevicesCache = getAllSyncContentDevices();
            // ContentFeeCache=getAllSyncContentFee();
            deviceMappingCache = DeviceDAO.getInstance().getDeviceList();
        }
        catch (DAOException e)
        {
            logger.error("��ʼ������ʧ��" + e);
            throw new BOException("��ʼ������ʧ��");
        }

    }

    /**
     * ���������Ϣ���ͷ��ڴ档
     */
    public void clearDate() throws BOException
    {
        ContentDevicesCache = null;
        // ContentFeeCache=null;
        deviceMappingCache = null;
    }

    /**
     * ����Ҫͬ���������б�������id������������ʱ����뵽���ݿ�t_syncContent_tmp�С�
     * 
     * @param systime,��ǰϵͳ��ʱ��
     * @param isFull boolean �Ƿ���ȫ��ͬ����true ��ʾȫ��ͬ����false ��ʾ����ͬ��
     */
    public void addContentTmp(long systime, boolean isFull) throws DAOException
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
                // ������id��������ʱ����뵽��t_syncContent_tmp��.
                sqlCode = "SyncData.CYDataSyncDAO.addContentTmp().INSERT1";
            }
            else
            // ����ͬ��
            {
                // �ڱ�t_lastsynctime�в�ѯ�õ��ϴ�ϵͳ����ʱ�䣻
                sqlCode = "SyncData.CYDataSyncDAO.addContentTmp().SELECT";
                rs = DB.getInstance().queryBySQLCode(sqlCode, null);
                // ����ü�¼������֤�����״�ͬ��,��CMS���ݱ��е���������״̬Ϊ�����͹��ڵĲ�ѯ����.
                if (rs.next())
                {
                    paras = new Object[1];
                    paras[0] = new Timestamp(systime);
                    // ������id��������ʱ����뵽��t_syncContent_tmp��.
                    sqlCode = "SyncData.CYDataSyncDAO.addContentTmp().INSERT2";
                }
                else
                // �鲻��ͬ��ʱ����ʹ��ȫ��ͬ����
                {
                    logger.info("t_lastsynctime_cy����û���ϴ�ͬ����¼������ͬ��ִ��ȫ��ͬ����");
                    addContentTmp(systime, true);
                    return;
                }
                // ������ϴ�����ʱ��,����CMS��ȡ����������ʱ�����ϴ�����ʱ���systime֮�������

            }
            TransactionDB tdb = this.getTransactionDB();
            tdb.executeBySQLCode(sqlCode, paras);

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
     * ��������������ʱ���������в�ѯ�õ���Ҫͬ��������id,����״̬������������ʱ���б�
     * 
     * @return list
     */
    public List getSyncContentTmp() throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("getSyncContentTmp()");
        }
        // ����t_synctime_tmp�������ݸ���ʱ��������ѯ�õ���ѯ�����
        String sqlCode = "SyncData.CYDataSyncDAO.getSyncContentTmp().SELECT";
        ResultSet rs = DB.getInstance().queryBySQLCode(sqlCode, null);
        ContentTmp tmp;
        List list = new ArrayList();
        // �������,��ÿ����¼������ContentTmp�ĸ���������
        try
        {
            while (rs.next())
            {
                tmp = new ContentTmp();
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
            // add by tungke for close
            DB.close(rs);

        }
        // ������ContentTmp����list�з���
        return list;
    }

    /**
     * ������ϵͳִ������ͬ��ʱ����뵽��t_lastsynctime
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
        String sqlCode = "SyncData.CYDataSyncDAO.insertSysTime().INSERT";
        Timestamp ts = new Timestamp(systime);
        Object[] paras = { ts };
        TransactionDB tdb = this.getTransactionDB();
        tdb.executeBySQLCode(sqlCode, paras);
    }

    /**
     * ��ͼ��ʼ��Ϊ��
     * 
     */
    public void initViewToTable() throws DAOException
    {

        // ����ͼ����Ϊ��
        // String dropTable1Sql = "drop table v_cm_content";
        String talbeName = "v_cm_content_cy";
        String fromSql = "select * from PPMS_V_CM_CONTENT_CY ";
        // String createTemp1Sql = "create table v_cm_content as select * from
        // PPMS_V_CM_CONTENT ";
         String createindexSql = "create index V_CM_CONTENT_CY_CONID on V_CM_CONTENT_CY (CONTENTID) ";

        try
        {
            this.fullSyncTables(talbeName, fromSql);

             DB.getInstance().execute(createindexSql, null);
            this.fullGetTableStats("V_CM_CONTENT_CY");
        }
        catch (BOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.error("drop��v_cm_content_cy����" + fromSql, e);
        }

        // ���ݳ�ʼ�����

    }

    /**
     * ɾ��������ʱ������Ӧ�ļ�¼
     * 
     * @param Id ,���ݿ��в��������
     */
    public void delSynccontetTmp(int Id) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("delSynccontetTmp(" + Id + ")");
        }
        // ͨ�������id�ֶ��ҵ���Ӧ�ļ�¼����ɾ��
        String sqlCode = "SyncData.CYDataSyncDAO.delSynccontetTmp().DELETE";
        Object[] paras = { new Integer(Id) };
        TransactionDB tdb = this.getTransactionDB();
        tdb.executeBySQLCode(sqlCode, paras);
    }

    /**
     * ��������id���������ʹ�cms�в�ѯ�õ����ݶ�������Ҳ��������ݣ����׳��쳣
     * 
     * @param ContentId,����id
     * @param contentType,���ݶ���
     * @return
     * @throws DAOException �������ݿ��쳣�������Ҳ���������
     */
    public GContent getGcontentFromCMS(String contentId, String contentType)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("getGcontentFromCMS(" + contentId + "," + contentType
                         + ")");
        }
        String sqlCode = this.getSqlCodeByType(contentType);// GContentFactory.getSqlCodeByType(contentType);
        Object[] paras = { contentId };
        ResultSet rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
        GContent gc = null;
        try
        {
            if (rs.next())
            {
                gc = this.getGContentByRS(rs, contentType);
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
        return gc;
    }

    /**
     * ����ѯ�����Ľ����
     * 
     * @param rs
     * @param tmp
     * @throws SQLException
     */
    private void getContentTmpByRS(ResultSet rs, ContentTmp tmp)
                    throws SQLException
    {

        tmp.setId(rs.getInt("id"));
        tmp.setContentId(rs.getString("contentid"));
        tmp.setName(rs.getString("name"));
        tmp.setContentType(rs.getString("contentType"));
        tmp.setStatus(rs.getString("status"));
        tmp.setLupdDate(rs.getDate("pLupdDate"));
    }

    /**
     * ����content������
     * 
     * @param rs�������
     * @param type,��������
     * @return
     */
    private GContent getGContentByRS(ResultSet rs, String type)
                    throws Exception
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("getGContentByRS(" + type + ")");
        }
        if (GAppGame.TYPE_APPGAME.equals(type.trim()))
        {
            GAppGame content = new GAppGame();
            this.setBaseContent(rs, content);
            this.setGAppGameContents(rs, content);
            return content;
        }
        else if (GAppSoftWare.TYPE_APPSOFTWARE.equals(type.trim()))
        {
            GAppSoftWare content = new GAppSoftWare();
            this.setBaseContent(rs, content);
            this.setGAppSoftwareContents(rs, content);
            return content;
        }
        else if (GAppTheme.TYPE_APPTHEME.equals(type.trim()))
        {
            GAppTheme content = new GAppTheme();
            this.setBaseContent(rs, content);
            this.setGAppthemeContents(rs, content);
            return content;

        }
        else
        {
            return null;
        }
    }

    /**
     * �������ݻ�������
     * 
     * @param rs
     * @param content
     * @throws SQLException
     */
    private void setBaseContent(ResultSet rs, GAppContent content)
                    throws SQLException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("setBaseContent()");
        }

        content.setName(rs.getString("name"));
        content.setCateName(rs.getString("cateName"));
        content.setSpName(rs.getString("spName"));
        content.setIcpCode(rs.getString("icpCode"));
        content.setIcpServId(rs.getString("icpServId"));
        content.setContentTag(rs.getString("contentTag"));
        content.setIntroduction(rs.getString("introduction"));
        content.setContentID(rs.getString("contentID"));
        content.setCompanyID(rs.getString("companyID"));
        content.setProductID(rs.getString("productID"));
        content.setKeywords(rs.getString("keywords"));
        // content.setCreateDate(PublicUtil.getCurDateTime());//createDate
        content.setCreateDate(rs.getString("createDate"));
        content.setMarketDate(rs.getString("marketdate"));
        content.setLupdDate(rs.getString("lupddate"));
        
        content.setPlupdDate(rs.getString("plupddate"));
        
        // add by tungke
        content.setOtherNet(rs.getString("otherNet"));
        // add by tungke
        
        content.setServAttr(rs.getString("servAttr"));
        content.setSubType(rs.getString("subType"));
        //
        content.setPvcID(rs.getString("pvcid"));
        content.setCityID(rs.getString("cityid"));
        									  
        content.setContestYear(rs.getString("contestyear"));//add by dongke for cy 20110427
        content.setAuditionUrl(rs.getString("AUDITIONURL")); //add by dongke for 20130709 wp
        
        // ������ά�������ã���ʼֵΪ0
        content.setOrderTimes(0);
        content.setWeekOrderTimes(0);
        content.setMonthOrderTimes(0);
        content.setDayOrderTimes(0);
        content.setScanTimes(0);
        content.setWeekScanTimes(0);
        content.setMonthScanTimes(0);
        content.setDayScanTimes(0);
        content.setSearchTimes(0);
        content.setWeekSearchTimes(0);
        content.setMonthSearchTimes(0);
        content.setDaySearchTimes(0);
        content.setCommentTimes(0);
        content.setWeekCommentTimes(0);
        content.setMonthCommentTimes(0);
        content.setDayCommentTimes(0);
        content.setMarkTimes(0);
        content.setWeekMarkTimes(0);
        content.setMonthMarkTimes(0);
        content.setDayMarkTimes(0);
        content.setCommendTimes(0);
        content.setWeekCommendTimes(0);
        content.setMonthCommendTimes(0);
        content.setDayCommendTimes(0);
        content.setCollectTimes(0);
        content.setWeekCollectTimes(0);
        content.setMonthCollectTimes(0);
        content.setDayCollectTimes(0);
        content.setAverageMark(0);
    }

    /**
     * ����Ӧ����Ϸ��������
     * 
     * @param rs
     * @param content
     */
    private void setGAppGameContents(ResultSet rs, GAppGame content)
                    throws Exception
    {
        content.setAppCateID(rs.getString("appCateID"));
        content.setAppCateName(rs.getString("appCateName"));
        content.setWWWPropaPicture1(this.getImageUrl(rs.getString("WWWPropaPicture1")));
        content.setWWWPropaPicture2(this.getImageUrl(rs.getString("WWWPropaPicture2")));
        content.setWWWPropaPicture3(this.getImageUrl(rs.getString("WWWPropaPicture3")));
        content.setClientPreviewPicture1(this.getImageUrl(rs.getString("clientPreviewPicture1")));
        content.setClientPreviewPicture2(this.getImageUrl(rs.getString("clientPreviewPicture2")));
        content.setClientPreviewPicture3(this.getImageUrl(rs.getString("clientPreviewPicture3")));
        content.setClientPreviewPicture4(this.getImageUrl(rs.getString("clientPreviewPicture4")));
        // content.setProvider(rs.getString("provider"));
        int temp = rs.getInt("contestchannel");
        
        if(temp == 0)
        {
            content.setProvider("");
        }
        else
        {
            content.setProvider(String.valueOf(temp));
        }
        content.setLanguage(rs.getString("language"));
        content.setHandBook(rs.getString("handBook"));
        content.setHandBookPicture(rs.getString("handBookPicture"));
        content.setUserGuide(rs.getString("userGuide"));
        content.setUserGuidePicture(this.getImageUrl(rs.getString("userGuidePicture")));
        content.setGameVideo(this.getVideoUrl(rs.getString("gameVideo")));
        content.setLOGO1(this.getImageUrl(rs.getString("LOGO1")));
        content.setLOGO2(this.getImageUrl(rs.getString("LOGO2")));
        content.setLOGO3(this.getImageUrl(rs.getString("LOGO3")));
        content.setLOGO4(this.getImageUrl(rs.getString("LOGO4")));
        content.setLOGO5(this.getImageUrl(rs.getString("LOGO5")));
        content.setCartoonPicture(this.getImageUrl(rs.getString("cartoonPicture")));
        content.setProgramSize(rs.getInt("programsize"));
        content.setProgramID(rs.getString("pid"));
        // content.setProgramSize(0);
        // content.setProgramID("");
        content.setOnlineType(rs.getInt("onlinetype"));
        content.setVersion(rs.getString("version"));
        content.setPicture1(this.getImageUrl(rs.getString("picture1")));
        content.setPicture2(this.getImageUrl(rs.getString("picture2")));
        content.setPicture3(this.getImageUrl(rs.getString("picture3")));
        content.setPicture4(this.getImageUrl(rs.getString("picture4")));
        content.setPicture5(this.getImageUrl(rs.getString("picture5")));
        content.setPicture6(this.getImageUrl(rs.getString("picture6")));
        content.setPicture7(this.getImageUrl(rs.getString("picture7")));
        content.setPicture8(this.getImageUrl(rs.getString("picture8")));
        content.setIsSupportDotcard(rs.getString("isSupportDotcard"));
        content.setPlatform(this.getPlatformByContentID(rs.getString("contentID")));
        content.setChargeTime(rs.getString("chargeTime"));
       

        // ��������֧�ֵ��ն�����,��Ʒ��
        setDeviceNameAndBrand(content);
        /*
         * String deviceName = this.getDeviceName(content);
         * content.setDeviceName(deviceName);
         */
    }

    /**
     * ����Ӧ���������չ����
     * 
     * @param rs
     * @param content
     */
    private void setGAppSoftwareContents(ResultSet rs, GAppSoftWare content)
                    throws Exception
    {
        content.setAppCateID(rs.getString("appCateID"));
        content.setAppCateName(rs.getString("appCateName"));
        content.setWWWPropaPicture1(this.getImageUrl(rs.getString("WWWPropaPicture1")));
        content.setWWWPropaPicture2(this.getImageUrl(rs.getString("WWWPropaPicture2")));
        content.setWWWPropaPicture3(this.getImageUrl(rs.getString("WWWPropaPicture3")));
        content.setClientPreviewPicture1(this.getImageUrl(rs.getString("clientPreviewPicture1")));
        content.setClientPreviewPicture2(this.getImageUrl(rs.getString("clientPreviewPicture2")));
        content.setClientPreviewPicture3(this.getImageUrl(rs.getString("clientPreviewPicture3")));
        content.setClientPreviewPicture4(this.getImageUrl(rs.getString("clientPreviewPicture4")));
        content.setLanguage(rs.getString("language"));
       
        // content.setProvider(rs.getString("provider"));
        int temp = rs.getInt("contestchannel");
        
        if(temp == 0)
        {
            content.setProvider("");
        }
        else
        {
            content.setProvider(String.valueOf(temp));
        }
        content.setManual(this.getFileUrl(rs.getString("manual")));
        content.setLOGO1(this.getImageUrl(rs.getString("LOGO1")));
        content.setLOGO2(this.getImageUrl(rs.getString("LOGO2")));
        content.setLOGO3(this.getImageUrl(rs.getString("LOGO3")));
        content.setLOGO4(this.getImageUrl(rs.getString("LOGO4")));
        content.setLOGO5(this.getImageUrl(rs.getString("LOGO5")));
        content.setCartoonPicture(this.getImageUrl(rs.getString("cartoonPicture")));
        content.setProgramSize(rs.getInt("programsize"));
        content.setProgramID(rs.getString("pid"));

        content.setOnlineType(rs.getInt("onlinetype"));
        content.setVersion(rs.getString("version"));
        content.setPicture1(this.getImageUrl(rs.getString("picture1")));
        content.setPicture2(this.getImageUrl(rs.getString("picture2")));
        content.setPicture3(this.getImageUrl(rs.getString("picture3")));
        content.setPicture4(this.getImageUrl(rs.getString("picture4")));
        content.setPicture5(this.getImageUrl(rs.getString("picture5")));
        content.setPicture6(this.getImageUrl(rs.getString("picture6")));
        content.setPicture7(this.getImageUrl(rs.getString("picture7")));
        content.setPicture8(this.getImageUrl(rs.getString("picture8")));
        content.setIsSupportDotcard(rs.getString("isSupportDotcard"));
        content.setPlatform(this.getPlatformByContentID(rs.getString("contentID")));
        content.setChargeTime(rs.getString("chargeTime"));
        content.setHandBook(rs.getString("handBook")); // add  by dongke 20110603
        content.setHandBookPicture(rs.getString("handBookPicture"));
        
        // �����������֧�ֵ��ն�����,��Ʒ��
        setDeviceNameAndBrand(content);
        /*
         * String deviceName = this.getDeviceName(content);
         * content.setDeviceName(deviceName);
         */
    }

    /**
     * ����Ӧ���������չ����
     * 
     * @param rs
     * @param content
     */
    private void setGAppthemeContents(ResultSet rs, GAppTheme content)
                    throws Exception
    {
        content.setAppCateID(rs.getString("appCateID"));
        content.setAppCateName(rs.getString("appCateName"));
        content.setWWWPropaPicture1(this.getImageUrl(rs.getString("WWWPropaPicture1")));
        content.setWWWPropaPicture2(this.getImageUrl(rs.getString("WWWPropaPicture2")));
        content.setWWWPropaPicture3(this.getImageUrl(rs.getString("WWWPropaPicture3")));
        content.setClientPreviewPicture1(this.getImageUrl(rs.getString("clientPreviewPicture1")));
        content.setClientPreviewPicture2(this.getImageUrl(rs.getString("clientPreviewPicture2")));
        content.setClientPreviewPicture3(this.getImageUrl(rs.getString("clientPreviewPicture3")));
        content.setClientPreviewPicture4(this.getImageUrl(rs.getString("clientPreviewPicture4")));
        content.setLanguage(rs.getString("language"));
        // content.setProvider(rs.getString("provider"));
        int temp = rs.getInt("contestchannel");
        
        if(temp == 0)
        {
            content.setProvider("");
        }
        else
        {
            content.setProvider(String.valueOf(temp));
        }
        
        content.setLOGO1(this.getImageUrl(rs.getString("LOGO1")));
        content.setLOGO2(this.getImageUrl(rs.getString("LOGO2")));
        content.setLOGO3(this.getImageUrl(rs.getString("LOGO3")));
        content.setLOGO4(this.getImageUrl(rs.getString("LOGO4")));
        content.setLOGO5(this.getImageUrl(rs.getString("LOGO5")));
        content.setCartoonPicture(this.getImageUrl(rs.getString("cartoonPicture")));
        content.setProgramSize(rs.getInt("programsize"));
        content.setProgramID(rs.getString("pid"));

        content.setOnlineType(rs.getInt("onlinetype"));
        content.setVersion(rs.getString("version"));
        content.setPicture1(this.getImageUrl(rs.getString("picture1")));
        content.setPicture2(this.getImageUrl(rs.getString("picture2")));
        content.setPicture3(this.getImageUrl(rs.getString("picture3")));
        content.setPicture4(this.getImageUrl(rs.getString("picture4")));
        content.setPicture5(this.getImageUrl(rs.getString("picture5")));
        content.setPicture6(this.getImageUrl(rs.getString("picture6")));
        content.setPicture7(this.getImageUrl(rs.getString("picture7")));
        content.setPicture8(this.getImageUrl(rs.getString("picture8")));
        content.setIsSupportDotcard(rs.getString("isSupportDotcard"));
        content.setPlatform(this.getPlatformByContentID(rs.getString("contentID")));
        content.setChargeTime(rs.getString("chargeTime"));
        
        content.setHandBook(rs.getString("handBook")); // add  by dongke 20110603
        content.setHandBookPicture(rs.getString("handBookPicture"));

        // ������Ϸ����֧�ֵ��ն�����,��Ʒ��
        setDeviceNameAndBrand(content);
        /*
         * String deviceName = this.getDeviceName(content);
         * content.setDeviceName(deviceName);
         */
    }

    /**
     * ��������id�õ��ն��б�
     * 
     * @param contentId
     * @return ֧�ֻ��͵��ն�id���б�
     */
    private List getTerminalList(String contentId)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("getTerminalList(" + contentId + ")");
        }
        //
        List newList = new ArrayList();
        HashMap deviceMap = ( HashMap ) ContentDevicesCache.get(contentId);
        if (deviceMap != null)
        {
            for (Iterator itrator = deviceMap.values().iterator(); itrator.hasNext();)
            {
                DeviceResourceVO vo = ( DeviceResourceVO ) itrator.next();
                if (vo.getMatch() == 1)
                {
                    newList.add(vo.getDeviceId());
                }
                // ��ҵ���� ȥ��ģ����������
                // else
                // {
                // Boolean isFree=(Boolean)ContentFeeCache.get(contentId);
                // if(isFree.booleanValue())//ֻ����Ѳ�ƥ�䡣
                // {
                // newList.add(vo.getDeviceId());
                // }
                // }
            }
        }
        return newList;
    }

    /**
     * �жϵ�ǰͬ�������Ƿ�Ϊ�״β���
     * 
     * @param systime ,��ǰϵͳ��ʱ��
     * @return �״β��� ����true
     * @throws DAOException
     * @author biran
     */
    public boolean getFirstSync(long systime) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("getFirstSync(" + systime + ")");
        }
        // �ڱ�t_lastsynctime�в�ѯ�õ��ϴ�ϵͳ����ʱ�䣻
        String sqlCode = "SyncData.DataSyncDAO.addContentTmp().SELECT";

        ResultSet rs = DB.getInstance().queryBySQLCode(sqlCode, null);
        // ����ü�¼������֤�����״�ͬ��,����������ͬ��������
        try
        {
            rs.next();
            Object o = rs.getObject(1);
            if (o == null)
            {
                // t_lastsynctime���¼������,Ϊ�״�ͬ������
                return true;
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new DAOException(e);
        }
        finally
        {
            DB.close(rs);
        }
        // ���ͬ����־��Ĭ��Ϊ���
        return false;
    }

    /**
     * ��ѯ��CMS������/���������ҵ����������ݡ�
     * 
     * @throws DAOException
     * @author biran
     */
    public void againInsSyncContentTmp() throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("againInsSyncContentTmp()");
        }
        /*
         * // ��ѯ��CMS������������ҵ����������� String sqlCode =
         * "SyncData.DataSyncDAO.addContentTmp().INSERT3";
         * 
         * TransactionDB tdb = this.getTransactionDB();
         * tdb.executeBySQLCode(sqlCode, null);
         */

        // ��ѯ��CMS�н��������ҵ�����������
        TransactionDB tdb = this.getTransactionDB();
        String sqlCode = "SyncData.CYDataSyncDAO.addContentTmp().INSERT4";
        tdb.executeBySQLCode(sqlCode, null);

    }

    /**
     * deviceName��ʽ��{devicename1},{devicename2}�� brand��ʽ��{brand1},{brand2}��
     * 
     * @param content
     * @throws DAOException
     */
    private void setDeviceNameAndBrand(GAppContent content) throws DAOException
    {
        // ȡ���ݿ���֧�ֵ��ն�����DeviceDAO
        // HashMap deviceMappingCache = DeviceDAO.getInstance().getDeviceList();
        // ȡ������֧�ֵ��ն����ͼ��� ��CM_DEVICE_RESOURCE�в�ѯ
        List cList = getTerminalList(content.getContentID());
        // ��¼ƽ̨֧�ֵĻ���
        // List errorList=new ArrayList();
        // cList = this.filterList(cList, deviceMappingCache,errorList);

        /*
         * //��¼PAS��֧��deviceName����־ if(errorList.size() > 0) {
         * this.logErrorDeviceName(errorList,content); }
         */
        // ��ȡ��ǰϵͳ֧�ֵĻ������ƣ���Ʒ�ƣ�������ĸ˳���������
        Collection deviceNameList = new TreeSet();
        HashMap brandMap = new HashMap();// ��Ҫȥ�ء�
        for (int i = 0; i < cList.size(); i++)
        {
            String deviceId = ( String ) cList.get(i);
            DeviceVO deviceVO = ( DeviceVO ) deviceMappingCache.get(deviceId);
            deviceNameList.add(deviceVO.getDeviceName());
            brandMap.put(deviceVO.getBrand(), "");
        }
        content.setFulldeviceID(list2String(cList));
        content.setDeviceName(list2String(deviceNameList));
        content.setBrand(list2String(brandMap.keySet()));
        String fulldevice = PublicUtil.filterMbrandEspecialChar(list2String(deviceNameList));
        content.setFulldeviceName(fulldevice);//add 20101108
    }

  
    
   
    /* *//**
             * Ŀǰ����Ҫ����֤�ˡ� �������ڵ�һ��list���Ҵ����ڵڶ���list�����ݷ���
             * 
             * @param one
             * @param deviceMap �ն˿����еĻ��ͼ���
             * @return
             */
    /*
     * private List filterList(List one,HashMap deviceMap,List errorList) { List
     * result = new ArrayList(); Object temp = ""; for(int i = 0,size =
     * one.size(); i < size; i++) { temp=one.get(i); DeviceVO
     * vo=(DeviceVO)deviceMap.get(temp); if(vo!=null) { result.add(vo); }else {
     * //��¼��PAS���в����ڵ�deviceName errorList.add(temp); } }
     * 
     * return result; }
     */
    /**
     * ��list�е�����ƴװΪ {devicename},{devicename}�� ����ʽ
     * 
     * @param list ����deviceName�ļ���
     * @return ���磺{devicename1},{devicename2}�� ���ַ���
     */
    private String list2String(Collection collection)
    {
        StringBuffer sb = new StringBuffer();
        Iterator iterator = collection.iterator();
        boolean dotFlag = false;// ��һ�β���Ҫ���붺��
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
            sb.append("{");
            sb.append(iterator.next());
            sb.append("}");
        }
        return sb.toString();
    }

    /**
     * ������Դid�õ��ļ����ݵ�url
     * 
     * @param resourceId,��Դid return String
     */
    private String getFileUrl(String resourceId) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("getFileUrl(" + resourceId + ")");
        }
        String sqlCode = "SyncData.CYDataSyncDAO.getFileUrl().SELECT";
        Object[] paras = { resourceId };
        ResultSet rs = null;
        rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
        String url = null;
        try
        {
            if (rs != null && rs.next())
            {
                url = rs.getString("URL");
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
        return url;
    }

    /**
     * ������Դid�õ�ͼƬ��url
     * 
     * @param resourceId,��Դid return String
     */
    private String getImageUrl(String resourceId) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("getImageUrl(" + resourceId + ")");
        }
        if (resourceId == null)
        {
            return null;
        }
        String sqlCode = "SyncData.CYDataSyncDAO.getImageUrl().SELECT";
        Object[] paras = { resourceId };
        ResultSet rs = null;
        rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
        String url = null;
        try
        {
            if (rs != null && rs.next())
            {
                url = rs.getString("URL");
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
        return url;
    }

    /**
     * ������Դid�õ���Ƶ���ݵ�url
     * 
     * @param resourceId,��Դid return String
     */
    private String getVideoUrl(String resourceId) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("getVideoUrl(" + resourceId + ")");
        }
        if (resourceId == null)
        {
            return null;
        }
        String sqlCode = "SyncData.CYDataSyncDAO.getVideoUrl().SELECT";
        Object[] paras = { resourceId };
        ResultSet rs = null;
        rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
        String url = null;
        try
        {
            if (rs != null && rs.next())
            {
                url = rs.getString("URL");
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
        return url;
    }

    /**
     * ��ѯ���ݶ�Ӧҵ���ҵ��ͨ������ liyouli add patch 134
     */
    public String queryContentUmflag(String icpCode, String icpServID)
                    throws DAOException
    {
        String umFlag = null;

        String sqlCode = "SyncData.CYDataSyncDAO.queryContentUmflag().SELECT";
        Object[] paras = { icpCode, icpServID };
        ResultSet rs = null;
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
            if (rs.next())
            {
                umFlag = rs.getString("umflag");
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

        return umFlag;
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
        String sqlCode = "SyncData.CYDataSyncDAO.getPlatformByContentID().SELECT";
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

    public void syncVCmDeviceResource() throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("ͬ���� V_CM_DEVICE_RESOURC");
        }
        try
        {
            String fromSql = DB.getInstance()
                               .getSQLByCode("DataSyncDAO.syncVCmDeviceResource().SELECT");
            fullSyncTables("v_cm_device_resource", fromSql,"CONTENTID, DEVICE_NAME");

            //DB.getInstance() .execute("create index INDEX_v_device_resource_cID on v_cm_device_resource (contentid)",  null);

            this.fullGetTableStats("V_CM_DEVICE_RESOURCE");
        }
        catch (DAOException e)
        {
            throw new BOException("ͬ��V_CM_DEVICE_RESOURC �����", e);
        }
    }

    /**
     * ͨ��contentID�������е���Ʒ��Ϣ,��Щ��Ϣ��Ҫ����ͬ���ʼ����չʾ��
     * 
     * @param contentID
     * @return list
     * @throws DAOException
     */
    public List getAllGoodsInfoByContentID(String contentID)
                    throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("getAllGoodsInfoByContentID(" + contentID
                         + ") is beginning ....");
        }
        Object[] paras = { contentID };
        String sqlCode = "DataSyncDAO.getAllGoodsInfoByContentID().SELECT";
        ArrayList list = new ArrayList();
        ResultSet rs = null;
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
            while (rs.next())
            {
                /*
                 * GoodsInfoVO vo = rs.getString("goodsid"));
                 * vo.setContentID(contentID)
                 */
                // list.add(vo);
            }
        }
        catch (SQLException ex)
        {
            throw new DAOException(ex);
        }
        finally
        {
            DB.close(rs);
        }
        return list;
    }

    /**
     * ��ȡ��Ҫͬ�����������ݵĻ���ƥ����Ϣ�� ����HashMap��key ��ʾ contentid��value ��ʾ ������͵��б�
     * 
     * @return HashMap
     * @throws DAOException
     */
    public HashMap getAllSyncContentDevices() throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("��ȡ��Ҫͬ�����������ݵĻ���ƥ����Ϣ");
        }
        Object[] paras = null;
        String sqlCode = "CYDataSyncDAO.getAllSyncContentDevices().SELECT";
        HashMap allMap = new HashMap();
        ResultSet rs = null;
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
            while (rs.next())
            {
                /*
                 * GoodsInfoVO vo = rs.getString("goodsid"));contentid
                 * vo.setContentID(contentID)
                 */
                // list.add(vo);
                String contentId = rs.getString(1);
                HashMap deviceMap = ( HashMap ) allMap.get(contentId);
                if (deviceMap == null)
                {
                    deviceMap = new HashMap();
                    allMap.put(contentId, deviceMap);
                }
                DeviceResourceVO vo = new DeviceResourceVO();
                vo.setDeviceId(rs.getString(2));
                vo.setMatch(rs.getInt(3));

                Date proSubmitDate = rs.getTimestamp(4);
                if (proSubmitDate == null)// ��ppms ȷ�ϣ���ֵ������Ϊ��
                {
                    continue;
                }
                vo.setProSubmitDate(proSubmitDate);

                DeviceResourceVO oldVO = ( DeviceResourceVO ) deviceMap.get(vo.getDeviceId());
                if (oldVO != null)// ˵���Ѿ�����
                {
                    if (vo.getProSubmitDate().after(oldVO.getProSubmitDate()))// ��ȡ���µĳ���Ļ��������ϵ��
                    {
                        deviceMap.put(vo.getDeviceId(), vo);
                    }
                }
                else
                {
                    deviceMap.put(vo.getDeviceId(), vo);
                }

            }
        }
        catch (SQLException ex)
        {
            throw new DAOException(ex);
        }
        finally
        {
            DB.close(rs);
        }
        return allMap;
    }

    /**
     * ��ȡ��Ҫͬ�����������ݵ��ʷ��Ƿ���ѡ�//��Ҫ���������Ϣ����ģ������
     * 
     * @return hashMap key ��ʾcontentId value ��ʾ��Ӧ���Ƿ����
     * @throws DAOException
     */
    /*
     * public HashMap getAllSyncContentFee() throws DAOException { if
     * (logger.isDebugEnabled()) { logger.debug("��ȡ��Ҫͬ�����������ݵ��ʷ��Ƿ������Ϣ" ); }
     * Object[] paras = null; String sqlCode =
     * "DataSyncDAO.getAllSyncContentFee().SELECT"; HashMap map = new HashMap();
     * ResultSet rs = null; try { rs = DB.getInstance().queryBySQLCode(sqlCode,
     * paras); while (rs.next()) { String contentId=rs.getString(1); int
     * mobilePrice=rs.getInt(2); if(mobilePrice!=0) { map.put(contentId, new
     * Boolean(false)); }else { map.put(contentId, new Boolean(true)); }
     *  } } catch (SQLException ex) { throw new DAOException(ex); } finally {
     * DB.close(rs); } return map; }
     */

    /***************************************************************************
     * ���б����
     * 
     * @param tableName
     * @throws BOException
     */
    public void fullGetTableStats(String tableName) throws BOException
    {

        String dbName = ConfigFactory.getSystemConfig()
                                     .getModuleConfig("ssms")
                                     .getItemValue("DB_NAME");
        String sql = "begin dbms_stats.GATHER_TABLE_STATS('"
                     + dbName.toUpperCase() + "','" + tableName.toUpperCase()
                     + "',cascade=>true,degree=>4); end;";
        logger.debug("���б������sqlΪ��" + sql);
        try
        {
            // 1 ���б����
            DB.getInstance().execute(sql, null);
        }
        catch (DAOException e)
        {
            logger.error("���������" + e);
            throw new BOException("���������", e);
        }
    }

    
    /**
     * ȫ��ͬ�������ݣ�ͨ����������Ȼ�����ʵ��
     * @param tableName
     * @fromSql ������Դ��sql
     * @throws BOException ͨ�����̳����쳣
     */
    public void fullSyncTables(String tableName,String fromSql,String indexTargetCon)throws BOException
    {
    	
        logger.info("��ʼͬ���� tableName:"+tableName);
        String timestamp=PublicUtil.getCurDateTime("HHSSS");
    	String tempTableName=tableName+timestamp;//��ֹͬʱ�����������
    	String backupTableName=tableName+timestamp+"_bak";
    	
    	String createTempSql = "create table "+tempTableName+" as "+fromSql;//������ʱ��
    	String backupTableSql="alter table "+tableName+" rename to "+backupTableName;//���ݾɱ�
    	String replaceSql ="alter table "+tempTableName+" rename to "+tableName;//����ʱ����Ϊ��ʽ��

    	String dropBackupSql="drop table "+backupTableName;//ɾ�����ݱ�  
    	String dropTempSql="drop table "+tempTableName;//ɾ����ʱ��
    	String revertBackupTableSql="alter table "+backupTableName+" rename to "+tableName;//��ԭ���ݱ�
    	
    	
    	logger.info("��ʼ������ʱ��:"+tempTableName);
        try
		{
			DB.getInstance().execute(createTempSql, null);
			logger.info("��ʼ������ʱ������:"+"create index IDX_"+tempTableName+" on "+tempTableName+" ("+indexTargetCon+")");
			DB.getInstance().execute("create index IDX_"+tempTableName+" on "+tempTableName+" ("+indexTargetCon+")", null);
			
			
		} catch (DAOException e)
		{
			throw new BOException("������ʱ�����" + tempTableName, e);
		}

		logger.info("��ʼ���ݾɱ� :"+backupTableName);
		try
		{
			DB.getInstance().execute(backupTableSql, null);
		} catch (DAOException e)
		{
			
			throw new BOException("���ݱ�:"+tableName+"����" + tempTableName, e);
		}

		logger.info("��ʼ������ʱ����Ϊ��ʽ��:"+tempTableName);
		try
		{
			DB.getInstance().execute(replaceSql, null);

		} catch (DAOException e)
		{
			//������ʱ��ʧ�ܣ���Ҫ��ԭ���ݱ���ɾ����ʱ��
			try
			{
				DB.getInstance().execute(revertBackupTableSql, null);// ��ԭ���ݱ�
				DB.getInstance().execute(dropTempSql, null);// ��Ҫɾ����ʱ��
			} catch (DAOException e1)
			{
				logger.error(e1);
			}
			throw new BOException("������ʱ��������" + tempTableName, e);
		} 
		logger.info("ɾ�����ݱ� "+backupTableName);
		try
		{
			DB.getInstance().execute(dropBackupSql, null);
		} catch (DAOException e)
		{
			logger.error("ɾ�����ݱ�ʧ��:"+backupTableName);
		}// ɾ�����ݱ�
     }
    
    /**
     * ȫ��ͬ�������ݣ�ͨ����������Ȼ�����ʵ��
     * 
     * @param tableName
     * @fromSql ������Դ��sql
     * @throws BOException ͨ�����̳����쳣
     */
    public void fullSyncTables(String tableName, String fromSql)
                    throws BOException
    {

        /**
         * 
         * logger.info("��ʼͬ���� tableName:"+tableName); // String
         * timestamp=PublicUtil.getCurDateTime("HHSSS"); // String
         * tempTableName=tableName+timestamp;//��ֹͬʱ����������� // String
         * backupTableName=tableName+timestamp+"_bak";
         * 
         * //String createTempSql = "create table "+tempTableName+" as
         * "+fromSql;//������ʱ�� String delSql = "truncate table "+tableName ;
         * String insertSql = "insert into "+tableName + " "+fromSql ; //String
         * backupTableSql="alter table "+tableName+" rename to
         * "+backupTableName;//���ݾɱ� //String replaceSql ="alter table
         * "+tempTableName+" rename to "+tableName;//����ʱ����Ϊ��ʽ��
         * 
         * //String dropBackupSql="drop table "+backupTableName;//ɾ�����ݱ� //String
         * dropTempSql="drop table "+tempTableName;//ɾ����ʱ�� //String
         * revertBackupTableSql="alter table "+backupTableName+" rename to
         * "+tableName;//��ԭ���ݱ�
         * 
         * 
         * logger.info("��ʼ��ձ�:"+tableName); try {
         * DB.getInstance().execute(delSql, null); } catch (DAOException e) {
         * throw new BOException("��ձ����" + tableName, e); }
         * 
         * logger.info("��ʼ���������ݵ��� :"+tableName); try {
         * DB.getInstance().execute(insertSql, null); } catch (DAOException e) {
         * 
         * throw new BOException("���������ݵ���:"+tableName+"����", e); }
         * 
         */
        
        logger.info("��ʼͬ���� tableName:"+tableName);
        String timestamp=PublicUtil.getCurDateTime("HHSSS");
        String tempTableName=tableName+timestamp;//��ֹͬʱ�����������
        String backupTableName=tableName+timestamp+"_bak";
        
        String createTempSql = "create table "+tempTableName+" as "+fromSql;//������ʱ��
        String backupTableSql="alter table "+tableName+" rename to "+backupTableName;//���ݾɱ�
        String replaceSql ="alter table "+tempTableName+" rename to "+tableName;//����ʱ����Ϊ��ʽ��

        String dropBackupSql="drop table "+backupTableName;//ɾ�����ݱ�  
        String dropTempSql="drop table "+tempTableName;//ɾ����ʱ��
        String revertBackupTableSql="alter table "+backupTableName+" rename to "+tableName;//��ԭ���ݱ�
        
        
        logger.info("��ʼ������ʱ��:"+tempTableName);
        try
        {
            DB.getInstance().execute(createTempSql, null);
        } catch (DAOException e)
        {
            throw new BOException("������ʱ�����" + tempTableName, e);
        }

        logger.info("��ʼ���ݾɱ� :"+backupTableName);
        try
        {
            DB.getInstance().execute(backupTableSql, null);
        } catch (DAOException e)
        {
            
            throw new BOException("���ݱ�:"+tableName+"����" + tempTableName, e);
        }

        logger.info("��ʼ������ʱ����Ϊ��ʽ��:"+tempTableName);
        try
        {
            DB.getInstance().execute(replaceSql, null);

        } catch (DAOException e)
        {
            //������ʱ��ʧ�ܣ���Ҫ��ԭ���ݱ���ɾ����ʱ��
            try
            {
                DB.getInstance().execute(revertBackupTableSql, null);// ��ԭ���ݱ�
                DB.getInstance().execute(dropTempSql, null);// ��Ҫɾ����ʱ��
            } catch (DAOException e1)
            {
                logger.error(e1);
            }
            throw new BOException("������ʱ��������" + tempTableName, e);
        } 
        logger.info("ɾ�����ݱ� "+backupTableName);
        try
        {
            DB.getInstance().execute(dropBackupSql, null);
        } catch (DAOException e)
        {
            logger.error("ɾ�����ݱ�ʧ��:"+backupTableName);
        }// ɾ�����ݱ�
     
    }

    /**
     * ��ȡӦ�ñ�ǩ
     * 
     * @param contentid
     * @return
     * @throws DAOException
     */
    /*
     * public String getTagByContentID(String contentid) throws DAOException {
     * StringBuffer sb = new StringBuffer(""); if (contentid != null &&
     * contentid.length() > 0) { String querySQLCode =
     * "CYDataSyncDAO.getTagByContentID().SELECT"; Object[] paras = { contentid };
     * ResultSet rs = null; try { rs =
     * DB.getInstance().queryBySQLCode(querySQLCode, paras); //sb = new
     * StringBuffer(""); while (rs.next()) { String tagName = rs.getString(1);
     * sb.append("{" + tagName + "};"); } } catch (SQLException ex) {
     * logger.error("��ȡ��ǩ���ݿ����ʧ��," + ex); throw new DAOException(ex); } finally {
     * DB.close(rs); } } return sb.toString(); }
     */

    /**
     * �������ݵ�����,ͨ���� t_pps_comment_grade ����õ�
     * 
     * @return
     * @throws DAOException
     */
    public void updateRemarks()
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("updateRemarks()");
        }
        String sqlCode = "SyncData.DataSyncDAO.updateRemarks.UPDATE";
        try
        {
            DB.getInstance().executeBySQLCode(sqlCode, null);
        }
        catch (DAOException e)
        {
            logger.error("�������������쳣", e);
        }

    }

    /**
     * ��ѯ����Ŀ¼�µ���Ʒ
     * 
     * @param refNodeId
     * @param appCateName
     * @return
     * @throws Exception
     */
    public List getRefContentsByCateName(String refNodeId) throws DAOException
    {
        String querySQLCode = "CYDataSyncDAO.getRefContentsByCateName().SELECT";
        Object[] paras = { refNodeId };
        ResultSet rs = null;
        List refs = new ArrayList();
        try
        {
            rs = DB.getInstance().queryBySQLCode(querySQLCode, paras);
            // sb = new StringBuffer("");
            while (rs.next())
            {
                String id = rs.getString(1);
                refs.add(id);
            }
        }
        catch (SQLException ex)
        {
            logger.error("��ȡ��ǩ���ݿ����ʧ��," + ex);
            throw new DAOException(ex);
        }
        finally
        {
            DB.close(rs);
        }
        return refs;
    }

    public String getGcAppCateNameById(String ctId) throws DAOException
    {
        String querySQLCode = "CYDataSyncDAO.getGcAppCateNameById().SELECT";
        Object[] paras = { ctId };
        ResultSet rs = null;
        String appCateName = null;
        try
        {
            rs = DB.getInstance().queryBySQLCode(querySQLCode, paras);
            // sb = new StringBuffer("");
            while (rs.next())
            {
                appCateName = rs.getString(1);
            }
        }
        catch (SQLException ex)
        {
            logger.error("��ȡ��ǩ���ݿ����ʧ��," + ex);
            throw new DAOException(ex);
        }
        finally
        {
            DB.close(rs);
        }
        return appCateName;
    }

    /**
     * ����ͬ�������
     * 
     * @param list
     * @throws DAOException
     */
    /*
     * public void insertSynResult(List[] list)throws DAOException{
     * logger.debug("CYDataSyncDAO.insertSynResult begin!"); List updateList =
     * list[0];//�ɹ����¡� List addList = list[1];//�ɹ����� List deleteList =
     * list[2];//�ɹ����� List errorList = list[3];//ʧ��ͬ��
     * PublicUtil.removeDuplicateWithOrder(errorList);//ȥ���ظ���¼ͬ��ʧ�ܵ����⡣ int
     * size=updateList.size()+addList.size()+deleteList.size()+errorList.size();
     * logger.debug("����"+size+"����¼�����£�"+updateList.size()+",����:"+addList.size()
     * +",���ߣ�"+deleteList.size()+",ɾ��:"+errorList.size()); String sqlCode =
     * "DataSyncDAO.insertSynResult().INSERT"; Object mutiParas[][] = new
     * Object[size][4]; String time = PublicUtil.getDateString(new Date(),
     * "yyyy-MM-dd HH:mm:ss"); if(size>0){ ContentTmp tmp = null; int j = 0;
     * for(int i=0;i<addList.size();i++){ tmp = (ContentTmp)addList.get(i);
     * mutiParas[j][0] = tmp.getContentId(); mutiParas[j][1] = tmp.getName();
     * mutiParas[j][2] = new Integer(1); mutiParas[j][3] = time; j++; } for(int
     * i=0;i<updateList.size();i++){ tmp = (ContentTmp)updateList.get(i);
     * mutiParas[j][0] = tmp.getContentId(); mutiParas[j][1] = tmp.getName();
     * mutiParas[j][2] = new Integer(2); mutiParas[j][3] = time; j++; } for(int
     * i=0;i<deleteList.size();i++){ tmp = (ContentTmp)deleteList.get(i);
     * mutiParas[j][0] = tmp.getContentId(); mutiParas[j][1] = tmp.getName();
     * mutiParas[j][2] = new Integer(3); mutiParas[j][3] = time; j++; } for(int
     * i=0;i<errorList.size();i++){ tmp = (ContentTmp)errorList.get(i);
     * mutiParas[j][0] = tmp.getContentId(); mutiParas[j][1] = tmp.getName();
     * mutiParas[j][2] = new Integer(4); mutiParas[j][3] = time; j++; }
     * 
     * try { DB.getInstance().executeBatchBySQLCode(sqlCode, mutiParas); } catch
     * (Exception e) { logger.error("����ͬ�����ݽ���쳣",e); } }
     * logger.debug("DataSyncDAO.insertSynResult end!"); }
     */

    /**
     * �����������͹������ݶ���
     * 
     * @param type
     * @return
     */
    public String getSqlCodeByType(String type)
    {
        if (GAppGame.TYPE_APPGAME.equals(type))
        {
            return "SyncData.CYDataSyncDAO.getGcontentFromCMS().SELECTGAME";
        }
        else if (GAppSoftWare.TYPE_APPSOFTWARE.equals(type))
        {
            return "SyncData.CYDataSyncDAO.getGcontentFromCMS().SELECTSOFTWARE";
        }
        else if (GAppTheme.TYPE_APPTHEME.equals(type))
        {
            return "SyncData.CYDataSyncDAO.getGcontentFromCMS().SELECTTHEME";
        }
        else
        {
            return "";
        }
    }

    /**
     * ��ȡ��ҵ������������ӳ���ϵ
     * 
     * @return
     * @throws DAOException
     */

    public List getAllCYToMMMapping() throws DAOException
    {

        // select * from t_cytomm_mapping t
        String querySQLCode = "CYDataSyncDAO.getAllCYToMMMapping().SELECT";
        List list = new ArrayList();
        ResultSet rs = null;

        try
        {
            rs = DB.getInstance().queryBySQLCode(querySQLCode, null);
            while (rs.next())
            {
                CYToMMMappingVO vo = new CYToMMMappingVO();
                vo.setAppCateId(rs.getString("APPCATEID"));
                vo.setAppCateName(rs.getString("APPCATENAME"));
                vo.setCateId(rs.getString("CATEID"));
                vo.setCateName(rs.getString("CATENAME"));
                vo.setCYAppCateName(rs.getString("CYAPPCATENAME"));
                vo.setCYCateId(rs.getString("CYCATEID"));
                list.add(vo);
            }
        }
        catch (SQLException ex)
        {
            logger.error("��ȡ��ҵ����Ӧ�ö�������ӳ���ϵ ���ݿ����ʧ��," + ex);
            throw new DAOException(ex);
        }
        finally
        {
            DB.close(rs);
        }
        return list;
    }
    
    /**
     * �����ڲ�ѯӦͬ�������ݡ�
     * @throws DAOException
     */
    public void reverseSyncContentTmp() throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("reverseSyncContentTmp()");
        }

        // ��ѯ��CMS�н��������ҵ�����������
        TransactionDB tdb = this.getTransactionDB();
        String sqlCode = "SyncData.CYDataSyncDAO.addContentTmp().INSERT5";
        tdb.executeBySQLCode(sqlCode, null);

    }

}
