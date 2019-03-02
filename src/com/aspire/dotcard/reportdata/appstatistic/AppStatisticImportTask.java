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
     * 报表专用日志
     */
    protected static JLogger REPORT_LOG = LoggerFactory.getLogger("reportimp.client");
	public void doTask()
	{
		StringBuffer sb = new StringBuffer("");
		try
		{
			LOG.info("推荐(最新)(精品)应用榜日统计数据报表导入开始");
			Calendar date=Calendar.getInstance();
			date.add(Calendar.DAY_OF_MONTH, -1);
			String dateString = PublicUtil.getDateString(date.getTime(), "yyyyMMdd");
			
			String appLastFtpDir=PublicUtil.getEndWithSlash(TopDataConfig.get("APPLast_FtpDir"));			
			String appLastFileNameRegex="latest_list_"+dateString+"_[0-9]{6}+\\.txt";//gmcc_song_play_YYYYMMDD_xxxxxx.txt
			List appLastList=AppStatisticImportBO.getInstance().downLoadReportFile(appLastFtpDir,appLastFileNameRegex);
			int applastcount=AppStatisticImportBO.getInstance().saveAppLastStatistic(appLastList);
			
			LOG.info("最新应用榜日统计数据报表导入完成，共成功导入"+applastcount+"个统计记录");
			REPORT_LOG.info("最新应用榜日统计数据报表导入完成，共成功导入"+applastcount+"个统计记录");
			sb.append("最新应用榜日统计数据报表导入完成，共成功导入"+applastcount+"个统计记录");
			sb.append("<br>");
			String appRecommondFtpDir=PublicUtil.getEndWithSlash(TopDataConfig.get("APPRecommond_FtpDir"));			
			String appRecommondFileNameRegex="recommend_list_"+dateString+"_[0-9]{6}+\\.txt";//gmcc_song_play_YYYYMMDD_xxxxxx.txt
			List appRecommondList=AppStatisticImportBO.getInstance().downLoadReportFile(appRecommondFtpDir,appRecommondFileNameRegex);
			int apprecommondcount=AppStatisticImportBO.getInstance().saveAppRecommondStatistic(appRecommondList);
			
			LOG.info("推荐应用榜日统计数据报表导入完成，共成功导入"+apprecommondcount+"个统计记录");
			REPORT_LOG.info("推荐应用榜日统计数据报表导入完成，共成功导入"+apprecommondcount+"个统计记录");
			
			sb.append("推荐应用榜日统计数据报表导入完成，共成功导入"+apprecommondcount+"个统计记录");
			sb.append("<br>");
			String appCompetitiveFtpDir=PublicUtil.getEndWithSlash(TopDataConfig.get("APPCompetitive_FtpDir"));			
			String appCompetitiveFileNameRegex="competitive_list_"+dateString+"_[0-9]{6}+\\.txt";//gmcc_song_play_YYYYMMDD_xxxxxx.txt
			List appCompetitiveList=AppStatisticImportBO.getInstance().downLoadReportFile(appCompetitiveFtpDir,appCompetitiveFileNameRegex);
			int appCompetitivecount=AppStatisticImportBO.getInstance().saveAppBaseStatistic("competitive",appCompetitiveList);
			
			LOG.info("精品应用榜日统计数据报表导入完成，共成功导入"+appCompetitivecount+"个统计记录");
			REPORT_LOG.info("精品应用榜日统计数据报表导入完成，共成功导入"+appCompetitivecount+"个统计记录");
			
			sb.append("精品应用榜日统计数据报表导入完成，共成功导入"+appCompetitivecount+"个统计记录");
			sb.append("<br>");
			
//			String appBlackListdFtpDir=PublicUtil.getEndWithSlash(TopDataConfig.get("contentBlackList_FtpDir"));			
//			String appBlackListFileNameRegex="contentblack_list_"+dateString+"_[0-9]{6}+\\.txt";//gmcc_song_play_YYYYMMDD_xxxxxx.txt
//			List appBlackList=AppStatisticImportBO.getInstance().downLoadReportFile(appBlackListdFtpDir,appBlackListFileNameRegex);
//			int appBlackListcount=AppStatisticImportBO.getInstance().saveContentBlackList(appBlackList);
//			
//			LOG.info("黑名单应用统计数据报表导入完成，共成功导入"+appBlackListcount+"个统计记录");
//			REPORT_LOG.info("黑名单应用统计数据报表导入完成，共成功导入"+appBlackListcount+"个统计记录");
//			sb.append("黑名单应用统计数据报表导入完成，共成功导入"+appBlackListcount+"个统计记录");
//			sb.append("<br>");
            
            int updateCount = AppStatisticImportBO.getInstance().updateRecommendDate();
            
            LOG.info("更新小编推荐数据完成，共成功导入"+updateCount+"个统计记录");
            REPORT_LOG.info("更新小编推荐数据完成，共成功导入"+updateCount+"个统计记录");
            sb.append("更新小编推荐数据完成，共成功导入"+updateCount+"个统计记录");
            sb.append("<br>");
			
			this.sendMail(sb.toString());
		} catch (Exception e)
		{
			LOG.error("推荐(最新)(精品)(小编推荐)应用榜日统计数据报表导入出错。",e);
			REPORT_LOG.error("推荐(最新)(精品)(小编推荐)应用榜日统计数据报表导入出错。",e);
			sb.append("推荐(最新)(精品)(小编推荐)应用榜日统计数据报表导入出错。"+e);
			sb.append("<br>");
			this.sendMail(sb.toString());
		}

	}
	 /**
	 * 发送邮件
	 * 
	 * @param mailContent,邮件内容
	 */
    private void sendMail(String mailContent)
    {
        if (LOG.isDebugEnabled())
        {
        	LOG.debug("sendMail(" + mailContent + ")");
        }
        // 得到邮件接收者数组
        String[] mailTo = MailConfig.getInstance().getMailToArray();
        String subject = "推荐(最新)(精品)(小编推荐)应用榜日统计数据报表导入";
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
		this.setTaskDesc("推荐(最新)应用榜日统计数据报表导入");
	}

}
