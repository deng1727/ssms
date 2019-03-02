package com.aspire.ponaadmin.web.dataexport.basefile.task;

import java.util.Date;
import java.util.TimerTask;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.dataexport.basefile.impl.CategoryBaseFile;
import com.aspire.ponaadmin.web.dataexport.basefile.impl.ReferenceBaseFile;
import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.util.PublicUtil;

public class LogBaseFileTask extends TimerTask
{
    /**
     * 记录日志的实例对象
     */
    private static JLogger log = LoggerFactory.getLogger(LogBaseFileTask.class);

    public void run()
    {
        StringBuffer message = new StringBuffer();
        Date startDate = new Date();

        try
        {
            CategoryBaseFile category = new CategoryBaseFile();
            category.createFile();
            message.append("全量货架维表日志文件生成  成功<br>");
        }
        catch (Exception e)
        {
            log.error("全量货架维表日志文件生成出错", e);
            message.append("全量货架维表日志文件生成  出错<br>")
                   .append("<p>失败原因：<br>")
                   .append(e.getMessage());
        }
        
        try
        {
            ReferenceBaseFile reference = new ReferenceBaseFile();
            reference.createFile();
            message.append("全量商品维表日志文件生成  成功<br>");
        }
        catch (Exception e)
        {
            log.error("全量商品维表日志文件生成出错", e);
            message.append("全量商品维表日志文件生成  出错<br>")
                   .append("<p>失败原因：<br>")
                   .append(e.getMessage());
        }


        String mailTitle;
        // 发送邮件表示本次处理结束
        Date endDate = new Date();
        
        StringBuffer sb = new StringBuffer();
        mailTitle = "日志维表文件生成结果";

        sb.append("开始时间：");
        sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
        sb.append(",结束时间：");
        sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
        sb.append("。<h4>处理结果：</h4>");
        sb.append(message);

        log.info(sb.toString());
        
        Mail.sendMail(mailTitle, sb.toString(), LogBaseFileConfig.mailTo);
    }

}
