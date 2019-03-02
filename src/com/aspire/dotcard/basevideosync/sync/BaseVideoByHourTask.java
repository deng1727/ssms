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
		
		if (logger.isDebugEnabled())
		{
			logger.debug("basevideosync.sync.BaseVideoByHourTask start()");
		}
		
		//������Ƶ��Ŀ��������ͬ��
		BaseVideoBO.getInstance().propramDataByHourSync();
		
		// ���ô洢���� ����ִ���м������ʽ��������ת��
		BaseVideoBO.getInstance().syncMidTableData();
		
		// ���ô洢���� ����ִ�н�Ŀ��Ʒ�ϼܲ���
		BaseVideoBO.getInstance().updateCategoryReference();
		
		//���汾������ִ��ʱ��
		BaseVideoBO.getInstance().saveLastTime(
				PublicUtil.getDateString(date.getTime(),"yyyy-MM-dd HH:mm:ss"));
		if (logger.isDebugEnabled())
		{
			logger.debug("basevideosync.sync.BaseVideoByHourTask end()");
		}
	}
	
}
