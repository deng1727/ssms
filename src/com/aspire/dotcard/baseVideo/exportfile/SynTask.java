package com.aspire.dotcard.baseVideo.exportfile;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseVideo.conf.BaseVideoConfig;
import com.aspire.dotcard.baseVideo.exportfile.impl.ProductExportFile;
import com.aspire.dotcard.baseVideo.exportfile.impl.ProgramByHourExportFile;
import com.aspire.dotcard.baseVideo.exportfile.impl.ProgramExportFile;
import com.aspire.dotcard.baseVideo.exportfile.impl.RecommendByHourExportFile;
import com.aspire.dotcard.baseVideo.exportfile.impl.VideoByHourExportFile;
import com.aspire.dotcard.baseVideo.exportfile.impl.VideoDetailExportFile;
import com.aspire.dotcard.baseVideo.exportfile.impl.VideoExportFile;
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
		if (!BaseVideoConfig.CHECK_DATA_SUCCESS
				.equals(base.checkData(tempData)))
		{
			// ���������Ϣ���ʼ���...................
			base.writeErrorToMail(tempFileName, lineNumeber, lineText, "У��ʧ��");
			base.setFailureCheckAdd();
			return;
		}

		String exportDBText;

		if (base instanceof VideoDetailExportFile)
		{
			exportDBText = exportDataToDBByVideoDetail(tempData);
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
		
		else if (!base.isDelTable && base instanceof VideoExportFile)
		{
			exportDBText = exportDataToDBByVideo(tempData);
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
			if (base.isDelTable && base instanceof ProgramExportFile)
			{
				DB.getInstance().executeBySQLCode(insertSqlCode, paras);
				return BaseVideoConfig.CHECK_DATA_SUCCESS;
			}
			else if (base instanceof VideoExportFile)
			{
				DB.getInstance().executeBySQLCode(insertSqlCode, paras);
				return BaseVideoConfig.CHECK_DATA_SUCCESS;
			}
			else
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
				
				if (base instanceof ProductExportFile)
				{
					// ����ǲ�Ʒ����ɾ���������еģ����ʣ�µģ�����Ӧ�����ߵ�
					ProductExportFile pbase = (ProductExportFile) base;
					pbase.delKeyMap(key);
				}
			}
		}
		catch (DAOException e)
		{
			logger.error("ִ�л�����Ƶ���ݲ���ʧ�ܣ���ǰ��������Ϊ��" + key + "��" + e);
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
	public String exportDataToDBByVideo(String[] data)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("��ʼ�洢����������.");
		}

		String insertSqlCode = base.getInsertSqlCode();
		String updateSqlCode = base.getUpdateSqlCode();
		Object[] paras = base.getObject(data);
		Object[] paras_del = base.getHasObject(data);
		String key = base.getKey(data);
		try
		{
			if ("1".equals(data[5]))
			{
				DB.getInstance().executeBySQLCode(insertSqlCode, paras);
			}
			else if ("2".equals(data[5]))
			{
				DB.getInstance().executeBySQLCode(updateSqlCode, paras_del);
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
			logger.error("ִ�л�����Ƶ���ݲ���ʧ�ܣ���ǰ��������Ϊ��" + key + "�����������Ϊ��" + data[5]
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
	public String exportDataToDBByVideoDetail(String[] data)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("��ʼ�洢����������.");
		}
		String insertSqlCode = base.getInsertSqlCode();
		String updateSqlCode = base.getUpdateSqlCode();
		Object[] insertParas = base.getObject(data);
		String key = base.getKey(data);

		try
		{
			if (base.hasKey(key))
			{
				DB.getInstance().executeBySQLCode(updateSqlCode, insertParas);
			}
			else
			{
				DB.getInstance().executeBySQLCode(insertSqlCode, insertParas);
			}
		}
		catch (DAOException e)
		{
			logger.error("ִ�л�����Ƶ���ݲ���ʧ��" + e);
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
		Object[] paras_del = base.getHasObject(data);
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
				DB.getInstance().executeBySQLCode(delSqlCode, paras_del);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#getObject(java.lang.String[])
	
	private Object[] getUpdateObject(String[] data)
	{
		Object[] object = new Object[3];

		object[0] = data[1];
		object[1] = data[2];
		object[2] = data[0];

		return object;
	} */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#getObject(java.lang.String[])
	 
	private String getVideoDetailKey(String[] data)
	{
		return data[0];
	}*/

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#getUpdateSqlCode()
	 
	private String getUpdateSqlCode1()
	{
		// update T_VO_VIDEODETAIL v set v.totalplaynum = v.totalplaynum +
		// v.dayplaynum, v.totaldownloadnum = v.totaldownloadnum +
		// v.daydownloadnum, v.updatetime = sysdate where v.programid = ?
		return "baseVideo.exportfile.VideoDetailExportFile.getUpdateSqlCode1";
	}*/

}
