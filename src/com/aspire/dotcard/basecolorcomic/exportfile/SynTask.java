package com.aspire.dotcard.basecolorcomic.exportfile;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basecolorcomic.conf.BaseColorComicConfig;
import com.aspire.ponaadmin.web.util.PublicUtil;

public class SynTask
{
	private JLogger logger = LoggerFactory.getLogger(SynTask.class);
	
	private String lineText;
	
	private String tempFileName;
	
	private int lineNumeber;
	
	private String dataSpacers;
	
	private BaseExportFile base;
	
	public SynTask(String lineTest, String tempFileName, int lineNumeber,
			String dataSpacers, BaseExportFile base)
			throws InstantiationException, IllegalAccessException
	{
		this.lineText = lineTest;
		this.tempFileName = tempFileName;
		this.lineNumeber = lineNumeber;
		this.dataSpacers = dataSpacers;
		this.base = base;
	}
	
	/**
	 * ���ڱ����̵߳��õľ���ִ�з���
	 * 
	 * @param lineText
	 * @param tempFileName
	 */
	public void sysDataByFile()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("��ʼ����У���ļ���" + lineNumeber + "�����ݡ�");
		}
		
		if (lineNumeber == 1)
		{
			// ɾ����һ��bom�ַ�
			lineText = PublicUtil.delStringWithBOM(lineText);
		}
		
		// ��ʱ����
		String[] tempData = lineText.split(dataSpacers, -1);
		
		// У��������ȷ�ԣ����У��ʧ�ܼ����ʼ���Ϣ
		if (!BaseColorComicConfig.CHECK_DATA_SUCCESS.equals(base
				.checkData(tempData)))
		{
			// ���������Ϣ���ʼ���...................
			base.writeErrorToMail(tempFileName, lineNumeber, lineText, "У��ʧ��");
			base.setFailureCheckAdd();
			return;
		}
		
		String exportDBText = exportDataToDB(tempData);
		
		// �������ݿ⣬�������ʧ�ܼ����ʼ���Ϣ
		if (!BaseColorComicConfig.EXPORT_DATA_SUCCESS.equals(exportDBText))
		{
			// ���������Ϣ���ʼ���...................
			base.writeErrorToMail(tempFileName, lineNumeber, lineText,
					exportDBText);
			base.setFailureProcessAdd();
			return;
		}
		else
		{
			base.setSuccessAddAdd();
		}
	}
	
	/**
	 * ʵ����ʵ�ְ����ݴ������
	 * 
	 * @param data
	 *            ������Ϣ
	 * @return ������ȷ/���ش�����Ϣ
	 */
	public String exportDataToDB(String[] data)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("��ʼ�洢����������.");
		}
		
		String insertSqlCode = base.getInsertSqlCode();
		String updateSqlCode = base.getUpdateSqlCode();
		Object[] paras = base.getObject(data);
		String key = base.getKey(data);
		
		try
		{
			if (!base.hasKey(key))
			{
				// ���½����Ϊ0,�����
				DB.getInstance().executeBySQLCode(insertSqlCode, paras);
			}
			else
			{
				DB.getInstance().executeBySQLCode(updateSqlCode, paras);
			}
		}
		catch (DAOException e)
		{
			logger.error("ִ�л�����Ƶ���ݲ���ʧ�ܣ���ǰ��������Ϊ��" + key + "��", e);
			return "ִ��ʱ�������ݿ��쳣";
		}
		
		return BaseColorComicConfig.CHECK_DATA_SUCCESS;
	}
}
