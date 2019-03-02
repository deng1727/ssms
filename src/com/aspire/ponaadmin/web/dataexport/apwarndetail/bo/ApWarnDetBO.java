/*
 * 文件名：ApWarnDetBO.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  用于二期预警数据处理。
 */

package com.aspire.ponaadmin.web.dataexport.apwarndetail.bo;

import java.io.File;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.dataexport.apwarn.ApWarnConfig;
import com.aspire.ponaadmin.web.dataexport.apwarn.vo.ApWarnVo;
import com.aspire.ponaadmin.web.dataexport.apwarndetail.ApWarnDetConfig;
import com.aspire.ponaadmin.web.dataexport.apwarndetail.ApWarnDetConstants;
import com.aspire.ponaadmin.web.dataexport.apwarndetail.dao.ApWarnDetDAO;
import com.aspire.ponaadmin.web.dataexport.apwarndetail.vo.ApWarnDetVO;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * <p>
 * Title:用于二期预警数据处理
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
public class ApWarnDetBO
{

    /**
     * 日志对象
     */
    protected static JLogger logger = LoggerFactory.getLogger(ApWarnDetBO.class);

    /**
     * 实例对象
     */
    private static ApWarnDetBO bo = new ApWarnDetBO();

    /**
     * 缓存SP下应用下载用户重叠率
     */
    private static Map SPUSERITERANCEMAP = Collections.synchronizedMap(new HashMap());

    /**
     * 用于返回统计全信息。file对应生成文件，cityWarn对应区域下载增幅巨大的统计，timeWarn对应时间段固定的统计
     */
    private static Map RETMAP = new HashMap();

    /**
     * 区域下载增幅巨大的统计
     */
    private static int cityWarn = 0;

    /**
     * 时间段固定的统计
     */
    private static int timeWarn = 0;

    /**
     * 单用户下载应用异常标准统计
     */
    private static int downloadWarn = 0;

    /**
     * 连号消费现像标准统计
     */
    private static int seriesNumWarn = 0;

    /**
     * 下载用户重叠率现象标准统计
     */
    private static int downloadUserIteranceWarn = 0;

    /**
     * 私有构造方法
     */
    private ApWarnDetBO()
    {
    }

    /**
     * 单态对象
     * 
     * @return
     */
    public static ApWarnDetBO getInstance()
    {
        return bo;
    }

    /**
     * 得到当前日期和前一日期的字符型式
     * 
     * @param date
     */
    private String[] getDataString()
    {
        String[] date = new String[2];

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        // 当前日期
        date[0] = sdf.format(cal.getTime());

        // 前一日期
        cal.add(Calendar.DATE, -1);
        date[1] = sdf.format(cal.getTime());

        return date;
    }

    /**
     * 根据第一期有预警嫌疑内容id，得到报表数据，存入本地临时表中。
     * 
     * @param date 当前日期，前一日期
     * @throws DAOException
     */
    private void copyReportDateToTemp(String[] date) throws DAOException
    {
        logger.info("删除之前本地临时表数据");

        // 删除之前数据
        ApWarnDetDAO.getInstance().clearOldDate();

        logger.info("加载一期预警详细信息至本地临时表");

        // 导入新数据
        ApWarnDetDAO.getInstance().copyReportDateToTemp(date);

        logger.info("创建临时表索引");

        // 创建临时表索引
        ApWarnDetDAO.getInstance().createIndexByTable();

        logger.info("进行临时表的表分析");

        String dbName = ConfigFactory.getSystemConfig()
                                     .getModuleConfig("ssms")
                                     .getItemValue("DB_NAME");

        // 进行表分析
        ApWarnDetDAO.getInstance()
                    .fullGetTableStats(dbName.toUpperCase(),
                                       "t_ap_warn_detail".toUpperCase());
    }

