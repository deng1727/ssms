package com.aspire.ponaadmin.web.datasync.implement.music;

import java.util.Date;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.DataSyncTask01;
import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.util.PublicUtil;
/**
 * 榜单同步任务类。该任务只只适用于榜单。
 * @author zhangwei
 *
 */
public class MusicTopListSyncTask extends DataSyncTask01
{
	int delCount;
	JLogger logger=LoggerFactory.getLogger(MusicTopListSyncTask.class);
	//需要重写父类的邮件发送方法。
	protected void mailToAdmin(boolean result,int errorCode,String reason)
	{
		//发送邮件之后表示本次任务结束，需要获取下架榜单的个数。
		MusicHotTopDealer dealer=(MusicHotTopDealer)this.dataDealer;
        delCount=dealer.getDelCount();
        //需要清理计数器，以便下次同步任务的计数正确。
        dealer.clearDelCount();
		String mailTitle;
		// 发送邮件表示本次处理结束
		Date endDate = new Date();
		StringBuffer sb = new StringBuffer();
		if (result)
		{

			// MailUtil.sen
			mailTitle = this.getDesc() + "成功";

			sb.append("开始时间：");
			sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
			sb.append(",结束时间：");
			sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
			sb.append("。<p>处理结果：<br>");
			sb.append("本次"+this.getDesc()+"任务总共成功处理");
			sb.append(this.getSuccessAdd()+delCount);
			sb.append("条。<br>其中成功新增");
			sb.append(this.getSuccessAdd());
			sb.append("条，成功删除");
			sb.append(delCount);
			sb.append("条\r\n");
			
			if(logger.isDebugEnabled())
			{
				logger.debug("成功新增："+this.getSuccessAdd()+"，成功删除："+this.getSuccessDelete());
			}

		}
		else
		{
			if(errorCode==DataSyncConstants.EXCEPTION_FILE_NOT_EXISTED)
			{
				mailTitle=this.getDesc() + "失败，没有取到正确的数据文件，详见邮件正文!";//XX内容导入失败，没有取到正确的数据文件，详见邮件正文!
			}else
			{
				mailTitle=this.getDesc() + "失败，详见邮件正文!";
			}
			mailTitle = this.getDesc() + "失败";
			sb.append("开始时间：");
			sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
			sb.append(",结束时间：");
			sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
			sb.append("。<p>失败原因：<br>");
			sb.append(reason);

		}
		Mail.sendMail(mailTitle, sb.toString(),this.getMailTo());
	}

}
