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
	 * ���췽��
	 * @param errorMsg ������Ϣ
	 */
    public ExcelException (String errorMsg)
    {
        super (errorMsg) ;
    }
	/**
	 * ���췽��
	 * @param errorMsg ������Ϣ
	 * @param cause ����BO�쳣�ĸ�Դ�쳣
	 */
    public ExcelException (String errorMsg, Throwable cause)
    {
        super (errorMsg, cause) ;
    }
}
