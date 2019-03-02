package com.aspire.dotcard.a8.task;

import java.util.TimerTask;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.a8.A8DataImport;
import com.aspire.dotcard.a8.A8MusicImport;
import com.aspire.dotcard.a8.A8ParameterConfig;
import com.aspire.ponaadmin.web.mail.Mail;

public class A8MusicImportTask extends TimerTask
{
	private static final JLogger logger = LoggerFactory.getLogger(A8MusicImportTask.class);

	public void run()
	{
		A8DataImport task;
		try
		{
			task = new A8MusicImport();
		} catch (BOException e)
		{
			logger.error("音乐数据导入出现异常",e);
			Mail.sendMail("音乐数据导入失败","无法创建A8MusicImport类。请查看配置项是否正确，或者联系管理员",A8ParameterConfig.MailTo);
			return ;
		}
		task.process();		
	}

}