    /**
     * 判断单用户下载应用是否存在异常
     * 
     * 单用户下载应用异常 需要同时满足以下规则： 1. 商品当日下载次数 > 200（可配置） 2.当日相同手机号码下载此商品次数 > 10（可配置）
     * 3. 第2点的手机号码集当日下载此商品次数总和 > 此商品当日下载次数的30%（可配置）
     * 
     * @param element 应用数据信息
     * @return 是/否
     * @throws BOException
     */
    private String isSingleUserDownloadWarn(ApWarnDetVO element)
                    throws BOException
    {
        StringBuffer retString = new StringBuffer(ApWarnDetConstants.WARN_DET_NO);
        int dayDownloadNum = 0;
        int userDownloadNum = 0;
        int userQuotietyNum = 0;

        try
        {
            // 得到商品当日下载次数
            dayDownloadNum = ApWarnDetDAO.getInstance()
                                         .getDayDownloadNum(element.getContentId());
            logger.info("内容id:" + element.getContentId() + " 当日下载次数为"
                        + dayDownloadNum);
        }
        catch (DAOException e)
        {
            throw new BOException("得到商品当日下载次数时出错", e);
        }

        // 如果大于当日下载次数指標
        if (dayDownloadNum > ApWarnDetConfig.MAX_DOWNLOAD_DAY)
        {
            try
            {
                // 得到同手机号下载次数
                userDownloadNum = ApWarnDetDAO.getInstance()
                                              .getUserDownloadNum(element.getContentId());
                logger.info("内容id:" + element.getContentId() + " 当日同手机号下载最多次数为"
                            + userDownloadNum);
            }
            catch (DAOException e)
            {
                throw new BOException("得到同手机号下载次数时出错", e);
            }

            // 如果大于当日同手机号下载数指標
            if (userDownloadNum > ApWarnDetConfig.MAX_DOWNLOAD_USER)
            {
                try
                {
                    // 同手机号下载与总下载比例
                    userQuotietyNum = ApWarnDetDAO.getInstance()
                                                  .getUserQuotietyNum(element.getContentId(),
                                                                      ApWarnDetConfig.MAX_DOWNLOAD_USER);
                    logger.info("内容id:" + element.getContentId()
                                + " 当日同手机号下载与总下载比例为" + userQuotietyNum);
                }
                catch (DAOException e)
                {
                    throw new BOException("得到同手机号下载与总下载比例时出错", e);
                }

                // 如果大于同手机号下载与总下载比例指標
                if (userQuotietyNum > ApWarnDetConfig.MAX_USER_DOWNLOAD_QUOTIETY)
                {
                    // 组装预警描述信息
                    retString.delete(0, 3);
                    retString.append(ApWarnDetConstants.WARN_DET_YES)
                             .append(ApWarnDetConstants.WARN_DET_INTERPUNCTION);
                    retString.append(ApWarnDetConstants.WARN_DET_DOWNLOAD_DAY)
                             .append(dayDownloadNum)
                             .append(ApWarnDetConstants.WARN_DET_INTERPUNCTION);
                    retString.append(ApWarnDetConstants.WARN_DET_DOWNLOAD_USER)
                             .append(userDownloadNum)
                             .append(ApWarnDetConstants.WARN_DET_INTERPUNCTION);
                    retString.append(ApWarnDetConstants.WARN_DET_USER_DOWNLOAD_QUOTIETY)
                             .append(userQuotietyNum)
                             .append(ApWarnDetConstants.WARN_DET_PERCENT)
                             .append(ApWarnDetConstants.WARN_DET_INTERPUNCTION);
                    downloadWarn++;
                }
            }
        }
        logger.info("内容id" + element.getContentId() + "判断单用户下载应用是否存在异常:"
                    + retString.toString());

        return retString.toString();
    }

