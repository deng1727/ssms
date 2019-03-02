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
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(BaseComicLoadTask.class);

    /**
     * ����run���з���
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
                logger.error("CP��Ϣ�ӿ�ͬ������", e);
            }

            msgInfo.append("<br>[" + (++i) + "]CP��Ϣ�ӿ�ͬ�������<br>");
            msgInfo.append(cp.getMsgInfo());

            ComicSeriesImportBO comicSeries = new ComicSeriesImportBO();
            try
            {
                comicSeries.importFile();
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                logger.error("�����ӿ�ͬ������", e);
            }
            msgInfo.append("<br>[" + (++i) + "]������ӿ�ͬ�������<br>");
            msgInfo.append(comicSeries.getMsgInfo());

            TVSeriesImportBO tvSeries = new TVSeriesImportBO();
            try
            {
                tvSeries.importFile();
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                logger.error("����Ƭ�ӿ�ͬ������", e);
            }
            msgInfo.append("<br>[" + (++i) + "]����Ƭ�ӿ�ͬ�������<br>");
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
                logger.error("����ӿ�ͬ������", e);
            }
            msgInfo.append("<br>[" + (++i) + "]����ӿ�ͬ�������<br>");
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
                logger.error("����ͳ�ƽӿ�ͬ������", e);
            }
            msgInfo.append("<br>[" + (++i) + "]����ͳ�ƽӿ�ͬ�������<br>");
            msgInfo.append(statistics.getMsgInfo());

            InfoImportBO info = new InfoImportBO();
            try
            {
                info.importFile();
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                logger.error("��Ѷ�ӿ�ͬ������", e);
            }
            msgInfo.append("<br>[" + (++i) + "]��Ѷ�ӿ�ͬ�������<br>");
            msgInfo.append(info.getMsgInfo());

            FirstImportBO first = new FirstImportBO();
            try
            {
                first.importFile();
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                logger.error("�׷��ӿ�ͬ������", e);
            }
            msgInfo.append("<br>[" + (++i) + "]�׷��ӿ�ͬ�������<br>");
            msgInfo.append(first.getMsgInfo());

            ChapterImportBO chapter = new ChapterImportBO();
            try
            {
                chapter.importFile();
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                logger.error("�½ڽӿ�ͬ������", e);
            }
            msgInfo.append("<br>[" + (++i) + "]�½ڽӿ�ͬ�������<br>");
            msgInfo.append(chapter.getMsgInfo());

            RankImportBO rank = new RankImportBO();
            try
            {
                rank.importFile();
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                logger.error("���а�ӿ�ͬ������", e);
            }
            msgInfo.append("<br>[" + (++i) + "]���а�ӿ�ͬ�������<br>");
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
                logger.error("Ʒ�ƹݽӿ�ͬ������", e);
            }
            msgInfo.append("<br>[" + (++i) + "]Ʒ�ƹݽӿ�ͬ�������<br>");
            msgInfo.append(brand.getMsgInfo());

            BrandContentImportBO brandContent = new BrandContentImportBO();
            try
            {
                brandContent.importFile();
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                logger.error("Ʒ�ƹ����ݽӿ�ͬ������", e);
            }
            msgInfo.append("<br>[" + (++i) + "]Ʒ�ƹ����ݽӿ�ͬ�������<br>");
            msgInfo.append(brandContent.getMsgInfo());

            // DeviceGroupImportBO group = new DeviceGroupImportBO();
            // try {
            // group.importFile();
            // } catch (Exception e) {
            // // TODO Auto-generated catch block
            // logger.error("������ӿ�ͬ������",e);
            // }
            // msgInfo.append("<br>[" + (++i) + "]������ӿ�ͬ�������<br>");
            // msgInfo.append(group.getMsgInfo());

            // DeviceGroupItemImportBO device = new DeviceGroupItemImportBO();
            // try {
            // device.importFile();
            // } catch (Exception e) {
            // // TODO Auto-generated catch block
            // logger.error("���ͽӿ�ͬ������",e);
            // }
            // msgInfo.append("<br>[" + (++i) + "]���ͽӿ�ͬ�������<br>");
            // msgInfo.append(device.getMsgInfo());

            AdapterImportBO adapter = new AdapterImportBO();
            try
            {
                adapter.importFile();
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                logger.error("���ֽӿ�ͬ������", e);
            }
            msgInfo.append("<br>[" + (++i) + "]���ֽӿ�ͬ�������<br>");
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
                logger.error("ר��ӿ�ͬ������", e);
            }
            msgInfo.append("<br>[" + (++i) + "]ר��ӿ�ͬ�������<br>");
            msgInfo.append(topic.getMsgInfo());

            TopicContentImportBO topicContent = new TopicContentImportBO();
            try
            {
                topicContent.importFile();
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                logger.error("ר�����ݽӿ�ͬ������", e);
            }
            msgInfo.append("<br>[" + (++i) + "]ר�����ݽӿ�ͬ�������<br>");
            msgInfo.append(topicContent.getMsgInfo());

            try
            {
                new BaseComicFtpProcessor().delFile();
                logger.info("�ɹ�ɾ��SFTP�������ص�ǰ�����ļ���");
            }
            catch (BOException e)
            {
                // TODO Auto-generated catch block
                logger.error("ɾ��SFTP�ϵ�ǰ������ļ�����", e);
            }//

            // dao.buildPortal();//����ǲ���Ҫ�ģ�����ʱ�䣬û���κ�Ч������Ҫԭ���ǻ���˵���Ǹ����ˡ��Ż������ԣ������ָ�.�������������̾�û�б�Ҫ��
            // add by aiyan 2012-11-20���������������Ż����ԣ����ظ����Ż����Զ���3����׼ȷ�����ô洢��������һ�顣
            long s = System.currentTimeMillis();
            int num = dao.buildPortal();
            if (num == 0)
            {
                logger.info("�ô洢����f_buildPortal�����Ż����Գɹ�����ʱ��" + (System.currentTimeMillis() - s));
            }
            else
            {
                logger.info("�ô洢����f_buildPortal�����Ż�����ʧ�ܣ�" + (System.currentTimeMillis() - s));
            }
            // ����ĳ���Ż��Ļ�������Ҫ����dao.buildPortal()�����������Ŷ��add by aiyan 2012-11-23
            dao.updateReferenceNum();
            logger.info("���»�����Ʒ������ϣ�");
            
            
            //add by fanqh 20131122
            logger.info("ִ�к�������ʱ������ʼ��");
            try
            {
                System.out.println("====ִ�к�������ʱ������ʼ====");
                BlackBO.getInstance().delReference();
                System.out.println("====ִ�к�������ʱ�������====");
            }
            catch (BOException e)
            {
                logger.error("ִ�к�������ʱ���񣬱���!",e);
            }
            logger.info("ִ�к�������ʱ������ɣ�");

        }
        finally
        {
            StringBuffer sb = new StringBuffer();
            sb.append("<br>");
            sb.append("��ʼʱ�䣺");
            sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
            sb.append(",����ʱ�䣺");
            sb.append(PublicUtil.getDateString(new Date(), "yyyy-MM-dd HH:mm:ss"));
            sb.append("<br>");
            sb.append("<br>");

            String str = sb.toString() + "�������ع�[" + i + "]���ļ���Ҫ����<br>" + msgInfo.toString();
            logger.info(str);
            String subject = module.getItemValue("dmBaseSynSubject");
            String[] mailTo = module.getItemValue("dmBaseSynMailto").split(",");
            this.sendMail(str, mailTo, subject);
        }
    }

    /**
     * �����ʼ�
     * 
     * @param mailContent,�ʼ�����
     */
    private void sendMail(String mailContent, String[] mailTo, String subject)
    {
        Mail.sendMail(subject, mailContent, mailTo);
    }

}
