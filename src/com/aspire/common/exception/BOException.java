package com.aspire.common.exception ;

/**
 * <p>BO异常类</p>
 * <p>BO异常类，为组件的BO类异常使用</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class BOException extends Exception
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * BO异常对应的错误代码
     */
	private int errorCode ;

	/**
	 * 构造方法
	 * @param errorMsg 错误信息
	 */
    public BOException (String errorMsg)
    {
        super (errorMsg) ;
    }

	/**
	 * 构造方法
	 * @param errorMsg 错误信息
	 * @param cause 引起BO异常的根源异常
	 */
    public BOException (String errorMsg, Throwable cause)
    {
        super (errorMsg, cause) ;
    }

    /**
     * 构造方法
     * @param errorMsg 错误信息
     * @param errorCode 异常对应的错误代码
     */
	public BOException (String errorMsg, int errorCode)
    {
        super (errorMsg) ;
        this.errorCode = errorCode ;
    }

    /**
     * 构造方法
     * @param errorMsg 错误信息
     * @param cause 引起BO异常的根源异常
     * @param errorCode 异常对应的错误代码
     */
	public BOException (String errorMsg, Throwable cause, int errorCode)
    {
        super (errorMsg, cause) ;
        this.errorCode = errorCode ;
    }

    /**
     * 构造方法
     * @param cause 引起BO异常的根源异常
     * @param errorCode 异常对应的错误代码
     */
	public BOException (Throwable cause, int errorCode)
    {
        super (cause) ;
        this.errorCode = errorCode ;
    }

    /**
     * 获取BO异常对应的错误代码
     * @return 错误代码
     */
	public int getErrorCode ()
    {
        return this.errorCode ;
    }

    /**
     * 设置BO异常对应的错误代码
     * @param errorCode int BO异常对应的错误代码
     */
    public void setErrorCode(int errorCode){
        this.errorCode = errorCode;
    }
}
