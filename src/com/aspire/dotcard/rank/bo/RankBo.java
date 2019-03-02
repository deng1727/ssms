package com.aspire.dotcard.rank.bo;

import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.threadtask.ReflectedTask;
import com.aspire.common.threadtask.TaskRunner;
import com.aspire.common.util.ListUtils;
import com.aspire.dotcard.rank.dao.RankDao;
import com.aspire.dotcard.rank.multihandle.RankHandle;
import com.aspire.dotcard.rank.vo.RankVo;
import com.aspire.ponaadmin.web.db.TransactionDB;


public class RankBo {

	private static RankBo instance = new RankBo();
	private static final int COUNT = 100;
	private RankBo() {
	}

	/**
	 * 得到单例模式
	 * 
	 */
	public static RankBo getInstance() {
		return instance;
	}
	
	
	public void insertVos(List<RankVo> vos) throws DAOException{
		TaskRunner dataSynTaskRunner = new TaskRunner(10,0);
		List<List<RankVo>> averageVos = ListUtils.averageAssign(vos, COUNT);
		RankDao.getTransactionInstance(null).doTruncateTable();
		for (List<RankVo> rankVos : averageVos) {
			RankHandle rh = new RankHandle(rankVos);
			ReflectedTask task = new ReflectedTask(rh, "multiInsert", null, null);
			dataSynTaskRunner.addTask(task);
		}
		   dataSynTaskRunner.waitToFinished();
	       dataSynTaskRunner.stop();
	}
}
