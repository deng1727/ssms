package com.aspire.ponaadmin.web.datasync.implement.game.uainfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataDealer;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConfig;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.DataSyncTask;
import com.aspire.ponaadmin.web.datasync.implement.game.GameSyncDAO;
import com.aspire.ponaadmin.web.datasync.implement.game.GameSyncTools;
/**
 * UA�ֵ�Ĵ����ࡣ���δ���ֻ�ǰѴӻ���UA�ֵ��ļ�uaInfo.data��UA���浽���ݿ��б�t_game_ua_mapping�С�
 * ����MM��devicename�ͻ���UA��Ӧ��ϵ��ά����Ҫ��Ӫ��ά����
 * @author zhangwei
 *
 */
public class UAInfoDealer implements DataDealer
{
	private JLogger LOG=LoggerFactory.getLogger(UAInfoDealer.class);
	//���浱ǰϵͳ����baseUA��key ��ʾbaseUA��
	//value��ʾ��Ϸ���µ�״̬�� 0��ʾ��ͬ��ǰ�����״̬��2��ʾϵͳ���ڸ�UA��Ϣ
	private HashMap hashMap;
	//���ø�dealer��task�����ڱ���ִ�еĽ����Ϣ��
	private DataSyncTask task;

	public void clearDirtyData()
	{
		hashMap=null;
	}

	public int dealDataRecrod(DataRecord record)
	{
		LOG.info("��ʼ�������UA�ֵ���Ϣ��");
		int size=record.size();
		for(int i=0;i<size;i++)
		{
			String baseUA=(String)record.get(i+1);
			if(hashMap.containsKey(baseUA))
			{
				hashMap.put(baseUA, "2");//���ڵĻ������ñ�־λΪ2
			}else
			{
				try
				{
					GameSyncDAO.getInstance().addGameInfo(baseUA);
					task.addStatisticCount(DataSyncConstants.SUCCESS_ADD);
				} catch (DAOException e)
				{
					LOG.error("��������UA��Ϣ����,baseUA="+baseUA,e);
					task.addStatisticCount(DataSyncConstants.FAILURE_ADD);
				}
				
			}
		}
		//ɾ�����ڵ�baseUA
		List delList=new ArrayList();
		Iterator iterator= hashMap.keySet().iterator();
		while(iterator.hasNext())
		{
			Object tmp=iterator.next();
			if("0".equals(hashMap.get(tmp)))//��־λΪ0��ʾ�˴�ͬ��û�и��¹���
			{
				delList.add(tmp);
			}
		}
		if(delList.size()>0)
		{
			try
			{
				task.addSuccessDelete(GameSyncDAO.getInstance().delGameInfo(delList));
			} catch (DAOException e)
			{
				LOG.error("ɾ������UA��Ϣ����",e);
				task.addFailureProcess(delList.size());
			}
		}
       
		return 0;
	}

	public void prepareData() throws Exception
	{
		hashMap=GameSyncTools.getInstance().getAllBaseUA();
	}

	public void init(DataSyncConfig config) throws Exception
	{

	}
	/**
	 * ������ô˴������task
	 * @param task
	 */
	public void setSyncTask(DataSyncTask task)
	{
		this.task=task;
	}
	

}
