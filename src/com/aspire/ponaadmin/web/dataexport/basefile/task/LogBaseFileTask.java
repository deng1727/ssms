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
     * ��¼��־��ʵ������
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
            message.append("ȫ������ά����־�ļ�����  �ɹ�<br>");
        }
        catch (Exception e)
        {
            log.error("ȫ������ά����־�ļ����ɳ���", e);
            message.append("ȫ������ά����־�ļ�����  ����<br>")
                   .append("<p>ʧ��ԭ��<br>")
                   .append(e.getMessage());
        }
        
        try
        {
            ReferenceBaseFile reference = new ReferenceBaseFile();
            reference.createFile();
            message.append("ȫ����Ʒά����־�ļ�����  �ɹ�<br>");
        }
        catch (Exception e)
        {
            log.error("ȫ����Ʒά����־�ļ����ɳ���", e);
            message.append("ȫ����Ʒά����־�ļ�����  ����<br>")
                   .append("<p>ʧ��ԭ��<br>")
                   .append(e.getMessage());
        }


        String mailTitle;
        // �����ʼ���ʾ���δ������
        Date endDate = new Date();
        
        StringBuffer sb = new StringBuffer();
        mailTitle = "��־ά���ļ����ɽ��";

        sb.append("��ʼʱ�䣺");
        sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
        sb.append(",����ʱ�䣺");
        sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
        sb.append("��<h4>��������</h4>");
        sb.append(message);

        log.info(sb.toString());
        
        Mail.sendMail(mailTitle, sb.toString(), LogBaseFileConfig.mailTo);
    }

}
