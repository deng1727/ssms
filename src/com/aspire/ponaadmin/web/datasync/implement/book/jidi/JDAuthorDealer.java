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
	 * 日志引用
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
		// 新增
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
		else if ("2".equals(changeType))// 更新
		{
			 //需要更新t_r_gcontent书的书名。为了提高效率，使用自定义sql
			GContentDAO.getInstance().updateContentAuhor(vo);
			return JDSyncDAO.getInstance().updateAuthor(vo);
		   
			
		}
		else if ("3".equals(changeType))// 删除
		{
			//不需要删除该作者对应的书名。
			return JDSyncDAO.getInstance().deleteAuthor(vo);
		}
		else
		{
			logger.error("changeType类型出错");
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
