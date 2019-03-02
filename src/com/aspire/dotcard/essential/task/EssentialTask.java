package com.aspire.dotcard.essential.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import com.aspire.common.log.constants.LogErrorCode;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.essential.bo.EssentialBo;
import com.aspire.dotcard.synczcoms.task.ZcomsDataSyncTimer;

public class EssentialTask extends TimerTask{
	 protected static JLogger logger = LoggerFactory.getLogger(EssentialTask.class);
	@Override
	public void run() {
		logger.debug("EssentialTask in............");
		//���T_MM_ESSENTIAL��������������ȫ������
		EssentialBo.getInstance().clearData();
		logger.debug("���T_MM_ESSENTIAL������........");
		//��ȡ��mmӦ��id
		List<String> contactIds =EssentialBo.getInstance().getContactIds();
		
		for(String contactId :contactIds ){
			logger.debug("contactId:"+ contactId);
			//���ݴ���Ӧ��id��t_a_cm_device_resource���в��clientid
			String clientid = EssentialBo.getInstance().getClientid(contactId);
			//����clientidȥt_r_gcontent���в������id
			String mmContentid = EssentialBo.getInstance().getMMContentId(clientid);
			logger.debug("mmContentid:" + mmContentid);
			if(null!=mmContentid && !"".equals(mmContentid)){
				EssentialBo.getInstance().insertData(mmContentid, contactId);
			}
			
		}

	}

}
