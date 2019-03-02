package com.aspire.dotcard.basevideosync.sync;

import java.util.Calendar;
import java.util.TimerTask;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basevideosync.bo.BaseVideoBO;
import com.aspire.dotcard.basevideosync.config.BaseVideoConfig;
import com.aspire.ponaadmin.web.util.PublicUtil;

public class BaseVideoByHourTask extends TimerTask {
	
	protected static JLogger logger = LoggerFactory.getLogger(BaseVideoByHourTask.class);

	public void run() {
		
		String[] hoursConf =BaseVideoConfig.STARTTIME_HOURS.split("\\|");
		Calendar date = Calendar.getInstance();
		String hours = String.valueOf(date.get(Calendar.HOUR_OF_DAY));
		boolean isExe = false;

		// 查看当前时间是否存在于配置执行同步时间
		for (String temp : hoursConf) {
			if (temp.equals(hours)) {
				isExe = true;
				break;
			}
		}
		// 如果不存在于配置执行时间范围内
		if (!isExe) {
			logger.info("当前时间段不存在于配置执行同步时间段范围内！当前小时数为：" + hours);
			return;
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("basevideosync.sync.BaseVideoByHourTask start()");
		}
		
		//基地视频节目增量数据同步
		BaseVideoBO.getInstance().propramDataByHourSync();
		
		// 调用存储过程 用以执行中间表与正式表中数据转移
		BaseVideoBO.getInstance().syncMidTableData();
		
		// 调用存储过程 用以执行节目商品上架操作
		BaseVideoBO.getInstance().updateCategoryReference();
		
		//保存本次增量执行时间
		BaseVideoBO.getInstance().saveLastTime(
				PublicUtil.getDateString(date.getTime(),"yyyy-MM-dd HH:mm:ss"));
		if (logger.isDebugEnabled())
		{
			logger.debug("basevideosync.sync.BaseVideoByHourTask end()");
		}
	}
	
}
