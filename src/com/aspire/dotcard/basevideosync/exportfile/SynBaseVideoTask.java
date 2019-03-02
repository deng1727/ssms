package com.aspire.dotcard.basevideosync.exportfile;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basevideosync.config.BaseVideoConfig;
import com.aspire.dotcard.basevideosync.exportfile.impl.PkgSalesExportFile;
import com.aspire.dotcard.basevideosync.exportfile.impl.ProgramByHourExportFile;
import com.aspire.ponaadmin.web.util.PublicUtil;

public class SynBaseVideoTask {

	private JLogger logger = LoggerFactory.getLogger(SynBaseVideoTask.class);
	
	/**
	 * ��ǰ����Ϣ
	 */
	private String lineText;
	
	/**
	 * ��ǰ�����ļ���
	 */
	private String tempFileName;
	
	/**
	 * ��ǰ�ǵڼ���
	 */
	private int lineNumeber;
	
	/**
	 * ��ǰ�еķָ���
	 */
	private String dataSpacers;
	
	/**
	 * ����ִ��������
	 */
	private BaseExportFile base;
	
	public SynBaseVideoTask(String lineTest, String tempFileName,
			int lineNumeber, String dataSpacers, BaseExportFile base)
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
			logger.debug("��ʼ����У���ļ���" + lineNumeber + "�����ݡ��ļ�����" + tempFileName);
		}
		
		if (lineNumeber == 1)
		{
			// ɾ����һ��bom�ַ�
			lineText = PublicUtil.delStringWithBOM(lineText);
		}
		
		// ��ʱ����
		String[] tempData = lineText.split(dataSpacers, -1);
		String exportDBText;

		// У��������ȷ�ԣ����У��ʧ�ܼ����ʼ���Ϣ
		if (!BaseVideoConfig.CHECK_DATA_SUCCESS
				.equals(base.checkData(tempData)))
		{
			// ���������Ϣ���ʼ���...................
			base.writeErrorToMail(tempFileName, lineNumeber, lineText, "У��ʧ��");
			base.setFailureCheckAdd();
			return;
		}
		//��Ƶ��Ŀ�������ݵ���
		if (base instanceof ProgramByHourExportFile)
		{
			exportDBText = exportDataToDBByProgramHour(tempData);
		}
		//������Ƶ��Ʒ�������Ʒ����ݵ���
		else if(base instanceof PkgSalesExportFile)
		{
			exportDBText = exportDataToDBByPkgSales(tempData);
		}else{
			exportDBText = exportDataToDB(tempData);
		}
		
		// �������ݿ⣬�������ʧ�ܼ����ʼ���Ϣ
		if (!BaseVideoConfig.EXPORT_DATA_SUCCESS.equals(exportDBText))
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
	private String exportDataToDB(String[] data)
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
				DB.getInstance().executeBySQLCode(insertSqlCode, paras);
				base.delKeyMap(key);
				base.setAddNumAdd();
			}
			else
			{
				DB.getInstance().executeBySQLCode(updateSqlCode, paras);
				base.delKeyMap(key);
				base.setModifyNumAdd();
			}
		}
		catch (DAOException e)
		{
			logger.error("ִ�л�����Ƶ���ݲ���ʧ�ܣ���ǰ��������Ϊ��" + key + "��", e);
			return "ִ��ʱ�������ݿ��쳣";
		}
		
		return BaseVideoConfig.CHECK_DATA_SUCCESS;
	}
	
	/**
	 * ʵ����ʵ�ְ����ݴ������
	 * 
	 * @param data
	 *            ������Ϣ
	 * @return ������ȷ/���ش�����Ϣ
	 */
	private String exportDataToDBByPkgSales(String[] data)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("��ʼ�洢����������.");
		}
		
		String insertSqlCode = base.getInsertSqlCode();
		Object[] paras = base.getObject(data);
		String key = base.getKey(data);
		try
		{
			//��Ʒ�������Ʒ�����ֻ���������
			DB.getInstance().executeBySQLCode(insertSqlCode, paras);
			base.setAddNumAdd();
		}
		catch (DAOException e)
		{
			logger.error("ִ�л�����Ƶ���ݲ���ʧ�ܣ���ǰ��������Ϊ��" + key + "��", e);
			return "ִ��ʱ�������ݿ��쳣";
		}
		
		return BaseVideoConfig.CHECK_DATA_SUCCESS;
	}
	
	/**
	 * ʵ����ʵ�ְ����ݴ������
	 * 
	 * @param data
	 *            ������Ϣ
	 * @return ������ȷ/���ش�����Ϣ
	 */
	private String exportDataToDBByProgramHour(String[] data)
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
			if(base.hasKey(key))
			{
				DB.getInstance().executeBySQLCode(updateSqlCode, paras);
				base.setModifyNumAdd();
			}
			else
			{
				DB.getInstance().executeBySQLCode(insertSqlCode, paras);
				base.setAddNumAdd();
			}
		}
		catch (DAOException e)
		{
			logger.error("ִ�л��ؽ�Ŀ���ݰ�Сʱ����ʧ�ܣ���ǰ��������Ϊ��" + key + "��" + e);
			e.printStackTrace();
			return "ִ��ʱ�������ݿ��쳣";
		}
		
		return BaseVideoConfig.CHECK_DATA_SUCCESS;
	}
}
