package com.aspire.dotcard.basevideosync.sync;

import java.util.TimerTask;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basevideosync.bo.BaseVideoBO;

public class BaseVideoTask extends TimerTask {

	protected static JLogger logger = LoggerFactory
	.getLogger(BaseVideoTask.class);
	
	public void run() {
		// 开始同步之旅
		if (logger.isDebugEnabled())
		{
			logger.debug("basvideosync.sync.BaseVideoTask start()");
		}
		
		//开始同步文件，先全量同步txt文件
		BaseVideoBO.getInstance().fileDataSync();
		
		//同步xml文件内容
		BaseVideoBO.getInstance().xmlFileDataSync();
		
		// 调用存储过程 用以执行中间表与正式表中数据转移
		BaseVideoBO.getInstance().syncMidTableData();
		
		// 调用存储过程 用以执行节目商品上架操作
		BaseVideoBO.getInstance().updateCategoryReference();
		
		if (logger.isDebugEnabled())
		{
			logger.debug("basvideosync.sync.BaseVideoTask end()");
		}
	}

}
