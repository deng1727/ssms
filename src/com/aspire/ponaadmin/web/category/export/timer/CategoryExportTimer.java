/**
 * <p>
 * 货架数据导出事件调度timer管理类
 * </p>
 * <p>
 * Copyright (c) 2009 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved
 * </p>
 * @Sep 16, 2009
 * @author dongke
 * @version 1.0.0.0
 */
package com.aspire.ponaadmin.web.category.export.timer;

import java.util.Calendar;
import java.util.Timer;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.category.export.CategoryExportConfig;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * @author dongke
 *
 */
public class CategoryExportTimer
{
	private static final JLogger logger = LoggerFactory.getLogger(CategoryExportTimer.class);

	
	/**
	 * 私有构造方法
	 *
	 */
	private CategoryExportTimer(){
		
	}
	 /**
   * 初始化方法启动timer
   */
	public static void start()
	{
		if(logger.isDebugEnabled()){
			
			logger.info("货架商品自动导出开始...");
		}
//		系统默认的货架导出 执行时间 5:00
	       int hour = 5;
	       int minute = 0;
	       //从配置项中获取配置值
	       try
	       {
	           String SynStartTime = CategoryExportConfig.STARTTIME;
	           hour = Integer.parseInt(SynStartTime.split(":")[0]);
	           minute = Integer.parseInt(SynStartTime.split(":")[1]);
	       }
	       catch (Exception ex)
	       {
	           logger.error("从配置项中获取货架数据导出的时间STARTTIME解析出错！");
	       }
	       if(hour<0||hour>23)
	       {
	           //配置值不正确，就使用默认值
	           hour = 5 ;
	       }
	       if(minute<0||minute>59)
	       {
	           //配置值不正确，就使用默认值
	           minute = 0 ;
	       }

	       //task的间隔时间
	       long taskIntervalMS;
	       //只有配置项的频率设置为week的时候才表示间隔周期为周
	       if("week".equals(CategoryExportConfig.CategorySynFrequency))
	       {
	       	taskIntervalMS=(long)(7*24 * 60 * 60 * 1000);
	       }else
	       {
	       	taskIntervalMS=(long) (24 * 60 * 60 * 1000) ;
	       }
	        
	       Timer timer = new Timer(true) ;
	    	 //第一次执行时间
	   		Calendar firstExecTime=Calendar.getInstance();
	   		Calendar curTime=Calendar.getInstance();
	   		//如果当前时间在计划执行时间之后就到第二天开始执行第一次操作

	   		if(curTime.get(Calendar.HOUR_OF_DAY)>hour)
	   		{
	   			firstExecTime.add(Calendar.DAY_OF_MONTH, 1);
	   		}else if(curTime.get(Calendar.HOUR_OF_DAY)==hour)
	   		{
	   		    if(curTime.get(Calendar.MINUTE)>=minute)
	   		    {
	   		        firstExecTime.add(Calendar.DAY_OF_MONTH, 1);
	   		    }
	   		}
	   		//设置执行时间
	   		firstExecTime.set(Calendar.HOUR_OF_DAY, hour);
	   		firstExecTime.set(Calendar.MINUTE, minute);
	   		firstExecTime.set(Calendar.SECOND, 0);
	   		firstExecTime.set(Calendar.MILLISECOND, 0);
	       timer.schedule(new CategoryExportTask(), firstExecTime.getTime(), taskIntervalMS) ;
	       if (logger.isDebugEnabled())
	       {
	           logger.debug("schedule a CategoryExportTask,first start time is:" + firstExecTime) ;
	       }
	}
	
	
}
