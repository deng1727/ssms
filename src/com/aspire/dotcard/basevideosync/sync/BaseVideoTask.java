package com.aspire.dotcard.basevideosync.sync;

import java.util.TimerTask;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basevideosync.bo.BaseVideoBO;

public class BaseVideoTask extends TimerTask {

	protected static JLogger logger = LoggerFactory
	.getLogger(BaseVideoTask.class);
	
	public void run() {
		// ��ʼͬ��֮��
		if (logger.isDebugEnabled())
		{
			logger.debug("basvideosync.sync.BaseVideoTask start()");
		}
		
		//��ʼͬ���ļ�����ȫ��ͬ��txt�ļ�
		BaseVideoBO.getInstance().fileDataSync();
		
		//ͬ��xml�ļ�����
		BaseVideoBO.getInstance().xmlFileDataSync();
		
		// ���ô洢���� ����ִ���м������ʽ��������ת��
		BaseVideoBO.getInstance().syncMidTableData();
		
		// ���ô洢���� ����ִ�н�Ŀ��Ʒ�ϼܲ���
		BaseVideoBO.getInstance().updateCategoryReference();
		
		if (logger.isDebugEnabled())
		{
			logger.debug("basvideosync.sync.BaseVideoTask end()");
		}
	}

}
