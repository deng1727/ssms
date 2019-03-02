package com.aspire.dotcard.a8.task;

import java.util.TimerTask;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.a8.A8DataImport;
import com.aspire.dotcard.a8.A8ParameterConfig;
import com.aspire.dotcard.a8.A8TopListImport;
import com.aspire.ponaadmin.web.mail.Mail;

public class A8TopListImportTask extends TimerTask
{
	private static final JLogger logger = LoggerFactory.getLogger(A8TopListImportTask.class);

	public void run()
	{
		A8DataImport task;
		try
		{
			task = new A8TopListImport();
		} catch (BOException e)
		{
			logger.error("�񵥵�������쳣",e);
			Mail.sendMail("�����ݵ���ʧ��","�޷�����A8TopListImport�ࡣ��鿴�������Ƿ���ȷ��������ϵ����Ա",A8ParameterConfig.MailTo);
			return ;
		}
		task.process();		
		
	}

}
