package com.aspire.ponaadmin.web.category.export.repchange;

import java.util.ArrayList;

import com.aspire.ponaadmin.web.excel.ExcelElementTemplate;

/**
 * 
 * @author tungke
 *
 *
 *select b.listname3,b.listname1,count(*),count(distinct a.id),(count(*)-count(distinct a.id))/count(*)*100  from r_charts_turnhis a,r_static_category_list b where a.phdate=to_char(sysdate,'yyyymmdd') 
and a.categoryid=b.categoryid and a.rowlist<31
group by b.listname3,b.listname1;
 *
 *分机型统计榜单分类重复率
 *
 */
public class ExportRepeateRate1  implements ExcelElementTemplate{

	
	public String deviceName;		//机型
	public String appType;			//应用类型
	public Integer caAllCount;		//本期榜单应用总数
	public Integer canoRepeateCount;//不重复应用数
	public String canorepeateRate; //应用重复率
	
	
	
	/**
	 * @return Returns the appType.
	 */
	public String getAppType() {
		return appType;
	}



	/**
	 * @param appType The appType to set.
	 */
	public void setAppType(String appType) {
		this.appType = appType;
	}



	/**
	 * @return Returns the caAllCount.
	 */
	public Integer getCaAllCount() {
		return caAllCount;
	}



	/**
	 * @param caAllCount The caAllCount to set.
	 */
	public void setCaAllCount(Integer caAllCount) {
		this.caAllCount = caAllCount;
	}



	/**
	 * @return Returns the canoRepeateCount.
	 */
	public Integer getCanoRepeateCount() {
		return canoRepeateCount;
	}



	/**
	 * @param canoRepeateCount The canoRepeateCount to set.
	 */
	public void setCanoRepeateCount(Integer canoRepeateCount) {
		this.canoRepeateCount = canoRepeateCount;
	}







	/**
	 * @return Returns the canorepeateRate.
	 */
	public String getCanorepeateRate()
	{
		java.text.DecimalFormat   myformat=new   java.text.DecimalFormat("#0.00"); 
		Double ls = new Double(canorepeateRate);
		return myformat.format(ls.doubleValue());
	}



	/**
	 * @param canorepeateRate The canorepeateRate to set.
	 */
	public void setCanorepeateRate(String canorepeateRate)
	{
		this.canorepeateRate = canorepeateRate;
	}



	/**
	 * @return Returns the deviceName.
	 */
	public String getDeviceName() {
		return deviceName;
	}



	/**
	 * @param deviceName The deviceName to set.
	 */
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}



	public ArrayList getExcelElement() {
		ArrayList arrayList = new ArrayList();
		//压入队列 FIFO
		//调整顺序，序号显示在第一列
		arrayList.add(this.getDeviceName());
		arrayList.add(this.getAppType());
		arrayList.add(this.getCaAllCount().toString());
		arrayList.add(this.getCanoRepeateCount().toString());
		arrayList.add(this.getCanorepeateRate().toString());
		
		return arrayList;
	}

}
