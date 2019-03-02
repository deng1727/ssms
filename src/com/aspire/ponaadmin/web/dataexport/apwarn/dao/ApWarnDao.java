package com.aspire.ponaadmin.web.dataexport.apwarn.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.dataexport.apwarn.ApWarnConfig;
import com.aspire.ponaadmin.web.dataexport.apwarn.ApWarnConstants;
import com.aspire.ponaadmin.web.dataexport.apwarn.vo.ApWarnVo;
import com.aspire.ponaadmin.web.util.DateUtil;

public class ApWarnDao {
	protected static JLogger logger = LoggerFactory.getLogger(ApWarnDao.class);

	private static ApWarnDao bd = new ApWarnDao();

	public static ApWarnDao getInstance() {
		return bd;
	}

	/**
	 * 视图初始化为表
	 */
	private void createTableFromView()throws Exception {

		String createTableSql = "create table T_AP_WARN_TEMP as select * from ap_warn_view";
		logger.debug("创建临时表的sql为：" + createTableSql);
		try {
			DB.getInstance().execute(createTableSql, null);
		} catch (DAOException e) {
			logger.error("创建临时表出错" + e);
			throw e;
		}		

	}

	/**
	 * 删掉临时表
	 */
	private void dropTable() {
		String dropSql = "drop table T_AP_WARN_TEMP";
		logger.debug("删除临时表的sql为：" + dropSql);
		try {
			DB.getInstance().execute(dropSql, null);
		} catch (DAOException e) {
			logger.error("删除临时表出错" + e);
		}
	}
	
	/**
	 * 初始预警数据
	 */
	public void initData()throws Exception{
		this.dropTable();
		this.createTableFromView();
	}

	/**
	 * 查询下载量波动比较大的 免费商品列表
	 * @return
	 * @throws Exception
	 */
	public List getFreeWarnApList()throws Exception{
		logger.debug("查询下载量波动比较大的 免费商品列表 ApWarnDao.getFreeWarnApList begin!");
		ResultSet rs = null;
		String sqlCode = "apwarn.dao.ApWarnDao.getFreeWarnApList";
		Object paras[] = new Object[]{new Integer(ApWarnConfig.freeWarnMaxDayOrder),new Integer(ApWarnConfig.freeWarnDayOrderLessYDayOrder)
		,new Integer(ApWarnConfig.freeWarnDayOrderLess7DayOrder),new Integer(ApWarnConfig.freeWarnDayOrderYDayOrderPercent)
		,new Integer(ApWarnConfig.freeWarnDayOrder7DayOrderPercent)};	
		List free = new ArrayList();
		ApWarnVo vo = null;
		StringBuffer sb = null;
		Calendar cl = Calendar.getInstance();
		cl.add(Calendar.DAY_OF_MONTH, -1);
		String d = DateUtil.formatDate(cl.getTime(), "yyyyMMdd");		
		try{
			rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
			while (rs.next()){
				vo = new ApWarnVo();
				vo.setWarnDate(d);
				vo.setContentId(rs.getString("contentid"));
				vo.setName(rs.getString("name"));
				vo.setWarnType(ApWarnConstants.WARN_DL_WAVE);
				vo.setDayDLTimes(rs.getInt("dayordertimes"));
				vo.setYesterdayDLTimes(rs.getInt("programsize"));
				vo.setDl7DaysCounts(rs.getInt("downloadtimes"));
				vo.setType(rs.getString("type"));
				vo.setPayType(rs.getString("chargetime"));
				vo.setPrice(rs.getInt("price"));
				//vo.setAppCateName(rs.getString("appcatename"));
				//vo.setAppCateId(rs.getString("appcateid"));
				vo.setSpName(rs.getString("spname"));
				vo.setMarketDate(rs.getString("marketdate"));
				sb = new StringBuffer();
				if(ApWarnConstants.APP_TYPE_THEME.equals(vo.getType())){
					sb.append("此商品为资费大于1元的主题类型，按下载量波动免费商品标准判断涉嫌刷榜。");
				}else{
					sb.append("此商品为免费商品，按下载量波动免费商品标准判断涉嫌刷榜。");
				}
				sb.append("当日下载次数:"+vo.getDayDLTimes());
				sb.append(";昨日下载次数:"+vo.getYesterdayDLTimes());
				sb.append(";7天下载次数均值:"+(float)vo.getDl7DaysCounts()/7);
				vo.setWarnDesc(sb.toString());
				free.add(vo);
				sb = null;
			}
		}catch(Exception e){
			logger.error("查询下载量波动比较大的 免费商品列表失败",e);
			throw e;
		}finally{
			DB.close(rs);
		}
		logger.debug("查询下载量波动比较大的 免费商品列表 ApWarnDao.getFreeWarnApList end!");
		return free;
	}
	
