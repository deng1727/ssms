package com.aspire.ponaadmin.web.dataexport.sqlexport.timer;

import java.util.Calendar;
import java.util.List;
import java.util.Timer;

import com.aspire.common.db.DAOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.dataexport.sqlexport.VO.DataExportGroupVO;
import com.aspire.ponaadmin.web.dataexport.sqlexport.dao.DataExportGroupDAO;

/**
 * <p>
 * 数据同步定时器
 * </p>
 * <p>
 * Copyright (c) 2003-2007 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights
 * Reserved
 * </p>
 * 
 * @author zhangmin
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class DataExportTimer
{
	/**
	 * 日志对象
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(DataExportTimer.class);
	
	/**
	 * 启动定时任务
	 * 
	 */
	public static void start()
	{
		logger.info("统一分组导出任务启动初始化！");
		
		List<DataExportGroupVO> dataExportGroupList = null;
		try
		{
			dataExportGroupList = DataExportGroupDAO.getInstance()
					.queryDataExportGroupList();
		}
		catch (DAOException e)
		{
			logger.error("统一分组导出任务启动初始化查询分组列表时发生错误，当前任务没启动！", e);
			e.printStackTrace();
			return;
		}
		
		for (DataExportGroupVO vo : dataExportGroupList)
		{
			String startTime = vo.getStartTime();
			
			// 得到启动的小时和分钟
			int syncDataHours = Integer.parseInt(startTime.substring(0, 2));
			int syncDataMin = Integer.parseInt(startTime.substring(2, 4));
			
			// 得到long类型一天的间隔周期
			Calendar date = Calendar.getInstance();
			int hours = date.get(Calendar.HOUR_OF_DAY);
			int minitue = date.get(Calendar.MINUTE);
			
			date.set(Calendar.HOUR_OF_DAY, syncDataHours);
			date.set(Calendar.MINUTE, syncDataMin);
			date.set(Calendar.SECOND, 0);
		
			if ("1".equals(vo.getTimeType()))
			{
				
				if (hours > syncDataHours)
				{
					date.set(Calendar.DAY_OF_MONTH, date
							.get(Calendar.DAY_OF_MONTH) + 1);
				}
				
				// 如果小时相等则判断分钟
				if (hours == syncDataHours)
				{
					if (minitue > syncDataMin)
					{
						date.set(Calendar.DAY_OF_MONTH, date
								.get(Calendar.DAY_OF_MONTH) + 1);
					}
				}
				
				// 得到间隔周期
				long period = 24 * 60 * 60 * 1000;
				
				// 调用Timer类的schedule方法启动定时任务。
				Timer timer = new Timer(vo.getMailTitle(), true);
				timer.schedule(new DataExportTask(vo), date.getTime(), period);
				
				logger.info("当前任务执行时间：" + date.getTime().toString() + "当前任务名："
						+ vo.getMailTitle());
			}
			else if ("2".equals(vo.getTimeType()))
			{
				// 当前系统配置为周几执行
				int confWeek = Integer.parseInt(vo.getTimeTypeCon());
				
				if (confWeek > 6)
				{
					confWeek = 1;
				}
				else
				{
					confWeek++;
				}
				
				date.set(Calendar.DAY_OF_WEEK, confWeek);
				
				// 如果当前时间超过设置时间，下周执行
				if (!date.after(Calendar.getInstance()))
				{
					date.set(Calendar.DAY_OF_MONTH, date
							.get(Calendar.DAY_OF_MONTH) + 7);
				}
				
				// 得到间隔周期
				long period = 24 * 60 * 60 * 1000 * 7;
				
				// 调用Timer类的schedule方法启动定时任务。
				Timer timer = new Timer(vo.getMailTitle(), true);
				timer.schedule(new DataExportTask(vo), date.getTime(), period);
				
				logger.info("当前任务执行时间：" + date.getTime().toString() + "当前任务名："
						+ vo.getMailTitle());
			}
			else if ("0".equals(vo.getTimeType()))
			{
				
				if (hours >=syncDataHours)
				{
					double a = (double) ((double)(hours-syncDataHours)/2);
					int b = (int) Math.ceil(a)*2+syncDataHours;
					if(b==hours){
						if(minitue > syncDataMin){
							date.set(Calendar.HOUR_OF_DAY, b+2);
							date.set(Calendar.MINUTE, syncDataMin);	
						}else{
							date.set(Calendar.HOUR_OF_DAY, b);
							date.set(Calendar.MINUTE, syncDataMin);	
						}
					}else{
						date.set(Calendar.HOUR_OF_DAY, b);
						date.set(Calendar.MINUTE, syncDataMin);
					}

				}
				
				
				// 得到间隔周期
				long period = 2 * 60 * 60 * 1000;
				
				// 调用Timer类的schedule方法启动定时任务。
				Timer timer = new Timer(vo.getMailTitle(), true);
				timer.schedule(new DataExportTask(vo), date.getTime(), period);
				
				logger.info("当前任务执行时间：" + date.getTime().toString() + "当前任务名："
						+ vo.getMailTitle());
			}		
			else
			{
				logger.info("当前分组任务的执行时间类型有错，请查看数据情况！groupID："
						+ vo.getGroupId());
			}
		}
		
		logger.info("统一分组导出任务启动初始化结束！");
	}
}
