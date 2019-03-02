/*
 * 文件名：ApWarnDetDAO.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
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
     * 日志对象
     */
    protected static JLogger logger = LoggerFactory.getLogger(ApWarnDetDAO.class);

    /**
     * 实例对象
     */
    private static ApWarnDetDAO dao = new ApWarnDetDAO();

    /**
     * 私有构造方法
     */
    private ApWarnDetDAO()
    {
    }

    /**
     * 单态对象
     * 
     * @return
     */
    public static ApWarnDetDAO getInstance()
    {
        return dao;
    }

    /**
     * 清除旧数据
     * 
     * @throws DAOException
     */
    public void clearOldDate() throws DAOException
    {
        /*
         * drop table t_ap_warn_detail
         */
        String clearOldSql = "apwarndetail.dao.ApWarnDetDAO.clearOldDate";

        logger.debug("删除临时表中原有数据的sql为：" + clearOldSql);
        try
        {
            DB.getInstance().executeBySQLCode(clearOldSql, null);
        }
        catch (DAOException e)
        {
            logger.error("删除临时表中原有数据出错" + e);
        }
    }

    /**
     * 根据第一期有预警嫌疑内容id，得到报表数据，存入本地临时表中。
     * 
     * @param date 当前日期，前一日期
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

        logger.debug("创建临时表的sql为：" + createTableSql);

        try
        {
            DB.getInstance().execute(createTableSql, null);
        }
        catch (DAOException e)
        {
            logger.error("创建临时表出错" + e);
            throw e;
        }
    }

    /**
     * 用于创建临时表索引
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
        logger.debug("创建临时表索引的sql为：" + createIndexSql);

        try
        {
            DB.getInstance().executeBySQLCode(createIndexSql, null);
        }
        catch (DAOException e)
        {
            logger.error("创建临时表索引出错" + e);
            throw e;
        }
    }

    /**
     * 对临时表进行表分析
     * 
     * @param dbName 要分析的库名
     * @param tableName 要分析的表名
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
        logger.debug("临时表进行表分析的sql为：" + fullGetTableSql);

        try
        {
            DB.getInstance()
              .executeBySQLCode(fullGetTableSql,
                                new Object[] { dbName, tableName });
        }
        catch (DAOException e)
        {
            logger.error("临时表进行表分析出错" + e);
            throw e;
        }
    }

    /**
     * 用于返回第一期预警数据
     * 
     * @param date 前天日期
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

        logger.debug("返回第一期预警数据的sql为：" + sqlCode);

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
            logger.error("返回第一期预警数据时出错" + e);
            throw e;
        }
        catch (SQLException e)
        {
            logger.error("数据放入对象中时出错" + e);
            throw new DAOException(e);
        }

        return retList;
    }

    /**
     * 用于返回指定内容id当天的下载总量
     * 
     * @param contentId 内容id
     * @return 下载次数
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

        logger.debug("返回指定内容id当天的下载总量的sql为：" + sqlCode);

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
            logger.error("返回第一期预警数据时出错" + e);
            throw e;
        }
        catch (SQLException e)
        {
            logger.error("数据放入对象中时出错" + e);
            throw new DAOException(e);
        }

        return retDate;
    }

    /**
     * 用于返回指定内容id同手机号下载最多次数
     * 
     * @param contentId 内容id
     * @return 下载最多次数
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

        logger.debug("返回指定内容id当天的下载总量的sql为：" + sqlCode);

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
            logger.error("返回指定内容id当天的下载总量时出错" + e);
            throw e;
        }
        catch (SQLException e)
        {
            logger.error("数据放入对象中时出错" + e);
            throw new DAOException(e);
        }

        return retDate;
    }

    /**
     * 用于返回指定内容id同手机号下载与总下载比例
     * 
     * @param contentId 内容id
     * @param maxNum 允许的同一手机号最大下载数
     * @return 下载最多次数
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

        logger.debug("返回指定内容id同手机号下载与总下载比例的sql为：" + sqlCode);

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
            logger.error("返回指定内容id同手机号下载与总下载比例时出错" + e);
            throw e;
        }
        catch (SQLException e)
        {
            logger.error("数据放入对象中时出错" + e);
            throw new DAOException(e);
        }

        return ( int ) Math.round(retDate * 100);
    }

    /**
     * 用于返回指定内容id下载手机连号最大为多少
     * 
     * @param contentId 内容id
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

        logger.debug("返回指定内容id下载手机连号最大为多少的sql为：" + sqlCode);

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
            logger.error("返回指定内容id下载手机连号最大为多少时出错" + e);
            throw e;
        }
        catch (SQLException e)
        {
            logger.error("数据放入对象中时出错" + e);
            throw new DAOException(e);
        }

        return retDate;
    }

    /**
     * 用于返回指定内容id地市消费增幅巨大的地市
     * 
     * @param contentId 内容id
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

        logger.debug("返回指定内容id地市消费增幅巨大的地市的sql为：" + sqlCode);

        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode,
                                                 new Object[] { contentId });

            // 只取配置的几个城市
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
            logger.error("返回指定内容id地市消费增幅巨大的地市时出错" + e);
            throw e;
        }
        catch (SQLException e)
        {
            logger.error("数据放入对象中时出错" + e);
            throw new DAOException(e);
        }

        return list;
    }

    /**
     * 用于返回指定内容的下载时间段信息
     * 
     * @param contentId contentId 内容id
     * @return 返回以时间点的key，以时间段下载量为value的map
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

        logger.debug("返回指定内容的下载时间段信息的sql为：" + sqlCode);

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
            logger.error("返回指定内容的下载时间段信息时出错" + e);
            throw e;
        }
        catch (SQLException e)
        {
            logger.error("数据放入对象中时出错" + e);
            throw new DAOException(e);
        }

        return list;
    }
    
    /**
     * 是否存在着下载时段分布异常（判断在凌晨0点至8点应用下载量超过全天下载量的30%，即存在异常）
     * 
     * @param contentId contentId 内容id
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

        logger.debug("返回是否存在着下载时段分布异常的sql为：" + sqlCode);

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
            logger.error("返回指定内容的下载时间段信息时出错" + e);
            throw e;
        }
        catch (SQLException e)
        {
            logger.error("数据放入对象中时出错" + e);
            throw new DAOException(e);
        }

        return ( int ) Math.round(retDate * 100);
    }
    
    /**
     * 计算消费用户复叠率
     * 
     * @param spName SP名称
     * @param date 预警日期
     * @return 复叠率
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

        logger.debug("计算消费用户复叠率的sql为：" + sqlCode);

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
            logger.error("返回计算消费用户复叠率时出错" + e);
            throw e;
        }
        catch (SQLException e)
        {
            logger.error("数据放入对象中时出错" + e);
            throw new DAOException(e);
        }

        return ( int ) Math.round(retDate * 100);
    }

    /**
     * 用于返回當前SP下面有多少內容在一期數據中
     * 
     * @param spName sp名稱
     * @param date 一期時間
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

        logger.debug("计算消费用户复叠率的sql为：" + sqlCode);

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
            logger.error("返回计算消费用户复叠率时出错" + e);
            throw e;
        }
        catch (SQLException e)
        {
            logger.error("数据放入对象中时出错" + e);
            throw new DAOException(e);
        }

        return retDate;
    }
}
