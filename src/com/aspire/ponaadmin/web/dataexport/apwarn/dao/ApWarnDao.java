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
	 * ��ͼ��ʼ��Ϊ��
	 */
	private void createTableFromView()throws Exception {

		String createTableSql = "create table T_AP_WARN_TEMP as select * from ap_warn_view";
		logger.debug("������ʱ���sqlΪ��" + createTableSql);
		try {
			DB.getInstance().execute(createTableSql, null);
		} catch (DAOException e) {
			logger.error("������ʱ�����" + e);
			throw e;
		}		

	}

	/**
	 * ɾ����ʱ��
	 */
	private void dropTable() {
		String dropSql = "drop table T_AP_WARN_TEMP";
		logger.debug("ɾ����ʱ���sqlΪ��" + dropSql);
		try {
			DB.getInstance().execute(dropSql, null);
		} catch (DAOException e) {
			logger.error("ɾ����ʱ�����" + e);
		}
	}
	
	/**
	 * ��ʼԤ������
	 */
	public void initData()throws Exception{
		this.dropTable();
		this.createTableFromView();
	}

	/**
	 * ��ѯ�����������Ƚϴ�� �����Ʒ�б�
	 * @return
	 * @throws Exception
	 */
	public List getFreeWarnApList()throws Exception{
		logger.debug("��ѯ�����������Ƚϴ�� �����Ʒ�б� ApWarnDao.getFreeWarnApList begin!");
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
					sb.append("����ƷΪ�ʷѴ���1Ԫ���������ͣ������������������Ʒ��׼�ж�����ˢ��");
				}else{
					sb.append("����ƷΪ�����Ʒ�������������������Ʒ��׼�ж�����ˢ��");
				}
				sb.append("�������ش���:"+vo.getDayDLTimes());
				sb.append(";�������ش���:"+vo.getYesterdayDLTimes());
				sb.append(";7�����ش�����ֵ:"+(float)vo.getDl7DaysCounts()/7);
				vo.setWarnDesc(sb.toString());
				free.add(vo);
				sb = null;
			}
		}catch(Exception e){
			logger.error("��ѯ�����������Ƚϴ�� �����Ʒ�б�ʧ��",e);
			throw e;
		}finally{
			DB.close(rs);
		}
		logger.debug("��ѯ�����������Ƚϴ�� �����Ʒ�б� ApWarnDao.getFreeWarnApList end!");
		return free;
	}
	
	/**
	 * ��ѯ�����������Ƚϴ�� ������Ʒ�б�
	 * @return
	 * @throws Exception
	 */
	public List getPayWarnApList()throws Exception{
		logger.debug("��ѯ�����������Ƚϴ�� ������Ʒ�б� ApWarnDao.getPayWarnApList begin!");
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
				sb.append("����ƷΪ������Ʒ��������������������Ʒ��׼�ж�����ˢ��");
				sb.append("�������ش���:"+vo.getDayDLTimes());
				sb.append(";�������ش���:"+vo.getYesterdayDLTimes());
				sb.append(";7�����ش�����ֵ:"+(float)vo.getDl7DaysCounts()/7);
				vo.setWarnDesc(sb.toString());	
				pay.add(vo);
				sb = null;
			}
		}catch(Exception e){
			logger.error("��ѯ�����������Ƚϴ�� ������Ʒ�б�ʧ��",e);
			throw e;
		}finally{
			DB.close(rs);
		}		
		logger.debug("��ѯ�����������Ƚϴ�� ������Ʒ�б� ApWarnDao.getPayWarnApList end!");
		
		return pay;
	}
	
	/**
	 * ��¼Ԥ����ʷ
	 * @param list
	 * @throws Exception
	 */
	public void addApWarnData(List list)throws Exception{
		logger.debug("Ԥ����ʷ��¼���� ��ʼ");
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
			logger.error("Ԥ����ʷ��¼�����쳣",e);
			throw new BOException("����APԤ���쳣");
		}
		logger.debug("Ԥ����ʷ��¼���� ����");
	}
}
