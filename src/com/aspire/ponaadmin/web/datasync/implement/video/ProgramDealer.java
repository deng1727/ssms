package com.aspire.ponaadmin.web.datasync.implement.video;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataDealer;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConfig;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;

/**
 * <p>
 * �Խ��������Ƶ���ݽ��д����BO��
 * </p>
 * <p>
 * Copyright (c) 2008 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved
 * </p>
 * 
 * @author bihui
 * @version 1.1.1.0
 */
public class ProgramDealer implements DataDealer
{
	/**
	 * ��־����
	 */
	private static final JLogger logger = LoggerFactory.getLogger(ProgramDealer.class);

	public int dealDataRecrod(DataRecord record)
	{
        ProgramVO program = new ProgramVO();
        program.setProgramId((String) record.get(1));
        program.setProgramName((String) record.get(2));
        program.setContentId((String) record.get(3));

		String insertSqlCode = "com.aspire.ponaadmin.web.datasync.implement.video.ProgramDealer.insert";
		String updateSqlCode = "com.aspire.ponaadmin.web.datasync.implement.video.ProgramDealer.update";
		Object[] paras = { program.getProgramName(), program.getContentId(), program.getProgramId() };
        
		int updateresult = 0;
		try
		{
			updateresult = DB.getInstance().executeBySQLCode(updateSqlCode, paras);
			if (updateresult == 0)
			{
				// ���½����Ϊ0,�����
				DB.getInstance().executeBySQLCode(insertSqlCode, paras);
			}
		} catch (DAOException e)
		{
			logger.error("ִ����Ƶ��Ŀ���ݲ���ʧ��" + e);
			e.printStackTrace();
			return DataSyncConstants.FAILURE_ADD;
		}

		return DataSyncConstants.SUCCESS_ADD;
	}

	public void init(DataSyncConfig config)
	{
	}

	public void clearDirtyData()
	{
	}

	public void prepareData()
	{
	}
}
