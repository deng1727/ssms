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
 * ����ͬ��������
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
	 * ��־����
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
	 * ִ������ҵ��ͬ��������ͬ����
	 */
	public void run()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("��������ģ�鿪ʼ...");
		}
		
		StringBuffer msgInfo = new StringBuffer();
		List<DataExportVO> list = null;
		
		String[] mailTo = groupVO.getToMail().split(",");
		
		long starttime = System.currentTimeMillis();
		Calendar startDate = Calendar.getInstance();
		msgInfo.append("��������ģ�鿪ʼִ��ʱ�䣺").append(
				startDate.get(Calendar.HOUR_OF_DAY)).append(":").append(
				startDate.get(Calendar.MINUTE)).append("<br>");
		
		// �õ���ǰ�����µ���������
		try
		{
			list = DataExportDAO.getInstance().queryDataExportList(groupVO.getGroupId());
		}
		catch (DAOException e)
		{
			logger.error("��ǰ���������÷�������������ʱ�����˴��� ��",e);
			return;
		}
		
		if(list!=null&&list.size()>0)
		{
			for (DataExportVO dataExportVO : list)
			{
				msgInfo.append(dataExportVO.getExportName()).append("����ִ�н��:");
				msgInfo.append(ExportSqlFactory.getExportSql(dataExportVO)
						.createFile(groupVO));
				msgInfo.append("<br>");
			}
		}
		
		if (list.size() == 0)
		{
			Mail.sendMail(groupVO.getMailTitle(), "��ǰ����û�������ִ�У�", mailTo);
		}
		else
		{
			// �����url֪ͨ��ַ
			if(null != groupVO.getUrl() && !"".equals(groupVO.getUrl()))
			{
				msgInfo.append("<br>").append("����URL����֪ͨ�����").append(
						waken(groupVO.getUrl())).append(".").append("<br>");
			}
			
			long endtime = System.currentTimeMillis();
			Calendar endDate = Calendar.getInstance();
			
			msgInfo.append("��������ģ��ִ�н���ʱ�䣺").append(
					endDate.get(Calendar.HOUR_OF_DAY)).append(":").append(
					endDate.get(Calendar.MINUTE)).append("<br>");
			msgInfo.append("ִ���ܺ�ʱΪ��").append(
					String.valueOf(endtime - starttime)).append("����");
			
			Mail.sendMail(groupVO.getMailTitle(), msgInfo.toString(), mailTo);
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("��������ģ�����...");
		}
	}
	
	/**
	 * url֪ͨ����
	 * 
	 * @param url
	 *            ֪ͨurl
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
			logger.error("�ļ���������url֪ͨ����ʧ��", e);
		}
		
		if(object != null)
		{
			if ("200".equals(String.valueOf(object[0])))
			{
				logger.info("��ǰ֪ͨurl֪ͨ�ɹ�!");
				return "֪ͨ�ɹ�. " + "<br>";
			}
			else
			{
				logger.error("��ǰ֪ͨʧ�ܵ���Ϣurl��" + url + ". ������Ϣ:" + String.valueOf(object[1]));
				return " ֪ͨʧ��. url=" + url + "<br>";
			}
		}
		else
		{
			return " ֪ͨʧ��. url=" + url + "<br>";
		}

	}
}
