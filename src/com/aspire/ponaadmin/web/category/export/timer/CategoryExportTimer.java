/**
 * <p>
 * �������ݵ����¼�����timer������
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
	 * ˽�й��췽��
	 *
	 */
	private CategoryExportTimer(){
		
	}
	 /**
   * ��ʼ����������timer
   */
	public static void start()
	{
		if(logger.isDebugEnabled()){
			
			logger.info("������Ʒ�Զ�������ʼ...");
		}
//		ϵͳĬ�ϵĻ��ܵ��� ִ��ʱ�� 5:00
	       int hour = 5;
	       int minute = 0;
	       //���������л�ȡ����ֵ
	       try
	       {
	           String SynStartTime = CategoryExportConfig.STARTTIME;
	           hour = Integer.parseInt(SynStartTime.split(":")[0]);
	           minute = Integer.parseInt(SynStartTime.split(":")[1]);
	       }
	       catch (Exception ex)
	       {
	           logger.error("���������л�ȡ�������ݵ�����ʱ��STARTTIME��������");
	       }
	       if(hour<0||hour>23)
	       {
	           //����ֵ����ȷ����ʹ��Ĭ��ֵ
	           hour = 5 ;
	       }
	       if(minute<0||minute>59)
	       {
	           //����ֵ����ȷ����ʹ��Ĭ��ֵ
	           minute = 0 ;
	       }

	       //task�ļ��ʱ��
	       long taskIntervalMS;
	       //ֻ���������Ƶ������Ϊweek��ʱ��ű�ʾ�������Ϊ��
	       if("week".equals(CategoryExportConfig.CategorySynFrequency))
	       {
	       	taskIntervalMS=(long)(7*24 * 60 * 60 * 1000);
	       }else
	       {
	       	taskIntervalMS=(long) (24 * 60 * 60 * 1000) ;
	       }
	        
	       Timer timer = new Timer(true) ;
	    	 //��һ��ִ��ʱ��
	   		Calendar firstExecTime=Calendar.getInstance();
	   		Calendar curTime=Calendar.getInstance();
	   		//�����ǰʱ���ڼƻ�ִ��ʱ��֮��͵��ڶ��쿪ʼִ�е�һ�β���

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
	   		//����ִ��ʱ��
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