    /**
     * 判断是否有连号消费的现象
     * 
     * 连号消费现象（稽核标准：对于预警出来的应用需要明确此具体标准值） 1、手机号码相连用户超过500个（可配置）
     * 
     * @param element 应用数据信息
     * @return 是/否
     * @throws BOException
     */
    private String isSeriesNumWarn(ApWarnDetVO element) throws BOException
    {
        StringBuffer retString = new StringBuffer(ApWarnDetConstants.WARN_DET_NO);
        int seriesNum = 0;

        try
        {
            // 得到连号数
            seriesNum = ApWarnDetDAO.getInstance()
                                    .getSeriesNum(element.getContentId());
            logger.info("内容id:" + element.getContentId() + " 得到连号消费次数为"
                        + seriesNum);
        }
        catch (DAOException e)
        {
            throw new BOException("得到连号消费次数时出错", e);
        }

        // 如果大于连号指标
        if (seriesNum > ApWarnDetConfig.MAX_SERIES_USER)
        {
            // 组装预警描述信息
            retString.delete(0, 3);
            retString.append(ApWarnDetConstants.WARN_DET_YES)
                     .append(ApWarnDetConstants.WARN_DET_INTERPUNCTION);
            retString.append(ApWarnDetConstants.WARN_DET_SERIES_USER)
                     .append(seriesNum)
                     .append(ApWarnDetConstants.WARN_DET_INTERPUNCTION);
            seriesNumWarn++;
        }

        logger.info("内容id:" + element.getContentId() + " 判断是否有连号消费的现象:"
                    + retString.toString());

        return retString.toString();
    }

    /**
     * 判断是否有非指定地市消费增幅巨大
     * 
     * @param citys 商品下载量前X的地市
     * @return
     */
    private String compareCityWarn(List citys)
    {
        // 得到指定的允许消费增幅巨大的城市
        String[] city = ApWarnDetConfig.MAX_DOWNLOAD_CITY.split(":");
        StringBuffer str = new StringBuffer();
        boolean isHave = false;

        // 迭代数据库中消费增幅城市
        for (Iterator iter = citys.iterator(); iter.hasNext();)
        {
            String data = ( String ) iter.next();

            isHave = false;

            // 迭代指定消费增幅城市
            for (int i = 0; i < city.length; i++)
            {
                String ci = city[i];

                // 如果不属于指定消费增幅城市
                if (ci.indexOf(data) != -1)
                {
                    isHave = true;
                }
            }

            if (!isHave)
            {
                str.append(data).append(",");
            }
        }

        return str.toString();
    }

    /**
     * 判断是否有非指定地市消费增幅巨大的现象
     * 
     * 地市消费增幅巨大（稽核标准：对于预警出来的应用需要明确此具体标准值） 1、商品下载量前三的地市中存在非广州、深圳、东莞、佛山的地市（可配置）
     * 
     * @param element 应用数据信息
     * @return 是/否
     * @throws BOException
     */
    public String isCityDownloadIncreaseWarn(ApWarnDetVO element)
                    throws BOException
    {
        StringBuffer retString = new StringBuffer(ApWarnDetConstants.WARN_DET_NO);
        List list = null;
        String temp;

        try
        {
            list = ApWarnDetDAO.getInstance()
                               .getCityDownloadIncrease(element.getContentId());
        }
        catch (DAOException e)
        {
            throw new BOException("得到是否有非指定地市消费增幅巨大的现象时出错", e);
        }

        // 得到非指定的消费增幅城市
        temp = compareCityWarn(list);

        // 判断是否有非指定地市消费增幅巨大
        if (!"".equals(temp))
        {
            // 组装预警描述信息
            retString.delete(0, 3);
            retString.append(ApWarnDetConstants.WARN_DET_YES)
                     .append(ApWarnDetConstants.WARN_DET_INTERPUNCTION);
            retString.append(ApWarnDetConstants.WARN_DET_DOWNLOAD_CITY)
                     .append(temp.substring(0, temp.length() - 1))
                     .append(ApWarnDetConstants.WARN_DET_INTERPUNCTION);
            cityWarn++;
        }

        logger.info("内容id" + element.getContentId() + "判断是否有地市消费增幅巨大的现象:"
                    + retString.toString());

        return retString.toString();
    }

