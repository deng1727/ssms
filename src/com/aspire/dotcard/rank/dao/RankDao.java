package com.aspire.dotcard.rank.dao;

import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.db.Executor;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.util.StringUtils;
import com.aspire.dotcard.rank.vo.RankVo;
import com.aspire.dotcard.syncAndroid.ppms.PPMSDAO;
import com.aspire.ponaadmin.web.db.TransactionDB;

public class RankDao {
	/**
	 * 日志引用
	 */
	private static JLogger LOG = LoggerFactory.getLogger(PPMSDAO.class);

	private TransactionDB transactionDB;

	private RankDao() {

	}

	/**
	 * 得到单例模式
	 * 
	 */
	public static RankDao getTransactionInstance(TransactionDB transactionDB) {
		RankDao dao = new RankDao();
		dao.transactionDB = transactionDB;
		return dao;
	}

	public void doInsertVo(RankVo vo) throws DAOException {

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertRankVO(" + vo + ")");
		}
		String sqlCode = "com.aspire.dotcard.rank.dao.RankDao.doInsertVo().INSERT";

		Object[] paras = { vo.getStat_time(), vo.getContent_id(),
				vo.getContent_name(), vo.getPrd_type_id(), vo.getAdd_dl_cnt(),
				vo.getAdd_fee(), vo.getDl_15days_cnt(), vo.getView_7days_cnt(),
				vo.getDl_7days_cnt(), vo.getFee_7days_cnt(),
				vo.getClass_dl_cnt(), vo.getSearch_dl_cnt(),
				vo.getHot_dl_cnt(), vo.getMan_dl_cnt(), vo.getDl_cnt(),
				vo.getView_cnt(), vo.getFee(), vo.getFee_cnt(),
				vo.getCom_cnt(), vo.getClass_dl_cnt1(), vo.getSearch_dl_cnt1(),
				vo.getHot_dl_cnt1(), vo.getMan_dl_cnt1(), vo.getFee1(),
				vo.getCom_cnt1(), vo.getClass_dl_cnt2(),
				vo.getSearch_dl_cnt2(), vo.getHot_dl_cnt2(),
				vo.getMan_dl_cnt2(), vo.getFee2(), vo.getCom_cnt2() };
		transactionDB.executeBySQLCode(sqlCode, paras);
	}

	public void doInsertVoWithNoTran(List<RankVo> vos) throws DAOException {
		List<Executor> executorLists = new ArrayList<Executor>();
		Executor executor = null;
		for (RankVo vo : vos) {
			String sqlCode = "com.aspire.dotcard.rank.dao.RankDao.doInsertVo().INSERT";
			Object[] paras = { vo.getStat_time(), vo.getContent_id(),
					vo.getContent_name(), vo.getPrd_type_id(),
					vo.getAdd_dl_cnt(), vo.getAdd_fee(), vo.getDl_15days_cnt(),
					vo.getView_7days_cnt(), vo.getDl_7days_cnt(),
					vo.getFee_7days_cnt(), vo.getClass_dl_cnt(),
					vo.getSearch_dl_cnt(), vo.getHot_dl_cnt(),
					vo.getMan_dl_cnt(), vo.getDl_cnt(), vo.getView_cnt(),
					vo.getFee(), vo.getFee_cnt(), vo.getCom_cnt(),
					vo.getClass_dl_cnt1(), vo.getSearch_dl_cnt1(),
					vo.getHot_dl_cnt1(), vo.getMan_dl_cnt1(), vo.getFee1(),
					vo.getCom_cnt1(), vo.getClass_dl_cnt2(),
					vo.getSearch_dl_cnt2(), vo.getHot_dl_cnt2(),
					vo.getMan_dl_cnt2(), vo.getFee2(), vo.getCom_cnt2() };
			executor = new Executor();
			executor.setSql(DB.getInstance().getSQLByCode(sqlCode));
			executor.setParas(paras);
			executorLists.add(executor);
		}
		// String sqlCode =
		// "com.aspire.dotcard.rank.dao.RankDao.doInsertVo().INSERT";
		// Object[] paras = { vo.getStat_time(), vo.getContent_id(),
		// vo.getContent_name(), vo.getPrd_type_id(), vo.getAdd_dl_cnt(),
		// vo.getAdd_fee(), vo.getDl_15days_cnt(), vo.getView_7days_cnt(),
		// vo.getDl_7days_cnt(), vo.getFee_7days_cnt(), vo.getClass_dl_cnt(),
		// vo.getSearch_dl_cnt(), vo.getHot_dl_cnt(),
		// vo.getMan_dl_cnt(),vo.getDl_cnt(),
		// vo.getView_cnt(),vo.getFee(),vo.getFee_cnt(), vo.getCom_cnt(),
		// vo.getClass_dl_cnt1(), vo.getSearch_dl_cnt1(), vo.getHot_dl_cnt1(),
		// vo.getMan_dl_cnt1(), vo.getFee1(), vo.getCom_cnt1(),
		// vo.getClass_dl_cnt2(), vo.getSearch_dl_cnt2(), vo.getHot_dl_cnt2(),
		// vo.getMan_dl_cnt2(), vo.getFee2(), vo.getCom_cnt2()};
		// DB.getInstance().executeBySQLCode(sqlCode, paras);
		DB.getInstance().executeMuti(executorLists);
		executorLists.clear();
	}

	public void insertBatch(List<RankVo> vos) throws DAOException {
		String sqlCode = "com.aspire.dotcard.rank.dao.RankDao.doInsertVo().INSERT";
		String sql = DB.getInstance().getSQLByCode(sqlCode);
		Object[][] mutiParas = new Object[vos.size()][31];
		for (int i = 0; i < vos.size(); i++) {
			RankVo vo = vos.get(i);
			mutiParas[i][0] = vo.getStat_time();
			mutiParas[i][1] = vo.getContent_id();
			mutiParas[i][2] = vo.getContent_name();
			mutiParas[i][3] = vo.getPrd_type_id();
			mutiParas[i][4] = vo.getAdd_dl_cnt();
			mutiParas[i][5] = vo.getAdd_fee();
			mutiParas[i][6] = vo.getDl_15days_cnt();
			mutiParas[i][7] = vo.getView_7days_cnt();
			mutiParas[i][8] = vo.getDl_7days_cnt();
			mutiParas[i][9] = vo.getFee_7days_cnt();
			mutiParas[i][10] = vo.getClass_dl_cnt();
			mutiParas[i][11] = vo.getSearch_dl_cnt();
			mutiParas[i][12] = vo.getHot_dl_cnt();
			mutiParas[i][13] = vo.getMan_dl_cnt();
			mutiParas[i][14] = vo.getDl_cnt();
			mutiParas[i][15] = vo.getView_cnt();
			mutiParas[i][16] = vo.getFee();
			mutiParas[i][17] = vo.getFee_cnt();
			mutiParas[i][18] = vo.getCom_cnt();
			mutiParas[i][19] = vo.getClass_dl_cnt1();
			mutiParas[i][20] = vo.getSearch_dl_cnt1();
			mutiParas[i][21] = vo.getHot_dl_cnt1();
			mutiParas[i][22] = vo.getMan_dl_cnt1();
			mutiParas[i][23] = vo.getFee1();
			mutiParas[i][24] = vo.getCom_cnt1();
			mutiParas[i][25] = vo.getClass_dl_cnt2();
			mutiParas[i][26] = vo.getSearch_dl_cnt2();
			mutiParas[i][27] = vo.getHot_dl_cnt2();
			mutiParas[i][28] = vo.getMan_dl_cnt2();
			mutiParas[i][29] = vo.getFee2();
			mutiParas[i][30] = vo.getCom_cnt2();
		}
		;
		DB.getInstance().executeBatch(sql, mutiParas);

	}
	
	public void doTruncateTable() throws DAOException{
		String sqlCode ="com.aspire.dotcard.rank.dao.RankDao.doTruncateTable().truncate";
		DB.getInstance().executeBySQLCode(sqlCode, null);
	}
}