	/**
	 * 查询下载量波动比较大的 付费商品列表
	 * @return
	 * @throws Exception
	 */
	public List getPayWarnApList()throws Exception{
		logger.debug("查询下载量波动比较大的 付费商品列表 ApWarnDao.getPayWarnApList begin!");
		ResultSet rs = null;
		String sqlCode = "apwarn.dao.ApWarnDao.getPayWarnApList";
		Object paras[] = new Object[]{new Integer(ApWarnConfig.payWarnMaxDayOrder),new Integer(ApWarnConfig.payWarnDayOrderLessYDayOrder)
		,new Integer(ApWarnConfig.payWarnDayOrderLess7DayOrder),new Integer(ApWarnConfig.payWarnDayOrderYDayOrderPercent)
		,new Integer(ApWarnConfig.payWarnDayOrder7DayOrderPercent)};	
		List pay = new ArrayList();		
		ApWarnVo vo = null;
		StringBuffer sb = null;		
		Calendar cl = Calendar.getInstance();
		cl.add(Calendar.DAY_OF_MONTH, -1);
		String d = DateUtil.formatDate(cl.getTime(), "yyyyMMdd");		
		try{
			rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
			while (rs.next()){
				vo = new ApWarnVo();
				vo.setWarnDate(d);
				vo.setContentId(rs.getString("contentid"));
				vo.setName(rs.getString("name"));
				vo.setWarnType(ApWarnConstants.WARN_DL_WAVE);
				vo.setDayDLTimes(rs.getInt("dayordertimes"));
				vo.setYesterdayDLTimes(rs.getInt("programsize"));
				vo.setDl7DaysCounts(rs.getInt("downloadtimes"));
				vo.setType(rs.getString("type"));
				vo.setPayType(rs.getString("chargetime"));
				vo.setPrice(rs.getInt("price"));
				//vo.setAppCateName(rs.getString("appcatename"));
				//vo.setAppCateId(rs.getString("appcateid"));
				vo.setSpName(rs.getString("spname"));
				vo.setMarketDate(rs.getString("marketdate"));				
				sb = new StringBuffer();
				sb.append("此商品为付费商品，按下载量波动付费商品标准判断涉嫌刷榜。");
				sb.append("当日下载次数:"+vo.getDayDLTimes());
				sb.append(";昨日下载次数:"+vo.getYesterdayDLTimes());
				sb.append(";7天下载次数均值:"+(float)vo.getDl7DaysCounts()/7);
				vo.setWarnDesc(sb.toString());	
				pay.add(vo);
				sb = null;
			}
		}catch(Exception e){
			logger.error("查询下载量波动比较大的 付费商品列表失败",e);
			throw e;
		}finally{
			DB.close(rs);
		}		
		logger.debug("查询下载量波动比较大的 付费商品列表 ApWarnDao.getPayWarnApList end!");
		
		return pay;
	}
	
	/**
	 * 记录预警历史
	 * @param list
	 * @throws Exception
	 */
	public void addApWarnData(List list)throws Exception{
		logger.debug("预警历史记录插入 开始");
		ApWarnVo vo = null;
		String sqlCode = "apwarn.dao.ApWarnDao.insert";
		Object mutiParas[][] = new Object[list.size()][13];

		for (int i = 0; i < list.size(); i++) {
			vo = (ApWarnVo) list.get(i);
			mutiParas[i][0] = vo.getWarnDate();
			mutiParas[i][1] = vo.getContentId();
			mutiParas[i][2] = vo.getName();
			mutiParas[i][3] = new Integer(vo.getWarnType());
			mutiParas[i][4] = new Integer(vo.getDayDLTimes());
			mutiParas[i][5] = new Integer(vo.getYesterdayDLTimes());
			mutiParas[i][6] = new Integer(vo.getDl7DaysCounts());
			mutiParas[i][7] = vo.getPayType();
			mutiParas[i][8] = vo.getType();
			mutiParas[i][9] = new Integer(vo.getPrice());
			mutiParas[i][10] = vo.getWarnDesc();
			mutiParas[i][11] = vo.getSpName();
			mutiParas[i][12] = vo.getMarketDate();
		}
		try {
			DB.getInstance().executeBatchBySQLCode(sqlCode, mutiParas);
		} catch (DAOException e) {
			logger.error("预警历史记录插入异常",e);
			throw new BOException("批量AP预警异常");
		}
		logger.debug("预警历史记录插入 结束");
	}
}
