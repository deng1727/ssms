/*
 * �ļ�����ApWarnDetDAO.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
 */

package com.aspire.ponaadmin.web.dataexport.apwarndetail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.dataexport.apwarndetail.ApWarnDetConfig;
import com.aspire.ponaadmin.web.dataexport.apwarndetail.ApWarnDetConstants;
import com.aspire.ponaadmin.web.dataexport.apwarndetail.vo.ApWarnDetVO;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2010
 * </p>
 * 
 * @author x_wangml
 * @version
 */
public class ApWarnDetDAO
{

    /**
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(ApWarnDetDAO.class);

    /**
     * ʵ������
     */
    private static ApWarnDetDAO dao = new ApWarnDetDAO();

    /**
     * ˽�й��췽��
     */
    private ApWarnDetDAO()
    {
    }

    /**
     * ��̬����
     * 
     * @return
     */
    public static ApWarnDetDAO getInstance()
    {
        return dao;
    }

    /**
     * ���������
     * 
     * @throws DAOException
     */
    public void clearOldDate() throws DAOException
    {
        /*
         * drop table t_ap_warn_detail
         */
        String clearOldSql = "apwarndetail.dao.ApWarnDetDAO.clearOldDate";

        logger.debug("ɾ����ʱ����ԭ�����ݵ�sqlΪ��" + clearOldSql);
        try
        {
            DB.getInstance().executeBySQLCode(clearOldSql, null);
        }
        catch (DAOException e)
        {
            logger.error("ɾ����ʱ����ԭ�����ݳ���" + e);
        }
    }

    /**
     * ���ݵ�һ����Ԥ����������id���õ��������ݣ����뱾����ʱ���С�
     * 
     * @param date ��ǰ���ڣ�ǰһ����
     * @throws DAOException
     */
    public void copyReportDateToTemp(String[] date) throws DAOException
    {
        /*
         * create table t_ap_warn_detail1 as select contentid, p.province_name,
         * c.city_name, userid, startdate from report_ondemand_order t,
         * REPORT_PROVINCE p, REPORT_CITY c where t.reportstatus = 0 and
         * t.province_id = p.province_id and t.city_id = c.city_id and startdate >=
         * to_date(20100906, 'yyyymmdd') and startdate < to_date(20100907,
         * 'yyyymmdd') and t.contentid in (select w.content_id from t_ap_warn w
         * where w.warn_date = '20100906')
         */
        String createTableSql = "create table t_ap_warn_detail as select contentid, p.province_name, c.city_name, userid, startdate from report_ondemand_order t, REPORT_PROVINCE p, REPORT_CITY c where t.reportstatus = 0 and t.province_id = p.province_id and t.city_id = c.city_id and startdate >= to_date("
                                + date[1]
                                + ", 'yyyymmdd') and startdate < to_date("
                                + date[0]
                                + ", 'yyyymmdd') and t.contentid in  (select w.content_id from t_ap_warn w where w.warn_date = '"
                                + date[1] + "')";

        logger.debug("������ʱ���sqlΪ��" + createTableSql);

        try
        {
            DB.getInstance().execute(createTableSql, null);
        }
        catch (DAOException e)
        {
            logger.error("������ʱ�����" + e);
            throw e;
        }
    }

    /**
     * ���ڴ�����ʱ������
     * 
     * @throws DAOException
     */
    public void createIndexByTable() throws DAOException
    {
        /*
         * create index t_ap_warn_detail_contentid on t_ap_warn_detail
         * (CONTENTID,userid)
         */
        String createIndexSql = "apwarndetail.dao.ApWarnDetDAO.createIndexByTable";
        logger.debug("������ʱ��������sqlΪ��" + createIndexSql);

        try
        {
            DB.getInstance().executeBySQLCode(createIndexSql, null);
        }
        catch (DAOException e)
        {
            logger.error("������ʱ����������" + e);
            throw e;
        }
    }

    /**
     * ����ʱ����б����
     * 
     * @param dbName Ҫ�����Ŀ���
     * @param tableName Ҫ�����ı���
     * @throws DAOException
     */
    public void fullGetTableStats(String dbName, String tableName)
                    throws DAOException
    {
        /*
         * begin dbms_stats.GATHER_TABLE_STATS(?, ?, cascade => true, degree =>
         * 4); end;
         */
        String fullGetTableSql = "apwarndetail.dao.ApWarnDetDAO.fullGetTableStats";
        logger.debug("��ʱ����б������sqlΪ��" + fullGetTableSql);

        try
        {
            DB.getInstance()
              .executeBySQLCode(fullGetTableSql,
                                new Object[] { dbName, tableName });
        }
        catch (DAOException e)
        {
            logger.error("��ʱ����б��������" + e);
            throw e;
        }
    }

