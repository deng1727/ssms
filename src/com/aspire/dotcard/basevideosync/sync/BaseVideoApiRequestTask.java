package com.aspire.dotcard.basevideosync.sync;

import java.util.TimerTask;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basevideosync.bo.BaseVideoBO;

public class BaseVideoApiRequestTask extends TimerTask {
	
	protected static JLogger logger = LoggerFactory.getLogger(BaseVideoApiRequestTask.class);

	public void run() {
		
		if (logger.isDebugEnabled())
		{
			logger.debug("basevideosync.sync.BaseVideoApiRequest start()");
		}
		
		//����api�ӿڵ��� ����ͬ��
		BaseVideoBO.getInstance().DataApiRequestSync();
		
		if (logger.isDebugEnabled())
		{
			logger.debug("basevideosync.sync.BaseVideoApiRequest end()");
		}
	}
	
}