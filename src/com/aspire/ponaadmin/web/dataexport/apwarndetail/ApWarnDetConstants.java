/*
 * 文件名：ApWarnDetConstants.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */
package com.aspire.ponaadmin.web.dataexport.apwarndetail;



/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2010</p>
 * @author x_wangml
 * @version 
 */
public class ApWarnDetConstants
{
    /**
     * 描述标点
     */
    public static final String WARN_DET_INTERPUNCTION = ". ";
    
    public static final String WARN_DET_PERCENT = "%";
    
    /**
     *  存在超标现象
     */
    public static final String WARN_DET_YES = "已超标";
    
    /**
     * 不存在超标现象
     */
    public static final String WARN_DET_NO = "不超标";
    
    /**
     * 日下载次数描述
     */
    public static final String WARN_DET_DOWNLOAD_DAY = "当日下载量次数为：";
    
    /**
     * 同手机号下载次数描述
     */
    public static final String WARN_DET_DOWNLOAD_USER = "同手机号下载最多次数为：";
    
    /**
     * 同手机号下载与总下载率描述
     */
    public static final String WARN_DET_USER_DOWNLOAD_QUOTIETY = "同手机号下载量占总下载量百分比为：";
    
    /**
     * 手机允许最大连号数描述
     */
    public static final String WARN_DET_SERIES_USER = "手机连号数为：";
    
    /**
     * 下载用户重叠率超标描述
     */
    public static final String WARN_DET_USER_ITERANCE_QUOTIETY = "下载用户重叠率为：";
    
    /**
     * 下载量最大城市不在范围描述
     */
    public static final String WARN_DET_DOWNLOAD_CITY = "下载量最大非指定城市为：";
    
    /**
     * 下载峰值时段不在范围描述
     */
    public static final String WARN_DET_DOWNLOAD_TIME = "下载峰值非指定时段为：";
    
    /**
     * 下载时段超总量百分比异常描述
     */
    public static final String WARN_DET_TIME = "下载时段超总量百分比异常值为：";
    
    /**
     * 应用类型为免费
     */
    public static final String WARN_PAY_TYPE_0 = "免费";
    
    /**
     * 应用类型为付费
     */
    public static final String WARN_PAY_TYPE_1 = "付费";
    
    /**
     * 预警类型为嫌疑
     */
    public static final String WARN_GRADE_SUSPICION = "嫌疑";
    
    /**
     * 预警类型为确认
     */
    public static final String WARN_GRADE_VALIDATE = "确认";
}
