
package com.aspire.ponaadmin.web.category;

import java.util.Date;
import java.util.TimerTask;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.mail.MailConfig;
import com.aspire.ponaadmin.web.util.PublicUtil;

public class SynCategoryTask extends TimerTask
{

    /**
     * 还未到执行时间。
     */
    public static final int EXECUTED = 1;

    /**
     * 规则还没有生效
     */
    public static final int INEFFECTIVE = 2;

    /**
     * 成功更新
     */
    public static final int UPDATESUCESS = 0;

    /**
     * 记录日志的实例对象
     */
    protected static JLogger LOG = LoggerFactory.getLogger(SynCategoryTask.class);

    public void run()
    {
        Date startDate = new Date();

        LOG.info("分类同步添加精品库开始：startTime:"
                 + PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));

        try
        {
            // 处理精品库数据
            if (!"".equals(SynCategoryConfig.WCATEGORYID))
            {
                CategoryRuleBO.getInstance()
                              .updateSynCategory(SynCategoryConfig.WCATEGORYID,
                                                 "W");
                LOG.info("本次分类同步添加精品库执行WWW门户结果：成功");
            }

            if (!"".equals(SynCategoryConfig.ACATEGORYID))
            {
                CategoryRuleBO.getInstance()
                              .updateSynCategory(SynCategoryConfig.ACATEGORYID,
                                                 "A");
                LOG.info("本次分类同步添加精品库执行WAP门户结果：成功");
            }

            if (!"".equals(SynCategoryConfig.OCATEGORYID))
            {
                CategoryRuleBO.getInstance()
                              .updateSynCategory(SynCategoryConfig.OCATEGORYID,
                                                 "O");
                LOG.info("本次分类同步添加精品库执行MO门户结果：成功");
            }

            if (!"".equals(SynCategoryConfig.OCATEGORYID)
                || !"".equals(SynCategoryConfig.ACATEGORYID)
                || !"".equals(SynCategoryConfig.WCATEGORYID))
            {
                LOG.info("本次分类同步添加精品库执行结果：成功");
            }
            else
            {
                LOG.info("本次分类同步添加精品库执行结果：无可执行的同步精品库");
            }

            Date endDate = new Date();
            StringBuffer sb = new StringBuffer();

            sb.append("开始时间：");
            sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
            sb.append(",结束时间：");
            sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
            sb.append("。<p>处理结果：成功<br>");

            Mail.sendMail("分类同步添加精品库成功",
                          sb.toString(),
                          MailConfig.getInstance().getMailToArray());
        }
        catch (Exception e)
        {
            LOG.error("分类同步添加精品库失败", e);
            Mail.sendMail("分类同步添加精品库失败",
                          "分类同步添加精品库失败,请联系管理员",
                          MailConfig.getInstance().getMailToArray());
        }
    }
}
