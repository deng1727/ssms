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
 * ����ͬ����ʱ��
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
	 * ��־����
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(DataExportTimer.class);
	
	/**
	 * ������ʱ����
	 * 
	 */
	public static void start()
	{
		logger.info("ͳһ���鵼������������ʼ����");
		
		List<DataExportGroupVO> dataExportGroupList = null;
		try
		{
			dataExportGroupList = DataExportGroupDAO.getInstance()
					.queryDataExportGroupList();
		}
		catch (DAOException e)
		{
			logger.error("ͳһ���鵼������������ʼ����ѯ�����б�ʱ�������󣬵�ǰ����û������", e);
			e.printStackTrace();
			return;
		}
		
		for (DataExportGroupVO vo : dataExportGroupList)
		{
			String startTime = vo.getStartTime();
			
			// �õ�������Сʱ�ͷ���
			int syncDataHours = Integer.parseInt(startTime.substring(0, 2));
			int syncDataMin = Integer.parseInt(startTime.substring(2, 4));
			
			// �õ�long����һ��ļ������
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
				
				// ���Сʱ������жϷ���
				if (hours == syncDataHours)
				{
					if (minitue > syncDataMin)
					{
						date.set(Calendar.DAY_OF_MONTH, date
								.get(Calendar.DAY_OF_MONTH) + 1);
					}
				}
				
				// �õ��������
				long period = 24 * 60 * 60 * 1000;
				
				// ����Timer���schedule����������ʱ����
				Timer timer = new Timer(vo.getMailTitle(), true);
				timer.schedule(new DataExportTask(vo), date.getTime(), period);
				
				logger.info("��ǰ����ִ��ʱ�䣺" + date.getTime().toString() + "��ǰ��������"
						+ vo.getMailTitle());
			}
			else if ("2".equals(vo.getTimeType()))
			{
				// ��ǰϵͳ����Ϊ�ܼ�ִ��
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
				
				// �����ǰʱ�䳬������ʱ�䣬����ִ��
				if (!date.after(Calendar.getInstance()))
				{
					date.set(Calendar.DAY_OF_MONTH, date
							.get(Calendar.DAY_OF_MONTH) + 7);
				}
				
				// �õ��������
				long period = 24 * 60 * 60 * 1000 * 7;
				
				// ����Timer���schedule����������ʱ����
				Timer timer = new Timer(vo.getMailTitle(), true);
				timer.schedule(new DataExportTask(vo), date.getTime(), period);
				
				logger.info("��ǰ����ִ��ʱ�䣺" + date.getTime().toString() + "��ǰ��������"
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
				
				
				// �õ��������
				long period = 2 * 60 * 60 * 1000;
				
				// ����Timer���schedule����������ʱ����
				Timer timer = new Timer(vo.getMailTitle(), true);
				timer.schedule(new DataExportTask(vo), date.getTime(), period);
				
				logger.info("��ǰ����ִ��ʱ�䣺" + date.getTime().toString() + "��ǰ��������"
						+ vo.getMailTitle());
			}		
			else
			{
				logger.info("��ǰ���������ִ��ʱ�������д���鿴���������groupID��"
						+ vo.getGroupId());
			}
		}
		
		logger.info("ͳһ���鵼������������ʼ��������");
	}
}
