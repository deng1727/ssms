package com.aspire.dotcard.baseVideoNew.sync;

import java.util.TimerTask;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseVideo.bo.BaseVideoFileBO;
import com.aspire.dotcard.baseVideo.bo.CollectionBO;
import com.aspire.dotcard.baseVideoNew.bo.BaseVideoDeleteBlackBO;
import com.aspire.dotcard.baseVideoNew.bo.BaseVideoNewFileBO;

public class BaseVideoNewTask extends TimerTask 
{
	protected static JLogger logger = LoggerFactory
			.getLogger(BaseVideoNewTask.class);
	
	public void run()
	{
		StringBuffer msgInfo = new StringBuffer();
		logger.info("基地视频数据全量同步开始！！！！！");
		// 开始同步之旅
		BaseVideoNewFileBO.getInstance().fileDataSync();
		
		// 调用存储过程 用以执行中间表与正式表中数据转移
		boolean syncTrue = BaseVideoNewFileBO.getInstance().syncVideoData();
		
		// 如果执行正确
		if (syncTrue)
		{
			msgInfo.append("调用存储过程 用以执行中间表与正式表中数据转移完成！");
			
			// 用于清空旧的模拟表数据，以用于全量更新
			BaseVideoNewFileBO.getInstance().cleanOldSimulationData(msgInfo);
			
			// 根据现有表情况，组装模拟表
			BaseVideoNewFileBO.getInstance().diyDataTable(msgInfo);
			
			// 用于统计子栏目数与栏目下节目数
			BaseVideoNewFileBO.getInstance().updateCategoryNodeNum();
		}
		else
		{
			msgInfo.append("调用存储过程 用以执行中间表与正式表中数据转移时发生错误，后续动作取消！");
		}
		// 查询中间表存储分类情况用以邮件显示 
		msgInfo.append(BaseVideoNewFileBO.getInstance().getMailText());
		
        BaseVideoFileBO.getInstance().sendResultMail("基地视频组装模拟货架结果邮件", msgInfo);
        
		//开始同步内容集节点及内容集数据
		BaseVideoNewFileBO.getInstance().syncCollectAndNodeData();
		UpdateIsshowFieldTask isshowTask= new UpdateIsshowFieldTask();
		BaseVideoDeleteBlackBO backBO = new BaseVideoDeleteBlackBO();
		backBO.delVideoBlack();
//        CollectionBO collectionBo = new CollectionBO();
//        collectionBo.updateCollections();
	    try {
			isshowTask.updateIsshowField();
		} catch (BOException e) {			
			logger.error("批量更新指定内容集(isShow字段)为是时发生异常！", e);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			logger.error("批量更新指定内容集(isShow字段)为是时发生异常！数据库更新失败！", e);
		}
		
		BaseVideoNewFileBO.getInstance().costProductRelation();

	}
}
