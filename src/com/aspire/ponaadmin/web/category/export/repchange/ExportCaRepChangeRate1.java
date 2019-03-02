/**
 * SSMS
 * com.aspire.ponaadmin.web.category.export.repchange ExportCaRepChangeRate1.java
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
 *select b.listname2 榜单名,sum(a.difcounts)/sum(a.counts)
from r_charts_turn02 a,r_static_category_list b where a.categoryid=b.categoryid 
group by b.listname2
 *
 *提取榜单变更率
 *
 */
public class ExportCaRepChangeRate1 implements ExcelElementTemplate {

	
	public String CateName;
	public String changeRate;
	
	
	
	/**
	 * @return Returns the cateName.
	 */
	public String getCateName() {
		return CateName;
	}



	/**
	 * @param cateName The cateName to set.
	 */
	public void setCateName(String cateName) {
		CateName = cateName;
	}



	



	/**
	 * @return Returns the changeRate.
	 */
	public String getChangeRate()
	{
		java.text.DecimalFormat   myformat=new   java.text.DecimalFormat("#0.00"); 
		Double ls = new Double(changeRate);
		return myformat.format(ls.doubleValue());
		//return changeRate;
	}



	/**
	 * @param changeRate The changeRate to set.
	 */
	public void setChangeRate(String changeRate)
	{
		this.changeRate = changeRate;
	}



	/* (non-Javadoc)
	 * @see com.aspire.ponaadmin.web.excel.ExcelElementTemplate#getExcelElement()
	 */
	public ArrayList getExcelElement() {
		ArrayList arrayList = new ArrayList();
		//压入队列 FIFO
		//调整顺序，序号显示在第一列
		arrayList.add(this.getCateName());
		arrayList.add(this.getChangeRate().toString());
		return arrayList;
	}

}
