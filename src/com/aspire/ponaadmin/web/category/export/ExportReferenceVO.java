/**
 * <p>
 * 指定货架导出数据模型类
 * </p>
 * <p>
 * Copyright (c) 2009 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved
 * </p>
 * @Sep 16, 2009
 * @author dongke
 * @version 1.0.0.0
 */
package com.aspire.ponaadmin.web.category.export;

import java.util.ArrayList;
import java.util.LinkedList;
import com.aspire.ponaadmin.web.excel.ExcelElementTemplate;

/**
 * @author dongke
 *
 */
public class ExportReferenceVO  implements ExcelElementTemplate
{
	private String contentId;				//内容ID
	private String appCode;					//应用编码
	private String appName;					//应用名称
	private String contentType;				//应用类型
	private String spName;					//提供商名称
	private String createTime;				//上线时间
	private Integer sort;					//货架位置序号
	private Integer dayDownloadTime;		//日下载量
	private Integer weekDownloadTime;		//周下载量
	private Integer monthDownloadTime;		//月下载量
	private Integer totalDownloadTime;		//总下载量
	private Integer grade;					//评分
	private String deviceName;				//适配终端名称
	
	public String getAppCode()
	{
		return appCode;
	}
	public void setAppCode(String appCode)
	{
		this.appCode = appCode;
	}
	public String getAppName()
	{
		return appName;
	}
	public void setAppName(String appName)
	{
		this.appName = appName;
	}
	public String getContentId()
	{
		return contentId;
	}
	public void setContentId(String contentId)
	{
		this.contentId = contentId;
	}
	public String getContentType()
	{
		return contentType;
	}
	public void setContentType(String contentType)
	{
		this.contentType = contentType;
	}
	public String getCreateTime()
	{
		return createTime;
	}
	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}
	public Integer getDayDownloadTime()
	{
		return dayDownloadTime;
	}
	public void setDayDownloadTime(Integer dayDownloadTime)
	{
		this.dayDownloadTime = dayDownloadTime;
	}
	
	
	
	
	public Integer getGrade()
	{
		return grade;
	}
	public void setGrade(Integer grade)
	{
		this.grade = grade;
	}
	public Integer getMonthDownloadTime()
	{
		return monthDownloadTime;
	}
	public void setMonthDownloadTime(Integer monthDownloadTime)
	{
		this.monthDownloadTime = monthDownloadTime;
	}
	public Integer getSort()
	{
		return sort;
	}
	public void setSort(Integer sort)
	{
		this.sort = sort;
	}
	public String getSpName()
	{
		return spName;
	}
	public void setSpName(String spName)
	{
		this.spName = spName;
	}
	public Integer getTotalDownloadTime()
	{
		return totalDownloadTime;
	}
	public void setTotalDownloadTime(Integer totalDownloadTime)
	{
		this.totalDownloadTime = totalDownloadTime;
	}
	public Integer getWeekDownloadTime()
	{
		return weekDownloadTime;
	}
	public void setWeekDownloadTime(Integer weekDownloadTime)
	{
		this.weekDownloadTime = weekDownloadTime;
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
	
	
	public ArrayList getExcelElement()
	{
		ArrayList arrayList = new ArrayList();
		//压入队列 FIFO
		//调整顺序，序号显示在第一列
		arrayList.add(this.getSort().toString());
		arrayList.add(this.getContentId());
		arrayList.add(this.getAppCode());
		arrayList.add(this.getAppName());
		arrayList.add(this.getContentType());
		arrayList.add(this.getSpName());
		arrayList.add(this.getCreateTime());
		//arrayList.add(this.getSort().toString());
		arrayList.add(this.getDayDownloadTime().toString());
		arrayList.add(this.getWeekDownloadTime().toString());
		arrayList.add(this.getMonthDownloadTime().toString());
		arrayList.add(this.getTotalDownloadTime().toString());
		arrayList.add(this.getGrade().toString());
		arrayList.add(this.getDeviceName().toString());
		return arrayList;
	}
	
	
	
	
}