    /**
     * ���ڷ��ص�һ��Ԥ������
     * 
     * @param date ǰ������
     * @return
     * @throws DAOException
     * @throws SQLException
     */
    public List getFirstDateList(String date) throws DAOException
    {
        /*
         * select * from t_ap_warn w where w.warn_date = ?
         */
        String sqlCode = "apwarndetail.dao.ApWarnDetDAO.getFirstDateList";
        ResultSet rs = null;
        List retList = new ArrayList();

        logger.debug("���ص�һ��Ԥ�����ݵ�sqlΪ��" + sqlCode);

        try
        {
            rs = DB.getInstance()
                   .queryBySQLCode(sqlCode, new Object[] { date });

            while (rs.next())
            {
                ApWarnDetVO vo = new ApWarnDetVO();

                vo.setContentId(rs.getString("content_id"));
                vo.setContentName(rs.getString("name"));
                vo.setSpName(rs.getString("spname"));
                if(0<rs.getInt("price"))
                {
                    vo.setPayType(ApWarnDetConstants.WARN_PAY_TYPE_1);
                }
                else
                {
                    vo.setPayType(ApWarnDetConstants.WARN_PAY_TYPE_0);
                }
                
                retList.add(vo);
            }
        }
        catch (DAOException e)
        {
            logger.error("���ص�һ��Ԥ������ʱ����" + e);
            throw e;
        }
        catch (SQLException e)
        {
            logger.error("���ݷ��������ʱ����" + e);
            throw new DAOException(e);
        }

        return retList;
    }

    /**
     * ���ڷ���ָ������id�������������
     * 
     * @param contentId ����id
     * @return ���ش���
     * @throws DAOException
     * @throws SQLException
     */
    public int getDayDownloadNum(String contentId) throws DAOException
    {
        /*
         * select count(*) as couTemp from t_ap_warn_detail d where d.contentid = ?
         */
        String sqlCode = "apwarndetail.dao.ApWarnDetDAO.getDayDownloadNum";
        ResultSet rs = null;
        int retDate = 0;

        logger.debug("����ָ������id���������������sqlΪ��" + sqlCode);

        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode,
                                                 new Object[] { contentId });

