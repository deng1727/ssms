
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
        logger.debug("APˢ��Ԥ����ʼ");
        String begin = DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
        List rs = new ArrayList();
        Map retMap = null;
        /*File atatch = null;*/
        StringBuffer mailContent = new StringBuffer();
        Calendar cl = Calendar.getInstance();
        cl.add(Calendar.DAY_OF_MONTH, -1);
        mailContent.append(DateUtil.formatDate(cl.getTime(), "MM��dd��")
                           + "MMӦ��ˢ��Ԥ�����ݾ�����Ϣ���£�");
        try
        {
            ApWarnBo.getInstance().initData();
            // ������������ ��ѯ�����Ʒ ˢ����Ϣ
            List free = ApWarnBo.getInstance().getFreeWarnApList();
            // ������������ ��ѯ������Ʒˢ����Ϣ
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
                // �����ˢ����Ʒ �������ʼ�����xsl�ļ�
                ApWarnBo.getInstance().exportToExcel(rs, atatch);*/
            }

            // ����Ԥ���������巽��
            retMap = ApWarnDetBO.getInstance().init(rs);
        }
        catch (Exception e)
        {
            logger.error("APˢ��Ԥ������", e);
        }

        // �����ʼ�
        String end = DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
        logger.debug("APˢ��Ԥ����ʼ��ʼʱ�䣺" + begin + "������ʱ�䣺" + end);

        mailContent.append("<br/>");
        mailContent.append("ϵͳ��Ԥ������ˢ��Ӧ��" + ( String ) retMap.get("suspicionNumber") + "����");
        mailContent.append("<br/>");
        mailContent.append("ϵͳ��Ԥ��ȷ��ˢ��Ӧ��" + ( String ) retMap.get("validateNumber") + "����");
        mailContent.append("<br/>");
        
        mailContent.append("����ˢ������׼Ԥ��������£�");
        mailContent.append("<br/>");
        mailContent.append("���ϡ������������쳣����ͬʱ���ϡ����غ���ֲ��쳣����׼��һ����" + ( String ) retMap.get("suspicionNumber_2")
                           + "������ˢ��Ӧ�ã�");
        mailContent.append("<br/>");
        mailContent.append("���ϡ������������쳣����ͬʱ���ϡ�����ʱ�ηֲ��쳣����׼��һ����" + ( String ) retMap.get("suspicionNumber_3")
                           + "������ˢ��Ӧ�ã�");
        mailContent.append("<br/>");
        mailContent.append("���ϡ������������쳣����ͬʱ���ϡ������û��ظ����쳣����׼��һ����" + ( String ) retMap.get("suspicionNumber_4")
                           + "������ˢ��Ӧ�ã�");
        mailContent.append("<br/>");
        mailContent.append("<br/>");
        
        mailContent.append("ȷ��ˢ������׼Ԥ��������£�");
        mailContent.append("<br/>");
        mailContent.append("���ϡ������������쳣����ͬʱ�����ϡ����غ���ֲ��쳣����������ʱ�ηֲ��쳣����׼��һ����"
                           + ( String ) retMap.get("validateNumber_2_3") + "��ȷ��ˢ��Ӧ�ã�");
        mailContent.append("<br/>");
        mailContent.append("���ϡ������������쳣����ͬʱ�����ϡ����غ���ֲ��쳣�����������û��ظ����쳣����׼��һ����"
                           + ( String ) retMap.get("validateNumber_2_4") + "��ȷ��ˢ��Ӧ�ã�");
        mailContent.append("<br/>");
        mailContent.append("���ϡ������������쳣����ͬʱ�����ϡ�����ʱ�ηֲ��쳣�����������û��ظ����쳣����׼��һ����"
                           + ( String ) retMap.get("validateNumber_3_4") + "��ȷ��ˢ��Ӧ�ã�");
        mailContent.append("<br/>");
        mailContent.append("���ϡ������������쳣����ͬʱ�����ϡ����غ���ֲ��쳣����������ʱ�ηֲ��쳣�����������û��ظ����쳣����׼��һ����"
                           + ( String ) retMap.get("validateNumber_2_3_4") + "��ȷ��ˢ��Ӧ�ã�");
        mailContent.append("<br/>");
        mailContent.append("���ϡ������������쳣����ͬʱ�����ϡ����������쳣����׼��һ����"
                           + ( String ) retMap.get("validateNumber_5") + "��ȷ��ˢ��Ӧ�ã�");
        mailContent.append("<br/>");
        mailContent.append("<br/>");
        String mailTitle = DateUtil.formatDate(cl.getTime(), "MM��dd��")
                           + "MMӦ��ˢ�����Ԥ������";

        logger.debug("��ʼ�����ʼ�");
        if (rs.size() > 0)
        {
            Mail.sendMail(mailTitle,
                          mailContent.toString(),
                          ApWarnConfig.mailTo,
                          new File[] { ( File ) retMap.get("file") });
        }
        else
        {
            // �޸���
            Mail.sendMail(mailTitle,
                          mailContent.toString(),
                          ApWarnConfig.mailTo);
        }
        logger.debug("�����ʼ�����");
        logger.debug("APˢ��Ԥ������");
    }
}
