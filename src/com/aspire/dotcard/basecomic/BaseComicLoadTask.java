package com.aspire.dotcard.basecomic;

import java.util.Date;
import java.util.TimerTask;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basecomic.bo.AdapterImportBO;
import com.aspire.dotcard.basecomic.bo.BlackBO;
import com.aspire.dotcard.basecomic.bo.BrandContentImportBO;
import com.aspire.dotcard.basecomic.bo.BrandImportBO;
import com.aspire.dotcard.basecomic.bo.CPImportBO;
import com.aspire.dotcard.basecomic.bo.ChapterImportBO;
import com.aspire.dotcard.basecomic.bo.ComicSeriesImportBO;
import com.aspire.dotcard.basecomic.bo.FirstImportBO;
import com.aspire.dotcard.basecomic.bo.InfoImportBO;
import com.aspire.dotcard.basecomic.bo.RankImportBO;
import com.aspire.dotcard.basecomic.bo.StatisticsImportBO;
import com.aspire.dotcard.basecomic.bo.TVSeriesImportBO;
import com.aspire.dotcard.basecomic.bo.ThemeImportBO;
import com.aspire.dotcard.basecomic.bo.TopicContentImportBO;
import com.aspire.dotcard.basecomic.bo.TopicImportBO;
import com.aspire.dotcard.basecomic.common.BaseComicFtpProcessor;
import com.aspire.dotcard.basecomic.common.Const;
import com.aspire.dotcard.basecomic.dao.BaseComicDBOpration;
import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.util.PublicUtil;

public class BaseComicLoadTask extends TimerTask
{

    /**
     * 日志引用
     */
    protected static JLogger logger = LoggerFactory.getLogger(BaseComicLoadTask.class);