            if (rs.next())
            {
                retDate = rs.getInt("couTemp");
            }
        }
        catch (DAOException e)
        {
            logger.error("���ص�һ��Ԥ������ʱ����" + e);
            throw e;
        }
        catch (SQLException e)
        {
            logger.error("���ݷ��������ʱ����" + e);
            throw new DAOException(e);
        }

        return retDate;
    }

    /**
     * ���ڷ���ָ������idͬ�ֻ�������������
     * 
     * @param contentId ����id
     * @return ����������
     * @throws DAOException
     */
    public int getUserDownloadNum(String contentId) throws DAOException
    {
        /*
         * select count(*) as couTemp, userid from t_ap_warn_detail d where
         * d.contentid = ? group by userid Order By couTemp desc;
         */
        String sqlCode = "apwarndetail.dao.ApWarnDetDAO.getUserDownloadNum";
        ResultSet rs = null;
        int retDate = 0;

        logger.debug("����ָ������id���������������sqlΪ��" + sqlCode);

        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode,
                                                 new Object[] { contentId });

            if (rs.next())
            {
                retDate = rs.getInt("couTemp");
            }
        }
        catch (DAOException e)
        {
            logger.error("����ָ������id�������������ʱ����" + e);
            throw e;
        }
        catch (SQLException e)
        {
            logger.error("���ݷ��������ʱ����" + e);
            throw new DAOException(e);
        }

        return retDate;
    }

    /**
     * ���ڷ���ָ������idͬ�ֻ��������������ر���
     * 
     * @param contentId ����id
     * @param maxNum �����ͬһ�ֻ������������
     * @return ����������
     * @throws DAOException
     */
    public int getUserQuotietyNum(String contentId, int maxNum)
                    throws DAOException
    {
        /*
         * select stepm.sunnum/ctemp.couunm as downloadQuotiety from (select
         * count(*) as couunm from t_ap_warn_detail d where d.contentid =
         * '300000066162') ctemp, (select sum(num) as sunnum from (select
         * count(*) as num, userid from t_ap_warn_detail d where d.contentid =
         * '300000066162' group by userid having count(*) > 1) a) stepm;
         */
        String sqlCode = "apwarndetail.dao.ApWarnDetDAO.getUserQuotietyNum";
        ResultSet rs = null;
        double retDate = 0;

        logger.debug("����ָ������idͬ�ֻ��������������ر�����sqlΪ��" + sqlCode);

        try
        {
            rs = DB.getInstance()
                   .queryBySQLCode(sqlCode,
                                   new Object[] { contentId, contentId,
                                                   new Integer(maxNum) });

            if (rs.next())
            {
                retDate = rs.getDouble("downloadQuotiety");
            }
        }
        catch (DAOException e)
        {
            logger.error("����ָ������idͬ�ֻ��������������ر���ʱ����" + e);
            throw e;
        }
        catch (SQLException e)
        {
            logger.error("���ݷ��������ʱ����" + e);
            throw new DAOException(e);
        }

        return ( int ) Math.round(retDate * 100);
    }

    /**
     * ���ڷ���ָ������id�����ֻ��������Ϊ����
     * 
     * @param contentId ����id
     * @return
     * @throws DAOException
     */
    public int getSeriesNum(String contentId) throws DAOException
    {
        /*
         * SELECT count(*) as couTemp, b.cc FROM (SELECT userid,
         * TO_NUMBER(a.userid - ROWNUM) cc, ROWNUM FROM (SELECT distinct userid
         * FROM t_ap_warn_detail d where d.contentid = '300000066162' Order By
         * userid) a) b GROUP BY b.cc Order By couTemp desc
         */
        String sqlCode = "apwarndetail.dao.ApWarnDetDAO.getSeriesNum";
        ResultSet rs = null;
        int retDate = 0;

        logger.debug("����ָ������id�����ֻ��������Ϊ���ٵ�sqlΪ��" + sqlCode);

        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode,
                                                 new Object[] { contentId });

            if (rs.next())
            {
                retDate = rs.getInt("couTemp");
            }
        }
        catch (DAOException e)
        {
            logger.error("����ָ������id�����ֻ��������Ϊ����ʱ����" + e);
            throw e;
        }
        catch (SQLException e)
        {
            logger.error("���ݷ��������ʱ����" + e);
            throw new DAOException(e);
        }

        return retDate;
    }

    /**
     * ���ڷ���ָ������id�������������޴�ĵ���
     * 
     * @param contentId ����id
     * @return
     * @throws DAOException
     */
    public List getCityDownloadIncrease(String contentId) throws DAOException
    {
        /*
         * select count(*) as couTemp, d.city_name from t_ap_warn_detail d where
         * d.contentid = ? group by d.city_name Order By couTemp desc
         */
        String sqlCode = "apwarndetail.dao.ApWarnDetDAO.getCityDownloadIncrease";
        ResultSet rs = null;
        List list = new ArrayList();

        logger.debug("����ָ������id�������������޴�ĵ��е�sqlΪ��" + sqlCode);

        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode,
                                                 new Object[] { contentId });

            // ֻȡ���õļ�������
            for (int i = 0; i < ApWarnDetConfig.MAX_DOWNLOAD_CITY_NUM; i++)
            {
                if (rs.next())
                {
                    list.add(rs.getString("city_name"));
                }
                else
                {
                    break;
                }
            }
        }
        catch (DAOException e)
        {
            logger.error("����ָ������id�������������޴�ĵ���ʱ����" + e);
            throw e;
        }
        catch (SQLException e)
        {
            logger.error("���ݷ��������ʱ����" + e);
            throw new DAOException(e);
        }

        return list;
    }

    /**
     * ���ڷ���ָ�����ݵ�����ʱ�����Ϣ
     * 
     * @param contentId contentId ����id
     * @return ������ʱ����key����ʱ���������Ϊvalue��map
     * @throws DAOException
     */
    public List getDownloadTime(String contentId) throws DAOException
    {
        /*
         * select count(*) as couTemp, newdate from (select TO_CHAR(d.startdate,
         * 'hh24') as newdate from t_ap_warn_detail d where d.contentid = ?)
         * GROUP BY newdate order by couTemp desc;
         */
        String sqlCode = "apwarndetail.dao.ApWarnDetDAO.getDownloadTime";
        ResultSet rs = null;
        List list = new ArrayList();

        logger.debug("����ָ�����ݵ�����ʱ�����Ϣ��sqlΪ��" + sqlCode);

        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode,
                                                 new Object[] { contentId });

            while (rs.next())
            {
                list.add(rs.getString("newdate") + "-" + rs.getInt("couTemp"));
            }
        }
        catch (DAOException e)
        {
            logger.error("����ָ�����ݵ�����ʱ�����Ϣʱ����" + e);
            throw e;
        }
        catch (SQLException e)
        {
            logger.error("���ݷ��������ʱ����" + e);
            throw new DAOException(e);
        }

        return list;
    }
    
    /**
     * �Ƿ����������ʱ�ηֲ��쳣���ж����賿0����8��Ӧ������������ȫ����������30%���������쳣��
     * 
     * @param contentId contentId ����id
     * @throws DAOException
     */
    public int getTimeWarn(String contentId) throws DAOException
    {

        /*
         * select (a.couTemp / b.couTemp) as couTemp from (select count(*) as
         * couTemp from (select TO_CHAR(d.startdate, 'hh24') as newdate from
         * t_ap_warn_detail d where d.contentid = '11') where newdate in ('00',
         * '01', '02', '03', '04', '05', '06', '07')) a, (select count(*) as
         * couTemp from t_ap_warn_detail d where d.contentid = '11') b;;
         */
        String sqlCode = "apwarndetail.dao.ApWarnDetDAO.getTimeWarn";
        ResultSet rs = null;
        double retDate = 0;

        logger.debug("�����Ƿ����������ʱ�ηֲ��쳣��sqlΪ��" + sqlCode);

        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode,
                                                 new Object[] { contentId, contentId });

            if (rs.next())
            {
                retDate = rs.getDouble("couTemp");
            }
        }
        catch (DAOException e)
        {
            logger.error("����ָ�����ݵ�����ʱ�����Ϣʱ����" + e);
            throw e;
        }
        catch (SQLException e)
        {
            logger.error("���ݷ��������ʱ����" + e);
            throw new DAOException(e);
        }

        return ( int ) Math.round(retDate * 100);
    }
    
    /**
     * ���������û�������
     * 
     * @param spName SP����
     * @param date Ԥ������
     * @return ������
     * @throws DAOException
     */
    public int getDownloadUserIterance(String spName, String date)
                    throws DAOException
    {
        /*
         * select fz.fz / fm.fm as iterance from (select count(distinct
         * d.userid) as fm from t_ap_warn_detail d where d.contentid in (select
         * w.content_id from t_ap_warn w where w.spname = ?)) fm, (select
         * sum(count(*)) as fz from (select count(*), contentid, userid from
         * t_ap_warn_detail d where d.contentid in (select w.content_id from
         * t_ap_warn w where w.spname = ?) group by userid, contentid) a group
         * by userid having count(*) = (select count(*) from t_ap_warn w where
         * w.spname = ?)) fz
         */
        String sqlCode = "apwarndetail.dao.ApWarnDetDAO.getDownloadUserIterance";
        ResultSet rs = null;
        double retDate = 0;

        logger.debug("���������û������ʵ�sqlΪ��" + sqlCode);

        try
        {
            rs = DB.getInstance()
                   .queryBySQLCode(sqlCode,
                                   new Object[] { spName, date, spName, date,
                                                   spName, date });
            if (rs.next())
            {
                retDate = rs.getDouble("iterance");
            }
        }
        catch (DAOException e)
        {
            logger.error("���ؼ��������û�������ʱ����" + e);
            throw e;
        }
        catch (SQLException e)
        {
            logger.error("���ݷ��������ʱ����" + e);
            throw new DAOException(e);
        }

        return ( int ) Math.round(retDate * 100);
    }

    /**
     * ���ڷ��خ�ǰSP�����ж��ك�����һ�ڔ�����
     * 
     * @param spName sp���Q
     * @param date һ�ڕr�g
     * @return
     * @throws DAOException
     */
    public int getCountContentBySPName(String spName, String date)
                    throws DAOException
    {
        /*
         * select count(*) as couTemp from t_ap_warn t where t.spname=? and
         * t.warn_date=?
         */
        String sqlCode = "apwarndetail.dao.ApWarnDetDAO.getCountContentBySPName";
        ResultSet rs = null;
        int retDate = 0;

        logger.debug("���������û������ʵ�sqlΪ��" + sqlCode);

        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode,
                                                 new Object[] { spName, date });
            if (rs.next())
            {
                retDate = rs.getInt("couTemp");
            }
        }
        catch (DAOException e)
        {
            logger.error("���ؼ��������û�������ʱ����" + e);
            throw e;
        }
        catch (SQLException e)
        {
            logger.error("���ݷ��������ʱ����" + e);
            throw new DAOException(e);
        }

        return retDate;
    }
}
