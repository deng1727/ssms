package com.aspire.dotcard.baseVideo.sync;

import java.util.Calendar;
import java.util.TimerTask;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseVideo.conf.BaseVideoConfig;
import com.aspire.dotcard.baseVideo.exportfile.BaseFileFactory;
import com.aspire.ponaadmin.web.util.HttpUtil;

public class BaseVideoByHourTask extends TimerTask {
	protected static JLogger logger = LoggerFactory.getLogger(BaseVideoByHourTask.class);

	public void run() {
		
		String[] hoursConf =ConfigFactory.getSystemConfig()
        .getModuleConfig("BaseVideoFileConfig").getItemValue("syncDataTimeIntervalByTime").split("\\|");
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
		
		BaseFileFactory.getInstance().getBaseFile(BaseVideoConfig.FILE_TYPE_VIDEO_ADD_HOUR).execution(true);
		BaseFileFactory.getInstance().getBaseFile(BaseVideoConfig.FILE_TYPE_PROGRAM_ADD_HOUR).execution(true);
		
		sendPortalIP(BaseVideoConfig.xpasUrlPortalConfig);
		
		BaseFileFactory.getInstance().getBaseFile(BaseVideoConfig.FILE_TYPE_RECOMMEND_ADD_HOUR).execution(true);
	}
	
	
	 /**
     * 通知门户方法
     * 
     * @param url
     *            门户url
     * @return
     */
    private void sendPortalIP(String url)
    {
        int code = 0;

        if (logger.isDebugEnabled())
        {
        	logger.debug("通知门户开始" );
        }
        
        try
        {
            code = HttpUtil.getResponseCodeFromURL(url, "utf-8");
          
        }
        catch (Exception e)
        {
        	logger.error("通知门户方法调用失败");
        }

        if (code == 200)
        {
            if (logger.isDebugEnabled())
            {
            	logger.debug("通知门户：url=" + url + " 成功. ");
            }
        }
        else
        {
            if (logger.isDebugEnabled())
            {
            	logger.debug("通知门户：url=" + url + " 失败. ");
            }
        }
    }
}
