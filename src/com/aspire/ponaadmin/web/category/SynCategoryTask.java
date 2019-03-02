
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
     * ��δ��ִ��ʱ�䡣
     */
    public static final int EXECUTED = 1;

    /**
     * ����û����Ч
     */
    public static final int INEFFECTIVE = 2;

    /**
     * �ɹ�����
     */
    public static final int UPDATESUCESS = 0;

    /**
     * ��¼��־��ʵ������
     */
    protected static JLogger LOG = LoggerFactory.getLogger(SynCategoryTask.class);

    public void run()
    {
        Date startDate = new Date();

        LOG.info("����ͬ����Ӿ�Ʒ�⿪ʼ��startTime:"
                 + PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));

        try
        {
            // ����Ʒ������
            if (!"".equals(SynCategoryConfig.WCATEGORYID))
            {
                CategoryRuleBO.getInstance()
                              .updateSynCategory(SynCategoryConfig.WCATEGORYID,
                                                 "W");
                LOG.info("���η���ͬ����Ӿ�Ʒ��ִ��WWW�Ż�������ɹ�");
            }

            if (!"".equals(SynCategoryConfig.ACATEGORYID))
            {
                CategoryRuleBO.getInstance()
                              .updateSynCategory(SynCategoryConfig.ACATEGORYID,
                                                 "A");
                LOG.info("���η���ͬ����Ӿ�Ʒ��ִ��WAP�Ż�������ɹ�");
            }

            if (!"".equals(SynCategoryConfig.OCATEGORYID))
            {
                CategoryRuleBO.getInstance()
                              .updateSynCategory(SynCategoryConfig.OCATEGORYID,
                                                 "O");
                LOG.info("���η���ͬ����Ӿ�Ʒ��ִ��MO�Ż�������ɹ�");
            }

            if (!"".equals(SynCategoryConfig.OCATEGORYID)
                || !"".equals(SynCategoryConfig.ACATEGORYID)
                || !"".equals(SynCategoryConfig.WCATEGORYID))
            {
                LOG.info("���η���ͬ����Ӿ�Ʒ��ִ�н�����ɹ�");
            }
            else
            {
                LOG.info("���η���ͬ����Ӿ�Ʒ��ִ�н�����޿�ִ�е�ͬ����Ʒ��");
            }

            Date endDate = new Date();
            StringBuffer sb = new StringBuffer();

            sb.append("��ʼʱ�䣺");
            sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
            sb.append(",����ʱ�䣺");
            sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
            sb.append("��<p>���������ɹ�<br>");

            Mail.sendMail("����ͬ����Ӿ�Ʒ��ɹ�",
                          sb.toString(),
                          MailConfig.getInstance().getMailToArray());
        }
        catch (Exception e)
        {
            LOG.error("����ͬ����Ӿ�Ʒ��ʧ��", e);
            Mail.sendMail("����ͬ����Ӿ�Ʒ��ʧ��",
                          "����ͬ����Ӿ�Ʒ��ʧ��,����ϵ����Ա",
                          MailConfig.getInstance().getMailToArray());
        }
    }
}
