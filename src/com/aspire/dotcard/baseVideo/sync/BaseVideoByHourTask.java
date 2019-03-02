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

		// �鿴��ǰʱ���Ƿ����������ִ��ͬ��ʱ��
		for (String temp : hoursConf) {
			if (temp.equals(hours)) {
				isExe = true;
				break;
			}
		}

		// ���������������ִ��ʱ�䷶Χ��
		if (!isExe) {
			logger.info("��ǰʱ��β�����������ִ��ͬ��ʱ��η�Χ�ڣ���ǰСʱ��Ϊ��" + hours);
			return;
		}
		
		BaseFileFactory.getInstance().getBaseFile(BaseVideoConfig.FILE_TYPE_VIDEO_ADD_HOUR).execution(true);
		BaseFileFactory.getInstance().getBaseFile(BaseVideoConfig.FILE_TYPE_PROGRAM_ADD_HOUR).execution(true);
		
		sendPortalIP(BaseVideoConfig.xpasUrlPortalConfig);
		
		BaseFileFactory.getInstance().getBaseFile(BaseVideoConfig.FILE_TYPE_RECOMMEND_ADD_HOUR).execution(true);
	}
	
	
	 /**
     * ֪ͨ�Ż�����
     * 
     * @param url
     *            �Ż�url
     * @return
     */
    private void sendPortalIP(String url)
    {
        int code = 0;

        if (logger.isDebugEnabled())
        {
        	logger.debug("֪ͨ�Ż���ʼ" );
        }
        
        try
        {
            code = HttpUtil.getResponseCodeFromURL(url, "utf-8");
          
        }
        catch (Exception e)
        {
        	logger.error("֪ͨ�Ż���������ʧ��");
        }

        if (code == 200)
        {
            if (logger.isDebugEnabled())
            {
            	logger.debug("֪ͨ�Ż���url=" + url + " �ɹ�. ");
            }
        }
        else
        {
            if (logger.isDebugEnabled())
            {
            	logger.debug("֪ͨ�Ż���url=" + url + " ʧ��. ");
            }
        }
    }
}