    /**
     * 用于判断当前内容id的下载时间段数据是否存在异常
     * 
     * @param timeDate 存储时间段与下载量的集合
     * @return 异常信息
     */
    private String compareTimeWarn(List timeDate, String contentId)
    {
        // 得到标准的的峰值数据下载时间段 例11:00-12:00
        String[] times = ApWarnDetConfig.MAX_DOWNLOAD_TIME.split(",");
        StringBuffer retString = new StringBuffer();
        boolean isWarn = true;

        // 迭代时间段
        for (int j = 0; j < timeDate.size(); j++)
        {
            // 数据库中时间段峰值 例 19-100
            String[] date = String.valueOf(timeDate.get(j)).split("-");

            // 只取最高二个时间段
            if (j < 2)
            {
                for (int i = 0; i < times.length; i++)
                {
                    // 时间 例11:00
                    String[] time = times[i].split("-");

                    // 开始小时数 例11
                    int startT = Integer.parseInt(time[0].substring(0,
                                                                    time[0].lastIndexOf(":")));

                    // 结束小时数 例12
                    int endT = Integer.parseInt(time[1].substring(0,
                                                                  time[1].lastIndexOf(":")));

                    // 当前时段数 例 11
                    int dT = Integer.parseInt(date[0]);

                    // 如果在指定时段内。为正确跳出。
                    if (startT <= dT && dT < endT)
                    {
                        isWarn = false;
                        break;
                    }
                }

                // 如果不在指定时间段内
                if (isWarn)
                {
                    retString.append(date[0])
                             .append("时内的下载量=")
                             .append(date[1])
                             .append(" ");
                }

                isWarn = true;
            }

            logger.info("内容id:" + contentId + ",分时数据统计为:从" + date[0]
                        + "时开始一小时的下载量为" + date[1]);
        }

        return retString.toString();
    }

    /**
     * 判断是否有消费时间异常的现象
     * 
     * 消费时间固定： 1、分时统计出商品当天下载峰值时段；
     * 2、判断实际峰值时段是否属于正常峰值时段：11:00-12:00、17:00-18:00（可配置）
     * 
     * @param element 应用数据信息
     * @return 是/否
     * @throws BOException
     */
    public String isDownloadTimeWarn(ApWarnDetVO element) throws BOException
    {
        StringBuffer retString = new StringBuffer(ApWarnDetConstants.WARN_DET_NO);
        List timeDate = null;
        String str = null;

        try
        {
            timeDate = ApWarnDetDAO.getInstance()
                                   .getDownloadTime(element.getContentId());
        }
        catch (DAOException e)
        {
            throw new BOException("得到消费时间段信息时出错", e);
        }

        // 校验此内容下载时间段是否存在异常
        str = compareTimeWarn(timeDate, element.getContentId());

        // 判断是否有消费时间异常的现象
        if (!"".equals(str))
        {
            // 组装预警描述信息
            retString.delete(0, 3);
            retString.append(ApWarnDetConstants.WARN_DET_YES)
                     .append(ApWarnDetConstants.WARN_DET_INTERPUNCTION);
            retString.append(ApWarnDetConstants.WARN_DET_DOWNLOAD_TIME)
                     .append(str)
                     .append(ApWarnDetConstants.WARN_DET_INTERPUNCTION);
            timeWarn++;
        }

        logger.info("内容id:" + element.getContentId() + " 判断是否有消费时间段异常的现象:"
                    + retString.toString());

        return retString.toString();
    }

