package com.aspire.dotcard.syncGoodsCenter.task;

import java.util.TimerTask;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.syncGoodsCenter.bo.GoodCenterBo;

public class GoodsCenterTask extends TimerTask{
	JLogger LOG = LoggerFactory.getLogger(GoodsCenterTask.class);
	@Override
	public void run() {
		GoodCenterBo g=new GoodCenterBo();
		//g.work();
		
	}
}
