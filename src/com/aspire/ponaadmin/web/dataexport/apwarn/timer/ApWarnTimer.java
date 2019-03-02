
package com.aspire.ponaadmin.web.dataexport.apwarn.timer;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.dataexport.apwarn.ApWarnConfig;
import com.aspire.ponaadmin.web.dataexport.apwarn.bo.ApWarnBo;
import com.aspire.ponaadmin.web.dataexport.apwarndetail.bo.ApWarnDetBO;
import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.util.DateUtil;

public class ApWarnTimer extends TimerTask
{

    protected static JLogger logger = LoggerFactory.getLogger(ApWarnTimer.class);

    public void run()
    {
        logger.debug("AP刷榜预警开始");
        String begin = DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
        List rs = new ArrayList();
        Map retMap = null;
        /*File atatch = null;*/
        StringBuffer mailContent = new StringBuffer();
        Calendar cl = Calendar.getInstance();
        cl.add(Calendar.DAY_OF_MONTH, -1);
        mailContent.append(DateUtil.formatDate(cl.getTime(), "MM月dd日")
                           + "MM应用刷量预警数据具体信息如下：");
        try
        {
            ApWarnBo.getInstance().initData();
            // 下载量波动大 查询免费商品 刷榜信息
            List free = ApWarnBo.getInstance().getFreeWarnApList();
            // 下载量波动大 查询付费商品刷榜信息
            List pay = ApWarnBo.getInstance().getPayWarnApList();

            rs.addAll(free);
            rs.addAll(pay);

            if (rs.size() > 0)
            {
                ApWarnBo.getInstance().addApWarnData(rs);
                /*atatch = new File(ApWarnConfig.localAttachFile
                                  + "/"
                                  + DateUtil.formatDate(cl.getTime(),
                                                        "yyyyMMdd")
                                  + "ApWarn.xls");
                if (atatch.exists())
                {
                    atatch.delete();
                }
                // 如果有刷榜商品 则生成邮件附件xsl文件
                ApWarnBo.getInstance().exportToExcel(rs, atatch);*/
            }

            // 生成预警详情主体方法
            retMap = ApWarnDetBO.getInstance().init(rs);
        }
        catch (Exception e)
        {
            logger.error("AP刷榜预警出错", e);
        }

        // 发送邮件
        String end = DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
        logger.debug("AP刷榜预警开始开始时间：" + begin + "，结束时间：" + end);

        mailContent.append("<br/>");
        mailContent.append("系统共预警嫌疑刷量应用" + ( String ) retMap.get("suspicionNumber") + "个；");
        mailContent.append("<br/>");
        mailContent.append("系统共预警确认刷量应用" + ( String ) retMap.get("validateNumber") + "个；");
        mailContent.append("<br/>");
        
        mailContent.append("嫌疑刷量各标准预警情况如下：");
        mailContent.append("<br/>");
        mailContent.append("符合“下载量波动异常”且同时符合“下载号码分布异常”标准，一共有" + ( String ) retMap.get("suspicionNumber_2")
                           + "个嫌疑刷量应用；");
        mailContent.append("<br/>");
        mailContent.append("符合“下载量波动异常”且同时符合“下载时段分布异常”标准，一共有" + ( String ) retMap.get("suspicionNumber_3")
                           + "个嫌疑刷量应用；");
        mailContent.append("<br/>");
        mailContent.append("符合“下载量波动异常”且同时符合“下载用户重复率异常”标准，一共有" + ( String ) retMap.get("suspicionNumber_4")
                           + "个嫌疑刷量应用；");
        mailContent.append("<br/>");
        mailContent.append("<br/>");
        
        mailContent.append("确认刷量各标准预警情况如下：");
        mailContent.append("<br/>");
        mailContent.append("符合“下载量波动异常”且同时还符合“下载号码分布异常”、“下载时段分布异常”标准，一共有"
                           + ( String ) retMap.get("validateNumber_2_3") + "个确认刷量应用；");
        mailContent.append("<br/>");
        mailContent.append("符合“下载量波动异常”且同时还符合“下载号码分布异常”、“下载用户重复率异常”标准，一共有"
                           + ( String ) retMap.get("validateNumber_2_4") + "个确认刷量应用；");
        mailContent.append("<br/>");
        mailContent.append("符合“下载量波动异常”且同时还符合“下载时段分布异常”、“下载用户重复率异常”标准，一共有"
                           + ( String ) retMap.get("validateNumber_3_4") + "个确认刷量应用；");
        mailContent.append("<br/>");
        mailContent.append("符合“下载量波动异常”且同时还符合“下载号码分布异常”、“下载时段分布异常”、“下载用户重复率异常”标准，一共有"
                           + ( String ) retMap.get("validateNumber_2_3_4") + "个确认刷量应用；");
        mailContent.append("<br/>");
        mailContent.append("符合“下载量波动异常”且同时还符合“连号消费异常”标准，一共有"
                           + ( String ) retMap.get("validateNumber_5") + "个确认刷量应用；");
        mailContent.append("<br/>");
        mailContent.append("<br/>");
        String mailTitle = DateUtil.formatDate(cl.getTime(), "MM月dd日")
                           + "MM应用刷量监控预警数据";

        logger.debug("开始发送邮件");
        if (rs.size() > 0)
        {
            Mail.sendMail(mailTitle,
                          mailContent.toString(),
                          ApWarnConfig.mailTo,
                          new File[] { ( File ) retMap.get("file") });
        }
        else
        {
            // 无附件
            Mail.sendMail(mailTitle,
                          mailContent.toString(),
                          ApWarnConfig.mailTo);
        }
        logger.debug("发送邮件结束");
        logger.debug("AP刷榜预警结束");
    }
}
