package com.aspire.ponaadmin.web.datasync.implement.book.jidi;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.gcontent.GContentDAO;
import com.aspire.ponaadmin.web.datasync.DataDealer;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConfig;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;

public class JDAuthorDealer implements DataDealer
{
	/**
	 * ��־����
	 */
	private static final JLogger logger = LoggerFactory.getLogger(JDAuthorDealer.class);

	public void clearDirtyData()
	{
	}

	public int dealDataRecrod(DataRecord record) throws Exception
	{
		AuthorVO vo = new AuthorVO();
		vo.setId((String) record.get(1));
		vo.setName((String) record.get(2));
		vo.setDesc((String) record.get(3));
		String changeType = (String) record.get(4);
		// ����
		if ("1".equals(changeType))
		{
			if (JDSyncDAO.getInstance().isExistedVO(vo))
			{
				return DataSyncConstants.FAILURE_ADD_EXIST;
			}
			else
			{
				return JDSyncDAO.getInstance().insertAuthor(vo);
			}
		}
		else if ("2".equals(changeType))// ����
		{
			 //��Ҫ����t_r_gcontent���������Ϊ�����Ч�ʣ�ʹ���Զ���sql
			GContentDAO.getInstance().updateContentAuhor(vo);
			return JDSyncDAO.getInstance().updateAuthor(vo);
		   
			
		}
		else if ("3".equals(changeType))// ɾ��
		{
			//����Ҫɾ�������߶�Ӧ��������
			return JDSyncDAO.getInstance().deleteAuthor(vo);
		}
		else
		{
			logger.error("changeType���ͳ���");
			return DataSyncConstants.FAILURE_NOT_CHANGETYPE;
		}

	}

	public void init(DataSyncConfig config) throws Exception
	{
	}

	public void prepareData()
	{
		
	}

}
