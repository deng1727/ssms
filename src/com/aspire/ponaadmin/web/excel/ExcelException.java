package com.aspire.ponaadmin.web.excel;

/**
 * @author zhanggaojing
 *
 */
public class ExcelException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * 构造方法
	 * @param errorMsg 错误信息
	 */
    public ExcelException (String errorMsg)
    {
        super (errorMsg) ;
    }
	/**
	 * 构造方法
	 * @param errorMsg 错误信息
	 * @param cause 引起BO异常的根源异常
	 */
    public ExcelException (String errorMsg, Throwable cause)
    {
        super (errorMsg, cause) ;
    }
}
