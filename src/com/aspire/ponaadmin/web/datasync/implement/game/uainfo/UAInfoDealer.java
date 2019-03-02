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
 * UA字典的处理类。本次处理，只是把从基地UA字典文件uaInfo.data中UA保存到数据库中表t_game_ua_mapping中。
 * 至于MM的devicename和基地UA对应关系的维护需要运营区维护。
 * @author zhangwei
 *
 */
public class UAInfoDealer implements DataDealer
{
	private JLogger LOG=LoggerFactory.getLogger(UAInfoDealer.class);
	//保存当前系统存在baseUA。key 表示baseUA，
	//value表示游戏更新的状态。 0表示从同步前的最初状态，2表示系统存在该UA信息
	private HashMap hashMap;
	//调用该dealer的task。用于保存执行的结果信息。
	private DataSyncTask task;

	public void clearDirtyData()
	{
		hashMap=null;
	}

	public int dealDataRecrod(DataRecord record)
	{
		LOG.info("开始处理基地UA字典信息。");
		int size=record.size();
		for(int i=0;i<size;i++)
		{
			String baseUA=(String)record.get(i+1);
			if(hashMap.containsKey(baseUA))
			{
				hashMap.put(baseUA, "2");//存在的话，设置标志位为2
			}else
			{
				try
				{
					GameSyncDAO.getInstance().addGameInfo(baseUA);
					task.addStatisticCount(DataSyncConstants.SUCCESS_ADD);
				} catch (DAOException e)
				{
					LOG.error("新增基地UA信息出错,baseUA="+baseUA,e);
					task.addStatisticCount(DataSyncConstants.FAILURE_ADD);
				}
				
			}
		}
		//删除过期的baseUA
		List delList=new ArrayList();
		Iterator iterator= hashMap.keySet().iterator();
		while(iterator.hasNext())
		{
			Object tmp=iterator.next();
			if("0".equals(hashMap.get(tmp)))//标志位为0表示此次同步没有更新过。
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
				LOG.error("删除基地UA信息出错",e);
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
	 * 保存调用此处理类的task
	 * @param task
	 */
	public void setSyncTask(DataSyncTask task)
	{
		this.task=task;
	}
	

}
