package com.aspire.dotcard.reportdata.appstatistic;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.reportdata.TopDataConfig;
import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.mail.MailConfig;
import com.aspire.ponaadmin.web.util.ArrangedTask;
import com.aspire.ponaadmin.web.util.PublicUtil;

public class AppStatisticImportTask extends ArrangedTask
{

	private static final JLogger LOG = LoggerFactory.getLogger(AppStatisticImportTask.class);
	/**
     * ����ר����־
     */
    protected static JLogger REPORT_LOG = LoggerFactory.getLogger("reportimp.client");
	public void doTask()
	{
		StringBuffer sb = new StringBuffer("");
		try
		{
			LOG.info("�Ƽ�(����)(��Ʒ)Ӧ�ð���ͳ�����ݱ����뿪ʼ");
			Calendar date=Calendar.getInstance();
			date.add(Calendar.DAY_OF_MONTH, -1);
			String dateString = PublicUtil.getDateString(date.getTime(), "yyyyMMdd");
			
			String appLastFtpDir=PublicUtil.getEndWithSlash(TopDataConfig.get("APPLast_FtpDir"));			
			String appLastFileNameRegex="latest_list_"+dateString+"_[0-9]{6}+\\.txt";//gmcc_song_play_YYYYMMDD_xxxxxx.txt
			List appLastList=AppStatisticImportBO.getInstance().downLoadReportFile(appLastFtpDir,appLastFileNameRegex);
			int applastcount=AppStatisticImportBO.getInstance().saveAppLastStatistic(appLastList);
			
			LOG.info("����Ӧ�ð���ͳ�����ݱ�������ɣ����ɹ�����"+applastcount+"��ͳ�Ƽ�¼");
			REPORT_LOG.info("����Ӧ�ð���ͳ�����ݱ�������ɣ����ɹ�����"+applastcount+"��ͳ�Ƽ�¼");
			sb.append("����Ӧ�ð���ͳ�����ݱ�������ɣ����ɹ�����"+applastcount+"��ͳ�Ƽ�¼");
			sb.append("<br>");
			String appRecommondFtpDir=PublicUtil.getEndWithSlash(TopDataConfig.get("APPRecommond_FtpDir"));			
			String appRecommondFileNameRegex="recommend_list_"+dateString+"_[0-9]{6}+\\.txt";//gmcc_song_play_YYYYMMDD_xxxxxx.txt
			List appRecommondList=AppStatisticImportBO.getInstance().downLoadReportFile(appRecommondFtpDir,appRecommondFileNameRegex);
			int apprecommondcount=AppStatisticImportBO.getInstance().saveAppRecommondStatistic(appRecommondList);
			
			LOG.info("�Ƽ�Ӧ�ð���ͳ�����ݱ�������ɣ����ɹ�����"+apprecommondcount+"��ͳ�Ƽ�¼");
			REPORT_LOG.info("�Ƽ�Ӧ�ð���ͳ�����ݱ�������ɣ����ɹ�����"+apprecommondcount+"��ͳ�Ƽ�¼");
			
			sb.append("�Ƽ�Ӧ�ð���ͳ�����ݱ�������ɣ����ɹ�����"+apprecommondcount+"��ͳ�Ƽ�¼");
			sb.append("<br>");
			String appCompetitiveFtpDir=PublicUtil.getEndWithSlash(TopDataConfig.get("APPCompetitive_FtpDir"));			
			String appCompetitiveFileNameRegex="competitive_list_"+dateString+"_[0-9]{6}+\\.txt";//gmcc_song_play_YYYYMMDD_xxxxxx.txt
			List appCompetitiveList=AppStatisticImportBO.getInstance().downLoadReportFile(appCompetitiveFtpDir,appCompetitiveFileNameRegex);
			int appCompetitivecount=AppStatisticImportBO.getInstance().saveAppBaseStatistic("competitive",appCompetitiveList);
			
			LOG.info("��ƷӦ�ð���ͳ�����ݱ�������ɣ����ɹ�����"+appCompetitivecount+"��ͳ�Ƽ�¼");
			REPORT_LOG.info("��ƷӦ�ð���ͳ�����ݱ�������ɣ����ɹ�����"+appCompetitivecount+"��ͳ�Ƽ�¼");
			
			sb.append("��ƷӦ�ð���ͳ�����ݱ�������ɣ����ɹ�����"+appCompetitivecount+"��ͳ�Ƽ�¼");
			sb.append("<br>");
			
//			String appBlackListdFtpDir=PublicUtil.getEndWithSlash(TopDataConfig.get("contentBlackList_FtpDir"));			
//			String appBlackListFileNameRegex="contentblack_list_"+dateString+"_[0-9]{6}+\\.txt";//gmcc_song_play_YYYYMMDD_xxxxxx.txt
//			List appBlackList=AppStatisticImportBO.getInstance().downLoadReportFile(appBlackListdFtpDir,appBlackListFileNameRegex);
//			int appBlackListcount=AppStatisticImportBO.getInstance().saveContentBlackList(appBlackList);
//			
//			LOG.info("������Ӧ��ͳ�����ݱ�������ɣ����ɹ�����"+appBlackListcount+"��ͳ�Ƽ�¼");
//			REPORT_LOG.info("������Ӧ��ͳ�����ݱ�������ɣ����ɹ�����"+appBlackListcount+"��ͳ�Ƽ�¼");
//			sb.append("������Ӧ��ͳ�����ݱ�������ɣ����ɹ�����"+appBlackListcount+"��ͳ�Ƽ�¼");
//			sb.append("<br>");
            
            int updateCount = AppStatisticImportBO.getInstance().updateRecommendDate();
            
            LOG.info("����С���Ƽ�������ɣ����ɹ�����"+updateCount+"��ͳ�Ƽ�¼");
            REPORT_LOG.info("����С���Ƽ�������ɣ����ɹ�����"+updateCount+"��ͳ�Ƽ�¼");
            sb.append("����С���Ƽ�������ɣ����ɹ�����"+updateCount+"��ͳ�Ƽ�¼");
            sb.append("<br>");
			
			this.sendMail(sb.toString());
		} catch (Exception e)
		{
			LOG.error("�Ƽ�(����)(��Ʒ)(С���Ƽ�)Ӧ�ð���ͳ�����ݱ��������",e);
			REPORT_LOG.error("�Ƽ�(����)(��Ʒ)(С���Ƽ�)Ӧ�ð���ͳ�����ݱ��������",e);
			sb.append("�Ƽ�(����)(��Ʒ)(С���Ƽ�)Ӧ�ð���ͳ�����ݱ��������"+e);
			sb.append("<br>");
			this.sendMail(sb.toString());
		}

	}
	 /**
	 * �����ʼ�
	 * 
	 * @param mailContent,�ʼ�����
	 */
    private void sendMail(String mailContent)
    {
        if (LOG.isDebugEnabled())
        {
        	LOG.debug("sendMail(" + mailContent + ")");
        }
        // �õ��ʼ�����������
        String[] mailTo = MailConfig.getInstance().getMailToArray();
        String subject = "�Ƽ�(����)(��Ʒ)(С���Ƽ�)Ӧ�ð���ͳ�����ݱ�����";
        if (LOG.isDebugEnabled())
        {
        	LOG.debug("mailTo Array is:" + Arrays.asList(mailTo));
        	LOG.debug("mail subject is:" + subject);
        	LOG.debug("mailContent is:" + mailContent);
        }
        Mail.sendMail(subject, mailContent, mailTo);
    }
    
	public void init() throws BOException
	{

		executeTime=TopDataConfig.get("APP_ImportTime");
		//this.setFirstTriggerTime(executeTime);
		this.setTaskDesc("�Ƽ�(����)Ӧ�ð���ͳ�����ݱ�����");
	}

}