    /**
     * 覆盖run运行方法
     */
    public void run()
    {
        StringBuffer msgInfo = new StringBuffer();
        ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("dmbase");

        int i = 0;
        Date startDate = new Date();
        try
        {
            BaseComicDBOpration dao = new BaseComicDBOpration();

            CPImportBO cp = new CPImportBO();
            try
            {
                cp.importFile();
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                logger.error("CP信息接口同步出错！", e);
            }

            msgInfo.append("<br>[" + (++i) + "]CP信息接口同步情况：<br>");
            msgInfo.append(cp.getMsgInfo());

            ComicSeriesImportBO comicSeries = new ComicSeriesImportBO();
            try
            {
                comicSeries.importFile();
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                logger.error("漫画接口同步出错！", e);
            }
            msgInfo.append("<br>[" + (++i) + "]漫画书接口同步情况：<br>");
            msgInfo.append(comicSeries.getMsgInfo());

            TVSeriesImportBO tvSeries = new TVSeriesImportBO();
            try
            {
                tvSeries.importFile();
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                logger.error("动画片接口同步出错！", e);
            }
            msgInfo.append("<br>[" + (++i) + "]动画片接口同步情况：<br>");
            msgInfo.append(tvSeries.getMsgInfo());

            ThemeImportBO theme = new ThemeImportBO();
            try
            {
                theme.importFile();
                dao.addThemeReference();
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                logger.error("主题接口同步出错！", e);
            }
            msgInfo.append("<br>[" + (++i) + "]主题接口同步情况：<br>");
            msgInfo.append(theme.getMsgInfo());

            dao.addComicSeriesReference();
            dao.addTVSeriesReference();

            StatisticsImportBO statistics = new StatisticsImportBO();
            try
            {
                statistics.importFile();
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                logger.error("动漫统计接口同步出错！", e);
            }
            msgInfo.append("<br>[" + (++i) + "]动漫统计接口同步情况：<br>");
            msgInfo.append(statistics.getMsgInfo());

            InfoImportBO info = new InfoImportBO();
            try
            {
                info.importFile();
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                logger.error("资讯接口同步出错！", e);
            }
            msgInfo.append("<br>[" + (++i) + "]资讯接口同步情况：<br>");
            msgInfo.append(info.getMsgInfo());

            FirstImportBO first = new FirstImportBO();
            try
            {
                first.importFile();
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                logger.error("首发接口同步出错！", e);
            }
            msgInfo.append("<br>[" + (++i) + "]首发接口同步情况：<br>");
            msgInfo.append(first.getMsgInfo());

            ChapterImportBO chapter = new ChapterImportBO();
            try
            {
                chapter.importFile();
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                logger.error("章节接口同步出错！", e);
            }
            msgInfo.append("<br>[" + (++i) + "]章节接口同步情况：<br>");
            msgInfo.append(chapter.getMsgInfo());

            RankImportBO rank = new RankImportBO();
            try
            {
                rank.importFile();
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                logger.error("排行榜接口同步出错！", e);
            }
            msgInfo.append("<br>[" + (++i) + "]排行榜接口同步情况：<br>");
            msgInfo.append(rank.getMsgInfo());

            BrandImportBO brand = new BrandImportBO();
            try
            {
                brand.importFile();
                dao.updateParentCategory(Const.NAME_BRAND);
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                logger.error("品牌馆接口同步出错！", e);
            }
            msgInfo.append("<br>[" + (++i) + "]品牌馆接口同步情况：<br>");
            msgInfo.append(brand.getMsgInfo());

            BrandContentImportBO brandContent = new BrandContentImportBO();
            try
            {
                brandContent.importFile();
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                logger.error("品牌馆内容接口同步出错！", e);
            }
            msgInfo.append("<br>[" + (++i) + "]品牌馆内容接口同步情况：<br>");
            msgInfo.append(brandContent.getMsgInfo());

            // DeviceGroupImportBO group = new DeviceGroupImportBO();
            // try {
            // group.importFile();
            // } catch (Exception e) {
            // // TODO Auto-generated catch block
            // logger.error("机型组接口同步出错！",e);
            // }
            // msgInfo.append("<br>[" + (++i) + "]机型组接口同步情况：<br>");
            // msgInfo.append(group.getMsgInfo());

            // DeviceGroupItemImportBO device = new DeviceGroupItemImportBO();
            // try {
            // device.importFile();
            // } catch (Exception e) {
            // // TODO Auto-generated catch block
            // logger.error("机型接口同步出错！",e);
            // }
            // msgInfo.append("<br>[" + (++i) + "]机型接口同步情况：<br>");
            // msgInfo.append(device.getMsgInfo());

            AdapterImportBO adapter = new AdapterImportBO();
            try
            {
                adapter.importFile();
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                logger.error("呈现接口同步出错！", e);
            }
            msgInfo.append("<br>[" + (++i) + "]呈现接口同步情况：<br>");
            msgInfo.append(adapter.getMsgInfo());

            TopicImportBO topic = new TopicImportBO();

            try
            {
                topic.importFile();
                dao.updateParentCategory(Const.NAME_TOPIC);
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                logger.error("专题接口同步出错！", e);
            }
            msgInfo.append("<br>[" + (++i) + "]专题接口同步情况：<br>");
            msgInfo.append(topic.getMsgInfo());

            TopicContentImportBO topicContent = new TopicContentImportBO();
            try
            {
                topicContent.importFile();
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                logger.error("专题内容接口同步出错！", e);
            }
            msgInfo.append("<br>[" + (++i) + "]专题内容接口同步情况：<br>");
            msgInfo.append(topicContent.getMsgInfo());

            try
            {
                new BaseComicFtpProcessor().delFile();
                logger.info("成功删除SFTP动漫基地的前几天文件！");
            }
            catch (BOException e)
            {
                // TODO Auto-generated catch block
                logger.error("删除SFTP上的前几天的文件出错！", e);
            }//

            // dao.buildPortal();//这个是不需要的，花了时间，没有任何效果，主要原因是基地说他们给不了“门户”属性，后来又给.这样这个计算过程就没有必要。
            // add by aiyan 2012-11-20今天再来做内容门户属性，基地给的门户属性都是3，不准确。我用存储过程来算一遍。
            long s = System.currentTimeMillis();
            int num = dao.buildPortal();
            if (num == 0)
            {
                logger.info("用存储过程f_buildPortal构建门户属性成功！耗时：" + (System.currentTimeMillis() - s));
            }
            else
            {
                logger.info("用存储过程f_buildPortal构建门户属性失败！" + (System.currentTimeMillis() - s));
            }
            // 计算某个门户的货架数量要放在dao.buildPortal()这个方法后面哦。add by aiyan 2012-11-23
            dao.updateReferenceNum();
            logger.info("更新货架商品数量完毕！");
            
            
            //add by fanqh 20131122
            logger.info("执行黑名单定时操作开始！");
            try
            {
                System.out.println("====执行黑名单定时操作开始====");
                BlackBO.getInstance().delReference();
                System.out.println("====执行黑名单定时操作完成====");
            }
            catch (BOException e)
            {
                logger.error("执行黑名单定时任务，报错!",e);
            }
            logger.info("执行黑名单定时操作完成！");

        }
        finally
        {
            StringBuffer sb = new StringBuffer();
            sb.append("<br>");
            sb.append("开始时间：");
            sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
            sb.append(",结束时间：");
            sb.append(PublicUtil.getDateString(new Date(), "yyyy-MM-dd HH:mm:ss"));
            sb.append("<br>");
            sb.append("<br>");

            String str = sb.toString() + "动漫基地共[" + i + "]种文件需要导入<br>" + msgInfo.toString();
            logger.info(str);
            String subject = module.getItemValue("dmBaseSynSubject");
            String[] mailTo = module.getItemValue("dmBaseSynMailto").split(",");
            this.sendMail(str, mailTo, subject);
        }
    }

    /**
     * 发送邮件
     * 
     * @param mailContent,邮件内容
     */
    private void sendMail(String mailContent, String[] mailTo, String subject)
    {
        Mail.sendMail(subject, mailContent, mailTo);
    }

}