    /**
     * 判断是否有消费用户复叠率过大的现象
     * 
     * 下载用户重叠率（稽核标准：对于预警出来的应用需要明确此具体标准值）（此值供参考）
     * 数据背景：在判定为嫌疑刷榜的的应用中提取出同一AP的多个应用按照以下公式计算重叠率；
     * 计算公式：重叠率=（应用A与应用B）的用户数交集/A或B的下载用户数（AB为同一AP的嫌疑刷榜应用）
     * 
     * @param element 应用数据信息
     * @return 是/否
     * @throws BOException
     */
    private String isDownloadUserIteranceWarn(ApWarnDetVO element, String date)
                    throws BOException
    {
        StringBuffer retString = new StringBuffer(ApWarnDetConstants.WARN_DET_NO);
        int iterance = 0;

        try
        {
            // 如果缓存中存在指定SP的重叠率
            if (SPUSERITERANCEMAP.containsKey(element.getSpName()))
            {
                iterance = Integer.parseInt(String.valueOf(SPUSERITERANCEMAP.get(element.getSpName())));
            }
            else
            {
                if (ApWarnDetDAO.getInstance()
                                .getCountContentBySPName(element.getSpName(),
                                                         date) > 1)
                {
                    // 得到消费用户复叠率
                    iterance = ApWarnDetDAO.getInstance()
                                           .getDownloadUserIterance(element.getSpName(),
                                                                    date);
                }

                SPUSERITERANCEMAP.put(element.getSpName(),
                                      new Integer(iterance));

            }
        }
        catch (DAOException e)
        {
            throw new BOException("得到消费用户复叠率时出错", e);
        }
        logger.info("内容id:" + element.getContentId() + " 消费用户复叠率为" + iterance);

        // 是否有消费用户复叠率过大的现象
        if (iterance > ApWarnDetConfig.MAX_USER_ITERANCE_QUOTIETY)
        {
            // 组装预警描述信息
            retString.delete(0, 3);
            retString.append(ApWarnDetConstants.WARN_DET_YES)
                     .append(ApWarnDetConstants.WARN_DET_INTERPUNCTION);
            retString.append(ApWarnDetConstants.WARN_DET_USER_ITERANCE_QUOTIETY)
                     .append(iterance)
                     .append(ApWarnDetConstants.WARN_DET_PERCENT)
                     .append(ApWarnDetConstants.WARN_DET_INTERPUNCTION);
            downloadUserIteranceWarn++;
        }

        logger.info("内容id" + element.getContentId() + "是否有消费用户复叠率过大的现象:"
                    + retString.toString());

        return retString.toString();
    }

    /**
     * 是否存在下载量波动异常
     * 
     * @param element
     * @param warnList
     */
    private void isDownWarn(ApWarnDetVO element, List warnList)
    {
        // 与第一期信息中得到相关数据
        for (Iterator iter = warnList.iterator(); iter.hasNext();)
        {
            ApWarnVo temp = ( ApWarnVo ) iter.next();

            // 如果有相同的存入
            if (element.getContentId().equals(temp.getContentId()))
            {
                element.setIsDownWarn(temp.getWarnDesc());
                break;
            }
        }
    }

    /**
     * 是否存在着下载时段分布异常（判断在凌晨0点至8点应用下载量超过全天下载量的30%，即存在异常）
     * 
     * @param element
     * @return
     */
    private String isTimeWarn(ApWarnDetVO element) throws BOException
    {
        StringBuffer retString = new StringBuffer(ApWarnDetConstants.WARN_DET_NO);
        int str = 0;

        try
        {
            str = ApWarnDetDAO.getInstance()
                              .getTimeWarn(element.getContentId());
        }
        catch (DAOException e)
        {
            throw new BOException("得到是否存在着下载时段分布异常时出错", e);
        }

        // 判断是否有消费时间异常的现象 判断在凌晨0点至8点应用下载量超过全天下载量的30%，即存在异常
        if (str > 30)
        {
            // 组装预警描述信息
            retString.delete(0, 3);
            retString.append(ApWarnDetConstants.WARN_DET_YES)
                     .append(ApWarnDetConstants.WARN_DET_INTERPUNCTION);
            retString.append(ApWarnDetConstants.WARN_DET_TIME)
                     .append(str)
                     .append("%")
                     .append(ApWarnDetConstants.WARN_DET_INTERPUNCTION);
            timeWarn++;
        }

        logger.info("内容id:" + element.getContentId() + " 判断是否有消费时间段异常的现象:"
                    + retString.toString());

        return retString.toString();
    }

