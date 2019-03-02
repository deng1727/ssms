package com.aspire.dotcard.hwcolorring.clrLoad ;

public class ColorringLoadConstants
{
    /**
     * 操作结果码定义：成功
     */
    public static final int RC_SUCC = 0 ;

    /**
     * 操作结果码定义：FTP，不存在对应的彩铃全量文件
     */
    public static final int RC_FTP_FULLDATAFILE_NOTFOUND = 9001 ;

    /**
     * 操作结果码定义：FTP，获取彩铃全量文件失败
     */
    public static final int RC_FTP_GETFULLDATAFILE_ERROR = 9002 ;

    /**
     * 操作结果码定义：FTP，其它的ftp操作失败
     */
    public static final int RC_FTP_ERROR = 9099 ;

    /**
     * 操作结果码定义：数据导入，失败
     */
    public static final int RC_IMP_ERROR = 9199 ;

}
