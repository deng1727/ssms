/*
 * 
 */

package com.aspire.ponaadmin.web.datasync.implement.htc;

import java.util.Set;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataDealer;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConfig;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;

/**
 * @author x_wangml
 * 
 */
public class DataHTCDownloadDealer implements DataDealer
{
	private int num = 0;
	private Set<String> htcDataSet = null;
	
	/**
	 * ��־����
	 */
	private static final JLogger logger = LoggerFactory
			.getLogger(DataHTCDownloadDealer.class);
	
	@Override
	public void clearDirtyData()
	{
		htcDataSet.clear();
	}
	
	@Override
	public int dealDataRecrod(DataRecord record) throws Exception
	{
		DataHTCDownloadVO vo = null;
		
		try
		{
			vo = this.getDataHTCDownloadVO(record);
		}
		catch (Exception e)
		{
			logger.error("���HTC��������ʱ������ԭ����Ϊģ������ʱ����");
			return DataSyncConstants.FAILURE;
		}
		
		// ����update
		if (hasKey(vo.getMmContentId()))
		{
			DataHTCDownloadDAO.getInstance().updateHTCDownloadData(vo);
			return DataSyncConstants.SUCCESS_UPDATE;
		}
		// ������add + map
		else
		{
			DataHTCDownloadDAO.getInstance().addHTCDownloadData(vo);
			return DataSyncConstants.SUCCESS_ADD;
		}
	}
	
    public synchronized boolean hasKey(String key)
    {
    	logger.info("���ڼ�������Ƿ���ڵĶ��̰߳�ȫ����htcDataSet.size" + htcDataSet.size() + "  ��key=" + key + ". num = " + num);
        if(!htcDataSet.contains(key))
        {
        	htcDataSet.add(key);
        	logger.info("false !!!!!!! key=" + key+ ". num = " + num++);
            return false;
        }
        else
        {
        	logger.info("true  !!!!!!! key=" + key+ ". num = " + num++);
            return true;
        }
    }
	
	/**
	 * ���ڽ���ԭ����Ϊģ������
	 * @param record
	 * @return
	 */
	private DataHTCDownloadVO getDataHTCDownloadVO(DataRecord record)
	{
		DataHTCDownloadVO vo = new DataHTCDownloadVO();
		vo.setApCode(String.valueOf(record.get(1)));
		vo.setAppId(String.valueOf(record.get(2)));
		vo.setMmContentId(String.valueOf(record.get(3)));
		vo.setDownCount(String.valueOf(record.get(4)));
		return vo;
	}
	
	@Override
	public void prepareData() throws Exception
	{
		htcDataSet = DataHTCDownloadDAO.getInstance().queryHTCDownload();
		num = 0;
	}
	
	@Override
	public void init(DataSyncConfig config) throws Exception
	{

	}
	
}
