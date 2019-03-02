package com.aspire.dotcard.rank.multihandle;

import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.rank.dao.RankDao;
import com.aspire.dotcard.rank.vo.RankVo;

public class RankHandle  {

	private static JLogger LOG = LoggerFactory.getLogger(RankHandle.class);
	private List<RankVo> vos = null;

	public RankHandle(List<RankVo> rankVos) {
		this.vos = rankVos;
	}

	public void multiInsert() {
		try {
			RankDao.getTransactionInstance(null).insertBatch(vos);
		} catch (DAOException e) {
			LOG.error(e);
		}
	}

}
