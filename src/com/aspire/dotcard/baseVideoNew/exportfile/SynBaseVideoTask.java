package com.aspire.dotcard.baseVideoNew.exportfile;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseVideo.conf.BaseVideoConfig;
import com.aspire.dotcard.baseVideoNew.exportfile.impl.ProgramByHourExportFile;
import com.aspire.dotcard.baseVideoNew.exportfile.impl.RecommendByHourExportFile;
import com.aspire.dotcard.baseVideoNew.exportfile.impl.VideoByHourExportFile;
import com.aspire.dotcard.baseVideoNew.config.BaseVideoNewConfig;
import com.aspire.dotcard.baseVideoNew.exportfile.impl.VideoExportFile;
import com.aspire.ponaadmin.web.util.PublicUtil;

public class SynBaseVideoTask
{
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
		boolean flag = true;
		if (tempFileName.indexOf(BaseVideoNewConfig.DEL_SHELL_FILE) > 0)
		{
			flag = false;
		}
		// У��������ȷ�ԣ����У��ʧ�ܼ����ʼ���Ϣ
		if (!BaseVideoConfig.CHECK_DATA_SUCCESS
				.equals(base.checkData(tempData,flag)))
		{
			// ���������Ϣ���ʼ���...................
			base.writeErrorToMail(tempFileName, lineNumeber, lineText, "У��ʧ��");
			base.setFailureCheckAdd();
			return;
		}
		
		logger
				.debug("��ǰ�ļ���Ϊ��"
						+ tempFileName
						+ "�� �ж��Ƿ�Ϊɾ���ļ�: "
						+ (tempFileName
								.indexOf(BaseVideoNewConfig.DEL_SHELL_FILE) > 0));
		
		if (base instanceof VideoExportFile)
		{
			exportDBText = exportDataToDBVideo(tempData);
		}
		// ��������ɵ�ɾ���ļ�
		else if (tempFileName.indexOf(BaseVideoNewConfig.DEL_SHELL_FILE) > 0)
		{
			exportDBText = exportDataToDBDel(tempData);
		}
		//��Ƶ��Сʱ����
		else if (base instanceof VideoByHourExportFile)
		{
			exportDBText = exportDataToDBByVideoHour(tempData);
		}
		////��Ŀ���鰴Сʱ����
		else if (base instanceof ProgramByHourExportFile)
		{
			exportDBText = exportDataToDBByProgramHour(tempData);
		}
		////�ȵ������Ƽ����ݰ�Сʱ����
		else if (base instanceof RecommendByHourExportFile)
		{
			exportDBText = exportDataToDBByRecommendHour(tempData);
		}
		else
		{
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
	 * ʵ����ʵ�ְ����ݴ�����������Ƶ�ļ�
	 * 
	 * @param data
	 *            ������Ϣ
	 * @return ������ȷ/���ش�����Ϣ
	 */
	private String exportDataToDBVideo(String[] data)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("��ʼ�洢����������.");
		}
		
		String insertSqlCode = base.getInsertSqlCode();
		String delSqlCode = base.getDelSqlCode();
		String key = base.getKey(data);
		Object[] paras = base.getObject(data);
		Object[] paras_d = base.getDelKey(key);
		try
		{
			if ("1".equals(data[5]))
			{
				DB.getInstance().executeBySQLCode(insertSqlCode, paras);
			}
			else
			{
				DB.getInstance().executeBySQLCode(delSqlCode, paras_d);
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
				if(base.isCollect){
					base.setAddNumAdd();
				}
			}
			else
			{
				DB.getInstance().executeBySQLCode(updateSqlCode, paras);
				base.delKeyMap(key);
                if(base.isCollect){
                	base.setModifyNumAdd();
				}
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
	 * ���ڰ�ɾ�����͵�����д���м����
	 * 
	 * @param data
	 *            ������Ϣ
	 * @return ������ȷ/���ش�����Ϣ
	 */
	private String exportDataToDBDel(String[] data)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("��ʼ�洢����������.");
		}
		
		String delSqlCode = base.getDelSqlCode();
		String key = base.getKey(data);
		try
		{
			DB.getInstance().executeBySQLCode(delSqlCode, base.getDelKey(key));
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
	public String exportDataToDBByVideoHour(String[] data)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("��ʼ�洢����������.");
		}

		String insertSqlCode = base.getInsertSqlCode();
		String updateSqlCode = base.getUpdateSqlCode();
		String delSqlCode = base.getDelSqlCode();
		Object[] paras = base.getObject(data);
		String key = base.getKey(data);
		try
		{
			if ("1".equals(data[5]))
			{
				//��ƵID�������ܴ󣬲���ID���ڴ�������ʧ�������´���
				try {
					
					DB.getInstance().executeBySQLCode(insertSqlCode, paras);
					
				} catch (DAOException e) {
					
					try {
						DB.getInstance().executeBySQLCode(updateSqlCode, paras);
					} catch (DAOException ex) {
						logger.error("ִ�л�����Ƶ���ݰ�Сʱ����ʧ�ܣ���ǰ��������Ϊ��" + key + "�����������Ϊ��" + data[5]
						+ ". " + ex);
						 
						 return "ִ��ʱ�������ݿ��쳣";
					}
					
				}
				
			}
			else if ("2".equals(data[5]))
			{
				DB.getInstance().executeBySQLCode(delSqlCode, new Object[]{data[0],data[1]});
			}
			else
			{
				logger.error("��ǰ�����ݱ���������󣬵�ǰ��������Ϊ��" + key + "�����������Ϊ��"
						+ data[5]);
				return "ִ��ʱ�������ݿ��쳣";
			}
		}
		catch (DAOException e)
		{
			logger.error("ִ�л�����Ƶ���ݰ�Сʱ����ʧ�ܣ���ǰ��������Ϊ��" + key + "�����������Ϊ��" + data[5]
					+ ". " + e);
			e.printStackTrace();
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
	public String exportDataToDBByProgramHour(String[] data)
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
			}
			else
			{
				DB.getInstance().executeBySQLCode(insertSqlCode, paras);
			}
			
		}
		catch (DAOException e)
		{
			logger.error("ִ�л��ؽ�Ŀ�������ݰ�Сʱ����ʧ�ܣ���ǰ��������Ϊ��" + key + "��" + e);
			e.printStackTrace();
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
	public String exportDataToDBByRecommendHour(String[] data)
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
			}
			else
			{
				DB.getInstance().executeBySQLCode(insertSqlCode, paras);
			}
			
		}
		catch (DAOException e)
		{
			logger.error("ִ�л����ȵ������Ƽ����ݰ�Сʱ��������ʧ�ܣ���ǰ��������Ϊ��" + key + "��" + e);
			e.printStackTrace();
			return "ִ��ʱ�������ݿ��쳣";
		}

		return BaseVideoConfig.CHECK_DATA_SUCCESS;
	}
}