    /**
     * 用于返回当前内容的预警等级,预警规则
     * 
     * @param element
     * @return
     */
    private void getWarnDet(ApWarnDetVO element)
    {
        int retI = 0;
        StringBuffer retS = new StringBuffer("1");

        // 如果单用户下载应用异常
        if (!ApWarnDetConstants.WARN_DET_NO.equals(element.getIsSingleUserDownloadWarn()))
        {
            retI += 9;
            retS.append(",2");
        }

        // 下载时段分布是否存在异常
        if (!ApWarnDetConstants.WARN_DET_NO.equals(element.getIsDownloadTimeWarn()))
        {
            retI += 9;
            retS.append(",3");
        }

        // 下载用户重叠率是否存在异常
        if (!ApWarnDetConstants.WARN_DET_NO.equals(element.getIsDownloadUserIteranceWarn()))
        {
            retI += 9;
            retS.append(",4");
        }

        // 是否存在连号消费现象
        if (!ApWarnDetConstants.WARN_DET_NO.equals(element.getIsSeriesNumWarn()))
        {
            retI += 11;
            retS.append(",5");
        }

        // 设计内容预警等级
        if (retI > 10)
        {
            element.setWarnGrade(ApWarnDetConstants.WARN_GRADE_VALIDATE);
        }
        else if (retI > 0)
        {
            element.setWarnGrade(ApWarnDetConstants.WARN_GRADE_SUSPICION);
        }
        else
        {
            element.setWarnGrade("");
        }

        // 设计内容预警规则
        element.setWarnRule(retS.toString());
    }

    /**
     * 处理单条一期预警数据 得到统计信息
     * 
     * @param element 预警数据信息
     * @param date 前一日期
     * @throws BOException
     */
    private void transactFirstDate(ApWarnDetVO element, String[] date,
                                   List warnList) throws BOException
    {
        // 是否存在下载量波动异常
        isDownWarn(element, warnList);

        // 单用户下载应用异常(下载号码分布)
        element.setIsSingleUserDownloadWarn(isSingleUserDownloadWarn(element));

        // 下载时段分布
        element.setIsDownloadTimeWarn(isTimeWarn(element));

        // 下载用户重叠率（稽核标准：对于预警出来的应用需要明确此具体标准值）（此值供参考）
        element.setIsDownloadUserIteranceWarn(isDownloadUserIteranceWarn(element,
                                                                         date[1]));
        // 连号消费现象（稽核标准：对于预警出来的应用需要明确此具体标准值）
        element.setIsSeriesNumWarn(isSeriesNumWarn(element));

        // 用于返回当前内容的预警等级,预警规则
        getWarnDet(element);
    }

    /**
     * 返回用于写入统计文件的数据集合
     * 
     * @param date 当前日期，前一日期
     * @return
     * @throws BOException
     */
    private List getFileDateList(String[] date, List warnList)
                    throws BOException
    {
        logger.info("得到一期数据集合");

        // 用于存放返回数据集合
        List retList = new ArrayList();
        List firstDateList = null;

        try
        {
            // 得到前一日的一期预警数据，主要返回内容id与spName
            firstDateList = ApWarnDetDAO.getInstance()
                                        .getFirstDateList(date[1]);
        }
        catch (DAOException e)
        {
            throw new BOException("得到前一日的一期预警数据时出错", e);
        }

        logger.info("处理二期数据");

        // 单独处理每一条一期预警数据
        for (Iterator iter = firstDateList.iterator(); iter.hasNext();)
        {
            ApWarnDetVO element = ( ApWarnDetVO ) iter.next();

            try
            {
                // 处理单条内容二期预警判断。。。。
                transactFirstDate(element, date, warnList);
            }
            catch (BOException e)
            {
                logger.info("校验指标时发生异常。内容数据id为：", element.getContentId());
            }

            // 存入返回数据集合
            retList.add(element);
        }

        return retList;
    }

