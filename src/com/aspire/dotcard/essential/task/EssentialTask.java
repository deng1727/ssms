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
		//清除T_MM_ESSENTIAL表中所有数据做全量部署
		EssentialBo.getInstance().clearData();
		logger.debug("清除T_MM_ESSENTIAL中数据........");
		//获取到mm应用id
		List<String> contactIds =EssentialBo.getInstance().getContactIds();
		
		for(String contactId :contactIds ){
			logger.debug("contactId:"+ contactId);
			//根据触点应用id在t_a_cm_device_resource表中查出clientid
			String clientid = EssentialBo.getInstance().getClientid(contactId);
			//根据clientid去t_r_gcontent表中查出触点id
			String mmContentid = EssentialBo.getInstance().getMMContentId(clientid);
			logger.debug("mmContentid:" + mmContentid);
			if(null!=mmContentid && !"".equals(mmContentid)){
				EssentialBo.getInstance().insertData(mmContentid, contactId);
			}
			
		}

	}

}
