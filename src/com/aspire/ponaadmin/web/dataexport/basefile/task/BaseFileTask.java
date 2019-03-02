
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
     * ��¼��־��ʵ������
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
            message.append("������Ϣͬ���ļ�����  �ɹ�<br>");
        }
        catch (Exception e)
        {
            log.error("������Ϣͬ���ļ����ɳ���", e);
            message.append("������Ϣͬ���ļ�����  ����<br>")
                   .append("<p>ʧ��ԭ��<br>")
                   .append(e.getMessage());
        }

        try
        {
            AppBaseFile app = new AppBaseFile();
            app.createFile();
            message.append("Ӧ����Ϣͬ�������ļ�  �ɹ�<br>");
        }
        catch (Exception e)
        {
            log.error("Ӧ����Ϣͬ�������ļ�����", e);
            message.append("Ӧ����Ϣͬ�������ļ�  ����<br>")
                   .append("<p>ʧ��ԭ��<br>")
                   .append(e.getMessage());
        }

        String mailTitle;
        // �����ʼ���ʾ���δ������
        Date endDate = new Date();
        
        StringBuffer sb = new StringBuffer();
        mailTitle = "�㽭MSTOREƽ̨Ӧ���ļ����ɽ��";

        sb.append("��ʼʱ�䣺");
        sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
        sb.append(",����ʱ�䣺");
        sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
        sb.append("��<h4>��������</h4>");
        sb.append(message);

        log.info(sb.toString());
        
        Mail.sendMail(mailTitle, sb.toString(), BaseFileConfig.mailTo);
    }

}
