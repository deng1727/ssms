package com.aspire.dotcard.syncGoodsCenter.task;

import java.util.TimerTask;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.syncGoodsCenter.bo.GoodCenterIncrementBo;

public class GoodsCenterIncrementTask extends TimerTask{
	JLogger LOG = LoggerFactory.getLogger(GoodsCenterIncrementTask.class);
	@Override
	public void run() {
		GoodCenterIncrementBo g=new GoodCenterIncrementBo();
		try {
			//g.work();
		} catch (Exception e) {
			LOG.error("",e);
		}
		
		
	}
}
