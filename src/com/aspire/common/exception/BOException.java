package com.aspire.common.exception ;

/**
 * <p>BO�쳣��</p>
 * <p>BO�쳣�࣬Ϊ�����BO���쳣ʹ��</p>
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
     * BO�쳣��Ӧ�Ĵ������
     */
	private int errorCode ;

	/**
	 * ���췽��
	 * @param errorMsg ������Ϣ
	 */
    public BOException (String errorMsg)
    {
        super (errorMsg) ;
    }

	/**
	 * ���췽��
	 * @param errorMsg ������Ϣ
	 * @param cause ����BO�쳣�ĸ�Դ�쳣
	 */
    public BOException (String errorMsg, Throwable cause)
    {
        super (errorMsg, cause) ;
    }

    /**
     * ���췽��
     * @param errorMsg ������Ϣ
     * @param errorCode �쳣��Ӧ�Ĵ������
     */
	public BOException (String errorMsg, int errorCode)
    {
        super (errorMsg) ;
        this.errorCode = errorCode ;
    }

    /**
     * ���췽��
     * @param errorMsg ������Ϣ
     * @param cause ����BO�쳣�ĸ�Դ�쳣
     * @param errorCode �쳣��Ӧ�Ĵ������
     */
	public BOException (String errorMsg, Throwable cause, int errorCode)
    {
        super (errorMsg, cause) ;
        this.errorCode = errorCode ;
    }

    /**
     * ���췽��
     * @param cause ����BO�쳣�ĸ�Դ�쳣
     * @param errorCode �쳣��Ӧ�Ĵ������
     */
	public BOException (Throwable cause, int errorCode)
    {
        super (cause) ;
        this.errorCode = errorCode ;
    }

    /**
     * ��ȡBO�쳣��Ӧ�Ĵ������
     * @return �������
     */
	public int getErrorCode ()
    {
        return this.errorCode ;
    }

    /**
     * ����BO�쳣��Ӧ�Ĵ������
     * @param errorCode int BO�쳣��Ӧ�Ĵ������
     */
    public void setErrorCode(int errorCode){
        this.errorCode = errorCode;
    }
}
