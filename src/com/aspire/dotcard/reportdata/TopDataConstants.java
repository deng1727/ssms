
package com.aspire.dotcard.reportdata;

/**
 * <p>
 * Title: IMALL PAS
 * </p>
 * <p>
 * Description: 排行榜数据导入常量类。
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: Aspire Technologies
 * </p>
 * 
 * @author x_liyunhong
 * @version 1.0
 */
public class TopDataConstants
{

    /**
     * 操作结果码定义：成功
     */
    public static final int TD_SUCC = 0;

    /**
     * 操作结果码定义：FTP，不存在对应的接口文件
     */
    public static final int TD_FTP_DATAFILE_NOTFOUND = 9001;

    /**
     * 操作结果码定义：FTP，获取接口文件失败
     */
    public static final int TD_FTP_GETDATAFILE_ERROR = 9002;

    /**
     * 操作结果码定义：FTP，其它的ftp操作失败
     */
    public static final int TD_FTP_ERROR = 9099;

    /**
     * 操作结果码定义：数据导入，失败
     */
    public static final int TD_IMP_ERROR = 9199;
    
    /**
     * 日数据导入类型
     */
    public static final int CONTENT_TYPE_DAY = 4;
    
    /**
     * 周数据导入类型
     */
    public static final int CONTENT_TYPE_WEEK = 5;
    
    /**
     * 月数据导入类型
     */
    public static final int CONTENT_TYPE_MONTH = 6;

}
