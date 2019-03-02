/**
 * SSMS
 * com.aspire.ponaadmin.web.category.export.repchange ExportRechangeRate1.java
 * Apr 19, 2010
 * @author tungke
 * @version 1.0
 *
 */
package com.aspire.ponaadmin.web.category.export.repchange;

import java.util.ArrayList;

import com.aspire.ponaadmin.web.excel.ExcelElementTemplate;

/**
 * @author tungke
 *
 *
 *提取变更率
select b.listname3 机型,b.listname1 类型,b.listname2 榜单名,a.allcounts 应用总数,a.counts 榜单应用数,a.difcounts 更换应用数,(a.difcounts/a.counts)*100 榜单更换率 
from r_charts_turn02 a,r_static_category_list b where a.categoryid=b.categoryid 
order by b.listname3,b.listname1,b.listname2;
 *
 *分机型提取榜单变更率
 *
 */
public class ExportRechangeRate1 implements ExcelElementTemplate {

	/* b.listname3 机型,
     b.listname1 类型,
     b.listname2 榜单名,
     a.allcounts 应用总数,
     a.counts 榜单应用数,
     a.difcounts 更换应用数,
     (a.difcounts / a.counts) * 100 榜单更换率
	*/
	
	
	public String deviceName;	// 机型
	public String type;			// 类型
	public String caName;		// 榜单名,
	public Integer allCount;	// 应用总数
	public Integer count;		// 榜单应用数
	public Integer difCount;	// 更换应用数
	public String changeRate;  // 榜单更换率
	
	
	
	
	/**
	 * @return Returns the allCount.
	 */
	public Integer getAllCount() {
		return allCount;
	}




	/**
	 * @param allCount The allCount to set.
	 */
	public void setAllCount(Integer allCount) {
		this.allCount = allCount;
	}




	




	/**
	 * @return Returns the changeRate.
	 */
	public String getChangeRate()
	{
		java.text.DecimalFormat   myformat=new   java.text.DecimalFormat("#0.00"); 
		Double ls = new Double(changeRate);
		return myformat.format(ls.doubleValue());
	}




	/**
	 * @param changeRate The changeRate to set.
	 */
	public void setChangeRate(String changeRate)
	{
		this.changeRate = changeRate;
	}




	/**
	 * @return Returns the count.
	 */
	public Integer getCount() {
		return count;
	}




	/**
	 * @param count The count to set.
	 */
	public void setCount(Integer count) {
		this.count = count;
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




	/**
	 * @return Returns the difCount.
	 */
	public Integer getDifCount() {
		return difCount;
	}




	/**
	 * @param difCount The difCount to set.
	 */
	public void setDifCount(Integer difCount) {
		this.difCount = difCount;
	}




	/**
	 * @return Returns the type.
	 */
	public String getType() {
		return type;
	}




	/**
	 * @param type The type to set.
	 */
	public void setType(String type) {
		this.type = type;
	}




	/**
	 * @return Returns the caName.
	 */
	public String getCaName() {
		return caName;
	}




	/**
	 * @param caName The caName to set.
	 */
	public void setCaName(String caName) {
		this.caName = caName;
	}




	/* (non-Javadoc)
	 * @see com.aspire.ponaadmin.web.excel.ExcelElementTemplate#getExcelElement()
	 */
	public ArrayList getExcelElement() {
		ArrayList arrayList = new ArrayList();
		//压入队列 FIFO
		//调整顺序，序号显示在第一列
		arrayList.add(this.getDeviceName());
		arrayList.add(this.getType());
		arrayList.add(this.getCaName());
		arrayList.add(this.getAllCount().toString());
		arrayList.add(this.getCount().toString());
		arrayList.add(this.getDifCount().toString());
		arrayList.add(this.getChangeRate().toString());	
		return arrayList;
	}

}
