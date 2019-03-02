
package com.aspire.ponaadmin.web.dataexport.basefile.task;

import java.util.Date;
import java.util.TimerTask;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.dataexport.basefile.impl.AppBaseFile;
import com.aspire.ponaadmin.web.dataexport.basefile.impl.DeviceBaseFile;
import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.util.PublicUtil;

public class BaseFileTask extends TimerTask
{
    /**
     * 记录日志的实例对象
     */
    private static JLogger log = LoggerFactory.getLogger(BaseFileTask.class);

    public void run()
    {
        StringBuffer message = new StringBuffer();
        Date startDate = new Date();

        try
        {
            DeviceBaseFile device = new DeviceBaseFile();
            device.createFile();
            message.append("机型信息同步文件生成  成功<br>");
        }
        catch (Exception e)
        {
            log.error("机型信息同步文件生成出错", e);
            message.append("机型信息同步文件生成  出错<br>")
                   .append("<p>失败原因：<br>")
                   .append(e.getMessage());
        }

        try
        {
            AppBaseFile app = new AppBaseFile();
            app.createFile();
            message.append("应用信息同步生成文件  成功<br>");
        }
        catch (Exception e)
        {
            log.error("应用信息同步生成文件出错", e);
            message.append("应用信息同步生成文件  出错<br>")
                   .append("<p>失败原因：<br>")
                   .append(e.getMessage());
        }

        String mailTitle;
        // 发送邮件表示本次处理结束
        Date endDate = new Date();
        
        StringBuffer sb = new StringBuffer();
        mailTitle = "浙江MSTORE平台应用文件生成结果";

        sb.append("开始时间：");
        sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
        sb.append(",结束时间：");
        sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
        sb.append("。<h4>处理结果：</h4>");
        sb.append(message);

        log.info(sb.toString());
        
        Mail.sendMail(mailTitle, sb.toString(), BaseFileConfig.mailTo);
    }

}