    /**
     * 导入预警二期有效数据到文件中
     * 
     * @param list 预警二期有效数据
     * @param file 文件名
     * @param date 预警日期
     * @throws SocketException
     * @throws BOException
     */
    private void exportToExcel(List list, File file, String date)
                    throws SocketException, BOException
    {

        WritableWorkbook workbook = null;
        WritableSheet sheet = null;
        ApWarnDetVO vo = null;

        // 单个sheet最大行数
        int maxSheetRowSize = 65534;
        int sheetNumber = 0;

        String[] titles = new String[] { "时间", "商品名称", "AP名称", "应用ID", "应用属性",
                        "预警等级", "符合规则", "规则1、是否存在下载量波动异常", "规则2、是否存在下载号码分布异常",
                        "规则3、是否存在下载时段分布异常", "规则4、是否非存在下载用户重复率异常",
                        "规则5、是否非存在号码连号异常" };
        try
        {
            // 创建文件
            workbook = Workbook.createWorkbook(file);

            for (int i = 0; i < list.size(); i++)
            {
                // 当前sheet的行数。
                int rowNumber = i % maxSheetRowSize;

                // 如果大于sheet最大允许的行数，则顺序创建接下来的sheet
                if (rowNumber == 0)
                {
                    sheet = workbook.createSheet("sheet" + (sheetNumber + 1),
                                                 sheetNumber);
                    sheetNumber++;

                    for (int j = 0; j < titles.length; j++)
                    {
                        // 第一列 列数 第二列 行数 第三列 内容
                        sheet.addCell(new Label(j, 0, titles[j]));
                    }
                }

                vo = ( ApWarnDetVO ) list.get(i);
                sheet.addCell(new Label(0, rowNumber + 1, date));
                sheet.addCell(new Label(1, rowNumber + 1, vo.getContentName()));
                sheet.addCell(new Label(2, rowNumber + 1, vo.getSpName()));
                sheet.addCell(new Label(3, rowNumber + 1, vo.getContentId()));
                sheet.addCell(new Label(4, rowNumber + 1, vo.getPayType()));
                sheet.addCell(new Label(5, rowNumber + 1, vo.getWarnGrade()));
                sheet.addCell(new Label(6, rowNumber + 1, vo.getWarnRule()));
                sheet.addCell(new Label(7, rowNumber + 1, vo.getIsDownWarn()));
                sheet.addCell(new Label(8,
                                        rowNumber + 1,
                                        vo.getIsSingleUserDownloadWarn()));
                sheet.addCell(new Label(9,
                                        rowNumber + 1,
                                        vo.getIsDownloadTimeWarn()));
                sheet.addCell(new Label(10,
                                        rowNumber + 1,
                                        vo.getIsDownloadUserIteranceWarn()));
                sheet.addCell(new Label(11,
                                        rowNumber + 1,
                                        vo.getIsSeriesNumWarn()));
            }

            if (sheet == null)
            {
                sheet = workbook.createSheet("sheet" + (sheetNumber + 1),
                                             sheetNumber);
            }

        }
        catch (SocketException e)
        {
            // 当用户选择取消的时候会出现这个异常，该异常不算错误。
            throw e;
        }
        catch (Exception e)
        {
            throw new BOException("创建excel文件出错", e);
        }
        finally
        {
            try
            {
                workbook.write();
                workbook.close();
            }
            catch (Exception e)
            {
                throw new BOException("保存excel数据有误", e);
            }
        }
    }

    /**
     * 根据数据集合生成统计文件
     * 
     * @param date 当前日期，前一日期
     * @param list 数据集合
     * @return
     * @throws BOException
     */
    private File createFileByList(String[] date, List list) throws BOException
    {
        File file = null;

        // 创建文件
        file = new File(ApWarnConfig.localAttachFile + File.separator + date[1]
                        + "ApWarnDet" + PublicUtil.getCurDateTime("yyyy_MM_dd_HH_ss") + ".xls");

        // 导入数据到文件中
        try
        {
            exportToExcel(list, file, date[1]);
        }
        catch (SocketException e)
        {
            throw new BOException("导入数据到文件中时当用户选择取消", e);
        }

        return file;
    }

    /**
     * 准备数据
     * 
     */
    private void start()
    {
        // 清除缓存信息
        SPUSERITERANCEMAP.clear();
        RETMAP.clear();
        cityWarn = 0;
        timeWarn = 0;
        downloadWarn = 0;
        seriesNumWarn = 0;
        downloadUserIteranceWarn = 0;
    }

