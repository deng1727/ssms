package com.aspire.ponaadmin.web.dataexport.sqlexport.timer;

import java.util.Calendar;
import java.util.List;
import java.util.TimerTask;

import com.aspire.common.db.DAOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.dataexport.sqlexport.VO.DataExportGroupVO;
import com.aspire.ponaadmin.web.dataexport.sqlexport.VO.DataExportVO;
import com.aspire.ponaadmin.web.dataexport.sqlexport.dao.DataExportDAO;
import com.aspire.ponaadmin.web.dataexport.sqlexport.exe.ExportSqlFactory;
import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.util.HttpUtil;

/**
 * <p>
 * 数据同步任务器
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
public class DataExportTask extends TimerTask
{
	/**
	 * 日志引用
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(DataExportTask.class);
	
	private DataExportGroupVO groupVO ;
	
	public DataExportTask(DataExportGroupVO groupVO)
	{
		super();
		this.groupVO = groupVO;
	}
	
	/**
	 * 执行任务（业务同步和内容同步）
	 */
	public void run()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("导出任务模块开始...");
		}
		
		StringBuffer msgInfo = new StringBuffer();
		List<DataExportVO> list = null;
		
		String[] mailTo = groupVO.getToMail().split(",");
		
		long starttime = System.currentTimeMillis();
		Calendar startDate = Calendar.getInstance();
		msgInfo.append("导出任务模块开始执行时间：").append(
				startDate.get(Calendar.HOUR_OF_DAY)).append(":").append(
				startDate.get(Calendar.MINUTE)).append("<br>");
		
		// 得到当前分组下的生成任务
		try
		{
			list = DataExportDAO.getInstance().queryDataExportList(groupVO.getGroupId());
		}
		catch (DAOException e)
		{
			logger.error("当前分组任务获得分组下生成任务时发生了错误 ！",e);
			return;
		}
		
		if(list!=null&&list.size()>0)
		{
			for (DataExportVO dataExportVO : list)
			{
				msgInfo.append(dataExportVO.getExportName()).append("任务执行结果:");
				msgInfo.append(ExportSqlFactory.getExportSql(dataExportVO)
						.createFile(groupVO));
				msgInfo.append("<br>");
			}
		}
		
		if (list.size() == 0)
		{
			Mail.sendMail(groupVO.getMailTitle(), "当前分组没有任务可执行！", mailTo);
		}
		else
		{
			// 如果有url通知地址
			if(null != groupVO.getUrl() && !"".equals(groupVO.getUrl()))
			{
				msgInfo.append("<br>").append("发起URL请求通知结果：").append(
						waken(groupVO.getUrl())).append(".").append("<br>");
			}
			
			long endtime = System.currentTimeMillis();
			Calendar endDate = Calendar.getInstance();
			
			msgInfo.append("导出任务模块执行结束时间：").append(
					endDate.get(Calendar.HOUR_OF_DAY)).append(":").append(
					endDate.get(Calendar.MINUTE)).append("<br>");
			msgInfo.append("执行总耗时为：").append(
					String.valueOf(endtime - starttime)).append("毫秒");
			
			Mail.sendMail(groupVO.getMailTitle(), msgInfo.toString(), mailTo);
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("导出任务模块结束...");
		}
	}
	
	/**
	 * url通知方法
	 * 
	 * @param url
	 *            通知url
	 * @return
	 */
	private String waken(String url)
	{
		Object[] object = null;

		try
		{
			object = HttpUtil.getResponseCodeAndRespFromURL(url, "utf-8");
		}
		catch (Exception e)
		{
			logger.error("文件导出结束url通知方法失败", e);
		}
		
		if(object != null)
		{
			if ("200".equals(String.valueOf(object[0])))
			{
				logger.info("当前通知url通知成功!");
				return "通知成功. " + "<br>";
			}
			else
			{
				logger.error("当前通知失败的消息url：" + url + ". 返回信息:" + String.valueOf(object[1]));
				return " 通知失败. url=" + url + "<br>";
			}
		}
		else
		{
			return " 通知失败. url=" + url + "<br>";
		}

	}
}
