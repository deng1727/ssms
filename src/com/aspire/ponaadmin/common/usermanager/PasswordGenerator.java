package com.aspire.ponaadmin.common.usermanager ;

import java.util.Random;

/**
 * <p>�û�������������</p>
 * <p>�û�������������</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public final class PasswordGenerator
{

	/**
	 * ˽�л����췽�������ⱻ���⹹�졣
	 */
	private PasswordGenerator () 
	{
		
	}

    /**
     * �����µ��û�����
     * ����java�����
     * @return String
     */
    public static String generate ()
    {
        int pwdLen = 10 ; //���볤��
        StringBuffer password = new StringBuffer(pwdLen);
        Random random = new Random();
        for(int i = 0; i < pwdLen; i++)
        {
            //һ������һ���ַ����ַ���Χ0-9
            int key = (int) (random.nextFloat()*10);
            if(key == 10)
            {
                key = 0;
            }
            password.append(key);
        }
        return password.toString();
    }
}