    /**
     * 用于组装返回全信息
     * 
     * @param File 生成文件的信息
     * @param list 数据集合
     */
    private void end(File warnDetailFile, List list)
    {
        RETMAP.put("file", warnDetailFile);

        // 嫌疑
        int suspicionNumber = 0;
        int suspicionNumber_2 = 0;
        int suspicionNumber_3 = 0;
        int suspicionNumber_4 = 0;

        // 确认
        int validateNumber = 0;
        int validateNumber_2_3 = 0;
        int validateNumber_3_4 = 0;
        int validateNumber_2_4 = 0;
        int validateNumber_2_3_4 = 0;
        int validateNumber_5 = 0;

        // 循环判断
        for (Iterator iter = list.iterator(); iter.hasNext();)
        {
            ApWarnDetVO element = ( ApWarnDetVO ) iter.next();

            String temp = element.getWarnRule();

            // 如果为嫌疑
            if (ApWarnDetConstants.WARN_GRADE_SUSPICION.equals(element.getWarnGrade()))
            {
                suspicionNumber++;

                if (temp.indexOf("2") > 0)
                {
                    suspicionNumber_2++;
                }
                else if (temp.indexOf("3") > 0)
                {
                    suspicionNumber_3++;
                }
                else if (temp.indexOf("4") > 0)
                {
                    suspicionNumber_4++;
                }
            }
            // 如果为确认
            else if (ApWarnDetConstants.WARN_GRADE_VALIDATE.equals(element.getWarnGrade()))
            {
                validateNumber++;

                if (temp.indexOf("2") > 0 && temp.indexOf("3") > 0
                    && temp.indexOf("4") > 0)
                {
                    validateNumber_2_3_4++;
                }
                else if (temp.indexOf("2") > 0 && temp.indexOf("3") > 0)
                {
                    validateNumber_2_3++;
                }
                else if (temp.indexOf("3") > 0 && temp.indexOf("4") > 0)
                {
                    validateNumber_3_4++;
                }
                else if (temp.indexOf("2") > 0 && temp.indexOf("4") > 0)
                {
                    validateNumber_2_4++;
                }

                if (temp.indexOf("5") > 0)
                {
                    validateNumber_5++;
                }
            }
        }
        
        RETMAP.put("suspicionNumber", String.valueOf(suspicionNumber));
        RETMAP.put("suspicionNumber_2", String.valueOf(suspicionNumber_2));
        RETMAP.put("suspicionNumber_3", String.valueOf(suspicionNumber_3));
        RETMAP.put("suspicionNumber_4", String.valueOf(suspicionNumber_4));
        
        RETMAP.put("validateNumber", String.valueOf(validateNumber));
        RETMAP.put("validateNumber_2_3", String.valueOf(validateNumber_2_3));
        RETMAP.put("validateNumber_3_4", String.valueOf(validateNumber_3_4));
        RETMAP.put("validateNumber_2_4", String.valueOf(validateNumber_2_4));
        RETMAP.put("validateNumber_2_3_4", String.valueOf(validateNumber_2_3_4));
        RETMAP.put("validateNumber_5", String.valueOf(validateNumber_5));
    }

    /**
     * 生成预警详情主体方法
     * 
     * @throws BOException
     */
    public Map init(List warnList) throws BOException
    {
        logger.info("开始执行二期预警");

        File warnDetailFile = null;

        List dataList = null;

        // 开始前准备
        start();

        // 载入配置项
        ApWarnDetConfig.loadConfig();

        // 得到当前日期和下一日期的字符型式
        String[] date = getDataString();

        try
        {
            // 根据第一期有预警嫌疑内容id，得到报表数据，存入本地临时表中。
            copyReportDateToTemp(date);
        }
        catch (DAOException e)
        {
            throw new BOException("存预警数据至存入本地临时表过程出错", e);
        }

        // 要地处理数据计算统计
        dataList = getFileDateList(date, warnList);

        // 写入文件
        warnDetailFile = createFileByList(date, dataList);

        logger.info("二期预警执行结束。生成文件为=" + warnDetailFile.getName());

        // 返回前组装信息准备
        end(warnDetailFile, dataList);

        return RETMAP;
    }
}
